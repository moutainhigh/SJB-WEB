<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>明细</title>
	<meta name="decorator" content="default"/>
<script type="text/javascript">
	$("#input").validate({
		submitHandler: function(form){
			if (confirm("请确认你的评分！")==true){ 
				loading('正在提交，请稍等...');
				form.submit();
			} 
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
	function test(num){  
		  var reg = /^((?!0)\d{1,2}|100)$/;  
		  if(!num.match(reg)){  
		   	return false;  
		  }else{  
		   	return true;  
		  }  
	};  
	
	
</script>

	
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/houseKeepingScore/">工作及内务总表</a></li>
		<li class="active"><a href="${ctx}/oa/houseKeepingScore/form?userId=${houseKeeping.userId}&userName=${houseKeeping.userName}&workDate=${houseKeeping.workDate}">明细</a></li>
	</ul><br/>
	<sys:message content="${message}"/>		
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
		<tr>
			<th>日期</th>
			<th>上班签到照片</th>
			<th>工作桌面照片</th>
			<th>内务环境照片</th>
			<th>综合评分</th>
		</tr>
	</thead>
	<tbody id="treeTableList">
		<c:forEach items="${list1}" var="work">
			<form:form id="btnSubmit" modelAttribute="work" action="${ctx}/oa/houseKeepingScore/score" onsubmit="return confirm('确定提交吗?')" method="post" class="form-horizontal">
			<input id="userId" name="userId" type="hidden" value="${work.userId}"/>
			<input id="userName" name="userName" type="hidden" value="${work.userName}"/>
			<input id="workDate" name="workDate" type="hidden" value="${work.workDate}"/>
			<tr>
				<td>
					${fns:substringAfterLast(work.workDate,'-')}
				</td>
				<c:choose>
						<c:when test="${ work.signUrl != null}">
							<td><a href="${fns:getParamValue('file_server_url', 'sysParam', '')}${work.signUrl}" target="_blank">签到图片</a></td>
						</c:when>
						<c:otherwise>
						<td></td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${ work.workUrl != null}">
							<td><a href="${fns:getParamValue('file_server_url', 'sysParam', '')}${work.workUrl }" target="_blank">上班图片</a></td>
						</c:when>
						<c:otherwise>
						<td></td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${ work.innerUrl != null}">
							<td><a href="${fns:getParamValue('file_server_url', 'sysParam', '')}${work.workUrl }" target="_blank">内务图片</a></td> 
						</c:when>
						<c:otherwise>
						<td></td>
						</c:otherwise>
					</c:choose>
				<td>
					<font color="blue">
					<c:choose>
						<c:when test="${work.isSign == '1' && work.score == null }">
							    <input type="hidden" name = "workId" value="${work.id }"/>
								<input id="score" name="score" type="text" onkeyup="value=test(value)?value:''" maxlength="255" required/>
								<input id="btn" type="submit" value="评分"  />
						</c:when>
						<c:when test="${work.signUrl == null || work.workUrl == null || work.innerUrl == null }">
						</c:when>
						<c:otherwise>
							<fmt:formatNumber value="${work.score}" pattern="#,##0.00"/>
						</c:otherwise>
					</c:choose>
					</font>
				</td>
			</tr>
		</form:form>	
		</c:forEach>
		<c:forEach items="${list2}" var="work">
			<tr>
				<td>
					${fns:substringAfterLast(work.workDate,'-')}
				</td>
				<td >
				</td>
				<td>
				</td>
				<td>
				</td>
				<td>
				</td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>