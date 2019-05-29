<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>待公告申请列表</title>
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
 
      	//判断字符串是否为数字
        function checkRate(nubmer) {
            //判断正整数
            var re = /^\d+$/;
            if (re.test(nubmer)) {
               return true;
            }else{
            	alert("公告批次只允许输入数字");
            	document.getElementById("batchNo").value="";
            }
        }
      	
      	function zWaitpublicApply(){
      		$("#searchForm").attr("action","${ctx}/enterprise/auditAndReport/zPublicApplyFunc");
            $("#searchForm").submit();
      	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/zWaitPublicApplyList">待公告申请列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="personRecord" action="${ctx}/enterprise/auditAndReport/zWaitPublicApplyList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登记类型: </label>
				<form:select id="declareType" path="declareType" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('counselor_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<div class="inputType">
				<li><label>批次号：</label>
					<form:input id="batchNo" path="batchNo" htmlEscape="false" maxlength="6" class="input-small" onchange="checkRate(this.value)"/>
				</li>
			</div>
			<div class="cssType">
				<li><label>公告日期：</label>
				<input id="publicDate" name="publicDate" type="text"  maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${publicDate}" pattern="yyyy-MM-dd 00:00:00"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',isShowClear:false});"/>
				</li>
			</div>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input type="button" class="btn btn-primary" type="button" value="公告" onclick="zWaitpublicApply()">
			</li>
		</ul>
		<sys:message content="${message}"/>
	</form:form>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>预审单位</th>
				<th>执业单位</th>
				<th>姓名</th>
				<th>身份证号</th>
				<th>登记类型</th>
				<th>登记日期</th>
				<th>结论</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personRecord" varStatus="index">
			<tr>
				<td>
					<input type="hidden" id="id" name="id" value="${personRecord.id}" >
					${index.index+1 }
				</td>
				<td>
					${personRecord.officeName}
				</td>
				<td>
					${personRecord.companyName}
				</td>
				<td>
					${personRecord.workerName}
				</td>
				<td>
					${personRecord.certificatesNum}
				</td>
				<td>
					${fns:getDictLabel(personRecord.declareType,'counselor_type','')}
				</td>
				<td>
					<fmt:formatDate value="${personRecord.declareDate}" type="date" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(personRecord.fdeclareResult,'final_result','')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>