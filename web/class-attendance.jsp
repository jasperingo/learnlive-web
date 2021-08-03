
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
                
                <% User user = (User) session.getAttribute("auth_user"); %>
                
                <% SchoolClass cl = (SchoolClass) request.getAttribute("class"); %>
                
                <div class="container">
                    
                    <% FormData.Flasher data = new FormData.Flasher(session); %>
                    
                    <% if (cl.getEndAt() == null && user instanceof Student) { %>
                    
                    <% long marked = (Long) request.getAttribute("attendance_id"); %>
                    
                    <% if (marked < 1) { %>
                    <form action="attendance/add" method="POST" class="mt-5" novalidate="">
                        
                        <% if (data.hasFormError()) { %>
                        <div class="alert alert-danger" role="alert"><%= data.getFormError() %></div>
                        <% } %>
                        
                        <input type="hidden" name="code" value="<%= cl.getCode() %>" />
                        <div>
                            <label for="attendance-input" class="form-label fw-bold">Attendance number</label>
                            <div class="input-group input-group-lg">
                                <input type="number" class="form-control" name="number" id="attendance-input" required="" />
                                <button class="btn btn-outline-primary" type="submit">Submit</button>
                            </div>
                        </div>
                    </form>
                    <% } %>
                    <% } %>
                    
                    <% if (user instanceof Lecturer) { %>
                    <form action="attendance/lecturer/add" method="POST" class="mt-5" novalidate="">
                        
                        <% if (data.hasFormError()) { %>
                        <div class="alert alert-danger" role="alert"><%= data.getFormError() %></div>
                        <% } %>
                        
                        <input type="hidden" name="code" value="<%= cl.getCode() %>" />
                        
                        <div class="mb-2">
                            <label for="m-input" class="form-label fw-bold">Matriculation number</label>
                            <input type="text" class="form-control form-control-lg" name="matriculation_number" id="m-input" required="" />
                        </div>
                        
                        <div>
                            <label for="attendance-input" class="form-label fw-bold">Attendance number</label>
                            <div class="input-group input-group-lg">
                                <input type="number" class="form-control" name="number" id="attendance-input" required="" />
                                <button class="btn btn-outline-primary" type="submit">Submit</button>
                            </div>
                        </div>
                    </form>
                    <% } %>
                    
                    <% List<Attendance> list = (List<Attendance>) request.getAttribute("data_list"); %>
                    
                    <% if (!list.isEmpty()) { %>
                
                    <table class="table mt-5">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Name</th>
                                <th scope="col">Matriculation number</th>
                                <th scope="col">Date</th>
                                <% if (user instanceof Lecturer) { %>
                                <th scope="col"></th>
                                <% } %>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (int i=0; i<list.size(); i++) { %>
                            <% Attendance a = list.get(i); %>
                            <tr>
                                <th scope="row"><%= a.getNumber() %></th>
                                <td>
                                    <%= a.getStudent().getFirstName() %>
                                    <%= a.getStudent().getLastName() %>
                                </td>
                                <td><%= a.getStudent().getMatriculationNumber()%></td>                                                                                                                                                                                                                                                            
                                <td><%= MyUtils.formatDateTime(a.getCreatedAt()) %></td>
                                <% if (user instanceof Lecturer) { %>
                                <td>
                                    <form method="POST" action="attendance/cancel">
                                        <input type="hidden" name="id" value="<%= a.getId() %>" />
                                        <input type="hidden" name="code" value="<%= cl.getCode() %>" />
                                        <button type="submit" class="btn btn-danger">Cancel</button>
                                    </form>
                                </td>
                                <% } %>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                    
                    <% } else { %>
                    <div class="alert alert-danger my-5" role="alert">No Attendance marked</div>
                    <% } %>
                    
                     <% String url = "class-attendance?code="+cl.getCode()+"&"; %>

                    <jsp:include page="res/includes/pager.jsp">
                        <jsp:param name="url" value="<%= url %>" />
                    </jsp:include>
                    
                </div>
                
            </section>
           
        </main>
        
       
        <%@include file="res/includes/footer.html" %>
        
    </body>
    
    
</html>




