package behaviors.event;

import java.util.LinkedList;

import org.json.simple.JSONObject;

import javafx.scene.input.KeyCode;
import saveandload.EventBehaviorLoader;
import sprite.Sprite;

public class EventBehaviorChain implements EventBehavior
{
	private LinkedList<EventBehavior> chain;
	
	public EventBehaviorChain()
	{
		chain = new LinkedList<>();
	}
	
	public LinkedList<EventBehavior> getChain()
	{
		return chain;
	}
	
	public void add(EventBehavior action)
	{
		chain.add(action);
	}
	
	public void clear()
	{
		chain.clear();
	}
	
	public void remove(EventBehavior action)
	{
		chain.remove(action);
	}
	
	public EventBehavior get(int index)
	{
		return chain.get(index);
	}
	
	public int size()
	{
		return chain.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject save() 
	{
		JSONObject json = new JSONObject();
		json.put("type","EventBehaviorChain");
		int size = size();
		json.put("size",size);
		for (int i=0; i<size; i++)
		{
			json.put(i,chain.get(i).save());
		}
		return json;
	}

	@Override
	public void load(JSONObject saveJSON) 
	{
		clear();
		int size = 0;
		try
		{
			size = ((Long)saveJSON.get("size")).intValue();
			if (size == 0)
			{
				return;
			}
			for (Integer i=0; i<size; i++)
			{
				EventBehavior e = EventBehaviorLoader.load((JSONObject)saveJSON.get(i.toString()));
				//System.out.println(e);
				add(e);
			}
		}catch(ClassCastException e)
		{
			size = (int)saveJSON.get("size");
			if (size == 0)
			{
				return;
			}
			for (Integer i=0; i<size; i++)
			{
				EventBehavior e2 = EventBehaviorLoader.load((JSONObject)saveJSON.get(i));
				//System.out.println(e);
				add(e2);
			}
		}
	}

	@Override
	public void onMousePress(Sprite sprite) 
	{
		for (EventBehavior e : chain)
		{
			e.onMousePress(sprite);
		}
	}

	@Override
	public void onGameStart(Sprite sprite) 
	{
		for (EventBehavior e : chain)
		{
			e.onGameStart(sprite);
		}
	}

	@Override
	public void onMouseMove(Sprite sprite) 
	{
		for (EventBehavior e : chain)
		{
			e.onMouseMove(sprite);
		}
	}

	@Override
	public void onKeyPress(Sprite sprite, KeyCode keyCode) 
	{
		for (EventBehavior e : chain)
		{
			e.onKeyPress(sprite,keyCode);

		}
	}

	@Override
	public void onGameTick(Sprite sprite) 
	{
		for (EventBehavior e : chain)
		{
			e.onGameTick(sprite);
		}
	}

	@Override
	public void onLevelIncrease(Sprite sprite) 
	{
		for (EventBehavior e : chain)
		{
			e.onLevelIncrease(sprite);
		}
	}

	@Override
	public EventBehaviorChain copy() 
	{
		EventBehaviorChain copy = new EventBehaviorChain();
		for (int i = 0; i<chain.size(); i++)
		{
			copy.add(chain.get(i).copy());
		}
		return copy;
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof EventBehaviorChain)
		{
			EventBehaviorChain e = (EventBehaviorChain)o;
			if (e.size() != size())
			{
				//System.out.println("Sizes aren't equal");
				return false;
			}
			for (int i=0; i<size(); i++)
			{
				if (!(get(i).equals(e.get(i))))
				{
					//System.out.println(get(i) + " does not equal " + e.get(i));
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public void setYVelocity(int yvel) 
	{
		for (EventBehavior e : chain )
		{
			if (e instanceof MovementEventBehavior)
			{
				MovementEventBehavior m = (MovementEventBehavior)e;
				m.setYVelocity(yvel);
			}
		}
	}
	public void setXVelocity(int xvel) 
	{
		for (EventBehavior e : chain )
		{
			if (e instanceof MovementEventBehavior)
			{
				MovementEventBehavior m = (MovementEventBehavior)e;
				m.setXVelocity(xvel);
			}
		}
	}
	
	public void flipXVelocity()
	{
		for (EventBehavior e : chain )
		{
			if (e instanceof MovementEventBehavior)
			{
				MovementEventBehavior m = (MovementEventBehavior)e;
				m.setXVelocity(m.getXVelocity() * -1);
			}
		}
	}
	public void flipYVelocity()
	{
		for (EventBehavior e : chain )
		{
			if (e instanceof MovementEventBehavior)
			{
				MovementEventBehavior m = (MovementEventBehavior)e;
				m.setYVelocity(m.getYVelocity() * -1);
			}
		}
	}

	public int getMaxXVelocity() 
	{
		int maxXVelocity = 0;
		for (EventBehavior e : chain )
		{
			if (e instanceof MovementEventBehavior)
			{
				MovementEventBehavior m = (MovementEventBehavior)e;
				int current = m.getXVelocity();
				if (Math.abs(current) > Math.abs(maxXVelocity))
					maxXVelocity = current;
			}
		}
		return maxXVelocity;
	}

	public int getMaxYVelocity() 
	{
		int maxYVelocity = 0;
		for (EventBehavior e : chain )
		{
			if (e instanceof MovementEventBehavior)
			{
				MovementEventBehavior m = (MovementEventBehavior)e;
				int current = m.getYVelocity();
				if (Math.abs(current) > Math.abs(maxYVelocity))
					maxYVelocity = current;
			}
		}
		return maxYVelocity;
	}

	
}
