<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>协作类型管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
			//流程结束状态默认勾选
			var month = $("#needProgresss").find("tr").length;
			for (var i = 0; i < month; i++){
				if($("input[name='needProgressList["+i+"].isEnd']").val()==1){
					$("input[name='needProgressList["+i+"].isEnd']").prop("checked", true);
				}
				if($("input[name='needProgressList["+i+"].isSelected']").val()==1){
					$("input[name='needProgressList["+i+"].isSelected']").prop("checked", true);
				}
			};
			//新建自动添加模版
			var count=0;
			var index=0;
			if($("input[name='id']").val() == '' && ${fns:getDictValue('系统模板状态', 'flag', '')=='1'}){
				var str = 
					"<tr id='tr0'>" + 
					"<td style=\"text-align:center;\">" + 
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[0].progressName\" type=\"text\" readonly=\"readonly\" value=\"需求提出\"  maxlength=\"30\" class=\"input-mini\"/>" +
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[0].isSelected\" type=\"radio\" onclick=\"radioChange(this);\" class=\"input-mini\"/>设为默认状态" +
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[0].isEnd\" type=\"checkbox\" onclick=\"this.value=(this.value==1)\?0:1\" class=\input-mini\"/>设为流程结束状态" +
					"</td>" +
					"</tr>" +
					
					"<tr id='tr1'>" + 
					"<td style=\"text-align:center;\">" + 
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[1].progressName\" type=\"text\" readonly=\"readonly\" value=\"需求审核中\"  maxlength=\"30\" class=\"input-mini\"/>" +
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[1].isSelected\" type=\"radio\" onclick=\"radioChange(this);\" class=\"input-mini\"/>设为默认状态" +
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[1].isEnd\" type=\"checkbox\" onclick=\"this.value=(this.value==1)\?0:1\" class=\input-mini\"/>设为流程结束状态" +
					"</td>" +
					"</tr>" +
					
					"<tr id='tr2'>" + 
					"<td style=\"text-align:center;\">" + 
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[2].progressName\" type=\"text\" readonly=\"readonly\" value=\"需求调研中\"  maxlength=\"30\" class=\"input-mini\"/>" +
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[2].isSelected\" type=\"radio\" onclick=\"radioChange(this);\" class=\"input-mini\"/>设为默认状态" +
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[2].isEnd\" type=\"checkbox\" onclick=\"this.value=(this.value==1)\?0:1\" class=\input-mini\"/>设为流程结束状态" +
					"</td>" +
					"</tr>" +
					
					"<tr id='tr3'>" + 
					"<td style=\"text-align:center;\">" + 
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[3].progressName\" type=\"text\" readonly=\"readonly\" value=\"产品设计中\"  maxlength=\"30\" class=\"input-mini\"/>" +
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[3].isSelected\" type=\"radio\" onclick=\"radioChange(this);\" class=\"input-mini\"/>设为默认状态" +
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[3].isEnd\" type=\"checkbox\" onclick=\"this.value=(this.value==1)\?0:1\" class=\input-mini\"/>设为流程结束状态" +
					"</td>" +
					"</tr>" +
					
					"<tr id='tr4'>" + 
					"<td style=\"text-align:center;\">" + 
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[4].progressName\" type=\"text\" readonly=\"readonly\" value=\"开发中\"  maxlength=\"30\" class=\"input-mini\"/>" +
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[4].isSelected\" type=\"radio\" onclick=\"radioChange(this);\" class=\"input-mini\"/>设为默认状态" +
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[4].isEnd\" type=\"checkbox\" onclick=\"this.value=(this.value==1)\?0:1\" class=\input-mini\"/>设为流程结束状态" +
					"</td>" +
					"</tr>" +
					
					"<tr id='tr5'>" + 
					"<td style=\"text-align:center;\">" + 
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[5].progressName\" type=\"text\" readonly=\"readonly\" value=\"上线\"  maxlength=\"30\" class=\"input-mini\"/>" +
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[5].isSelected\" type=\"radio\" onclick=\"radioChange(this);\" class=\"input-mini\"/>设为默认状态" +
					"</td>" +
					"<td style=\"text-align:center;\">" +
						"<input name=\"needProgressList[5].isEnd\" type=\"checkbox\" onclick=\"this.value=(this.value==1)\?0:1\" class=\input-mini\"/>设为流程结束状态" +
					"</td>" +
					"</tr>" ;
					$("#needProgresss").append(str);
			}
			//动态列表全选监控事件委托
			$("input[name='box']").live("click",function(){
				allchk();
			});
			//动态列表全选事件监听
			$(document).on("click","#checkAll",function(){
				checkAll();
			});
			function PrefixInteger(num, length) {
				return (Array(length).join('0') + num).slice(-length);
			};
			//动态列表行添加事件
			$("#addButton").bind("click", function() {
				//获取目前最大下标
				count = $("#needProgresss").find("tr").length;
				
				if(count > 30){
					alert("当前最多只能添加30条明细数据!");
					return;
				}
				index = count;
				var str = 
					
				"<tr id='tr"+index+"'>" + 
				 
				"<td style=\"text-align:center;\">" + 
				"<input type=\"checkbox\" name=\"box\" value=\""+index+"\" /></td>" +
				"<td style=\"text-align:center;\">" +
					"<input name=\"needProgressList["+index+"].progressName\" type=\"text\" maxlength=\"30\" onchange=\"jiaoyan("+index+");\" class=\"input-mini  required\"/>" +
				"</td>" +
				"<td style=\"text-align:center;\">" +
					"<input name=\"needProgressList["+index+"].isSelected\" type=\"radio\" onclick=\"radioChange(this);\" class=\"input-mini \"/>设为默认状态" +
				"</td>" +
				"<td style=\"text-align:center;\">" +
					"<input name=\"needProgressList["+index+"].isEnd\" type=\"checkbox\" onclick=\"this.value=(this.value==1)\?0:1\" class=\input-mini\"/>设为流程结束状态" +
				"</td>" +
				"<span class=\"help-inline\"><font color=\"red\" name=\"xiao"+index+"\"></font> </span>" +
				"</tr>";
				
				$("#needProgresss").append(str);
				index++;
			});
			//动态列表行删除事件
			$("#removeButton").bind("click", function() {
				var ids = new Array();
				$("input[name='box']:checked").each(function(){
					if ($(this).val()!='') {
						ids[ids.length] = $(this).val();
						$("#tr"+$(this).val()).remove();
					}
				});
				if(ids == null || ids == ""){
					alert("请勾选要删除的类型！");
				};
				if($("#needProgresss").find("tr").length < 7){
					$("input[name='checkAll']").prop("checked", false);
				}
				
			});
			//判断是否自动勾选全选按钮
			function allchk(){ 
			    var chknum = $("input[name='box']").size();//选项总个数 
			    var chk = 0; 
			    $("input[name='box']").each(function () {
			    	if ($(this).prop('checked')) {
			            chk++; 
			        } 
			    }); 
			    if(chknum==chk){//全选 
			    	$("input[name='checkAll']").prop("checked", true);
			    }else{//不全选 
			    	$("input[name='checkAll']").prop("checked", false);
			    } 
			};
		
			//全选按钮
			function checkAll(){
				if($("input[name='checkAll']").prop('checked')){
					$("input[name='box']").prop("checked", true);
				}else{
					$("input[name='box']").prop("checked", false);
				}
			};
		});
		
		
		function radioChange(select){
			var month = $("#needProgresss").find("tr").length;
			for (var i = 0; i < month; i++){
				$("input[name='needProgressList["+i+"].isSelected']").prop("checked", false);
				$("input[name='needProgressList["+i+"].isSelected']").val("0");
			};
			select.checked = true;
			select.value = 1;
		};
		
		//校验
		function jiaoyan(indexs){
			debugger;
			var month = $("#needProgresss").find("tr").length;
			var values = $("input[name='needProgressList["+indexs+"].progressName']").val();
			var ss = 0;
			for (var i = 0; i < month; i++){
				if(indexs != i){
					if($("input[name='needProgressList["+i+"].progressName']").val() == values){
						ss = 1;
					}
				}
			};
			if(ss == 1){
				alert('进度名称不能重复');
			}
			
		};
		
		
		function jiaoyanForm(){
			debugger;
			var members = 0;
			var counts = 0;
			var ss = $("#needProgresss").find("tr").length;
			for(var i = 0; i < ss; i++){
				if($("input[name='needProgressList["+i+"].isSelected']").val() == 1){
					members++;
				}
				if($("input[name='needProgressList["+i+"].isEnd']").val() == 1){
					counts++;
				}
			}
			if(parseInt(members) < 1){
				alert("默认进度状态不能为空！");
				return false;
			}
			if(parseInt(counts) < 1){
				alert("流程结束状态不能为空！");
				return false;
			}
			
	    };
		
		
	</script>
