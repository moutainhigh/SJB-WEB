<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统参数信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出系统所有参数信息吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/sysParams/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			$("#btnSubmit").click(function(){
				$("#searchForm").attr("action","${ctx}/sys/sysParams/list");
				$("#searchForm").submit();
			});
			
			$("#synSubmit").click(function(){
				$("#searchForm").attr("action","${ctx}/sys/sysParams/syn");
				$("#searchForm").submit();
			});
			
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/sysParams/list");
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/sysParams/">系统参数信息列表</a></li>
		<shiro:hasPermission name="sys:sysParams:edit"><li><a href="${ctx}/sys/sysParams/form">系统参数信息添加</a></li></shiro:hasPermission>
	</ul>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/sysParams/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<span style="color: red;">导入为全量更新操作，请确认文件内容是否为全量后再进行导入！！！</span>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
		</form>
	</div>
	<form:form id="searchForm" modelAttribute="sysParams" action="${ctx}/sys/sysParams/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>参数名：</label>
				<form:input path="paramName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>参数类型：</label>
				<form:select path="paramType" class="input-xlarge " >
					<option value="">请选择</option>
					<form:options items="${fns:getDictList('param_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<input id="synSubmit" class="btn btn-primary" type="button" value="同步"/>
			<shiro:hasPermission name="sys:sysParams:export">
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysParams:import">
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
			</shiro:hasPermission>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>参数名</th>
				<th>参数值</th>
				<th>参数类型</th>
				<th>备注</th>
				<th>是否可修改</th>
				<shiro:hasPermission name="sys:sysParams:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysParams">
			<tr>
				<td><a href="${ctx}/sys/sysParams/form?id=${sysParams.id}">
					${sysParams.paramName}
				</a></td>
				<td title="${sysParams.paramValue }">
					${fns:abbr(sysParams.paramValue, 70)}
				</td>
				<td>
					${fns:getDictLabel(sysParams.paramType, 'param_type', '')}
				</td>
				<td>
					${sysParams.remarks}
				</td>
				<td>
					${fns:getDictLabel(sysParams.isModify, 'yes_no', '')}
				</td>
				<shiro:hasPermission name="sys:sysParams:edit"><td>
    				<a href="${ctx}/sys/sysParams/form?id=${sysParams.id}">修改</a>
					<a href="${ctx}/sys/sysParams/delete?id=${sysParams.id}" onclick="return confirmx('确认要删除该系统参数信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>