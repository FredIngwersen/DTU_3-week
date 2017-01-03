package obligatory_2;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;

import javax.swing.*;

public class Multithread_GUI extends JFrame implements ActionListener{

	public JFrame frame;
	public JTextArea textArea;
	public JButton b1;
	private CustomComponent customComponent = new CustomComponent();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Multithread_GUI window = new Multithread_GUI();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setTitle("Custom Component");
		frame.setBounds(100, 100, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textArea = new JTextArea();
		textArea.setBounds(0, 300, 800, 300);
		frame.getContentPane().add(textArea);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);


		//Custom component
		customComponent = new CustomComponent();
		customComponent.setBounds(0, 0, 800, 300);
		frame.getContentPane().add(customComponent);

		b1 = new JButton("Refresh");
		b1.setBounds(300, 650, 200, 50);
		b1.addActionListener(this);
		frame.getContentPane().add(b1);

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
			// Refresh all
			textArea.setText("");
			customComponent.removeAll(); customComponent.updateUI();
		} 
	}
}