</head>




<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/needType/">协作类型管理列表</a></li>
		<li class="active"><a href="${ctx}/oa/needType/form?id=${needType.id}">协作类型管理<shiro:hasPermission name="oa:needType:edit">${not empty needType.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:needType:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="needType" action="${ctx}/oa/needType/save" method="post" class="form-horizontal">
<%-- 		<form:hidden id="typeId" path="id"/> --%>
		<input type="hidden" id="id" name="id" value="${needType.id}"/>
		<input type="hidden" id="flag" name="flag" value="${needType.flag}"/>
		<input type="hidden" id="oldTypeName" name="oldTypeName" value="${needType.typeName}"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">类型名称：</label>
			<div class="controls">
				<form:input path="typeName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
			<table width="40%" border="1" class="required">
				<tr>
					<th width="30" style="text-align:center;"><input type="checkbox" name="checkAll" id="checkAll"/>选择</th>
					<th width="30">进度名称</th>
					<th width="30">默认状态</th>
					<th width="30">流程结束状态</th>
<!-- 					<td></td> -->
				</tr>
				<tbody id="needProgresss">
				
				<c:set var="flag" value="${needType.flag }"/>
				<c:choose>
					<c:when test="${flag == '0'}">
						<c:forEach items="${needType.needProgressList}" var="detail" varStatus="status">
							<tr id='tr${status.index}'> 
								<td style="text-align:center;"> 
								<input type="checkbox" name="box" value="${status.index}" /></td>
								<td  style="text-align:center;">
									<input name="needProgressList[${status.index}].progressName" type="text" maxlength="300" value="${detail.progressName }" onchange="jiaoyan(${status.index});" class="input-mini  required"/>
								</td>
								<td  style="text-align:center;">
									<input name="needProgressList[${status.index}].isSelected" type="radio" maxlength="300" onclick="radioChange(this);" value="${detail.isSelected }" class="input-mini"/>设为默认状态
								</td>
								<td  style="text-align:center;">
									<input name="needProgressList[${status.index}].isEnd" type="checkbox" onclick="this.value=(this.value==1)?0:1" maxlength="300" value="${detail.isEnd}" class="input-mini"/>设为流程结束状态
								</td>
								<span class="help-inline"><font color="red" name="xiao${status.index}"></font> </span>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
				
					<c:forEach items="${needType.needProgressFirst}" var="detail" varStatus="status">
						<tr id='tr${status.index}'> 
							<td style="text-align:center;"> 
							<td  style="text-align:center;">
								<input name="needProgressList[${status.index}].progressName" type="text" readonly="readonly" maxlength="300" value="${detail.progressName }" onchange="jiaoyan(${status.index});" class="input-mini  required"/>
							</td>
							<td  style="text-align:center;">
								<input name="needProgressList[${status.index}].isSelected" type="radio" maxlength="300" onclick="radioChange(this);" value="${detail.isSelected }" class="input-mini"/>设为默认状态
							</td>
							<td  style="text-align:center;">
								<input name="needProgressList[${status.index}].isEnd" type="checkbox" onclick="this.value=(this.value==1)?0:1" maxlength="300" value="${detail.isEnd}" class="input-mini"/>设为流程结束状态
							</td>
							<span class="help-inline"><font color="red" name="xiao${status.index}"></font> </span>
						</tr>
					</c:forEach>
					<c:forEach items="${needType.needProgressSecond}" var="detail" varStatus="status">
						<tr id='tr${status.index + 6}'> 
							<td style="text-align:center;"> 
							<input type="checkbox" name="box" value="${status.index + 6}" /></td>
							<td  style="text-align:center;">
								<input name="needProgressList[${status.index + 6}].progressName" type="text" maxlength="300" value="${detail.progressName }" onchange="jiaoyan(${status.index + 6});" class="input-mini  required"/>
							</td>
							<td  style="text-align:center;">
								<input name="needProgressList[${status.index + 6}].isSelected" type="radio" maxlength="300" onclick="radioChange(this);" value="${detail.isSelected }" class="input-mini"/>设为默认状态
							</td>
							<td  style="text-align:center;">
								<input name="needProgressList[${status.index + 6}].isEnd" type="checkbox" onclick="this.value=(this.value==1)?0:1" maxlength="300" value="${detail.isEnd}" class="input-mini"/>设为流程结束状态
							</td>
							<span class="help-inline"><font color="red" name="xiao${status.index + 6}"></font> </span>
						</tr>
					</c:forEach>
					
				</c:otherwise>
			</c:choose>
					
					
				</tbody>
				<div>
				<br>
					<input id="addButton" class="btn btn-primary" type="button" value="新增"/>
					<input id="removeButton" class="btn btn-inverse" type="button" value="删除"/><br><br>
				</div>
			</table>
		
		
		<div class="form-actions">
			<shiro:hasPermission name="oa:needType:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"  onclick="return jiaoyanForm();" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>

</body>

</html>