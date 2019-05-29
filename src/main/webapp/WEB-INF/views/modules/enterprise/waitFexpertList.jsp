<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中咨待审核申请列表</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.inputType input{
			width:150px;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function lockOver(workersId,personRecordId){
        	/* alert("workersId:"+workersId+"personRecordId:"+personRecordId); */
        	window.location.href = "${ctx}/counselor/examine/tree?recordId="+personRecordId+"&type=5";
        }
		function btnclick()
		{
			var rbtvalue=$("input[name=radioChoose]:checked").val();
    		$("#searchForm").attr("action","${ctx}/enterprise/auditAndReport/waitFexpertList?rbtvalue="+rbtvalue);
    		$("#searchForm").submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/expert/expertDistribute/toBeAssignedApplys">待分配列表</a></li> --%>
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/waitFexpertList">待复议申请单列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/waitFexpertList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登记类型: </label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('counselor_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<div class="inputType">
				<li><label>姓名：</label>
					<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
			</div>
			<li class="btns">
				<input type="radio" name="radioChoose" value="1"/>已复议
			</li>
			<li class="btns">
				<input type="radio" name="radioChoose" value="0"/>未复议
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="button" onclick="btnclick()" value="查询"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>所属地区</th>
				<th>单位名称</th>
				<th>姓名</th>
<!-- 				<th>性别</th> -->
<!-- 				<th>证件类型</th> -->
				<th>证件号</th>
				<th>咨询工程师（投资）状态</th>
				<th>登记类型</th>
				<th>登记日期</th>
				<th>批次状态</th>
				<th>申请单状态</th>
				<th>复核主结论</th>
				<th>复核辅结论</th>
				<th>复核结论</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseWorkers" varStatus="index">
			<tr>
				<td>
					<input type="hidden" id="personRecordId" name="personRecordId" value="${enterpriseWorkers.personRecordId}" >
					<input type="hidden" name="id" value="${enterpriseWorkers.id}" >
					${index.index+1 }
				</td>
				<td>
					${enterpriseWorkers.officeName}
					<%-- ${fns:getDictLabel(enterpriseWorkers.title, 'worker_title', '')} --%>
				</td>
				<td>
					${enterpriseWorkers.companyName}
					<%-- ${fns:getDictLabel(enterpriseWorkers.title, 'worker_title', '')} --%>
				</td>
				<td>
					${enterpriseWorkers.name}
					<%-- ${fns:getDictLabel(enterpriseWorkers.title, 'worker_title', '')} --%>
				</td>
<!-- 				<td> -->
<%-- 					${fns:getDictLabel(enterpriseWorkers.sex, 'sex', '')} --%>
<!-- 				</td> -->

<!-- 				<td> -->
<%-- 					${fns:getDictLabel(enterpriseWorkers.certificatesType, 'ID_type', '')} --%>
<!-- 				</td> -->
				<td>
					${enterpriseWorkers.certificatesNum}
				</td>
				<%-- <td>
					${fns:getDictLabel(enterpriseWorkers.confimFlg,'confim_flag','')}
				</td> --%>
				<td>
					${fns:getDictLabel(enterpriseWorkers.isValid,'isValid','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.type,'counselor_type','')}
				</td>
				<td>
					<fmt:formatDate value="${enterpriseWorkers.createDate}" type="date" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.batchStatus,'counselor_status','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.declareStatus,'counselor_status','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.firstFexpertResult,'decaler_result','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.secondFexpertResult,'decaler_result','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.fdeclareResult,'decaler_result','')}
				</td>
				<td>
					<input type="button" value="复议" onclick="lockOver('${enterpriseWorkers.id}','${enterpriseWorkers.personRecordId}')">
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>