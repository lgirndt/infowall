<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Dashboards</title>
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