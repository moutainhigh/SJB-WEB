<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户信息管理</title>
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
		<li class="active"><a href="${ctx}/oa/custInfo/">客户信息列表</a></li>
		<shiro:hasPermission name="oa:custInfo:edit"><li><a href="${ctx}/oa/custInfo/form">客户信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="custInfo" action="${ctx}/oa/custInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>客户编号：</label>
				<form:input path="custCode" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>客户名称：</label>
				<form:input path="custName" htmlEscape="false" maxlength="90" class="input-medium"/>
			</li>
			<li><label>客户简称：</label>
				<form:input path="custAbbreviation" htmlEscape="false" maxlength="90" class="input-medium"/>
			</li>
			<li><label>客户类别：</label>
				<form:input path="custType" htmlEscape="false" maxlength="8" class="input-medium"/>
			</li>
			<li><label>客户阶段：</label>
				<form:input path="custStage" htmlEscape="false" maxlength="8" class="input-medium"/>
			</li>
			<li><label>市场负责人：</label>
				<form:input path="marketLeader.id" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>所属行业：</label>
				<form:input path="custTrades" htmlEscape="false" maxlength="8" class="input-medium"/>
			</li>
			<li><label>业务类型：</label>
				<form:input path="custBusinessType" htmlEscape="false" maxlength="8" class="input-medium"/>
			</li>
			<li><label>规模：</label>
				<form:input path="custCompanySize" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>备注</th>
				<shiro:hasPermission name="oa:custInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="custInfo">
			<tr>
				<td><a href="${ctx}/oa/custInfo/form?id=${custInfo.id}">
					${custInfo.remarks}
				</a></td>
				<shiro:hasPermission name="oa:custInfo:edit"><td>
    				<a href="${ctx}/oa/custInfo/form?id=${custInfo.id}">修改</a>
					<a href="${ctx}/oa/custInfo/delete?id=${custInfo.id}" onclick="return confirmx('确认要删除该单表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>