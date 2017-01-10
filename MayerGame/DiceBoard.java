package MayerGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class DiceBoard extends JPanel {

	private Color c = new Color(25, 111, 61);
	private Image[] images;
	private int diceRoll;
	private int timer;



	public Dimension getPreferredSize() {
		return(new Dimension(550, 450));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(c);
		g.fillRect(0, 0, getWidth(), getHeight());


	}
}



