
<%@page import="learnlive.entities.Submission"%>
<%@page import="learnlive.entities.Assignment"%>
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
                    
                    <% User user = (User) session.getAttribute("auth_user"); %>
                    
                    <% SchoolClass cl = (SchoolClass) request.getAttribute("class"); %>
                    
                    <% FormData.Flasher data = new FormData.Flasher(session); %>
                    
                    <% Assignment a = (Assignment) request.getAttribute("assignment"); %>
                    
                    <% if (user instanceof Lecturer && a == null) { %>
                    <form action="assignment/u" method="POST" class="mt-5" enctype="multipart/form-data">
                        
                        <% if (data.hasError()) { %>
                        <div class="alert alert-danger" role="alert">
                            <div><%= data.getError("content") %></div>
                            <div><%= data.getError("document") %></div>
                        </div>
                        <% } %>
                        
                        <div>
                            <label for="attendance-input" class="form-label fw-bold">Give assignment</label>
                            <div class="input-group input-group-lg">
                                <input type="hidden" name="code" value="<%= cl.getCode() %>" />
                                <input type="text" class="form-control" name="content" id="attendance-input" placeholder="Caption" required="" />
                                <input type="file" class="form-control" name="document" id="attendance-input" accept="*/*" required="" />
                                <button class="btn btn-outline-primary" type="submit">Submit</button>
                            </div>
                        </div>
                    </form>
                    <% } else if (a != null) { %>
                    
                    <h4 class="mt-5">Assignment</h4>
                    
                    <div class="d-flex border-bottom py-2">
                        <div class="flex-grow-1 me-2"><%= a.getContent() %></div>
                        <a href="assignment/d?file=<%= a.getDocument()%>" class="btn btn-outline-primary">Download assignment file</a>
                    </div>
                    
                    <% } else { %>
                    <div class="alert alert-danger my-5" role="alert">No assignment</div>
                    <% } %>
                    
                    <% if (user instanceof Student && a != null) { %>
                    
                    <% long marked = (Long) request.getAttribute("attendance_id"); %>
                    
                    <% long subID = (Long) request.getAttribute("submission_id"); %>
                    
                    <% if (marked > 0 && subID < 1) { %>
                    
                    <form action="submission/u" method="POST" class="mt-5" enctype="multipart/form-data" novalidate="">
                        
                        <% if (data.hasError()) { %>
                        <div class="alert alert-danger" role="alert">
                            <div><%= data.getError("content") %></div>
                            <div><%= data.getError("document") %></div>
                        </div>
                        <% } %>
                        
                        <div>
                            <label for="attendance-input" class="form-label fw-bold">Submit assignment</label>
                            <div class="input-group input-group-lg">
                                <input type="hidden" name="assignment" value="<%= a.getId() %>" />
                                <input type="hidden" name="code" value="<%= cl.getCode() %>" />
                                <input type="text" class="form-control" name="content" id="attendance-input" placeholder="Caption" required="" />
                                <input type="file" class="form-control" name="document" id="attendance-input" accept="*/*" required="" />
                                <button class="btn btn-outline-primary" type="submit">Submit</button>
                            </div>
                        </div>
                    </form>
                    
                    <% } %>
                    <% } %>
                    
                    <% if (a != null) { %>
                    <h4 class="mt-4">Submissions</h4>
                   
                    <% List<Submission> list = (List<Submission>) request.getAttribute("data_list"); %>
                    
                    <% if (!list.isEmpty()) { %>
                    
                    <table class="table mt-2">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Name</th>
                                <th scope="col">Matriculation number</th>
                                <th scope="col">Attendance number</th>
                                <th scope="col">Date</th>
                                <% if (user instanceof Lecturer) { %>
                                <th scope="col">caption</th>
                                <th scope="col"></th>
                                <% } %>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (int i=0; i<list.size(); i++) { %>
                            <% Submission sub = list.get(i); %>
                            <tr>
                                <th scope="row"><%= a.getId() %></th>
                                <td>
                                    <%= sub.getAttendance().getStudent().getFirstName() %>
                                    <%= sub.getAttendance().getStudent().getLastName() %>
                                </td>
                                <td><%= sub.getAttendance().getStudent().getMatriculationNumber()%></td>     
                                <td><%= sub.getAttendance().getNumber()%></td> 
                                <td><%= MyUtils.formatDateTime(sub.getCreatedAt()) %></td>
                                <% if (user instanceof Lecturer) { %>
                                <td><%= sub.getContent() %></td> 
                                <td>
                                    <a href="submission/d?file=<%= sub.getDocument() %>" class="btn btn-success">Download</a>
                                </td>
                                <% } %>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                    
                    <% } else { %>
                    <div class="alert alert-danger my-3" role="alert">No submission</div>
                    <% } %>
                    
                     <% String url = "class-assignments?code="+cl.getCode()+"&"; %>

                    <jsp:include page="res/includes/pager.jsp">
                        <jsp:param name="url" value="<%= url %>" />
                    </jsp:include>
                    
                    <% } %>
                    
                    
                </div>
                
            </section>
           
        </main>
        
       
        <%@include file="res/includes/footer.html" %>
        
    </body>
    
    
</html>




