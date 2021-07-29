

<%@page import="learnlive.filters.PaginationFilter"%>
<%@page import="java.util.List"%>

<% List<Integer> pager = (List<Integer>) request.getAttribute("pager"); %>
<% int thePage = pager.get(0); %>
<% int dataCount = (Integer) request.getAttribute("data_count"); %>
<% int totalPages = (int)Math.ceil((double)dataCount/(double)PaginationFilter.LIMIT); %>

<% if (dataCount > 0) { %>
<div>
    <ul class="pagination pagination-md my-4">
        <% if (thePage > 1) { %>
        <li class="page-item">
            <a class="page-link text-violent" href="<%= request.getParameter("url") %>page=<%= thePage-1%>">Previous</a>
        </li>
        <% } else { %>
        <li class="page-item disabled">
            <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
        </li>
        <% } %>
        
        
        <% if (totalPages > thePage) { %>
        <li class="page-item">
            <a class="page-link text-violent" href="<%= request.getParameter("url") %>page=<%= thePage+1%>">Next</a>
        </li>
        <% } else { %>
        <li class="page-item disabled">
            <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Next</a>
        </li>
        <% } %>
    </ul>
</div>

<% } %>



