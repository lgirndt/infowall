<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${dashboard.title}</title>
</head>
<body>
<h1>${dashboard.title}</h1>
<h2>Items</h2>
<ul>
<c:forEach var="item" items="${dashboard.items}">
    <li>${item.title}</li>
</c:forEach>
</ul>
</body>
</html>