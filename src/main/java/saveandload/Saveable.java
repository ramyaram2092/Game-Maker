package saveandload;

import org.json.simple.JSONObject;

//Everything which we can save will be Saveable
public interface Saveable 
{
	//returns a JSONObject with all of the saveable's contents
	//as well as a string entry mapping "type" to a string of the class's name for the save/load manager's sake
	//basic data types are saved directly, other member variables need to be saveable and save() will be called
	public JSONObject save();
	
	//restores a saveable's state by overwriting all of its member variables with the ones found in saveJSON
	//to load, instantiate deafult saveable, then call load, passing the json - > Sprite s = new Sprite().load(saveJSON);
	public void load(JSONObject saveJSON);
}
