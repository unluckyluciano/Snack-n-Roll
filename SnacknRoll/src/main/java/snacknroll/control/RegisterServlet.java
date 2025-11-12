package snacknroll.control;

import snacknroll.dao.UtenteDAO;
import snacknroll.dao.JdbcUtenteDAO;
import snacknroll.model.Utente;
import snacknroll.security.PasswordUtils;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
  private final UtenteDAO utenteDAO = new JdbcUtenteDAO();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String nome = trim(req.getParameter("nome"));
    String cognome = trim(req.getParameter("cognome"));
    String email = trim(req.getParameter("email"));
    String password = req.getParameter("password");
    String indirizzo = trim(req.getParameter("indirizzo_spedizione"));

    if (isBlank(nome) || isBlank(cognome) || isBlank(email) || isBlank(password)) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dati mancanti");
      return;
    }
    if (!email.matches("^[\\w-.]+@[\\w-]+\\.[A-Za-z]{2,}$")) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email non valida");
      return;
    }
    if (password.length() < 8) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Password troppo corta");
      return;
    }
    if (utenteDAO.existsByEmail(email)) {
      resp.sendError(HttpServletResponse.SC_CONFLICT, "Email già registrata");
      return;
    }

    Utente u = new Utente();
    u.setNome(nome);
    u.setCognome(cognome);
    u.setEmail(email.toLowerCase());
    u.setPasswordHash(PasswordUtils.hash(password));
    u.setRuolo("USER");
    u.setIndirizzoSpedizione(indirizzo);

    int id = utenteDAO.create(u);

    HttpSession session = req.getSession(true);
    session.setAttribute("userId", id);
    session.setAttribute("userEmail", u.getEmail());
    session.setAttribute("userNome", u.getNome());
    session.setAttribute("userRuolo", u.getRuolo());

    resp.sendRedirect(req.getContextPath() + "/"); 
  }
  
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	  req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
  }

  private static boolean isBlank(String s) { return s == null || s.isBlank(); }
  private static String trim(String s) { return s == null ? null : s.trim(); }
}