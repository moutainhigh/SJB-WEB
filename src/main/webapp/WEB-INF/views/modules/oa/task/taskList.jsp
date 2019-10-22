<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务配置</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#btnSubmit").click(function(){
				$("#searchForm").attr("action","${ctx}/sys/task/list");
				$("#searchForm").submit();
			});
			
			$("input[name='box']").on("click", function(event){
				event.stopPropagation();//阻止事件冒泡
			});
			
			//全选按钮
			$("#checkAll").click(function(){
				if($("#checkAll").prop('checked')){
					$("input[name='box']").prop("checked", true);
				}else{
					$("input[name='box']").prop("checked", false);
				}
			});
		});
		
		//优化行点击事件
		function clickTr(id){
			if($("#"+id).prop('checked')){
				$("#"+id).prop("checked", false);
			//如果选中,则判断多选框是否全部选中,如果是全部选中则吧,列头中的选择多选框设置为选中状态
			}else{
				$("#"+id).prop("checked", true);
			}
			boxOnclick(id);
		}
		
		//多选框点击事件
		function boxOnclick(id){
			//如果取消选中,则列头的多选框取消选中
			if(!$("#"+id).prop('checked')){
				$("#checkAll").prop("checked", false);
			//如果选中,则判断多选框是否全部选中,如果是全部选中则吧,列头中的选择多选框设置为选中状态
			}else{
				var mark = true;
				$("input[name='box']").each(function(){
					if (!$(this).is(":checked")) {
						mark = false;
					}
				});
				if(mark){
					$("#checkAll").prop("checked", true);
				}
			}
		}
		
		//新增任务
		function addTask(){
			window.location.href = "${ctx}/oa/taskAllocation/form";
		}
		
		//启动定时器
		function startTask(){
			var box = $('input:checkbox[name=box]:checked');
			
			if(box.length==0){
				alert("请选择一条记录!");
			}else{
					var ids = "";
					$.each(box, function(i, n){
						ids += $(n).attr("id") + ",";
					});
					ids = ids.substring(0,ids.length-1);
					
					window.location.href="${ctx}/oa/taskAllocation/startTask?ids="+ids;
			}
		}
		//更新定时器
		function updateTask(){
			var box = $('input:checkbox[name=box]:checked');
			
			if(box.length==0){
				alert("请选择一条记录!");
			}else if(box.length > 1){
				alert("只能选择一条数据!");
			}else{
				
				var id = $(box[0]).attr("id");
				window.location.href="${ctx}/oa/taskAllocation/form?id="+id;
			}
		}
		//刷新定时器
		function refreshTask(){
			var box = $('input:checkbox[name=box]:checked');
			
			if(box.length==0){
				alert("请选择一条记录!");
			}else{
				
				var ids = "";
				$.each(box, function(i, n){
					ids += $(n).attr("id") + ",";
				});
				window.location.href="${ctx}/sys/task/refreshTask?ids="+ids;
			}
		}
		
		//设置调度器时间戳
		function szddqsjcTask(){
			var box = $('input:checkbox[name=box]:checked');
			
			if(box.length==0){
				alert("请选择一条记录!");
			}else if(box.length > 1){
				alert("只能选择一条数据!");
			}else{
				
				var taskType = $(box[0]).attr("taskType");
				
				if(taskType!="调度器"){
					alert("此功能只能调度器使用!");
					return false;
				}
				
				var classPath = $(box[0]).attr("classPath");
				
				//弹出框
				top.$.jBox("<div class='form-search' style='padding:20px;text-align:center;'>"+
							"<ul class='ul-form'>" +
								"<li>"+
									"<label>时间戳：</label>"+
									"<input type='text' readonly='readonly' maxlength='20' onclick='WdatePicker({dateFmt:\"yyyy-MM-dd HH:mm:ss\",isShowClear:true});' id='hkrq' name='hkrq' class='input-medium Wdate'>"+
								"</li>"+
							"</ul>"+
						"</div>", {
						title: "实时还款", submit: function (v, h, f){
							if (f.hkrq == '') {
						        top.$.jBox.tip("请输入缓存时间！", 'error');
						        return false;
						    }
							
							resetTip(); //loading();
							$.ajax({
								type: "POST",
								url: "${ctx}/sys/task/chengeTimestamp",
								data: "taskType="+taskType+"&date="+f.hkrq+"&classPath="+classPath,
								success: function(msg){
									if(null!=msg){
										alert(msg.msg);
									}else{
										alert("没有返回数据!");
									}
								}
							});
						},closed:function(){
							if (typeof closed == 'function') {
								closed();
							}
						}
					}
				);
				
			}
		}
		
		//查看调度器参数
		function ckddqsjcTask(){
			var box = $('input:checkbox[name=box]:checked');
			
			if(box.length==0){
				alert("请选择一条记录!");
			}else if(box.length > 1){
				alert("只能选择一条数据!");
			}else{
				
				var taskType = $(box[0]).attr("taskType");
				
				if(taskType!="调度器"){
					alert("此功能只能调度器使用!");
					return false;
				}
				
				var classPath = $(box[0]).attr("classPath");
				
				$.ajax({
					type: "POST",
					url: "${ctx}/sys/task/ckddqsjc",
					data: "taskType="+taskType+"&classPath="+classPath,
					success: function(msg){
						if(null!=msg){
							if(msg.code == 1){
								alert("当前时间戳为:"+msg.timestamp);				
							}else{
								alert(msg.msg);
							}
						}else{
							alert("没有返回数据!");
						}
					}
				});
			}
		}
		//删除定时器
		function deleteTask(){
			var box = $('input:checkbox[name=box]:checked');
			
			if(box.length==0){
				alert("请选择一条记录!");
			}else{
				
				var ids = "";
				$.each(box, function(i, n){
					ids += $(n).attr("id") + ",";
				});
				window.location.href="${ctx}/oa/taskAllocation/deleteTask?ids="+ids;
			}
		}
		//停止定时器
		function stopTask(){
			var box = $('input:checkbox[name=box]:checked');
			
			if(box.length==0){
				alert("请选择一条记录!");
			}else{
				
				var ids = "";
				$.each(box, function(i, n){
					ids += $(n).attr("id") + ",";
				});
				ids = ids.substring(0,ids.length-1);
				window.location.href="${ctx}/oa/taskAllocation/stopTask?ids="+ids;
			}
		}
		
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
		<li class="active"><a href="${ctx}/oa/taskAllocation/list">任务配置</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="taskAllocationRequest" action="${ctx}/oa/taskAllocation/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
