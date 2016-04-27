package box;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class BookmarkView{
 
    private final TableView<Bookmark> table = new TableView<>();
    private final ObservableList<Bookmark> data =
            FXCollections.observableArrayList();
    private final VBox vbox = new VBox();
    private ArrayList<Bookmark> bookmarklist; 
    
    BookmarkView(ArrayList<Bookmark> b) {       
    	bookmarklist = b;
    }

    public void createBookmarkView(){
 
    	for(Bookmark b : bookmarklist){
    		data.add(b);
    	}
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(250);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("Name"));
 
        TableColumn urlCol = new TableColumn("Url");
        urlCol.setMinWidth(800);
        urlCol.setCellValueFactory(
                new PropertyValueFactory<>("Url"));
 
        table.setItems(data);
        table.getColumns().addAll(nameCol, urlCol);
 
        nameCol.setSortType(TableColumn.SortType.DESCENDING);
        urlCol.setSortable(false);
 
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table);
        table.setMinHeight(880);
    }
    
    public VBox getVbox(){
    	return vbox;
    }
}