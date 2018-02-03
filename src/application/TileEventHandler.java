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
        	showConnections(tile);
        	gridController.getCoordinateLabel().setText(tile.getVertex().toString());
        	System.out.print("Tile " + tile.getVertex().toString() + " selected.");
        });
        
    	tile.setOnMouseExited(e -> {
        	tile.setFill(defaultColor);
        	removeConnections(tile);
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
    
    private void showConnections(Tile tile) {
    	tile.getConnections().setVisible(true);
    }
    
    private void removeConnections(Tile tile) {
    	tile.getConnections().setVisible(false);
    }
    
}
