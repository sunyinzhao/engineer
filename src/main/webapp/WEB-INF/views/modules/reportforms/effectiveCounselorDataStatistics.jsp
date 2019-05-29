<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>有效咨询师数据统计</title>
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
	       		 $("#searchForm").attr("action","${ctx}/report/exportEffectiveCounselorData");
      			 $("#searchForm").submit();
       	 	}
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/report/effectiveCounselorDataStatistics">有效咨询师数据统计</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/report/effectiveCounselorDataStatistics" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <div class="inputType">
				<li><label>批次号：</label>
					<form:input path="batchNo" htmlEscape="false" maxlength="20" class="input-small" onchange="checkBatchNo(this.value)"/>
				</li>
			</div> --%>
			<li class="btns">
				<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/> -->
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
				<th>身份证号</th>
				<th>性别</th>
				<th>执业单位</th>
				<th>审查单位</th>
				<th>主专业</th>
				<th>辅专业</th>
				<th>登记证书编号</th>
				<th>单位联系人</th>
				<th>单位电话区号</th>
				<th>单位电话</th>
				<th>执业登记联系人</th>
				<th>联系电话</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseWorkers" varStatus="index">
			<tr>
				<td>
					<input type="hidden" name="id" value="${enterpriseWorkers.id}" >
					${index.index+1 }
				</td>
				<td>
					${enterpriseWorkers.name}
				</td>
				<td>
					${enterpriseWorkers.certificatesNum}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.sex,'sex','')}
				</td>
				<td>
					${enterpriseWorkers.companyName}
				</td>
				<td>
					${enterpriseWorkers.officeName}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.registerMainSpecialty ,"specialty_type" ,"" )}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.registerAuxiliarySpecialty  ,"specialty_type" ,"" )}
				</td>
				<td>
					${enterpriseWorkers.registerCertificateNum }
				</td>
				<td>
					${enterpriseWorkers.companyContact }
				</td>
				<td>
					${enterpriseWorkers.companyArea }
				</td>
				<td>
					${enterpriseWorkers.companyTel }
				</td>
				<td>
					${enterpriseWorkers.contactsZy }
				</td>
				<td>
					${enterpriseWorkers.contactsZyPhone }
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>