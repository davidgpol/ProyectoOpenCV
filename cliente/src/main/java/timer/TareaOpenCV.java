package main.java.timer;

import org.opencv.highgui.VideoCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.service.VideoService;

@Component
public class TareaOpenCV implements Runnable{
	
	@Autowired
	private VideoService videoService;
	
	private VideoCapture videoCapture;
	
	private ImageView frameActual;
	
	public VideoCapture getVideoCapture() {
		return videoCapture;
	}

	public void setVideoCapture(VideoCapture videoCapture) {
		this.videoCapture = videoCapture;
	}

	public ImageView getFrameActual() {
		return frameActual;
	}

	public void setFrameActual(ImageView frameActual) {
		this.frameActual = frameActual;
	}

	@Override
	public void run() {		
        Image imagen = this.videoService.getFrame(this.videoCapture);
        Platform.runLater(new Runnable() {
                @Override
                public void run() {
                	frameActual.setImage(imagen);
                }
        });
	}

}