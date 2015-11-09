package utils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesUtils {
	
	public static boolean exists(Path ruta) {
		return Files.exists(ruta, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
	}
	
	public static void borrarFichero(String rutaFichero) throws IOException {
		Path fichero = Paths.get(rutaFichero);
		Files.delete(fichero);
	}
	
	public static Path crearFichero(String rutaFichero) throws IOException {
		Path fichero = Paths.get(rutaFichero);
		return Files.createFile(fichero);		
	}
	
	public static boolean createDirectory(String rutaCarpeta) throws IOException {
		Path carpeta = Paths.get(rutaCarpeta);
		return (Files.createDirectory(carpeta) != null) ? true : false;  
	}
	
	public static boolean deleteIfExists(String rutaCarpeta) throws IOException {
		Path carpeta = Paths.get(rutaCarpeta);		
		if(!exists(carpeta)) {					
			return true;
		}
		
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(carpeta);
		
		for (Path path : directoryStream) {
            Files.delete(path);
        }
		
		Files.deleteIfExists(carpeta);
		directoryStream.close();
		return exists(carpeta);

	}
	
}