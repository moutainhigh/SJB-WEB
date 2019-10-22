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
											"${ctx}/oa/report/expenseFlow");
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
																					"${ctx}/oa/report/expenseFlowExport");
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
</head>
<body>
	<form:form id="searchForm" modelAttribute="expensesReportInfo"
		class="breadcrumb form-search" action="${ctx}/oa/report/expenseFlow"
		method="post">
		<ul class="ul-form">
			<li><label>部门</label> <sys:treeselect id="querOffice"
					name="querOffice" value="${items.getQuerOffice() }"
					labelName="office.name" labelValue="${items.getQuerOfficeName()}"
					title="部门" url="/sys/office/treeData?type=2&isAll=false"
					cssStyle="width:150px" allowClear="true"
					notAllowSelectParent="false" /></li>
			<li><label>姓名</label>
				<form:input path="userName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>开始时间</label> <input id="querDateStart"
				name="querDateStart" type="text"
				value="${items.getQuerDateStart() }" readonly="readonly"
				maxlength="20" class="input-medium Wdate"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});">
			</li>
			<li><label>截止时间</label> <input id="querDateEnd"
				name="querDateEnd" type="text" value="${items.getQuerDateEnd() }"
				readonly="readonly" maxlength="20" class="input-medium Wdate"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});">
			</li>
			<li><label>项目名称</label> <form:select path="projectName"
					class="input-medium">
					<option value="">请选择</option>
					<%--<form:options items="${projectList}"--%>
						<%--itemLabel="label" itemValue="value" htmlEscape="false" />--%>

				<form:options items="${projectList}" itemLabel="projectName" itemValue="id" htmlEscape="false"/>

				</form:select></li>
			<li><label>状态</label> <form:select path="status"
					class="input-medium">
					<form:option value="-1" label="请选择" />
					<form:option value="0" label="在途" />
					<form:option value="1" label="完结" />
				</form:select></li>
			<li><label>是否隐藏列</label> <form:select path="isHide"
					class="input-medium">
					<option value="1">请选择</option>
					<form:options items="${fns:getDictList('yes_no')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li>
		</ul>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询"><input	id="btnExport" class="btn btn-primary"
				type="button" value="导出" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>

	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<!-- <caption id="cap">隐藏无数据的列</caption> -->
		<sys:message content="${message}" />
		<div id="report">
			<thead>
				<!-- 第一行添加列名 -->
				<tr>
					<th class="alig"
						style="text-align: center; vertical-align: middle;" width="5%"
						rowspan="2">部门</th>
					<th class="alig"
						style="text-align: center; vertical-align: middle;" width="5%"
						rowspan="2">姓名</th>
					<th class="alig"
						style="text-align: center; vertical-align: middle;" width="5%"
						rowspan="2">项目名称</th>
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
		</div>
	</table>
</body>
</html>