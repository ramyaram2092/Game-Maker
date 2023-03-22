package behaviors.event;

import javafx.scene.input.KeyCode;
import saveandload.Saveable;
import sprite.Sprite;

public interface EventBehavior extends Saveable
{
	//These will be called from within the Sprite class like so:
	
	//this.eventBehavior.onKeyPress(this);
	
	//That way EventBehaviors will be able to modify the relevant fields within the sprite without storing a circular reference to it
	
	public void onMousePress(Sprite sprite);
	public void onGameStart(Sprite sprite);
	public void onMouseMove(Sprite sprite);
	public void onKeyPress(Sprite sprite,KeyCode keyCode);
	public void onGameTick(Sprite sprite);
	public void onLevelIncrease(Sprite sprite);
	public EventBehavior copy();

}
