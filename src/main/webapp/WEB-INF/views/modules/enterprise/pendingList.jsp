<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人用户审核</title>
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
        function pass(id,userId){
        	window.location.href = "${ctx}/enterprise/auditAndReport/pass?userId="+userId+"&id="+id;
        }
        function goBack(id,userId){
        	window.location.href = "${ctx}/enterprise/auditAndReport/goBack?userId="+userId+"&id="+id;
        }
        function lockOver(id,userId){
        	//alert("查看:"+"id:"+id+"userId:"+userId);
        	window.location.href = "${ctx}/sys/user/infoShow?id="+id;
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/expert/expertDistribute/toBeAssignedApplys">待分配列表</a></li> --%>
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/pendingList">单位个人用户信息</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/pendingList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
			<li><label>审查状态: </label>
				<form:select path="confimFlg" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('confim_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>证件类型</th>
				<th>证件号</th>
				<th>咨询工程师（投资）状态</th>
				<th>注册日期</th>
<!-- 				<th>审核状态</th> -->
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
					${fns:getDictLabel(enterpriseWorkers.certificatesType, 'ID_type', '')}
				</td>
				<td>
					${enterpriseWorkers.certificatesNum}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.isValid,'isValid','')}
				</td>
				<td>
					<fmt:formatDate value="${enterpriseWorkers.createDate}" type="date" pattern="yyyy-MM-dd"/>
				</td>
<!-- 				<td> -->
<%-- 					${fns:getDictLabel(enterpriseWorkers.confimFlg,'confim_flag','')} --%>
<!-- 				</td> -->
				<td>
					<c:if test="${enterpriseWorkers.confimFlg eq '0'}">
						<input type="button" value="通过" onclick="pass('${enterpriseWorkers.id}','${enterpriseWorkers.userId}')">
						<input type="button" value="退回" onclick="goBack('${enterpriseWorkers.id}','${enterpriseWorkers.userId}')">
					</c:if>
					<c:if test="${enterpriseWorkers.confimFlg ne '0'}">
						<input type="button" value="查看" onclick="lockOver('${enterpriseWorkers.id}','${enterpriseWorkers.userId}')">
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>