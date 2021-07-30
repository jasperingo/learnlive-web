
package learnlive.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import learnlive.db.LoginHistoryDB;
import learnlive.db.SchoolClassDB;
import learnlive.db.StudentDB;
import learnlive.entities.LoginHistory;
import learnlive.entities.SchoolClass;
import learnlive.entities.Student;
import learnlive.filters.PaginationFilter;


@WebServlet(name = "StudentServlet", urlPatterns = {
    "/student/register", 
    "/student/dashboard",
    "/student/login",
    "/student/profile",
    "/student/logout"
})
public class StudentServlet extends LiveServlet {
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        super.setRequest(request, response);
        
        switch (request.getServletPath()) {
            
            case "/student/register" :
                forwardTo("/student-register.jsp");
                break;
                
            case "/student/dashboard" :
                forwardToDashboard();
                break;
                
            case "/student/login" :
                forwardTo("/student-login.jsp");
                break;
            
            case "/student/profile" : 
                forwardTo("/student-profile.jsp");
                break;
                
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet LecturerServlet at " + request.getContextPath() + "</h1>");
        }
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        super.setRequest(request, response);
        
        switch (request.getServletPath()){
            case "/student/register":
                register();
                break;
                
            case "/student/login" :
                login();
                break;
            
            case "/student/logout" :
                logout();
                break;
            
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet LecturerServlet at " + request.getContextPath() + " 4 POST</h1>");
        }
    }
    
    private void register() throws IOException {
        
        String matricNumber = request.getParameter("matriculation_number");
        
        String firstName = request.getParameter("first_name");
        
        String lastName = request.getParameter("last_name");
                
        String phoneNumber = request.getParameter("phone_number");
        
        String password = request.getParameter("password");
        
        
        if (firstName == null || firstName.isEmpty()) {
            formData.putError("first_name", "First name is invalid");
        }
        
        if (lastName == null || lastName.isEmpty()) {
            formData.putError("last_name", "Last name is invalid");
        }
        
        if (matricNumber == null || matricNumber.length() != 11) {
            formData.putError("matriculation_number", "Matriculation number is invalid");
        } else {
            
            try {
                
                long matric = StudentDB.findIdByMatriculationNumber(matricNumber);
                
                if (matric > 0) {
                    formData.putError("matriculation_number", "Matriculation number exists");
                }
                
            } catch (SQLException ex) {
                respondWithPostServerError();
                return;
            }
        }
        
        if (phoneNumber == null || phoneNumber.length() != 11) {
            formData.putError("phone_number", "Phone number is invalid");
        } else {
            
            try {
                
                long phone = StudentDB.findIdByPhoneNumber(phoneNumber);
                
                if (phone > 0) {
                    formData.putError("phone_number", "Phone number exists");
                }
                
            } catch (SQLException ex) {
                respondWithPostServerError();
                return;
            }
        }
        
        if (password == null || password.length() < 6) {
            formData.putError("password", "Password must be more than 5 characters");
        }
        
        if (formData.hasErrors()) {
            respondWithPostDataErrors();
            return;
        }
        
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setPhoneNumber(phoneNumber);
        student.setPassword(password);
        student.setMatriculationNumber(matricNumber);
        
        try {
            StudentDB.insert(student);
        } catch (SQLException ex) {
            respondWithPostServerError();
            return;
        }
        
        putSessionData("auth_user", student);
        redirectTo("/student/dashboard");
    }
    
    
    private void login() throws IOException {
        
        String number = request.getParameter("matriculation_number");
        
        String password = request.getParameter("password");
        
        if (number == null || number.isEmpty()) {
            formData.setFormError("Crediantials are incorrect");
        } else if (password == null || password.length() < 6) {
            formData.setFormError("Crediantials are incorrect");
        } else {
            
            try {
                
                Student student = StudentDB.findByMatriculationNumber(number);
                
                if (student  == null) {
                    formData.setFormError("Crediantials are incorrect");
                } else if (student.getPassword() == null || !student.getPassword().equals(password)) {
                    formData.setFormError("Crediantials are incorrect");
                } else {
                    putSessionData("auth_user", student);
                    
                    LoginHistory history = new LoginHistory();
                    history.setStudent(student);
                    
                    try {
                        LoginHistoryDB.insertForStudent(history);
                    } catch (SQLException ex) {

                    }
                    
                    redirectTo("/student/dashboard");
                    return;
                }
                
            } catch (SQLException ex) {
                respondWithPostServerError();
                return;
            }
            
        }
        
        respondWithPostDataErrors();
    }

    private void forwardToDashboard() throws ServletException, IOException {
        
        try {
            List<Integer> pager = (List<Integer>) request.getAttribute("pager");
            
            List<SchoolClass> list = SchoolClassDB.findList(getAuthStudent(), pager.get(1), PaginationFilter.LIMIT);
            
            int listCount = SchoolClassDB.countAll(getAuthStudent());
            
            request.setAttribute("schoolclasses", list);
            
            request.setAttribute("data_count", listCount);
            
            forwardTo("/student-dashboard.jsp");
            
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    
    
}



