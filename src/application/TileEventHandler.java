package application;
import javafx.scene.paint.Color;

public class TileEventHandler extends EventController {
	
	TileEventHandler() {
		
	};
	
    public void hoverHandler(Tile tile) {
    	
    	Color hoverColor = Color.GOLD;
   	
    	tile.setOnMouseEntered(e -> {
        	tile.setFill(hoverColor);
        	gridController.getCoordinateLabel().setText(tile.getVertex().toString());
        	//System.out.print("Tile " + tile.getVertex().toString() + " selected.");
        });
        
    	tile.setOnMouseExited(e -> {
        	tile.setFill(tile.getDefaultColor());
        	gridController.getCoordinateLabel().setText("No tile selected");
        });
    }
    
    private void showConnections(Tile tile) {
    	for(Connection connection : tile.getConnections()) {
    		connection.setVisible(true);
    	}
    }
    
    private void removeConnections(Tile tile) {
    	for(Connection connection : tile.getConnections()) {
    		connection.setVisible(false);
    	}
    }
    
}
