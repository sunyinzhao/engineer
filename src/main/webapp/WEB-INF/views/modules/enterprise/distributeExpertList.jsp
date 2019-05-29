<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申请数据分配专家列表</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.cssType input{
			width:150px;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
			var index = 0;
	       	var personRecordId = "";
			$('input[id="personRecordId"]').each(function () {
	       		index ++;
	        	personRecordId =personRecordId +this.value+";";
	       	  });
			if(index > 0){
	       		 //将该页面的personRecordId赋值到personRecords上
	       		 $('input[name="personRecordIds"]').val(personRecordId);//将当前页面中所有personRecordId添加到personRecordIds标签中
	       	 }else{
	       		 alert("待处理数据不存在");
	       	 }
			
			//初始化页面时，如果申请类型不为3和1，专业不可选
			var value = $('#declareType option:selected').val();;
			if(value =="1" || value =="3"){
				$("#specialty").removeAttr("disabled");
			}else{
				//document.getElementById("specialty").disabled =true;
				$("#specialty").attr("disabled","disabled");
			}
			
			var choose = '${radioChoose}';
			if(choose == " " || choose == null || choose =="null" || choose =="" || choose ==undefined || choose =="undefined"){
				//初始化,隐藏复选框下的信息
				document.getElementById("divHide").style.display="none";
			}else if(choose == "8"){
				document.getElementById("divHide").style.display="";
				var input_choose = document.getElementsByName("radioChoose");
				for(var i = 0; i<input_choose.length;i++){
					if(choose == input_choose[i].value){
						input_choose[i].checked = true;
					}
				}
			}else if(choose == "7"){
				document.getElementById("divHide").style.display="none";
				var input_choose = document.getElementsByName("radioChoose");
				for(var i = 0; i<input_choose.length;i++){
					if(choose == input_choose[i].value){
						input_choose[i].checked = true;
					}
				}
			}
			
			$("#searchForm").validate({
				rules:{
					declareType:{
						required:true,
					}
				},
				messages:{
					declareType:{
						required:"必选项",
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
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
        function updateExpert1(personRecordId,expertName){
        	if(expertName == " " || expertName == null || expertName =="null" || expertName =="" || expertName ==undefined || expertName =="undefined" ){
        		alert("请重新选择");
        	}else{
        		window.location.href="${ctx}/enterprise/auditAndReport/form?personRecordId="+personRecordId+"&url="+'1';
        	}
        }
        function updateExpert2(personRecordId,expertName){
        	if(expertName == " " || expertName == null || expertName =="null" || expertName =="" || expertName ==undefined || expertName =="undefined" ){
        		alert("请重新选择");
        	}else{
        		window.location.href="${ctx}/enterprise/auditAndReport/form?personRecordId="+personRecordId+"&url="+'2';
        	}
        }
        
        function changeType(value){
			if(value =="1" || value =="3"){
				$("#specialty").removeAttr("disabled");
			}else{
				//document.getElementById("specialty").disabled =true;
				$("#specialty").attr("disabled","disabled");
			}
        }
        
        function distribute(){
        	if(window.confirm("你确定要进行分配吗？")){
        		var ids = $('input[name="personRecordIds"]').val();
	        	if(ids.length > 0){
	        		$("#searchForm").attr("action","${ctx}/enterprise/auditAndReport/localDistributeExpert");
	        		$("#searchForm").submit();
	        	}else{
	        		alert("待处理数据不存在，分配失败");
	        	}
        	}
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/expert/expertDistribute/toBeAssignedApplys">待分配列表</a></li> --%>
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/applyDataDistributeExpert">地方专家分配</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/applyDataDistributeExpert" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input name="personRecordIds" type="hidden" value=""/>
		<!-- flag=0,地方分配专家 -->
		<input name="flag" type="hidden" value="0">
		<ul class="ul-form">
			<li>查询条件：</li>
			<div class = "cssType">
				<li><label>单位名称：</label>
					<form:input path="companyName" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
			</div>
			<li class="btns">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="radioChoose" value="8"/>查询未分配
			</li>
			<li class="btns">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="radioChoose" value="7"/>查询已分配
			</li>
			<li class="clearfix"></li>
			<li>分配形式：</li>
			<li><label>登记类型:</label>
				<form:select path="declareType" class="input-medium" onchange="changeType(this.value)">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('counselor_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
				<li><label>专业: </label>
					<form:select id="specialty" path="specialty" class="input-medium">
						<form:option value="" label=" "/>
						<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
			<li class="btns">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			</li>
			<div id = "divHide">
				<li class="btns">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="distribution" class="btn btn-primary" type="button" value="自动分配" onclick="distribute();"/>
				</li>
			</div>
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
				<th>登记类型</th>
				<th>登记日期</th>
				<th>是否分配</th>
				<th>审核专家1</th>
				<th>审核专家2</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		 <c:forEach items="${page.list}" var="enterpriseWorkers" varStatus="index">
			<tr>
				<td>
					<input type="hidden" id="personRecordId" name="personRecordId" value="${enterpriseWorkers.personRecordId}" >
					<input type="hidden" name="id" value="${enterpriseWorkers.id}" >
					<input type="hidden" name="batchStatus" value="${enterpriseWorkers.batchStatus}" >
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
					${fns:getDictLabel(enterpriseWorkers.sex, 'sex', '')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.certificatesType, 'ID_type', '')}
				</td>
				<td>
					${enterpriseWorkers.certificatesNum}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.type,'counselor_type','')}
				</td>
				<td>
					<fmt:formatDate value="${enterpriseWorkers.createDate}" type="date" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<c:if test="${enterpriseWorkers.batchStatus eq '8'}">
						<span>未分配</span>
					</c:if>
					<c:if test="${enterpriseWorkers.batchStatus eq '7'}">
						<span>已分配</span>
					</c:if>
				</td>
				<td>
					${enterpriseWorkers.firstExpertName}
				</td>
				<td>
					${enterpriseWorkers.secondExpertName}
				</td>
				<td>
					<c:if test="${not empty enterpriseWorkers.firstExpertName}">
						<input type="button" value="手动分配专家1" onclick="updateExpert1('${enterpriseWorkers.personRecordId}','${enterpriseWorkers.firstExpertName}')">
					</c:if>
					<c:if test="${not empty enterpriseWorkers.secondExpertName}">
						<input type="button" value="手动分配专家2" onclick="updateExpert2('${enterpriseWorkers.personRecordId}','${enterpriseWorkers.secondExpertName}')">
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>