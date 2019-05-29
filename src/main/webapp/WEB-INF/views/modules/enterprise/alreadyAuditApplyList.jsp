<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中咨已审核申请列表</title>
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
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function lockOver(workersId,personRecordId){
        	/* alert("workersId:"+workersId+"personRecordId:"+personRecordId); */
        	window.location.href = "${ctx}/counselor/examine/tree?recordId="+personRecordId+"&type=4";
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/expert/expertDistribute/toBeAssignedApplys">待分配列表</a></li> --%>
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/alreadyAuditApplyList">中咨已审核申请列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/alreadyAuditApplyList" method="post" class="breadcrumb form-search">
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
				<%-- <li><label>审请日期: </label>
					<form:input type="date" path="createDate"/>~
					<form:input type="date" path="updateDate" />
				</li> --%>
				<li><label>登记日期：</label>
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
			<li><label>归属地：</label><sys:treeselect id="officeId" name="officeId" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
				title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>所属地区</th>
				<th>单位名称</th>
				<th>姓名</th>
<!-- 				<th>性别</th> -->
<!-- 				<th>证件类型</th> -->
				<th>证件号</th>
				<th>咨询工程师（投资）状态</th>
				<th>登记类型</th>
				<th>登记日期</th>
				<th>结论1</th>
				<th>结论2</th>
				<th>单位是否反馈</th>
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
					<%-- ${fns:getDictLabel(enterpriseWorkers.title, 'worker_title', '')} --%>
				</td>
				<td>
					${enterpriseWorkers.companyName}
					<%-- ${fns:getDictLabel(enterpriseWorkers.title, 'worker_title', '')} --%>
				</td>
				<td>
					${enterpriseWorkers.name}
					<%-- ${fns:getDictLabel(enterpriseWorkers.title, 'worker_title', '')} --%>
				</td>
<!-- 				<td> -->
<%-- 					${fns:getDictLabel(enterpriseWorkers.sex, 'sex', '')} --%>
<!-- 				</td> -->

<!-- 				<td> -->
<%-- 					${fns:getDictLabel(enterpriseWorkers.certificatesType, 'ID_type', '')} --%>
<!-- 				</td> -->
				<td>
					${enterpriseWorkers.certificatesNum}
				</td>
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
					${fns:getDictLabel(enterpriseWorkers.firstZdeclareResult,'decaler_result','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.secondZdeclareResult,'decaler_result','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.utilFeedback,'yes_no','')}
				</td>
				<td>
 					<input type="button" value="修改" onclick="lockOver('${enterpriseWorkers.id}','${enterpriseWorkers.personRecordId}')">
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>