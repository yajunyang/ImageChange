package yang.plugin;

import java.awt.Color;
import java.awt.image.BufferedImage;

import yang.Plugin;
import yang.Yang;

public class GrayScalePlugin implements Plugin {

	private BufferedImage im;

	@Override
	public void run() {
		im = Yang.getCurrentImage();
		BufferedImage result = grayScale();
		Yang.setResultImage(result);
	}

	public BufferedImage grayScale() {
		BufferedImage result = new BufferedImage(im.getWidth(), im.getHeight(), im.getType());
		for(int y = 0; y < im.getHeight(); y++) 
			for(int x = 0; x < im.getWidth(); x++) {
				Color c = new Color(im.getRGB(x, y));
				int red = (int) (c.getRed() * 0.299);
				int green = (int) (c.getGreen() * 0.587);
				int blue = (int) (c.getBlue() * 0.114);
				Color newColor = new Color(red + green + blue, red + green
						+ blue, red + green + blue);
				result.setRGB(x, y, newColor.getRGB());
			}
		return result;
	}
}
