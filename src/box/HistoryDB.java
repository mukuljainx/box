package box;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class HistoryDB implements dbInterface {
    
    
    Connection conn;
    Statement stmt;
    ResultSet rs;
    
    HistoryDB(){
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
        try{
                stmt= conn.createStatement();
                sql="insert into history values ('"+url+"',now());";
                System.out.println(sql);
                int j=stmt.executeUpdate(sql);
                System.out.println("number of rows affected are: "+j);
            }
        catch(Exception e){
            System.out.println("couldn't insert");
            e.printStackTrace();
        }
    }
    
    public void delete(String url,String time){
        String sql;
        stmt=null;
        try{
                stmt= conn.createStatement();
                sql="delete from history where url = '"+url+"' and time='"+time+"';";
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
                sql="delete from history;";
                int j=stmt.executeUpdate(sql);
                System.out.println("number of rows affected are: "+j);
            }
        catch(Exception e){
            System.out.println("couldn't delete ");
            e.printStackTrace();
        }
    }
    
    
    public ArrayList<History> view()
    {
        String sql;
        ArrayList<History> a=new ArrayList<History>();
        stmt=null;
        rs=null;
        try{
            stmt= conn.createStatement();
            sql="select * from history order by time desc";
            rs=stmt.executeQuery(sql);
            
            while(rs.next()){
                a.add(new History(rs.getString("url"), rs.getString("time")));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();    
        }
        return a;
    }
    
    
    public ArrayList<History> viewone(String url)
    {
        String sql;
        stmt=null;
        ArrayList<History> a=new ArrayList<History>();
        rs=null;
        try{
            stmt= conn.createStatement();
            sql="select * from history where url like '%"+url+"%';";
            rs=stmt.executeQuery(sql);
            while(rs.next()){
                a.add(new History(rs.getString("url"), rs.getString("time")));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return a;
    }

}
