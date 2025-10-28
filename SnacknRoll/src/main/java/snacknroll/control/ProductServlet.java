package snacknroll.control;

import snacknroll.dao.ProductDAO;
import snacknroll.dao.JdbcProductDAO;
import snacknroll.model.Product;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@WebServlet("/catalogo")
public class ProductServlet extends HttpServlet {
  private final ProductDAO productDAO = new JdbcProductDAO();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String q = req.getParameter("q");
    String cat = req.getParameter("cat");

    List<Product> prodotti;
    if (q != null && !q.isBlank()) {
      prodotti = productDAO.searchByQuery(q);
    } else if (cat != null && !cat.isBlank() && !"tutte".equalsIgnoreCase(cat)) {
      prodotti = productDAO.findByCategory(cat);
    } else {
      prodotti = productDAO.findAll();
    }

    req.setAttribute("prodotti", prodotti);
    req.getRequestDispatcher("/WEB-INF/views/catalogo.jsp").forward(req, resp);
  }
}
