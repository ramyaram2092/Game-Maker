package command;


import constants.Constants;
import main.GameMaker;
import model.Model;
import sprite.Sprite;
import sprite.SpriteManager;

/**
 * @author ramya
 * The following class is dedicated to  create/remove sprite objects commands 
 * 
 */

public class CreateSpriteCommand implements Command 
{

	private int spriteId;
	private Model model;

	
	//constructor 1 : Empty Constructor 
	public CreateSpriteCommand(Model m)
	{
		model=m;
		spriteId = Constants.DEFAULT_SPRITE_ID;
	}
	
	
	@Override
	public void execute() 
	{
		//adds a new sprite to the model and updates the spriteId in the CreateSpriteCommand
		//with the spriteId which the sprite manager assigned the new sprite
		spriteId = model.addSprite(new Sprite());
	}

	@Override
	
	public void unexecute() 
	{
		//remove the sprite at spriteId
		model.removeSprite(spriteId);
	}

}