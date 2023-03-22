//Author Isaiah Sherfick
//Created 8 October 2021
//SpriteManager is a glorified HashMap which maps spriteIds to the corresponding sprite
//It is responsible for tracking and correctly assigning unique spriteIDs to each sprite
//it guarantees that each sprite has a unique id which it doesn't have to concern itself with
//and also that relationships between specific sprites will be preserved on save/load
package sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import constants.Constants;

public class SpriteManager 
{
	private int currentHighestSpriteId;
	private HashMap<Integer,Sprite> spriteMap;
	private int size;
	
	//default constructor
	public SpriteManager()
	{
		spriteMap = new HashMap<>();
		currentHighestSpriteId = Constants.SPRITE_MANAGER_DEFAULT_ID;
		size = 0;
	}
	
	public int addCopy(Sprite sprite)
	{
		//We don't want to insert null sprites
		if (sprite.getSpriteId() == Constants.NULL_SPRITE_ID)
		{
			return Constants.NULL_SPRITE_ID;
		}
		
		currentHighestSpriteId++;
		sprite.setSpriteId(currentHighestSpriteId);
		currentHighestSpriteId = sprite.getSpriteId();
		spriteMap.put(sprite.getSpriteId(), sprite);
		size++;
		return sprite.getSpriteId();
	}
	
	//Add a new sprite to the spritemanager
	public int add(Sprite sprite)
	{
		//We don't want to insert null sprites
		if (sprite.getSpriteId() == Constants.NULL_SPRITE_ID)
		{
			return Constants.NULL_SPRITE_ID;
		}
		
		if (sprite.getSpriteId() < currentHighestSpriteId)
		{
			currentHighestSpriteId++;
			sprite.setSpriteId(currentHighestSpriteId);
		}
		else
		{
			currentHighestSpriteId = sprite.getSpriteId();
		}
		spriteMap.put(sprite.getSpriteId(), sprite);
		size++;
		return sprite.getSpriteId();
	}
	
	//Remove a sprite from the spritemanager by its spriteid
	public void remove(int spriteId)
	{
		Sprite removed = spriteMap.remove(spriteId);
		if (removed != null)
			size--;
	}

	//Get a copy of the sprite at spriteId
	public Sprite get(int spriteId)
	{
		Sprite sprite = spriteMap.get(spriteId);
		if (sprite == null)
		{
			sprite = new NullSprite();
		}
		return sprite.copy();
	}
	
	public boolean containsSprite(int spriteId)
	{
		return spriteMap.containsKey(spriteId);
	}
	
	//Get the size of the sprite master
	public int getSize()
	{
		return size;
	}
	
	//Returns all the sprites as an arrayList
	public ArrayList<Sprite> getSpriteList()
	{
		ArrayList <Sprite> spriteList = new ArrayList<>();
		for (int spriteId : spriteMap.keySet())
		{
			spriteList.add(spriteMap.get(spriteId).copy());
		}
		return spriteList;
	}
	
	public ArrayList<Sprite> getDirectReferenceSpriteList()
	{
		ArrayList <Sprite> spriteList = new ArrayList<>();
		for (int spriteId : spriteMap.keySet())
		{
			spriteList.add(spriteMap.get(spriteId));
		}
		return spriteList;
	}
	
	public Set<Integer> getSpriteIds()
	{
		return spriteMap.keySet();
	}

	//Overwrite a sprite already in the sprite manager
	//takes in a sprite which has been modified by the controller
	//since the frontend only ever receives copies of sprites
	public void modifySprite(Sprite sprite) 
	{
		if (containsSprite(sprite.getSpriteId()))
		{
			spriteMap.put(sprite.getSpriteId(), sprite);
		}
	}

	public void addAll(ArrayList<Sprite> spriteList) 
	{
		for (Sprite s : spriteList)
		{
			add(s);
		}
	}

	public void onGameTick() 
	{
		for (int spriteId : spriteMap.keySet())
		{
			spriteMap.get(spriteId).onGameTick();
		}
	}
}
