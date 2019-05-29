<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人员管理</title>
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
	
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/enterpriseWorkers/listForWindow" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>专业: </label>
				<form:select path="specialty" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<%--<li class="btns"><input onclick="addSubbmit()" class="btn btn-primary" type="button" value="增加人员"/></li>--%>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>职称</th>
				<th>从事专业</th>
				<th>主专业</th>
				<th>辅助专业</th>
				<th>人员类型</th>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseWorkers">
			<tr>
				<td>
					<input type="checkbox" name="workersId" value="${(enterpriseWorkers.id)}" >	${enterpriseWorkers.name}
				</td>
				<td>
					<%--${enterpriseWorkers.title}--%>
							${fns:getDictLabel(enterpriseWorkers.title, 'worker_title', '')}
				</td>
					<td>
							${fns:getDictLabel(enterpriseWorkers.engageSpecialty, 'specialty_type', '')}
					</td>

				<td>
						${fns:getDictLabel(enterpriseWorkers.registerMainSpecialty, 'specialty_type', '')}
				</td>
				<td>
						${fns:getDictLabel(enterpriseWorkers.registerAuxiliarySpecialty, 'specialty_type', '')}
				</td>
				<td>
						${fns:getDictLabel(enterpriseWorkers.type,'worker_type','')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>