package application;


import graph.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class GridController {
	
	@FXML private Group group;
	@FXML private StackPane nodePane;
	@FXML private CheckBox gridLines;
	@FXML private CheckBox xAxis;
	@FXML private CheckBox yAxis;
	@FXML private CheckBox centerSelector;
	@FXML private Label coordinateLabel;
	
	private TileEventHandler tileEventHandler;
		
	@FXML
	protected void initialize() {
		
		buildEventControllers();
		injectControllers( tileEventHandler );
		GraphFactory graphFactory = new GraphFactory(20, 20, new Hexagon());
		Graph<Vertex> graph = graphFactory.buildGraph();
		buildTiles("Hexagon", 10, graph);
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
	private void buildTiles(String tileType, int radius, Graph<Vertex> graph) {    
	    
		for(Vertex vertex : graph.getAllVertices()) {
			Tile tile = null;
			switch(tileType) {
				case "Hexagon": tile = new HexagonTile(vertex, radius); break;
				case "Square" : tile = new SquareTile(vertex, radius); break;
				case "Triangle" : tile = new TriangleTile(vertex, radius); break;
				default : break;
			}
			
			CenterSelector centerSelector = new CenterSelector(tile, tile.getRadius());
			tileEventHandler.hoverHandler(tile);
			this.group.getChildren().addAll(tile, centerSelector);
		}
		nodePane.setScaleY(-1);
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
