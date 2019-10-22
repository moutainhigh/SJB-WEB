<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同公司信息管理</title>
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
		<li><a href="${ctx}/flow/contractCompanyInfo/">合同公司信息列表</a></li>
		<li class="active"><a href="${ctx}/flow/contractCompanyInfo/form?id=${contractCompanyInfo.id}">合同公司信息<shiro:hasPermission name="flow:contractCompanyInfo:edit">${not empty contractCompanyInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="flow:contractCompanyInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="contractCompanyInfo" action="${ctx}/flow/contractCompanyInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="controls">
				<input id="oldContractName" name="oldContractName" type="hidden"
					value="${contractCompanyInfo.contractName}">
		</div>
		
		<div class="control-group">
			<label class="control-label">合同名称：</label>
			<div class="controls">
				<form:input path="contractName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">乙方名称：</label>
			<div class="controls">
				<form:select path="secondPartyName" class="input-xlarge required">
					<option value="">---请选择---</option>
					<form:options items="${fns:getDictList('second_party_name')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">乙方统一社会信用代码：</label>
			<div class="controls">
				<form:input path="secondCreditCode" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">乙方住所：</label>
			<div class="controls">
				<form:input path="secondAddress" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">乙方法定代表人：</label>
			<div class="controls">
				<form:input path="secondLegalRepresentative" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">乙方联系人：</label>
			<div class="controls">
				<form:input path="secondLinkman" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">乙方联系人电话：</label>
			<div class="controls">
				<form:input path="secondLinkmanPhone" htmlEscape="false" maxlength="16" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">乙方联系人邮箱：</label>
			<div class="controls">
				<form:input path="secondLinkmanMail" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效期：</label>
			<div class="controls">
				<form:input path="validityTime" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">合同类型：</label>
			<div class="controls">
				<form:select path="contractTypeKey" class="input-xlarge required">
							<option value="">---请选择---</option>
 							<form:options items="${fns:getDictList('contract_type')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">违约金比例：</label>
			<div class="controls">
				<form:input path="penaltyProportion" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="300" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="flow:contractCompanyInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>