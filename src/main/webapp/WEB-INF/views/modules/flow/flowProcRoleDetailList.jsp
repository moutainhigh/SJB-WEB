<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程角色明细管理</title>
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
		<li class="active"><a href="${ctx}/flow/flowProcRoleDetail/">流程角色明细列表</a></li>
		<shiro:hasPermission name="flow:flowProcRoleDetail:edit"><li><a href="${ctx}/flow/flowProcRoleDetail/form?mainId=${mainId}">流程角色明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="flowProcRoleDetail" action="${ctx}/flow/flowProcRoleDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>审批人姓名：</label>
				<form:input path="userName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>审批人名称</th>
				<th>管辖机构</th>
				<th>备注信息</th>
				<shiro:hasPermission name="flow:flowProcRoleDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="flowProcRoleDetail">
			<tr>
				<td>
					${flowProcRoleDetail.userName}
				</td>
				<td>
					${flowProcRoleDetail.orgName}
				</td>
				<td>
					${flowProcRoleDetail.remarks}
				</td>
				<shiro:hasPermission name="flow:flowProcRoleDetail:edit"><td>
    				<a href="${ctx}/flow/flowProcRoleDetail/form?id=${flowProcRoleDetail.id}">修改</a>
					<a href="${ctx}/flow/flowProcRoleDetail/delete?id=${flowProcRoleDetail.id}" onclick="return confirmx('确认要删除该审批人员信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>