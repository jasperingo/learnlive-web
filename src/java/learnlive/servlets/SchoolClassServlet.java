
package learnlive.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import learnlive.db.AssignmentDB;
import learnlive.db.AttendanceDB;
import learnlive.db.SchoolClassDB;
import learnlive.db.SubmissionDB;
import learnlive.entities.Assignment;
import learnlive.entities.Attendance;
import learnlive.entities.Lecturer;
import learnlive.entities.SchoolClass;
import learnlive.entities.Student;
import learnlive.entities.Submission;
import learnlive.filters.PaginationFilter;
import learnlive.utils.MyUtils;


@WebServlet(name = "SchoolClassServlet", urlPatterns = {
    "/class/create",
    "/class",
    "/class-assignments",
    "/class-attendance",
    "/class-settings",
    "/class/update-capacity",
    "/class/update-take-attendance",
    "/class/update-end-at"
})
public class SchoolClassServlet extends LiveServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        super.setRequest(request, response);
        
        switch (request.getServletPath()) {
            
            case "/class/create" :
                forwardTo("/class-create.jsp");
                break;
                
            case "/class" :
                forwardToClass();
                break;
                
            case "/class-settings" :
                forwardTo("/class-settings.jsp");
                break;
                
            case "/class-assignments" :
                forwardToAssignments();
                break;
                
            case "/class-attendance" :
                forwordToAttendance();
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
            
            case "/class/create":
                create();
                break;
                
            case "/class/update-capacity" :
                 updateCapacity();
                 break;

            case "/class/update-take-attendance" :
               updateTakeAttendance();
               break;
            
            case  "/class/update-end-at" :
                updateEndAt();
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
    
    private void create() throws IOException {
        
        String topic = request.getParameter("topic");
        
        String time = request.getParameter("time");
        
        String date = request.getParameter("date");
        
        System.out.println(topic+" "+time+" "+date);
        
        
        if (topic == null || topic.length() < 3) {
            formData.putError("topic", "Topic is invalid");
        }
        
        if (time == null || time.isEmpty()) {
            formData.putError("time", "Time is invalid");
        } else {
            
            try {
                new SimpleDateFormat("HH:mm").parse(time);
            } catch (ParseException ex) {
                formData.putError("time", "Time is invalid");
            }
        }
        
        if (date == null || date.isEmpty()) {
            formData.putError("date", "Date is invalid");
        } else {
            try {
                new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (ParseException ex) {
                formData.putError("date", "Date is invalid");
            }
        }
        
        if (formData.hasErrors()) {
            respondWithPostDataErrors();
            return;
        }
        
        LocalDateTime startDate;
        
        try {
            Date dd = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date+" "+time);
            startDate = dd.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (ParseException ex) {
            respondWithPostServerError();
            return;
        }
        
        
        Lecturer user = getAuthLecturer();
        
        SchoolClass sch = new SchoolClass();
        sch.setTopic(topic);
        sch.setLecturer(user);
        sch.setStartAt(startDate);
        sch.setCode(MyUtils.generateToken(6, SchoolClass.CODE_ALLOWED_CHARS));
        
        try {
            SchoolClassDB.insert(sch);
        } catch (SQLException ex) {
            respondWithPostServerError();
            return;
        }
        
        redirectTo("/class?code="+sch.getCode());
    }
    
    
    private void updateCapacity() throws IOException {
        
        int theLimit;
        
        String capacity = request.getParameter("capacity");
        
        SchoolClass sch = getSchoolClass();
        
        try {
            theLimit = Integer.parseInt(capacity);
            if (theLimit < 1) 
                throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            formData.putError("capacity", "Capacity is invalid");
            redirectBackWithPostDataErrors();
            return;
        }
        
        //count existing attendance and validate;
        
        sch.setCapacity(theLimit);
        
        try {
            SchoolClassDB.updateCapacity(sch);
        } catch (SQLException ex) {
            formData.putError("capacity", "An unknown error occured");
            redirectBackWithPostDataErrors();
            return;
        }
        
        redirectBack();
        
    }

    private void updateTakeAttendance() throws IOException {
        
        String takeAttendance = request.getParameter("take_attendance");
        
        if (takeAttendance == null || (!takeAttendance.equals("0") && !takeAttendance.equals("1"))) {
            formData.putError("take_attendance", "Field is invalid");
            redirectBackWithPostDataErrors();
            return;
        }
        
        SchoolClass sch = getSchoolClass();
        
        boolean take = !takeAttendance.equals("0");
        
        sch.setTakeAttendance(take);
        
        try {
            SchoolClassDB.updateTakeAttendance(sch);
        } catch (SQLException ex) {
            formData.putError("take_attendance", "An unknown error occured");
            redirectBackWithPostDataErrors();
            return;
        }
        
        redirectBack();
    }
    
    private long setIfStudentHasAttendance() throws SQLException {
        SchoolClass sch = getSchoolClass();
        
        if (getAuthUser() instanceof Student) {

            Long aid = AttendanceDB.findIdIfStudentExists(getAuthStudent().getId(), sch.getId());
            
            request.setAttribute("attendance_id", aid);
            
            return aid;
        }
        
        return 0;
    }
    
    private void forwordToAttendance() throws ServletException, IOException {
        
        SchoolClass sch = getSchoolClass();
        
        try {
            
            setIfStudentHasAttendance();
            
            List<Integer> pager = (List<Integer>) request.getAttribute("pager");
            
            List<Attendance> list = AttendanceDB.find(sch.getId(), pager.get(1), PaginationFilter.LIMIT);
            
            int listCount = AttendanceDB.countAll(sch.getId());
            
            request.setAttribute("data_list", list);
            
            request.setAttribute("data_count", listCount);
            
            forwardTo("/class-attendance.jsp");
            
        } catch (SQLException ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void updateEndAt() throws IOException {
        
        //check if already ended
        
        SchoolClass sch = getSchoolClass();
        
        sch.setEndAt(LocalDateTime.now());
        
        try {
            SchoolClassDB.updateEndAt(sch);
            redirectBack();
        } catch (SQLException ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
    }
    
    
    private void forwardToAssignments() throws ServletException, IOException {
        
        try {
            
            SchoolClass sch = getSchoolClass();
            
            long attendance = setIfStudentHasAttendance();
            
            Assignment a = AssignmentDB.findBySchoolClass(sch);
            
            if (getAuthUser() instanceof Student && a != null) {
                Long subID = SubmissionDB.findIdByAttendaceAndAssignment(a.getId(), attendance);
                request.setAttribute("submission_id", subID);
            }
            
            if (a != null) {
                
                List<Integer> pager = (List<Integer>) request.getAttribute("pager");

                List<Submission> list = SubmissionDB.find(a.getId(), pager.get(1), PaginationFilter.LIMIT);

                int listCount = SubmissionDB.countAll(a.getId());

                request.setAttribute("data_list", list);
                
                request.setAttribute("data_count", listCount);
            
            }
            
            request.setAttribute("assignment", a);
            
            forwardTo("/class-assignments.jsp");
            
        } catch (SQLException ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void forwardToClass() throws ServletException, IOException {
        
        String ip;
        
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 5002);
            ip = socket.getLocalAddress().getHostAddress();
        }catch(Exception e){
            InetAddress ina = InetAddress.getLocalHost();
            ip = ina.getHostAddress();
        }
        
        request.setAttribute("stream_ip", ip);
        
        forwardTo("/class.jsp");
    }

    
    
}




