import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.Timer;

public class MathGame extends JFrame {
    private JTextField answerField;
    private JLabel questionLabel, feedbackLabel, scoreLabel, timerLabel;
    private int currentAnswer;
    private int score;
    private int timeRemaining;
    private Timer countdownTimer;

    public MathGame() {
        setSize(500, 600);
        setLocationRelativeTo(null);
        setTitle("Math Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        questionLabel = new JLabel("");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 40));
        questionLabel.setPreferredSize(new Dimension(500, 100));

        answerField = new JTextField();
        answerField.setFont(new Font("Arial", Font.PLAIN, 40));
        answerField.setPreferredSize(new Dimension(500, 100));

        feedbackLabel = new JLabel("");
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        feedbackLabel.setPreferredSize(new Dimension(500, 100));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setPreferredSize(new Dimension(500, 100));

        timerLabel = new JLabel("Time: 120");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        timerLabel.setPreferredSize(new Dimension(500, 100));

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        add(questionLabel);
        add(answerField);
        add(submitButton);
        add(feedbackLabel);
        add(scoreLabel);
        add(timerLabel);

        generateQuestion();
        startTimer();
        setVisible(true);
        answerField.requestFocusInWindow();
    }

    private void generateQuestion() {
        Random rand = new Random();
        int num1 = rand.nextInt(100);
        int num2 = rand.nextInt(100);
        currentAnswer = num1 + num2;
        questionLabel.setText("What is " + num1 + " + " + num2 + "?");
        answerField.setText("");
        feedbackLabel.setText("");
    }

    private void checkAnswer() {
        try {
            int answer = Integer.parseInt(answerField.getText());
            if (answer == currentAnswer) {
                feedbackLabel.setText("Correct!");
                score++;
                scoreLabel.setText("Score: " + score);
                Timer timer = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        generateQuestion();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                feedbackLabel.setText("Incorrect! Try again.");
            }
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Invalid input. Please enter a number.");
        }
        answerField.requestFocusInWindow();
    }

    private void startTimer() {
        timeRemaining = 120;
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("Time: " + timeRemaining);
                if (timeRemaining <= 0) {
                    countdownTimer.stop();
                    feedbackLabel.setText("Time's up! Final Score: " + score);
                    answerField.setEnabled(false);
                }
            }
        });
        countdownTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MathGame());
    }
}