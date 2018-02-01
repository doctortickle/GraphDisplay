package application;
import graph.Vertex;
import javafx.scene.paint.Color;

public class TileEventHandler extends EventController {
	
	TileEventHandler() {
		
	};
	
    public void hoverHandler(Tile tile) {
    	
    	Color hoverColor = Color.GOLD;
    	Color defaultColor = getDefaultColor(tile.getVertex());
    	tile.setFill(defaultColor);
    	
    	tile.setOnMouseEntered(e -> {
        	tile.setFill(hoverColor);
        	gridController.getCoordinateLabel().setText(tile.getVertex().toString());
        });
        
    	tile.setOnMouseExited(e -> {
        	tile.setFill(defaultColor);
        	gridController.getCoordinateLabel().setText("No tile selected");
        });
    }
    
    private Color getDefaultColor(Vertex vertex) {
		Color defaultColor = Color.TRANSPARENT;
		
		if (vertex.getX() == 0 && gridController.yAxisSelected()) {
			defaultColor = Color.YELLOW;
		}
		else if(vertex.getY() == 0 && gridController.xAxisSelected()) {
			defaultColor = Color.GREEN;
		}
		return defaultColor;
	}
    
}
