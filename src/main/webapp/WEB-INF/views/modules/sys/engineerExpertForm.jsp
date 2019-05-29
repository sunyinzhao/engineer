<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专家管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#no").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "专家登录名已存在"},
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
			//begin add by gaoyongjian 20180620
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
            //e n d add by gaoyongjian 20180620
		});
		
		
		//弹窗. 弹窗用于搜索
        function openCompany(){
            top.$.jBox.open("iframe:${ctx}/counselor/apply/companyWindow","单位名称", 400, 500, {
                ajaxData:{selectIds: $("#${id}Id").val()},
                buttons:{"确定":"ok","关闭":true}
                , submit:function(v, h, f){
                    if (v=="ok"){
                        var cusId = ""
                        var table = h.find("iframe")[0];
                        var radio = ($(table)).contents().find("input[name='radioName']");
                        for(var i=0;i<radio.length;i++){
                            if(radio[i].checked){
                                var value = radio[i].value;
                                var split = value.split(",")
                                var companyName = split[0];
                                var backupNum = split[1];
                                var userId= split[2];
                                $("#userCompanyName").val(companyName)
                                //$("#backupNum").val(backupNum)
                                $("#userCompanyId").val(userId);
                                //将返回的companyNameval到当前页面
                                //$("#companyName").val(checkvalue);
                            }
                        }
                    }
                }, loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }


		function hiddenSpecialty(){
            var result = $('input[name="allSpecialty"]:checked').val();
            if(result=='1'){
                //给div 隐藏了
				$("#hiddenDiv").attr("hidden","hidden")
            }else if(result=='0'){
                $("#hiddenDiv").removeAttr("hidden")
            }
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/engineerExpertList">咨询工程师（投资）评审专家列表</a></li>
		<li class="active"><a href="${ctx}/sys/user/engineerExpertForm?id=${user.id}">咨询工程师（投资）评审专家<shiro:hasPermission name="sys:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:engineerExpert:view">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" commandName="user" action="${ctx}/sys/user/engineerExpertSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		
		
		
		<div class="control-group">
			<label class="control-label">所属地区:</label>
			<div class="controls">
                <sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
					title="公司" url="/sys/office/treeData?type=1" cssClass="required"/>
					
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">所属公司:</label>
			<div class="controls">
                <input id="userCompanyId" name="userCompany.id" type="hidden" value="${user.userCompany.id}">
				<form:input id="userCompanyName" path="userCompany.name" htmlEscape="false" readonly="true"/>
                <input type="button" value="选择" style="width:45px"class="btn" onclick="openCompany()">	
          </div>
		</div>


		<div class="control-group">
			<label class="control-label">是否全选专业:</label>

			<label class="control-label">
				是: <input name="allSpecialty" id="allSpecialty" type="radio" value="1" onclick="hiddenSpecialty()">
				否: <input name="allSpecialty" id="allSpecialty1" type="radio" value="0" onclick="hiddenSpecialty()">

			</label>

		</div>
		
		
		
		
		<div class="control-group" id="hiddenDiv">
			<label class="control-label">专业  </label>
			<div class="controls">
				<c:set value="${fn:split(user.specialtyType,',' ) }" var="categorys" />  <%--得到选中的值--%>
				<form:select path="specialtyType" class="input-large "  multiple="true">
					
					<c:forEach items="${fns:getDictList('specialty_type')}" var="item" >  <%-- 遍历下拉框的所有值--%>
						<c:set value="0" var="selected"/>   <%--是否选中的默认值1为未选中--%>

						<c:forEach items="${categorys}" var="cat"  > <%--判断某一个值是否选中，选中则将变量selected设置为“0”--%>
							<c:if test="${item.value eq  cat}">
								<c:set value="1" var="selected"/>   <%--是否选中的默认值1为未选中--%>
							</c:if>
						</c:forEach>

						<c:choose>
							<c:when test="${selected eq '1' }">
								<form:option value="${item.value}" selected="selected" label="${item.label}"/>  <%--标签选中--%>
							</c:when>
							<c:otherwise>
								<form:option value="${item.value}" label="${item.label}"/>  <%--标签不选中--%>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>
				
				
			</div>
		</div>

		<div class="control-group" >
			<label class="control-label">登记类型  </label>
			<div class="controls">
				<form:select path="registerType" class="input-large "  multiple="true">
					<c:forEach items="${fns:getDictList('counselor_type ')}" var="item" >
						<c:choose>
							<c:when test="${fn:contains(user.registerType,item.value)}">
								<form:option value="${item.value}" selected="selected" label="${item.label}"/>
							</c:when>
							<c:otherwise >
								<form:option value="${item.value}"  label="${item.label}"/>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登录名:</label>
			<div class="controls">
				<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
				<form:input path="loginName" htmlEscape="false" maxlength="50" class="required userName"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="20" minlength="6" class="${empty user.id?'required':''}"/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
				<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="20" minlength="6" equalTo="#newPassword"/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="11" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="100" class="email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="15" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">是否允许登录:</label>
			<div class="controls">
				<form:select path="loginFlag">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
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
		<div class="form-actions">
			<shiro:hasPermission name="sys:engineerExpert:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>