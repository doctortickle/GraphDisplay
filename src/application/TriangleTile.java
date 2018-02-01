package application;
import graph.Vertex;
import javafx.scene.paint.Color;

public class TriangleTile extends Tile {
	
	public TriangleTile(Vertex vertex, int radius) {
		super(vertex, radius);
	}
	
	protected void buildTile() {
		double offsetX = 0;
		double offsetY = 0;
		if( (vertex.getX() & 1) == 0 && (vertex.getY() & 1) == 0 || (vertex.getX() & 1) != 0 && (vertex.getY() & 1) != 0 ) {
			for(int i = 0; i <= 2; i++) {
				double theta = i*(2*Math.PI/3);
				double x = radius*Math.cos(theta + (Math.PI/6));
				double y = radius*Math.sin(theta + (Math.PI/6));
				this.getPoints().addAll(x,y);
				offsetY = 0.25 * radius;
			}
		}
		else {
			for(int i = 0; i <= 2; i++) {
				double theta = i*(2*Math.PI/3);
				double x = radius*Math.cos(theta - (Math.PI/6));
				double y = radius*Math.sin(theta - (Math.PI/6));
				this.getPoints().addAll(x,y);
				offsetY = -0.25 * radius;
			}
		}
		
		offsetX = -vertex.getX() * ( ( ( Math.sqrt(3) - 1 ) * radius )/( 2 * ( 1 + Math.sqrt( 3 ) ) ) );
		offsetY = offsetY + (vertex.getY() * 0.5 * radius);
		
		this.setLayoutX(vertex.getX()*radius + offsetX);
		this.setLayoutY(vertex.getY()*radius + offsetY);
		this.setStroke(Color.BLACK);
		this.setFill(Color.TRANSPARENT);
	}
	
}
