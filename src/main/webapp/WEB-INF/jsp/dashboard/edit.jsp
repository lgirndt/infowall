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
    <title>${dashboard.title} - Dashboard</title>
    <%@ include file="/WEB-INF/jsp/include/head.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/jsp/include/messages.jsp" %>

<%@ include file="/WEB-INF/jsp/include/navbar.jsp" %>

<div class="container">

    <ul class="breadcrumb">
        <li><a href="<c:url value='/app/dashboard/'/>">Home</a> <span class="divider">/</span></li>
        <li><a href="<c:url value='/app/configure/dashboard/${model.itemRef.dashboardId}'/>">${model.dashboardTitle}</a> <span class="divider">/</span></li>
        <li class="active">${model.itemTitle}</li>
    </ul>

    <form action="<c:url value="/app/configure/save/dashboard"/>" method="POST">
        <input type="hidden" name="dashboardId" value="${model.itemRef.dashboardId}">
        <input type="hidden" name="itemName" value="${model.itemRef.itemName}">
        <div>
            <label for="insert-data">Enter the item's data as JSON:</label>
        </div>
        <textarea id="insert-data" name="data" rows="10">${model.data}</textarea>
        <div>
            <button class="btn btn-primary" type="submit">Save</button>
            <a class="btn" href="<c:url value='/app/configure/dashboard/${model.itemRef.dashboardId}'/>">Cancel</a>
        </div>
    </form>

    <div>

    </div>

</div>
<!-- /container -->
</body>
</html>
