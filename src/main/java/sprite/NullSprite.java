package sprite;

import org.json.simple.JSONObject;

import constants.Constants;

public class NullSprite extends Sprite
{
	public NullSprite()
	{
		super();
		hitBox.setWidth(0);
		hitBox.setHeight(0);
		spriteId = Constants.NULL_SPRITE_ID;
	}
	
	public Sprite copy()
	{
		return new NullSprite();
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject save()
	{
		JSONObject j = new JSONObject();
		j.put("type","NullSprite");
		return j;
	}
	public void load()
	{
		
	}
}
