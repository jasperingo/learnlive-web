
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <jsp:include page="/res/includes/head.jsp">
        <jsp:param name="title" value="LearnLive - Classroom ehancement system" />
    </jsp:include>
    
    <body>
        
       <jsp:include page="res/includes/header.jsp" />
        
       <main>
           
           <section class="container">
               
               <div class="row">
                   
                   <div class="col-md-6">
                        
                        <h2 class="scale-up text-violent p-3 mt-md-5 fw-bold">A Classroom Enhancement System</h2>

                        <h3 class="scale-up text-violent p-3 my-md-2 d-inline-block">For better education</h3>
                        
                        <div class="my-5 scale-up">
                            <a href="lecturer/register" class="bg-orange text-white p-3 rounded-pill text-decoration-none">Lecturer register</a>
                            <a href="student/register" class="bg-orange text-white p-3 ms-3 rounded-pill text-decoration-none">Student register</a>
                        </div>
                        
                   </div>
                   
                   <div class="col-md-6">
                       <img src="res/icon/index-icon.svg" class="d-none d-md-block" />
                   </div>
                   
               </div>
               
           </section>
           
           
           <section class="container">
               
               <h4 class="mt-5 fw-bold bg-violent text-white p-3 rounded-pill d-inline-block">Features of LearnLive</h4>
               
               <ul class="row list-unstyled mb-5">
                   
                    <li class="col-md-4 my-4">
                        <div class="card shadow">
                            <img src="res/icon/streaming-svg.svg" class="card-img-top" alt="icon of a lady studying" />
                            <div class="card-body">
                                <h5 class="card-title fw-bold">Stream classes</h5>
                            </div>
                        </div>
                    </li>
                    
                    <li class="col-md-4 my-4">
                        <div class="card shadow">
                            <img src="res/icon/attendance-svg.svg" class="card-img-top" alt="icon of a boy writing" />
                            <div class="card-body">
                                <h5 class="card-title fw-bold">Take class attendance</h5>
                            </div>
                        </div>
                    </li>
                   
                    <li class="col-md-4 my-4">
                        <div class="card shadow">
                            <img src="res/icon/assignment-svg.svg" class="card-img-top" alt="icon of a boy writing" />
                            <div class="card-body">
                                <h5 class="card-title fw-bold">Give and submit assignment</h5>
                            </div>
                        </div>
                    </li>
                    
               </ul>
               
           </section>
           
        </main>
       
       
       <%@include file="res/includes/footer.html" %>
        
    </body>
    
    
</html>


