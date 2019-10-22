<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>费用科目信息管理</title>
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
		<li><a href="${ctx}/oa/expensesSubInfo/">费用科目信息列表</a></li>
		<li class="active"><a href="${ctx}/oa/expensesSubInfo/form?id=${expensesSubInfo.id}">费用科目信息<shiro:hasPermission name="oa:expensesSubInfo:edit">${not empty expensesSubInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:expensesSubInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expensesSubInfo" action="${ctx}/oa/expensesSubInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">科目编号：</label>
			<div class="controls">
				<form:input path="subCode" htmlEscape="false" maxlength="10" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级科目：</label>
			<%--<div class="controls">
				<form:input path="parSubCode" htmlEscape="false" maxlength="10" class="input-medium "/>
			</div>--%>
			<div class="controls">
				<sys:treeselect id="parSubCode" name="parSubCode"
								value="${expensesSubInfo.parSubCode}" labelName="parSubCodeName"
								labelValue="${parSubCodeName}" title="科目列表"
								url="/oa/expensesSubInfo/treeDataByCode" allowClear="true" cssClass="input-xsmall"
								notAllowSelectParent="false" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">科目名称：</label>
			<div class="controls">
				<form:input path="subName" htmlEscape="false" maxlength="10" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">天数计算方式：</label>
			<div class="controls">
				<form:select path="dayCalculation" class="input-medium required">
					<form:options items="${fns:getDictList('day_calculation')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font><font color="gray">常规：（结束日期 - 发生日期 + 1），住宿：（结束日期 - 发生日期）</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="300" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:expensesSubInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>