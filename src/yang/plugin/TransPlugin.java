package yang.plugin;

import java.awt.Point;
import java.awt.image.BufferedImage;

import yang.Plugin;
import yang.Yang;
import yang.utility.TransEquation;

public class TransPlugin implements Plugin{

	private static final int NUMBER = 2;
	private BufferedImage im;
	
	@Override
	public void run() {
		im = Yang.getCurrentImage();
		int r = random.nextInt(NUMBER);
		operate(r);
	}
	
	public void operate(int random) {
		switch (random) {
		case 0:
			Yang.addSonOperation("toSphere");
			toSphere();
			break;
		case 1:
			Yang.addSonOperation("toPolarCoord");
			toPolarCoord();
			break;
		default:
			break;
		}
	}

	public void toSphere() {
		BufferedImage result = Yang.getResultImage();
		int width = im.getWidth();
		int height = im.getHeight();
		int x1, y1;
		TransEquation trans = new TransEquation(new Point(im.getWidth(), im.getHeight()));
		Point p;
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				int rgb = im.getRGB(x, y);
				p = trans.toSphere(x, y);
				x1 = p.x;
				y1 = p.y;
				result.setRGB(x1, y1, rgb);
			}
		Yang.setResultImage(result);
	}
	
	public void toPolarCoord() {
		BufferedImage result = Yang.getResultImage();
		int width = im.getWidth();
		int height = im.getHeight();
		int x1, y1;
		TransEquation trans = new TransEquation(new Point(im.getWidth(), im.getHeight()));
		Point p;
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				int rgb = im.getRGB(x, y);
				p = trans.toPolarCoord(x, y);
				x1 = p.x;
				y1 = p.y;
				result.setRGB(x1, y1, rgb);
			}
		Yang.setResultImage(result);
	}
}
