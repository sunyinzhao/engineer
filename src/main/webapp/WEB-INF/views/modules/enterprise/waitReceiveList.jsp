<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位待接收申请列表</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		div{
			width:500px;
            margin:0 auto;
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
        function pass(personRecordId){
        	window.location.href = "${ctx}/enterprise/auditAndReport/passWaitReceive?personRecordId="+personRecordId;
        }
        function goBack(personRecordId,batchId){
        	window.location.href = "${ctx}/enterprise/auditAndReport/goBackWaitReceive?personRecordId="+personRecordId+"&batchId="+batchId;
        }
        function lockOver(id,personRecordId){
        	//window.location.href = "${ctx}/counselor/list";
        	window.location.href = "${ctx}/counselor/examine/tree?recordId="+personRecordId+"&type=1&flag=1";
        }
        
        function updatePersonStatus(){
        	var index = 0;
        	 $('input[id="personRecordId"]').each(function () {
        		index ++;
        	  });
        	 //window.location.href = "${ctx}/enterprise/auditAndReport/passWaitReceiveList?personRecordId="+personRecordId; */
        	 if(index > 0){
        		 $("#searchForm1").submit();
        	 }else{
        		 alert("待处理数据不存在");
        	 }
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/expert/expertDistribute/toBeAssignedApplys">待分配列表</a></li> --%>
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/waitReceiveList">单位待接受申请列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/waitReceiveList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登记类型: </label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('counselor_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>登记日期: </label>
				<%-- <form:input type="date" path="createDate"/>~
				<form:input type="date" path="updateDate"/> --%>
				<input name="createDate" type="text"  maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${counselor.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="updateDate" type="text"  maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${counselor.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
			<%-- <li><label>审查状态: </label>
				<form:select path="confimFlg" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('confim_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> --%>
			<li class="clearfix"></li>
			<div>
				<li class="btns">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				</li>
				<li class="btns">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnSubmit" class="btn btn-primary" type="button" value="接收查询结果数据" onclick="updatePersonStatus()"/>
				</li>
			</div>
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
<!-- 				<th>证件类型</th> -->
<!-- 				<th>证件号</th> -->
				<th>咨询工程师（投资）状态</th>
				<th>登记类型</th>
				<th>登记日期</th>
				<th>登记事项批次状态</th>
				<th>登记事项状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<form:form id="searchForm1" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/passWaitReceiveList" method="post" class="breadcrumb form-search">
			<c:forEach items="${page.list}" var="enterpriseWorkers" varStatus="index">
				<tr>
					<td>
						<input type="hidden" id="personRecordId" name="personRecordId" value="${enterpriseWorkers.personRecordId}" >
						<input type="hidden" name="id" value="${enterpriseWorkers.id}" >
						${index.index+1 }
					</td>
					<td>
						${enterpriseWorkers.name}
						<%-- ${fns:getDictLabel(enterpriseWorkers.title, 'worker_title', '')} --%>
					</td>
					<td>
						${fns:getDictLabel(enterpriseWorkers.sex, 'sex', '')}
					</td>
	
<!-- 					<td> -->
<%-- 						${fns:getDictLabel(enterpriseWorkers.certificatesType, 'ID_type', '')} --%>
<!-- 					</td> -->
<!-- 					<td> -->
<%-- 						${enterpriseWorkers.certificatesNum} --%>
<!-- 					</td> -->
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
						<input type="button" value="接收" onclick="pass('${enterpriseWorkers.personRecordId}')">
						<input type="button" value="退回" onclick="goBack('${enterpriseWorkers.personRecordId}','${enterpriseWorkers.batchId}')">
						<input type="button" value="查看" onclick="lockOver('${enterpriseWorkers.id}','${enterpriseWorkers.personRecordId}')">
					</td>
				</tr>
			</c:forEach>
		</form:form>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>