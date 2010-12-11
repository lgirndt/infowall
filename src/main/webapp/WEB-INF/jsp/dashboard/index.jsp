<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${dashboard.title}</title>
    <%@ include file="/WEB-INF/jsp/include/head.jsp"%>
    <script type="text/javascript" src="<c:url value='/static/js/dashboard.js'/>"></script>
</head>
<script type="text/mustache" id="template-single-value">
<!--
Hallo Welt
-->
</script>
<body>
<script type="text/javascript">
(function($){
    $(document).ready(function(){
       console.log("hello world");
        var dashboard = ${json};
        var templateManager = new infowall.TemplateManager();
        var templates = templateManager.getTemplates(['single-value']);
        var renderEngine = new infowall.RenderEngine({
            items : dashboard.items,
            templates: templates,
            views : {
                "single-value" : new infowall.SingleValueView()
            },
            baseUrl : "<c:url value='/app/item/${dashboard.id}'/>"
        });
        renderEngine.renderItem('bugs','#dest');
    });
})(jQuery);
</script>
<h1>${dashboard.title}</h1>
<h2>Items</h2>
<ul>
<c:forEach var="item" items="${dashboard.items}">
    <li>${item.title}</li>
</c:forEach>
</ul>

<div id="dest"></div>

<a href="<c:url value='/app/dashboard'/>">Dashboards</a>
</body>
</html>