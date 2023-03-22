package behaviors.event;

import org.json.simple.JSONObject;

import input.KeyPolling;
import javafx.scene.input.KeyCode;
import sprite.Sprite;

public class FroggerMovementBehavior implements EventBehavior{
	
	public FroggerMovementBehavior() {
		//Does nothing atm
	}
	
	@Override
	public JSONObject save() {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		json.put("type","FroggerMovementBehavior");
		return json;
	}

	@Override
	public void load(JSONObject saveJSON) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMousePress(Sprite sprite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameStart(Sprite sprite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseMove(Sprite sprite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPress(Sprite sprite, KeyCode keyCode) 
	{
		switch (keyCode)
		{
			case W:
				sprite.setY(sprite.getY() - sprite.getHitBox().getHeight());
				break;
			case UP:
				sprite.setY(sprite.getY() - sprite.getHitBox().getHeight());
				break;
			case A:
				sprite.setX(sprite.getX() - sprite.getHitBox().getWidth());
				break;
			case LEFT:
				sprite.setX(sprite.getX() - sprite.getHitBox().getWidth());
				break;
			case D:
				sprite.setX(sprite.getX() + sprite.getHitBox().getWidth());
				break;
			case RIGHT:
				sprite.setX(sprite.getX() + sprite.getHitBox().getWidth());
				break;
		}
	}

	@Override
	public void onGameTick(Sprite sprite) {
	}

	@Override
	public void onLevelIncrease(Sprite sprite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EventBehavior copy() {
		// TODO Auto-generated method stub
		return new FroggerMovementBehavior();
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof FroggerMovementBehavior)
		{
			FroggerMovementBehavior m = (FroggerMovementBehavior)o;
			return true;
		}
		return false;
	}
	
	public String toString() {
		return "Move on Arrow Keys";
	}
	
		

}
