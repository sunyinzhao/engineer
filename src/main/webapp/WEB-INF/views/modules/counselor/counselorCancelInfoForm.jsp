<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<%--<title>基本情况</title>--%>
	<meta name="decorator" content="default"/>
    <script src="${ctxStatic}/jquery-print/jQuery.print.js" type="text/javascript"></script>
	<script type="text/javascript">
        $(function (){
            var value = $("#cancelType").val();
            if(value=='13'){
                $("#hiddenDiv").removeAttr('hidden')
            }else{
                $("#hiddenDiv").attr("hidden","hidden")
            }
        })

        //确认按钮, 跳到外一层.
        function saveConfirm(){
            document.getElementById("btnSubmit1").disabled=true;
            document.getElementById("btnSubmit").disabled=true;
            $("#inputForm").attr("action","${ctx}/counselor/cancel/saveInfo?tableId=${tableId}&flag=1")
            $("#inputForm").submit();
        }

        //保存按钮, 跳本页面
        function saveForm(){
            document.getElementById("btnSubmit1").disabled=true;
            document.getElementById("btnSubmit").disabled=true;
            $("#inputForm").submit();
        }

        //打印
        function jqp(){
            //测试:打印之前去掉整个页面的样式
           // $("#id").remove("")
            jQuery('#printDiv').print(
                {
                    //Use Global styles
                    globalStyles : true
                    ,mediaPrint : true
//		                 stylesheet : "http://fonts.googleapis.com/css?family=Inconsolata",
                    ,iframe : true
                    ,noPrintSelector : ".avoid-this"
                    ,prepend : "Hello World!!!<br/>"
                    ,append : "<br/>Buh Bye!"
                    ,deferred: $.Deferred().done(function() { console.log('Printing done', arguments); })


                }
            );
        }

        function hiddenDiv(obj){
            var value = $(obj).val();
            if(value=='13'){
                $("#hiddenDiv").removeAttr('hidden')
            }else{
                $("#hiddenDiv").attr("hidden","hidden")
            }
        }

	</script>
    <style>
        .table-span{font-weight: bold}
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="">注销登记</a></li>
    <li class="active"><a href="">基本情况</a></li>
</ul>
    <input type="hidden" id="personRecordId" value="${personRecordId}"/>
    <input type="hidden" id="personId" value="${enterpriseWorkers.id}"/>
    <input type="hidden" id="tableId" value="${tableId}"/>
    <input type="hidden" id="cancelType" value="${counselor.cancelType}"/>

    <%--<legend>基本情况</legend>--%>
    <div style="width:750pt; border: 1px solid #999;">
    <div id="printDiv" style="width: 700pt;">
	<form:form id="inputForm" modelAttribute="counselor" action="${ctx}/counselor/cancel/saveInfo?tableId=${tableId}" method="post" class="form-horizontal">
        <form:input path="id" type="hidden"/>
		<sys:message content="${message}"/>
        <table class="table-form">
            　　<tr align="center">
                    <td colspan="20" height="50px" width="100%" ><span style="font-size: 30px">基本情况</span></td>
                </tr>
            　　<tr align="center">
            <td width="20%" height="30px" colspan="4"><span style="font-weight: bold">姓名</span></td>
                    <td  width="30%" colspan="6">
                        ${enterpriseWorkers.name}
                    </td>
            <td  width="20%"colspan="4"><span style="font-weight: bold">性别</span></td>
                    <td  width="30%" colspan="6">
                            ${fns:getDictLabel(enterpriseWorkers.sex,'sex','' )}
                    </td>
                 </tr>
            　　<tr align="center">
            <td height="30px" width="20%" colspan="4"><span style="font-weight: bold">身份证类型</span></td>
                    <td width="20%" colspan="4">
                        ${fns:getDictLabel(enterpriseWorkers.certificatesType,'ID_type','' )}
                    </td>
            <td width="30%" colspan="6"><span style="font-weight: bold">证件号</span></td>
                    <td width="30%" colspan="6">
                        ${enterpriseWorkers.certificatesNum}
                    </td>
                </tr>
                <tr align="center">
                    <td height="40px" width="20%" colspan="4"><span style="font-weight: bold">联系电话</span></td>
                    <td width="40%" colspan="8">
                        ${enterpriseWorkers.mobile}
                    </td>
                    <td width="10%" colspan="2"><span style="font-weight: bold">邮箱</span></td>
                    <td width="30%" colspan="6">
                            ${enterpriseWorkers.email}
                    </td>
                 </tr>


                <tr align="center">
                    <td colspan="2" rowspan="3">
                        <span style="font-weight: bold">执业情况</span>
                    </td>
                    <td  height="30px" colspan="8">
                        <span style="font-weight: bold">原登记证书编号</span>
                    </td>
                    <td  height="30px" colspan="12">
                        ${enterpriseWorkers.registerCertificateNum}
                    </td>
                </tr>

                <tr align="center">
                    <td  height="30px" colspan="4">
                        <span style="font-weight: bold">原主专业</span>
                    </td>
                    <td  height="30px" colspan="6">
                        ${fns:getDictLabel(enterpriseWorkers.registerMainSpecialty,"specialty_type" ,"" )}
                    </td>
                    <td   colspan="4">
                        <span style="font-weight: bold">原辅专业</span>
                    </td>
                    <td   colspan="6">
                            ${fns:getDictLabel(enterpriseWorkers.registerAuxiliarySpecialty,"specialty_type" ,"" )}
                    </td>
                </tr>

                <tr align="center">
                        <td height="40px" colspan="8">
                            <span style="font-weight: bold">原执业单位</span>
                        </td>
                    <td height="40px" colspan="12">
                        ${enterpriseWorkers.companyName}
                    </td>
                </tr>



                <tr align="center">
                    <td height="30px" colspan="4" >
                        <span style="font-weight: bold">注销原因</span>
                    </td>
                    <td colspan="14">
                        <form:select path="cancelType" class="input-medium " cssStyle="width: 60%" onchange="hiddenDiv(this)">
                            <form:option  value="" label=""/>
                            <form:options items="${fns:getDictList('cancel_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                        <div id="hiddenDiv" hidden>
                        描述:<form:input path="remarks"/>
                        </div>
                    </td>
                </tr>
            <tr align="right">
                <td colspan="2"  height="240px" >执业单位意见</td>
                <td colspan="18" style="padding-right:40px;padding-top:120px">
                    单位盖章(公章)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</br></br>                     年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
                 </td>
            </tr>


        </table>
    </div>
    </div>

        <br/>
		<div class="form-actions">

            <input id="jqprint" class="btn" type="button" value="打 印" onclick="jqp()"/>

            <c:if test="${empty look}">

            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveForm()" value="保存"/>
            </c:if>

            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
            <c:if test="${empty look}">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="btnSubmit1" class="btn btn-primary" type="button" onclick="saveConfirm()" value="确认完成"/>
            </c:if>
		</div>
	</form:form>


</body>
</html>