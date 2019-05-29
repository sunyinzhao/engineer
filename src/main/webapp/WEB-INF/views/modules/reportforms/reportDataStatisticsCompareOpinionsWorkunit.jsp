<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>终审专家复核后意见对比表-单位</title>
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
            var resultType = $("#type option:selected").val();
            if(BatchNo!=""){
                if (resultType != 0){
                $("#searchForm").attr("action","${ctx}/report/datastatistics/compareOpinions?resultType="+resultType+"&declareType=2");
                loading('正在为您处理数据，请稍等...');
                $("#searchForm").submit();
                }
            }
            return false;
        }


        //导出数据
        function exportData(){
            var BatchNo = $("#batchNo").val();
            var resultType = $("#type option:selected").val();
            if(BatchNo!=""){
                if (resultType != 0){
                if (window.confirm("是否确定导出当前数据？")){
                    $("#searchForm").attr("action","${ctx}/report/datastatistics/exportCompareOpinions?resultType="+resultType+"&declareType=2");
                    $("#searchForm").submit();
                }
                }
            }return false;
        }


    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/report/datastatistics/compareOpinions?declareType=2">变更单位</a></li>
</ul>
<%--action="${ctx}/report/datastatistics/compareOpinions?resultType=${resultType}"--%>
<form:form id="searchForm" modelAttribute="reportDataStatistics"   method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>批次号：</label>
            <%--<input id="batchNo"  maxlength="6" class="input-small container required"/>--%>
            <form:input path="batchNo" id="batchNo" htmlEscape="false" maxlength="6" class="input-small required"/>
        </li>
            <c:set var="finalResult" value=""/>
            <c:forEach items="${page.list}" var="reportDataStatistics" varStatus="status" begin="0" end="0">
                <c:set var="finalResult" value="${reportDataStatistics.finalResult}"/>
            </c:forEach>
        <li><label class="control-label">是否通过：</label>
            <select id="type" class="input-small ">
                <option value="0">请选择</option>
                <option <c:if test="${fns:getDictValue(finalResult, 'decaler_result', '')==1 }"> selected="selected" </c:if>value="1">通过</option>
                <option <c:if test="${fns:getDictValue(finalResult, 'decaler_result', '')==2 }"> selected="selected" </c:if>value="2">部分通过</option>
                <option <c:if test="${fns:getDictValue(finalResult, 'decaler_result', '')==3 }"> selected="selected" </c:if>value="3">未通过</option>

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

    <tr align="center">
        <th style="text-align:center;vertical-align:middle;">序号</th>
        <th style="text-align:center;vertical-align:middle;">申请类型</th>
        <th style="text-align:center;vertical-align:middle;">姓名</th>
        <th style="text-align:center;vertical-align:middle;">登记主专业</th>
        <th style="text-align:center;vertical-align:middle;">登记辅专业</th>
        <th style="text-align:center;vertical-align:middle;">职称</th>
        <th style="text-align:center;vertical-align:middle;">职称专业</th>
        <th style="text-align:center;vertical-align:middle;">所学专业</th>
        <th style="text-align:center;vertical-align:middle;">培训专业</th>
        <th style="text-align:center;vertical-align:middle;">原执业单位</th>
        <th style="text-align:center;vertical-align:middle;">新执业单位</th>
        <th style="text-align:center;vertical-align:middle;">预审单位</th>
        <th style="text-align:center;vertical-align:middle;">专家1姓名</th>
        <th style="text-align:center;vertical-align:middle;">专家1结论</th>
        <th style="text-align:center;vertical-align:middle;">专家评审结论</th>
        <th style="text-align:center;vertical-align:middle;">专家评审意见</th>
        <th style="text-align:center;vertical-align:middle;">反馈意见</th>
        <th style="text-align:center;vertical-align:middle;">复议结论</th>
        <th style="text-align:center;vertical-align:middle;">复议意见</th>
    </tr>


    </thead>
    <tbody>
    <c:set var="temp_area" value=""/>
    <c:set var="count" value="1"/>

    <c:forEach items="${page.list}" var="reportDataStatistics" varStatus="status">
        <tr>

            <td>
                    ${status.index+1 }
            </td>
            <td>
                    ${reportDataStatistics.declareType}
            </td>
            <td>
                    ${reportDataStatistics.enterpriseName}
            </td>

            <td>
                            ${fns:getDictLabel(reportDataStatistics.registerMainSpecialty, 'specialty_type', '')}

            </td>
            <td>
                            ${fns:getDictLabel(reportDataStatistics.registerMainSpecialty, 'specialty_type', '')}

            </td>
            <td>
                    ${fns:getDictLabel(reportDataStatistics.title, 'worker_title', '')}
            </td>
            <td>
                    ${reportDataStatistics.titleSpecialty}
            </td>
            <td>
                    ${reportDataStatistics.suoxueSpecialty}
            </td>
            <td>
                    ${reportDataStatistics.peixunSpecialty}
            </td>

            <td>
                    ${reportDataStatistics.yuanDeclareWork}
            </td>
            <td>
                    ${reportDataStatistics.xinDeclareWork}
            </td>

            <td>
                    ${reportDataStatistics.areaName}
            </td>

            <td>
                    ${reportDataStatistics.zexpert1Name}
            </td>
            <td>
                    ${reportDataStatistics.fzResult}
            </td>
            <td>
                    ${reportDataStatistics.zdResult}
            </td>
            <td>
                    ${reportDataStatistics.finalOpinion}
            </td>
            <td>
                    ${reportDataStatistics.feedBack}
            </td>

            <td>
                    ${reportDataStatistics.finalResult}
            </td>
            <td>
                    ${reportDataStatistics.advice}
            </td>
                <%--<td>--%>
                <%----%>
                <%--<fmt:formatDate value="${reportDataStatistics.declareDate}" pattern="yyyy-MM-dd"/>--%>
                <%--</td>--%>

        </tr>
    </c:forEach>

    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>