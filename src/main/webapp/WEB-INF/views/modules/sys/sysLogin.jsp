<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<style type="text/css">
	body{ 
		background:url(${pageContext.request.contextPath}/static/images/back.jpg) fixed center center no-repeat;
		background-size: cover;
 		width: 100%;
 		height: 100%;
		} 
	
      html,body,table{width:100%;text-align:center;}
      .form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px; margin-top:10%; color:#fff;}
      .form-signin{ opacity:0.85; position:relative;text-align:left;width:350px;padding:25px 29px 10px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
        	-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);}
      .form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
      .form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
      .form-signin .btn.btn-large{font-size:16px;} .form-signin #themeSwitch{position:absolute;right:15px;botjktom:10px;}
      .form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
      .header{height:80px;padding-top:20px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
      label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
      
     *{padding:0px;margin:0px;}
	.pop { opacity:0.85; display: block;  width: 330px; min-height: 350px;  max-height: 750px;  height:470px;  position: absolute;  top: 20px;  left: 20px;  bottom: 0;  right: 0;  padding: 15px;  z-index: 130;  border-radius: 8px;  background-color: #fff;  box-shadow: 0 3px 18px rgba(100, 0, 0, .5);  }
	.pop-top{  height:40px;  width:100%;  border-bottom: 1px #E5E5E5 solid;  }
	.pop-top h3{  float: left;  display:black}
	.pop-top span{  float: right;  cursor: pointer;  font-weight: bold; display:black}
	.pop-foot{  height:50px;  line-height:50px;  width:100%;  border-top: 1px #E5E5E5 solid;  text-align: right;  }
	.pop-cancel, .pop-ok {  padding:8px 15px;  margin:15px 5px;  border: none;  border-radius: 5px;  background-color: #337AB7;  color: #fff;  cursor:pointer;  }
	.pop-cancel {  background-color: #FFF;  border:1px #CECECE solid;  color: #000;  }
	.pop-content{  height: 420px; text-align:left; }
	.pop-content-left{ float: left;  }
	.pop-content-right{overflow:hidden;text-overflow:ellipsis; width:320px;  float: left;  padding-top:20px;  padding-left:1px;  font-size: 16px;  line-height:35px;  }
    </style>

	<script type="text/javascript">
		var office_id = "0";
		$(document).ready(function() {

			getTtileName(window.location.href);
			$("#loginForm").validate({
				
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"},
					//username:{remote:"${pageContext.request.contextPath}/login/checkLoginOffic?officeId="+office_id}
				},
				messages: {
					username: {remote:"企业用户暂时不可登录|请选择您所在的咨询服务地址",required: "请填写用户名."},
					password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
			$("#companyregister").click(function(){
				
				  alert("aaaa");

			});
			
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
		
		function getTtileName(httpurl)
		{
			if (httpurl.indexOf("businesslocal") > 0)
			{
				var urlstr = httpurl.substring(0,20);
				if (urlstr.indexOf("bmsbj.")>0){
					office_id='06a3473c1e8e49278dc512f5592cae68';
					document.getElementById("titlename").innerHTML="北京市工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmstj.")>0){
					office_id='c937f18db52842ada70781ebce89a674';
					document.getElementById("titlename").innerHTML="天津市工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmshb.")>0){
					office_id='9495f08c28e24f3388e2bca52dfeaaf3';
					document.getElementById("titlename").innerHTML="河北省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmssx.")>0){
					office_id='689ed07f64e24e5cad075a849f56da8f';
					document.getElementById("titlename").innerHTML="山西省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsnmgzzq.")>0){
					office_id='30f34bd996864f3ab8fcdeaf5b12e3da';
					document.getElementById("titlename").innerHTML="内蒙古自治区工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsln.")>0){
					office_id="f24b0723b3484cb58ee1def426238b6f";
					document.getElementById("titlename").innerHTML="辽宁省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsdl.")>0){
					office_id="d478a76a0c794670a06e0dea0f1676a6";
					document.getElementById("titlename").innerHTML="大连市工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsdl.")>0){
					office_id="d478a76a0c794670a06e0dea0f1676a6";
					document.getElementById("titlename").innerHTML="大连市工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsjls.")>0){
					office_id="b12bfffc5e194e5eb361f3da90432a7e";
					document.getElementById("titlename").innerHTML="吉林省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmshlj.")>0){
					office_id="016362e2b98d4eab89f15e5ce669f483";
					document.getElementById("titlename").innerHTML="黑龙江省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmssh.")>0){
					office_id="4b4d225d75894074a4829710f067c569";
					document.getElementById("titlename").innerHTML="上海市工程咨询行业协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsjs.")>0){
					office_id="e670a59fc0c34e8dbb822788cdf861e8";
					document.getElementById("titlename").innerHTML="江苏省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmszj.")>0){
					office_id="501742a4f0354db0b454ff75846c65b9";
					document.getElementById("titlename").innerHTML="浙江省工程咨询行业协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsnb.")>0){
					office_id="05da80b291394295846219eef1dfd9dc";
					document.getElementById("titlename").innerHTML="宁波市工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsah.")>0){
					office_id="3949e5ca80df4b6dabaa8e4ba30b38a0";
					document.getElementById("titlename").innerHTML="安徽省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsfj.")>0){
					office_id="1bcd71175425442c84ffed62905ad89a";
					document.getElementById("titlename").innerHTML="福建省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsxm.")>0){
					office_id="5fb7a79f97c14a298d87a97dc849f61a";
					document.getElementById("titlename").innerHTML="厦门市工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsjsx.")>0){
					office_id="e996e27bcdd844478815b3e068f4afba";
					document.getElementById("titlename").innerHTML="江西省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmssd.")>0){
					office_id="967b7c57ee4c4db18dfe592c2a94d97c";
					document.getElementById("titlename").innerHTML="山东省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsqd.")>0){
					office_id="785d284b8b654009bb3b47e939debf19";
					document.getElementById("titlename").innerHTML="青岛市工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmshn.")>0){
					office_id="22f71a66016c4664b9934fb853d4051d";
					document.getElementById("titlename").innerHTML="河南省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmshub.")>0){
					office_id="163fae7b36e440e495bbe0998903ded8";
					document.getElementById("titlename").innerHTML="湖北省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmshun.")>0){
					office_id="ff59bac36ce649e6b0bca4814e732751";
					document.getElementById("titlename").innerHTML="湖南省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsgd.")>0){
					office_id="49abe2050d6c42129c37942f1637e683";
					document.getElementById("titlename").innerHTML="广东省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmssz.")>0){
					office_id="26e80531a73b492fa4b9514899cdc2d9";
					document.getElementById("titlename").innerHTML="深圳市工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsgxzzq.")>0){
					office_id="8c564855c9af4d84b90e88b3a06208f1";
					document.getElementById("titlename").innerHTML="广西工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmshain.")>0){
					office_id="9a0555a508c34f338b40dc0881db6f01";
					document.getElementById("titlename").innerHTML="海南省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmssc.")>0){
					office_id="4dc7f4bdb15c4d7ba1bfc7f187232184";
					document.getElementById("titlename").innerHTML="四川省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmscq.")>0){
					office_id="c3e3fba9b2f0409d898f2800038d89a3";
					document.getElementById("titlename").innerHTML="重庆市工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsgz.")>0){
					office_id="39be636b9f34429ba2003f2fd2592e87";
					document.getElementById("titlename").innerHTML="贵州省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsyn.")>0){
					office_id="62a6de326363453e85ea6a12cb866bf8";
					document.getElementById("titlename").innerHTML="云南省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsxzzzq.")>0){
					office_id="f8f348f4a5f54098aa4661a097c275fc";
					document.getElementById("titlename").innerHTML="西藏自治区工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsss.")>0){
					office_id="84c42685673e487ebd34ce588ba57f6a";
					document.getElementById("titlename").innerHTML="陕西省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsgs.")>0){
					office_id="ebce7f4878f34b5690811db23d55d2f7";
					document.getElementById("titlename").innerHTML="甘肃省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsqh.")>0){
					office_id="a3ac261de65c41188741527967fc0913";
					document.getElementById("titlename").innerHTML="青海省工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsnxzzq.")>0){
					office_id="4c1bbd1981034d2ca59c78a974a0a30e";
					document.getElementById("titlename").innerHTML="宁夏回族自治区工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsxjzzq.")>0){
					office_id="190acd30008142f0a2b37e52df97f8f7";
					document.getElementById("titlename").innerHTML="新疆维吾尔自治区工程咨询协会</br>工程咨询单位资信评价管理系统";}
				if (urlstr.indexOf("bmsxjjsbt.")>0){
					office_id="4d81b042c0c44ea7891e595269247e58";
					document.getElementById("titlename").innerHTML="新疆生产建设兵团工程咨询协会</br>工程咨询单位资信评价管理系统";}
			}else if (httpurl.indexOf("business") > 0){
				//地方管理员与地方用户不让登
				var urlstr = httpurl.substring(0,20);
					if (urlstr.indexOf("bms.")>0){
						document.getElementById("titlename").innerHTML="工程咨询单位资信评价管理系统";}
			}else if (httpurl.indexOf("authorize") > 0){
				office_id="all";
				document.getElementById("titlename").innerHTML="中国工程咨询协会统一权限系统";}
			else{
				document.getElementById("titlename").innerHTML="咨询工程师（投资）执业登记系统";
			}
		}
		
	</script>
