<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
            <c:if test="${office.isNewRecord == true}">
                window.parent.refreshTree();
            </c:if>

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

				    //转换编码
				    if(row.useable == '1'){
                        row.useable = '启用'
                    }else {
                        row.useable = '停用'
                    }

					$(list).append(Mustache.render(tpl, {
						pid: (root?0:pid),
						row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/office/list?id=${office.id}">机构列表</a></li>
		<shiro:hasPermission name="sys:office:edit"><li><a href="${ctx}/sys/office/form?parent.id=${office.id}">机构添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>

	<c:choose>
		<c:when test="${fn:length(list) != 0}">
			<table id="treeTable" class="table table-striped table-bordered table-condensed">
				<thead><tr><th>机构编码</th><th>机构名称</th><th>机构主负责人</th><th>是否可用</th><th>备注</th><shiro:hasPermission name="sys:office:edit"><th>操作</th></shiro:hasPermission></tr></thead>
				<tbody id="treeTableList"></tbody>
			</table>
		</c:when>
		<c:otherwise>
			<div>没有下级机构</div>
		</c:otherwise>
	</c:choose>

	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td>{{row.code}}</td>
			<td><a href="${ctx}/sys/office/form?id={{row.id}}">{{row.name}}</a></td>
			<td>{{row.primaryPerson.name}}</td>
            <td>{{row.useable}}</td>
            <td>{{row.remarks}}</td>
			<shiro:hasPermission name="sys:office:edit"><td>
				<a href="${ctx}/sys/office/form?id={{row.id}}">编辑</a>
				<a href="${ctx}/sys/office/delete?id={{row.id}}" onclick="return confirmx('确定删除该机构和所有子机构吗？删除后机构将不可找回！', this.href)">删除</a>
				<a href="${ctx}/sys/office/form?parent.id={{row.id}}">添加下级机构</a>
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>