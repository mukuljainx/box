package box;


import javafx.collections.*;
//import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox; //trial
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javafx.scene.web.WebHistory;
import javafx.scene.web.WebHistory.Entry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;

public class Layout {
	
	private BookmarkDB bookmarkdb;
	private HistoryDB historydb;
	private WebListDB weblistdb;
	
	private BorderPane root;
	private Scene scene;
	private ToolBar toolBar;
	private TextField searchBox;
	private String bck = "", fwd = "";
	
	private Button backward, forward, mic, bookmark, refresh;
	private ToggleButton home, historylist, bookmarklist , weblist; 
	
	private Stack<String> forwardlist, backwardlist;
	
	Layout(BookmarkDB b, HistoryDB h, WebListDB w){
		
		bookmarkdb = b;
		historydb = h;
		weblistdb = w;
		
		root = new BorderPane();
		scene = new Scene(root, 300, 250, Color.WHITE);
		forwardlist = new Stack<String>();
		backwardlist = new Stack<String>();
	}

	public BorderPane getRoot(){
		return root;
	}
	
	public Scene getScene(){
		return scene;
	}
	
	public ToolBar getToolBar(){
		return toolBar;
	}

	public void createLayout(WebView browser){
	    //top Hbox to contain toolbar
		HBox topContainer = new HBox();
	    
	    //Tool Bar
	    toolBar = new ToolBar();

	    backward = new Button();
	    backward.setGraphic(new ImageView("file:icons/backward.png"));
	    backward.setDisable(true);    

	    forward = new Button();
	    forward.setGraphic(new ImageView("file:icons/forward.png"));
	    forward.setDisable(true);    

	    refresh = new Button();
	    refresh.setGraphic(new ImageView("file:icons/refresh.png"));

	    bookmark = new Button();
	    bookmark.setGraphic(new ImageView("file:icons/bookmark.png"));

	    mic = new Button();
	    mic.setGraphic(new ImageView("file:icons/micOff.png"));
	    
	    home = new ToggleButton();
	    home.setGraphic(new ImageView("file:icons/home.png"));

	    historylist = new ToggleButton();
	    historylist.setGraphic(new ImageView("file:icons/historylist.png"));

	    weblist = new ToggleButton();
	    weblist.setGraphic(new ImageView("file:icons/weblist.png"));

	    bookmarklist = new ToggleButton();
	    bookmarklist.setGraphic(new ImageView("file:icons/bookmarklist.png"));

	    searchBox = new TextField ();
	    searchBox.setText("Enter Website URL");
	    searchBox.setMinWidth(250);
	    
	    ToggleGroup extra = new ToggleGroup();

	    bookmarklist.setToggleGroup(extra);
	    historylist.setToggleGroup(extra);
	    weblist.setToggleGroup(extra);
	    home.setToggleGroup(extra);
	    home.setSelected(true);

	    HBox.setHgrow(searchBox, Priority.ALWAYS);
	
	    toolBar.getItems().addAll(backward, forward, refresh, searchBox, 
	            bookmark, mic , new Separator(),new Separator(),home, bookmarklist, historylist, weblist);

	    topContainer.getChildren().add(toolBar);
	    
	    root.setTop(topContainer);   
	    root.setCenter(browser);
	}
	
