package obligatory_2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class CustomComponent {

	private JFrame frmCustomComponent;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomComponent window = new CustomComponent();
					window.frmCustomComponent.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CustomComponent() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCustomComponent = new CustomComponent();
		frmCustomComponent.setTitle("Custom Component");
		frmCustomComponent.setBounds(100, 100, 800, 400);
		frmCustomComponent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCustomComponent.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(0, 200, 800, 200);
		frmCustomComponent.getContentPane().add(textField);
		textField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 800, 200);
		frmCustomComponent.getContentPane().add(panel);
	}
}
