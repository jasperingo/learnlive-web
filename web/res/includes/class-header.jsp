

<%@page import="learnlive.entities.Lecturer"%>
<%@page import="learnlive.utils.MyUtils"%>
<%@page import="learnlive.entities.SchoolClass"%>

<% SchoolClass cl = (SchoolClass) request.getAttribute("class"); %>
            
<section class="border-bottom pt-3">

    <div class="container">

        <div class="d-flex mb-3">
            <h2 class="flex-grow-1 fw-bold"><%= cl.getTopic() %></h2>

            <div class=" d-flex justify-content-between align-items-center">
                <div>
                    <dt class="text-violent">Start date</dt>
                    <dd><%= MyUtils.formatDateTime(cl.getStartAt()) %></dd>
                </div>
                <% if (cl.getEndAt() != null) { %>
                <div class="ms-3">
                    <dt class="text-violent">End date</dt>
                    <dd><%= MyUtils.formatDateTime(cl.getEndAt()) %></dd>
                </div>
                <% } %>
                <% String[] statusData = MyUtils.getClassTimeStatus(cl.getStartAt(), cl.getEndAt()); %>
                <div class="<%= statusData[0] %> ms-3 py-1 px-2 rounded-pill"><%= statusData[1] %></div>
            </div>
        </div>

        <div class="row">

            <div class="col-md-6">
                <div class="fw-bold text-orange">Lecturer</div>
                <h3>
                    <%= cl.getLecturer().getFirstName() %>
                    <%= cl.getLecturer().getLastName() %>
                </h3>
            </div>

            <div class="col-md-6">
                <div class="fw-bold text-orange">Class link</div>
                <div class="p-2 bg-secondary text-white rounded d-flex align-items-center">
                    <span class="flex-grow-1">http://localhost:8080/learnlive/class?code=<%= cl.getCode() %></span>
                    <button class="btn btn-primary" id="copy-button">Copy</button>
                    <input type="hidden" id="copy-input" value="http://localhost:8080/learnlive/class?code=<%= cl.getCode() %>" />
                </div>

                <div class="position-fixed bottom-0 end-0 p-2" style="z-index: 5">
                    <div id="copyToast" style="width: auto;" class="toast hide" role="alert" aria-live="assertive" aria-atomic="true">
                        <div class="d-flex">
                            <div class="toast-body">Class link has been copied.</div>
                            <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                        </div>
                    </div>
                </div>
            </div>

        </div>        
        
        <%! 
            public static String getActive(HttpServletRequest req, String path) {
               return req.getServletPath().equals(path) ? "bg-violent text-white" : "btn text-violent bg-light";
        }%>
        
        <ul class="list-group list-group-horizontal mt-4 mb-2 overflow-auto w-100 text-nowrap">
            <li class="list-group-item border-0 ps-0">
                <a href="class?code=<%= cl.getCode() %>" class="btn <%= getActive(request, "/class.jsp") %>">Stream</a>
            </li>
            <li class="list-group-item border-0">
                <a href="class-attendance?code=<%= cl.getCode() %>" class="btn <%= getActive(request, "/class-attendance.jsp") %>">Attendance</a>
            </li>
            <li class="list-group-item border-0">
                <a href="class-assignments?code=<%= cl.getCode() %>" class="btn <%= getActive(request, "/class-assignments.jsp") %>">Assignments</a>
            </li>
            <% if (session.getAttribute("auth_user") instanceof Lecturer) { %>
            <li class="list-group-item border-0">
                <a href="class-settings?code=<%= cl.getCode() %>" class="btn <%= getActive(request, "/class-settings.jsp") %>">Settings</a>
            </li>
            <% } %>
       </ul>


    </div>

</section>

            
            
            
