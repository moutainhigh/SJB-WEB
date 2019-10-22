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
				/* rules : {
					custName : {
						remote : "${ctx}/oa/custInfo/checkCustName?oldCustName="
							+ encodeURIComponent('${custInfo.custName}')
					}
				},
				messages : {
					custName : {
						remote : "项目已存在！"
					}
				}, */
				
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else if(element.is(":input")){
						error.appendTo(element.parent());
					}else {
						error.insertAfter(element);
					}
				}
			});
			//市场人员选择
			$("#marketLeaderBtn").click(function(){
				// 正常打开	
				top.$.jBox.open("iframe:${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=3"), "选择市场人员", 300, 420, {
					ajaxData:{selectIds: $("#labelId").val()},buttons:{"确定":"ok", "关闭":true}, submit:function(v, h, f){
						if (v=="ok"){
							var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
							var ids = [], names = [], mobiles = [], userIds = [];
							if ("" == "true"){
								nodes = tree.getCheckedNodes(true);
							}else{
								nodes = tree.getSelectedNodes();
							}
							if(nodes.length==0){
								top.$.jBox.tip('请先选择市场人员.');
								return;
							}
							
							for(var i=0; i<nodes.length; i++) {//
								ids.push(nodes[i].id);
								names.push(nodes[i].name);//
								mobiles.push(nodes[i].mobile);//
								userIds.push(nodes[i].userId);//
								break; // 如果为非复选框选择，则返回第一个选择  
							}
							$("#leader").val(names[0]);
							$("input[name='leader.name']").val(names[0]);
							$("input[name='leader.id']").val(userIds[0]);
							$("input[name='leader.mobile']").val(mobiles[0]);
						}
						if(typeof labelTreeselectCallBack == 'function'){
							labelTreeselectCallBack(v, h, f);
						}
					}, loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					}
				});
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/custinfo/custInfo/">客户信息列表</a></li>
		<li class="active"><a href="${ctx}/custinfo/custInfo/form?id=${custInfo.id}">客户信息<shiro:hasPermission name="custinfo:custInfo:edit">${not empty custInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="custinfo:custInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="custInfo" action="${ctx}/custinfo/custInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">客户编号：</label>
			<div class="controls">
				<form:input path="custCode" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户名称：</label>
			<div class="controls">
				<form:input path="custName" htmlEscape="false" maxlength="90" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户简称：</label>
			<div class="controls">
				<form:input path="custAbbreviation" htmlEscape="false" maxlength="90" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户分类：</label>
			<div class="controls">
				<form:input path="custClassify" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属区域：</label>
			<div class="controls">
				<sys:treeselect id="area" name="area.id" value="${custInfo.area.id}" labelName="area.name" labelValue="${custInfo.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区域名称：</label>
			<div class="controls">
				<form:input path="areaName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">股东结构：</label>
			<div class="controls">
				<form:input path="custHolderStructure" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司性质：</label>
			<div class="controls">
				<form:input path="custNature" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户来源：</label>
			<div class="controls">
				<form:input path="custSource" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户注册地址：</label>
			<div class="controls">
				<form:input path="custAddress" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户类别：</label>
			<div class="controls">
				<form:input path="custType" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户阶段：</label>
			<div class="controls">
				<form:input path="custStage" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场负责人：</label>
			<div class="controls">
				<form:input path="marketLeader" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场负责人手机号：</label>
			<div class="controls">
				<form:input path="marketLeaderPhone" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户状态：</label>
			<div class="controls">
				<form:input path="custStatus" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属行业：</label>
			<div class="controls">
				<form:input path="custTrades" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务类型：</label>
			<div class="controls">
				<form:input path="custBusinessType" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规模：</label>
			<div class="controls">
				<form:input path="custCompanySize" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">运力组织方式：</label>
			<div class="controls">
				<form:input path="custPowerMode" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发货方式：</label>
			<div class="controls">
				<form:input path="custDeliverMode" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货方式：</label>
			<div class="controls">
				<form:input path="custReceiveMode" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结算对象：</label>
			<div class="controls">
				<form:input path="custBalanceObj" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周期：</label>
			<div class="controls">
				<form:input path="custBalanceCycle" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付方式：</label>
			<div class="controls">
				<form:input path="payMethod" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${custInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间：</label>
			<div class="controls">
				<input name="updateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${custInfo.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="300" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="custinfo:custInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>