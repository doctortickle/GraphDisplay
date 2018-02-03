package application;

import java.io.PrintStream;
import graph.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class GridController {
	
	@FXML 	private Group superGroup;
			private Group tileGroup, centerSelectorGroup;
	@FXML private StackPane nodePane;
	@FXML private CheckBox gridLines;
	@FXML private CheckBox xAxis;
	@FXML private CheckBox yAxis;
	@FXML private CheckBox centerSelector;
	@FXML private Label coordinateLabel;
	@FXML private ListView<String> output;
	
	private Graph graph;
	private TileEventHandler tileEventHandler;
		
	@FXML
	protected void initialize() {
		
		buildEventControllers(); // build all event controllers for this scene.
		injectControllers( tileEventHandler ); // inject this scene controller into each event controller
		GraphFactory graphFactory = new GraphFactory(20, 20, new Hexagon()); // prepare the factory for graph construction
		graph = graphFactory.buildGraph(); //build the edge node graph
		buildTiles("Hexagon", 20, graph); // build each individual tile
		buildCenterSelectors(); // build centerSelectors;
		buildConnections(); // build the connections
		buildHoverHandler();
		buildConsole(); // build the console 
		handleCheckBoxes(); // handle the check box actions
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
	    
		tileGroup = new Group();
		for(Vertex vertex : graph.getAllVertices()) {
			Tile tile = null;
			switch(tileType) {
				case "Hexagon": tile = new HexagonTile(vertex, radius); break;
				case "Square" : tile = new SquareTile(vertex, radius); break;
				case "Triangle" : tile = new TriangleTile(vertex, radius); break;
				default : break;
			}
			tileGroup.getChildren().add(tile);			
		}
		superGroup.getChildren().add(tileGroup);
		nodePane.setScaleY(-1);
	}
	
	private void buildCenterSelectors() {	
		centerSelectorGroup = new Group();
		for(Node node : tileGroup.getChildren()) {
			Tile tile = (Tile) node;
			CenterSelector centerSelector = new CenterSelector(tile, tile.getRadius());
			centerSelectorGroup.getChildren().add(centerSelector);
		}
		superGroup.getChildren().add(centerSelectorGroup);
	}
	private void buildConnections() {
		for(Node node : tileGroup.getChildren()) {
			Tile tile = (Tile) node;
	    	tile.buildConnections();
	    	superGroup.getChildren().add(tile.getConnections());
		}	
	}
	private void buildHoverHandler() {
		tileEventHandler = new TileEventHandler();
		for(Node node : tileGroup.getChildren()) {
			Tile tile = (Tile) node;
	    	tileEventHandler.hoverHandler(tile);
		}	
	}
	
	private void buildConsole() {
		Console console = new Console(output);
		PrintStream ps = new PrintStream(console, true);
		System.setOut(ps);
		System.setErr(ps);
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
			for(Node node : superGroup.getChildren()) {
				if(node instanceof Tile) {
					Tile tile = (Tile) node;
					tile.setStroke(Color.TRANSPARENT);
				}
			}
		}
		if(gridLines.isSelected()) {
			for(Node node : superGroup.getChildren()) {
				if(node instanceof Tile) {
					Tile tile = (Tile) node;
					tile.setStroke(Color.BLACK);
				}
			}
		}
	}
	@FXML
	private void handleXAxis() {
		for(Node node : superGroup.getChildren()) {
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
		for(Node node : superGroup.getChildren()) {
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
		for(Node node : superGroup.getChildren()) {
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
