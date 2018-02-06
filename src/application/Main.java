package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	@Override
	public void start(Stage stage) {
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( getClass().getResource( "../application/Graph.fxml" ) );
			Parent root = loader.load();

	        Scene scene = new Scene( root );    
	        //stage.initStyle( StageStyle.UNDECORATED );
	        stage.setScene( scene );
	        stage.show();
	        
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void main(String[] args) {
		
		launch(args);
	
	}
}
