
package learnlive.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import learnlive.entities.SchoolClass;
import learnlive.entities.Student;
import learnlive.entities.User;



@WebFilter(filterName = "CanUpdateSchoolClassFilter", urlPatterns = {
    "/class-settings",
    "/class/update-capacity",
    "/class/update-take-attendance",
    "/class/update-end-at",
    "/attendance/cancel",
    "/attendance/lecturer/add",
})
public class CanUpdateSchoolClassFilter  implements Filter {
    
    public CanUpdateSchoolClassFilter() {
    }    
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        
        HttpSession session = httpReq.getSession();
        
        User user = (User)session.getAttribute("auth_user");
        
        if (user instanceof Student) {
            httpResp.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            
            SchoolClass sch = (SchoolClass)request.getAttribute("class");
            
            if (user == null || sch.getLecturer().getId() != user.getId()) {
                httpResp.sendError(HttpServletResponse.SC_FORBIDDEN);
            } else {
                chain.doFilter(request, response);
            }
           
        }
        
        
    }
    
}





