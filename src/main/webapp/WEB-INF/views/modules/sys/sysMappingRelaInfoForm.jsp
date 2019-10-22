<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>映射关系信息管理</title>
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
		<li><a href="${ctx}/sys/sysMappingRelaInfo/">映射关系信息列表</a></li>
		<li class="active"><a href="${ctx}/sys/sysMappingRelaInfo/form?id=${sysMappingRelaInfo.id}">映射关系信息<shiro:hasPermission name="sys:sysMappingRelaInfo:edit">${not empty sysMappingRelaInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysMappingRelaInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysMappingRelaInfo" action="${ctx}/sys/sysMappingRelaInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<c:choose>
		<c:when test="${sysMappingRelaInfo.id == '' || sysMappingRelaInfo.id == null}">
		<div class="control-group">
			<label class="control-label">映射类型：</label>
			<div class="controls">
				<form:input path="mappingType" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		</c:when>
		<c:otherwise>
		<div class="control-group">
			<label class="control-label">映射类型：</label>
			<div class="controls">
				<form:input path="mappingType" htmlEscape="false" maxlength="100" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		</c:otherwise>
		</c:choose>
		<div class="control-group">
			<label class="control-label">映射名称：</label>
			<div class="controls">
				<form:input path="mappingName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">映射值：</label>
			<div class="controls">
				<form:input path="mappingValue" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">映射后对应名称：</label>
			<div class="controls">
				<form:input path="mappingToName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">映射后对应值：</label>
			<div class="controls">
				<form:input path="mappingToValue" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他：</label>
			<div class="controls">
				<form:input path="mappingAttr" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysMappingRelaInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>