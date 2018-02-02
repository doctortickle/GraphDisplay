package application;
import graph.Vertex;
import javafx.scene.paint.Color;

public class SquareTile extends Tile {
	
	public SquareTile(Vertex vertex, int radius) {
		super(vertex, radius);
	}
	
	protected void buildTile() {

		for(int i = 1; i <= 8; i+=2) {
			double theta = i*(Math.PI/4);
			double x = radius*Math.cos(theta);
			double y = radius*Math.sin(theta);
			this.getPoints().addAll(x,y);
		}
		double offset = radius - (2 * (radius - (radius/Math.sqrt(2))));
		setLayoutX(vertex.getX()*(radius+offset));
		setLayoutY(vertex.getY()*(radius+offset));
	}

}
