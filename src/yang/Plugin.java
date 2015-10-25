package yang;

import java.util.Random;

public interface Plugin {

	/**
	 * Any class implementing this plugin can be designed to execute any kind of
	 * random "image processing" method if this java file is under the the dir
	 * "yang.plugin". <br/>
	 * <h1/>There are two 'images' reference in 'Yang', {@link currentImage}
	 * {@link resultImage}<h1>
	 */
	public void run();
	
	static final Random random = new Random();
}

