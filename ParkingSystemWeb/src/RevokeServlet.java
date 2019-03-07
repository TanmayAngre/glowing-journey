

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RevokeServlet
 */
@WebServlet("/RevokeServlet")
public class RevokeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RevokeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int flag = 0;
		try{
			JDBCCon jdbc = JDBCCon.getInstanceJDBC();
			Ticket ticket = jdbc.retrieveTicket(request.getParameter("ticketno"));
			Slot sslot = jdbc.retrieveSlot(request.getParameter("ticketno"));       
			
			/* ----------------------Generate new Cost ---------------------- */
			double newCost = CostGenerator.generateCost(ticket, sslot);
			System.out.println("New Cost is:"+newCost);
			jdbc.insertTicketHistory(ticket);
			TicketHistoryFileWriter.writeFile(ticket, "C:\\Users\\TANMAY\\Desktop\\ParkingSystemWeb\\src\\tickethistory.csv");
			/* -----------------Revoking the filled slot----------- */
			Slot filled = RevokeController.revokeFilledSlot((ParkingLot)getServletContext().getAttribute("pl"), sslot);
			System.out.println("DEBUG:" + filled.isAvailable());
			jdbc.updateSlot(sslot);
			/* Delete ticket entry after exit... Ticket Entry persists in TicketHistory */
			jdbc.deleteTicket(ticket);
		}
		catch(Exception e){
			System.out.println(e);
			flag = 1;
		}
		//response.setContentType("text/html");
		//PrintWriter out = response.getWriter();
		//out.print("<h3>Vehicle removed!!!</h3>");
		if(flag == 0)
			getServletContext().setAttribute("message", "Vehicle removed successfully!!");
		else
			getServletContext().setAttribute("message", "Ticket number entered is invalid!");
		request.getRequestDispatcher("index.jsp").forward(request, response);
		//response.sendRedirect("index.jsp");
	}

}
