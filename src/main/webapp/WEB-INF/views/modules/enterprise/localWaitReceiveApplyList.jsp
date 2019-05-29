<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>地方待接收申请列表</title>
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
        	/* alert("workersId:"+workersId+"personRecordId:"+personRecordId); */
        	//window.location.href = "${ctx}/counselor/list";
        	window.location.href = "${ctx}/counselor/examine/tree?recordId="+personRecordId+"&type=0";
        }
        
        function updatePersonStatus(){
        	var count = 0;
        	var rbtvalue=$("input[name=rbttype]:checked").val();
        	 $('input[id="personRecordId"]').each(function () {
        		 count++;
        	  });
        	 if(count > 0){

        	 	if (window.confirm("是否确定接收"+count+"条申请数据。"))
        	 	{
        	 		$("#searchForm1").attr("action","${ctx}/enterprise/auditAndReport/localPassWaitReceiveApplyList?rbtvalue="+rbtvalue);
        	 		$("#searchForm1").submit();
        	 	}
        		 
        	 }else{
        		 alert("待处理数据不存在");
        	 }
        	 //window.location.href = "${ctx}/enterprise/auditAndReport/localPassWaitReceiveApplyList?personRecordId="+personRecordId;
        }

        function pass(personRecordId){
        	var rbtvalue=$("input[name=rbttype]:checked").val();
        	window.location.href = "${ctx}/enterprise/auditAndReport/localPassWaitReceiveApply?personRecordId="+personRecordId+"&rbtvalue="+rbtvalue;
        }
        
        function returnReport(workerId,personRecordId,batchId){
        	if(window.confirm){
        		//window.location.href = "${ctx}/enterprise/auditAndReport/ReceiveReturn?personRecordId="+personRecordId;
        		window.open ("${ctx}/enterprise/auditAndReport/ReceiveReturn?id="+personRecordId+"&workerId="+workerId+"&batchId="+batchId,"window","height=400,width=800,scrollbars=no,location=no");	
        	}
        }
        function submitForm()
        {
        	var rbtvalue=$("input[name=rbttype]:checked").val();
        	/* if(rbtvalue == null || rbtvalue =="" || rbtvalue== "undefined"){
        		alert("1234567");
        		rbtvalue = '0';
        	} */
        	$("#searchForm").attr("action","${ctx}/enterprise/auditAndReport/localWaitReceiveApplyListSearch?rbtvalue="+rbtvalue);
        	$("#searchForm").submit();
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/expert/expertDistribute/toBeAssignedApplys">待分配列表</a></li> --%>
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/localWaitReceiveApplyList">地方待接受申请列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/localWaitReceiveApplyListSearch" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登记类型: </label>
				<input id="rbtonly" class="rbttype" name="rbttype" type="radio" value="1" <c:if test="${rbtnvalue =='1'}"> checked="checked"</c:if>>仅</input>
				<input id="rbtexp" class="rbttype" name="rbttype" type="radio" value="2" <c:if test="${rbtnvalue =='2'}"> checked="checked"</c:if>>不包含</input>
				<input id="rbtin" class="rbttype" name="rbttype" type="radio" value="3" <c:if test="${rbtnvalue =='3'}"> checked="checked"</c:if>>包含</input>
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
				<input id="btnSubmit" class="btn btn-primary" type="button" onclick="submitForm()" value="查询"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="btnSubmit" class="btn btn-primary" type="button" value="接收全部查询结果" onclick="updatePersonStatus()"/>
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
				<th>证件类型</th>
				<th>证件号</th>
				<th>咨询工程师（投资）状态</th>
				<th>登记类型</th>
				<th>登记日期</th>
				<th>单位名称</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<form:form id="searchForm1" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/localPassWaitReceiveApplyList" method="post" class="breadcrumb form-search">
			<c:forEach items="${page.list}" var="enterpriseWorkers" varStatus="index">
				<tr>
					<td>
						<input type="hidden" id="personRecordId" name="personRecordId" value="${enterpriseWorkers.personRecordId}" >
						<input type="hidden" name="id" value="${enterpriseWorkers.id}" >
						<input type="hidden" name="batchId" value="${enterpriseWorkers.batchId}">
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
						${fns:getDictLabel(enterpriseWorkers.certificatesType, 'ID_type', '')}
					</td>
					<td>
						${enterpriseWorkers.certificatesNum}
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
						<%-- ${fns:getDictLabel(enterpriseWorkers.title, 'worker_title', '')} --%>
					</td>
					<td>
						<input type="button" value="查看" onclick="lockOver('${enterpriseWorkers.id}','${enterpriseWorkers.personRecordId}')">
						<input type="button" value="接收" onclick="pass('${enterpriseWorkers.personRecordId}')">
						<input type="button" value="退回" onclick="returnReport('${enterpriseWorkers.id}','${enterpriseWorkers.personRecordId}','${enterpriseWorkers.batchId}')">
					</td>
				</tr>
			</c:forEach>
		</form:form>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>