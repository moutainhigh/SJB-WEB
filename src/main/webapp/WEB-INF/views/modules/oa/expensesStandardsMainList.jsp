<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>费用科目信息管理</title>
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
		<li class="active"><a href="${ctx}/oa/expensesStands/list">费用控制标准列表</a></li>
		<shiro:hasPermission name="oa:expensesStands:edit"><li><a href="${ctx}/oa/expensesStands/form">费用控制标准添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="expensesStandardsMain" action="${ctx}/oa/expensesStands/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标准名称：</label>
				<form:input path="standsName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>控制标准名称</th>
				<th>所属公司</th>
				<th>费用科目</th>
				<th>控制类别</th>
				<th>创建时间</th>
				<th>创建人</th>
				<th>更新时间</th>
				<th>更新人</th>
				<th>备注</th>
				<shiro:hasPermission name="oa:expensesStands:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="expensesStandsMain">
			<tr>
				<td><a href="${ctx}/oa/expensesStands/form?id=${expensesStandsMain.id}">
					${expensesStandsMain.standsName}
				</a></td>
				<td>
					${expensesStandsMain.officeName}
				</td>
				<td>
					${expensesStandsMain.project.subName}
				</td>
				<td>
					${expensesStandsMain.standsType}
				</td>
				<td>
					<fmt:formatDate value="${expensesStandsMain.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${expensesStandsMain.createBy.name}
				</td>
				<td>
					<fmt:formatDate value="${expensesStandsMain.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${expensesStandsMain.updateBy.name}
				</td>
				<td>
					${expensesStandsMain.remarks}
				</td>
				<shiro:hasPermission name="oa:expensesStands:edit"><td>
				    <a onclick="addTabPage('${expensesStandsMain.standsName}','${ctx}/oa/expensesStands/listDetail?standsMainId=${expensesStandsMain.id}',null,$(this),true)" >配置明细</a>
    				<a href="${ctx}/oa/expensesStands/form?id=${expensesStandsMain.id}">修改</a>
					<a href="${ctx}/oa/expensesStands/delete?id=${expensesStandsMain.id}" onclick="return confirmx('确认要删除该控制标准信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>