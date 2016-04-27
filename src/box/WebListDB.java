package box;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class WebListDB implements dbInterface {
	
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	WebListDB(){
		conn=null;
		stmt=null;
		rs=null;
	}
	
	public void connect(){ 
 		try{
			  conn= DriverManager.getConnection(DB_URL,USER,PASS);
 		}catch(Exception e){
		e.printStackTrace();
		}
 	}
	
	
	public void close(){  
		try{
			if(rs!=null)
				rs.close();
			if(stmt!=null)
				stmt.close();
			if(conn!=null)
				conn.close();
 		}
		catch(Exception e){
			e.printStackTrace();
		}
 	}
	
	
	public void insert(String url){
		String sql;
		stmt=null;
		String name="";
		for(int i=0;i<url.length();i++){
			int flag=0;
			if(url.charAt(i)=='.'){
				for(int j=0;url.charAt(i+j)!='.';j++){
					name=name+url.charAt(i+j);
					flag=1;
				}
			}
			if(flag==1) break;
		}
		try{
				stmt= conn.createStatement();
				sql="insert into weblist values ("+name+","+url+");";
				int j=stmt.executeUpdate(sql);
				System.out.println("number of rows affected are: "+j);
			}
		catch(Exception e){
			System.out.println("couldn't insert");
			e.printStackTrace();
		}
	}
	
	public void delete(String url){
		String sql;
		stmt=null;
		try{
				stmt= conn.createStatement();
				sql="delete from weblist where url like "+url+";";
				int j=stmt.executeUpdate(sql);
				System.out.println("number of rows affected are: "+j);
			}
		catch(Exception e){
			System.out.println("couldn't delete ");
			e.printStackTrace();
		}
	}
	
	
	public void deleteAll(){
		String sql;
		stmt=null;
		try{
				stmt= conn.createStatement();
				sql="delete from weblist;";
				int j=stmt.executeUpdate(sql);
				System.out.println("number of rows affected are: "+j);
			}
		catch(Exception e){
			System.out.println("couldn't delete ");
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<WeblistObject> view()
	{
		String sql;
		stmt=null;
		rs=null;
		ArrayList<WeblistObject> a=new ArrayList<WeblistObject>();
		try{
			stmt= conn.createStatement();
			sql="select * from weblist";
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				a.add(new WeblistObject(rs.getString("name"), rs.getString("url")));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return a;
	}

}
