package behaviors.event;

import org.json.simple.JSONObject;

import constants.Constants;
import javafx.scene.input.KeyCode;
import sprite.Sprite;

public class MoveOnGameTickBehavior implements MovementEventBehavior
{
	private int xVelocity;
	private int yVelocity;
	
	public MoveOnGameTickBehavior()
	{
		xVelocity = Constants.DEFAULT_SPRITE_VELOCITY_X;
		yVelocity = Constants.DEFAULT_SPRITE_VELOCITY_Y;
	}
	
	public MoveOnGameTickBehavior(int dx, int dy)
	{
		xVelocity = dx;
		yVelocity = dy;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject save() 
	{
		JSONObject json = new JSONObject();
		json.put("type","MoveOnGameTickBehavior");
		json.put("xVelocity",xVelocity);
		json.put("yVelocity",yVelocity);
		return json;
	}

	@Override
	public void load(JSONObject saveJSON) 
	{
		try
		{
			xVelocity = (int)saveJSON.get("xVelocity");
			yVelocity = (int)saveJSON.get("yVelocity");
		}
		catch (ClassCastException c)
		{
			xVelocity = ((Long)saveJSON.get("xVelocity")).intValue();
			yVelocity = ((Long)saveJSON.get("yVelocity")).intValue();
		}
	}

	@Override
	public void onMousePress(Sprite sprite) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameStart(Sprite sprite) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseMove(Sprite sprite) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override

	public void onKeyPress(Sprite sprite, KeyCode keyCode) 

	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameTick(Sprite sprite) 
	{
		int x = (int)sprite.getX();
		int y = (int)sprite.getY();
		sprite.setX(x + xVelocity);
		sprite.setY(y + yVelocity);
	}

	@Override
	public void onLevelIncrease(Sprite sprite) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public EventBehavior copy() 
	{
		return new MoveOnGameTickBehavior(xVelocity, yVelocity);
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof MoveOnGameTickBehavior)
		{
			MoveOnGameTickBehavior m = (MoveOnGameTickBehavior)o;
			return xVelocity == m.getXVelocity() && yVelocity == m.getYVelocity();
		}
		return false;
	}

	public int getYVelocity() 
	{
		return yVelocity;
	}

	public int getXVelocity() 
	{
		return xVelocity;
	}
	
	public void setYVelocity(int y)
	{
		yVelocity = y;
	}
	public void setXVelocity(int x)
	{
		xVelocity = x;
	}
	
	public String toString() {
		return "Move Continuously";
	}
}
