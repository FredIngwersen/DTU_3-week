package obligatory_2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;

public class CustomComponent extends JPanel {
	public CustomComponent() {
		
	}
	
	public Dimension getPreferredSize() {
		return(new Dimension(800, 300));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		//g.drawLine(0, 0, getWidth(), getHeight());
	}
	
	public void DrawSomething() {
		if (isShowing()) {
			Graphics g = getGraphics();
			g.drawRect(10, 10, 40, 30);
		}
	}

	
	
	
	
	
	
	
	/*
	JTextArea textArea = new JTextArea();
	
	textArea.setLineWrap(true);
	
	JScrollPane scrollpane = new JScrollPane(textArea);
	scrollpane.setHorizontalScrollbarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	getContentPane().add(scrollpane);
	
	textArea.append("Some arbitrary text\n");
	textArea.setCaretPosition(textArea.getDocument().getLength());
	*/

}
