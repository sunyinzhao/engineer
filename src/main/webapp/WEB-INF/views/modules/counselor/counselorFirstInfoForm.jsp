<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<%--<title>基本情况</title>--%>
	<meta name="decorator" content="default"/>
    <script src="${ctxStatic}/jquery-print/jQuery.print.js" type="text/javascript"></script>
	<script type="text/javascript">
        $(function(){
            var confirmFive = "${enterpriseWorkers.isRegister}";
            if(confirmFive!=null&&confirmFive!=""&&confirmFive==1){
                $("#reviewCompany").removeAttr("disabled")
                $("input[name='companyArea']").removeAttr("disabled")
                $("input[name='fileNo']").removeAttr("disabled")
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

        function changeDown(num){
            if(num==1){
                $("#reviewCompany").removeAttr("disabled");
                $("input[name='companyArea']").removeAttr("disabled");
                $("input[name='fileNo']").removeAttr("disabled");
            }else{
                $("#reviewCompany").attr("disabled","true");
                $("#reviewCompany").val("");
                $("input[name='companyArea']").attr("disabled","true");
                $("input[name='companyArea']").removeAttr("checked");
                $("input[name='fileNo']").attr("disabled","true");
                $("input[name='fileNo']").removeAttr("checked");
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
            var a = validate();
            if(!a){
                return;
            }

            document.getElementById("btnSubmit1").disabled=true;
            document.getElementById("btnSubmit").disabled=true;
            $("#inputForm").attr("action","${ctx}/counselor/saveConfirm?tableId=${tableId}&recordId=${recordId}")
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

        //打印
        function jqp(){

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
            var main = $("#registerMainSpecialty").val();
            var isRegister=$('input:radio[name="isRegister"]:checked').val();
            var reviewCompany = $("#reviewCompany").val();
            var backupType=$('input:radio[name="fileNo"]:checked').val();
            var companyType=$('input:radio[name="companyArea"]:checked').val();
            //var iseducate=$('input:radio[name="mobile"]:checked').val();
            var form = document.getElementById("inputForm");


            if(main==''||isRegister==null||isRegister==''){
                alert("请填写完整")
                return false;
            }
            if(isRegister==1){
                if(reviewCompany==null||backupType==null||companyType==null){
                    alert("请填写完整")
                    return false;
                }
            }
           var result = form.elements[5].value;
            if(result==''){
                alert("请填写完整")
                return false;
            }
            return true;
            //判断
            // 1.工作单位   reviewCompany 2
            //  2.备案状况  redio fileNo
            //3.工作性质  redio companyArea
            //4.继续教育   redio mobile
        }
        function isfifthonclick(rbtn)
        {
        	if (rbtn.value=='0')
        	{
        		$("#workcompany").attr("readonly",true);
        	}
        	else
        	{
        		$("#workcompany").attr("readonly",false);
        	}
        }
	</script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="">初始登记列表</a></li>
    <li class="active"><a href="">基本情况</a></li>
</ul>
    <input type="hidden" id="recordId" value="${recordId}"/>
    <input type="hidden" id="personId" value="${enterpriseWorkers.id}"/>
    <input type="hidden" id="tableId" value="${tableId}"/>
    <%--<legend>基本情况</legend>--%>
    <div style="border: 1px solid #999;">
    <div id="printDiv" style="width: 1000px;">
	<form:form id="inputForm" modelAttribute="enterpriseWorkers" action="${ctx}/counselor/saveInfo?tableId=${tableId}&recordId=${recordId}" method="post" class="form-horizontal">
        <form:input path="id" type="hidden"/>
        <form:input path="title" type="hidden"/>
		<sys:message content="${message}"/>
        <table cellspacing=0 border=1 align="center" width="90%">
            　　<tr align="center">
                    <td colspan="20" height="50px" width="100%" ><span style="font-size: 30px">基本情况</span></td>
                </tr>
            　　<tr align="center">
                    <td height="50px" width="10%" colspan="2" style="font-weight: bold">姓名</td>
                    <td width="15%" colspan="4">
                        ${enterpriseWorkers.name}
                        <%--<form:input path="name" cssStyle="width: 90%" readonly="true"></form:input>--%>
                    </td>
                    <td width="10%" colspan="2" style="font-weight: bold">性别</td>
                    <td width="10%" colspan="2">
                            ${fns:getDictLabel(enterpriseWorkers.sex,'sex','' )}
                        <%--<form:input path="sex" value="${fns:getDictLabel(enterpriseWorkers.sex,'sex','' )}" cssStyle="width: 80%" readonly="true"/>--%>
                    </td>

                    <td width="10%" colspan="2" style="font-weight: bold">民族</td>
                    <td width="15%" colspan="4">
                            ${fns:getDictLabel(enterpriseWorkers.nation,'sys_nation','' )}
                        <%--<c:choose>
                            <c:when test="${enterpriseWorkers.nation!=null&&enterpriseWorkers.nation!=''}">
                                <form:input path="nation" value="${fns:getDictLabel(enterpriseWorkers.nation,'sys_nation','' )}" readonly="true" cssStyle="width: 90%" />
                            </c:when>
                            <c:otherwise>
                                <form:input path="nation"  cssStyle="width: 90%" readonly="true"/>
                            </c:otherwise>
                        </c:choose>--%>
                    </td>
                    <td width="30%" colspan="4" rowspan="4" >
                        <img  src="${ctx}/uploadImage/id2/${enterpriseWorkers.id}"  width="150px">
                       <%-- <img src="${enterpriseWorkers.pictureUrl}${enterpriseWorkers.pictureName}"/>--%>
                    </td>
                 </tr>
            　　<tr align="center">
                    <td height="40px" width="10%" colspan="2" style="font-weight: bold">身份证类型</td>
                    <td width="20%" colspan="4">
                            ${fns:getDictLabel(enterpriseWorkers.certificatesType,'ID_type','' )}
                        <%--<c:choose>
                            <c:when test="${enterpriseWorkers.certificatesType!=null&&enterpriseWorkers.certificatesType!=''}">
                                <form:input path="certificatesType" value="${fns:getDictLabel(enterpriseWorkers.certificatesType,'ID_type','' )}" readonly="true" cssStyle="width: 90%" />
                            </c:when>
                            <c:otherwise>
                                <form:input path="certificatesType"  cssStyle="width: 90%" readonly="true"/>
                            </c:otherwise>
                        </c:choose>--%>
                    </td>
                    <td width="20%" colspan="4" style="font-weight: bold">证件号</td>
                    <td width="30%" colspan="6">
                        ${enterpriseWorkers.certificatesNum}
                        <%--<form:input path="certificatesNum" cssStyle="width: 90%" readonly="true"></form:input>--%>
                    </td>
                </tr>
            　　<tr align="center">
                    <td height="50px" width="10%" colspan="2" style="font-weight: bold">出生日期</td>
                    <td width="20%" colspan="4">
                            ${map.date}
                        <%--<form:input path="" value="${map.date}" cssStyle="width: 90%" readonly="true"/>--%>
                    </td>
                    <td width="20%" colspan="4" style="font-weight: bold">参加工作时间</td>
                    <td width="30%" colspan="6">
                        <fmt:formatDate value="${enterpriseWorkers.entryDate}" pattern="yyyy-MM-dd"/>
                    </td>
                </tr>
                <tr align="center">
                    <td height="40px" width="10%" colspan="2" style="font-weight: bold">联系电话</td>
                    <td width="20%" colspan="4">
                            ${mobile}
                        <%--<form:input path="phone" readonly="true" cssStyle="width: 90%"/>--%>
                    </td>
                    <td width="20%" colspan="4" style="font-weight: bold"> 邮箱</td>
                    <td width="30%" colspan="6">
                            ${map.email}
                        <%--<form:input path="" value="${map.email}" cssStyle="width: 95%" readonly="true"/>--%>
                    </td>
                 </tr>

                <tr align="center">
                <td height="50px" width="15%" colspan="3" style="font-weight: bold">职业资格证书取得年份</td>
                <td width="15%" colspan="3">
                    <c:if test="${flag}">
                        <form:input path="getyear" maxlength="4" readonly="true" cssStyle="width: 90%"></form:input></td>
                    </c:if>
                    <c:if test="${!flag}">
                        <form:input path="getyear" maxlength="4" cssStyle="width: 90%"></form:input></td>
                    </c:if>
                <td width="20%" colspan="4" style="font-weight: bold">职业资格证书编号</td>
                <td width="50%" colspan="10">
                    <!--区分两种情况,一种是登记失效,isValid==0 一种是其余-->
                    <c:choose>
                        <c:when test="${flag}">
                            <form:input path="professioncardNum" maxlength="30" cssStyle="width: 95%" readonly="true" />
                        </c:when>
                        <c:otherwise>
                            <form:input path="professioncardNum" maxlength="30" cssStyle="width: 95%" />
                        </c:otherwise>
                    </c:choose>
                </td>
                </tr>

            <tr align="left">
                <td colspan="20" height="16px" width="100%" >
                    <div align="left" style="font-size: 18px">学历教育情况</div>

                   <%-- <div align="right" style="font-size: 18px">
                        <c:if test="${empty look}">
                        <a onclick="openJsp('1')" style="cursor:pointer">添加</a></div>
                    </c:if>--%>
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
                                <c:when test="${result == 200}">
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
                                <c:when test="${result == 200}">
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
                    <tr>
                        <td colspan="20" align="right">
                            <c:choose>
                                <c:when test="${result == 200}">
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
                    <td width="5%" colspan="1" rowspan="5" style="font-weight: bold">申请登记情况</td>
                    <td height="40px" width="10%" colspan="2" style="font-weight: bold">执业单位</td>
                    <td width="35%" colspan="7">
                        <%--<form:input path="companyName" cssStyle="width: 95%"></form:input>--%>
                        <input name="companyName" value="${enterpriseWorkers.companyName}" type="hidden">
                                ${enterpriseWorkers.companyName}
                    </td>
                    <td width="10%" colspan="2" style="font-weight: bold">预审单位</td>
                    <td width="40%" colspan="8">
                        <%--<input name="registerCertificateNum" value="${user.recordNumber}" type="hidden">--%>
                            ${map.companyName}
                        <%--<form:input path="registerCertificateNum" cssStyle="width: 90%">

                        </form:input>--%>
                    </td>
                </tr>

                <tr align="center">
                    <td height="40px" colspan="8" style="font-weight: bold">
                        是否采用登记规程第五条
                    </td>

                    <td colspan="10">
                        <form:radiobutton path="isRegister" value="1" onclick="changeDown(1)"/>是
                        <form:radiobutton path="isRegister" value="0" onclick="changeDown(0)"/>否
                    </td>
                </tr>


                <tr align="center">
                    <td width="10%"  height="40px" colspan="2" style="font-weight: bold">工作单位</td>
                    <td width="50%" colspan="16">
                        <form:input path="reviewCompany" disabled="true"  maxlength="80" cssStyle="width: 95%"/>
                    <%--<input type="hidden" id="hidisfifth" value="${isfifth}" />
                    <c:if test="${isfifth eq '0'}">
                    	<form:input id="workcompany" path="reviewCompany"  maxlength="80" cssStyle="width:97%" readonly="true"></form:input>
                    </c:if>
                    <c:if test="${isfifth eq '1'}">
                    	<form:input id="workcompany" path="reviewCompany"  maxlength="80" cssStyle="width:97%" ></form:input>
                    </c:if>--%>
                    </td>
                </tr>

                <tr align="center">
                    <td width="10%" colspan="2" style="font-weight: bold">工作单位性质</td>
                    <td width="40%" colspan="8">
                        <form:radiobuttons path="companyArea" itemLabel="label" disabled="true" items="${fns:getDictList('declare_company_type')}" itemValue="value"   class="i-checks carries_type" htmlEscape="false"/>
                       <%-- <form:radiobuttons path="companyArea" items="${fns:getDictList('declare_company_type')}" itemLabel="label" itemValue="value"  htmlEscape="false" />--%>
                            <%-- <flect>--%>
                    </td>
                    <td width="10%"  height="40px" colspan="2" style="font-weight: bold">备案状况</td>
                    <td width="35%" colspan="7">
                        <form:radiobuttons path="fileNo" itemLabel="label" disabled="true" items="${fns:getDictList('backup_type')}" itemValue="value"   class="i-checks carries_type" htmlEscape="false"/>
                       <%-- <form:radiobuttons path="fileNo" items="${fns:getDictList('backup_type')}" itemLabel="label" itemValue="value"  htmlEscape="false" />--%>
                    </td>
                </tr>



                <tr align="center">
                    <td width="10%" colspan="2"  height="50px" style="font-weight: bold">申请主专业</td>
                    <td width="35%" colspan="7">
                        <%--<form:input path="registerMainSpecialty" cssStyle="width: 95%"></form:input>--%>
                        <form:select path="registerMainSpecialty" class="input-medium " cssStyle="width: 99%">
                            <form:option value="" label=""/>
                            <form:option value="" label=""/>
                            <form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </td>
                    <td width="10%" colspan="2" style="font-weight: bold">申请辅专业</td>
                    <td width="40%" colspan="8">
                        <%--<form:input path="registerAuxiliarySpecialty" cssStyle="width: 99%"></form:input>--%>
                        <form:select path="registerAuxiliarySpecialty" class="input-medium " cssStyle="width: 99%">
                            <form:option value="" label=""/>
                            <form:option value="" label=""/>
                            <form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </td>
                </tr>
                <tr align="center">
                    <td width="5%" colspan="1"  height="140px" style="font-weight: bold">
                    	执业单位意见
                    </td>
                    <td width="95%" colspan="18" align="right" style="font-weight: bold;margin-left:50px">
                    	单位盖章(公章)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</br></br>
                    	年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>

                </tr>
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

            <input id="return" class="btn btn-primary" type="button"  onclick="history.go(-1)" value="返回"/>
			<%--<shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
            <shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" onclick="saveBaseInfo();"/>&nbsp;</shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>--%>
            <c:if test="${empty look}">
                &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
            <input id="btnSubmit1" class="btn btn-primary" type="button" onclick="saveConfirm()" value="确认完成"/>
            </c:if>
		</div>
	</form:form>


</body>
</html>