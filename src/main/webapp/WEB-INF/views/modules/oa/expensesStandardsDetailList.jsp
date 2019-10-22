<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>费用控制标准明细管理</title>
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
		<li class="active"><a href="${ctx}/oa/expensesStands/listDetail?standsMainId=${standsMainId}">费用控制标准明细列表</a></li>
		<shiro:hasPermission name="oa:expensesStands:edit"><li><a href="${ctx}/oa/expensesStands/formDetail?standsMainId=${standsMainId}">费用控制标准管理明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="expensesStandsDetail" action="${ctx}/oa/expensesStands/listDetail" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
<!-- 			<li><label>控制标准名称：</label> -->
<%-- 				<form:input path="standsName" htmlEscape="false" maxlength="50" class="input-medium"/> --%>
<!-- 			</li> -->
<!-- 			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->
<!-- 			<li class="clearfix"></li> -->
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>控制类别</th>
				<th>金额</th>
				<th>金额单位</th>
				<th>所属岗位</th>
				<th>城市类别</th>
				<th>交通方式</th>
				<shiro:hasPermission name="oa:expensesStands:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="expensesStandsDetail">
			<tr>
				<td><a href="${ctx}/oa/expensesStands/formDetail?id=${expensesStandsDetail.id}&standsMainId=${standsMainId}">
					${expensesStandsDetail.standsType}
				</a></td>
				<td>
					${expensesStandsDetail.standsAmount}
				</td>
				<td>
					${expensesStandsDetail.amountUnit}
				</td>
				<td>
					${expensesStandsDetail.postCode}
				</td>
				<td>
					${expensesStandsDetail.cityType}
				</td>
				<td>
					${expensesStandsDetail.transMode}
				</td>
				<shiro:hasPermission name="oa:expensesStands:edit"><td>
    				<a href="${ctx}/oa/expensesStands/formDetail?id=${expensesStandsDetail.id}&standsMainId=${standsMainId}">修改</a>
					<a href="${ctx}/oa/expensesStands/deleteDetail?id=${expensesStandsDetail.id}&standsMainId=${standsMainId}" onclick="return confirmx('确认要删除该控制标准明细信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>