<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>咨询工程师（投资）信息</title>
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
        function addSubbmit(){
			$("#searchForm").attr('action','${ctx}'+'/enterprise/enterpriseWorkers/list');
            $("#searchForm").submit();
            //self.location.href="${ctx}/enterprise/enterpriseWorkers/list";
           // window.navigate("${ctx}/enterprise/enterpriseWorkers/form");
		}

	</script>
</head>
<body>
	
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/enterpriseWorkers/listForSignature" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
			<li><label>证件号: </label>
				<form:input path="certificatesNum" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>证件证号</th>
				<th>主专业</th>
				<th>辅助专业</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseWorkers">
			<tr>
				<td>
					<input type="checkbox" name="workersId" value="${(enterpriseWorkers.id)}" >	${enterpriseWorkers.name}
				</td>
				<td>
					${enterpriseWorkers.certificatesNum}
<%-- 							${fns:getDictLabel(enterpriseWorkers.title, 'worker_title', '')} --%>
				</td>
				<td>
						${fns:getDictLabel(enterpriseWorkers.registerMainSpecialty, 'specialty_type', '')}
				</td>
				<td>
						${fns:getDictLabel(enterpriseWorkers.registerAuxiliarySpecialty, 'specialty_type', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>