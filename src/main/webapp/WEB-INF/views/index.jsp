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
    <script src="/js/jquery.min.js"></script>
    <script src="/js/underscore-min.js"></script>
    <script src="/js/custom.js"></script>
    <!-- styles -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">
  </head>
  <body>
    <div class="container">
      <div class="page-header">
        <h1>archvile <small>custom web crawler</small></h1>
      </div>
      <div class="row">
        <div class="col-lg-8 col-md-7 col-sm-6">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h3>Controls</h3>
            </div>
            <div class="panel-body">
              <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12">
                  <c:choose>
                    <c:when test="${isRunning == true}">
                      <form class="form" role="form" method="post" action="/index/stop">
                      	<div class="form-group">
                          <input class="form-control input-lg" type="text" value="${searchTerms}" disabled>
                        </div>
                        <div class="form-group">
                          <div class="input-group">
                            <div class="input-group-addon"><b>http://</b></div>
                            <input class="form-control input-lg" type="text" value="${seedUrl}" disabled>
                            <div class="input-group-addon"><button type="submit" class="glyphicon glyphicon-stop btn btn-sm btn-danger"></button></div>
                          </div>
                        </div>
                      </form>
                    </c:when>
                    <c:otherwise>
                      <form class="form" role="form" method="post" onsubmit="return validate()" action="/index/start">
	                    <div class="form-group">
	                     <label class="control-label" for="searchTerms"></label>
                          <input class="form-control input-lg validate" id="searchTerms" name="searchTerms" type="text" placeholder="Enter search terms">
                        </div>
                        <div class="form-group">
                         <label class="control-label" for="seedUrl"></label>
                          <div class="input-group">
                            <div class="input-group-addon"><b>http://</b></div>
                            <input class="form-control input-lg validate" id="seedUrl" name="seedUrl" type="text" placeholder="www.example.com">
                            <div class="input-group-addon"><button type="submit" class="glyphicon glyphicon-play btn btn-sm btn-primary"></button></div>
                          </div>
                        </div>
                      </form>
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-md-5 col-sm-6">
          <div class="panel panel-default">
            <div class="panel-heading">
              <table style="width: 100%;">
              	<tr>
              	 <td><h3>Statistics </h3></td>
              	 <td class="text-right"><a href="/"><button class="glyphicon glyphicon-refresh btn btn-lg btn-default"></button></a></td>
              	</tr>
              </table>
            </div>
            <table class="table table-statistics">
              <tr>
                <th>Last Run Date:</th>
                <td>
                  <c:choose>
                    <c:when test="${empty lastRunDate}">
                      None
                    </c:when>
                    <c:otherwise>
                      <c:out value="${lastRunDate}"/>
                    </c:otherwise>
                  </c:choose>
                </td>
              </tr>
              <tr>
                <th>Last Run Duration:</th>
                <td>
                  <c:choose>
                    <c:when test="${empty lastRunDuration}">
                      None
                    </c:when>
                    <c:otherwise>
                      <c:out value="${lastRunDuration}"/>
                    </c:otherwise>
                  </c:choose>
                </td>
              </tr>
              <tr>
                <th>Current Run Duration:</th>
                <td>
                  <c:choose>
                    <c:when test="${empty currentRunDuration}">
                      None
                    </c:when>
                    <c:otherwise>
                      <c:out value="${currentRunDuration}"/>
                    </c:otherwise>
                  </c:choose>
                </td>
              </tr>
              <tr>
                <th>Seed URL:</th>
                <td>
                  <c:choose>
                    <c:when test="${empty seedUrl}">
                      None
                    </c:when>
                    <c:otherwise>
                      <c:out value="${seedUrl}"/>
                    </c:otherwise>
                  </c:choose>
                </td>
              </tr>
              <tr>
                <th>Pages viewed:</th>
                <td><c:out value="${pagesViewed}"/></td>
              </tr>
              <tr>
                <th>Index size:</th>
                <td>
                  <c:choose>
                    <c:when test="${empty index}">
                      0
                    </c:when>
                    <c:otherwise>
                      <c:out value="${fn:length(index)}"/>
                    </c:otherwise>
                  </c:choose>
                </td>
              </tr>
              <tr>
                <th>URLs in queue:</th>
                <td>
                  <c:out value="${urls}"/>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3>Index</h3>
        </div>
        <table class="table table-bordered table-striped table-hover">
          <tr>
            <th>Keyword</th>
            <th>Count</th>
            <th>Urls</th>
          </tr>
          <c:choose>
            <c:when test="${not empty index}">
              <c:forEach var="entry" items="${index}">
                <tr>
                  <td>${entry.value.keyword}</td>
                  <td>${entry.value.count}</td>
                  <td>
                    <ul>
                      <c:forEach var="url" items="${entry.value.urls}">
                        <li><a href="${url}">${url}</a></li>
                      </c:forEach>
                    </ul>
                  </td>
                </tr>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <tr>
                <td colspan="3" class="text-center"><em>No entries</em></td>
              </tr>
            </c:otherwise>
          </c:choose>
        </table>
      </div>
    </div>
  </body>
</html>