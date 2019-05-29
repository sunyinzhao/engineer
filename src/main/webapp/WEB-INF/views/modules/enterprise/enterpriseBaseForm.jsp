<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>基本数据管理</title>
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

        // 手机号码验证
        jQuery.validator.addMethod("mobile", function(value, element) {
            var tel = /(^1[3,4,5,7,8]\d{9}$)/g;
            return this.optional(element) || (tel.test(value));
        }, "手机为:13,14,15,17,18号段，例如：13888888888");

        // 电话号码验证
        jQuery.validator.addMethod("phone", function(value, element) {
            var tel =  /\d{2,5}-\d{7,8}$/g;
            return this.optional(element) || (tel.test(value));
        }, "格式为:固话为区号(3-4位)号码(7-8位),例如：010-88888888");
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%--<li><a href="${ctx}/enterprise/enterpriseBase/">基本数据列表</a></li>--%>
		<li class="active"><a href="${ctx}/enterprise/enterpriseBase/form?id=${enterpriseBase.id}">基本数据<shiro:hasPermission name="enterprise:enterpriseBase:edit">${not empty enterpriseBase.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="enterprise:enterpriseBase:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="enterpriseBase" action="${ctx}/enterprise/enterpriseBase/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<legend>企业基本信息</legend>
		<table class="table-form">


			<tr>

				<td class="tit">企业名称：</td>
				<td>
						${enterpriseBase.user.name }
<%-- 					<form:input path="companyName" htmlEscape="false" maxlength="200" class="input-medium "/> --%>
				</td>
				
				<td class="tit">统一社会信用代码：</td>
				<td>
					${enterpriseBase.user.orgCode }
					<%-- <form:input path="organizationCode" htmlEscape="false" maxlength="20" class="input-medium "/>
					<span class="help-inline"><font color="red">*</font> </span> --%>
				</td>

				<td class="tit">所属地区：</td>
				<td>
				<%-- 	<sys:treeselect id="user" name="user.id" value="${enterpriseBase.user.id}" labelName="user.name" labelValue="${enterpriseBase.user.name}"
								title="用户" url="/sys/office/treeData?type=3" cssClass="input-medium" allowClear="true" notAllowSelectParent="true"/>
				 --%>
				 ${enterpriseBase.office.name }
				 </td>


			</tr>

			<tr>

				<td class="tit">注册地址：</td>
				<td>
					${enterpriseBase.registerAddress}
				</td>

                <td class="tit">备案编号：</td>
                <td>
                        ${enterpriseBase.applicationCode}
                </td>

                <td class="tit">成立时间：</td><td>
                <input name="establishmentDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                       value="<fmt:formatDate value="${enterpriseBase.establishmentDate}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                </td>
            </tr>

            <tr>
				<td class="tit">通讯地址：</td>
				<td>
					<form:input path="postalAddress" htmlEscape="false" readonly="true" maxlength="300" class="input-medium " value="${enterpriseBase.user.address }"/>
				</td>
                <td class="tit">网址：</td>
                <td>
                    <form:input path="url" htmlEscape="false" maxlength="100" readonly="true" class="input-medium "/>
                </td>
				<td class="tit">公司性质：</td>
				<td>
					<form:select path="comanpyType" disabled="true" class="input-medium ">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('declare_company_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>


			</tr>
			<tr>
				
				<td class="tit">从事咨询业务年限：</td>
				<td>
					<form:input path="years" htmlEscape="false" maxlength="5" readonly="true" class="input-medium  digits"/>
				</td>

				<td class="tit">邮编：</td>
				<td>
					<form:input path="zipCode" htmlEscape="false" maxlength="10" readonly="true" class="input-medium "/>
				</td>

				<td class="tit">法人代表：</td>
				<td>
					<form:input path="legalPerson" htmlEscape="false" maxlength="15" readonly="true" class="input-medium "/>
				</td>
			</tr>
			<tr>

				<td class="tit">职务：</td>
				<td>
					<form:input path="post" htmlEscape="false" maxlength="255"  readonly="true" class="input-medium "/>
				</td>

				<td class="tit">电话：</td>
				<td>
					<form:input path="phone" htmlEscape="false" maxlength="15" readonly="true" class="input-medium phone"/>
				</td>

				<td class="tit">手机：</td>
				<td>
					<form:input path="mobile" htmlEscape="false" maxlength="15" readonly="true" class="input-medium mobile"/>
				</td>
			</tr>
			<tr>
				<td class="tit">联系部门：</td>
				<td>
					<form:input path="contactDept" htmlEscape="false" maxlength="30" readonly="true" class="input-medium "/>
				</td>

				<td class="tit">联系电话：</td>
				<td colspan="3">
					<form:input path="contactPhone" htmlEscape="false" maxlength="15" readonly="true" class="input-medium phone"/>
				</td>					
			</tr>
			
			<tr>
				<td class="tit">资信联系人：</td>
				<td>
					<form:input path="contactsZx" htmlEscape="false" maxlength="10"  class="input-medium "/>
				</td>

				<td class="tit">资信联系电话：</td>
				<td colspan="3">
					<form:input path="contactsZxPhone" htmlEscape="false" maxlength="15"  class="input-medium mobile "/>
				</td>					
			</tr>
			
			<tr>
				<td class="tit">执业联系人：</td>
				<td>
					<form:input path="contactsZy" htmlEscape="false" maxlength="30" class="input-medium "/>
				</td>

				<td class="tit">执业联系电话：</td>
				<td colspan="3">
					<form:input path="contactsZyPhone" htmlEscape="false" maxlength="15" class="input-medium mobile"/>
				</td>					
			</tr>
			
			<tr>
				<td class="tit">评优联系人：</td>
				<td>
					<form:input path="contactsPy" htmlEscape="false" maxlength="30" class="input-medium "/>
				</td>

				<td class="tit">评优联系电话：</td>
				<td colspan="3">
					<form:input path="contactsPyPhone" htmlEscape="false" maxlength="15" class="input-medium mobile"/>
				</td>					
			</tr>
			<tr>
				<td class="tit">公司简介：</td>
				<td colspan="5">
					<form:textarea path="companyIntroduction" htmlEscape="false" rows="12"  readonly="true"  cssStyle="width:98%;" maxlength="2000" />
				</td>
			</tr>
		</table>
	

		<div class="form-actions">
			<shiro:hasPermission name="enterprise:enterpriseBase:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>