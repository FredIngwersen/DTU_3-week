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
		Random rand = new Random();
		try {
			images = new Image[6];
			images[0] = ImageIO.read(new File("MayerGame/img/dice1.png"));
			images[1] = ImageIO.read(new File("MayerGame/img/dice2.png"));
			images[2] = ImageIO.read(new File("MayerGame/img/dice3.png"));
			images[3] = ImageIO.read(new File("MayerGame/img/dice4.png"));
			images[4] = ImageIO.read(new File("MayerGame/img/dice5.png"));
			images[5] = ImageIO.read(new File("MayerGame/img/dice6.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.paintComponent(g);
		g.setColor(c);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(images[5], 100, 161, null);
		g.drawImage(images[5], 328, 161, null);
	}
	//g.drawImage(images[direction], secondPosX, secondPosY, null);

	// TODO Auto-generated method stub

}



