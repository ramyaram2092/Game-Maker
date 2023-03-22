package saveandload;

import org.json.simple.JSONObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface SaveableShape extends Saveable
{
	public Color getColor();
	public void setColor(Color c);
	public void draw(GraphicsContext g);
	public double getX();
	public double getY();
	public void setX(double newX);
	public void setY(double newY);
	public double getWidth();
	public double getHeight();
	public void setWidth(double w);
	public void setHeight(double h);
	public SaveableShape copy();
}
