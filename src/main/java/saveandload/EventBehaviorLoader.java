package saveandload;

import org.json.simple.JSONObject;

import behaviors.collision.DestroyCollisionBehavior;
import behaviors.event.ChangeDirectionBehavior;
import behaviors.event.DoNothingBehavior;
import behaviors.event.EventBehavior;
import behaviors.event.FroggerMovementBehavior;
import behaviors.event.MoveOnGameTickBehavior;
import behaviors.event.PacManEventBehaviours;
import behaviors.event.SpawnBehavior;
import main.GameMaker;
import model.Model;

public class EventBehaviorLoader 
{
	private static final Model model = GameMaker.getModel();
	public static EventBehavior load(JSONObject json)
	{
		String type = (String)json.get("type");
		
		switch(type)
		{
			case "DoNothingBehavior":
				return new DoNothingBehavior();
			case "MoveOnGameTickBehavior":
				MoveOnGameTickBehavior moveOnGameTickBehavior = new MoveOnGameTickBehavior();
				moveOnGameTickBehavior.load(json);
				return moveOnGameTickBehavior;
			case "SpawnBehavior":
				SpawnBehavior s = new SpawnBehavior(model);
				s.load(json);
				return s;
			case "FroggerMovementBehavior":
				FroggerMovementBehavior f = new FroggerMovementBehavior();
				f.load(json);
				return f;
			case "PacManEventBehaviours":
				PacManEventBehaviours p = new PacManEventBehaviours();
				p.load(json);
				return p;
			case "ChangeDirectionBehavior":
				return new ChangeDirectionBehavior();
			default:
				System.out.println("BehaviorLoader was given something it doesn't have a case for: " + type);
				return new DoNothingBehavior();
		}
	}
}
