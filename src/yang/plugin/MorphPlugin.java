package yang.plugin;

import java.awt.image.BufferedImage;

import yang.Plugin;
import yang.Yang;


public class MorphPlugin implements Plugin{

	private BufferedImage im;
	private static final int NUMBER = 1;
	
	@Override
	public void run() {
		im = Yang.getCurrentImage();
		int r = random.nextInt(NUMBER);
		operate(r);
	}
	
	public void operate(int random) {
		switch (random) {
		case 0:
			break;

		default:
			Yang.addSonOperation("default");
			break;
		}
	}
}
