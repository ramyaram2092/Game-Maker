package saveandload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sprite.Sprite;

public class SaveAndLoadManager {
	private ArrayList<Saveable> saveObjects;

	public SaveAndLoadManager() {
		saveObjects = new ArrayList<>();
	}

	public void addAll(ArrayList<Saveable> saveableList) {
		saveObjects.addAll(saveableList);
	}

	public ArrayList<Saveable> getSaveObjects() {
		return saveObjects;
	}

	public ArrayList<Sprite> getSprites() {
		ArrayList<Sprite> sprites = new ArrayList<>();
		for (Saveable s : saveObjects) {
			if (s instanceof Sprite) {
				sprites.add((Sprite) s);
			}
		}
		return sprites;
	}

	@SuppressWarnings("unchecked")
	public JSONObject save() {
		JSONObject json = new JSONObject();
		json.put("size", saveObjects.size());

		for (int i = 0; i < saveObjects.size(); i++) {
			json.put(i, saveObjects.get(i).save());
		}
		return json;
	}

	public void load(JSONObject json) 
	{
		saveObjects.clear();
		int size = 0;
		try
		{
			size = ((Long) json.get("size")).intValue();
			for (Integer i = 0; i < size; i++) 
			{
				JSONObject entry = (JSONObject) json.get(i.toString());
				String type = (String) entry.get("type");
				switch (type) {
				case "Sprite":
					Sprite s = new Sprite();
					s.load(entry);
					saveObjects.add((Saveable) s);
					break;
				default:
					System.out.println(type);
					System.out.println("Tried to load something the save/load manager doesn't recognize");
				}
			}
		}catch(ClassCastException e)
		{
			size = (int)json.get("size");
			for (Integer i = 0; i < size; i++) 
			{
				JSONObject entry = (JSONObject) json.get(i);
				String type = (String) entry.get("type");
				switch (type) {
				case "Sprite":
					Sprite s = new Sprite();
					s.load(entry);
					saveObjects.add((Saveable) s);
					break;
				default:
					System.out.println(type);
					System.out.println("Tried to load something the save/load manager doesn't recognize");
				}
			}
		}
//		System.out.println("SaveObjects:");
//		for (int i = 0; i < saveObjects.size(); i++)
//		{
//			System.out.println(saveObjects.get(i));
//		}
	}

	public void saveFile(String saveFilePath) throws IOException
	{
		String jsonString = save().toString();
		File saveFile = new File(saveFilePath);
		
		
		//If the file doesn't exist, create it
		//if it already exists, this does nothing (really it returns false but we're not storing the return value)
		saveFile.createNewFile();

															//false to make it overwrite instead of append
		FileOutputStream fos = new FileOutputStream(saveFile,false);
		fos.write(jsonString.getBytes());
		fos.close();
	}
	
	public void loadFile(String saveFilePath) throws IOException, ParseException
	{
		byte[] encoded = Files.readAllBytes(Paths.get(saveFilePath));
		String jsonString = new String(encoded, Charset.defaultCharset());
		
		JSONParser parser = new JSONParser();  
		JSONObject json = (JSONObject) parser.parse(jsonString); 	
		load(json);
	}
		
}
