<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>费用科目配置管理</title>
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
		<li class="active"><a href="${ctx}/oa/expensesSubConf/?subCode=${expensesSubConf.subCode}&subName=${expensesSubConf.subName}">费用科目配置列表</a></li>
		<shiro:hasPermission name="oa:expensesSubConf:edit"><li><a href="${ctx}/oa/expensesSubConf/form?subCode=${expensesSubConf.subCode}">费用科目配置添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>科目编号</th>
				<th>科目名称</th>
				<th>字典类型</th>
				<th>配置类型</th>
				<th>配置描述</th>
				<th>是否必填</th>
				<th>排序</th>
				<th>创建时间</th>
				<th>创建人</th>
				<th>更新时间</th>
				<th>更新人</th>
				<th>备注</th>
				<shiro:hasPermission name="oa:expensesSubConf:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="expensesSubConf">
			<tr>
				<td><a href="${ctx}/oa/expensesSubConf/form?id=${expensesSubConf.id}">
					${expensesSubConf.subCode}
				</a></td>
				<td>
					${expensesSubConf.subName}
				</td>
				<td>
					${expensesSubConf.dictType}
				</td>
				<td>
					${fns:getDictLabel(expensesSubConf.confType, 'conf_type', '')}
				</td>
				<td>
					${expensesSubConf.confDesc}
				</td>
				<td>
					${fns:getDictLabel(expensesSubConf.isRequired, 'yes_no', '')}
				</td>
				<td>
					${expensesSubConf.sort}
				</td>
				<td>
					<fmt:formatDate value="${expensesSubConf.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${expensesSubConf.createBy.loginName}
				</td>
				<td>
					<fmt:formatDate value="${expensesSubConf.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${expensesSubConf.updateBy.loginName}
				</td>
				<td>
					${expensesSubConf.remarks}
				</td>
				<shiro:hasPermission name="oa:expensesSubConf:edit"><td>
    				<a href="${ctx}/oa/expensesSubConf/form?id=${expensesSubConf.id}">修改</a>
					<a href="${ctx}/oa/expensesSubConf/delete?id=${expensesSubConf.id}" onclick="return confirmx('确认要删除该费用科目配置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>