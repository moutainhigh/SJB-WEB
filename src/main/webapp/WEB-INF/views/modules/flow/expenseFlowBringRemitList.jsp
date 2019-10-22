<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提前打款管理</title>
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
	<form:form id="searchForm" modelAttribute="expenseFlow" action="${ctx}/flow/expenseFlow/bringRemitList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>流程编号：</label>
				<form:input path="procCode" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>流程名称：</label>
				<form:input path="procName" htmlEscape="false" maxlength="300" class="input-medium"/>
			</li>
			<li><label>申请人名称：</label>
				<form:input path="applyPerName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>申请时间：</label>
				<input id="beginApplyTime" name="beginApplyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${expenseFlow.beginApplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endApplyTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endApplyTime" name="endApplyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${expenseFlow.endApplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginApplyTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>项目名称：</label>
				<form:select path="projectId" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${projectInfoList}"  itemLabel="projectName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>报销状态：</label>
				<form:select path="expenseStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('expense_status')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>流程编号</th>
				<th>流程名称</th>
				<th>申请人名称</th>
				<th>申请日期</th>
				<th>费用合计</th>
				<shiro:hasPermission name="flow:expenseFlow:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="expenseFlow">
			<tr>
				<td><a href="${ctx}/flow/expenseFlow/bringRemitForm?id=${expenseFlow.id}">
					${expenseFlow.procCode}
				</a></td>
				<td>
					${expenseFlow.procName}
				</td>
				<td>
					${expenseFlow.applyPerName}
				</td>
				<td>
					<fmt:formatDate value="${expenseFlow.applyTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${expenseFlow.expenseTotal}
				</td>
				<shiro:hasPermission name="flow:expenseFlow:edit"><td>
    				<a href="${ctx}/flow/expenseFlow/bringRemitForm?id=${expenseFlow.id}">详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>