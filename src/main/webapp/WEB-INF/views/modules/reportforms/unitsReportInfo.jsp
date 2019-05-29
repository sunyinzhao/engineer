<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预审单位上报信息汇总</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.inputType input{
			width:150px;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#searchForm").validate({
				rules:{
				},
				messages:{
				},
				submitHandler: function(form){
					loading('正在为您处理数据，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function checkBatchNo(batchNo){
        	var reg = /^[12]\d{3}(0[1-9]|1[0-2])$/;
        	var r = batchNo.match(reg);
            if(r == null){
                alert('请输入您本次接收数据的批次号，格式为yyyyMM（4位年2位月）');
                return;
            }
        }
        
        //导出数据
        function exportData(){
        	if (window.confirm("是否确定导出当前数据。")){
	       		 $("#searchForm").attr("action","${ctx}/report/exportUnitsReportInfo");
      			 $("#searchForm").submit();
       	 	}
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/report/unitsReportInfo">预审单位上报信息汇总</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="reportEnterpriseWorkers" action="${ctx}/report/unitsReportInfo" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<c:if test="${flag eq '1' }">
				<li><label>归属地：</label><sys:treeselect id="officeId" name="officeId" value="${reportEnterpriseWorkers.officeId}" labelName="officeName" labelValue="${reportEnterpriseWorkers.officeName}"
					title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/>
				</li>
			</c:if>
			<li class="btns">
				<c:if test="${flag eq '1' }">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				</c:if>
				<input id="btnSubmit" class="btn btn-primary" type="button" value="导出" onclick="exportData()"/>
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>单位名称</th>
				<th>继续登记</th>
				<th>初始登记</th>
				<th>变更单位</th>
				<th>变更专业</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reportEnterpriseWorkers" varStatus="index">
			<tr>
				<td>
					${index.index+1 }
				</td>
				<td>
					${reportEnterpriseWorkers.companyName}
				</td>
				<td>
					${reportEnterpriseWorkers.continueRegistration}
				</td>
				<td>
					${reportEnterpriseWorkers.initialRegistration}
				</td>
				<td>
					${reportEnterpriseWorkers.changeUnit}
				</td>
				<td>
					${reportEnterpriseWorkers.changeSpecialty}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>