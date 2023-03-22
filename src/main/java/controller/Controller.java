package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import command.Command;
import command.CreateSpriteCommand;
import command.DeleteSpriteCommand;
import command.DuplicateSpriteCommand;
import command.ModifySpriteCommand;
import constants.Constants;
import input.KeyPolling;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.Model;
import pattern.Observer;
import sprite.Sprite;
import sprite.SpriteManager;
import views.View;
/**
 * @author ramya
 * The following class is responsible for creating commands and 
 * passing them to the model for every event which can occur in the view
 */



//Controller will observe the gameclock
public class Controller implements Observer
{
	private Model model;
	private JSONObject modelStash;
	private View view;
	private GameClock gameClock;
	private Timer timer;
	private CollisionManager collisionManager;
	 
	 //constructor
	 public Controller(Model m, View v)
	 {
			model = m;
			view = v;
			collisionManager = new CollisionManager();
			gameClock = new GameClock();
			gameClock.register(this);
			timer = new Timer();
	 }  
	 
	 public Controller() 
	 {
			collisionManager = new CollisionManager();
			gameClock = new GameClock();
			gameClock.register(this);
			timer = new Timer();
	 } 
	 

	// It creates the "create sprite" command and passes it to the commandInvoker 
	 public void createSprite()
	 {
		 Command createSprite=new CreateSpriteCommand(model);
		 model.receiveCommand(createSprite);
	 }
	 
	 //Creates the duplicate sprite command and passes to commandInvoker
	 public void duplicateSprite(Sprite s) {
		 Command duplicateSprite = new DuplicateSpriteCommand(model, s);
		 model.receiveCommand(duplicateSprite);
	 }
	 
	 //returns true on success
	 //false on IOException (bad filepath)
	 public boolean save()
	 {
		 try 
		 {
			model.save();
		 } 
		 catch (IOException e) 
		 {
			 return false;
		 }
		 return true;
	 }
	 
	 //returns true on success
	 //false on IOException (bad filepath)
	 public boolean saveToFile(String filePath)
	 {
		 try 
		 {
			model.saveToFile(filePath);
		 } 
		 catch (IOException e) 
		 {
			 System.out.println(e);
			 return false;
		 }
		 return true;
	 }
	 
	 public ArrayList<Sprite> getSpriteList()
	 {
		 return model.getSpriteList();
	 }
	 
	 //Stash the model, switch contexts to play mode, start the clock
	 public void play()
	 {
		 modelStash = model.stash();
		 gameClock = new GameClock();
		 gameClock.register(this);
		 timer = new Timer();
		 timer.schedule(gameClock, (long)0.0, (long)gameClock.getMsBetweenTicks());
	
	 }
	 
	 //Stop the clock
	 public void pause()
	 {
		 timer.cancel();
	 }
	 
	 //resume the clock
	 public void resume()
	 {
		 gameClock = new GameClock();
		 gameClock.register(this);
		 timer = new Timer();
		 timer.schedule(gameClock, (long)0.0, (long)gameClock.getMsBetweenTicks());
	 }
	 
	 //Stop the clock, restore the stashed model, switch contexts to create mode
	 public void stop()
	 {
		 timer.cancel();
		 model.restore(modelStash);
	 }
	 
	 public void modifySprite(Sprite modifiedSprite)
	 {
		 ModifySpriteCommand modifyCommand = new ModifySpriteCommand(model, modifiedSprite);
		 model.receiveCommand(modifyCommand);
	 }
	 
	 public void undo()
	 {
		 model.undo();
	 }

	public void setModel(Model m) 
	{
		model = m;
	}
	

	 public void setView(View view) {
		 this.view = view;
	 }
	 
	 public View getView() {
		 return this.view;
	 }

	public Sprite getSprite(int spriteId) 
	{
		return model.getSprite(spriteId);
	}

	//Returns constants depending on success
	// 0 for success
	// 1 for IOError (bad filepath)
	// 2 for ParseError (corrupted JSON)
	public int load() 
	{
		try
		{
			model.load();
			return Constants.LOAD_SUCCESS;
		}
		catch(IOException e)
		{
			return Constants.LOAD_BADFILE;
		}
		catch(ParseException e)
		{
			return Constants.LOAD_BADJSON;
		}

	}

	//Returns constants depending on success
	// 0 for success
	// 1 for IOError (bad filepath)
	// 2 for ParseError (corrupted JSON)
	public int loadFromFile(String filePath) 
	{
		try
		{
			model.loadFromFile(filePath);
			return Constants.LOAD_SUCCESS;
		}
		catch(IOException e)
		{
			return Constants.LOAD_BADFILE;
		}
		catch(ParseException e)
		{
			return Constants.LOAD_BADJSON;
		}

	}

	public GameClock getClock() 
	{
		return gameClock;
	}
	
	public void addWalls() {
		//Create and modify the leftWall
		createSprite();
		Sprite leftWall = getSpriteList().get(getSpriteList().size() - 1);
		leftWall.setHeight(Constants.CANVAS_HEIGHT);
		leftWall.setWidth(5);
		leftWall.getAppearance().setColor(Color.BLACK);
		modifySprite(leftWall); 
		
		//Create and modify the topWall
		createSprite();
		Sprite topWall = getSpriteList().get(getSpriteList().size() - 1);
		topWall.setHeight(5);
		topWall.setWidth(Constants.SCREEN_WIDTH);
		topWall.getAppearance().setColor(Color.BLACK);
		modifySprite(topWall);
		
		//Create and modify the rightWall
		createSprite();
		Sprite rightWall = getSpriteList().get(getSpriteList().size() - 1);
		rightWall.setX(Constants.SCREEN_WIDTH - 5);
		rightWall.setHeight(Constants.CANVAS_HEIGHT);
		rightWall.setWidth(5);
		rightWall.getAppearance().setColor(Color.BLACK);
		modifySprite(rightWall);
		
		//Create and modify bottomWall
		createSprite();
		Sprite bottomWall = getSpriteList().get(getSpriteList().size() - 1);
		bottomWall.setY(Constants.CANVAS_HEIGHT - 5);
		bottomWall.setHeight(5);
		bottomWall.setWidth(Constants.SCREEN_WIDTH);
		bottomWall.getAppearance().setColor(Color.BLACK);
		modifySprite(bottomWall);
		
		model.notifyObservers();
	}

	@Override
	public void update() 
	{
		model.onGameTick();
		collisionManager.handleAllCollisions(model.getSpriteManager());
		model.notifyObservers();
	}

	public void onKeyPress(KeyEvent k) 
	{
		model.onKeyPress(k);
	}

	public void deleteSprite(int spriteId) 
	{
		DeleteSpriteCommand c = new DeleteSpriteCommand(spriteId,model);
		model.receiveCommand(c);
	}	
	    	   

}
