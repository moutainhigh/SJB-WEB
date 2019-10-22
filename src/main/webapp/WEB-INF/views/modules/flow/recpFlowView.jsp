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
			$("#submitForm").attr("action","${ctx}/flow/recpFlow/repealTask");
			$("#submitForm").submit();
		}
		
		//删除报销单据
		function deleteFormInfo(){
			$("#submitForm").attr("action","${ctx}/flow/recpFlow/delete").submit();
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/flow/recpFlow/">接待申请列表</a></li>
		<li class="active"><a href="${ctx}/flow/recpFlow/form/?procInsId=${recpFlow.procInsId}">接待申请详情</a></li>
	</ul>
	
	<form:form id="submitForm" modelAttribute="recpFlow" action="" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden id="taskId" path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden path="act.title"/>
		<div id="printDiv">
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
		</fieldset>
		<act:histoicFlow procInsId="${recpFlow.procInsId}" />
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
