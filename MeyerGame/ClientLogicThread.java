
package MeyerGame;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by William Ben Embarek on 13/01/2017.
 */
public class ClientLogicThread extends Thread {
	private GameGuiTesting p;
	private static String chatMessage;
	static boolean gameStart = false;
	static boolean waiting = false;

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
	static boolean visible = true;
	private static ClientOutputStream chatStream;
	int counter = 0;
	int prevRoll;

	public void run() {
		try {
			input = connectSocket.getInputStream();
			output = connectSocket.getOutputStream();
			bir = new BufferedReader(new InputStreamReader(input));
			bos = new BufferedOutputStream(connectSocket.getOutputStream());

			chatInput = chatSocket.getInputStream();
			chatOutput = chatSocket.getOutputStream();
			chatPw = new PrintWriter(chatOutput, true);
			chatBir = new BufferedReader(new InputStreamReader(chatInput));

			chatStream = new ClientOutputStream(p.chatWindow, chatBir);
			chatStream.start();

			while (!gameStart) {
				String gameStartS = bir.readLine();
				if (gameStartS.contains("SGame")) {
					gameStart = true;
				}
			}
			while (1 == 1) {
				while (!waiting) {
					visible = true;
					p.updateButtons(visible);
					System.out.println("ReadingFromServer");
					String starter = bir.readLine();
					System.out.println("read from server");
					System.out.println(starter);
					if (starter.contains("SGame")) {
						starter = bir.readLine();
					}
					if (starter.contains("norm") || start) {
						waiting = true;
						visible = true;
						p.updateButtons(visible);

					} else if (starter.contains("Start")) {
						visible = false;
						p.updateButtons(visible);
						start = true;
						System.out.println("Client here");
						bos.write("RR\n".getBytes());
						bos.flush();
						dice1 = Integer.parseInt(bir.readLine());
						p.setDice1(dice1);
						dice2 = Integer.parseInt(bir.readLine());
						p.setDice2(dice2);
						waiting = false;
					} else if (starter.contains("endGame")) {
						prevRoll = Integer.parseInt(bir.readLine());
						System.out.println("Game ended");
					}
				}
				try {
					Thread.sleep(7000);
				} catch (InterruptedException e) {
					counter = counter + 1;
				}
				if (counter == 8
						) {
					counter = 0;
					waiting = false;
				}
			}
		} catch (IOException e) {
			System.out.println("we had a little error server connection wise");
		}

	}

	public ClientLogicThread(GameGuiTesting p) {
		this.p = p;
		try {
			connectSocket = new Socket(p.ipAddress.getText(), 8080);
			System.out.println("Client connected to port 8080");
			chatSocket = new Socket(p.ipAddress.getText(), 8081);
			System.out.println("Client connected to chat /port 8081");

		} catch (IOException e) {
			System.out.println("Client couldn't connect to server");
		}

	}


	public void pressFalse() {
		try {
			System.out.println("pressed false");
			bos.write("false\n".getBytes());
			bos.flush();
			String prevRoll = bir.readLine();
			System.out.println(prevRoll);
			setWaiting(false);
			visible = false;
			p.updateButtons(visible);
			//TODO show output of prevRoll

		} catch (IOException e1) {
			System.out.println("Error Button");
		}

	}

	public void pressTrue() {
		try {
			System.out.println("pressed true");
			bos.write("true\n".getBytes());
			bos.flush();
			String dice1String = bir.readLine();
			dice1 = Integer.parseInt(dice1String);
			String dice2String = bir.readLine();
			System.out.println(dice1String);
			System.out.println(dice2String);
			dice2 = Integer.parseInt(dice2String);
			p.setDice1(dice1);
			p.setDice2(dice2);

			//TODO display dice
			setWaiting(false);
			visible = false;
			p.updateButtons(visible);

		} catch (IOException e1) {
			System.out.println("Error Button");
		}
	}

	public boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}

	public void setWaiting(boolean x) {
		waiting = x;
	}

	public boolean getWaiting() {
		return waiting;
	}

	public void userText() {
		String msgText = p.getUserText();
		chatPw.println(p.getUsernameText() + p.getUserText());
		chatPw.flush();
		p.setUserText("");
	}

	private void whileChatting() throws IOException {
		chatMessage = chatBir.readLine();
		showMessage("\n" + chatMessage);
	}

	private void showMessage(final String message){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						p.chatWindow.append(message);
					}
				});
	}


	public void updateChat(String str) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run(){
						p.chatWindow.append("\n" + str);
					}
				});

	}

}
