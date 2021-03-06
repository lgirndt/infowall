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
    <title>Dashboards</title>
    <%@ include file="/WEB-INF/jsp/include/head.jsp" %>
</head>
<body>

<%@ include file="/WEB-INF/jsp/include/navbar.jsp" %>

<div class="container">

    <ul class="breadcrumb">
        <li class="active">Home</li>
    </ul>


    <h1>Dashboards</h1>
    <table class="table table-striped table-bordered table-hover">
        <c:forEach var="dashboard" items="${dashboards}">
            <tr>
                <td>${dashboard.title}</td>
                <td >
                    <a class="btn btn-mini btn-primary"  href="<c:url value='/app/dashboard/${dashboard.id}'/>">View</a>
                    <a class="btn btn-mini" href="<c:url value='/app/configure/dashboard/${dashboard.id}'/>">Configure</a>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>
<!-- /container -->

</body>
</html>
