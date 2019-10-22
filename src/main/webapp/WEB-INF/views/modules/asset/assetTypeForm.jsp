<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>资产类别管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
        function changed(){
            var name = $("#name").val();
            if(/[`~!@#$%^&*_\-——+=<>?:"{}|,.\/;\\[\]·~！@#￥%&*\-+={}|《》？：“”【】、；‘’，。、]/im.test(name)){
                alert("禁止输入特殊字符!");
                $("#name").val("");
            }
            return;
        };

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li>
        <a href="${ctx}/oa/assetType/list">资产类别列表</a>
    </li>
    <li class="active">
        <a href="${ctx}/oa/assetType/form?id=${assetType.id}">资产类别
            <%--<shiro:hasPermission name="oa:assetType:edit">--%>
                ${not empty assetType.id?'修改':'添加'}
            <%--</shiro:hasPermission>--%>
            <%--<shiro:lacksPermission name="oa:assetType:edit">--%>
                <%--查看--%>
            <%--</shiro:lacksPermission>--%>
        </a>
    </li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="assetType" action="${ctx}/oa/assetType/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">分类编号：</label>
        <div class="controls">
            <form:input path="code" value="${assetType.code}" htmlEscape="false" maxlength="18" class="input-xlarge required"/>
            <span class="help-inline" style="color: red; ">*</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">分类名：</label>
        <div class="controls">
            <form:input path="name" id="name" onKeyUp="changed()" value="${assetType.name}" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
            <span class="help-inline" style="color: red; ">*</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">上级分类：</label>
        <div class="controls">
            <sys:treeselect id="parentId" name="parentId" value="${assetType.parentId}" labelName="parentName" labelValue="${assetType.parentName}"
                            title="类别" allowClear="true" url="/oa/assetType/treeData"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">计量单位：</label>
        <div class="controls">
            <form:input path="unit" value="${assetType.unit}" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
            <span class="help-inline" style="color: red; ">*</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注：</label>
        <div class="controls">
                <form:input path="remarks" value="${assetType.remarks}"  htmlEscape="false" maxlength="4000" class="input-xlarge"/>
        </div>
    </div>
    <div class="form-actions">
        <%--<shiro:hasPermission name="oa:assetType:edit">--%>
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        <%--</shiro:hasPermission>--%>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>