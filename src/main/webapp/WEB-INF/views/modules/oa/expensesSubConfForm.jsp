<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>费用科目配置管理</title>
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
		<li><a href="${ctx}/oa/expensesSubConf/?subCode=${expensesSubConf.subCode}&subName=${expensesSubConf.subName}">费用科目配置列表</a></li>
		<li class="active"><a href="${ctx}/oa/expensesSubConf/form?id=${expensesSubConf.id}&subCode=${expensesSubConf.subCode}&subName=${expensesSubConf.subName}">费用科目配置<shiro:hasPermission name="oa:expensesSubConf:edit">${not empty expensesSubConf.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:expensesSubConf:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expensesSubConf" action="${ctx}/oa/expensesSubConf/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="subCode"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">科目编号：</label>
			<div class="controls">
				<form:input path="sCode" htmlEscape="false" readonly="true" class="input-medium"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">科目名称：</label>
			<div class="controls">
				<form:input path="subName" htmlEscape="false" readonly="true" class="input-medium"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">字典类型：</label>
			<div class="controls">
				<form:select path="confType" class="input-medium required">
					<option value="">请选择</option>
					<form:options items="${fns:getDictList('conf_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配置描述：</label>
			<div class="controls">
				<form:input path="confDesc" htmlEscape="false" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否必填：</label>
			<div class="controls">
				<form:select path="isRequired" class="input-medium required">
					<option value="">请选择</option>
					<form:options items="${fns:getDictList('yes_no')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="300" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:expensesSubConf:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>