<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>明细</title>
	<meta name="decorator" content="default"/>
<script type="text/javascript">
//校验
function expense_validate(pass){
	$("#btnSubmit").submit();
}
$(document).ready(function() {
	var count = 0;
	$("[name^=file]").fileinput({
	    uploadUrl: "${ctx}/oa/houseKeeping/upload?", // server upload action
	    uploadAsync: true,
	    showPreview: false,
	    showRemove:true,
	    showCancel:true,
	    showBrowse: true,
	    allowedFileExtensions: ['jpg', 'jpeg', 'png', 'gif'],
	    maxFileCount: 1,
	    language : 'zh',
	    uploadExtraData: function() {   //额外参数的关键点
   		    var obj = {};
            obj.src = $(this).attr('id');
            var idx = $("#images-scroll ul li").index($("#images-scroll ul .active")[0]);
            if(idx > -1){
            	obj.src = $(this).attr('id') + "_" + idx;
            }else{
          	    obj.src = $(this).attr('id');
            }
            return obj;
        }
	}).on('filebatchpreupload', function(event, data, id, index){
	}).on('fileuploaded', function(event, data, id, index) {
		top.$.jBox.tip(data.response.resDesc);
		window.location.reload(true);
	});
	$("#kvFileinputModal").css("z-index","-999");
})
</script>
<style type="text/css">
	td {
	    text-align:center;
	}
	.progress-bar-success {
	    background-color: #5cb85c;
	}
	.shuli{ margin:0 auto;width:20px;line-height:24px;} 
	.images-scroll{
		border:1px solid #CCC;
		margin:100px auto;
		position:relative;
	}	
	.images-scroll ul{
		list-style:none;
	}
	.images-scroll li{
		float:left;	
		display:none;			
	}
	.images-scroll .active{
		display:block;
	}
	.images-scroll a{
		
	}
	.images-scroll .left-scroll{
		position:absolute;
		top:40%;
		left:10px;
		opacity:1;
		background:url("${ctxStatic}/images/bg_direction_nav.png");
		background-repeat:no-repeat;
		background-position:0px 0px;
		height:27px;
		width:27px;
		cursor:pointer;
	}
	.images-scroll .right-scroll{
		position:absolute;
		top:40%;
		opacity:1;
		right:10px;
		background:url("${ctxStatic}/images/bg_direction_nav.png");
		background-repeat:no-repeat;
		background-position:-30px 0px;
		height:27px;
		cursor:pointer;
		width:27px;				
	}
	.images-scroll .right-scroll:hover{
		background-color:transparent;
	}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/houseKeeping/">工作及内务总表</a></li>
		<li class="active"><a href="${ctx}/oa/houseKeeping/form?userId=${houseKeeping.userId}&userName=${houseKeeping.userName}&workDate=${houseKeeping.workDate}">明细</a></li>
	</ul><br/>
	<form:form id="btnSubmit" action="${ctx}/oa/houseKeeping/form?userId=${houseKeeping.userId}&userName=${houseKeeping.userName}&workDate=${houseKeeping.workDate}" 
		method="post" class="form-horizontal">
		<sys:message content="${message}"/>		
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>日期</th>
				<th>上班签到照片</th>
				<th>工作桌面照片</th>
				<th>内务环境照片</th>
				<th>综合评分</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="treeTableList">
			<c:forEach items="${list1}" var="work">
				<tr>
					<td>
						${fns:substringAfterLast(work.workDate,'-')}
					</td>
					<c:choose>
						<c:when test="${work.signUrl != null}">
							<td><a href="${fns:getParamValue('file_server_url', 'sysParam', '')}${work.signUrl}" target="_blank">签到图片</a></td>
						</c:when>
						<c:otherwise>
						<td>
							<input type="file" id="sign_${work.id }" name="files" multiple class="file-loading" />
							<input type="hidden" id="img_workSign" name="img_workSign" />
							<input type="hidden" id="workId" name="workId" value="${work.id}"/>
						</td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${ work.workUrl != null}">
							<td><a href="${fns:getParamValue('file_server_url', 'sysParam', '')}${work.workUrl }" target="_blank">上班图片</a></td>
						</c:when>
						<c:otherwise>
						<td>
							<input type="file" id="work_${work.id }" name="files" multiple class="file-loading" />
							<input type="hidden" id="img_workPhoto" name="img_workPhoto" />
							<input type="hidden" id="workId" name="workId" value="${work.id}"/>
						</td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${ work.innerUrl != null}">
							<td><a href="${fns:getParamValue('file_server_url', 'sysParam', '')}${work.innerUrl }" target="_blank">内务图片</a></td> 
							</td>
						</c:when>
						<c:otherwise>
						<td>
							<input type="file" id="inner_${work.id }" name="files" multiple class="file-loading" />
							<input type="hidden" id="img_interPhoto" name="img_interPhoto" />
							<input type="hidden" id="workId" name="workId" value="${work.id}"/>
						</td>
						</c:otherwise>
					</c:choose>
					<td>
					<c:choose>
						<c:when test="${empty work.score }">
						</c:when>
						<c:otherwise>
							${work.score}
						</c:otherwise>
					</c:choose>
					</td>
					<td>
						<c:if test="${work.isSign != '1'}">
							<a href="${ctx}/oa/houseKeeping/save?id=${work.id }" onclick="return confirmx('确认签到吗？', this.href)">签到</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			<c:forEach items="${list2}" var="work">
				<tr>
					<td>
						${fns:substringAfterLast(work.workDate,'-')}
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
</form:form>
</body>
</html>