package yang.plugin.face;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

/**
 * This class is used in {@link MorphPlugin}
 * @author yang
 *
 */

public class FaceDetector {

	private Mat faceImage;
	
	public FaceDetector(final Mat face) {
		faceImage = face;
	}
	
	public void initImage(Mat face) {
		faceImage = face;
	}
	
	/**
	 * Take face detection for the current image. 
	 * @return the image marking the face.
	 */
	public Mat run() {
		faceDetect();
		return faceImage;
	}
	
	public void faceDetect() {
		CascadeClassifier faceDetector = new CascadeClassifier(
				FaceDetector.class.getResource("haarcascade_frontalface_alt.xml").getPath());
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(faceImage, faceDetections);
		
		 for (Rect rect : faceDetections.toArray()) {
	            Core.rectangle(faceImage, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
	                    new Scalar(0, 255, 0));
	        }
	}
	
	
	// Test
	public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("\nRunning FaceDetector");

        CascadeClassifier faceDetector = new CascadeClassifier(FaceDetector.class.getResource("haarcascade_frontalface_alt.xml").getPath());
        Mat image = Highgui.imread(FaceDetector.class.getResource("shekhar.JPG").getPath());

        System.out.println(image.width());
        
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

        for (Rect rect : faceDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }

        String filename = "ouput.png";
        System.out.println(String.format("Writing %s", filename));
        Highgui.imwrite(filename, image);
    }
}
