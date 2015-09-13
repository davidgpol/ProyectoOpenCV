package main.java;

import java.io.IOException;
import java.net.URL;

import org.opencv.core.Core;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.configuracion.ContextoSpring;
import main.java.controlador.OpenCVControlador;
import main.java.utils.Constantes;

public class MainApp extends Application {

	static { 
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);		
	}
	
	private Stage primaryStage;
	private StackPane rootLayout;
	private FXMLLoader openCVControlerLoader;
	private ApplicationContext contextoSpring = new AnnotationConfigApplicationContext(ContextoSpring.class);
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage	= primaryStage;
		this.primaryStage.setTitle("Cliente OpenCV");
		this.initRootLayout();
		this.mostrarOpenCVLayout();
	}

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
        	FXMLLoader loader = new FXMLLoader();
            URL rootLayoutResource = getClass().getClassLoader().getResource(Constantes.RUTA_VISTA + "RootLayout.fxml");
            loader.setLocation(rootLayoutResource);
            rootLayout = (StackPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }	
	
    public void mostrarOpenCVLayout() {
        try {
            // Load person overview. 
        	openCVControlerLoader = new FXMLLoader();
            URL openCVLayoutResource = getClass().getClassLoader().getResource(Constantes.RUTA_VISTA + "OpenCVLayout.fxml");
            openCVControlerLoader.setLocation(openCVLayoutResource);
            openCVControlerLoader.setControllerFactory(new Callback<Class<?>, Object>() {

            	@Override
            	public Object call(Class<?> clase) {
            		if (clase == OpenCVControlador.class) {
            			return new OpenCVControlador(contextoSpring);
            		} else
            			try {
            				return clase.newInstance();
            			} catch (Exception e) {	
            				e.printStackTrace();
            				throw new RuntimeException(e);
            			}
            	}

            });
            
            AnchorPane openCVScene = (AnchorPane) openCVControlerLoader.load();

            StackPane.setAlignment(openCVScene,Pos.BOTTOM_CENTER);
            rootLayout.getChildren().add(0,openCVScene);

            rootLayout.widthProperty().addListener(   
                    (observable, oldvalue, newvalue) -> {                    	
                    	Double newValueDouble = (Double) newvalue;
                    	
                    	// Obtiene el AnchorPane
                    	ObservableList<Node> lista = rootLayout.getChildren();
                    	AnchorPane ap = (AnchorPane) lista.get(0);
                    	
                    	// Actualiza el ancho del AnchorPane
                    	ap.setMinWidth(newValueDouble);
                    	
                    	// Actualiza el tamaño del ImageView
                        ImageView iv = (ImageView) ap.getChildren().get(0);
                        iv.setFitWidth(ap.getMinWidth());    
                        ap.getChildren().set(0, iv);                            
                        
                    	rootLayout.getChildren().set(0, ap);                                        	
                    }
            );
                     
            rootLayout.heightProperty().addListener(   
                    (observable, oldvalue, newvalue) -> {
                    	// Obtiene el AnchorPane
                    	ObservableList<Node> lista = rootLayout.getChildren();
                    	AnchorPane ap = (AnchorPane) lista.get(0);
                    	
                    	// Actualiza el ancho del AnchorPane
                    	ap.setMinHeight((Double)newvalue);
                    	System.out.println(ap.getMinHeight());
                    	
                    	// Actualiza el tamaño del ImageView
                        ImageView iv = (ImageView) ap.getChildren().get(0);
                        iv.setFitHeight(ap.getMinHeight());                       
                        
                        ap.getChildren().set(0, iv);
                        
                    	rootLayout.getChildren().set(0, ap);                       	                    	
                    }
            );            
            

            System.out.println(rootLayout.getMinWidth() + " " + rootLayout.getMinHeight());

        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    @Override  
    public void stop() {
    	OpenCVControlador openCVControlador = this.openCVControlerLoader.getController();
    	openCVControlador.pararReconocimiento();
    }  
    
	public static void main(String[] args) {		
		launch(args);		
	}
}