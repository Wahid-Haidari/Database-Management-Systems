import java.sql.Connection; 
import java.sql.Statement; 
import java.util.Scanner; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
 
public class sample { 
 
    // Database credentials 
    final static String HOSTNAME = "haid0000-sql-server.database.windows.net"; 
    final static String DBNAME = "cs-dsa-4513-sql-db"; 
    final static String USERNAME = "haid0000"; 
    final static String PASSWORD = "Changquan2023"; 
 
    // Database connection string 
    final static String URL = 
    		String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", 
            HOSTNAME, DBNAME, USERNAME, PASSWORD); 
 
    // Query templates 
    final static String QUERY_TEMPLATE_1 = "Enter a new employee";
    
    final static String QUERY_TEMPLATE_1_Worker = "Enter a new worker " +
    		"VALUES (worker_name, address, salary, max_num_prod_per_day);";
    final static String QUERY_TEMPLATE_1_QualityController = "Enter a new quality controller " +
    		"VALUES (qual_cont_name, address, salary, product_type);";
    final static String QUERY_TEMPLATE_1_TechnicalStaff = "Enter a new technical staff " +
    		"VALUES (tech_staff_name, address, salary, education_record, technical_position);";
    		
    final static String QUERY_TEMPLATE_2 = "Enter a new product associated with the person who made the product, repaired the product if it is repaired, or cheched the product.";
    final static String QUERY_TEMPLATE_2_Product1 = "Enter a new product of type 1 " +
    		"VALUES (product1_id, production_date, time_spent, produced_by, tested_by, repaired_by, name, sofwareware_used, account1_num);";
 
    final static String QUERY_TEMPLATE_2_Product2 = "Enter a new product of type 2 " +
    		"VALUES (product2_id, production_date, time_spent, produced_by, tested_by, repaired_by, size, color, account2_num);";
    final static String QUERY_TEMPLATE_2_Product3 = "Enter a new product of type 3 " +
    		"VALUES (product3_id, production_date, time_spent, produced_by, tested_by, repaired_by, size, weight, account3_num);";
    
    final static String QUERY_TEMPLATE_3 = "Enter a new customer associated with some products.";
    
    final static String QUERY_TEMPLATE_4 = "Create a new account associated with a product.";
    
    final static String QUERY_TEMPLATE_5 = "Enter a new customer associated with some products.";
    
    final static String QUERY_TEMPLATE_6 = "Enter an accident associated with an appropriate employee and product";
    
    final static String QUERY_TEMPLATE_7 = "Retrieve the date produced and time spent to produce a particular product";
    
    final static String QUERY_TEMPLATE_8 = "Retrieve all products made by a particular worker";
    
    final static String QUERY_TEMPLATE_9 = "Retrieve the total number of errors a particular quality controller made.  This is the total number of products certified by this controller and got some complaints";
    
    final static String QUERY_TEMPLATE_10 = "Retrieve the total costs  of the  products  in the product3 category which were repaired at the request of a particular quality controller";
    
    final static String QUERY_TEMPLATE_11 = "Retrieve  all  customers  (in  name  order)  who  purchased  all  products  of  a  particular  color";
    
    final static String QUERY_TEMPLATE_12 = " Retrieve all employees whose salary is above a particular salary";
    
    final static String QUERY_TEMPLATE_13 = " Retrieve the total number of workdays lost due to accidents in repairing the products which got complaints";
    
    final static String QUERY_TEMPLATE_14 = "Retrieve the average cost of all products made in a particular year";
    
    final static String QUERY_TEMPLATE_15 = "Delete all accidents whose dates are in some range";
    
    final static String QUERY_TEMPLATE_16 = "Import: enter new employees from a data file until the file is empty the user must be \r\n"
    		+ "asked to enter the input file name);";
    
    final static String QUERY_TEMPLATE_17 = "Export:  Retrieve  all  customers  (in  name  order)  who  purchased  all  products  of  a \r\n"
    		+ "particular color and output them to a data file instead of screen (the user must be asked to \r\n"
    		+ "enter the output file name); ";
    
    final static String QUERY_TEMPLATE_18 = "Quit";
    
 
    // User input prompt// 
    final static String PROMPT =  
            "\nPlease select one of the options below: \n" + 
            "1) Insert new student; \n" +  
            "2) Display all students; \n" +  
            "3) Exit!"; 
 
    public static void main(String[] args) throws SQLException { 
 
        System.out.println("Welcome to the sample application!"); 
        
        final Scanner sc = new Scanner(System.in); // Scanner is used to collect the user input 
        String option = ""; // Initialize user option selection as nothing 
        while (!option.equals("3")) { // As user for options until option 3 is selected 
            System.out.println(PROMPT); // Print the available options 
            option = sc.next(); // Read in the user option selection 
 
            switch (option) { // Switch between different options 
                case "1": // Insert a new student option 
                    // Collect the new student data from the user 
                    System.out.println("Please enter integer student ID:"); 
                    final int id = sc.nextInt(); // Read in the user input of student ID 
 
                    System.out.println("Please enter student first name:"); 
                    // Preceding nextInt, nextFloar, etc. do not consume new line characters from the user input. 
                    // We call nextLine to consume that newline character, so that subsequent nextLine doesn't return nothing. 
                    sc.nextLine(); 
                    final String fname = sc.nextLine(); // Read in user input of student First Name (white-spaces allowed). 
 
                    System.out.println("Please enter student last name:"); 
                    // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character. 
                    final String lname = sc.nextLine(); // Read in user input of student Last Name (white-spaces allowed). 
 
                    System.out.println("Please enter float student GPA:"); 
                    final float gpa = sc.nextFloat(); // Read in user input of student GPA 
 
                    System.out.println("Please enter student major:"); 
                    sc.nextLine(); // Consuming the trailing new line character left after nextFloat 
                    final String major = sc.nextLine(); // Read in user input of student Major 
 
                    System.out.println("Please enter student classification (Freshman, Sophomore, Junior, or Senior):"); 
                    final String classification = sc.nextLine(); // Read in user input of student Classification 
 
                    System.out.println("Connecting to the database..."); 
                    // Get a database connection and prepare a query statement 
                    try (final Connection connection = DriverManager.getConnection(URL)) { 
                        try ( 
                            final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_1)) { 
                            // Populate the query template with the data collected from the user 
                            statement.setInt(1, id); 
                            statement.setString(2, fname); 
                            statement.setString(3, lname); 
                            statement.setFloat(4, gpa); 
                            statement.setString(5, major); 
                            statement.setString(6, classification); 
 
                            System.out.println("Dispatching the query..."); 
                            // Actually execute the populated query 
                            final int rows_inserted = statement.executeUpdate(); 
                            System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
                        } 
                    } 
 
                    break; 
                case "2": 
                    System.out.println("Connecting to the database..."); 
                    // Get the database connection, create statement and execute it right away, as no user input need be collected 
                    try (final Connection connection = DriverManager.getConnection(URL)) { 
                        System.out.println("Dispatching the query..."); 
                        try ( 
                            final Statement statement = connection.createStatement(); 
                            final ResultSet resultSet = statement.executeQuery(QUERY_TEMPLATE_2)) { 
 
                                System.out.println("Contents of the Student table:"); 
                                System.out.println("ID | first name | last name | GPA | major | classification "); 
 
                                // Unpack the tuples returned by the database and print them out to the user 
                                while (resultSet.next()) { 
                                    System.out.println(String.format("%s | %s | %s | %s | %s | %s ", 
                                        resultSet.getString(1), 
                                        resultSet.getString(2), 
                                        resultSet.getString(3), 
                                        resultSet.getString(4), 
                                        resultSet.getString(5), 
                                        resultSet.getString(6))); 
                                } 
                        } 
                    } 
 
                    break; 
                case "3": // Do nothing, the while loop will terminate upon the next iteration 
                    System.out.println("Exiting! Good-buy!"); 
                    break; 
                default: // Unrecognized option, re-prompt the user for the correct one 
                    System.out.println(String.format( 
                        "Unrecognized option: %s\n" +  
                        "Please try again!",  
                        option)); 
                    break; 
            } 
        } 
 
        sc.close(); // Close the scanner before exiting the application 
    } 
}  