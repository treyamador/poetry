package crawler;



/*

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.sql.ResultSet;
	
	// assume that conn is an already created JDBC connection (see previous examples)
	
	Statement stmt = null;
	ResultSet rs = null;
	
	try {
	    stmt = conn.createStatement();
	    rs = stmt.executeQuery("SELECT foo FROM bar");
	
	    // or alternatively, if you don't know ahead of time that
	    // the query will be a SELECT...
	
	    if (stmt.execute("SELECT foo FROM bar")) {
	        rs = stmt.getResultSet();
	    }
	
	    // Now do something with the ResultSet ....
	}
	catch (SQLException ex){
	    // handle any errors
	    System.out.println("SQLException: " + ex.getMessage());
	    System.out.println("SQLState: " + ex.getSQLState());
	    System.out.println("VendorError: " + ex.getErrorCode());
	}
	finally {
	    // it is a good idea to release
	    // resources in a finally{} block
	    // in reverse-order of their creation
	    // if they are no-longer needed
	
	    if (rs != null) {
	        try {
	            rs.close();
	        } catch (SQLException sqlEx) { } // ignore
	
	        rs = null;
	    }
	
	    if (stmt != null) {
	        try {
	            stmt.close();
	        } catch (SQLException sqlEx) { } // ignore
	
	        stmt = null;
	    }
	}

*/


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class Database {
	
	
	private final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	private Connection connect = null;
	
	
    public Database(String db, String user, String pwd) {
    	this.init(db, user, pwd);
    }
	
	
    private void init(String db, String user, String pwd) {
    	try {
			Class.forName(this.DRIVER_NAME);
			this.connect = DriverManager.getConnection(
					"jdbc:mysql://localhost/" + db + "?" + 
							"user=" + user + "&password=" + pwd +
							"&allowPublicKeyRetrieval=true&useSSL=false");
			// Statement statement = this.connect.createStatement();
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR! "+ e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
    
    private ResultSetMetaData getMetaData(String table) {
    	ResultSetMetaData metadata = null;
    	Statement statement = null;
    	try {
    		statement = this.connect.createStatement();
			metadata = statement.executeQuery("SELECT * from "+table).getMetaData();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    	return metadata;
    }
    
	
    public ResultSet select() {
    	
    	ResultSet result = null;
    	
    	try {
			Statement statement = this.connect.createStatement();
			result = statement.executeQuery("select * from poemsection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return result;
    	
    }
    
	

}

