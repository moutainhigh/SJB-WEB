<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>市场需求管理</title>
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
		
		//删除申请
		function deleteFormInfo(){
			$("input,select").each(function(){
				$(this).removeClass("required");
			});
			$("#inputForm").attr("action","${ctx}/oa/oaDemandManagement/delete").submit();
		}
		
		//清除关联项目
		function clearProject(){
			$("#projectName").val("");
			$("#projectId").val("");
// 			$("#areaName").val("");
// 			$("#areaCode").val("");
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
// 					var areaName = h.find("iframe").contents().find("input:radio[id='checkRedio']:checked").closest("tr").find("#areaName").text();
// 					var areaCode = h.find("iframe").contents().find("input:radio[id='checkRedio']:checked").closest("tr").find("#areaCode").val();
					$("#projectName").val($.trim(projectName));
					$("#projectId").val($.trim(projectId));
// 					$("#areaName").val($.trim(areaName));
// 					$("#areaCode").val($.trim(areaCode));
				},
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/oaDemandManagement/">需求申请列表</a></li>
		<li class="active"><a href="${ctx}/oa/oaDemandManagement/form?id=${demandManagement.id}">需求申请<shiro:hasPermission name="oa:oaDemandManagement:edit">${not empty demandManagement.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaDemandManagement:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="demandManagement" action="${ctx}/oa/oaDemandManagement/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden id="saveFlag" path="saveFlag" />
		<form:hidden path="billType" value="1"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden path="procCode"/>
		<sys:message content="${message}"/>		
		<fieldset>
			<legend style="text-align: center;">需求申请</legend>
			<table class="table table-striped table-bordered table-condensed">
				<tbody>
					<tr>
						<td class="tit" colspan="4" style="text-align: center;"><b>基本信息</b></td>
					</tr>
					<tr>
						<td class="tit">申请人名称</td>
						<td>
							${demandManagement.applyPerName}
							<form:hidden path="applyPerCode" value="${demandManagement.applyPerCode}"/>
							<form:hidden path="applyPerName" value="${demandManagement.applyPerName}"/>
						</td>
						<td class="tit">所属部门</td>
						<td>
							${demandManagement.office.name}
							<form:hidden path="office.id" value="${demandManagement.office.id}"/>
							<form:hidden path="officeName" value="${demandManagement.office.name}"/>
							<form:hidden path="postCode" value="${demandManagement.postCode}"/>	
							<form:hidden path="postName" value="${demandManagement.postName}"/>		
						</td>
					</tr>
					<tr>
						<td class="tit">需求名称</td>
						<td>
								<form:input path="demandName" htmlEscape="false" maxlength="300" class="input-xlarge required"/>
						</td>
						<td class="tit">需求类型</td>
						<td>
							<form:select path="demandType" class="input-xlarge required">
								<option value="">---请选择---</option>
							<form:options items="${fns:getDictList('oa_demand_type')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						</td>
					</tr>
					<tr>
						<td class="tit">归属项目</td>
						<td>
						<form:input id="projectName" path="projectName" readonly="true" htmlEscape="false" maxlength="300" class="input-xlarge" onclick="relevanceProjectFlow();"/>
						<form:hidden id="projectId" path="projectId"  htmlEscape="false" maxlength="300" class="input-xlarge "/>
						<a href="#" onclick="clearProject();">清除</a>
						</td>
						<td class="tit">需求人数</td>
						<td>
								<form:input path="demandPersonelNum" type="number" htmlEscape="false" maxlength="300" class="input-xlarge required"/>
						</td>
					</tr>
					<tr>
						<td class="tit">期望抵达日期</td>
						<td>
								<input name="expectDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
									value="<fmt:formatDate value="${demandManagement.expectDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
						</td>
						<td class="tit">预计时长</td>
						<td>
								<form:input path="timeLong" type="number" htmlEscape="false" maxlength="300" class="input-xlarge required"/>
						</td>
					</tr>
					<tr>
						<td class="tit">预算总金额</td>
						<td>
								<form:input path="amountSum" type="number" htmlEscape="false" class="input-xlarge required"/>
						</td>
						<td></td><td></td>
					</tr>
					<tr>
						<td class="tit">备注</td>
						<td colspan="3">
								<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge required"/>
						</td>
					</tr>
				</tbody>
			</table>
			
			
		</fieldset>
		
		<div class="form-actions">
			<shiro:hasPermission name="oa:oaDemandManagement:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="提交申请"/>&nbsp;</shiro:hasPermission>
			<shiro:hasPermission name="oa:oaDemandManagement:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="$('#saveFlag').val('yes')"/>&nbsp;</shiro:hasPermission>
			<c:if test="${not empty demandManagement.id}">
				<input id="btnSubmit2" class="btn btn-inverse" type="button" value="删除申请" onclick="deleteFormInfo();"/>&nbsp;
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>