	public void activateSearchBox(WebView browser, WebEngine webEngine){
	    
	    
	    searchBox.setOnMousePressed(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent me) {
	            if("Enter Website URL".equals(searchBox.getText()))
	                searchBox.setText("");
	            
	        }
	    });

	    browser.setOnMousePressed(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent me) {
	            if("".equals(searchBox.getText()))
	                searchBox.setText("Enter Website URL");
	        }
	    });


	    searchBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
	        public void handle(KeyEvent keyEvent) {
	        
	            if(keyEvent.getCode() == KeyCode.ENTER){
	           
	                String url = searchBox.getText();
	              
	                if (!url.contains("http://") || !url.contains("https://") )
	                    url = "https://" + url;

	                //Using REGEX to check if its a URL
	                String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

	                Pattern p = Pattern.compile(URL_REGEX);
	                Matcher m = p.matcher(url);//replace with string to compare
	              //if its a url load directly
	                if(m.find()) { 
	                    webEngine.load(url);
	                }
	                else{ // if not URL use google search
	                    url = searchBox.getText();
	                    url = "http://www.google.com/search?q=" + url;
	                    webEngine.load(url);
	                }
	                searchBox.setText(url);
	            }
	        }
	    });
	}
	
	public void activateHistoryService(WebEngine webEngine, Stage primaryStage){
	    
	  final WebHistory history = webEngine.getHistory();
      history.getEntries().addListener(new 
          ListChangeListener<WebHistory.Entry>(){
              @Override
              public void onChanged(Change<? extends Entry> c) {
                  c.next();
                  for (Entry e : c.getAddedSubList()) {
//                	  int x = c.getTo()-2;
//                	  System.out.println(x);
//                	  System.out.println("title : " + history.getEntries().get(x).getTitle());
                	  historydb.insert(e.getUrl());
                	  primaryStage.titleProperty().bind(webEngine.titleProperty());
                	  if(!backwardlist.isEmpty()){
                		  bck = backwardlist.peek();
                	  }
                	  backwardlist.push(e.getUrl());
                      if(backwardlist.size() == 1){
                      	backward.setDisable(true);
                      }
                      else{
                      	backward.setDisable(false);
                      }
//                      System.out.println(!forwardlist.isEmpty());
//                      if(!forwardlist.isEmpty()){
//                    	  System.out.println("URL : " + e.getUrl());
//                          System.out.println("Forwardpeek : " + forwardlist.peek());
//                    	  if(e.getUrl() != forwardlist.peek()){
//                    		  System.out.println("Entered");
//                    		  forwardlist.clear();
//                    		  forward.setDisable(true);
//                    	  }
//                      }
                      searchBox.setText(e.getUrl());
                  }
              }
      });
      
      toolBar.prefWidthProperty().bind(primaryStage.widthProperty());
	}
	
	public void activateButton(WebEngine webEngine, Stage primaryStage){
	    //Refresh Button
	    refresh.setOnMousePressed(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent me) {
	            webEngine.load(searchBox.getText());            
	        }
	    });

	    
	    //Bookmark Button
	    bookmark.setOnMousePressed(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent me) {
	        	bookmarkdb.insert(webEngine.getTitle(), webEngine.getLocation());
	        }
	    });


	    //Maintaing forward and backward using two stacks
	    backward.setOnMousePressed(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent me) {
	        	forwardlist.push(backwardlist.pop());
	        	String temp = backwardlist.pop().toString(); 
	            webEngine.load(temp);            
	            searchBox.setText(temp);
	            bck = backwardlist.peek();
	            fwd = forwardlist.peek();
	            if(backwardlist.empty())
	                backward.setDisable(true);
	            if(!forwardlist.empty())
	                forward.setDisable(false);
	        }
	    });

	    forward.setOnMousePressed(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent me) {
	            String temp = forwardlist.pop().toString();
	            backwardlist.push(temp); 
	            webEngine.load(temp);
	            searchBox.setText(temp);            
	            bck = backwardlist.peek();
	            fwd = forwardlist.peek();	            
	            if(forwardlist.empty())
	                forward.setDisable(true);
	            if(!backwardlist.empty())
	                backward.setDisable(false);
	        }
	    });    
	}
	
	public void activateToggleButton(WebView w){
		//home toggle
	    home.setOnAction(new EventHandler<ActionEvent>() { 
	        @Override
	        public void handle(ActionEvent e){	
	            System.out.println("home");           
	            root.setCenter(w);
	        } 
	    });
		
		
		//Bookmark toggle
	    bookmarklist.setOnAction(new EventHandler<ActionEvent>() { 
	        @Override
	        public void handle(ActionEvent e){
	        	BookmarkView b = new BookmarkView(bookmarkdb.view());
	        	b.createBookmarkView();
	        	root.setCenter(b.getVbox());
	        } 
	    });

	    historylist.setOnAction(new EventHandler<ActionEvent>() { 
	        @Override
	        public void handle(ActionEvent e){
	            HistoryView h = new HistoryView(historydb.view());
	            h.createHistoryView();
	            root.setCenter(h.getVbox());
	        } 
	    });

	    weblist.setOnAction(new EventHandler<ActionEvent>() { 
	        @Override
	        public void handle(ActionEvent e){
	            System.out.println("WebList");
	        } 
	    });
	}
	
	public void activateMic(WebView browser, WebEngine webEngine){
	    // speech recongization 
	    mic.setOnMousePressed(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent me) {
	        	mic.setGraphic(new ImageView("file:icons/mic.png"));
	        	Mic micx = new Mic();
	        	micx.micActivate();          
	        	String outputString = micx.getOutput();
//	        	String outputString = "";
	        	VoiceCMD cmd = new VoiceCMD(outputString, browser, webEngine, fwd, bck, root, bookmarkdb, historydb.view());
	        	cmd.findCMD();
	        }
	    });
	}
}	