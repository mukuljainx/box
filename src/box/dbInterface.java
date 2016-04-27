package box;

public interface dbInterface {
		// JDBC driver name and database URL
		static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		static final String DB_URL = "jdbc:mysql://localhost/box";
		//  Database credentials
		static final String USER = "root";
		static final String PASS = "mukulj@in16";
		
		void connect();
		void close();
		void deleteAll();
}
