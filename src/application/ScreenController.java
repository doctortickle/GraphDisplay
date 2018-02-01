package application;


import graph.Graph;
import graph.GraphFactory;
import graph.Square;
import graph.Vertex;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ScreenController {
	@FXML
	private Group group;
	@FXML
	private StackPane nodePane;
	@FXML
	private CheckBox gridLines;
	@FXML
	private CheckBox xAxis;
	@FXML
	private CheckBox yAxis;
	private TileEventHandler tileEventHandler;
		
	@FXML
	protected void initialize() {
		
		tileEventHandler = new TileEventHandler();
		GraphFactory graphFactory = new GraphFactory(20, 20, new Square());
		Graph<Vertex> graph = graphFactory.buildGraph();
		buildTiles("Square", 20, graph);
		handleGridLines();
		handleXAxis();
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
		if(!xAxis.isSelected()) {
			for(Node node : group.getChildren()) {
				if(node instanceof Tile) {
					Tile tile = (Tile) node;
					tile.setFill(Color.TRANSPARENT);
				}
			}
		}
		if(xAxis.isSelected()) {
			for(Node node : group.getChildren()) {
				if(node instanceof Tile) {
					Tile tile = (Tile) node;
					tileEventHandler.hoverHandler(tile);
				}
			}
		}
	}
	
	@FXML
	private void handleYAxis() {
		if(!yAxis.isSelected()) {
			group.setVisible(false);
		}
		if(yAxis.isSelected()) {
			group.setVisible(true);
		}
	}
	
}
