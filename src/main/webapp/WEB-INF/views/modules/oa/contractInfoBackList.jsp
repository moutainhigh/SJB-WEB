<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同信息管理</title>
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
		<li class="active"><a href="${ctx}/oa/contractInfoBack/">合同信息列表</a></li>
		<shiro:hasPermission name="oa:contractInfoBack:edit"><li><a href="${ctx}/oa/contractInfoBack/form">合同信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="contractInfoBack" action="${ctx}/oa/contractInfoBack/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>公司名称：</label>
				<form:input path="companyName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>合同状态：</label>
				<form:select path="del" class="input-large">
					<form:option value="" label="请选择" selected="true"/>
					<form:option value="0" label="启用"/>
					<form:option value="1" label="停用"/>
<%-- 					<form:options items="${fns:getDictList('company_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
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
				<th>公司名称</th>
				<th>公司简称</th>
				<th>统一社会信用代码</th>
				<th>住址</th>
				<th>法定代表人</th>
				<th>合同信息状态</th>
				<shiro:hasPermission name="oa:contractInfoBack:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="contractInfoBack">
			<tr>
				<td><a href="${ctx}/oa/contractInfoBack/form?id=${contractInfoBack.id}">
					${contractInfoBack.companyName}
				</a></td>
				<td>
					${contractInfoBack.abbreviation}
				</td>
				<td>
					${contractInfoBack.creditCode}
				</td>
				<td>
					${contractInfoBack.address}
				</td>
				<td>
					${contractInfoBack.legalRepresentative}
				</td>
				<td>
					${fns:getDictLabel(contractInfoBack.delFlag, 'company_type', '')}
				</td>
				<shiro:hasPermission name="oa:contractInfoBack:edit"><td>
					<input id="btnS" class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/oa/contractInfoBack/form?id=${contractInfoBack.id}'" value="编辑"/>
					
					
					<c:choose>
						<c:when test="${contractInfoBack.delFlag == '0'}">
							<input id="btn" class="btn btn-danger" type="button" onclick="window.location.href='${ctx}/oa/contractInfoBack/delete?id=${contractInfoBack.id}'" value="${fns:getDictLabel(contractInfoBack.delFlag, 'company_type_d', '')}"/>
						</c:when>
						<c:otherwise>
							<input id="btn" class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/oa/contractInfoBack/delete?id=${contractInfoBack.id}'" value="${fns:getDictLabel(contractInfoBack.delFlag, 'company_type_d', '')}"/>
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