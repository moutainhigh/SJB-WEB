<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同信息管理</title>
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
		<li><a href="${ctx}/oa/contractInfoBack/">合同信息列表</a></li>
		<li class="active"><a href="${ctx}/oa/contractInfoBack/form?id=${contractInfoBack.id}">合同信息<shiro:hasPermission name="oa:contractInfoBack:edit">${not empty contractInfoBack.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:contractInfoBack:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="contractInfoBack" action="${ctx}/oa/contractInfoBack/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<table class="table-form">
		<tr>
			<td>
			<div class="control-group">
				<label class="control-label">公司名称：</label>
				<div class="controls">
					<form:input path="companyName" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			</td>
			<td>
			<div class="control-group">
				<label class="control-label">公司缩写：</label>
				<div class="controls">
					<form:input path="abbreviation" onKeyUp="value=value.replace(/[^A-Z/-]/g,'')" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font></span>
				</div>
			</div>
			</td>
			
		</tr>
		<tr>
			<td>
			<div class="control-group" >
				<label class="control-label">统一社会信用代码：</label>
				<div class="controls">
					<form:input path="creditCode" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			</td>
			<td>	
			<div class="control-group">
				<label class="control-label">住址：</label>
				<div class="controls">
					<form:input path="address" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			</td>
			
		</tr>	
		<tr>
			<td colspan="2">
			<div class="control-group">
				<label class="control-label">法定代表人：</label>
				<div class="controls">
					<form:input path="legalRepresentative" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			</td>
		</tr>
		</table>	
		<div class="form-actions">
			<shiro:hasPermission name="oa:contractInfoBack:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>