<%--
  Created by IntelliJ IDEA.
  User: 84249
  Date: 2018/8/14
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title></title>
    <meta name="decorator" content="blank"/>
    <style type="text/css">
        body{
           /* background:url(${pageContext.request.contextPath}/static/images/back.jpg) fixed center center no-repeat;
            background-size: cover;
            width: 100%;
            height: 100%;*/
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


        // 只能单选
        //注销 以及 初次 只能单选
        function onlyCheck(id){
            //点击单选之后,其余所有的全部remove
            //1. 如果点的是初次
            if(id=='first'){
                $("#cancel").removeAttr("checked");
            }else{
                $("#first").removeAttr("checked");
            }
            $("#apply").removeAttr("checked");
            $("#specialty").removeAttr("checked");
            $("#continue").removeAttr("checked");
            //var flag = $('#'+id).is(':checked');
            //alert(flag)
        }
        //可以复选
        //其余多个可以复选
        function manyCheck(){
            //需要把注销以及初次去掉
            $("#first").removeAttr("checked");
            $("#cancel").removeAttr("checked");
        }


        function submitCheck(){
            $.confirm({
                title: '不予登记情况确认',
                content: '<div style="text-align:left">1.不具有完全民事行为能力&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>2.年龄在70周岁及以上<br>3.同时在两个及以上工程咨询单位执业,被注销登记不满3年<br>' +
                '4.列入发展改革部门工程咨询不良记录或"黑名单",被注销登记不满3年<br>' +
                '5.在咨询工程师(投资)执业档案中有不良记录或被列入黑名单,被注销登记不满3年<br>' +
                '6.不接受执业检查或执业检查不合格,被注销登记不满1年&nbsp;<br>' +
                '7.在工程咨询工作中有重大过失,收到行政处罚或撤职以上行政处分不满3年<br>' +
                '8.通过不正当手段取得登记,被注销登记不满3年<br>' +
                '9.受到刑事处罚,自刑事处罚执行完毕之日至申请登记之日不满5年<br>' +
                '10.咨询工程师(投资)在登记申请中弄虚作假的,当年不予登记且3年内不得再次申请登记<br></div>',
                type: 'green',
                icon: 'glyphicon glyphicon-question-sign',
                buttons: {
                    ok: {
                        text: '确认不存在以上情况',
                        btnClass: 'btn-primary',
                        action: function() {
                            submitCheck1()
                        }
                    },
                    cancel: {
                        text: '取消',
                        btnClass: 'btn-primary'
                    }
                }
            });


        }

        //提交使用ajax进行提交, 因为涉及到 判断事件
        function submitCheck1(){
            var values="";
            if ($("#isfreeze").val()=="1")
            {
            	alert("您处于冻结状态，不允许创建登记事项。");
            	return;
            }
            var continueEducation = submitGetEducation();
            if(continueEducation==203){
                return;
            }
           var types=  document.getElementsByName("declareType");
            for(var i = 0;i<types.length;i++){
                if(types[i].checked==true){
                    var value = types[i].value;
                    if(value=='1'){
                        values+="初始登记、"
                    }else if(value=='2'){
                        values+="变更执业单位、"
                    }else if(value=='3'){
                        values+="变更专业、";
                    }else if(value=='4'){
                        values+="继续登记、"
                    }else if(value=='0'){
                        values+="注销登记、"
                    }
                }
            }
            var values =values.substr(0,values.length-1);

           //校验规则  04-15之前 初始登记先查询temp_photo有没有上一年的记录 如果有记录  直接通过  如果没有记录 再查询学时是否满足
            //继续登记直接查询证件有效期以内的三年是否满足全部满足
            if(values.indexOf("初始登记")>-1){
                var date = new Date();
                var month = date.getMonth()+1;
                if(Number(month)<4){//小于4月份
                    if(submitGetTempPhoto(date.getFullYear()) ==0){
                        if (continueEducation == 0) {
                            alert("初始登记学时不满足");
                            return;
                        }
                    }
                }else if(Number(month)==4){
                    var day = date.getDate();
                    if (Number(day) < 15) {//四月十五号之前
                        if(submitGetTempPhoto(date.getFullYear()) ==0){
                            if (continueEducation == 0) {
                                alert("初始登记学时不满足");
                                return;
                            }
                        }
                    } else {//四月十五号之后
                        if (continueEducation == 0) {
                            alert("初始登记学时不满足");
                            return;
                        }
                    }
                }else{//大于4月份
                    if (continueEducation == 0) {
                        alert("初始登记学时不满足");
                        return;
                    }
                }
            }else if(values.indexOf("继续登记")>-1){
                //alert(continueEducation);
                if(continueEducation!=3){
                    alert("继续登记学时不满足");
                    return;
                }
            }

            //分两种情况,当选择初始登记的时候,不需要弹窗,当第一个初始登记选中的时候,直接进行提交
            if(types[0].checked==true){
                submitForm();
                //submitGetEducation();

            }else{
            if(confirm(values+" 同一批次只能提交一组申请，请确认是否满足")){
                submitForm();
            }
            }
        }
        //获取当前用户的上一年考试结果
        function submitGetTempPhoto(year){
            var id = "${enterpriseWorkers.id}";
            var count = 0;
            $.ajax({
                type:'POST',
                url: '${ctx}/counselor/submitGetTempPhoto?id='+id+"&year="+year,
                dataType: 'text',
                contentType : 'application/text;charset=UTF-8',
                async:false,
                success: function(data) {
                    count = data;
                },
                error:function (result) {
                    alert("ajax请求错误");
                }
            });
            return count;
        }

        //获取当前用户以往的学时合格年数
        function submitGetEducation(){
            var id = "${enterpriseWorkers.id}";
            var state = 203;
            $.ajax({
               type:'POST',
                url: '${ctx}/counselor/submitGetEducation?id='+id,
                dataType: 'text',
                contentType : 'application/text;charset=UTF-8',
                async:false,
                success: function(data) {
                    if(data==203){
                        alert("系统出现错误，请联系管理员");
                    }else{
                      state=data;
                    }
                },
                error:function (result) {
                    alert("ajax请求错误");
                }
            });
            return state;
        }


        function submitForm(){


            var data = $("#checkForm").serialize();
            // alert(data)
            $.ajax({
                type: 'POST',
                url: '${ctx}/counselor/submitCheck?'+data,
                /*data:data,*/
                dataType: 'text',
                contentType : 'application/text;charset=UTF-8',
                success: function(data) {
                    if(data=='200'){
                        alert("申请成功")
                        window.location.assign("${ctx}/counselor/list")
                    }else if(data=='203'){
                        alert("请填选类型")
                    }else if(data=='202'){
                        // window.location.assign("${ctx}/expert/pending1?flag=2")
                        alert("已存在未完成申请单")
                    }else if(data=='203'){
                        alert("用户不存在")
                    }else if(data=='204'){
                        alert("超过70岁不允许初始登记")
                    }else if(data=='205'){
                        alert("继续登记只能在公告时间后 2年7个月 至 2年10个月 之间进行申请")
                    }
                }
            });
        }


    </script>
    <link href="${pageContext.request.contextPath}/static/jquery-confirm/jquery-confirm.css" rel="stylesheet" media="screen">
    <script src="${pageContext.request.contextPath}/static/jquery-confirm/jquery-confirm.js" type="text/javascript"></script>

</head>
<body>
<legend>选择登记类型</legend>
<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
<!-- <div class="header"> -->
<%--     <div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button> --%>
<%--         <label id="loginError" class="error">${message}</label> --%>
<!--     </div> -->
<!-- </div> -->
<h1 class="form-signin-heading" id="titlename"></h1>
<form id="checkForm" class="form-signin" action="" method="post">
<input type="hidden" id="isfreeze" value="${enterpriseWorkers.isFreeze}" />
<c:if test="${enterpriseWorkers.isValid!='1'}">
	<div class="group" style="border:1px solid #ddd;padding-top:5px;padding-bottom:5px">
    <input type="checkbox" id="first" name="declareType" checked="checked" value="1" onclick="onlyCheck(this.id)"/>
    <label class="input-label">初始登记</label>
    </div>
</c:if>
    <br/>
    <c:if test="${enterpriseWorkers.isValid=='1'}">
    <div class="group" style="border:1px solid #ddd;padding-top:5px;padding-bottom:5px">
    <input type="checkbox" id="apply" name="declareType"  value="2" onclick="manyCheck()"/>
    <label class="input-label">变更执业单位申请</label>
    
    <br/>
    <input type="checkbox" id="specialty" name="declareType"  value="3" onclick="manyCheck()"/>
    <label class="input-label">变更专业申请</label>
    <br/>
    <input type="checkbox" id="continue" name="declareType"  value="4" onclick="manyCheck()"/>
    <label class="input-label">继续登记</label>
    </div>
    <br/>
    <div class="group" style="border:1px solid #ddd;padding-top:5px;padding-bottom:5px">
    <input type="checkbox" id="cancel" name="declareType"  value="0" onclick="onlyCheck(this.id)"/>
    <label class="input-label">注销登记</label>
    </div>
    <br/>
    </c:if>
	<br/>
    <input class="btn btn-large btn-primary" type="button" value="确&nbsp;&nbsp;&nbsp;定" onclick="submitCheck()"/>

</div>
</form>
<div class="footer" style="text-align:left;padding-left:20%;width:70%;color:red">
</br>
1. 已申请的登记事项公布前不能新增其他登记申请事项，请在提交前确定已全部勾选拟申请登记事项。</br></br>
2. 继续登记办理周期为两个月，变更专业办理周期为三个月，变更执业单位办理周期为一个月，如同时申请继续登记、变更专业、变更执业单位将一起公布结果。</br></br>
3. 登记且无效、未申请登记的咨询师只能申请初始登记。
</div>
</body>
</html>
