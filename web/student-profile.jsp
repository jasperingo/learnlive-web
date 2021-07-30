
<%@page import="learnlive.entities.Student"%>
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
            
            <% Student s = (Student) session.getAttribute("auth_user"); %>
           
            <section class="container">
                
                <div class="shadow rounded p-3 mt-4 mx-auto" style="width: 300px">
                    
                    <img src="res/img/user.png" width="50" height="50" alt="The website's logo" class="bg-white d-inline-block align-middle" />
                    
                    <dl class="mt-4">
                        <div>
                            <dt class="text-violent">First name</dt>
                            <dd class="fs-4"><%= s.getFirstName() %></dd>
                        </div>

                        <div>
                            <dt class="text-violent">Last name</dt>
                            <dd class="fs-4"><%= s.getLastName() %></dd>
                        </div>

                        <div>
                            <dt class="text-violent">Matriculation number</dt>
                            <dd class="fs-4"><%= s.getMatriculationNumber()%></dd>
                        </div>

                        <div>
                            <dt class="text-violent">Phone number</dt>
                            <dd class="fs-4"><%= s.getPhoneNumber() %></dd>
                        </div>
                    </dl>
                        
                    <form method="POST" action="student/logout">
                        <button class="btn btn-danger">Log out</button>
                    </form>
                    
                </div>
               
           </section>
           
           
        </main>
        
        <%@include file="res/includes/footer.html" %>
       
    </body>
    
    
</html>