</head>
<body>
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<div class="header">
		<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error">${message}</label>
		</div>
	</div>
	<div class="pop">
    <div class="pop-top">
        <h3>相关文档下载</h3>
        <span class="pop-close"></span>
    </div>
    <div class="pop-content">

        <div class="pop-content-right">
            <p>一、<b class="lname"><a title="咨询工程师（投资）修改管理系统有关信息申请表" href="${pageContext.request.contextPath}/enterprise/download?type=9">咨询工程师（投资）修改管理系统有关信息申请表</a></b></p>
            <p>二、<b class="lname"><a title="登记证书（电子版）和执业专用章电子签章上线的通知" href="${pageContext.request.contextPath}/enterprise/download?type=10">登记证书（电子版）和执业专用章电子签章上线的通知</a></b></p>
			<p>三、<b class="lname"><a title="咨询工程师（投资）执业专用章电子签章使用指南" href="${pageContext.request.contextPath}/enterprise/download?type=11">咨询工程师（投资）执业专用章电子签章使用指南</a></b></p>
           		申请表请邮寄：职业资格处 01088337625 北京市西城区阜外大街1号四川大厦东塔楼1006</br></br>
           		<font color='red'>有关单位用户注册及使用相关事宜，请咨询01088337663</font>
           </div>
    </div>

