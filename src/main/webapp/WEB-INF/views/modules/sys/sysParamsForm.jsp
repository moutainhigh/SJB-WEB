<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统参数信息管理</title>
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
		<li><a href="${ctx}/sys/sysParams/">系统参数信息列表</a></li>
		<li class="active"><a href="${ctx}/sys/sysParams/form?id=${sysParams.id}">系统参数信息<shiro:hasPermission name="sys:sysParams:edit">${not empty sysParams.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysParams:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysParams" action="${ctx}/sys/sysParams/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">参数名：</label>
			<div class="controls">
				<form:input path="paramName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参数值：</label>
			<div class="controls">
				<form:textarea path="paramValue" htmlEscape="false" maxlength="255" rows="5" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参数类型：</label>
			<div class="controls">
				<form:select path="paramType" class="input-xlarge " >
					<option value="">请选择</option>
					<form:options items="${fns:getDictList('param_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remarks" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可修改：</label>
			<div class="controls">
				<form:select path="isModify" class="input-xlarge " >
					<option value="">请选择</option>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysParams:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>