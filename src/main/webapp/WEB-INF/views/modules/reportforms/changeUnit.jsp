<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>变更执业单位复核统计</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        .inputType input{
            width:150px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function() {

            $("#searchForm").validate({
                rules:{
                },
                messages:{
                },
                submitHandler: function(form){
                    var BatchNo = $("#batchNo").val();
                    var reg = /^[12]\d{3}(0[1-9]|1[0-2])$/;
                    var s = BatchNo.match(reg);
                    if (s == null) {
                        alert('请输入您本次接收数据的批次号，格式为yyyyMM（4位年2位月）');
                        $("#batchNo").val("");
                        return;
                    }
                    var Type = $("#type option:selected").val();
                    if (Type == 0 ) {
                        alert('请选择想要查询的人员类型：是否通过 ');
                        return;
                    }
                    // alert(BatchNo);
                    <%--form.action = "${ctx}/report/counselor/findReportCounselorFirstInfo?Type=" + Type ;--%>
                    <%--window.location.href = "${ctx}/report/counselor/findReportCounselorFirstInfo?Type=" + Type + "&batchNo=" + BatchNo;--%>
                    // loading('正在为您处理数据，请稍等...');
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

        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            var BatchNo = $("#batchNo").val();
            var Type = $("#type option:selected").val();
            if(BatchNo!=""){
                if (Type != 0){
                $("#searchForm").attr("action","${ctx}/check/result/changeUnit?Type="+Type);
                loading('正在为您处理数据，请稍等...');
                $("#searchForm").submit();
            }}
            return false;
        }


        //导出数据
        function exportData(){
            var BatchNo = $("#batchNo").val();
            var Type = $("#type option:selected").val();
            if(BatchNo!=""){
                if (Type != 0){
                if (window.confirm("是否确定导出当前数据？")){
                    $("#searchForm").attr("action","${ctx}/check/result/exportChangeUnit?Type="+Type);
                    $("#searchForm").submit();
                }}
            }return false;
        }


    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/check/result/changeUnit">变更执业单位复核统计</a></li>
</ul>
<form:form id="searchForm" modelAttribute="changeUnit"  method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>批次号：</label>
            <%--<input id="batchNo"  maxlength="6" class="input-small container required"/>--%>
            <form:input path="batchNo" id="batchNo" htmlEscape="false" maxlength="6" class="input-small required"/>
        </li>
            <c:set var="fResult" value=""/>
            <c:forEach items="${page.list}" var="changeUnit" varStatus="status" begin="0" end="0">
                <c:set var="fResult" value="${changeUnit.fResult}"/>
            </c:forEach>
        <li><label class="control-label">是否通过：</label>
            <select id="type" class="input-small ">
                <option value="0">请选择</option>
                <option <c:if test="${fns:getDictValue(fResult, 'decaler_result', '')==1 }"> selected="selected" </c:if>value="1">通过</option>
                <option <c:if test="${fns:getDictValue(fResult, 'decaler_result', '')==2 }"> selected="selected" </c:if>value="2">部分通过</option>
                <option <c:if test="${fns:getDictValue(fResult, 'decaler_result', '')==3 }"> selected="selected" </c:if>value="3">未通过</option>

            </select>
        </li>
        <li class="btns">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="page()"/>
            <input id="btnExport" class="btn btn-primary" type="submit" value="导出" onclick="exportData()"/>
        </li>

    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th style="text-align: center;" >序号</th>
        <th style="text-align: center;" >姓名</th>
        <th style="text-align: center;" >性别</th>
        <th style="text-align: center;" >执业登记证书编号</th>
        <th style="text-align: center;" >所属协会</th>
        <th style="text-align: center;" >登记类型</th>
        <th style="text-align: center;" >变更前单位名称</th>
        <th style="text-align: center;" >变更后单位名称</th>
        <th style="text-align: center;" >上报日期</th>
        <th style="text-align: center;" >终审专家</th>
        <th style="text-align: center;" >终审结论</th>
        <th style="text-align: center;" >终审意见</th>
        <th style="text-align: center;" >反馈意见</th>
        <th style="text-align: center;" >复议专家</th>
        <th style="text-align: center;" >复议结论</th>
        <th style="text-align: center;" >复议意见</th>
        
    </tr>
    </thead>
    <tbody>
    <c:set var="temp_area" value=""/>
    <c:set var="count" value="1"/>

    <c:forEach items="${page.list}" var="changeUnit" varStatus="status">
        <tr>
        <td>
        ${status.count }
        </td>
            <td>
                    ${changeUnit.enterprisename}
            </td>
            <!--             <td>
                <c:choose>
                    <c:when test="${temp_area ne reportCounselorCount.areaName}">
                        <c:set var="count" value="1"/>
                        <c:set var="temp_area" value="${reportCounselorCount.areaName}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="count" value="${count+1}"/>
                    </c:otherwise>
                </c:choose>
                ${count}
                <%--  如果areaName相同就累加计数，跟上一个不同则重新计数  --%>
            </td> -->


             <td>
                    ${changeUnit.workerSex}
            </td>
                         <td>
                    ${changeUnit.registerCertificateNum}
            </td>
                         <td>
                    ${changeUnit.city}
            </td>
                         <td>
                    ${changeUnit.type}
            </td>
                         <td>
                    ${changeUnit.oldName}
            </td>
                         <td>
                    ${changeUnit.newName}
            </td>
                         <td>
                    ${changeUnit.date}
            </td>
                         <td>
                    ${changeUnit.expertName}
            </td>
                         <td>
                    ${changeUnit.result}
            </td>
                         <td>
                    ${changeUnit.lastView}
            </td>
                         <td>
                    ${changeUnit.expertView}
            </td>
                         <td>
                    ${changeUnit.MMMexpertName}
            </td>
                        
                         <td>
                    ${changeUnit.fResult}
            </td>
                        <td>
                    ${changeUnit.reconsiderAdvice}
            </td>          
        </tr>
    </c:forEach>

    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>