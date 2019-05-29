<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中咨待接收申请列表</title>
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
        
        function updatePersonStatus(){
        	var index = $("input[name=count]").val();
        	var rbtvalue=$("input[name=rbttype]:checked").val();
	       	if(index > 0){
	       		var name = prompt("请输入您本次接收数据的批次号，格式为yyyyMM（4位年2位月）", ""); //将输入的内容赋给变量 name ，  
	       	  
	            //这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值  
	            if (name)//如果返回的有内容  
	            {  	
	            	var reg = /^[12]\d{3}(0[1-9]|1[0-2])$/;
	            	var r = name.match(reg);
	                if(r == null){
	                    alert('对不起，您输入的批次格式不正确!'); //请将“日期”改成你需要验证的属性名称!  
	                    return;
	                }
	        	 	if(index <= 1000){
		        	 	if (window.confirm("是否确定接收"+index+"条申请数据。")){
			       		 $("#searchForm").attr("action","${ctx}/enterprise/auditAndReport/passWaitReceiveApplyList?rbtvalue="+rbtvalue+"&batchNo="+name);
		       			 $("#searchForm").submit();
		        	 	}
		       		}else{
		       			if (window.confirm("是否确定接收"+1000+"条申请数据。")){
				       		 $("#searchForm").attr("action","${ctx}/enterprise/auditAndReport/passWaitReceiveApplyList?rbtvalue="+rbtvalue+"&batchNo="+name);
			       			 $("#searchForm").submit();
			        	 	}
		       		}
	            }  
	       	 }else{
	       		alert("待处理数据不存在");
	       	 }
        }

        function pass(personRecordId){
        	var name = prompt("请输入您本次接收数据的批次号，格式为yyyyMM（4位年2位月）", "");
        	if (name)//如果返回的有内容  
            {  	
            	var reg = /^[12]\d{3}(0[1-9]|1[0-2])$/;
            	var r = name.match(reg);
            	alert(r);
                if(r == null){
                    alert('对不起，您输入的批次格式不正确!'); //请将“日期”改成你需要验证的属性名称!  
                    return;
                }
                window.location.href = "${ctx}/enterprise/auditAndReport/passWaitReceiveApply?batchNo="+name+"&recordid="+personRecordId;
            }  
        }
        
        function lockOver(workerId,personRecordId){
        	window.location.href = "${ctx}/counselor/examine/tree?recordId="+personRecordId+"&type=0";
        }
//         function updateall()
//         {
//         	alert("1");
//         	$("#searchForm").attr("action","${ctx}/counselor/feedBack/updateAll");
//         	$("#searchForm").submit();
//         }
        function submitForm()
        {
        	var rbtvalue=$("input[name=rbttype]:checked").val();
        	$("#searchForm").attr("action","${ctx}/enterprise/auditAndReport/zzWaitReceiveApplyListSearch?rbtvalue="+rbtvalue);
        	$("#searchForm").submit();
        }
        
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/expert/expertDistribute/toBeAssignedApplys">待分配列表</a></li> --%>
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/zzWaitReceiveApplyList">中咨待接收申请列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/zzWaitReceiveApplyListSearch" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="count" name="count" type="hidden" value="${page.count}"/>
		<ul class="ul-form">
			<li><label>登记类型: </label>
				<input id="rbtonly" class="rbttype" name="rbttype" type="radio" value="1" <c:if test="${rbtnvalue =='1'}"> checked="checked"</c:if>>仅</input>
				<input id="rbtexp" class="rbttype" name="rbttype" type="radio" value="2" <c:if test="${rbtnvalue =='2'}"> checked="checked"</c:if>>不包含</input>
				<input id="rbtin" class="rbttype" name="rbttype" type="radio" value="3" <c:if test="${rbtnvalue =='3'}"> checked="checked"</c:if>>包含</input>
				<form:select path="type" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('counselor_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<div class="cssType">
				<li><label>登记日期: </label>
					<input name="createDate" type="text"  maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${enterpriseWorkers.createDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					至
					<input name="updateDate" type="text"  maxlength="20" class="input-medium Wdate "
						   value="<fmt:formatDate value="${enterpriseWorkers.updateDate}" pattern="yyyy-MM-dd"/>"
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
			<li><label>归属地：</label><sys:treeselect id="officeId" name="officeId" value="${enterpriseWorkers.officeId}" labelName="officeName" labelValue="${enterpriseWorkers.officeName}"
				title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="button" onclick="submitForm()" value="查询"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="btnSubmit" class="btn btn-primary" type="button" value="接收全部查询结果" onclick="updatePersonStatus()"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="btnSubmit" class="btn btn-primary" type="button" value="统计"/>
			
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
				<th>性别</th>
				<th>证件类型</th>
				<th>证件号</th>
				<th>咨询工程师（投资）状态</th>
				<th>登记类型</th>
				<th>登记日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<form:form id="searchForm1" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/passWaitReceiveApplyList" method="post" class="breadcrumb form-search">
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
					<td>
						${fns:getDictLabel(enterpriseWorkers.sex, 'sex', '')}
					</td>
	
					<td>
						${fns:getDictLabel(enterpriseWorkers.certificatesType, 'ID_type', '')}
					</td>
					<td>
						${enterpriseWorkers.certificatesNum}
					</td>
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
						<input type="button" value="查看" onclick="lockOver('${enterpriseWorkers.id}','${enterpriseWorkers.personRecordId}')">
						<input type="button" value="接收" onclick="pass('${enterpriseWorkers.personRecordId}')">
					</td>
				</tr>
			</c:forEach>
		</form:form>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>