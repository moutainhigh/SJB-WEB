<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户信息管理</title>
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
		<li><a href="${ctx}/oa/custInfo/">客户信息列表</a></li>
		<li class="active"><a href="${ctx}/oa/custInfo/form?id=${custInfo.id}">详细<shiro:hasPermission name="oa:custInfo:edit"></shiro:hasPermission><shiro:lacksPermission name="oa:custInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="custInfo" action="#" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">客户编号：</label>
			<div class="controls">
				${custInfo.custCode}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户名称：</label>
			<div class="controls">
				${custInfo.custName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户类别：</label>
			<div class="controls">
				${fns:getDictLabel(custInfo.custType, 'cust_type', 'label')}
			</div>
		</div>
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">归属区域：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				${custInfo.area.name} --%>
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="control-group">
			<label class="control-label">客户阶段：</label>
			<div class="controls">
				${fns:getDictLabel(custInfo.custStage, 'cust_stage', 'label')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场负责人:</label>
			<div class="controls">
				${custInfo.leader.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场负责人手机号：</label>
			<div class="controls">
				${custInfo.leader.mobile}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				${custInfo.remarks}
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>