<!-- 			<li><label>查询：</label> -->
<%-- 				<form:input path="fuzzyQuery" htmlEscape="false" maxlength="32" class="input-large" placeholder="id,任务名称,业务ID,业务名称"/> --%>
<!-- 			</li> -->
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<td colspan="9">
				<shiro:hasPermission name="oa:task:form">
					<button class="btn btn-success" onclick="addTask();">新增任务</button>
					<button class="btn btn-info" onclick="updateTask();">修改任务</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="oa:task:start">
					<button class="btn btn-success" onclick="startTask();">开始任务</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="oa:task:stop">
					<button class="btn btn-warning" onclick="stopTask();">停止任务</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="oa:task:refresh">
					<button class="btn btn-info" onclick="refreshTask();">刷新任务</button>
				</shiro:hasPermission>
<!-- 					<button class="btn btn-primary" onclick="szddqsjcTask();">设置调度器时间戳</button> -->
<%-- 				<shiro:hasPermission name="oa:task:view"> --%>
<!-- 					<button class="btn btn-warning" onclick="ckddqsjcTask();">查看调度器参数</button> -->
<%-- 				</shiro:hasPermission> --%>
<!-- 					<button class="btn btn-inverse" onclick="deleteTask();">删除任务</button> -->
				</td>
			</tr>
			<tr>
				<th style="text-align:center;"><input type="checkbox" id="checkAll" />选择</th>
				<th>任务名称</th>
				<th>任务类型</th>
				<th>开始时间</th>
				<th>间隔时间(秒)</th>
				<th>结束时间</th>
				<th>定时器状态</th>
				<th>Mac</th>
				<th>是否开启Mac验证</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="task">
			<tr>
				<td style="text-align:center;" onclick="clickTr('${task.id}');">
					<input type="checkbox" name="box" onclick="boxOnclick('${task.id}');" taskType="${task.taskType}" classPath="${task.classPath}" id="${task.id}"/>
				</td>
				<td>${task.taskName}</td>
				<td>${task.taskType}</td>
				<td><fmt:formatDate value="${task.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${task.intervalTime}</td>
				<td><fmt:formatDate value="${task.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${task.state}</td>
				<td>
					${fns:getDictValue(task.mac, 'dict_mac', '无')}
				</td>
				<td>${fns:getDictLabel(task.isMacVaild, 'yes_no', '')}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>