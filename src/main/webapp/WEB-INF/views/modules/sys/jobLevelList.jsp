<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>职级管理</title>
	<meta name="decorator" content="default"/>
    <style>
        body{
            font-family: Arial,Helvetica,sans-serif;
            font-size: 13px;
            text-align: 1.5;
        }

        #open_btn {
            background: #009900;
        }

        #background {
            display: none;
            position: fixed;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            /*background-color: rgba(0,0,0,0.5);*/
        }

        #div1 {
            background:#eeeeee;
            width: 40%;
            z-index: 1;
            margin: 12% auto;
            overflow: auto;
			border: 1px solid #ddd;
        }

        span {
            color: white;
            padding-top: 12px;
            cursor: pointer;
            padding-right: 15px;
        }

        #div2 {
            /*background:#eeeeee;*/
            margin: auto;
            height: 300px;
            padding: 0 20px;
        }

        #close {
            padding: 5px;
            background: #ddd;
        }

        #close-button {
            float: right;
            font-size: 30px;
        }

    </style>
	<script type="text/javascript">
		$(document).ready(function() {
            // var btn = document.getElementById('open_btn');
            var div = document.getElementById('background');
            var close = document.getElementById('close-button');

            // btn.onclick = function show() {
            //     div.style.display = "block";
            // }

            close.onclick = function close() {
                div.style.display = "none";
            }

            window.onclick = function close(e) {
                if (e.target == div) {
                    div.style.display = "none";
                }
            }
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

        function delLevel(url,names) {
			// if (names == "") {
				confirmx('确认要删除该职级吗？', url);
			// } else {
			// 	alertx("该职级正在使用中，不可删除！");
			// }
			return false;
		}

		function showNames(names) {
			var div = document.getElementById('background');
			div.style.display = "block";
			$("#names").html(names);
		}





	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/jobLevel/">职级列表</a></li>
		<%--<shiro:hasPermission name="sys:jobLevel:edit">--%><li><a href="${ctx}/sys/jobLevel/form">职级添加</a></li><%--</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="jobLevel" action="${ctx}/sys/jobLevel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>精确搜索：</label>
				<form:input path="levelName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="请输入职级名称/职级人员"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>职级编号</th>
				<th>职级名称</th>
				<th>职级人员</th>
				<th>职级说明</th>
				<th>更新人</th>
				<th>更新时间</th>
				<shiro:hasPermission name="sys:jobLevel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="jobLevel">
			<tr>
				<td>${jobLevel.levelCode}</td>
				<td>${jobLevel.levelName}</td>
				<td>
					<c:if test="${fn:length(jobLevel.userNames) lt 10}" >
						${jobLevel.userNames}
					</c:if>
					<c:if test="${fn:length(jobLevel.userNames) ge 10}">
						<a onclick="return showNames('${jobLevel.userNames}')">${fn:substring(jobLevel.userNames,0,10)}...</a>
					</c:if>
				</td>
				<td>${jobLevel.description}</td>
				<td>${jobLevel.updateByName}</td>
				<td><fmt:formatDate value="${jobLevel.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<%-- <shiro:hasPermission name="sys:jobLevel:edit"> --%>
				<td>
				<a href="${ctx}/sys/jobLevel/form?id=${jobLevel.id}">修改</a>
				<a href="${ctx}/sys/jobLevel/delete?id=${jobLevel.id}" onclick="return delLevel(this.href,'${jobLevel.userNames}')">删除</a>
				</td>
				<%-- </shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
    <!-- 弹窗内容开始 -->
    <div id="background" class="back">
        <div id="div1" class="content">
            <div id="close">
                <span id="close-button">×</span>
                <label style="font-size: 17px;">职级管理</label>
            </div>
            <div id="div2">
                <label style="font-size: 15px;">职级人员</label>
                <p id="names"></p>
            </div>

        </div>
    </div>


</body>
</html>