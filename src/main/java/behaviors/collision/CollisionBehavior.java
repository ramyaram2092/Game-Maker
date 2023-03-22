package behaviors.collision;

import saveandload.Saveable;
import sprite.Sprite;

public interface CollisionBehavior extends Saveable
{
	//Modify the sprite who this collisionbehavior belongs to - collidee
	//Each sprite should only care about how it alone behaves during a collision; it should not know how the other sprite reacts
	public void collide(Sprite collidee, int colliderId);
	public void addSound(String audioname);
}
