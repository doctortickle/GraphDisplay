package application;
import graph.Vertex;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class TileEventHandler {
	
	@FXML 
	Label coordinateLabel;

	TileEventHandler() {

	};
	
    public void hoverHandler(Tile tile) {
    	
    	Color hoverColor = Color.GOLD;
    	Color defaultColor = getDefaultColor(tile.getVertex());
    	tile.setFill(defaultColor);
    	
    	tile.setOnMouseEntered(e -> {
        	tile.setFill(hoverColor);
        	//coordinateLabel.setText(tile.getVertex().toString());
        });
        
    	tile.setOnMouseExited(e -> {
        	tile.setFill(defaultColor);
        });
    }
    
    private Color getDefaultColor(Vertex vertex) {
		Color defaultColor = Color.TRANSPARENT;
		
		if (vertex.getX() == 0) {
			defaultColor = Color.YELLOW;
		}
		else if(vertex.getY() == 0) {
			defaultColor = Color.GREEN;
		}
		return defaultColor;
	}
    
}
