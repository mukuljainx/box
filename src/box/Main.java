package box;

import javafx.application.Application;
import javafx.geometry.Rectangle2D; //To maximize window according to screen size
import javafx.scene.image.*;
import javafx.stage.Screen; //To maximize window according to screen size
import javafx.stage.Stage;

public class Main extends Application {
	
	//Initializing Database
	BookmarkDB bookmarkdb = new BookmarkDB();
	HistoryDB historydb = new HistoryDB();
	WebListDB weblistdb = new WebListDB();
	
  @Override
  public void start(Stage primaryStage) {
	  
	//Creating connections
	bookmarkdb.connect();
	historydb.connect();
	weblistdb.connect();

	//Initializing Engine
    Engine browser = new Engine();
    
    //Initializing Layout
    Layout layout = new Layout(bookmarkdb, historydb, weblistdb);
    
    layout.createLayout(browser.getWebView());
    layout.activateSearchBox(browser.getWebView(), browser.getWebEngine());
    layout.activateHistoryService(browser.getWebEngine(), primaryStage);
    layout.activateButton(browser.getWebEngine(), primaryStage);
    layout.activateToggleButton(browser.getWebView());
    layout.activateMic(browser.getWebView(), browser.getWebEngine());
    
    primaryStage.setScene(layout.getScene());

    //Connecting to Database
    BookmarkDB bookmarkDB = new BookmarkDB();
    bookmarkDB.connect();
    
    //Stage Minimum width
    
    primaryStage.setMinWidth(400);
    
    //To maximize window according to screen size

    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    //set Stage boundaries to visible bounds of the main screen
    primaryStage.setX(primaryScreenBounds.getMinX());
    primaryStage.setY(primaryScreenBounds.getMinY());
    primaryStage.setWidth(primaryScreenBounds.getWidth());
    primaryStage.setHeight(primaryScreenBounds.getHeight());


    primaryStage.getIcons().add(new Image("file:icon.png"));
    primaryStage.setTitle("Box");
    
    primaryStage.show();
  }
  
  public void stop(){
	  bookmarkdb.close();
	  historydb.close();
	  weblistdb.close();
  }
  
}