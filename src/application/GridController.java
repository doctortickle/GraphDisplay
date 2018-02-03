package application;

import java.io.PrintStream;
import java.util.ArrayList;
import graph.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GridController {
	
	@FXML private Group superGroup;
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
		
		buildEventControllers();
		injectControllers( tileEventHandler );
		GraphFactory graphFactory = new GraphFactory(3, 3, new Triangle());
		graph = graphFactory.buildGraph();
		
		buildTiles("Triangle", 20, graph);
		buildCenterSelectors();
		buildHoverHandlers();
		buildConnections();
		buildConsole();
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
		superGroup.getChildren().addAll(tileGroup);
		nodePane.setScaleY(-1);
	}
	
	private void buildCenterSelectors() {
		centerSelectorGroup = new Group();
		for(Node node : tileGroup.getChildren()) {
			Tile tile = (Tile) node;
			centerSelectorGroup.getChildren().add(new CenterSelector(tile, tile.getRadius()));
		}
		superGroup.getChildren().add(centerSelectorGroup);
	}	
	
	private void buildHoverHandlers() {
		for(Node node : tileGroup.getChildren()) {
			Tile tile = (Tile) node;
			tileEventHandler.hoverHandler(tile);
		}
	}
	
	private void buildConnections() {
		ArrayList<Line> connections = new ArrayList<>();
		for(Node node : superGroup.getChildren()) {
			if(node instanceof Tile) {
				Tile tile = (Tile) node;
		    	for(Vertex vertex : graph.getAdjacentVertices(tile.getVertex())) {
		    		Connection connection = new Connection(tile, (Tile) vertex.getGUIComponenet());
		    		tile.addConnection(connection);
		    		connections.add(connection);
		    	}
			}
		}
		superGroup.getChildren().addAll(connections);
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
		
		for(Node node: tileGroup.getChildren()) {
			Tile tile = (Tile) node;
			
			if(gridLines.isSelected()) {
				tile.setStroke(Color.BLACK);
			}
			else {
				tile.setStroke(Color.TRANSPARENT);
			}
			
		}
		
	}
	@FXML
	private void handleXAxis() {

	}	
	@FXML
	private void handleYAxis() {

	}
	@FXML
	private void handleCenterSelectors() {
		if(centerSelector.isSelected()) {
			centerSelectorGroup.setVisible(true);
		}
		else {
			centerSelectorGroup.setVisible(false);
		}
		/*for(Node node : superGroup.getChildren()) {
			if(node instanceof CenterSelector) {
				CenterSelector centerDot = (CenterSelector) node;
				if(centerSelector.isSelected()) {
					centerDot.setColor();
				}
				else {
					centerDot.setFill(Color.TRANSPARENT);
				}
			}
		}*/
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
