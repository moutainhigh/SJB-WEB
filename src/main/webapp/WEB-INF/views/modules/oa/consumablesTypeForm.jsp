<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消耗品类别管理管理</title>
	<meta name="decorator" content="default"/>
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
					if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});

		function keyup(){
			var value = $("#ctCode").val();
			if(/^[a-zA-Z0-9]{0,18}$/.test(value)){

			}else{
				alert("字段长度1-18个字符，可输入大/小写字母和数字!");
				$("#ctCode").val("");
			}

		};

		function changed(){
			var name = $("#ctName").val();
			if(/[`~!@#$%^&*_\-——+=<>?:"{}|,.\/;\\[\]·~！@#￥%&*\-+={}|《》？：“”【】、；‘’，。、]/im.test(name)){
				alert("禁止输入特殊字符!");
				$("#ctName").val("");
			}
			return;
		};


	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/consumablesType/">消耗品类别管理列表</a></li>
		<li class="active"><a href="${ctx}/oa/consumablesType/form?id=${consumablesType.id}&parent.id=${consumablesTypeparent.id}">消耗品类别管理${not empty consumablesType.id?'修改':'添加'}查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="consumablesType" action="${ctx}/oa/consumablesType/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">分类编号：</label>
			<div class="controls">
				<form:input path="ctCode" id = "ctCode" onKeyUp="keyup()" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				</span><span class="help-inline"><font color="red">*</font><span class="help-inline"><font color="#808080">字段长度1-18个字符，可输入大/小写字母和数字</font>  </span></div>
		</div>
		<div class="control-group">
			<label class="control-label">分类名称：</label>
			<div class="controls">
				<form:input path="ctName" id="ctName" onKeyUp="changed()" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级分类:</label>
			<div class="controls">
				<sys:treeselect id="parent" name="parent.id" value="${consumablesType.parent.id}" labelName="parentName" labelValue="${consumablesType.parent.ctName}"
					title="父级id" url="/oa/consumablesType/treeData" extId="${consumablesType.id}" cssClass="" allowClear="true"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="3000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>