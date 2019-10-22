<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同模版管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
        
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oa/contractConfig/">合同模版列表</a></li>
		<shiro:hasPermission name="contract:contractConfig:edit"><li><a href="${ctx}/oa/contractConfig/form">合同模版添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="contractConfig" action="${ctx}/oa/contractConfig/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>合同名称：</label>
				<form:input path="contractName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>合同类型:</label>
				<form:select path="contractType" class="input-large">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('contract_type_s')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			
			<li><label>模板状态：</label>
				<form:select path="delFlag" class="input-large">
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
				<th>合同模板编码</th>
				<th>合同名称</th>
				<th>合同缩写</th>
				<th>合同类型</th>
				<th>合同方数量</th>
				<th>是否关联客户</th>
				<th>合同模板状态</th>
				<shiro:hasPermission name="contract:contractConfig:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="contractConfig">
			<tr>
				<td>${contractConfig.contractCode}</td>
				<td>${contractConfig.contractName}</td>
				<td>${contractConfig.sort}</td>
				<td>${fns:getDictLabel(contractConfig.contractType, 'contract_type_s', '')}</td>
				<td>${contractConfig.contractMemberCount}</td>
				<td>${contractConfig.associationMain==0?"否":"是"}</td>
				<td>${contractConfig.delFlag==0?"启用":"停用"}</td>
				
				
				<td>
				<shiro:hasPermission name="contract:contractConfig:edit">
				<input id="btnS" class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/oa/contractConfig/form?id=${contractConfig.id}'" value="编辑"/>
    				<c:choose>
						<c:when test="${contractConfig.delFlag == '0'}">
							<input id="btn" class="btn btn-warning" type="button" onclick="window.location.href='${ctx}/oa/contractConfig/delete?id=${contractConfig.id}'" value="${fns:getDictLabel(contractConfig.delFlag, 'company_type_d', '')}"/>
						</c:when>
						<c:otherwise>
							<input id="btn" class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/oa/contractConfig/delete?id=${contractConfig.id}'" value="${fns:getDictLabel(contractConfig.delFlag, 'company_type_d', '')}"/>
						</c:otherwise>
					</c:choose></shiro:hasPermission>
					<shiro:hasPermission name="contract:contractConfig:del">
					&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="${ctx}/oa/contractConfig/del?id=${contractConfig.id}" 
						 onclick="return confirmx('确认要删除该合同模板吗？', this.href)" >删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>