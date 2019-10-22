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
		<li class="active"><a href="${ctx}/oa/expensesSubInfo/">费用科目信息列表</a></li>
		<shiro:hasPermission name="oa:expensesSubInfo:edit"><li><a href="${ctx}/oa/expensesSubInfo/form">费用科目信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="expensesSubInfo" action="${ctx}/oa/expensesSubInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>科目编号：</label>
				<form:input path="subCode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label style="width: 100px;">父级科目编号：</label>
				<form:input path="parSubCode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>科目名称：</label>
				<form:input path="subName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>科目编号</th>
				<th>父级科目编号</th>
				<th>科目名称</th>
				<th>创建时间</th>
				<th>创建人</th>
				<th>更新时间</th>
				<th>更新人</th>
				<th>备注</th>
				<shiro:hasPermission name="oa:expensesSubInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="expensesSubInfo">
			<tr>
				<td><a href="${ctx}/oa/expensesSubInfo/form?id=${expensesSubInfo.id}">
					${expensesSubInfo.subCode}
				</a></td>
				<td>
					${expensesSubInfo.parSubCode}
				</td>
				<td>
					${expensesSubInfo.subName}
				</td>
				<td>
					<fmt:formatDate value="${expensesSubInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${expensesSubInfo.createBy.loginName}
				</td>
				<td>
					<fmt:formatDate value="${expensesSubInfo.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${expensesSubInfo.updateBy.loginName}
				</td>
				<td>
					${expensesSubInfo.remarks}
				</td>
				<td>
					<a onclick="addTabPage('${expensesSubInfo.subName}','${ctx}/oa/expensesSubConf/list?subCode=${expensesSubInfo.id}',null,$(this),true)" style="cursor:pointer">配置</a>
					<shiro:hasPermission name="oa:expensesSubInfo:edit"><a href="${ctx}/oa/expensesSubInfo/form?id=${expensesSubInfo.id}">修改</a></shiro:hasPermission>
					<shiro:hasPermission name="oa:expensesSubInfo:del"><a href="${ctx}/oa/expensesSubInfo/delete?id=${expensesSubInfo.id}" onclick="return confirmx('确认要删除该费用科目信息吗？', this.href)">删除</a></shiro:hasPermission>
					<c:choose>
						<c:when test="${expensesSubInfo.enable == '0'}">
							<input id="btn" class="btn btn-warning" type="button" onclick="window.location.href='${ctx}/oa/expensesSubInfo/enableChange?id=${expensesSubInfo.id}&enable=1'" value="${fns:getDictLabel(expensesSubInfo.enable, 'company_type_d', '')}"/>
						</c:when>
						<c:otherwise>
							<input id="btn" class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/oa/expensesSubInfo/enableChange?id=${expensesSubInfo.id}&enable=0'" value="${fns:getDictLabel(expensesSubInfo.enable, 'company_type_d', '')}"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>