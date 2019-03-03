

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SlotServlet
 */
@WebServlet("/SlotServlet")
public class SlotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SlotServlet() {
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
		String name = request.getParameter("name");
		String regno = request.getParameter("regno");
		String type = request.getParameter("type");
		Vehicle v = null;
		if(type.equals("twowheeler")){
			v = new TwoWheeler( name, regno, type);
		}
		if(type.equals("minifour")){
			v = new FourMini( name, regno, type);
		}
		if(type.equals("maxfour")){
			v = new MaxFour( name, regno, type);
		}
		getServletContext().setAttribute("v", v);
		//----------------------TODO---------------- put p and pl in sevlet context 
		Properties p = (Properties)getServletContext().getAttribute("p");
		ParkingLot pl = (ParkingLot)getServletContext().getAttribute("pl");
		v.display(p);
		
		//System.out.println(v.getType());
		/* ---------------Finding empty slot ----------------------- */
		Slot slot = AllocateController.getEmptySlot( pl, v, p);
		if(slot == null){
			System.out.println("\nParking Slot not available!!!");
			return;
		}
		System.out.println("Slot allocated: " + slot);
		getServletContext().setAttribute("slot", slot);
		request.getRequestDispatcher("service.jsp").forward(request, response);

	}

}
