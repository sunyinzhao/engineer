<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申请单列表</title>
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
        	window.location.href = "${ctx}/counselor/examine/tree?recordId="+personRecordId+"&type=0";
        }
        
      //导出数据
        function exportData(){
        	if (window.confirm("是否确定导出当前数据。")){
	       		 $("#searchForm").attr("action","${ctx}/enterprise/auditAndReport/exportApplyFromList");
      			 $("#searchForm").submit();
       	 	}
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/applicationBillsList">申请单列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/applicationBillsList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<c:if test="${flag eq '1' }">
				<li><label>预审单位：</label><sys:treeselect id="officeId" name="officeId" value="${enterpriseWorkers.officeId}" labelName="officeName" labelValue="${enterpriseWorkers.officeName}"
					title="预审单位" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/>
				</li>
			</c:if>
			<li><label>登记类型: </label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('counselor_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<div class="inputType">
				<li><label>咨询师姓名：</label>
					<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
				<li><label>单位名称：</label>
					<form:input path="companyName" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
			</div>
			<li><label>结论: </label>
				<form:select path="expertResult" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('decaler_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>申请单状态: </label>
				<form:select path="declareStatus" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('counselor_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnSubmit" class="btn btn-primary" type="button" value="导出" onclick="exportData()"/>
			</li>
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
				<th>身份证号</th>
				<th>登记类型</th>
				<th>批次状态</th>
				<th>申请单状态</th>
				<th>专家意见1</th>
				<th>专家意见2</th>
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
					${enterpriseWorkers.certificatesNum}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.type,'counselor_type','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.batchStatus,'counselor_status','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.declareStatus,'counselor_status','')}
				</td>
				<c:if test="${flag eq '1'}">
					<td>
						${fns:getDictLabel(enterpriseWorkers.firstZdeclareResult,'decaler_result','')}
					</td>
					<td>
						${fns:getDictLabel(enterpriseWorkers.secondZdeclareResult,'decaler_result','')}
					</td>
				</c:if>
				<c:if test="${flag eq '0'}">
					<td>
						${fns:getDictLabel(enterpriseWorkers.firstCdeclareResult,'decaler_result','')}
					</td>
					<td>
						${fns:getDictLabel(enterpriseWorkers.secondCdeclareResult,'decaler_result','')}
					</td>
				</c:if>
				<td>
					<input type="button" value="查看" onclick="lockOver('${enterpriseWorkers.id}','${enterpriseWorkers.personRecordId}')">
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>