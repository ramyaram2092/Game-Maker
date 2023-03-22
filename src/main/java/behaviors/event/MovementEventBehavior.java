package behaviors.event;

public interface MovementEventBehavior extends EventBehavior
{
	public void setXVelocity(int x);
	public int getXVelocity();
	public void setYVelocity(int y);
	public int getYVelocity();
}
