<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程机构管理</title>
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
		<li class="active"><a href="${ctx}/flow/flowProcOrg/">流程机构列表</a></li>
		<shiro:hasPermission name="flow:flowProcOrg:edit"><li><a href="${ctx}/flow/flowProcOrg/form">流程机构添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="flowProcOrg" action="${ctx}/flow/flowProcOrg/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
<!-- 		<ul class="ul-form"> -->
<!-- 			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->
<!-- 			<li class="clearfix"></li> -->
<!-- 		</ul> -->
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>流程编码</th>
				<th>机构名称</th>
				<th>单据类型</th>
				<th>备注信息</th>
				<shiro:hasPermission name="flow:flowProcOrg:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="flowProcOrg">
			<tr>
				<td><a href="${ctx}/flow/flowProcOrg/form?id=${flowProcOrg.id}">
					${flowProcOrg.procCode}
				</a></td>
				<td>
					${flowProcOrg.orgName}
				</td>
				<td>
					${flowProcOrg.billTypeName}
				</td>
				<td>
					${flowProcOrg.remarks}
				</td>
				<shiro:hasPermission name="flow:flowProcOrg:edit"><td>
    				<a href="${ctx}/flow/flowProcOrg/form?id=${flowProcOrg.id}">修改</a>
					<a href="${ctx}/flow/flowProcOrg/delete?id=${flowProcOrg.id}" onclick="return confirmx('确认要删除该流程机构吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>