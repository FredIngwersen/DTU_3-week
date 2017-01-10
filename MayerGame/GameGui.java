package MayerGame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import javax.swing.*;

public class GameGui extends JFrame implements ActionListener {
	private JFrame frame;
	private JTextField userText;
	private JTextArea chatWindow;
	private DiceBoard diceBoard;
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
	
    // Launches the threads in designated components.
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGui window = new GameGui();
					window.frame.setVisible(true);
/*					input = connectSocket.getInputStream();
					output = connectSocket.getOutputStream();
					bir = new BufferedReader(new InputStreamReader(input));
					bos = new BufferedOutputStream(connectSocket.getOutputStream());
					System.out.println("ReadingFromServer");
					String starter = bir.readLine();
					System.out.println("read from server");
					System.out.println(starter);
					if(starter.contains("Start")){
					start = true;
						//TODO Change GUI remove true and false
						bos.write("Request Roll".getBytes());
						bos.flush();
						dice1 = Integer.parseInt(bir.readLine());
						dice2 = Integer.parseInt(bir.readLine());
						//TODO Show dice
				}*/
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
	public GameGui() {
		initialize();
		try {
			connectSocket = new Socket(ip, 8080);
			System.out.println("Client connected to port 8080");
		} catch (IOException e){
			System.out.println("Client couldn't connect to server");
		}
	}

	// Initialize the frame and its contents.
	private void initialize() {
		// Main window.
		frame = new JFrame();
		frame.setTitle("A Game of Meyer!");
		frame.setBounds(100, 100 , 900, 598);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		ip = JOptionPane.showInputDialog(frame, "Input IP address");
		
		// Chat box containing all sent messages.
		chatWindow = new JTextArea();
		chatWindow.setBounds(550, 0, 345, 547);
		frame.getContentPane().add(chatWindow);
		chatWindow.setLineWrap(true);
		chatWindow.setWrapStyleWord(true);
		scrollPane = new JScrollPane(chatWindow);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(550, 0, 345, 547);
		frame.getContentPane().add(scrollPane);

	
		// Text input field.
		userText = new JTextField();
		userText.setBounds(550, 545, 280, 25);
		frame.getContentPane().add(userText);

		// Send button - sends text from text field to the chat box.
		send = new JButton("Send");
		send.setBounds(830, 545, 65, 25);
		frame.getContentPane().add(send);
		
		// Dice board.
		diceBoard = new DiceBoard();
		diceBoard.setBounds(0, 0, 550, 469);
		frame.getContentPane().add(diceBoard);
		
		// b2: The "false" button.
		b2 = new JButton("FALSE");
		b2.setBounds(0, 469, 275, 100);
		frame.getContentPane().add(b2);
		
		// b3: The "true" button
		b3 = new JButton("TRUE");
		b3.setBounds(275, 469, 275, 100);
		frame.getContentPane().add(b3);
	}

	public String getIp() {
		return ip;
	}
}
