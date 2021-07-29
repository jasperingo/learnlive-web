
<%@page import="learnlive.entities.Lecturer"%>
<%@page import="learnlive.utils.FormData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <jsp:include page="/res/includes/head.jsp">
        <jsp:param name="title" value="Lecturer Log in | LearnLive" />
    </jsp:include>
    
    
    <body>
        
        <jsp:include page="res/includes/header.jsp" />
        
        <main>
           
            <% Lecturer l = (Lecturer) session.getAttribute("auth_user"); %>
            
            <section class="container">
                
                <div class="shadow rounded p-3 mt-4 mx-auto" style="width: 300px">
                    
                    <img src="res/img/user.png" width="50" height="50" alt="The website's logo" class="bg-white d-inline-block align-middle" />
                    
                    <dl class="mt-4">
                        <div>
                            <dt class="text-violent">First name</dt>
                            <dd class="fs-4"><%= l.getFirstName() %></dd>
                        </div>

                        <div>
                            <dt class="text-violent">Last name</dt>
                            <dd class="fs-4"><%= l.getLastName() %></dd>
                        </div>

                        <div>
                            <dt class="text-violent">Personnel number</dt>
                            <dd class="fs-4"><%= l.getPersonnelNumber() %></dd>
                        </div>

                        <div>
                            <dt class="text-violent">Phone number</dt>
                            <dd class="fs-4"><%= l.getPhoneNumber() %></dd>
                        </div>
                    </dl>
               
                </div>
               
           </section>
           
           
        </main>
        
        <%@include file="res/includes/footer.html" %>
       
    </body>
    
    
</html>



