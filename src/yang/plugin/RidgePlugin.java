package yang.plugin;

import java.awt.image.BufferedImage;

import yang.Plugin;
import yang.Yang;

public class RidgePlugin implements Plugin{

	private BufferedImage im;
	private static final int NUMBER = 4;
	
	@Override
	public void run() {
		im = Yang.getCurrentImage();
		int r = random.nextInt(NUMBER);
		operate(r);
	}
	
	
	public void operate(int random) {
		switch (random) {
		case 0:
			Yang.addSonOperation("1");
			break;

		default:
			Yang.addSonOperation("default");
			break;
		}
	}
	
}
