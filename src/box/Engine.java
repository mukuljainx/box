package box;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Engine {
	 final WebView browser;
	 final WebEngine webEngine;
	 	
	Engine(){
	    browser = new WebView();
	    webEngine = browser.getEngine();
	    webEngine.load("http://google.com");
	}
	
	public WebView getWebView(){
		return browser;
	}
	
	public WebEngine getWebEngine(){
		return webEngine;
	}	
}