
<%@ page isErrorPage="true" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <jsp:include page="res/includes/head.jsp">
        <jsp:param name="title" value="Error page | LearnLive" />
    </jsp:include>
    
    <body>
        
        <header class="py-3 ff-bold text-center">
            <div class="container">
                <a class="navbar-brand" href="<%= request.getContextPath() %>">
                    <img src="res/img/favicon.png" width="50" height="50" alt="The website's logo" class="bg-white d-inline-block align-middle" />
                    <h1 class="text-truncate d-none d-sm-inline-block align-middle text-orange">LearnLive</h1>
                </a>
            </div>
        </header>
        
        
        <main class="py-3 text-center">
            
            <div class="container">
                <% System.out.println(exception); %>
                <h2 class="text-danger fw-bold mt-4 fs-1">Oops! <%= request.getAttribute("javax.servlet.error.status_code")%> error occurred</h2>
                
		<h3 class="mt-4"> At this url: <%= request.getAttribute("javax.servlet.error.request_uri")%></h3>
                
                <div>
                    <img src="res/icon/error-svg.svg" class="card-img-top d-block m-auto" alt="icon of a boy writing" width="200" height="300" />
                </div>
                
            </div>
            
        </main>
        
        
    </body>
</html>


