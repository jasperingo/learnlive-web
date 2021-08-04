
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
                    
                    <video id="screen" width="300" height="400" 
                           class="d-block w-100 h-auto bg-dark my-5" controls poster="res/img/favicon.png"></video>
                    
                    <button id="start-stream" class="btn btn-primary" data-ip="${stream_ip}">Start</button>
                    
                </div>
                
            </section>
           
        </main>
        
       
        <%@include file="res/includes/footer.html" %>
        
        <script src="res/js/hls.js"></script>
         
        
    </body>
    
    
</html>




