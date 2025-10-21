import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener, KeyListener {
    private JTextField display;
    private String operator;
    private double firstNumber, secondNumber;

    public Calculator() {
        setTitle("Calculator");
        setSize(350, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttons = {
            "C", "⌫", "/", "*",
            "7", "8", "9", "-",
            "4", "5", "6", "+",
            "1", "2", "3", "=",
            "0", ".", "", ""
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            if (!text.isEmpty()) {
                btn.addActionListener(this);
                panel.add(btn);
            } else {
                panel.add(new JLabel()); 
            }
        }

        add(panel, BorderLayout.CENTER);

        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.matches("[0-9.]")) { 
            display.setText(display.getText() + cmd);
        } else if (cmd.matches("[+\\-*/]")) { 
            if (!display.getText().isEmpty()) {
                firstNumber = Double.parseDouble(display.getText());
                operator = cmd;
                display.setText("");
            }
        } else if (cmd.equals("=")) { 
            calculateResult();
        } else if (cmd.equals("C")) { 
            display.setText("");
        } else if (cmd.equals("⌫")) {
            String text = display.getText();
            if (!text.isEmpty()) display.setText(text.substring(0, text.length() - 1));
        }
    }

    private void calculateResult() {
        if (!display.getText().isEmpty() && operator != null) {
            secondNumber = Double.parseDouble(display.getText());
            double result = 0;
            switch (operator) {
                case "+": result = firstNumber + secondNumber; break;
                case "-": result = firstNumber - secondNumber; break;
                case "*": result = firstNumber * secondNumber; break;
                case "/":
                    if (secondNumber != 0) result = firstNumber / secondNumber;
                    else {
                        JOptionPane.showMessageDialog(this, "Cannot divide by zero");
                        display.setText("");
                        return;
                    }
                    break;
            }
            display.setText(String.valueOf(result));
            operator = null; 
        }
    }


    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
        if (Character.isDigit(key) || key == '.') {
            display.setText(display.getText() + key);
        } else if (key == '+' || key == '-' || key == '*' || key == '/') {
            if (!display.getText().isEmpty()) {
                firstNumber = Double.parseDouble(display.getText());
                operator = String.valueOf(key);
                display.setText("");
            }
        } else if (key == '\n') { 
            calculateResult();
        } else if (key == '\b') { 
            String text = display.getText();
            if (!text.isEmpty()) display.setText(text.substring(0, text.length() - 1));
        }
    }

    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}
