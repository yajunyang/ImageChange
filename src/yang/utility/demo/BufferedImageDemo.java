package yang.utility.demo;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BufferedImageDemo extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4486305356953195021L;

	@Override
	public void paint(Graphics g) {
		Image img = createImageWithText();
		g.drawImage( img, (getWidth()-200)/2, (getHeight()-200)/2, this);
	}

	private Image createImageWithText() {
		BufferedImage bf = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
		Graphics g = bf.getGraphics();

		g.drawString("www.google.com", 20, 20);
		g.drawString("www.google.com", 20, 40);
		g.drawString("www.google.com", 20, 60);
		g.drawString("www.google.com", 20, 80);
		g.drawString("www.google.com", 20, 100);

		return bf;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setMinimumSize(new Dimension(200, 200));
		frame.getContentPane().add(new BufferedImageDemo());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 200);
		frame.setVisible(true);
	}
}
