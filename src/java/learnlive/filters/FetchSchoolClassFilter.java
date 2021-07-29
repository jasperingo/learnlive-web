
package learnlive.filters;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import learnlive.db.SchoolClassDB;
import learnlive.entities.SchoolClass;


@WebFilter(filterName = "FetchSchoolClassFilter", urlPatterns = {
    "/class",
    "/class-settings",
    "/class-attendance",
    "/class/update-capacity",
    "/class/update-take-attendance",
    "/class/update-end-at",
    "/attendance/add"
})
public class FetchSchoolClassFilter implements Filter {
    
    
    public FetchSchoolClassFilter() {
    }    
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        try {
            
            String code = request.getParameter("code");
            SchoolClass sch = SchoolClassDB.findBycode(code);
            
            if (sch== null) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            
            request.setAttribute("class", sch);
            
            chain.doFilter(request, response);
            
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

}




