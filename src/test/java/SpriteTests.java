
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import behaviors.collision.BounceCollisionBehaviorX;
import behaviors.collision.CollisionBehavior;
import behaviors.collision.CustomCollisionMap;
import behaviors.collision.DestroyCollisionBehavior;
import behaviors.event.EventBehavior;
import behaviors.event.EventBehaviorChain;
import behaviors.event.MoveOnGameTickBehavior;
import sprite.Appearance;
import sprite.HitBox;
import sprite.Sprite;
import testdata.TestData;


public class SpriteTests
{
	
	@Test
	void setSpriteIdTest()
	{
		int spriteid=10;
		Sprite sprite=new Sprite();
		sprite.setSpriteId(spriteid); //sprite method to be tested 
		assertEquals(sprite.getSpriteId(),spriteid);
	}
	
	@Test
	void getSpriteIdTest()
	{
		Sprite sprite=new Sprite();
		int spriteid=5;
		sprite.setSpriteId(spriteid); //sprite method to be tested 
		int result=sprite.getSpriteId();
		assertEquals(result,spriteid);
		
	}
	@Test
	void setAppearanceTest()
	{
		Sprite sprite= new Sprite();
		Appearance appearance=new Appearance(); 
		sprite.setAppearance(appearance); //sprite method to be tested 
		sprite.setX(TestData.DEFAULT_SPRITE_X);
		sprite.setY(TestData.DEFAULT_SPRITE_Y);
		
		 
		
		
		assertEquals(appearance.getX(),TestData.DEFAULT_SPRITE_X);
		assertEquals(appearance.getY(),TestData.DEFAULT_SPRITE_Y);
		
		// verify  if the appearance has created a 2D point size when set via sprite 
		sprite.setWidth(TestData.DEFAULT_SPRITE_WIDTH);
		sprite.setHeight(TestData.DEFAULT_SPRITE_HEIGHT);
		assertEquals(TestData.DEFAULT_SPRITE_WIDTH,appearance.getWidth());
		assertEquals(TestData.DEFAULT_SPRITE_HEIGHT,appearance.getHeight());
		
		
		//verify if the appearance has created a shape by default
		assertEquals(TestData.SHAPE,appearance.getShapeOrImage());
		
	}
	
	@Test
	void getAppearanceTest()
	{
		Sprite sprite= new Sprite();
		Appearance appearance=new Appearance(); 
		Appearance verifyappearance;
		sprite.setAppearance(appearance);
		sprite.setX(TestData.DEFAULT_SPRITE_X);
		sprite.setHeight(TestData.DEFAULT_SPRITE_HEIGHT);
		
		verifyappearance=sprite.getAppearance();
		assertEquals(verifyappearance.getHeight(),TestData.DEFAULT_SPRITE_HEIGHT);
		assertEquals(verifyappearance.getX(),TestData.DEFAULT_SPRITE_X);
		
		
	}
	@Test
	void setHitBoxTest()
	{
		Sprite sprite= new Sprite();
		HitBox hitbox=new HitBox();
		sprite.setHitBox(hitbox);  //sprite method to be tested 
		
		sprite.setX(TestData.DEFAULT_SPRITE_X);
		sprite.setY(TestData.DEFAULT_SPRITE_Y);
		assertEquals(TestData.DEFAULT_SPRITE_X,hitbox.getX());
		assertEquals(TestData.DEFAULT_SPRITE_Y,hitbox.getY());
		
		// verify  if the hitbox  has created a 2D point size 
		sprite.setWidth(TestData.DEFAULT_SPRITE_WIDTH);
		sprite.setHeight(TestData.DEFAULT_SPRITE_HEIGHT);
		assertEquals(TestData.DEFAULT_SPRITE_WIDTH,hitbox.getWidth());
		assertEquals(TestData.DEFAULT_SPRITE_HEIGHT,hitbox.getHeight());
			
	}
	
	
	@Test
	void getHitBox()
	{
		Sprite sprite= new Sprite();
		HitBox hitbox=new HitBox();
		HitBox verifyhitbox;
		sprite.setHitBox(hitbox);
		sprite.setX(TestData.DEFAULT_SPRITE_X);
		sprite.setHeight(TestData.DEFAULT_SPRITE_HEIGHT);
		
		verifyhitbox=sprite.getHitBox();
		assertEquals(verifyhitbox.getHeight(),TestData.DEFAULT_SPRITE_HEIGHT);
		assertEquals(verifyhitbox.getX(),TestData.DEFAULT_SPRITE_X);
	}
	
	
	
