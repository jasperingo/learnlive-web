
<%@page import="learnlive.utils.MyUtils"%>
<%@page import="learnlive.entities.SchoolClass"%>
<%@page import="learnlive.utils.FormData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    
    <jsp:include page="/res/includes/head.jsp">
        <jsp:param name="title" value="Class | LearnLive" />
    </jsp:include>
    
    <body>
        
        <jsp:include page="/res/includes/header.jsp" />
        
        <main>
           
            <jsp:include page="/res/includes/class-header.jsp" />
                   
            <section>
                
                <div class="container">
                    
                    <video width="300" height="400" class="d-block w-100 h-auto bg-dark my-5" controls>
                        <source src="res/vid/test.mp4" type="video/mp4">
                        <source src="movie.ogg" type="video/ogg">
                        <span>Your browser does not support the video tag.</span>
                    </video>
                    
                    <div class="alert alert-danger my-5" role="alert">Currently unavailable</div>
                    
                    
                </div>
                
            </section>
           
        </main>
        
       
        <%@include file="res/includes/footer.html" %>
        
    </body>
    
    
</html>




