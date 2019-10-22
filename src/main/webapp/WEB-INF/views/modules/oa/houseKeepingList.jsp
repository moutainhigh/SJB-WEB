<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内务工作管理</title>
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oa/houseKeeping/">工作及内务总表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="houseKeeping" action="${ctx}/oa/houseKeeping/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
<!-- 				<label>开始时间：</label> -->
<!-- 				<input id="start" name="start" type="text" -->
<%-- 				value="${work.getStart() }" readonly="readonly" --%>
<!-- 				maxlength="20" class="input-medium Wdate" -->
<!-- 				onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"> -->
				
<!-- 				<label>截止时间：</label> -->
<!-- 				<input id="end" name="end" type="text" -->
<%-- 				value="${work.getEnd() }" readonly="readonly" --%>
<!-- 				maxlength="20" class="input-medium Wdate" -->
<!-- 				onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"> -->
			</li>
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>年份</th>
				<th>月份</th>
				<th>签到照片上传天数</th>
				<th>工作照片上传天数</th>
				<th>内务照片上传天数</th>
				<th>平均得分</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="work">
			<tr>
				<td>${fns:substring(work.workDate, 0, 4)}</td>
				<td>${fns:substringAfterLast(work.workDate,'-')}</td>
				<td>${work.signNum}</td>
				<td>${work.workNum}</td>
				<td>${work.innerNum}</td>
				<td><fmt:formatNumber value="${work.score}" pattern="#,##0.00"/></td>
    			<td><a href="${ctx}/oa/houseKeeping/form?userId=${work.userId}&userName=${work.userName}&workDate=${work.workDate}">操作</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>