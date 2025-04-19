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
    String salary = request.getParameter("salary");
   
    /*
     * If the user hasn't filled out the salary. This is very simple checking.
     */
    if (salary.equals("")) {
        response.sendRedirect("add_salary_form.jsp");
    } else {
              
        // Now perform the query with the data from the form.
        boolean success = DataHandler.addSalary(salary);
        if (!success) { // Something went wrong
            %>
                <h2>There was a problem inserting the course</h2>
            <%
        } else { // Confirm success to the user
            %>
           <h2>Request is successfully submitted</h2>
                <li>salary: <%=salary%></li>          

          
            <a href="get_some_employees.jsp">Get the list of employees.</a>
            <%
        }
    }
    %>
    </body>
</html>
