// Saved as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class Question extends HttpServlet {  // JDK 6 and above only
 
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
            "jdbc:mysql://localhost:3306/clicker?useSSL=false&serverTimezone=UTC", "myuser", "xxxx");  // <<== Check
 
         // Step 2: Create a "Statement" object inside the "Connection"
         stmt = conn.createStatement();
         
         // Step 3: Execute a SQL SELECT query
         String sqlStr = "Select * from qbank where questionNo =" + request.getParameter("questionNo");

         // Print an HTML page as output of query
         ResultSet rset = stmt.executeQuery(sqlStr); // Send the query to the server

         while(rset.next()){
         out.println("<html><head><title>Document</title><link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'><link rel='stylesheet' href='style.css'></head><body class='bg'><h1 class='title-head'><a href='index.html'>MENTI<span class='subscript'>v2</span></a></h1>");
         out.println("<p class ='op'>"+rset.getString("question")+"</p>");
         //out.println("<p class ='op'>I made it</p>");
         out.println("<div class = 'questions'>");
         out.println("<p class ='op1'>A: 20</p><p class ='op1'>B: 40</p><p class ='op1'>C: 60</p><p class ='op1'>D: 80</p>");
         out.println("</div><form action='display' method='get'><div class='btn-contain'><button class = 'btn' name='questionNo' type='submit' value='"+rset.getInt("questionNo")+"'>Results</button></div></form></body></html>");
         }
 
        //  // Step 3: Execute a SQL SELECT query
        //  String sqlStr = "SELECT * FROM figures WHERE code = "
        //        + request.getParameter("code")
        //        + " AND quantity > 0 ORDER BY name ASC";
 
        //  // Print an HTML page as output of query
        //  ResultSet rset = stmt.executeQuery(sqlStr); // Send the query to the server

        //  // Step 4: Process the query result
        //  System.out.println(sqlStr);
        //  out.println("<html><head>");
        //  out.println("<title>Product Details</title>");
        //  out.println("<link href= 'https://fonts.googleapis.com/css?family=Anton|Montserrat ' rel= 'stylesheet '>");
        //  out.println("<link rel='stylesheet' href='style.css'> ");
        //  out.println("</head><body class='product-page-main'>");
        //  out.println("<a href='shop.html'><h1 class='head-text'><a href='shop.html'>The Figurines</a><h1></a>");
        //  while(rset.next()){
        //     out.println("<div class='order-image'>");
        //     out.println("<img class='order-image-pic' src='images/"+rset.getInt("image")+".jpg' alt=''></div>");
        //     out.println("<div class='order-non-image'>");
        //     out.println("<div class='order-details'>");
        //     out.println("<p class='od-name'>"+rset.getString("name")+" (1/"+rset.getInt("scale")+")</p>");
        //     out.println("<p class='od-artist'>By "+rset.getString("artist")+"</p>");
        //     out.println("<p class='od-price'>S$"+rset.getInt("price")+".00</p></div>");
            
        //     out.println("<div class='order-submission'>");
        //     out.println("<form method='get' action='buy'>");
        //     out.println("<input class='os-name'type='text' name='name' placeholder='Enter Name Here'/>");
        //     out.println("<br><input class='os-email'type='text' name='email' placeholder='Enter Email Address'/>");
        //     out.println("<br><button class='os-btn'name='code' type='submit' value='"+rset.getInt("code")+"'>BUY NOW</button></form></div></div></body></html>");
        //  }

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