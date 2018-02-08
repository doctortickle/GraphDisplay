package application;

import java.io.PrintStream;
import java.util.HashSet;
import graph.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

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
	@FXML private ColorPicker colorPicker;
	@FXML private ChoiceBox<String> choiceBox;
	
	private Graph graph;
	private ShapeFactory shapeFactory;
	private String shape;
	private int x, y;
	private ImagePattern grass, water, mountain, road, lava;
		
	@FXML
	protected void initialize() {
		
		init();
		
	}
	
	public void init() {
		
		getTextures();
		buildGrid();
		
	}
	
	private void getTextures() {
		
		this.grass = new ImagePattern( new Image( "resources/grass.PNG" ), 0, 0, 1, 1, true );
		this.water = new ImagePattern ( new Image( "resources/water.PNG" ), 0, 0, 1, 1, true );
		this.mountain = new ImagePattern ( new Image( "resources/mountain.PNG" ), 0, 0, 1, 1, true );
		this.road = new ImagePattern ( new Image( "resources/road.PNG" ), 0, 0, 1, 1, true );
		this.lava = new ImagePattern ( new Image( "resources/lava.PNG" ), 0, 0, 1, 1, true );
		
	}
	
	/**
	 * Builds the grid using a GraphFactory.
	 */
	private void buildGrid() {
		
		GraphFactory graphFactory = new GraphFactory( 50, 50, 0, new Hexagon2D() );
		graphFactory.buildGraph();
		graph = graphFactory.getGraph();
		
		buildVertices();
		buildCenterSelectors();
		buildMouseHandler();
		buildConnections();
		buildConsole();
		handleCheckBoxes();
		handleChoiceBox();
		
	}
	
	/**
	 * Adds all the vertices to the correct groups for easy visual manipulation.
	 */
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
			v.setDefaultColor( grass );
			v.setStroke( Color.BLACK );
			
		});
		
		superGroup.getChildren().addAll( vertexGroup );
		nodePane.setScaleY( -1 ); // Flips the Y axis so positive is up.
	}
	
	/**
	 * Builds the center selector dots for each vertex.
	 */
	private void buildCenterSelectors() {
		
		centerSelectorGroup = new Group();
		vertexGroup.getChildren().forEach( v -> 
			centerSelectorGroup.getChildren().add( new CenterSelector( ( Vertex ) v ) ) 
		);
		superGroup.getChildren().add( centerSelectorGroup );
		
	}
	
	/**
	 * Builds the mouse event handler for each vertex.
	 */
	private void buildMouseHandler() {
		
		Color hoverColor = Color.GOLD;
		
		vertexGroup.getChildren().forEach( v -> {
			
	    	v.setOnMouseEntered( e -> {
	    		
	        	( ( Vertex ) v ).setFill( hoverColor );
	        	( ( Vertex ) v ).getConnections().setVisible( connectionsSelected() );
	        	coordinateLabel.setText( v.toString() );
	        	
	        });	
			
	    	v.setOnMouseExited( e -> {
	    		
	    		( ( Vertex ) v ).setFill( ( ( Vertex ) v ).getDefaultColor() );
	    		( ( Vertex ) v ).getConnections().setVisible( false );
	        	coordinateLabel.setText( "No tile selected" );
	        	
	        });
	    	
	    	v.setOnMouseClicked( e -> {
	    		
	    		setOnMouseClicked( (Vertex) v );
	    		
	    	});    	
	    	
		});
		
	}
	
	
	private void setOnMouseClicked( Vertex v ) {
		
		
		v.setDefaultColor( getTexture() );
		v.setOpacity( 1 );
		
	}
	
	private ImagePattern getTexture() {
		
		switch( choiceBox.getValue() ) {
		
			case "Grass" : return grass;
			case "Water" : return water;
			case "Mountain" : return mountain;
			case "Road" : return road;
			case "Lava" : return lava;
			default : return grass;
			
		}
		
	}
	
	/**
	 * Illustrates the edges between vertices.
	 */
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
	
	/**
	 * Builds the console functionality so that System.out prints to the in-game console.
	 */
	private void buildConsole() {
		
		Console console = new Console( output );
		PrintStream ps = new PrintStream( console, true );
		System.setOut( ps );
		System.setErr( ps );
		
	}
	
	/**
	 * Runs all the checkbox functionality methods.
	 */
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
			
			if( xAxis.isSelected() ) { v.setStroke( Color.RED ); }
			else if ( v.getX() == 0 && yAxisSelected() ) { v.setStroke( Color.RED ); }
			else if ( gridLines.isSelected() ) { v.setStroke( Color.BLACK ); }
			else { v.setStroke( Color.TRANSPARENT ); }
			
		});

	}	
	
	@FXML
	private void handleYAxis() {
		
		yAxisVertices.forEach( v -> {
			
			if( yAxis.isSelected() ) { v.setStroke( Color.RED ); }
			else if ( v.getY() == 0 && xAxisSelected() ) { v.setStroke( Color.RED ); }
			else if ( gridLines.isSelected() ) { v.setStroke( Color.BLACK ); }
			else { v.setStroke( Color.TRANSPARENT ); }
			
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
	
	
	private void handleChoiceBox() {
		
		choiceBox.setItems( FXCollections.observableArrayList(
				
				"Grass",
				"Water",
				"Mountain",
				"Road",
				"Lava"
				
				) );
		
		choiceBox.setValue("Grass");
		
	}

	
	
}
