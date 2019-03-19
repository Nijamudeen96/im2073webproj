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
 
         // // Step 3: Execute a SQL SELECT query
         // String sqlStr = "SELECT * FROM figures WHERE code = "
         //       + request.getParameter("code")
         //       + " AND quantity > 0 ORDER BY name ASC";
 
         // Print an HTML page as output of query
         String[] code = request.getParameterValues("code");
         String[] name = request.getParameterValues("name");
         String[] email = request.getParameterValues("email");
         if(code != null){
            String sqlStr;
            int count;

            for(int i = 0; i<code.length; ++i){
               sqlStr = "UPDATE figures SET quantity = quantity - 1 WHERE code = " + code[i];
               count = stmt.executeUpdate(sqlStr);

               sqlStr = "INSERT INTO order_records (code, quantity, name, email) VALUES ("
               + code[i] + ", 1, '"+name[i]+"', '"+email[i]+"')";
               count = stmt.executeUpdate(sqlStr);

               out.println("<html><head>");
               out.println("<title>Order Succesful</title>");
               out.println("<link href= 'https://fonts.googleapis.com/css?family=Anton|Montserrat ' rel= 'stylesheet '>");
               out.println("<link rel='stylesheet' href='style.css'></head><body class='confirm-page-main'>");
               out.println("<div class='container'><p class='cfm-text-1'>Your order has been confirmed</p>");
               out.println("<p class='cfm-text-2'>Thank you for shopping with THE FIGURINES</p>");
               out.println("<button class='cfm-page-btn'><a href='shop.html'>CONTINUE SHOPPING</a></button></div></body></html>");
               
            }
            
         }

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