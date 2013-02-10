<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  ~ Licensed to the Apache Software Foundation (ASF) under one
  ~ or more contributor license agreements.  See the NOTICE file
  ~ distributed with this work for additional information
  ~ regarding copyright ownership.  The ASF licenses this file
  ~ to you under the Apache License, Version 2.0 (the
  ~ "License"); you may not use this file except in compliance
  ~ with the License.  You may obtain a copy of the License at
  ~
  ~  http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing,
  ~ software distributed under the License is distributed on an
  ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  ~ KIND, either express or implied.  See the License for the
  ~ specific language governing permissions and limitations
  ~ under the License.
  --%>

<html>
<head>
    <title>${dashboard.title}</title>

    <link href='http://fonts.googleapis.com/css?family=Droid+Serif' rel='stylesheet' type='text/css'>

    <link href="<c:url value='/static/css/reset.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/static/css/text.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/static/css/default.css'/>" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
    <script type="text/javascript" src="<c:url value='/static/js/jquery.mustache.js'/>"></script>

    <script type="text/javascript" src="<c:url value='/static/js/dashboard.js'/>"></script>
</head>
<script type="text/mustache" id="template-single-value">
<!--
<div class="single-value">
    <div class="current back-{{status}}">{{current}}{{unit}}</div>
    <div>
        <div class="changes"><span class="text-{{diffStatus}}">{{diff}}</span> {{since}}</div>
    </div>
</div>
-->
</script>
<script type="text/mustache" id="template-table-value">
<!--
<div class="table-value">
<table>
    {{#table}}
     <tr><td class="descr text-{{status}}">{{title}}</td><td class="value text-{{status}}">{{value}}</td></tr>
    {{/table}}
</table>
</div>
-->
</script>
<script type="text/mustache" id="template-html">
    <!--
    <div class="html-value">
    <table>
        {{{html}}}
    </table>
    </div>
    -->
</script>
<body class="dashboard">


<h1 id="title"></h1>
<div id="container">
  <div class="view"></div>
</div>
<div id="bottom">
    <div class="control-panel">
        <a href="<c:url value="/"/>">Overview</a> |
        <a href="<c:url value="/app/configure/dashboard/${dashboard.id}"/>">Configure</a>
    </div>
</div>
</body>
<script type="text/javascript">
(function($){
    $(document).ready(function(){
        var dashboard = ${json};
        var templateManager = new infowall.TemplateManager();
        var templates = templateManager.getTemplates(['single-value','table-value','html']);
        var renderEngine = new infowall.RenderEngine({
            items : dashboard.items,
            templates: templates,
            views : {
                "single-value" : new infowall.SingleValueView(),
                "table-value"  : new infowall.TableValueView(),
                "html"         : new infowall.HtmlView()
            },
            baseUrl : "<c:url value='/app/item/${dashboard.id}'/>"
        });
        var slideShow = new infowall.SlideShow({
            items : dashboard.items,
            renderEngine : renderEngine,
            container : '#container',
            fxDuration: 600,
            animation: dashboard.animation,
            delay: dashboard.delay
        });
        slideShow.start();

        $('#bottom').hover(function(){
            $('.control-panel').fadeIn();
        },function(){
            $('.control-panel').fadeOut();
        });
        $('.control-panel').delay(1200).fadeOut();
    });
})(jQuery);
</script>
</html>
