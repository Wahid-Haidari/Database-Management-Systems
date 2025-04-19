<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
        <title>MyProducts</title>
    </head>
    <body>
        <%@page import="Individual_Project.DataHandler"%>
        <%@page import="java.sql.ResultSet"%>
        <%
            // We instantiate the data handler here, and get the employees from the database
            final DataHandler handler = new DataHandler();
            final ResultSet employees = handler.getEmployees();
        %>
        <!-- The table for displaying all the employees who has more than a particular salary -->
        <table cellspacing="2" cellpadding="2" border="1">
            <tr> <!-- The table headers row -->
              <td align="center">
                <h4>Name</h4>
              </td>
              <td align="center">
                <h4>Address</h4>
              </td>
              <td align="center">
                <h4>Salary</h4>
              </td>         
            </tr>
            <%
               while(employees.next()) { // For each employee record returned...
                   // Extract the attribute values for every row returned
                   final String name = employees.getString("employee_name");
                   final String address = employees.getString("address");
                   final String salary = employees.getString("salary");
                 
                   
                   out.println("<tr>"); // Start printing out the new table row
                   out.println( // Print each attribute value
                        "<td align=\"center\">" + name +
                        "</td><td align=\"center\"> " + address +                                    
                        "</td><td align=\"center\"> " + salary + "</td>");
                   out.println("</tr>");
               }
               %>
          </table>
    </body>
</html>
