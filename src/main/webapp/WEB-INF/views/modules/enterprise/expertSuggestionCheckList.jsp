<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中咨专家意见复核列表</title>
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
        function lockOver1(workersId,personRecordId){
        	/* alert("workersId:"+workersId+"personRecordId:"+personRecordId); */
        	window.location.href = "${ctx}/counselor/examine/tree?recordId="+personRecordId+"&type=6";
        }
		function uploaddata()
		{
			$("#searchForm").attr("action","${ctx}/${adminPath}/counselor/updateService/updateWorker");
// 			$("#searchForm").attr("action","${ctx}/${adminPath}/counselor/updateService/updateWorker?uuid=ferfaf4g4t4093ff329jg&pageNo=1&pageSize=10");
    		$("#searchForm").submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/expertSuggestionCheckList">专家意见复核</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/expertSuggestionCheckList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>预审单位：</label><sys:treeselect id="officeId" name="officeId" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
				title="预审单位" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/>
			</li>
			<li><label>登记类型: </label>
				<form:select path="declareType" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('counselor_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>终审结论: </label>
				<form:select path="flag" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('decaler_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>复核结论: </label>
				<form:select path="fdeclareResult" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('decaler_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
<!-- 			<div class="inputType"> -->
				<li><label>咨询师姓名：</label>
					<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
				<li><label>单位名称：</label>
					<form:input path="companyName" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
				<li><label>终审专家：</label>
					<form:input path="expertName" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
				<li><label>复核专家：</label>
					<form:input path="fexpertName" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
<!-- 			</div> -->
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			</li>
<!-- 				<li class="btns"> -->
<!-- 					<input id="btnSubmit" class="btn btn-primary" type="button" value="继续教育同步数据" onclick="uploaddata();"/> -->
<!-- 				</li> -->
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>预审单位</th>
				<th>单位名称</th>
				<th>姓名</th>
				<th>申请登记时间</th>
				<th>申请登记类型</th>
				<th>批次状态</th>
				<th>申请单状态</th>
				<th>终审结论</th>
				<th>是否反馈</th>
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
				</td>
				<td>
					${enterpriseWorkers.companyName}
				</td>
				<td>
					${enterpriseWorkers.name}
				</td>
				<td>
					<fmt:formatDate value="${enterpriseWorkers.createDate}" type="date" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.declareType,'counselor_type','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.batchStatus,'counselor_status','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.declareStatus,'counselor_status','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.zdeclareResult,'decaler_result','')}
				</td>
<!-- 				<td> -->
<%-- 					${fns:getDictLabel(enterpriseWorkers.secondZdeclareResult,'decaler_result','')} --%>
<!-- 				</td> -->
				<td>
					${fns:getDictLabel(enterpriseWorkers.utilFeedback,'yes_no','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.fdeclareResult,'decaler_result','')}
				</td>
				<td>
					<input type="button" value="修改" onclick="lockOver1('${enterpriseWorkers.id}','${enterpriseWorkers.personRecordId}')">
 					<c:if test="${enterpriseWorkers.utilFeedback eq '1' || enterpriseWorkers.utilFeedback eq '2' ||enterpriseWorkers.secondZdeclareResult eq '1' || enterpriseWorkers.firstZdeclareResult eq '1'}">
						<input type="button" value="复议" onclick="lockOver('${enterpriseWorkers.id}','${enterpriseWorkers.personRecordId}')">
 					</c:if> 
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>