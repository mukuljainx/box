package box;

public class WeblistObject {
	String name;
	String url;
	
	public WeblistObject(String name,String url){
		this.url=url;
		this.name=name;
	}
	
	public void seturl(String a){
		url=a;
	}
	public String geturl(){
		return url;
	}
	public void setname(String a){
		name=a;
	}
	public String getname(){
		return name;
	}
}
