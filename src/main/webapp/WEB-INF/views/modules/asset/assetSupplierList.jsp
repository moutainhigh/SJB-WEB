<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>资产供应商管理</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/oa/assetSupplier/">资产供应商列表</a></li>
    <%--<shiro:hasPermission name="oa:assetSupplier:edit">--%>
    <li><a href="${ctx}/oa/assetSupplier/form">资产供应商添加</a></li>
    <%--</shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="assetSupplier" action="${ctx}/oa/assetSupplier/list" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>供应商名称：</label>
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
        <th>供应商名称</th>
        <th>联系方式</th>
        <th>地址</th>
        <th>备注</th>
        <%--<shiro:hasPermission name="oa:assetSupplier:edit">--%>
        <th>操作</th>
        <%--</shiro:hasPermission>--%>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="assetSupplier">
        <tr>
            <td>${assetSupplier.name}</td>
            <td>${assetSupplier.contactWay}</td>
            <td>${assetSupplier.address}</td>
            <td>${assetSupplier.remarks}</td>
                <%--<shiro:hasPermission name="oa:assetSupplier:edit">--%>
            <td>
                <a href="${ctx}/oa/assetSupplier/form?id=${assetSupplier.id}">编辑</a>
                <a href="${ctx}/oa/assetSupplier/delete?id=${assetSupplier.id}"
                   onclick="return confirmx('确认删除供应商${assetSupplier.name}？', this.href)">删除</a>
            </td>
                <%--</shiro:hasPermission>--%>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>