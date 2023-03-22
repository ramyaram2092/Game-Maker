package layout;

/**
 * @author: Abhishek Tiwari
 * @CreationDate: Sep 19, 2021
 * @editors: Abhishek Tiwari
 * Last modified on: 19 Sep 2021
 * Last modified by: Abhishek Tiwari
 **/

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

//TODO
//Questioning if this is necessary at all
//What does this give us that Jafafx doesn't?
//Isaiah
public class CustomLayout extends GridPane {
	
	private List<GridPane> gridPanes = new ArrayList<>();
	private GridPane currentFormPane;
	
	public CustomLayout(int hGap, int vGap) {
		super.setHgap(hGap);
		super.setVgap(vGap);
		super.setPadding(new Insets(10, 10, 10, 10));
	}
	
	public boolean addNewChild(GridPane pane, int column, int row) {
		super.add(pane, column, row);
		return gridPanes.add(pane);
	}
	
	public boolean addNewChildPane(GridPane pane, int column, int row) {
		gridPanes.remove(currentFormPane);
		super.getChildren().remove(currentFormPane);
		this.currentFormPane = pane;
		super.add(pane, column, row);
		super.setColumnSpan(pane, 6);
		return gridPanes.add(pane);
	}

	public boolean removeChild(GridPane pane) {
		return gridPanes.remove(pane);
	}

}
