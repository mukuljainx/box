package box;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BookmarkDB implements dbInterface {
	
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	public BookmarkDB(){
		conn=null;
		stmt=null;
		rs=null;
	}
	
	
	public void connect(){ 
 		try{
 			Class.forName(JDBC_DRIVER);
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
	
	
	public void insert(String name,String url){
		String sql;
		stmt=null;
		try{
				stmt= conn.createStatement();
				sql="insert into bookmarks values ('"+name+"','"+url+"');";
				int j=stmt.executeUpdate(sql);
				System.out.println("number of rows affected are: "+j);
			}
		catch(Exception e){
			System.out.println("couldn't insert");
			e.printStackTrace();
		}
	}
	
	public void delete(String name){
		String sql;
		stmt=null;
		try{
				stmt= conn.createStatement();
				sql="delete from bookmarks where name like '"+name+"';";
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
				sql="delete from bookmarks;";
				int j=stmt.executeUpdate(sql);
				System.out.println("number of rows affected are: "+j);
			}
		catch(Exception e){
			System.out.println("couldn't delete ");
			e.printStackTrace();
		}
	}
	

	public ArrayList<Bookmark> view()
	{
		String sql;
		stmt=null;
		ArrayList<Bookmark> a=new ArrayList<Bookmark>();
		try{
			stmt= conn.createStatement();
			sql="select * from bookmarks";
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				a.add(new Bookmark(rs.getString("name"), rs.getString("url")));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return a;
	}

}
