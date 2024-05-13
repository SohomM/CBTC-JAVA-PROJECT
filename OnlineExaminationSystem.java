import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Define OnlineExaminationSystem class first
public class OnlineExaminationSystem {
    public static void main(String[] args) {
        // Sample questions
        MCQQuestion[] questions = new MCQQuestion[3];
        questions[0] = new MCQQuestion("What is the capital of France?",
                new String[]{"Paris", "Berlin", "London", "Rome"}, 0);
        questions[1] = new MCQQuestion("Which planet is known as the Red Planet?",
                new String[]{"Mars", "Jupiter", "Venus", "Mercury"}, 0);
        questions[2] = new MCQQuestion("Who painted the Mona Lisa?",
                new String[]{"Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Michelangelo"}, 0);

        // Start exam
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Examination exam = new Examination(questions, 3); // 3 minutes exam
                exam.setVisible(true);
            }
        });
    }
}

// Then define other classes
class User {
    private String username;
    private String password;
    // Other profile information

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        // Initialize other profile information
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Methods to update profile and password
}

class MCQQuestion {
    private String question;
    private String[] options;
    private int correctOption;

    public MCQQuestion(String question, String[] options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}

class Examination extends JFrame {
    private MCQQuestion[] questions;
    private int totalTimeInMinutes;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup buttonGroup;
    private JButton nextButton;

    public Examination(MCQQuestion[] questions, int totalTimeInMinutes) {
        this.questions = questions;
        this.totalTimeInMinutes = totalTimeInMinutes;

        setTitle("Online Examination System");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel questionPanel = new JPanel(new GridLayout(5, 1));
        questionLabel = new JLabel();
        questionPanel.add(questionLabel);

        optionButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            buttonGroup.add(optionButtons[i]);
            questionPanel.add(optionButtons[i]);
        }

        add(questionPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processAnswer();
                showNextQuestion();
            }
        });
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);

        showNextQuestion();
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < questions.length) {
            MCQQuestion currentQuestion = questions[currentQuestionIndex];
            questionLabel.setText(currentQuestion.getQuestion());
            String[] options = currentQuestion.getOptions();
            for (int i = 0; i < options.length; i++) {
                optionButtons[i].setText(options[i]);
                optionButtons[i].setSelected(false);
            }
            currentQuestionIndex++;
        } else {
            // Display score
            JOptionPane.showMessageDialog(this, "Exam Completed! Your Score: " + score);
            dispose();
        }
    }

    private void processAnswer() {
        MCQQuestion currentQuestion = questions[currentQuestionIndex - 1];
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                if (i == currentQuestion.getCorrectOption()) {
                    score++;
                }
                break;
            }
        }
    }
}
