<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分支机构管理</title>
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
		<li class="active"><a href="${ctx}/enterprise/enterpriseBranchOrg/">分支机构列表</a></li>
		<c:if test="${tableType ne '6' }"><shiro:hasPermission name="enterprise:enterpriseBranchOrg:edit"><li><a href="${ctx}/enterprise/enterpriseBranchOrg/form">分支机构添加</a></li></shiro:hasPermission></c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseBranchOrg" action="${ctx}/enterprise/enterpriseBranchOrg/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<input type="hidden" name="tableType" id="tableType" value="${tableType}">
		<input type="hidden" name="declareRecordId" id="declareRecordId" value="${declareRecordId}">
		<input type="hidden" name="confirm" id="confirm" >		
		
		
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<c:if test="${tableType eq '6' }">
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
				<th>分支机构名称</th>
				<th>所在地区</th>
				<th>职工人数</th>
				<th>修改时间</th>
				<th>备注</th>
				<shiro:hasPermission name="enterprise:enterpriseBranchOrg:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseBranchOrg">
			<tr>
				<td><a href="${ctx}/enterprise/enterpriseBranchOrg/form?id=${enterpriseBranchOrg.id}">
					${enterpriseBranchOrg.branchOrgName}
				</a></td>
				<td>
					${enterpriseBranchOrg.area.id}
				</td>
				<td>
					${enterpriseBranchOrg.workersCount}
				</td>
				<td>
					<fmt:formatDate value="${enterpriseBranchOrg.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${enterpriseBranchOrg.remarks}
				</td>
				<shiro:hasPermission name="enterprise:enterpriseBranchOrg:edit"><td>
    				<a href="${ctx}/enterprise/enterpriseBranchOrg/form?id=${enterpriseBranchOrg.id}">修改</a>
					<a href="${ctx}/enterprise/enterpriseBranchOrg/delete?id=${enterpriseBranchOrg.id}" onclick="return confirmx('确认要删除该分支机构吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>