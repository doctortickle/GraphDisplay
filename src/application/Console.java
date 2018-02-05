package application;

import java.io.IOException;
import java.io.OutputStream;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class Console extends OutputStream {
	
    private TextArea output;

    public Console(TextArea output)  {
        this.output = output;
        output.setEditable(false);
    }
    
    private void addText(String str) {
    	Platform.runLater( () -> output.appendText(str) );
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
