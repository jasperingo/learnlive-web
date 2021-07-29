
<%@page import="learnlive.utils.MyUtils"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="learnlive.entities.SchoolClass"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    
    <jsp:include page="/res/includes/head.jsp">
        <jsp:param name="title" value="Dashboard Lecturer | LearnLive" />
    </jsp:include>
    
    <body>
        
        <jsp:include page="/res/includes/header.jsp" />
        
        
        <main>
           
            <section class="container">
                 
                <h2 class="mt-3">Created classes</h2>
                
                <% List<SchoolClass> list = (List<SchoolClass>) request.getAttribute("schoolclasses"); %>
                    
                <% if (!list.isEmpty()) { %>
                
                <ul class="list-unstyled">
                    
                    <% for (int i=0; i<list.size(); i++) { %>
                    <% SchoolClass cl = list.get(i); %>
                    <li class="my-4">
                        <a href="class?code=<%= cl.getCode() %>" class="class-list-item d-block shadow p-3 rounded text-decoration-none text-dark">
                            <div class="fw-bold fs-4"><%= cl.getTopic() %></div>
                            <div class="mt-2">
                                <img src="res/icon/clock.svg" class="d-inline-block align-middle" width="25" height="25" />
                                <span class="d-inline-block align-middle"><%= MyUtils.formatDateTime(cl.getStartAt()) %></span>
                                <% String[] statusData = MyUtils.getClassTimeStatus(cl.getStartAt(), cl.getEndAt()); %>
                                <span class="<%= statusData[0] %> d-inline-block align-middle py-1 px-2"><%= statusData[1] %></span>
                            </div>
                        </a>
                    </li>
                    <% } %>
                    
                </ul>
                
                <% } else { %>
                <div class="alert alert-danger my-5" role="alert">No classes</div>
                <% } %>
                
                <jsp:include page="res/includes/pager.jsp">
                    <jsp:param name="url" value="lecturer/dashboard?" />
                </jsp:include>

            </section>
           
        </main>
       
       
        <%@include file="res/includes/footer.html" %>
        
        
    </body>
    
    
</html>



