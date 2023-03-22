import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import behaviors.collision.BounceCollisionBehaviorX;
import behaviors.collision.CustomCollisionMap;
import behaviors.collision.DestroyCollisionBehavior;
import behaviors.collision.DoNothingCollisionBehavior;
import behaviors.event.DoNothingBehavior;
import behaviors.event.MoveOnGameTickBehavior;
import behaviors.event.SpawnBehavior;
import constants.Constants;
import javafx.geometry.Point2D;
import model.Model;
import saveandload.SaveAndLoadManager;
import saveandload.SaveableEllipse;
import saveandload.SaveableRectangle;
import sprite.Appearance;
import sprite.HitBox;
import sprite.NullSprite;
import sprite.Sprite;
import sprite.SpriteManager;

class ModelTests {

	@Test
	//Test to ensure that the sprite manager is correctly modifying the sprite IDs upon insertion
	//Also tests that it will not insert NullSprites
	public void SpriteManagerTest()
	{
		int before, after;
		SpriteManager spriteManager = new SpriteManager();
		Sprite sprite1 = new Sprite();
		Sprite sprite2 = new Sprite();
		Sprite sprite3 = new Sprite();
		Sprite sprite4 = new Sprite();
		
		//make sure that spritemanager updates ids appropriately
		assertEquals(Constants.DEFAULT_SPRITE_ID, sprite1.getSpriteId());
		assertEquals(Constants.DEFAULT_SPRITE_ID, sprite2.getSpriteId());
		assertEquals(Constants.DEFAULT_SPRITE_ID, sprite3.getSpriteId());
		assertEquals(Constants.DEFAULT_SPRITE_ID, sprite4.getSpriteId());

		//add sprites to the manager, ensure that the size variable is updating appropriately
		spriteManager.add(sprite1);
		assertEquals(1,spriteManager.getSize());
		spriteManager.add(sprite2);
		assertEquals(2,spriteManager.getSize());
		spriteManager.add(sprite3);
		assertEquals(3,spriteManager.getSize());
		spriteManager.add(sprite4);
		assertEquals(4,spriteManager.getSize());

		assertEquals(0, sprite1.getSpriteId());
		assertEquals(1, sprite2.getSpriteId());
		assertEquals(2, sprite3.getSpriteId());
		assertEquals(3, sprite4.getSpriteId());
		
		//remove a sprite that does exist from the manager
		before = spriteManager.getSize();
		spriteManager.remove(2);
		after = spriteManager.getSize();
		assertEquals(3,after);
		
		NullSprite nullSprite = new NullSprite();
		assertEquals(Constants.NULL_SPRITE_ID, nullSprite.getSpriteId());
		
		before = spriteManager.getSize();
		spriteManager.add(nullSprite);
		after = spriteManager.getSize();
		assertEquals(before,after);
		
		//remove a sprite that doesn't exist from the manager
		before = spriteManager.getSize();
		spriteManager.remove(-5);
		after = spriteManager.getSize();
		assertEquals(before,after);
		
		//Test getting the sprites as an arraylist
		ArrayList<Sprite> spriteList = spriteManager.getSpriteList();
		
		//Confirm that there are the same number of sprites in the list
		assertEquals(after,spriteList.size());
	}
	
	@Test
	public void HitBoxTest()
	{
		HitBox hitBox = new HitBox();
		
		Point2D topRight = new Point2D(hitBox.getX() + Constants.DEFAULT_SPRITE_WIDTH, hitBox.getY());
		assertEquals(topRight, hitBox.getTopRight());
		
		Point2D topLeft = hitBox.getLocation();
		assertEquals(topLeft,hitBox.getTopLeft());
		
		Point2D bottomLeft = new Point2D(hitBox.getX(), hitBox.getY() + Constants.DEFAULT_SPRITE_HEIGHT);
		assertEquals(bottomLeft, hitBox.getBottomLeft());
		
		Point2D bottomRight = new Point2D(hitBox.getX() + Constants.DEFAULT_SPRITE_WIDTH, hitBox.getY() + Constants.DEFAULT_SPRITE_HEIGHT);
		assertEquals(bottomRight, hitBox.getBottomRight());
		
		double x = hitBox.getX();
		assertEquals(Constants.DEFAULT_SPRITE_X, x);
		
		double y = hitBox.getY();
		assertEquals(Constants.DEFAULT_SPRITE_Y, y);
		
		hitBox.setX(10);
		assertEquals(hitBox.getX(),10);
		
		hitBox.setY(10);
		assertEquals(hitBox.getY(),10);
		
		double height = hitBox.getHeight();
		assertEquals(Constants.DEFAULT_SPRITE_HEIGHT, height);
		
		double width = hitBox.getWidth();
		assertEquals(Constants.DEFAULT_SPRITE_WIDTH, width);
		
		hitBox.setHeight(15);
		assertEquals(hitBox.getHeight(),15);
		
		hitBox.setWidth(15);
		assertEquals(hitBox.getWidth(),15);
		
		Point2D size = new Point2D(15,15);
		assertEquals(size,hitBox.getSize());
		
		Point2D location = new Point2D(10,10);
		assertEquals(location,hitBox.getLocation());
		
		HitBox hitBoxCopy = hitBox.copy();
		assertEquals(hitBoxCopy.getLocation(),location);
		assertEquals(hitBoxCopy.getSize(),size);
	}
	
