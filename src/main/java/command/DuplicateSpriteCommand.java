package command;


import constants.Constants;
import main.GameMaker;
import model.Model;
import sprite.Sprite;
import sprite.SpriteManager;

/**
 * @author Christian
 * The following class is dedicated to  duplicate sprite objects commands 
 * 
 */

public class DuplicateSpriteCommand implements Command 
{
	private Model model;
	private Sprite toDuplicate;
	private int spriteId;
	
	public DuplicateSpriteCommand(Model m, Sprite spriteToDuplicate)
	{
		model=m;
		toDuplicate = spriteToDuplicate;
		toDuplicate.setSpriteId(Constants.DEFAULT_SPRITE_ID);
		toDuplicate.setX(Constants.DEFAULT_SPRITE_X);
		toDuplicate.setY(Constants.DEFAULT_SPRITE_Y);
	}
	
	
	@Override
	public void execute() 
	{
		//adds a duplicate sprite to the model and updates the spriteId in the ModifySpriteCommand with new ID.
		spriteId = model.addSprite(toDuplicate);
	}

	@Override
	
	public void unexecute() 
	{
		//remove the sprite at spriteId
		model.removeSprite(spriteId);
	}

}