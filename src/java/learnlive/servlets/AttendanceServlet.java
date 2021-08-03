
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
import learnlive.db.LoginHistoryDB;
import learnlive.db.StudentDB;
import learnlive.entities.Attendance;
import learnlive.entities.LoginHistory;
import learnlive.entities.SchoolClass;
import learnlive.entities.Student;


@WebServlet(name = "AttendanceServlet", urlPatterns = {
    "/attendance/add",
    "/attendance/cancel",
    "/attendance/lecturer/add",
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
                addByStudent();
                break;
            
            case "/attendance/cancel" :
                cancel();
                break;
                
            case "/attendance/lecturer/add" :
                addByLecturer();
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
    
    
    private void addByStudent() throws IOException {
        add(getAuthStudent());
    }
    
    private void addByLecturer() throws IOException {
        
        String number = request.getParameter("matriculation_number");
        
        if (number == null || number.isEmpty()) {
            formData.setFormError("Matriculation number is invalid");
            redirectBackWithPostDataErrors();
        } else {
            
            try {
                
                Student student = StudentDB.findByMatriculationNumber(number);
                
                if (student == null) {
                    formData.setFormError("Matriculation number is invalid");
                    redirectBackWithPostDataErrors();
                    return;
                }
                
                add(student);
                
            } catch (SQLException ex) {
                redirectBackWithPostServerError();
            }
        }
    }
    
    
    private void add(Student student) throws IOException {
        
        //check if class has ended
        
        int theNumber;
        
        String number = request.getParameter("number");
        
        SchoolClass sch = getSchoolClass();
        
        try {
            
            theNumber = Integer.parseInt(number);
            
            if (theNumber < 1) 
                throw new NumberFormatException();
            
            if (!sch.isTakeAttendance()) {
                formData.setFormError("Attendance can't be marked yet");
                redirectBackWithPostDataErrors();
                return;
            }
            
            if (theNumber > sch.getCapacity()) {
                formData.setFormError("Number can't be greater than attendance limit");
                redirectBackWithPostDataErrors();
                return;
            }
            
            long aid = AttendanceDB.findIdIfNumberExists(theNumber, sch.getId());
            
            if (aid != 0) {
                formData.setFormError("Number already exists");
                redirectBackWithPostDataErrors();
                return;
            }
            
            long aid2 = AttendanceDB.findIdIfStudentExists(student.getId(), sch.getId());
            
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
        a.setStudent(student);
        
        try {
            AttendanceDB.insert(a);
        } catch (SQLException ex) {
            redirectBackWithPostServerError();
            return;
        }
        
        redirectBack();
    }

    private void cancel() throws IOException {
        
        long theNumber;
        
        String id = request.getParameter("id");
        
        try {
            theNumber = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            formData.setFormError("id is invalid");
            redirectBackWithPostDataErrors();
            return;
        }
        
        try {
            AttendanceDB.delete(theNumber);
            redirectBack();
        } catch (SQLException ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    
    
    
}





