<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">专家配置</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/enterprise/auditAndReport/form" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input type="hidden" name= "personRecordId" value ="${personRecordId}">
		<input type="hidden" name= "url" value ="${url}">
		<ul class="ul-form">
			<li><label>专业：</label>
				<form:select path="specialtyType" style="width:178px;" multiple="false" >
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value"  htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>姓名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<sys:message content="${message}"/>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/enterprise/auditAndReport/update" method="post">
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>专家单位</th>
				<th>专家姓名</th>
				<th>专业</th>
				<th>操作</th>
			</tr> 
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="user">
				<tr>
					<td>
						<input type="hidden" name = "id" value = "${user.id}" /><!-- 专家id -->
						<input type="hidden" name= "personRecordId" value ="${personRecordId}">
						<input type="hidden" name= "url" value ="${url}">
						${user.office.name}
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
					<td>
						<a href="${ctx}/enterprise/auditAndReport/update?id=${user.id}&personRecordId=${personRecordId}&url=${url}" 
							onclick="return confirmx('确认要手动分配吗？', this.href)">选择该专家</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form:form>
</body>
</html>