package sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.FlowPane;

//Drawable interface
public interface Drawable 
{
	//Tell the drawable to draw itself on g
	public void draw(GraphicsContext g);
}
