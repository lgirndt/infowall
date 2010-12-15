<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>${dashboard.title} - Dashboard</title>
        <%@ include file="/WEB-INF/jsp/include/head.jsp"%>
    </head>
    <body>
    <div class="navigation">
    <h1>${dashboard.title}</h1>
    <table>
        <%--
        <c:forEach var="dashboard" items="${dashboards}">
            <tr><td><a href="<c:url value='/app/dashboard/${dashboard.id}'/>">${dashboard.title}</a></td>
                <td>Edit</td></tr>
        </c:forEach>
        --%>
    </table>
    </div>
    </body>
</html>