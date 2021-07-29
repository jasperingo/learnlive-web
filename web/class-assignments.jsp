
<%@page import="learnlive.entities.Attendance"%>
<%@page import="java.util.List"%>
<%@page import="learnlive.entities.Lecturer"%>
<%@page import="learnlive.entities.Student"%>
<%@page import="learnlive.entities.User"%>
<%@page import="learnlive.utils.MyUtils"%>
<%@page import="learnlive.entities.SchoolClass"%>
<%@page import="learnlive.utils.FormData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    
    <jsp:include page="/res/includes/head.jsp">
        <jsp:param name="title" value="Class Attendance | LearnLive" />
    </jsp:include>
    
    <body>
        
        <jsp:include page="/res/includes/header.jsp" />
        
        <main>
           
            <jsp:include page="/res/includes/class-header.jsp" />
                   
            <section>
                
                <div class="container">
                    
                    <div class="alert alert-danger my-5" role="alert">Currently unavailable</div>
                    
                </div>
                
            </section>
           
        </main>
        
       
        <%@include file="res/includes/footer.html" %>
        
    </body>
    
    
</html>




