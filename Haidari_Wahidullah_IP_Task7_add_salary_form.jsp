<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Enter the salary</title>
    </head>
    <body>
        <h2>Retrieve all employees whose salary is above a particular salary</h2>
        <!--
            Form for collecting the salary of the employees.
            Upon form submission, add_salary.jsp file will be invoked.
        -->
        <form action="add_salary.jsp">
            <!-- The form organized in an HTML table for better clarity. -->
            <table border=1>
                <tr>
                    <th colspan="2">Enter the salary:</th>
                </tr>
                <tr>
                    <td>Salary</td>
                    <td><div style="text-align: center;">
                    <input type=text name=salary>
                    </div></td>
                </tr>
                
                <tr>
                    <td><div style="text-align: center;">
                    <input type=reset value=Clear>
                    </div></td>
                    <td><div style="text-align: center;">
                    <input type=submit value=Insert>
                    </div></td>
                </tr>
            </table>
        </form>
    </body>
</html>
