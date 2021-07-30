
<%@page import="learnlive.entities.Lecturer"%>
<%@page import="learnlive.entities.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<header class="ff-bold">
    <nav class="navbar navbar-expand-md navbar-light bg-gold py-3">
        <div class="container">
            <a class="navbar-brand" href="<%= request.getContextPath() %>">
                <img src="res/img/favicon.png" width="50" height="50" alt="The website's logo" class="bg-white d-inline-block align-middle" />
                <h1 class="text-truncate d-none d-sm-inline-block align-middle text-orange">LearnLive</h1>
            </a>
             
            <% User user = (User) session.getAttribute("auth_user"); %>
            
            <% if (user != null && !request.getServletPath().equals("/index.jsp")) { %>
            <div>
                <% if (user instanceof Lecturer) { %>
                
                <% if (request.getServletPath().equals("/lecturer-dashboard.jsp")) { %>
                <a href="class/create" class="text-white text-decoration-none bg-violent p-2 p-md-3 rounded-pill">Create class</a>
                <% } else { %>
                <a href="lecturer/dashboard" class="text-white text-decoration-none bg-violent p-2 p-md-3 rounded-pill">Dashboard</a>
                <% } %>
                
                <% if (request.getServletPath().equals("/lecturer-dashboard.jsp")) { %>
                <a href="lecturer/profile" class="text-white text-decoration-none bg-violent p-2 p-md-3 rounded-pill ms-3">Profile</a>
                <% } %>
                
                <% } else { %>
                
                <% if (request.getServletPath().equals("/student-dashboard.jsp")) { %>
                <a href="student/profile" class="text-white text-decoration-none bg-violent p-2 p-md-3 rounded-pill ms-3">Profile</a>
                <% } else { %>
                <a href="student/dashboard" class="text-white text-decoration-none bg-violent p-2 p-md-3 rounded-pill">Dashboard</a>
                <% } %>
                
                <% } %>
                
            </div>
            <% } %>
            
            <% if (request.getServletPath().equals("/index.jsp")) { %>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link text-violent" href="lecturer/login">I'm a lecturer</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-violent" href="student/login">I'm a student</a>
                    </li>
                </ul>
            </div>
            <% } %>
            
        </div>
    </nav>
</header>




