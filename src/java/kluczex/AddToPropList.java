package kluczex;
 /*servlet obslugujacy dodawanie przedmiotow do listy proponowanych*/
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author racecar
 */
@WebServlet(name = "AddToPropList", urlPatterns = {"/AddToPropList"})
public class AddToPropList extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*jesli id_produktu nie istanieje albo jest juz w proponowanych, program zwraca blad, w innym wypadku dodaje produkt do proponowanych*/
        try {
            String productID = request.getParameter("productID");
            DBConnection dbc = new DBConnection();
            ResultSet result = dbc.ExecuteQuery("select * from produkty where id_produktu = " + productID);
            ResultSet polecaneResult = dbc.ExecuteQuery("select * from polecane where id_produktu =" + productID);
            if (!result.next()) {
                request.setAttribute("errorMessage", "W bazie nie ma produktu z tym ID");
                RequestDispatcher rd = request.getRequestDispatcher("HTML/admin.jsp");
                rd.forward(request, response);
            } else if (polecaneResult.next()) {
                request.setAttribute("errorMessage", "Produkt z tym ID jest już w polecanych");
                RequestDispatcher rd = request.getRequestDispatcher("HTML/admin.jsp");
                rd.forward(request, response);
            } else {
                dbc.ExecuteUpdate("insert into polecane values(" + productID + ")");
                request.setAttribute("errorMessage", "Produkt został dodany");
                RequestDispatcher rd = request.getRequestDispatcher("HTML/admin.jsp");
                rd.forward(request, response);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AddToPropList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddToPropList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
