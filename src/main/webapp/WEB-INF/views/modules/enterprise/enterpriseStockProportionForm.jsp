<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>股权结构管理</title>
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
		<li><a href="${ctx}/enterprise/enterpriseStockProportion/">股权结构列表</a></li>
		<li class="active"><a href="${ctx}/enterprise/enterpriseStockProportion/form?id=${enterpriseStockProportion.id}">股权结构<shiro:hasPermission name="enterprise:enterpriseStockProportion:edit">${not empty enterpriseStockProportion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="enterprise:enterpriseStockProportion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="enterpriseStockProportion" action="${ctx}/enterprise/enterpriseStockProportion/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="pid"/>
		<input type="hidden" name="tableType" id="tableType" value="${tableType}">
		<input type="hidden" name="declareRecordId" id="declareRecordId" value="${declareRecordId}">
		

		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">股东名称：</label>
			<div class="controls">
				<form:input path="shareholder" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">股权比例：</label>
			<div class="controls">
				<form:input path="stockProportion" htmlEscape="false" class="input-xlarge "/>%
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">父对象Id：</label>
			<div class="controls">
				<form:input path="pid" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="enterprise:enterpriseStockProportion:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>