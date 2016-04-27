package box;

import javafx.beans.property.SimpleStringProperty;

public class History {
    private SimpleStringProperty url;
    private SimpleStringProperty time;

    public History() {
    }

    public History(String s1, String s2) {
        url = new SimpleStringProperty(s1);
        time = new SimpleStringProperty(s2);
    }

    public String getUrl() {
        return url.get();
    }
    
    public String getTime() {
        return time.get();
    }
    
    public void setUrl(String s) {
        url.set(s);
    }
    
    public void setTime(String s) {
        time.set(s);
    }

    @Override
    public String toString() {
      return (url.get() + " : " + time.get());
    }
}