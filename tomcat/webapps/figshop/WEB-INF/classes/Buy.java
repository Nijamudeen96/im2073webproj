// Saved as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class Buy extends HttpServlet {  // JDK 6 and above only
 
   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
                     throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
 
      Connection conn = null;
      Statement stmt = null;
      try {
         // Step 1: Create a database "Connection" object
         // For MySQL
         Class.forName("com.mysql.jdbc.Driver");  // Needed for JDK9/Tomcat9
         conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/figshop?useSSL=false&serverTimezone=UTC", "myuser", "xxxx");  // <<== Check
 
         // Step 2: Create a "Statement" object inside the "Connection"
         stmt = conn.createStatement();
 
         // Step 3: Execute a SQL SELECT query
         String[] code = request.getParameterValues("code");
         String[] name = request.getParameterValues("name");
         String[] email = request.getParameterValues("email");
 
        if(code != null){
            String sqlStr;
            int count;
            int i = 0;

            sqlStr = "update figures set quantity = quantity - 1 where code =" + code[i];
            count = stmt.executeUpdate(sqlStr);

            sqlStr = "insert into order_records values("+code[i]+", 1, ''"+name[i]+"'',''"+email[i]+"'')";
            count = stmt.executeUpdate(sqlStr);
        }
    

         // Print an HTML page as output of query
         //ResultSet rset = stmt.executeQuery(    sqlStr); // Send the query to the server

         // Step 4: Process the query result

         out.println("<html><head><title>The Figurines Store</title><link rel='stylesheet' href='style.css'></head>");
         out.println("<body class='product-page-main'>");
         out.println("<h1 class='head-text'>The Fingurines</h1>");
         out.println("<p class ='success-page'>YOUR ORDER HAS BEEN PLACED SUCCESSFULLY</p>");
         out.println("<button class='front-page-btn'><a href='shop.html'>Continue Shopping</a></button>");
         out.println("</body></html>");


      } catch (SQLException ex) {
         ex.printStackTrace();
     } catch (ClassNotFoundException ex) {
        ex.printStackTrace();
     } finally {
         out.close();
         try {
            // Step 5: Close the Statement and Connection
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
         } catch (SQLException ex) {
            ex.printStackTrace();
         }
      }
   }
}