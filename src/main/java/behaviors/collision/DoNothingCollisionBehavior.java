package behaviors.collision;

import java.io.File;

import org.json.simple.JSONObject;

import audio.AudioFunctionality;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sprite.Sprite;

public class DoNothingCollisionBehavior implements CollisionBehavior 
{
	
	@Override
	public JSONObject save() 
	{
		JSONObject json = new JSONObject();
		json.put("type","DoNothingCollisionBehavior");
		return json;
	}

	@Override
	public void load(JSONObject saveJSON) 
	{
		
	}

	@Override
	public void collide(Sprite collidee, int colliderId) 
	{
		//do nothing
	}
	
	public boolean equals(Object o)
	{
		return o instanceof DoNothingCollisionBehavior;
	}
	
	public String toString() {
		return "Do Nothing";
	}

	@Override
	public void addSound(String audioname) {
		AudioFunctionality audiof=new AudioFunctionality(audioname);
		audiof.playAudio();
		
	}

}
