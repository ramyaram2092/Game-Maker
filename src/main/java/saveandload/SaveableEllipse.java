package saveandload;

import org.json.simple.JSONObject;

import constants.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import sprite.Drawable;
import sprite.Sprite;

public class SaveableEllipse implements SaveableShape, Drawable
{
	private Ellipse ellipse;
	private double x,y,width,height;
	private Color color;
	
	public SaveableEllipse()
	{
		x = Constants.DEFAULT_SPRITE_X;
		y = Constants.DEFAULT_SPRITE_Y;
		width = Constants.DEFAULT_SPRITE_WIDTH;
		height = Constants.DEFAULT_SPRITE_HEIGHT;
		ellipse = new Ellipse(x,y,width,height);
		color = Color.web("#FF0000");
	}
	
	public SaveableEllipse(double x, double y,  double width,  double height,  String colorString)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = Color.web(colorString);
		ellipse = new Ellipse();
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject save() 
	{
		JSONObject json = new JSONObject();
		json.put("type","SaveableEllipse");
		json.put("x",x);
		json.put("y",y);
		json.put("width",width);
		json.put("height",height);
		json.put("color",color.toString());
		return json;
	}

	@Override
	public void load(JSONObject saveJSON) 
	{
		x = (double)saveJSON.get("x");
		y = (double)saveJSON.get("y");
		width = (double)saveJSON.get("width");
		height = (double)saveJSON.get("height");
		ellipse = new Ellipse(x,y,width,height);
		color = Color.web((String)saveJSON.get("color"));
	}

	@Override
	public Color getColor() 
	{
		return color;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	

	@Override
	public void draw(GraphicsContext g) 
	{
		g.setFill(color);
		g.fillOval(x, y, width, height);
	}

	@Override
	public double getWidth() 
	{
		return width;
	}

	@Override
	public double getHeight() 
	{
		return height;
	}

	@Override
	public void setWidth(double w) 
	{
		width = w;
		ellipse.setRadiusX(width);
	}

	@Override
	public void setHeight(double h) 
	{
		height = h;
		ellipse.setRadiusY(height);
	}


	@Override
	public double getX() 
	{
		return x;
	}

	@Override
	public double getY() 
	{
		return y;
	}

	@Override
	public void setX(double newX) 
	{
		x = newX;
		ellipse.setCenterX(x);
	}

	@Override
	public void setY(double newY) 
	{
		y = newY;
		ellipse.setCenterY(y);
	}

	public boolean equals(Object o)
	{
		if (o instanceof SaveableEllipse)
		{
			SaveableEllipse r = (SaveableEllipse)o;
			return x == r.getX() && y == r.getY() && width == r.getWidth() && height == r.getHeight();
		}
		return false;
	}

	public String toString() {
		return "Ellipse";
	}
	
	public SaveableShape copy()
	{
		return new SaveableEllipse(x,y,width,height,color.toString());
	}
	
}
