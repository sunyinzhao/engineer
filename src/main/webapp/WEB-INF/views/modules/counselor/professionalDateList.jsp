<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专业匹配列表</title>
	<meta name="decorator" content="default"/>
	<script src="${pageContext.request.contextPath}/static/jquery-easyui-1.6.10/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript">
        $(document).ready(function() {
            $("#searchForm").validate({
                rules: {
                    name:{required:true}
                },
                messages: {
                    name:{
                        required:"必填"
                    }
                },
                submitHandler: function(form){
                    loading('正在查询，请稍等...');
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
        	return false;
        }

        function exportExcel() {
		    var batch = $("#name").val();
		    if(batch==null||batch==""||typeof batch=="undefined"){
		        alert("批次号必填");
		        return;
			}
            location.href="${ctx}/counselor/specialtyConfig/exportProfessionalDate?batch="+batch;

        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/counselor/specialtyConfig/compare">专业匹配列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="professionalDate" action="${ctx}/counselor/specialtyConfig/compare" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<div class="inputType">
			<li><label>批次号：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
            <li>
                <label>专业：</label>
                <form:select path="newValue" multiple="false" class="input-xlarge ">
                    <from:option value="" label="请选择"></from:option>
					<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" onclick="exportExcel()" value="导出"/></li>
			</div>
        </ul>
	</form:form>
	<sys:message content="${message}"/>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>所属协会</th>
				<th>单位名称</th>
				<th>姓名</th>
				<th>身份证号</th>
				<th>咨询工程师(投资)状态</th>
				<th>所报专业</th>
				<th>所学专业</th>
				<th>职称专业</th>
				<th>培训专业</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="professionalDate" varStatus="index">
			<tr>
				<td> ${index.index+1}</td>
				<td>
						${professionalDate.reviewCompany}
						<%--${counselor.declareType}--%>
				</td>
				<td>
						${professionalDate.companyName}
				</td>
				<td>
						${professionalDate.name}
				</td>
				<td>
						${professionalDate.certificatesNum}
				</td>
				<td>
						${fns:getDictLabel(professionalDate.isValid,'isValid','')}
				</td>
				<td>
					<c:if test="${professionalDate.changeType == 2||professionalDate.changeType == 4}">
						主:${fns:getDictLabel(professionalDate.newValue,'specialty_type','')}
					</c:if>
					<c:if test="${professionalDate.changeType == 3||professionalDate.changeType == 5}">
						辅:${fns:getDictLabel(professionalDate.newValue,'specialty_type','')}
					</c:if>
				</td>
				<td>
						${professionalDate.result1}
				</td>
				<td>
						${professionalDate.result2}
				</td>
				<td>
						${professionalDate.result3}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>