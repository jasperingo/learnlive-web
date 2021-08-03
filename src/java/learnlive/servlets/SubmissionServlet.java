
package learnlive.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import learnlive.db.AttendanceDB;
import learnlive.db.SubmissionDB;
import learnlive.entities.Assignment;
import learnlive.entities.Attendance;
import learnlive.entities.SchoolClass;
import learnlive.entities.Submission;
import learnlive.utils.MyUtils;
import org.apache.commons.io.IOUtils;


@MultipartConfig
@WebServlet(name = "SubmissionServlet", urlPatterns = {
    "/submission/u",
    "/submission/d"
})
public class SubmissionServlet extends LiveServlet {
    
    
    public static final String DOCUMENTS_PATH = "../../../../submissions";
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        super.setRequest(request, response);
        
        switch (request.getServletPath()) {
            
            case "/submission/d" :
                forwardToDownload();
                break;
                
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet SubmissionServlet at " + request.getContextPath() + "</h1>");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        super.setRequest(request, response);
        
        switch (request.getServletPath()){
            
            case "/submission/u":
                upload();
                break;
              
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet SubmissionServlet at " + request.getContextPath() + " 4 POST</h1>");
        }
    }   
    
    
    private void upload() throws IOException, ServletException {
        
        SchoolClass sch = (SchoolClass) request.getAttribute("class");
        
        long attend, assignId=0;
        
        try {
            attend = AttendanceDB.findIdIfStudentExists(getAuthStudent().getId(), sch.getId());
            
            if (attend < 1) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            
        } catch (SQLException ex) {
            redirectBackWithPostServerError();
            return;
        }
        
        try {
            assignId = Long.parseLong(request.getParameter("assignment"));
        } catch (NumberFormatException e) {
            formData.putError("assignment", "Assignment is invalid");
        }
        
        String content = request.getParameter("content");
        
        Part filePart = request.getPart("document");
        
        
        if (content == null || content.isEmpty()) {
            formData.putError("content", "Content is invalid");
        }
        
        if (filePart == null || Paths.get(filePart.getSubmittedFileName()).getFileName().toString().isEmpty()) {
             formData.putError("document", "Document is invalid");
        }
        
        //check for existing submission and redirect
        
        if (formData.hasErrors()) {
            redirectBackWithPostDataErrors();
            return;
        }
        
        File aFolder = new File(DOCUMENTS_PATH);
        if(!aFolder.exists()) {
            aFolder.mkdir();
        }
        
        if (filePart != null) {
            
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            
            String[] fileNameSplit = fileName.split("\\.");
            String extension = fileNameSplit[fileNameSplit.length-1];
            String uploadName = ("submission-"+MyUtils.generateToken(6, Submission.CODE_ALLOWED_CHARS)+"."+extension).replace(" ", "-");
            String upload = DOCUMENTS_PATH+"/"+uploadName;
            
            InputStream fileContent = filePart.getInputStream();

            File file = new File(upload);

            try(OutputStream outputStream = new FileOutputStream(file)){
                IOUtils.copy(fileContent, outputStream);
            } catch (IOException e) {
                redirectBackWithPostServerError();
                return;
            }
            
            Submission sub = new Submission();
            sub.setContent(content);
            sub.setDocument(uploadName);
            
            Assignment a = new Assignment();
            a.setId(assignId);
            
            Attendance at = new Attendance();
            at.setId(attend);
            
            sub.setAssignment(a);
            sub.setAttendance(at);
            
            try {
                SubmissionDB.insert(sub);
            } catch (SQLException ex) {
                respondWithPostServerError();
                return;
            }
            
        }
        
        redirectBack();
    }
    
    
    private void forwardToDownload() throws IOException {
        
        String document = request.getParameter("file");
        
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\""+document);
        try(FileInputStream file = new FileInputStream(DOCUMENTS_PATH+"/"+document)){
            int i;
            while ((i = file.read())!=-1){
                response.getOutputStream().write(i);
            }
        } catch (IOException ex) {
            Logger.getLogger(SubmissionServlet.class.getName()).log(Level.SEVERE, null, ex);
            redirectBack();
        }
        
        
    }
    
}




