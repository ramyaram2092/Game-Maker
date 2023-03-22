package controller;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import constants.Constants;
import pattern.Observable;
import pattern.Observer;

public class GameClock extends TimerTask implements Observable 
{
	private int ticks;
	private double msBetweenTicks;
	private ArrayList<Observer> observers;
	
	public GameClock()
	{
		ticks = 0;
		msBetweenTicks = Constants.MS_BETWEEN_TICKS;
		observers = new ArrayList<>();
	}
	
	@Override
	public void register(Observer observer) 
	{
		observers.add(observer);
	}

	@Override
	public void unregister(Observer observer) 
	{
		observers.remove(observer);
	}

	public void tick() 
	{
		ticks++;
		notifyObservers();
	}

	@Override
	public void run() 
	{
		tick();
	}
	
	public double getMsBetweenTicks()
	{
		return msBetweenTicks;
	}
	
	public void setMsBetweenTicks(double tps)
	{
		msBetweenTicks = tps;
	}
	
	public int getTicks()
	{
		return ticks;
	}

	@Override
	public void notifyObservers() 
	{
		for (Observer o: observers)
		{
			o.update();
		}
	}

}
