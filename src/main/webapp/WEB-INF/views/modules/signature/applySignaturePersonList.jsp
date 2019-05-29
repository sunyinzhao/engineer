<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>签章咨询工程师（投资）管理</title>
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
		<li class="active"><a href="${ctx}/signature/applySignaturePerson/">签署执业专用章</a></li>
<%-- 		<shiro:hasPermission name="signature:applySignaturePerson:edit"><li><a href="${ctx}/signature/applySignaturePerson/form">签章咨询师添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="applySignaturePerson" action="${ctx}/signature/applySignaturePerson/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目名称：</label>
				<form:input path="applySignature.projectName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>完成签章：</label>
			
				<form:select path="status" class="input-medium ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>所属公司</th>
				<th>项目名称</th>
				<th>服务范围</th>
				<th>完成日期</th>
				<th>项目专业</th>				
				<th>承担工作内容职责</th>
				<th>完成签章</th>
				<shiro:hasPermission name="signature:applySignaturePerson:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="applySignaturePerson">
			<tr>
			
			
				<td>
					${applySignaturePerson.enterpriseName}
				</td>
				<td>
					${applySignaturePerson.applySignature.projectName}
				</td>
				<td>
					${fns:getDictLabel(applySignaturePerson.applySignature.services, 'service_range', '')}
				</td>
				<td>
					<fmt:formatDate value="${applySignaturePerson.applySignature.completeDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(applySignaturePerson.applySignature.projectSpecialty, 'specialty_type', '')}
				</td>
			
				<td>
					${fns:getDictLabel(applySignaturePerson.duty, 'signature_duty', '')}
				</td>
				
				<td>
					<c:choose>
						<c:when test="${applySignaturePerson.status =='1' }">是</c:when>
						<c:otherwise>否</c:otherwise>
					</c:choose>
				</td>
				
				
				<shiro:hasPermission name="signature:applySignaturePerson:edit"><td>
					<c:choose>
						<c:when test="${applySignaturePerson.status =='1' }">
							<a href="${ctx}/signature/applySignaturePerson/form?id=${applySignaturePerson.id}">查看</a>
						</c:when>
						<c:otherwise>
							<a href="${ctx}/signature/applySignaturePerson/form?id=${applySignaturePerson.id}">签章</a>
						</c:otherwise>
					</c:choose>
    				
    				
    				&nbsp;
			    	<a target="_blank" href="${ctx}/signaturePDFView/viewSignature?id=${applySignaturePerson.signatureId}">签章文件</a>
					<a  href="#" onclick="downloadFile('${applySignature.id}')">下载 </a>
					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>