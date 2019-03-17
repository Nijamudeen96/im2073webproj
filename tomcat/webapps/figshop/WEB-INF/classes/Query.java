// Saved as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class Query extends HttpServlet {  // JDK 6 and above only
 
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
         String sqlStr = "SELECT * FROM figures WHERE name like "
               + "'%" + request.getParameter("name") + "%'"
               + " AND quantity > 0 ORDER BY name ASC";
 
         // Print an HTML page as output of query
         ResultSet rset = stmt.executeQuery(sqlStr); // Send the query to the server

         // // Step 4: Process the query result
         out.println("<html>");
         out.println("<head>");
         out.println("<title>The Figurines Store</title>");
         out.println("<link rel='stylesheet' href='style.css'/>");   
         out.println("</head>");
         out.println("<body class='product-page-main'>");

         out.println("<h1 class='head-text'>The Figurines</h1>");

         out.println("<form class='searchbar' method='get' action='query'><input type='text' name='name'/>");
         out.println("<select name='artist' size ='1'>");
         out.println("</select>");
         out.println("<input type='submit' value='Search' /></form>");

         out.println("<li>");
         int count = 0;
         while(rset.next()){
         out.println("<ul><a href='productpage.html'><img src='images/2.png' alt='#'></a><p>");
         out.println("Name:" + rset.getString("name") + "</p>");
         out.println("</ul>");
         }
         
         
         
         out.println("</li>");

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