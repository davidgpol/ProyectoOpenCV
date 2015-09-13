package main.java.controlador;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.opencv.highgui.VideoCapture;
import org.springframework.context.ApplicationContext;

import com.proyecto.comun.constantes.ConstantesComun;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.java.rest.RestCliente;
import main.java.timer.TareaOpenCV;
import main.java.utils.Constantes;

public class OpenCVControlador {
	
    @FXML
    private Text iniciarReconocimientoLabel;
    
    @FXML
    private ImageView frameActual;
    
    private TareaOpenCV tareaOpenCV;
    
    private ScheduledExecutorService executor;
    private ScheduledFuture<?> result;
    private VideoCapture videoCapture;
    
    public OpenCVControlador() {
    	this.videoCapture = new VideoCapture();
    }
    
    public OpenCVControlador(ApplicationContext applicationContext) {
    	this();
    	this.tareaOpenCV = (TareaOpenCV) applicationContext.getBean("tareaOpenCV");
	}
    
    @FXML
    public void iniciarReconocimiento() {
    	if (!this.videoCapture.isOpened()) {
    		this.videoCapture.open(0);

    		// Inicia el procesado y la muestra de imagenes
    		this.tareaOpenCV.setVideoCapture(this.videoCapture);
    		this.tareaOpenCV.setFrameActual(this.frameActual);

    		// Realiza el procesado a 30 fps
    		this.executor = Executors.newScheduledThreadPool(ConstantesComun.UNO_INT);    		
    		this.result = this.executor.scheduleAtFixedRate(this.tareaOpenCV, 0, Constantes.MILISEGUNDOS_FRAME, TimeUnit.MILLISECONDS);	
    		this.iniciarReconocimientoLabel.setText("Parar reconocimiento");
    		System.out.println(RestCliente.getStadoServidor());
    	} else {
    		this.iniciarReconocimientoLabel.setText("Iniciar reconocimiento");
    		pararReconocimiento();
    	}
    }

    public void pararReconocimiento() {
		// Detiene la tarea
		if (this.result != null) {
			this.result.cancel(true);
			this.executor.shutdownNow();
		}
		
		// Libera la camara
		this.videoCapture.release();
		// Inicializa el contenedor de imagenes
		frameActual.setImage(null);    	
    }
}