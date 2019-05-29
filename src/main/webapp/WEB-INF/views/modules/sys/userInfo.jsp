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
			document.getElementById("divHid").style.display="none";
			
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
				//判断证件类型是不是身份证
				//var type = $("input[name=certificatesType]").val();
				var type = "${fns:getDictLabel(enterpriseWorkers.certificatesType, 'ID_type', '')}";
				if(type == "身份证"){
					document.getElementById("certificatesType").readOnly=true;
					document.getElementById("certificatesNum").readOnly =true;
					document.getElementById("name").readOnly =true;
					document.getElementById("age").disabled =true;
					document.getElementById("sex").disabled =true;
				}else{
					document.getElementById("certificatesType").readOnly =true;
					document.getElementById("certificatesNum").readOnly =true;
					document.getElementById("name").readOnly =true;
				}
				
				//判断职业单位是否为空，如果为空则允许修改，如果不为空则不允许修改
				var companyName = $("input[name='companyName']").val();
// 				if(companyName != null & companyName !="null" & companyName !="" & companyName != "undefined" & companyName != undefined){
// 					//设置只读属性
// 					document.getElementById("btn_search").disabled =true;
// 				}
				var isValid = $("input[name='isValid']").val();
				if(isValid != "0" & isValid !="2" ){
					//设置只读属性
					document.getElementById("btn_search").disabled =true;
				}
			}
		});
		
		function deleteInfo(){//0：登记且无效    2：未shenqig登记可以删除，单位审查之前都可以删除。
			if(window.confirm("您确定删除吗?")){
				var workerId = $("input[name='id']").val();
				var isValid = $("input[name='isValid']").val();
				var roleId = $("input[name='roleId']").val();
				var cardNumber = document.getElementById("certificatesNum").value;
				var name = document.getElementById("name").value;
				name = encodeURI(name);
				if(isValid != "2" && roleId != "92"){
					alert("该登录用户当前状态不允许删除个人信息");
					return false;
				}else{
					var url = "${pageContext.request.contextPath}/enterprise/deleteInfo?cardNumber="+cardNumber+"&name="+name+"&workerId="+workerId;
					$.ajax({
						  type: 'POST',
					      url: url,
					      dataType: 'text',
					      contentType : 'application/text;charset=UTF-8',
					      success: function(data) {
					    	  if(data == "true"){
			                     alert("删除成功");
					    	  }else{
					    		  alert("删除失败");
					    	  }
					      },
					      error:function(){
					    	  alert("ajax请求失败");
					      }  
						});
				}
			}else{
				alert("取消成功");
			}
		}	
		
		function changeMobile(){
			document.getElementById("divHid").style.display="";
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
		    		var name=document.getElementById("name").value;
		    		var certificatesNum=document.getElementById("certificatesNum").value;
		    		var url="${pageContext.request.contextPath}/forgetPwd/checkcodenumInfo?name="+name+"&certificatesNum="+certificatesNum+"&checkcode="+checkcodenum;
		    		
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
		
		//提交按钮绑定时间
		function subfrom() {
			if (document.getElementById("txtcode").value==document.getElementById("checkcode").value){
				$("#inputForm").submit();
			}
			else{
				alert("验证码输入错误，请重新填写。");
			}
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
		
		function reloadImage(){
			$.ajax({
			  type: 'POST',
		      url: '${ctx}/sys/user/getPictureName?workId='+"${enterpriseWorkers.id}",
		      dataType: 'text',
		      contentType : 'application/text;charset=UTF-8',
		      success: function(data) {
		    	
		    	  var imageName = data;
		    	  
		    	  var url = "${ctx}/uploadImage/id2/"+"${enterpriseWorkers.id}"+"?radom="+Math.random();
		    	  //document.getElementById('picture_url').src = url;
		    	  $("#picture_url").remove();
		    	  $("#showImageDiv").append('<img id="picture_url" style="width: 150px;" src="'+url+'"><br/>');
		    	  //$('#picture_url').attr('src', url);
		    	  //$(".attachmentImageOl").empty();
				  //$(".attachmentImageOl").append("<li id='"+"${enterpriseWorkers.id}"+"' > <a href='"+url+"' target='_blank'  >"+imageName+"</a>" +" &nbsp;&nbsp;<a href='javascript:deleteImageId(\""+"${enterpriseWorkers.id}"+"\",this);'  onclick='return confirmx(\"要删除该图片吗？\", this.href)'>×</a> </li>");
				  // ;
		    	  //no_params_test(back);
		      },
		      
		      error:function(){
		    	  alert("ajax请求失败");
		    	  return;
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
		<li><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/infoSave" method="post" enctype="multipart/form-data" class="form-horizontal"><%--
		<form:hidden path="email" htmlEscape="false" maxlength="255" class="input-xlarge"/>
		<sys:ckfinder input="email" type="files" uploadPath="/mytask" selectMultiple="false"/> --%>
		<sys:message content="${message}"/>
		<div id="divHidden">
		<div class="control-group">
			<label class="control-label">一寸照片:</label>
           <div class="controls">
               <input class="attachmentImageIds" id="11" type="hidden"   name="attachmentId" value=""/>
               <ol class="attachmentImageOl" id="aaa">
<%--                		<c:if test="${not empty enterpriseWorkers.pictureUrl }"> --%>
<%--                      	<li id="${enterpriseWorkers.id}"><a href="${ctx}/uploadImage/showImage/id2/${enterpriseWorkers.id}" target='_blank'  >${enterpriseWorkers.pictureName}</a> &nbsp;&nbsp;<a href='javascript:deleteImageId("${enterpriseWorkers.id}",this);'  onclick="return confirmx('要删除该图片吗？', this.href)">×</a> </li> --%>
<%--                     </c:if> --%>
               </ol>
               <div id="showImageDiv">
                <img id="picture_url" style="width: 150px;" src="${ctx}/uploadImage/id2/${enterpriseWorkers.id}"><br/>
               </div>
               <c:if test="${enterpriseWorkers.pictureLock == '0'}">
               		<input type="button" style="width:150px; margin-top: 10px;" class="btn" value="上传一寸照片"  onclick="uploadImage(this)">
           		</c:if>
           </div>
       </div>
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
			<div class="control-group" >
				<label class="control-label">身份证照片:</label>
				<div class="controls">
					<input class="attachmentImageIds"    id="12" type="hidden"   name="attachmentId" value=""/>
					<input class="attachmentType"    type="hidden" name="attachmentId"   value="5"/>
					<input class="attachmentImageTableType"    type="hidden" name="attachmentId"   value="5"/>
					<ol class="attachmentImageOl" id="bbb">
						<c:forEach items="${attachList}" var="attach">
								<li id="${attach.id}" > <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageIds("${attach.id}",this);'><c:if test="${enterpriseWorkers.pictureLock == '0'}">×</c:if></a> </li>
						</c:forEach>
					</ol>
					<c:if test="${enterpriseWorkers.pictureLock == '0'}">
						<input type="button" class="btn" value="添加附件"  onclick="uploads(this)">
					</c:if>

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
				<input typt="text" id="mobile" name="mobile" value="${enterpriseWorkers.mobile}" htmlEscape="false" maxlength="50" onchange="changeMobile()"/>
			</div>
		</div>
		<div id="divHid">
		<div class="control-group">
			<label class="control-label">短信验证码:</label>
			<div class="controls">
				<input  id="txtcode" type="text"  name="cardNumber" htmlEscape="false" maxlength="8" class="required"/>
				<input  type="button"  name="cardNumber" onclick="time(this)" htmlEscape="false" class="required" value="获取验证码"/>
			</div>
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
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="subfrom()"/>
			<input class="btn btn-primary" type="button" value="删除" onclick="deleteInfo()"/>
		</div>
		<input id="checkcode" type="hidden" value=""/>
	</form:form>
</body>
</html>