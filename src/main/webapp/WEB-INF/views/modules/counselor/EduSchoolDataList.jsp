<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>高校数据列表</title>
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
                    // var BatchNo = $("#batchNo").val();
                    // var reg = /^[12]\d{3}(0[1-9]|1[0-2])$/;
                    // var s = BatchNo.match(reg);
                    // if (s == null) {
                    //     alert('请输入您本次接收数据的批次号，格式为yyyyMM（4位年2位月）');
                    //     $("#batchNo").val("");
                    //     return;
                    // }
                    // var Type = $("#type option:selected").val();
                    // if (Type == 0 ) {
                    //     alert('请选择想要查询的人员类型：是否通过 ');
                    //     return;
                    // }

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
            $("#searchForm").submit();
        }


        //编辑
        function editJsp(id){
            location.href='${ctx}/counselor/schoolData/edit?id='+id;
        }

        //删除
        function delJsp(id){
            //需要弹窗.
            if(confirm("确定要删除此项吗?")){
                location.href='${ctx}/counselor/schoolData/delEducation?id='+id;
            }

        }

        <%--//导出数据--%>
        <%--function exportData(){--%>
            <%--var BatchNo = $("#batchNo").val();--%>
            <%--var resultType = $("#type option:selected").val();--%>
            <%--if(BatchNo!=""){--%>
                <%--if (resultType != 0){--%>
                <%--if (window.confirm("是否确定导出当前数据？")){--%>
                    <%--$("#searchForm").attr("action","${ctx}/counselor/schoolData/edit?resultType="+resultType+"&declareType=0");--%>
                    <%--$("#searchForm").submit();--%>
                <%--}--%>
                <%--}--%>
            <%--}return false;--%>
        <%--}--%>


    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/counselor/schoolData/list">高校数据列表</a></li>
    <li><a href="${ctx}/counselor/schoolData/edit">高校数据录入</a></li>
</ul>
<form:form id="searchForm" modelAttribute="educationSchoolInfo" action="${ctx}/counselor/schoolData/list"  method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>学校代码：</label>
            <%--<input id="batchNo"  maxlength="6" class="input-small container required"/>--%>
            <form:input path="schoolCode" id="schoolCode" htmlEscape="false" maxlength="5" class="input-small "/>
        </li>
        <li><label>学校名称：</label>
            <form:input path="schoolName" id="schoolName" htmlEscape="false" maxlength="50" class="input-small "/>
        </li>
            <%--<c:set var="finalResult" value=""/>--%>
            <%--<c:forEach items="${page.list}" var="educationSchoolInfo" varStatus="status" begin="0" end="0">--%>
                <%--<c:set var="finalResult" value="${educationSchoolInfo.finalResult}"/>--%>
            <%--</c:forEach>--%>
        <%--<li><label class="control-label">是否通过：</label>--%>
            <%--<select id="type" class="input-small ">--%>
                <%--<option value="0">请选择</option>--%>
                <%--<option <c:if test="${fns:getDictValue(finalResult, 'decaler_result', '')==1 }"> selected="selected" </c:if>value="1">通过</option>--%>
                <%--<option <c:if test="${fns:getDictValue(finalResult, 'decaler_result', '')==2 }"> selected="selected" </c:if>value="2">部分通过</option>--%>
                <%--<option <c:if test="${fns:getDictValue(finalResult, 'decaler_result', '')==3 }"> selected="selected" </c:if>value="3">未通过</option>--%>

            <%--</select>--%>
        <%--</li>--%>
        <li class="btns">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="page()"/>
            <%--<input id="btnExport" class="btn btn-primary" type="submit" value="导出" onclick="exportData()"/>--%>
        </li>

    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>

    <tr align="center">
        <th style="text-align:center;vertical-align:middle;">序号</th>
        <th style="text-align:center;vertical-align:middle;">学校代码</th>
        <th style="text-align:center;vertical-align:middle;">学校名称</th>
        <th style="text-align:center;vertical-align:middle;">建校年份</th>
        <th style="text-align:center;vertical-align:middle;">办学类型</th>
        <th style="text-align:center;vertical-align:middle;">可培养层次</th>
        <th style="text-align:center;vertical-align:middle;">操作</th>
    </tr>


    </thead>
    <tbody align="center">
    <c:forEach items="${page.list}" var="educationSchoolInfo" varStatus="status">
        <tr>

            <td>
                    ${status.index+1 }
            </td>
            <td>
                    ${educationSchoolInfo.schoolCode }
            </td>
            <td>
                    ${educationSchoolInfo.schoolName }
            </td>
            <td>
                    ${educationSchoolInfo.schoolStartyear}
                        <%--ne '' && educationSchoolInfo.schoolStartyear ne null--%>
                <c:if test="${!empty educationSchoolInfo.schoolStartyear}">年</c:if>
            </td>
            <td>
                <c:set value="${fn:split(educationSchoolInfo.educationType,',' ) }" var="educationType" />  <%--得到选中的值--%>
                <c:forEach items="${fns:getDictList('edu_type')}" var="item" > <%-- 遍历下拉框的所有值--%>
                    <c:forEach items="${educationType}" var="type"  >
                        <c:if test="${item.value eq  type}">
                            ${item.label}<br/>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </td>
            <td>
                <c:set value="${fn:split(educationSchoolInfo.educationCode,',' ) }" var="educationCode" />  <%--得到选中的值--%>
                <c:forEach items="${fns:getDictList('edu_code')}" var="item" > <%-- 遍历下拉框的所有值--%>
                    <c:forEach items="${educationCode}" var="code"  >
                        <c:if test="${item.value eq  code}">
                            ${item.label}<br/>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </td>
            <td colspan="20" align="right">
                <%--<c:if test="${readOnly == '200'}">--%>
                    <input type="button" value="编辑" class="btn" onclick="editJsp('${educationSchoolInfo.id}')"/>
                    <input type="button" value="删除" class="btn" onclick="delJsp('${educationSchoolInfo.id}')"/>
                <%--</c:if>--%>
            </td>
            <%--<td>--%>
                    <%--${fns:getDictLabel(educationSchoolInfo.registerMainSpecialty, 'specialty_type', '')}--%>
            <%--</td>--%>
            <%--<td>--%>
            <%----%>
            <%--<fmt:formatDate value="${educationSchoolInfo.declareDate}" pattern="yyyy-MM-dd"/>--%>
            <%--</td>--%>

        </tr>
    </c:forEach>

    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>