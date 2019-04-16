// Saved as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class Create extends HttpServlet {  // JDK 6 and above only
 
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
 
         // // Step 3: Execute a SQL SELECT query
         // String sqlStr = "SELECT * FROM figures WHERE question = "
         //       + request.getParameter("question")
         //       + " AND quantity > 0 ORDER BY name ASC";
 
         // Print an HTML page as output of query
         String[] questionNo = request.getParameterValues("questionNo");
         String[] question = request.getParameterValues("question");
         String[] optA = request.getParameterValues("optA");
         String[] optB = request.getParameterValues("optB");
         String[] optC = request.getParameterValues("optC");
         String[] optD = request.getParameterValues("optD");

         if(question != null){
            String sqlStr;
            int count;

            for(int i = 0; i<question.length; ++i){
               sqlStr = "INSERT INTO qbank (questionNo, question, optA, optB, optC, optD, isOpen) VALUES ("+questionNo[i]+",'"+
               question[i]+"', '"+optA[i]+"', '"+optB[i]+"', '"+optC[i]+"', '"+optD[i]+"')";
               count = stmt.executeUpdate(sqlStr);

               out.println("<html><head>");
               out.println("<title>Question Added</title>");
               out.println("<link href= 'https://fonts.googleapis.com/css?family=Anton|Montserrat ' rel= 'stylesheet '>");
               out.println("<link rel='stylesheet' href='style.css'></head><body class='bg'>");
               out.println("<h1 class='title-head'><a class='a-tag' href='index.html'>MENTI<span class='subscript'>v2</span></a></h1>");
               out.println("<form action='index.html'><input class='btn btn-small' type='submit' value='HOME' /></form>");
               out.println("<form action='create-question.html'><input class='btn btn-small' type='submit' value='NEW QUESTION' /></form>");
               out.println("</body></html>");  
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