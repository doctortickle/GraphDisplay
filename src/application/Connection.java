package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class Connection extends Line {
	
	public Connection(Tile a, Tile b) {
		drawLine(a, b);
	}
	
	private void drawLine(Tile a, Tile b) {
		setStartX(a.getLayoutX());
		setStartY(a.getLayoutY());
		setEndX(b.getLayoutX());
		setEndY(b.getLayoutY());
		this.setStrokeLineCap(StrokeLineCap.SQUARE);
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(3);
	}
	
	

}
