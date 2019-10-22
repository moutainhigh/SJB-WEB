<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>资产类别管理</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/oa/assetType/">资产类别列表</a></li>
    <%--<shiro:hasPermission name="oa:assetType:edit">--%>
    <li><a href="${ctx}/oa/assetType/form">资产类别添加</a></li>
    <%--</shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="assetType" action="${ctx}/oa/assetType/list" method="post"
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
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>分类编号</th>
        <th>上级分类编号</th>
        <th>分类名称</th>
        <th>计量单位</th>
        <th>备注</th>
        <%--<shiro:hasPermission name="oa:assetType:edit">--%>
        <th>操作</th>
        <%--</shiro:hasPermission>--%>
    </tr>

    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="assetType">
        <tr>
            <td>${assetType.code}</td>
            <td>${assetType.parentCode}</td>
            <td>${assetType.name}</td>
            <td>${assetType.unit}</td>
            <td>${assetType.remarks}</td>
                <%--<shiro:hasPermission name="oa:assetType:edit">--%>
            <td>
                <a href="${ctx}/oa/assetType/form?id=${assetType.id}">编辑</a>
                <a href="${ctx}/oa/assetType/delete?id=${assetType.id}"
                   onclick="return confirmx('确认删除类别${assetType.name}？', this.href)">删除</a>
            </td>
                <%--</shiro:hasPermission>--%>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>