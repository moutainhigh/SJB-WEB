<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExportFlow").click(function(){
				top.$.jBox.confirm("确认要导出审批人审批时间吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/oa/featureTool/flow");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});

			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出提交人修改时间吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/oa/featureTool/funct");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});

			$("#btnOwn").click(function(){
				top.$.jBox.confirm("确认要个人KPI数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/oa/featureTool/own");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});

			$("#btnOwnUn").click(function(){
				top.$.jBox.confirm("确认要导出单据审批时间吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/oa/featureTool/ownUn");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});

		});
		<%--function page(n,s){--%>
			<%--if(n) $("#pageNo").val(n);--%>
			<%--if(s) $("#pageSize").val(s);--%>
			<%--$("#searchForm").attr("action","${ctx}/oa/featureTool/view");--%>
			<%--$("#searchForm").submit();--%>
			<%--return false;--%>
		<%--};--%>
		// 恢复提示框显示
		// function reset(){
		// 	debugger;
		// 	top.$.jBox.tip.mess = null;
		// };

	</script>
</head>
<body>

<form:form id="searchForm" modelAttribute="FeatureTools" action="${ctx}/oa/featureTool/flow" method="post" class="breadcrumb form-search ">
<li><label>开始时间</label> <input id="start"
							   name="start" type="text"
							   value="${items.getStart() }" readonly="readonly"
							   maxlength="20" class="input-medium Wdate required"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});">
	<span class="help-inline"><font color="red">*</font> </span>
</li>
<li><label>截止时间</label> <input id="end"
							   name="end" type="text" value="${items.getEnd() }"
							   readonly="readonly" maxlength="20" class="input-medium Wdate required"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});">
	<span class="help-inline"><font color="red">*</font> </span>
</li>
	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
	<ul class="ul-form">

		<li class="btns">
			<input id="btnOwnUn" class="btn btn-primary" type="button" value="导出单据审批时间（新）"/>
		</li>
		<li class="btns">
			<input id="btnExportFlow" class="btn btn-primary" type="button" value="导出审批人审批时间（新）"/>
		</li>
		<li class="btns">
			<input id="btnExport" class="btn btn-primary" type="button" value="导出提交人修改时间（新）"/>
		</li>
		<li class="btns">
			<input id="btnOwn" class="btn btn-primary" type="button" value="导出个人KPI数据（旧）"/>
		</li>

		<li class="clearfix"></li>
	</ul>
</form:form>
<sys:message content="${message}"/>

</html>