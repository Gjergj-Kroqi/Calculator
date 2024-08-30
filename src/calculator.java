import java.util.Stack;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class calculator implements ActionListener {

	JFrame frame;
	JTextField textField;
	JButton[] numberButtons = new JButton[10];
	JButton[] functionButtons = new JButton[10];
	JButton addButton, subButton, mulButton, divButton;
	JButton decButton, equButton, delButton, clrButton;
	JButton openBracket, closedBracket;
	JPanel panel;

	Font myFont = new Font("", Font.PLAIN, 30);

	calculator() {

		ImageIcon image = new ImageIcon("C:\\Users\\Gjerg\\eclipse-workspace\\Calculator\\src\\icon.png");

		frame = new JFrame("Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420, 550);
		frame.setLayout(null);
		frame.setResizable(false);

		textField = new JTextField();
		textField.setBounds(50, 25, 300, 50);
		textField.setFont(myFont);

		textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "enterPressed");
		textField.getActionMap().put("enterPressed", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				equButton.doClick();
			}
		});

		addButton = new JButton("+");
		subButton = new JButton("-");
		mulButton = new JButton("*");
		divButton = new JButton("/");
		decButton = new JButton(".");
		equButton = new JButton("=");
		delButton = new JButton("Del");
		clrButton = new JButton("AC");
		openBracket = new JButton("(");
		closedBracket = new JButton(")");

		functionButtons[0] = addButton;
		functionButtons[1] = subButton;
		functionButtons[2] = mulButton;
		functionButtons[3] = divButton;
		functionButtons[4] = decButton;
		functionButtons[5] = equButton;
		functionButtons[6] = delButton;
		functionButtons[7] = clrButton;
		functionButtons[8] = openBracket;
		functionButtons[9] = closedBracket;

		for (int i = 0; i < 10; i++) {
			functionButtons[i].addActionListener(this);
			functionButtons[i].setFont(myFont);
			functionButtons[i].setFocusable(false);
		}

		for (int i = 0; i < 10; i++) {
			numberButtons[i] = new JButton(String.valueOf(i));
			numberButtons[i].addActionListener(this);
			numberButtons[i].setFont(myFont);
			numberButtons[i].setFocusable(false);
		}

		delButton.setBounds(183, 430, 80, 50);
		clrButton.setBounds(270, 430, 80, 50);
		openBracket.setBounds(50, 430, 59, 50);
		closedBracket.setBounds(116, 430, 59, 50);

		panel = new JPanel();
		panel.setBounds(50, 100, 300, 300);
		panel.setLayout(new GridLayout(4, 4, 10, 10));

		panel.add(numberButtons[1]);
		panel.add(numberButtons[2]);
		panel.add(numberButtons[3]);
		panel.add(addButton);
		panel.add(numberButtons[4]);
		panel.add(numberButtons[5]);
		panel.add(numberButtons[6]);
		panel.add(subButton);
		panel.add(numberButtons[7]);
		panel.add(numberButtons[8]);
		panel.add(numberButtons[9]);
		panel.add(mulButton);
		panel.add(decButton);
		panel.add(numberButtons[0]);
		panel.add(equButton);
		panel.add(divButton);

		frame.add(panel);
		frame.add(delButton);
		frame.add(clrButton);
		frame.add(openBracket);
		frame.add(closedBracket);
		frame.add(textField);
		frame.setVisible(true);

		frame.setIconImage(image.getImage());

	}

	public static void main(String[] args) {
		calculator calc = new calculator();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < 10; i++) {
			if (e.getSource() == numberButtons[i]) {
				textField.setText(textField.getText().concat(String.valueOf(i)));
			}

		}

		if (e.getSource() == decButton) {
			textField.setText(textField.getText().concat("."));
		}
		if (e.getSource() == addButton) {
			textField.setText(textField.getText().concat("+"));
		}
		if (e.getSource() == subButton) {
			textField.setText(textField.getText().concat("-"));
		}
		if (e.getSource() == mulButton) {
			textField.setText(textField.getText().concat("*"));
		}
		if (e.getSource() == divButton) {
			textField.setText(textField.getText().concat("/"));
		}
		if (e.getSource() == openBracket) {
			textField.setText(textField.getText().concat("("));
		}
		if (e.getSource() == closedBracket) {
			textField.setText(textField.getText().concat(")"));
		}
		if (e.getSource() == equButton) {

			try {

				String expression = textField.getText();
				expression = fixExpression(expression);

				ArrayList<String> infix = makeIntoInfixArrayList(expression);

				ArrayList<String> postFix = infixToPostfix(infix);

				double result = runCalculation(postFix);

				int resultInt = (int) result;

				double resultIntToDouble = Double.valueOf(resultInt);

				if (result == resultIntToDouble) {
					textField.setText(String.valueOf(resultInt));
				} else {
					textField.setText(String.valueOf(result));
				}

			} catch (Exception ex) {
				textField.setText("Error");
			}
		}

		if (e.getSource() == clrButton) {
			textField.setText("");
		}

		if (e.getSource() == delButton) {
			String currentText = textField.getText();
			if (!currentText.isEmpty()) {
				textField.setText(currentText.substring(0, currentText.length() - 1));
			}
		}

	}

	public static String fixExpression(String expression) {
		expression = removeSpaces(expression);
		expression = addOneSpace(expression);

		return expression;

	}

	private static String removeSpaces(String str) {

		if (str == null || str == "") {
			return "";
		}

		if (str.charAt(0) == ' ') {
			return removeSpaces(str.substring(1));
		} else {

			return str.substring(0, 1) + removeSpaces(str.substring(1));
		}

	}

	private static String addOneSpace(String str) {

		if (str.charAt(0) == '-') {
			return addOneSpaceMinusAtTheStart(str);
		}

		else {
			return addOneSpaceNoMinusAtTheStart(str);
		}

	}

	private static String addOneSpaceMinusAtTheStart(String str) {
		return str.charAt(0) + addOneSpaceNoMinusAtTheStart(str.substring(1));
	}

	private static String addOneSpaceNoMinusAtTheStart(String str) {

		if (str == null) {
			return "";
		}
		if (str.length() == 1) {
			return str;
		}

		char currentChar = str.charAt(0);
		char nextChar = str.charAt(1);

		// if both are operators, handles decimals too
		if ((Character.isDigit(currentChar) && Character.isDigit(nextChar))
				|| ((Character.isDigit(currentChar) && nextChar == '.')
						|| (currentChar == '.' && Character.isDigit(nextChar)))) { // if both are operands
			return currentChar + addOneSpaceNoMinusAtTheStart(str.substring(1));
		}

		// if a minus follows after an operator, but not a bracket
		if ((!Character.isDigit(currentChar) && (currentChar != '(' && currentChar != ')')) && nextChar == '-') {
			return currentChar + " " + nextChar + addOneSpaceNoMinusAtTheStart(str.substring(2));
		}

		///////
		if (Character.isDigit(currentChar) && nextChar == '(') {
			return currentChar + " * " + nextChar + " " + addOneSpaceNoMinusAtTheStart(str.substring(2));
		}

		///////
		if (currentChar == ')' && Character.isDigit(nextChar)) {
			return currentChar + " " + nextChar + " * " + addOneSpaceNoMinusAtTheStart(str.substring(1));
		}

		// if one is an operator and the other is an operand
		if ((Character.isDigit(currentChar) && !Character.isDigit(nextChar))
				|| (!Character.isDigit(currentChar) && Character.isDigit(nextChar))) {
			return currentChar + " " + addOneSpaceNoMinusAtTheStart(str.substring(1));
		}

		// if there is a '(' right next to a '-'
		if (!Character.isDigit(currentChar) && !Character.isDigit(nextChar)) {
			if (currentChar == '(' && nextChar == '-') {
				return currentChar + " " + nextChar + addOneSpaceNoMinusAtTheStart(str.substring(2));
			}
			// if there is an operator next to an operator
			return currentChar + " " + addOneSpaceNoMinusAtTheStart(str.substring(1));
		}

		return addOneSpaceNoMinusAtTheStart(str.substring(1));

	}

	public static ArrayList<String> makeIntoInfixArrayList(String expression) {

		ArrayList<String> result = new ArrayList<String>();

		String element = "";

		for (int i = 0; i < expression.length(); i++) {

			element += String.valueOf(expression.charAt(i));

			if (expression.charAt(i) == ' ') {
				result.add(element.substring(0, element.length() - 1));
				element = "";
			}

		}

		result.add(element);

		return result;

	}

	public static ArrayList<String> infixToPostfix(ArrayList<String> infix) {

		Stack<String> ops = new Stack<String>();
		ArrayList<String> postFix = new ArrayList<String>();

		for (int i = 0; i < infix.size(); i++) {

			if (infix.get(i).equals("(") || infix.get(i).equals(")") || infix.get(i).equals("-(")
					|| infix.get(i).equals("+") || infix.get(i).equals("-") || infix.get(i).equals("*")
					|| infix.get(i).equals("/") || infix.get(i).equals("^")) {

				if (infix.get(i).equals("(") || infix.get(i).equals(")") || infix.get(i).equals("-(")) {

					if (ops.isEmpty()) {

						if (infix.get(i).equals("-(")) {
							ops.add(infix.get(i));
							postFix.add("-1");
						} else {

							ops.add(infix.get(i));
						}

					} else {

						if (infix.get(i).equals("(")) {
							ops.add(infix.get(i));
						}

						if (infix.get(i).equals("-(")) {
							ops.add(infix.get(i));
							postFix.add("-1");
						}

						if (infix.get(i).equals(")")) {

							while (!(ops.peek().equals("-(")) && !(ops.peek().equals("("))) {
								postFix.add(ops.pop());
							}
							if (ops.peek().equals("-(")) {
								postFix.add("*");
								ops.pop();
							} else {
								ops.pop();
							}
						}
					}
				} else {

					if (ops.isEmpty()) {
						ops.add(infix.get(i));
					} else {
						while (precedence(infix.get(i)) <= precedence(ops.peek())) {
							postFix.add(ops.pop());
							if (ops.isEmpty()) {
								break;
							}
						}
						ops.add(infix.get(i));
					}

				}
			}

			else {

				postFix.add(infix.get(i));

			}
		}

		while (!ops.isEmpty()) {
			postFix.add(ops.pop());
		}

		return postFix;

	}

	private static int precedence(String operator) {

		if (operator.equals("^")) {
			return 3;
		}

		else if (operator.equals("*") || operator.equals("/")) {
			return 2;
		}

		else if (operator.equals("+") || operator.equals("-")) {
			return 1;
		} else {
			return -1;
		}
	}

	public static double runCalculation(ArrayList<String> postFix) {
		Stack<Double> result = new Stack<Double>();

		for (int i = 0; i < postFix.size(); i++) {
			if (postFix.get(i).equals("+")) {
				double num2 = result.pop();
				double num1 = result.pop();

				result.push(num1 + num2);
			} else if (postFix.get(i).equals("-")) {
				double num2 = result.pop();
				double num1 = result.pop();

				result.push(num1 - num2);
			} else if (postFix.get(i).equals("*")) {
				double num2 = result.pop();
				double num1 = result.pop();

				result.push(num1 * num2);
			} else if (postFix.get(i).equals("/")) {
				double num2 = result.pop();
				double num1 = result.pop();

				result.push(num1 / num2);
			} else if (postFix.get(i).equals("^")) {
				double num2 = result.pop();
				double num1 = result.pop();

				result.push(Math.pow(num1, num2));
			}

			else {
				result.push(Double.parseDouble(postFix.get(i)));
			}

		}

		return result.pop();

	}
}
