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
	
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<!-- <caption id="cap">隐藏无数据的列</caption> -->
		<sys:message content="${message}" />
		<div id="report">
			<br/>
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