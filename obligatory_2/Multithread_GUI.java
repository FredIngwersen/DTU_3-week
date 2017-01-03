package obligatory_2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import javax.swing.JButton;

public class Multithread_GUI {

	private JFrame frmCustomComponent;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Multithread_GUI window = new Multithread_GUI();
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
	public Multithread_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCustomComponent = new JFrame();
		frmCustomComponent.setTitle("Custom Component");
		frmCustomComponent.setBounds(100, 100, 800, 800);
		frmCustomComponent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCustomComponent.getContentPane().setLayout(null);
		
		textPane = new JTextPane();
		textPane.setBounds(0, 300, 800, 300);
		frmCustomComponent.getContentPane().add(textPane);
		//textPane.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 800, 300);
		frmCustomComponent.getContentPane().add(panel);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(300, 650, 200, 50);
		frmCustomComponent.getContentPane().add(btnNewButton);
	}
}
