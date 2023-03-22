package behaviors.collision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONObject;

import saveandload.Saveable;
import sprite.Sprite;

public class CustomCollisionMap implements Saveable
{
	
	private HashMap<Integer, CollisionBehavior> collisionMap;
	private CollisionBehavior defaultCollisionBehavior;
	
	public CustomCollisionMap()
	{
		collisionMap = new HashMap<>();
		defaultCollisionBehavior = new DoNothingCollisionBehavior();
	}
	
	public int size()
	{
		return collisionMap.size();
	}
	
	public void setDefaultCollisionBehavior(CollisionBehavior newDefault)
	{
		defaultCollisionBehavior = newDefault;
	}
	
	public CollisionBehavior getdDefaultCollisionBehaviour()
	{
		return defaultCollisionBehavior;
	}
	
	public void put(int spriteId, CollisionBehavior customCollision)
	{
		collisionMap.put(spriteId, customCollision);
	}
	
	public CollisionBehavior get(int spriteId)
	{
		CollisionBehavior result = collisionMap.get(spriteId);
		if (result == null)
		{
			return defaultCollisionBehavior;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public JSONObject save() 
	{
		JSONObject json = new JSONObject();
		json.put("type", "CustomCollisionMap");
		json.put("size", size());
		json.put("defaultCollisionBehavior", defaultCollisionBehavior.save());
		
		Integer i  = 0;
		for (Entry<Integer, CollisionBehavior> e : collisionMap.entrySet())
		{
			CustomCollisionPair currentPair = new CustomCollisionPair(e.getKey(), e.getValue());
			json.put(i.toString(), currentPair.save());
			i++;
		}
		
		return json;
	}

	public void load(JSONObject saveJSON) 
	{
		collisionMap.clear();
		int size = 0;
		try 
		{
			size = ((Long)saveJSON.get("size")).intValue();
		}
		catch(ClassCastException e)
		{
			size = (Integer)saveJSON.get("size");
		}
		defaultCollisionBehavior = CollisionBehaviorLoader.load((JSONObject)saveJSON.get("defaultCollisionBehavior"));

		for (Integer i = 0; i  < size; i++)
		{
			CustomCollisionPair currentPair = new CustomCollisionPair();
			currentPair.load((JSONObject)saveJSON.get(i.toString()));
			collisionMap.put(currentPair.getSpriteId(), currentPair.getCollisionBehavior());
		}
	}

	//Will be the method actually called by Sprite on a collision
	public void collide(Sprite collidee, int colliderId) 
	{
		boolean containsCustomCollision = false;
		//Loop through the ids stored in the map
		for (Integer key : collisionMap.keySet())
		{
			//If we have an entry for the thing we're colliding with
			if (key == colliderId)
			{
				//Call that entry's collide method
				collisionMap.get(key).collide(collidee,colliderId);
				containsCustomCollision = true;
			}
		}
		if (!containsCustomCollision)
		{
			defaultCollisionBehavior.collide(collidee,colliderId);
		}
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof CustomCollisionMap)
		{
			CustomCollisionMap other = (CustomCollisionMap)o;
			if (!(defaultCollisionBehavior.equals(other.getDefaultCollisionBehavior())))
			{
//				System.out.println("Defaults aren't equal.");
//				System.out.println(defaultCollisionBehavior);
//				System.out.println(other.getDefaultCollisionBehavior());
				return false;
			}
			for (Integer spriteId : collisionMap.keySet())
			{
				if (!(get(spriteId).equals(other.get(spriteId))))
				{
//					System.out.println(String.format("Collision for spriteId%d doesn't match",spriteId));
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public CollisionBehavior getDefaultCollisionBehavior() 
	{
		return defaultCollisionBehavior;
	}

	public CustomCollisionMap copy() 
	{
		CustomCollisionMap copy = new CustomCollisionMap();
		copy.setDefaultCollisionBehavior(defaultCollisionBehavior);
		for (Entry<Integer, CollisionBehavior> e : collisionMap.entrySet())
		{
			copy.put(e.getKey(), e.getValue());
		}
		return copy;
	}

	public ArrayList<CustomCollisionPair> getCustomCollisionPairs() 
	{
		ArrayList<CustomCollisionPair> customCollisionPairs = new ArrayList<>();
		for (Entry<Integer, CollisionBehavior> e : collisionMap.entrySet())
		{
			CustomCollisionPair currentPair = new CustomCollisionPair(e.getKey(), e.getValue());
			customCollisionPairs.add(currentPair);
		}
		return customCollisionPairs;
	}
}
