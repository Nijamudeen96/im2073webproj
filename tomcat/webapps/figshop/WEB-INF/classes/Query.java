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
               + " AND artist like '%"+ request.getParameter("artist")+"%' and"
               + " scale ="+request.getParameter("scale")+" and quantity > 0 ORDER BY name ASC";
 
         // Print an HTML page as output of query
         ResultSet rset = stmt.executeQuery(sqlStr); // Send the query to the server

         // Step 4: Process the query result

         out.println("<html><head>");
         out.println("<title>The Figurines Store</title>");
         out.println("<link href= 'https://fonts.googleapis.com/css?family=Anton|Montserrat ' rel= 'stylesheet '>");
         out.println("<link rel='stylesheet' href='style.css'></head>");

         out.println("<body class='product-page-main'><h1 class='head-text'><a href='shop.html'>The Figurines</a></h1>");
            
         out.println("<div class='search-bar'>");
         out.println("<form class='searchbar' method='get' action='query'>");
         out.println("<input class='name-search' type='text' name='name'/>");
         out.println("<select class='artist-search' name='artist' size ='1'>");
         out.println("<option value=' '>All Artist</option>");
         out.println("<option value='Jae Sung Eom'>Jae Sung Eom</option>");
         out.println("<option value='JC Hong'>JC Hong</option>");
         out.println("<option value='Lok Ho'>Lok Ho</option>");
         out.println("<option value='Hernan Azcarate'>Hernan Azcarate  </option>");
         out.println("<option value='Victor Hugo Sousa'>Victor Hugo Sousa</option></select>");

         out.println("<select class='scale-search' name='scale' size ='1'>");
         out.println("<option value='6'>1/6</option>");
         out.println("<option value='4'>1/4</option></select>");

         out.println("<input class='submit-search' type='submit' value='SEARCH' /></form></div >");

         out.println("<div class='row'>");
         while(rset.next()){           
            out.println("<div class='column'>");
            out.println("<img class ='std-image' src='images/"+rset.getInt("image")+".jpg' alt=''>");
            out.println("<div class='product-brief'>");
            out.println("<p class='product-brief-name'>"+rset.getString("name")+"</p>");
            out.println("<p class='product-brief-price'>S$"+rset.getInt("price")+".00</p></div>");
            out.println("<div class='more-info'>");
            out.println("<form method='get' action='product'>");
            out.println("<button class='more-info-btn' name='code' type='submit' value='"+rset.getInt("code")+"'>More Info</button>");
            out.println("</form></div><br></div>");
         }
         out.println("</div></body></html>");


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