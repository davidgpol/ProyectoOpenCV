package main.java.rest;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.proyecto.comun.constantes.ConstantesComun;
import com.proyecto.comun.dto.MatrizVO;
import com.proyecto.comun.opencv.OpenCVUtils;

public class RestCliente {
	
	public static String getStadoServidor() {
		RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(ConstantesComun.SERVIDOR_URI + ConstantesComun.ESTADO_SERVIDOR, String.class);
    }
	
	public static Mat postProcesarFrame(Mat frame) {
		MatrizVO frameProcesado = null;
		
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        MatrizVO matrizVO = new MatrizVO(frame.rows(), frame.cols(), frame.type(), OpenCVUtils.getBytesMatriz(frame));

        try {
        	frameProcesado = restTemplate.postForObject(ConstantesComun.SERVIDOR_URI + ConstantesComun.RECONOCER_IMAGEN,
        			matrizVO, MatrizVO.class);
        	
        	return OpenCVUtils.matrizVOToMat(frameProcesado);
        }
        catch (HttpClientErrorException e) {
            System.out.println("error:  " + e.getResponseBodyAsString());
        }
        catch(Exception e) {
            System.out.println("Error generico");
        }        
        return frame;
    }
 
}