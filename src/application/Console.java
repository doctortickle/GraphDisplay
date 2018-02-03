package application;

import java.io.IOException;
import java.io.OutputStream;

import javafx.application.Platform;
import javafx.scene.control.ListView;


public class Console extends OutputStream {
	
    private ListView<String> output;

    public Console(ListView<String> output)  {
        this.output = output;
    }
    
    private void addText(String str) {
    	Platform.runLater( () -> output.getItems().add(str) );
    }

	@Override
	public void write(int b) throws IOException {
		addText(String.valueOf((char) b));		
	}
	
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
    	addText(new String(b, off, len));
    }

    @Override
    public void write(byte[] b) throws IOException {
    	write(b, 0, b.length);
    }

}
