<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>终审专家评审情况统计表    </title>
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

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            var batchN = $("#batchN").val();
            var reviewType = $("#type option:selected").val();
            var reg = /^[12]\d{3}(0[1-9]|1[0-2])$/;
            var e = batchN.match(reg);
            if (e == null) {
                alert('请输入您本次接收数据的批次号，格式为yyyyMM（4位年2位月）');
                $("#batchno").val("");
                return;
            }
            if (reviewType == "0") {
                alert('请选择评审阶段');
                return;
            }
            <%--window.location.href = "${ctx}/report/expertReview/reviewCount?batchNo=" + batchN + "&reviewType=" + reviewType;batchNo=" + batchN + "&--%>
            $("#searchForm").attr("action","${ctx}/report/expertReview/reviewCount?type=" + reviewType);
            $("#searchForm").submit();

            return false;
        }

        //导出数据
        function exportData(){
            var batchN = $("#batchN").val();
            var type = $("#type option:selected").val();
            var reg = /^[12]\d{3}(0[1-9]|1[0-2])$/;
            var e = batchN.match(reg);
            if (e == null) {
                alert('请输入您本次接收数据的批次号，格式为yyyyMM（4位年2位月）');
                $("#batchno").val("");
                return;
            }
            if (type == "0") {
                alert('请选择评审阶段');
                return;
            }
            if (window.confirm("是否确定导出当前数据？")){
                $("#searchForm").attr("action","${ctx}/report/expertReview/reviewCountExport?type=" + type);
                $("#searchForm").submit();
            }
            return false;
        }

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/report/expertReview/reviewCount">终审专家评审情况统计表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="finalExpertReviewCount" action="${ctx}/report/expertReview/reviewCount" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form" style="margin-bottom: auto">
        <div class="cssType">
            <li><label>批次号：</label>
                <form:input path="batchNo" id="batchN" htmlEscape="false" maxlength="6" class="input-small  required"/>
            </li>
                <%-- 专家评审 12 13  专家复核 17 19   给个简单的状态到后台 ，用两条sql实现查询 --%>
            <%-- 只是为了做回显 --%>
            <%--<c:set var="reviewType" value=""/>--%>
            <%--<c:forEach items="${page.list}" var="erc" varStatus="status" begin="0" end="0">--%>
                <%--<c:set var="reviewType" value="${erc.reviewType}"/>--%>
            <%--</c:forEach>--%>
            <li><label class="control-label">评审阶段：</label>
                <select id="type" class="input-small ">
                    <option value="0">请选择</option>
                    <option <c:if test="${ finalExpertReviewCount.reviewType == '专家评审' }"> selected="selected" </c:if>value="1">专家评审</option>
                    <option <c:if test="${ finalExpertReviewCount.reviewType == '专家复核' }"> selected="selected" </c:if>value="2">专家复核</option>

                </select>
            </li>
        </div>
        <li class="btns">
            <input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="page()"/>
            <input id="btnExport" class="btn btn-primary" type="button" value="导出" onclick="exportData()"/>
        </li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed" >
    <thead>
    <tr>
        <td style="text-align: center;" rowspan="2" >
            序号
        </td>
        <td style="text-align: center;" rowspan="2" >
            批次
        </td>
        <td style="text-align: center;" rowspan="2" >
            评审阶段
        </td>
        <td style="text-align: center;" rowspan="2" >
            专家姓名
        </td>
        <td style="text-align: center;" colspan="4" >
            初始登记
        </td>
        <td style="text-align: center;" colspan="4" >
            变更执业单位
        </td>
        <td style="text-align: center;" colspan="4" >
            变更专业
        </td>
        <td style="text-align: center;" colspan="4" >
            继续登记
        </td>
        <td style="text-align: center;" colspan="4" >
            注销登记
        </td>
    </tr>
    <tr>
        <td style="text-align: center;" >
            分配数量
        </td>
        <td style="text-align: center;" >
            符合
        </td>
        <td style="text-align: center;" >
            不符合
        </td>
        <td style="text-align: center;" >
            未完成数量
        </td>
        <td style="text-align: center;" >
            分配数量
        </td>
        <td style="text-align: center;" >
            符合
        </td>
        <td style="text-align: center;" >
            不符合
        </td>
        <td style="text-align: center;" >
            未完成数量
        </td>
        <td style="text-align: center;" >
            分配数量
        </td>
        <td style="text-align: center;" >
            符合
        </td>
        <td style="text-align: center;" >
            不符合
        </td>
        <td style="text-align: center;" >
            未完成数量
        </td>
        <td style="text-align: center;" >
            分配数量
        </td>
        <td style="text-align: center;" >
            符合
        </td>
        <td style="text-align: center;" >
            不符合
        </td>
        <td style="text-align: center;" >
            未完成数量
        </td>
        <td style="text-align: center;" >
            分配数量
        </td>
        <td style="text-align: center;" >
            符合
        </td>
        <td style="text-align: center;" >
            不符合
        </td>
        <td style="text-align: center;" >
            未完成数量
        </td>

    </tr>

    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="expertReview" varStatus="index" >
        <tr>
            <td style="text-align: center;" >${index.index+1 }</td>
            <td style="text-align: center;" >${expertReview.batchNo}</td>
            <td style="text-align: center;" >${expertReview.reviewStage}</td>
            <td style="text-align: center;" >${expertReview.expertName}</td>
            <td style="text-align: center;" >${expertReview.initialAllocatedQuantity}</td>
            <td style="text-align: center;" >${expertReview.initialAccord}</td>
            <td style="text-align: center;" >${expertReview.initialIncompatible}</td>
            <td style="text-align: center;" >${expertReview.initialUndone}</td>
            <td style="text-align: center;" >${expertReview.unitAllocatedQuantity}</td>
            <td style="text-align: center;" >${expertReview.unitAccord}</td>
            <td style="text-align: center;" >${expertReview.unitIncompatible}</td>
            <td style="text-align: center;" >${expertReview.unitUndone}</td>
            <td style="text-align: center;" >${expertReview.specialtyAllocatedQuantity}</td>
            <td style="text-align: center;" >${expertReview.specialtyAccord}</td>
            <td style="text-align: center;" >${expertReview.specialtyIncompatible}</td>
            <td style="text-align: center;" >${expertReview.specialtyUndone}</td>
            <td style="text-align: center;" >${expertReview.continueAllocatedQuantity}</td>
            <td style="text-align: center;" >${expertReview.continueAccord}</td>
            <td style="text-align: center;" >${expertReview.continueIncompatible}</td>
            <td style="text-align: center;" >${expertReview.continueUndone}</td>
            <td style="text-align: center;" >${expertReview.logoutAllocatedQuantity}</td>
            <td style="text-align: center;" >${expertReview.logoutAccord}</td>
            <td style="text-align: center;" >${expertReview.logoutIncompatible}</td>
            <td style="text-align: center;" >${expertReview.logoutUndone}</td>
        </tr>
    </c:forEach>

    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>