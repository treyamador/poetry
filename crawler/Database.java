package crawler;


import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class Database {
	
	
	private final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	private Connection connect = null;
	
	private final int DEFAULT_SUBSECTION_LENGTH = 250;
	private final String TEXT_COLUMN_NAME = "Text";
	// private final String POEM_ID_COLUMN = "ID";
	
	
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
			result = statement.executeQuery("SELECT * from poemsection");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return result;
    	
    }
    
    
    private String createPoemInsertQuery(String url, Poem poem) {
    	
    	String query = "INSERT into poem(title, poet_id, date, url) VALUES " + 
    			"(" + 
    				"'" + poem.title + "', " + 
    				0 + ", " + 
    				"NULL, " + 
    				"'" + url + "'" + 
    			")";
    	
    	return query;
    	
    }
    
    
    public boolean executePreparedStatement(String query) {
    	
    	try {
			PreparedStatement statement = this.connect.prepareStatement(query);
			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    	
    }
    
    
    public boolean insertPoem(String url, Poem poem) {
    	String query = this.createPoemInsertQuery(url, poem);
    	return this.executePreparedStatement(query);
    }
    
    
    private int getSubsectionPrecision() {
    	
    	int precision = this.DEFAULT_SUBSECTION_LENGTH;
    	
    	try {
    		
    		ResultSetMetaData metadata = this.getMetaData("poemsection");
			for (int i = 1; i <= metadata.getColumnCount(); ++i) {
				String column_name = metadata.getColumnName(i);
				if (column_name == this.TEXT_COLUMN_NAME) {
					precision = metadata.getPrecision(i);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return precision;
    	
    }
    
    
    private ArrayList<String> createSubsections(Poem poem) {
    	
    	int len_text = poem.poem.length();
    	int subsect_prec = this.getSubsectionPrecision();
	    int num_subsects = (int)(len_text / subsect_prec) + 1;
		
	    ArrayList<String> subsects = new ArrayList<String>();
    	for (int i = 0; i < num_subsects; ++i) {
    		int sub_i = i*subsect_prec,
    			sub_f = (i+1)*subsect_prec;
    		if (sub_i < len_text) {
    			String subsect = poem.poem.substring(sub_i, 
    					sub_f < len_text ? sub_f : len_text-1);
    			subsects.add(subsect);
    		}
    	}
    	
    	return subsects;
    	
    }
    
    
    private int getLastID() {
    	
    	int id = -1;
    	
    	try {
			Statement statement = this.connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT LAST_INSERT_ID()");
			result.first();
			id = result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return id;
    	
    }
    
    
    private String createSubsectionInsertQuery(int poemid, ArrayList<String> subsections) {
    	
    	String query = "INSERT INTO poemsection(poem_id, section_num, text) VALUES ";
    	
    	for (int i = 0; i < subsections.size(); ++i) {
    		query +=  
    				"(" +
    						poemid + ", " +
    						(i+1) + ", " +
    						"'" + subsections.get(i) + "'" +
    				")";
    		if (i < subsections.size()-1)
    			query += ", ";
    	}
    	
    	return query;
    	
    }
    
    
    public boolean insertSubsections(Poem poem) {
    	
    	int id = this.getLastID();
    	if (id == -1)
    		return false;
    	
    	ArrayList<String> subsections = this.createSubsections(poem);
    	String query = this.createSubsectionInsertQuery(id, subsections);
    	
    	return this.executePreparedStatement(query);
    	
    }
    
    
    public boolean insert(String url, Poem poem) {
    	boolean inserted_poem = this.insertPoem(url, poem);
    	if (inserted_poem)
    		return this.insertSubsections(poem);
    	return inserted_poem;
    }
    
    
}

