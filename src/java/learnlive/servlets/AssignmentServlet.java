
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
import learnlive.db.AssignmentDB;
import learnlive.entities.Assignment;
import learnlive.entities.SchoolClass;
import learnlive.entities.Submission;
import learnlive.utils.MyUtils;
import org.apache.commons.io.IOUtils;


@MultipartConfig
@WebServlet(name = "AssignmentServlet", urlPatterns = {
    "/assignment/u",
    "/assignment/d"
})
public class AssignmentServlet extends LiveServlet {
    
    
    public static final String DOCUMENTS_PATH = "../../../../assignments";
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        super.setRequest(request, response);
        
        switch (request.getServletPath()) {
            
            case "/assignment/d" :
                forwardToDownload();
                break;
                
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet AssignmentServlet at " + request.getContextPath() + "</h1>");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        super.setRequest(request, response);
        
        switch (request.getServletPath()){
            
            case "/assignment/u":
                upload();
                break;
              
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet AssignmentServlet at " + request.getContextPath() + " 4 POST</h1>");
        }
    }   
    
    
    private void upload() throws IOException, ServletException {
        
        String content = request.getParameter("content");
        
        Part filePart = request.getPart("document");
        
        
        if (content == null || content.isEmpty()) {
             formData.putError("content", "Content is invalid");
        }
        
        if (filePart == null || Paths.get(filePart.getSubmittedFileName()).getFileName().toString().isEmpty()) {
             formData.putError("document", "Document is invalid");
        }
        
        //check for existing assignment and redirect
        
        if (formData.hasErrors()) {
            redirectBackWithPostDataErrors();
            return;
        }
        
        File aFolder = new File(DOCUMENTS_PATH);
        if(!aFolder.exists()) {
            aFolder.mkdir();
        }
        
        SchoolClass sch = (SchoolClass) request.getAttribute("class");
        
        if (filePart != null) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            String[] fileNameSplit = fileName.split("\\.");
            String extension = fileNameSplit[fileNameSplit.length-1];
            String uploadName = ("assigment-"+MyUtils.generateToken(6, Assignment.CODE_ALLOWED_CHARS)+"."+extension).replace(" ", "-");
            String upload = DOCUMENTS_PATH+"/"+uploadName;
            
            InputStream fileContent = filePart.getInputStream();

            File file = new File(upload);

            try(OutputStream outputStream = new FileOutputStream(file)){
                IOUtils.copy(fileContent, outputStream);
            } catch (IOException e) {
                redirectBackWithPostServerError();
                return;
            }
            
            Assignment a = new Assignment();
            a.setContent(content);
            a.setDocument(uploadName);
            a.setSchoolClass(sch);
            
            try {
                AssignmentDB.insert(a);
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
            Logger.getLogger(AssignmentServlet.class.getName()).log(Level.SEVERE, null, ex);
            redirectBack();
        }
        
        
    }
    
    
    
}