</div> 
	<%-- <h1 class="form-signin-heading">${fns:getConfig('productName')}</h1> --%>
	<h1 class="form-signin-heading" id="titlename"></h1>
	<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
<!-- 		<img alt="" src=""> -->
		
		<label class="input-label" for="username">登录名</label>
		<input type="text" id="username" name="username" placeholder="用户名/身份证件号（统一社会信用代码）"  class="input-block-level required" value="">
		<label class="input-label" for="password">密码</label>
		<input type="password" id="password" name="password" class="input-block-level required">
		<c:if test="${isValidateCodeLogin}"><div class="validateCode">
			<label class="input-label mid" for="validateCode">验证码</label>
			<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
		</div>
		
		</c:if><div style="text-align:left"><%--
		<label for="mobile" title="手机登录"><input type="checkbox" id="mobileLogin" name="mobileLogin" ${mobileLogin ? 'checked' : ''}/></label> --%>
		<input class="btn btn-large btn-primary" type="submit" value="登&nbsp;&nbsp;&nbsp;录"/>
<%-- 		<label for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我（公共场所慎用）</label> --%>
		
<!-- 		<input class="btn btn-large btn-primary" type="button" value="单位注册" id="companyregister" style="right:15px"  /> -->
		
		</div>
		
		<div> 
		<a style="line-height: 40px; margin-right: 10px;" href="${pageContext.request.contextPath}/enterprise" target="blank" title="个人用户注册">个人用户注册</a>
		
<%--		<a style="line-height: 40px;" href="${pageContext.request.contextPath}/enterprise"  title="个人注册">个人注册</a>--%>
					<a style="line-height: 40px; margin-left: 55px;" href="http://bms.cnaec.com.cn/business/enterprise" target="blank" title="单位注册">单位注册</a>
		<a style="padding-right:10px;line-height: 40px;margin-left: 55px;" href="${pageContext.request.contextPath}/forgetPwd"  title="忘记密码">忘记密码</a>
		</div>
		<%-- <div id="themeSwitch" class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}<b class="caret"></b></a>
			<ul class="dropdown-menu">
			  <c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href">${dict.label}</a></li></c:forEach>
			</ul>
		</div> --%>
		 <img alt="" src="${pageContext.request.contextPath}/static/images/logoC.png" style="margin-top: 10px; " >
	</form>
	<div class="footer">
<%-- 		Copyright &copy; 2016-${fns:getConfig('copyrightYear')} <a href="${pageContext.request.contextPath}${fns:getFrontPath()}">${fns:getConfig('productName')}</a> - Powered By <a href="http://jeesite.com" target="_blank">JeeSite</a> ${fns:getConfig('version')}  --%>
Copyright &copy; 中国工程咨询协会
	</div>
<%-- 	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script> --%>
</body>
</html>