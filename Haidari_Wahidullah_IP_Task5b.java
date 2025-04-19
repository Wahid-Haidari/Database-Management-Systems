import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection; 
import java.sql.Statement; 
import java.util.Scanner; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;

 public class MyProducts { 
 
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
    
    //Query 1, insert to Worker
    final static String QUERY1_WORKER = "INSERT INTO Worker " +
			"VALUES (?, ?, ?, ?);";
    
  //Query 1, insert to QualityController
    final static String QUERY1_QUALITY_CONTROLLER = "INSERT INTO QualityController " +
			"VALUES (?, ?, ?, ?);";
    
  //Query 1, insert to TechnicalStaff
    final static String QUERY1_TECHNICAL_STAFF = "INSERT INTO TechnicalStaff " +
			"VALUES (?, ?, ?, ?, ?);";
    
  //Query 1, insert to Undergrad Technical Staff
    final static String QUERY1_UNDERGRAD_TECHNICAL_STAFF = "INSERT INTO UndergradTechnicalStaff " +
			"VALUES (?, ?, ?, ?, ?);";
    
    //Query 1, insert to Grad Technical Staff
    final static String QUERY1_GRAD_TECHNICAL_STAFF = "INSERT INTO GradTechnicalStaff " +
			"VALUES (?, ?, ?, ?, ?);";
    
    
    //Query 2, insert to Product
    final static String QUERY2_PRODUCT = "INSERT INTO Product " +
			"VALUES (?, ?, ?, ?, ?, ?);";
    
    //Query 2, insert to Product type 1
    final static String QUERY2_PRODUCT1 = "INSERT INTO Product1 " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    
    //Query 2, insert to Product type 2
   final static String QUERY2_PRODUCT2 = "INSERT INTO Product2 " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    
   //Query 2, insert to Product type 3
   final static String QUERY2_PRODUCT3 = "INSERT INTO Product3 " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
   
   //Query 2, insert to Produce
   final static String QUERY2_PRODUCE = "INSERT INTO Produce " +
			"VALUES (?, ?);";
   
   //Query 2, insert to Test
   final static String QUERY2_TEST = "INSERT INTO Test " +
			"VALUES (?, ?);";
   //Query 2, insert to Repair
   final static String QUERY2_REPAIR = "INSERT INTO Repair " +
			" (tech_staff_name, product_id) VALUES (?, ?);";
   
   //Query 2, insert to RepairP1. This table includes all the products of type 1,
   // and their associated graduate technical staff who repaired them.
   final static String QUERY2_REPAIR_P1 = "INSERT INTO RepairP1 " +
			" (tech_staff_name, product1_id, account1_num) VALUES (?, ?, ?);";
   
   //Query 2, insert to Request. When a product is repaired because it
   //is requested by a quality controller.
   final static String QUERY2_REQUEST = "INSERT INTO Request " +
			" (qual_cont_name, product_id, tech_staff_name) VALUES (?, ?, ?);";
   
   //Query 3 insert to customer, 
   final static String QUERY3_INSERT_CUSTOMER = "INSERT INTO Customer " + 
   		 	" VALUES (?, ?);";
   
   //Query 3 insert into purchase, 
   final static String QUERY3_INSERT_PURCHASE = "INSERT INTO Purchase " + 
   		 	" VALUES (?, ?);";
   
   //Query 4, Insert into account1
   final static String QUERY4_ACCOUNT1 = "INSERT INTO Account1 " +
		   "VALUES (?, ?, ?);";
   
   //Query 4, Insert into account2
   final static String QUERY4_ACCOUNT2 = "INSERT INTO Account2 " +
		   "VALUES (?, ?, ?);";
   
   //Query 4, Insert into account3
   final static String QUERY4_ACCOUNT3 = "INSERT INTO Account3 " +
		   "VALUES (?, ?, ?);";
   
   //Query 5, Insert into Complaint
   final static String QUERY5_INSERT_COMPLAINT= "INSERT INTO Complaint " +
		   "VALUES (?, ?, ?, ?);";
   
   //Query 5, Insert into Make
   final static String QUERY5_INSERT_MAKE= "INSERT INTO Make " +
		   "VALUES (?, ?, ?);";
   
   //Query 5, Insert into Got
   final static String QUERY5_INSERT_GOT = "INSERT INTO Got " +
		   "VALUES (?, ?,? );";
   
   //Query 6, Insert into Accident
   final static String QUERY6_INSERT_ACCIDENT = "INSERT INTO Accident " +
		   "VALUES (?, ?, ?);";
   
   //Query 6, Insert into ProduceCause
   final static String QUERY6_INSERT_PRODUCE_CAUSE = "INSERT INTO ProduceCause " +
		   "VALUES (?, ?, ?);";
   
   //Query 6, Insert into RepairCause
   final static String QUERY6_INSERT_REPAIR_CAUSE = "INSERT INTO RepairCause " +
		   "VALUES (?, ?, ?);";
   
   //Query 7
   final static String QUERY7 = "SELECT production_date , time_spent FROM Product " +
		   "WHERE product_id = ?;";
   
   //Query 8
   final static String QUERY8 = "SELECT Product.* FROM Produce JOIN Product " +
		   "ON Produce.product_id = Product.product_id " +
		   "WHERE worker_name = ?;";
   
   //Query 9
   final static String QUERY9 = "SELECT COUNT (Make.product_id) FROM Make JOIN Test " +
		   "ON Make.product_id = Test.product_id " +
		   "WHERE qual_cont_name = ?;";
   
   //Query 10
   final static String QUERY10 = "SELECT SUM (product3_cost) AS total_product3_cost " +
		   "FROM Product3 " +
		   "JOIN Account3 ON  Product3.account3_num = Account3.account3_num " +
		   "JOIN Request ON Request.product_id = Product3.product3_id " +
		   "JOIN Repair ON Repair.product_id = Product3.product3_id " +
		   "WHERE Request.qual_cont_name = ?;";
   
   //Query 11
   final static String QUERY11 = "SELECT Customer.customer_name " +
		   "FROM Customer, Product2, Purchase " +
		   "WHERE Customer.customer_name = Purchase.customer_name " +
		   "AND product_id = Product2.product2_id " +
		   "AND Product2.color = ? " +
		   "ORDER BY customer_name;";
   
   //Query 12
   final static String QUERY12 = "WITH Employee as ( " +
		   "SELECT salary,addr, worker_name as employee_name FROM Worker " +
		   "UNION " +
		   "SELECT salary, addr, qual_cont_name as employee_name FROM QualityController " +
		   "UNION " +
		   "SELECT salary, addr, tech_staff_name as employee_name " +
		   "FROM TechnicalStaff ) " +
		   "SELECT employee_name, addr AS address, salary " +
		   "FROM Employee Where salary > ?;";
		   
   //Query 13
   final static String QUERY13 = "SELECT SUM (Accident.num_work_day) " +
		   "FROM Accident " +
		   "JOIN RepairCause " +
		   "ON RepairCause.accident_number = Accident.accident_number " +
		   "JOIN Got ON Got.product_id = RepairCause.product_id;";
   
   //Query 14
   final static String QUERY14 = "WITH unionized as ( " +
		   "SELECT product_id, Product.production_date,  product1_cost as cost " +
		   "FROM Product " +
		   "JOIN Product1 ON Product.product_id = Product1.product1_id " +
		   "JOIN Account1 ON Product1.account1_num = Account1.account1_num " +
		   "UNION " +
		   "SELECT product_id, Product.production_date, product2_cost as cost " +
		   "FROM Product " +
		   "JOIN Product2 ON Product.product_id = Product2.product2_id " +
		   "JOIN Account2 ON Product2.account2_num = Account2.account2_num " +
		   "UNION " +
		   "SELECT product_id, Product.production_date, product3_cost as cost " +
		   "FROM Product " +
		   "JOIN Product3 ON Product.product_id = Product3.product3_id " +
		   "JOIN Account3 ON Product3.account3_num = Account3.account3_num " +
		   ") " +
		   "SELECT AVG(cost) FROM unionized "+
		   "WHERE YEAR(production_date) = ?";
   
   //Query 15
   
   final static String QUERY15_PRODUCE_CAUSE = "DELETE ProduceCause FROM ProduceCause pc " +
		   "RIGHT JOIN Accident ac on ac.accident_number = pc.accident_number " +
		   "WHERE accident_date >= ? AND accident_date <= ?";
   
   final static String QUERY15_REPAIR_CAUSE = "DELETE RepairCause FROM RepairCause rc " +
		   "RIGHT JOIN Accident ac on ac.accident_number = rc.accident_number " +
		   "WHERE accident_date >= ? AND accident_date <= ?";
   
   final static String QUERY15_ACCIDENT = "DELETE FROM Accident " +
		   "WHERE accident_date >= ? AND accident_date <= ?";
   
   
   //Query 15. Print Accident after deletion
   final static String QUERY15_PRINT = "SELECT * FROM Accident;";
    
    // User input prompt// 
    final static String PROMPT =  
            "\nPlease select one of the options below: \n" + 
            "1) Enter a new employee. \n" +
            "2) Enter a new product associated with the person who made the product, \n" + 
            "repaired the product if it is repaired, or checked the product \n" + 
            "3) Enter a customer associated with some products.\n" +
            "4) Create a new account associated with a product.\n" + 
            "5) Enter a complaint associated with a customer and product.\n" + 
            "6) Enter an accident associated with an appropriate employee and product.\n" + 
            "7) Retrieve the date produced and time spent to produce a particular product.\n" + 
            "8) Retrieve all products made by a particular worker.\n" +
            "9) Retrieve the total number of errors a particular quality controller made.\n" +
            "This is the total number of products certified by this controller and got some complaints.\n" +   
            "10) Retrieve the total costs  of the  products  in the product3 category which \n" + 
            "were repaired at the request of a particular quality controller. \n" +
            "11) Retrieve  all  customers  (in  name  order)  who  purchased  all  products  of  a  particular  color.\n" +  
            "12) Retrieve all employees whose salary is above a particular salary.\n" + 
            "13) Retrieve the total number of workdays lost due to accidents in \n" +
            		"repairing the products which got complaints.\n" +   
            "14) Retrieve the average cost of all products made in a particular year.\n" + 
            "15) Delete all accidents whose dates are in some range.\n" +
            "16) Import\n" +
            "17) Export\n" +
            "18) Quit"; 
 
    public static void main(String[] args) throws SQLException, FileNotFoundException { 
    	
    	//print the the welcome message
        System.out.println("WELCOME TO THE DATABASE SYSTEM OF MyProducts, Inc."); 
 
        
        final Scanner sc = new Scanner(System.in); // Scanner is used to collect the user input 
        String option = ""; // Initialize user option selection as nothing 
        while (!option.equals("18")) { // As user for options until option 18 is selected 
            System.out.println(PROMPT); // Print the available options 
            option = sc.next(); // Read in the user option selection 
 
            switch (option) { // Switch between different options 
                case "1": // Insert a new employee 
                    option1(sc);    
                    break;                    
                case "2": // Insert a new product               	
                	option2(sc);
                	break;                	
                case "3": 
                	option3(sc);
                	break;               	
                case "4":
                	option4(sc);
                	break;                	
                case "5":
                	option5(sc);              
                	break;                	
                case "6":
                	option6(sc);	
                	break;                	
                case "7":
                	option7(sc);
                	break;                	
                case "8":
                	option8(sc);
                	break;                	
                case "9":
                	option9(sc);
                	break;                	
                case "10":
                	option10(sc);
                	break;                	
                case "11":
                	option11(sc);
                	break;                	
                case "12":
                	option12(sc);
                	break;                	
                case "13":                	  
                	option13();
                	break;               	
                case "14":
                	option14(sc);
                	break;               	
                case "15":
                	option15(sc);
                	break;               	
                case "16":  //Import     	               	                	
                	option16(sc);
                	break;                       	
                case "17": //Export
                	option17(sc);
                	break;                	
                case "18": // Do nothing, the while loop will terminate upon the next iteration 
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
    
    //Option1 --------------------------------------------------------------------------------
    public static void option1(Scanner sc) throws SQLException {
    	// Since we have three different tables for the 3 types of employees, we ask the user to pick what kind
    	//of employee they want to insert.
        System.out.println("Do you want enter a worker, a quality controller or a technical staff?\n"
        		+ "Enter w for worker.\n"
        		+ "Enter q for quality controller.\n"
        		+ "Enter t for technical staff.\n"
        		);
        char employeeOption = sc.next().charAt(0);
        switch (employeeOption) {
        	case 'w': //For worker
        		insertWorker(sc);        	
            break;
        	case 'q': //For quality controller
        		insertQualityController(sc);
            break;
        	case 't': // For technical staff
        		insertTechnicalStaff(sc);
        	break;
        	default:
        		System.out.println("unrecognizable option");
        		
        }                          
    }
    
    //If the user wants to insert a worker
    public static void insertWorker(Scanner sc) throws SQLException {
    	sc.nextLine();// Read next line if to consume any any newline characters if left from other scannnings.
    	System.out.println("Enter the name of the Worker.");
		String workerName = sc.nextLine();
		
		System.out.println("Enter the address of the Worker in one line.");
		String workerAddress = sc.nextLine();
		System.out.println("Enter the salary of the Worker in dollars.");
		double workerSalary = sc.nextDouble();
		System.out.println("Enter the maximum number of product a worker produces per day.");
		int maxNamProdPerDay = sc.nextInt();
		
		 System.out.println("Connecting to the database..."); 
         // Get a database connection and prepare a query statement 
         try (final Connection connection = DriverManager.getConnection(URL)) { 
             try ( 
                 final PreparedStatement statement = connection.prepareStatement(QUERY1_WORKER)) { 
                 // Populate the query template with the data collected from the user 
                 statement.setString(1, workerName); 
                 statement.setString(2, workerAddress); 
                 statement.setDouble(3, workerSalary); 
                 statement.setInt(4, maxNamProdPerDay); 
        
                 System.out.println("Dispatching the query..."); 
                 // Actually execute the populated query 
                 final int rows_inserted = statement.executeUpdate(); 
                 System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
             } 
         } 
    }
    
    //When the user inserts a new quality controller
    public static void insertQualityController(Scanner sc) throws SQLException {
    	sc.nextLine();
    	System.out.println("Enter the name of the quality controller.");
		String qualContName = sc.nextLine();
		System.out.println("Enter the address of the quality controller in one line.");
		String qualContAddress = sc.nextLine();
		System.out.println("Enter the salary of the quality controller.");
		double qualContSalary = sc.nextDouble();
		System.out.println("Enter the product type the quality controller tests.\n" +
				"enter type1 for type 1\n" +
				"enter type1 for type 2\n" +
				"enter type1 for type 3." 				
				);
		String productType = sc.next();
		
		 System.out.println("Connecting to the database..."); 
         // Get a database connection and prepare a query statement 
         try (final Connection connection = DriverManager.getConnection(URL)) { 
             try (final PreparedStatement statement = connection.prepareStatement(QUERY1_QUALITY_CONTROLLER)) { 
                 // Populate the query template with the data collected from the user 
                 statement.setString(1, qualContName); 
                 statement.setString(2, qualContAddress); 
                 statement.setDouble(3, qualContSalary); 
                 statement.setString(4, productType);
        

                 System.out.println("Dispatching the query..."); 
                 // Actually execute the populated query 
                 final int rows_inserted = statement.executeUpdate(); 
                 System.out.println(String.format("Done. %d rows inserted.", rows_inserted));                
             } 
         } 
    }
    
    //When the user inserts a new technical staff
    public static void insertTechnicalStaff(Scanner sc) throws SQLException {
    	sc.nextLine();
    	System.out.println("Enter the name of the technical staff.\n");
		String techStaffName = sc.nextLine();
		System.out.println("Enter the address of the technical staff in one line.\n");
		String techStaffAddress = sc.nextLine();
		System.out.println("Enter the salary of the technical staff.\n");
		double techStaffSalary = sc.nextDouble();
		sc.nextLine();
		System.out.println("Enter the education record for the technical staff.\n");
		String educationRecord = sc.nextLine();
		System.out.println("Enter the technical position of the technical staff.\n");
		String technicalPosition = sc.nextLine();
		
		 System.out.println("Connecting to the database..."); 
         // Get a database connection and prepare a query statement 
         try (final Connection connection = DriverManager.getConnection(URL)) { 
             try (final PreparedStatement statement = connection.prepareStatement(QUERY1_TECHNICAL_STAFF)) { 
                 // Populate the query template with the data collected from the user 
                 statement.setString(1, techStaffName); 
                 statement.setString(2, techStaffAddress); 
                 statement.setDouble(3, techStaffSalary); 
                 statement.setString(4, educationRecord);
                 statement.setString(5, technicalPosition);
                 
                 System.out.println("Dispatching the query..."); 
                 // Actually execute the populated query 
                 final int rows_inserted = statement.executeUpdate(); 
                 System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                 
                 
                 //If the technical staff has a BS degree, add it also to the table designated for the undergraduate technical staff
                 if (educationRecord.equals("BS")) {
                	 
                	 try (final PreparedStatement statementForUndergrad = connection.prepareStatement(QUERY1_UNDERGRAD_TECHNICAL_STAFF)) { 
                             // Populate the query template with the data collected from the user 
                		 	statementForUndergrad.setString(1, techStaffName); 
                		 	statementForUndergrad.setString(2, techStaffAddress); 
                		 	statementForUndergrad.setDouble(3, techStaffSalary); 
                		 	statementForUndergrad.setString(4, educationRecord);
                		 	statementForUndergrad.setString(5, technicalPosition);
                             
                             // Actually execute the populated query 
                             final int rows_inserted_undergrad = statementForUndergrad.executeUpdate(); 
                             System.out.println(String.format("Done. %d rows inserted.", rows_inserted_undergrad ));
                	 }
                 }
                 
                 
                 //If the technical staff is not a BS, they are either MS or PhD. So in addition to the technical staff table
                 // Put them in their designated table, 'Grad Tehcnical Staff' too.
                 else {
                	 
                	 try (final PreparedStatement statementForGrad = connection.prepareStatement(QUERY1_GRAD_TECHNICAL_STAFF)) { 
                             // Populate the query template with the data collected from the user 
                		 	statementForGrad.setString(1, techStaffName); 
                		 	statementForGrad.setString(2, techStaffAddress); 
                		 	statementForGrad.setDouble(3, techStaffSalary); 
                		 	statementForGrad.setString(4, educationRecord);
                		 	statementForGrad.setString(5, technicalPosition);
                             
                             // Actually execute the populated query 
                             final int rows_inserted_grad = statementForGrad.executeUpdate(); 
                             System.out.println(String.format("Done. %d rows inserted.", rows_inserted_grad ));
                	 } //Close try CHANGE, add small case also
                 }
             }//End of Second try
         }
    }
    
    //Option 2 -----------------------------------------------------------------------------------------------
    
    //Since their are 3 types of products, ask the user about the type of product they want to insert.
    public static void option2(Scanner sc) throws SQLException {
    	System.out.println("Which type of product you want to enter?\n"
        		+ "Enter 1 for type1\n"
        		+ "Enter 2 for type2\n"
        		+ "Enter 3 for type3"
        		);
    
    	int productTypeOption = sc.nextInt();
    	System.out.print("Enter the product id\n");
		int productID = sc.nextInt();
		System.out.print("Enter the production date in the format YYYY-MM-DD\n");
		String productionDate = sc.next();
		System.out.println("Enter the number of hours spent on the product.");
		double timeSpent = sc.nextDouble();                   		                  	
		System.out.println("Enter the name of the employee who produced the product.");
		sc.nextLine();
		String producedBy = sc.nextLine();                    		
		System.out.println("Enter the name of the employee who tested the product.");
		String testedBy = sc.nextLine();
		System.out.println("Is this product repaired?\n"
				+ "Enter y for yes.\n"
				+ "Enter n for no.");
		String isRepaired = sc.nextLine();
		
		String repairedBy ="";
		if(isRepaired.equals("y")){
			System.out.println("Enter the name of the employee who repaired the product.\n");
			repairedBy = sc.nextLine();
		}
		
    	
		//Insert any product, in the Product table
		try (final Connection connection = DriverManager.getConnection(URL)) { 
			try (final PreparedStatement statement = connection.prepareStatement(QUERY2_PRODUCT)) { 
                // Populate the query template with the data collected from the user 
                statement.setInt(1, productID); 
                statement.setString(2, productionDate); 
                statement.setDouble(3, timeSpent); 
                statement.setString(4, producedBy); 
                statement.setString(5, testedBy);
                statement.setString(6, repairedBy);
                                                                   
                // Actually execute the populated query 
                final int rows_inserted = statement.executeUpdate();                
             } 
        }
		
		//Now in addition to adding the product in the Product table, 
		//add them to the tables designated for their types also.
    	switch(productTypeOption) {
    		case 1: //If the type of the product a user wants to enter is type 1.
    			insertProductType1(sc, productID, productionDate, timeSpent, producedBy, testedBy, repairedBy, isRepaired);       		
                 break;    			
    		case 2: //If the type of the product a user wants to enter is type 2.		
    			insertProductType2(sc, productID, productionDate, timeSpent, producedBy, testedBy, repairedBy);   			
    			break;    			
    		case 3: //If the type of the product a user wants to enter is type 3.
    			insertProductType3(sc, productID, productionDate, timeSpent, producedBy, testedBy, repairedBy);        		
    	}
 	
        //When a new product is added, a new entry is added to Produce table and this will check if
    	//the employee who produced it exists. If they do not exist, the sql will give error.
         try (final Connection connection = DriverManager.getConnection(URL)) { 
                 try ( final PreparedStatement statement = connection.prepareStatement(QUERY2_PRODUCE)) { 
                         // Populate the query template with the data collected from the user 
                     statement.setString(1, producedBy); 
                     statement.setInt(2, productID); 

                     System.out.println("Dispatching the query..."); 
                     // Actually execute the populated query 
                     final int rows_inserted = statement.executeUpdate(); 
                     System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
                 } 
         }
         
         //When a new product is added, a new entry is added to Test table and this will check if
     	//the employee who tested it exists. If they do not exist, the sql will give error.
         try (final Connection connection = DriverManager.getConnection(URL)) { 
                 try ( final PreparedStatement statement = connection.prepareStatement(QUERY2_TEST)) { 
                         // Populate the query template with the data collected from the user 
                     statement.setString(1, testedBy); 
                     statement.setInt(2, productID); 

                     System.out.println("Dispatching the query..."); 
                     // Actually execute the populated query 
                     final int rows_inserted = statement.executeUpdate(); 
                     System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
                 } 
         }
         
         //When a new product is added, a new entry is added to Repair Relation and this will check if
     	//the employee who repaired it exists. If they do not exist, the sql will give error.
         
         if(isRepaired.equals("y")) {
        	 try (final Connection connection = DriverManager.getConnection(URL)) { 
                 try ( final PreparedStatement statement = connection.prepareStatement(QUERY2_REPAIR)) { 
                         // Populate the query template with the data collected from the user 
                     statement.setString(1, repairedBy); 
                     statement.setInt(2, productID);

                     System.out.println("Dispatching the query..."); 
                     // Actually execute the populated query 
                     final int rows_inserted = statement.executeUpdate(); 
                     System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
                 }
        	 }
        	 
        	 try (final Connection connection = DriverManager.getConnection(URL)) { 
                 try ( final PreparedStatement statement = connection.prepareStatement(QUERY2_REQUEST)) { 
                         // Populate the query template with the data collected from the user 
                     statement.setString(1, testedBy); 
                     statement.setInt(2, productID);
                     statement.setString(3, repairedBy); 
                     

                     System.out.println("Dispatching the query..."); 
                     // Actually execute the populated query 
                     final int rows_inserted = statement.executeUpdate(); 
                     System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
                 }
        	 }   
         }
                                      
    }
    
    //-----------------------------------------------------------------------------------------------------------
    //This method is called to inserts products of type1 into Product1 table.
    public static void insertProductType1(Scanner sc, int productID, String productionDate,
    		double timeSpent, String producedBy, String testedBy, String repairedBy, String isRepaired) throws SQLException {
    	System.out.println("Enter the name of the product.");
		String product1Name = sc.nextLine();
		System.out.println("Enter the sofware used for this product.");
		String software = sc.nextLine();
		System.out.print("Enter the product's account number\n");
		int account1Num = sc.nextInt();

		 System.out.println("Connecting to the database..."); 
         // Get a database connection and prepare a query statement 
         try (final Connection connection = DriverManager.getConnection(URL)) { 
             try ( 
                 final PreparedStatement statement = connection.prepareStatement(QUERY2_PRODUCT1)) { 
                 // Populate the query template with the data collected from the user 
                 statement.setInt(1, productID); 
                 statement.setString(2, productionDate); 
                 statement.setDouble(3, timeSpent); 
                 statement.setString(4, producedBy); 
                 statement.setString(5, testedBy);
                 statement.setString(6, repairedBy);
                 statement.setString(7, product1Name);
                 statement.setString(8, software);
                 statement.setInt(9, account1Num);
        

                 System.out.println("Dispatching the query..."); 
                 // Actually execute the populated query 
                 final int rows_inserted = statement.executeUpdate(); 
                 System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
             } 
         }
         
         /*After we insert the product of type1, we also add entries to the table RepareP1 which
         is a relation between product type 1 and Grad Technical Staff. This will check if the
         technical staff who repairs the product is a graduate technical staff. If it is not the SQL
         will give an error message. */
         if(isRepaired.equals("y")) {
        	 
        	 try (final Connection connection = DriverManager.getConnection(URL)) { 
                 try ( final PreparedStatement statement = connection.prepareStatement(QUERY2_REPAIR_P1)) { 
                         // Populate the query template with the data collected from the user 
                     statement.setString(1, repairedBy); 
                     statement.setInt(2, productID);
                     statement.setInt(3, account1Num);

                     System.out.println("Dispatching the query..."); 
                     // Actually execute the populated query 
                     final int rows_inserted = statement.executeUpdate(); 
                     System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
                 } 
             }
         }
         
    }
    
    //--------------------------------------------------------------------------------------------------------
  //This method is called to inserts products of type2 into Product1 table.
    public static void insertProductType2(Scanner sc, int productID, String productionDate,
    		double timeSpent, String producedBy, String testedBy, String repairedBy) throws SQLException {
    	System.out.println("Enter the size of the product. S, M, or L.");
		String product2Size = sc.nextLine();
		System.out.println("Enter the color of the product.");
		String color = sc.nextLine();
		System.out.print("Enter the product's account number\n");
		int account2Num = sc.nextInt();

		 System.out.println("Connecting to the database...");
		 
		
         // Get a database connection and prepare a query statement 			 
		 
	         try (final Connection connection = DriverManager.getConnection(URL)) { 
	             try ( 
	                 final PreparedStatement statement = connection.prepareStatement(QUERY2_PRODUCT2)) { 
	                 // Populate the query template with the data collected from the user 
	                 statement.setInt(1, productID); 
	                 statement.setString(2, productionDate); 
	                 statement.setDouble(3, timeSpent); 
	                 statement.setString(4, producedBy); 
	                 statement.setString(5, testedBy);
	                 statement.setString(6, repairedBy);
	                 statement.setString(7, product2Size);
	                 statement.setString(8, color);
	                 statement.setInt(9, account2Num);
	        
	
	                 System.out.println("Dispatching the query..."); 
	                 // Actually execute the populated query 
	                 final int rows_inserted = statement.executeUpdate(); 
	                 System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
	             } 
	         }
		 
    }
    
    //-------------------------------------------------------------------------------------------------
  //This method is called to inserts products of type3 into Product1 table.
    public static void insertProductType3(Scanner sc, int productID, String productionDate,
    		double timeSpent, String producedBy, String testedBy, String repairedBy) throws SQLException {
    	System.out.println("Enter the size of the product. S, M, or L");
		String product3Size = sc.next();
		System.out.println("Enter the weight of the product in grmas.");
		double weight = sc.nextDouble();
		System.out.print("Enter the product's account number\n");
		int account3Num = sc.nextInt();

		 System.out.println("Connecting to the database...");
		 
		
         // Get a database connection and prepare a query statement
	         try (final Connection connection = DriverManager.getConnection(URL)) { 
	             try (final PreparedStatement statement = connection.prepareStatement(QUERY2_PRODUCT3)) { 
	                 // Populate the query template with the data collected from the user 
	                 statement.setInt(1, productID); 
	                 statement.setString(2, productionDate); 
	                 statement.setDouble(3, timeSpent); 
	                 statement.setString(4, producedBy); 
	                 statement.setString(5, testedBy);
	                 statement.setString(6, repairedBy);
	                 statement.setString(7, product3Size);
	                 statement.setDouble(8, weight);
	                 statement.setInt(9, account3Num);
	
	                 System.out.println("Dispatching the query..."); 
	                 // Actually execute the populated query 
	                 final int rows_inserted = statement.executeUpdate(); 
	                 System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
	             } 
	         }
		 
    }
    
  //Option 3 --------------------------------------------------------------------------------
    public static void option3(Scanner sc) throws SQLException {
    	sc.nextLine();
    	System.out.println("Enter the customer name\n");
    	String customerName = sc.nextLine();
    	System.out.println("Enter the address of the customer in one line.");
    	String customerAddress = sc.nextLine();
    	System.out.println("Enter the product id of that the customer bought.");
    	int customerBuyProductID= sc.nextInt();
    	
    	 try (final Connection connection = DriverManager.getConnection(URL)) { 
             try ( 
                 final PreparedStatement statement = connection.prepareStatement(QUERY3_INSERT_CUSTOMER)) { 
                 // Populate the query template with the data collected from the user 
                 statement.setString(1, customerName); 
                 statement.setString(2, customerAddress); 
                 System.out.println("Dispatching the query..."); 
                 // Actually execute the populated query 
                 final int rows_inserted = statement.executeUpdate(); 
                 System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
             } 
         }
    	 
    	 try (final Connection connection = DriverManager.getConnection(URL)) { 
             try ( 
                 final PreparedStatement statement = connection.prepareStatement(QUERY3_INSERT_PURCHASE)) { 
                 // Populate the query template with the data collected from the user 
                 statement.setInt(1, customerBuyProductID); 
                 statement.setString(2, customerName); 
                 System.out.println("Dispatching the query..."); 
                 // Actually execute the populated query 
                 final int rows_inserted = statement.executeUpdate(); 
                 System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
             } 
         }
    }
    
    //Option 4 --------------------------------------------------------------------------------
    
    //We have 3 kinds of accounts each for a product type. 
    //Therefore, we ask the user the type of account they want to enter.
    public static void option4(Scanner sc) throws SQLException {
    	System.out.println("What type of Account do you want to enter?\n"
    			+ "enter 1, for type 1\n"
    			+ "enter 2, for type 2\n"
    			+ "enter 3, for type 3");
    	int accountTypeOption = sc.nextInt();
    	System.out.println("Enter the account number?\n");
    	int accounttNum = sc.nextInt();
    	System.out.println("Enter the establish date?\n");
    	String establishDate = sc.next();
    	System.out.println("Enter the associated product cost?\n");
    	double productCost = sc.nextDouble();
    	
    		//If the user wants to enter an account for type 1 product.
    		if (accountTypeOption == 1){
    			try (final Connection connection = DriverManager.getConnection(URL)) { 
                    try ( 
                        final PreparedStatement statement = connection.prepareStatement(QUERY4_ACCOUNT1)) { 
                        // Populate the query template with the data collected from the user 
                        statement.setInt(1, accounttNum); 
                        statement.setString(2, establishDate);
                        statement.setDouble(3, productCost); 
                        System.out.println("Dispatching the query..."); 
                        // Actually execute the populated query 
                        final int rows_inserted = statement.executeUpdate(); 
                        System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
                    } 
                }
    		}
    		
    		//If the user wants to enter an account for type 2 product.
    		if (accountTypeOption == 2){
    			try (final Connection connection = DriverManager.getConnection(URL)) { 
                    try ( 
                        final PreparedStatement statement = connection.prepareStatement(QUERY4_ACCOUNT2)) { 
                        // Populate the query template with the data collected from the user 
                        statement.setInt(1, accounttNum); 
                        statement.setString(2, establishDate);
                        statement.setDouble(3, productCost); 
                        System.out.println("Dispatching the query..."); 
                        // Actually execute the populated query 
                        final int rows_inserted = statement.executeUpdate(); 
                        System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
                    } 
                }
    		}
    		
    		//If the user wants to enter an account for type 2 product.
    		if (accountTypeOption == 3){
    			try (final Connection connection = DriverManager.getConnection(URL)) { 
                    try ( 
                        final PreparedStatement statement = connection.prepareStatement(QUERY4_ACCOUNT3)) { 
                        // Populate the query template with the data collected from the user 
                        statement.setInt(1, accounttNum); 
                        statement.setString(2, establishDate);
                        statement.setDouble(3, productCost); 
                        System.out.println("Dispatching the query..."); 
                        // Actually execute the populated query 
                        final int rows_inserted = statement.executeUpdate(); 
                        System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
                    } 
                }
    		}	
    }
    
    //Option 5 -------------------------------------------------------------------------------------------
    public static void option5(Scanner sc) throws SQLException {
    	sc.nextLine();
    	System.out.println("Enter the complaint id.");
    	int complaintID = sc.nextInt();
    	System.out.println("Enter the complaint date.");
    	String complaintDate = sc.next();
    	System.out.println("Enter the description of the issue in one line.");
    	sc.nextLine();
    	String description = sc.nextLine();
    	System.out.println("What is the treatment?");
    	String treatment = sc.nextLine();
    	System.out.println("Enter the customer's name.\n");
    	String complainCustomerName = sc.nextLine();
    	System.out.println("Enter the product ID.\n");
    	int complainProductID = sc.nextInt();
    	sc.nextLine();
    	System.out.println("Enter the name of the technical staff who repaired the product.");
    	String complainTechStaffName = sc.nextLine();
    	
    	
    	//Insert data into the Complaint Table
    	try (final Connection connection = DriverManager.getConnection(URL)) { 
            try ( final PreparedStatement statement = connection.prepareStatement(QUERY5_INSERT_COMPLAINT)) { 
                    // Populate the query template with the data collected from the user 
                statement.setInt(1, complaintID);
                statement.setString(2, complaintDate);
                statement.setString(3, description);
                statement.setString(4, treatment); 

                System.out.println("Dispatching the query..."); 
                // Actually execute the populated query 
                final int rows_inserted = statement.executeUpdate(); 
                System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
            } 
    	}
    	
    	//Insert data into the Make table
    	try (final Connection connection = DriverManager.getConnection(URL)) { 
            try ( final PreparedStatement statement = connection.prepareStatement(QUERY5_INSERT_MAKE)) { 
                    // Populate the query template with the data collected from the user 
                statement.setString(1, complainCustomerName); 
                statement.setInt(2, complainProductID);
                statement.setInt(3, complaintID);

                System.out.println("Dispatching the query..."); 
                // Actually execute the populated query 
                final int rows_inserted = statement.executeUpdate(); 
                System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
            } 
    	}
    	
    	//Insert data into the table Got. This table is used when a product 
    	//is repaired by a technical staff because it got a complaint.
    	try (final Connection connection = DriverManager.getConnection(URL)) { 
            try ( final PreparedStatement statement = connection.prepareStatement(QUERY5_INSERT_GOT)) { 
                    // Populate the query template with the data collected from the user 
            	statement.setInt(1, complaintID);
            	statement.setInt(2, complainProductID);
                statement.setString(3, complainTechStaffName); 
                
                

                System.out.println("Dispatching the query..."); 
                // Actually execute the populated query 
                final int rows_inserted = statement.executeUpdate(); 
                System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
            } 
    	}
    }
    
    //Option 6---------------------------------------------------------------------------------------
    public static void option6(Scanner sc) throws SQLException {
    	sc.nextLine();
    	System.out.println("Enter the accident number");
    	int accidentNum = sc.nextInt();
    	System.out.println("Enter the accident date");
    	String accidentDate = sc.next();
    	System.out.println("Enter the number of work days lost due to the accident.\n");
    	int numDaysLost = sc.nextInt();
    	System.out.println("Enter the product id that had the accident.\n");
    	int accidentProductID = sc.nextInt();
    	
    	//There are two causes for the accident. We ask the user to enter the cause.
    	System.out.println("Is this accident caused during production or during repair?\n" +
    			
    			"Enter p for production\n" +
    			"Enter r for repair");
    	char accidentCauseOption = sc.next().charAt(0);
    	
    	//Regardless of the cuase of the accident, we enter entry to the accident table.
    	try (final Connection connection = DriverManager.getConnection(URL)) { 
            try ( final PreparedStatement statement = connection.prepareStatement(QUERY6_INSERT_ACCIDENT)) { 
                // Populate the query template with the data collected from the user 
                statement.setInt(1, accidentNum); 
                statement.setString(2, accidentDate);
                statement.setInt(3, numDaysLost);

                
                // Actually execute the populated query 
                final int rows_inserted = statement.executeUpdate(); 
                
            } 
    	}
    	
    	
    	switch(accidentCauseOption) {
    		case 'p': //If the accident is caused during the production
    			sc.nextLine();
    			System.out.println("Enter the name of the worker.");
        		String accidentWorkerName = sc.nextLine();
        		
        		try (final Connection connection = DriverManager.getConnection(URL)) { 
                    try ( final PreparedStatement statement = connection.prepareStatement(QUERY6_INSERT_PRODUCE_CAUSE)) { 
                        // Populate the query template with the data collected from the user 
                        statement.setInt(1, accidentNum); 
                        statement.setString(2, accidentWorkerName);
                        statement.setInt(3, accidentProductID);

                        System.out.println("Dispatching the query..."); 
                        // Actually execute the populated query 
                        final int rows_inserted = statement.executeUpdate(); 
                        System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
                    } 
            	}
        		break;
        		
    		case 'r': //If the accident is caused during the repair.
    			sc.nextLine();
    		System.out.println("Enter the name of the technical staff.");
    		String accidentTechStaffName = sc.nextLine();
    		
    		try (final Connection connection = DriverManager.getConnection(URL)) { 
                try ( final PreparedStatement statement = connection.prepareStatement(QUERY6_INSERT_REPAIR_CAUSE)) { 
                    // Populate the query template with the data collected from the user 
                    statement.setInt(1, accidentNum); 
                    statement.setString(2, accidentTechStaffName);
                    statement.setInt(3, accidentProductID);

                    System.out.println("Dispatching the query..."); 
                    // Actually execute the populated query 
                    final int rows_inserted = statement.executeUpdate(); 
                    System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
                } 
        	}
    		
    	}                
    }
    
    //Option 7 -----------------------------------------------------------------------------------------------------
    public static void option7(Scanner sc) throws SQLException {
    	sc.nextLine();
    	System.out.println("Enter the product id");
        
    	int q7ProductID = sc.nextInt();
    	System.out.println("Connecting to the database...");
    	try (final Connection connection = DriverManager.getConnection(URL)) {
    		System.out.println("Dispatching the query...");
            try (final PreparedStatement statement = connection.prepareStatement(QUERY7)){
            	statement.setInt(1, q7ProductID);
            	System.out.println("Here is the result:");
        		System.out.println("Production Date | Time Spent ");
        		// Unpack the tuples returned by the database and print them out to the user
        		ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    System.out.println(String.format("%s | %s ",
                        resultSet.getString(1),
                        resultSet.getString(2)));
                }            	
            }                                       
    	}
    	
    }
    
    //Option 8 ------------------------------------------------------------------------------------------------------
    public static void option8(Scanner sc) throws SQLException {
    	sc.nextLine();
    	System.out.println("Enter the worker's name.");
        
    	String q8workerName = sc.nextLine();
    	System.out.println("Connecting to the database...");
    	try (final Connection connection = DriverManager.getConnection(URL)) {
    		System.out.println("Dispatching the query...");
            try (final PreparedStatement statement = connection.prepareStatement(QUERY8)){
            	statement.setString(1, q8workerName);
            	System.out.println("Here is the result:");
        		System.out.println("Product ID | Production Date | Time Spent | Produced By |" +
        				" Tested By | Repaired By");
        		// Unpack the tuples returned by the database and print them out to the user
        		ResultSet resultSet = statement.executeQuery();
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
    }
    
    //Option 9 --------------------------------------------------------------------------------
    public static void option9(Scanner sc) throws SQLException {
    	sc.nextLine();
    	System.out.println("Enter the quality controller's name who made the mistake.");
        
    	String q9QualContName = sc.nextLine();
    	System.out.println("Connecting to the database...");
    	try (final Connection connection = DriverManager.getConnection(URL)) {
    		System.out.println("Dispatching the query...");
            try (final PreparedStatement statement = connection.prepareStatement(QUERY9)){
            	statement.setString(1, q9QualContName);
            	System.out.println("Here is the result:");
        		System.out.println("Total Number of Products:");
        		// Unpack the tuples returned by the database and print them out to the user
        		ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    System.out.println(String.format("%s ",                               	
                    		resultSet.getString(1)));
                }            	
            }                                       
    	}
    }
    
    //Option 10 --------------------------------------------------------------------------------
    public static void option10(Scanner sc) throws SQLException {
    	sc.nextLine();
    	System.out.println("Enter the quality controller's name who requested the repair.");
        
    	String q10QualContName = sc.nextLine();
    	System.out.println("Connecting to the database...");
    	try (final Connection connection = DriverManager.getConnection(URL)) {
    		System.out.println("Dispatching the query...");
            try (final PreparedStatement statement = connection.prepareStatement(QUERY10)){
            	statement.setString(1, q10QualContName);
            	System.out.println("Here is the result:");
        		System.out.println("Total Cost:");
        		// Unpack the tuples returned by the database and print them out to the user
        		ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    System.out.println(String.format("%s ",                               	
                    		resultSet.getString(1)));
                }            	
            }                                       
    	}
    }
     
    //Option 11 -------------------------------------------------------------------------------
    public static void option11(Scanner sc) throws SQLException {
    	System.out.println("Enter the color.");     
    	String q11Color = sc.next();
    	System.out.println("Connecting to the database...");
    	
    	try (final Connection connection = DriverManager.getConnection(URL)) {
    		System.out.println("Dispatching the query...");
            try (final PreparedStatement statement = connection.prepareStatement(QUERY11)){
            	statement.setString(1, q11Color);
            	System.out.println("Here is the result:");
        		System.out.println("Customer Names:");
        		// Unpack the tuples returned by the database and print them out to the user
        		ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    System.out.println(String.format("%s ",                               	
                    		resultSet.getString(1)));
                }            	
            }                                       
    	}
    }
    
    //Option 12 --------------------------------------------------------------------------------
    public static void option12(Scanner sc) throws SQLException {
    	System.out.println("Enter the salary.");     
    	double q12Salary = sc.nextDouble();
    	System.out.println("Connecting to the database...");
    	
    	try (final Connection connection = DriverManager.getConnection(URL)) {
    		System.out.println("Dispatching the query...");
            try (final PreparedStatement statement = connection.prepareStatement(QUERY12)){
            	statement.setDouble(1, q12Salary);
            	
            	System.out.println("Here is the result:");
        		System.out.println("Name | Address | Salary ");
        		// Unpack the tuples returned by the database and print them out to the user
        		ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    System.out.println(String.format("%s | %s | %s ",                               	
                    		resultSet.getString(1),
                    		resultSet.getString(2),
                    		resultSet.getString(3)));
                }            	
            }                                       
    	}
    }
    
    //Option 13 --------------------------------------------------------------------------------
    public static void option13() throws SQLException {
    	System.out.println("Connecting to the database...");
    	
    	try (final Connection connection = DriverManager.getConnection(URL)) {
    		System.out.println("Dispatching the query...");
            try (
            	final Statement statement = connection.createStatement();
            	final ResultSet resultSet = statement.executeQuery(QUERY13)) { 
            	                       	
                	System.out.println("Here is the result:");
            		System.out.print("Total number of workdays lost: ");
            		// Unpack the tuples returned by the database and print them out to the user                   		
                    while (resultSet.next()) {
                        System.out.println(String.format("%s ",                               	
                        		resultSet.getString(1)));
                    }            	
            	}                                       
    	} 	
    }
    
    //Option 14 --------------------------------------------------------------------------------
    public static void option14(Scanner sc) throws SQLException {
    	System.out.println("Enter the production year.");     
    	double productionYear14 = sc.nextDouble();
    	System.out.println("Connecting to the database...");
    	
    	try (final Connection connection = DriverManager.getConnection(URL)) {
    		System.out.println("Dispatching the query...");
            try (final PreparedStatement statement = connection.prepareStatement(QUERY14)){
            	statement.setDouble(1, productionYear14);
            	
            	System.out.println("Here is the result:");
        		System.out.println("The average cost:");
        		// Unpack the tuples returned by the database and print them out to the user
        		ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    System.out.println(String.format("%s ",                               	
                    		resultSet.getString(1)));
                }            	
            }                                       
    	}
    }
    
    //Option 15 --------------------------------------------------------------------------------
    public static void option15(Scanner sc) throws SQLException {
    	
    	//Ask the user for the starting year.
    	System.out.println("Enter the earliest year in the range.");
    	String startYear = sc.next();
    	//Ask the user for the ending year.
    	System.out.println("Enter the last year in the range.");
    	String endYear = sc.next();
    	
    	System.out.println("Connecting to the database...");
    	
    	/*Before deleting from accident, we should delete from ProduceCasue because
    	   the data in ProduceCasue refer to Accident*/    	
    	try (final Connection connection = DriverManager.getConnection(URL)) {
    		System.out.println("Dispatching the query...");
            try (final PreparedStatement statement = connection.prepareStatement(QUERY15_PRODUCE_CAUSE)){
            	statement.setString(1, startYear);
            	statement.setString(2, endYear);
            	final int rows_deleted = statement.executeUpdate(); 
                System.out.println(String.format("Done. %d rows deleted.", rows_deleted));
            	
            }                                       
    	}
    	
    	/*Before deleting from accident, we should delete from RepairCasue because
        the data in RepairCasue refer to Accident*/
    	try (final Connection connection = DriverManager.getConnection(URL)) {
    		System.out.println("Dispatching the query...");
            try (final PreparedStatement statement = connection.prepareStatement(QUERY15_REPAIR_CAUSE)){
            	statement.setString(1, startYear);
            	statement.setString(2, endYear);
                final int rows_deleted = statement.executeUpdate(); 
                System.out.println(String.format("Done. %d rows deleted.", rows_deleted));
            }                                       
    	}
    	
    	try (final Connection connection = DriverManager.getConnection(URL)) {
    		System.out.println("Dispatching the query...");
            try (final PreparedStatement statement = connection.prepareStatement(QUERY15_ACCIDENT)){
            	statement.setString(1, startYear);
            	statement.setString(2, endYear);
            	System.out.println("DELETING");
            	final int rows_deleted = statement.executeUpdate(); 
                System.out.println(String.format("Done. %d rows deleted.", rows_deleted));
            }                                       
    	}
    	
    	
    	try (final Connection connection = DriverManager.getConnection(URL)) {
    		System.out.println("Dispatching the query...");
            try (
            	final Statement statement = connection.createStatement();
            	final ResultSet resultSet = statement.executeQuery(QUERY15_PRINT)) { 
            	                       	
                	System.out.println("Here is the result after delete:");
            		System.out.println("Accident number | accident date | number of work day lost");
            		// Unpack the tuples returned by the database and print them out to the user                   		
                    while (resultSet.next()) {
                        System.out.println(String.format("%s | %s | %s",                               	
                        		resultSet.getString(1),
                        		resultSet.getString(2),
                        		resultSet.getString(3)));	                                		
                    }            	
            	}                                       
    	}
    	
    }
    
    //Option 16 --------------------------------------------------------------------------------
    public static void option16(Scanner sc) throws SQLException {
    	System.out.println("Enter the name of the file including the file type. ex: .csv");
    	String fileName = sc.next();
    	
    	//specify the location of the file.
    	File file = new File("src/" + fileName);    	
    	   	
    	//Make a scanner and read the values separated by commas.
    	try { 
  	      Scanner myReader = new Scanner(file);
  	      myReader.useDelimiter(",");
  	     
  	      //This array includes all the attributes for a worker
  	      String[] workerAttr = new String[4];
  	      //This array includes all the attributes for a quality controller.
  	      String[] qualityControllerAttr = new String[4];
  	      //This array includes all the attributes for a technical staff.
  	      String[] techStaffAttr= new String[5];
  	      //a variable that will hold the values of the attributes.
  	      String data =""; //Initialize it to empty string.
  	      
  	      //Keep scanning the file until there is no more lines.
  	      while (myReader.hasNextLine()) {
  	    	  data = myReader.next();	    	  
  	    	  //If the row from the list is a worker, then take all the 
  	    	  //attributes and put then in its array          
  	    	  if(data.contains("worker")) {
  	    		  for(int i=0 ; i<4 ;i++) {
  	    			  	workerAttr[i] = myReader.next();	 
  	    		  }
  	    		  
  	    		  try (final Connection connection = DriverManager.getConnection(URL)) { 
  	    			  try ( 
  	    				final PreparedStatement statement = connection.prepareStatement(QUERY1_WORKER)) { 
  	    				// Populate the query template with the data collected from the user 
  	    				statement.setString(1, workerAttr[0]);
  	    				statement.setString(2, workerAttr[1]);
  	    				statement.setDouble(3, Double.valueOf(workerAttr[2]));
  	    				statement.setInt(4, Integer.valueOf(workerAttr[3])); 
  	          
  	    				System.out.println("Dispatching the query..."); 
  	    				// Actually execute the populated query 
  	    				final int rows_inserted = statement.executeUpdate(); 
  	    				System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
  	    			  } 
  	    		  } 
  	    	  }
  	    	  
  	    	 //If the row from the list is a quality controller, then take all the 
  	    	  //attributes and put then in its array 
  	    	  else if(data.contains("quality controller")) {
  	    		  qualityControllerAttr[0] = myReader.next(); //read the name
  	    		  qualityControllerAttr[1] = myReader.next(); //read the address
  	    		  qualityControllerAttr[2] = myReader.next(); //read the salary
  	    		  
  	    		//since this column includes the maximum number of production per year read it,
  	    		  //but don't store it because it does not apply to quality controller
  	    		  myReader.next(); 
  	    		  qualityControllerAttr[3] = myReader.next();
  	    		  
 
  	    		  try (final Connection connection = DriverManager.getConnection(URL)) { 
	    			  try ( 
	    				final PreparedStatement statement = connection.prepareStatement(QUERY1_QUALITY_CONTROLLER)) { 
	    				// Populate the query template with the data collected from the user 
	    				statement.setString(1,qualityControllerAttr[0]); 
	    				statement.setString(2, qualityControllerAttr[1]); 
	    				statement.setDouble(3, Double.valueOf(qualityControllerAttr[2]));
	    				statement.setString(4, qualityControllerAttr[3]);
	          
	    				System.out.println("Dispatching the query..."); 
	    				// Actually execute the populated query 
	    				final int rows_inserted = statement.executeUpdate(); 
	    				System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
	    			  } 
  	    		  } 
  	    	  }
  	    	  
  	    	//If the row from the list is a technical staff, then take all the 
  	    	  //attributes and put then in its array
  	    	  else if(data.contains("technical staff")) {
  	    		  techStaffAttr[0] = myReader.next();
  	    		  techStaffAttr[1] = myReader.next();
  	    		  techStaffAttr[2] = myReader.next();
  	    		  myReader.next(); //this column does not apply to technical staff. Read but don't store.
  	    		  myReader.next(); //this column does not apply to technical staff. Read but don't store.
  	    		  techStaffAttr[3] = myReader.next();
  	    		  techStaffAttr[4] = myReader.next();
  	    		  
  	    		  try (final Connection connection = DriverManager.getConnection(URL)) { 
	    			  try ( 
	    				final PreparedStatement statement = connection.prepareStatement(QUERY1_TECHNICAL_STAFF)) { 
	    				// Populate the query template with the data collected from the user 
	    				statement.setString(1, techStaffAttr[0]); 
	    				statement.setString(2, techStaffAttr[1]); 
	    				statement.setDouble(3, Double.valueOf(techStaffAttr[2]));
	    				statement.setString(4, techStaffAttr[3]);
	    				statement.setString(5, techStaffAttr[4]);
	          
	    				System.out.println("Dispatching the query..."); 
	    				// Actually execute the populated query 
	    				final int rows_inserted = statement.executeUpdate(); 
	    				System.out.println(String.format("Done. %d rows inserted.", rows_inserted)); 
	    			  } 
	    		  }
  	    	 }               	    		                 	    			                 	    		           
  	    } 	    	   	   
  	      
  	    myReader.close();
  	    } catch (FileNotFoundException e) {
  	      System.out.println("An error occurred.");
  	      e.printStackTrace();
  	    }
    }
    
    //Option 17 ----------------------------------------------------------------------------------------------
  //Exports result of query 11 to a file
    public static void option17(Scanner sc) throws SQLException, FileNotFoundException{ 	
    	System.out.println("Enter the name of the file including the format.");
    	String fileName = sc.next();
    	System.out.println("Enter the color.");     
    	String q11Color = sc.next();
    	System.out.println("Connecting to the database...");
    	  	    	
    	try (final Connection connection = DriverManager.getConnection(URL)) {
    		System.out.println("Dispatching the query...");
            try (final PreparedStatement statement = connection.prepareStatement(QUERY11)){
            	statement.setString(1, q11Color);
            	    	
                PrintStream output = new PrintStream(fileName);        
                output.printf("Here is the result:\n");
                output.printf("Customer Names:\n");
   	        		
        		// Unpack the tuples returned by the database and print them out to the user
        		ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                	output.printf(String.format("%s \n", resultSet.getString(1)));
                }   
                output.close();
            }                                       
    	}
        
    }
    
 
}  