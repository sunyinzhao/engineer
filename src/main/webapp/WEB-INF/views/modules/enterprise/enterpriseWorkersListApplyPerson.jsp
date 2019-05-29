<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人员管理</title>
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
		
		function addWorkers(){
			// 正常打开	
			top.$.jBox.open("iframe:${ctx}/enterprise/enterpriseWorkers/listForWindow", "选择人员", 600, 500, {
				ajaxData:{selectIds: $("#${id}Id").val()},
				buttons:{"确定":"ok", "清除":"clear", "关闭":true}
				, submit:function(v, h, f){
					if (v=="ok"){
						var cusId = ""
						var table = h.find("iframe")[0];
						var table1 = $("#contentTable");
						var radio = ($(table)).contents().find("input[name=workersId]");
						var workersId=new Array();
						var i=0;
						($(table)).contents().find("input[name=workersId]").each(function () {
							 if ($(this).attr("checked") == 'checked') { 
								 workersId[i++]=$(this).val();
							 }  
						});	
						appendWorkers(workersId);
					}
					else if (v=="clear"){
		            }
					
				}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//用提交表单的方式进行保存选中的数据
		function appendWorkers(workersId){
			if(workersId.length>0){
				$("#workersId").val(workersId);
				$("#searchForm").attr('action','${ctx}'+'/declare/declareApplyPerson/savePerson');    
				$("#searchForm").submit();
			}else{
				
			}
		};
		
	</script>
	
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/enterprise/enterpriseWorkers/">人员列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/enterpriseWorkers/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		
		
		<input type="hidden" name="tableType" id="tableType" value="${tableType}">
		<input type="text" name="declareRecordId" id="declareRecordId" value="${declareRecordId}">
		<input type="hidden" name="workersId" id="workersId" >
		
		
		<input type="hidden" name="confirm" id="confirm" >		
		
		
		
		
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>职称：</label>
				<form:input path="title" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" onclick="complate()" type="button" value="确认完成"/>
				<input id="add" onclick="addWorkers();" class="btn btn-primary" type="button" value="添加人员"/>
				<a href="${ctx}/declare/declareRecord/form?id=${declareRecordId}"> <input id="btn btn-primary" class="btn" type="button" value="返 回" /></a>
			</li>
			
			
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>职称</th>
				<th>主专业</th>
				<th>注释</th>
				<shiro:hasPermission name="enterprise:enterpriseWorkers:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseWorkers">
			<tr>
				<td><a href="${ctx}/enterprise/enterpriseWorkers/form?id=${enterpriseWorkers.id}">
					${enterpriseWorkers.name}
				</a></td>
				<td>
					${enterpriseWorkers.title}
				</td>
				<td>
					${enterpriseWorkers.registerMainSpecialty}
				</td>
				<td>
					${enterpriseWorkers.remarks}
				</td>
				<shiro:hasPermission name="enterprise:enterpriseWorkers:edit"><td>
    				<a href="${ctx}/enterprise/enterpriseWorkers/form?id=${enterpriseWorkers.id}">修改</a>
					<a href="${ctx}/enterprise/enterpriseWorkers/delete?id=${enterpriseWorkers.id}" onclick="return confirmx('确认要删除该人员吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>