package com.mclientui.microserviceclientui.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class RestrictionAdminFilterConfig implements Filter {
    public static final String LOGIN_PAGE = "/Utilisateurs/connexion";

    public void init( FilterConfig config ) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain ) throws IOException, ServletException {
        /* Cast of objects request & response */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        /* Non-filtering of static resources */
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (!path.contains("/admin")) {
            /* Display requested page */
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = request.getSession();

        // if loggedInUserEmail doesn't exist in session, then user isn't connected
        if (session.getAttribute("loggedInUserEmail") == null) {
            /* Redirect to login page */
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
        } else {
            // if loggedInUserRole isn't admin, then user isn't permitted to access
            if (!session.getAttribute("loggedInUserRole").equals("ADMIN")) {
                /* Redirect to login page */
                System.out.println("User trying to access admin datas is not an admin");
                System.out.println("User is: [id:"
                        +session.getAttribute("loggedInUserId")+ ", email:"
                        +session.getAttribute("loggedInUserEmail")+", role:"
                        +session.getAttribute("loggedInUserRole")+"]");
                response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            } else {
                /* Display requested page */
                chain.doFilter(request, response);
            }
        }
    }

        public void destroy() {
        }
    }