	@Test
	public void AppearanceTest()
	{
		Appearance appearance = new Appearance();
		
		double x = appearance.getX();
		assertEquals(Constants.DEFAULT_SPRITE_X, x);
		
		double y = appearance.getY();
		assertEquals(Constants.DEFAULT_SPRITE_Y, y);
		
		appearance.setX(10);
		assertEquals(appearance.getX(),10);
		
		appearance.setY(10);
		assertEquals(appearance.getY(),10);
		
		double height = appearance.getHeight();
		assertEquals(Constants.DEFAULT_SPRITE_HEIGHT, height);
		
		double width = appearance.getWidth();
		assertEquals(Constants.DEFAULT_SPRITE_WIDTH, width);
		
		appearance.setHeight(15);
		assertEquals(appearance.getHeight(),15);
		
		appearance.setWidth(15);
		assertEquals(appearance.getWidth(),15);
		
		Point2D size = new Point2D(15,15);
		assertEquals(size,appearance.getSize());
		
		Point2D location = new Point2D(10,10);
		assertEquals(location,appearance.getLocation());
		
		Appearance appearanceCopy = appearance.copy();
		assertEquals(appearanceCopy.getLocation(),location);
		assertEquals(appearanceCopy.getSize(),size);
		
		
	}
	
	@Test
	public void SaveableRectangleSaveTest()
	{
		SaveableRectangle rect = new SaveableRectangle();
		rect.setX(1);
		rect.setY(2);
		rect.setWidth(3);
		rect.setHeight(4);
		
		JSONObject json = rect.save();
		
		SaveableRectangle loader = new SaveableRectangle();
		loader.load(json);
		
		
		assertEquals(rect,loader);
	}

	@Test
	public void SaveableEllipseSaveTest()
	{
		SaveableEllipse ellipse = new SaveableEllipse();
		ellipse.setX(1);
		ellipse.setY(2);
		ellipse.setWidth(3);
		ellipse.setHeight(4);
		
		JSONObject json = ellipse.save();
		
		SaveableEllipse loader = new SaveableEllipse();
		loader.load(json);
		
		
		assertEquals(ellipse,loader);
	}
	
	@Test
	public void AppearanceSaveAndLoadTest()
	{
		Appearance appearance = new Appearance();
		double x,y,width,height;
		x = 1;
		y = 2;
		width = 3;
		height = 4;
		
		appearance.setX(x);
		appearance.setY(y);
		appearance.setWidth(width);
		appearance.setHeight(height);
		
		JSONObject result = appearance.save();
		//System.out.println(result);
		
		Appearance loaded = new Appearance();
		loaded.load(result);
		
		assertEquals(appearance,loaded);
	}
	
