import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame {
    private JTextField display;
    private double result = 0;
    private String lastCommand = "=";
    private boolean start = true;

    public Calculator() {
        // Set up the frame
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create display field
        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.PLAIN, 20));
        add(display, BorderLayout.NORTH);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        // Add buttons
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            buttonPanel.add(button);
            if (Character.isDigit(label.charAt(0)) || label.equals(".")) {
                button.addActionListener(new NumberListener());
            } else {
                button.addActionListener(new OperatorListener());
            }
        }

        // Add clear button
        JButton clearButton = new JButton("C");
        clearButton.addActionListener(e -> {
            start = true;
            result = 0;
            lastCommand = "=";
            display.setText("0");
        });
        
        // Add panels to frame
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(clearButton, BorderLayout.SOUTH);
        add(mainPanel);

        // Set frame properties
        setSize(300, 400);
        setLocationRelativeTo(null);
    }

    private class NumberListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String digit = event.getActionCommand();
            if (start) {
                display.setText(digit);
                start = false;
            } else {
                display.setText(display.getText() + digit);
            }
        }
    }

    private class OperatorListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (!start) {
                calculate();
                lastCommand = event.getActionCommand();
                start = true;
            }
        }
    }

    public void calculate() {
        double x = Double.parseDouble(display.getText());
        switch (lastCommand) {
            case "+": result += x; break;
            case "-": result -= x; break;
            case "*": result *= x; break;
            case "/": 
                if (x != 0) {
                    result /= x;
                } else {
                    display.setText("Error");
                    start = true;
                    return;
                }
                break;
            case "=": result = x; break;
        }
        display.setText(String.valueOf(result));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
            calc.setVisible(true);
        });
    }
}