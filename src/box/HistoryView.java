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
 
    private final TableView<History> table = new TableView<>();
    private final ObservableList<History> data =
            FXCollections.observableArrayList();
    private final VBox vbox = new VBox();
    private ArrayList<History> historylist; 
    
    HistoryView(ArrayList<History> h) {       
    	historylist = h;
    }

    public void createHistoryView(){
 
    	for(History h : historylist){
    		data.add(h);
    	}
        TableColumn urlCol = new TableColumn("Url");
        urlCol.setMinWidth(900);
        urlCol.setCellValueFactory(
                new PropertyValueFactory<>("Url"));
 
        TableColumn timeCol = new TableColumn("Time");
        timeCol.setMinWidth(100);
        timeCol.setCellValueFactory(
                new PropertyValueFactory<>("Time"));
 
        table.setItems(data);
        table.getColumns().addAll(urlCol, timeCol);
 
        timeCol.setSortType(TableColumn.SortType.ASCENDING);
        timeCol.setSortable(true);
 
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table);
        
    }
    
    public VBox getVbox(){
    	return vbox;
    }
}