//@Author Christian Dummer
package views;

import java.util.ArrayList;
import controller.Controller;
import input.KeyPolling;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import sprite.NullSprite;
import sprite.Sprite;
import javafx.stage.WindowEvent;

public class View implements Observer
{
		private Controller controller;
		private Sprite currentlySelectedSprite;
		private PlayerView playerView;
		private MakerView makerView;
		private boolean playing;

		//Displays both views, called by Main.java when program is launched.
		public View(Stage primaryStage) {
			primaryStage.setOnCloseRequest( (WindowEvent event1) ->
			{
				Platform.exit();
				System.exit(0);
			});
			this.makerView = new MakerView(primaryStage, this);
			this.playerView = new PlayerView(this);
			playing = false;
			//By default a null sprite
			currentlySelectedSprite = new NullSprite();
		}
		
		//Just for unit tests
		public View()
		{
			playerView.setGameCanvas(new Canvas());
		}
		
		public PlayerView getPlayerView() {
			return this.playerView;
		}
		
		public MakerView getMakerView() {
			return this.makerView;
		}
		
		public boolean getPlaying() {
			return this.playing;
		}
		
		public void setPlaying(boolean p) {
			this.playing = p;
		}
	
		public void showStages()
		{
			this.makerView.showMaker();
			this.playerView.showPlayer();
		}
		
		public void setController(Controller c) {
			this.controller = c;
		}
	
		public Controller getController() {
			return this.controller;
		}
		
		//sets currentlySelectedSprite
		public void setCurrentlySelectedSprite(Sprite s) {
			this.currentlySelectedSprite = s;
		}
		
		public Sprite getCurrentlySelectedSprite() {
			return this.currentlySelectedSprite;
		}
		
		public void modifySpriteCommand() {
			this.controller.modifySprite(currentlySelectedSprite);
		}
	    
	    public void drawAll()
	    {
	    	playerView.clearCanvas();
	    	ArrayList<Sprite> allSprites = controller.getSpriteList();
	    	for (Sprite s : allSprites)
	    	{
	    		//If sprite is not visible, only make it disappear on game run
	    		if(!s.isVisible() && !playing) {
	    				s.draw(playerView.getGameCanvas().getGraphicsContext2D());
	    		}
	    		//Otherwise always draw if visible
	    		else if(s.isVisible()) {
	    			s.draw(playerView.getGameCanvas().getGraphicsContext2D());
	    		}
	    	}
	    }
	    
	    //Used for dragging currentlySelectedSprite
	    public void drawAllExcept(int spriteId)
	    {
	    	playerView.clearCanvas();
	    	ArrayList<Sprite> allSprites = controller.getSpriteList();
	    	for (Sprite s : allSprites)
	    	{
	    		if (s.getSpriteId() != spriteId)
	    			//If sprite is not visible, only make it disappear on game run
	    			if(!s.isVisible() && !playing) {
	    				s.draw(playerView.getGameCanvas().getGraphicsContext2D());
	    			}
	    			//Otherwise always draw if visible
	    			else if(s.isVisible()) {
	    				s.draw(playerView.getGameCanvas().getGraphicsContext2D());
	    			}
	    	}
	    }
	    
	    
		@Override
		public void update() 
		{
			drawAll();
		}
}

