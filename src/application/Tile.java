package application;
import java.util.ArrayList;

import graph.Vertex;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public abstract class Tile extends Polygon {
	
	protected Vertex vertex;
	protected ArrayList<Connection> connections;
	protected int radius;
	
	public Tile(Vertex vertex, int radius) {
		this.vertex = vertex;
		this.radius = radius;
		this.connections = new ArrayList<>();
		buildTile();
		assignTileToVertex();
		setStroke(Color.BLACK);
		setOpacity(0.5);
		setFill(Color.TRANSPARENT);
	}
	
	protected abstract void buildTile();
	
	private void assignTileToVertex() {
		vertex.setGUIComponent(this);
	}
	
	public void addConnection(Connection connection) {
		connections.add(connection);
	}
	
	public Vertex getVertex() {
		return this.vertex;
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public ArrayList<Connection> getConnections() {
		return connections;
	}
	
	public Point2D getCenter() {
		return new Point2D(getLayoutX(), getLayoutY());
	}
	
}
