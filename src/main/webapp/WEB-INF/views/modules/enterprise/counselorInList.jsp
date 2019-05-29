<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位咨询师列表</title>
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
        function lockOver(id,userId){
        	window.location.href = "${ctx}/sys/user/infoShow?id="+id;
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/expert/expertDistribute/toBeAssignedApplys">待分配列表</a></li> --%>
		<li ><a href="${ctx}/enterprise/auditAndReport/counselorList">有效咨询师列表</a></li>
		<li><a href="${ctx}/enterprise/auditAndReport/counselorOutList">准备转出咨询师列表</a></li>
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/counselorInList">准备转入咨询师列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/counselorInList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
			<li><label>咨询师状态: </label>
				<form:select path="isValid" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('isValid')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>序号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>证件号</th>
				<th>主专业</th>
				<th>辅专业</th>
				<th>咨询工程师（投资）状态</th>
				<th>登记证书编号</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseWorkers" varStatus="index">
			<tr>
				<td>
					<input type="hidden" name="userId" value="${enterpriseWorkers.userId}" >
					<input type="hidden" name="id" value="${enterpriseWorkers.id}" >
					${index.index+1}
				</td>
				<td>
					${enterpriseWorkers.name}
					<%-- ${fns:getDictLabel(enterpriseWorkers.title, 'worker_title', '')} --%>
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.sex, 'sex', '')}
				</td>
				<td>
					${enterpriseWorkers.certificatesNum}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.registerMainSpecialty ,"specialty_type" ,"" )}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.registerAuxiliarySpecialty  ,"specialty_type" ,"" )}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.isValid,'isValid','')}
				</td>
				<td>
					${enterpriseWorkers.registerCertificateNum }
				</td>
				<%-- <td>
					<fmt:formatDate value="${enterpriseWorkers.createDate}" type="date" pattern="yyyy-MM-dd"/>
				</td> --%>
				<td>
					<input type="button" value="查看" onclick="lockOver('${enterpriseWorkers.id}','${enterpriseWorkers.userId}')">
					<c:if test="${enterpriseWorkers.zhengShuFlag=='1' && enterpriseWorkers.electronicChapterFlag=='1'}">
						<a target="_blank" href="${ctx}/signaturePDFView/viewEngineerSignature?id=${enterpriseWorkers.id}">证书预览</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>