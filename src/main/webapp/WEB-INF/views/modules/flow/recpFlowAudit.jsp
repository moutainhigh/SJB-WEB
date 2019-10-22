<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>接待申请管理</title>
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
		<li class="active"><a href="#"><shiro:hasPermission name="flow:recpFlow:edit">${recpFlow.act.taskName}</shiro:hasPermission><shiro:lacksPermission name="flow:recpFlow:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="recpFlow" action="${ctx}/flow/recpFlow/saveAudit" method="post" class="form-horizontal">
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
		<sys:message content="${message}"/>
		<fieldset>
			<legend style="text-align: center;">接待申请详情</legend>
			<table class="table table-striped table-bordered table-condensed">
				<tr>
					<td class="tit" colspan="4" style="text-align: center;"><b>基本信息</b></td>
				</tr>
				<tr>
					<td class="tit">流程编号</td><td>${recpFlow.procCode}</td>
					<td class="tit">流程名称</td><td>${recpFlow.procName}</td>
				</tr>
				<tr>
					<td class="tit">申请人员</td><td>${recpFlow.applyPerName}</td>
					<td class="tit">申请日期</td><td><fmt:formatDate value="${recpFlow.applyTime}" pattern="yyyy-MM-dd"/></td>
				</tr>
				<tr>
					<td class="tit">所属部门</td>
					<td >${recpFlow.office.name}</td>
					<td class="tit">接待主题</td>
					<td>${recpFlow.recpTheme}</td>
				</tr>
				<tr>
					<td class="tit">项目名称</td>
					<td>${recpFlow.projectName}</td>
					<td class="tit">项目负责人</td>
					<td >${recpFlow.projectPersonel}</td>
				</tr>
				<tr>
					<td class="tit">预计接待人数</td>
					<td>${recpFlow.recpNum }</td>
					<td class="tit">预计接待时间</td>
					<td>
						<fmt:formatDate value="${recpFlow.recpTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td class="tit">预计陪客人员</td>
					<td>
						<form:select path="employees" class="input-xlarge required" multiple="true" disabled="true">
							<form:options items="${employeeList}" itemLabel="name" itemValue="loginName" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="tit">预算合计</td>
					<td>
						<fmt:formatNumber value="${recpFlow.budgetTotal}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber>
					</td>
				</tr>
			</table>
			<table id="budgetDetails" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<td class="tit" colspan="12" style="text-align: center;"><b>报销明细</b></td>
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
						<th>报销金额</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${budgetList}" var="detail" varStatus="status">
						<tr> 
							<td>
								<fmt:formatDate value="${detail.startDate }" pattern="yyyy-MM-dd"/>
							</td>
							<td>
								${detail.startPointName }
							</td>
							<td>
								<fmt:formatDate value="${detail.endDate }" pattern="yyyy-MM-dd"/>
							</td>
							<td>
								${detail.endPointName }
							</td>
							<td>
								${detail.firstSubName }
							</td>
							<td>
								${detail.secondSubName }
							</td>
							<td>
								${detail.personNum }
							</td>
							<td>
								${detail.dayNum }
							</td>
							<td>
								<fmt:formatNumber value="${detail.expenseAmt }" pattern="##.##" minFractionDigits="2"></fmt:formatNumber>
							</td>
							<td>
								${detail.remarks }
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<act:histoicFlow procInsId="${recpFlow.act.procInsId}" />
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
			<shiro:hasPermission name="flow:recpFlow:edit">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="同 意" onclick="commentClick('yes')"/>&nbsp;
				<input id="btnSubmit" class="btn btn-inverse" type="button" value="驳 回" onclick="commentClick('no')"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
