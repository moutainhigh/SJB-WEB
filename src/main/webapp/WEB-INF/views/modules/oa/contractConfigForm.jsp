<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同模版管理</title>
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
			
			if($("input[name='id']").val() == ''){
				$("#haveAccessories").attr('checked',true); 
				$("#must1").attr("disabled",false);
				$("#must2").attr("disabled",false);
				$("#haveAccessories").val('1');
			}
			
			
			if($("#association").val() == 1){
				$("#association").attr('checked',true); 
				$("#ent").attr("disabled",false);
			};
			

			if($("#ent").val() == 1){
				$("#ent").attr('checked',true); 
			};
			

			if($("#haveAccessories").val() == 1){
				$("#haveAccessories").attr('checked',true); 
				$("#must1").attr("disabled",false);
				$("#must2").attr("disabled",false);
			}else{
				$("#must1").val('');
				$("#must2").val('');
			};
			
			
			$("#association").click(function(){
				if($("#association").is(':checked') == false ) {
					$("#association").attr("value","0"); 
					$("#ent").attr("disabled",true);
					$("#ent").val(0);
					$("#ent").attr('checked',false); 
				} else {
					$("#association").attr("value","1"); 
					$("#ent").attr("disabled",false);
				}
			});
			$("#ent").click(function(){
				if($("#ent").is(':checked') == false) {
					$("#ent").val(0);
				} else {
					$("#ent").val(1);
				}
			});
			$("#haveAccessories").click(function(){
				if($("#haveAccessories").val() == 1) {
					$("#haveAccessories").val(0);
					$("#must1").attr("disabled",true);
					$("#must2").attr("disabled",true);
					$("#must1").val('');
					$("#must2").val('');
				} else {
					$("#haveAccessories").val(1);
					$("#must1").attr("disabled",false);
					$("#must2").attr("disabled",false);
				}
			});
			
			
			//触发器
			$("select[name='contractMemberList[0].contractType']").trigger(function(){
				
				if($("select[name='contractMemberList[0].contractType']").val() == 2){
					$("select[name='contractMemberList[0].dataSource']").val(3);
					$("select[name='contractMemberList[0].dataSource']").attr("disabled",true);
					var str = "<input id=\"h\" type=\"hidden\" name=\"contractMemberList[0].dataSource\" value=\"3\"/>";
					$("#memberTable").append(str);
				}
			});
			//触发器
			$("select[name='contractMemberList[1].contractType']").trigger(function(){
				
				if($("select[name='contractMemberList[1].contractType']").val() == 2){
					$("select[name='contractMemberList[1].dataSource']").val(3);
					$("select[name='contractMemberList[1].dataSource']").attr("disabled",true);
					var str = "<input id=\"h1\" type=\"hidden\" name=\"contractMemberList[1].dataSource\" value=\"3\"/>";
					$("#memberTable").append(str);
				}
			});
			//触发器
			$("select[name='contractMemberList[2].contractType']").trigger(function(){
				
				if($("select[name='contractMemberList[2].contractType']").val() == 2){
					$("select[name='contractMemberList[2].dataSource']").val(3);
					$("select[name='contractMemberList[2].dataSource']").attr("disabled",true);
					var str = "<input id=\"h2\" type=\"hidden\" name=\"contractMemberList[0].dataSource\" value=\"3\"/>";
					$("#memberTable").append(str);
				}
			});
			
		});
		//选择2方合同或3方合同
		function btnClick(count){
			//删除table下的tr标签
			var tr = document.getElementById("memberTable"); 
			var rowNum = tr.rows.length;
			for(i=0;i<rowNum;i++){
			tr.deleteRow(i);
			rowNum=rowNum-1;
			i=i-1;
			} 
			//添加2方表格
			if(count == "2"){
				$("#contractMemberCount").val(2);
				var str = "<tr id='tr'>" + 
				"<td width=\"30\">合同方</td><td width=\"30\">合同方类型</td><td width=\"50\">数据来源</td></tr>" +
				"<td width=\"30\">甲方</td><td width=\"30\" >" +
				"<input type=\"hidden\" name=\"contractMemberList[0].memberType\" value='1'/>" +
				"<select name=\"contractMemberList[0].contractType\" class=\"input-mini required\" onclick=\"selectClick0(this)\;\"> " +
					"<option value=\"\" label=\"请选择\"/>" +
					"<option value=\"1\" label=\"公司\"/>" +
					"<option value=\"2\" label=\"个人\"/>" +
				"</select>" +
				"<span class=\"help-inline\"><font color=\"red\">*</font> </span>" +
				"</td><td width=\"50\">" +
				"<select name=\"contractMemberList[0].dataSource\" class=\"input-mini required\" onclick=\"select0(this)\;\">" +
					"<option value=\"\" label=\"请选择\"/>" +
					"<option value=\"1\" label=\"客户数据\"/>" +
					"<option value=\"2\" label=\"合同信息\"/>" +
					"<option value=\"3\" label=\"非系统数据\"/>" +
				"</select>" +
				"<span class=\"help-inline\"><font color=\"red\">*</font> </span>" +
				"</td></tr>" +
				"<tr>" +
				"<td width=\"30\">乙方</td><td width=\"30\" >" +
				"<input type=\"hidden\" name=\"contractMemberList[1].memberType\" value='2'/>" +
				"<select name=\"contractMemberList[1].contractType\" class=\"input-mini required\" onclick=\"selectClick1(this)\;\">" +
					"<option value=\"\" label=\"请选择\"/>" +
					"<option value=\"1\" label=\"公司\"/>" +
					"<option value=\"2\" label=\"个人\"/>" +
				"</select>" +
				"<span class=\"help-inline\"><font color=\"red\">*</font> </span>" +
				"</td><td width=\"50\">" +
				"<select name=\"contractMemberList[1].dataSource\" class=\"input-mini required\" onclick=\"select1(this)\;\">" +
					"<option value=\"\" label=\"请选择\"/>" +
					"<option value=\"1\" label=\"客户数据\"/>" +
					"<option value=\"2\" label=\"合同信息\"/>" +
					"<option value=\"3\" label=\"非系统数据\"/>" +
				"</select>" +
				"<span class=\"help-inline\"><font color=\"red\">*</font> </span>" +
				"</td></tr>" +
				"<tr>" 
				$("#memberTable").append(str);
				
			};
// 			添加3方表格
			if(count == "3"){
				$("#contractMemberCount").val(3);
				var str = "<tr id='tr'>" + 
				"<td width=\"30\">合同方</td><td width=\"30\">合同方类型</td><td width=\"50\">数据来源</td></tr>" +
				"<td width=\"30\">甲方</td><td width=\"30\" >" +
				"<input type=\"hidden\" name=\"contractMemberList[0].memberType\" value='1'/>" +
				"<select name=\"contractMemberList[0].contractType\" class=\"input-mini required\" onclick=\"selectClick0(this)\;\">" +
					"<option value=\"\" label=\"请选择\"/>" +
					"<option value=\"1\" label=\"公司\"/>" +
					"<option value=\"2\" label=\"个人\"/>" +
				"</select>" +
				"<span class=\"help-inline\"><font color=\"red\">*</font> </span>" +
				"</td><td width=\"50\">" +
				"<select name=\"contractMemberList[0].dataSource\" class=\"input-mini required\" onclick=\"select0(this)\;\">" +  
					"<option value=\"\" label=\"请选择\"/>" +
					"<option value=\"1\" label=\"客户数据\"/>" +
					"<option value=\"2\" label=\"合同信息\"/>" +
					"<option value=\"3\" label=\"非系统数据\"/>" +
				"</select>" +
				"<span class=\"help-inline\"><font color=\"red\">*</font> </span>" +
				"</td></tr>" +
				"<tr>" +
				"<td width=\"30\">乙方</td><td width=\"30\" >" +
				"<input type=\"hidden\" name=\"contractMemberList[1].memberType\" value='2'/>" +
				"<select name=\"contractMemberList[1].contractType\" class=\"input-mini required\" onclick=\"selectClick1(this)\;\">" +
					"<option value=\"\" label=\"请选择\"/>" +
					"<option value=\"1\" label=\"公司\"/>" +
					"<option value=\"2\" label=\"个人\"/>" +
				"</select>" +
				"<span class=\"help-inline\"><font color=\"red\">*</font> </span>" +
				"</td><td width=\"50\">" +
				"<select name=\"contractMemberList[1].dataSource\" class=\"input-mini required\" onclick=\"select1(this)\;\">" +
					"<option value=\"\" label=\"请选择\"/>" +
					"<option value=\"1\" label=\"客户数据\"/>" +
					"<option value=\"2\" label=\"合同信息\"/>" +
					"<option value=\"3\" label=\"非系统数据\"/>" +
				"</select>" +
				"<span class=\"help-inline\"><font color=\"red\">*</font> </span>" +
				"</td></tr>" +
				"<tr>" +
				"<td width=\"30\">丙方</td><td width=\"30\" >" +
				"<input type=\"hidden\" name=\"contractMemberList[2].memberType\" value='3'/>" +
				"<select name=\"contractMemberList[2].contractType\" class=\"input-mini required\" onclick=\"selectClick2(this)\;\">" +
					"<option value=\"\" label=\"请选择\"/>" +
					"<option value=\"1\" label=\"公司\"/>" +
					"<option value=\"2\" label=\"个人\"/>" +
				"</select>" +
				"<span class=\"help-inline\"><font color=\"red\">*</font> </span>" +
				"</td><td width=\"50\">" +
				"<select name=\"contractMemberList[2].dataSource\" class=\"input-mini required\" onclick=\"select2(this)\;\">" +
					"<option value=\"\" label=\"请选择\"/>" +
					"<option value=\"1\" label=\"客户数据\"/>" +
					"<option value=\"2\" label=\"合同信息\"/>" +
					"<option value=\"3\" label=\"非系统数据\"/>" +
				"</select>" +
				"<span class=\"help-inline\"><font color=\"red\">*</font> </span>" +
				"</td></tr>"
				$("#memberTable").append(str);
			}
		};
		var member0 = 0 ;
		var member1 = 0 ;
		var member2 = 0 ;
		//公司、个人选定后，数据来源的变动
		function selectClick0(select){
			if(select.value == 2){
				$("select[name='contractMemberList[0].dataSource']").val(3);
				$("select[name='contractMemberList[0].dataSource']").attr("disabled",true);
				var str = "<input id=\"h\" type=\"hidden\" name=\"contractMemberList[0].dataSource\" value=\"3\"/>";
				$("#memberTable").append(str);
			}else{
				$("select[name='contractMemberList[0].dataSource']").val('');
				$("select[name='contractMemberList[0].dataSource']").attr("disabled",false);
				$("#memberTable").children("#h").remove();
			}
		};
		
		//公司、个人选定后，数据来源的变动
		function selectClick1(select){
			if(select.value == 2){
				$("select[name='contractMemberList[1].dataSource']").val(3);
				$("select[name='contractMemberList[1].dataSource']").attr("disabled",true);
				var str = "<input id=\"h1\" type=\"hidden\" name=\"contractMemberList[1].dataSource\" value=\"3\"/>";
				$("#memberTable").append(str);
			}else{
				$("select[name='contractMemberList[1].dataSource']").val('');
				$("select[name='contractMemberList[1].dataSource']").attr("disabled",false);
				$("#memberTable").children("#h1").remove();
			}
		};
		
		//公司、个人选定后，数据来源的变动
		function selectClick2(select){
			if(select.value == 2){
				$("select[name='contractMemberList[2].dataSource']").val(3);
				$("select[name='contractMemberList[2].dataSource']").attr("disabled",true);
				var str = "<input id=\"h2\" type=\"hidden\" name=\"contractMemberList[2].dataSource\" value=\"3\"/>";
				$("#memberTable").append(str);
			}else{
				$("select[name='contractMemberList[2].dataSource']").val('');
				$("select[name='contractMemberList[2].dataSource']").attr("disabled",false);
				$("#memberTable").children("#h2").remove();
			}
		};
		function select0(select){
			if(select.value == 2){
				member0 = 1 ;
			}else{
				member0 = 0 ;
			}
		};
		function select1(select){
			if(select.value == 2){
				member1 = 1 ;
			}else{
				member1 = 0 ;
			}
		};
		function select2(select){
			if(select.value == 2){
				member2 = 1 ;
			}else{
				member2 = 0 ;
			}
		};
		
	    
	    function validateForm(form){
			if($('#contractMemberCount').val() == ''){
				$('#memberGroup').html('* 合同方信息必填！');
				return false;	
			};
			var members = 0;
			var month = $("#memberTable").find("tr").length;
			for(var i = 0 ; i< month ; i++){
				if($("select[name='contractMemberList["+i+"].dataSource']").val() == 2){
					members++;
				}
			}
			if(parseInt(members) > 1){
				$('#members').html('* 合同方中只能存在一方数据来源为公司合同信息管理！');
				return false;
			};
			if(parseInt(members) < 1){
				$('#members').html('* 合同方中必须存在一方数据来源为公司合同信息管理！');
				return false;
			};
			
			var must1 = document.getElementById('must1').value; 
	        var must2 = document.getElementById('must2').value;
	        
	        var must3 = document.getElementById('must3').value; 
	        var must4 = document.getElementById('must4').value;
	        
	        var must5 = document.getElementById('must5').value; 
	        var must6 = document.getElementById('must6').value;
	        
	        if (parseInt(must1) < parseInt(must2)) {  
	        	return false;
	        };
	        if (parseInt(must3) < parseInt(must4)) {  
	        	return false;
		    
		    };
	        if (parseInt(must5) < parseInt(must6)) {  
	        	return false;
		    };
	        
			
// 			
	    };
	    
	  //校验
		function jiaoyan(){
	        var must1 = document.getElementById('must1').value; 
	        var must2 = document.getElementById('must2').value;
	        
	        var must3 = document.getElementById('must3').value; 
	        var must4 = document.getElementById('must4').value;
	        
	        var must5 = document.getElementById('must5').value; 
	        var must6 = document.getElementById('must6').value;
	        if (parseInt(must1) < parseInt(must2)) {  
	        	  $('#must12').html('必填数目超过了可上传数！');
	        }else{
	        	 $('#must12').html('');
	        };
	        if (parseInt(must3) < parseInt(must4)) {  
	        	  $('#must34').html('必填数目超过了可上传数！');
		    }else{
		    	$('#must34').html('');
		    };
	        if (parseInt(must5) < parseInt(must6)) {  
	        	  $('#must56').html('必填数目超过了可上传数！');
		    }else{
		    	$('#must56').html('');
		    };
	        
	    };
	    //校验2
	    function jiaoyans(){
	        var must1 = document.getElementById('must1').value; 
	        var must2 = document.getElementById('must2').value;
	        
	        var must3 = document.getElementById('must3').value; 
	        var must4 = document.getElementById('must4').value;
	        
	        var must5 = document.getElementById('must5').value; 
	        var must6 = document.getElementById('must6').value;
	        if (parseInt(must1) < parseInt(must2)) {  
	           $('#must12').html('必填数目超过了可上传数！');
	        }else{
	        	$('#must12').html('');
	        };
	        if (parseInt(must3) < parseInt(must4)) {  
	        	 $('#must34').html('必填数目超过了可上传数！');
		    }else{
		    	$('#must34').html('');
		    };
	        if (parseInt(must5) < parseInt(must6)) {  
	        	 $('#must56').html('必填数目超过了可上传数！');
		    }else{
		    	$('#must56').html('');
		    };
	    };
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/contractConfig/">合同模版列表</a></li>
		<li class="active"><a href="${ctx}/oa/contractConfig/form?id=${contractConfig.id}">合同模版<shiro:hasPermission name="contract:contractConfig:edit">${not empty contractConfig.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="contract:contractConfig:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="contractConfig" action="${ctx}/oa/contractConfig/save" method="post" class="form-horizontal">
		<input type="hidden" id="id" name="id" value="${contractConfig.id }"/>
		<form:hidden path="oldContractName" value="${contractConfig.contractName }"/>
		<form:hidden path="contractCode" value="${contractConfig.contractCode }"/>
		<form:hidden path="version" value="${contractConfig.version }"/>
		<sys:message content="${message}"/>
		<table class="table-form">
		<tr>	
			<c:choose>
					<c:when test="${ contractConfig.id == null}">
						<td colspan="2">
								<div class="control-group">
									<label class="control-label">合同名称：</label>
										<form:input path="contractName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
										<span class="help-inline"><font color="red">*</font> </span>&nbsp;&nbsp;&nbsp;&nbsp;
									<span>合同缩写：</span>
										<form:input path="sort" onKeyUp="value=value.replace(/[^A-Z/-]/g,'')" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
										<span class="help-inline"><font color="red">*</font> </span>
								</div>
						</td>
					</c:when>
					<c:otherwise>
						<td colspan="2">
							<div class="control-group">
								<span class="control-label">合同名称：</span>
<%-- 								<span class="control-label-left">${contractConfig.contractName}</span> --%>
									<font class="control-label-left">${contractConfig.contractName}</font>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<span>合同缩写：</span>
									<form:input path="sort" onKeyUp="value=value.replace(/[^A-Z/-]/g,'')" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
									<span class="help-inline"><font color="red">*</font> </span>
							</div>
						</td>
<!-- 					<td> -->
<!-- 						<div class="control-group"> -->
<!-- 							<label class="control-label">合同名称：</label> -->
<!-- 							<div class="controls"> -->
<%-- 							<h4>${contractConfig.contractName}</h4> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</td> -->
<!-- 					<td> -->
<!-- 						<span>合同缩写：</span> -->
<%-- 						<form:input path="sort" onKeyUp="value=value.replace(/[^A-Z/-]/g,'')" htmlEscape="false" maxlength="64" class="input-xlarge required"/> --%>
<!-- 						<span class="help-inline"><font color="red">*</font> </span> -->
<!-- 					<td> -->
					</c:otherwise>
			</c:choose>
<!-- 			<div class="control-group"> -->
<!-- 				<label class="control-label">合同名称：</label> -->
<%-- 					<form:input path="contractName" htmlEscape="false" maxlength="64" class="input-xlarge required"/> --%>
<!-- 					<span class="help-inline"><font color="red">*</font> </span>&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 				<span>合同缩写：</span> -->
<%-- 					<form:input path="sort" onKeyUp="value=value.replace(/[^A-Z/-]/g,'')" htmlEscape="false" maxlength="64" class="input-xlarge required"/> --%>
<!-- 					<span class="help-inline"><font color="red">*</font> </span> -->
<!-- 			</div> -->
<!-- 			</td> -->
			
		</tr>
		<tr>
			<td colspan="2">	
			<div class="control-group">
				<div class="controls">
					<input id="association" type="checkbox" name="associationMain" value="${contractConfig.associationMain }" />关联主合同
				</div>
			</div>
			</td>
		</tr>
		<tr style="display:none">	
			<td colspan="2">	
			<div class="control-group">
				<div class="controls">
					<input id="ent" type="checkbox" disabled="true" name="endTimeConsistent" value="${contractConfig.endTimeConsistent }" />限定合同结束日期跟主合同一致
				</div>
			</div>
			</td>
		</tr>
		<tr>	
			<td colspan="2">	
			<div class="control-group">
				<label class="control-label">合同类型:</label>
				<div class="controls">
					<form:select path="contractType" class="input-large required">
						<form:option value="" label="请选择"/>
						<form:options items="${fns:getDictList('contract_type_s')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			</td>
		</tr>
		
		<tr>
			<td colspan="2">	
			<div class="control-group">
					<span class="control-label">合同方情况：</span>
				<div class="controls">
					<form:hidden id="contractMemberCount" path="contractMemberCount" htmlEscape="false" maxlength="1" class="input-xlarge required"/>
					<input id="btn2" class="btn btn-primary" type="button" onclick="btnClick('2');" value="2方"/>&nbsp;&nbsp;
					<input id="btn3" class="btn btn-primary" type="button" onclick="btnClick('3');" value="3方"/>
					<span class="help-inline"><font color="red"  id="memberGroup">*</font> </span>
				</div>
			</div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>	
			<div>
				<table id="memberTable" width="40%" border="1" class="required">
				<span class="help-inline"><font color="red" id="members"></font> </span>
					<tr><td width="30">合同方</td><td width="30">合同方类型</td><td width="50">数据来源</td></tr>
						<c:if test="${not empty contractConfig.contractMemberList}">
							<c:forEach items="${contractConfig.contractMemberList}" var="member" varStatus="status">
								<td width="30">${fns:getDictLabel(member.memberType, 'member_type', '')}</td><td width="30" >
								<input type="hidden" name="contractMemberList[${status.index}].memberType" value="${member.memberType }"/>
									<form:select path="contractMemberList[${status.index}].contractType" class="input-mini required" onclick="selectClick${status.index}(this);">
										<form:option value="" label="请选择" />
										<form:options items="${fns:getDictList('member_contract_type')}"
											itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<span class="help-inline"><font color="red">*</font> </span>
								</td><td width="50">
									<form:select path="contractMemberList[${status.index}].dataSource" class="input-mini required">
										<form:option value="" label="请选择" />
										<form:options items="${fns:getDictList('data_source')}"
											itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<span class="help-inline"><font color="red">*</font> </span>
								</td></tr>
							</c:forEach>
					    </c:if>
				</table>
			</div>
			</td>	
		</tr>
		<tr><th colspan="2"><span class="control-label">附件配置：</span></th></tr>
		
		<tr><td colspan="2">
			<span class="control-label">预签合同：</span>
			<div>
				<form:hidden path="contractConfigAttachmentList[0].attachmentType" value="1"/>
				<h7>可上传</h7>&nbsp;
				<form:input path="contractConfigAttachmentList[0].maxCount" id="must5" value="${contractConfig.contractConfigAttachmentList[0].maxCount}" onKeyUp="value=value.replace(/[^0-9]\D*$/,'')" onchange="jiaoyans()" htmlEscape="false" maxlength="64" style="width:30px;" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				&nbsp;<h7>个附件。（只可上传图片）</h7>&nbsp;
				&nbsp;<h7>必填：</h7>&nbsp;
				<form:input path="contractConfigAttachmentList[0].mustCount" id="must6" value="${contractConfig.contractConfigAttachmentList[0].mustCount}" onKeyUp="value=value.replace(/[^0-9]\D*$/,'')" onchange="jiaoyan()" htmlEscape="false" maxlength="64" style="width:30px;" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				&nbsp;<h7>个附件。</h7>&nbsp;<span class="help-inline"><font color="red" id="must56"></font></span>
			</div>
		</td></tr>
		
		
		<tr><td colspan="2">
			<span class="control-label">合同扫描件：</span>
			<div>
			<form:hidden path="contractConfigAttachmentList[1].attachmentType" value="2"/>
				<h7>可上传</h7>&nbsp;
				<form:input path="contractConfigAttachmentList[1].maxCount" id="must3" value="${contractConfig.contractConfigAttachmentList[1].maxCount}"  onKeyUp="value=value.replace(/[^0-9]\D*$/,'')" onchange="jiaoyans()" htmlEscape="false" maxlength="64" style="width:30px;" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				&nbsp;<h7>个附件。（支持PDF）</h7>&nbsp;
				&nbsp;<h7>必填：</h7>&nbsp;
				<form:input path="contractConfigAttachmentList[1].mustCount" id="must4" value="${contractConfig.contractConfigAttachmentList[1].mustCount}" onKeyUp="value=value.replace(/[^0-9]\D*$/,'')" onchange="jiaoyan()" htmlEscape="false" maxlength="64" style="width:30px;" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				&nbsp;<h7>个附件。</h7>&nbsp;<span class="help-inline"><font color="red" id="must34"></font></span>
			</div>
		</td></tr>
		
		<tr><td colspan="2">
			<span class="control-label"><input type="checkbox" id="haveAccessories" name="haveAccessories" value="${contractConfig.haveAccessories }" />附件资料：</span>
			<div>
			<form:hidden path="contractConfigAttachmentList[2].attachmentType" value="3"/>
				<h7>可上传</h7>&nbsp;
				<form:input path="contractConfigAttachmentList[2].maxCount" id="must1" value="${contractConfig.contractConfigAttachmentList[2].maxCount}" onKeyUp="value=value.replace(/[^0-9]\D*$/,'')" onchange="jiaoyans()" disabled="true" htmlEscape="false" maxlength="64" style="width:30px;" class="required"/>
				&nbsp;<h7>个附件。（支持图片，PDF）</h7>&nbsp;
				&nbsp;<h7>必填：</h7>&nbsp;
				<form:input path="contractConfigAttachmentList[2].mustCount" id="must2" value="${contractConfig.contractConfigAttachmentList[2].mustCount}" onKeyUp="value=value.replace(/[^0-9]\D*$/,'')" onchange="jiaoyan()" disabled="true" htmlEscape="false" maxlength="64" style="width:30px;" class="required"/>
				&nbsp;<h7>个附件。</h7>&nbsp;<span class="help-inline"><font color="red"  id="must12"></font></span>
			</div>
		</td></tr>
		
<!-- 		<tr><td colspan="2"> -->
<!-- 			<span class="control-label">提示文案：</span> -->
<!-- 			<div> -->
<%-- 				<form:input path="remarks" value="${contractConfig.remarks }" htmlEscape="false" maxlength="64" class="input-xlarge"/> --%>
<!-- 			</div> -->
<!-- 		</td></tr> -->
		
		
		</table>	
			
			<form:hidden path="allowOpenAccount" htmlEscape="false" maxlength="1" value="1"  class="input-xlarge required"/>
			
			<div class="form-actions">
				<shiro:hasPermission name="contract:contractConfig:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return validateForm(this)" value="保 存"/>&nbsp;</shiro:hasPermission>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</div>
	</form:form>
</body>
</html>