<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/taskAllocation/list">任务管理</a></li>
		<li class="active"><a href="${ctx}/oa/taskAllocation/form?id=${taskAllocation.id}"><shiro:hasPermission name="oa:task:saveOrUpdate">${not empty taskAllocation.id?'修改':'添加'}</shiro:hasPermission>任务</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="taskAllocation" action="${ctx}/oa/taskAllocation/saveOrUpdate" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="taskIntfzAddress.id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">任务名称:</label>
			<div class="controls">
				<form:input path="taskName" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任务类型:</label>
			<div class="controls">
				<form:input path="taskType" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">job工作名:</label>
			<div class="controls">
				<form:input path="jobName" htmlEscape="false" maxlength="50" class="required" placeholder="建议为英文"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类全名:</label>
			<div class="controls">
				<form:input path="classPath" htmlEscape="false" maxlength="256" class="required input-xxlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任务开始时间:</label>
			<div class="controls">
				<input id="beginTime" name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-large Wdate"
					value="<fmt:formatDate value="${taskAllocation.beginTime}" pattern="yyyy-MM-dd HH:mm:00"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任务结束时间:</label>
			<div class="controls">
				<input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" class="input-large Wdate"
					value="<fmt:formatDate value="${taskAllocation.endTime}" pattern="yyyy-MM-dd HH:mm:00"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">间隔时间:</label>
			<div class="controls">
				<form:input path="intervalTime" htmlEscape="false" maxlength="50" class="required"/> &nbsp;单位:秒  一天的秒数:86400
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">mac:</label>
			<div class="controls">
				<form:select path="mac" cssClass="input-large">
					<form:options items="${fns:getDictList('dict_mac')}" itemLabel="value" itemValue="label" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否开启mac验证:</label>
			<div class="controls">
				<form:radiobuttons path="isMacVaild" items="${fns:getDictList('yes_no')}"  itemLabel="label" itemValue="value" htmlEscape="false" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回调ip:</label>
			<div class="controls">
				<form:input path="taskIntfzAddress.ip" htmlEscape="false" maxlength="50" class="required" placeholder="ip端口与域名只需要填写一种"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回调端口:</label>
			<div class="controls">
				<form:input path="taskIntfzAddress.port" htmlEscape="false" maxlength="50" class="required" placeholder="ip端口与域名只需要填写一种"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回调域名:</label>
			<div class="controls">
				<form:input path="taskIntfzAddress.domainName" htmlEscape="false" maxlength="50" class="required" placeholder="ip端口与域名只需要填写一种"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回调工程名:</label>
			<div class="controls">
				<form:input path="taskIntfzAddress.workName" htmlEscape="false" maxlength="50" class="required" placeholder="建议为英文"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回调接口名:</label>
			<div class="controls">
				<form:input path="taskIntfzAddress.intfzName" htmlEscape="false" maxlength="50" class="required" placeholder="建议为英文"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:task:list">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>