import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Database {
    private String[] questions;
    private String[][] choices;
    private String[] correctAnswers;

    // connects and loads data
    public Database() {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/quiz_system";
            String username = "root";
            String password = "sonu1911";

            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Established successfully!");

            // SQL query
            String query = "SELECT id, question_text, option1, option2, option3, option4, correct_option FROM questions ORDER BY RAND() LIMIT 10";

            // statement
            Statement stmt = connection.createStatement();

            // Execute query
            ResultSet rs = stmt.executeQuery(query);

            questions = new String[10];
            choices = new String[10][4];
            correctAnswers = new String[10];

            int i = 0;
            while (rs.next() && i < 10) {
                questions[i] = rs.getString("question_text");
                for (int j = 0; j < 4; j++) {
                    choices[i][j] = rs.getString("option" + (j + 1));
                }
                correctAnswers[i]= rs.getString("correct_option");

                i++;
            }

            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }

    // Getter methods to return arrays
    public String[] getQuestions() {
        return questions;
    }

    public String[][] getChoices() {
        return choices;
    }

    public String[] getCorrectAnswers() {
        return correctAnswers;
    }
}

public class QuizSystem{
    
	public static void main(String[] args){
		Database db = new Database(); 
		SwingUtilities.invokeLater(() -> new UserWindow());
	}

}
// 🪟 First Window — Username Input
class UserWindow extends JFrame {

    public UserWindow() {
        setTitle("Online Quiz System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(210, 210, 210));
        add(panel);

        JLabel label = new JLabel("Enter Your Name:");
        JTextField nameField = new JTextField(20);
        JButton startBtn = new JButton("Start Quiz");

        Font labelFont = new Font("Times New Roman", Font.BOLD, 18);
        Font fieldFont = new Font("Times New Roman", Font.PLAIN, 18);
        label.setFont(labelFont);
        nameField.setFont(fieldFont);
        startBtn.setFont(fieldFont);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 20, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(label, gbc);

        gbc.gridy++;
        panel.add(nameField, gbc);

        gbc.gridy++;
        panel.add(startBtn, gbc);

        startBtn.addActionListener(e -> {
            String userName = nameField.getText().trim();
            if (userName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name!");
            } else {
                dispose(); // close current window
                new QuizWindow(userName); // open quiz window
            }
        });

        setVisible(true);
    }
}

// 🧠 Second Window — Quiz Window
class QuizWindow extends JFrame implements ActionListener {
    JLabel questionLabel, timerLabel, userLabel;
    JRadioButton[] options = new JRadioButton[4];
    JButton nextButton, submitButton;
    ButtonGroup group;
    Timer timer;
	
	Database db = new Database();
	String[] questions = db.getQuestions();
	String[][] choices = db.getChoices();
	String[] correctAnswers = db.getCorrectAnswers();
	
	int timeLeft = 20, currentQuestion = 0, score = 0;
    String userName;
	
	public QuizWindow(String userName) {
        this.userName = userName;

        setTitle("Quiz Window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		getContentPane().setBackground(Color.WHITE);
		setLayout(null);

        userLabel = new JLabel("Player: " + userName);
        userLabel.setBounds(30, 10, 300, 25);
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        userLabel.setForeground(new Color(0, 102, 204));
        add(userLabel);
		
		
        questionLabel = new JLabel();
		questionLabel.setVerticalAlignment(SwingConstants.TOP);
        questionLabel.setBounds(30, 50, 1500, 100);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(questionLabel);

        timerLabel = new JLabel("⏳ 20s");
        timerLabel.setBounds(1000, 80, 80, 25);
        timerLabel.setForeground(Color.RED);
        add(timerLabel);

        group = new ButtonGroup();
        int y = 100;
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setBounds(50, y, 400, 25);
            options[i].setBackground(Color.WHITE);
            options[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
            group.add(options[i]);
            add(options[i]);
            y += 35;
        }

        nextButton = new JButton("Next ➜");
        nextButton.setBounds(80, 280, 120, 35);
        nextButton.addActionListener(this);
        add(nextButton);

        submitButton = new JButton("Submit ✅");
        submitButton.setBounds(1000, 280, 120, 35);
        submitButton.addActionListener(this);
        add(submitButton);

        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("⏳ " + timeLeft + "s");
            if (timeLeft <= 0) {
                timer.stop();
                checkAnswer();
                nextQuestion();
            }
        });

        loadQuestion();
        timer.start();

        setVisible(true);
    }

    void loadQuestion() {
        if (currentQuestion < questions.length) {
            questionLabel.setText("Q" + (currentQuestion + 1) + ". " + questions[currentQuestion]);
			group.clearSelection();
            for (int i = 0; i < 4; i++) {
                options[i].setText(choices[currentQuestion][i]);
                options[i].setSelected(false);
            }
            timeLeft = 20;
            timerLabel.setText("⏳ 20s");
            timer.start();
        } else {
            showResult();
        }
    }

    void checkAnswer() {
    int selected = getSelectedOption();
    if (selected != 0 && selected == Integer.parseInt(correctAnswers[currentQuestion])) {
        score++;
    }
}


    void nextQuestion() {
        timer.stop();
        currentQuestion++;
        if (currentQuestion < questions.length) {
            loadQuestion();
        } else {
            showResult();
        }
    }

    private int getSelectedOption() {
        for (int i=0;i<options.length;i++) {
            if (options[i].isSelected()) {
                return i+1;
            }
        }
        return 0;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
			timer.stop();
			checkAnswer();
            nextQuestion();
        } else if (e.getSource() == submitButton) {
            timer.stop();
            checkAnswer();
            showResult();
        }
    }

    void showResult() {
        dispose(); // close quiz window
        new ResultWindow(userName, score, questions.length);
    }
}

// 🏁 Result Window
class ResultWindow extends JFrame {
    public ResultWindow(String userName, int score, int total) {
        setTitle("Quiz Result");
        setSize(400, 300);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 255, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;

        JLabel nameLabel = new JLabel("🎉 Congratulations, " + userName + "!");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(nameLabel, gbc);

        gbc.gridy++;
        JLabel scoreLabel = new JLabel("Your Score: " + score + " / " + total);
        scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        add(scoreLabel, gbc);

        gbc.gridy++;
        String message;
        if (score == total) message = "🏆 Excellent! Perfect Score!";
        else if (score >= total / 2) message = "👏 Good Job!";
        else message = "💪 Keep Practicing!";

        JLabel msgLabel = new JLabel(message);
        msgLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
        add(msgLabel, gbc);

        gbc.gridy++;
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton, gbc);

        setVisible(true);
    }
}

	
	