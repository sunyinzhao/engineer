<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中咨复议分配专家列表</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.cssType input{
			width:150px;
		}
		#cssinput input{
			width: 80px;
			display: inline;
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
				$('input[name="personRecordIds"]').val(personRecordId);
			}else{
	       		alert("待处理数据不存在");
	       	 }
			
			var choose = '${flag}';
			if(choose == " " || choose == null || choose =="null" || choose =="" || choose ==undefined || choose =="undefined"){
				//初始化,隐藏复选框下的信息
				document.getElementById("divHide").style.display="none";
			}else if(choose == "0"){
				document.getElementById("divHide").style.display="";
				var input_choose = document.getElementsByName("flag");
				for(var i = 0; i<input_choose.length;i++){
					if(choose == input_choose[i].value){
						input_choose[i].checked = true;
					}
				}
			}else if(choose == "1"){
				document.getElementById("divHide").style.display="none";
				var input_choose = document.getElementsByName("flag");
				for(var i = 0; i<input_choose.length;i++){
					if(choose == input_choose[i].value){
						input_choose[i].checked = true;
					}
				}
			}
			
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
		
        /* 用户点击查询按钮，弹出一个页面窗口 */
		function companyNameWindows(){
			// 正常打开	
			top.$.jBox.open("iframe:${ctx}/enterprise/auditAndReport/form","中咨协会专家列表", 800, 500, {
				ajaxData:{selectIds: $("#${id}Id").val()},
				buttons:{"确定":"ok","关闭":true}
				, submit:function(v, h, f){
					if (v=="ok"){
						var cusId = ""
						var table = h.find("iframe")[0];
						var table1 = $("#contentTable");
						var radio = ($(table)).contents().find("input[name='id']:checked");
						var idAndName = radio.val();
						var array = idAndName.split(";");
						var userId = array[0];
						var userName = array[1];
						$("#name").val(userName);
						$("input[name='userId']").val(userId);
					}
				}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
        
		function manualDistribute(){
			if(window.confirm("你确定要进行手动分配吗？")){
        		var ids = $('input[name="personRecordIds"]').val();
        		var declareType = $('#declareType option:selected').val();
//     			if(declareType =="1" || declareType =="3"){
    				var userId = $("input[name='userId']").val();
    				if(userId == null || userId == '' || userId == undefined){
    					alert("请您选择专家");
    					return;
    				}
    				if(ids.length > 0){
    					var index = prompt("请输入分配给该专家的申请单上限(整数)", "");
            			if(index){
            				var reg = /^[1-9]\d*$/;
            				if (reg.test(index)) {
    			        		$("#searchForm").attr("action","${ctx}/enterprise/auditAndReport/reconsiderManualDistribute?index="+index);
    			        		$("#searchForm").submit();
            		        }else{
            		        	alert('请输入整数!');
                                return;
            		        }
            			}
    	        	}else{
    	        		alert("待处理数据不存在，分配失败");
    	        	}
        	}
		}
			function uploaddata()
			{
        		
			}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/reconsiderDistributeExpert">中咨复议分配专家列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/reconsiderDistributeExpert" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input name="personRecordIds" type="hidden" value=""/>
		<ul class="ul-form">
			<li>查询条件：</li>
			<li><label>归属地：</label><sys:treeselect id="officeId" name="officeId" value="${enterpriseWorkers.officeId}" labelName="officeName" labelValue="${enterpriseWorkers.officeName}"
				title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li>
			<div class = "cssType">
				<li><label>单位名称：</label>
					<form:input path="companyName" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
			</div>
			<li class="btns">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="flag" value="0"/>查询未分配
			</li>
			<li class="btns">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="flag" value="1"/>查询已分配
			</li>
			<div id = "cssinput">
				<li class="btns">
					&nbsp;&nbsp;&nbsp;
					<input type="text" id="name" value=""/>
				</li>
			</div>
			<li class="btns">
				<input type="hidden" name="userId" value="" readonly="readonly">
				<input class="btn btn-primary" type="button" value="选择专家" onclick="companyNameWindows()"/>
			</li>
			<li class="clearfix"></li>
			<li>分配形式：</li>
			<li><label>登记类型:</label>
				<form:select path="declareType" class="input-medium" onchange="changeType(this.value)">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('counselor_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否反馈:</label>
				<form:select path="utilFeedback" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			</li>
			<div id = "divHide">
				<li class="btns">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="btn btn-primary" type="button" value="手动分配" onclick="manualDistribute();"/>
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
				<th>登记日期</th>
				<th>登记类型</th>
				<th>批次状态</th>
				<th>申请单状态</th>
				<th>复核主结论</th>
				<th>复核辅结论</th>
				<th>复核专家</th>
				<th>单位是否反馈</th>
				<th>是否分配</th>
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
					${fns:getDictLabel(enterpriseWorkers.firstZdeclareResult,'decaler_result','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.secondZdeclareResult,'decaler_result','')}
				</td>
				<td>
					${enterpriseWorkers.fexpertName}
				</td>
					
				<td>
					${fns:getDictLabel(enterpriseWorkers.utilFeedback,'yes_no','')}
				</td>
				<td>
					<c:if test="${enterpriseWorkers.batchStatus eq '17' and empty enterpriseWorkers.fExpertId}">
						<span>未分配</span>
					</c:if>
					<c:if test="${enterpriseWorkers.batchStatus eq '17' and not empty enterpriseWorkers.fExpertId}">
						<span>已分配</span>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>