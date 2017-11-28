package webrec;

import webrec.model.WebCam;

import java.io.File;
import java.io.IOException;

import java.util.Scanner;


public class Driver {

	public static void main(String[] args) throws IOException {
		WebCam webcam = new WebCam(WebCam.EXTERNAL_WEBCAM);

		webcam.startDetector();
		System.in.read(); // keep program open
		

	}
}
