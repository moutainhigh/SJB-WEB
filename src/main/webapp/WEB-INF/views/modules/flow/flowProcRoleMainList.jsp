<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程角色管理</title>
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
		<li class="active"><a href="${ctx}/flow/flowProcRoleMain/">流程角色列表</a></li>
		<shiro:hasPermission name="flow:flowProcRoleMain:edit"><li><a href="${ctx}/flow/flowProcRoleMain/form">流程角色添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="flowProcRoleMain" action="${ctx}/flow/flowProcRoleMain/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>角色名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>角色编码：</label>
				<form:input path="code" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>流程角色名称</th>
				<th>流程角色编码</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="flow:flowProcRoleMain:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="flowProcRoleMain">
			<tr>
				<td><a href="${ctx}/flow/flowProcRoleMain/form?id=${flowProcRoleMain.id}">
					${flowProcRoleMain.name}
				</a></td>
				<td>
					${flowProcRoleMain.code}
				</td>
				<td>
					<fmt:formatDate value="${flowProcRoleMain.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${flowProcRoleMain.remarks}
				</td>
				<shiro:hasPermission name="flow:flowProcRoleMain:edit"><td>
					<a style="cursor:pointer" onclick="addTabPage('${flowProcRoleMain.name}','${ctx}/flow/flowProcRoleDetail/list?mainId=${flowProcRoleMain.id}',null,$(this),true)" >配置明细</a>
    				<a href="${ctx}/flow/flowProcRoleMain/form?id=${flowProcRoleMain.id}">修改</a>
					<a href="${ctx}/flow/flowProcRoleMain/delete?id=${flowProcRoleMain.id}" onclick="return confirmx('确认要删除该流程角色吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>