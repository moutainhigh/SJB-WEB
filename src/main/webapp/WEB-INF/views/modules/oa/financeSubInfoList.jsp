<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>财务科目管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {

        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        };

	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/oa/financeSubInfo/">财务科目列表</a></li>
	<li><a href="${ctx}/oa/financeSubInfo/form">财务科目添加</a></li>
</ul>
<form:form id="searchForm" modelAttribute="financeSubInfo" action="${ctx}/oa/financeSubInfo/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<ul class="ul-form">
		<li><label>关键词：</label>
			<form:input path="keyWord" placeholder="请输入科目编号/科目名称/备注/费用科目名称" htmlEscape="false" maxlength="64" class="input-medium"/>
		</li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<li class="clearfix"></li>
	</ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th width="100">科目编号</th>
		<th width="100">上级分类编号</th>
		<th width="200">科目名称</th>
		<th width="130">控制类别</th>
		<th>费用科目</th>
		<th width="130">更新时间</th>
		<th width="80">更新人名称</th>
		<th>备注</th>
		<th width="150">操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="financeSubInfo">
		<tr>
			<td width="100">${financeSubInfo.subCode}</td>
			<td>${financeSubInfo.parSubCode}</td>
			<td>${financeSubInfo.subName}</td>
			<td>${fns:getDictLabel(financeSubInfo.conType, 'con_type', '')}</td>
			<td>
				<c:if test="${fn:length(financeSubInfo.expensesSubNameStr) lt 50}" >
					${financeSubInfo.expensesSubNameStr}
				</c:if>
				<c:if test="${fn:length(financeSubInfo.expensesSubNameStr) ge 50}">
					${fn:substring(financeSubInfo.expensesSubNameStr,0,50)}...
				</c:if>
			</td>
			<td><fmt:formatDate value="${financeSubInfo.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${financeSubInfo.updateByname}</td>
			<td>${financeSubInfo.remarks}</td>
			<td>
				<shiro:hasPermission name="contract:financeSubInfo:edit">
					<input id="btnS" class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/oa/financeSubInfo/form?id=${financeSubInfo.id}'" value="编辑"/>
					</shiro:hasPermission>
				<shiro:hasPermission name="contract:financeSubInfo:del">
					&nbsp;
					<input id="btnS" class="btn btn-warning" type="button" onclick="return confirmx('确认删除${financeSubInfo.subName}科目及其子科目？删除后，将不可再找回！', '${ctx}/oa/financeSubInfo/delete?id=${financeSubInfo.id}')" value="删除"/>
					<%--<a href="${ctx}/oa/financeSubInfo/delete?id=${financeSubInfo.id}"--%>
					   <%--onclick="return confirmx('确认删除${financeSubInfo.subName}科目及其子科目？删除后，将不可再找回！', this.href)" >删除</a>--%>
				</shiro:hasPermission>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>