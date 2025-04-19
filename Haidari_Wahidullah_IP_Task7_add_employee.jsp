<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Query Result</title>
</head>
    <body>
    <%@page import="Individual_Project.DataHandler"%>
    <%@page import="java.sql.ResultSet"%>
    <%@page import="java.sql.Array"%>
    <%
    // The handler is the one in charge of establishing the connection.
    DataHandler handler = new DataHandler();

    // Get the attribute values passed from the input form.
    
  

    
    String name = request.getParameter("name");
    String address = request.getParameter("address");
    String salary = request.getParameter("salary");
    String maxNumProduct = request.getParameter("maxNumProduct");
    String productType = request.getParameter("productType");
    String educationRecord = request.getParameter("educationRecord");
    String technicalPosition = request.getParameter("technicalPosition");


    //If the user is filling out the form for Worker.
    if (maxNumProduct != null) {
    	
        //response.sendRedirect("add_employee_form.jsp");
     
        // Now perform the query with the data from the form.
        boolean success = handler.addWorker(name, address, salary, maxNumProduct);
        if (!success) { // Something went wrong
            %>
                <h2>There was a problem inserting the course</h2>
            <%
        } else { // Confirm success to the user
            %>
            <h2>The New Employee:</h2>

            <ul>
                <li>Name: <%=name%></li>
                <li>Address: <%=address%></li>
                <li>Salary: <%=salary%></li>                
                <li>Maximum Number of Products per Day:<%=maxNumProduct%></li>
            </ul>

            <h2>Was successfully inserted.</h2>
      
            <%
        }
    }
    
  //If the user is filling out the form for Quality Controller.
    else if (productType != null) {
   
        // Now perform the query with the data from the form.
        boolean success = handler.addQualityController(name, address, salary, productType);
        if (!success) { // Something went wrong
            %>
                <h2>There was a problem inserting the course</h2>
            <%
        } else { // Confirm success to the user
            %>
            <h2>The New Employee:</h2>

            <ul>
                <li>Name: <%=name%></li>
                <li>Address: <%=address%></li>
                <li>Salary: <%=salary%></li>                
                <li>Product Type:<%=productType%></li>
            </ul>

            <h2>Was successfully inserted.</h2>     
            <%
        }
    }
	
  //If the user is filling out the form for Technical Staff.
    else if (educationRecord != null) {
		   
        // Now perform the query with the data from the form.
        boolean success = handler.addTechnicalStaff(name, address, salary, educationRecord, technicalPosition);
        if (!success) { // Something went wrong
            %>
                <h2>There was a problem inserting the course</h2>
            <%
        } else { // Confirm success to the user
            %>
            <h2>The New Employee:</h2>

            <ul>
                <li>Name: <%=name%></li>
                <li>Address: <%=address%></li>
                <li>Salary: <%=salary%></li>                
                <li>Education Record:<%=productType%></li>
                <li>Technical Position:<%=productType%></li>
            </ul>

            <h2>Was successfully inserted.</h2>
      
            <%
        }
    }
    %>
    </body>
</html>
