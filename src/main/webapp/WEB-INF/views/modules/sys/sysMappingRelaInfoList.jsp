<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>映射关系信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//初始化多选按钮
			$("#synchronize").click(function(){
				$("#searchForm").attr("action","${ctx}/sys/sysMappingRelaInfo/synchronize");
				$("#searchForm").submit();
			});
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
		<li class="active"><a href="${ctx}/sys/sysMappingRelaInfo/">映射关系信息列表</a></li>
		<shiro:hasPermission name="sys:sysMappingRelaInfo:edit"><li><a href="${ctx}/sys/sysMappingRelaInfo/form">映射关系信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysMappingRelaInfo" action="${ctx}/sys/sysMappingRelaInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>映射类型：</label>
				<form:input path="mappingType" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>映射名称：</label>
				<form:input path="mappingName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>映射值：</label>
				<form:input path="mappingValue" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="synchronize" class="btn btn-primary" type="button" value="同步"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>映射类型</th>
				<th>映射名称</th>
				<th>映射值</th>
				<th>映射后对应名称</th>
				<th>映射后对应值</th>
				<th>其他</th>
				<shiro:hasPermission name="sys:sysMappingRelaInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysMappingRelaInfo">
			<tr>
				<td><a href="${ctx}/sys/sysMappingRelaInfo/form?id=${sysMappingRelaInfo.id}">
					${sysMappingRelaInfo.mappingType}
				</a></td>
				<td>
					${sysMappingRelaInfo.mappingName}
				</td>
				<td>
					${sysMappingRelaInfo.mappingValue}
				</td>
				<td>
					${sysMappingRelaInfo.mappingToName}
				</td>
				<td>
					${sysMappingRelaInfo.mappingToValue}
				</td>
				<td>
					${sysMappingRelaInfo.mappingAttr}
				</td>
				<shiro:hasPermission name="sys:sysMappingRelaInfo:edit"><td>
    				<a href="${ctx}/sys/sysMappingRelaInfo/form?id=${sysMappingRelaInfo.id}">修改</a>
					<a href="${ctx}/sys/sysMappingRelaInfo/delete?id=${sysMappingRelaInfo.id}" onclick="return confirmx('确认要删除该映射关系信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>