package obligatory_2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.*;

public class CustomComponent extends JPanel {
	private Color[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY,
			Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
			Color.PINK, Color.RED, Color.YELLOW};
	Random rand = new Random();
	Color c = new Color(245, 245, 245);

	public Dimension getPreferredSize() {
		return(new Dimension(800, 300));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(c);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public void DrawSomething() {
		if (isShowing()) {
			Graphics g = getGraphics();
			g.setColor(colors[rand.nextInt(colors.length)]);
			g.fillRect(rand.nextInt(800), rand.nextInt(300), rand.nextInt(100) + 20, rand.nextInt(100) + 20);
		}
	}
}