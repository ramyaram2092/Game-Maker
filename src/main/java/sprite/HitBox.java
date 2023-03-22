package sprite;

import org.json.simple.JSONObject;

import constants.Constants;
import javafx.geometry.Point2D;
import saveandload.Saveable;

//Author Isaiah Sherfick
//Responsible for knowing a sprite's bounds for collision and returning relevant values
//last edited by Isaiah Sherfick
public class HitBox implements Saveable
{
	//Top left x,y value
	private Point2D location;
	
	//width,height
	private Point2D size;
	
	public HitBox()
	{
		location = new Point2D(Constants.DEFAULT_SPRITE_X,Constants.DEFAULT_SPRITE_Y);
		size = new Point2D(Constants.DEFAULT_SPRITE_WIDTH, Constants.DEFAULT_SPRITE_HEIGHT);
	}
	
	public HitBox(Point2D loc, Point2D siz)
	{
		location = loc;
		size = siz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject save() 
	{
		double x = location.getX();
		double y = location.getY();
		double width = size.getX();
		double height = size.getY();
		JSONObject saveObj = new JSONObject();
		saveObj.put("type","HitBox");
		saveObj.put("x",x);
		saveObj.put("y",y);
		saveObj.put("width",width);
		saveObj.put("height",height);
		return saveObj;
	}

	@Override
	public void load(JSONObject saveJSON) 
	{
		double x = (double)saveJSON.get("x");
		double y = (double)saveJSON.get("y");
		double width = (double)saveJSON.get("width");
		double height = (double)saveJSON.get("height");
		
		location = new Point2D(x,y);
		size = new Point2D(width,height);
	}
	
	public void setX(double x)
	{
		double y = location.getY();
		location = new Point2D(x,y);
	}

	public void setY(double y)
	{
		double x = location.getX();
		location = new Point2D(x,y);
	}

	public void setWidth(double width)
	{
		double height = size.getY();
		size = new Point2D(width,height);
	}

	public void setHeight(double height)
	{
		double width = size.getX();
		size = new Point2D(width,height);
	}
	
	public Point2D getSize()
	{
		return size;
	}

	public Point2D getLocation() 
	{
		return location;
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof HitBox)
		{
			HitBox other = (HitBox)o;
			return size.equals(other.getSize()) && location.equals(other.getLocation());
		}
		return false;
	}

	public double getX() 
	{
		return location.getX();
	}
	public double getY() 
	{
		return location.getY();
	}

	public Point2D getTopRight() 
	{
		return new Point2D(getX() + getWidth(), getY());
	}
	public Point2D getTopLeft() 
	{
		return location;
	}
	public Point2D getBottomLeft() 
	{
		return new Point2D(getX(), getY() + getHeight());
	}
	public double getHeight() 
	{
		return size.getY();
	}

	public Point2D getBottomRight() 
	{
		return new Point2D(getX() + getWidth(), getY() + getHeight());
	}

	public double getWidth() 
	{
		return size.getX();
	}

	public HitBox copy() 
	{
		Point2D locationCopy = new Point2D(location.getX(), location.getY());
		Point2D sizeCopy = new Point2D(size.getX(), size.getY());
		return new HitBox(locationCopy, sizeCopy);
	}
}
