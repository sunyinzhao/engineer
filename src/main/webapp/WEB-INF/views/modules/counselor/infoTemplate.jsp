<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>非咨变更管理</title>
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
	<form:form id="inputForm" modelAttribute="enterpriseWorkers" action="" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
        <table class="table-form">
            <tr>
                <td class="tit">姓名：</td>
                <td>
                    <form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly="true"/>
                </td>

                <td class="tit">性别:</td>
                <td>
                    <form:input path="sex" htmlEscape="false" maxlength="100" class="input-xlarge  " readonly="true"/>
                </td>

                <td class="tit">年龄:</td>
                <td>
                        <form:input path="age" htmlEscape="false" maxlength="100" class="input-xlarge  " readonly="true"/>
                </td>

            </tr>

            <tr>

                <td class="tit">证件类型：</td>
                <td>
                   <form:input path="certificatesType" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly="true"/>
                </td>

                <td class="tit">证件号：</td>
                <td>
                    <form:input path="certificatesNum" htmlEscape="false" maxlength="100" class="input-xlarge required " readonly="true"/>
                </td>

                <td class="tit">手机号：</td>
                <td>
                        <form:input path="mobile" htmlEscape="false" maxlength="100" class="input-xlarge required " readonly="true"/>
                </td>

            </tr>

            <tr>

                <td class="tit">邮箱：</td>
                <td>
                    <%--<form:input path="registerAddressNew" htmlEscape="false" maxlength="100" class="input-xlarge required"/>--%>
                </td>

                <td class="tit">执业单位：</td>
                <td>
                    <form:input path="companyName" htmlEscape="false" maxlength="100" class="input-xlarge  required" readonly="true"/>
                </td>

                <td>
                        <%--<form:input path="registerAddress" htmlEscape="false" maxlength="100" class="input-xlarge  required" readonly="true"/>--%>
                </td>

            </tr>
        </table>
        <br/>
	</form:form>

