package behaviors.collision;

import org.json.simple.JSONObject;

import audio.AudioFunctionality;
import sprite.Sprite;

public class DestroyCollisionBehavior implements CollisionBehavior 
{

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject save() 
	{
		JSONObject json = new JSONObject();
		json.put("type","DestroyCollisionBehavior");
		return json;
	}

	@Override
	public void load(JSONObject saveJSON) 
	{

	}

	@Override
	public void collide(Sprite collidee, int colliderId) 
	{
		collidee.destroy();
	}

	public boolean equals(Object o)
	{
		return (o instanceof DestroyCollisionBehavior);
	}
	
	public String toString() {
		return "Destroy Self on Collision";
	}

	@Override
	public void addSound(String audioname) {
		AudioFunctionality audiof=new AudioFunctionality(audioname);
		audiof.playAudio();
		
	}
}
