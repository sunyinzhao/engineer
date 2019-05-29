<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>退回登记项列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
        });
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">登记事项列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="returnPojo" action="${ctx}/counselor/view/returnList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登记类型：</label>
				<form:select path="declareType" style="width:130px;" multiple="false" >
					<form:option value="" label=""/>
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('counselor_type')}" itemLabel="label" itemValue="value"  htmlEscape="false"/>
				</form:select>
			</li>

			<li><label>退回类型：</label>
				<form:select path="returnType" style="width:130px;" multiple="false" >
					<form:option value="" label=""/>
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('return_type')}" itemLabel="label" itemValue="value"  htmlEscape="false"/>
				</form:select>
			</li>

			<li><label>执业单位：</label>
				<form:input path="companyName"/>
			</li>

			<li><label>姓名：</label>
				<form:input path="workerName" maxlength="10"/>
			</li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        </ul>
	<ul class="ul-form">
		<li><label>登记日期：</label>
			<input name="startTime" type="text"  maxlength="20" class="input-medium Wdate "
				   value="${returnPojo.startTime}"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			至
			<input name="endTime" type="text"  maxlength="20" class="input-medium Wdate "
				   value="${returnPojo.endTime}"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		</li>
	</ul>
	</form:form>
	<sys:message content="${message}"/>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>预审单位</th>
				<th>执业单位名称</th>
				<th>执业单位联系人</th>
				<th>执业单位联系电话</th>
				<th>姓名</th>
				<th>登记类型</th>
				<th>批次状态</th>
				<th>申请单状态</th>
				<th>退回类型</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="returnPojo" varStatus="index">
			<tr>
				<td> ${index.index+1}</td>
				<td>
						${returnPojo.officeName}
				</td>
				<td>
						${returnPojo.companyName}
				</td>
				<td>
						${returnPojo.contactsZy}
				</td>
				<td>
						${returnPojo.contactZyPhone}
				</td>

				<td>
						${returnPojo.workerName}
				</td>

				<td>
					${fns:getDictLabel(returnPojo.declareType,'counselor_type','')}
				</td>

				<td>
							${fns:getDictLabel(returnPojo.batchStatus,'counselor_status','')}
				</td>

				<td>
							${fns:getDictLabel(returnPojo.declareStatus,'counselor_status','')}
				</td>

				<td>
						${fns:getDictLabel(returnPojo.returnType,'return_type','')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>