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


    <h1>${dashboard.title}</h1>

    <p>
    <table class="table">
        <tr>
            <td>Configuration</td>
            <td><a href="<c:url value='/app/reload/dashboard/${dashboard.id}'/>">Reload</a></td>
        </tr>
    </table>
    <p>
    <table class="table table-striped table-bordered table-hover">
        <tr>
            <th>Item</th>
            <th>Cron Scheduler</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="cItem" items="${items}">
            <tr>
                <td>${cItem.item.title}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty cItem.item.scheduler}"><code>${cItem.item.scheduler}</code></c:when>
                        <c:otherwise><em>no scheduler</em></c:otherwise>
                    </c:choose>
                </td>
                <td><a href="<c:url value="/app/configure/edit/dashboard/${dashboard.id}/${cItem.item.name}"/>">Edit</a>
                    <c:if test="${cItem.executable}">| <a href="<c:url value='/app/configure/exec/dashboard/${dashboard.id}/${cItem.item.name}'/>">Execute</a></c:if></td>
            </tr>
        </c:forEach>
    </table>
    <div>
        <a href="<c:url value='/app/dashboard/'/>">Overview</a> | <a
            href="<c:url value='/app/dashboard/${dashboard.id}'/>">View</a>
    </div>
</div>
<!-- /container -->
</body>
</html>
