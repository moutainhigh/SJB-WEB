<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>已办任务</title>
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
		<li><a href="${ctx}/act/task/todo/">待办任务</a></li>
		<li class="active"><a href="${ctx}/act/task/historic/">已办任务</a></li>
<%-- 		<li><a href="${ctx}/act/task/process/">新建任务</a></li> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="taskInfoEntity" action="${ctx}/act/task/historic/" method="get" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
		<br>
			<label>标题：&nbsp;</label>
			<form:input path="title" htmlEscape="false" maxlength="300" class="input-medium"/>
			<label>创建人：&nbsp;</label>
			<form:input path="assigneeName" htmlEscape="false" maxlength="300" class="input-medium"/>
			<label>归属部门：&nbsp;</label>
			<sys:treeselect id="office" name="officeId" value="${officeId}" labelName="officeName" labelValue="${officeName}" 
				title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			<br/><br/>
			<label>到达时间：</label>
			<input id="beginDate"  name="beginDate"  htmlEscape="false"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				value="<fmt:formatDate value="${beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd'});"/>
				　--　
			<input id="endDate" name="endDate"  htmlEscape="false" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({{minDate:'#F{$dp.$D(\'beginDate\')}',dateFmt:'yyyy-MM-dd'});"/>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>当前环节</th><%--
				<th>任务内容</th> --%>
				<th>流程名称</th>
				<th>流程版本</th>
				<th>完成时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="task">
<%-- 				<c:set var="task" value="${act.histTask}" /> --%>
<%-- 				<c:set var="vars" value="${act.vars}" /> --%>
<%-- 				<c:set var="procDef" value="${act.procDef}" /> --%>
				<%--<c:set var="procExecUrl" value="${act.procExecUrl}" /> --%>
<%-- 				<c:set var="status" value="${act.status}" /> --%>
				<tr>
					<td>
						<a href="javascript:addTabPage('已审批','${ctx}/act/task/form?businessId=${task.businessId}&taskId=${task.id}&taskName=${task.taskName}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${task.status}&executionId=${task.executionId}',null,$(this),true);">${task.title}</a>
					</td>
					<td>
<%-- 						<a target="_blank" href="${pageContext.request.contextPath}/act/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">${task.taskName}</a> --%>
							${task.taskName}
					</td>
					<td>${task.procDefName}</td>
					<td><b title='流程版本号'>V: ${task.version}</b></td>
					<td><fmt:formatDate value="${task.completedTime}" type="both"/></td>
					<td>
						<a href="javascript:addTabPage('已审批','${ctx}/act/task/form?businessId=${task.businessId}&taskId=${task.id}&taskName=${task.taskName}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${task.status}',null,$(this),true);">详情</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
