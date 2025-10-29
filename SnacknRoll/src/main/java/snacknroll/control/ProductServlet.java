package snacknroll.control;

import snacknroll.dao.ProductDAO;
import snacknroll.dao.JdbcProductDAO;
import snacknroll.model.Product;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Map;   

@WebServlet("/catalogo")
public class ProductServlet extends HttpServlet {
  private final ProductDAO productDAO = new JdbcProductDAO();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String q    = req.getParameter("q");
    String slug = req.getParameter("cat");

    List<Product> prodotti;

    if (q != null && !q.isBlank()) {
      prodotti = productDAO.searchByQuery(q);

    } else {
      Map<String,String> catMap = Map.of(
          "dolci",   "Dolci",
          "salati",  "Salati",
          "bevande", "Bevande",
          "mystery_box", "Mystery Box"
      );

      if (slug != null && !slug.isBlank() && !"tutte".equalsIgnoreCase(slug)) {
        String dbCat = catMap.get(slug.toLowerCase());
        if (dbCat != null) {
          prodotti = productDAO.findByCategory(dbCat);
        } else {
          prodotti = productDAO.findAll();
        }
      } else {
        prodotti = productDAO.findAll();
      }
    }

    req.setAttribute("prodotti", prodotti);
    req.getRequestDispatcher("/WEB-INF/views/catalogo.jsp").forward(req, resp);
  }
}
