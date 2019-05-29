<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>地方待上报申请列表</title>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-jbox/2.3/Skins/Default/jbox.css"/>
	<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.cssType input{
			width:150px;
		}
		.inputType input{
			width:150px;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			/* var count = 0;
	       	 $('input[id="personRecordId"]').each(function () {
	       		 count++;
	       	  });
	       	 if(count== "0"){
	       		 alert("无接收信息");
	       	 } */
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function lockOver(workersId,personRecordId){
        	window.location.href = "${ctx}/counselor/examine/tree?recordId="+personRecordId+"&type=3";
        }
        
        function updatePersonStatus(){
        	var count = 0;
        	 $('input[id="personRecordId"]').each(function () {
        		 count++;
        	  });
        	 if(count > 0){
        	 	if (window.confirm("是否确定上报"+count+"条申请数据。"))
        	 	{$("#searchForm1").submit();}
        	 }else{
        		 alert("待处理数据不存在");
        	 }
        }

        function report(workerId,personRecordId){
        	window.location.href = "${ctx}/enterprise/auditAndReport/localPassWaitReportZZApply?personRecordId="+personRecordId+"&id="+workerId;
        }
        
        function returnReport(workerId,personRecordId){
        	if(window.confirm("若同批次下有其他登记事项，其他事项将一并退回，是否确定退回该条申请数据。")){
        		window.location.href = "${ctx}/enterprise/auditAndReport/returnReportWindow?id="+personRecordId+"&workerId="+workerId;
        	}
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/localWaitReceiveApplyList">地方待上报中咨列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/localWaitReportZZListSearch" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登记类型: </label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('counselor_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<div class="cssType">
				<li><label>登记日期: </label>
					<%-- <form:input type="date" path="createDate"/>~
					<form:input type="date" path="updateDate" /> --%>
					<input name="createDate" type="text"  maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${counselor.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					至
					<input name="updateDate" type="text"  maxlength="20" class="input-medium Wdate "
						   value="<fmt:formatDate value="${counselor.endDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
			</div>
			<div class="inputType">
				<li><label>姓名：</label>
					<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
				<li class="clearfix"></li>
				<li><label>单位名称：</label>
					<form:input path="companyName" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
			</div>
			<li class="btns">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="btnSubmit" class="btn btn-primary" type="button" value="上报已查询数据" onclick="updatePersonStatus()"/>
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>咨询工程师（投资）状态</th>
				<th>登记类型</th>
				<th>登记日期</th>
				<th>单位</th>
				<th>登记事项批次状态</th>
				<th>登记事项状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<form:form id="searchForm1" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/localPassWaitReportZZApplyList" method="post" class="breadcrumb form-search">
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
						${enterpriseWorkers.companyName}
					</td>
					<td>
						${fns:getDictLabel(enterpriseWorkers.batchStatus,'counselor_status','')}
					</td>
					<td>
						${fns:getDictLabel(enterpriseWorkers.declareStatus,'counselor_status','')}
					</td>
					<td>
						<input type="button" value="查看" onclick="lockOver('${enterpriseWorkers.id}','${enterpriseWorkers.personRecordId}')">
						<input type="button" value="上报" onclick="report('${enterpriseWorkers.id}','${enterpriseWorkers.personRecordId}')">
						<input type="button" value="退回" onclick="returnReport('${enterpriseWorkers.id}','${enterpriseWorkers.personRecordId}')">
					</td>
				</tr>
			</c:forEach>
		</form:form>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>