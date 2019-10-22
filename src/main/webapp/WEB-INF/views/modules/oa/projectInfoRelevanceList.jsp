<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目信息管理</title>
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
	<form:form id="searchForm" modelAttribute="projectInfo" action="${ctx}/oa/projectInfo/relevanceList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目名称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="16" class="input-medium"/>
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
				<th>项目名称</th>
				<th>项目类型</th>
				<th>归属部门</th>
				<th>客户名称</th>
<!-- 				<th>归属区域</th> -->
				<th>项目状态</th>
				<th>上线时间</th>
				<th>项目负责人</th>
				<th>更新时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectInfo">
			<tr>
				<td>
					<input type="radio" name="checkRedio" id="checkRedio"/>
				</td>
				<input type="hidden" id="projectId" value="${projectInfo.id }"/>
				<td id="projectName">
					${projectInfo.projectName}
				</td>
				<td>
					${fns:getDictLabel(projectInfo.projectType, 'project_type', '')}
				</td>
				<td>
					${projectInfo.office.name}
				</td>
				<td>
					${projectInfo.custInfo.custName}
				</td>
<!-- 				<td id="areaName"> -->
<%-- 					${projectInfo.area.name} --%>
<!-- 				</td> -->
<%-- 				<input id="areaCode" type="hidden" value="${projectInfo.area.id}"/> --%>
				<td>
					${fns:getDictLabel(projectInfo.projectState, 'project_state', '')}
				</td>
				<td>
					<fmt:formatDate value="${projectInfo.onLineDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td id="projectPersonel">
					${projectInfo.projectLeader.name}
				</td>
				<td>
					<fmt:formatDate value="${projectInfo.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>