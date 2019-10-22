<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>费用报销管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			file_upload(); //附件上传
			subject_file_upload(); //科目附件上传
			autoSum(); //金额，票数汇总
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			//动态列表全选监控事件委托
			$("input[name='box']").live("click",function(){
				allchk();
			});
			var count=0;
			var index=0;
			if($("#expenseDetails").find("tbody tr").length!=0){
				index=$("#expenseDetails").find("tbody tr").length;
			}
			
			//动态列表全选事件监听
			$(document).on("click","#checkAll",function(){
				checkAll();
			});
			function PrefixInteger(num, length) {
				return (Array(length).join('0') + num).slice(-length);
			}
			//动态列表行添加事件
			$("#addButton").bind("click", function() {
				
				//获取目前最大下标
				count = $("#expenseDetails").find("tbody tr").length;
				
				if(count > 30){
					alert("当前最多只能添加30条明细数据!");
					return;
				}
				
				var str = "<tr id='tr"+index+"'>" + 
				"<td style=\"text-align:center;\">" + 
				"<input type=\"checkbox\" name=\"box\" value=\""+index+"\" /></td>" + 
				"</td>" + 
				"<td>" + 
					"<input id=\"startDate"+index+"\" name=\"expenseDetail["+index+"].startDate\" type=\"text\" readOnly=\"readOnly\" class=\"input-mini required\" onClick=\"WdatePicker({maxDate:'#F{$dp.$D(\\'endDate"+index+"\\')}'})\"/>" + 
				"</td>" + 
				"<td>" + 
				"<input id=\"expenseDetail["+index+"].startPointName\" name=\"expenseDetail["+index+"].startPointName\" readOnly=\"readOnly\" type=\"text\" maxlength=\"32\" class=\"input-mini required\"/>" +
				"<input id=\"expenseDetail["+index+"].startPoint\" name=\"expenseDetail["+index+"].startPoint\" readOnly=\"readOnly\" type=\"hidden\" maxlength=\"32\" class=\"input-mini required\"/>" +
				"</td>" + 
				"<td>" + 
					"<input id=\"endDate"+index+"\" name=\"expenseDetail["+index+"].endDate\" type=\"text\" readOnly=\"readOnly\" class=\"input-mini required\" onClick=\"WdatePicker({minDate:'#F{$dp.$D(\\'startDate"+index+"\\')}'})\"/>" + 
				"</td>" + 
				"<td>" + 
					/* "<input name=\"expenseDetail["+index+"].endPoint\" type=\"text\" maxlength=\"32\" class=\"input-mini required\"/>" + */ 
					"<input id=\"expenseDetail["+index+"].endPointName\" name=\"expenseDetail["+index+"].endPointName\" readOnly=\"readOnly\" type=\"text\" maxlength=\"32\" class=\"input-mini required\"/>" +
					"<input id=\"expenseDetail["+index+"].endPoint\" name=\"expenseDetail["+index+"].endPoint\" readOnly=\"readOnly\" type=\"hidden\" maxlength=\"32\" class=\"input-mini required\"/>" +
				"</td>" + 
				"<td>" + 
					"<select name=\"expenseDetail["+index+"].firstSub\"  maxlength=\"32\" class=\"input-small required\"/>" + 
				"</td>" + 
				"<td>" + 
					"<select id=\"secondSub\" name=\"expenseDetail["+index+"].secondSub\"  maxlength=\"32\" class=\"input-small\"/>" + 
				"</td>" + 
				"<td>" + 
					"<input name=\"expenseDetail["+index+"].personNum\" type=\"number\" maxlength=\"5\" class=\"input-mini required\"/>" + 
				"</td>" + 
				"<td>" + 
					"<input name=\"expenseDetail["+index+"].dayNum\" type=\"number\" maxlength=\"5\" class=\"input-mini required\"/>" + 
				"</td>" + 
				"<td>" + 
					"<input count=\"billNum\" name=\"expenseDetail["+index+"].billNum\" type=\"number\" maxlength=\"5\" class=\"input-mini required\"/>" + 
				"</td>" + 
				"<td>" + 
					"<input count=\"expenseAmt\" name=\"expenseDetail["+index+"].expenseAmt\" type=\"number\" step=\"0.01\" maxlength=\"32\" class=\"input-mini required\"/>" + 
				"</td>" + 
				"<td>" + 
					"<input name=\"expenseDetail["+index+"].remarks\" type=\"text\" maxlength=\"300\" class=\"input-mini\"/>" + 
				"</td>" +
				"<td id=\"more\">" + 
					 
				"</td>" + 
			"</tr>";
				
				$("#expenseDetails tbody").append(str);
				$("select").select2();
				initSelectInfo(index); //加载科目字段数据源
				initPointTree(index); //加载区域字段数据源
				autoSum(); //金额，票数汇总
				index++;
			});
			//动态列表行删除事件
			$("#removeButton").bind("click", function() {
				var ids = new Array();
				$("input[name='box']:checked").each(function(){
					if ($(this).val()!='') {
						ids[ids.length] = $(this).val();
						$("#tr"+$(this).val()).remove();
					}
				});
				if(ids == null || ids == ""){
					alert("请选择要删除的报销信息！");
				}
				countSum(); //金额，票数汇总
			});
			loadSelectTree(); //默认加载科目树
			initPointSelectInfo(); //加载城市类别
		});
		
		//判断是否自动勾选全选按钮
		function allchk(){ 
		    var chknum = $("input[name='box']").size();//选项总个数 
		    var chk = 0; 
		    $("input[name='box']").each(function () {
		    	if ($(this).prop('checked')) {
		            chk++; 
		        } 
		    }); 
		    if(chknum==chk){//全选 
		    	$("input[name='checkAll']").prop("checked", true);
		    }else{//不全选 
		    	$("input[name='checkAll']").prop("checked", false);
		    } 
		}
		
		//全选按钮
		function checkAll(){
			if($("input[name='checkAll']").prop('checked')){
				$("input[name='box']").prop("checked", true);
			}else{
				$("input[name='box']").prop("checked", false);
			}
		}
		
		//新增加载科目select
		var subjectAttach = {};
		function initSelectInfo(num){
			$("select[name='expenseDetail["+num+"].firstSub']").each(function(){
				var this_ = $(this);
				this_.empty(); 
				var param = {};
				param["isFirst"] = "1";
				$.ajax({
			        url:"${ctx}/flow/expenseFlow/subInfoList",//请求地址
			        data:param,
			        type:'post',
			        datatype:'json',
			        success:function(data){
			        	data = JSON.parse(data);
			        	this_.append("<option value='' selected>请选择</option>");
			        	for(var i = 0;i<data.length;i++){
			        		this_.append("<option value='"+data[i]["value"]+"'>"+data[i]["name"]+"</option>");
			        	}
			        }
			    });
			    this_.on("change",function(){ //一级改变时加载二级科目
			    	param["isFirst"] = "0";
			    	param["parSubCode"] = this_.val(); //一级科目编码
			    	$.ajax({
				        url:"${ctx}/flow/expenseFlow/subInfoList",//请求地址
				        data:param,
				        type:'post',
				        datatype:'json',
				        success:function(data){
				        	data = JSON.parse(data);
				        	var senondSelect = $("select[name='expenseDetail["+num+"].secondSub']");
				        	senondSelect.empty(); 
				        	senondSelect.val("").select2();
				        	senondSelect.append("<option value='' selected>请选择</option>");
				        	for(var i = 0;i<data.length;i++){
				        		if(data[i]["subConfList"] != null){
					        		subjectAttach[data[i]["value"]] = data[i]["subConfList"];
				        		}
				        		senondSelect.append("<option value='"+data[i]["value"]+"' confObj='"+data[i]["subConfList"]+"'>"+data[i]["name"]+"</option>");
				        	}
				        	if(data.length > 0){
				        		senondSelect.addClass("required");
				        	}else{
				        		senondSelect.removeClass("required");
				        	}
				        	senondSelect.on("change",function(){
				        		second_this = $(this);
				        		var sa = subjectAttach[second_this.val()];
				        		if(sa != null){
				        			second_this.closest("td").closest("tr").find("#more").attr("class","required");
				        			second_this.closest("td").closest("tr").find("#more").empty();
				        			var lineNum = second_this.closest("td").closest("tr").attr("id");
	 				        		lineNum = lineNum.substr(2,lineNum.length-2);
				        			second_this.closest("td").closest("tr").find("#more").append(
				        			"<a href=\"#\" onclick=\"return billInfoAudit(this,'"+second_this.val()+"',"+lineNum+");\">上传附件</a>");
				        			for(var i = 0;i<sa.length;i++){
				        				second_this.closest("td").closest("tr").find("#more").append("<input type=\"hidden\" id='required' value="+sa[i]["isRequired"]+" />");
				        			}
				        		}else{
				        			second_this.closest("td").closest("tr").find("#more").empty();
				        		}
				        	});
				        }
				    });	
			    });
			});
		}
		
		//加载区域下拉框选项
		function initPointTree(num){
				$("input[name='expenseDetail["+num+"].startPointName'],input[name='expenseDetail["+num+"].endPointName").click(function(){
					var inputName = $(this).attr("name");
					// 正常打开	
					top.$.jBox.open("iframe:${ctx}/tag/treeselectArea?url=/sys/area/treeData&module=&checked=&extId=&isAll=", "选择区域", 300, 420, {
						ajaxData:{selectIds: $("#${id}Id").val()},buttons:{"确定":"ok", ${allowClear?"\"清除\":\"clear\", ":""}"关闭":true}, submit:function(v, h, f){
							if (v=="ok"){
								var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
								var ids = [], names = [], nodes = [];
								if ("${checked}" == "true"){
									nodes = tree.getCheckedNodes(true);
								}else{
									nodes = tree.getSelectedNodes();
								}
								for(var i=0; i<nodes.length; i++) {//<c:if test="${checked && notAllowSelectParent}">
									if (nodes[i].isParent){
										continue; // 如果为复选框选择，则过滤掉父节点
									}//</c:if><c:if test="${notAllowSelectRoot}">
									if (nodes[i].level == 0){
										top.$.jBox.tip("不能选择根节点（"+nodes[i].name+"）请重新选择。");
										return false;
									}//</c:if><c:if test="${notAllowSelectParent}">
									if (nodes[i].isParent){
										top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
										return false;
									}//</c:if><c:if test="${not empty module && selectScopeModule}">
									if (nodes[i].module == ""){
										top.$.jBox.tip("不能选择公共模型（"+nodes[i].name+"）请重新选择。");
										return false;
									}else if (nodes[i].module != "${module}"){
										top.$.jBox.tip("不能选择当前栏目以外的栏目模型，请重新选择。");
										return false;
									}//</c:if>
									ids.push(nodes[i].id);
									names.push(nodes[i].name);//<c:if test="${!checked}">
									break; // 如果为非复选框选择，则返回第一个选择  </c:if>
								}
								$("input[name='"+inputName+"']").val(names.join(","));
								if(inputName.indexOf("start") > 0){
									$("input[name='expenseDetail["+num+"].startPoint']").val(ids.join(",").replace(/u_/ig,""));
								}
								if(inputName.indexOf("end") > 0){
									$("input[name='expenseDetail["+num+"].endPoint']").val(ids.join(",").replace(/u_/ig,""));
								}
								
							}//<c:if test="${allowClear}">
							else if (v=="clear"){
								$("#${id}Id").val("");
								$("#${id}Name").val("");
			                }//</c:if>
							if(typeof ${id}TreeselectCallBack == 'function'){
								${id}TreeselectCallBack(v, h, f);
							}
						}, loaded:function(h){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				});
		}
		
		function initPointSelectInfo(){
			$("input[id='expenseDetail.startPointName'],input[id='expenseDetail.endPointName").each(function(){
				var this_ = $(this);
				this_.click(function(){
					var inputName = this_.attr("name");
					// 正常打开	
					top.$.jBox.open("iframe:${ctx}/tag/treeselectArea?url=/sys/area/treeData&module=&checked=&extId=&isAll=", "选择区域", 300, 420, {
						ajaxData:{selectIds: $("#${id}Id").val()},buttons:{"确定":"ok", ${allowClear?"\"清除\":\"clear\", ":""}"关闭":true}, submit:function(v, h, f){
							if (v=="ok"){
								var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
								var ids = [], names = [], nodes = [];
								if ("${checked}" == "true"){
									nodes = tree.getCheckedNodes(true);
								}else{
									nodes = tree.getSelectedNodes();
								}
								for(var i=0; i<nodes.length; i++) {//<c:if test="${checked && notAllowSelectParent}">
									if (nodes[i].isParent){
										continue; // 如果为复选框选择，则过滤掉父节点
									}//</c:if><c:if test="${notAllowSelectRoot}">
									if (nodes[i].level == 0){
										top.$.jBox.tip("不能选择根节点（"+nodes[i].name+"）请重新选择。");
										return false;
									}//</c:if><c:if test="${notAllowSelectParent}">
									if (nodes[i].isParent){
										top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
										return false;
									}//</c:if><c:if test="${not empty module && selectScopeModule}">
									if (nodes[i].module == ""){
										top.$.jBox.tip("不能选择公共模型（"+nodes[i].name+"）请重新选择。");
										return false;
									}else if (nodes[i].module != "${module}"){
										top.$.jBox.tip("不能选择当前栏目以外的栏目模型，请重新选择。");
										return false;
									}//</c:if>
									ids.push(nodes[i].id);
									names.push(nodes[i].name);//<c:if test="${!checked}">
									break; // 如果为非复选框选择，则返回第一个选择  </c:if>
								}
								$("input[name='"+inputName+"']").val(names.join(","));
								if(inputName.indexOf("start") > 0){
									this_.closest("tr").find("input:hidden[id='expenseDetail.startPoint']").val(ids.join(",").replace(/u_/ig,""));
								}
								if(inputName.indexOf("end") > 0){
									this_.closest("tr").find("input:hidden[id='expenseDetail.endPoint']").val(ids.join(",").replace(/u_/ig,""));
								}
								
							}//<c:if test="${allowClear}">
							else if (v=="clear"){
								$("input[name='"+inputName+"']").val('');
								if(inputName.indexOf("start") > 0){
									this_.closest("tr").find("input:hidden[id='expenseDetail.startPoint']").val('');
								}
								if(inputName.indexOf("end") > 0){
									this_.closest("tr").find("input:hidden[id='expenseDetail.endPoint']").val('');
								}
			                }//</c:if>
							if(typeof ${id}TreeselectCallBack == 'function'){
								${id}TreeselectCallBack(v, h, f);
							}
						}, loaded:function(h){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				});
			});
		}
		
		
		//编辑页面科目下拉框加载
		function loadSelectTree(){
			$("select[id='firstSub']").each(function(){
				var this_ = $(this);
				this_.empty(); 
				var firstValue = this_.closest("tr").find("input:hidden[name='firstSub_val']").val();
				var param = {};
				param["isFirst"] = "1";
				$.ajax({
				        url:"${ctx}/flow/expenseFlow/subInfoList",//请求地址
				        data:param,
				        type:'post',
				        datatype:'json',
				        success:function(data){
				        	data = JSON.parse(data);
				        	for(var i = 0;i<data.length;i++){
				        		if(data[i]["value"] == firstValue){
				        			this_.append("<option selected value='"+data[i]["value"]+"'>"+data[i]["name"]+"</option>");
				        		}else{
					        		this_.append("<option value='"+data[i]["value"]+"'>"+data[i]["name"]+"</option>");
				        		}
				        	}
				        	this_.val(firstValue).select2();
				        }
				    });
					secondSelect(this_,firstValue);
				    this_.on("change",function(){ //一级改变时加载二级科目
				    	secondSelect(this_,this_.val());
				    });
			});
		}
		
		//加载二级科目
		function secondSelect(this_,firstValue){
			var param = {};
			param["parSubCode"] = firstValue; //一级科目编码
			$.ajax({
		        url:"${ctx}/flow/expenseFlow/subInfoList",//请求地址
		        data:param,
		        type:'post',
		        datatype:'json',
		        success:function(data){
		        	data = JSON.parse(data);
		        	var secondSelect = this_.closest("tr").find("select[id='secondSub']");
		        	var secondValue = secondSelect.closest("tr").find("input:hidden[name='secondSub_val']").val();
		        	secondSelect.empty(); 
		        	for(var i = 0;i<data.length;i++){
		        		if(data[i]["value"] == secondValue){
		        			secondSelect.append("<option selected value='"+data[i]["value"]+"'>"+data[i]["name"]+"</option>");
		        		}else{
		        			secondSelect.append("<option value='"+data[i]["value"]+"'>"+data[i]["name"]+"</option>");
		        		}
		        		if(data[i]["subConfList"] != null){
			        		subjectAttach[data[i]["value"]] = data[i]["subConfList"];
		        		}
		        	}
		        	secondSelect.val(secondValue).select2();
		        	secondSelect.on("change",function(){
		        		second_this = $(this);
		        		
		        		var secondSubCount = 0;
	        			$("#subjectAttachment").find("tr").each(function(){
	        				var trId = $(this).attr("id");
	        				$("select[id='secondSub']").each(function(){
		        				var secondSub = $(this);
		        				if(trId == secondSub.val()){
		        					secondSubCount++;
		        				}
		        			});
			        		if(secondSubCount == 0){
			        			$(this).remove();
			        		}
		        		});
		        		
		        		var sa = subjectAttach[second_this.val()];
		        		if(sa != null){
		        			second_this.closest("td").closest("tr").find("#more").empty();
		        			var lineNum = second_this.closest("td").closest("tr").attr("id");
				        		lineNum = lineNum.substr(2,lineNum.length-2);
		        			second_this.closest("td").closest("tr").find("#more").append(
		        			"<a href=\"#\" onclick=\"return billInfoAudit(this,'"+second_this.val()+"',"+lineNum+");\">上传附件</a>");
		        		}else{
		        			second_this.closest("td").closest("tr").find("#more").empty();
		        		}
		        	});
		        }
		    });
		}
		
		//报销校验
		function expense_validate(pass){
			
			if($("#applyType").val() == '2'){
				$("#recpProcName").addClass("required");
				$("#employees").addClass("required");
			}else{
				$("#recpProcName").removeClass("required");
				$("#employees").removeClass("required");
			}

			$('#flag').val(pass);
			var flag = true;			
			//校验科目附件
			$("td[id='more']").each(function(){
				var this_ = $(this);
				var senconValue = this_.closest("tr").find("#secondSub").find("option:selected").text();
				var subjectAttach = 0;
				if(this_.closest("tr").find("#secondSub").find("option:selected").attr("confobj") != null && this_.closest("tr").find("#secondSub").find("option:selected").attr("confobj") != 'undefined'){
					subjectAttach = this_.closest("tr").find("#secondSub").find("option:selected").attr("confobj").split(",").length;
				}
				var fileNameLength = 0;
				this_.find("input:hidden[id='fileName']").each(function(){
					if($(this).val() != 'undefined'){
						fileNameLength++;
					}
				});
				var required = 0;
				this_.find("#required").each(function(){
					if($(this).val() == "1"){
						required++;
					}
				});
				if(required > 0 && this_.find("a").length > 0 && fileNameLength < subjectAttach){
					alert("请上传"+senconValue+"附件");
					flag =  false;
				}
			});
			if(flag == false){
				return flag;
			}
			
			//判断明细是否添加
			var detailLength = $("#expenseDetails tbody tr").length;
			if(detailLength == 0){
				alert("请填写报销明细信息!");
				return false;
			}
			$('#saveFlag').val('no');
			$("#inputForm").submit();
		}
		var img_count = 0;
		//附件上传
		function file_upload(){
			$("[id^=expenseFile]").fileinput({
			    uploadUrl: "${ctx}/flow/expenseFlow/upload?", // server upload action
			    uploadAsync: true,
			    showPreview: false,
			    showRemove:true,
			    showCancel:true,
			    showBrowse: true,
			    allowedFileExtensions: ['jpg', 'jpeg', 'png', 'gif','doc','docx','xlsx','xls','pdf','rar'],
			    maxFileCount: 100,
			    language : 'zh',
			    elErrorContainer: '#kv-error-1',
			    uploadExtraData: function() {   //额外参数的关键点
                    var obj = {};
                    obj.src = $(this).attr('id');
                    return obj;
                }
			}).on('filebatchpreupload', function(event, data, id, index) {
				
			}).on('fileuploaded', function(event, data, id, index) {
				if(data.response.resCode == 1){
						$("#fileTd").append("<input type='hidden' name='expenseAttachments["+img_count+"].expenseAttachmentUrl' value='"+ data.response.storfiles.url+"'/>");
						$("#fileTd").append("<input type='hidden' name='expenseAttachments["+img_count+"].fileName' value='"+ data.response.storfiles.fileName+"'/>");
						$("#fileTd").append("<input type='hidden' name='expenseAttachments["+img_count+"].fileType' value='"+ data.response.storfiles.fileType+"'/>");
							var url = data.response.storfiles.serverUrl;
							url = url + data.response.storfiles.url;
							if(data.response.storfiles.fileType == "0"){
								$("#fileShow").append("<span id=\"fileSpan\"> ("+
								"<a id='expenseAttachmentId' href='"+url+"' target='_blank'>"+data.response.storfiles.fileName+"</a>"+
								" <a href='#' onclick=\"deleteThisAttachment(this,"+img_count+")\";>删除</a>"+
								") </span>");							
							}else{
								$("#fileShow").append("<span id=\"fileSpan\"> ("+
								"<a id='expenseAttachmentId' href='${ctx}/flow/expenseFlow/downFiles?url="+url+"&fileName="+data.response.storfiles.fileName+"'>"+data.response.storfiles.fileName+"</a>"+
								" <a href='#' onclick=\"deleteThisAttachment(this,"+img_count+")\";>删除</a>"+
								") </span>");							
							}
						
						img_count++;
						$("#btnSubmit").removeAttr("disabled");
						$("#btnSubmit2").removeAttr("disabled");
						$("#btnSubmit3").removeAttr("disabled");
						$("#btnCancel").removeAttr("disabled");
				}
				top.$.jBox.tip(data.response.resDesc);
			}).on('filelock',function(exent,id){ //上传过程中屏蔽按钮
				 $("#btnSubmit").attr("disabled","disabled");
				 $("#btnSubmit2").attr("disabled","disabled");
				 $("#btnSubmit3").attr("disabled","disabled");
				 $("#btnCancel").attr("disabled","disabled");
			});
			$(".modal-body").remove();
		}
		
		//附件上传
		function subject_file_upload(subjectCode){
			if($("#subFileTd") != null){
				var subFileTdLength = $("#subFileTd").length;
				if(subFileTdLength > 0){
					img_count = img_count+subFileTdLength - 1;
				}
			}
			
			$("[id^=subjectAttachmentInput]").fileinput({
			    uploadUrl: "${ctx}/flow/expenseFlow/upload?", // server upload action
			    uploadAsync: true,
			    showPreview: false,
			    showRemove:true,
			    showCancel:true,
			    showBrowse: true,
			    allowedFileExtensions: ['jpg', 'jpeg', 'png', 'gif'],
			    maxFileCount: 1,
			    language : 'zh',
			    elErrorContainer: '#kv-error-1',
			    uploadExtraData: function() {   //额外参数的关键点
                    var obj = {};
                    obj.src = $(this).attr('id');
                    return obj;
                }
			}).on('filebatchpreupload', function(event, data, id, index) {
				
			}).on('fileuploaded', function(event, data, id, index) {
				if(data.response.resCode == 1){
						var fileTd = $(this).closest("#subFileTd");
						var subCode = fileTd.closest("tr").attr("id");
						var subImgDes = fileTd.find("#subImgDes").text();
						var subImgConfId = fileTd.find("#subImgConfId").val();
						var lineNumber = fileTd.find("#detail_line_number").val();
						fileTd.find("input:hidden").remove();
						
						fileTd.append("<input type='hidden' name='expenseAttachments["+img_count+"].detailLineNumber' value='"+ lineNumber+"' />");
						fileTd.append("<input type='hidden' name='expenseAttachments["+img_count+"].subImgConfId' value='"+ subImgConfId+"' />");
						fileTd.append("<input type='hidden' name='expenseAttachments["+img_count+"].subImgDes' value='"+ subImgDes+"' />");
						fileTd.append("<input type='hidden' name='expenseAttachments["+img_count+"].subCode' value='"+ subCode+"'/>");
						fileTd.append("<input type='hidden' name='expenseAttachments["+img_count+"].expenseAttachmentUrl' value='"+ data.response.storfiles.url+"'/>");
						fileTd.append("<input type='hidden' name='expenseAttachments["+img_count+"].fileName' value='"+ data.response.storfiles.fileName+"'/>");
						fileTd.append("<input type='hidden' name='expenseAttachments["+img_count+"].fileType' value='"+ data.response.storfiles.fileType+"'/>");
							var url = data.response.storfiles.serverUrl;
							url = url + data.response.storfiles.url;
							if(data.response.storfiles.fileType == "0"){
								fileTd.find("#fileShow").find("#fileSpan").remove();
								fileTd.find("#fileShow").append("<span id=\"fileSpan\"> ("+
								"<a id='expenseAttachmentId' href='"+url+"' target='_blank'>"+data.response.storfiles.fileName+"</a>"+
								" <a href='#' onclick=\"deleteThisAttachment(this,"+img_count+")\";>删除</a>"+
								") </span>");							
							}else{
								fileTd.find("#fileShow").find("#fileSpan").remove();
								fileTd.find("#fileShow").append("<span id=\"fileSpan\"> ("+
								"<a id='expenseAttachmentId' href='${ctx}/flow/expenseFlow/downFiles?url="+url+"&fileName="+data.response.storfiles.fileName+"'>"+data.response.storfiles.fileName+"</a>"+
								" <a href='#' onclick=\"deleteThisAttachment(this,"+img_count+")\";>删除</a>"+
								") </span>");							
							}
						
							img_count++;
						$("#btnSubmit").removeAttr("disabled");
						$("#btnSubmit2").removeAttr("disabled");
						$("#btnSubmit3").removeAttr("disabled");
						$("#btnCancel").removeAttr("disabled");
				}
				top.$.jBox.tip(data.response.resDesc);
			}).on('filelock',function(exent,id){ //上传过程中屏蔽按钮
				 $("#btnSubmit").attr("disabled","disabled");
				 $("#btnSubmit2").attr("disabled","disabled");
				 $("#btnSubmit3").attr("disabled","disabled");
				 $("#btnCancel").attr("disabled","disabled");
			});
			$(".modal-body").remove();
		}
		
		
		//删除附件信息
		function deleteAttachment(this_attachment,attachmentId){
			if(window.confirm('确定要删除当前附件吗？')){
					var param = {};
					param["id"] = attachmentId; //附件ID
					$.ajax({
				        url:"${ctx}/flow/expenseFlow/deleteAttachment",//请求地址
				        data:param,
				        type:'post',
				        datatype:'json',
				        success:function(data){
				        	top.$.jBox.tip("删除成功");
				        	$(this_attachment).closest("#attachmentSpan").remove();
				        }
				    });
			}
		}
		
		function deleteThisAttachment(this_attachment,count){
			$(this_attachment).closest("#fileSpan").remove();
			$("input[name='expenseAttachments["+count+"].expenseAttachmentUrl']").remove();
			$("input[name='expenseAttachments["+count+"].fileName']").remove();
			$("input[name='expenseAttachments["+count+"].fileType']").remove();
			$("input[name='expenseAttachments["+count+"].subCode']").remove();
		}
		
		//自动汇总报销金额和票据张数
		function autoSum(){
			$("input[count='billNum'],input[count='expenseAmt']").on("blur",function(){
				countSum()
			});
		}
		
		function countSum(){
			//票据张数计算
			var billNumSum = 0;
			$("input[count='billNum']").each(function(){
				var this_ = $(this);
				billNumSum += parseInt(this_.val());
			});
			$("#billNums").val(billNumSum);
			
			//报销金额计算
			var expenseAmtSum = 0.00;
			$("input[count='expenseAmt']").each(function(){
				var this_ = $(this);
				expenseAmtSum += parseFloat(this_.val(),2);
			});
			$("#expenseTotal").val(expenseAmtSum.toFixed(2));
		}
		
		//删除报销单据
		function deleteFormInfo(){
			if(window.confirm('确定要删除该单据吗？')){
				$("input,select").each(function(){
					$(this).removeClass("required");
				});
				$("#inputForm").attr("action","${ctx}/flow/expenseFlow/delete").submit();
			}
		}
		
		//科目附件上传
		function billInfoAudit(secondSub,subCode, lineNumber){
			var sa = subjectAttach[subCode];
			var arr=new Array();
    		for(var i = 0;i<sa.length;i++){
    			if($(secondSub).closest("td").find("input:hidden[id='fileName']").length > 0){
    				var fileName = "";
    				var showUrl = "";
    				var url = "";
    				$(secondSub).closest("td").find("input:hidden[id='fileName']").each(function(){
    					var index = $(this).attr("index");
    					if($(this).val() != 'undefined'){
	    					var subImgDes = $(this).closest("td").find("input:hidden[name='expenseDetail["+lineNumber+"].expenseAttachment["+index+"].subImgDes']").val();
	    					if(subImgDes == sa[i]["confDesc"]){    						
		    					fileName = $(this).closest("td").find("input:hidden[name='expenseDetail["+lineNumber+"].expenseAttachment["+index+"].fileName']").val();
		        				showUrl = $(this).closest("td").find("input:hidden[id='expenseDetail["+lineNumber+"].expenseAttachment["+index+"].showUrl']").val();
		        				url = $(this).closest("td").find("input:hidden[name='expenseDetail["+lineNumber+"].expenseAttachment["+index+"].expenseAttachmentUrl']").val();
		        				arr.push("<ul style=\"list-style-type:none\" class='ul-form'><li><label id=\"subImgDes\">"+sa[i]["confDesc"]+"</label><input type=\"hidden\" name='subImgConfId' value="+sa[i]["id"]+" /></li>"+
		    	    					"<li><label id=\"fileShow\">"+
		    	    					"<a id=\"expenseAttachmentUrl\" href="+showUrl+" target=\"_blank\">"+fileName+"</a>"+
		    							"<input type=\"hidden\" name=\"imgUrl\" value="+url+" />"+
		    							"<input type=\"hidden\" name=\"imgName\" value="+fileName+" />"+
		    							"<input type=\"hidden\" name=\"required\" value="+sa[i]["isRequired"]+" />"+
		    	    					"</label></li>"+
		    	    			"<li><input type=\"file\" id=\"subjectAttachmentInput\" name=\"files\" multiple class=\"file-loading\" /></li></ul>");
	    					}
	    					if(subImgDes == ''){
	    						arr.push("<ul style=\"list-style-type:none\" class='ul-form'><li><label id=\"subImgDes\">"+sa[i]["confDesc"]+"</label><input type=\"hidden\" name='subImgConfId' value="+sa[i]["id"]+" /></li>"+
	    	        					"<li><label id=\"fileShow\"></label><input type=\"hidden\" name=\"required\" value="+sa[i]["isRequired"]+" /></li>"+
	    	        			"<li><input type=\"file\" id=\"subjectAttachmentInput\" name=\"files\" multiple class=\"file-loading\" /></li></ul>");		
	    					}
    					}else{
    						var subImgDes = $(this).closest("td").find("input:hidden[name='expenseDetail["+lineNumber+"].expenseAttachment["+index+"].subImgDes']").val();
    						if(subImgDes == sa[i]["confDesc"]){  
	    						arr.push("<ul style=\"list-style-type:none\" class='ul-form'><li><label id=\"subImgDes\">"+sa[i]["confDesc"]+"</label><input type=\"hidden\" name='subImgConfId' value="+sa[i]["id"]+" /></li>"+
	    	        					"<li><label id=\"fileShow\"></label><input type=\"hidden\" name=\"required\" value="+sa[i]["isRequired"]+" /></li>"+
	    	        			"<li><input type=\"file\" id=\"subjectAttachmentInput\" name=\"files\" multiple class=\"file-loading\" /></li></ul>");
    						}
    					}
    				});
    			}else{
    				arr.push("<ul style=\"list-style-type:none\" class='ul-form'><li><label id=\"subImgDes\">"+sa[i]["confDesc"]+"</label><input type=\"hidden\" name='subImgConfId' value="+sa[i]["id"]+" /></li>"+
        					"<li><label id=\"fileShow\"></label><input type=\"hidden\" name=\"required\" value="+sa[i]["isRequired"]+" /></li>"+
        			"<li><input type=\"file\" id=\"subjectAttachmentInput\" name=\"files\" multiple class=\"file-loading\" /></li></ul>");	
    			}
    		}
    		var str=arr.join("");
    		console.log(str);
    		
			top.$.jBox("<div style='padding:20px;text-align:center;'>" + str +
					"</div>", {
					title: "上传科目附件", 
			submit: function (v, h, f){
				var index = 0;
				$(secondSub).closest("td").find("input:hidden").remove();
				h.find("label[id='fileShow']").each(function(){
					var url = $(this).find("input:hidden[name='imgUrl']").val();
					var fileName = $(this).find("input:hidden[name='imgName']").val();
					var imgDesc = $(this).closest("ul").find("#subImgDes").text();
					var showUrl = $(this).find("a[id='expenseAttachmentUrl']").attr("href");
					var subImgConfId = $(this).closest("ul").find("input:hidden[name='subImgConfId']").val();
					var required = $(this).closest("ul").find("input:hidden[name='required']").val();
					$(secondSub).closest("td").append("<input type=\"hidden\" index="+index+" id='expenseAttachmentUrl' name = \"expenseDetail["+lineNumber+"].expenseAttachment["+index+"].expenseAttachmentUrl\" value="+url+" />");
					$(secondSub).closest("td").append("<input type=\"hidden\" index="+index+" id='fileName' name = \"expenseDetail["+lineNumber+"].expenseAttachment["+index+"].fileName\" value="+fileName+" />");
					$(secondSub).closest("td").append("<input type=\"hidden\" index="+index+" id='subImgDes' name = \"expenseDetail["+lineNumber+"].expenseAttachment["+index+"].subImgDes\" value="+imgDesc+" />");
					$(secondSub).closest("td").append("<input type=\"hidden\" index="+index+" id='subImgConfId' name = \"expenseDetail["+lineNumber+"].expenseAttachment["+index+"].subImgConfId\" value="+subImgConfId+" />");
					$(secondSub).closest("td").append("<input type=\"hidden\" index="+index+" id='required' name = \"expenseDetail["+lineNumber+"].expenseAttachment["+index+"].required\" value="+required+" />");
					$(secondSub).closest("td").append("<input type=\"hidden\" index="+index+" id='expenseDetail["+lineNumber+"].expenseAttachment["+index+"].showUrl' value="+showUrl+" />");
					index++;
				});
			},loaded:function(h){
				//加载附件
				h.find("[id^=subjectAttachmentInput]").fileinput({
				    uploadUrl: "${ctx}/flow/expenseFlow/upload?", // server upload action
				    uploadAsync: true,
				    showPreview: false,
				    showRemove:true,
				    showCancel:true,
				    showBrowse: true,
				    allowedFileExtensions: ['jpg', 'jpeg', 'png', 'gif'],
				    maxFileCount: 1,
				    language : 'zh',
				    elErrorContainer: '#kv-error-1',
				    uploadExtraData: function() {   //额外参数的关键点
	                    var obj = {};
	                    obj.src = $(this).attr('id');
	                    return obj;
	                }
				}).on('filebatchpreupload', function(event, data, id, index) {
					
				}).on('fileuploaded', function(event, data, id, index) {
					if(data.response.resCode == 1){
						$(this).closest("ul").find("#fileShow").empty();
						$(this).closest("ul").find("#fileShow").append("<a id=\"expenseAttachmentUrl\" href="+data.response.storfiles.serverUrl+data.response.storfiles.url+" target=\"_blank\">"+data.response.storfiles.fileName+"</a>");
						$(this).closest("ul").find("#fileShow").append("<input type=\"hidden\" name=\"imgUrl\" value="+data.response.storfiles.url+" />");
						$(this).closest("ul").find("#fileShow").append("<input type=\"hidden\" name=\"imgName\" value="+data.response.storfiles.fileName+" />");
					}
					top.$.jBox.tip(data.response.resDesc);
				}).on('filelock',function(exent,id){ //上传过程中屏蔽按钮
					
				});
				h.find(".modal-body").remove();
				h.find(".kv-zoom-body file-zoom-content").css("z-index","-9999");
			},closed:function(){
				if (typeof closed == 'function') {
					closed();
				}
			}});
			
			return false;
		}
		
		//报销类型切换事件
		function applyTypeChange(this_){
			if($(this_).find("option:selected").val() == '2'){
				$("#recpTr").show();
			}else{
				$("#recpTr").hide();
				$("#employees").val("");
				$("#s2id_employees").find(".select2-search-choice").remove();
				$("#recpProcName").val("");
				$("#recpProcCode").val("");
			}
		}
		
		//清除关联接待
		function relevanceRecpFlow(){
			$.jBox("iframe:${ctx}/flow/recpFlow/queryRecpFlowInfo", { 
			    title: "关联接待申请", 
			    width: 1000, 
			    height: 500, 
			    submit: function (v, h, f){
					var recpTheme = h.find("iframe").contents().find("input:radio[id='checkRedio']:checked").closest("tr").find("#recpTheme").text();
					var procCode = h.find("iframe").contents().find("input:radio[id='checkRedio']:checked").closest("tr").find("#procCode").text();
					$("#recpProcName").val($.trim(recpTheme));
					$("#recpProcCode").val($.trim(procCode));
				},
			});
		}
		
		//清除关联接待
		function clearRecp(){
			$("#recpProcName").val("");
			$("#recpProcCode").val("");
		}
		
		//清除关联项目
		function clearProject(){
			$("#projectName").val("");
			$("#projectId").val("");
			$("#projectPersonel").val("");
		}
		
		//关联项目
		function relevanceProjectFlow(){
			$.jBox("iframe:${ctx}/oa/projectInfo/relevanceList", { 
			    title: "关联项目", 
			    width: 1000, 
			    height: 500,
			    submit: function (v, h, f){
					var projectName = h.find("iframe").contents().find("input:radio[id='checkRedio']:checked").closest("tr").find("#projectName").text();
					var projectId = h.find("iframe").contents().find("input:radio[id='checkRedio']:checked").closest("tr").find("#projectId").val();
					var projectPersonel = h.find("iframe").contents().find("input:radio[id='checkRedio']:checked").closest("tr").find("#projectPersonel").text();
					$("#projectName").val($.trim(projectName));
					$("#projectId").val($.trim(projectId));
					$("#projectPersonel").val($.trim(projectPersonel));
				},
			});
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/flow/expenseFlow/">费用报销列表</a></li>
		<li class="active"><a href="${ctx}/flow/expenseFlow/form?id=${expenseFlow.id}">费用报销<shiro:hasPermission name="flow:expenseFlow:edit">${not empty expenseFlow.id?'修改':'申请'}</shiro:hasPermission><shiro:lacksPermission name="flow:expenseFlow:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="expenseFlow" action="${ctx}/flow/expenseFlow/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<form:hidden path="procCode" value="${expenseFlow.procCode}"/>
		<form:hidden path="procName" value="${expenseFlow.procName}"/>
		<form:hidden id="saveFlag" path="saveFlag" />
		<sys:message content="${message}"/>	
		<fieldset>
			<legend style="text-align: center;">费用报销详情</legend>
			<table class="table table-striped table-bordered table-condensed">
				<tbody>
				<tr>
					<td class="tit" colspan="4" style="text-align: center;"><b>基本信息</b></td>
				</tr>
				<tr>
					<td class="tit">报销人员</td>
					<td>
						${expenseFlow.applyPerName}
						<form:hidden path="applyPerCode" value="${expenseFlow.applyPerCode}"/>
						<form:hidden path="applyPerName" value="${expenseFlow.applyPerName}"/>
					</td>
					<td class="tit">报销日期</td>
					<td>
						<fmt:formatDate value="${expenseFlow.applyTime}" pattern="yyyy-MM-dd"/>
						<input name="applyTime" type="hidden" maxlength="20" class="input-medium Wdate"
							value="<fmt:formatDate value="${expenseFlow.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>	
					</td>
				</tr>
				<tr>
					<td class="tit">所属部门</td>
					<td>
						${expenseFlow.office.name}
						<form:hidden path="office.id" value="${expenseFlow.office.id}"/>
						<form:hidden path="office.name" value="${expenseFlow.office.name}"/>	
					</td>
					<td class="tit">所属岗位：</td>
					<td>
						${postName }
					</td>
					
				</tr>
				<tr>
					<td class="tit">票据数量</td><td><form:input path="billNum" readonly="true" id="billNums" type="number" htmlEscape="false" maxlength="5" class="input-xlarge  required"/></td>
					<td class="tit">费用合计</td><td><form:input path="expenseTotal" readonly="true" type="number" htmlEscape="false" pattern="##.##" minFractionDigits="2" class="input-xlarge  required"/></td>
				</tr>
				<tr>
					<td class="tit">报销类型</td>
					<td >
						<form:select path="applyType" class="input-xlarge  required"  onchange="applyTypeChange(this)">
							<form:options items="${fns:getDictList('oa_expense_type')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					
					<td class="tit">发票所属公司</td>
					<td >
						<form:select path="taxCityCode" class="input-xlarge  required">
							<form:options items="${fns:getDictList('tax_city')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<c:if test="${not empty expenseFlow.applyType && expenseFlow.applyType == '2'}">
					<tr id="recpTr">
					<td class="tit">接待申请</td>
						<td>
							<form:input path="recpProcName" id="recpProcName" readonly="true"  htmlEscape="false"  class="input-xlarge" onclick="relevanceRecpFlow();"/>
							<form:hidden path="recpProcCode" id="recpProcCode" value="${expenseFlow.recpProcCode}"/>
							<a href="#" onclick="clearRecp();">清除</a>	
						</td>
						<td class="tit">陪客人员</td>
						<td>
							<form:select id="employees" path="employees" class="input-xlarge" multiple="true" >
										<form:options items="${employeeList}" itemLabel="name" itemValue="loginName" htmlEscape="false"/>
							</form:select>
						</td>
					</tr>
				</c:if>
				<c:if test="${expenseFlow.applyType != '2'}">
					<tr id="recpTr" style="display:none">
					<td class="tit">接待申请</td>
						<td>
							<form:input path="recpProcName" id="recpProcName" readonly="true"  htmlEscape="false"  class="input-xlarge" onclick="relevanceRecpFlow();"/>
							<form:hidden path="recpProcCode" id="recpProcCode" value="${expenseFlow.recpProcCode}"/>
							<a href="#" onclick="clearRecp();">清除</a>	
						</td>
						<td class="tit">陪客人员</td>
						<td>
							<form:select path="employees" class="input-xlarge" multiple="true" >
										<form:options items="${employeeList}" itemLabel="name" itemValue="loginName" htmlEscape="false"/>
							</form:select>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="tit">项目名称</td>
					<td >
					<form:input id="projectName" path="projectName" readonly="true" htmlEscape="false" maxlength="300" class="input-xlarge" onclick="relevanceProjectFlow();"/>
					<form:hidden id="projectId" path="projectId"  htmlEscape="false" maxlength="300" class="input-xlarge "/>
					<a href="#" onclick="clearProject();">清除</a>
<%-- 						<form:select path="projectId" class="input-xlarge"> --%>
<!-- 							<option value="">---请选择---</option> -->
<%-- 							<form:options items="${fns:getDictList('oa_project')}"  itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<%-- 						</form:select> --%>
					<br/>
					<b style="color:red">提醒：请务必确认报销是否需要关联项目,否则费用将归属到部门及个人</b>
					</td>
					<td class="tit">项目负责人</td>
					<td>
						<form:input path="projectPersonel" readonly="true" id="projectPersonel" htmlEscape="false" class="input-xlarge"/>
					</td>
				</tr>
				<tr>
				</tr>
				</tbody>
			</table>
			<table id="expenseDetails" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<td class="tit" colspan="12" style="text-align: center;"><b>报销明细</b></td>
					</tr>
					<tr>
						<th style="text-align:center;"><input type="checkbox" name="checkAll" id="checkAll"/>选择</th>
						<th>发生日期</th>
						<th>起点</th>
						<th>结束日期</th>
						<th>终点</th>
						<th>一级科目</th>
						<th>二级科目</th>
						<th>人数</th>
						<th>天数</th>
						<th>票据张数</th>
						<th>报销金额</th>
						<th>备注</th>
						<th>更多操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${detailsList}" var="detail" varStatus="status">
						<tr id='tr${status.index}'> 
							<td style="text-align:center;"> 
							<input type="checkbox" name="box" value="${status.index}" /></td>
							<td>
								<input id="startDate${status.index}" name="expenseDetail[${status.index}].startDate" type="text" maxlength="32" class="input-mini required" 
									 value="<fmt:formatDate value="${detail.startDate }" pattern="yyyy-MM-dd"/>" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate${status.index}\')}'});"/>
							</td>
							<td>
								<%-- <input name="expenseDetail[${status.index}].startPoint" type="text" maxlength="32" value="${detail.startPoint }" class="input-mini required"/> --%>
								<input id="expenseDetail.startPointName" name="expenseDetail[${status.index}].startPointName" value="${detail.startPointName }" readOnly="readOnly" type="text" maxlength="32" class="input-mini required"/>
								<input id="expenseDetail.startPoint" name="expenseDetail[${status.index}].startPoint" value="${detail.startPoint }" readOnly="readOnly" type="hidden" maxlength="32" class="input-mini required"/>
							</td>
							<td>
								<input id="endDate${status.index}" name="expenseDetail[${status.index}].endDate" type="text" maxlength="32" class="input-mini required"
									value="<fmt:formatDate value="${detail.endDate }" pattern="yyyy-MM-dd"/>" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate${status.index}\')}'});"/>
							</td>
							<td>
								<%-- <input name="expenseDetail[${status.index}].endPoint" type="text" maxlength="32" value="${detail.endPoint }" class="input-mini required"/> --%>
								<input id="expenseDetail.endPointName" name="expenseDetail[${status.index}].endPointName" value="${detail.endPointName }" readOnly="readOnly" type="text" maxlength="32" class="input-mini required"/>
								<input id="expenseDetail.endPoint" name="expenseDetail[${status.index}].endPoint" value="${detail.endPoint }" readOnly="readOnly" type="hidden" maxlength="32" class="input-mini required"/>
							</td>
							<td>
								<select id="firstSub" name="expenseDetail[${status.index}].firstSub"  maxlength="32" class="input-small required"/>
								<input type="hidden" name="firstSub_val" value="${detail.firstSub }"></input>
							</td>
							<td>
								<select id="secondSub" name="expenseDetail[${status.index}].secondSub" maxlength="32" class="input-small"/>
								<input type="hidden" name="secondSub_val" value="${detail.secondSub }"></input>
							</td>
							<td>
								<input name="expenseDetail[${status.index}].personNum" type="number" maxlength="5" value="${detail.personNum }" class="input-mini required"/>
							</td>
							<td>
								<input name="expenseDetail[${status.index}].dayNum" type="number" maxlength="5" value="${detail.dayNum }" class="input-mini required"/>
							</td>
							<td>
								<input count="billNum" name="expenseDetail[${status.index}].billNum" type="number" maxlength="5" value="${detail.billNum }" class="input-mini required"/>
							</td>
							<td>
								<input count="expenseAmt" name="expenseDetail[${status.index}].expenseAmt" type="number" step="0.01" maxlength="32" value="${detail.expenseAmt }" class="input-mini required"/>
							</td>
							<td>
								<input name="expenseDetail[${status.index}].remarks" type="text" maxlength="300" value="${detail.remarks }" class="input-mini"/>
							</td>
							<td id="more">
							    <c:if test="${not empty detail.expenseAttachment}">
									<a href="#" onclick="return billInfoAudit(this,'${detail.secondSub}',${status.index});">上传附件</a>
									<c:forEach items="${detail.expenseAttachment}" var="dattachment" varStatus="st">
										<input type="hidden" index="${st.index}" id="fileName" name="expenseDetail[${status.index}].expenseAttachment[${st.index}].fileName" value="${dattachment.fileName}">
										<input type="hidden" index="${st.index}" id="subImgDes" name="expenseDetail[${status.index}].expenseAttachment[${st.index}].subImgDes" value="${dattachment.subImgDes}">
										<input type="hidden" index="${st.index}" id="expenseAttachmentUrl" name="expenseDetail[${status.index}].expenseAttachment[${st.index}].expenseAttachmentUrl" value="${dattachment.subImgUrl}">
										<input type="hidden" index="${st.index}" id="subImgConfId" name="expenseDetail[${status.index}].expenseAttachment[${st.index}].subImgConfId" value="${dattachment.subImgConfId}">
										<input type="hidden" index="${st.index}" id="required" name="expenseDetail[${status.index}].expenseAttachment[${st.index}].required" value="${dattachment.isRequired}">
										<input type="hidden" index="${st.index}" id="expenseDetail[${status.index}].expenseAttachment[${st.index}].showUrl" value="${dattachment.expenseAttachmentUrl}">
									</c:forEach>
							    </c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<div>
					<input id="addButton" class="btn btn-primary" type="button" value="新增"/>
					<input id="removeButton" class="btn btn-inverse" type="button" value="删除"/>
				</div>
			</table>
			<table class="table table-striped table-bordered table-condensed">
				<tr>
					<td class="tit">备注</td>
					<td><form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="300" class="input-xxlarge "/></td>
				</tr>
				<tr>
					<td rowspan="2" class="shuli" ><b>附件</b></td>
					<td></td>
				</tr>
				<tr>
					<td id="fileShow">
						<c:forEach items="${expenseAttachmentList}" var="attachment">
						  <c:if test="${empty attachment.subCode}">
						 	<span id="attachmentSpan">
							 	（
							 	<c:if test="${attachment.fileType == '0'}">						 		
									<a id="expenseAttachmentId" href="${attachment.expenseAttachmentUrl}" target="_blank">${attachment.fileName}</a>
							 	</c:if>
							 	<c:if test="${attachment.fileType != '0'}">						 		
									<a id="expenseAttachmentId" href="${ctx}/flow/expenseFlow/downFiles?url=${attachment.expenseAttachmentUrl}&fileName=${attachment.fileName}">${attachment.fileName}</a>
							 	</c:if>
								<a href="#" onclick="deleteAttachment(this,'${attachment.id}');">删除</a>
							  	）
						  	</span>
						  </c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td rowspan="2" class="shuli">上传</td>
					<td id="fileTd">
					<div></div>
						<input type="file" id="expenseFile" name="files" multiple class="file-loading" />
						<input type="hidden" id="img_expenseFile" name="expenseAttachment.expenseAttachmentUrl" />
						<input type="hidden" id="img_expenseFile_fileName" name="expenseAttachment.fileName">
					</td>
				</tr>
			</table>
		</fieldset>	
			
		<div class="form-actions">
			<shiro:hasPermission name="flow:expenseFlow:edit">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="提交申请" onclick="expense_validate('yes')"/>&nbsp;
			<c:if test="${empty expenseFlow.procInsId}">
				<input id="btnSubmit3" class="btn btn-primary" type="submit" value="报销保存" onclick="$('#saveFlag').val('yes')">
			</c:if>
			<c:if test="${not empty expenseFlow.id}">
				<input id="btnSubmit2" class="btn btn-inverse" type="button" value="删除申请" onclick="deleteFormInfo();"/>&nbsp;
			</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<c:if test="${not empty expenseFlow.id}">
			<act:histoicFlow procInsId="${expenseFlow.act.procInsId}" />
		</c:if>
	</form:form>
</body>
</html>