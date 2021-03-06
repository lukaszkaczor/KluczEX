package kluczex;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DeleteServlet", urlPatterns = {"/DeleteServlet"})
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String user = null;
        Boolean isLoggedIn = false;
        user = (String)session.getAttribute("user");
        
        if(user!=null)
        {
            isLoggedIn = true;
        }
        /*usuwanie z koszyka wybranego produktu*/

        String productID = request.getParameter("productID");
        try {
            DBConnection dbc = new DBConnection();
            dbc.ExecuteUpdate("delete from koszyk where login = '"+user+"' and id_produktu ="+productID);
            response.sendRedirect("HTML/cart.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
