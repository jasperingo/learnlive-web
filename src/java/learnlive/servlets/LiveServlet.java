
package learnlive.servlets;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import learnlive.entities.Lecturer;
import learnlive.entities.Student;
import learnlive.entities.User;
import learnlive.utils.FormData;


@WebServlet(name = "LiveServlet")
abstract public class LiveServlet extends HttpServlet {
    
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected FormData formData;
    
    
    protected void setRequest(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        
        if (request.getMethod().equals("POST")) formData = new FormData();
        
    }
    
    protected void putSessionData(String key, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }
    
    protected User getAuthUser() {
        return (User) getSessionData("auth_user");
    }
    
    protected Lecturer getAuthLecturer() {
        return (Lecturer) getAuthUser();
    }
    
    protected Student getAuthStudent() {
        return (Student) getAuthUser();
    }
    
    protected Object getSessionData(String key) {
        HttpSession session = request.getSession();
        return session.getAttribute(key);
    }
    
    protected void removeSessionData(String key) {
        HttpSession session = request.getSession();
        session.removeAttribute(key);
    }
    
    protected Object flashSessionData(String key) {
        HttpSession session = request.getSession();
        Object o = session.getAttribute(key);
        session.removeAttribute(key);
        return o;
    }
    
    protected void putSessionFormData() {
        putSessionData("form_data", formData);
    }
    
    protected void respondWithPostServerError() throws IOException {
        formData.setFormError("An error occured try again");
        respondWithPostDataErrors();
    }
    
    
    protected void putRequestParametersInToFormData() {
        Map<String, String[]> values = request.getParameterMap();
        values.entrySet().forEach(mapElement -> {
            formData.putData((String)mapElement.getKey(), (String[])mapElement.getValue());
        });
    }
    
    protected void respondWithPostDataErrors() throws IOException {
        respondWithPostDataErrors(request.getServletPath());
    }
    
    protected void respondWithPostDataErrors(String url) throws IOException {
        putRequestParametersInToFormData();
        putSessionFormData();
        redirectTo(url);
    }
    
    protected void redirectBackWithPostServerError() throws IOException {
        formData.setFormError("An error occured try again");
        redirectBackWithPostDataErrors();
    }
    
    protected void redirectBackWithPostDataErrors() throws IOException {
        putRequestParametersInToFormData();
        putSessionFormData();
        redirectBack();
    }
    
    protected void forwardTo(String url) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
    protected void redirectBack() throws IOException {
        response.sendRedirect(request.getHeader("referer"));
    }
    
    protected void redirectTo(String url) throws IOException {
        response.sendRedirect(request.getContextPath()+url);
    }
    
    protected void logout() throws IOException {
        
        HttpSession session = request.getSession();
        session.invalidate();
        redirectTo("");
    }

    
    
}


