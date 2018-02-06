package application;

public abstract class EventController {
	
	protected GraphController gridController;
	
	EventController() {
		
	}
	
	public void receiveControllerInjection(GraphController gridController) {
		this.gridController = gridController;
	}
	
}
