<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>有效咨询师列表</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        .cssType input {
            width: 150px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#searchForm").validate({
                submitHandler: function(form){

                    var name = $("#officeIdName").val();
                    $("#officeName").val(name);
                   // loading('正在提交，请稍等...');
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
        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        function lockOver(name, certificatesNum) {
            name = encodeURI(name);
            window.location.href = "${ctx}/sys/user/zRegistrationForm?name=" + name + "&certificatesNum=" + certificatesNum;
        }

        function edit(id, userId) {
            window.location.href = "${ctx}/sys/user/infoShowAdmin?id=" + id;
        }

        function createPdf(id){
            var url = "${ctx}/enterprise/enterpriseWorkers/createWorkerPdf?id="+id;
            $.ajax({
                type: 'POST',
                url: url,
                dataType: 'text',
                contentType : 'application/text;charset=UTF-8',
                success:function(data) {
                    if(data =="Y"){
                        alert("生成证书成功");
                    }else{
                        alert("生成失败");
                    }
                },
                error:function(){
                    alert("ajax请求失败");
                }
            });
        }

        function batchCreateWorkerPdf(){
            //resetTip();
            //top.$.jBox.tip("正在生成证书，请稍等...",'loading',{opacity:0});
            $.jBox.tip('正在生成证书，请稍等...','loading');

            var count = $("#count").val();
            var officeId = $("#officeIdId").val();
            var companyName = $("#companyName").val();
            var certificatesNum = $("#certificatesNum").val();
            var registerCertificateNum = $("#registerCertificateNum").val();
            var batchNo = $("#batchNo").val();
            var aollowDate = $("#aollowDate").val();
            var name = $("#name").val();
            var zhengShuFlag = "0";

            var url = "${ctx}/enterprise/enterpriseWorkers/batchCreateWorkerPdf?count="+count+"&officeId="+officeId+"&companyName="
                +companyName+"&certificatesNum="+certificatesNum+"&zhengShuFlag="+zhengShuFlag
                +"&registerCertificateNum="+registerCertificateNum
                +"&batchNo="+batchNo+"&aollowDate="+aollowDate
                +"&name="+name;;

            var enCodeUri= encodeURI(url);
            $.ajax({
                type: 'POST',
                url: enCodeUri,
                dataType: 'text',
                contentType : 'application/text;charset=UTF-8',
                success:function(data) {
                    $.jBox.closeTip()
                    if(data =="Y"){
                        alert("生成证书成功");
                    }else{
                        alert("生成失败");
                    }
                },
                error:function(){
                    $.jBox.closeTip();
                    alert("ajax请求失败");
                }
            });
        }




        function applyCfcaExectronicChapter(id){
            var url = "${ctx}/enterprise/enterpriseWorkers/applyElectronicChapter?id="+id;
            $.ajax({
                type: 'POST',
                url: url,
                dataType: 'text',
                contentType : 'application/text;charset=UTF-8',
                success: function(data) {
                    if(data == "true"){
                        alert("下发成功");
                    }else{
                        alert("下发失败");
                    }
                },
                error:function(){
                    alert("ajax请求失败");
                }
            });
        }

        function batchApplyElectronicChapter(){
            
            $.jBox.tip('正在下发电子证书，请稍等...','loading');

            var count = $("#ecount").val();
            var officeId = $("#officeIdId").val();
            var companyName = $("#companyName").val();
            var certificatesNum = $("#certificatesNum").val();
            var registerCertificateNum = $("#registerCertificateNum").val();
            var batchNo = $("#batchNo").val();
            var aollowDate = $("#aollowDate").val();
            var name = $("#name").val();
          

            var url = "${ctx}/enterprise/enterpriseWorkers/batchApplyElectronicChapter?count="+count+"&officeId="+officeId+"&companyName="
                +companyName+"&certificatesNum="+certificatesNum
                +"&registerCertificateNum="+registerCertificateNum
                +"&batchNo="+batchNo+"&aollowDate="+aollowDate
                +"&name="+name;
            var enCodeUri= encodeURI(url);
            $.ajax({
                type: 'POST',
                url: enCodeUri,
                dataType: 'text',
                contentType : 'application/text;charset=UTF-8',
                success:function(data) {
                    $.jBox.closeTip()
                    if(data =="Y"){
                        alert("申请电子章成功");
                    }else{
                        alert("申请电子章失败");
                    }
                },
                error:function(){
                    $.jBox.closeTip();
                    alert("ajax请求失败");
                }
            });
        }


    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <%-- <li><a href="${ctx}/expert/expertDistribute/toBeAssignedApplys">待分配列表</a></li> --%>
    <li class="active"><a href="${ctx}/enterprise/enterpriseWorkers/availableWorkersList">中咨协会咨询师列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/enterpriseWorkers/availableWorkersList"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="officeName" name="officeName" type="hidden" value="${enterpriseWorkers.officeName}"/>

    <ul class="ul-form">
        <li><label>归属地：</label><sys:treeselect id="officeId" name="officeId" value="${enterpriseWorkers.officeId}"
                                               labelName="company.name" labelValue="${enterpriseWorkers.officeName}"
                                               title="公司" url="/sys/office/treeData?type=1"  cssStyle="width: 80px;"
                                               allowClear="true"/>
        </li>

        <li><label >批次号：</label>
            <form:input path="batchNo" htmlEscape="false" maxlength="20" class="input-small"/>
        </li>

        <li><label >公告日期：</label>

            <input name="aollowDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate "
                   value="<fmt:formatDate value="${enterpriseWorkers.aollowDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
        </li>


        <div class="cssType">
            <li><label>单位名称：</label>
                <form:input path="companyName" htmlEscape="false" maxlength="20" class="input-small"/>
            </li>
        </div>
        <li><label>咨询师姓名：</label>
            <form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
        </li>
        <li><label>身份证号：</label>
            <form:input path="certificatesNum" htmlEscape="false" maxlength="20" class="input-small"/>
        </li>
        <li><label style="width: 140px">执业登记证书编号：</label>
            <form:input path="registerCertificateNum" htmlEscape="false" maxlength="20" class="input-small"/>
        </li>
        <li><label style="width: 140px">证书是否生成：</label>
            <form:select path="zhengShuFlag" class="input-small">
                <form:option value="" label=" "/>
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>

            </form:select>
        </li>

        <li><label style="width: 140px">电子章是否生成：</label>
            <form:select path="electronicChapterFlag" class="input-small">
                <form:option value="" label=" "/>
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li class="btns">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
        </li>

    </ul>
    <ul class="ul-form">
        <li><label style="width: 140px;">批量生成证书的条数：</label>
            <input id="count" name="count" htmlEscape="false" maxlength="20"   placeholder="每次最多10000条" class="input-small"/>
        </li>
        <li  class="btns">
            <input class="btn btn-primary" type="button" value="批量生成证书" onclick="batchCreateWorkerPdf()">
        </li>

        <li><label style="width: 140px;">批量下发电子章：</label>
            <input id="ecount" name="ecount" htmlEscape="false" maxlength="20"   placeholder="每次最多100000条" class="input-small"/>
        </li>
        <li  class="btns">
            <input class="btn btn-primary" type="button" value="批量下发电子章" onclick="batchApplyElectronicChapter()">
        </li>
    </ul>

</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>单位名称</th>
        <th>姓名</th>
        <th>性别</th>
        <th>证件号</th>
        <th>主专业</th>
        <th>辅专业</th>
        <th>咨询工程师（投资）状态</th>
        <th>冻结状态</th>
        <th>是否注册</th>
        <th>证书是否生成</th>
        <th>电子章是否申请</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="enterpriseWorkers" varStatus="index">
        <tr>
            <td>
                <input type="hidden" name="userId" value="${enterpriseWorkers.userId}">
                <input type="hidden" name="id" value="${enterpriseWorkers.id}">
                    ${index.index+1}
            </td>

            <td>
                    ${enterpriseWorkers.companyName}
            </td>
            <td>
                    ${enterpriseWorkers.name}
                    <%-- ${fns:getDictLabel(enterpriseWorkers.title, 'worker_title', '')} --%>
            </td>
            <td>
                    ${fns:getDictLabel(enterpriseWorkers.sex, 'sex', '')}
            </td>
            <td>
                    ${enterpriseWorkers.certificatesNum}
            </td>
            <td>
                    ${fns:getDictLabel(enterpriseWorkers.registerMainSpecialty ,"specialty_type" ,"" )}
            </td>
            <td>
                    ${fns:getDictLabel(enterpriseWorkers.registerAuxiliarySpecialty  ,"specialty_type" ,"" )}
            </td>
            <td>
                    ${fns:getDictLabel(enterpriseWorkers.isValid,'isValid','')}
            </td>
            <td>
                    ${fns:getDictLabel(enterpriseWorkers.isFreeze,'is_freeze','')}
            </td>
            <td>
                    ${fns:getDictLabel(enterpriseWorkers.isRegister,'yes_no','')}
            </td>
            <td>
                <c:choose>
                    <c:when test="${enterpriseWorkers.zhengShuFlag eq '1'}">
                        是
                    </c:when>
                    <c:otherwise>
                        否
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${enterpriseWorkers.electronicChapterFlag eq '1'}">
                        是
                    </c:when>
                    <c:otherwise>
                        否
                    </c:otherwise>
                </c:choose>
            </td>

            <td>
                <input type="button" value="查看信息" onclick="lockOver('${enterpriseWorkers.name}','${enterpriseWorkers.certificatesNum}')">
                <input type="button" value="生成证书" onclick="createPdf('${enterpriseWorkers.id}')">
                <input type="button" value="下发电子章" onclick="applyCfcaExectronicChapter('${enterpriseWorkers.id}')">
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>