<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${dashboard.title}</title>
    <%@ include file="/WEB-INF/jsp/include/head.jsp"%>
    <script type="text/javascript" src="<c:url value='/static/js/dashboard.js'/>"></script>
</head>
<script type="text/mustache" id="template-single-value">
<!--
<div>
<h2>{{title}}</div>
<div>{{current}}</div>
<div>{{previous}}</div>
</div>
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
        var slideShow = new infowall.SlideShow({
            items : dashboard.items,
            renderEngine : renderEngine,
            container : '#container'
        });
        slideShow.start();

        console.log('body:' +  $('body').innerHeight());
        console.log('title' + $('#title').height());
        $('#container').height(
            $('body').innerHeight() - $('#container').offset().top
        )

    });
})(jQuery);
</script>
<h1 id="title">Hello World</h1>
<div id="container">
  <div class="view">tick tick tick ...</div>
</div>

</body>
</html>