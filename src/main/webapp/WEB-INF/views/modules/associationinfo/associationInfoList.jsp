<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>协会基本信息管理</title>
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
		<li class="active"><a href="${ctx}/associationinfo/associationInfo/">协会基本信息列表</a></li>
		<shiro:hasPermission name="associationinfo:associationInfo:edit"><li><a href="${ctx}/associationinfo/associationInfo/form">协会基本信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="associationInfo" action="${ctx}/associationinfo/associationInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>协会名称</th>
				<th>组织机构代码</th>
				<th>所属地区</th>
				<shiro:hasPermission name="associationinfo:associationInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="associationInfo">
			<tr>
				<td><a href="${ctx}/associationinfo/associationInfo/form?id=${associationInfo.id}">
					${associationInfo.associationName}
				</a></td>
				<td>
					${associationInfo.orgNum}
				</td>
				<td>
					${associationInfo.office.name}
				</td>
				<shiro:hasPermission name="associationinfo:associationInfo:edit"><td>
    				<a href="${ctx}/associationinfo/associationInfo/form?id=${associationInfo.id}">修改</a>
					<a href="${ctx}/cfca/electronicChapter/certification?id=${associationInfo.id}">电子章</a>
    				
					<a href="${ctx}/associationinfo/associationInfo/delete?id=${associationInfo.id}" onclick="return confirmx('确认要删除该协会基本信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>