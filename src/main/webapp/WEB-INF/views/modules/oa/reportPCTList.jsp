<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>报销统计查询</title>

<meta name="decorator" content="blank">
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#btnSubmit").click(
								function() {
									$("#searchForm").attr("action",
											"${ctx}/flow/expenseFlow/expenseSubInfoReport/cent");
									$("#searchForm").submit();
								});
						$("#btnExport")
								.click(
										function() {
											top.$.jBox
													.confirm(
															"确认要导出数据吗？",
															"系统提示",
															function(v, h, f) {
																if (v == "ok") {
																	$(
																			"#searchForm")
																			.attr(
																					"action",
																					"${ctx}/flow/expenseFlow/expenseSubInfoReport/cent/expenseFlowExportPCT");
																	$(
																			"#searchForm")
																			.submit();
																}
															},
															{
																buttonsFocus : 1
															});
											top.$('.jbox-body .jbox-icon').css(
													'top', '55px');
										});
					});
</script>
<link rel="Stylesheet"
	href="/SCFS-WEB/static/jerichotab/css/jquery.jerichotab.css">
<script type="text/javascript"
	src="/SCFS-WEB/static/jerichotab/js/jquery.jerichotab.js"></script>
<style type="text/css">
#main {
	padding: 0;
	margin: 0;
}

#main .container-fluid {
	padding: 0 4px 0 6px;
}

#header {
	margin: 0 0 8px;
	position: static;
}

#header li {
	font-size: 14px;
	_font-size: 12px;
}

#header .brand {
	font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
	font-size: 26px;
	padding-left: 33px;
}

#footer {
	margin: 8px 0 0 0;
	padding: 3px 0 0 0;
	font-size: 11px;
	text-align: center;
	border-top: 2px solid #0663A2;
}

#footer, #footer a {
	color: #999;
}

#left {
	overflow-x: hidden;
	overflow-y: auto;
}

#left .collapse {
	position: static;
}

#userControl>li>a { /*color:#fff;*/
	text-shadow: none;
}

#userControl>li>a:hover, #user #userControl>li.open>a {
	background: transparent;
}

table, tr, th, td {
	border: 1px solid #666;
	border-collapse: collapse;
	/* background-color: rgb(210, 219, 236); */
}

caption {
	border: 1px solid #666;
	border-collapse: collapse;
	background-color: rgb(210, 219, 380);
}

th, td, caption {
	font-family: '微软雅黑';
	font-size: 12px;
	text-align: center;
	padding: 3px 15px;
}

caption {
	border-bottom: none;
	cursor: pointer;
}
</style>
</head>
<body>
		<form:form id="searchForm" modelAttribute="expensesReportInfo" class="breadcrumb form-search"
		action="${ctx}/flow/expenseFlow/expenseSubInfoReport/cent" method="post">
		<ul class="ul-form">
			<li><label>部门</label> <sys:treeselect id="querOffice"
					name="querOffice" value="${items.getQuerOffice() }"
					labelName="office.name" labelValue="${items.getQuerOfficeName()}"
					title="部门" url="/sys/office/treeData?type=2&isAll=false"
					cssStyle="width:150px" allowClear="true"
					notAllowSelectParent="false" /></li>
			<li>
				<label>开始时间</label>
				<input id="querDateStart" name="querDateStart" type="text"
				value="${items.getQuerDateStart() }" readonly="readonly"
				maxlength="20" class="input-medium Wdate"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});">
			</li>
			<li>
				<label>截止时间</label>
				<input id="querDateEnd" name="querDateEnd"
				type="text" value="${items.getQuerDateEnd() }" readonly="readonly"
				maxlength="20" class="input-medium Wdate"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});">
			</li>
			
			<li>
				<label>项目名称</label>
				<form:select path="projectName" class="input-medium">
					<option value="">请选择</option>
					<%--<form:options items="${fns:getDictList('oa_project')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
					<form:options items="${projectList}" itemLabel="projectName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li>
				<label>状态</label>
				<form:select  path="status" class="input-medium">
					<form:option value="-1" label="请选择"/>
					<form:option value="0" label="在途"/>
					<form:option value="1" label="完结"/>
				</form:select>
			</li>
			<li>
				<label>是否隐藏列</label>
				<form:select path="isHide" class="input-medium">
					<option value="1">请选择</option>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询"><input
				id="btnExport" class="btn btn-primary" type="button" value="导出" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>


	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<!-- <caption>隐藏无数据的列</caption> -->
		<thead>
			<!-- 第一行添加列名 -->
			<tr>
				<th class="alig" style="text-align: center; vertical-align: middle;"
					width="5%" rowspan="2">部门</th>
				<th class="alig" style="text-align: center; vertical-align: middle;"
					width="5%" rowspan="2">姓名</th>
				<th class="alig" style="text-align: center; vertical-align: middle;"
				width="5%" rowspan="2">项目名称</th>
				<c:forEach items="${ReportSpanLists}" var="nl">
					<th class="alig"
						style="text-align: center; vertical-align: middle;" width="3%"
						rowspan="${nl.rowspan }" colspan="${nl.colspan}">${nl.rowName}</th>
				</c:forEach>
			</tr>
			<!-- 第二行数据 -->
			<tr>
				<c:forEach items="${findSecondRows}" var="data">
					<th class="alig"
						style="text-align: center; vertical-align: middle;" width="3%"
						rowspan="1">${data.rowName}</th>
				</c:forEach>
			</tr>

			<!-- 获取所有人的数字信息 -->
			<c:forEach items="${data}" var="da">
				<tr>
					<c:forEach items="${da}" var="d">
						<td class="alig"
							style="text-align: center; vertical-align: middle;" width="3%"
							rowspan="1">${d}</td>
					</c:forEach>
				</tr>
			</c:forEach>

		</thead>
	</table>


</body>
</html>