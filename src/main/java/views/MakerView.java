//@Author Christian Dummer
package views;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import behaviors.collision.BounceCollisionBehaviorX;
import behaviors.collision.BounceCollisionBehaviorXY;
import behaviors.collision.BounceCollisionBehaviorY;
import behaviors.collision.CollisionBehavior;
import behaviors.collision.CustomCollisionPair;
import behaviors.collision.DestroyCollisionBehavior;
import behaviors.collision.DoNothingCollisionBehavior;
import behaviors.collision.PacManRandomCollisionBehaviour;
import behaviors.event.ChangeDirectionBehavior;
import behaviors.event.EventBehavior;
import behaviors.event.EventBehaviorChain;
import behaviors.event.FroggerMovementBehavior;
import behaviors.event.MoveOnGameTickBehavior;
import behaviors.event.MovementEventBehavior;
import behaviors.event.PacManEventBehaviours;
import behaviors.event.SpawnBehavior;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.GameMaker;
import saveandload.SaveableEllipse;
import saveandload.SaveableRectangle;
import saveandload.SaveableShape;
import sprite.HitboxView;
import sprite.Sprite;

public class MakerView {


	private Scene makerScene;
	private Stage makerStage;
	private View view;
	public MakerView(Stage primaryStage, View v) {
		try {
			view = v;
			//Loads and shows the makerView
			makerStage = primaryStage;
			makerStage.setTitle("Maker View");
			FXMLLoader windowLoader = new FXMLLoader();
			windowLoader.setLocation(View.class.getResource("MakerView.fxml"));
			windowLoader.setController(this);
			AnchorPane makerLayout = (AnchorPane)windowLoader.load();
			makerScene = new Scene(makerLayout);
			makerStage.setScene(makerScene);
			makerStage.setX(300);
			makerStage.setY(50);
		}
		catch(IOException ioe) {
			System.out.println("Failed to load MakerView");
		}
	}
	
	public Scene getScene() {
		return this.makerScene;
	}
	
	public Stage getStage() {
		return this.makerStage;
	}
	
	public TabPane getTabPane() {
		return this.tabPane;
	}
	
