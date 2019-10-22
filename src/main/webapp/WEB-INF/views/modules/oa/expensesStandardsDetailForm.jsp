<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>费用控制标准明细管理</title>
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
		<li><a href="${ctx}/oa/expensesStands/listDetail?standsMainId=${expensesStandardsDetail.standsMainId}">费用控制标准明细列表</a></li>
		<li class="active"><a href="${ctx}/oa/expensesStands/formDetail?id=${expensesStandardsDetail.id}&standsMainId=${expensesStandardsDetail.standsMainId}">费用控制标准明细<shiro:hasPermission name="oa:expensesStands:edit">${not empty expensesStandardsDetail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:expensesStands:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expensesStandardsDetail" action="${ctx}/oa/expensesStands/saveDetail" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="standsMainId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">控制类别：</label>
			<div class="controls">
			  <form:select path="standsTypes" class="input-medium" multiple="true" disabled="true" >
<%-- 				<option value="">${expensesStandardsDetail.standsTypes }</option> --%>
				<form:options items="${fns:getDictList('oa_standsType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			  </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">金额：</label>
			<div class="controls">
                <form:input path="standsAmount" type="number" htmlEscape="false" maxlength="10" class="input-medium"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">金额单位：</label>
			<div class="controls">
				<form:select path="amountUnit" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('oa_amountUnit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属岗位：</label>
			 <div class="controls">
				 <form:select path="postCodes" class="input-xlarge" multiple="true" >
					<option value="">${expensesStandsMain.postCodes }</option>
					<form:options items="${fns:getPostInfoAllList()}" itemLabel="postName" itemValue="postCode" htmlEscape="false"/>
				</form:select>
<%-- 			 	<form:input path="postCode" readonly="readonly" htmlEscape="false" maxlength="10" class="input-medium"/> --%>
			 </div> 	
		</div>
		<div class="control-group">
			<label class="control-label">城市类别：</label>
			<div class="controls">
				<form:select path="cityTypes" class="input-xlarge" multiple="true">
<%-- 					<option value="">${expensesStandardsDetail.cityTypes }</option> --%>
					<form:options items="${fns:getDictList('oa_city_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交通方式：</label>
			<div class="controls">
				<form:select path="transModes" class="input-xlarge" multiple="true">
<%-- 					<option value="">${expensesStandardsDetail.transModes }</option> --%>
					<form:options items="${fns:getDictList('oa_traffic_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:expensesStands:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>