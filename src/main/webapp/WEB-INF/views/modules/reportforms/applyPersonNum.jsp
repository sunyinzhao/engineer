<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>登记申请-申请人数-材料</title>
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
        //导出数据
        function exportData(){
        	if (window.confirm("是否确定导出当前数据。")){
	       		 $("#searchForm").attr("action","${ctx}/report/exportApplyPersonNumInfo");
      			 $("#searchForm").submit();
       	 	}
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/report/applyPersonNum">登记申请-申请人数-材料</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="reportEnterpriseWorkers" action="${ctx}/report/applyPersonNum" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns">
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
				<th>总计</th>
				<th>初始登记</th>
				<th>小计</th>
				<th>变更专业(含同时变更单位或继续登记)</th>
				<th>变更单位(含同时继续登记)</th>
				<th>继续登记</th>
				<th>注销登记</th>
				<th>签字</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reportEnterpriseWorkers" varStatus="index">
			<tr>
				<td>
					${index.index+1 }
				</td>
				<td>
					${reportEnterpriseWorkers.officeName}
				</td>
				<td>
					${reportEnterpriseWorkers.tatol}
				</td>
				<td>
					${reportEnterpriseWorkers.initialRegistration}
				</td>
				<td>
					${reportEnterpriseWorkers.subtotal}
				</td>
				<td>
					${reportEnterpriseWorkers.changeSpecialty}
				</td>
				<td>
					${reportEnterpriseWorkers.changeUnit}
				</td>
				<td>
					${reportEnterpriseWorkers.continueRegistration}
				</td>
				<td>
					${reportEnterpriseWorkers.cancellationOfRegistration }
				</td>
				<td width="80px"></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>