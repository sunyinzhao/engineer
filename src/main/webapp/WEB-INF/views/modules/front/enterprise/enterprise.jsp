<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>咨询工程师（投资）注册</title>
	
	<link href="${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet">
	<link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet">
	<link href="${ctxStatic}/modules/cms/front/themes/basic/style.css" type="text/css" rel="stylesheet">
	<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/modules/cms/front/themes/basic/script.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.method.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/webuploader-0.1.5/webuploader.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/webuploader-0.1.5/webuploader.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-jbox/2.3/Skins/Default/jbox.css"/>
	<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
	<style>
	*{padding:0px;margin:0px;}
	.pop {  display: none;  width: 850px; min-height: 470px;  max-height: 750px;  height:570px;  position: absolute;  top: 0;  left: 0;  bottom: 0;  right: 0;  margin: auto;  padding: 25px;  z-index: 130;  border-radius: 8px;  background-color: #fff;  box-shadow: 0 3px 18px rgba(100, 0, 0, .5);  }
	.pop-top{  height:40px;  width:100%;  border-bottom: 1px #E5E5E5 solid;  }
	.pop-top h2{  float: left;  display:black}
	.pop-top span{  float: right;  cursor: pointer;  font-weight: bold; display:black}
	.pop-foot{  height:50px;  line-height:50px;  width:100%;  border-top: 1px #E5E5E5 solid;  text-align: right;  }
	.pop-cancel, .pop-ok {  padding:8px 15px;  margin:15px 5px;  border: none;  border-radius: 5px;  background-color: #337AB7;  color: #fff;  cursor:pointer;  }
	.pop-cancel {  background-color: #FFF;  border:1px #CECECE solid;  color: #000;  }
	.pop-content{  height: 480px;  }
	.pop-content-left{  float: left;  }
	.pop-content-right{  width:710px;  float: left;  padding-top:20px;  padding-left:20px;  font-size: 16px;  line-height:35px;  }
	.bgPop{  display: none;  position: absolute;  z-index: 129;  left: 0;  top: 0;  width: 100%;  height: 100%;  background: rgba(0,0,0,.2);  }
</style>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			var success = '${success}';
			if(success == "0"){
				alert("注册成功");
			}
			
			//初始化,隐藏复选框下的信息
			document.getElementById("divHide").style.display="none";
			$("#btn_checkbox").attr("disabled","disabled");//初始化页面时，"已确认登记状态正确"按钮设为不可选
			
            <c:if test="${not empty message}">
				alert("${message}");
            	window.close();
            </c:if>
            $("#inputForm").validate({
                rules: {
                    validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"},
                    loginName: {remote: "${pageContext.request.contextPath}/enterprise/checkLoginName"},
                    orgCode: {remote: "${pageContext.request.contextPath}/enterprise/checkOrganizationCode"},
                    mobile:{required:true,
                    		remote:{
                    			type:"POST",
                    			url:"${pageContext.request.contextPath}/enterprise/checkMobile",
                    		data:{
                    			mobile : function() {
                    				return $("#mobile").val();
                    				        },
         				     	id : function() {
         				        	return $("#id").val();
         				        	        },
                    		}}
                    },
                    name:{required:true,
                		remote:{
                			type:"POST",
                			url:"${pageContext.request.contextPath}/enterprise/checkNameEqualDBName",
                		data:{
                			certificatesNum : function() {
                				return $("#certificatesNum").val();
                				       },
     				     	name : function() {
     				        	return $("#name").val();
     				        	       },
                		}}
               		 },
                    certificatesNum: {required:true,certificatesNum:true,
                    	remote:{
                    		type:"POST",
                    		url:"${pageContext.request.contextPath}/enterprise/checkCertificatesNum",
                    		data:{
                    			certificatesNum:function(){
                    				return $("#certificatesNum").val();
                    			}
                    		}}},
                    nation:{required:true}
                },
                messages: {
                	certificatesNum:{
						required:"必填",
						remote:jQuery.format("身份证号已注册")
					},
					nation:{
						required:"必填"
					},
                    validateCode: {remote: "验证码不正确"},
                    loginName: {remote: "登录名已存在"},
                    confirmNewPassword: {equalTo: "输入与上面相同的密码"},
                    orgCode:{remote:"统一社会信用代码已存在"},
                    mobile:{required:"必填",remote:jQuery.format("手机号码已注册")},
                    name:{required:"必填",remote:jQuery.format("身份证号与姓名不匹配，请联系010-88337625")}
                },
                submitHandler: function(form){
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                	$("#allowchk").removeAttr("checked");
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
            
            jQuery.validator.addMethod("certificatesNum", function(value, element) {
            	var certificatesType = document.getElementById("type");
				var index=certificatesType.selectedIndex;
				var type=certificatesType[index].value;
				if(type == '1'){//身份证
					// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 
					var reg = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9X]$)/; 
                	return this.optional(element) || (reg.test(value));
				}else{
					return true;
				}
            }, "身份证号不合法");

            $("#btnsubmit").bind("click", function () {
            	$("#allowchk").removeAttr("checked");
    			var orgCodeid = $("#orgCodeid").val();
    			var recordNumberid = $("#recordNumberid").val();
                document.getElementById("btnsubmit").disabled=true;//add by gaoyongjian   防止重复提交
                document.getElementById("type").disabled=false;
                $("#inputForm").submit();
            });
            // 手机号码验证
            jQuery.validator.addMethod("mobile", function(value, element) {
                var tel = /(^1[3,4,5,7,8,9]\d{9}$)/g;
                return this.optional(element) || (tel.test(value));
            }, "手机为:13,14,15,17,18,19号段，例如：13888888888");

            // 电话号码验证
            jQuery.validator.addMethod("phone", function(value, element) {
                var tel =  /\d{2,5}-\d{7,8}$/g;
                return this.optional(element) || (tel.test(value));
            }, "格式为:固话为区号(3-4位)号码(7-8位),例如：010-88888888");

            // 电话号码验证
            jQuery.validator.addMethod("validateImage", function(value, element) {

                var maxsize = 2*1024*1024;//2M
                var errMsg = "上传的附件文件不能超过2M！！！";
                var tipMsg = "您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过2M，建议使用IE、FireFox、Chrome浏览器。";
                var  browserCfg = {};
                var ua = navigator.userAgent;

                if (ua.indexOf("MSIE")>=1){
                    browserCfg.ie = true;
                }else if(ua.indexOf("Firefox")>=1){
                    browserCfg.firefox = true;
                }else if(ua.indexOf("Chrome")>=1){
                    browserCfg.chrome = true;
                }
                try{
                    var obj_file = document.getElementById("licenseFile");
                    if(obj_file.value==""){
                       // alert("请先选择上传文件");
                        return false;
                    }
                    var filesize = 0;
                    if(browserCfg.firefox || browserCfg.chrome ){
                        filesize = obj_file.files[0].size;
                    }else if(browserCfg.ie){
                        var obj_img = document.getElementById('licenseFile');
                       // alert('浏览器判断2' + obj_file.value);
                        obj_img.src =obj_file.value;
                        filesize = obj_img.fileSize;
                        var fso,f,fname,fsize;
                        fso=new ActiveXObject("Scripting.FileSystemObject");
                        f=fso.GetFile(obj_file.value);//文件的物理路径
                        filesize=f.Size; //文件大小（bit）
                    }else{
                       // alert("非IE 谷歌" + tipMsg);
                        return true;
                    }
                    if(filesize>maxsize){
                        return false;
                    }
                }catch(e){
                   return true;
                }

                return true;
            }, "营业执照必填并且不得大于2MB");
            
            //控制注册须知显示隐藏
            $('.pop-close').click(function () {
                $('.bgPop,.pop').hide();
            });
            $('.click_pop').click(function () {
            	$('.bgPop').css({"height":$(document).height()})
                $('.bgPop,.pop').show();
            });
        });
		function page(n,s){
			location="${ctx}/guestbook?pageNo="+n+"&pageSize="+s;;
		}
		//checkbox同意点击事件
		function checkAllow(){
			if(document.getElementById("allowchk").checked==true){
				document.getElementById("btnsubmit").disabled=false;
			}else{
				document.getElementById("btnsubmit").disabled=true;
			}
		}
		
		/*已确认登记状态正确 复选框 以下的内容隐藏。  */
		function registerStatus(){
			if($('#btn_checkbox').is(':checked')){
				document.getElementById("divHide").style.display="";
				$("#test input").attr("readonly","readonly");
				document.getElementById("type").disabled = true;
			 }else{
				 document.getElementById("divHide").style.display="none";
				 $("#test input").removeAttr("readonly")
				 document.getElementById("type").disabled = false;
			 }
		}
		
		/* 根据证件号查询该用户数据，如果有则将数据中的已有的数据赋值到相应位置 */
		function inputOnBlur(value,name){
			var v = value.replace(/\s+/g, "");
			$("input[name='"+name+"']").val(v);
			var certificatesNum= $("input[name='certificatesNum']").val();
			if(certificatesNum == " " || certificatesNum == null || certificatesNum =="null" || certificatesNum =="" || certificatesNum =="undefined" || certificatesNum =="undefined"){
				alert("请先填写您的证件号");
				return false;
			}
			/* window.location.href= "${pageContext.request.contextPath}/enterprise/findByCertificatesNum?name="+value+"&certificatesNum="+certificatesNum; */
			value = encodeURI(value); 
			var url="${pageContext.request.contextPath}/enterprise/findByCertificatesNum?name="+value+"&certificatesNum="+certificatesNum;
			$.ajax({
			  type: 'POST',
		      url: url,
		      dataType: 'json',
		      contentType : 'application/json;charset=UTF-8',
		      success: function(data) {
		    	if(data != null){
		    	//赋值
		    	$("input[name=mobile]").val(data.mobile);
		    	$("input[name=email]").val(data.email);
		    	$("input[name=companyName]").val(data.companyName);
		    	$("input[name=id]").val(data.id);
		    	$("input[name=pid]").val(data.pid);
		    	$("input[name=officeId]").val(data.officeId);
		    	//下拉框默认选中
		    	
		    	$("#type").find("option").each(function(){  
		            if($(this).val()==data.certificatesType){  
		                $(this).attr("selected","selected");  
		            }  
		        });
		    	
		    	$("#nation").find("option").each(function(){  
		            if($(this).val()==data.nation){  
		                $(this).attr("selected","selected");  
		            }  
		        });
		    	
		    	/* $("select option").each(function(){  
		            if($(this).val()==data.nation){  
		                $(this).attr("selected","selected");  
		            }  
		        });  */ 
		    	
		    	//获取isValid，0时为登录且无效，1登录且有效，2未申请登录
					var isValid = data.isValid;
					if(isValid == "0"){
						document.getElementById("status").value = "登记过但无效";
						document.getElementById('hint').innerText="*(如确认状态不正确，请与010-88337625联系。)";
						$("#test input").attr("readonly", "readonly");
						/* $("#divHidden input").attr("readonly", "readonly"); */
						$("#btn_checkbox").attr("disabled","disabled");
						$("#allowchk").removeAttr("disabled");
						/* $("#btnsubmit").removeAttr("disabled"); */
						$("#btn_checkbox").removeAttr("disabled");
						$("#nation").attr("disabled","disabled");
					}
				//登录且有效，所有input框不可修改
					if(isValid == "1"){
						document.getElementById("status").value = "登记且有效"; 
						document.getElementById('hint').innerText="*(如确认状态不正确，请与010-88337625联系。)";
						$("#test input").attr("readonly", "readonly");
						/* $("#divHidden input").attr("readonly", "readonly"); */
						document.getElementById("search").disabled = true;
						$("#allowchk").removeAttr("disabled");
						$("#btn_checkbox").removeAttr("disabled");
						$("#nation").attr("disabled","disabled");
					}

					//判断是否注册，如果已注册，"已确认登记状态复选框"不可选
					var isRegister = data.isRegister;
					$("input[name=isRegister]").val(isRegister);
					if(isRegister == "1"){//已注册
						$("#btn_checkbox").attr("disabled","disabled");
						document.getElementById("status").value = "已注册"; 
						document.getElementById('hint').innerText="*(如确认状态不正确，请与010-88337625联系。)";
					}
				
					if(isRegister != "1" && isValid == "2"){
						document.getElementById("status").value = "未申请登记"; 
						document.getElementById('hint').innerText="*(如确认状态不正确，请与010-88337625联系。)";
						$("#btn_checkbox").removeAttr("disabled");
					}
				
		    	}else{
					document.getElementById("status").value = "未申请登记";
					document.getElementById('hint').innerText="*(如确认状态不正确，请与010-88337625联系。)";
					$("#btn_checkbox").removeAttr("disabled");
		    	 }
		      },
		      error:function(){
		    	  alert("ajax请求失败");
		    	  $("#btn_checkbox").attr("disabled","disabled");
		      }  
			});
		}
		
		var id = "";//单位id存放
		var officeId="";//存放地区id
			/* 用户点击查询按钮，弹出一个页面窗口 */
			function companyNameWindows(){
				// 正常打开	
				top.$.jBox.open("iframe:${pageContext.request.contextPath}/enterprise/companyNameWidow","单位名称", 500, 500, {
					ajaxData:{selectIds: $("#${id}Id").val()},
					buttons:{"确定":"ok","清除单位":"clear","关闭":true}
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
						if(v=="clear"){
							$("input[name='pid']").val("");
							$("input[name='officeId']").val("");
							$("#companyName").val("");
						}
					}, loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					}
				});
			}
		
		function replaceNull(value,name){
			if(name == "certificatesNum"){
				var certificatesType = document.getElementById("type");
				var index=certificatesType.selectedIndex;
				var type=certificatesType[index].value;
				var result = "";
				if(type == '1'){//身份证
					var end = value.substring(value.length-1,value.length);//最后一位
					if(end == 'X'){//将全角改为半角
					for(var i=0;i<value.length;i++){
					   var cCode = value.charCodeAt(i);
					   cCode = (cCode>=0xFF01 && cCode<=0xFF5E)?(cCode - 65248) : cCode;
					   cCode = (cCode==0x03000)?0x0020:cCode;
					   result += String.fromCharCode(cCode);
						}
					 value = result;
					}else if(end == 'x'){
						value = value.substring(0,value.length-1)+'X';
					}else{
						for(var i=0;i<value.length;i++){
						   var cCode = value.charCodeAt(i);
						   cCode = (cCode>=0xFF01 && cCode<=0xFF5E)?(cCode - 65248) : cCode;
						   cCode = (cCode==0x03000)?0x0020:cCode;
						   result += String.fromCharCode(cCode);
						}
						value = result;
					}
				}
			}
			var v = value.replace(/\s+/g, "");
			$("input[name='"+name+"']").val(v);
		}
	</script>
</head>
<body >
	<!--遮罩层-->
<div class="bgPop"></div>
<!--弹出框-->
<div class="pop">
    <div class="pop-top">
        <h2>需知</h2>
        <span class="pop-close">Ｘ</span>
    </div>
    <div class="pop-content">
        <div class="pop-content-left">
            <img src="" alt="" class="teathumb">
        </div>
        <div class="pop-content-right">
            <p>一、<b class="lname">遵守中华人民共和国法律法令和其他相关法规，不可发布破坏宪法和法律、法规的信息。</b></p>
            <p>二、<b class="price">遵守公安部关于《计算机信息网络国际联网安全保护管理办法》的规定，自觉维护计算机信息网络的安全。</b></p>
            <p>三、<b class="ltime">不得提交/上传封建迷信、淫秽、色情、暴力、赌博等不健康信息。</b></p>
            <p>四、<b class="teacher">企业或个人填写的信息要真实有效、准确完整，并不得有引人误解或者虚假的陈述。</b></p>
            <p>五、<b class="teasynopsis">申请人必须填写详细、真实信息。因信息填写不真实、不完整或填写错误将影响后续审核进程。 </b></p>
            <p>六、<b class="synopsis">用户注册成功后，请妥善保管您的用户名和密码。</b></p>
        </div>
    </div>
    <div class="pop-foot">
        <input type="button" value="关闭" class="pop-cancel pop-close">
    </div>
</div>
	<div style="margin-left:auto;margin-right:auto;  margin-top:50px;margin-bottom:50px; width: 900px; ">
		<%-- <img alt="" src="${pageContext.request.contextPath}/static/images/flowone.png" > --%>
		<sys:message content="${message}"/>
		<h4>咨询工程师注册</h4>
		 <form:form id="inputForm" action="${pageContext.request.contextPath}/enterprise/register" method="post" enctype="multipart/form-data" class="form-horizontal">
		 	
		 	<div class="control-group">
				<label class="control-label">证件类型:</label>
				<div class="controls">
					<%-- <input type="hidden" name="certificatesType" value= "${enterpriseWork.certificatesType }"> --%>
					<select id="type" name="certificatesType" class="input  required" >
						<c:forEach items="${fns:getDictList('ID_type')}" var="item">
							<option value="${item.value}" label="${item.label}">
						</c:forEach>
					</select>
					<span class="help-inline"><font color="red">*（必填）</font> </span>
				</div>
			</div>
			<div id="test">
				<div class="control-group">
					<label class="control-label">证件号:</label>
					<div class="controls">
						<input type="text" id="certificatesNum" name="certificatesNum" value="${enterpriseWork.certificatesNum }" maxlength="18" class="required" onblur="replaceNull(this.value,this.name)"/>
						<span class="help-inline"><font color="red">*（必填）</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">姓名:</label>
					<div class="controls">
						<input type="text" id="name" name="name" value="${enterpriseWork.name }" maxlength="20" class="required" onblur="inputOnBlur(this.value,this.name)"/>
						<span class="help-inline"><font color="red">*（必填）</font> </span>
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<input type="hidden" name ="isRegister" value="${enterpriseWork.isRegister }">
					<%-- <input type="hidden" name ="isValid" value="${enterpriseWork.isValid }"> --%>
					<input type="hidden" id="id" name = "id" value="${enterpriseWork.id }">
					<input type="text" id="status" name="isValid" readonly="readonly" style="color:#ff0000">
					<span id="hint" style="color: red"></span>
					<br>
					<input type= "checkbox" id="btn_checkbox" onclick="registerStatus()"><span>已确认登记状态正确</span>
				</div>
			</div>
			<div id ="divHide">
			<!-- <div id="divHidden"> -->
			<div class="control-group">
				<label class="control-label">民族：</label>
				<div class="controls">
					<select id="nation" name="nation" class="input  required" >
						<c:forEach items="${fns:getDictList('sys_nation')}" var="item">
							<option value="${item.value}" label="${item.label}">
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">手机:</label>
				<div class="controls">
					<input type="text" id="mobile" name="mobile" value="${enterpriseWork.mobile }" htmlEscape="false" maxlength="11" class="required mobile" onblur="replaceNull(this.value,this.name)"/>
					<!-- <span class="help-inline"><font color="red">*请输入有效的手机号码</font> </span> -->
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">邮箱:</label>
				<div class="controls">
					<input type="text"  name="email" value="${enterpriseWork.email }" htmlEscape="false" maxlength="100" class="email required" onblur="replaceNull(this.value,this.name)"/>
	                <!-- <span class="help-inline"><font color="red">*请输入有效的邮箱格式</font> </span> -->
				</div>
			</div>
			 <div class="control-group">
				 <label class="control-label">单位名称:</label>
				 <div class="controls">
				 	 <input type="hidden" name="pid" value="">
				 	 <input type="hidden" name="officeId" value="">
					 <input type="text" id="companyName" readonly="readonly" name="companyName" value = "${enterpriseWork.companyName }" onblur="replaceNull(this.value,this.name)"/>
					 <input type="button" id="search" name="search" value ="查找" onclick="companyNameWindows()" style="height: 30px;width: 80px"/>
				 </div>
			 </div>
			<!-- </div> -->
		<div class="control-group">
			<label class="control-label">登录系统用户名:</label>
			<div class="controls">
				<input  type="text"  name="loginName" value = "${enterpriseWork.loginName}" htmlEscape="false" maxlength="30" minlength="6" class="required userName" onblur="replaceNull(this.value,this.name)"/>
				<span class="help-inline"><font color="red">*（请勿加空格，建议用名称拼音或英文缩写）</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="newPassword" name="password" type="password" value="" maxlength="30" minlength="6" class="required" onblur="replaceNull(this.value,this.name)"/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*只能输入数学、字母或其组合，位数限定6~12位</font> </span></c:if>
				<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="30" minlength="6" equalTo="#newPassword" onblur="replaceNull(this.value,this.name)"/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*确认密码</font> </span></c:if>
			</div>
		</div>	
			<div class="control-group">
				<div class="controls">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="allowchk" onclick="checkAllow()"/>同意 <a style="color:blue;" href="javascript:void(0)" class="click_pop">注册须知</a>
					<span class="help-inline"><font color="red">*状态是未申请登记的用户，需要单位审核通过后才能填写登记事项。</font> </span>
				</div>
			</div>
			<div class="form-actions">
				<input id="btnsubmit" class="btn" type="button" value="提 交" disabled="true" />&nbsp;
				<input type="hidden" name = "token" value="${token}">
			</div>
		</div>
		</form:form> 
	</div>
</body>
</html>