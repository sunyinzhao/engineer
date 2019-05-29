<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>手动分配专家列表</title>
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
		<li class="active"><a href="#">手动分配专家配置</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/enterprise/auditAndReport/form" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>专业：</label>
				<form:select path="specialtyType" style="width:178px;" multiple="false" >
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value"  htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>姓名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>登记类型：</label>
                <from:select path="registerType" style="width:178px;" multiple="false">
                    <form:option value="" label="请选择"/>
                    <form:options items="${fns:getDictList('counselor_type ')}" itemLabel="label" itemValue="value"  htmlEscape="false"/>
                </from:select>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<sys:message content="${message}"/>
	<form:form id="searchForm" modelAttribute="user" action="#" method="post">
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>单选</th>
				<th>专家姓名</th>
				<th>专业</th>
			</tr> 
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="user">
				<tr>
					<td>
						<input type="radio" name = "id" value = "${user.id};${user.name}" /><!-- 专家id -->
					</td>
					<td>
						${user.name}
					</td>
					<td>
						<c:set value="${fn:split(user.specialtyType,',' ) }" var="specialtys" />
						<c:forEach items="${fns:getDictList('specialty_type')}" var="item" >
						<c:forEach items="${specialtys}" var="specialty"  >
							<c:if test="${item.value eq specialty}">
								${item.label}&nbsp;&nbsp;;
							</c:if>
						</c:forEach>
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</form:form>
</body>
</html>