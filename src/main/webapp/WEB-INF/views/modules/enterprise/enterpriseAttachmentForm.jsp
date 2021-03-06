<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>附件管理</title>
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
		<li><a href="${ctx}/enterprise/enterpriseAttachment/?declareRecordId=${declareRecordId}&declareRecordStatus=${declareRecordStatus}&declareRecordFileNo=${declareRecordFileNo}">附件列表</a></li>
		<li class="active"><a href="${ctx}/enterprise/enterpriseAttachment/form?id=${enterpriseAttachment.id}">附件<shiro:hasPermission name="enterprise:enterpriseAttachment:edit">${not empty enterpriseAttachment.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="enterprise:enterpriseAttachment:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="enterpriseAttachment" action="${ctx}/enterprise/enterpriseAttachment/save?pid=${declareRecordId}&declareRecordStatus=${declareRecordStatus}&declareRecordFileNo=${declareRecordFileNo}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<input type="hidden" value="${declareRecordId}" id="declareRecordId" name="declareRecordId">

		<div class="control-group">
			<label class="control-label">附件类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('attachment_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>





		<div class="control-group">
			<label class="control-label">文件名称：</label>
			<div class="controls">
				<form:input path="fileName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">文件存放地址：</label>
			<div class="controls">
				<%--<form:input path="fileNo" value="enterprise${declareRecordFileNo}"  htmlEscape="false" maxlength="100" class="input-xlarge" disabled="true"/>--%>
				<input value="enterprise${declareRecordFileNo}" htmlEscape="false" maxlength="100" class="input-xlarge " disabled="true"  />
			</div>
		</div>


		<%--<div class="control-group">
			<label class="control-label">附件类型（文件扩展名）：</label>
			<div class="controls">
				<form:input path="fileType" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">文件：</label>
			<div class="controls">
				<form:hidden id="path" path="path" htmlEscape="false"  class="input-xlarge"/>
				<sys:ckfinder input="path" type="files" uploadPath="/enterprise/enterprise${declareRecordFileNo}" selectMultiple="true"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">文档ID：</label>
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
			<c:if test="${declareRecordStatus==0}">
				<shiro:hasPermission name="enterprise:enterpriseAttachment:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<c:if test="${declareRecordStatus==1}">
			<shiro:hasPermission name="enterprise:enterpriseAttachment:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>