<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理</title>
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
		function complate(){
			$("#confirm").val("1");
			$("#searchForm").attr('action','${ctx}'+'/declare/declareRecord/saveConfirmStatus');    
			$("#searchForm").submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/enterprise/enterpriseProject/">项目列表</a></li>
		<c:if test="${tableType ne '11' }"><shiro:hasPermission name="enterprise:enterpriseProject:edit"><li><a href="${ctx}/enterprise/enterpriseProject/form">项目添加</a></li></shiro:hasPermission></c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseProject" action="${ctx}/enterprise/enterpriseProject/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		
		<input type="hidden" name="tableType" id="tableType" value="${tableType}">
		<input type="hidden" name="declareRecordId" id="declareRecordId" value="${declareRecordId}">
		<input type="hidden" name="confirm" id="confirm" >	
		
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			
			<c:if test="${tableType eq '11' }">
				<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" onclick="complate()" type="button" value="确认完成"/>
				<a href="${ctx}/declare/declareRecord/form?id=${declareRecordId}"> <input id="btn btn-primary" class="btn" type="button" value="返 回" /></a>
				</li>
			</c:if>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目名称</th>
				<th>项目服务范围</th>
				<th>专业</th>
				<th>申请的服务范围</th>
				<th>项目总投资（万元）</th>
				<th>建设规模</th>
				<th>完成时间</th>
				<th>委托单位</th>
				<th>修改时间</th>
				<th>注释</th>
				<shiro:hasPermission name="enterprise:enterpriseProject:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseProject">
			<tr>
				<td><a href="${ctx}/enterprise/enterpriseProject/form?id=${enterpriseProject.id}">
					${enterpriseProject.projectName}
				</a></td>
				<td>
					${enterpriseProject.projectServiceRang}
				</td>
				<td>
					${fns:getDictLabel(enterpriseProject.specialty, 'specialty_type', '')}
				</td>
				<td>
					${enterpriseProject.applyServiceRang}
				</td>
				<td>
					${enterpriseProject.projectInvestAmount}
				</td>
				<td>
					${enterpriseProject.constructionScale}
				</td>
				<td>
					<fmt:formatDate value="${enterpriseProject.completeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${enterpriseProject.entrustCompany}
				</td>
				<td>
					<fmt:formatDate value="${enterpriseProject.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${enterpriseProject.remarks}
				</td>
				<shiro:hasPermission name="enterprise:enterpriseProject:edit"><td>
    				<a href="${ctx}/enterprise/enterpriseProject/form?id=${enterpriseProject.id}">修改</a>
					<a href="${ctx}/enterprise/enterpriseProject/delete?id=${enterpriseProject.id}" onclick="return confirmx('确认要删除该项目吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>