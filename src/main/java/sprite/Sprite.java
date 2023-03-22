package sprite;


import java.util.ArrayList;

import org.json.simple.JSONObject;

import behaviors.collision.CollisionBehavior;
import behaviors.collision.CustomCollisionMap;
import behaviors.collision.CustomCollisionPair;
import behaviors.event.EventBehavior;
import behaviors.event.EventBehaviorChain;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import saveandload.Saveable;
import constants.Constants;

public class Sprite implements Drawable, Saveable 
{
	//unique int identifier - will be handled by sprite manager
	protected int spriteId;

	//hitbox for collision handling, also contains x,y location
	protected HitBox hitBox;
	
	//appearance - either shape or image, sprite doesn't care which
	protected Appearance appearance;
	
	//Chain of event behaviors
	protected EventBehaviorChain eventBehaviorChain;
	
	//map of spriteId : collisions against the corresponding sprite
	protected CustomCollisionMap customCollisionMap;
	
	protected boolean enabled;
	
	protected boolean visible;
	


	public Sprite() 
	{
		spriteId = Constants.DEFAULT_SPRITE_ID;

		//initializes hitbox with default x,y,width,height found in Constants.java
		hitBox = new HitBox();
		appearance = new Appearance();
		eventBehaviorChain = new EventBehaviorChain();
		customCollisionMap = new CustomCollisionMap();
		enabled = true;
		visible = true;
	}


	@Override
	public void draw(GraphicsContext g) 
	{
			appearance.draw(g);
	}

	public int getSpriteId() 
	{
		return spriteId;
	}


	public void setSpriteId(int newId) 
	{
		spriteId = newId;
	}
	


	public void setAppearance(Appearance appearance2) 
	{
		appearance = appearance2;
	}

	public void setHitBox(HitBox h) 
	{
		hitBox = h;
	}

	public void setX(double x) 
	{
		hitBox.setX(x);
		appearance.setX(x);
	}


	public void setY(double y) 
	{
		hitBox.setY(y);
		appearance.setY(y);
	}
	
	public void setWidth(double w)
	{
		hitBox.setWidth(w);
		appearance.setWidth(w);
	}
	public void setHeight(double h)
	{
		hitBox.setHeight(h);
		appearance.setHeight(h);
	}
	
	public void addEventBehavior(EventBehavior e)
	{
		eventBehaviorChain.add(e);
	}
	
	public void addCustomCollision(int colliderSpriteId, CollisionBehavior collisionBehavior)
	{
		customCollisionMap.put(colliderSpriteId, collisionBehavior);
	}
	
	public void collide(int colliderId)
	{
		customCollisionMap.collide(this, colliderId);
	}
	
	//TODO how should we handle this discrepancy between appearance/hitbox? Should we just reconcile the two? Seems to violate single responsibilty
	public double getX()
	{
		return hitBox.getX();
	}
	
	public double getY()
	{
		return hitBox.getY();
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject save() 
	{
		JSONObject json = new JSONObject();
		json.put("type","Sprite");
		json.put("spriteId",spriteId);
		json.put("hitBox",hitBox.save());
		json.put("appearance",appearance.save());
		json.put("eventBehaviorChain",eventBehaviorChain.save());
		json.put("customCollisionMap",customCollisionMap.save());
		json.put("visible", visible);
		json.put("enabled",enabled);

		//TODO keep this growing as more stuff is added to Sprite class
		
		return json;
	}

	//TODO this needs updated as new fields are added to Sprite
	public Sprite copy()
	{
		Sprite copySprite = new Sprite();
		copySprite.setSpriteId(spriteId);
		copySprite.setHitBox(hitBox.copy());
		copySprite.setAppearance(appearance.copy());
		copySprite.setEventBehaviorChain(eventBehaviorChain.copy());
		copySprite.setCustomCollisionMap(customCollisionMap.copy());
		copySprite.setVisible(visible);
		copySprite.setEnabled(enabled);
		return copySprite;
	}

	public void setCustomCollisionMap(CustomCollisionMap ccm) 
	{
		customCollisionMap = ccm;
	}

	public void setEventBehaviorChain(EventBehaviorChain e) 
	{
		eventBehaviorChain = e;
	}
	
	public int getEventBehaviorChainSize()
	{
		return eventBehaviorChain.size();
	}

	@Override
	public void load(JSONObject saveJSON) 
	{
		boolean nullSprite = ((String)saveJSON.get("type")).equals("NullSprite");
		if (nullSprite)
		{
			return;
		}
		hitBox = new HitBox();
		hitBox.load((JSONObject)saveJSON.get("hitBox"));
		appearance = new Appearance();
		appearance.load((JSONObject)saveJSON.get("appearance"));
		try
		{
			spriteId = ((Long)saveJSON.get("spriteId")).intValue();
		}catch(ClassCastException e)
		{
			spriteId = (int)saveJSON.get("spriteId");
		}
		EventBehaviorChain ebc = new EventBehaviorChain();
		ebc.load((JSONObject)saveJSON.get("eventBehaviorChain"));
		eventBehaviorChain = ebc;
		CustomCollisionMap ccm = new CustomCollisionMap();
		ccm.load((JSONObject)saveJSON.get("customCollisionMap"));
		customCollisionMap = ccm;
		enabled = (boolean)saveJSON.get("enabled");
		visible = (boolean)saveJSON.get("visible");
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Sprite)
		{
			Sprite s = (Sprite)o;
			boolean spriteIdEquals = s.getSpriteId() == spriteId;
			if (!spriteIdEquals)
			{
				//System.out.println("Sprite Ids aren't equal");
			}
			boolean hitBoxEquals = s.getHitBox().equals(hitBox);
			if (!hitBoxEquals)
			{
				//System.out.println("Hit boxes aren't equal");
			}
			boolean appearanceEquals = s.getAppearance().equals(appearance);
			if (!appearanceEquals)
			{
				//System.out.println("appearances aren't equal");
			}
			boolean eventBehaviorChainEquals = s.getEventBehaviorChain().equals(eventBehaviorChain);
			if (!eventBehaviorChainEquals)
			{
				//System.out.println("event behavior chains aren't equal");
				//System.out.println(eventBehaviorChain.size());
				//System.out.println(s.getEventBehaviorChain().size());
			}
			boolean customCollisionMapEquals = s.getCustomCollisionMap().equals(customCollisionMap);
			if (!customCollisionMapEquals)
			{
				//System.out.println("custom collision maps aren't equal");
			}
			boolean visibleEquals = s.isVisible() && visible;
			
			boolean enabledEquals = s.isEnabled() && enabled;
			
			return spriteIdEquals && hitBoxEquals && appearanceEquals && eventBehaviorChainEquals && customCollisionMapEquals && visibleEquals && enabledEquals;
		}
		return false;
	}
	
