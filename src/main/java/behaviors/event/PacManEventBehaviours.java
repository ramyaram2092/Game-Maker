package behaviors.event;

import org.json.simple.JSONObject;

import constants.Constants;
import input.KeyPolling;
import javafx.scene.input.KeyCode;

import sprite.Sprite;

public class PacManEventBehaviours implements EventBehavior {
	 private MovementEventBehavior movement=new MoveOnGameTickBehavior();
	@Override
	public JSONObject save() {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		json.put("type","PacManEventBehaviours");
		json.put("movement",movement);
		return json;
	}

	
	public PacManEventBehaviours() {
		movement = new MoveOnGameTickBehavior();
		movement.setXVelocity(0);
		movement.setYVelocity(Constants.MOVE_UP);
	}
	
	public PacManEventBehaviours(MovementEventBehavior m) {
		this.movement = m;
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
	public void onKeyPress(Sprite sprite,  KeyCode keyCode)
	{
	        
	 }
	
	public void onKeyPress() {
		  if (KeyPolling.shared.isDown(KeyCode.UP))
	      {
			  movement.setYVelocity(Constants.MOVE_UP); //add  to constants file
			  movement.setXVelocity(0);
	           
	      }
		  else if (KeyPolling.shared.isDown(KeyCode.DOWN)) //add  to constants file
	      {
			  movement.setYVelocity(Constants.MOVE_DOWN);//add  to constants file
	          movement.setXVelocity(0);
	      }
		  else if (KeyPolling.shared.isDown(KeyCode.RIGHT))
	      {
			  movement.setXVelocity(Constants.MOVE_RIGHT);//add  to constants file
			  movement.setYVelocity(0);
	           
	      }
		  else if (KeyPolling.shared.isDown(KeyCode.LEFT))
	      {
			  movement.setXVelocity(Constants.MOVE_LEFT);  //add  to constants file
			  movement.setYVelocity(0);
	      }
	}
	


	@Override
	public void onGameTick(Sprite sprite) {
		onKeyPress();
		movement.onGameTick(sprite);
	}

	@Override
	public void onLevelIncrease(Sprite sprite) {
		// TODO Auto-generated method stub

	}

	@Override
	public EventBehavior copy() {
		// TODO Auto-generated method stub
		return new PacManEventBehaviours(movement);
	}

	public String toString() {
		return "Continuous Movement Changed on KeyPress";
	}
	

}
