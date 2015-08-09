package yang.plugin;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import yang.Plugin;
import yang.Yang;

public class EdgePlugin implements Plugin {

	private BufferedImage im;	// Get the current image
	private static final int NUMBER = 4;
	private static int previousRandom;
	
	@Override
	public void run() {
		im = Yang.getCurrentImage();
		
		int r = random.nextInt(NUMBER);
		while(r == previousRandom) {
			r = random.nextInt(NUMBER);
		}
		previousRandom = r;
		operate(1);
	}

	public void operate(int random) {
		switch (random) {
		case 0:
			Yang.addSonOperation("sobel");
			Yang.setDescription("This is a soble image algorithm!");
			sobel();
			break;
		case 1:
			Yang.addSonOperation("canny");
			canny();
			break;
		case 2:
			Yang.addSonOperation("log");
			log();
			break;
		case 3:
			Yang.addSonOperation("laplace");
			laplace();
			break;
		}
	}

	public void sobel() {
		Mat mat = Yang.img2Mat(im);	// LOW EFFICIENT
		Mat dst = new Mat(mat.rows(), mat.cols(), mat.type());
		Imgproc.Sobel(mat, dst, mat.depth(), 1, 0);
		BufferedImage result = Yang.mat2Img(dst);
		Yang.setResultImage(result);
	}

	public void canny() {
		Mat mat = Yang.img2Mat(im);
		Mat edges = new Mat(mat.rows(), mat.cols(), mat.type());
		Imgproc.Canny(mat, edges, 30 + random.nextInt(30), 150 - random.nextInt(30));
		BufferedImage result = Yang.mat2Img(edges);
		Yang.setResultImage(result);
	}

	public void log() {
		BufferedImage result = new BufferedImage(im.getWidth(), im.getHeight(), im.getType());
		int width = im.getWidth();
		int height = im.getHeight();
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				int rgb = im.getRGB(x, y);
				int red = (rgb & 0x00ff0000) >> 16;
				int green = (rgb & 0x0000ff00) >> 8;
				int blue = rgb & 0x000000ff;
				
				red = (int)((255.0 / Math.log(256)) * Math.log(1.0 + (double)red));
				green = (int)((255.0 / Math.log(256)) * Math.log(1.0 + (double)green));
				blue = (int)((255.0 / Math.log(256)) * Math.log(1.0 + (double)blue));
				
				Color color = new Color(red, green, blue);
				int pixel = color.getRGB();
				result.setRGB(x, y, pixel);
			}
		Yang.setResultImage(result);
	}

	public void laplace() {
		
		// 0  -1  0
		// -1  4 -1 
		// 0  -1  0
		
//		int kernelSize = 3;
//		Mat source = Yang.img2Mat(im);
//		Mat destination = new Mat(source.rows(), source.cols(), source.type());
//		Mat kernel = new Mat(kernelSize, kernelSize, CvType.CV_32F) {
//			{
//				put(0, 0, 0);
//				put(0, 1, -1);
//				put(0, 2, 0);
//
//				put(1, 0 - 1);
//				put(1, 1, 4);
//				put(1, 2, -1);
//
//				put(2, 0, 0);
//				put(2, 1, -1);
//				put(2, 2, 0);
//			}
//		};
//		Imgproc.filter2D(source, destination, -1, kernel);
//		Yang.setResultImage(Yang.mat2Img(destination));
		
		float[] kernel= {0, -1, 0, -1, 4, -1, 0, -1, 0};
		BufferedImage result = Yang.convolveNxN(im, kernel, 3, false);
		Yang.setResultImage(result);
	}
}
