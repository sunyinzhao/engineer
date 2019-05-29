<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/enterprise/enterpriseProject/">项目列表</a></li>
		<li class="active"><a href="${ctx}/enterprise/enterpriseProject/form?id=${enterpriseProject.id}">项目<shiro:hasPermission name="enterprise:enterpriseProject:edit">${not empty enterpriseProject.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="enterprise:enterpriseProject:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="enterpriseProject" action="${ctx}/enterprise/enterpriseProject/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="tableType" id="tableType" value="${tableType}">
		<input type="hidden" name="declareRecordId" id="declareRecordId" value="${declareRecordId}">
		
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:input path="projectName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目服务范围：</label>
			<div class="controls">
				<form:input path="projectServiceRang" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业：</label>
			<div class="controls">
				<form:select path="specialty" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请的服务范围：</label>
			<div class="controls">
				<form:input path="applyServiceRang" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目总投资（万元）：</label>
			<div class="controls">
				<form:input path="projectInvestAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建设规模：</label>
			<div class="controls">
				<form:input path="constructionScale" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">完成时间：</label>
			<div class="controls">
				<input name="completeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${enterpriseProject.completeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">委托单位：</label>
			<div class="controls">
				<form:input path="entrustCompany" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注释：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="enterprise:enterpriseProject:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>