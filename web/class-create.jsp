
<%@page import="learnlive.utils.FormData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    
    <jsp:include page="/res/includes/head.jsp">
        <jsp:param name="title" value="Create Class | LearnLive" />
    </jsp:include>
    
    <body>
        
        <jsp:include page="/res/includes/header.jsp" />
        
        
        <main>
           
            <section class="container">
                
                <div class="row shadow my-5">
                    
                    <div class="col-md-6 d-none d-md-block border">
                        <img src="res/icon/create-svg.svg" width="400" height="400" class="d-block mx-auto" alt="icon of a boy writing" />
                    </div>
                    
                    <div class="col-md-6 border">
                        
                        <% FormData.Flasher data = new FormData.Flasher(session); %>
                        
                        <form class="login-form  rounded px-3 py-5 m-auto" method="POST" action="" novalidate="">

                            <h2 class="fs-4 mb-3 text-center text-orange fw-bold">Create class</h2>
                            
                            <% if (data.hasFormError()) { %>
                            <div class="alert alert-danger" role="alert"><%= data.getFormError() %></div>
                            <% } %>

                            <div class="mb-3">
                                <label for="topic_input" class="small">Topic</label>
                                <input type="text" name="topic" id="topic_input" 
                                       class="form-control <%= data.hasError("topic") ? "is-invalid" : "" %>" 
                                       value="<%= data.getData("topic")%>" required="" />
                                <div class="invalid-feedback"><%= data.getError("topic")%></div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="time_input" class="small">Start time</label>
                                <input type="time" name="time" id="time_input" 
                                       class="form-control <%= data.hasError("time") ? "is-invalid" : "" %>" 
                                       value="<%= data.getData("time")%>" required="" />
                                <div class="invalid-feedback"><%= data.getError("time")%></div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="date_input" class="small">Start date</label>
                                <input type="date" name="date" id="date_input" 
                                       class="form-control <%= data.hasError("date") ? "is-invalid" : "" %>" 
                                       value="<%= data.getData("date")%>" required="" />
                                <div class="invalid-feedback"><%= data.getError("date")%></div>
                            </div>
                            
                            <div class="mb-3">
                                <button type="submit" class="btn bg-orange text-white w-100 rounded-pill text-center">Create</button>
                            </div>

                        </form>
                        
                    </div>
                            
                </div>

            </section>
           
        </main>
       
       
        <footer class="bg-violent py-5 px-3 text-white text-center">
           
            <div>Made with love by students of Information Technology in Federal University of Technology Owerri.</div>
           
        </footer>
        
    </body>
    
    
</html>



