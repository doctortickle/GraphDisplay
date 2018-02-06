package application;

import graph.Vertex;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class Connection extends Line {
	
	public Connection( Vertex a, Vertex b ) {
		
		drawLine( a, b );
		
	}
	
	private void drawLine( Vertex a, Vertex b ) {
		
		setStartX( a.getLayoutX() );
		setStartY( a.getLayoutY() );
		setEndX( b.getLayoutX() );
		setEndY( b.getLayoutY() );
		setStrokeLineCap( StrokeLineCap.SQUARE );
		setStroke( Color.BLACK );
		setMouseTransparent( true );
		
	}
	
	

}
