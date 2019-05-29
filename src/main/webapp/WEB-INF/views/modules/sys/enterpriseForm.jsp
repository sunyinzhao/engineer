<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#no").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "企业登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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
		<li><a href="${ctx}/list">企业列表</a></li>
		<li class="active"><a href="#">企业查看</a></li>
	</ul><br/>


	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/enterprise/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="confimFlg" />
		<form:hidden path="licensePath" />
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">营业执照:</label>
			<div class="controls">
				<a href="${ctx}/sys/enterprise/id/${user.id}" target="_blank">
					<img src="${ctx}/sys/enterprise/id/${user.id}" id="npcImg" style="max-width: 500px; max-height: 500px;" />
				</a>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">身份证照片:</label>
			<div class="controls">
				<img src="/images/${user.licensePath}"  style="width:50px"/>
			</div>
		</div>--%>
		<!--  <div class="control-group">
		<label class="control-label">营业执照:</label>
		<div class="controls">
		<img src="${user.licensePath}" style="" />
		</div>
		</div>
		<div class="control-group">
		<label class="control-label">身份证照片:</label>
		<div class="controls">
		<img src="${user.cardNumberPath}" style=""/>
		</div>
		</div>-->
		<div class="control-group">
			<label class="control-label">归属地:</label>
			<div class="controls">
				<sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
								title="公司" url="/sys/office/treeData?type=1" cssClass="required" />
			</div>
		</div>

		<%--
		<div class="control-group">
			<label class="control-label">身份证号:</label>
			<div class="controls">
				<form:input path="cardNumber" htmlEscape="false" maxlength="20" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		--%>

		<div class="control-group">
			<label class="control-label">企业名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" />

			</div>
		</div>

		<div class="control-group">
			<label class="control-label">统一社会信用代码:</label>
			<div class="controls">
				<form:input path="orgCode" htmlEscape="false" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">备案号:</label>
			<div class="controls">
				<form:input path="recordNumber" htmlEscape="false"  />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">注册地址:</label>
			<div class="controls">
				<form:input path="registerAddress" htmlEscape="false"  />
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">通讯地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false"   />
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">法人代表:</label>
			<div class="controls">
				<form:input path="legalPerson" htmlEscape="false" />

			</div>
		</div>


		<div class="control-group">
			<label class="control-label">登录名:</label>
			<div class="controls">
				<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
				<form:input path="loginName" htmlEscape="false" maxlength="50" class="required userName" readonly="true"/>

			</div>
		</div>


		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="100" class="required" />

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="100" class="email" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="100"  />
			</div>
		</div>


		<%-- <div class="control-group">
			<label class="control-label">用户类型:</label>
			<div class="controls">
				<form:select path="userType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div> --%>
		<%--<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div class="controls">
				<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">审批意见:</label>
			<div class="controls">
				<form:textarea path="reviewMemo" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>--%>
		<c:if test="${not empty user.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">最后登陆:</label>
				<div class="controls">
					<label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">是否允许登录:</label>
			<div class="controls">
				<form:select path="loginFlag">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:expert:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>