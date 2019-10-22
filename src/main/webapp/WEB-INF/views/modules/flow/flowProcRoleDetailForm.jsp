<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程角色明细管理</title>
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
		<li><a href="${ctx}/flow/flowProcRoleDetail?mainId=${flowProcRoleDetail.mainId}">流程角色明细列表</a></li>
		<li class="active"><a href="${ctx}/flow/flowProcRoleDetail/form?id=${flowProcRoleDetail.id}">流程角色明细<shiro:hasPermission name="flow:flowProcRoleDetail:edit">${not empty flowProcRoleDetail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="flow:flowProcRoleDetail:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="flowProcRoleDetail" action="${ctx}/flow/flowProcRoleDetail/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="mainId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">审批人员：</label>
			<div class="controls">
				<sys:treeselect id="userId" name="userId" value="${flowProcRoleDetail.userId}" labelName="userName" labelValue="${flowProcRoleDetail.userName}"
					title="用户" url="/sys/office/treeData?type=3&exclusionExOffice=true" includeExEmployee="false" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">管辖机构编码：</label>
			<div class="controls">
				<sys:treeselect id="orgId" name="orgId" value="${flowProcRoleDetail.orgId}" labelName="orgName" labelValue="${flowProcRoleDetail.	orgName}" 
				title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="300" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="flow:flowProcRoleDetail:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>