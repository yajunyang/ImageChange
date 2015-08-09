package yang;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Yang {

	private static Random random = new Random();
	private static int pluginCount = 0;
	private static BufferedImage currentImage;	// current image
	private static BufferedImage resultImage = null;	// result image
	private static String currentOperation;
	private static String currentDescription;
	private static Vector<String> pluginNames = new Vector<String>();
	private static int previousRandom;

	static {
		Runtime.getRuntime().gc();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		File pluginFile = new File("src/yang/plugin");
		String[] files = pluginFile.list();

		for (int i = 0; i < files.length; i++) {
			if (files[i].endsWith(".java")) {
				pluginNames.add("yang.plugin." + files[i].substring(0, files[i].length()-5));
				pluginCount++;
			}
		}
	}

	public static String getRandomPluginName() {
		int rand = random.nextInt(pluginCount);
		while(rand == previousRandom) {
			rand = random.nextInt(pluginCount);
		}
		previousRandom = rand;
		String pluginFile = pluginNames.get(rand);
		currentOperation = pluginFile;
		return pluginFile;
	}
	
	public static void setDescription(String description) {
		currentDescription = description;
	}
	
	public static String getDescription() {
		return currentDescription;
	}

	public static void clearDescription() {
		currentDescription = null;
	}

	
	public static void addSonOperation(String sonOperation) {
		currentOperation = currentOperation + "/" + sonOperation;
	}
	
	/**
	 * Return the 'PLugin' object's path.
	 * @return
	 */
	public static String getCurrentOperation() {
		return currentOperation;
	}
	
	/**
	 * The current image being processed
	 * <h1>Reference Relationship<h1/>
	 * @param im
	 */
	public static void setCurrentImage(BufferedImage im) {
		currentImage = im;
	}

	public static BufferedImage getCurrentImage() {
		if(currentImage == null) 
			throw new IllegalAccessError("The current image can't be null!");
		return currentImage;
	}
	
	/**
	 * Reference
	 * @param result
	 */
	public static void setResultImage(BufferedImage result) {
		resultImage = result;
	}
	
	/**
	 * Return a reference of the 'Result' image.
	 * @return
	 */
	public static BufferedImage getResultImage() {
		return resultImage;
	}

	/**
	 * set the 'currentImage' reference with the 'resultImage' reference.
	 */
	public static void update() {
		currentImage = resultImage;
	}
	
	
	
	//// Some Tools /////
	
	public static BufferedImage mat2Img(Mat in) {
		if (null == in) throw new IllegalAccessError("The parameter of method mat2Img() is null!");
		int width = in.cols();
		int height = in.rows();
		byte[] data = new byte[width * height * (int) in.elemSize()];
		in.get(0, 0, data);

		int type;
		if (in.channels() == 1)
			type = BufferedImage.TYPE_BYTE_GRAY;
		else
			type = BufferedImage.TYPE_3BYTE_BGR;

		BufferedImage out = new BufferedImage(width, height, type);
		out.getRaster().setDataElements(0, 0, width, height, data);
		return out;
	}

	public static Mat img2Mat(BufferedImage in) {
		if (null == in) throw new IllegalAccessError("The parameter of method img2Mat() is null!");
		int rows = in.getHeight();
		int cols = in.getWidth();
		Mat out;
		byte[] data;
		int r, g, b;

		if (in.getType() != BufferedImage.TYPE_BYTE_GRAY) {
			out = new Mat(rows, cols, CvType.CV_8UC3);
			data = new byte[cols * rows * (int) out.elemSize()];
			int[] dataBuff = in.getRGB(0, 0, cols, rows, null, 0, cols);

			for (int i = 0; i < dataBuff.length; i++) {
				data[i * 3] = (byte) ((dataBuff[i] >> 16) & 0xFF);
				data[i * 3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
				data[i * 3 + 2] = (byte) ((dataBuff[i] >> 0) & 0xFF);
			}
		} else {
			out = new Mat(rows, cols, CvType.CV_8UC1);
			data = new byte[cols * rows * (int) out.elemSize()];
			int[] dataBuff = in.getRGB(0, 0, cols, rows, null, 0, cols);

			for (int i = 0; i < dataBuff.length; i++) {
				r = (byte) ((dataBuff[i] >> 16) & 0xFF);
				g = (byte) ((dataBuff[i] >> 8) & 0xFF);
				b = (byte) ((dataBuff[i] >> 0) & 0xFF);
				data[i] = (byte) ((0.21 * r) + (0.71 * g) + (0.07 * b));
			}
		}

		out.put(0, 0, data);
		return out;
	}

	/**
	 * Not reference relationship.
	 */
	public static BufferedImage convolveNxN(BufferedImage img, float[] kernel, int size, boolean indexOutCheck) {
		assert kernel.length == size * size;
		assert size / 2 != 0;
		
		int w = img.getWidth();
		int h = img.getHeight();	
		int k = size / 2;
		
		BufferedImage image = duplicate(img, k, k);
		
		BufferedImage result = new BufferedImage(w, h, img.getType());
		Graphics g1 = result.getGraphics();
		g1.drawImage(img, 0, 0, null);
		
		for(int y = 0; y < h; y++) 
			for(int x = 0; x < w; x++) {
				int rgb, index;
				int alpha = 0, red = 0, green = 0, blue = 0;
				for(int i = 0; i < size; i++) 
					for(int j = 0; j < size; j++) {
						rgb = image.getRGB(x + j, y + i);
						index = j + i * size;
						alpha += kernel[index] * ((rgb >> 24) & 0xff);
						red += kernel[index]  * ((rgb >> 16) & 0xff);
						green += kernel[index] * ((rgb >> 8) & 0xff);
						blue += kernel[index] * (rgb & 0xff);
					}
				if(indexOutCheck) {
					if(red > 255) red = 255;
					if(red < 0) red = 0;
					if(green > 255) green = 255;
					if(green < 0) green = 0;
					if(blue > 255) blue = 255;
					if(blue < 0) blue = 0;
				}
				rgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
				result.setRGB(x, y, rgb);
			}
		return result;
	}
	
	/**
	 * Duplicate an image and which in the center part is {@link img}
	 * <h1>dx and dy can't be zero!!!<h1/>
	 */
	@Deprecated
	public static BufferedImage duplicate(BufferedImage img, int dx, int dy) {
		BufferedImage image = new BufferedImage(img.getWidth() + 2 * dx, img.getHeight() * 2 * dy, img.getType());
		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.clearRect(0, 0, image.getWidth()-1, image.getHeight()-1);
		g.drawImage(img, dx, dy, null);
		g.dispose();
		return image;
	}

}
