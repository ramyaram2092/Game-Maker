package input;

import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

//TODO
//I can't say I'm an expert in event listeners
//but I think this is 99% useable
//Isaiah
public class ClickPolling {
	
	public static ClickPolling shared = new ClickPolling();
    private static Scene scene;
    private final static HashMap<Double, Double> mouseClickCoordinates = new HashMap<>();
    
    private ClickPolling() {}
    
    public void pollScene(Scene scene) {
        clearKeys();
        removeCurrentKeyHandlers();
        setScene(scene);
    }
    
    private void clearKeys() {
    	mouseClickCoordinates.clear();
    }
    
    private void removeCurrentKeyHandlers() {
        if(scene != null) {
			scene.setOnMouseClicked(null);
		}
	}
    
    private static void setScene(Scene scene) {
    	ClickPolling.scene = scene;
    	ClickPolling.scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseClickCoordinates.put(event.getSceneX(), event.getSceneY());
			}
    	});
    	
	}
    
}
