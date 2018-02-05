package application;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import graph.*;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GridController {
	
	@FXML private Group superGroup;
	private Group tileGroup, centerSelectorGroup, connectionsGroup;
	private HashSet<Tile> xAxisTiles, yAxisTiles, zAxisTiles;
	@FXML private StackPane nodePane;
	@FXML private CheckBox gridLines;
	@FXML private CheckBox xAxis;
	@FXML private CheckBox yAxis;
	@FXML private CheckBox centerSelector;
	@FXML private CheckBox connections;
	@FXML private Label coordinateLabel;
	@FXML private TextArea output;
	
	private Graph graph;
	private TileEventHandler tileEventHandler;
		
	@FXML
	protected void initialize() {
		
		buildGrid();		
	}
	
	
	private void buildGrid() {
		
		buildEventControllers();
		injectControllers( tileEventHandler );
		GraphFactory graphFactory = new GraphFactory( 10, 10, 0, new Triangle2D() );
		graphFactory.buildGraph();
		graph = graphFactory.getGraph();
		
		buildTiles( "Triangle", 20, graph );
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
		
		for( EventController eventController : eventControllers ) {
			
			eventController.receiveControllerInjection( this );
			
		}
		
	}
	private void buildTiles( String tileType, int radius, Graph graph ) {    
	    
		tileGroup = new Group();
		xAxisTiles = new HashSet<Tile>();
		yAxisTiles = new HashSet<Tile>();
		zAxisTiles = new HashSet<Tile>();
		
		for( Vertex vertex : graph.getAllVertices() ) {
			
			Tile tile = null;	
			switch( tileType ) {
			
				case "Hexagon": tile = new HexagonTile( vertex, radius ); break;
				case "Square" : tile = new SquareTile( vertex, radius ); break;
				case "Triangle" : tile = new TriangleTile( vertex, radius ); break;
				default : break;
				
			}
			if ( tile.getVertex().getX() == 0 ) {
				
				yAxisTiles.add( tile );
				
			}
			if( tile.getVertex().getY() == 0 ) {
				
				xAxisTiles.add( tile );
				
			}
			if( tile.getVertex().getZ() == 0 ) {
				
				zAxisTiles.add( tile );
				
			}
			tileGroup.getChildren().add(tile);
			
		}
		superGroup.getChildren().addAll( tileGroup );
		nodePane.setScaleY( -1 );
	}
	
	private void buildCenterSelectors() {
		
		centerSelectorGroup = new Group();
		for( Node node : tileGroup.getChildren() ) {
			
			Tile tile = ( Tile ) node;
			centerSelectorGroup.getChildren().add( new CenterSelector( tile, tile.getRadius() ) );
			
		}
		superGroup.getChildren().add( centerSelectorGroup );
		
	}	
	
	private void buildHoverHandlers() {
		
		for( Node node : tileGroup.getChildren() ) {
			
			Tile tile = ( Tile ) node;
			tileEventHandler.hoverHandler( tile );
		
		}
		
	}
	
	private void buildConnections() {
		
		connectionsGroup = new Group();
		
		for( Node node : tileGroup.getChildren() ) {
				
				Tile tile = ( Tile ) node;
				
		    	for( Vertex vertex : graph.getAdjacentVertices( tile.getVertex() ) ) {
		    		
		    		Connection connection = new Connection( tile, ( Tile ) vertex.getGUIComponenet() );
		    		tile.addConnection( connection );
		    		connectionsGroup.getChildren().add( connection );
		    	}		
			
		}
		superGroup.getChildren().addAll(connectionsGroup);
		
	}
	
	private void buildConsole() {
		
		Console console = new Console( output );
		PrintStream ps = new PrintStream( console, true );
		System.setOut( ps );
		System.setErr( ps );
		
	}
	
	private void handleCheckBoxes() {
		
		handleGridLines();
		handleXAxis();
		handleYAxis();
		handleCenterSelectors();
		
	}	
	
	@FXML
	private void handleGridLines() {
		
		for( Node node: tileGroup.getChildren() ) {
			
			Tile tile = ( Tile ) node;
			
			if( gridLines.isSelected() ) {
				
				tile.setStroke( Color.BLACK );
				
			}
			
			else {
				
				tile.setStroke( Color.TRANSPARENT );
				
			}
			
		}
		
	}
	
	@FXML
	private void handleXAxis() {
		
		for( Tile tile : xAxisTiles ) {
			
			if( xAxis.isSelected() ) {
				
				tile.setDefaultColor( Color.GREEN );
				
			}
			
			else if( tile.getVertex().getX() == 0 && yAxisSelected() ) {
				
				tile.setDefaultColor( Color.YELLOW );
				
			}
			
			else {
				
				tile.setDefaultColor( Color.TRANSPARENT );
				
			}
			
		}
		
	}	
	
	@FXML
	private void handleYAxis() {
		
		for( Tile tile : yAxisTiles ) {
			
			if( yAxis.isSelected() ) {
				
				tile.setDefaultColor( Color.YELLOW );
				
			}
			
			else if( tile.getVertex().getY() == 0 && xAxisSelected() ) {
				
				tile.setDefaultColor( Color.GREEN );
				
			}
			
			else {
				
				tile.setDefaultColor( Color.TRANSPARENT );
				
			}
			
		}
		
	}
	
	@FXML
	private void handleCenterSelectors() {
		
		centerSelectorGroup.setVisible( centerSelector.isSelected() );

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
	
	public boolean connectionsSelected( ) {
		
		return connections.isSelected();
		
	}
	
	public Label getCoordinateLabel() {
		
		return coordinateLabel;
		
	}
	
}
