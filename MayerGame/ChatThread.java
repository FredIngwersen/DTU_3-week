package MayerGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatThread implements Runnable {
	private Socket client;

	
	//Constructor
	public ChatThread(Socket client) {
		this.client = client;
	}
	
	public void run() {
		String text;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			
		} catch (IOException e) {
			System.out.println("ERROR: chatThread");
		}
		
		while(true) {
			try {
				// Reads input and returns it to clients
				text = in.readLine();
				out.println(text);
				//textArea.append(text); - BACKDOOR FOR SERVER TO READ MESSAGES!
			} catch(IOException e) {
				System.out.println("ERROR: chatThread");
			}
		}
	}
	
	//INCLUDE THE BELOW IN SERVER SCRIPTCODE
	/*
	public void chatSocket() {
		try {
			server = new ServerSocket(8081);
		} catch (IOException e) {
			System.out.println("Chat can't connect");
		}
		while(true) {
			ChatTread c;
			try {
				c = new ChatThread(server.accept());
				Thread t = new Thread(c);
				t.start();
			} catch (IOException e) {
				System.out.println("Can't connect to chat port");
			}
		}
	}
	*/
}