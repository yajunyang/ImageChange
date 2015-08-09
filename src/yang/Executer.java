package yang;

import java.awt.image.BufferedImage;

/**
 * This class is 'single instance model'.
 * @author yang
 *
 */
public class Executer {

	private static Executer executer = new Executer();
	
	private Executer() {}
	
	public static Executer getExecuterInstance() {
		return executer;
	}
	
	/**
	 * Every time you involve method 'run()', you must be sure
	 * the currentImage in 'Yang' is not empty.
	 * You can call Yang.setCurrentImage(image) to initial the current image.
	 */
	public void run() {
		BufferedImage im = Yang.getCurrentImage();	
		if(null == im) 
			throw new IllegalAccessError("You must set or input an image");
		
		Object object = getPluginInstance();
		Plugin plugin = null;
		
		if(object instanceof Plugin)
			plugin = (Plugin)object;
			
		if(null == plugin) {
			return;
		} else {
			Yang.clearDescription();
			
			long startTime = System.currentTimeMillis();
			
			// NOTE: in fact, every time to create image to initial resultImage in Yang is 
			//		 low efficient. We can try to solve this problem in the method of single instance.
			// 	     TRY TO REDUCE THR NEW OF REPEATED OBJECT
			plugin.run();
			
			long endTime = System.currentTimeMillis();
			long runTime = endTime - startTime;
			Yang.setDescription("Running Time: " + runTime + "ms");
		}
	}
	
	protected Object getPluginInstance() {
		String file = Yang.getRandomPluginName();

		Object plugin = null;
		try {
			plugin = Class.forName(file).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return plugin;
	}

}
