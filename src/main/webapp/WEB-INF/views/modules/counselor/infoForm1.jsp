<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学历教育情况</title>
	<meta name="decorator" content="blank"/>
    <script>
        function sumbitForm1(){
            //每一项都是必填,需要进行判断. 毕业时间需必须大于起始时间
            //获取表单对象

            //时间进行判断
            var targetUrl = $("#inputForm").attr("action");
            var data = $("#inputForm").serialize();
            $.ajax({
                url:targetUrl,
                type:"POST",
                cache: false,
                data:data,
                dataType:'json',
                success:function(data) {
                    if(data=='200'){
                        alert("提交成功")
                        self.opener.location.reload();
                        //测试父页面进行刷新
                        window.close();
                    }else if(data=='201'){
                        alert("请填写完整")
                    }
                }
            })
        }
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
	</ul><br/>
	<form:form id="inputForm" modelAttribute="educationtbl" action="${ctx}/counselor/saveForm1?tableId=${tableId}&personId=${personId}" method="post" class="form-horizontal">
        <form:input path="id" type="hidden"/>
		<sys:message content="${message}"/>
        <legend>基本情况</legend>
        <table id="contentTable" class="table table-striped table-bordered table-condensed">
            <tr>
                <td colspan="20" height="60px" width="100%" align="center"><span  style="font-size: 18px;align-content: center">学历教育情况</span></td>
            </tr>
            <tr>
                <td colspan="4" height="50px" width="20%" >办学类型<span style="color: red">*</span></td>
                <td colspan="6"  width="30%" >
                    <form:select path="schoolType" class="input-medium " cssStyle="width: 99%">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('school_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </td>
                <td colspan="4" width="20%" >毕业院校<span style="color: red">*</span></td>
                <td colspan="6" width="30%" ><form:input  maxlength="50" path="school"/></td>
            </tr>
            <tr>
               <td colspan="4" height="30px" width="20%" >所学专业<span style="color: red">*</span></td>
                <td colspan="6"  width="30%" >
                    <form:input path="specialty" maxlength="100"/>
                </td>
                <td colspan="4"  width="20%" >学习方式<span style="color: red">*</span></td>
                <td colspan="6"  width="30%" >
                    <form:select path="studyType" class="input-medium " cssStyle="width: 99%">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('study_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td colspan="4" height="30px" width="20%" >学历<span style="color: red">*</span></td>
                <td colspan="6"  width="30%" >
                   <%-- <form:input path="education"/>--%>
                    <form:select path="education" class="input-medium " cssStyle="width: 99%">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('education')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </td>
                <td colspan="4"  width="20%" >学制(年)<span style="color: red">*</span></td>
                <td colspan="6"  width="30%" ><form:input maxlength="3" path="studyYear" cssStyle="width: 80%"/></td>

            </tr>
            <tr>
                <td colspan="4" height="30px" width="20%" >起始时间<span style="color: red">*</span></td>
                <td colspan="6"  width="30%" >
                    <%--<form:input path="startTime"/>--%>
                    <input id="startTime" name="startTime" type="text"  maxlength="20" class="input-medium Wdate " cssStyle="width: 99%"
                           value="<fmt:formatDate value="${educationtbl.startTime}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                </td>
                <td colspan="4"  width="20%" >毕业时间<span style="color: red">*</span></td>
                <td colspan="6"  width="30%" >
                    <%--<form:input path="endTime"/>--%>
                    <input id="endTime" name="endTime" type="text" maxlength="20" class="input-medium Wdate " cssStyle="width: 99%"
                           value="<fmt:formatDate value="${educationtbl.endTime}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                </td>

            </tr>
            <tr>
                <td colspan="4" height="30px" width="20%" >校(院)长<span style="color: red">*</span></td>
                <td colspan="6"  width="30%" ><form:input  maxlength="10" path="schoolMaster" cssStyle="width: 93%" /></td>
                <td colspan="4"  width="20%" >证书编号<span style="color: red">*</span></td>
                <td colspan="6"  width="30%" >
                   <%-- <form:input path="getgctime"/>--%>
                       <form:input maxlength="20" path="zsNo" cssStyle="width: 80%"/>
                </td>

            </tr>
        </table>

        <br/>
		<div class="form-actions">

            <c:if test="${empty readOnly}">
            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="sumbitForm1()" value="提交"/>
            </c:if>
            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="javascript:window.close();" value="关闭"/>
			<%--<shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
            <shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" onclick="saveBaseInfo();"/>&nbsp;</shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>--%>
		</div>
	</form:form>


</body>
</html>