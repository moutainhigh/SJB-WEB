<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>职级管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/jobLevel/">职级列表</a></li>
		<li class="active"><a href="${ctx}/sys/jobLevel/form?id=${jobLevel.id}">职级<shiro:hasPermission name="sys:jobLevel:edit">${not empty jobLevel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:jobLevel:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="jobLevel" action="${ctx}/sys/jobLevel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">职级编号：</label>
			<div class="controls">
				<form:input path="levelCode" htmlEscape="false" maxlength="255" class="input-medium required" placeholder="ZJ"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职级名称：</label>
			<div class="controls">
				<form:input path="levelName" htmlEscape="false" maxlength="255" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职级说明：</label>
			<div class="controls">
				<form:input path="description" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<%--<shiro:hasPermission name="sys:jobLevel:edit">--%><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;<%--</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>