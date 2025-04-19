<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add employee</title>
    </head>
    <body>
        <h2>Use this form for entering a worker.</h2>
        <!--
            Form for collecting user input for the new employee.
            Upon form submission, add_employee.jsp file will be invoked.
        -->

        <form action="add_employee.jsp">
            <!-- The form organized in an HTML table for better clarity. -->
            <table border=1>
                <tr>
                    <th colspan="2">Worker</th>
                </tr>
                <tr>
                    <td>Name</td>
                    <td><div style="text-align: center;">
                    <input type=text name=name>
                    </div></td>
                </tr>
                <tr>
                    <td>Address</td>
                    <td><div style="text-align: center;">
                    <input type=text name=address>
                    </div></td>
                </tr>
                <tr>
                    <td>Salary</td>
                    <td><div style="text-align: center;">
                    <input type=text name=salary>
                    </div></td>
                </tr>
                <tr>
                    <td>Maximum # of product per day</td>
                    <td><div style="text-align: center;">
                    <input type=text name=maxNumProduct>
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
        <h2>Use this form for entering a quality controller.</h2>
        <form action="add_employee.jsp">
            <!-- The form organized in an HTML table for better clarity. -->
            <table border=1>
                <tr>
                    <th colspan="2">Quality Controller</th>
                </tr>
                <tr>
                    <td>Name</td>
                    <td><div style="text-align: center;">
                    <input type=text name=name>
                    </div></td>
                </tr>
                <tr>
                    <td>Address</td>
                    <td><div style="text-align: center;">
                    <input type=text name=address>
                    </div></td>
                </tr>
                <tr>
                    <td>Salary</td>
                    <td><div style="text-align: center;">
                    <input type=text name=salary>
                    </div></td>
                </tr>
                <tr>
                    <td>Product type</td>
                    <td><div style="text-align: center;">
                    <input type=text name=productType>
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
        
        <h2>Use this form for entering a technical staff.</h2>
        
        <form action="add_employee.jsp">
            <!-- The form organized in an HTML table for better clarity. -->
            <table border=1>
                <tr>
                    <th colspan="2">Technical Staff</th>
                </tr>
                 <tr>
                    <td>Name</td>
                    <td><div style="text-align: center;">
                    <input type=text name=name>
                    </div></td>
                </tr>
                <tr>
                    <td>Address</td>
                    <td><div style="text-align: center;">
                    <input type=text name=address>
                    </div></td>
                </tr>
                <tr>
                    <td>Salary</td>
                    <td><div style="text-align: center;">
                    <input type=text name=salary>
                    </div></td>
                </tr>
                <tr>
                    <td>Education Record</td>
                    <td><div style="text-align: center;">
                    <input type=text name=educationRecord>
                    </div></td>
                </tr>
                <tr>
                    <td>Technical Position</td>
                    <td><div style="text-align: center;">
                    <input type=text name=technicalPosition>
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
