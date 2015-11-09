package testUtils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.net.URL;

import org.opencv.core.Mat;

public class Utils {
	
	public static final int DIMENSIONES_VIDEO_FILAS			= 640;
	public static final int DIMENSIONES_VIDEO_COLUMNAS		= 480;	
	public static final double UMBRAL_RECONOCIMIENTO_TEST	= 0.996;
	public static final float RANGO_INICIO_TEST				= 0f;
	public static final float RANGO_FIN_TEST				= 256f;
	public static final int TAMANO_HISTORIAL_TEST			= 25;
	public static final int RANGO_MIN_NORMALIZAR_TEST		= 0;
	public static final int RANGO_MAX_NORMALIZAR_TEST		= 1;
	public static final int TIPO_NORMALIZAR_TEST			= -1;
	public static final int CANALES_TEST					= 0;
	
	public static String getRutaRecurso(String carpeta, String nombreRecurso) {		
		URL urlImagen = Utils.class.getClassLoader().getResource(carpeta + nombreRecurso);
		String rutaVideo = urlImagen.getFile().substring(1);
		return rutaVideo;
	}
		
	public static BufferedImage mat2AwtImage(Mat mat) {

	    int type = 0;
	    if (mat.channels() == 1) {
	        type = BufferedImage.TYPE_BYTE_GRAY;
	    } else if (mat.channels() == 3) {
	        type = BufferedImage.TYPE_3BYTE_BGR;
	    } else {
	        return null;
	    }

	    BufferedImage image = new BufferedImage(mat.width(), mat.height(), type);
	    image.getScaledInstance(DIMENSIONES_VIDEO_FILAS, DIMENSIONES_VIDEO_COLUMNAS, Image.SCALE_SMOOTH);
	    WritableRaster raster = image.getRaster();
	    DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
	    byte[] data = dataBuffer.getData();
	    mat.get(0, 0, data);

	    return image;
	}
}
