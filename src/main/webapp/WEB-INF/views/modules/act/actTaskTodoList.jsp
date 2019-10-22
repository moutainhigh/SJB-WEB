<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>待办任务</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
// 			var title ="${taskInfoEntity.title}";
// 			console.log("title"+title);
// 			var url = "${ctx}/act/task/form?taskId=${task.id}&taskName=${task.taskName}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${task.status}&title=${taskInfoEntity.title}&assigneeName="+encodeURI(encodeURI(title))+"&beginDate=${taskInfoEntity.beginDate}&endDate=${taskInfoEntity.endDate}";
// 			console.log("length:"+$("a[name='taskDetail']").length);
// 			$("a[name='taskDetail']").each(function(){
// 				$(this).attr("href",url);
// 			});
		});
		/**
		 * 签收任务
		 */
		function claim(taskId) {
			$.get('${ctx}/act/task/claim' ,{taskId: taskId}, function(data) {
				if (data == 'true'){
		        	top.$.jBox.tip('签收完成');
		            location = '${ctx}/act/task/todo/';
				}else{
		        	top.$.jBox.tip('签收失败');
				}
		    });
		}
		
		function setHref(obj,ctx,id,taskName,taskDefinitionKey,processInstanceId,processDefinitionId,status,title,assigneeName,beginDate,endDate,executionId){
			var url = "javascript:addTabPage('待审批','"+ctx+"/act/task/form?taskId="+id+"&taskName="+taskName+"&taskDefKey="+taskDefinitionKey+"&procInsId="+processInstanceId+
					"&procDefId="+processDefinitionId+"&status="+status+"&title="+encodeURI(encodeURI(encodeURI(title)))+"&assigneeName="+encodeURI(encodeURI(encodeURI(assigneeName)))+"&beginDate="+beginDate+"&endDate="+endDate+"&executionId="+executionId+"',null,$(this),true)";
			
			$(obj).attr("href",url);		
		}
		
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
		<li class="active"><a href="${ctx}/act/task/todo/">待办任务</a></li>
		<li><a href="${ctx}/act/task/historic/">已办任务</a></li>
<%-- 		<li><a href="${ctx}/act/task/process/">新建任务</a></li> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="taskInfoEntity" action="${ctx}/act/task/todo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
		<br>
			<div>
			<label>标题：&nbsp;</label>
			<form:input path="title" htmlEscape="false" maxlength="300" class="input-medium"/>
			<label>创建人：&nbsp;</label>
			<form:input path="assigneeName" htmlEscape="false" maxlength="300" class="input-medium"/>
			<label>归属部门：&nbsp;</label>
			<sys:treeselect id="office" name="officeId" value="${officeId}" labelName="officeName" labelValue="${officeName}" 
				title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</div><br>
			<div>
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
				<th>到达时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="task">
<%-- 				<c:set var="task" value="${task}" /> --%>
<%-- 				<c:set var="vars" value="${vars}" /> --%>
<%-- 				<c:set var="procDef" value="${procDef}" /> --%>
<%-- 				<c:set var="procExecUrl" value="${procExecUrl}" /> --%>
<%-- 				<c:set var="status" value="${status}" /> --%>
				<tr>
					<td>
						<c:if test="${empty task.assignee}">
							<a href="javascript:claim('${task.id}');" title="签收任务">${task.title}</a>
						</c:if>
						<c:if test="${not empty task.assignee}">
<%-- 							<a id="taskDetail" href="${ctx}/act/task/form?taskId=${task.id}&taskName=${task.taskName} --%>
<%-- 							&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId} --%>
<%-- 							&procDefId=${task.processDefinitionId}&status=${task.status}&title=${taskInfoEntity.title} --%>
<%-- 							&assigneeName=${taskInfoEntity.assigneeName}&beginDate=${taskInfoEntity.beginDate}&endDate=${taskInfoEntity.endDate}">${task.title}</a> --%>
<%--  ,${ctx},${task.id},${task.taskName},${task.taskDefinitionKey},${task.processInstanceId},${task.processDefinitionId},${task.status},${taskInfoEntity.title},${taskInfoEntity.assigneeName},${taskInfoEntity.beginDate},${taskInfoEntity.endDate} --%>
								<a name="taskDetail" href="#" onclick="setHref(this,'${ctx}','${task.id}','${task.taskName}','${task.taskDefinitionKey}','${task.processInstanceId}','${task.processDefinitionId}','${task.status}','${taskInfoEntity.title}','${taskInfoEntity.assigneeName}','${taskInfoEntity.beginDate}','${taskInfoEntity.endDate}','${task.executionId}');">${task.title}</a>
						</c:if>
					</td>
					<td>
<%-- 						<a target="_blank" href="${pageContext.request.contextPath}/act/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">${task.assigneeName}${task.taskName}</a> --%>
					${task.assigneeName}${task.taskName}
					</td><%--
					<td>${description}</td> --%>
					<td>${task.procDefName}</td>
					<td><b title='流程版本号'>V: ${task.version}</b></td>
					<td><fmt:formatDate value="${task.createTime}" type="both"/></td>
					<td>
						<c:if test="${empty task.assignee}">
							<a href="javascript:claim('${id}');">签收任务</a>
						</c:if>
						<c:if test="${not empty task.assignee}"><%--
							<a href="${ctx}${procExecUrl}/exec/${taskDefinitionKey}?procInsId=${processInstanceId}&taskId=${id}">办理</a> --%>

							<a href="#" onclick="setHref(this,'${ctx}','${task.id}','${task.taskName}','${task.taskDefinitionKey}','${task.processInstanceId}','${task.processDefinitionId}','${task.status}','${taskInfoEntity.title}','${taskInfoEntity.assigneeName}','${taskInfoEntity.beginDate}','${taskInfoEntity.endDate}','${task.executionId}');">任务办理</a>

						</c:if>
						<shiro:hasPermission name="act:process:edit">
							<c:if test="${empty task.executionId}">
								<a href="${ctx}/act/task/deleteTask?taskId=${task.id}&reason=" onclick="return promptx('删除任务','删除原因',this.href);">删除任务</a>
							</c:if>
						</shiro:hasPermission>
<%-- 						<a target="_blank" href="${pageContext.request.contextPath}/act/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">跟踪</a>
						<a target="_blank" href="${ctx}/act/task/trace/photo/${processDefinitionId}/${executionId}">跟踪2</a> 
						<a target="_blank" href="${ctx}/act/task/trace/info/${processInstanceId}">跟踪信息</a> --%>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
