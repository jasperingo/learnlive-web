
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
import learnlive.entities.Lecturer;
import learnlive.entities.Student;
import learnlive.entities.User;


@WebFilter(filterName = "IsAuthFilter", urlPatterns = {
    "/lecturer/register", 
    "/lecturer/register/1", 
    "/lecturer/login",
    "/student/register",
    "/student/login"
})
public class IsAuthFilter implements Filter {
    
    public IsAuthFilter() {
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
        
        User user = (User)session.getAttribute("user");
        
        if (user != null && user instanceof Lecturer) {
            
            if (httpReq.getServletPath().equals("/lecturer/register/1")) {
                httpResp.sendRedirect("../dashboard");
            } else {
                httpResp.sendRedirect("dashboard");
            }
            
        } else if (user != null && user instanceof Student) {
            httpResp.sendRedirect("dashboard");
        } else {
            chain.doFilter(request, response);
        }
    }
     
    @Override
    public void destroy() {
         
    }   
    
}




