package application;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Vector;

import graph.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


public class GraphController {
	
	@FXML private Group superGroup;
	private Group vertexGroup, centerSelectorGroup, connectionsGroup;
	private HashSet<Vertex> xAxisVertices, yAxisVertices, zAxisVertices;
	@FXML private StackPane nodePane;
	@FXML private CheckBox gridLines;
	@FXML private CheckBox xAxis;
	@FXML private CheckBox yAxis;
	@FXML private CheckBox centerSelector;
	@FXML private CheckBox connections;
	@FXML private Label coordinateLabel;
	@FXML private TextArea output;
	
	private Graph graph;
	private ShapeFactory shapeFactory;
	private String shape;
	private int x, y;
		
	@FXML
	protected void initialize() {
		
		init();
		
	}
	
	public void init() {
		
		buildGrid();
		
	}
	
	
	private void buildGrid() {
		
		GraphFactory graphFactory = new GraphFactory( 10, 10, 0, new Hexagon2D() );
		graphFactory.buildGraph();
		graph = graphFactory.getGraph();
		
		buildVertices();
		buildCenterSelectors();
		buildHoverHandlers();
		buildConnections();
		buildConsole();
		handleCheckBoxes();
		
	}
	
	private void buildVertices() {    
	    
		vertexGroup = new Group();
		xAxisVertices = new HashSet< Vertex >();
		yAxisVertices = new HashSet< Vertex >();
		zAxisVertices = new HashSet< Vertex >();
		
		graph.getAllVertices().forEach( v -> {
		
			if(v.getX() == 0) { yAxisVertices.add( v ); }
			if(v.getY() == 0) { xAxisVertices.add( v ); }
			if(v.getZ() == 0) { zAxisVertices.add( v ); }
			vertexGroup.getChildren().add( v );
			
		});
		
		superGroup.getChildren().addAll( vertexGroup );
		nodePane.setScaleY( -1 );
	}
	
	
	private void buildCenterSelectors() {
		
		centerSelectorGroup = new Group();
		vertexGroup.getChildren().forEach( v -> 
			centerSelectorGroup.getChildren().add( new CenterSelector( ( Vertex ) v ) ) 
		);
		superGroup.getChildren().add( centerSelectorGroup );
		
	}	
	
	private void buildHoverHandlers() {
		
		Color hoverColor = Color.GOLD;
		
		vertexGroup.getChildren().forEach( v -> {
			
	    	v.setOnMouseEntered(e -> {
	    		
	        	( ( Vertex ) v ).setFill( hoverColor );
	        	( ( Vertex ) v ).getConnections().setVisible( connectionsSelected() );
	        	coordinateLabel.setText( v.toString() ); 
	        	
	        });	
			
	    	v.setOnMouseExited(e -> {
	    		
	    		( ( Vertex ) v ).setFill( ( ( Vertex ) v ).getDefaultColor() );
	    		( ( Vertex ) v ).getConnections().setVisible( false );
	        	coordinateLabel.setText( "No tile selected" );
	        	
	        });   
	    	
		});
		
	}
	
	
	private void buildConnections() {
		
		connectionsGroup = new Group();
		
		vertexGroup.getChildren().forEach( v -> { 
			
			Group connections = new Group();
			
			graph.getAdjacentVertices( (Vertex) v ).forEach( c -> {
				
				connections.getChildren().add( new Connection ( ( Vertex ) v, c ) );
				
			});
			
			connections.setVisible( false );
			( ( Vertex ) v ).addConnections( connections );
			connectionsGroup.getChildren().add( connections );
			
		});
		
		superGroup.getChildren().addAll( connectionsGroup );
		
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
		
		vertexGroup.getChildren().forEach( v -> {
			
			if( gridLines.isSelected() ) { ( ( Vertex ) v).setStroke( Color.BLACK ); }
			else { ( ( Vertex ) v).setStroke( Color.TRANSPARENT ); }
			
		});
		
	}
	
	@FXML
	private void handleXAxis() {
		
		xAxisVertices.forEach( v -> {
			
			if( xAxis.isSelected() ) { v.setDefaultColor( Color.GREEN ); }
			else if ( v.getX() == 0 && yAxisSelected() ) { v.setDefaultColor( Color.YELLOW ); }
			else { v.setDefaultColor( Color.TRANSPARENT ); }
			
		});

	}	
	
	@FXML
	private void handleYAxis() {
		
		yAxisVertices.forEach( v -> {
			
			if( yAxis.isSelected() ) { v.setDefaultColor( Color.YELLOW ); }
			else if ( v.getY() == 0 && xAxisSelected() ) { v.setDefaultColor( Color.GREEN ); }
			else { v.setDefaultColor( Color.TRANSPARENT ); }
			
		});

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
