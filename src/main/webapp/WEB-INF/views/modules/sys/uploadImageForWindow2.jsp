<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>



	
<html>
<head>
	<title>上传图片</title>
	<script type="text/javascript">
	var  imagePid= '${imagePid}';
    var  imageType=   '${imageType}';
    var  imageIds= '${imageIds}';
    var  imageOl=   '${imageOl}';
    var workerId = '${workerId}';
	</script>
	<link href="${pageContext.request.contextPath}/static/webuploader-0.1.5/webuploader.css" type="text/css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/static/webuploader-0.1.5/image-upload/style.css" type="text/css" rel="stylesheet" />
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>	
	<script src="${pageContext.request.contextPath}/static/webuploader-0.1.5/webuploader.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/webuploader-0.1.5/image-upload/upload2.js" type="text/javascript"></script>
	
</head>
<body>
<input type="hidden" id="ctx" value="${ctx}">	

	<div id="wrapper">
        <div id="container">
            <!--头部，相册选择和格式选择-->
            <div id="uploader">
                <div class="queueList">
                    <div id="dndArea" class="placeholder">
                        <div id="filePicker"></div>
                        <p>或将照片拖到这里</p>
                    </div>
                </div>
                <div class="statusBar" style="display:none;">
                    <div class="progress">
                        <span class="text">0%</span>
                        <span class="percentage"></span>
                    </div><div class="info"></div>
                    <div class="btns">
                        <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
	
</body>
</html>