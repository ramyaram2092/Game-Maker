package behaviors.collision;

import org.json.simple.JSONObject;

import audio.AudioFunctionality;
import constants.Constants;
import sprite.Sprite;

public class BounceCollisionBehaviorY implements CollisionBehavior
{

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject save() 
	{
		JSONObject json = new JSONObject();
		json.put("type","BounceCollisionBehaviorY");
		return json;
	}

	@Override
	public void load(JSONObject saveJSON) 
	{
		
	}

	@Override
	public void collide(Sprite collidee, int colliderId) 
	{
		collidee.flipYVelocity();
	}
	
	public boolean equals(Object o)
	{
		return (o instanceof BounceCollisionBehaviorY);
	}
	
	public String toString() {
		return "Bounce Vertically";
	}

	@Override
	public void addSound(String audioname) {
		AudioFunctionality audiof=new AudioFunctionality(audioname);
		audiof.playAudio();
		
	}

}
