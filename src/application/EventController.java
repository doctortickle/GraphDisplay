package application;

public abstract class EventController {
	
	protected GridController gridController;
	
	EventController() {
		
	}
	
	public void receiveControllerInjection(GridController gridController) {
		this.gridController = gridController;
	}
	
}
