import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import behaviors.event.MoveOnGameTickBehavior;
import behaviors.event.SpawnBehavior;
import command.CreateSpriteCommand;
import command.ModifySpriteCommand;
import constants.Constants;
import controller.Controller;
import model.Model;
import sprite.Sprite;

class ControllerTests 
{ 
	@Test
	//Ensure that we can create and un-create a sprite using commands from the controller
	void createSpriteCommandTest() 
	{
		Model m = new Model();
		assertEquals(m.getNumberOfSprites(), 0);
		CreateSpriteCommand createCommand = new CreateSpriteCommand(m);
		m.receiveCommand(createCommand);
		assertEquals(m.getNumberOfSprites(), 1);
		m.undo();
		assertEquals(m.getNumberOfSprites(),0);
		m.undo(); // make sure undo handles an empty stack gracefully
	}
	
	@Test
	//Ensure that we can properly modify a sprite in the model using commands from the controller
	void ModifySpriteCommandTest()
	{
		Model m = new Model();
		CreateSpriteCommand createCommand = new CreateSpriteCommand(m);
		m.receiveCommand(createCommand);
		assertEquals(m.getNumberOfSprites(), 1);

		Sprite mySprite = m.getSprite(0);
		mySprite.setWidth(200);
		assertNotEquals(mySprite,m.getSprite(0));
		
		ModifySpriteCommand modifyCommand = new ModifySpriteCommand(m, mySprite);
		m.receiveCommand(modifyCommand);
		
		assertEquals(mySprite,m.getSprite(0));
		m.undo();
		assertNotEquals(mySprite,m.getSprite(0));
	}
	
	@Test
	//Test saving and loading when the controller is pulling the strings
	public void saveAndLoadTest()
	{
		//Make new model and a bunch of sprites
		Model m = new Model();
		Controller c = new Controller();
		c.setModel(m);
		
		ArrayList<Sprite> sprites = new ArrayList<>();
		
		for (int i = 0; i < 20; i++)
		{
			c.createSprite();
			sprites.add(m.getSprite(i));
		}

		for (int i = 0; i < 20; i++)
		{
			Sprite current = sprites.get(i);
			current.setX(i*500);
			if (i != 0)
				assertNotEquals(current,c.getSprite(i));
			c.modifySprite(current);
			assertEquals(current,c.getSprite(i));
		}
		
		c.save();
		m = new Model();
		c.setModel(m);
		assertEquals(Constants.LOAD_SUCCESS, c.load());

		for (int i = 0; i < 20; i++)
		{
			Sprite current = sprites.get(i);
			assertEquals(current,c.getSprite(i));
		}
	}
	
	//Test switching play/create context
	//play should stash the model
	//pause should pause the clock
	//resume should resume it
	//stop should restore the stashed model
	@Test
	public void playPauseResumeStopTest() throws InterruptedException
	{
		Model m = new Model();
		Controller c = new Controller();
		c.setModel(m);
		c.createSprite();
		Sprite sprite = c.getSprite(0);
		sprite.addEventBehavior(new MoveOnGameTickBehavior());
		c.modifySprite(sprite);
		assertEquals(Constants.DEFAULT_SPRITE_X, sprite.getX());
		assertEquals(Constants.DEFAULT_SPRITE_Y, sprite.getY());
		c.play();
		Thread.sleep(1000); //expect 60 ticks
		c.pause();
		sprite = c.getSprite(0);
		double x = sprite.getX();
		double y = sprite.getY();
		assertNotEquals(Constants.DEFAULT_SPRITE_X, x);
		assertNotEquals(Constants.DEFAULT_SPRITE_Y, y);
		c.resume();
		Thread.sleep(1000);
		c.pause();
		sprite = c.getSprite(0);
		assertNotEquals(x, sprite.getX());
		assertNotEquals(y, sprite.getY());
		c.stop();
		sprite = c.getSprite(0);
		assertEquals(Constants.DEFAULT_SPRITE_X, sprite.getX());
		assertEquals(Constants.DEFAULT_SPRITE_Y, sprite.getY());
	}
	
	@Test
	public void modifySpriteVisibilityTest()
	{
		Model m = new Model();
		Controller c = new Controller();
		c.setModel(m);
		c.createSprite();
		Sprite sprite = c.getSprite(0);
		assertTrue(sprite.isVisible());
		sprite.setVisible(false);
		c.modifySprite(sprite);
		c.play(); //force a context switch so the model is stashed and restored in ram
		c.stop();
		sprite = c.getSprite(0);
		assertFalse(sprite.isVisible());
		assertTrue(sprite.isEnabled());
		sprite.disable();
		assertFalse(sprite.isEnabled());
		c.modifySprite(sprite);
		c.play(); //force a context switch so the model is stashed and restored in ram
		c.stop();
		sprite = c.getSprite(0);
		assertFalse(sprite.isEnabled());
		assertFalse(sprite.isVisible());
	}
	
	@Test
	public void SpawnBehaviorTest() throws InterruptedException
	{
		Model m = new Model();
		Controller c = new Controller();
		c.setModel(m);
		c.createSprite();
		c.createSprite();
		Sprite spawner = c.getSprite(0);
		Sprite blueprint = c.getSprite(1);
		blueprint.addEventBehavior(new MoveOnGameTickBehavior());
		blueprint.getAppearance().setHeight(200);
		c.modifySprite(blueprint);
		SpawnBehavior sb = new SpawnBehavior(m,1); //pass reference to model and spriteid of blueprint
		spawner.addEventBehavior(sb);
		c.modifySprite(spawner);
		
		assertEquals(2,m.getNumberOfSprites());
		
		c.play();
		Thread.sleep(7000);
		c.pause();
		
		assertNotEquals(2,m.getNumberOfSprites());

		
		c.stop();
		
		assertEquals(2,m.getNumberOfSprites());
		
		
	}
	
}
