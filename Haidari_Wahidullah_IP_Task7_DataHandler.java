package Individual_Project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataHandler {
	
	private Connection conn;
    // Azure SQL connection credentials
    private String server = "haid0000-sql-server.database.windows.net";
    private String database = "cs-dsa-4513-sql-db";
    private String username = "haid0000";
    private String password = "Changquan2023";
    static String inputSalary = "0";
    
    // Resulting connection string
    final private String url =
            String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
                    server, database, username, password);
    // Initialize and save the database connection
    private void getDBConnection() throws SQLException {
        if (conn != null) {
            return;
        }
        this.conn = DriverManager.getConnection(url);
    }
    // Return the result of selecting everything from the movie_night table 
    public ResultSet getEmployees() throws SQLException {
        getDBConnection();
        
        final String QUERY12 = "WITH Employee as ( " +
     		   "SELECT salary,addr, worker_name as employee_name FROM Worker " +
     		   "UNION " +
     		   "SELECT salary, addr, qual_cont_name as employee_name FROM QualityController " +
     		   "UNION " +
     		   "SELECT salary, addr, tech_staff_name as employee_name " +
     		   "FROM TechnicalStaff ) " +
     		   "SELECT employee_name, addr AS address, salary " +
     		   "FROM Employee Where salary > ?;";
        
        final PreparedStatement stmt = conn.prepareStatement(QUERY12);
        stmt.setString(1, inputSalary);
   
       
        return stmt.executeQuery();
    }
    
    public static boolean addSalary(String salary) {
    	inputSalary = salary;
    
    	return true;
    }
    
    public boolean addWorker(String name, String address, String salary, String maxNumProduct) throws SQLException {
        getDBConnection(); // Prepare the database connection
        // Prepare the SQL statement
        final String QUERY1_WORKER = "INSERT INTO Worker " +
    			"VALUES (?, ?, ?, ?);";
        final PreparedStatement stmt = conn.prepareStatement(QUERY1_WORKER);
        // Replace the '?' in the above statement with the given attribute values
        stmt.setString(1, name);
        stmt.setString(2, address);
        stmt.setString(3, salary);
        stmt.setString(4, maxNumProduct);
        
        // Execute the query, if only one record is updated, then we indicate success by returning true
        return stmt.executeUpdate() == 1;
    }
    
    public boolean addQualityController(String name, String address, String salary, String productType) throws SQLException {
        getDBConnection(); // Prepare the database connection
        // Prepare the SQL statement
        final String QUERY1_QUALITY_CONTROLLER = "INSERT INTO QualityController " +
    			"VALUES (?, ?, ?, ?);";
        final PreparedStatement stmt = conn.prepareStatement(QUERY1_QUALITY_CONTROLLER);
        // Replace the '?' in the above statement with the given attribute values
        stmt.setString(1, name);
        stmt.setString(2, address);
        stmt.setString(3, salary);
        stmt.setString(4, productType);
        
        // Execute the query, if only one record is updated, then we indicate success by returning true
        return stmt.executeUpdate() == 1;
    }
    
    public boolean addTechnicalStaff(String name, String address, String salary, String educationRecord, String technicalPosition) throws SQLException {
        getDBConnection(); // Prepare the database connection
        // Prepare the SQL statement
        final String QUERY1_TECHNICAL_STAFF = "INSERT INTO TechnicalStaff " +
    			"VALUES (?, ?, ?, ?, ?);";
        final PreparedStatement stmt = conn.prepareStatement(QUERY1_TECHNICAL_STAFF);
        // Replace the '?' in the above statement with the given attribute values
        stmt.setString(1, name);
        stmt.setString(2, address);
        stmt.setString(3, salary);
        stmt.setString(4, educationRecord);
        stmt.setString(5, technicalPosition);
        
        // Execute the query, if only one record is updated, then we indicate success by returning true
        return stmt.executeUpdate() == 1;
    }
    
}
