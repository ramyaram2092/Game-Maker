package behaviors.collision;

import java.io.File;

import org.json.simple.JSONObject;

import com.thoughtworks.qdox.model.expression.Add;

import audio.AudioFunctionality;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sprite.Sprite;

public class PacManRandomCollisionBehaviour implements CollisionBehavior 
{

	@Override
	public JSONObject save() 
	{
		JSONObject json = new JSONObject();
		json.put("type","PacManRandomCollisionBehaviour");
		return json;
	}

	@Override
	public void load(JSONObject saveJSON) 
	{

	}

	@Override
	public void collide(Sprite collidee, int colliderId) 
	{
		collidee.changeDirection();
		AudioFunctionality audiof=new AudioFunctionality("fire.wav");
		audiof.playAudio();
	}
	
	public boolean equals(Object o)
	{
		return o instanceof PacManRandomCollisionBehaviour;
	}
	
	public String toString() {
		return "Random Collision Behavior";
	}

	@Override
	public void addSound(String audioname) {
		AudioFunctionality audiof=new AudioFunctionality(audioname);
		audiof.playAudio();
	}
}