	public CustomCollisionMap getCustomCollisionMap() 
	{
		return customCollisionMap;
	}

	public EventBehaviorChain getEventBehaviorChain() 
	{
		return eventBehaviorChain;
	}

	public String toString()
	{
		return String.format("Sprite#%d, hitbox: %s, appearance: %s",spriteId, hitBox.toString() ,appearance.toString());
	}


	public Appearance getAppearance() 
	{
		return appearance;
	}

	public HitBox getHitBox() 
	{
		return hitBox;
	}

	public void setImage(String path) 
	{
		appearance.setImage(path);
	}

	public void destroy() 
	{
		//TODO
		//probably just set visible to false and play any explosions etc
		setVisible(false);
		disable();
	}
	
	public void setDefaultCollisionBehavior(CollisionBehavior c)
	{
		customCollisionMap.setDefaultCollisionBehavior(c);
	}
	
	public CollisionBehavior getDefaultCollisionBehavior() {
		return customCollisionMap.getDefaultCollisionBehavior();
	}

	public void flipYVelocity() 
	{
		eventBehaviorChain.flipYVelocity();
	}
	
	public void flipXVelocity()
	{
		eventBehaviorChain.flipXVelocity();
	}
	
	public int getXVelocity()
	{
		return eventBehaviorChain.getMaxXVelocity();
	}
	
	public int getYVelocity()
	{
		return eventBehaviorChain.getMaxYVelocity();
	}
	public void  setXVelocity(int xVelocity)
	{
		eventBehaviorChain.setXVelocity( xVelocity);
	}
	
	public void  setYVelocity(int yVelocity)
	{
		eventBehaviorChain.setYVelocity( yVelocity);
	}

	public void flipBothVelocities() 
	{
		flipXVelocity();
		flipYVelocity();
	}


	public void onGameTick() 
	{
		eventBehaviorChain.onGameTick(this);
	}
	
	public ArrayList<CustomCollisionPair> getCustomCollisionPairs()
	{
		return customCollisionMap.getCustomCollisionPairs();
	}



	/**
	 * The following method tries all possible direction for the sprite object to move when it impacts another sprite 
	 * Ex: Ghost sprite in pac man must change course whenever it comes across a wall
	 */
	public void changeDirection() 
	{
		int xVelocity=this.getXVelocity();
		int yVelocity=this.getYVelocity();
		
	
		if(xVelocity==0 && yVelocity>0)// sprite moving down 
		{
			setY(Constants.SPRITE_COLLISION_WARP_DISTANCE + getY());
			xVelocity=yVelocity;//make sprite move right
			yVelocity=0;
		}
		else if(xVelocity==0 && yVelocity<0)// sprite moving up 
		{
			setY(getY() - Constants.SPRITE_COLLISION_WARP_DISTANCE);
			xVelocity=yVelocity; // make sprite move left
			yVelocity=0;
		} 
		else if(yVelocity==0 && xVelocity>0)// sprite moving right
		{
			setX(getX() - Constants.SPRITE_COLLISION_WARP_DISTANCE);
			yVelocity=-(xVelocity);// sprite moving up
			xVelocity=0;
		}
		else if((yVelocity==0 && xVelocity<0))// sprite moving left
		{
			setX(getX() + Constants.SPRITE_COLLISION_WARP_DISTANCE);
			yVelocity=-(xVelocity);// sprite will move down
			xVelocity=0;
		}
		
		this.setXVelocity(xVelocity);
		this.setYVelocity(yVelocity);
			
	}
		
	public boolean isVisible() 
	{
		return visible;
	}

	public void setVisible(boolean v) 
	{
		visible = v;
	}
	public boolean isEnabled() 
	{
		return enabled;
	}
	public void enable() 
	{
		enabled = true;
	}
	public void disable() 
	{
		enabled = false;
	}
	public void setEnabled(boolean e)
	{
		enabled = e;
	}


	public double getHeight() 
	{
		return hitBox.getHeight();
	}


	public double getWidth() 
	{
		return hitBox.getWidth();
	}


	public void onKeyPress(KeyEvent k) 
	{
		KeyCode kc = k.getCode();
		eventBehaviorChain.onKeyPress(this,kc);
	}
}

