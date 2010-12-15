<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${dashboard.title} - Dashboard</title>
    <%@ include file="/WEB-INF/jsp/include/head.jsp" %>
</head>
<body>
<c:if test="${info != null}">
    <div id="info">${info}</div>
    <script type="text/javascript">
        (function($) {
            $(document).ready(function() {
                $('#info').fadeOut(0).fadeIn(800).delay(10000).fadeOut();
            });
        })(jQuery);
    </script>
</c:if>

<div class="navigation">
    <h1>${dashboard.title}</h1>

    <table>
        <tr>
            <td>Configuration</td>
            <td><a href="<c:url value='/app/reload/dashboard/${dashboard.id}'/>">Reload</a></td>
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

    <div>
        <a href="<c:url value='/app/dashboard/'/>">Back</a>
    </div>
</div>
</body>
</html>