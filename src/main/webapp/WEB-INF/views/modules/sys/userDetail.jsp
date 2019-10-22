<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>用户管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#no").focus();
		$("#inputForm").validate({
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
			errorPlacement : function(error,element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")
						|| element.is(":radio")
						|| element.parent().is(".input-append")) {
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
		<li><a href="${ctx}/sys/user/list">用户列表</a></li>
		<li class="active"><a href="${ctx}/sys/user/detail?id=${user.id}">用户<shiro:hasPermission
					name="sys:user:edit">${not empty user.id?'详细':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="sys:user:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<br/>
	<form:form id="inputForm" modelAttribute="user"
		action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
		<sys:message content="${message}" />
		
		<div class="control-group">
			<label class="control-label">工号:</label>
			<div class="controls">
				<h6>${user.no}</h6>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">头像:</label>
			<div class="controls">
				<form:hidden id="nameImage" path="photo" htmlEscape="false"
					maxlength="255" class="input-xlarge" />
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" readonly="true" 
					selectMultiple="false" maxWidth="100" maxHeight="100" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属公司:</label>
			<div class="controls">
			<h6>${user.company.name}</h6>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属部门:</label>
			<div class="controls">
			<h6>${user.office.name}</h6>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
			<h6>${user.name}</h6>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">手机(登录账号):</label>
			<div class="controls">
			<h6>${user.mobile}</h6>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">唯一账号:</label>
			<div class="controls">
			<h6>${user.loginName}</h6>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
			<h6>${user.email}</h6>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">允许登录:</label>
			<div class="controls">
			<h6>${user.loginFlag=="1"?"是":"否"}</h6>
			</div>
		</div>
		

		<div class="control-group">
			<label class="control-label">岗位名称:</label>
			<div class="controls">
			<h6>${user.postName}</h6>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">职级:</label>
			<div class="controls">
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div class="controls">
			<h6>${user.roleName}</h6>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
			<h6>${user.remarks}</h6>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">本人的离职交接：</label>
		</div>
			<div class="controls">
				<label class="control-label">离职交接时间：</label>
				<div class="controls">
					<h6><fmt:formatDate value="${userQuit.createTime}" type="both" dateStyle="full" /></h6>
				</div>
			</div>
			<div class="controls">
				<label class="control-label">离职交接人：</label>
				<div class="controls">
					<h6>${userQuit.handover}</h6>
				</div>
			</div>
			<div class="controls">
				<label class="control-label">个人申请单据是否都完结：</label>
				<div class="controls">
					<h6>${fns:getDictLabels(userQuit.isFinish,'is_finish','')}</h6>
				</div>
			</div>
			<div class="controls">	
				<label class="control-label">备注：</label>
				<div class="controls">
					<h6>${userQuit.unfinishRemarks}</h6>
				</div>
			</div>
			<div class="controls">	
				<label class="control-label">备注：</label>
				<div class="controls">
					<h6>${userQuit.remarks}</h6>
				</div>
			</div>
		
		
		<div class="control-group">
			<label class="control-label">接手的离职交接：</label>
			<div class="controls">
			<h6>${handovers}</h6>
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>