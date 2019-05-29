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
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	    $(document).ready(function() {
	    	
	    	var flag = '${flag}';
	    	if(flag =="1"){
	    		var personRecordId = "$(input[name='id']).val()";
	    		var workerId = "$(input[name='workerId']).val()";
	    		//var batchId = "$(input[name='batchId']).val()";
	    		var batchId = $("#batchId").val();
	    		//关闭本窗口，并跳转到退回方法
	    		//window.location.href = "${ctx}/enterprise/auditAndReport/LocalReturnReport?personRecordId="+personRecordId+"&id="+workerId+"batchId="+batchId;
	    		var url = "${ctx}/enterprise/auditAndReport/LocalReceiveReturnReport?personRecordId="+personRecordId+"&batchId="+batchId;
	    		$.ajax({
	    			type: 'POST',
	  		      	url: url,
	  		      	dataType: 'text',
	  		      	contentType : 'application/text;charset=UTF-8',
	                success:function(data) {
	                    if(data =="200"){
	                        alert("提交成功")
	                        self.opener.location.reload();
	                        //测试父页面进行刷新
	                        window.close();
	                    }
	                },
	  		      error:function(){
			    	  alert("ajax请求失败");
			      } 
	            });
	    	}else if(flag == "0"){
	    		alert("退回失败");
	    	}
	    })
	    
	    function returnReport(){
	    	//var returnReason = "$(textarea[name='localReceiveReturnReason']).val()";
	    	var returnReason = $("#localReceiveReturnReason").val();
	    	if(returnReason == "" || returnReason == null || returnReason == " " || returnReason == "null" || returnReason == undefined){
	    		alert("请填写退回原因");
	    	}else{
	    		$("#searchForm").submit();
	    	}
	    }
	    
	    function closeWindow(){
	    	window.close();
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">地方退回单位上报申请原因</a></li>
	</ul>
	<%-- <sys:message content="${message}"/> --%>
	<form:form id="searchForm" modelAttribute="personRecord" action="${ctx}/enterprise/auditAndReport/savalocalReceiveReturnReason" method="post">
		<div style="text-align:center;">
			<span>请填写退回原因：</span>	
			<input type="hidden" name="id" value="${id }">
			<input type ="hidden" name= "workerId" value = "${workerId }">
			<input type ="hidden" id="batchId" name="batchId" value="${batchId }">
			<br style="left: 0px">
			<br>
			<textarea class="test" id ="localReceiveReturnReason"name = "localReceiveReturnReason" style="width:90%" rows="6">${returnReason }</textarea> 
			<br>
			<input id="returnReason" type="button" value="确定" onclick="returnReport();">
			<input id="returnReason" type="button" value="取消" onclick="closeWindow();">
		</div>
	</form:form>
</body>
</html>