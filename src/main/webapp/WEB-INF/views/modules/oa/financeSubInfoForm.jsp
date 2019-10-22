<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同模版管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {
            $("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {//校验
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else if (element.is(":input")){
                        error.appendTo(element.parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
            $("input:radio[value='${financeSubInfo.conType}']").attr('checked','true');
            $("input:radio").change(function(){//根据控制类别，动态改变费用科目和成本中心的input框
                if($("input[type='radio']:checked").val() == '0'){
					$('#expensesSubIds').val("");
					$('#costCenterIds').val("");
                    var strexpen = "<input id=\"expensesSubIds\" name=\"expensesSubIds\"" +
					"htmlEscape=\"false\" readonly=\"true\" class=\"\"/>";
                    var strcost = "<input id=\"costCenterIds\" name=\"costCenterIds\"" +
						" htmlEscape=\"false\" readonly=\"true\" class=\"\"/>";
                    $('#expensesSub').html(strexpen);
                    $('#costCenter').html(strcost);
				}
                if($("input[type='radio']:checked").val() == '1'){
                    var strexpen ="<input id=\"expensesSubIds\" name=\"expensesSubIds\" type=\"hidden\"/>" +
						"<input id=\"expensesSubNames\" name=\"expensesSubNames\"" +
                        " placeholder=\"请输入科目编号/名称\"" +
						" htmlEscape=\"false\" readonly=\"true\" onclick=\"expensesSubSelect();\"" +
						" class=\"required\"/><a href=\"javascript:\" class=\"btn\"" +
						"  onclick=\"expensesSubSelect();\" style=\"\">&nbsp;<i class=\"icon-search\"" +
						"></i>&nbsp;</a><span class=\"help-inline\"><font color=\"red\">*</font> </span>";
                    var strcost = "<input id=\"costCenterIds\" name=\"costCenterIds\" type=\"hidden\"/>" +
						"<input id=\"costCenterNames\" name=\"costCenterNames\" htmlEscape=\"false\"" +
                        " placeholder=\"请输入科目编号/名称\"" +
						" readonly=\"true\" onclick=\"costCenterSelect();\" class=\" required\"/>" +
						"<a href=\"javascript:\" class=\"btn\" onclick=\"costCenterSelect();\"" +
						" style=\"\">&nbsp;<i class=\"icon-search\"></i>&nbsp;</a><span class=\"help-inline\"" +
						"><font color=\"red\">*</font> </span>";
					$('#expensesSub').html(strexpen);
                    $('#costCenter').html(strcost);
                }
                if($("input[type='radio']:checked").val() == '2'){
                    $('#costCenterIds').val("");
                    var strexpen ="<input id=\"expensesSubIds\" name=\"expensesSubIds\" type=\"hidden\"/>" +
                        "<input id=\"expensesSubNames\" name=\"expensesSubNames\"" +
                        " placeholder=\"请选择(输入科目编号/名称)-多选\"" +
                        " htmlEscape=\"false\" readonly=\"true\" onclick=\"expensesSubSelect();\"" +
                        " class=\"required\"/><a href=\"javascript:\" class=\"btn\"" +
                        "  onclick=\"expensesSubSelect();\" style=\"\">&nbsp;<i class=\"icon-search\"" +
                        "></i>&nbsp;</a><span class=\"help-inline\"><font color=\"red\">*</font> </span>";
                    var strcost = "<input id=\"costCenterIds\" name=\"costCenterIds\" htmlEscape=\"false\" readonly=\"true\" class=\"\"/>";
                    $('#expensesSub').html(strexpen);
                    $('#costCenter').html(strcost);
                }
                if($("input[type='radio']:checked").val() == '3'){
                    $('#expensesSubIds').val("");
                    var strexpen = "<input id=\"expensesSubIds\" name=\"expensesSubIds\" htmlEscape=\"false\" readonly=\"true\" class=\"\"/>";
                    var strcost = "<input id=\"costCenterIds\" name=\"costCenterIds\" type=\"hidden\"/>" +
                        "<input id=\"costCenterNames\" name=\"costCenterNames\" htmlEscape=\"false\"" +
                        " placeholder=\"请选择(输入科目编号/名称)-多选\"" +
                        " readonly=\"true\" onclick=\"costCenterSelect();\" class=\"required\"/>" +
                        "<a href=\"javascript:\" class=\"btn\" onclick=\"costCenterSelect();\"" +
                        " style=\"\">&nbsp;<i class=\"icon-search\"></i>&nbsp;</a><span class=\"help-inline\"" +
                        "><font color=\"red\">*</font> </span>";
                    $('#expensesSub').html(strexpen);
                    $('#costCenter').html(strcost);
                }
            });
        });
        function expensesSubSelect(){//科目弹窗
            // 正常打开
            top.$.jBox.open("iframe:${ctx}/tag/treeselect?url="+encodeURIComponent("/oa/expensesSubInfo/treeData")+"&checked=true", "费用科目", 300, 420, {
                ajaxData:{selectIds: $("#expensesSubIds").val()},buttons:{"确定":"ok", "清除":"clear","关闭":true}, submit:function(v, h, f){
                    if (v=="ok"){
                        var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
                        var ids = [], names = [];
						nodes = tree.getCheckedNodes(true);
                        if(nodes.length==0){
                            top.$.jBox.tip('请先选择费用科目.');
                            return;
                        }
                        for(var i=0; i<nodes.length; i++) {//
                            if (nodes[i].isParent){
                                continue; // 如果为复选框选择，则过滤掉父节点
                            }
                            ids.push(nodes[i].id);
                            names.push(nodes[i].name);//
                        }
                        $("input[name='expensesSubNames']").val(names);
                        $("input[name='expensesSubIds']").val(ids);
                    }else if (v=="clear"){
                        $("#expensesSubNames").val("");
                        $("#expensesSubIds").val("");
                    }
                    if(typeof labelTreeselectCallBack == 'function'){
                        labelTreeselectCallBack(v, h, f);
                    }
                }, loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        };
        function costCenterSelect(){//成本中心弹窗
            // 正常打开
            top.$.jBox.open("iframe:${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=4")+"&checked=true", "成本中心", 300, 420, {
                ajaxData:{selectIds: $("#costCenterIds").val()},buttons:{"确定":"ok", "清除":"clear","关闭":true}, submit:function(v, h, f){
                    if (v=="ok"){
                        var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
                        var ids = [], names = [], mobiles = [], userIds = [];
                        nodes = tree.getCheckedNodes(true);
                        if(nodes.length==0){
                            top.$.jBox.tip('请先选择成本中心.');
                            return;
                        }
                        for(var i=0; i<nodes.length; i++) {//
                            if (nodes[i].isParent){
                                continue; // 如果为复选框选择，则过滤掉父节点
                            }
                            ids.push(nodes[i].id);
                            names.push(nodes[i].name);//
                        }
                        $("input[name='costCenterNames']").val(names);
                        $("input[name='costCenterIds']").val(ids);
                    }else if (v=="clear"){
                        $("#costCenterNames").val("");
                        $("#costCenterIds").val("");
                    }
                    if(typeof labelTreeselectCallBack == 'function'){
                        labelTreeselectCallBack(v, h, f);
                    }
                }, loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        };
        function changed(){
            var name = $("#subCode").val();
            if(/[`~！@#￥%&*\-+={}|（）《》？：“”【】、；‘’，。、a-zA-Z\u4e00-\u9fa5]/im.test(name)){
                alert("仅支持“数字”和英文格式特殊字符!");
                $("#subCode").val("");
            }
            return;
        };
	</script>
</head>
    <body>
        <ul class="nav nav-tabs">
            <li><a href="${ctx}/oa/financeSubInfo/">财务科目列表</a></li>
            <li class="active"><a href="${ctx}/oa/financeSubInfo/form?id=${financeSubInfo.id}">财务科目编辑</a></li>
        </ul>
        <br/>
        <form:form id="inputForm" modelAttribute="financeSubInfo" action="${ctx}/oa/financeSubInfo/save" method="post" class="form-horizontal">
            <input type="hidden" id="id" name="id" value="${financeSubInfo.id }"/>
            <sys:message content="${message}"/>
            <div class="control-group">
                <label class="control-label">科目编号：</label>
                <div class="controls">
                    <form:input id="subCode" placeholder="请输入" path="subCode" onchange="changed();" onkeyup="changed();" onpaste="changed();" htmlEscape="false" maxlength="14" class="input-medium required"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">科目名称：</label>
                <div class="controls">
                    <form:input path="subName" placeholder="请输入" htmlEscape="false" maxlength="32" class="input-medium required"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">上级科目：</label>
                <div class="controls">
                    <sys:treeselect id="parId" name="parId" value="${financeSubInfo.parId}" labelName="parName" labelValue="${financeSubInfo.parName}"
                                    title="上级科目" url="/oa/financeSubInfo/treeData?extId=${financeSubInfo.id}" allowClear="true" notAllowSelectParent="false"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">控制类别：</label>
                <div class="controls">
                    <input name="conType" type="radio" value="0" checked="checked">不关联费用科目和部门
                    <input name="conType" type="radio" value="1">关联费用科目，部门
                    <input name="conType" type="radio" value="2">关联费用科目
                    <input name="conType" type="radio" value="3">关联部门
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">费用科目：</label>
                <div id="expensesSub" class="controls">
                <c:choose>
                    <c:when test="${ financeSubInfo.conType == '1' ||  financeSubInfo.conType == '2' }">
                        <input id="expensesSubIds" name="expensesSubIds" value="${financeSubInfo.expensesSubIdStr}" type="hidden"/>
                        <input id="expensesSubNames" placeholder="请输入科目编号/名称" name="expensesSubNames" value="${financeSubInfo.expensesSubNameStr}"
                         htmlEscape="false" readonly="true" onclick="expensesSubSelect();"
                         class="required"/><a href="javascript:" class="btn"
                        onclick="expensesSubSelect();" style="">&nbsp;<i class="icon-search"
                        ></i>&nbsp;</a><span class="help-inline"><font color="red">*</font> </span>
                    </c:when>
                    <c:otherwise>
                        <form:input id="expensesSubIds" path="expensesSubIds" htmlEscape="false" readonly="true" class="input-medium"/>
                    </c:otherwise>
                </c:choose>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">成本中心：</label>
                <div id="costCenter" class="controls">
                    <c:choose>
                        <c:when test="${ financeSubInfo.conType == 1 ||  financeSubInfo.conType == 3 }">
                            <input id="costCenterIds" name="costCenterIds" value="${financeSubInfo.costCenterIdStr}" type="hidden"/>
                            <input id="costCenterNames" placeholder="请输入科目编号/名称" name="costCenterNames" htmlEscape="false" value="${financeSubInfo.costCenterNameStr}"
                             readonly="true" onclick="costCenterSelect();" class="required"/>
                            <a href="javascript:" class="btn" onclick="costCenterSelect();"
                             style="">&nbsp;<i class="icon-search"></i>&nbsp;</a><span class="help-inline"
                            ><font color="red">*</font> </span>
                        </c:when>
                        <c:otherwise>
                            <form:input id="costCenterIds" path="costCenterIds" htmlEscape="false" readonly="true" class="input-medium"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">备注：</label>
                <div class="controls">
                    <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="300" class="input-xlarge "/>
                </div>
            </div>
            <div class="form-actions">
                <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return validateForm(this)" value="保 存"/>&nbsp;
                <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
            </div>
        </form:form>
	</body>
</html>