<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>培训院校</title>
	<meta name="decorator" content="blank"/>
    <script>
        function sumbitForm3(){
            //必填索引
            var must=[1,2,3,4,5,6,7,9]
            //非必填项.
            var form = document.getElementById("inputForm");
            for(var i =0;i<must.length;i++){//序号8为非必填. 必填项为:
                var value = form.elements[must[i]].value
                if(value==null||value==''){
                    alert("必填项请填写完整")
                    return;
                }
            }
            //起始时间与终止时间进行判断
            var start = $("#startTime").val();
            var end = $("#endTime").val();
            if(start>end){
                alert("起始时间不能大于终止时间")
                return;
            }


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
                        self.opener.location.reload();
                        alert("提交成功")
                        window.close();
                    }
                }
            })
        }
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
	</ul><br/>
	<form:form id="inputForm" modelAttribute="specialtyTrain" action="${ctx}/counselor/saveForm3?tableId=${tableId}&personId=${personId}" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
        <form:input path="id" type="hidden"/>
        <legend>基本情况</legend>
        <table id="contentTable" class="table table-striped table-bordered table-condensed">
            <tr>
                <td colspan="20" height="60px" width="100%" align="center"><span  style="font-size: 18px;align-content: center">专业培训情况</span></td>
            </tr>
            <tr>
                <td colspan="4" height="20px" width="20%" >培训院校<span style="color: red">*</span></td>
                <td colspan="16" width="80%" ><form:input path="trainSchool" maxlength="80"  cssStyle="width: 97%" /></td>
            </tr>
            <tr>
               <td colspan="4" height="20px" width="20%" ><div align="center">证书类型<span style="color: red">*</span></div></td>
                <td colspan="6"  width="30%" ><div align="center">所学专业<span style="color: red">*</span></div></td>
                <td colspan="5"  width="25%" ><div align="center">培训起始时间<span style="color: red">*</span></div></td>
                <td colspan="5"  width="25%" ><div align="center">培训终止时间<span style="color: red">*</span></div></td>
            </tr>
            <tr>
                <td colspan="4" height="20px" width="20%" ><form:input path="trainType" maxlength="20" cssStyle="width: 90%"/></td>
                <td colspan="6"  width="30%" ><form:input path="specialty" maxlength="30"  cssStyle="width: 90%"/></td>
                <td colspan="5"  width="25%" >
                    <input id="startTime" name="startTime" type="text"  maxlength="20" class="input-medium Wdate "
                           value="<fmt:formatDate value="${specialtyTrain.startTime}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                </td>
                <td colspan="5"  width="25%" >
                    <input id="endTime" name="endTime" type="text"  maxlength="20" class="input-medium Wdate "
                           value="<fmt:formatDate value="${specialtyTrain.endTime}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

                </td>

            </tr>
            <tr>
                <td colspan="6" height="20px" width="30%" ><div align="center">证书编号<span style="color: red">*</span></div></td>
                <td colspan="4"  width="20%" ><div align="center">学习方式<span style="color: red">*</span></div></td>
                <td colspan="5"  width="25%" ><div align="center">校长</div></td>
                <td colspan="5"  width="25%" ><div align="center">颁证时间<span style="color: red">*</span></div></td>

            </tr>
            <tr>
                <td colspan="6" height="20px" width="30%" ><form:input path="cardnum" maxlength="30" cssStyle="width: 90%"/></td>
                <td colspan="4"  width="20%" ><form:input path="studyType" maxlength="30" cssStyle="width: 90%"/></td>
                <td colspan="5"  width="25%" ><form:input path="schoolMaster" maxlength="20" cssStyle="width: 90%"/></td>
                <td colspan="5"  width="25%" >
                    <input name="getgctime" type="text"  maxlength="20" class="input-medium Wdate "
                           value="<fmt:formatDate value="${specialtyTrain.getgctime}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                </td>
            </tr>
        </table>

        <br/>
		<div class="form-actions">

            <c:if test="${empty readOnly}">
            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="sumbitForm3()" value="提交"/>
            </c:if>
            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="javascript:window.close();" value="关闭"/>
			<%--<shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
            <shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" onclick="saveBaseInfo();"/>&nbsp;</shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>--%>
		</div>
	</form:form>


</body>
</html>