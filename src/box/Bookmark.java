package box;

import javafx.beans.property.SimpleStringProperty;

public class Bookmark {
	private SimpleStringProperty name;
    private SimpleStringProperty url;

    public Bookmark () {
    }

    public Bookmark (String s1, String s2) {

        name = new SimpleStringProperty(s1);
        url = new SimpleStringProperty(s2);
    }

    public String getName() {

        return name.get();
    }
    public void setName(String s) {
	
        name.set(s);
    }

    public String getUrl() {

        return url.get();
    }
    public void setUrl(String s) {

        url.set(s);
    }

    @Override
    public String toString() {

        return (name.get() + " : " + url.get());
    }

}