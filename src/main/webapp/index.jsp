<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<%
    // TODO use urlrewrite
    String url = request.getContextPath() + "/app/dashboard";
    response.sendRedirect(url);    

%>
</body>
</html>
