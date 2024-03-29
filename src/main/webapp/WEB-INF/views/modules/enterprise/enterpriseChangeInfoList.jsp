<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>非咨变更管理</title>
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
		<li class="active"><a href="${ctx}/enterprise/enterpriseChangeInfo/">非咨变更列表</a></li>
		<shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><li><a href="${ctx}/enterprise/enterpriseChangeInfo/form">非咨变更添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseChangeInfo" action="${ctx}/enterprise/enterpriseChangeInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>企业名称：</label>
				<form:input path="nameNew" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>法人：</label>
				<form:input path="legalPersonNew" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>注册地址：</label>
				<form:input path="registerAddressNew" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>备案号：</label>
				<form:input path="applicationCodeNew" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>企业名称</th>
				<th>法人</th>
				<th>注册地址</th>
				<th>备案号</th>
				<shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseChangeInfo">
			<tr>
				<td><a href="${ctx}/enterprise/enterpriseChangeInfo/form?id=${enterpriseChangeInfo.id}">
					${enterpriseChangeInfo.nameNew}
				</a></td>
				<td>
					${enterpriseChangeInfo.legalPersonNew}
				</td>
				<td>
					${enterpriseChangeInfo.registerAddressNew}
				</td>
				<td>
					${enterpriseChangeInfo.applicationCodeNew}
				</td>
				<shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><td>
    				<a href="${ctx}/enterprise/enterpriseChangeInfo/form?id=${enterpriseChangeInfo.id}">修改</a>
					<a href="${ctx}/enterprise/enterpriseChangeInfo/delete?id=${enterpriseChangeInfo.id}" onclick="return confirmx('确认要删除该非咨变更吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>