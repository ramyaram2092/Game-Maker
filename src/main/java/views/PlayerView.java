//@Author Christian Dummer
package views;

import java.io.File;
import java.io.IOException;

import input.KeyPolling;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import sprite.Sprite;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class PlayerView {

	private Scene playerScene;
	private Stage playerStage;
	private View view;
	private Color canvasColor;

	//Controls for elements in PlayerView.fxml
	@FXML
	private Button playStopButton;
	@FXML
	private Button undoPauseButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button loadButton;
	@FXML 
	private Canvas gameCanvas;
	public PlayerView(View v) {
		try {
			view = v;
			//Loads and shows the playerView
			playerStage = new Stage();
			playerStage.setOnCloseRequest( (WindowEvent event1) ->
			{
				Platform.exit();
				System.exit(0);
			});
			playerStage.setTitle("Player View");
			FXMLLoader playerWindowLoader = new FXMLLoader();
			playerWindowLoader.setLocation(View.class.getResource("PlayerView.fxml"));
			playerWindowLoader.setController(this);
			AnchorPane playerLayout = (AnchorPane)playerWindowLoader.load();
			playerScene = new Scene(playerLayout);
			playerScene.setOnKeyPressed(new EventHandler<KeyEvent>()
			{
				@Override public void handle(KeyEvent k)
				{
					System.out.println("DHSAJKDSHAKLDAS");
					view.getController().onKeyPress(k);
				}
			});
			playerStage.setScene(playerScene);
			playerStage.setX(905);
			playerStage.setY(50);
			canvasColor = Color.WHITE;
		}
		catch(IOException ioe) {
			System.out.println("Failed to load PlayerView");
		}
	}
	
	public void showPlayer()
	{
		playerStage.show();
	}

	public Scene getScene() {
		return this.playerScene;
	}

	public Stage getStage() {
		return this.playerStage;
	}
	
	public void setGameCanvas(Canvas c) {
		this.gameCanvas = c;
	}
	
	public void setCanvasColor(Color c) {
		this.canvasColor = c;
	}
	public Canvas getGameCanvas() {
		return this.gameCanvas;
	}

			// Event Listener on Button[#playStopButton].onAction
			@FXML
			public void playStopButtonClicked(ActionEvent event) {
				if(playStopButton.getText().equals("Play")) {
					//If play is pressed, switch buttons to Play Context
					playStopButton.setText("Stop");
					undoPauseButton.setText("Pause");
					saveButton.setVisible(false);
					saveButton.setDisable(true);
					loadButton.setVisible(false);
					loadButton.setDisable(true);
					
					view.setPlaying(!view.getPlaying());
					view.getMakerView().getTabPane().setDisable(true);;
					view.getController().play();
				}
				else if(playStopButton.getText().equals("Stop")) {
					//If Stop is pressed, switch buttons back to maker context
					playStopButton.setText("Play");
					undoPauseButton.setText("Undo");
					saveButton.setVisible(true);
					saveButton.setDisable(false);
					loadButton.setVisible(true);
					loadButton.setDisable(false);

					view.setPlaying(!view.getPlaying());
					view.getMakerView().getTabPane().setDisable(false);
					view.getController().stop();
				}
			}
			
			
			
			
			// Event Listener on Button[#undoPauseButton].onAction
			@FXML
			public void undoPauseButtonClicked(ActionEvent event) 
			{
				if(undoPauseButton.getText().equals("Undo")) {
					view.getController().undo();
				}
				else if(undoPauseButton.getText().equals("Pause")) {
					view.getController().pause();
					undoPauseButton.setText("Resume");
				}
				else if(undoPauseButton.getText().equals("Resume")) {
					view.getController().resume();
					undoPauseButton.setText("Pause");
				}
				
			}
			// Event Listener on Button[#saveButton].onAction
			@FXML
			public void saveButtonClicked(ActionEvent event) {
					FileChooser fileChooser = new FileChooser();
					fileChooser.getExtensionFilters().add(new ExtensionFilter("Save Files", "*.json"));
					File file = fileChooser.showSaveDialog(view.getMakerView().getStage());
					if(file != null) {
						view.getController().saveToFile(file.toURI().toString().substring(6));
					}
			}
			// Event Listener on Button[#loadButton].onAction
			@FXML
			public void loadButtonClicked(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				File file = fileChooser.showOpenDialog(view.getMakerView().getStage());
				if(file != null) {
					view.getController().loadFromFile(file.toURI().toString().substring(6));
				}
			}
			
			//On the canvas clicked, check to see if the click intersects with a sprite's hitbox, and if so make it the currently
			//selected Sprite.
			@FXML
			public void canvasClicked(MouseEvent event) {
				if(!view.getPlaying()) {
					double clickedX = event.getX();
					double clickedY = event.getY();
					for(Sprite s: view.getController().getSpriteList()) {
						if(clickedX >= s.getHitBox().getTopLeft().getX() && clickedY >= s.getHitBox().getTopLeft().getY()) {
							if(clickedX <= s.getHitBox().getBottomRight().getX() && clickedY <= s.getHitBox().getBottomRight().getY()) {
								//If click is within the hitbox, then make it currently selected sprite and adjust the properties pane;
								view.setCurrentlySelectedSprite(s);
								view.getMakerView().setPanesForCurrentlySelectedSprite();
							}
						}
					}
				}
			}
			
			public void clearCanvas()
			{
				gameCanvas.getGraphicsContext2D().setFill(canvasColor);
				gameCanvas.getGraphicsContext2D().fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
			}
			
			//When the canvas is dragged, get the sprite and adjust it's x/y
			@FXML 
			public void canvasDragged(MouseEvent event) {
				//TODO This is inefficient, but without a check for the new currently selected sprite it bugs out
				if(!view.getPlaying()) {
					//canvasClicked(event);
					Sprite currentlySelectedSprite = view.getCurrentlySelectedSprite();
					//Get the events x/y and set it to the sprite
					double newX = event.getX() - (currentlySelectedSprite.getAppearance().getWidth() * .5);
					double newY = event.getY() - (currentlySelectedSprite.getAppearance().getHeight() * .5);
					currentlySelectedSprite.setX(newX);
					currentlySelectedSprite.setY(newY);
					view.drawAllExcept(currentlySelectedSprite.getSpriteId());
					currentlySelectedSprite.draw(gameCanvas.getGraphicsContext2D());
				}
			}
			
			//When the canvas is released, update the sprite
			@FXML
			public void canvasReleased(MouseEvent event)
			{
				if(!view.getPlaying()) {
					view.modifySpriteCommand();
				}
			}
			
	
}
