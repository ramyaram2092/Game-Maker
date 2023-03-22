package command;

import model.Model;
import sprite.NullSprite;
import sprite.Sprite;

public class ModifySpriteCommand implements Command 
{
	
	private Sprite currentState;
	private Sprite swapState;
	private Model model;
	
	public ModifySpriteCommand(Model m, Sprite modifiedSprite)
	{
		currentState = modifiedSprite;
		swapState = new NullSprite();
		model = m;
	}

	@Override
	public void execute() 
	{
		//Store the previous state
		swapState = model.getSprite(currentState.getSpriteId());
		
//		System.out.println(swapState.getX() + " " + swapState.getY());
		
		//Update the state of the sprite to the new state
		model.modifySprite(currentState);
	}

	@Override
	public void unexecute() 
	{
		//Restore the previous state
		if (!(swapState instanceof NullSprite))
		{
			//set currentState to be the old stored state
			currentState = swapState;
			
			//update swapState to be the state we're overwriting (only used for redo, which is a stretch goal)
			swapState = model.getSprite(swapState.getSpriteId());
			
			//update the sprite in the model
			model.modifySprite(currentState);
		}
	}

}