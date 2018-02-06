package splash;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SplashController {
	
	@FXML 
	private StackPane rootPane;
	@FXML
	private ImageView image;
	
	@FXML void initialize() {
		
		new SplashScreen().start();
		
	}
	
	class SplashScreen extends Thread {
		
		@Override
		public void run() {
			
			try {
				
				image.fitWidthProperty().bind( rootPane.widthProperty() ); 
				Thread.sleep( 3000 );
				
				Platform.runLater( new Runnable() {
					
					@Override
					public void run() {
						
						Parent root = null;
						try {
							
							root = FXMLLoader.load( getClass().getResource( "../mainMenu/MainMenu.FXML" ) );
							
						} catch ( IOException e ) {
							
							e.printStackTrace();
							
						}
						
						Scene scene = new Scene( root );
						Stage stage = new Stage();
				        stage.setScene(scene);
				        stage.setResizable(false);
				        stage.setTitle( "RussellCode" );
				        stage.show();
				        rootPane.getScene().getWindow().hide();
						
					}
					
				});

			} catch (InterruptedException e) {
				
				e.printStackTrace();
			
			}
		
		}
		
	} 
	
}