	@Test
	void setXTest()
	{
		Sprite sprite=new Sprite();
		Appearance appearance=new Appearance();
		sprite.setAppearance(appearance);  
		HitBox hitbox=new HitBox();
		sprite.setHitBox(hitbox);
		sprite.setX(TestData.DEFAULT_SPRITE_X); //sprite method to be tested 
		assertEquals(TestData.DEFAULT_SPRITE_X,hitbox.getX());
		assertEquals(TestData.DEFAULT_SPRITE_X,appearance.getX());
	}
	
	@Test
	void setYTest()
	{
		Sprite sprite=new Sprite();
		Appearance appearance=new Appearance();
		sprite.setAppearance(appearance);
		HitBox hitbox=new HitBox();
		sprite.setHitBox(hitbox);
		sprite.setY(TestData.DEFAULT_SPRITE_Y); //sprite method to be tested 
		assertEquals(TestData.DEFAULT_SPRITE_Y,hitbox.getY());
		assertEquals(appearance.getY(),TestData.DEFAULT_SPRITE_Y);
	}
	
	@Test
	void setWidthTest()
	{
		Sprite sprite=new Sprite();
		Appearance appearance=new Appearance();
		sprite.setAppearance(appearance);
		HitBox hitbox=new HitBox();
		sprite.setHitBox(hitbox);
		sprite.setWidth(TestData.DEFAULT_SPRITE_WIDTH); //sprite method to be tested 
		assertEquals(hitbox.getWidth(),TestData.DEFAULT_SPRITE_WIDTH);
		assertEquals(appearance.getWidth(),TestData.DEFAULT_SPRITE_WIDTH);
			
	}
	
	@Test
	void setHeightTest()
	{
		Sprite sprite=new Sprite();
		Appearance appearance=new Appearance();
		sprite.setAppearance(appearance);
		HitBox hitbox=new HitBox();
		sprite.setHitBox(hitbox);
		sprite.setHeight(TestData.DEFAULT_SPRITE_HEIGHT); //sprite method to be tested 
		assertEquals(hitbox.getHeight(),TestData.DEFAULT_SPRITE_HEIGHT);
		assertEquals(appearance.getHeight(),TestData.DEFAULT_SPRITE_HEIGHT);
			
	}
	
	@Test
	void getWidthTest()
	{
		Sprite sprite=new Sprite();
		HitBox hitbox=new HitBox();
		sprite.setHitBox(hitbox);
		sprite.setWidth(TestData.DEFAULT_SPRITE_WIDTH);
		double width=sprite.getWidth(); //sprite method to be tested 
		assertEquals(width,TestData.DEFAULT_SPRITE_WIDTH);
	}
	@Test
	void getHeightTest()
	{
		Sprite sprite=new Sprite();
		HitBox hitbox=new HitBox();
		sprite.setHitBox(hitbox);
		sprite.setHeight(TestData.DEFAULT_SPRITE_HEIGHT); 
		double width=sprite.getHeight(); //sprite method to be tested 
		assertEquals(width,TestData.DEFAULT_SPRITE_HEIGHT);
	}
	
	@Test
	void copySpriteTest()
	{
		
		//to do	
	}
	
	@Test
	void setEventBehaviourChain()
	{
		Sprite sprite=new Sprite();
		EventBehaviorChain eventBehaviorChain = new EventBehaviorChain();
		sprite.setEventBehaviorChain(eventBehaviorChain); //sprite method to be tested 
		
		LinkedList<EventBehavior> chain =eventBehaviorChain.getChain();
		chain.add(eventBehaviorChain); 
		
		assertEquals(1,chain.size()); 
		
		chain.remove();
		assertEquals(0,chain.size()); 
		
				
	}
	@Test
	void getEventBehaviorChainTest()
	{
		Sprite sprite=new Sprite();
		EventBehaviorChain eventBehaviorChain = new EventBehaviorChain();
		sprite.setEventBehaviorChain(eventBehaviorChain); //sprite method to be tested 
		
		LinkedList<EventBehavior> chain =eventBehaviorChain.getChain();
		chain.add(eventBehaviorChain); 
		EventBehaviorChain verifyeventBehaviorChain=sprite.getEventBehaviorChain();
		
		int size=verifyeventBehaviorChain.size();
		assertEquals(1,size); 
		
		
	}
	
