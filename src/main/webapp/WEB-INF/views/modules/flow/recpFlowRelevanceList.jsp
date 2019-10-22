<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>接待申请管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="recpFlow" action="${ctx}/flow/recpFlow/queryRecpFlowInfo" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>流程编号：</label>
				<form:input path="procCode" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>申请时间：</label>
				<input id="beginApplyTime" name="beginApplyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${recpFlow.beginApplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endApplyTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input id="endApplyTime" name="endApplyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${recpFlow.endApplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginApplyTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>选择</th>
				<th>流程编号</th>
				<th>接待主题</th>
				<th>申请人名称</th>
				<th>申请日期</th>
				<th>费用合计</th>
				<th>审批状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="recpFlow">
			<tr>
				<td>
					<input type="radio" name="checkRedio" id="checkRedio"/>
				</td>
				<td id="procCode">
					${recpFlow.procCode}
				</td>
				<td id="recpTheme">
					${recpFlow.recpTheme}
				</td>
				<td>
					${recpFlow.applyPerName}
				</td>
				<td>
					<fmt:formatDate value="${recpFlow.applyTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${recpFlow.budgetTotal}
				</td>
				<td>
					${recpFlow.recpStatusValue}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>