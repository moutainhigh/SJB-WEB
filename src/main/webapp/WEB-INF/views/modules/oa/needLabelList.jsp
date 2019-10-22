<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>标签管理管理</title>
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
		<li class="active"><a href="${ctx}/oa/needLabel/">标签管理列表</a></li>
		<shiro:hasPermission name="oa:needLabel:edit"><li><a href="${ctx}/oa/needLabel/form">标签管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="needLabel" action="${ctx}/oa/needLabel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标签名称：</label>
				<form:input path="labelName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>标签状态：</label>
<%-- 				<form:radiobuttons path="delFlag" items="${fns:getDictList('del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
				<form:select path="del" class="input-large">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('company_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标签编号</th>
				<th>标签名称</th>
				<th>标签状态</th>
				<shiro:hasPermission name="oa:needLabel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
			
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="needLabel">
			<tr>
				<td>${needLabel.labelCode}</td>
				<td>${needLabel.labelName}</td>
<!-- 				contractConfig.associationMain==0?"否":"是" -->
				<td>${needLabel.delFlag==0?"启用":"停用"}</td>
<%-- 				<shiro:hasPermission name="oa:needLabel:edit"><td> --%>
<%--     				<a href="${ctx}/oa/needLabel/form?id=${needLabel.id}">修改</a> --%>
<%-- 					<a href="${ctx}/oa/needLabel/delete?id=${needLabel.id}" onclick="return confirmx('确认要删除该标签管理吗？', this.href)">删除</a> --%>
<%-- 				</td></shiro:hasPermission> --%>
				<shiro:hasPermission name="contract:contractConfig:edit"><td>
				<input id="btnS" class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/oa/needLabel/form?id=${needLabel.id}'" value="编辑"/>
    				<c:choose>
						<c:when test="${needLabel.delFlag == '0'}">
							<input id="btn" class="btn btn-danger" type="button" onclick="window.location.href='${ctx}/oa/needLabel/delete?id=${needLabel.id}'" value="${fns:getDictLabel(needLabel.delFlag, 'company_type_d', '')}"/>
						</c:when>
						<c:otherwise>
							<input id="btn" class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/oa/needLabel/delete?id=${needLabel.id}'" value="${fns:getDictLabel(needLabel.delFlag, 'company_type_d', '')}"/>
						</c:otherwise>
					</c:choose>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>