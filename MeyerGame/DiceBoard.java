package MeyerGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DiceBoard extends JPanel {

	public Color c = new Color(25, 111, 61);
	private Image[] images;

	// Loads pictures from pathway
	public DiceBoard() {
		try {
			images = new Image[6];
			images[0] = ImageIO.read(new File("MeyerGame/img/dice1.png"));
			images[1] = ImageIO.read(new File("MeyerGame/img/dice2.png"));
			images[2] = ImageIO.read(new File("MeyerGame/img/dice3.png"));
			images[3] = ImageIO.read(new File("MeyerGame/img/dice4.png"));
			images[4] = ImageIO.read(new File("MeyerGame/img/dice5.png"));
			images[5] = ImageIO.read(new File("MeyerGame/img/dice6.png"));
		} catch (IOException e) {
				try {
					images = new Image[6];
					images[0] = ImageIO.read(new File("src/MeyerGame/img/dice1.png"));
					images[1] = ImageIO.read(new File("src/MeyerGame/img/dice2.png"));
					images[2] = ImageIO.read(new File("src/MeyerGame/img/dice3.png"));
					images[3] = ImageIO.read(new File("src/MeyerGame/img/dice4.png"));
					images[4] = ImageIO.read(new File("src/MeyerGame/img/dice5.png"));
					images[5] = ImageIO.read(new File("src/MeyerGame/img/dice6.png"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}  
		}



	/*
	public void images() {
		images = new Image[6];
		images[0] = new ImageIcon("dice1.png").getImage();
		images[1] = new ImageIcon("dice2.png").getImage();
		images[2] = new ImageIcon("dice3.png").getImage();
		images[3] = new ImageIcon("dice4.png").getImage();
		images[4] = new ImageIcon("dice5.png").getImage();
		images[5] = new ImageIcon("dice6.png").getImage();
	}
	 */


	public Dimension getPreferredSize() {
		return(new Dimension(550, 450));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(c);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	//drawDice(GameGuiNew.start);
	public void DrawDices() {
		if (isShowing()) {
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		    int screenW = (int)(Math.round(screenSize.width*0.50));
		    int screenH = (int)(Math.round(screenSize.height*0.60));
		    int boardW = (int)(Math.round(screenW*0.6));
		    int boardH = (int)(Math.round(screenH*0.81));
		    
			Graphics g = getGraphics();
			g.drawImage(images[GameGuiTesting.getDice1()],
					(int)(Math.round(boardW*0.22)), (int)(Math.round(boardH*0.4)), null);
			
			g.drawImage(images[GameGuiTesting.getDice2()],
					(int)(Math.round(boardW*0.52)), (int)(Math.round(boardH*0.4)), null);
		}
	}
	/*
	public void drawDice(boolean start) {
		if (start == true) {
			Graphics g = getGraphics();
			g.drawImage(images[GameGuiNew.getDice1()], 100, 200, null);
			g.drawImage(images[GameGuiNew.getDice2()], 250, 200, null);
		}
	}
	 */

}


