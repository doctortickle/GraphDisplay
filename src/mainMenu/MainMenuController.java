package mainMenu;

import java.io.IOException;

import application.GraphController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class MainMenuController {
	
	@FXML private ChoiceBox<String> tileShape;
	@FXML private Slider maxX;
	@FXML private Slider maxY;
	@FXML private Button generate;
	
	@FXML void initialize() {
		
		setTileOptions();
		
	}
	
	private void setTileOptions() {
		tileShape.setItems( FXCollections.observableArrayList( "Triangle", "Square", "Hexagon" ) );
	}
	
	@FXML
	private void generateMap() {
		
		new GameScreen().start();
		
	}
	
	class GameScreen extends Thread {
		
		@Override
		public void run() {
			
			Platform.runLater( new Runnable() {
				
				@Override
				public void run() {
						
					FXMLLoader loader = new FXMLLoader( getClass().getResource( "../application/Graph.FXML" ) );
					
					Stage stage = new Stage();
			        try {
						stage.setScene( new Scene( loader.load() ) );
					} catch (IOException e) {
						e.printStackTrace();
					}
			        
					GraphController controller = loader.<GraphController>getController();
			        controller.init(tileShape.getValue(), (int) maxX.getValue(), (int) maxY.getValue());
			        stage.setTitle( "Graph Display" );
			        stage.show();
					
				}
				
			});
		
		}
		
	} 
	
}
