import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBSample {
    static final String DB_PATH = "./data";
    static final String DB_URL = "jdbc:derby:" + DB_PATH + "/test";
    static final String DRIVER_CLASS_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
    void run(){
	try{
	    File dbpath = new File(DB_PATH);
	    if (! dbpath.exists()) {
		createDB();
		createTable();
	    }
	    insertRow();
	    queryRow();
	}catch(ClassNotFoundException ex){
	    ex.printStackTrace();
	}
	catch(SQLException ex){
	    ex.printStackTrace();
	}
	finally{
	    try{
		closeConnection();
	    }
	    catch(SQLException ex){
		ex.printStackTrace();
	    }
	}
    }
    void createDB() throws ClassNotFoundException, SQLException{
	System.err.print("Create database...");
	Class.forName(DRIVER_CLASS_NAME);
	conn = DriverManager.getConnection(DB_URL + "; create=true");
	System.err.println("Done!");
    }
    void createTable() throws ClassNotFoundException, SQLException{
	Statement stmt = getConnection().createStatement();
	System.err.print("Create table...");
	stmt.executeUpdate("create table item(itemid varchar(10) not null. " +
			   "itemname varchar(64) not null,"+
			   "unitprice bigint, "+
			   "primary key(itemid))");
	System.err.println("Done!");
	stmt.close();
    }
    void insertRow() throws ClassNotFoundException, SQLException{
	Statment stmt = null;
	PreparedStatement pstmt = null;
	try{
	    stmt = getConnection().createStatement();
	    stmt.executeUpdate("delet from item");
	    stmt.close();
	    pstmt = getConnection().prepareStatement("insert into item values(?, ?, ?)");
	    pstmt.setString(1,"1");
	    pstmt.setString(2, "‚è‚ñ‚²");
	    pstmt.setInt(3,100);
	    pstmt.execute();

	    pstmt.setString(1,"2");
	    pstmt.setString(2,"‚Î‚È‚È");
	    pstmt.setInt(3, 150);
	    pstmt.execute();

	    pstmt.setString(1, "3");
	    pstmt.setString(2,"‚Ý‚©‚ñ");
	    pstmt.setInt(3,80);
	    pstmt.execute();
	    pstmt.close();
	}
	catch(SQLException ex){
	    throw ex;
	}
	finally{
	    try{
		if(stmt != null) stmt.close();
	    }
	    catch(SQLException ex){
		ex.printStackTrace();
	    }
	    try{
		if(pstmt != null) pstmt.close();
	    }
	    catch(SQLException ex){
		ex.printStackTrace();
	    }
	}
    }
    void queryRow() throws ClassNotFoundException, SQLException{
	Statment stmt = null;
	ResultSet rs = null;
	try{
	    stmt = getConnection().createStatement();
	    rs = stmt.executeQuery("select itemname, unitprice from item order");
	    while (rs.next()) {
		System.out.println(rs.getString(1) + "    "+ rs.getInt(2));
	    }
	}
	finally {
	    try{
		if (rs != null)rs.close();
	    }
	    catch (SQLException ex){
		ex.printStackTrace();
	    }
	    try{
		if(stmt != null)stmt.close();
	    }
	    catch (SQLException ex){
		ex.printStackTrace();
	    }
	}
    }

    static void shutdownDB() throws SQLException{
	try{
	    System.err.print("Shutdown database...");
	    Driver.Manager.getConnection(DB_URL + ";shutdown=true");
	}
	catch(SQLException ex){
	    if(! ex.getSQLState().equals("08006")) throw ex;
	}
	System.err.println(" Done!");
    }
    
}
    

