<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>接待申请管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			autoSum(); //金额汇总
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
			
			//动态列表全选监控事件委托
			$("input[name='box']").live("click",function(){
				allchk();
			});
			var count=0;
			var index=0;
			if($("#demandBudgetDetails").find("tbody tr").length!=0){
				index=$("#demandBudgetDetails").find("tbody tr").length;
			}
			
			//动态列表全选事件监听
			$(document).on("click","#checkAll",function(){
				checkAll();
			});
			function PrefixInteger(num, length) {
				return (Array(length).join('0') + num).slice(-length);
			}
			//动态列表行添加事件
			$("#addButton").bind("click", function() {
				//获取目前最大下标
				count = $("#demandBudgetDetails").find("tbody tr").length;
				
				if(count > 30){
					alert("当前最多只能添加30条明细数据!");
					return;
				}
				
				var str = "<tr id='tr"+index+"'>" + 
				"<td style=\"text-align:center;\">" + 
				"<input type=\"checkbox\" name=\"box\" value=\""+index+"\" /></td>" + 
				"</td>" + 
				"<td>" + 
					"<input id=\"startDate"+index+"\" name=\"demandBudgetList["+index+"].startDate\" type=\"text\" readOnly=\"readOnly\" class=\"input-mini required\" onClick=\"WdatePicker({maxDate:'#F{$dp.$D(\\'endDate"+index+"\\')}'})\"/>" + 
				"</td>" + 
				"<td>" + 
				"<input id=\"demandBudgetList["+index+"].startPointName\" name=\"demandBudgetList["+index+"].startPointName\" readOnly=\"readOnly\" type=\"text\" maxlength=\"32\" class=\"input-mini required\"/>" +
				"<input id=\"demandBudgetList["+index+"].startPoint\" name=\"demandBudgetList["+index+"].startPoint\" readOnly=\"readOnly\" type=\"hidden\" maxlength=\"32\" class=\"input-mini required\"/>" +
				"</td>" + 
				"<td>" + 
					"<input id=\"endDate"+index+"\" name=\"demandBudgetList["+index+"].endDate\" type=\"text\" readOnly=\"readOnly\" class=\"input-mini required\" onClick=\"WdatePicker({minDate:'#F{$dp.$D(\\'startDate"+index+"\\')}'})\"/>" + 
				"</td>" + 
				"<td>" + 
					/* "<input name=\"expenseDetail["+index+"].endPoint\" type=\"text\" maxlength=\"32\" class=\"input-mini required\"/>" + */ 
					"<input id=\"demandBudgetList["+index+"].endPointName\" name=\"demandBudgetList["+index+"].endPointName\" readOnly=\"readOnly\" type=\"text\" maxlength=\"32\" class=\"input-mini required\"/>" +
					"<input id=\"demandBudgetList["+index+"].endPoint\" name=\"demandBudgetList["+index+"].endPoint\" readOnly=\"readOnly\" type=\"hidden\" maxlength=\"32\" class=\"input-mini required\"/>" +
				"</td>" + 
				"<td>" + 
					"<select name=\"demandBudgetList["+index+"].firstSub\"  maxlength=\"32\" class=\"input-small required\"/>" + 
				"</td>" + 
				"<td>" + 
					"<select name=\"demandBudgetList["+index+"].secondSub\"  maxlength=\"32\" class=\"input-small\"/>" + 
				"</td>" + 
				"<td>" + 
					"<input name=\"demandBudgetList["+index+"].personNum\" type=\"number\" maxlength=\"5\" class=\"input-mini required\"/>" + 
				"</td>" + 
				"<td>" + 
					"<input name=\"demandBudgetList["+index+"].dayNum\" type=\"number\" maxlength=\"5\" class=\"input-mini required\"/>" + 
				"</td>" + 
				"<td>" + 
					"<input count=\"expenseAmt\" name=\"demandBudgetList["+index+"].expenseAmt\" type=\"number\" step=\"0.01\" maxlength=\"32\" class=\"input-mini required\"/>" + 
				"</td>" + 
				"<td>" + 
					"<input name=\"demandBudgetList["+index+"].remarks\" type=\"text\" maxlength=\"300\" class=\"input-mini\"/>" + 
				"</td>" + 
			"</tr>";
				
				$("#demandBudgetDetails tbody").append(str);
				$("select").select2();
				initSelectInfo(index); //加载科目字段数据源
				initPointTree(index); //加载区域字段数据源
				autoSum();
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
					alert("请选择要删除的报销信息！");
				}
				countSum();
			});
			loadSelectTree(); //默认加载科目树
			initPointSelectInfo(); //加载城市类别
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
		}
		
		//全选按钮
		function checkAll(){
			if($("input[name='checkAll']").prop('checked')){
				$("input[name='box']").prop("checked", true);
			}else{
				$("input[name='box']").prop("checked", false);
			}
		}
		
		//新增加载科目select
		var subjectAttach = {};
		function initSelectInfo(num){
			$("select[name='demandBudgetList["+num+"].firstSub']").each(function(){
				var this_ = $(this);
				this_.empty(); 
				var param = {};
				param["isFirst"] = "1";
				$.ajax({
			        url:"${ctx}/flow/expenseFlow/subInfoList",//请求地址
			        data:param,
			        type:'post',
			        datatype:'json',
			        success:function(data){
			        	data = JSON.parse(data);
			        	this_.append("<option value='' selected>请选择</option>");
			        	for(var i = 0;i<data.length;i++){
			        		this_.append("<option value='"+data[i]["value"]+"'>"+data[i]["name"]+"</option>");
			        	}
			        }
			    });
			    this_.on("change",function(){ //一级改变时加载二级科目
			    	param["isFirst"] = "0";
			    	param["parSubCode"] = this_.val(); //一级科目编码
			    	$.ajax({
				        url:"${ctx}/flow/expenseFlow/subInfoList",//请求地址
				        data:param,
				        type:'post',
				        datatype:'json',
				        success:function(data){
				        	data = JSON.parse(data);
				        	var senondSelect = $("select[name='demandBudgetList["+num+"].secondSub']");
				        	senondSelect.empty(); 
				        	senondSelect.val("").select2();
				        	senondSelect.append("<option value='' selected>请选择</option>");
				        	for(var i = 0;i<data.length;i++){
				        		if(data[i]["subConfList"] != null){
					        		subjectAttach[data[i]["value"]] = data[i]["subConfList"];
				        		}
				        		senondSelect.append("<option value='"+data[i]["value"]+"' confObj='"+data[i]["subConfList"]+"'>"+data[i]["name"]+"</option>");
				        	}
				        	if(data.length > 0){
				        		senondSelect.addClass("required");
				        	}else{
				        		senondSelect.removeClass("required");
				        	}
				        }
				    });	
			    });
			});
		}
		
		//加载区域下拉框选项
		function initPointTree(num){
				$("input[name='demandBudgetList["+num+"].startPointName'],input[name='demandBudgetList["+num+"].endPointName").click(function(){
					var inputName = $(this).attr("name");
					// 正常打开	
					top.$.jBox.open("iframe:${ctx}/tag/treeselectArea?url=/sys/area/treeData&module=&checked=&extId=&isAll=", "选择区域", 300, 420, {
						ajaxData:{selectIds: $("#${id}Id").val()},buttons:{"确定":"ok", ${allowClear?"\"清除\":\"clear\", ":""}"关闭":true}, submit:function(v, h, f){
							if (v=="ok"){
								var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
								var ids = [], names = [], nodes = [];
								if ("${checked}" == "true"){
									nodes = tree.getCheckedNodes(true);
								}else{
									nodes = tree.getSelectedNodes();
								}
								for(var i=0; i<nodes.length; i++) {//<c:if test="${checked && notAllowSelectParent}">
									if (nodes[i].isParent){
										continue; // 如果为复选框选择，则过滤掉父节点
									}//</c:if><c:if test="${notAllowSelectRoot}">
									if (nodes[i].level == 0){
										top.$.jBox.tip("不能选择根节点（"+nodes[i].name+"）请重新选择。");
										return false;
									}//</c:if><c:if test="${notAllowSelectParent}">
									if (nodes[i].isParent){
										top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
										return false;
									}//</c:if><c:if test="${not empty module && selectScopeModule}">
									if (nodes[i].module == ""){
										top.$.jBox.tip("不能选择公共模型（"+nodes[i].name+"）请重新选择。");
										return false;
									}else if (nodes[i].module != "${module}"){
										top.$.jBox.tip("不能选择当前栏目以外的栏目模型，请重新选择。");
										return false;
									}//</c:if>
									ids.push(nodes[i].id);
									names.push(nodes[i].name);//<c:if test="${!checked}">
									break; // 如果为非复选框选择，则返回第一个选择  </c:if>
								}
								$("input[name='"+inputName+"']").val(names.join(","));
								if(inputName.indexOf("start") > 0){
									$("input[name='demandBudgetList["+num+"].startPoint']").val(ids.join(",").replace(/u_/ig,""));
								}
								if(inputName.indexOf("end") > 0){
									$("input[name='demandBudgetList["+num+"].endPoint']").val(ids.join(",").replace(/u_/ig,""));
								}
								
							}//<c:if test="${allowClear}">
							else if (v=="clear"){
								$("#${id}Id").val("");
								$("#${id}Name").val("");
			                }//</c:if>
							if(typeof ${id}TreeselectCallBack == 'function'){
								${id}TreeselectCallBack(v, h, f);
							}
						}, loaded:function(h){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				});
		}
		
		function initPointSelectInfo(){
			$("input[id='demandBudgetList.startPointName'],input[id='demandBudgetList.endPointName").each(function(){
				var this_ = $(this);
				this_.click(function(){
					var inputName = this_.attr("name");
					// 正常打开	
					top.$.jBox.open("iframe:${ctx}/tag/treeselectArea?url=/sys/area/treeData&module=&checked=&extId=&isAll=", "选择区域", 300, 420, {
						ajaxData:{selectIds: $("#${id}Id").val()},buttons:{"确定":"ok", ${allowClear?"\"清除\":\"clear\", ":""}"关闭":true}, submit:function(v, h, f){
							if (v=="ok"){
								var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
								var ids = [], names = [], nodes = [];
								if ("${checked}" == "true"){
									nodes = tree.getCheckedNodes(true);
								}else{
									nodes = tree.getSelectedNodes();
								}
								for(var i=0; i<nodes.length; i++) {//<c:if test="${checked && notAllowSelectParent}">
									if (nodes[i].isParent){
										continue; // 如果为复选框选择，则过滤掉父节点
									}//</c:if><c:if test="${notAllowSelectRoot}">
									if (nodes[i].level == 0){
										top.$.jBox.tip("不能选择根节点（"+nodes[i].name+"）请重新选择。");
										return false;
									}//</c:if><c:if test="${notAllowSelectParent}">
									if (nodes[i].isParent){
										top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
										return false;
									}//</c:if><c:if test="${not empty module && selectScopeModule}">
									if (nodes[i].module == ""){
										top.$.jBox.tip("不能选择公共模型（"+nodes[i].name+"）请重新选择。");
										return false;
									}else if (nodes[i].module != "${module}"){
										top.$.jBox.tip("不能选择当前栏目以外的栏目模型，请重新选择。");
										return false;
									}//</c:if>
									ids.push(nodes[i].id);
									names.push(nodes[i].name);//<c:if test="${!checked}">
									break; // 如果为非复选框选择，则返回第一个选择  </c:if>
								}
								$("input[name='"+inputName+"']").val(names.join(","));
								if(inputName.indexOf("start") > 0){
									this_.closest("tr").find("input:hidden[id='demandBudgetList.startPoint']").val(ids.join(",").replace(/u_/ig,""));
								}
								if(inputName.indexOf("end") > 0){
									this_.closest("tr").find("input:hidden[id='demandBudgetList.endPoint']").val(ids.join(",").replace(/u_/ig,""));
								}
								
							}//<c:if test="${allowClear}">
							else if (v=="clear"){
								$("input[name='"+inputName+"']").val('');
								if(inputName.indexOf("start") > 0){
									this_.closest("tr").find("input:hidden[id='demandBudgetList.startPoint']").val('');
								}
								if(inputName.indexOf("end") > 0){
									this_.closest("tr").find("input:hidden[id='demandBudgetList.endPoint']").val('');
								}
			                }//</c:if>
							if(typeof ${id}TreeselectCallBack == 'function'){
								${id}TreeselectCallBack(v, h, f);
							}
						}, loaded:function(h){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				});
			});
		}
		
		
		//编辑页面科目下拉框加载
		function loadSelectTree(){
			$("select[id='firstSub']").each(function(){
				var this_ = $(this);
				this_.empty(); 
				var firstValue = this_.closest("tr").find("input:hidden[name='firstSub_val']").val();
				var param = {};
				param["isFirst"] = "1";
				$.ajax({
				        url:"${ctx}/flow/expenseFlow/subInfoList",//请求地址
				        data:param,
				        type:'post',
				        datatype:'json',
				        success:function(data){
				        	data = JSON.parse(data);
				        	for(var i = 0;i<data.length;i++){
				        		if(data[i]["value"] == firstValue){
				        			this_.append("<option selected value='"+data[i]["value"]+"'>"+data[i]["name"]+"</option>");
				        		}else{
					        		this_.append("<option value='"+data[i]["value"]+"'>"+data[i]["name"]+"</option>");
				        		}
				        	}
				        	this_.val(firstValue).select2();
				        }
				    });
					secondSelect(this_,firstValue);
				    this_.on("change",function(){ //一级改变时加载二级科目
				    	secondSelect(this_,this_.val());
				    });
			});
		}
		
		//加载二级科目
		function secondSelect(this_,firstValue){
			var param = {};
			param["parSubCode"] = firstValue; //一级科目编码
			$.ajax({
		        url:"${ctx}/flow/expenseFlow/subInfoList",//请求地址
		        data:param,
		        type:'post',
		        datatype:'json',
		        success:function(data){
		        	data = JSON.parse(data);
		        	var secondSelect = this_.closest("tr").find("select[id='secondSub']");
		        	var secondValue = secondSelect.closest("tr").find("input:hidden[name='secondSub_val']").val();
		        	secondSelect.empty(); 
		        	for(var i = 0;i<data.length;i++){
		        		if(data[i]["value"] == secondValue){
		        			secondSelect.append("<option selected value='"+data[i]["value"]+"'>"+data[i]["name"]+"</option>");
		        		}else{
		        			secondSelect.append("<option value='"+data[i]["value"]+"'>"+data[i]["name"]+"</option>");
		        		}
		        		if(data[i]["subConfList"] != null){
			        		subjectAttach[data[i]["value"]] = data[i]["subConfList"];
		        		}
		        	}
		        	secondSelect.val(secondValue).select2();
		        }
		    });
		}
		
		//报销校验
		function expense_validate(pass){
			
			$('#flag').val(pass);
			//判断明细是否添加
			var detailLength = $("#demandBudgetDetails tbody tr").length;
			if(detailLength == 0){
				alert("请填写报销明细信息!");
				return false;
			}
			$('#saveFlag').val('no');
			$("#inputForm").submit();
		}
		
		//自动汇总报销金额和票据张数
		function autoSum(){
			$("input[count='expenseAmt']").on("blur",function(){
				countSum()
			});
		}
		
		function countSum(){
			//预算金额计算
			var expenseAmtSum = 0.00;
			$("input[count='expenseAmt']").each(function(){
				var this_ = $(this);
				expenseAmtSum += parseFloat(this_.val(),2);
			});
			$("#budgetTotal").val(expenseAmtSum.toFixed(2));
		}
		
		//删除申请单据
		function deleteFormInfo(){
			$("input,select").each(function(){
				$(this).removeClass("required");
			});
			$("#inputForm").attr("action","${ctx}/flow/recpFlow/delete").submit();
		}
		
		//清除关联项目
		function clearProject(){
			$("#projectName").val("");
			$("#projectId").val("");
			$("#projectPersonel").val("");
		}
		
		//关联项目
		function relevanceProjectFlow(){
			$.jBox("iframe:${ctx}/oa/projectInfo/relevanceList", { 
			    title: "关联项目", 
			    width: 1000, 
			    height: 500, 
			    submit: function (v, h, f){
					var projectName = h.find("iframe").contents().find("input:radio[id='checkRedio']:checked").closest("tr").find("#projectName").text();
					var projectId = h.find("iframe").contents().find("input:radio[id='checkRedio']:checked").closest("tr").find("#projectId").val();
					var projectPersonel = h.find("iframe").contents().find("input:radio[id='checkRedio']:checked").closest("tr").find("#projectPersonel").text();
					$("#projectName").val($.trim(projectName));
					$("#projectId").val($.trim(projectId));
					$("#projectPersonel").val($.trim(projectPersonel));
				},
			});
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/flow/recpFlow/">接待申请列表</a></li>
		<li class="active"><a href="${ctx}/flow/recpFlow/form?id=${recpFlow.id}">接待申请发起</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="recpFlow" action="${ctx}/flow/recpFlow/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<form:hidden path="procCode" value="${recpFlow.procCode}"/>
		<form:hidden path="procName" value="${recpFlow.procName}"/>
		<form:hidden id="saveFlag" path="saveFlag" />
		<sys:message content="${message}"/>	
		<fieldset>
			<legend style="text-align: center;">接待申请详情</legend>
			<table class="table table-striped table-bordered table-condensed">
				<tbody>
				<tr>
					<td class="tit" colspan="4" style="text-align: center;"><b>基本信息</b></td>
				</tr>
				<tr>
					<td class="tit">申请人员</td>
					<td>
						${recpFlow.applyPerName}
						<form:hidden path="applyPerCode" value="${recpFlow.applyPerCode}"/>
						<form:hidden path="applyPerName" value="${recpFlow.applyPerName}"/>
					</td>
					<td class="tit">申请日期</td>
					<td>
						<fmt:formatDate value="${recpFlow.applyTime}" pattern="yyyy-MM-dd"/>
						<input name="applyTime" type="hidden" maxlength="20" class="input-medium Wdate"
							value="<fmt:formatDate value="${recpFlow.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>	
					</td>
				</tr>
				<tr>
					<td class="tit">所属部门</td>
					<td>
						${recpFlow.office.name}
						<form:hidden path="office.id" value="${recpFlow.office.id}"/>
						<form:hidden path="office.name" value="${recpFlow.office.name}"/>	
					</td>
					<td class="tit">接待主题</td>
					<td>
						<form:input path="recpTheme" htmlEscape="false" class="input-xlarge  required"/>
					</td>
				</tr>
				<tr>
					<td class="tit">项目名称</td>
					<td >
					<form:input id="projectName" path="projectName" readonly="true" htmlEscape="false" maxlength="300" class="input-xlarge required" onclick="relevanceProjectFlow();"/>
					<form:hidden id="projectId" path="projectId"  htmlEscape="false" maxlength="300" class="input-xlarge "/>
					<a href="#" onclick="clearProject();">清除</a>
<%-- 						<form:select path="projectId" class="input-xlarge required"> --%>
<!-- 							<option value="">---请选择---</option> -->
<%-- 							<form:options items="${fns:getDictList('oa_project')}"  itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<%-- 						</form:select> --%>
					</td>
					<td class="tit">项目负责人</td>
					<td >
						<form:input path="projectPersonel" readonly="true" htmlEscape="false" class="input-xlarge required"/>
					</td>
				</tr>
				<tr>
					<td class="tit">预计接待人数</td>
					<td>
						<form:input path="recpNum" type="number" htmlEscape="false" pattern="##.##" minFractionDigits="2" class="input-xlarge  required"/>
					</td>
					<td class="tit">预计接待时间</td>
					<td>
						<input name="recpTime" type="text" readonly="readonly" maxlength="20" class="input-xlarge Wdate required"
									value="<fmt:formatDate value="${recpFlow.recpTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
				</tr>
				<tr>
					<td class="tit">预计陪客人员</td>
					<td>
						<form:select path="employees" class="input-xlarge required" multiple="true" >
									<form:options items="${employeeList}" itemLabel="name" itemValue="loginName" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="tit">预算合计</td>
					<td>
						<form:input path="budgetTotal" readonly="true" type="number" htmlEscape="false" pattern="##.##" minFractionDigits="2" class="input-xlarge  required"/>
					</td>
				</tr>
				</tbody>
			</table>
			<table id="demandBudgetDetails" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<td class="tit" colspan="12" style="text-align: center;"><b>费用预算</b></td>
					</tr>
					<tr>
						<th style="text-align:center;"><input type="checkbox" name="checkAll" id="checkAll"/>选择</th>
						<th>发生日期</th>
						<th>起点</th>
						<th>结束日期</th>
						<th>终点</th>
						<th>一级科目</th>
						<th>二级科目</th>
						<th>人数</th>
						<th>天数</th>
						<th>预算金额</th>
						<th>备注</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach items="${budgetList}" var="detail" varStatus="status">
						<tr id='tr${status.index}'> 
							<td style="text-align:center;"> 
							<input type="checkbox" name="box" value="${status.index}" /></td>
							<td>
								<input id="startDate${status.index}" name="demandBudgetList[${status.index}].startDate" type="text" maxlength="32" class="input-mini required" 
									 value="<fmt:formatDate value="${detail.startDate }" pattern="yyyy-MM-dd"/>" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate${status.index}\')}'});"/>
							</td>
							<td>
								<%-- <input name="expenseDetail[${status.index}].startPoint" type="text" maxlength="32" value="${detail.startPoint }" class="input-mini required"/> --%>
								<input id="demandBudgetList.startPointName" name="demandBudgetList[${status.index}].startPointName" value="${detail.startPointName }" readOnly="readOnly" type="text" maxlength="32" class="input-mini required"/>
								<input id="demandBudgetList.startPoint" name="demandBudgetList[${status.index}].startPoint" value="${detail.startPoint }" readOnly="readOnly" type="hidden" maxlength="32" class="input-mini required"/>
							</td>
							<td>
								<input id="endDate${status.index}" name="demandBudgetList[${status.index}].endDate" type="text" maxlength="32" class="input-mini required"
									value="<fmt:formatDate value="${detail.endDate }" pattern="yyyy-MM-dd"/>" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate${status.index}\')}'});"/>
							</td>
							<td>
								<%-- <input name="expenseDetail[${status.index}].endPoint" type="text" maxlength="32" value="${detail.endPoint }" class="input-mini required"/> --%>
								<input id="demandBudgetList.endPointName" name="demandBudgetList[${status.index}].endPointName" value="${detail.endPointName }" readOnly="readOnly" type="text" maxlength="32" class="input-mini required"/>
								<input id="demandBudgetList.endPoint" name="demandBudgetList[${status.index}].endPoint" value="${detail.endPoint }" readOnly="readOnly" type="hidden" maxlength="32" class="input-mini required"/>
							</td>
							<td>
								<select id="firstSub" name="demandBudgetList[${status.index}].firstSub"  maxlength="32" class="input-small required"/>
								<input type="hidden" name="firstSub_val" value="${detail.firstSub }"></input>
							</td>
							<td>
								<select id="secondSub" name="demandBudgetList[${status.index}].secondSub" maxlength="32" class="input-small"/>
								<input type="hidden" name="secondSub_val" value="${detail.secondSub }"></input>
							</td>
							<td>
								<input name="demandBudgetList[${status.index}].personNum" type="number" maxlength="5" value="${detail.personNum }" class="input-mini required"/>
							</td>
							<td>
								<input name="demandBudgetList[${status.index}].dayNum" type="number" maxlength="5" value="${detail.dayNum }" class="input-mini required"/>
							</td>
							<td>
								<input count="expenseAmt" name="demandBudgetList[${status.index}].expenseAmt" type="number" step="0.01" maxlength="32" value="${detail.expenseAmt }" class="input-mini required"/>
							</td>
							<td>
								<input name="demandBudgetList[${status.index}].remarks" type="text" maxlength="300" value="${detail.remarks }" class="input-mini"/>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<div>
					<input id="addButton" class="btn btn-primary" type="button" value="新增"/>
					<input id="removeButton" class="btn btn-inverse" type="button" value="删除"/>
				</div>
		</table>
		</fieldset>	
			
		<div class="form-actions">
			<shiro:hasPermission name="flow:recpFlow:edit">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="提交申请" onclick="expense_validate('yes')"/>&nbsp;
			<c:if test="${empty recpFlow.procInsId}">
				<input id="btnSubmit3" class="btn btn-primary" type="submit" value="报销保存" onclick="$('#saveFlag').val('yes')">
			</c:if>
			<c:if test="${not empty recpFlow.id}">
				<input id="btnSubmit2" class="btn btn-inverse" type="button" value="删除申请" onclick="deleteFormInfo();"/>&nbsp;
			</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<c:if test="${not empty recpFlow.id}">
			<act:histoicFlow procInsId="${recpFlow.act.procInsId}" />
		</c:if>
	</form:form>
</body>
</html>