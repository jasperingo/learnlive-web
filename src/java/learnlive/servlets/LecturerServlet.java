
package learnlive.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import learnlive.db.LecturerDB;
import learnlive.db.LoginHistoryDB;
import learnlive.db.SchoolClassDB;
import learnlive.entities.Lecturer;
import learnlive.entities.LoginHistory;
import learnlive.entities.SchoolClass;
import learnlive.filters.PaginationFilter;


@WebServlet(name = "LecturerServlet", urlPatterns = {
    "/lecturer/register", 
    "/lecturer/register/1", 
    "/lecturer/dashboard",
    "/lecturer/login"
})
public class LecturerServlet extends LiveServlet {
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        super.setRequest(request, response);
        
        switch (request.getServletPath()) {
            
            case "/lecturer/register" :
                forwardTo("/lecturer-register.jsp");
                break;
                
            case "/lecturer/register/1" :
                forwardToRegisterStageTwo();
                break;
                
            case "/lecturer/dashboard" :
                forwardToDashboard();
                break;
                
            case "/lecturer/login" :
                forwardTo("/lecturer-login.jsp");
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
            case "/lecturer/register":
                register();
                break;
                
            case "/lecturer/register/1" :
                registerStageTwo();
                break;
                
            case "/lecturer/login" :
                login();
                break;
            
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet LecturerServlet at " + request.getContextPath() + " 4 POST</h1>");
        }
    }
    
    private void register() throws IOException {
       
        String number = request.getParameter("personnel_number");
        
        if (number == null || number.isEmpty()) {
            formData.putError("personnel_number", "Personnel number is require");
        } else {
            
            try {
                
                Lecturer lecturer = LecturerDB.findByPersonnelNumber(number);
                
                if (lecturer == null) {
                    formData.putError("personnel_number", "Personnel number is invalid");
                } else if (lecturer.getPassword() != null) {
                    formData.putError("personnel_number", "Personnel with this number already registered");
                } else {
                    putSessionData("lecturer", lecturer);
                }
                
            } catch (SQLException ex) {
                respondWithPostServerError();
                return;
            }
            
        }
        
        if (formData.hasErrors()) {
            respondWithPostDataErrors();
            return;
        }
        
        redirectTo("/lecturer/register/1");
    }
    
    private boolean lecturerNotInsession() throws IOException {
        Lecturer lecturer = (Lecturer) getSessionData("lecturer");
        
        if (lecturer == null) {
            redirectTo("/lecturer/register");
            return true;
        }
        
        request.setAttribute("lecturer", lecturer);
        
        return false;
    }
    
    private void forwardToRegisterStageTwo() throws ServletException, IOException {
        
        if (lecturerNotInsession()) {
            return;
        }
        
        forwardTo("/lecturer-register1.jsp");
    }
    
    private void registerStageTwo() throws IOException {
        
        if (lecturerNotInsession()) {
            return;
        }
        
        String id = request.getParameter("lecturer_id");
                
        String number = request.getParameter("phone_number");
        
        String password = request.getParameter("password");
        
        if (number == null || number.length() != 11) {
            formData.putError("phone_number", "Phone number is invalid");
        } else {
            
            try {
                
                String phone = LecturerDB.findPhoneNumberByPersonnelNumber(number);
                
                if (phone != null) {
                    formData.putError("phone_number", "Phone number is invalid");
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
        
        Lecturer lec = (Lecturer) getSessionData("lecturer");
        lec.setPhoneNumber(number);
        lec.setPassword(password);
        lec.setId(Long.parseLong(id));
        
        try {
            LecturerDB.insert(lec);
        } catch (SQLException ex) {
            respondWithPostServerError();
            return;
        }
        
        removeSessionData("lecturer");
        putSessionData("auth_user", lec);
        response.sendRedirect("/lecturer/dashboard");
    }
    
    
    private void login() throws IOException {
        
        String number = request.getParameter("personnel_number");
        
        String password = request.getParameter("password");
        
        if (number == null || number.isEmpty()) {
            formData.setFormError("Crediantials are incorrect");
        } else if (password == null || password.length() < 6) {
            formData.setFormError("Crediantials are incorrect");
        } else {
            
            try {
                
                Lecturer lecturer = LecturerDB.findByPersonnelNumber(number);
                
                if (lecturer == null) {
                    formData.setFormError("Crediantials are incorrect");
                } else if (lecturer.getPassword() == null || !lecturer.getPassword().equals(password)) {
                    formData.setFormError("Crediantials are incorrect");
                } else {
                    putSessionData("auth_user", lecturer);
                    
                    LoginHistory history = new LoginHistory();
                    history.setLecturer(lecturer);
                    
                    try {
                        LoginHistoryDB.insertForLecturer(history);
                    } catch (SQLException ex) {

                    }

                    redirectTo("/lecturer/dashboard");
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
            
            List<SchoolClass> list = SchoolClassDB.findList(pager.get(1), PaginationFilter.LIMIT);
            
            int listCount = SchoolClassDB.countAll();
            
            request.setAttribute("schoolclasses", list);
            
            request.setAttribute("data_count", listCount);
            
            forwardTo("/lecturer-dashboard.jsp");
            
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    
    
    

}



