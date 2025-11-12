package snacknroll.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req  = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    String ctx  = req.getContextPath();                    
    String path = req.getRequestURI().substring(ctx.length()); 

    boolean isStatic =
        path.startsWith("/styles/") ||
        path.startsWith("/images/") ||
        path.startsWith("/scripts/") ||
        path.equals("/favicon.ico");

    boolean isPublic =
        path.equals("/login") ||
        path.equals("/register") ||
        path.equals("/") ||
        path.equals("/catalogo") || path.startsWith("/catalogo/");

    if (isStatic || isPublic) {
      chain.doFilter(request, response);
      return;
    }

    boolean needsAuth =
        path.startsWith("/account") ||
        path.startsWith("/checkout") ||
        path.startsWith("/admin");

    if (needsAuth) {
      HttpSession session = req.getSession(false);
      boolean logged = session != null && session.getAttribute("userId") != null;

      if (!logged) {
        String uri = path;
        String qs = req.getQueryString();
        if (qs != null) uri += "?" + qs;
        req.getSession(true).setAttribute("postLoginRedirect", uri);
        resp.sendRedirect(ctx + "/login");
        return;
      }
    }
    chain.doFilter(request, response);
  }
}