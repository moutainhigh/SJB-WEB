<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>用户管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#no").focus();
						$("#inputForm")
								.validate(
										{
											rules : {
												loginName : {
													remote : "${ctx}/sys/user/checkLoginName?oldLoginName="
															+ encodeURIComponent('${user.loginName}')
												}
											},
											messages : {
												loginName : {
													remote : "用户登录名已存在"
												},
												confirmNewPassword : {
													equalTo : "输入与上面相同的密码"
												}
											},
											submitHandler : function(form) {
												loading('正在提交，请稍等...');
												form.submit();
											},
											errorContainer : "#messageBox",
											errorPlacement : function(error,
													element) {
												$("#messageBox").text(
														"输入有误，请先更正。");
												if (element.is(":checkbox")
														|| element.is(":radio")
														|| element
																.parent()
																.is(
																		".input-append")) {
													error.appendTo(element
															.parent().parent());
												} else {
													error.insertAfter(element);
												}
											}
										});
					});

	//编辑
	function selectBill(form) {
		//        	if(document.getElementById("handovers") == null){
		//        		$("#han").html("交接人不能为空！");
		//        	}else 
		if ($("#isFinish").is(':checked') == true
				&& document.getElementById("unfinishRemarks").value == '') {
			$("#isFin").html("个人申请单据未完结，请填写备注！");
		} else {
			var param = "id=" + "${user.id}";
			$.ajax({
				url : "${ctx}/sys/user/queryBills",//请求地址
				data : param,
				type : 'post',
				datatype : 'json',
				// 	        async:false,
				success : function(data) {
					data = JSON.parse(data);
					if (data != '' && data != null && data != "1") {
						alert(data);
					} else if (data != '' && data != null && data == "1") {
						if (window.confirm('该离职人还有未完结的单据，是否确定提交离职申请？')) {
							document.getElementById("inputForm").submit();
						}
					} else {
						document.getElementById("inputForm").submit();
						top.$.jBox.tip.mess = null;
					}

				}
			});
		}

	};
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/list">用户列表</a></li>
		<li class="active"><a href="${ctx}/sys/user/quit?id=${user.id}"><shiro:hasPermission
					name="sys:user:edit">${not empty user.id?'离职':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="sys:user:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<br />
	<%-- 		<sys:message content="${message}" /> --%>
	<form:form id="inputForm" modelAttribute="user"
		action="${ctx}/sys/user/saveQuit" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" id="userId" name="userId" value="${user.id}" />
		<sys:message content="${message}" />
		<table>

			<tr>
				<td coscaption="2"><label>离职交接人：</label> <sys:treeselect
						id="handovers" name="handovers" value="${items.getQuerOffice() }"
						labelName="office.name" labelValue="${items.getQuerOfficeName()}"
						title="离职交接人" url="/sys/office/treeData?type=3&isAll=false&extId=${user.id}"
                        includeExEmployee="false"
						cssStyle="width:150px" allowClear="true" checked="true"
						cssClass="required" notAllowSelectParent="true" /> <span
					class="help-inline"><font color="red" id="han">*</font> </span></td>
			</tr>
			<tr>
				<td><label class="control-label">个人申请单据是否都完结：</label> <input
					type="radio" name="isFinish" value="0" checked="checked" /> <label>是（全部完结/没有申请单据）</label>
					<input type="radio" name="isFinish" value="1" id="isFinish" /> <label>否（还有未完结单据）</label>
					<span class="help-inline"><font color="red" id="isFin">*</font>
				</span></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label>备注：</label>
					<input type="text" name="unfinishRemarks" id="unfinishRemarks" /> <span
					class="help-inline"><font color="red"></font> </span>
				</td>
			</tr>
			<tr>
				<td coscaption="2" align="left"><label>备注：</label> <form:textarea
						path="remarks" htmlEscape="false" rows="3" maxlength="200"
						class="input-xlarge" /></td>
			</tr>
		</table>

		<div class="form-actions">
			<shiro:hasPermission name="contract:contractConfig:edit">
				<input id="btnSubmit" class="btn btn-primary" type="button"
					onclick="selectBill(this);" value="确 定" />&nbsp;</shiro:hasPermission>
			<%-- 				  type="submit" onclick="return validateForm(this);" value="确 定"/>&nbsp;</shiro:hasPermission> --%>
			<input id="btnCancel" class="btn" type="button" value="取 消"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>