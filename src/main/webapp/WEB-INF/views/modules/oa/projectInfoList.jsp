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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oa/projectInfo/">项目信息列表</a></li>
		<shiro:hasPermission name="oa:projectInfo:edit"><li><a href="${ctx}/oa/projectInfo/form">项目信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="projectInfo" action="${ctx}/oa/projectInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目名称</label>
				<form:input path="projectName" htmlEscape="false" maxlength="90" class="input-medium"/>
			</li>
			<li><label>项目类别</label>
				<form:select path="projectType" class="input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('project_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>归属部门</label>
				<sys:treeselect id="office" name="office.id" value="${projectInfo.office.id}" labelName="${projectInfo.office.name}" labelValue="${projectInfo.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目名称</th>
				<th>项目类型</th>
				<th>归属部门</th>
				<th>客户名称</th>
<!-- 				<th>归属区域</th> -->
				<th>项目状态</th>
				<th>上线时间</th>
				<th>项目负责人</th>
				<th>更新时间</th>
				<shiro:hasPermission name="oa:projectInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectInfo">
			<tr>
				<td><a href="${ctx}/oa/projectInfo/form?id=${projectInfo.id}">
					${projectInfo.projectName}
				</a></td>
				<td>
					${fns:getDictLabel(projectInfo.projectType, 'project_type', '')}
				</td>
				<td>
					${projectInfo.office.name}
				</td>
				<td>
					${projectInfo.custInfo.custName}
				</td>
<!-- 				<td> -->
<%-- 					${projectInfo.area.name} --%>
<!-- 				</td> -->
				<td>
					${fns:getDictLabel(projectInfo.projectState, 'project_state', '')}
				</td>
				<td>
					<fmt:formatDate value="${projectInfo.onLineDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${projectInfo.projectLeader.name}
				</td>
				<td>
					<fmt:formatDate value="${projectInfo.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="oa:projectInfo:edit"><td>
    				<a href="${ctx}/oa/projectInfo/form?id=${projectInfo.id}">修改</a>
					<a href="${ctx}/oa/projectInfo/close?id=${projectInfo.id}" onclick="return confirmx('确认要关闭该项目吗？', this.href)">关闭</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>