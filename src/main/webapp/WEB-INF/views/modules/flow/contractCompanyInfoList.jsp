<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同公司信息管理</title>
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
		<li class="active"><a href="${ctx}/flow/contractCompanyInfo/">合同公司信息列表</a></li>
		<shiro:hasPermission name="flow:contractCompanyInfo:edit"><li><a href="${ctx}/flow/contractCompanyInfo/form">合同公司信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="contractCompanyInfo" action="${ctx}/flow/contractCompanyInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>乙方名称：</label>
				<form:select path="secondPartyName" class="input-xlarge required">
					<option value="">---请选择---</option>
					<form:options items="${fns:getDictList('second_party_name')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>公司编码</th>
				<th>合同类型</th>
				<th>乙方名称</th>
				<th>乙方统一社会信用代码</th>
				<th>乙方住所</th>
				<th>乙方法定代表人</th>
				<th>乙方联系人</th>
				<th>乙方联系人电话</th>
				<th>乙方联系人邮箱</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="flow:contractCompanyInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>		
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="contractCompanyInfo">
			<tr>
				<td>${contractCompanyInfo.contractCompanyCode}</td>
				<td>${fns:getDictLabel(contractCompanyInfo.contractTypeKey,'contract_type','')}</td>
				<td><a href="${ctx}/flow/contractCompanyInfo/form?id=${contractCompanyInfo.id}">
					${fns:getDictLabel(contractCompanyInfo.secondPartyName,'second_party_name','')}</a></td>
				<td>${contractCompanyInfo.secondCreditCode}</td>
				<td>${contractCompanyInfo.secondAddress}</td>
				<td>${contractCompanyInfo.secondLegalRepresentative}</td>
				<td>${contractCompanyInfo.secondLinkman}</td>
				<td>${contractCompanyInfo.secondLinkmanPhone}</td>
				<td>${contractCompanyInfo.secondLinkmanMail}</td>
				<td><fmt:formatDate value="${contractCompanyInfo.updateTime}" pattern="yyyy-MM-dd"/></td>
				<td>${contractCompanyInfo.remarks}</td>
				<shiro:hasPermission name="flow:contractCompanyInfo:edit"><td>
    				<a href="${ctx}/flow/contractCompanyInfo/form?id=${contractCompanyInfo.id}">修改</a>
					<a href="${ctx}/flow/contractCompanyInfo/delete?id=${contractCompanyInfo.id}" onclick="return confirmx('确认要删除该合同公司信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>