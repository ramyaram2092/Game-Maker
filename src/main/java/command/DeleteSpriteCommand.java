package command;

import model.Model;
import sprite.NullSprite;
import sprite.Sprite;

public class DeleteSpriteCommand implements Command
{
	private Model model;
	private Sprite restoreState;
	private int spriteId;
	public DeleteSpriteCommand(int spriteId, Model m)
	{
		model = m;
		this.spriteId = spriteId;
		restoreState = new NullSprite();
	}
	@Override
	public void execute() 
	{
		restoreState = model.getSprite(spriteId);
		model.removeSprite(spriteId);
	}

	@Override
	public void unexecute() 
	{
		model.addSprite(restoreState);
	}
}
