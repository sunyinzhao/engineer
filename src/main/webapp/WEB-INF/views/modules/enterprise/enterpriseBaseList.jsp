<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>基本数据管理</title>
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
		<li class="active"><a href="${ctx}/enterprise/enterpriseBase/">基本数据列表</a></li>
		<shiro:hasPermission name="enterprise:enterpriseBase:edit"><li><a href="${ctx}/enterprise/enterpriseBase/form">基本数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseBase" action="${ctx}/enterprise/enterpriseBase/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属企业：</label>
				<sys:treeselect id="user" name="user.id" value="${enterpriseBase.user.id}" labelName="user.name" labelValue="${enterpriseBase.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>修改</th>
				<th>备注</th>
				<shiro:hasPermission name="enterprise:enterpriseBase:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseBase">
			<tr>
				<td><a href="${ctx}/enterprise/enterpriseBase/form?id=${enterpriseBase.id}">
					<fmt:formatDate value="${enterpriseBase.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${enterpriseBase.remarks}
				</td>
				<shiro:hasPermission name="enterprise:enterpriseBase:edit"><td>
    				<a href="${ctx}/enterprise/enterpriseBase/form?id=${enterpriseBase.id}">修改</a>
					<a href="${ctx}/enterprise/enterpriseBase/delete?id=${enterpriseBase.id}" onclick="return confirmx('确认要删除该基本数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>