	@Test 
	void getEventBehvaiourChainSize()
	{
		Sprite sprite=new Sprite();
		EventBehaviorChain eventBehaviorChain = new EventBehaviorChain();
		sprite.setEventBehaviorChain(eventBehaviorChain); //sprite method to be tested 
		
		LinkedList<EventBehavior> chain =eventBehaviorChain.getChain();
		chain.add(eventBehaviorChain); 
		
		int chainSize= sprite.getEventBehaviorChainSize(); //sprite method to be tested 
		assertEquals(chainSize,chain.size()); 
		
		chain.remove();
		chainSize= sprite.getEventBehaviorChainSize(); //sprite method to be tested 
		assertEquals(chainSize,chain.size()); 
	}
	
	
	@Test
	void setCustomCollisionMapTest()
	{
		Sprite sprite=new Sprite();
		CustomCollisionMap collisionmap=new CustomCollisionMap();
		CollisionBehavior defaultCollisionBehavior;
		sprite.setCustomCollisionMap(collisionmap); //sprite method to be tested

		defaultCollisionBehavior=collisionmap.getDefaultCollisionBehavior();
		assertEquals(TestData.DEFAULTCOLLISION,defaultCollisionBehavior.toString());
		
	}
	
	@Test
	void getCustomCollisionMapTest()
	{
		Sprite sprite=new Sprite();
		CustomCollisionMap collisionmap=new CustomCollisionMap();
		CustomCollisionMap verifyCollisionmap;
		CollisionBehavior defaultCollisionBehavior;
		sprite.setCustomCollisionMap(collisionmap);
		verifyCollisionmap=sprite.getCustomCollisionMap(); //sprite method to be tested
		
		
		defaultCollisionBehavior=verifyCollisionmap.getDefaultCollisionBehavior();
		assertEquals(TestData.DEFAULTCOLLISION,defaultCollisionBehavior.toString());
		
	}
	
	@Test 
	void setDefaultCollisionBehaviorTest()
	{
		Sprite sprite=new Sprite();
		CustomCollisionMap collisionmap=new CustomCollisionMap();
		CollisionBehavior bounce=new BounceCollisionBehaviorX();
		sprite.setCustomCollisionMap(collisionmap);
		sprite.setDefaultCollisionBehavior(bounce); //sprite method to be tested
		
		CollisionBehavior actual=collisionmap.getdDefaultCollisionBehaviour();
		assertEquals(bounce,actual);
		
	}
	
	@Test 
	void getDefaultCollisionBehaviorTest()
	{
		Sprite sprite=new Sprite();
		CustomCollisionMap collisionmap=new CustomCollisionMap();
		CollisionBehavior destroy=new DestroyCollisionBehavior();
		sprite.setCustomCollisionMap(collisionmap);
		sprite.setDefaultCollisionBehavior(destroy); 
		
		CollisionBehavior actual=sprite.getDefaultCollisionBehavior();//sprite method to be tested
		assertEquals(destroy,actual);
	}
	
	
	@Test
	void setgetXVelocityTest()
	{
		Sprite sprite=new Sprite();
		sprite.addEventBehavior(new MoveOnGameTickBehavior());
		sprite.setXVelocity(TestData.XVELOCITY);//sprite method to be tested
		int actual= sprite.getXVelocity(); //sprite method to be tested
		assertEquals(TestData.XVELOCITY,actual);
		
		sprite.setXVelocity(1000); //sprite method to be tested
		actual= sprite.getXVelocity();//sprite method to be tested
		assertEquals(1000,actual);
	}
	
	@Test
	void setgetYVelocityTest()  
	{
		Sprite sprite=new Sprite();
		sprite.addEventBehavior(new MoveOnGameTickBehavior());
		sprite.setYVelocity(TestData.YVELOCITY); //sprite method to be tested
		int actual= sprite.getYVelocity();//sprite method to be tested
		assertEquals(TestData.YVELOCITY,actual);
		
		sprite.setYVelocity(2000); //sprite method to be tested
		actual= sprite.getYVelocity();//sprite method to be tested
		assertEquals(2000,actual);
	}
	


}
