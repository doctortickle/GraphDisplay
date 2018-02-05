package application;

import javafx.scene.paint.Color;

public class TileEventHandler extends EventController {

    public void hoverHandler(Tile tile) {
    	
    	Color hoverColor = Color.GOLD;
    	
    	tile.setOnMouseEntered(e -> {
        	tile.setFill(hoverColor);
        	tile.getConnections().forEach( c -> c.setVisible( gridController.connectionsSelected() ) );
        	gridController.getCoordinateLabel().setText(tile.getVertex().toString());   
        	System.out.println("Test.");
        });

    	tile.setOnMouseExited(e -> {
        	tile.setFill(tile.getDefaultColor());
        	tile.getConnections().forEach( c -> c.setVisible( false ) );
        	gridController.getCoordinateLabel().setText("No tile selected");
        });    			
    	
    }
       
}
