package application;
import graph.Vertex;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public abstract class Tile extends Polygon {
	
	protected Vertex vertex;
	protected int radius;
	
	public Tile(Vertex vertex, int radius) {
		this.vertex = vertex;
		this.radius = radius;
		buildTile();
		assignTileToVertex();
	}
	
	protected abstract void buildTile();
	
	private void assignTileToVertex() {
		vertex.setGUIComponent(this);
	}
	
	public Vertex getVertex() {
		return this.vertex;
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public Point2D getCenter() {
		return new Point2D(getLayoutX(), getLayoutY());
	}
	
}