	public void initializeUIElements() {
		
		//Initializes slider layouts since there is no ChangeListener in fxml
		spriteWidthSlider.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				widthSliderChanged();
			}
		});
		spriteHeightSlider.valueProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				heightSliderChanged();
				
			}
			
		});
		hitboxWidthSlider.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				hitboxWidthSliderChanged();
			}
		});
		hitboxHeightSlider.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				hitboxHeightSliderChanged();
			}
		});
		
		
		//Populate dropdown menus
		spriteShapeDropdown.getItems().add(new SaveableRectangle());
		spriteShapeDropdown.getItems().add(new SaveableEllipse());
		
		spriteBehaviorTypeDropdown.getItems().add("On Click Behavior");
		spriteBehaviorTypeDropdown.getItems().add("On Key Press Behavior");
		spriteBehaviorTypeDropdown.getItems().add("On Game Tick Behavior");
		
		//TODO add behaviors as they are created to proper views
     	timeBehaviorActions.getItems().add(new MoveOnGameTickBehavior());
     	timeBehaviorActions.getItems().add(new SpawnBehavior());
     	
     	
     	keyBehaviorAction.getItems().add(new FroggerMovementBehavior());
     	keyBehaviorAction.getItems().add(new ChangeDirectionBehavior());
		
		collisionBehaviorAction.getItems().add(new BounceCollisionBehaviorX());
		collisionBehaviorAction.getItems().add(new BounceCollisionBehaviorY());
		collisionBehaviorAction.getItems().add(new BounceCollisionBehaviorXY());
		collisionBehaviorAction.getItems().add(new DestroyCollisionBehavior());
		collisionBehaviorAction.getItems().add(new DoNothingCollisionBehavior());
		collisionBehaviorAction.getItems().add(new PacManRandomCollisionBehaviour());
		
	}
	
	public void showMaker()
	{
		makerStage.show();
		initializeUIElements();
	}
	
	//Controls for elements in MakerView.fxml
			//Root Anchor Pane
		    @FXML
		    private AnchorPane makerPane;
		    @FXML
		    private TabPane tabPane;
		    //Sprite Behavior tab fields

		    @FXML
		    private AnchorPane spriteBehaviorEditPane;

		    @FXML
		    private AnchorPane spriteBehaviorEditPane1;

		    @FXML
		    private ScrollPane spriteBehaviorList;

		    @FXML
		    private ComboBox<String> spriteBehaviorTypeDropdown;

		    @FXML
		    private Button spriteChooseImageButton;

		    @FXML
		    private ColorPicker spriteColorPicker;

		    @FXML
		    private Slider spriteHeightSlider;
		    
		    @FXML
		    private Slider hitboxHeightSlider;
		    
		    @FXML
		    private Slider hitboxWidthSlider;

		    @FXML
		    private Label spriteIdLabel;

		    @FXML
		    private ComboBox<SaveableShape> spriteShapeDropdown;

		    @FXML
		    private Slider spriteWidthSlider;

		    @FXML
		    private Label spriteXLabel;

		    @FXML
		    private Label spriteYLabel;
		  
		    @FXML
		    private Button createNewSpriteButton;

		    @FXML
		    private Button duplicateSpriteButton;
		    
		    @FXML
		    private CheckBox visibleCheckbox;
		    
		    @FXML
		    private CheckBox disableCheckbox;

		    
		    //Fields for the timed behavior pane
		    @FXML
		    private AnchorPane timeBehaviorPane;
		 
		    @FXML 
		    private TextField velocityXInput;
		   
		    @FXML
		    private TextField velocityYInput;
		    
		    @FXML
		    private TextField intervalInput;
		    
		    @FXML
		    private TextField spriteIdSpawnInput;
		    
		    @FXML
		    private ComboBox<EventBehavior> timeBehaviorActions;
		    
		    @FXML
		    private Button addTimedBehaviorButton;
		    
		    @FXML
		    private Button deleteSpriteButton;
		    
		   
		    
		    //Fields for the mouse behavior pane
		    @FXML
		    private AnchorPane mouseBehaviorPane;
		    
		    @FXML
		    private ComboBox<EventBehavior> clickBehaviorAction;
		    
		    @FXML
		    private Button addClickBehaviorButton;
		    
		    //Fields for the key behavior pane
		    @FXML
		    private AnchorPane keyBehaviorPane;
		    
		    @FXML
		    private ComboBox<EventBehavior> keyBehaviorAction;
		    
		    @FXML
		    private Button addKeyBehaviorButton;
		    
		    //Fields for the collision behavior tab
		    @FXML
		    private Button newCollisionBehaviorButton;
		   
		    @FXML
		    private ComboBox<CollisionBehavior> collisionBehaviorAction;
		    
		    @FXML 
		    private TextField spriteIdInput;
		    
		    @FXML
		    private Label collisionSpriteIdLabel;
		    
		    //Fields for the Game Properties tab

		    @FXML
		    private Button imageBackgroundButton;
		    @FXML
		    private ColorPicker backgroundColorPicker;
		    @FXML
		    private CheckBox usesLevelsCheckbox;
		    @FXML 
		    private ScrollPane collisionBehaviorList;
		    
		    @FXML
		    public void addBehaviorButtonClicked(ActionEvent event) {
		    	try {
			    	if(event.getSource().equals(addClickBehaviorButton)) {
			    		view.getCurrentlySelectedSprite().addEventBehavior(clickBehaviorAction.getValue());
			    		setPanesForCurrentlySelectedSprite();
			    	}
			    	else if(event.getSource().equals(addKeyBehaviorButton)) {
			    		view.getCurrentlySelectedSprite().addEventBehavior(keyBehaviorAction.getValue());
			    		view.modifySpriteCommand();
			    		setPanesForCurrentlySelectedSprite();
			    	}
			    	else if(event.getSource().equals(addTimedBehaviorButton)) {
			    		//TODO
			    		EventBehavior toAdd = timeBehaviorActions.getValue();
			    		if(toAdd instanceof MovementEventBehavior) {
			    			((MovementEventBehavior) toAdd).setXVelocity(Integer.parseInt(velocityXInput.getText()));
			    			((MovementEventBehavior) toAdd).setYVelocity(Integer.parseInt(velocityYInput.getText()));
			    		}
			    		if(toAdd instanceof SpawnBehavior) {
			    			int spriteID = Integer.parseInt(spriteIdSpawnInput.getText());
			    			toAdd = new SpawnBehavior(GameMaker.getModel(), spriteID);

			    			((SpawnBehavior) toAdd).setTimeInterval(Integer.parseInt(intervalInput.getText()));
			    		//	((SpawnBehavior) toAdd).setSpawnX((int)view.getController().getSprite(spriteID).getX());
			    		//	((SpawnBehavior) toAdd).setSpawnY((int)view.getController().getSprite(spriteID).getY());
			    			
			    		}
			    		view.getCurrentlySelectedSprite().addEventBehavior(toAdd);
			    		view.modifySpriteCommand();
			    		setPanesForCurrentlySelectedSprite();
			    	}
			    }
		    	catch(Exception ex) {
		    		
		    	}

		    }
		    
		    @FXML
		    private void deleteSpriteButtonClicked(ActionEvent event)
		    {
		    		view.getController().deleteSprite(view.getCurrentlySelectedSprite().getSpriteId());
		    }
		    
		    private void setBehaviorsPane() {
		    	Text behaviors = new Text();
		    	EventBehaviorChain behaviorsList = view.getCurrentlySelectedSprite().getEventBehaviorChain();
		    	for(int i = 0; i < behaviorsList.size(); i++) {
		    		String collisionEntry = behaviorsList.get(i).toString();
		    		behaviors.setText(behaviors.getText() + "\n" + collisionEntry);
		    	}
		    	spriteBehaviorList.setContent(behaviors);
		    }

		    @FXML
		    public void addCollisionBehaviorButtonClicked(ActionEvent event) {
		    	try {
		    		int spriteId = Integer.parseInt(spriteIdInput.getText());
		    		CollisionBehavior toAdd = collisionBehaviorAction.getValue();
		    		view.getCurrentlySelectedSprite().addCustomCollision(spriteId, toAdd);
		    		view.modifySpriteCommand();
		    		setCollisionBehaviorsPane();
		    	}
		    	catch(Exception ex) {
		    		System.out.println("A field is missing or invalid");
		    	}
		    }

		    @FXML
		    public void defaultCollisionBehaviorButtonClicked(ActionEvent event) {
		    	try {
		    		CollisionBehavior toAdd = collisionBehaviorAction.getValue();
		    		view.getCurrentlySelectedSprite().setDefaultCollisionBehavior(toAdd);
		    		view.modifySpriteCommand();
		    		setCollisionBehaviorsPane();
		    	}
		    	catch(Exception ex) {
		    		System.out.println(ex);
		    		System.out.println("A field is missing or invalid");
		    	}
		    }
		    
		    private void setCollisionBehaviorsPane() {
		    	Text collisions = new Text();
		    	ArrayList<CustomCollisionPair> collisionsList = view.getCurrentlySelectedSprite().getCustomCollisionPairs();
		    	for(CustomCollisionPair c: collisionsList) {
		    		String collisionEntry = c.getCollisionBehavior().toString() + " Colliding with Sprite ID: " + Integer.toString(c.getSpriteId());
		    		collisions.setText(collisions.getText() + "\n" + collisionEntry);
		    	}
		    	String defaultCollisionBehavior = view.getCurrentlySelectedSprite().getDefaultCollisionBehavior().toString() + " On any Default Collision";
		    	collisions.setText(defaultCollisionBehavior + " \n" + collisions.getText());
		    	collisionBehaviorList.setContent(collisions);
		    }

		    
		    @FXML
		    //@author Ramya 
		    // Requests the controller to add new sprite 
		    public void createSpriteButtonClicked(ActionEvent event) 
		    {
		    		view.getController().createSprite();
		    	 	view.setCurrentlySelectedSprite(view.getController().getSpriteList().get(view.getController().getSpriteList().size() - 1));
		    	 	setPanesForCurrentlySelectedSprite();
		    }

		    @FXML
		    public void duplicateSpriteButtonClicked(ActionEvent event) {
		    		view.getController().duplicateSprite(view.getCurrentlySelectedSprite().copy());
		    		view.setCurrentlySelectedSprite(view.getController().getSpriteList().get(view.getController().getSpriteList().size() - 1));
		    		setPanesForCurrentlySelectedSprite();
		    }
		    
		    @FXML
		    public void disableCheckboxClicked(ActionEvent event) {
		    	view.getCurrentlySelectedSprite().setEnabled(!view.getCurrentlySelectedSprite().isEnabled());
		    	view.modifySpriteCommand();
		    }
		    
		    @FXML
		    public void visibleCheckboxClicked(ActionEvent event) {
		    	view.getCurrentlySelectedSprite().setVisible(!view.getCurrentlySelectedSprite().isVisible());
		    	view.modifySpriteCommand();
		    }
		    
		    @FXML
		    void spriteBehaviorTypeSelected(ActionEvent event) {
		    	timeBehaviorPane.setVisible(false);
		    	timeBehaviorPane.setDisable(true);
		    	mouseBehaviorPane.setVisible(false);
		    	mouseBehaviorPane.setDisable(true);
		    	keyBehaviorPane.setVisible(false);
		    	keyBehaviorPane.setDisable(true);
		    	
		    	switch(spriteBehaviorTypeDropdown.getSelectionModel().getSelectedItem()) {
		    	case "On Game Tick Behavior": timeBehaviorPane.setVisible(true); timeBehaviorPane.setDisable(false); break;
		    	case "On Click Behavior": mouseBehaviorPane.setVisible(true); mouseBehaviorPane.setDisable(false); break;
		    	case "On Key Press Behavior": keyBehaviorPane.setVisible(true); keyBehaviorPane.setDisable(false); break;
		    	}
		    }

		    @FXML
		    public void spriteAppearanceSelected(ActionEvent event) {
		    	if(event.getSource().equals(spriteShapeDropdown)) {
		    		//Stores all the width height information and sets it after the new shape is created, as setting  a new
		    		//shape to the sprites appearance defaults the values
		    		Sprite current  = view.getCurrentlySelectedSprite();
		    		double positionX = current.getX();
		    		double positionY = current.getY();
		    		double currentWidth = current.getAppearance().getWidth();
		    		double currentHeight = current.getAppearance().getHeight();
		    		double hitboxWidth = current.getHitBox().getWidth();
		    		double hitboxHeight = current.getHitBox().getHeight();
		    
		    		//Change all of the sprite's properties to match what it was before the change, with only the shape changing
		    		current.getAppearance().setShape(spriteShapeDropdown.getValue());
		    		current.setX(positionX);
		    		current.setY(positionY);
		    		current.getAppearance().setWidth(currentWidth);
		    		current.getAppearance().setHeight(currentHeight);
		    		current.getHitBox().setWidth(hitboxWidth);
		    		current.getHitBox().setHeight(hitboxHeight);
		    		
		    		//Set the currentSprite to this modified sprite,
		    		view.setCurrentlySelectedSprite(current);
		    		//tell model to update it's sprite to this newly edited one
		    		view.modifySpriteCommand();
		    	}
		    	else if(event.getSource().equals(spriteChooseImageButton)) {
		    		FileChooser fileChooser = new FileChooser();
		    		File file = fileChooser.showOpenDialog(makerStage);
					if (file != null) {
						view.getCurrentlySelectedSprite().getAppearance().setImage(file.toURI().toString());
						view.modifySpriteCommand();
					}
		    	}
		    }

		    @FXML
		    public void spriteColorSelected(ActionEvent event) {
		    	boolean modifyNeeded = !(view.getCurrentlySelectedSprite().getAppearance().getColor().equals(spriteColorPicker.getValue()));
		    	view.getCurrentlySelectedSprite().getAppearance().setColor(spriteColorPicker.getValue());
		    	if (modifyNeeded)
		    		view.modifySpriteCommand();
		    }

		    
		    //Controls for game properties
		    
		    @FXML 
		    public void backgroundColorPicked(ActionEvent event){
		    	Color c = backgroundColorPicker.getValue();
		    //	makerPane.setStyle( "-fx-background-color: #" + c.toString().substring(2, 8) + ";");
		    	view.getPlayerView().setCanvasColor(c);
		    	view.drawAll();
		    }
		    
		    @FXML
		    public void imageBackgroundButtonClicked(ActionEvent event) {
		    	FileChooser fileChooser = new FileChooser();
	    		File file = fileChooser.showOpenDialog(makerStage);
				if (file != null) {
					makerPane.setStyle("-fx-background-image: url(" + file.toURI().toString() + ");");
				}
		    }
		    
		    public void usesLevelsSelected(ActionEvent event) {
		    	//TODO uses levels
		    	
		    }

		    @FXML
		    public void usesWallsSelected(ActionEvent event) {
		    	//Spawn four sprites in the walls
		    	view.getController().addWalls();
		    }

		    
		    public void heightSliderChanged() {
		    	boolean modifyNeeded = !(view.getCurrentlySelectedSprite().getAppearance().getHeight() == spriteHeightSlider.getValue());
		    	view.getCurrentlySelectedSprite().setHeight(spriteHeightSlider.getValue());
		    	if (modifyNeeded)
		    		view.modifySpriteCommand();
		    }
		    
		    public void widthSliderChanged() {
		    	boolean modifyNeeded = !(view.getCurrentlySelectedSprite().getAppearance().getWidth() == spriteWidthSlider.getValue());
		    	view.getCurrentlySelectedSprite().setWidth(spriteWidthSlider.getValue());
		    	if (modifyNeeded)
		    		view.modifySpriteCommand();
		    }
		    
		    public void hitboxWidthSliderChanged() {
		    	boolean modifyNeeded = !(view.getCurrentlySelectedSprite().getHitBox().getWidth() == hitboxWidthSlider.getValue());
		    	view.getCurrentlySelectedSprite().getHitBox().setWidth(hitboxWidthSlider.getValue());
		    	if (modifyNeeded)
		    	{
		    		view.modifySpriteCommand();
		    		showHitbox();
		    	}
		    }
		    
		    public void hitboxHeightSliderChanged() {
		    	boolean modifyNeeded = !(view.getCurrentlySelectedSprite().getHitBox().getHeight() == hitboxHeightSlider.getValue());
		    	view.getCurrentlySelectedSprite().getHitBox().setHeight(hitboxHeightSlider.getValue());
		    	if (modifyNeeded)
		    	{
		    		view.modifySpriteCommand();
		    		showHitbox();
		    	}
		    }
		    
		    public void showHitbox() {
		    	 HitboxView tempHitboxSprite = new HitboxView();
		    	tempHitboxSprite.setWidth(view.getCurrentlySelectedSprite().getHitBox().getWidth());
		    	tempHitboxSprite.setHeight(view.getCurrentlySelectedSprite().getHitBox().getHeight());
		    	tempHitboxSprite.setX(view.getCurrentlySelectedSprite().getHitBox().getX());
		    	tempHitboxSprite.setY(view.getCurrentlySelectedSprite().getHitBox().getY());
		    	tempHitboxSprite.draw(view.getPlayerView().getGameCanvas().getGraphicsContext2D());
		    }
		    
		  //Sets the Sprite Properties pane values to the values of CurrentlySelectedSprite
			public void setPanesForCurrentlySelectedSprite() {
				//Set all labels to corresponding value of the sprite
				spriteIdLabel.setText("" + view.getCurrentlySelectedSprite().getSpriteId());
				collisionSpriteIdLabel.setText("" + view.getCurrentlySelectedSprite().getSpriteId());
				spriteXLabel.setText("" + view.getCurrentlySelectedSprite().getX());
				spriteYLabel.setText("" + view.getCurrentlySelectedSprite().getY());
				
				//Set the color selector
				spriteColorPicker.setValue(view.getCurrentlySelectedSprite().getAppearance().getColor());
				
				//Set width and height slider values
				spriteWidthSlider.setValue(view.getCurrentlySelectedSprite().getAppearance().getWidth());
				spriteHeightSlider.setValue(view.getCurrentlySelectedSprite().getAppearance().getHeight());
				hitboxWidthSlider.setValue(view.getCurrentlySelectedSprite().getHitBox().getWidth());
				hitboxHeightSlider.setValue(view.getCurrentlySelectedSprite().getHitBox().getHeight());
				
				
				setCollisionBehaviorsPane();
				setBehaviorsPane();
			}
			
			
	
}
