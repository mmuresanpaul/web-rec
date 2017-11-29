package webrec.model;

import java.util.List;
import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Dimension;
import javax.imageio.ImageIO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;


public class WebCam implements WebcamMotionListener {
	
	private Webcam webcam;
	private List<Webcam> webcams;
	private WebcamMotionDetector detector;
	public static final int DEFAULT_WEBCAM = 0;
	public static final int EXTERNAL_WEBCAM = 1;

	public WebCam(int webcamInput) {
		webcams = Webcam.getWebcams();
	
		//webcam[0] is default, webcam[1] first pluggen in one and so on
		if(webcams.size() < webcamInput) 
			webcam = Webcam.getDefault();
		else if(webcams.get(webcamInput) != null) {
			webcam = webcams.get(webcamInput);
		}
		else webcam = Webcam.getDefault();
		webcam.setViewSize(new Dimension(640, 480));
//		webcam.open();

		detector = new WebcamMotionDetector(webcam);
		detector.setInterval(500); // one check per 500 ms
		detector.addMotionListener(this);

	}

	public void startDetector() {
		detector.start();
	}
	public void stopDetector() {
		detector.stop();
	}

	private String getDateAsString() {
		Date currentDate = new Date();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(currentDate);
		return date;
    }

	@Override
	public void motionDetected(WebcamMotionEvent wme) {
		try {
			takePicture();
		} catch(IOException e) {
			System.out.println("couldn't save picture");
		}
		System.out.println("Motion detected at: " + getDateAsString());
	}

	public void takePicture() throws IOException {
		BufferedImage image = webcam.getImage();
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();

		String userHomeFolder = System.getProperty("user.home");
		String directoryPath = userHomeFolder + "/WebPics" + "/"+year +	
				"/"+month+"-"+day;
		File directory = new File(directoryPath);
		directory.mkdirs();
		File pathFile = new File(directoryPath + "/" +
				   	getDateAsString() + ".png");
		pathFile.createNewFile();
		ImageIO.write(image, "PNG", pathFile);
	}

}
