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
	       		 $("#searchForm").attr("action","${ctx}/report/exportPretrialUnitsReportInfoFile");
      			 $("#searchForm").submit();
       	 	}
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/report/pretrialUnitsReportInfo">预审单位上报信息汇总</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="reportEnterpriseWorkers" action="${ctx}/report/pretrialUnitsReportInfo" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<div class="inputType">
				<li><label>批次号：</label>
					<form:input path="batchNo" htmlEscape="false" maxlength="20" class="input-small" onchange="checkBatchNo(this.value)"/>
				</li>
			</div>
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
				<th>姓名</th>
				<th>上报编号</th>
				<th>身份证号</th>
				<th>性别</th>
				<th>登记单位</th>
				<th>审查单位</th>
				<th>初始登记</th>
				<th>继续登记</th>
				<th>变更单位</th>
				<th>变更专业</th>
				<th>注销登记</th>
				<th>单位分类</th>
				<th>证书编号</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reportEnterpriseWorkers" varStatus="index">
			<tr>
				<td>
					<input type="hidden" name="id" value="${reportEnterpriseWorkers.id}" >
					${index.index+1 }
				</td>
				<td>
					${reportEnterpriseWorkers.name}
				</td>
				<td>
					${reportEnterpriseWorkers.batchNo}
				</td>
				<td>
					${reportEnterpriseWorkers.certificatesNum}
				</td>
				<td>
					${fns:getDictLabel(reportEnterpriseWorkers.sex,'sex','')}
				</td>
				<td>
					${reportEnterpriseWorkers.companyName}
				</td>
				<td>
					${reportEnterpriseWorkers.officeName}
				</td>
				<td align="center">
					${reportEnterpriseWorkers.initialRegistration}
				</td>
				<td align="center">
					${reportEnterpriseWorkers.continueRegistration}
				</td>
				<td align="center">
					${reportEnterpriseWorkers.changeUnit}
				</td>
				<td align="center">
					${reportEnterpriseWorkers.changeSpecialty}
				</td>
				<td align="center">
					${reportEnterpriseWorkers.cancellationOfRegistration}
				</td>
				<td>
					
				</td>
				<td>
					${reportEnterpriseWorkers.registerCertificateNum }
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>