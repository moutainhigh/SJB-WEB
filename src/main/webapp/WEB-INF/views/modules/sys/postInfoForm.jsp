<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<title>岗位信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
$(document).ready(function() {
	$("#name").focus();
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
			}else if(element.is(":input")){
				error.appendTo(element.parent());
			}else {
				error.insertAfter(element);
			}
		}
	});
});

	
   
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/post/">岗位信息列表</a></li>
		<li class="active"><a
			href="${ctx}/sys/post/form?id=${postInfo.id}">岗位信息<shiro:hasPermission
					name="oa:postInfo:edit">${not empty postInfo.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="oa:postInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="postInfo"
		action="${ctx}/sys/post/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="oldPostCode" value="${postCode}"/>
		<sys:message content="${message}" />
		
		<div class="control-group">
			<label class="control-label">岗位编号：</label>
			<div class="controls">
				<form:input path="postCode" htmlEscape="false" maxlength="10"
					class="input-medium required" placeholder="GW"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">岗位名称：</label>
			<div class="controls">
				<form:input path="postName" htmlEscape="false" maxlength="10"
					class="input-medium required" />
					<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级岗位名称：</label>
			<div class="controls">
				<form:select path="parentId" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${items}" itemLabel="postName" itemValue="id" htmlEscape="false"/>
				</form:select>				
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">创建者：</label>
			<div class="controls">
				${post.getCreateByName() }
				<form:hidden path="createByName" value = "${post.getCreateByName() }" htmlEscape="false" maxlength="10"
					class="input-medium required" />
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				${post.getCreateDateTime() }
				<form:hidden path="createDateTime" value = "${post.getCreateDateTime() }" htmlEscape="false" maxlength="10"
					class="input-medium required" />
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">更新者：</label>
			<div class="controls">
				${fns:getUser().name}
				<form:hidden path="updataByName" value="${fns:getUser().name}" htmlEscape="false" maxlength="10"
					class="input-medium required" />
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">更新时间：</label>
			<div class="controls">
				${newTime }
				<form:hidden path="updataTime" value="${newTime }" htmlEscape="false" maxlength="10"
					class="input-medium required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">岗位说明：</label>
			<div class="controls">
				
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="300" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<%-- <shiro:hasPermission name="oa:postInfo:edit"> --%>
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;<%-- </shiro:hasPermission> --%>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>