package splash;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * SplashController controls the Splash.FXML file. This is the initial software display. After a 
 * designated amount of time, the MainMenu.FXML is run.  
 *
 */
public class SplashController {
	
	@FXML 
	private StackPane splashRoot;
	@FXML
	private ImageView splashImage;
	
	/**
	 * Called after all @FXML annotated members have been injected. Starts a new thread "SplashScreen()".
	 */
	@FXML void initialize() {
		
		new SplashScreen().start(); //Start SplashScreen thread.
		
	}
	
	/**
	 * Inner class that extends thread. Displays the splash screen for a designated amount of time then loads
	 * MainMenu.FXML, sets a new stage, and displays the main menu. Hides the splash screen.
	 *
	 */
	class SplashScreen extends Thread {
		
		@Override
		public void run() {
			
			try {
				
				splashImage.fitWidthProperty().bind( splashRoot.widthProperty() );  // binds the image to the rootPane width.
				Thread.sleep( 3000 ); // puts the thread (SplashScreen) to sleep in order to allow the splash to display long enough.
				
				Platform.runLater( new Runnable() { // Forces the main menu scene to load onto the JavaFX application thread when the SplashScreen thread is available.
					
					@Override
					public void run() {
						
						FXMLLoader loader = new FXMLLoader( getClass().getResource( "../mainMenu/MainMenu.FXML" ) ); // Places MainMenu.FXML into the loader.
						Stage stage = new Stage(); // Creates a new stage.
						
						try {
							
							stage.setScene( new Scene( loader.load() )  ); // Sets the scene using the root node of the loaded FXML document.
							
						} catch ( IOException e ) {
							
							e.printStackTrace();
							
						}
						
				        stage.setResizable(false); // Prevents user from resizing the window.
				        stage.setTitle( "Test" ); // Sets the stage title.
				        stage.show(); // Displays the stage.
				        splashRoot.getScene().getWindow().hide(); // Hides the splash screen and presumably ends the SplashScreen thread.
						
					}
					
				});

			} catch (InterruptedException e) {
				
				e.printStackTrace();
			
			}
		
		}
		
	} 
	
}
