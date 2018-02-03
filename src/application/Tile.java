package application;
import java.util.ArrayList;

import graph.Vertex;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public abstract class Tile extends Polygon {
	
	protected Vertex vertex;
	protected Group connectionsGroup;
	protected Color defaultColor, axisColor;
	protected int radius;
	
	public Tile(Vertex vertex, int radius) {
		this.vertex = vertex;
		this.radius = radius;
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
	
	public Vertex getVertex() {
		return this.vertex;
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public void buildConnections() {
		connectionsGroup = new Group();
    	for(Vertex connectedVertex : vertex.getConnectedVertices()) {
    		Connection connection = new Connection(this, (Tile) connectedVertex.getGUIComponenet());
    		connectionsGroup.getChildren().add(connection);
    	}
	}
	
	public Group getConnections() {
		return connectionsGroup;
	}
	
	public Point2D getCenter() {
		return new Point2D(getLayoutX(), getLayoutY());
	}
	
}
