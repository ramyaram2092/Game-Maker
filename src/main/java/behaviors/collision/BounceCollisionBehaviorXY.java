package behaviors.collision;

import org.json.simple.JSONObject;

import audio.AudioFunctionality;
import sprite.Sprite;

public class BounceCollisionBehaviorXY implements CollisionBehavior
{

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject save() 
	{
		JSONObject json = new JSONObject();
		json.put("type","BounceCollisionBehaviorXY");
		return json;
	}

	@Override
	public void load(JSONObject saveJSON) 
	{
		
	}

	@Override
	public void collide(Sprite collidee, int colliderId) 
	{
		collidee.flipBothVelocities();
		AudioFunctionality audiof=new AudioFunctionality("fire.wav");
		audiof.playAudio();
	}
	
	public boolean equals(Object o)
	{
		return (o instanceof BounceCollisionBehaviorXY);
	}
	
	public String toString() {
		return "Bounce Diagonally";
	}

	@Override
	public void addSound(String audioname) {
		AudioFunctionality audiof=new AudioFunctionality(audioname);
		audiof.playAudio();
	}

}
