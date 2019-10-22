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
		
		function commentClick(pass){
			var comment = $("#actComment").val();
			if(pass == "yes"){
				$('#flag').val('yes');
			}else{
				$('#flag').val('no');
				if($.trim(comment) == ''){
					alert("请输入驳回意见!");
					return false;
				}
			}
			$("#inputForm").submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">待审批</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="demandManagement" action="${ctx}/oa/oaDemandManagement/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<form:hidden path="act.title"/>
		<form:hidden path="act.assigneeName"/>
		<form:hidden path="act.beginDate"/>
		<form:hidden path="act.endDate"/>
		<form:hidden path="billType"/>
		<sys:message content="${message}"/>		
		<fieldset>
			<legend style="text-align: center;">需求申请</legend>
			<table class="table table-striped table-bordered table-condensed">
				<tbody>
					<tr>
						<td class="tit" colspan="4" style="text-align: center;"><b>基本信息</b></td>
					</tr>
					<tr>
						<td class="tit">流程名称</td>
						<td>
							${demandManagement.procName}
						</td>
						<td class="tit">流程编号</td>
						<td>
							${demandManagement.procCode}
							<form:hidden path="procCode" value="${demandManagement.procCode}"/>
						</td>
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
							${demandManagement.demandName}
						</td>
						<td class="tit">需求类型</td>
						<td>
							${demandManagement.demandTypeName}
						</td>
					</tr>
					<tr>
						<td class="tit">归属项目</td>
						<td>
							${demandManagement.projectName}
						</td>
<!-- 						<td class="tit">归属区域</td> -->
<!-- 						<td> -->
<%-- 							${demandManagement.areaName}  --%>
<!-- 						</td> -->
						<td class="tit">需求人数</td>
						<td>
							${demandManagement.demandPersonelNum}人
						</td>
					</tr>
					<tr>
						<td class="tit">期望抵达日期</td>
						<td>
							<fmt:formatDate value="${demandManagement.expectDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td class="tit">预计时长</td>
						<td>
							${demandManagement.timeLong}
						</td>
					</tr>
					<tr>
						<td class="tit">预算总金额</td>
						<td>
							${demandManagement.amountSum}
						</td>
						<td class="tit">备注</td>
						<td colspan="3">
							${demandManagement.remarks }
						</td>
					</tr>
					<tr>
						<td class="tit">实施申请人</td>
						<td colspan="3">
							${assigneeInfo.name }-${assigneeInfo.loginName}
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
						<th>发生日期</th>
						<th>起点</th>
						<th>结束日期</th>
						<th>终点</th>
						<th>一级科目</th>
						<th>二级科目</th>
						<th>人数</th>
						<th>天数</th>
<!-- 						<th>票据张数</th> -->
						<th>预算金额</th>
						<th>备注</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach items="${budgetList}" var="detail" >
						<tr> 
							<td>
									<fmt:formatDate value="${detail.startDate}" pattern="yyyy-MM-dd"/>
							</td>
							<td>
									${detail.startPointName}
							</td>
							<td>
									<fmt:formatDate value="${detail.endDate}" pattern="yyyy-MM-dd"/>
							</td>
							<td>
									${detail.endPointName}
							</td>
							<td>
									${detail.firstSubName}
							</td>
							<td>
									${detail.secondSubName}
							</td>
							<td>
									${detail.personNum}
							</td>
							<td>
									${detail.dayNum}
							</td>
<!-- 							<td> -->
<%-- 									${detail.billNum} --%>
<!-- 							</td> -->
							<td>
									${detail.expenseAmt}
							</td>
							<td>
									${detail.remarks}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<act:histoicFlow procInsId="${demandManagement.procInsId}" 
				billType="demand" 
				executionId="${demandManagement.act.executionId}" 
				taskDefKey="${demandManagement.act.taskDefKey}"/>
			<table class="table table-striped table-bordered table-condensed">
				<tr>
					<td class="tit">您的意见</td>
					<td>
						<form:textarea id="actComment" path="act.comment" rows="5" maxlength="200" cssStyle="width:500px"/>
					</td>
				</tr>
			</table>
		</fieldset>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oaDemandManagement:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="同意" onclick="commentClick('yes')"/>&nbsp;</shiro:hasPermission>
			<shiro:hasPermission name="oa:oaDemandManagement:edit"><input id="btnSubmit" class="btn btn-inverse" type="button" value="驳 回" onclick="commentClick('no')"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>