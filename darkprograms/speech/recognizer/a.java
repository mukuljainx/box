package box;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class HistoryView{
 
    private final TableView<Bookmark> table = new TableView<>();
    private final ObservableList<Bookmark> data =
            FXCollections.observableArrayList();
    private final VBox vbox = new VBox();
    private ArrayList<Bookmark> Historylist; 
    
    HistoryView(ArrayList<History> b) {       
        Historylist = b;
    }

    public void createHistoryView(){
 
        data.add(new Bookmark("a","b"));

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
 
    }
    
    public VBox getVbox(){
        return vbox;
    }
}