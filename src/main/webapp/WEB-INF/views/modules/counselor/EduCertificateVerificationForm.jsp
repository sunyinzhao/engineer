<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>| 学历证书验证</title>
	<meta name="decorator" content="default"/>

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
            var checkType = $("#checkType").val();
            if(BatchNo == "" || checkType == null){
                return;
            }else {
                    $("#searchForm").attr("action","${ctx}/counselor/schoolData/checkEducationalList");
                    loading('正在为您处理数据，请稍等...');
                    $("#searchForm").submit();
            }

        }


        //导出数据
        function exportData(){
            var BatchNo = $("#batchNo").val();
            var checkType = $("#checkType").val();
            if(BatchNo == "" || checkType == null){
                    return;
            }else {
                if (window.confirm("是否确定导出当前数据？")){
                    $("#searchForm").attr("action","${ctx}/counselor/schoolData/checkEducationalList?export=1");
                    $("#searchForm").submit();
                }
            }
        }


    </script>
</head>
<body>

<ul class="nav nav-tabs">
	<li class="active"><a href="">学历证书验证</a></li>
</ul><br/>
<input type="hidden" name="queryId" id="queryId" value="${educationSchoolInfo.id}">
<form:form id="searchForm" modelAttribute="educationSchoolInfo" action="${ctx}/counselor/schoolData/checkEducationalList" method="post" class="form-horizontal">

	<sys:message content="${message}"/>
	<%--<legend>申请单</legend>--%>
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<table>
		<tr>
			<td style="margin-left: 20px">
				<label style="align-items: center">批次号：</label>
					<%--<input id="batchNo"  maxlength="6" class="input-small container required"/>--%>
				<form:input path="batchNo" id="batchNo" htmlEscape="false" maxlength="6"
							class="input-small required" value=""/>
			</td>
			<td style="padding-left:30px; margin-left: 20px">
					<%--class="control-label"--%>
				<label style="align-items: center">验证方式：</label>

					<%--<c:set value="${fn:split(educationSchoolInfo.educationType,',' ) }" var="educationType" />  &lt;%&ndash;得到选中的值&ndash;%&gt;--%>
					<%--<%--%>
					<%--// 可以写死?--%>
					<%--// 学历编号位数验证 1--%>
					<%--// 学校代码验证 2--%>
					<%--// 办学类型验证 3--%>
					<%--// 毕业年份验证 4--%>
					<%--// 培养层次验证 5--%>
					<%--//  ..........youwenti--%>
					<%--%>--%>
				<%--<%--%>
					<%--String sss = "1,2,3,4,5";--%>
					<%--application.setAttribute("string1", sss);--%>
					<%--// String sss1="学历编号位数验证,学校代码验证,办学类型验证,毕业年份验证,培养层次验证";--%>
					<%--//  application.setAttribute("string2",sss1);--%>
				<%--%>--%>
				<%--<c:set value="${ fn:split(string1, ',') }" var="str1"/>--%>
					<%--<c:set value="${ fn:split(string2, ',') }" var="str2" />--%>
				<form:select path="checkType" id="checkType" class="input-medium required" multiple="true"
							 cssStyle="width: 150px" value="">
					<%-- <c:forEach items="${fns:getDictList('declare_category')}" var="item" >
                      fns:getDictList('edu_type')
                      --%>
					<c:forEach items="${fns:getDictList('check_zhengshu_type')}" var="item"> <%-- 遍历下拉框的所有值--%>
						<c:set value="0" var="selected"/>   <%--是否选中的默认值1为未选中--%>

						<c:forEach items="${educationSchoolInfo.checkType}" var="typevalue"> <%--判断某一个值是否选中，选中则将变量selected设置为“0”--%>
							<c:if test="${item.value eq  typevalue}">
								<c:set value="1" var="selected"/>   <%--是否选中的默认值1为未选中--%>
							</c:if>
						</c:forEach>

						<c:choose>
							<c:when test="${selected eq '1' }">
								<form:option value="${item.value}" selected="selected"
											 label="${item.label}"/>  <%--标签选中--%>
							</c:when>
							<c:otherwise>
								<form:option value="${item.value}" label="${item.label}"/>  <%--标签不选中--%>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>
			</td>
			<td style="padding-left:30px; margin-left: 20px">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="page()"/>&nbsp;&nbsp;
				<input id="btnExport" class="btn btn-primary" type="submit" value="导出" onclick="exportData()"/>
			</td>
		</tr>
	</table>
</form:form>
<br/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr align="center">
				<th class="hide"></th>
				<th style="text-align:center; vertical-align:middle;">序号</th>
				<th style="text-align:center; vertical-align:middle;">所属协会</th>
				<th style="text-align:center; vertical-align:middle;">单位名称</th>
				<th style="text-align:center; vertical-align:middle;">咨询师姓名</th>
				<th style="text-align:center; vertical-align:middle;">身份证号</th>
				<th style="text-align:center; vertical-align:middle;">咨询工程师(投资)状态</th>
				<th style="text-align:center; vertical-align:middle;">毕业院校</th>
				<th style="text-align:center; vertical-align:middle;">不合格原因</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${conformList}" var="educationSchoolInfo" varStatus="status">
				<tr>
					<td>
							${status.index+1}
					</td>
					<td>
							${educationSchoolInfo.areaName}
					</td>
					<td>
							${educationSchoolInfo.companyName}
					</td>
					<td>
							${educationSchoolInfo.enterpriseName}
					</td>
					<td>
							${educationSchoolInfo.certificatesNum}
					</td>
					<td>
							${fns:getDictLabel(educationSchoolInfo.isValid,'isValid','')}
					</td>
					<td>
							${educationSchoolInfo.school}
					</td>
					<td>
                            <c:set value="${fn:split(educationSchoolInfo.type,',' ) }" var="Type" />
                            <c:forEach items="${Type}" var="type" varStatus="status">
                                <c:if test="${type ne 'null'}" >
                                    ${fns:getDictLabel(type,'check_zhengshu_type','')}未通过<br/>
                                </c:if>
                            </c:forEach>
						<%--<fmt:formatDate value="${educationSchoolInfo.declareDate}" pattern="yyyy-MM-dd"/>--%>
					</td>
				</tr>
			</c:forEach>
		</tbody>

	</table>
<%--<div class="pagination">${page}</div>--%>
</body>
</html>