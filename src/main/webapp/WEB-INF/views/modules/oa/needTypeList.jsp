<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>协作类型管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oa/needType/">协作类型管理列表</a></li>
		<shiro:hasPermission name="oa:needType:edit"><li><a href="${ctx}/oa/needType/form">协作类型管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="needType" action="${ctx}/oa/needType/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标签名称：</label>
				<form:input path="typeName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>协作类型编号</th>
				<th>类型名称</th>
				<th>默认进度状态</th>
				<th>模板类型</th>
				<shiro:hasPermission name="oa:needType:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="needType">
			<tr>
			
				<td>${needType.typeCode}</td>
				<td>${needType.typeName}</td>
				<td>${needType.isSelectedName}</td>
				<td>${needType.flag == "0"?"非系统模板":"系统模板"}</td>
				<shiro:hasPermission name="oa:needType:edit"><td>
				<input id="btnS" class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/oa/needType/form?id=${needType.id}'" value="编辑"/>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>