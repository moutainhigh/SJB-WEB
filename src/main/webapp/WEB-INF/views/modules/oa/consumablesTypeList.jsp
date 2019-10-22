<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消耗品类别管理管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/oa/consumablesType/">消耗品类别管理列表</a></li>
	<li><a href="${ctx}/oa/consumablesType/form">消耗品类别管理添加</a></li>
</ul>
<form:form id="searchForm" modelAttribute="consumablesType" action="${ctx}/oa/consumablesType/" method="post" class="breadcrumb form-search">
	<ul class="ul-form">
		<li><label>分类名称：</label>
			<form:input path="ctName" htmlEscape="false" maxlength="64" class="input-medium"/>
		</li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<li class="clearfix"></li>
	</ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>分类编号</th>
		<th>上级分类编号</th>
		<th>分类名称</th>
		<th>备注</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="consumablesType">
		<tr>
			<td>${consumablesType.ctCode}</td>
			<td>${consumablesType.parent.ctCode}</td>
			<td>${consumablesType.ctName}</td>
			<td>${consumablesType.remarks}</td>

			<td>
				<a href="${ctx}/oa/consumablesType/form?id=${consumablesType.id}">修改</a>
				<a href="${ctx}/oa/consumablesType/delete?id=${consumablesType.id}" onclick="return confirmx('确认要删除该消耗品类别管理及所有子消耗品类别管理吗？', this.href)">删除</a>
				<%--<a href="${ctx}/oa/consumablesType/form?parent.id=${consumablesType.id}">添加下级消耗品类别管理</a>--%>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>