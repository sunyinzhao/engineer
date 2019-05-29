<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>执业资格证书管理</title>
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
		<li class="active"><a href="${ctx}/qualificationcertificate/qualificationCertificate/">执业资格证书列表</a></li>
		<shiro:hasPermission name="qualificationcertificate:qualificationCertificate:edit"><li><a href="${ctx}/qualificationcertificate/qualificationCertificate/form">执业资格证书添加</a></li></shiro:hasPermission>
	</ul>
	<%--<form:form id="searchForm" modelAttribute="qualificationCertificate" action="${ctx}/qualificationcertificate/qualificationCertificate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		 <ul class="ul-form">
			<li><label>name：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul> 
	</form:form>--%>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				
				<th>登记证书编号</th>
				<th>登记主专业</th>
				<th>登记辅助专业</th>
				<th>批准日期</th>
				<th>截止日期</th>
				<shiro:hasPermission name="qualificationcertificate:qualificationCertificate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="qualificationCertificate">
			<tr>
				
				<td>
					${qualificationCertificate.registerCertificateNum}
				</td>
				<td>
					${fns:getDictLabel(qualificationCertificate.registerMainSpecialty, 'specialty_type', '')}
				</td>
				<td>
					${fns:getDictLabel(qualificationCertificate.registerAuxiliarySpecialty, 'specialty_type', '')}
				</td>
				<td>
				<fmt:formatDate value="${qualificationCertificate.startDate}" pattern="yyyy-MM-dd"/>
					
				</td>
				<td>
				<fmt:formatDate value="${qualificationCertificate.endDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="qualificationcertificate:qualificationCertificate:edit"><td>
    				<a href="${ctx}/qualificationcertificate/qualificationCertificate/form?id=${qualificationCertificate.id}">修改</a>
					<a href="${ctx}/qualificationcertificate/qualificationCertificate/delete?id=${qualificationCertificate.id}" onclick="return confirmx('确认要删除该执业资格证书吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>