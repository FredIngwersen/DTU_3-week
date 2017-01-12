package MeyerGame;

import java.io.BufferedReader;
import java.io.IOException;
 
import javax.swing.JTextArea;
 

public class ClientOutputStream extends Thread{
    private JTextArea textArea;
    private BufferedReader stream;
     
    public ClientOutputStream(JTextArea textArea, BufferedReader stream) {
        this.textArea = textArea;
        this.stream = stream;
    }
     
    public void run(){
    	while(1 == 1){
    		try {
				String text = stream.readLine() + "\n";
				textArea.append(text);
				textArea.setCaretPosition(textArea.getDocument().getLength());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }
}