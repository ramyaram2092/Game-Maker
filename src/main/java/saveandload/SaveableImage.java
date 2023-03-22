package saveandload;

import org.json.simple.JSONObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import sprite.Drawable;
import sprite.Sprite;

public class SaveableImage implements Saveable, Drawable
{
	private String path;
	private double x,y,width,height;
	private Image image;
	
	public SaveableImage()
	{
		path = "";
	}
	
	public SaveableImage(double x, double y, double width, double height, String path)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.path = path;
	}


	public void setX(double x) 
	{
		this.x = x;
	}
	
	public double getX()
	{
		return x;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getWidth()
	{
		return width;
	}
	
	public double getHeight()
	{
		return height;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject save()
	{
		JSONObject json = new JSONObject();
		json.put("type","SaveableImage");
		json.put("x",x);
		json.put("y",y);
		json.put("width",width);
		json.put("height",height);
		json.put("path",path);
		
		return json;
	}
	
	public void load(JSONObject json)
	{
		
		x = (double)json.get("x");
		y = (double)json.get("y");
		width = (double)json.get("width");
		height = (double)json.get("height");
		path = (String)json.get("path");
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof SaveableImage)
		{
			SaveableImage i = (SaveableImage)o;
			return i.getX() == x && i.getY() == y && i.getWidth() == width && i.getHeight() == height;
		}
		return false;
	}

	public void setWidth(double width) 
	{
		this.width = width;
	}

	public void setHeight(double height) 
	{
		this.height = height;
	}
	public void setPath(String path) 
	{
		this.path = path;
	}
	
	public void loadImage()
	{
		image = new Image(path);
	}

	@Override
	public void draw(GraphicsContext g) 
	{
		if (image == null)
		{
			loadImage();
		}
		g.drawImage(image, x, y, width, height);
	}

	public SaveableImage copy() 
	{
		return new SaveableImage(x,y,width,height,path);
	}
}
