<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>咨询师电子章</title>
	<link href="${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet">
	<link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet">
	<link href="${ctxStatic}/modules/cms/front/themes/basic/style.css" type="text/css" rel="stylesheet">
	<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.method.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/webuploader-0.1.5/image-upload/uploadImageElectronicChapter.js" type="text/javascript"></script>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var ievs= IEVersion();
			if(ievs == -1){
				alert("您使用的不是IE浏览器，请使用IE浏览器下载电子证书");
			}else if(ievs == 'edge'){
				alert("您使用的是EDGE浏览器，请使用IE浏览器下载电子证书");
			}			
		});
		function IEVersion() {
            var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
            var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
            var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
            var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
            if(isIE) {
                var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
                reIE.test(userAgent);
                var fIEVersion = parseFloat(RegExp["$1"]);
                if(fIEVersion == 7) {
                    return 7;
                } else if(fIEVersion == 8) {
                    return 8;
                } else if(fIEVersion == 9) {
                    return 9;
                } else if(fIEVersion == 10) {
                    return 10;
                } else {
                    return 6;//IE版本<=7
                }   
            } else if(isEdge) {
                return 'edge';//edge
            } else if(isIE11) {
                return 11; //IE11  
            }else{
                return -1;//不是ie浏览器
            }
        }
		
		
		function applyCfcaExectronicChapter(){
			
			var url = "${ctx}/cfca/electronicChapter/applyElectronicChapter";
			$.ajax({
				  type: 'POST',
			      url: url,
			      dataType: 'text',
			      contentType : 'application/text;charset=UTF-8',
			      success: function(data) {
			    	  if(data == "true"){
	                     alert("申请成功");
			    	  }else{
			    		  alert("申请失败");
			    	  }
			      },
			      error:function(){
			    	  alert("ajax请求失败");
			      }  
				});
		}
		
		function reloadImage(){
			  var url = "${ctx}/uploadImage/idCfcaElectronicChapter/"+"${cfcaElectronicChapter.id}"+"?radom="+Math.random();
	    	  $("#picture_url").remove();
	    	  $("#showImageDiv").append('<img id="picture_url" style="width: 200px;" src="'+url+'"><br/>');
		}
        function download(type){
            location.href='${ctx}/certificate/engineerCertificate/downloadFile?type='+type;
        }
	</script>
</head>
<body>
	<input id="ctx" type="hidden" value="${ctx}" />
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">咨询工程师申请电子证书</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cfcaElectronicChapter" action="${ctx}/sys/user/infoSave" method="post" enctype="multipart/form-data" class="form-horizontal">
		<form:hidden class="id" path="id" value="${cfcaElectronicChapter.id}"/>
		<legend>咨询工程师申请电子证书信息</legend>
		<sys:message content="${message}"/>
		
		<c:choose>
			<c:when test="${ not empty cfcaElectronicChapter.id  }">
			
				<div class="control-group">
					<label class="control-label" style="width: 300px; margin-right: 20px;">电子证书下载地址:</label>

					<div class="controls">
						<%--<a style="font-size:18px; color: #2fa4e7;" href="https://cstest.cfca.com.cn/cgi-bin/userCertDownload/v_input.do?displayAgreement=true" target="_blank" >点击进入下载电子证书页面（测试）</a>
--%>
						<a style="font-size:18px; color: #2fa4e7;" href="https://cs.cfca.com.cn/cgi-bin/userCertDownload/v_input.do?displayAgreement=true" target="_blank">去下载电子证书</a>

					</div>
				</div>
<!-- 				<div class="control-group"> -->
<!-- 				<label class="control-label" style="width: 300px; margin-right: 20px;">上传签章图片:</label> -->
<!-- 		           <div class="controls"> -->
<%-- 		           		<form:hidden path="chapterImage" /> --%>
<!-- 		               <input class="attachmentImageIds" id="11" type="hidden"   name="attachmentId" value=""/> -->
<!-- 		               <ol class="attachmentImageOl" id="aaa"> -->
<!-- 		               </ol> -->
<!-- 		               <div id="showImageDiv"> -->
<%-- 		                <img id="picture_url" style="width: 200px;" src="${ctx}/uploadImage/idCfcaElectronicChapter/${cfcaElectronicChapter.id}"><br/> --%>
<!-- 		               </div> -->
<!-- 		              	<input type="button" style="width:200px; margin-top: 10px;" class="btn" value="上传签章图片"  onclick="uploadImage(this)"> -->
<!-- 		           </div> -->
<!-- 		       </div>   -->
				<div class="control-group">
					<label class="control-label" style="width: 300px;margin-right: 20px;">工具:</label>
					<div class="controls">
						<li class="btns"><input  class="btn btn-primary" type="button" onclick="download('1');" value="浏览器插件"/>
						<input  class="btn btn-primary" type="button" onclick="download('2');" value="证书工具"/>
						<input  class="btn btn-primary" type="button" onclick="download('4');" value="证书工具(指纹)"/>
						</li>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" style="width: 300px;margin-right: 20px;">证书DN:</label>
					<div class="controls">
						
		<%-- 				<form:input path="dn" htmlEscape="false"  maxlength="100" class="input-medium " readonly="true"/> --%>
						${cfcaElectronicChapter.dn}
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" style="width: 300px;margin-right: 20px;">证书序号:</label>
					<div class="controls">				
		<%-- 			<form:input path="sequenceNo" htmlEscape="false"  maxlength="100" class="input-medium " readonly="true"/> --%>
					${cfcaElectronicChapter.sequenceNo}
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" style="width: 300px;margin-right: 20px;">证书序列号（下载证书使用）:</label>
					<div class="controls">
						<form:input path="serialNo" htmlEscape="false"  maxlength="100" class="input-medium " readonly="true"/>
						
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" style="width: 300px; margin-right: 20px;">授权码（下载证书使用）:</label>
					<div class="controls">
					<form:input path="authCode" htmlEscape="false"  class="input-medium " readonly="true"/>
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" style="width: 300px;margin-right: 20px;">开始时间:</label>
					<div class="controls">
						<fmt:formatDate value="${cfcaElectronicChapter.startTime}" pattern="yyyy-MM-dd"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" style="width: 300px;margin-right: 20px;">结束时间:</label>
					<div class="controls">
						<fmt:formatDate value="${cfcaElectronicChapter.endTime}" pattern="yyyy-MM-dd"/>
					</div>
				</div>
			</c:when>
			<c:otherwise>
			<div class="control-group">
					<center>
						<label  style="width: 300px; font-size:18px; color: #2fa4e7;">您暂时没有电子证书!</label>
					</center>
				</div>
			</c:otherwise>
		
		</c:choose>
		<div class="form-actions">
			<input id="btnSubmit1" class="btn btn-primary" type="button" value="返回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
	
	
</body>
</html>