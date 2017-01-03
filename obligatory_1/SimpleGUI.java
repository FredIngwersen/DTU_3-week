package obligatory_1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;

public class SimpleGUI extends JFrame implements ActionListener{

	private JFrame frmNumberConverter;
	private JTextField txtBinary;
	private JTextField txtDecimal;
	private JTextField txtHexadecimal;
	public JButton b1, b2, b3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimpleGUI window = new SimpleGUI();
					window.frmNumberConverter.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SimpleGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNumberConverter = new JFrame();
		frmNumberConverter.setTitle("Scientific Converter");
		frmNumberConverter.setBounds(100, 100, 500, 170);
		frmNumberConverter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNumberConverter.getContentPane().setLayout(null);


		// Binary field & button
		txtBinary = new JTextField();
		txtBinary.setBounds(20, 10, 300, 25);
		frmNumberConverter.getContentPane().add(txtBinary);
		txtBinary.setColumns(10);
		txtBinary.setUI(new ghostText("Binary", true));

		b1 = new JButton("Convert");
		b1.addActionListener(this);
		b1.setBounds(350, 10, 125, 25);
		frmNumberConverter.getContentPane().add(b1);

		// Decimal field & button
		txtDecimal = new JTextField();
		txtDecimal.setBounds(20, 45, 300, 25);
		frmNumberConverter.getContentPane().add(txtDecimal);
		txtDecimal.setColumns(10);
		txtDecimal.setUI(new ghostText("Decimal", true));

		b2 = new JButton("Convert");
		b2.setBounds(350, 45, 125, 25);
		b2.addActionListener(this);
		frmNumberConverter.getContentPane().add(b2);

		// Hexadecimal field & button
		txtHexadecimal = new JTextField();
		txtHexadecimal.setBounds(20, 80, 300, 25);
		frmNumberConverter.getContentPane().add(txtHexadecimal);
		txtHexadecimal.setColumns(10);
		txtHexadecimal.setUI(new ghostText("Hexadecimal", true));

		b3 = new JButton("Convert");
		b3.setBounds(350, 80, 125, 25);
		b3.addActionListener(this);
		frmNumberConverter.getContentPane().add(b3);
	}

	// Convert binary to decimal
	public String binaryToDecimal(String binary) {
		int decimal = Integer.parseInt(binary,2);
		String decimalString = Integer.toString(decimal);
		return decimalString;
	}

	// Convert binary to hexadecimal
	public String binaryToHex(String binary) {
		int decimal = Integer.parseInt(binary,2);
		return  Integer.toString(decimal,16);
	}

	// Convert decimal to binary
	public String decimalToBinary(String decimal) {
		int decimalToInt = Integer.parseInt(decimal);
		return Integer.toString(decimalToInt,2);
	}

	// Convert decimal to hexadecimal
	public String decimalToHexadecimal(String decimal) {
		int decimalToInt = Integer.parseInt(decimal);
		return Integer.toHexString(decimalToInt);
	}

	// Convert hexadecimal to binary
	public String hexToBinary(String hex) {
		int hexToInt = (Integer.parseInt(hex, 16));
		return Integer.toBinaryString(hexToInt);
	}

	// Convert hexadecimal to decimal
	public String hexToDecimal(String hex) {
		return binaryToDecimal(hexToBinary(hex));
	}


	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			// Execute button 1
			if (txtBinary.getText().matches("[01]+")) {
				txtDecimal.setText(binaryToDecimal(txtBinary.getText()));
				txtHexadecimal.setText(binaryToHex(txtBinary.getText()));
			} else {
				txtBinary.setText("Error!");
			}
		} else if (e.getSource() == b2) {
			// Execute button 2
			if (txtDecimal.getText().matches("[0-9]+")) {
				txtBinary.setText(decimalToBinary(txtDecimal.getText()));
				txtHexadecimal.setText(decimalToHexadecimal(txtDecimal.getText()));
			} else {
				txtDecimal.setText("Error!");
			}
		} else if (e.getSource() == b3) {
			// execute button 3
			if (txtHexadecimal.getText().matches("-?[0-9a-fA-F]+")) {
				txtBinary.setText(hexToBinary(txtHexadecimal.getText()));
				txtDecimal.setText(hexToDecimal(txtHexadecimal.getText()));
			} else {
				txtHexadecimal.setText("Error!");
				
			}
		}
	}
}
