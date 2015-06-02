/**	Lab 3	*/

/**	DATE:-	04/10/2015	*/

/**	Goutham Goud Kata #700632234	*/

/**	Naveen Yadavalli  #700632470	*/

/**	Ravi Teja Tatineni #700631439	*/

package changePassword;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ChangePasswordServlet
 */
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			/*
			 * TODO output your page here. You may use following sample code.
			 */
			

			String uname, pswd, newpswd, confrmpswd;
			try {
				
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");	
				System.out.println("Driver loaded");
				// Establish a connection
				Connection connection = DriverManager
						.getConnection("jdbc:odbc:Lab3");	
				System.out.println("Database connected");
				// Create a statement
				Statement statement = connection.createStatement();
				// Execute a statement
				uname = request.getParameter("UserName");
				pswd = request.getParameter("OldPassword");
				newpswd = request.getParameter("NewPassword");
				confrmpswd = request.getParameter("ConfirmPassword");
				Statement stmt = connection.createStatement();
				ResultSet resultSet = statement
						.executeQuery("select * from Account where userName ='"
								+ uname + "' and Password ='" + pswd + "'");
				String msg;
				if (resultSet.next()) {
					if(newpswd != null && confrmpswd != null && newpswd.equals(confrmpswd)){
					stmt.executeUpdate("update Account set  Password ='"
							+ newpswd + "' where userName ='" + uname
							+ "' and Password ='" + pswd + "' ");
					msg = uname + " has been updated with password =" + newpswd;
					request.setAttribute("message", msg);
					System.out.print("Updated");
					}else if(newpswd != null && confrmpswd != null && !newpswd.equals(confrmpswd)){
						msg = "New Password and Old Password doesn't match";
						request.setAttribute("message", msg);
					}
				} else {
					msg = "Please enter a valid username and password";
				request.setAttribute("message", msg);
					
				}
				String url = "/ChangePwd.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
				dispatcher.forward(request, response);
				//response.sendRedirect("ChangePwd.jsp");
				

			} catch (Exception ex) {
				System.out.println(ex);
			}
			
			
		} finally {
			out.close();
		}
	}

	public ChangePasswordServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
