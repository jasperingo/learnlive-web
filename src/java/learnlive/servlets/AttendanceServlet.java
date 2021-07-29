
package learnlive.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import learnlive.db.AttendanceDB;
import learnlive.db.SchoolClassDB;
import learnlive.entities.Attendance;
import learnlive.entities.SchoolClass;


@WebServlet(name = "AttendanceServlet", urlPatterns = {
    "/attendance/add"
})
public class AttendanceServlet extends LiveServlet {
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        super.setRequest(request, response);
        
        switch (request.getServletPath()) {
            
            case "/attendance" :
                forwardTo("/class-create.jsp");
                break;
                
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet SchoolClassServlet at " + request.getContextPath() + "</h1>");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        super.setRequest(request, response);
        
        switch (request.getServletPath()){
            
            case "/attendance/add" :
                add();
                break;
            
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet LecturerServlet at " + request.getContextPath() + " 4 POST</h1>");
        }
    }
    
    private SchoolClass getSchoolClass() {
        return (SchoolClass) request.getAttribute("class");
    }
    
    private void add() throws IOException {
        
        int theNumber;
        
        String number = request.getParameter("number");
        
        SchoolClass sch = getSchoolClass();
        
        try {
            
            theNumber = Integer.parseInt(number);
            
            if (theNumber < 1) 
                throw new NumberFormatException();
            
            long aid = AttendanceDB.findIdIfNumberExists(theNumber, sch.getId());
            
            if (aid != 0) {
                formData.setFormError("Number already exists");
                redirectBackWithPostDataErrors();
                return;
            }
            
            long aid2 = AttendanceDB.findIdIfStudentExists(getAuthStudent().getId(), sch.getId());
            
            if (aid2 != 0) {
                formData.setFormError("Student already marked attendance");
                redirectBackWithPostDataErrors();
                return;
            }
            
        } catch (NumberFormatException ex) {
            formData.setFormError("Number is invalid");
            redirectBackWithPostDataErrors();
            return;
        } catch (SQLException ex) {
            redirectBackWithPostServerError();
            return;
        }
        
        
        Attendance a = new Attendance();
        a.setNumber(theNumber);
        a.setSchoolClass(sch);
        a.setStudent(getAuthStudent());
        
        try {
            AttendanceDB.insert(a);
        } catch (SQLException ex) {
            redirectBackWithPostServerError();
            return;
        }
        
        redirectBack();
    }
    
    
    
}





