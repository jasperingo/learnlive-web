
<%@page import="learnlive.entities.Lecturer"%>
<%@page import="learnlive.utils.FormData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <jsp:include page="/res/includes/head.jsp">
        <jsp:param name="title" value="Lecturer Register | LearnLive" />
    </jsp:include>
    
    
    <body>
        
        <main>
           
           <section class="container-fluid">
                   
                <div class="col-md-4 bg-violent d-none d-md-flex align-items-center position-fixed top-0 start-0 bottom-0">

                    <img src="res/icon/signup-svg.svg" class="d-none d-md-block w-75 m-auto" />

                </div>
                
                <div class="col-md-8 d-flex align-items-center py-5 position-fixed top-0 end-0 bottom-0 overflow-auto">

                    <% FormData.Flasher data = new FormData.Flasher(session); %>
                    
                    <form class="login-form shadow rounded px-3 py-5 m-auto" method="POST" action="">

                         <a class="d-block mb-4" href="<%= request.getContextPath() %>">
                             <img src="res/img/favicon.png" width="40" height="40" alt="The website's logo" class="bg-white d-block m-auto" />
                         </a>

                         <h2 class="fs-4 mb-3 text-center text-violent fw-bold">Register as a lecturer</h2>

                         <% if (data.hasFormError()) { %>
                         <div class="alert alert-danger" role="alert"><%= data.getFormError() %></div>
                         <% } %>

                         <div class="bg-light p-2 mb-3">
                             <% Lecturer l = (Lecturer)session.getAttribute("lecturer"); %>
                             <span>Is this you?</span>
                             <div class="fw-bold"><%= l.getFirstName() %> <%= l.getLastName() %></div>
                             <input type="hidden" name="lecturer_id" value="<%= l.getId() %>" />
                         </div>
                         
                         <div class="mb-3">
                             <label for="number_input" class="small">Phone number</label>
                             <input type="text" name="phone_number" id="number_input" 
                                    class="form-control <%= data.hasError("phone_number") ? "is-invalid" : "" %>" 
                                    value="<%= data.getData("phone_number")%>" required="" />
                             <div class="invalid-feedback"><%= data.getError("phone_number")%></div>
                         </div>

                         <div class="mb-3">
                             <label for="number_input" class="small">Password</label>
                             <input type="password" name="password" id="password_input" 
                                    class="form-control <%= data.hasError("password") ? "is-invalid" : "" %>" 
                                    value="<%= data.getData("password")%>" required="" />
                             <div class="invalid-feedback"><%= data.getError("password")%></div>
                         </div>
                         
                         <div class="mb-3">
                             <button type="submit" class="btn bg-violent text-white w-100 rounded-pill text-center">Register</button>
                         </div>

                         <div class="mb-3">
                             <span>Already have an account?</span>
                             <a href="lecturer/login">Login</a>
                         </div>
                         
                     </form>
                   
               </div>
               
           </section>
           
           
       </main>
       
       
    </body>
    
</html>



