<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<%--<title>基本情况</title>--%>
	<meta name="decorator" content="default"/>
    <script src="${ctxStatic}/jquery-print/jQuery.print.js" type="text/javascript"></script>
	<script type="text/javascript">
        $(function(){
            var confirmFive = "${personRegister.confirmFive}";
            if(confirmFive!=null&&confirmFive!=""&&confirmFive==1){
                $("#jobCompanyName").removeAttr("disabled")
                $("input[name='companyType']").removeAttr("disabled")
                $("input[name='backupType']").removeAttr("disabled")
            }
        })

        function openJsp(obj){
            var tableId = $("#tableId").val();
            var personId = $("#personId").val();
            if (obj =='2'){
            	window.open ("${ctx}/counselor/openJsp?num="+obj+"&tableId="+tableId+"&personId="+personId,"window","height=400,width=800,scrollbars=no,location=no");	
            }
            else{
            	window.open ("${ctx}/counselor/openJsp?num="+obj+"&tableId="+tableId+"&personId="+personId,"window","height=600,width=700,scrollbars=no,location=no");	
                
            }
        }

        //根据是否符合第五条  决定工作单位，工作单位性质，备案情况
        function changeDown(num){
            if(num==1){
                $("#jobCompanyName").removeAttr("disabled");
                $("input[name='companyType']").removeAttr("disabled");
                $("input[name='backupType']").removeAttr("disabled");
            }else{
                $("#jobCompanyName").attr("disabled","true");
                $("#jobCompanyName").val("");
                $("input[name='companyType']").attr("disabled","true");
                $("input[name='companyType']").removeAttr("checked");
                $("input[name='backupType']").attr("disabled","true");
                $("input[name='backupType']").removeAttr("checked");
            }
        }

        //确认按钮, 跳到外一层.
        function saveConfirm(){
            var a = validate();
            if(!a){
                return;
            }
            document.getElementById("btnSubmit1").disabled=true;
            document.getElementById("btnSubmit").disabled=true;
            $("#inputForm").attr("action","${ctx}/counselor/personRegister/saveInfo?tableId=${tableId}&recordId=${recordId}&personId=${personId}&flag=1")
            $("#inputForm").submit();
        }

        //保存按钮, 跳本页面
        function saveForm(){
            var a = validate();
            if(!a){
                return;
            }
            document.getElementById("btnSubmit1").disabled=true;
            document.getElementById("btnSubmit").disabled=true;
            $("#inputForm").submit();
        }

        function validate(){
            var confirmFive=$('input:radio[name="confirmFive"]:checked').val();
            /*var accountantCheck=$('input:radio[name="accountantCheck"]:checked').val();*/
            var form = document.getElementById("inputForm");

            if(confirmFive==null){
                alert("请填写完整")
                return false;
            }else if(confirmFive==1){
                var backupType=$('input:radio[name="backupType"]:checked').val();
                var companyType=$('input:radio[name="companyType"]:checked').val();
                if(backupType==null||companyType==null){
                    alert("请填写完整")
                    return false;
                }
            }

//                 var result = form.elements[2].value;
//                 if(result==''){
//                     alert("请填写完整")
//                     return false;
//                 }
                return true;
            //判断
            // 1.工作单位   jobCompanyName 2
            //  2.备案情况  redio backupType
            //3.工作单位性质  redio companyType
            //4.继续教育   redio iseducate
            //5.上一年度  readio    accountantCheck
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

        //选项框改变时间

	</script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="">继续登记</a></li>
    <li class="active"><a href="">基本情况</a></li>
</ul>
    <input type="hidden" id="personRecordId" value="${personRecordId}"/>
    <input type="hidden" id="personId" value="${enterpriseWorkers.id}"/>
    <input type="hidden" id="tableId" value="${tableId}"/>
    <%--<legend>基本情况</legend>--%>
    <div style="width:750pt; border: 1px solid #999;">
    <div id="printDiv" style="width: 700pt;">
	<form:form id="inputForm" modelAttribute="personRegister" action="${ctx}/counselor/personRegister/saveInfo?tableId=${tableId}&recordId=${recordId}&personId=${personId}" method="post" class="form-horizontal">
        <form:input path="id" type="hidden"/>
       <%-- <form:input path="title" type="hidden"/>--%>
		<sys:message content="${message}"/>
        <table class="table-form">
            　　<tr align="center">
                    <td colspan="20" height="40px" width="100%" ><span style="font-size: 30px">基本情况</span></td>
                </tr>
            　　<tr align="center">
                    <td width="20%" height="40px" colspan="4">姓名</td>
                    <td  width="30%" colspan="6">
                        ${enterpriseWorkers.name}
                    </td>
                    <td  width="20%"colspan="4">性别</td>
                    <td  width="30%" colspan="6">
                            ${fns:getDictLabel(enterpriseWorkers.sex,'sex','' )}
                    </td>
                 </tr>


            <tr align="center">
                <td width="20%" height="40px" colspan="4">身份证件类型</td>
                <td  width="30%" colspan="6">
                        ${fns:getDictLabel(enterpriseWorkers.certificatesType,'ID_type','' )}
                </td>
                <td  width="20%"colspan="4">证件号</td>
                <td  width="30%" colspan="6">
                        ${enterpriseWorkers.certificatesNum}
                </td>
            </tr>
            　　
                <tr align="center">
                    <th height="50px" width="20%" colspan="4">联系电话</th>
                    <td width="30%" colspan="6">
                        ${enterpriseWorkers.mobile}
                    </td>
                    <th width="20%" colspan="4">邮箱</th>
                    <td width="30%" colspan="6">
                        ${enterpriseWorkers.email}
                    </td>
                 </tr>


                <tr align="center">
                    <td colspan="2" rowspan="6">
                        申请登记情况
                    </td>
                    <td  height="40px" colspan="4">
                        执业单位
                    </td>
                    <td  height="40px" colspan="6">
                        <input type="hidden" name="companyName" value="${enterpriseWorkers.companyName}">
                        ${enterpriseWorkers.companyName}
                    </td>
                    <td   colspan="4">
                        预审单位
                    </td>
                    <td   colspan="6">
                            ${enterpriseWorkers.officeName}
                    </td>
                </tr>

                <tr align="center">
                    <td height="40px" colspan="10">
                        是否采用登记规程第五条
                    </td>

                    <td colspan="10">
                        <form:radiobutton path="confirmFive" value="1" onclick="changeDown(1)"/>是
                        <form:radiobutton path="confirmFive" value="0" onclick="changeDown(0)"/>否
                    </td>
                </tr>

                <tr align="center">
                    <td height="40px" colspan="4">
                        工作单位
                    </td>
                    <td colspan="14">
                        <form:input path="jobCompanyName" disabled="true" maxlength="80" cssStyle="width: 95%"/>
                    </td>

                </tr>

                <tr align="center">
                    <td height="40px" colspan="4">
                        <span >工作单位性质</span>
                    </td>
                    <td colspan="6">
                        <form:radiobuttons path="companyType" itemLabel="label" disabled="true" items="${fns:getDictList('declare_company_type')}" itemValue="value"   class="i-checks carries_type" htmlEscape="false"/>
                    </td>
                    <td height="40px" colspan="4">
                        <span >备案情况</span>
                    </td>
                    <td colspan="6">
                        <form:radiobuttons path="backupType" itemLabel="label" disabled="true" items="${fns:getDictList('backup_type')}" itemValue="value"   class="i-checks carries_type" htmlEscape="false"/>
                    </td>
                </tr>

            <%--<tr align="center">
                <td height="40px" colspan="10">
                    继续教育是否满足要求
                </td>

                <td colspan="10">
                    <form:radiobutton path="iseducate" value="1"/>满足
                    <form:radiobutton path="iseducate" value="0"/>不满足
                </td>
            </tr>--%>

            <tr align="center">
                <td height="40px" colspan="4">
                    登记证书编号
                </td>
                <td colspan="6">
                    ${enterpriseWorkers.registerCertificateNum}
                  <%--  <form:input path="registerCertificateNum"/>--%>
                </td>

                <%--<td height="40px" colspan="4">
                    上一年度执业检查结论
                </td>
                <td colspan="6">
                   <form:radiobutton path="accountantCheck" value="1" disabled="true"/>合格
                    <form:radiobutton path="accountantCheck" value="0" disabled="true"/>不合格
                </td>--%>
            </tr>

            <tr align="center">
                <td height="40px" colspan="4">
                    主专业
                </td>
                <td colspan="6">
                    <input type="hidden" name="registerMainSpecialty" value="${enterpriseWorkers.registerMainSpecialty}">
                    ${fns:getDictLabel(enterpriseWorkers.registerMainSpecialty ,'specialty_type','' )}
                    <%--<form:select path="registerMainSpecialty" class="input-medium ">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>--%>
                </td>

                <td height="40px" colspan="4">
                    辅专业
                </td>
                <td colspan="6">
                    <input type="hidden" name="registerAuxiliarySpecialty" value="${enterpriseWorkers.registerAuxiliarySpecialty}">
                        ${fns:getDictLabel(enterpriseWorkers.registerAuxiliarySpecialty ,'specialty_type','' )}
                    <%--<form:select path="registerAuxiliarySpecialty" class="input-medium ">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>--%>
                </td>
            </tr>


                <tr align="right">
                    <td colspan="2" style="height:240px">
                        执业单位意见
                    </td>
                    <td colspan="18" style="padding-right:50px;padding-top:120px">
单位盖章(公章)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</br></br>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input id="btnSubmit1" class="btn btn-primary" type="button" onclick="saveConfirm()" value="确认完成"/>
            </c:if>
		</div>
	</form:form>


</body>
</html>