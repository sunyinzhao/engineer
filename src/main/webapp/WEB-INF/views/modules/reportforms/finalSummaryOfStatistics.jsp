<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>终审各批次评审情况统计汇总</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        .inputType input {
            width: 150px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#searchForm").validate({
                rules: {},
                messages: {},
                submitHandler: function (form) {

                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });

        // function page(n, s) {
        //     $("#pageNo").val(n);
        //     $("#pageSize").val(s);
        //     $("#searchForm").submit();
        //     return false;
        // }

        //查询
        function findall() {
            var startBatchNo = $("#startBatchNo").val();
            var endBatchNo = $("#endBatchNo").val();
            var gsd = $("#officeIdId").val();
            var reg = /^[12]\d{3}(0[1-9]|1[0-2])$/;
            var s = startBatchNo.match(reg);
            var e = endBatchNo.match(reg);
            if (s == null) {
                alert('请输入您本次接收数据的批次号，格式为yyyyMM（4位年2位月）');
                $("#batchNo").val("");
                return;
            }
            if (e == null) {
                alert('请输入您本次接收数据的批次号，格式为yyyyMM（4位年2位月）');
                $("#batchNo").val("");
                return;
            }
            var startTimestamp = Date.UTC(startBatchNo.substring(0, 4), startBatchNo.substring(4, 6), 01);
            var endTimestamp = Date.UTC(endBatchNo.substring(0, 4), endBatchNo.substring(4, 6), 01);
            if (startTimestamp > endTimestamp) {
                alert("起始日期应在结束日期之前");
                return;
            }
            if ((endTimestamp - startTimestamp) > 180 * 24 * 60 * 60 * 1000) {
                alert("时间段最长为六个月");
                return;
            }
            loading('正在为您处理数据，请稍等...');
            window.location.href = "${ctx}/enterprise/enterpriseWorkersCount/count?id=" + gsd + "&startBatchNo=" + startBatchNo + "&endBatchNo=" + endBatchNo;
            return;
        }


        //导出数据
        function exportData(){
            var startBatchNo = $("#startBatchNo").val();
            var endBatchNo = $("#endBatchNo").val();
            var officeId = $("#officeIdId").val();
            var reg = /^[12]\d{3}(0[1-9]|1[0-2])$/;
            var s = startBatchNo.match(reg);
            var e = endBatchNo.match(reg);
            if (s == null) {
                alert('请输入您本次接收数据的批次号，格式为yyyyMM（4位年2位月）');
                $("#batchNo").val("");
                return;
            }
            if (e == null) {
                alert('请输入您本次接收数据的批次号，格式为yyyyMM（4位年2位月）');
                $("#batchNo").val("");
                return;
            }
            var startTimestamp = Date.UTC(startBatchNo.substring(0, 4), startBatchNo.substring(4, 6), 01);
            var endTimestamp = Date.UTC(endBatchNo.substring(0, 4), endBatchNo.substring(4, 6), 01);
            if (startTimestamp > endTimestamp) {
                alert("起始日期应在结束日期之前");
                return;
            }
            if ((endTimestamp - startTimestamp) > 180 * 24 * 60 * 60 * 1000) {
                alert("时间段最长为六个月");
                return;
            }
            if (window.confirm("是否确定导出当前数据？")){
                $("#searchForm").attr("action","${ctx}/enterprise/enterpriseWorkersCount/export?id=" + officeId);
                $("#searchForm").submit();
            }
            return false;
        }

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/enterprise/enterpriseWorkersCount/count">终审各批次评审情况统计汇总</a></li>
</ul>
<form:form id="searchForm" modelAttribute="enterpriseWorkersCount" action="${ctx}/enterprise/enterpriseWorkersCount/count" method="post" class="breadcrumb form-search">
    <%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>--%>
    <%--<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
    <ul class="ul-form" style="margin-bottom: auto">
        <div class="cssType">
            <li><label>归属地：</label>
                <sys:treeselect id="officeId" name="officeId" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
                                title="公司" url="/sys/office/treeData?type=1" cssClass="input-small"  allowClear="true"/>
            </li>
            <li><label>批次号：</label>
                <form:input path="startBatchNo" id="startBatchNo" htmlEscape="false" maxlength="6" class="input-small  required"/>
                <span>-</span>
                <form:input path="endBatchNo" id="endBatchNo" htmlEscape="false" maxlength="6" class="input-small  required"/>
            </li>
        </div>
        <li class="btns">
            <input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="findall()"/>
            <input id="btnExport" class="btn btn-primary" type="submit" value="导出" onclick="exportData()"/>
        </li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed" >
    <thead>
    <tr>
        <td style="text-align: center;" rowspan="2" width="43">
            序号
        </td>
        <td style="text-align: center;" rowspan="2" width="87">
            归属地
        </td>
        <td style="text-align: center;" colspan="3" width="113">
            申请人数
        </td>
        <td style="text-align: center;" colspan="4" width="198">
            初始登记
        </td>
        <td style="text-align: center;" colspan="4" width="198">
            变更专业
        </td>
        <td style="text-align: center;" colspan="3" width="142">
            变更单位
        </td>
        <td style="text-align: center;" colspan="3" width="142">
            继续登记
        </td>
        <td style="text-align: center;" colspan="3" width="142">
            注销登记
        </td>
    </tr>
    <tr>
        <td style="text-align: center;" width="38">
            申请
        </td>
        <td style="text-align: center;" width="38">
            有效
        </td>
        <td style="text-align: center;" width="38">
            失效
        </td>
        <td style="text-align: center;" width="38">
            符合
        </td>
        <td style="text-align: center;" width="38">
            申请
        </td>
        <td style="text-align: center;" width="56">
            部分<br/>符合
        </td>
        <td style="text-align: center;" width="66">
            不符合
        </td>
        <td style="text-align: center;" width="38">
            符合
        </td>
        <td style="text-align: center;" width="38">
            申请
        </td>
        <td style="text-align: center;" width="57">
            部分<br/>符合
        </td>
        <td style="text-align: center;" width="66">
            不符合
        </td>
        <td style="text-align: center;" width="38">
            符合
        </td>
        <td style="text-align: center;" width="38">
            申请
        </td>
        <td style="text-align: center;" width="66">
            不符合
        </td>
        <td style="text-align: center;" width="38">
            符合
        </td>
        <td style="text-align: center;" width="38">
            申请
        </td>
        <td style="text-align: center;" width="66">
            不符合
        </td>
        <td style="text-align: center;" width="38">
            符合
        </td>
        <td style="text-align: center;" width="38">
            申请
        </td>
        <td style="text-align: center;" width="66">
            不符合
        </td>
    </tr>

    </thead>
    <tbody>

    <c:forEach items="${list1}" var="finalSummaryOfStatistics" varStatus="status" begin="0" end="0">
        <tr>
            <td style="text-align: center;" colspan="2" width="130">
                批次
            </td>
            <td style="text-align: center;" colspan="20" width="935">
                <c:set var="str" value="0"></c:set>
                <c:forEach items="${list1}" var="item" varStatus="index" begin="0" end="0">
                <c:set var="str" value="${item.batchNo}"></c:set>
                    ${fn:substring(str, 0, 4)}年
                    ${fn:substring(str, 4, 6)}月
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td style="text-align: center;" colspan="2" width="130">总计</td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.sqrs}"></c:set>
                </c:forEach>
                ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.yxrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.sxrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.csfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.csdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="56">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.csbffh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.csbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.zyfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.bgzy}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="57">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.zybffh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.zybf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.dwfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.bgdw}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.dwbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.jxfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.jxdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.jxbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.zxfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.zxdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list1}" var="item">
                    <c:set var="count" value="${count+item.zxbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
        </tr>
        <c:forEach items="${list1}" var="finalSummaryOfStatistics" varStatus="index" >
        <tr>
                <td style="text-align: center;" width="43">${index.index+1 }</td>
                <td style="text-align: center;" width="87">${finalSummaryOfStatistics.officeName}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.sqrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.yxrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.sxrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.csfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.csdj}</td>
                <td style="text-align: center;" width="56">${finalSummaryOfStatistics.csbffh}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.csbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zyfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.bgzy}</td>
                <td style="text-align: center;" width="57">${finalSummaryOfStatistics.zybffh}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.zybf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.dwfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.bgdw}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.dwbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.jxfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.jxdj}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.jxbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zxfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zxdj}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.zxbf}</td>
            </tr>
        </c:forEach>
    </c:forEach>
    <c:forEach items="${list2}" var="finalSummaryOfStatistics" varStatus="status" begin="0" end="0">
        <tr>
            <td style="text-align: center;" colspan="2" width="130">
                批次
            </td>
            <td style="text-align: center;" colspan="20" width="935">
                <c:set var="str" value="0"></c:set>
                <c:forEach items="${list2}" var="item" varStatus="index" begin="0" end="0">
                    <c:set var="str" value="${item.batchNo}"></c:set>
                    ${fn:substring(str, 0, 4)}年
                    ${fn:substring(str, 4, 6)}月
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td style="text-align: center;" colspan="2" width="130">总计</td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.sqrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.yxrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.sxrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.csfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.csdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="56">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.csbffh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.csbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.zyfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.bgzy}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="57">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.zybffh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.zybf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.dwfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.bgdw}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.dwbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.jxfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.jxdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.jxbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.zxfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.zxdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list2}" var="item">
                    <c:set var="count" value="${count+item.zxbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
        </tr>
        <c:forEach items="${list2}" var="finalSummaryOfStatistics" varStatus="index" >
            <tr>
                <td style="text-align: center;" width="43">${index.index+1 }</td>
                <td style="text-align: center;" width="87">${finalSummaryOfStatistics.officeName}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.sqrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.yxrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.sxrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.csfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.csdj}</td>
                <td style="text-align: center;" width="56">${finalSummaryOfStatistics.csbffh}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.csbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zyfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.bgzy}</td>
                <td style="text-align: center;" width="57">${finalSummaryOfStatistics.zybffh}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.zybf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.dwfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.bgdw}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.dwbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.jxfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.jxdj}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.jxbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zxfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zxdj}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.zxbf}</td>
            </tr>
        </c:forEach>
    </c:forEach>
    <c:forEach items="${list3}" var="finalSummaryOfStatistics" varStatus="status" begin="0" end="0">
        <tr>
            <td style="text-align: center;" colspan="2" width="130">
                批次
            </td>
            <td style="text-align: center;" colspan="20" width="935">
                <c:set var="str" value="0"></c:set>
                <c:forEach items="${list3}" var="item" varStatus="index" begin="0" end="0">
                    <c:set var="str" value="${item.batchNo}"></c:set>
                    ${fn:substring(str, 0, 4)}年
                    ${fn:substring(str, 4, 6)}月
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td style="text-align: center;" colspan="2" width="130">总计</td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.sqrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.yxrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.sxrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.csfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.csdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="56">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.csbffh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.csbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.zyfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.bgzy}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="57">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.zybffh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.zybf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.dwfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.bgdw}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.dwbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.jxfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.jxdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.jxbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.zxfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.zxdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list3}" var="item">
                    <c:set var="count" value="${count+item.zxbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
        </tr>
        <c:forEach items="${list3}" var="finalSummaryOfStatistics" varStatus="index" >
            <tr>
                <td style="text-align: center;" width="43">${index.index+1 }</td>
                <td style="text-align: center;" width="87">${finalSummaryOfStatistics.officeName}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.sqrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.yxrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.sxrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.csfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.csdj}</td>
                <td style="text-align: center;" width="56">${finalSummaryOfStatistics.csbffh}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.csbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zyfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.bgzy}</td>
                <td style="text-align: center;" width="57">${finalSummaryOfStatistics.zybffh}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.zybf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.dwfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.bgdw}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.dwbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.jxfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.jxdj}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.jxbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zxfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zxdj}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.zxbf}</td>
            </tr>
        </c:forEach>
    </c:forEach>
    <c:forEach items="${list4}" var="finalSummaryOfStatistics" varStatus="status" begin="0" end="0">
        <tr>
            <td style="text-align: center;" colspan="2" width="130">
                批次
            </td>
            <td style="text-align: center;" colspan="20" width="935">
                <c:set var="str" value="0"></c:set>
                <c:forEach items="${list4}" var="item" varStatus="index" begin="0" end="0">
                    <c:set var="str" value="${item.batchNo}"></c:set>
                    ${fn:substring(str, 0, 4)}年
                    ${fn:substring(str, 4, 6)}月
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td style="text-align: center;" colspan="2" width="130">总计</td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.sqrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.yxrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.sxrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.csfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.csdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="56">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.csbffh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.csbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.zyfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.bgzy}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="57">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.zybffh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.zybf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.dwfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.bgdw}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.dwbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.jxfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.jxdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.jxbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.zxfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.zxdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list4}" var="item">
                    <c:set var="count" value="${count+item.zxbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
        </tr>
        <c:forEach items="${list4}" var="finalSummaryOfStatistics" varStatus="index" >
            <tr>
                <td style="text-align: center;" width="43">${index.index+1 }</td>
                <td style="text-align: center;" width="87">${finalSummaryOfStatistics.officeName}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.sqrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.yxrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.sxrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.csfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.csdj}</td>
                <td style="text-align: center;" width="56">${finalSummaryOfStatistics.csbffh}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.csbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zyfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.bgzy}</td>
                <td style="text-align: center;" width="57">${finalSummaryOfStatistics.zybffh}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.zybf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.dwfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.bgdw}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.dwbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.jxfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.jxdj}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.jxbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zxfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zxdj}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.zxbf}</td>
            </tr>
        </c:forEach>
    </c:forEach>
    <c:forEach items="${list5}" var="finalSummaryOfStatistics" varStatus="status" begin="0" end="0">
        <tr>
            <td style="text-align: center;" colspan="2" width="130">
                批次
            </td>
            <td style="text-align: center;" colspan="20" width="935">
                <c:set var="str" value="0"></c:set>
                <c:forEach items="${list5}" var="item" varStatus="index" begin="0" end="0">
                    <c:set var="str" value="${item.batchNo}"></c:set>
                    ${fn:substring(str, 0, 4)}年
                    ${fn:substring(str, 4, 6)}月
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td style="text-align: center;" colspan="2" width="130">总计</td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.sqrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.yxrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.sxrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.csfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.csdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="56">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.csbffh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.csbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.zyfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.bgzy}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="57">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.zybffh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.zybf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.dwfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.bgdw}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.dwbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.jxfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.jxdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.jxbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.zxfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.zxdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list5}" var="item">
                    <c:set var="count" value="${count+item.zxbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
        </tr>
        <c:forEach items="${list5}" var="finalSummaryOfStatistics" varStatus="index" >
            <tr>
                <td style="text-align: center;" width="43">${index.index+1 }</td>
                <td style="text-align: center;" width="87">${finalSummaryOfStatistics.officeName}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.sqrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.yxrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.sxrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.csfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.csdj}</td>
                <td style="text-align: center;" width="56">${finalSummaryOfStatistics.csbffh}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.csbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zyfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.bgzy}</td>
                <td style="text-align: center;" width="57">${finalSummaryOfStatistics.zybffh}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.zybf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.dwfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.bgdw}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.dwbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.jxfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.jxdj}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.jxbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zxfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zxdj}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.zxbf}</td>
            </tr>
        </c:forEach>
    </c:forEach>
    <c:forEach items="${list6}" var="finalSummaryOfStatistics" varStatus="status" begin="0" end="0">
        <tr>
            <td style="text-align: center;" colspan="2" width="130">
                批次
            </td>
            <td style="text-align: center;" colspan="20" width="935">
                <c:set var="str" value="0"></c:set>
                <c:forEach items="${list6}" var="item" varStatus="index" begin="0" end="0">
                    <c:set var="str" value="${item.batchNo}"></c:set>
                    ${fn:substring(str, 0, 4)}年
                    ${fn:substring(str, 4, 6)}月
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td style="text-align: center;" colspan="2" width="130">总计</td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.sqrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.yxrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.sxrs}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.csfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.csdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="56">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.csbffh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.csbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.zyfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.bgzy}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="57">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.zybffh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.zybf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.dwfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.bgdw}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.dwbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.jxfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.jxdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.jxbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.zxfh}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="38">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.zxdj}"></c:set>
                </c:forEach>
                    ${count}
            </td>
            <td style="text-align: center;" width="66">
                <c:set var="count" value="0"></c:set>
                <c:forEach items="${list6}" var="item">
                    <c:set var="count" value="${count+item.zxbf}"></c:set>
                </c:forEach>
                    ${count}
            </td>
        </tr>
        <c:forEach items="${list6}" var="finalSummaryOfStatistics" varStatus="index" >
            <tr>
                <td style="text-align: center;" width="43">${index.index+1 }</td>
                <td style="text-align: center;" width="87">${finalSummaryOfStatistics.officeName}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.sqrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.yxrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.sxrs}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.csfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.csdj}</td>
                <td style="text-align: center;" width="56">${finalSummaryOfStatistics.csbffh}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.csbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zyfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.bgzy}</td>
                <td style="text-align: center;" width="57">${finalSummaryOfStatistics.zybffh}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.zybf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.dwfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.bgdw}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.dwbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.jxfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.jxdj}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.jxbf}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zxfh}</td>
                <td style="text-align: center;" width="38">${finalSummaryOfStatistics.zxdj}</td>
                <td style="text-align: center;" width="66">${finalSummaryOfStatistics.zxbf}</td>
            </tr>
        </c:forEach>
    </c:forEach>
    </tbody>
</table>
<%--<div class="pagination">${page}</div>--%>
</body>
</html>