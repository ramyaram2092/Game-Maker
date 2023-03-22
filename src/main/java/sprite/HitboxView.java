package sprite;

import org.json.simple.JSONObject;

import constants.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import saveandload.SaveableRectangle;

public class HitboxView extends SaveableRectangle{

	private Rectangle rect;
	private double x,y,width,height;
	private Color color;
	
	public HitboxView()
	{
		x = Constants.DEFAULT_SPRITE_X;
		y = Constants.DEFAULT_SPRITE_Y;
		width = Constants.DEFAULT_SPRITE_WIDTH;
		height = Constants.DEFAULT_SPRITE_HEIGHT;
		rect = new Rectangle(x,y,width,height);
		color = Color.BLUE;
	}
	

	@Override
	public void draw(GraphicsContext g) 
	{
		g.setFill(color);
		g.setStroke(Color.BLACK);
		g.fillRect(x,y,width,height);
		
	}

	@Override
	public void setWidth(double w) 
	{
		width = w;
		rect.setWidth(width);
	}

	@Override
	public void setHeight(double h) 
	{
		height = h;
		rect.setHeight(h);
	}
	
	@Override
	public void setX(double x) {
		this.x = x;
		rect.setX(x);
	}
	
	public void setY(double y) {
		this.y = y;
		rect.setY(y);
	}
}
