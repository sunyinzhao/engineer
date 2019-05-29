<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家意见告知列表</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#searchForm").validate({
				rules:{
					batchNo:{
						batchNo:true,
					}
				},
				messages:{
					batchNo:{
						
					}
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
			
			 // 批次号验证
            jQuery.validator.addMethod("batchNo", function(value, element) {
                var reg = /^[12]\d{3}(0[1-9]|1[0-2])$/;
                return this.optional(element) || (reg.test(value));
            }, "批次格式错误");
			 
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        function pass(personRecordId,batchId){
        	window.location.href = "${ctx}/enterprise/auditAndReport/expertSuggestionPublic?personRecordId="+personRecordId+"&batchId="+batchId;
        }
        
        function updatePersonStatus(){
        	var index = $("input[name=count]").val();
        	
        	if(index <= 1000){
        	 	if (window.confirm("是否确定接收"+index+"条申请数据。")){
	       		 $("#searchForm").attr("action","${ctx}/enterprise/auditAndReport/passExpertSuggestionPublic");
       			 $("#searchForm").submit();
        	 	}
       		}else{
       			if (window.confirm("是否确定接收"+1000+"条申请数据。")){
		       		 $("#searchForm").attr("action","${ctx}/enterprise/auditAndReport/passExpertSuggestionPublic");
	       			 $("#searchForm").submit();
	        	 	}
       		}
        	/* var index = 0;
        	 $('input[id="personRecordId"]').each(function () {
        		index ++;
        	  });
        	 if(index > 0){
        		 $("#searchForm1").submit();
        	 }else{
        		 alert("待处理数据不存在");
        	 } */
        }
        
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/expert/expertDistribute/toBeAssignedApplys">待分配列表</a></li> --%>
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/expertSuggestionPublicList">专家意见告知列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/expertSuggestionPublicList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="count" name="count" type="hidden" value="${page.count}"/>
		<ul class="ul-form">
			<li><label>预审单位：</label>
				<sys:treeselect id="officeId" name="officeId" value="${enterpriseWorkers.officeId}" labelName="officeName" labelValue="${enterpriseWorkers.officeName}"
				title="预审单位:" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li>
			<li><label>登记类型: </label>
				<form:select path="declareType" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('counselor_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>咨询师姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
			<li><label>单位名称：</label>
				<form:input path="companyName" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
			<li><label>专家姓名：</label>
				<form:input path="expertName" htmlEscape="false" maxlength="20" class="input-small"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</li>
			<li><label>公告批次：</label>
				<form:input path="batchNo" htmlEscape="false" maxlength="20" class="input-small" onchange="checkBatchNo(this.value)"/>
			</li>
			<div>
				<li class="btns">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				</li>
				<li class="btns">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnSubmit" class="btn btn-primary" type="button" value="告知全部查询结果" onclick="updatePersonStatus()"/>
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
				<th>预审单位</th>
				<th>单位名称</th>
				<th>姓名</th>
				<th>申请登记时间</th>
				<th>申请登记类型</th>
				<th>批次状态</th>
				<th>申请单状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<form:form id="searchForm1" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/passExpertSuggestionPublic" method="post" class="breadcrumb form-search">
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
						<input type="button" value="意见公告" onclick="pass('${enterpriseWorkers.personRecordId}','${enterpriseWorkers.batchId}')">
					</td>
				</tr>
			</c:forEach>
		</form:form>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>