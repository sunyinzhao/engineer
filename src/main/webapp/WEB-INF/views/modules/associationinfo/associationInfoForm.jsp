<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>协会基本信息管理</title>
	<script src="${pageContext.request.contextPath}/static/webuploader-0.1.5/image-upload/uploadImageAssociation.js" type="text/javascript"></script>
	
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
		
		function reloadImage(){
			
			  var url = "${ctx}/uploadImage/idAssociation/"+"${associationInfo.id}"+"?radom="+Math.random();
	    	  $("#picture_url").remove();
	    	  $("#showImageDiv").append('<img id="picture_url" style="width: 200px;" src="'+url+'"><br/>');
			
			/*  $.ajax({
			  type: 'POST',
		      url: '${ctx}/sys/user/getPictureName?workId='+"${enterpriseWorkers.id}",
		      dataType: 'text',
		      contentType : 'application/text;charset=UTF-8',
		      success: function(data) {
		    	  var imageName = data;
		    	  var url = "${ctx}/uploadImage/id2/"+"${enterpriseWorkers.id}"+"?radom="+Math.random();
		    	  $("#picture_url").remove();
		    	  $("#showImageDiv").append('<img id="picture_url" style="width: 150px;" src="'+url+'"><br/>');
		      },
		      error:function(){
		    	  alert("ajax请求失败");
		    	  return;
		      }
			});  */
		}
		
	</script>
</head>
<body>
<input id="ctx" type="hidden" value="${ctx}" />
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/associationinfo/associationInfo/">协会基本信息列表</a></li>
		<li class="active"><a href="${ctx}/associationinfo/associationInfo/form?id=${associationInfo.id}">协会基本信息<shiro:hasPermission name="associationinfo:associationInfo:edit">${not empty associationInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="associationinfo:associationInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="associationInfo" action="${ctx}/associationinfo/associationInfo/save" method="post" class="form-horizontal">
		<form:hidden class="id" path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">协会名称：</label>
			<div class="controls">
				<form:input path="associationName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组织机构代码：</label>
			<div class="controls">
				<form:input path="orgNum" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属地区：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${associationInfo.office.id}" labelName="office.name" labelValue="${associationInfo.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">签章图片:</label>
           <div class="controls">
           		<form:hidden path="sealPicUrl" />
               <input class="attachmentImageIds" id="11" type="hidden"   name="attachmentId" value=""/>
               <ol class="attachmentImageOl" id="aaa">
               </ol>
               <div id="showImageDiv">
                <img id="picture_url" style="width: 200px;" src="${ctx}/uploadImage/idAssociation/${associationInfo.id}"><br/>
               </div>
              	<input type="button" style="width:200px; margin-top: 10px;" class="btn" value="上传签章图片"  onclick="uploadImage(this)">
           </div>
       </div>
		
		<div class="form-actions">
			<shiro:hasPermission name="associationinfo:associationInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>