import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import views.View;

public class Main extends Application {
	@Override
    public void start(Stage primaryStage) throws Exception{
		Model m = new Model();
		Controller c = new Controller();
		c.setModel(m);
		View view = new View(primaryStage);
		view.setController(c);
		//C needs to setView
	}

	public static void main(String[] args) {
        launch(args);
    }
}