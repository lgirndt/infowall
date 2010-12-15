<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${dashboard.title} - Dashboard</title>
    <%@ include file="/WEB-INF/jsp/include/head.jsp" %>
</head>
<body>
<div class="navigation">
    <h1>${dashboard.title}</h1>

    <table>
        <tr>
            <td>Configuration</td>
            <td>Reload</td>
        </tr>
    </table>
    <table>
        <tr>
            <th>Item</th>
            <th>Cron Scheduler</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="item" items="${dashboard.items}">
            <tr>
                <td>${item.title}</td>
                <td>${item.scheduler}</td>
                <td>Edit <c:if test="${item.scheduler != null}">| <a href="">Execute</a></c:if></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>