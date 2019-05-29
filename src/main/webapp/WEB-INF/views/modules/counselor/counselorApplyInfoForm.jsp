<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<%--<title>基本情况</title>--%>
	<meta name="decorator" content="default"/>
    <script src="${ctxStatic}/jquery-print/jQuery.print.js" type="text/javascript"></script>
	<script type="text/javascript">
        $(function(){
            var confirmFive = "${accountantStatus.confirmFive}";
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

        //确认按钮, 跳到外一层.
        function saveConfirm(){
            var a = validate();
            if(!a){
                return;
            }
            document.getElementById("btnSubmit1").disabled=true;
            document.getElementById("btnSubmit").disabled=true;
            var userId = $("#userId").val();
            var personId =$("#personId").val();
            $("#inputForm").attr("action","${ctx}/counselor/accountantStatus/saveInfo?tableId=${tableId}&recordId=${recordId}&flag=1&userId="+userId+"&personId="+personId)
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
            var userId = $("#userId").val();
            var personId =$("#personId").val();
            $("#inputForm").attr("action","${ctx}/counselor/accountantStatus/saveInfo?tableId=${tableId}&recordId=${recordId}&userId="+userId+"&personId="+personId)
            $("#inputForm").submit();
        }

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

        //验证
        function validate(){
            //1.验证pid 与 user.id 一致
            var pid = $("#companyname1").val();
            var userId = $("#companyName").val();
            if(pid==userId){
                alert("变更单位不可与原单位一致")
                return false;
            }

            //一共要填写
            //调入现执业单位时间 1
            //变更理由         radio   reason
            //上一年度执业     radio   accountantCheck
            //现申请执业     13
            //工作单位      15
            //工作单位性质    radio  companyType
            //备案情况      radio   backupType
            var values = [13,15]

            //2.所有的表单的input 需要填写
            var form = document.getElementById("inputForm");
            //var reason = $("#reasonDiv").val();

            var reason=$('input:radio[name="reason"]:checked').val();
            var confirmFive=$('input:radio[name="confirmFive"]:checked').val();
            if(reason!=null&&confirmFive!=null&&userId!=null&&userId!=''){
            }else{
                //有空的值 return
                alert("请填写完整")
                return false;
            }
            if(confirmFive==1){
                var companyType=$('input:radio[name="companyType"]:checked').val();
                var backupType=$('input:radio[name="backupType"]:checked').val();
                if(companyType!=null&&backupType!=null){}else{
                    alert("请填写完整")
                    return false;
                }
            }
            for(var i = 0; i<values.length;i++){
                var result = form.elements[values[i]].value;
                if(result==''){
                    alert("请填写完整")
                    return false;
                }
            }
            return true;
            //2.验证全部填写
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

        //弹窗. 弹窗用于搜索
        function openCompany(){
            top.$.jBox.open("iframe:${ctx}/counselor/apply/companyWindow","单位名称", 400, 500, {
                ajaxData:{selectIds: $("#${id}Id").val()},
                buttons:{"确定":"ok","关闭":true}
                , submit:function(v, h, f){
                    if (v=="ok"){
                        var cusId = ""
                        var table = h.find("iframe")[0];
                        var radio = ($(table)).contents().find("input[name='radioName']");
                        for(var i=0;i<radio.length;i++){
                            if(radio[i].checked){
                                var value = radio[i].value;
                                var split = value.split(",")
                                var companyName = split[0];
                                var backupNum = split[1];
                                var userId= split[2];
                                var officeName = split[3]
                                $("#companyName").val(companyName)
                                $("#backupNum").val(backupNum)
                                $("#userId").val(userId);
                                $("#officeName").val(officeName);
                                //将返回的companyNameval到当前页面
                                //$("#companyName").val(checkvalue);
                            }
                        }
                    }
                }, loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }

	</script>
    <style>
        .table-span{font-weight: bold}
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="">变更执业单位</a></li>
    <li class="active"><a href="">基本情况</a></li>
</ul>
    <input type="hidden" id="personRecordId" value="${personRecordId}"/>
    <input type="hidden" id="tableId" value="${tableId}"/>
    <input type="hidden" id="userId" value="${enterpriseWorkers.pid}">
    <input type="hidden" id="personId" value="${personId}">
    <input type="hidden" id="pid" value="${enterpriseWorkers.pid}">
  

    <%--<legend>基本情况</legend>--%>
    <div style="width:750pt; border: 1px solid #999;">
    <div id="printDiv" style="width: 700pt;">
	<form:form id="inputForm" modelAttribute="accountantStatus" action="${ctx}/counselor/accountantStatus/saveInfo?tableId=${tableId}&recordId=${recordId}" method="post" class="form-horizontal">
        <form:input path="id" type="hidden"/>
       <%-- <form:input path="title" type="hidden"/>--%>
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
                    <td colspan="2" rowspan="12">
                        <span style="font-weight: bold">执业情况</span>
                    </td>
                    <td  height="30px" colspan="4">
                        <span style="font-weight: bold">调入现执业单位时间</span>
                    </td>
                    <td  height="30px" colspan="6">
                        <input  type="text"  maxlength="20"
                               value="${startDate}"/>
                        <%--<input name="startDate" type="text" maxlength="20" class="input-medium Wdate "
                               value="<fmt:formatDate value="${accountantStatus.startDate}" pattern="yyyy-MM-dd"/>" onclick=""/>--%>
                    </td>
                    <td   colspan="4">
                        <span style="font-weight: bold">登记证书编号</span>
                    </td>
                    <td   colspan="6">
                        <input type="hidden" value="${enterpriseWorkers.registerCertificateNum}" name="registerCertificateNum">
                        ${enterpriseWorkers.registerCertificateNum}
                    </td>
                </tr>

                <tr align="center">
                    <td  height="30px" colspan="4">
                        <span style="font-weight: bold">主专业</span>
                    </td>
                    <td  height="30px" colspan="6">
                            <input value="${enterpriseWorkers.registerMainSpecialty}" name="oldMainSpecialty" type="hidden">
                            ${fns:getDictLabel(enterpriseWorkers.registerMainSpecialty,"specialty_type" ,"" )}
                       <%-- ${enterpriseWorkers.registerMainSpecialty}--%>
                    </td>
                    <td   colspan="4">
                        <span style="font-weight: bold">辅专业</span>
                    </td>
                    <td   colspan="6">
                        <input value="${enterpriseWorkers.registerAuxiliarySpecialty}" name="oldAuxiliarySpecialty" type="hidden">
                        ${fns:getDictLabel(enterpriseWorkers.registerAuxiliarySpecialty,"specialty_type" ,"" )}
                            <%--${enterpriseWorkers.registerAuxiliarySpecialty}--%>
                    </td>
                </tr>

            <div id="reasonDiv">
                    <tr >
                        <td align="center" colspan="4" rowspan="5">
                            <span style="font-weight: bold">变更理由</span>
                        </td>
                        <td height="30px" align="left" colspan="14">
                            <form:radiobutton path="reason" value="1"/>工作调动
                        </td>
                    </tr>

                   <%-- <tr align="left">
                        <td colspan="14" height="30px">
                            <form:radiobutton path="reason" value="2"/>单位注销,工作调动
                        </td>
                    </tr>--%>

                    <tr align="left">
                        <td colspan="14" height="30px">
                            <form:radiobutton path="reason" value="2"/>简单更名（具有名称变更核准通知书或上级更名批复）
                        </td>
                    </tr>


                    <tr align="left">
                        <td colspan="14" height="30px">
                            <form:radiobutton path="reason" value="3"/>单位名称变更（合并、分立等）
                        </td>
                    </tr>


                    <tr align="left">
                        <td colspan="14" height="40px">
                        <form:radiobutton path="reason" value="0" />其他
                            <form:input path="remarks" maxlength="200" cssStyle="width: 85%"/>
                        </td>
                    </tr>
            </div>

                    <tr align="center">

                    </tr>

                <tr align="center">
                        <td height="40px" colspan="4">
                            <span style="font-weight: bold">原执业单位</span>
                        </td>
                    <td height="40px" colspan="6">
                    <input type="hidden" id="companyname1" value="${enterpriseWorkers.companyName}">
                        ${enterpriseWorkers.companyName}
                    </td>
                    <td height="40px" colspan="4">
                        <span style="font-weight: bold">现申请执业单位</span>
                    </td>
                    <td colspan="6">
                        <input type="text" id="companyName" style="width:70%" name="companyName" value="${accountantStatus.companyName}" readonly="readonly">
                        <input type="button" value="选择" style="width:45px"class="btn" onclick="openCompany()">
                    </td>
                    <%--<td colspan="2">
                        <form:radiobutton path="accountantCheck" value="1" disabled="true"/>合格
                        <form:radiobutton path="accountantCheck" value="0" disabled="true"/>不合格
                    </td>--%>
                </tr>

                <tr align="center">
                    <td height="40px" colspan="4">
                        <span style="font-weight: bold">预审单位</span>
                    </td>
                    <td colspan="6">
                        <%--<input id="backupNum" type="text" name="backupNum" value="${accountantStatus.backupNum}" readonly="readonly">--%>
                            <input type="text" id="officeName" style="width:70%" name="officeName" value="${enterpriseWorkers.officeName}" readonly="readonly">
                    </td>
                    <td height="40px" colspan="4">
                        <span style="font-weight: bold">是否采用登记规程第五条</span>
                    </td>
                    <td colspan="6">
                        <form:radiobutton path="confirmFive" value="1" onclick="changeDown(1)"/>是
                        <form:radiobutton path="confirmFive" value="0" onclick="changeDown(0)"/>否
                    </td>
                </tr>

                <tr align="center">
                    <td height="30px" colspan="4">
                        <span style="font-weight: bold">工作单位</span>
                    </td>
                    <td colspan="14">
                        <form:input path="jobCompanyName" disabled="true"  maxlength="80" cssStyle="width: 95%"/>

                    </td>

                </tr>

            <tr align="center">
                <td height="40px" colspan="4">
                    <span style="font-weight: bold">工作单位性质</span>
                </td>
                <td colspan="6">
                    <form:radiobuttons path="companyType" itemLabel="label" disabled="true" items="${fns:getDictList('declare_company_type')}" itemValue="value"   class="i-checks carries_type" htmlEscape="false"/>
                </td>
                <td height="40px" colspan="4">
                    <span style="font-weight: bold">备案情况</span>
                </td>
                <td colspan="6">
                    <form:radiobuttons path="backupType" itemLabel="label" disabled="true" items="${fns:getDictList('backup_type')}" itemValue="value"   class="i-checks carries_type" htmlEscape="false"/>
                </td>
            </tr>

                <tr align="right">
                    <td colspan="2" height="240px" >
                        原执业单位意见
                    </td>
                    <td colspan="18" style="padding-right:40px;padding-top:120px">
						单位盖章(公章)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</br></br>                     年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
                    </td>
                </tr>
            <tr align="right">
                <td colspan="2"  height="240px" >现执业单位意见</td>
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
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="btnSubmit1" class="btn btn-primary" type="button" onclick="saveConfirm()" value="确认完成"/>
            </c:if>
		</div>
	</form:form>


</body>
</html>