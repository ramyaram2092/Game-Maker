package behaviors.collision;

import org.json.simple.JSONObject;

import constants.Constants;
import saveandload.Saveable;

public class CustomCollisionPair implements Saveable
{
	private Integer spriteId;
	private CollisionBehavior collisionBehavior;
	
	public CustomCollisionPair(int sid, CollisionBehavior cb)
	{
		spriteId = sid;
		collisionBehavior = cb;
	}
	
	public CustomCollisionPair() 
	{
		spriteId = Constants.DEFAULT_SPRITE_ID;
		collisionBehavior = new DoNothingCollisionBehavior();
	}
	
	public int getSpriteId()
	{
		return spriteId;
	}
	
	public CollisionBehavior getCollisionBehavior()
	{
		return collisionBehavior;
	}

	@SuppressWarnings("unchecked")
	public JSONObject save()
	{
		JSONObject json = new JSONObject();
		json.put("type","CustomCollisionPair");
		json.put("spriteId",spriteId);
		json.put("collisionBehavior",collisionBehavior.save());
		return json;
	}

	public void load(JSONObject json)
	{
		try
		{
			spriteId = ((Long)json.get("spriteId")).intValue();
		}
		catch (ClassCastException e)
		{
			spriteId = (Integer)json.get("spriteId");
		}
		collisionBehavior = CollisionBehaviorLoader.load((JSONObject)json.get("collisionBehavior"));
	}
}
