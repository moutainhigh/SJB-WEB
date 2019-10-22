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
		
		//打印  
		function btnPrintClick(){
        	//获取打印的div
        	var bodyHtml = $("body").html();
        	window.document.body.innerHTML = $("#printDiv").html();
        	window.print(); //打印
        	window.document.body.innerHTML = bodyHtml;
		}
		
		//导出为pdf
		function btnExportPdf(){
			var bodyHtml = $("#printDiv").html();
			 html2canvas(document.getElementById("printDiv"), {
			        // 渲染完成时调用，获得 canvas
			        onrendered: function(canvas) {
			        	//返回图片dataURL，参数：图片格式和清晰度(0-1)
		                  var pageData = canvas.toDataURL('image/jpeg', 1.0);

		                  //方向默认竖直，尺寸ponits，格式a4[595.28,841.89]
		                  var pdf = new jsPDF('', 'pt', 'a4');

		                  //addImage后两个参数控制添加图片的尺寸，此处将页面高度按照a4纸宽高比列进行压缩
		                  pdf.addImage(pageData, 'JPEG', 0, 0, 595.28, 592.28/canvas.width * canvas.height );

		                  pdf.save($("#procNameTd").text()+'.pdf');
			        },
			        background: '#FFF'
			    })
		}
		
		//流程撤回
		function canalTask(){
			$("#submitForm").attr("action","${ctx}/flow/expenseFlow/repealTask");
			$("#submitForm").submit();
		}
		
		//删除报销单据
		function deleteFormInfo(){
			$("#submitForm").attr("action","${ctx}/flow/expenseFlow/delete").submit();
		}
		
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
		
		function page(n, s) {
			$("#submitForm").submit();
			return false;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/flow/expenseFlow/">费用报销列表</a></li>
		<li class="active"><a href="${ctx}/flow/expenseFlow/form/?procInsId=${expenseFlow.procInsId}">费用报销详情</a></li>
	</ul>
	
	<form:form id="submitForm" modelAttribute="expenseFlow" action="${ctx}/flow/expenseFlow/form/?procInsId=${expenseFlow.procInsId}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden id="taskId" path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden path="act.title"/>
		<form:hidden path="act.status"/>
		<div id="printDiv">
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
					<td class="tit">报销人员</td><td>${expenseFlow.applyPerName}</td>
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
		</fieldset>
		<act:histoicFlow procInsId="${expenseFlow.procInsId}" />
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			<c:if test="${noRepeal != true}">
				<input id="btnCancel" class="btn btn-primary" type="button" value="任务撤回" onclick="canalTask();"/>
			</c:if>
			<c:if test="${delete == true}">
				<input id="btnCancel" class="btn btn-primary" type="button" value="删除申请" onclick="deleteFormInfo();"/>
			</c:if>
			<input id="btnPrint"  class="btn btn-primary" type="button" value="打印" onclick="btnPrintClick()"/>
			<input id="btnExPdf"  class="btn btn-primary" type="button" value="导出PDF" onclick="btnExportPdf()"/>
		</div>
	</form:form>
</body>
</html>
