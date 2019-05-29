<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>电子签章业绩管理</title>
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

        function downloadFile( id) {
            location.href='${ctx}//signature/applySignature/downloadFile?id='+id;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/signature/applySignature/">电子签章业绩列表</a></li>
		<shiro:hasPermission name="signature:applySignature:edit"><li><a href="${ctx}/signature/applySignature/form">电子签章业绩添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="applySignature" action="${ctx}/signature/applySignature/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目名称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>项目名称</th>
				<th>服务范围</th>
				<th>服务范围小类</th>
				<th>完成时间</th>
				<th>项目专业</th>
				<shiro:hasPermission name="signature:applySignature:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="applySignature">
			<tr>
				<td>
					${applySignature.projectName}
				</td>
				<td>
					${fns:getDictLabel(applySignature.services, 'service_rang', '')}
				</td>
				<td>
					${fns:getDictLabel(applySignature.childServices, 'service_rang_'.concat(applySignature.services), '')}
				</td>
				<td>
					<fmt:formatDate value="${applySignature.completeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(applySignature.projectSpecialty, 'specialty_type', '')}
				</td>
				<td>
				
				
				
					<c:choose>
						<c:when test="${applySignature.status eq '0' or applySignature.status eq '1'}">
						<shiro:hasPermission name="signature:applySignature:edit">
							<a href="${ctx}/signature/applySignature/form?id=${applySignature.id}">修改</a>
							<a href="${ctx}/signature/applySignature/delete?id=${applySignature.id}" onclick="return confirmx('确认要删除该电子签章业绩吗？', this.href)">删除</a>
						</shiro:hasPermission>
						</c:when>
						<c:otherwise>
							<shiro:hasPermission name="signature:applySignature:view">
							<a href="${ctx}/signature/applySignature/form?id=${applySignature.id}">查看</a>&nbsp;
							</shiro:hasPermission>
						</c:otherwise>
					</c:choose>
					
					<c:if test="${applySignature.status eq '1' or applySignature.status eq '2'}">
			    		<a target="_blank" href="${ctx}/signaturePDFView/viewSignature?id=${applySignature.id}">签章文件</a>
						<a  href="#" onclick="downloadFile('${applySignature.id}')">下载 </a>
					</c:if>
					<c:if test="${applySignature.status == '2'}">
						<a href="${ctx}/signature/applySignature/cancellation?id=${applySignature.id}" onclick="return confirm('确认注销吗？', this.href)">注销 </a>
					</c:if>
    			</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>