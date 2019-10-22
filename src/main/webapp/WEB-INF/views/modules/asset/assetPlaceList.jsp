<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>资产放置地管理</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/oa/assetPlace/">资产放置地列表</a></li>
    <%--<shiro:hasPermission name="oa:assetPlace:edit">--%>
    <li><a href="${ctx}/oa/assetPlace/form">资产放置地添加</a></li>
    <%--</shiro:hasPermission>--%>
</ul>
<%--<form:form id="searchForm" modelAttribute="assetPlace" action="${ctx}/oa/assetPlace/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>分类名称：</label>
            <form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>--%>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>位置名称</th>
        <th>备注</th>
        <%--<shiro:hasPermission name="oa:assetPlace:edit">--%>
        <th>操作</th>
        <%--</shiro:hasPermission>--%>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="assetPlace">
        <tr>
            <td>${assetPlace.name}</td>
            <td>${assetPlace.remarks}</td>
                <%--<shiro:hasPermission name="oa:assetPlace:edit">--%>
            <td>
                <a href="${ctx}/oa/assetPlace/form?id=${assetPlace.id}">编辑</a>
                <a href="${ctx}/oa/assetPlace/delete?id=${assetPlace.id}"
                   onclick="return confirmx('确认删除放置地${assetPlace.name}？', this.href)">删除</a>
            </td>
                <%--</shiro:hasPermission>--%>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>