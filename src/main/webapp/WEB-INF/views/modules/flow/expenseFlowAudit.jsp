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
			$("#inputForm").attr("action","${ctx}/flow/expenseFlow/saveAudit");
			$("#inputForm").submit();
		};
		
		//科目附件上传
		function billInfoAudit(secondSub,subCode, lineNumber){
			
			var arr=new Array();
			$(secondSub).closest("td").find("input:hidden[id='subImgDes']").each(function(){
				var confDes = $(this).val();	
   				var fileName = "";
   				var showUrl = "";
   				var url = "";
   				var index = $(this).attr("index");
   					
				fileName = $(this).closest("td").find("input:hidden[name='expenseDetail["+lineNumber+"].expenseAttachment["+index+"].fileName']").val();
   				showUrl = $(this).closest("td").find("input:hidden[id='expenseDetail["+lineNumber+"].expenseAttachment["+index+"].showUrl']").val();
   				url = $(this).closest("td").find("input:hidden[name='expenseDetail["+lineNumber+"].expenseAttachment["+index+"].expenseAttachmentUrl']").val();
   				if(fileName != 'undefined'){
	   				arr.push("<ul style=\"list-style-type:none\" class='ul-form'><li><label id=\"subImgDes\">"+confDes+"</label></li>"+
	    					"<li><label id=\"fileShow\">"+
	    					"<a id=\"expenseAttachmentUrl\" href="+showUrl+" target=\"_blank\">"+fileName+"</a>"+
							"<input type=\"hidden\" name=\"imgUrl\" value="+url+" />"+
							"<input type=\"hidden\" name=\"imgName\" value="+fileName+" />"+
	    					"</label></li>"+
	    			"</ul>");
   				}else{
   					arr.push("<ul style=\"list-style-type:none\" class='ul-form'><li><label id=\"subImgDes\">"+confDes+"</label></li>"+
	    					"<li><label id=\"fileShow\">"+
							"<input type=\"hidden\" name=\"imgUrl\" value="+url+" />"+
							"<input type=\"hidden\" name=\"imgName\" value="+fileName+" />"+
	    					"</label></li>"+
	    			"</ul>");
   				}
    		});
    		var str=arr.join("");
    		console.log(str);
    		
			top.$.jBox("<div style='padding:20px;text-align:center;'>" + str +
					"</div>", {
					title: "科目附件", 
			submit: function (v, h, f){
				
			},loaded:function(h){
				
			},closed:function(){
				if (typeof closed == 'function') {
					closed();
				}
			}});
			
			return false;
		}
		
		function historyDetail(){
			var endTime = (new Date()).valueOf();
			var startTime = (new Date()).valueOf();
			$.jBox("iframe:${ctx}/oa/report/historyDetail?applyPerCode=${expenseFlow.applyPerCode}&isInit=2", { 
			    title: "即日起前三个月统计查询", 
			    width: 1500, 
			    height: 500, 
			});
		}	
		
		function page(n, s) {
			$("#inputForm").submit();
			return false;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#"><shiro:hasPermission name="flow:expenseFlow:edit">${expenseFlow.act.taskName}</shiro:hasPermission><shiro:lacksPermission name="flow:expenseFlow:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="expenseFlow" action="${ctx}/flow/expenseFlow/form" method="post" class="form-horizontal">
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
		<sys:tableSort id="orderBy" name="detailOrderBy" value="${expenseFlow.detailOrderBy}" callback="page();"/>
		<fieldset>
			<legend style="text-align: center;">费用报销详情</legend>
				<table class="table table-striped table-bordered table-condensed">
				<tr>
					<td class="tit" colspan="4" style="text-align: center;"><b>基本信息</b></td>
				</tr>
				<tr>
					<td class="tit">流程编号</td><td>${expenseFlow.procCode}</td>
					<td class="tit">流程名称</td><td id="procNameTd">${expenseFlow.procName}</td>
				</tr>
				<tr>
					<td class="tit">报销人员</td><td>${expenseFlow.applyPerName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a type="button" onclick="historyDetail()">查看历史报销统计</a></td>
					<td class="tit">报销日期</td><td><fmt:formatDate value="${expenseFlow.applyTime}" pattern="yyyy-MM-dd"/></td>
				</tr>
				<tr>
					<td class="tit">所属部门</td>
					<td>${expenseFlow.office.name}</td>
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
					<td class="tit">报销类型</td>
					<td >${expenseFlow.applyTypeName}</td>
					<td class="tit">发票所属公司</td>
					<td >${expenseFlow.taxCityName}</td>
				</tr>
				<c:if test="${not empty expenseFlow.applyType && expenseFlow.applyType == '2'}">
					<tr>
						<td class="tit">接待申请</td>
						<td >${expenseFlow.recpProcName}</td>
						<td class="tit">陪客人员</td>
						<td >
							<form:select path="employees" class="input-xlarge" multiple="true" disabled="true">
										<form:options items="${employeeList}" itemLabel="name" itemValue="loginName" htmlEscape="false"/>
							</form:select>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="tit">项目名称</td>
					<td >${expenseFlow.projectName}</td>
					<td class="tit">项目负责人</td>
					<td >${expenseFlow.projectPersonel}</td>
				</tr>
			</table>
			<table id="expenseDetails" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<td class="tit" colspan="12" style="text-align: center;"><b>报销明细</b></td>
					</tr>
					<tr>
						<th  class="sort-column start_date">发生日期</th>
						<th>起点</th>
						<th  class="sort-column end_date">结束日期</th>
						<th>终点</th>
						<th class="sort-column first_sub">一级科目</th>
						<th class="sort-column second_sub">二级科目</th>
						<th>人数</th>
						<th>天数</th>
						<th>票据张数</th>
						<th>报销金额</th>
						<th>备注</th>
						<th>更多操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${detailsList}" var="detail" varStatus="status">
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
							<td id="more">
							    <c:if test="${not empty detail.expenseAttachment}">
									<a href="#" onclick="return billInfoAudit(this,'${detail.secondSub}',${status.index});">查看附件</a>
									<c:forEach items="${detail.expenseAttachment}" var="dattachment" varStatus="st">
										<input type="hidden" index="${st.index}" id="fileName" name="expenseDetail[${status.index}].expenseAttachment[${st.index}].fileName" value="${dattachment.fileName}">
										<input type="hidden" index="${st.index}" id="subImgDes" name="expenseDetail[${status.index}].expenseAttachment[${st.index}].subImgDes" value="${dattachment.subImgDes}">
										<input type="hidden" index="${st.index}" id="expenseAttachmentUrl" name="expenseDetail[${status.index}].expenseAttachment[${st.index}].expenseAttachmentUrl" value="${dattachment.subImgUrl}">
										<input type="hidden" index="${st.index}" id="subImgConfId" name="expenseDetail[${status.index}].expenseAttachment[${st.index}].subImgConfId" value="${dattachment.subImgConfId}">
										<input type="hidden" index="${st.index}" id="expenseDetail[${status.index}].expenseAttachment[${st.index}].showUrl" value="${dattachment.expenseAttachmentUrl}">
									</c:forEach>
							    </c:if>
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
			<act:histoicFlow procInsId="${expenseFlow.act.procInsId}" />
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
			<shiro:hasPermission name="flow:expenseFlow:edit">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="同 意" onclick="commentClick('yes')"/>&nbsp;
				<input id="btnSubmit" class="btn btn-inverse" type="button" value="驳 回" onclick="commentClick('no')"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
