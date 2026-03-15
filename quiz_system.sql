CREATE DATABASE quiz_system;
USE quiz_system;

CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_text VARCHAR(255) NOT NULL,
    option1 VARCHAR(255) NOT NULL,
    option2 VARCHAR(255) NOT NULL,
    option3 VARCHAR(255) NOT NULL,
    option4 VARCHAR(255) NOT NULL,
    correct_option INT NOT NULL
);

INSERT INTO questions (question_text, option1, option2, option3, option4, correct_option) VALUES
('What is the capital of France?', 'Berlin', 'Madrid', 'Paris', 'Rome', 3),
('Who wrote the national anthem of India?', 'Rabindranath Tagore', 'Bankim Chandra Chatterjee', 'Sarojini Naidu', 'Mahatma Gandhi', 1),
('Which planet is known as the Red Planet?', 'Earth', 'Mars', 'Jupiter', 'Saturn', 2),
('Which is the largest ocean on Earth?', 'Atlantic Ocean', 'Indian Ocean', 'Pacific Ocean', 'Arctic Ocean', 3),
('Who invented the telephone?', 'Alexander Graham Bell', 'Thomas Edison', 'Nikola Tesla', 'Isaac Newton', 1),
('What is the national animal of India?', 'Elephant', 'Tiger', 'Lion', 'Peacock', 2),
('Which gas is essential for human respiration?', 'Oxygen', 'Carbon Dioxide', 'Nitrogen', 'Hydrogen', 1),
('Who was the first President of India?', 'Dr. Rajendra Prasad', 'Dr. S. Radhakrishnan', 'Jawaharlal Nehru', 'Lal Bahadur Shastri', 1),
('Which is the smallest continent?', 'Africa', 'Europe', 'Australia', 'South America', 3),
('Who discovered gravity?', 'Albert Einstein', 'Isaac Newton', 'Galileo Galilei', 'Charles Darwin', 2),
('Which country gifted the Statue of Liberty to the USA?', 'France', 'Germany', 'Italy', 'Spain', 1),
('Which festival is known as the festival of lights?', 'Holi', 'Eid', 'Diwali', 'Christmas', 3),
('Which is the largest desert in the world?', 'Sahara Desert', 'Gobi Desert', 'Thar Desert', 'Kalahari Desert', 1),
('Which is the longest river in the world?', 'Amazon', 'Nile', 'Yangtze', 'Mississippi', 2),
('Who was the first man to step on the Moon?', 'Buzz Aldrin', 'Yuri Gagarin', 'Neil Armstrong', 'John Glenn', 3),
('Which planet is known as the Blue Planet?', 'Earth', 'Neptune', 'Uranus', 'Venus', 1),
('Which Indian city is known as the Silicon Valley of India?', 'Delhi', 'Hyderabad', 'Bangalore', 'Pune', 3),
('In which year did India gain independence?', '1945', '1947', '1950', '1952', 2),
('Which organ purifies blood in the human body?', 'Heart', 'Kidney', 'Liver', 'Lungs', 2),
('What is the chemical formula of water?', 'CO2', 'H2O', 'O2', 'NaCl', 2),
('Which is the hottest planet in our solar system?', 'Venus', 'Mercury', 'Mars', 'Jupiter', 1),
('What is the currency of Japan?', 'Yuan', 'Won', 'Yen', 'Dollar', 3),
('Who painted the Mona Lisa?', 'Pablo Picasso', 'Leonardo da Vinci', 'Vincent van Gogh', 'Michelangelo', 2),
('Which Indian festival celebrates colors?', 'Holi', 'Diwali', 'Baisakhi', 'Onam', 1),
('Which metal is liquid at room temperature?', 'Mercury', 'Iron', 'Silver', 'Lead', 1);

SELECT * FROM questions;


