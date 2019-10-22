<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目信息管理</title>
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
					}else if(element.is(":input")){
					/* else if(element.is("#impleLeader") || element.is("#marketLeader") || element.is("#office") || element.is("#custInfo")){ */
						error.appendTo(element.parent());
					}
					else{
						error.insertAfter(element);
					}
				}
			});
			
			//添加编号
			$("#custInfoBtn").click(function(){
				// 正常打开	
				top.$.jBox.open("iframe:${ctx}/tag/treeselect?url="+encodeURIComponent("/oa/custInfo/treeData"), "选择客户", 300, 420, {
					ajaxData:{selectIds: $("#labelId").val()},buttons:{"确定":"ok", "关闭":true}, submit:function(v, h, f){
						if (v=="ok"){
							var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
							var ids = [], names = [], custNames = [], areaIds = [], areaNames = [], marketLeaderIds = [], marketLeaders = [];
							if ("" == "true"){
								nodes = tree.getCheckedNodes(true);
							}else{
								nodes = tree.getSelectedNodes();
							}
							
							if(nodes.length==0){
								top.$.jBox.tip('请先选择客户.');
								return;
							}
							
							for(var i=0; i<nodes.length; i++) {//
								ids.push(nodes[i].id);
								names.push(nodes[i].name);//
								custNames.push(nodes[i].custName);//
								marketLeaderIds.push(nodes[i].marketLeaderId);//
								marketLeaders.push(nodes[i].marketLeader);//
								break; // 如果为非复选框选择，则返回第一个选择  
							}
							$("input[name='custInfo.id']").val(ids[0]);
							$("input[name='custInfo.custName']").val(custNames[0]);
							$("input[name='area.id']").val(areaIds[0]);
							$("input[name='area.name']").val(areaNames[0]);
							$("input[name='marketLeader.id']").val(marketLeaderIds[0]);
							$("input[name='marketLeader.name']").val(marketLeaders[0]);
						}
						if(typeof labelTreeselectCallBack == 'function'){
							labelTreeselectCallBack(v, h, f);
						}
					}, loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					}
				});
			});
			
			//当项目类型为“公司级”时，归属部门默认为“武汉物易云通网络科技有限公司”
			$("#projectType").bind("change",function(){ 
				debugger
				var pa = $("#projectType").val();
				var this_ = $(this);
				$.ajax({
			        url:"${ctx}/oa/projectInfo/officeChoose",//请求地址
			        data:{projectType : pa },
			        type:'post',
			        datatype:'json',
			        success:function(data){
			        	if(data.projectType == 0){
			        		$("#officePar").html("<input id='office.id' name='office.id' type='hidden' value='"+data.office.id+"'/>"+
									"<input id='office.name' name='office.name' type='hidden' value='"+data.office.name+"'/>"+
										"<input id='office' name='office' value='"+data.office.name+"' type='text' readonly='readonly' class='input-xlarge required'/>"+
										"<span class='help-inline'><font color='red'>*</font> </span>");
			        	}else{
			        		$("#officePar").html("<input id='office.id' name='office.id' type='hidden' value='${projectInfo.office.id}'/>"+
									"<input id='office.name' name='office.name' type='hidden' value='${projectInfo.office.name}'/>"+
									"<input id='office' name='office' value='${projectInfo.office.name}' type='text' readonly='readonly' class='input-xlarge required'/>"+
									"<a id='officeBtn' href='javascript:' class='btn' onclick='office()'  style=''>&nbsp;<i class='icon-search'></i>&nbsp;</a><span class='help-inline'><font color='red'>*</font> </span>");
			        	}
			        	
			        }
			    });
		    });
			$("#projectType").trigger("change");
			//当项目状态为“已上线”时，上线时间、市场负责人、实施负责人为必填字段
			debugger
			$("#projectState").bind("change",function(){ 
				var pr = $("#projectState").val();
				if(pr == 1){
					$("#onLineDate").addClass("required");//上线时间:onLineDate
					$("#marketLeader").addClass("required");//市场负责人：marketLeader
					$("#impleLeader").addClass("required");//实施负责人:impleLeader
					$("#onLineDatePar span").html("<font color='red'>*</font>");
					$("#marketLeaderPar span").html("<font color='red'>*</font>");
					$("#impleLeaderPar span").html("<font color='red'>*</font>");
				}else{
					$("#onLineDate").removeClass("required");
					$("#marketLeader").removeClass("required");
					$("#impleLeader").removeClass("required");
					$("#onLineDatePar span").html("<font color='red'></font>");
					$("#marketLeaderPar span").html("<font color='red'></font>");
					$("#impleLeaderPar span").html("<font color='red'></font>");
				}
		    });
			$("#projectState").trigger("change");
			//项目负责人选择
			$("#projectLeaderBtn").click(function(){
				// 正常打开	
				top.$.jBox.open("iframe:${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=3"), "项目负责人", 300, 420, {
					ajaxData:{selectIds: $("#labelId").val()},buttons:{"确定":"ok", "清除":"clear","关闭":true}, submit:function(v, h, f){
						if (v=="ok"){
							var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
							var ids = [], names = [], mobiles = [], userIds = [];
							if ("" == "true"){
								nodes = tree.getCheckedNodes(true);
							}else{
								nodes = tree.getSelectedNodes();
							}
							if(nodes.length==0){
								top.$.jBox.tip('请先选择项目负责人.');
								return;
							}
							for(var i=0; i<nodes.length; i++) {//
								if (nodes[i].isParent){
									top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
									return false;
								}
								ids.push(nodes[i].id);
								names.push(nodes[i].name);//
								mobiles.push(nodes[i].mobile);//
								userIds.push(nodes[i].userId);//
								break; // 如果为非复选框选择，则返回第一个选择  
							}
							$("#projectLeader").val(names[0]);
							$("input[name='projectLeader.name']").val(names[0]);
							$("input[name='projectLeader.id']").val(userIds[0]);
						}else if (v=="clear"){
							$("#projectLeader.id").val("");
							$("#projectLeader.name").val("");
							$("#projectLeader").val("");
		                }
						if(typeof labelTreeselectCallBack == 'function'){
							labelTreeselectCallBack(v, h, f);
						}
					}, loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					}
				});
			});
			//市场人员选择
			$("#marketLeaderBtn").click(function(){
				// 正常打开	
				top.$.jBox.open("iframe:${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=3"), "选择市场人员", 300, 420, {
					ajaxData:{selectIds: $("#labelId").val()},buttons:{"确定":"ok", "清除":"clear","关闭":true}, submit:function(v, h, f){
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
								if (nodes[i].isParent){
									top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
									return false;
								}
								ids.push(nodes[i].id);
								names.push(nodes[i].name);//
								mobiles.push(nodes[i].mobile);//
								userIds.push(nodes[i].userId);//
								break; // 如果为非复选框选择，则返回第一个选择  
							}
							$("#marketLeader").val(names[0]);
							$("input[name='marketLeader.name']").val(names[0]);
							$("input[name='marketLeader.id']").val(userIds[0]);
						}else if (v=="clear"){
							$("#marketLeader.id").val("");
							$("#marketLeader.name").val("");
							$("#marketLeader").val("");
		                }
						if(typeof labelTreeselectCallBack == 'function'){
							labelTreeselectCallBack(v, h, f);
						}
					}, loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					}
				});
			});
			//实施人员选择
			$("#impleLeaderBtn").click(function(){
				// 正常打开	
				top.$.jBox.open("iframe:${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=3"), "实施人员选择", 300, 420, {
					ajaxData:{selectIds: $("#labelId").val()},buttons:{"确定":"ok", "清除":"clear","关闭":true}, submit:function(v, h, f){
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
								if (nodes[i].isParent){
									top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
									return false;
								}
								ids.push(nodes[i].id);
								names.push(nodes[i].name);//
								mobiles.push(nodes[i].mobile);//
								userIds.push(nodes[i].userId);//
								break; // 如果为非复选框选择，则返回第一个选择  
							}
							$("#impleLeader").val(names[0]);
							$("input[name='impleLeader.name']").val(names[0]);
							$("input[name='impleLeader.id']").val(userIds[0]);
						}else if (v=="clear"){
							$("#impleLeader.id").val("");
							$("#impleLeader.name").val("");
							$("#impleLeader").val("");
		                }
						if(typeof labelTreeselectCallBack == 'function'){
							labelTreeselectCallBack(v, h, f);
						}
					}, loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					}
				});
			});
			function test(ele){
				console.log(1);
			}
		});
		//部门选择
		function office(){
			// 正常打开	
			top.$.jBox.open("iframe:${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=2"), "部门选择", 300, 420, {
				ajaxData:{selectIds: $("#labelId").val()},buttons:{"确定":"ok", "清除":"clear","关闭":true}, submit:function(v, h, f){
					if (v=="ok"){
						var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
						var ids = [], names = [], mobiles = [], userIds = [];
						if ("" == "true"){
							nodes = tree.getCheckedNodes(true);
						}else{
							nodes = tree.getSelectedNodes();
						}
						if(nodes.length==0){
							top.$.jBox.tip('请先选择部门.');
							return;
						}
						
						for(var i=0; i<nodes.length; i++) {//
							if (nodes[i].isParent){
								top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
								return false;
							}
							ids.push(nodes[i].id);
							names.push(nodes[i].name);//
							mobiles.push(nodes[i].mobile);//
							userIds.push(nodes[i].userId);//
							break; // 如果为非复选框选择，则返回第一个选择  
						}
						$("#office").val(names[0]);
						$("input[name='office.name']").val(names[0]);
						$("input[name='office.id']").val(userIds[0]);
					}else if (v=="clear"){
						$("#office.id").val("");
						$("#office.name").val("");
						$("#office").val("");
	                }
					if(typeof labelTreeselectCallBack == 'function'){
						labelTreeselectCallBack(v, h, f);
					}
				}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/projectInfo/">项目信息列表</a></li>
		<li class="active"><a href="${ctx}/oa/projectInfo/form?id=${projectInfo.id}">项目信息<shiro:hasPermission name="oa:projectInfo:edit">${not empty projectInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:projectInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="projectInfo" action="${ctx}/oa/projectInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">项目编号</label>
			<div class="controls">
				<form:input path="projectCode" htmlEscape="false" maxlength="90" readonly="true" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目名称</label>
			<div class="controls">
				<form:input path="projectName" htmlEscape="false" maxlength="90" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目类型</label>
			<div class="controls">
				<form:select id="projectType" path="projectType" class="input-xlarge required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('project_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属部门</label>
			<div class="controls" id="officePar">
				<sys:treeselect id="office" name="office.id"
					value="${projectInfo.office.id}" labelName="office.name"
					labelValue="${projectInfo.office.name}" title="归属部门"
					url="/sys/office/treeData?type=2" allowClear="true" cssClass="input-xsmall required"
					notAllowSelectParent="true"  dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">客户名称</label>
			<div class="controls">
				<input id="custInfo.id" name="custInfo.id" type="hidden" value="${projectInfo.custInfo.id}"/>
				<input id="custInfo" name="custInfo.custName" readonly="readonly" class="input-xsmall required" type="text" value="${projectInfo.custInfo.custName}"><a id="custInfoBtn" href="javascript:" class="btn  " style="">&nbsp;<i class="icon-search"></i>&nbsp;</a>&nbsp;&nbsp;
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目状态</label>
			<div class="controls">
				<form:select path="projectState" class="input-large required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('project_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上线时间</label>
			<div class="controls" id="onLineDatePar">
				<input id="onLineDate" name="onLineDate" type="text" readonly="readonly" maxlength="20"
				value="<fmt:formatDate value="${projectInfo.onLineDate}" pattern="yyyy-MM-dd"/>"
				onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目负责人</label>
			<div class="controls">
				<%-- <sys:treeselect id="projectLeader" name="projectLeader.id" value="${projectInfo.projectLeader.id}" labelName="projectLeader.name" labelValue="${projectInfo.projectLeader.name}"
					title="项目负责人" url="/sys/office/treeData?type=3" includeExEmployee="false" cssClass="input-xsmall required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span> --%>
				<input id="projectLeader.id" name="projectLeader.id" type="hidden" value="${projectInfo.projectLeader.id}"/>
				<input id="projectLeader.name" name="projectLeader.name" type="hidden" value="${projectInfo.projectLeader.name}"/>
				<input id="projectLeader" name="projectLeader" class="required" readonly="readonly" type="text"  value="${projectInfo.projectLeader.name}"><a id="projectLeaderBtn" href="javascript:" class="btn" style="">&nbsp;<i class="icon-search"></i>&nbsp;</a>&nbsp;&nbsp;
				<font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">市场负责人</label>
			<div class="controls" id="marketLeaderPar">
				<input id="marketLeader.id" name="marketLeader.id" type="hidden" value="${projectInfo.marketLeader.id}"/>
				<input id="marketLeader.name" name="marketLeader.name" type="hidden" value="${projectInfo.marketLeader.name}"/>
				<input id="marketLeader" name="marketLeader" readonly="readonly" type="text" value="${projectInfo.marketLeader.name}"><a id="marketLeaderBtn" href="javascript:" class="btn" style="">&nbsp;<i class="icon-search"></i>&nbsp;</a>&nbsp;&nbsp;
				<span class="help-inline"></span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">实施负责人</label>
			<div class="controls" id="impleLeaderPar">
				<input id="impleLeader.id" name="impleLeader.id" type="hidden" value="${projectInfo.impleLeader.id}"/>
				<input id="impleLeader.name" name="impleLeader.name" type="hidden" value="${projectInfo.impleLeader.name}"/>
				<input id="impleLeader" name="impleLeader" readonly="readonly" type="text" value="${projectInfo.impleLeader.name}"><a id="impleLeaderBtn" href="javascript:" class="btn" style="">
				&nbsp;<i class="icon-search"></i>&nbsp;</a>&nbsp;&nbsp;
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="300" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:projectInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>