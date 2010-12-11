<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Dashboards</title>
        <link href="${pageContext.request.contextPath}/static/css/reset.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/static/css/text.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/static/css/default.css" rel="stylesheet" type="text/css">
    </head>
    <body>
    <h1>Dashboards</h1>
    Please choose:
    <ul>
        <c:forEach var="dashboard" items="${dashboards}">
            <li><a href="<c:url value='/app/dashboard/${dashboard.id}'/>">${dashboard.title}</a></li>
        </c:forEach>
    </ul>
    </body>
</html>