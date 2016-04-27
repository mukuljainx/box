package box;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class VoiceCMD {	
	
	private String[] out;
	private WebView browser;
	private WebEngine webEngine;
	private String bck,url, fwd, urltext;
	private TextField searchBox;
	private BorderPane root;
	
	BookmarkDB bookmarkdb;
	private ArrayList<History> historylist;
	
	VoiceCMD(String outx, WebView browser, WebEngine webEngine, String fwd, String bck, BorderPane root, BookmarkDB b, ArrayList<History> h){
		out = outx.split(" ", 2);
		this.browser = browser;
		this.webEngine = webEngine;
		this.fwd = fwd;
		this.bck = bck;
		this.root = root;
		url = "";
		
		bookmarkdb = b;
		historylist =h;
		
		System.out.println("backward : " + bck);
		System.out.println("forward : " + fwd);
		System.out.println(out[0]);
	}
	
	public void findCMD(){
		
		switch(out[0]){
	    case "open" :
	       open();
	       break; 
	       
	    case "show" :
	       show();
	       break; 
	       
	    case "go" :
	    	go();
		    break; 
	    
	    case "bookmark" :
	    	bookmark();
	    	break; 
	    
	    case "refresh" :
	    	refresh();
	    	break; 
	    //You can have any number of case statements.
	    default : 
	       //Statements
		}
	
	}
	
	public void open(){
		url = urltext = out[1];

		if (!url.contains("http://") || !url.contains("https://") )
		    url = "https://" + url;

		String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

		Pattern p = Pattern.compile(URL_REGEX);
		Matcher m = p.matcher(url);//replace with string to compare
		//if its a url load directly
		if(m.find()) { 
			webEngine.load(url);
		}
		else{
		    url = "http://www.google.com/search?q=" + urltext;
		    webEngine.load(url);
		}
		try{searchBox.setText(url);}
		catch(Exception e){}
	}
	

	public void show(){
		switch(out[1]){
	    case "web" :
	    	root.setCenter(browser);
	    	break; 
	       
	    case "history" :
        	HistoryView h = new HistoryView(historylist);
        	h.createHistoryView();
        	root.setCenter(h.getVbox());
	    	break; 
		       
	    case "bookmark" :
        	BookmarkView b = new BookmarkView(bookmarkdb.view());
        	b.createBookmarkView();
        	root.setCenter(b.getVbox());
	    	break; 
		       
	    case "weblist" :
	    	show();
	    	break;
		       
	    default : 
	       //Statements
		}
	}
	
	public void go(){
		switch(out[1]){
	    case "forward" :
	    	webEngine.load(fwd);
	    	break; 
	       
	    case "backward" :
	    	webEngine.load(bck);
	        break; 
	
	    default : 
	       //Statements
		}
	}
	
	public void bookmark(){
		bookmarkdb.insert(webEngine.getTitle(), webEngine.getLocation());
	}
	
	public void refresh(){
		webEngine.load(webEngine.getLocation());
	}
	
}