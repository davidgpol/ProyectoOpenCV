package main.java.service;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.highgui.VideoCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.comun.constantes.ConstantesComun;
import com.proyecto.comun.dto.MatrizVO;
import javafx.scene.image.Image;
import main.java.rest.RestCliente;
import main.java.utils.Constantes;
import main.java.utils.OpenCVUtils;

@Service
public class VideoService {

	@Autowired
	private OpenCVService openCVService;
	
	public Image getFrame(VideoCapture videoCapture) {
		
    	Image imagenProcesada = null;
    	Mat frame = new Mat();
    	List <MatrizVO> listaCaras;

    	try {
    		// read the current frame
    		videoCapture.read(frame);
    		
    		// if the frame is not empty, process it
    		if (!frame.empty()) {
    			MatOfRect frameProcesadas = openCVService.detectarCaras(frame, 
    											OpenCVUtils.getClasificador(Constantes.HAARCASCADE_FRONTALFACE));
    			
    			Mat framePintadas = openCVService.pintarCaras(frame, frameProcesadas);
    			
    			if(frameProcesadas.toArray().length > ConstantesComun.CERO_INT) {
    				listaCaras = openCVService.getCaras(frameProcesadas, frame);
    				List<MatrizVO> listaCarasReconocidas = RestCliente.postCaras(listaCaras);
    			}
    			
    			// Convierte la matriz de OpenCV a una imagen de javaFX
    			imagenProcesada = OpenCVUtils.mat2Image(framePintadas);
    		}
    	} catch (Exception e) {
    		System.err.println("Fallo obteniendo el frame: " + e.getMessage());
    	}

    	return imagenProcesada;
	}
	
}