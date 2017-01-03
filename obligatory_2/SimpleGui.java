package obligatory_2;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SimpleGui extends JFrame implements ActionListener {
	private JFrame frame;
	public JTextArea textArea;
	public CustomComponent customComponent;
    private JScrollPane scrollpane;
    public JButton b1;
    public boolean restart;

    //Launches the threads in designated components.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimpleGui window = new SimpleGui();
					window.frame.setVisible(true);
					PrimeThread thread1 = new PrimeThread(window);
                    CustomThread thread2 = new CustomThread(window);
                    thread1.start();
                    thread2.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }

	//Runs the GUI.
	public SimpleGui() {
		initialize();
	}

	//Initialize the frame and its contents.
	private void initialize() {
		//Main window.
		frame = new JFrame();
		frame.setTitle("Forms and Numbers");
		frame.setBounds(100, 100, 800, 675);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		//Prime text area.
		textArea = new JTextArea();
		textArea.setBounds(0, 300, 800, 300);
		frame.getContentPane().add(textArea);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
        scrollpane = new JScrollPane(textArea);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollpane.setBounds(0, 300, 800, 300);
		frame.getContentPane().add(scrollpane);
	
		//Custom component.
		customComponent = new CustomComponent();
		customComponent.setBounds(0, 0, 800, 300);
		frame.getContentPane().add(customComponent);
		
		//Refresh button.
		b1 = new JButton("Reset");
		b1.addActionListener(this);
		b1.setBounds(0, 600, 800, 25);
		frame.getContentPane().add(b1);
	}
	
	//Controls reset button. Clear components and resets the counter for the prime number.
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			textArea.setText("2 ");
			PrimeThread.restart();
			customComponent.repaint();
		}
	}
}

