<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>费用报销管理</title>
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
		<li class="active"><a href="#"><shiro:hasPermission name="flow:expenseFlow:edit">${expenseFlow.act.taskName}</shiro:hasPermission><shiro:lacksPermission name="flow:expenseFlow:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="expenseFlow" action="${ctx}/flow/expenseFlow/saveBringRemitInfo" method="post" class="form-horizontal">
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
		<form:hidden path="procCode"/>
		<sys:message content="${message}"/>
		<fieldset>
			<legend style="text-align: center;">费用报销详情</legend>
			<table class="table table-striped table-bordered table-condensed">
				<tr>
					<td class="tit" colspan="4" style="text-align: center;"><b>基本信息</b></td>
				</tr>
				<tr>
					<td class="tit">流程编号</td><td>${expenseFlow.procCode}</td>
					<td class="tit">流程名称</td><td>${expenseFlow.procName}</td>
				</tr>
				<tr>
					<td class="tit">报销人员</td><td>${expenseFlow.applyPerName}</td>
					<td class="tit">报销日期</td><td><fmt:formatDate value="${expenseFlow.applyTime}" pattern="yyyy-MM-dd"/></td>
				</tr>
				<tr>
					<td class="tit">所属部门</td>
					<td >${expenseFlow.office.name}</td>
					<td class="tit">所属岗位</td>
					<td>${postName }</td>
				</tr>
				<tr>
					<td class="tit">票据数量</td><td>${expenseFlow.billNum}</td>
					<td class="tit">费用合计</td>
					<td>
						<fmt:formatNumber value="${expenseFlow.expenseTotal}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber>
					</td>
				</tr>
				<tr>
					<td class="tit">项目名称</td>
					<td>${expenseFlow.projectName}</td>
					<td class="tit">发票所属公司</td>
					<td>${expenseFlow.taxCityName}</td>
				</tr>
				<tr>
					<td class="tit">本次打款金额</td><td><form:input path="bringAmount" id="bringAmount" type="number" htmlEscape="false" maxlength="5" class="input-xlarge  required"/></td>
					<td colspan="2"></td>
				</tr>
			</table>
			<table id="expenseDetails" class="table table-striped table-bordered table-condensed">
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
						<th>票据张数</th>
						<th>报销金额</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${detailsList}" var="detail">
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
								${detail.billNum }
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
			<table class="table table-striped table-bordered table-condensed">
				<tr>
					<td class="tit">备注</td>
					<td>${expenseFlow.remarks}</td>
				</tr>
				<tr>
					<td>
						<c:forEach items="${expenseAttachmentList}" var="attachment">
						 <c:if test="${empty attachment.subCode}">
							 <span id="attachmentSpan">
							 	（
							 	<c:if test="${attachment.fileType == '0'}">						 		
									<a id="expenseAttachmentId" href="${attachment.expenseAttachmentUrl}" target="_blank">${attachment.fileName}</a>
							 	</c:if>
							 	<c:if test="${attachment.fileType != '0'}">						 		
									<a id="expenseAttachmentId" href="${ctx}/flow/expenseFlow/downFiles?url=${attachment.expenseAttachmentUrl}&fileName=${attachment.fileName}">${attachment.fileName}</a>
							 	</c:if>
							  	）
							  </span>
						  </c:if>
						</c:forEach>
					</td>
				</tr>
			</table>
			<table id="subjectAttachment" class="table table-striped table-bordered table-condensed">
				<c:forEach items="${subAttachmentMap}" var="attachmentMap">
					<tr style='width:20%' id='${attachmentMap.key}'>
						  <c:forEach items="${attachmentMap.value}" var="attachment">
						   <td class='shuli' id="fileTd">
						   		<span id="subImgDes">${attachment.subImgDes}</span>
						   		<input type="hidden" id="subImgConfId" value="${attachment.subImgConfId}"/>
						   		<span id="fileShow">
								 	<c:if test="${attachment.fileType == '0'}">						 		
										<a id="expenseAttachmentId" href="${attachment.expenseAttachmentUrl}" target="_blank">${attachment.fileName}</a>
								 	</c:if>
						   		</span>
			        			<input type="hidden" id="img_expenseFile" name="expenseAttachment.expenseAttachmentUrl" value="${attachment.expenseAttachmentUrl}"/>
				        		<input type="hidden" id="img_expenseFile_fileName" name="expenseAttachment.fileName" value="${attachment.fileName}"/></td>
						  </c:forEach>
				  </tr>
				</c:forEach>
			</table>
		</fieldset>
		<div class="form-actions">
			<shiro:hasPermission name="flow:expenseFlow:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
