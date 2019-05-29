<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业证书管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/enterprise/enterpriseCertificate/">企业证书列表</a></li>
		<shiro:hasPermission name="enterprise:enterpriseCertificate:edit"><li><a href="${ctx}/enterprise/enterpriseCertificate/form">企业证书添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseCertificate" action="${ctx}/enterprise/enterpriseCertificate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>证书类型：</label>
				<form:select path="certificateType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('certificate_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>专业：</label>
				<form:select path="specialty" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>级别：</label>
				<form:select path="grade" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('declare_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>证书类型</th>
				<th>颁发机构</th>
				<th>专业</th>
				<th>级别</th>
				<th>批准时间</th>
				<th>有效期截至</th>
				<th>修改时间</th>
				<th>注释</th>
				<shiro:hasPermission name="enterprise:enterpriseCertificate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseCertificate">
			<tr>
				<td><a href="${ctx}/enterprise/enterpriseCertificate/form?id=${enterpriseCertificate.id}">
					${fns:getDictLabel(enterpriseCertificate.certificateType, 'certificate_type', '')}
				</a></td>
				<td>
					${enterpriseCertificate.awardOrg}
				</td>
				<td>
					${fns:getDictLabel(enterpriseCertificate.specialty, 'specialty_type', '')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseCertificate.grade, 'declare_grade', '')}
				</td>
				<td>
					<fmt:formatDate value="${enterpriseCertificate.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${enterpriseCertificate.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${enterpriseCertificate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${enterpriseCertificate.remarks}
				</td>
				<shiro:hasPermission name="enterprise:enterpriseCertificate:edit"><td>
    				<a href="${ctx}/enterprise/enterpriseCertificate/form?id=${enterpriseCertificate.id}">修改</a>
					<a href="${ctx}/enterprise/enterpriseCertificate/delete?id=${enterpriseCertificate.id}" onclick="return confirmx('确认要删除该企业证书吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>