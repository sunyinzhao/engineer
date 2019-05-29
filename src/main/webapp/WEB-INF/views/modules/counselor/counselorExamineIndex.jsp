<%--
  Created by IntelliJ IDEA.
  User: 84249
  Date: 2018/8/14
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<!--a b c d -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title></title>
    <meta name="decorator" content="blank"/>
    <style type="text/css">
        body{
            background:url(${pageContext.request.contextPath}/static/images/examine.jpg) fixed center center no-repeat;
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
        .pop { opacity:0.85; display: block;  width: 300px; min-height: 230px;  max-height: 750px;  height:350px;  position: absolute;  top: 20px;  left: 20px;  bottom: 0;  right: 0;  padding: 15px;  z-index: 130;  border-radius: 8px;  background-color: #fff;  box-shadow: 0 3px 18px rgba(100, 0, 0, .5);  }
        .pop-top{  height:40px;  width:100%;  border-bottom: 1px #E5E5E5 solid;  }
        .pop-top h3{  float: left;  display:black}
        .pop-top span{  float: right;  cursor: pointer;  font-weight: bold; display:black}
        .pop-foot{  height:50px;  line-height:50px;  width:100%;  border-top: 1px #E5E5E5 solid;  text-align: right;  }
        .pop-cancel, .pop-ok {  padding:8px 15px;  margin:15px 5px;  border: none;  border-radius: 5px;  background-color: #337AB7;  color: #fff;  cursor:pointer;  }
        .pop-cancel {  background-color: #FFF;  border:1px #CECECE solid;  color: #000;  }
        .pop-content{  height: 240px; text-align:left; }
        .pop-content-left{  float: left;  }
        .pop-content-right{ white-space:nowrap;overflow:hidden;text-overflow:ellipsis; width:290px;  float: left;  padding-top:20px;  padding-left:10px;  font-size: 16px;  line-height:35px;  }
    </style>

    <script type="text/javascript">
    </script>
</head>
<body>
<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
<div class="header">
    <div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
        <label id="loginError" class="error">${message}</label>
    </div>
</div>
<h1 class="form-signin-heading" id="titlename"></h1>

<div class="footer">
</div>
</body>
</html>
