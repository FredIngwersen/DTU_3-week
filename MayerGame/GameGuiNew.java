package MayerGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import javax.swing.*;


public class GameGuiNew extends JFrame implements ActionListener {
	//private JFrame frame;
	private JTextField userText;
	private JTextArea chatWindow;
	public DiceBoard diceBoard;
	private JScrollPane scrollPane;
	private JButton send;
	private JButton b2;
	private JButton b3;
	private String ip;

	private static Socket connectSocket;
	static InputStream input;
	static OutputStream output;
	static BufferedReader bir;
	static BufferedOutputStream bos;
	static boolean start = false;
	static int dice1;
	static int dice2;
	private static boolean repaint = false;

	// Launches the threads in designated components.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGuiNew window = new GameGuiNew();
					//window.initialize();
					window.setVisible(true);
					//window.repaint();
					DiceThread thread = new DiceThread(window);
					thread.start();
					while (true){
						input = connectSocket.getInputStream();
						output = connectSocket.getOutputStream();
						bir = new BufferedReader(new InputStreamReader(input));
						bos = new BufferedOutputStream(connectSocket.getOutputStream());
						System.out.println("ReadingFromServer");
						String starter = bir.readLine();
						System.out.println("read from server");
						System.out.println(starter);
						if(starter.contains("Start")){
							start = true;
							System.out.println("Client here");
							//TODO Change GUI remove true and false
							bos.write("RR\n".getBytes());
							bos.flush();
							dice1 = Integer.parseInt(bir.readLine());
							dice2 = Integer.parseInt(bir.readLine());
							System.out.println(dice1);
							System.out.println(dice2);
							//TODO Show dice
						}
						else {
							//TODO
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b2)
		{
			try {
				bos.write("false".getBytes());
				int prevroll = Integer.parseInt(bir.readLine());
				//TODO show output of prevRoll

			}catch (IOException e1) {
				System.out.println("Error Button");
			}
		}
		else if (e.getSource() == b3)
		{
			try {
				bos.write("true".getBytes());
				dice1 = Integer.parseInt(bir.readLine());
				dice2 = Integer.parseInt(bir.readLine());
				//TODO display dice

			}catch (IOException e1) {
				System.out.println("Error Button");
			}
		}
	}

	// Runs the GUI.
	public GameGuiNew() {
		initialize();
		try {
			connectSocket = new Socket("127.0.0.1", 8080);
			System.out.println("Client connected to port 8080");
		} catch (IOException e){
			System.out.println("Client couldn't connect to server");
		}
	}

	// Initialize the frame and its contents.
	private void initialize() {
		
		// Main window.
		// frame = new JFrame();
		setTitle("A Game of Meyer!");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout();
		setResizable(false);
		
		// ip = JOptionPane.showInputDialog(frame, "Input IP address");

		// Chat box containing all sent messages.
		chatWindow = new JTextArea();
		// chatWindow.setBounds(550, 0, 345, 547);
		// getContentPane().add(chatWindow);
		chatWindow.setLineWrap(true);
		chatWindow.setWrapStyleWord(true);
		scrollPane = new JScrollPane(chatWindow);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(550, 0, 345, 547);
		getContentPane().add(scrollPane);


		// Text input field.
		userText = new JTextField();
		userText.setBounds(550, 545, 280, 25);
		getContentPane().add(userText);

		// Send button - sends text from text field to the chat box.
		send = new JButton("Send");
		send.setBounds(830, 545, 65, 25);
		getContentPane().add(send);

		// Dice board.
		diceBoard = new DiceBoard();
		diceBoard.setBounds(0, 0, 550, 469);
		getContentPane().add(diceBoard);

		/*
		// Dices
		if (start = true) {
			diceTest = new DicesTest();
			diceTest.setBounds(150, 150, 128, 128);
			frame.getContentPane().add(diceTest);
		}
		 */

		// b2: The "false" button.
		b2 = new JButton("FALSE");
		b2.setBounds(0, 469, 275, 100);
		getContentPane().add(b2);

		// b3: The "true" button
		b3 = new JButton("TRUE");
		b3.setBounds(275, 469, 275, 100);
		getContentPane().add(b3);
	}

	public String getIp() {
		return ip;
	}

	public static boolean getRepaint() {
		return repaint;
	}

	public static int getDice1() {
		return dice1;
	}

	public static int getDice2() {
		return dice2;
	}
}