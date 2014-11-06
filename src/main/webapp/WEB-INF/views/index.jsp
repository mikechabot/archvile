<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
  <head>    
    <!-- meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content=".archvile.">
    
    <title>archvile</title>
    
    <!-- scripts -->
    <script src="/js/query.min.js"></script>

    <!-- styles -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
        
  </head>
  <body>
  	<div class="container">
      <div class="page-header">
        <h1>Archvile <small>Index</small></h1>
      </div>
	  
	  <div class="text-center"><a href="index/start"><button type="button" class="btn btn-default">Start</button></a></div> 	 
	 
	  <table class="table table-bordered">
	    <tr>
	  	  <th>Keyword</th>
	  	  <th>Count</th>
	  	  <th>Urls</th>
	  	</tr>
	  	<c:forEach var="entry" items="${index}">
	  	 <tr>
	  	 	  <td>${entry.value.keyword}</td>
	  	 	  <td>${entry.value.count}</td>
	  	 	  <td>
	  	 	    <ul>
				<c:forEach var="url" items="${entry.value.urls}">
				  <li>${url}</li>
				</c:forEach>
				</ul>
	  	 	  </td>
	  	 	</tr>
	  	 </c:forEach>
	  </table>
	</div>
  </body>
</html>
