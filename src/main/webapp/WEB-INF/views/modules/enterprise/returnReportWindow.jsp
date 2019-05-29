<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位名称</title>
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
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	    $(document).ready(function() {
	    	$("#searchForm").validate({
                rules: {
                	returnReason:{required:true}
                },
                messages: {
                	returnReason:{
						required:"必填"
					}
                },
                submitHandler: function(form){
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
	    	
	    	/* var flag = ${flag};
	    	if(flag =="1"){
	    		var personRecordId = $("input[name='id']").val();
	    		var workerId = $("input[name='workerId']").val();
	    		//关闭本窗口，并跳转到退回方法
	    		window.location.href = "${ctx}/enterprise/auditAndReport/LocalReturnReport?personRecordId="+personRecordId+"&id="+workerId;
	    	}else if(flag == "0"){
	    		alert("退回失败");
	    	} */
	    })
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">地方退回单位上报申请原因</a></li>
	</ul>
	<sys:message content="${message}"/>
	<%-- <form:form id="searchForm" modelAttribute="personRecord" action="${ctx}/enterprise/auditAndReport/savaReturnReason" method="post"> --%>
	<form:form id="searchForm" modelAttribute="personRecord" action = "${ctx}/enterprise/auditAndReport/LocalReturnReport" method = "post">
		<div style="text-align:center;">
			<span>请填写退回原因：</span>	
			<input type="hidden" name="id" value="${id }">
			<input type = "hidden" name= "workerId" value = "${workerId }">
			<br style="left: 0px">
			<br>
			<textarea class="test" name = "returnReason" style="width:90%" rows="6">${returnReason }</textarea> 
			<br>
			<input id="returnReason" type="submit" value="确定">
			<input id="returnReason" type="button" value="取消" onclick="history.go(-1)">
		</div>
	</form:form>
</body>
</html>