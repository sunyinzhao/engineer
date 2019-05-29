<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>处罚处理列表</title>
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
        function punish(batchId,personRecordId){
        	var url = "${ctx}/enterprise/auditAndReport/batchNumber?personRecordId="+personRecordId+"&batchId="+batchId;
        	$.ajax({
  			  type: 'POST',
  		      url: url,
  		      ascyn:true,
  		      dataType: 'text',
  		      contentType : 'application/text;charset=UTF-8',
  		      success: function(data) {
  		    	if(data != ""){
  		    		if(window.confirm("此条处罚将影响同批次内的"+data+"的最终结果")){
  		    			window.location.href = "${ctx}/enterprise/auditAndReport/punish?personRecordId="+personRecordId+"&batchId="+batchId;
  		    		}
  		    	}else{
  		    		if(window.confirm("确定要对该咨询师进行处罚吗？")){
  		        		window.location.href = "${ctx}/enterprise/auditAndReport/punish?personRecordId="+personRecordId+"&batchId="+batchId;
  		        	}
  		    	}
  		      },
  		      error:function(){
  		    	  alert("ajax请求失败");
  		      }  
  			});
        	
        	/* if(window.confirm("确定要对该咨询师进行处罚吗？")){
        		window.location.href = "${ctx}/enterprise/auditAndReport/punish?personRecordId="+personRecordId+"&batchId="+batchId;
        	} */
        }
        
        function punishOver(){
			alert("以对该用户进行处罚");
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/punishList">处罚处理列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/punishList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>预审单位：</label><sys:treeselect id="officeId" name="officeId" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
				title="预审单位" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/>
			</li>
			<div class="inputType">
				<li><label>单位名称：</label>
					<form:input path="companyName" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
				<li><label>咨询师姓名：</label>
					<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
				<li><label>批次：</label>
					<form:input path="batchNo" htmlEscape="false" maxlength="6" class="input-small"/>
				</li>
				<%-- <li><label>专家姓名：</label>
					<form:input path="expertName" htmlEscape="false" maxlength="20" class="input-small"/>
				</li> --%>
			</div>
			<li><label>处罚状态: </label>
				<form:select path="isPunish" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('is_punish')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="clearfix"></li>
<!-- 			<li class="btns"> -->
<!-- 				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 				<input type="radio" name="flag" value="20"/>未公告 -->
<!-- 			</li> -->
<!-- 			<li class="btns"> -->
<!-- 				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 				<input type="radio" name="batchStatus" value="20"/>已公告 -->
<!-- 			</li> -->
			<li class="btns">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
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
				<th>申请登记时间</th>
				<th>申请登记类型</th>
				<th>处罚原因</th>
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
					<fmt:formatDate value="${enterpriseWorkers.createDate}" type="date" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.declareType,'counselor_type','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.impropriety,'impropriety','')}
				</td>
				<td>
					<c:if test="${enterpriseWorkers.isPunish eq '1'}">
						<input type="button" value="已处罚" onclick="punishOver();">
					</c:if>
					<c:if test="${enterpriseWorkers.isPunish ne '1'}">
						<input type="button" value="处罚" onclick="punish('${enterpriseWorkers.batchId}','${enterpriseWorkers.personRecordId}')">
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>