	@Test
	public void HitBoxSaveAndLoadTest()
	{
		HitBox hitBox = new HitBox();
		double x,y,width,height;
		x = 1;
		y = 2;
		width = 3;
		height = 4;
		
		hitBox.setX(x);
		hitBox.setY(y);
		hitBox.setWidth(width);
		hitBox.setHeight(height);
		
		JSONObject result = hitBox.save();
		
		HitBox loaded = new HitBox();
		loaded.load(result);
		
		assertEquals(hitBox,loaded);
	}
	
	
	@Test
	//This is the big test that will ensure the model, spritemanager, saveandloadmanager, and all related systems work in tandem
	//Needs updated as more stuff we want to save gets implemented
	public void saveAndLoadTest()
	{
		//Make new model and a bunch of sprites
		Model m = new Model();
		Sprite sprite1 = new Sprite();
		Sprite sprite2 = new Sprite();
		Sprite sprite3 = new Sprite();
		Sprite sprite4 = new Sprite();
		Sprite sprite5 = new Sprite();
		Sprite sprite6 = new Sprite();
		Sprite sprite7 = new Sprite();
		Sprite sprite8 = new Sprite();
		
		sprite1.setX(1);
		sprite2.setY(2);
		sprite3.setWidth(3);
		sprite4.setHeight(4);
		sprite5.setImage("donkey_kong.jpg");
		sprite6.addEventBehavior(new MoveOnGameTickBehavior());
		assertEquals(1,sprite6.getEventBehaviorChainSize());
		sprite6.addEventBehavior(new DoNothingBehavior());
		assertEquals(2,sprite6.getEventBehaviorChainSize());
		sprite7.addCustomCollision(3, new DestroyCollisionBehavior());
		sprite8.addCustomCollision(6, new BounceCollisionBehaviorX());
		sprite8.setDefaultCollisionBehavior(new DestroyCollisionBehavior());
		
		
		//add them to the model
		m.addSprite(sprite1);
		m.addSprite(sprite2);
		m.addSprite(sprite3);
		m.addSprite(sprite4);
		m.addSprite(sprite5);
		m.addSprite(sprite6);
		m.addSprite(sprite7);
		m.addSprite(sprite8);

		ArrayList<Sprite> sprites = new ArrayList<>();
		sprites.add(sprite1);
		sprites.add(sprite2);
		sprites.add(sprite3);
		sprites.add(sprite4);
		sprites.add(sprite5);
		sprites.add(sprite6);
		sprites.add(sprite7);
		sprites.add(sprite8);
		
		//save them
		try {
			m.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//reset the saveandloadmanager and the spritemanager
		m.resetSpriteManager();
		m.resetSaveAndLoadManager();
		
		//load
		try {
			m.load();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotEquals(0, m.getNumberOfSprites());
		
		//assert that everything got preserved
		for (int i = 0; i < sprites.size(); i++)
		{
			Sprite expected = sprites.get(i);
			Sprite actual = m.getSprite(i);
			if (!(expected.equals(actual)))
			{
				System.out.println(expected + " doesn't equal " + actual);
			}
			assertEquals(expected,actual);
		}
	}
	
	@Test
	public void CustomCollisionMapTest()
	{
		CustomCollisionMap customCollisionMap = new CustomCollisionMap();
		customCollisionMap.setDefaultCollisionBehavior(new BounceCollisionBehaviorX());
		customCollisionMap.put(1, new DoNothingCollisionBehavior());
		customCollisionMap.put(2, new DestroyCollisionBehavior());
		JSONObject json = customCollisionMap.save();
		
		CustomCollisionMap loader = new CustomCollisionMap();
		loader.load(json);
		assertEquals(customCollisionMap,loader);
	}
	
	@Test
	public void SpriteVisibilityTest() throws IOException, ParseException
	{
		Model m = new Model();
		m.addSprite(new Sprite());
		Sprite s = m.getSprite(0);
		assertTrue(s.isVisible());
		assertTrue(s.isEnabled());
		s.setVisible(false);
		assertFalse(s.isVisible());
		assertTrue(s.isEnabled());
		s.disable();
		assertFalse(s.isVisible());
		assertFalse(s.isEnabled());
		s.enable();
		assertTrue(s.isEnabled());
		s.disable();
		assertFalse(s.isVisible());
		assertFalse(s.isEnabled());
		m.modifySprite(s);
		m.save();
		m = new Model();
		m.load();
		s = m.getSprite(0);
		assertFalse(s.isVisible());
		assertFalse(s.isEnabled());
		
	}
	
	@Test
	public void SpawnBehaviorTest() throws IOException, ParseException
	{
		Model m = new Model();
		SpawnBehavior s = new SpawnBehavior(m);
		assertEquals(s,s);
		Sprite sprite = new Sprite();
		sprite.addEventBehavior(s);
		m.addSprite(sprite);
		m.save();
		m = new Model();
		m.load();
		Sprite spriteAfter = m.getSprite(0);
		assertEquals(sprite,spriteAfter);
	}
}
