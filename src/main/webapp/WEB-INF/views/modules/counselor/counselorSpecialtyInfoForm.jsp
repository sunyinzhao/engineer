<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<%--<title>基本情况</title>--%>
	<meta name="decorator" content="default"/>
    <script src="${ctxStatic}/jquery-print/jQuery.print.js" type="text/javascript"></script>
	<script type="text/javascript">
        $(function () {
            var registerMainSpecialty = "${enterpriseWorkers.registerMainSpecialty}";
            var registerAuxiliarySpecialty = "${enterpriseWorkers.registerAuxiliarySpecialty}";

            if(registerMainSpecialty != null && registerMainSpecialty != ""){
                $("#newMainSpecialty option[value="+registerMainSpecialty+"]").remove();
                $("#newAuxiliarySpecialty option[value="+registerMainSpecialty+"]").remove();
            }
            if(registerAuxiliarySpecialty != null && registerAuxiliarySpecialty != ""){
                $("#newMainSpecialty option[value="+registerAuxiliarySpecialty+"]").remove();
                $("#newAuxiliarySpecialty option[value="+registerAuxiliarySpecialty+"]").remove();
            }
        })

        //验证新主辅专业不可以相同
        function checkSelect(num){
            var newMainSpecialty = $("#newMainSpecialty option:selected").val();
            var newAuxiliarySpecialty = $("#newAuxiliarySpecialty option:selected").val();
            if(newMainSpecialty != null && newMainSpecialty != "" && newMainSpecialty.length>0 && newAuxiliarySpecialty!=null && newAuxiliarySpecialty.length>0){
                if(newMainSpecialty == newAuxiliarySpecialty){
                    alert("主辅专业不可以相同");
                    var selectObj = $("#newAuxiliarySpecialty option");
                    for(var i = 0; i < selectObj.length; i++) {
                        if(i==0){
                            selectObj[i].selected=true;
                        }else{
                            selectObj[i].selected=false;
                        }
                    }
                    var selectObjJqu = $("#newAuxiliarySpecialty");
                    var span = selectObjJqu.parent().find("[class='select2-chosen']");
                    var sp=$(span[0]);
                    sp.text("");
                }
            }
        }

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

        function editJsp(id,obj,flag){
            if(flag==null||flag==''){//有flag 表示为查看
                if (obj =='titleCertificate'){
                    window.open ("${ctx}/counselor/window/edit?id="+id+"&object="+obj,"window","height=400,width=800,scrollbars=no,location=no");
                }
                else{
                    window.open ("${ctx}/counselor/window/edit?id="+id+"&object="+obj,"window","height=600,width=700,scrollbars=no,location=no");
                }
            }else{
            if (obj =='titleCertificate'){
                window.open ("${ctx}/counselor/window/edit?id="+id+"&object="+obj+"&readOnly="+flag,"window","height=400,width=800,scrollbars=no,location=no");
            }
            else{
                window.open ("${ctx}/counselor/window/edit?id="+id+"&object="+obj+"&readOnly="+flag,"window","height=600,width=700,scrollbars=no,location=no");
            }
            }
        }

        //确认按钮, 跳到外一层.
        function saveConfirm(){
            var personId = $("#personId").val();
            var a = validate();
            if(!a){
                return;
            }
            document.getElementById("btnSubmit1").disabled=true;
            document.getElementById("btnSubmit").disabled=true;
            $("#inputForm").attr("action","${ctx}/counselor/accountantStatus/saveInfo?tableId=${tableId}&recordId=${recordId}&flag='1'&personId="+personId)
            $("#inputForm").submit();
        }

        //保存按钮, 跳本页面
        function saveForm(){
            var personId = $("#personId").val();
            var a = validate();
            if(!a){
                return;
            }
            var main= $("#newMainSpecialty").val();
            var oldmain= $("#oldMainSpecialty").val();
            var Auxi= $("#newAuxiliarySpecialty").val();
            var oldAuxi= $("#oldAuxiliarySpecialty").val();
            if(Auxi==oldmain)
            {
            	if(confirm("若改变主辅专业顺序，该专业将被重新审核，请确认是否改变顺序?")!="1");
            	{
            		return;
            	}
            }
            if(main==oldAuxi)
            {
            	if(confirm("若改变主辅专业顺序，该专业将被重新审核，请确认是否改变顺序?")!="1");
            	{
            		return;
            	}
            }
            
            document.getElementById("btnSubmit1").disabled=true;
            document.getElementById("btnSubmit").disabled=true;
            $("#inputForm").attr("action","${ctx}/counselor/accountantStatus/saveInfo?tableId=${tableId}&recordId=${recordId}&personId="+personId)
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
        //根据id删除jsp
        function delJsp(id,obj){
            var tableId = $("#tableId").val();
            var personId = $("#personId").val();
            var recordId = $("#recordId").val();
            //弹出提示框,确认删除吗.
            if(confirm("确认删除吗?")){
                //跳转删除
                location.href='${ctx}/counselor/'+obj+'/delete?id='+id+'&personId='+personId+'&recordId='+recordId+'&tableId='+tableId;
            }else{
                return;
            }
        }

        function validate(){
            //工作单位  jobCompanyName
            //备案情况  radio   backupType
            //工作单位性质    radio  companyType
            //上一年度  radio   accountantCheck
            //现申请主 select   newMainSpecialty

           /* var backupType=$('input:radio[name="backupType"]:checked').val();
            var companyType=$('input:radio[name="companyType"]:checked').val();*/
            /*var accountantCheck=$('input:radio[name="accountantCheck"]:checked').val();*/
            var form = document.getElementById("inputForm");
//             var result = form.elements[2].value;
//             if(result==''){
//                 alert("请填写完整")
//                 return false;
//             }
           /* if(backupType==null||companyType==null){
                alert("请填写完整")
                return false;
            }*/
            return true;
        }





	</script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="">变更专业</a></li>
    <li class="active"><a href="">基本情况</a></li>
</ul>
    <input type="hidden" id="personRecordId" value="${personRecordId}"/>
    <input type="hidden" id="personId" value="${enterpriseWorkers.id}"/>
    <input type="hidden" id="tableId" value="${tableId}"/>
    <input type="hidden" id="recordId" value="${recordId}"/>
    <%--<legend>基本情况</legend>--%>
    <div style="width:750pt; border: 1px solid #999;">
    <div id="printDiv" style="width: 700pt;">
	<form:form id="inputForm" modelAttribute="accountantStatus" action="${ctx}/counselor/accountantStatus/saveInfo?tableId=${tableId}&recordId=${recordId}" method="post" class="form-horizontal">
        <form:input path="id" type="hidden"/>
       <%-- <form:input path="title" type="hidden"/>--%>
		<sys:message content="${message}"/>
        <table class="table-form" width="100%">
            　　<tr align="center">
                    <td colspan="20" height="40px" width="100%" ><span style="font-size: 30px">基本情况</span></td>
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
                    <th height="40px" width="20%" colspan="4">联系电话</th>
                    <td width="30%" colspan="6">
                        ${enterpriseWorkers.mobile}
                    </td>
                    <th width="20%" colspan="4">邮箱</th>
                    <td width="30%" colspan="6">
                        ${enterpriseWorkers.email}
                    </td>
                 </tr>
            <tr align="left">
                <td colspan="20" height="16px" width="100%" >
                    <div align="left" style="font-size: 18px">学历教育情况</div>
                </td>
            </tr>

            <c:if test="${infoMap.educationtblList!=null}">
                <c:forEach items="${infoMap.educationtblList}" var="educationtbl">
                    <tr align="center">
                        <td height="40px" width="10%" colspan="2"><span style="font-weight: bold">学历</span></td>
                        <td width="40%" colspan="8">
                                ${fns:getDictLabel(educationtbl.education,"education" ,"" )}

                        </td>
                        <td width="10%" colspan="2"><span style="font-weight: bold">学习方式</span></td>
                        <td width="40%" colspan="8">
                                ${fns:getDictLabel(educationtbl.studyType,"study_type" ,"" )}
                        </td>
                    </tr>
                    <tr align="center">
                        <td height="40px" width="10%" colspan="2"><span style="font-weight: bold">大专院校</span></td>
                        <td width="40%" colspan="8">
                                ${educationtbl.school}
                        </td>
                        <td width="10%" colspan="2"><span style="font-weight: bold">专业</span></td>
                        <td width="40%" colspan="8">
                                ${educationtbl.specialty}
                        </td>
                    </tr>
                    <tr>
                        <td colspan="20" align="right">
                            <c:choose>
                                <c:when test="${empty look}">
                                    <input type="button" value="编辑" class="btn btn-primary" onclick="editJsp('${educationtbl.id}','educationtbl')"/>
                                   <%-- <input type="button" value="删除" class="btn" onclick="delJsp('${educationtbl.id}','educationtbl')"/>--%>
                                </c:when>
                                <c:otherwise>
                                    <input type="button" value="查看" class="btn btn-primary" onclick="editJsp('${educationtbl.id}','educationtbl','1')"/>
                                </c:otherwise>
                            </c:choose>

                        </td>
                    </tr>
                </c:forEach>
            </c:if>








            <tr align="left">
                <td colspan="20" height="18px" width="100%" >
                        <%--<div><span style="font-size: 20px"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;职称情况</span></div>--%>
                    <br>
                    <div align="left" style="font-size: 18px">职称情况</div>
                    <%--<div align="right" style="font-size: 18px">
                        <c:if test="${empty look}">
                        <a onclick="openJsp('2')" style="cursor:pointer">添加</a>
                        </c:if>
                    </div>--%>
                </td>
            </tr>

            <c:if test="${infoMap.titleCertificateList!=null}">
                <c:forEach items="${infoMap.titleCertificateList}" var="titleCertificate">
            <tr align="center">
                <td height="40px" width="10%" colspan="2"><span style="font-weight: bold">职称</span></td>
                <td width="40%" colspan="8">
                    ${fns:getDictLabel(titleCertificate.titleType,"worker_title" ,"" )}
                  </td>
                <td width="10%" colspan="2"><span style="font-weight: bold">专业</span></td>
                <td width="40%" colspan="8">
                        ${titleCertificate.specialty}
                </td>
            </tr>

            <tr align="center">
                <td height="40px" width="10%" colspan="2"><span style="font-weight: bold">评审机构</span></td>
                <td width="40%" colspan="8">
                    ${titleCertificate.approveEmployer}
                </td>
                <td width="10%" colspan="2"><span style="font-weight: bold">取得时间</span></td>
                <td width="40%" colspan="8">
                    <fmt:formatDate value="${titleCertificate.approveTime}" pattern="yyyy-MM-dd"/>
                </td>
            </tr>
                    <tr>

                        <td colspan="20" align="right">
                            <c:choose>
                                <c:when test="${empty look}">
                                    <input type="button" value="编辑" class="btn btn-primary" onclick="editJsp('${titleCertificate.id}','titleCertificate')"/>
                                   <%-- <input type="button" value="删除" class="btn" onclick="delJsp('${titleCertificate.id}','title')"/>--%>
                                </c:when>
                                <c:otherwise>
                                    <input type="button" value="查看" class="btn btn-primary" onclick="editJsp('${titleCertificate.id}','titleCertificate','1')"/>
                                </c:otherwise>
                            </c:choose>

                        </td>
                    </tr>
                </c:forEach>
            </c:if>



            <tr align="left">
                <td colspan="20" height="18px" width="100%" >
                    <br>
                    <div align="left" style="font-size: 18px">培训证书</div>
                    <%--<div align="right" style="font-size: 18px">
                        <c:if test="${empty look}">
                        <a onclick="openJsp('3')" style="cursor:pointer">添加</a>
                        </c:if>
                    </div>--%>
                </td>
            </tr>

            <c:if test="${infoMap.specialtyTrainList!=null}">
                <c:forEach items="${infoMap.specialtyTrainList}" var="specialtyTrain">
            <tr align="center">
                <td height="40px" width="10%" colspan="2"><span style="font-weight: bold">证书类型</span></td>
                <td width="40%" colspan="8">
                        ${specialtyTrain.trainType}
                </td>
                <td width="10%" colspan="2"><span style="font-weight: bold">学习时长</span></td>
                <td width="40%" colspan="8">
                    ${specialtyTrain.studyTime}
                </td>
            </tr>

            <tr align="center">
                <td height="40px" width="10%" colspan="2"><span style="font-weight: bold">大专院校</span></td>
                <td width="40%" colspan="8">
                    ${specialtyTrain.trainSchool}
                </td>
                <td width="10%" colspan="2"><span style="font-weight: bold">专业</span></td>
                <td width="40%" colspan="8">
                   ${specialtyTrain.specialty}
                </td>
            </tr>
                    <!--10.11 72职称情况、培训证书、学历情况  要留查看的按钮，在删除按钮旁边，点查看可以编辑，提交后可以查看不可以编辑-->
                    <tr>
                        <td colspan="20" align="right">
                            <c:choose>
                            <c:when test="${empty look}">
                        <input type="button" value="编辑" class="btn btn-primary" onclick="editJsp('${specialtyTrain.id}','specialtyTrain')"/>
                                <%--<input type="button" value="删除" class="btn" onclick="delJsp('${specialtyTrain.id}','specialtyTrain')"/>--%>
                        </c:when>
                        <c:otherwise>
                            <input type="button" value="查看" class="btn btn-primary" onclick="editJsp('${specialtyTrain.id}','specialtyTrain','1')"/>
                        </c:otherwise>
                        </c:choose>

                        </td>
                    </tr>
                </c:forEach>
            </c:if>


                <tr align="center">
                    <td colspan="2" rowspan="8" width="10%">
                        <span style="font-weight: bold">执业情况</span>
                    </td>
                    <td  height="30px" colspan="4" width="20%">
                        <span style="font-weight: bold">执业单位</span>
                    </td>
                    <td  height="30px" colspan="6" width="30%">
                            <input type="hidden" name="companyName" value="${enterpriseWorkers.companyName}">
                        ${enterpriseWorkers.companyName}
                    </td>
                    <td   colspan="4" width="20%">
                        <span style="font-weight: bold">预审单位</span>
                    </td>
                    <td   colspan="6" width="30%">
                      <%--  <input type="hidden" name="backupNum" value="${user.recordNumber}">--%>
                        ${enterpriseWorkers.officeName}
                    </td>
                </tr>

                <tr align="center">

                </tr>



                    <tr align="center">

                    </tr>


<%--
                <tr align="center">
                    <td height="30px" colspan="4">
                        <span style="font-weight: bold">工作单位</span>
                    </td>
                    <td colspan="14">
                        <form:input path="jobCompanyName" maxlength="80" cssStyle="width: 95%"/>
                    </td>

                </tr>

            <tr align="center">
                <td height="40px" colspan="4">
                    <span style="font-weight: bold">备案情况</span>
                </td>
                <td colspan="6">
                    <form:radiobutton path="backupType" value="1"/>已备案
                    <form:radiobutton path="backupType" value="0"/>未备案
                </td>

                <td height="40px" colspan="4">
                    <span style="font-weight: bold">工作单位性质</span>
                </td>
                <td colspan="6">
                    <form:radiobutton path="companyType" value="2"/>事业
                    <form:radiobutton path="companyType" value="1"/>企业
                </td>
            </tr>--%>

            <tr align="center">
                <td height="40px" colspan="4">
                    <span style="font-weight: bold">登记证书编号</span>
                </td>
                <td colspan="6">
                    ${enterpriseWorkers.registerCertificateNum}
                </td>

                <%--<td height="40px" colspan="4">
                    <span style="font-weight: bold">上一年度执业检查结论</span>
                </td>
                <td colspan="6">
                    <form:radiobutton path="accountantCheck" value="1" disabled="true"/>合格
                    <form:radiobutton path="accountantCheck" value="0" disabled="true"/>不合格
                </td>--%>
            </tr>

            <tr align="center">
                <td height="40px" colspan="4">
                    <span style="font-weight: bold">原登记主专业</span>
                </td>
                <td colspan="6">
                    <input type="hidden" name="oldMainSpecialty" value="${enterpriseWorkers.registerMainSpecialty}">
                        ${fns:getDictLabel(enterpriseWorkers.registerMainSpecialty,"specialty_type" ,"" )}
                </td>

                <td height="40px" colspan="4">
                    <span style="font-weight: bold">现申请主专业</span>
                </td>
                <td colspan="6">
                    <form:select path="newMainSpecialty" class="input-medium " onchange="checkSelect(1)">
                        <form:option value="" label=""/>
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </td>
            </tr>

            <tr align="center">
                <td height="40px" colspan="4">
                    <span style="font-weight: bold">原登记辅专业</span>
                </td>
                <td colspan="6">
                    <input type="hidden" name="oldAuxiliarySpecialty" value="${enterpriseWorkers.registerAuxiliarySpecialty}">
                    ${fns:getDictLabel(enterpriseWorkers.registerAuxiliarySpecialty,"specialty_type" ,"" )}
                </td>

                <td height="40px" colspan="4">
                    <span style="font-weight: bold">现申请辅专业</span>
                </td>
                <td colspan="6">
                    <form:select path="newAuxiliarySpecialty" class="input-medium " onchange="checkSelect(2)">
                        <form:option value="" label=""/>
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </td>
            </tr>

                <tr align="center">
                    <td colspan="2" rowspan="6">
                        <span style="font-weight: bold">执业单位意见</span>
                    </td>
                    <th height="40px" colspan="18">

                    </th>
                </tr>
                <tr align="center">
                    <th height="40px" colspan="20"></th>
                </tr>
            <tr align="center">
                <th height="40px" colspan="20"></th>
            </tr>
            <tr align="center">
                <th height="40px" colspan="20"></th>
            </tr>
            <tr align="center">
                <th height="40px" colspan="20" align="right">
                    单位盖章(公章)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </th>
            </tr>
            <tr align="center">
                <th height="40px" colspan="20" align="right">
                    年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </th>
            </tr>

        </table>
    </div>
    </div>

        <br/>

	</form:form>
<div class="form-actions">

    <input id="jqprint" class="btn" type="button" value="打 印" onclick="jqp()"/>
    <c:if test="${empty look}">
    <input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveForm()" value="保存"/>
    </c:if>

    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    <c:if test="${empty look}">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input id="btnSubmit1" class="btn btn-primary" type="button" onclick="saveConfirm()" value="确认完成"/>
    </c:if>
</div>


</body>
</html>