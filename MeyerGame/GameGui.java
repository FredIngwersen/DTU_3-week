package MeyerGame;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import javax.swing.*;


public class GameGui extends JFrame {
	//private JFrame frame;
	private JTextField userText;
	private JTextField ipAddress;
	private JTextField username;
	static JTextArea chatWindow;
	public DiceBoard diceBoard;
	private JScrollPane scrollPane;
	private JButton send;
	private JButton b2;
	private JButton b3;
	private String ip;
	private static String chatMessage;
	static boolean gameStart = false;
	static boolean waiting = true;

	private static Socket chatSocket;
	static InputStream chatInput;
	static OutputStream chatOutput;
	static BufferedReader chatBir;
	static PrintWriter chatPw;

	private static Socket connectSocket;
	static InputStream input;
	static OutputStream output;
	static BufferedReader bir;
	static BufferedOutputStream bos;
	static boolean start = false;
	static int dice1;
	static int dice2;
	private static boolean repaint = false;
	
	private static ClientOutputStream chatStream;

	// Launches the threads in designated components.
	public static void main(String[] args) {
		EventQueue.invokeLater(
				new Runnable() {
					public void run() {
						try {
							GameGui window = new GameGui();
							input = connectSocket.getInputStream();
							output = connectSocket.getOutputStream();
							bir = new BufferedReader(new InputStreamReader(input));
							bos = new BufferedOutputStream(connectSocket.getOutputStream());

							chatInput = chatSocket.getInputStream();
							chatOutput = chatSocket.getOutputStream();
							chatPw = new PrintWriter(chatOutput,true);
							chatBir = new BufferedReader(new InputStreamReader(chatInput));

							
							window.initialize();
							window.setVisible(true);
							DiceThread thread = new DiceThread(window);
							thread.start();
							
							chatStream = new ClientOutputStream(window.chatWindow, chatBir);
							chatStream.start();

							while (!gameStart)
							{
								String gameStartS = bir.readLine();
								if (gameStartS.contains("SGame"))
								{
									gameStart = true;
								}
							}
							do{
								System.out.println("ReadingFromServer");
								String starter = bir.readLine();
								System.out.println("read from server");
								System.out.println(starter);
								if (starter.contains("SGame")) {
									starter = bir.readLine();
								}
								if (starter.contains("Start")) {
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
								} else if (starter.contains("norm")) {
									waiting = true;
								}
							} while (!waiting);
						} catch (Exception e) {
							e.printStackTrace();
						}


					}
				});

	}
	// Runs the GUI.
	public GameGui() {

		try {
			setupConnection();
			connectSocket = new Socket("127.0.0.1", 8080);
			System.out.println("Client connected to port 8080");
			chatSocket = new Socket("127.0.0.1", 8081);
			System.out.println("Client connected to chat /port 8081");

		} catch (IOException e){
			System.out.println("Client couldn't connect to server");
		}
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
				String msgText = userText.getText();
				chatPw.println(username.getText() + ": " + msgText);
				chatPw.flush();
				userText.setText("");
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
				String msgText = userText.getText();
				chatPw.println(username.getText() + ": " + msgText);
				chatPw.flush();
				userText.setText("");
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
				try {
					System.out.println("pressed false");
					bos.write("false\n".getBytes());
					bos.flush();
					String prevRoll = bir.readLine();
					while (!isNumeric(prevRoll)){
						prevRoll = bir.readLine();
					}
					System.out.println(prevRoll);
					waiting = false;
					b2.setEnabled(false);
					b3.setEnabled(false);
					//TODO show output of prevRoll

				}catch (IOException e1) {
					System.out.println("Error Button");
				}
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
				try {
					System.out.println("pressed true");
					bos.write("true\n".getBytes());
					bos.flush();
					String dice1String = bir.readLine();
					while (!isNumeric(dice1String)){
						dice1String = bir.readLine();
					}
					dice1 = Integer.parseInt(dice1String);
					String dice2String = bir.readLine();
					System.out.println(dice1String);
					System.out.println(dice2String);
					dice2 = Integer.parseInt(dice2String);

					//TODO display dice
					waiting = false;
					b2.setEnabled(false);
					b3.setEnabled(false);
					
				}catch(IOException e1){
					System.out.println("Error Button");
				}
			}

		});
		
	}
	

	private void whileChatting() throws IOException {
		chatMessage = chatBir.readLine();
		showMessage("\n" + chatMessage);
	}

	private void showMessage(final String message){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append(message);
					}
				});
	}

	
	public void updateChat(String str) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run(){
						chatWindow.append("\n" + str);
					}
				});
		
	}
	// Connection dialog message for start setup
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

	public boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
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
