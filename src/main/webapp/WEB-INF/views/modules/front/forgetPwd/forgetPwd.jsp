<%@ page contentType="text/html;charset=UTF-8"%>

<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
	<title>忘记密码</title>
	
	<link href="/engineer/static/bootstrap/2.3.1/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet">
	<link href="/engineer/static/common/jeesite.min.css" type="text/css" rel="stylesheet">
	<link href="/engineer/static/modules/cms/front/themes/basic/style.css" type="text/css" rel="stylesheet">
	
	<script src="/engineer/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="/engineer/static/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
	<script src="/engineer/static/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="/engineer/static/modules/cms/front/themes/basic/script.js" type="text/javascript"></script>
	

	<link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.method.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${not empty message}">alert("${message}");</c:if>
			$("#inputForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					validateCode: {remote: "验证码不正确"}
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			$("#main_nav li").each(function(){
				$(this).toggleClass("active", $(this).text().indexOf('公共留言')>=0);
			});
			//提交按钮绑定时间
			$("#btnsubmit").bind("click", function () {
				if (document.getElementById("txtcode").value==document.getElementById("checkcode").value){
					$("#inputForm").submit();
				}
				else{
					alert("验证码输入错误，请重新填写。");
				}
            });
		});
		function page(n,s){
			location="${ctx}/guestbook?pageNo="+n+"&pageSize="+s;
		}
		//60秒发送一次短信
		var wait=60;   
		function time(obj) {   
		       if (wait == 0) {   
		    	obj.disabled=false;
		        //obj.removeAttribute("disabled");      
		        obj.value="获取验证码";  
		           wait = 60;   
		       } else {   
		    	if(wait==60){
		    		document.getElementById("checkcode").value=RndNum(6);
		    		var checkcodenum=document.getElementById("checkcode").value;
		    		var cardNumber=document.getElementById("cardNumber").value;
		    		var url="${pageContext.request.contextPath}/forgetPwd/checkcodenum?cardNumber="+cardNumber+"&checkcode="+checkcodenum;
					
					$.ajax({
					  type: 'POST',
				      url: url,
				      dataType: 'text',
				      contentType : 'application/text;charset=UTF-8',
				      success: function(data) {
				    	 if (data=='1'){
				    	  alert("短信已发送！");
				    	 }
				    	 else{
				    		 alert("用户名不存在，请重新输入！");
				    		 obj.disabled=false;
				    		 return;
				    	}
				      },
				      error:function(){
				    	  alert("ajax请求失败");
				    	  return;
				      }
					})
		    	}
		    	obj.disabled=true;  
		        obj.value=wait+"秒后重新发送";  
		           wait--;   
		           setTimeout(function() {   
		               time(obj)   
		           },   
		           1000)   
		       }   
		}  
		//生成随机数
		function RndNum(n){
		    var rnd="";
		    for(var i=0;i<n;i++)
		        rnd+=Math.floor(Math.random()*10);
		    return rnd;
		}
		
		function check(){
			document.getElementById("checkNumber").disabled = false;
		}
	</script>
</head>
<body >
	
	<div style="margin-left:auto;margin-right:auto;  margin-top:50px;margin-bottom:50px; width: 600px; ">
		<h4>忘记密码找回</h4>
		 <form:form id="inputForm"  action="${pageContext.request.contextPath}/forgetPwd" method="post" class="form-horizontal">
			<div class="control-group">
				<label class="control-label">身份证号:</label>
				<div class="controls">
					<input type="text" id="cardNumber" name="cardNumber" maxlength="20" class="required" onchange="check();" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		<div class="control-group">
			<label class="control-label">新密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认新密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">短信验证码:</label>
			<div class="controls">
				<input  id="txtcode" type="text"  name="checkNumberTest" htmlEscape="false" maxlength="8" class="required"/>
				<input  type="button"id="checkNumber" disabled="disabled" name="checkNumber" onclick="time(this)" htmlEscape="false" class="required" value="获取验证码"/>
			</div>
		</div> 
		<div class="form-actions">
				<input id="btnsubmit" class="btn" type="button" value="提 交"/>&nbsp;
		</div>
		<input id="checkcode" type="hidden" value=""/>
		<a href="http://bms.cnaec.com.cn/authorize/forgetPwd">单位密码找回</a>
		</form:form>  
	</div>
</body>
</html>