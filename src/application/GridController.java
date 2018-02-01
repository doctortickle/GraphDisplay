package application;


import java.util.ArrayList;

import graph.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GridController {
	
	@FXML private Group group;
	@FXML private StackPane nodePane;
	@FXML private CheckBox gridLines;
	@FXML private CheckBox xAxis;
	@FXML private CheckBox yAxis;
	@FXML private CheckBox centerSelector;
	@FXML private Label coordinateLabel;
	
	private Graph graph;
	private TileEventHandler tileEventHandler;
		
	@FXML
	protected void initialize() {
		
		buildEventControllers();
		injectControllers( tileEventHandler );
		GraphFactory graphFactory = new GraphFactory(5, 5, new Triangle());
		graph = graphFactory.buildGraph();
		buildTiles("Triangle", 40, graph);
		buildConnections();
		handleCheckBoxes();
	}
	
	private void buildEventControllers() {
		tileEventHandler = new TileEventHandler();
	}	
	private void injectControllers(EventController... eventControllers) {
		for(EventController eventController : eventControllers) {
			eventController.receiveControllerInjection(this);
		}
	}
	private void buildTiles(String tileType, int radius, Graph graph) {    
	    
		for(Vertex vertex : graph.getAllVertices()) {
			Tile tile = null;
			switch(tileType) {
				case "Hexagon": tile = new HexagonTile(vertex, radius); break;
				case "Square" : tile = new SquareTile(vertex, radius); break;
				case "Triangle" : tile = new TriangleTile(vertex, radius); break;
				default : break;
			}
			//System.out.println(vertex.toString() + "= " + vertex.getGUIComponenet());
			CenterSelector centerSelector = new CenterSelector(tile, tile.getRadius());
			centerSelector.setMouseTransparent(true);
			tileEventHandler.hoverHandler(tile);
			this.group.getChildren().addAll(tile, centerSelector);
		}
		nodePane.setScaleY(-1);
	}
	private void buildConnections() {
		ArrayList<Line> connections = new ArrayList<>();
		for(Node node : group.getChildren()) {
			if(node instanceof Tile) {
				Tile tile = (Tile) node;
		    	for(Vertex vertex : graph.getAdjacentVertices(tile.getVertex())) {
		    		connections.add(new Connection(tile, (Tile) vertex.getGUIComponenet()));
		    	}
			}
		}
		group.getChildren().addAll(connections);
	}
	
	private void handleCheckBoxes() {
		handleGridLines();
		handleXAxis();
		handleYAxis();
		handleCenterSelectors();
	}	
	@FXML
	private void handleGridLines() {
		if(!gridLines.isSelected()) {
			for(Node node : group.getChildren()) {
				if(node instanceof Tile) {
					Tile tile = (Tile) node;
					tile.setStroke(Color.TRANSPARENT);
				}
			}
		}
		if(gridLines.isSelected()) {
			for(Node node : group.getChildren()) {
				if(node instanceof Tile) {
					Tile tile = (Tile) node;
					tile.setStroke(Color.BLACK);
				}
			}
		}
	}
	@FXML
	private void handleXAxis() {
		for(Node node : group.getChildren()) {
			if(node instanceof Tile) {
				Tile tile = (Tile) node;
				if(tile.getVertex().getY() == 0) {
					tileEventHandler.hoverHandler(tile);
				};
			}
		}
	}	
	@FXML
	private void handleYAxis() {
		for(Node node : group.getChildren()) {
			if(node instanceof Tile) {
				Tile tile = (Tile) node;
				if(tile.getVertex().getX() == 0) {
					tileEventHandler.hoverHandler(tile);
				};
			}
		}
	}
	@FXML
	private void handleCenterSelectors() {
		for(Node node : group.getChildren()) {
			if(node instanceof CenterSelector) {
				CenterSelector centerDot = (CenterSelector) node;
				if(centerSelector.isSelected()) {
					centerDot.setColor();
				}
				else {
					centerDot.setFill(Color.TRANSPARENT);
				}
			}
		}
	}	
	
	public Graph getGraph() {
		return this.graph;
	}
	public boolean xAxisSelected() {
		return xAxis.isSelected();
	}	
	public boolean yAxisSelected() {
		return yAxis.isSelected();
	}	
	public Label getCoordinateLabel() {
		return coordinateLabel;
	}
	
}
