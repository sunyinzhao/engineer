<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>显示图片</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">		
	</script>
</head>
<body>
	<img src="${ctx}/uploadImage/id3/${id}" id="npcImg" />
</body>
</html>