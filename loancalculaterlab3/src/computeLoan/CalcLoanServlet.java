/**	Lab 3	*/

/**	DATE:-	04/10/2015	*/

/**	Goutham Goud Kata #700632234	*/

/**	Naveen Yadavalli  #700632470	*/

/**	Ravi Teja Tatineni #700631439	*/

package computeLoan;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CalcLoanServlet
 */
public class CalcLoanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String loanamount = request.getParameter("loanAmount");
        String interest = request.getParameter("annualIntrest");
        String years = request.getParameter("numberOfYears");

        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Loan Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            int year = Integer.valueOf(years);
            double Loan = Double.valueOf(loanamount);
            double interest1 = Double.valueOf(interest);

            out.println("<div style=\"padding-top: 50px; padding-left: 200px; padding-right: 100px; width: 800px\"> ");
            out.println(" <fieldset> <legend> Computing Loan using Servlet</legend> <table>  ");
           
            out.println("    <tr> <td > Program </td> <td> Compute loan using Servlet </td> ");
            out.println("   </tr>    <tr></tr>  <tr>");
            Loan hum = new Loan(interest1, year, Loan);
            out.println("<h3>Calculated Loan values </h3>");

            out.println("<td> LoanAmount </td>" + "<td> " + hum.getLoanAmount() + " </td> </tr>");
            out.println("<tr><td>  interest</td>" + "<td> " + hum.getAnnualInterestRate() + " </td> </tr>");
            out.println("<tr><td>  years </td>" + "<td> " + hum.getNumberOfYears() + "</td> </tr>");
            out.println("<tr><td> monthly payment </td>" + "<td> " + hum.getMonthlyPayment() + "</td> </tr>");
            out.println("<tr><td> total </td>" + "<td> " + "" + hum.getTotalPayment() + "</td> </tr> </table>");
            out.println(" </div>       </fieldset>          </div> ");
            out.println("</body>");
            out.println("</html>");

        } finally {
            out.close();
        }
    }
    public CalcLoanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
