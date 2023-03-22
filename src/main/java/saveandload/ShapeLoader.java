package saveandload;

import org.json.simple.JSONObject;

//Responsible for loading SaveableShapes and instantiating the correct subtype before calling load and returning the shape
public class ShapeLoader 
{
	public static SaveableShape loadShape(JSONObject json)
	{
		String type = (String)json.get("type");
		
		switch(type)
		{
			case "SaveableRectangle":
				SaveableRectangle rect = new SaveableRectangle();
				rect.load(json);
				return rect;
			case "SaveableEllipse":
				SaveableEllipse ellipse = new SaveableEllipse();
				ellipse.load(json);
				return ellipse;
				
			default:
				System.out.println("ShapeLoader was given a shape it doesn't know how to load: " + type);
				return new SaveableRectangle();
		}
	}
}
