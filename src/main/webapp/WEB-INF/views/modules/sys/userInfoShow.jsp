<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<link href="${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet">
	<link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet">
	<link href="${ctxStatic}/modules/cms/front/themes/basic/style.css" type="text/css" rel="stylesheet">
	<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.method.min.js" type="text/javascript"></script>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			//初始化,隐藏验证码框
			//document.getElementById("divHid").style.display="none";
			
			$("#inputForm").validate({
				rules:{
					validateCode: {
						remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"
					},
					certificatesNum:{
						required:true,
					},
					certificatesType:{
						certificatesType:true,
					},
					name:{
						/* remote: "${pageContext.request.contextPath}/enterprise/checkName", */
						required:true,
					},
					age:{
						required:true,
					},
					sex:{
						required:true,
					},
					mobile:{
						required:true,
						isMobile:true
					},
					email:{
						required:true,
						email:true,
					}
				},
				messages:{
					validateCode: {
						remote: "验证码不正确"
					},
					certificatesNum:{
						required:"必填",
					},
					name:{
						required:"必填",
					},
					age:{
						required:"必填",
					},
					sex:{
						required:"性别(男，女)",
					},
					certificatesType:{
						required:"身份类型必须为(身份证，护照，侨胞证)",
					},
					mobile:{
						required:"*必填！",
						isMobile:"请正确填写您的手机号码"
					},
					email:{
						required:"*必填！",
					}
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
			
			//验证身份证类型
			jQuery.validator.addMethod("certificatesType", function(value, element) {
				return this.optional(element) || (value == "身份证" || value == "侨胞证" || value == "护照");
            }, "身份类型必须为(身份证，护照，侨胞证)");
			
			// 手机号码验证
			jQuery.validator.addMethod("isMobile", function(value, element) {
				var length = value.length;
				var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
				return this.optional(element) || (length == 11 && mobile.test(value));
			}, "请正确填写您的手机号码");

			//页面加载成功判断id是不是协会管理员
			var flag = '${flag}';
			if(flag == '0'){
				//都可以修改
			}else{
				document.getElementById("certificatesType").readOnly=true;
				document.getElementById("certificatesNum").readOnly =true;
				document.getElementById("name").readOnly =true;
				document.getElementById("age").disabled =true;
				document.getElementById("sex").disabled =true;
				document.getElementById("mobile").readOnly =true;
				document.getElementById("email").readOnly =true;
			}
		});
		
		//提交按钮绑定时间
		function subfrom() {
			$("#inputForm").submit();
        };
        
        
        var id = "";//单位id存放
		var officeId="";//存放地区id
			/* 用户点击查询按钮，弹出一个页面窗口 */
			function companyNameWindows(){
				// 正常打开	
				top.$.jBox.open("iframe:${pageContext.request.contextPath}/enterprise/companyNameWidow","单位名称", 400, 500, {
					ajaxData:{selectIds: $("#${id}Id").val()},
					buttons:{"确定":"ok","关闭":true}
					, submit:function(v, h, f){
						if (v=="ok"){
							var cusId = ""
							var table = h.find("iframe")[0];
							var table1 = $("#contentTable");
							var radio = ($(table)).contents().find("input[name='name']");
							var checkvalue ="";
							for(var i=0;i<radio.length;i++){
						        if(radio[i].checked){
						           checkvalue = radio[i].value;
						           //将返回的companyNameval到当前页面
						           $("#companyName").val(checkvalue);
						        }
						    }
							id = ($(table)).contents().find("input[name="+checkvalue+"]").val();
							officeId = ($(table)).contents().find("input[name="+checkvalue+'1'+"]").val();
							$("input[name='pid']").val(id);
							$("input[name='officeId']").val(officeId);
							if(id =="" || id == "undefined" || id == undefined || id == "null" || id == null){
								alert("请选择单位后再确定");
								return false;
							}
						}
					}, loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					}
				});
			}
	</script>
	<script src="${pageContext.request.contextPath}/static/webuploader-0.1.5/image-upload/uploadImage2.js" type="text/javascript"></script>
</head>
<body>
	<input id="ctx" type="hidden" value="${ctx}" />
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/info">个人信息</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/infoSave" method="post" enctype="multipart/form-data" class="form-horizontal">
		<sys:message content="${message}"/>
		<div id="divHidden">
		<div class="control-group">
			<label class="control-label">证件类型:</label>
			<div class="controls">
				<input id="certificatesType" type="text" name="certificatesType" value="${fns:getDictLabel(enterpriseWorkers.certificatesType, 'ID_type', '')}">
				<input class="workerId" type="hidden" name="id" value="${enterpriseWorkers.id }">
				<input type="hidden" name="isValid" value="${enterpriseWorkers.isValid }">
				<input type="hidden" name="roleId" value="${enterpriseWorkers.roleId }">
				<input type="hidden" name="confimFlg" value="${enterpriseWorkers.confimFlg }">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号:</label>
			<div class="controls">
				<label class="lbl"><input id="certificatesNum" type="text" name ="certificatesNum" value="${enterpriseWorkers.certificatesNum }" maxlength="18"></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<input id="name" type="text" name="name" value="${enterpriseWorkers.name}" htmlEscape="false" maxlength="20" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年龄:</label>
			<div class="controls">
				<label class="lbl"><input id="age" type="text" name = "age" value="${enterpriseWorkers.age}"></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别:</label>
			<div class="controls">
				<label class="lbl"><input id="sex" type="text" name="sex" value="${fns:getDictLabel(enterpriseWorkers.sex, 'sex', '')}"></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<%-- <input typt="text" id="mobile" name="mobile" value="${enterpriseWorkers.mobile}" htmlEscape="false" maxlength="50" onchange="changeMobile()"/> --%>
				<input typt="text" id="mobile" name="mobile" value="${enterpriseWorkers.mobile}" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<input id="email" type="text" name="email" value="${enterpriseWorkers.email}" htmlEscape="false" maxlength="50" class="email"/>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">执业单位:</label>
			<div class="controls">
				<input type="hidden" name="pid" value="${enterpriseWorkers.pid}">
				<input type="hidden" name="officeId" value="${enterpriseWorkers.officeId}">
				<input type="text" id="companyName" name="companyName" value = "${enterpriseWorkers.companyName }" readonly="readonly"/>
				<input type="button" id="btn_search" name="search" value ="查找" onclick="companyNameWindows()"/>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${flag eq '0'}">
				<input type="hidden" name="flag" value="${flag}">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="subfrom()"/>
			</c:if>
			<input class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>