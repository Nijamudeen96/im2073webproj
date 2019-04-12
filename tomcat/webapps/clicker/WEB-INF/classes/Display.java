// Saved as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class Display extends HttpServlet {  // JDK 6 and above only
 
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
 
         // Print an HTML page as output of query
         //String[] questionNo = {"8"};
         String[] questionNo = request.getParameterValues("questionNo");
        //  String[] name = request.getParameterValues("name");
        //  String[] email = request.getParameterValues("email");
          if(questionNo != null){
            String sqlStr;
            String [] choices = {"a", "b", "c", "d"};
            int count;
            ResultSet rset;
            out.println("<html><head><title>Display</title><link rel='stylesheet' href='style.css'><link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'></head><body class='default'><h1 class='title-head'>MENTI<span class='subscript'>v2</span></h1>");
            out.println("<canvas id='myChart' width='80' height='30'><p>fallback</p></canvas><script src='https://cdn.jsdelivr.net/npm/chart.js@2.8.0'></script><script>");
            out.println("var ctx = document.getElementById('myChart').getContext('2d');");
            out.println("var chart = new Chart(ctx, {type: 'bar',data: {labels: ['A', 'B', 'C', 'D'],datasets: [{");
            

            for(int i = 0; i<questionNo.length; i++){
                out.println("label: 'Question "+questionNo[i]+"',");
                out.println("backgroundColor: 'rgb(21, 101, 192)',borderColor: 'rgb(21, 101, 192)',");
                out.println("data: [");
                for(int j=0; j<choices.length; j++){
                    //out.println("<p>SELECT questionNo, count(*) FROM responses WHERE questionNo= '" + questionNo[i] + "' AND choice= '"+ choices[j] +"'</p>");
                    sqlStr =  "SELECT questionNo, count(*) FROM responses WHERE questionNo= '" + questionNo[i] + "' AND choice= '"+ choices[j] +"'";
                    rset = stmt.executeQuery(sqlStr);
                    if(j<choices.length-1){
                        while(rset.next()){
                              out.println(rset.getInt("count(*)")+", ");
                        }
                    }else{
                        while(rset.next()){
                           out.println(rset.getInt("count(*)"));
                        }
                    }
            }
            
            out.println("]}],borderWidth: 1},");
            out.println("options: {legend:{display: false,},label: {display: false,},scales: {yAxes: [{ticks: {beginAtZero: true},gridLines: {display: false,},}],xAxes: [{gridLines: {display: false,}}],}}});</script></body></html>");

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