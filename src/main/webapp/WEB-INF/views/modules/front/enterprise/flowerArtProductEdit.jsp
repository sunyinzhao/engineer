<%@ page contentType="text/html;charset=UTF-8"%>

<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
	<title>企业注册</title>
	
<!-- 	<link href="/authorize/static/bootstrap/2.3.1/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet"> -->
<!-- 	<link href="/authorize/static/common/jeesite.min.css" type="text/css" rel="stylesheet"> -->
<!-- 	<link href="/authorize/static/modules/cms/front/themes/basic/style.css" type="text/css" rel="stylesheet"> -->
	
	<script src="/authorize/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<!-- 	<script src="/authorize/static/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script> -->
<!-- 	<script src="/authorize/static/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script> -->
<!-- 	<script src="/authorize/static/modules/cms/front/themes/basic/script.js" type="text/javascript"></script> -->
	

<%-- 	<link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet" /> --%>
<%-- 	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script> --%>
<%-- 	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.method.min.js" type="text/javascript"></script> --%>
	
	
	<link href="${ctxStatic}/webuploader-0.1.5/webuploader.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/webuploader-0.1.5/webuploader.js" type="text/javascript"></script>
	
	
	<script type="text/javascript">
		/* $(document).ready(function() {
			<c:if test="${not empty message}">alert("${message}");</c:if>
			$("#inputForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					content: {required: "请填写留言内容"},
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
		});
		function page(n,s){
			location="${ctx}/guestbook?pageNo="+n+"&pageSize="+s;;
		} */
		
		
		jQuery(function() {
			
			/* var uploader = new WebUploader.Uploader({
			    swf: '${ctxStatic}/webuploader-0.1.5/Uploader.swf'

			    // 其他配置项
			}); */
			

			// 初始化Web Uploader
			var uploader = WebUploader.create({

			    // 选完文件后，是否自动上传。
			    auto: true,

			    // swf文件路径
			    swf: '${ctxStatic}/webuploader-0.1.5/Uploader.swf',

			    // 文件接收服务端。
			    server: 'http://webuploader.duapp.com/server/fileupload.php',

			    // 选择文件的按钮。可选。
			    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
			    pick: '#filePicker',

			    // 只允许选择图片文件。
			    accept: {
			        title: 'Images',
			        extensions: 'gif,jpg,jpeg,bmp,png',
			        mimeTypes: 'image/*'
			    }
			});
			
			
			// 当有文件添加进来的时候
			uploader.on( 'fileQueued', function( file ) {
			    var $li = $(
			            '<div id="' + file.id + '" class="file-item thumbnail">' +
			                '<img>' +
			                '<div class="info">' + file.name + '</div>' +
			            '</div>'
			            ),
			        $img = $li.find('img');
				alert($li);

			    // $list为容器jQuery实例
			    
			    var $list = $("#fileList");
			    $list.append( $li );

			    // 创建缩略图
			    // 如果为非图片文件，可以不用调用此方法。
			    // thumbnailWidth x thumbnailHeight 为 100 x 100
			    uploader.makeThumb( file, function( error, src ) {
			        if ( error ) {
			            $img.replaceWith('<span>不能预览</span>');
			            return;
			        }

			        $img.attr( 'src', src );
			    }, 100, 100 );
			});
			
			// 文件上传过程中创建进度条实时显示。
			uploader.on( 'uploadProgress', function( file, percentage ) {
			    var $li = $( '#'+file.id ),
			        $percent = $li.find('.progress span');

			    // 避免重复创建
			    if ( !$percent.length ) {
			        $percent = $('<p class="progress"><span></span></p>')
			                .appendTo( $li )
			                .find('span');
			    }

			    $percent.css( 'width', percentage * 100 + '%' );
			});

			// 文件上传成功，给item添加成功class, 用样式标记上传成功。
			uploader.on( 'uploadSuccess', function( file ) {
			    $( '#'+file.id ).addClass('upload-state-done');
			});

			// 文件上传失败，显示上传出错。
			uploader.on( 'uploadError', function( file ) {
			    var $li = $( '#'+file.id ),
			        $error = $li.find('div.error');

			    // 避免重复创建
			    if ( !$error.length ) {
			        $error = $('<div class="error"></div>').appendTo( $li );
			    }

			    $error.text('上传失败');
			});

			// 完成上传完了，成功或者失败，先删除进度条。
			uploader.on( 'uploadComplete', function( file ) {
			    $( '#'+file.id ).find('.progress').remove();
			});
		    
		    
		});
	</script>
</head>
<body >
	
	<div id="uploader-demo">
	    <!--用来存放item-->
	    <div id="fileList" class="uploader-list"></div>
	    <div id="filePicker">选择图片</div>
	</div>

		<h4>企业注册信息填写</h4>
<%-- 		 <form:form id="inputForm"  action="${pageContext.request.contextPath}/enterprise" method="post" class="form-horizontal">
		 
		 	
		 	<div class="control-group">
				<label class="control-label">所属地区:</label>
				<div class="controls">
					<select name="company.id" class="input  required" >
						<option value="">请选择</option>
						<c:forEach items="${fns:getSecondOfficeList()}" var="office">
							<option value="${office.id}">${office.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>	
			<div class="control-group">
				<label class="control-label">企业名称:</label>
				<div class="controls">
					<input type="text" name="name" maxlength="11" class="required" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">注册地址:</label>
				<div class="controls">
					<input type="text" name="registeraddress" maxlength="100" class="required" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">通讯地址:</label>
				<div class="controls">
					<input type="text" name="address" maxlength="100" class="required" />
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		 
		<div class="control-group">
			<label class="control-label">身份证号:</label>
			<div class="controls">
				<input  type="text"  name="cardNumber" htmlEscape="false" maxlength="20" class="required"/>
				
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">登录名:</label>
			<div class="controls">
				<input  type="text"  name="loginName" htmlEscape="false" maxlength="50" class="required userName"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="newPassword" name="password" type="password" value="" maxlength="50" minlength="3" class="required"/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
				<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<input type="text"  name="mobile" htmlEscape="false" maxlength="100" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<input type="text"  name="email" htmlEscape="false" maxlength="100" class="email required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">:</label>
			<div class="controls">
				<input type="text"  name="phone" htmlEscape="false" maxlength="100" />
			</div> 
		</div>		
		
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<textarea name="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge" ></textarea>
			</div>
		</div> 
			<div class="control-group">
				<label class="control-label">验证码:</label>
				<div class="controls">
					<sys:validateCode name="validateCode" />
				</div>
			</div>
			<div class="form-actions">
				<input class="btn" type="submit" value="提 交"/>&nbsp;
			</div>
		</form:form> 
 --%>
	</div>
	
	
</body>
</html>