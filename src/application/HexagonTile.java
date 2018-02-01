package application;
import graph.Vertex;
import javafx.scene.paint.Color;

public class HexagonTile extends Tile {

	public HexagonTile(Vertex vertex, int radius) {
		super(vertex, radius);
	}
	
	protected void buildTile() {
		for(int i = 1; i <= 6; i++) {
			double theta = i*(Math.PI/3);
			double x = radius*Math.cos(theta);
			double y = radius*Math.sin(theta);
			this.getPoints().addAll(x,y);
		}    
       
		double horiz = (radius*2)*0.75;
		double vert = (Math.sqrt(3)/2) * (radius*2);
		double offset = (vertex.getX()%2)*0.5*vert;
		this.setLayoutX(vertex.getX()*horiz);
		this.setLayoutY(vertex.getY()*vert + Math.abs(offset));
		this.setStroke(Color.BLACK);
		this.setFill(Color.TRANSPARENT);
	}
	
}
