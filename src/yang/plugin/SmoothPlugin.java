package yang.plugin;

import java.awt.image.BufferedImage;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import yang.Plugin;
import yang.Yang;


public class SmoothPlugin implements Plugin{

	private BufferedImage im;
	private static final int NUMBER = 5; 
	
	@Override
	public void run() {
		im = Yang.getCurrentImage();
		int r = random.nextInt(NUMBER);
		operate(r);
	}

	
	public void operate(int random) {
		switch (random) {
		case 0:
			Yang.addSonOperation("gaussianSmooth");
			gaussianSmooth();
			break;
		case 1:
			Yang.addSonOperation("medianSmooth");
			medianSmooth();
			break;
		case 2: 
			Yang.addSonOperation("weightedSmooth");
			weightedSmooth();
			break;
		case 3:
			break;
		case 4:
			break;
		default:
			break;
		}
	}
	
	public void gaussianSmooth() {
		Mat mat = Yang.img2Mat(im);
		Mat gaussian = new Mat(mat.rows(), mat.cols(), mat.type());
		Imgproc.GaussianBlur(mat, gaussian, new Size(5, 5), 1);
		BufferedImage result = Yang.mat2Img(gaussian);
		Yang.setResultImage(result);
	}
	
	public void medianSmooth() {
		Mat mat = Yang.img2Mat(im);
		Mat median = new Mat(mat.rows(), mat.cols(), mat.type());
		Imgproc.medianBlur(mat, median, 3);
		BufferedImage result = Yang.mat2Img(median);
		Yang.setResultImage(result);
	}
	
	public void weightedSmooth() {
		 int kernelSize = 9;
		 Mat source = Yang.img2Mat(im);
		 Mat destination = new Mat(source.rows(),source.cols(),source.type());
	
		 Mat kernel = Mat.ones(kernelSize, kernelSize, CvType.CV_32F);
		 for(int i=0; i<kernel.rows(); i++) {
			 for(int j=0; j<kernel.cols(); j++) {
				 double[] m = kernel.get(i, j);
				 for(int k =0; k<m.length; k++) {
	                  if(i==1 && j==1) {
	                     m[k] = 10/18;
	                  }
	                  else{
	                     m[k] = m[k]/(18);
	                  }
	               } 
	               kernel.put(i,j, m);
			 }
		 }
		 
		 Imgproc.filter2D(source, destination, -1, kernel);
		 Yang.setResultImage(Yang.mat2Img(destination));
	}
}
