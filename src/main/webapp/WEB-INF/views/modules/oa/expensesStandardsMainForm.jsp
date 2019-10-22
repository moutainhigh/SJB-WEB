<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>费用控制标准管理</title>
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
		<li><a href="${ctx}/oa/expensesStands/list">费用控制标准列表</a></li>
		<li class="active"><a href="${ctx}/oa/expensesStands/form?id=${expensesStandsMain.id}">费用控制标准<shiro:hasPermission name="oa:expensesStands:edit">${not empty expensesStandsMain.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:expensesStands:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expensesStandsMain" action="${ctx}/oa/expensesStands/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">控制标准名称：</label>
			<div class="controls">
				<form:input path="standsName" htmlEscape="false" maxlength="10" class="input-medium required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属部门：</label>
			<div class="controls">
                <sys:treeselect id="office" name="officeId" value="${expensesStandsMain.officeId}" labelName="office.name" labelValue="${expensesStandsMain.officeName}"
					title="公司" url="/sys/office/treeData?type=4" cssClass="required" notAllowSelectParent="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">费用科目：</label>
			<div class="controls">
                <sys:treeselect id="project" name="project.id" value="${expensesStandsMain.project.id}" labelName="project.subName" labelValue="${expensesStandsMain.project.subName}"
					title="科目" url="/oa/expensesSubInfo/treeData" cssClass="required" notAllowSelectParent="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">控制类别：</label>
			<div class="controls">
				<form:select path="standsTypes" class="input-medium" multiple="true" >
<%-- 					<option value="">请选择${expensesStandsMain.standsTypes }</option> --%>
					<form:options items="${fns:getDictList('oa_standsType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">控制策略：</label>
			<div class="controls">
				<form:select path="controlStrategy" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('oa_expense_controlStrategy')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="300" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:expensesStands:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>