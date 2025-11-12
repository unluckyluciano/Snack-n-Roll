package snacknroll.control;

import snacknroll.dao.UtenteDAO;
import snacknroll.dao.JdbcUtenteDAO;
import snacknroll.model.Utente;
import snacknroll.security.PasswordUtils;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private final UtenteDAO utenteDAO = new JdbcUtenteDAO();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String email = req.getParameter("email");
    String password = req.getParameter("password");

    if (email == null || password == null) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Credenziali mancanti");
      return;
    }

    Utente u = utenteDAO.findByEmail(email.toLowerCase());
    if (u == null || !PasswordUtils.verify(password, u.getPasswordHash())) {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Email o password non corretti");
      return;
    }

    HttpSession session = req.getSession(true);
    session.setAttribute("userId", u.getIdUtente());
    session.setAttribute("userEmail", u.getEmail());
    session.setAttribute("userNome", u.getNome());
    session.setAttribute("userRuolo", u.getRuolo());
    
    String target = (String) session.getAttribute("postLoginRedirect");
    if (target != null) {
      session.removeAttribute("postLoginRedirect");
      resp.sendRedirect(target);
    } else {
      resp.sendRedirect(req.getContextPath() + "/");
    }

    session.setMaxInactiveInterval(60 * 60 * 2); 

    resp.sendRedirect(req.getContextPath() + "/");
  }
  
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	  req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
  }
}