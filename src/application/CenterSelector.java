package application;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CenterSelector extends Circle {
	
	Tile tile;
	
	public CenterSelector(Tile tile, int radius) {
		this.tile = tile;
		setCenterSelector(tile.getCenter(), radius);
	}
	
	private void setCenterSelector(Point2D center, int radius) {
		this.setRadius(radius*0.1);
		this.setLayoutX(center.getX());
		this.setLayoutY(center.getY());
		setColor();
	}
	
	public void setColor() {		
		if(tile.getVertex().getX()%2 == 0) {
			this.setFill(Color.RED);
		}
		else {
			this.setFill(Color.BLUE);
		}
	}
	
}
