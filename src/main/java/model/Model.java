package model;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import command.Command;
import command.CommandInvoker;
import constants.Constants;
import controller.Controller;
import javafx.scene.input.KeyEvent;
import views.Observer;
import saveandload.SaveAndLoadManager;
import saveandload.Saveable;
import sprite.Sprite;
import sprite.SpriteManager;

public class Model 
{
	private SpriteManager spriteManager;
	private SaveAndLoadManager saveAndLoadManager;
	private String saveFilePath;
	private CommandInvoker commandInvoker;
	private ArrayList<Observer> observers;

	public Model()
	{
		spriteManager = new SpriteManager();
		saveAndLoadManager = new SaveAndLoadManager();
		saveFilePath = Constants.DEFAULT_SAVE_FILE_PATH;
		commandInvoker = new CommandInvoker();
		observers = new ArrayList<>();
	}
	//Adds a sprite to the sprite manager, returns its assigned spriteId
	public int addSprite(Sprite sprite)
	{
		int returnVal =  spriteManager.addCopy(sprite);
		notifyObservers();
		return returnVal;
	}
	
	public void receiveCommand(Command c)
	{
		commandInvoker.receiveCommand(c);
	}
	
	//pass this method a sprite which has been changed by the user
	//since the view never receives a direct reference to the sprite,
	//we now need to overwrite the one which is in the spritemanager with the changed sprite
	public void modifySprite(Sprite sprite)
	{
		spriteManager.modifySprite(sprite);
		notifyObservers();
	}
	
	//Returns a copy of the sprite stored at spriteId in the spritemanager
	public Sprite getSprite(int spriteId)
	{
		return spriteManager.get(spriteId);
	}
	
	//Removes the sprite if the manager contains an entry at its id
	public void removeSprite(int spriteId)
	{
		spriteManager.remove(spriteId);
		notifyObservers();
	}
	
	//get an arraylist of all sprites in the manager
	public ArrayList<Sprite> getSpriteList()
	{
		return spriteManager.getSpriteList();
	}
	
	//replace the sprite manager with a new one
	public void resetSpriteManager()
	{
		spriteManager = new SpriteManager();
		notifyObservers();
	}
	
	//replace the save/load manager with a new one
	public void resetSaveAndLoadManager()
	{
		saveAndLoadManager = new SaveAndLoadManager();
		notifyObservers();
	}
	
	//Save all sprites, write them to the file stored at saveFilePath
	//Catch the exception in controller
	public void save() throws IOException
	{
		resetSaveAndLoadManager();
		ArrayList<Sprite> spriteList = spriteManager.getSpriteList();
		ArrayList<Saveable> saveableList = new ArrayList<>();
		for (Sprite s : spriteList)
		{
			saveableList.add((Saveable)s);
		}
		saveAndLoadManager.addAll(saveableList);
		saveAndLoadManager.saveFile(saveFilePath);
	}

	//Save all sprites, write them to the file stored at saveFilePath
	//Catch the exception in controller
	public void saveToFile(String filePath) throws IOException
	{
		resetSaveAndLoadManager();
		ArrayList<Sprite> spriteList = spriteManager.getSpriteList();
		ArrayList<Saveable> saveableList = new ArrayList<>();
		for (Sprite s : spriteList)
		{
			saveableList.add((Saveable)s);
		}
		saveAndLoadManager.addAll(saveableList);
		saveAndLoadManager.saveFile(filePath);
	}
	
	//Load all sprites from the file at saveFilePath
	//Catch the exceptions in controller
	//IOExeption : file can't be found
	//ParseException : JSON is bad
	public void loadFromFile(String filePath) throws IOException, ParseException
	{
		resetSaveAndLoadManager();
		saveAndLoadManager.loadFile(filePath);
		ArrayList<Sprite> spriteList = saveAndLoadManager.getSprites();
//		for (int i = 0; i<spriteList.size(); i++)
//		{
//			System.out.println(spriteList.get(i));
//		}
		resetSpriteManager();
		spriteManager.addAll(spriteList);
		notifyObservers();
	}
	
	//stash the model as a JSON - will be called when the controller switches contexts
	public JSONObject stash()
	{
		resetSaveAndLoadManager();
		ArrayList<Sprite> spriteList = spriteManager.getSpriteList();
		ArrayList<Saveable> saveableList = new ArrayList<>();
		for (Sprite s : spriteList)
		{
			saveableList.add((Saveable)s);
		}
		saveAndLoadManager.addAll(saveableList);
		return saveAndLoadManager.save();
	}
	
	//restore the model from a stashed JSON - called when the controller switches contexts
	public void restore(JSONObject stashedJSON)
	{
		resetSaveAndLoadManager();
		saveAndLoadManager.load(stashedJSON);
		ArrayList<Sprite> spriteList = saveAndLoadManager.getSprites();
		resetSpriteManager();
		spriteManager.addAll(spriteList);
		notifyObservers();
	}
	
	//Load all sprites from the file at saveFilePath
	//Catch the exceptions in controller
	//IOExeption : file can't be found
	//ParseException : JSON is bad
	public void load() throws IOException, ParseException
	{
		resetSaveAndLoadManager();
		saveAndLoadManager.loadFile(saveFilePath);
		ArrayList<Sprite> spriteList = saveAndLoadManager.getSprites();
//		for (int i = 0; i<spriteList.size(); i++)
//		{
//			System.out.println(spriteList.get(i));
//		}
		resetSpriteManager();
		spriteManager.addAll(spriteList);
		notifyObservers();
	}
	
	public String getSaveFilePath()
	{
		return saveFilePath;
	}
	
	public void setSaveFilePath(String path)
	{
		saveFilePath = path;
		notifyObservers();
	}

	//Return the number of sprites in the system
	public int getNumberOfSprites()
	{
		return spriteManager.getSize();
	}
	
	public void undo()
	{
		//undo the previous command in the commandInvoker
		commandInvoker.undo();
		notifyObservers();
	}

	public void registerObserver(Observer observer) 
	{
		observers.add(observer);
	}
	
	public void notifyObservers()
	{
		for (Observer o : observers)
		{
			o.update();
		}
	}
	public SpriteManager getSpriteManager() 
	{
		return spriteManager;
	}
	public void onGameTick() 
	{
		ArrayList<Sprite> spriteList = spriteManager.getDirectReferenceSpriteList();
		for (Sprite s : spriteList)
		{
			s.onGameTick();
		}
	}
	public void onKeyPress(KeyEvent k) {
		ArrayList<Sprite> spriteList = spriteManager.getDirectReferenceSpriteList();
		for (Sprite s : spriteList)
		{
			s.onKeyPress(k);
		}
	}
}