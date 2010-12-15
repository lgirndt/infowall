<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Dashboards</title>
        <%@ include file="/WEB-INF/jsp/include/head.jsp"%>
    </head>
    <body>
    <div class="navigation">
    <h1>Dashboards</h1>
    <table>
        <c:forEach var="dashboard" items="${dashboards}">
            <tr>
                <td><a href="<c:url value='/app/dashboard/${dashboard.id}'/>">${dashboard.title}</a></td>
                <td><a href="<c:url value='/app/configure/dashboard/${dashboard.id}'/>">Configure</a></td>
            </tr>
        </c:forEach>
    </table>
    </div>
    </body>
</html>