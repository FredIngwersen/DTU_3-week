package MeyerGame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameGUI extends JFrame {
	//private JFrame frame;
	private JTextField userText;
	public JTextField ipAddress;
	private JTextField username;
	static JTextArea chatWindow;
	public DiceBoard diceBoard;
	private JScrollPane scrollPane;
	private JButton send;
	private JButton b2;
	private JButton b3;
	private static String chatMessage;

	static int dice1;
	static int dice2;
	private static boolean repaint = false;
	static ClientLogicThread thread2;
	// Launches the threads in designated components.
	public static void main(String[] args) {
		EventQueue.invokeLater(
				new Runnable() {
					public void run() {
						try {
							GameGUI window = new GameGUI();
							window.initialize();
							window.setVisible(true);
							DiceThread thread = new DiceThread(window);
							thread.start();
							thread2 = new ClientLogicThread(window);
							thread2.start();


						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

	}


	private void showMessage(final String message){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append(message);
					}
				});
	}

	// Runs the GUI.
	public GameGUI() {
		setupConnection();
	}

	// Initialize the frame and its contents.
	private void initialize() {

		// Main window.
		// frame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenW = (int)(Math.round(screenSize.width*0.50));
		int screenH = (int)(Math.round(screenSize.height*0.60));

		setTitle("A Game of Meyer!");
		setSize(screenW, screenH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setResizable(false);

		// ip = JOptionPane.showInputDialog(frame, "Input IP address");

		// Chat box containing all sent messages.
		chatWindow = new JTextArea();
		chatWindow.setLineWrap(true);
		chatWindow.setWrapStyleWord(true);
		scrollPane = new JScrollPane(chatWindow);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds((int)(Math.round(screenW*0.6)), 0,
				(int)(Math.round(screenW*0.4)), (int)(Math.round(screenH*0.9)));
		getContentPane().add(scrollPane);

		userText = new JTextField();
		userText.setBounds((int)(Math.round(screenW*0.6)), (int)(Math.round(screenH*0.9)),
				(int)(Math.round(screenW*0.3)), (int)(Math.round(screenH*0.06)));
		getContentPane().add(userText);
		userText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thread2.userText();
			}
		});

		// Send button - sends text from text field to the chat box.
		send = new JButton("Send");
		send.setBounds((int)(Math.round(screenW*0.9)), (int)(Math.round(screenH*0.9)),
				(int)(Math.round(screenW*0.1)), (int)(Math.round(screenH*0.06)));
		getContentPane().add(send);
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thread2.userText();
			}
		});

		// Dice board.
		diceBoard = new DiceBoard();
		diceBoard.setBounds(0, 0,
				(int)(Math.round(screenW*0.6)), (int)(Math.round(screenH*0.81)));
		getContentPane().add(diceBoard);


		// b2: The "false" button.
		b2 = new JButton("FALSE");
		b2.setBounds(0, (int)(Math.round(screenH*0.81)),
				(int)(Math.round(screenW*0.3)), (int)(Math.round(screenH*0.15)));
		getContentPane().add(b2);
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thread2.pressFalse();
			}
		});

		// b3: The "true" button
		b3 = new JButton("TRUE");
		b3.setBounds((int)(Math.round(screenW*0.3)), (int)(Math.round(screenH*0.81)),
				(int)(Math.round(screenW*0.3)), (int)(Math.round(screenH*0.15)));
		getContentPane().add(b3);
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thread2.pressTrue();
			}

		});
	}

	public static boolean getRepaint() {
		return repaint;
	}

	public static void setDice1(int dice) {dice1 = dice;}
	public static void setDice2(int dice) {dice2 = dice;}

	public static int getDice1() {
		return dice1;
	}
	public static int getDice2() {
		return dice2;
	}

	public String getUserText() { return userText.getText();}

	public void setUserText(String text) { userText.setText(text); }

	public String getUsernameText() {return username.getText();}

	public void updateButtons(boolean state) { b2.setEnabled(state); b3.setEnabled(state);}

	private void setupConnection() {

		JPanel p = new JPanel(new BorderLayout(5,5));

		// Labels for input text boxes
		JPanel labels = new JPanel(new GridLayout(0,1,2,2));
		labels.add(new JLabel("IP-Address "));
		labels.add(new JLabel("Username "));
		p.add(labels, BorderLayout.WEST);

		// Input text boxes
		JPanel controls = new JPanel(new GridLayout(0,1,2,2));
		ipAddress = new JTextField();
		controls.add(ipAddress);
		username = new JTextField();
		controls.add(username);
		p.add(controls, BorderLayout.CENTER);

		JOptionPane.showMessageDialog(
				null, p, "Connecting...", JOptionPane.PLAIN_MESSAGE);
	}

	public void endGamePopup(int prevRoll) {
		setDice1(0);
		setDice2(0);
		JOptionPane.showMessageDialog(null, "The revealed roll was" + prevRoll);

	}
}
