<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>执业资格证书管理</title>
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
		<li><a href="${ctx}/qualificationcertificate/qualificationCertificate/">执业资格证书列表</a></li>
		<li class="active"><a href="${ctx}/qualificationcertificate/qualificationCertificate/form?id=${qualificationCertificate.id}">执业资格证书<shiro:hasPermission name="qualificationcertificate:qualificationCertificate:edit">${not empty qualificationCertificate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="qualificationcertificate:qualificationCertificate:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="qualificationCertificate" action="${ctx}/qualificationcertificate/qualificationCertificate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记主专业：</label>
			<div class="controls">
				<form:select path="registerMainSpecialty" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记辅助专业：</label>
			<div class="controls">
				<form:select path="registerAuxiliarySpecialty" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记证书编号：</label>
			<div class="controls">
				<form:input path="registerCertificateNum" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执业单位：</label>
			<div class="controls">
				<form:input path="companyName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批准日期：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${qualificationCertificate.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">截止日期：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${qualificationCertificate.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="qualificationcertificate:qualificationCertificate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>