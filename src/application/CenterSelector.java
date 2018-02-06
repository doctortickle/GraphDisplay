package application;
import graph.Vertex;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CenterSelector extends Circle {
	
	
	public CenterSelector( Vertex v ) {
		
		setRadius( 5 );
		setLayoutX( v.getLayoutX() );
		setLayoutY( v.getLayoutY() );
		setMouseTransparent( true );
		setColor( v );
		
	}
	
	private void setColor( Vertex v ) {		
		
		if( ( v.getX()&1 ) == 0 ) {
			
			this.setFill( Color.RED );
			
		}
		
		else {
			
			this.setFill( Color.BLUE );
			
		}
		
	}
	
}
