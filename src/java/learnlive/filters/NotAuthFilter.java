
package learnlive.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import learnlive.entities.User;


@WebFilter(filterName = "NotAuthFilter", urlPatterns = {
    "/class/create", 
    "/lecturer/dashboard", 
    "/student/dashboard", 
    "/lecturer/profile",
    "/student/profile",
    "/class",
    "/class-settings",
    "/class-attendance",
    "/class-assignments",
    "/class/update-capacity",
    "/class/update-take-attendance",
    "/class/update-end-at",
    "/attendance/add",
    "/attendance/cancel",
    "/attendance/lecturer/add",
    "/assignment/u",
    "/assignment/d",
    "/submission/d",
    "/submission/u"
})
public class NotAuthFilter implements Filter {
    
    public NotAuthFilter() {
    }    
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        
        HttpSession session = httpReq.getSession();
        
        User user = (User) session.getAttribute("auth_user");
        
        if (user == null) {
            httpResp.sendRedirect(httpReq.getContextPath());
        } else {
            chain.doFilter(request, response);
        }
    }
     
    @Override
    public void destroy() {
         
    }   
    
}


