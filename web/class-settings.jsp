
<%@page import="learnlive.utils.MyUtils"%>
<%@page import="learnlive.entities.SchoolClass"%>
<%@page import="learnlive.utils.FormData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    
    <jsp:include page="/res/includes/head.jsp">
        <jsp:param name="title" value="Class Settings | LearnLive" />
    </jsp:include>
    
    <body>
        
        <jsp:include page="/res/includes/header.jsp" />
        
        <main>
           
            <jsp:include page="/res/includes/class-header.jsp" />
                   
            <section>
                
                <% SchoolClass cl = (SchoolClass) request.getAttribute("class"); %>
                
                <div class="container">
                    
                    <% if (cl.getEndAt() == null) { %>
                    
                    <% FormData.Flasher data = new FormData.Flasher(session); %>
                    
                    <form action="class/update-capacity" method="POST" class="mt-5" novalidate="">
                        <input type="hidden" name="code" value="<%= cl.getCode() %>" />
                        <div>
                            <label for="capacity-input" class="form-label fw-bold">Capacity</label>
                            <div class="input-group input-group-lg">
                                <input type="number" class="form-control <%= data.hasError("capacity") ? "is-invalid" : "" %>" 
                                       name="capacity" id="capacity-input" placeholder="Set attendance limit" 
                                       value="<%= cl.getCapacity() == 0 ? "" : cl.getCapacity() %>"
                                       required="" />
                                <button class="btn btn-outline-secondary" type="submit">Submit</button>
                                <div class="invalid-feedback"><%= data.getError("capacity")%></div>
                            </div>
                        </div>
                    </form>
                    
                    <form action="class/update-take-attendance" method="POST" class="mt-5">
                        <input type="hidden" name="code" value="<%= cl.getCode() %>" />
                        <fieldset>
                            <label for="" class="col-sm-3 col-form-label fw-bold">Take attendance</label>
                            <div class="input-group input-group-lg">
                                <fieldset class="form-control border p-2 rounded">
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="take_attendance" id="take_1_input" value="0" 
                                               <%= cl.isTakeAttendance() ? "" : "checked" %> />
                                        <label class="form-check-label" for="flexRadioDefault1">No</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="take_attendance" id="take_2_input"value="1" 
                                               <%= !cl.isTakeAttendance() ? "" : "checked" %> />
                                        <label class="form-check-label" for="flexRadioDefault2">Yes</label>
                                    </div>
                                </fieldset>
                                <button class="btn btn-outline-secondary" type="submit">Submit</button>
                            </div>
                        </fieldset>
                    </form>
                    
                    <form action="" method="POST" class="mt-5">
                        <input type="hidden" name="code" value="<%= cl.getCode() %>" />
                        <div>
                            <label for="" class="col-sm-3 col-form-label fw-bold">End class</label>
                            <button class="btn btn-outline-secondary btn-lg d-block w-100" type="button" id="button-addon2">End</button>
                        </div>
                    </form>
                                       
                    <% } else { %>
                    
                    <div class="alert alert-danger my-5" role="alert">This class has ended</div>
                    
                    <% } %>
                                       
                    
                </div>
                
            </section>
           
        </main>
        
       
        <%@include file="res/includes/footer.html" %>
        
    </body>
    
    
</html>




