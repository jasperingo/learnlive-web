
package learnlive.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


@WebFilter(filterName = "PaginationFilter", urlPatterns = {
    "/lecturer/dashboard", 
    "/student/dashboard",
    "/class-attendance",
})
public class PaginationFilter implements Filter {
    
    public static int LIMIT = 2;
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        int page = 1;
        
        String page1 = request.getParameter("page");
        
        if (page1 != null) {
            page1 = page1.replaceAll("[^0-9]", "");
            page = Integer.valueOf(page1);
        }
        
        int start = page <= 1 ? 0 : (page * LIMIT) - LIMIT;
        
        List<Integer> pager = new ArrayList<>();
        pager.add(page);
        pager.add(start);
        
        request.setAttribute("pager", pager);
        
        chain.doFilter(request, response);
        
    }
     
    @Override
    public void destroy() {
         
    }   
}

