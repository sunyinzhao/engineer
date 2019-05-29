<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>执业登记情况表</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet">
	<link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet">
	<link href="${ctxStatic}/modules/cms/front/themes/basic/style.css" type="text/css" rel="stylesheet">
	<script src="${ctxStatic}/jquery-print/jQuery.print.js" type="text/javascript"></script>
	<style type="text/css">
		.form-horizontal{
			width: 80%;
			margin:0 auto;
			text-align:center;
		}
	</style>
	<script type="text/javascript">
	
		$(document).ready(function() {


            var url="${ctx}/certificate/engineerCertificate/engineerCertificateAvailable?workerId="+"${enterpriseWorker.id}";
            $.ajax({
                type: 'POST',
                url: url,
                dataType: 'text',
                contentType : 'application/text;charset=UTF-8',
                success: function(data) {
                    if(data == 'Y'){
                        document.getElementById("certificatelook").type="button";
                    }else{
                        document.getElementById("certificatelook").type="hidden";
                    }
                }
            });



		});
		//打印
	    function jqp(){
	        //测试:打印之前去掉整个页面的样式
	       // $("#id").remove("")
	        jQuery('#printDiv').print(
	            {
	                //Use Global styles
	                globalStyles : true
	                ,mediaPrint : true
					//stylesheet : "http://fonts.googleapis.com/css?family=Inconsolata",
	                ,iframe : true
	                ,noPrintSelector : ".avoid-this"
	                ,prepend : "Hello World!!!<br/>"
	                ,append : "<br/>Buh Bye!"
	                ,deferred: $.Deferred().done(function() { console.log('Printing done', arguments); })
	
	
	            }
	        );
	    }
        function printInfo(){

            // location.href='${ctx}/counselor/info/infoWindow';
            var url ='${ctx}/signaturePDFView/viewEngineerSignature?id=${enterpriseWorker.id}';

            window.open(url, "about:blank");
        }
        
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">咨询工程师（投资）执业登记情况表</a></li>
	</ul><br/>
	<div style="width:750pt; border: 0px solid #999;">
	<div id="printDiv" style="width: 700pt;">
	<form:form id="inputForm" modelAttribute="" action="" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div>
			<span style="color: red"></span>
		</div>
		<table class="table-form" border="1" width="90%">
			<tr>
				<td width="17%" height="30px">姓名：</td>
				<td width="12%">${enterpriseWorker.name }</td>
				<td width="17%">性别：</td>
				<td width="12%">${fns:getDictLabel(enterpriseWorker.sex, 'sex', '')}</td>
				<td width="17%">民族：</td>
				<td width="12%">${fns:getDictLabel(enterpriseWorker.nation, 'sys_nation', '')}</td>
				<td width="23%" rowspan="3"><img id="picture_url" width="150px" src="${ctx}/uploadImage/id2/${enterpriseWorker.id}"></td>
			</tr>
			<tr>
				<td height="30px">证件类型：</td>
				<td>${fns:getDictLabel(enterpriseWorker.certificatesType, 'ID_type', '')}</td>
				<td colspan="2">身份证件号：</td>
				<td colspan="2">${enterpriseWorker.certificatesNum }</td>
			</tr>
			<tr>
				<td height="30px">职业资格证书取得年份：</td>
				<td>${enterpriseWorker.getyear }</td>
				<td colspan="2">职业资格证书编号：</td>
				<td colspan="2">${enterpriseWorker.professioncardNum }</td>
			</tr>
			<tr>
				<td colspan="5" align="center">现执业登记情况：</td>
				<td rowspan="2">有效性验证</td>
				<td rowspan="2">有效</td>
			</tr>
			<tr>
				<td>执业登记状况：</td>
				<td colspan="4">${fns:getDictLabel(enterpriseWorker.isValid,'isValid','')}</td>
			</tr>
			<tr>
				<td>执业单位：</td>
				<c:if test="${empty companyName}">
					<td colspan="3">${enterpriseWorker.companyName }</td>
				</c:if>
				<c:if test="${not empty companyName}">
					<td colspan="3">${companyName }</td>
				</c:if>
				<!-- <td>备案编号：</td> -->
				<%-- <td colspan="2">${enterpriseWorker.recordNumber }</td> --%>
				<td>预审单位：</td>
				<td colspan="2">${enterpriseWorker.officeName }</td>
			</tr>
			<tr>
				<td>工作单位：</td>
				<c:if test="${empty companyName}">
					<td colspan="3">${enterpriseWorker.companyName }</td>
				</c:if>
				<c:if test="${not empty companyName}">
					<td colspan="3">${companyName }</td>
				</c:if>
				<td>登记证书编号：</td>
				<td colspan="2">${enterpriseWorker.registerCertificateNum }</td>
			</tr>
			<tr>
				<td>主专业：</td>
				<td colspan="3">${fns:getDictLabel(enterpriseWorker.registerMainSpecialty, 'specialty_type', '')}</td>
				<td>辅专业：</td>
				<td colspan="2">${fns:getDictLabel(enterpriseWorker.registerAuxiliarySpecialty, 'specialty_type', '')}</td>
			</tr>
			<tr>
				<td>有效期至：</td>
				<td colspan="3"><fmt:formatDate value="${enterpriseWorker.validDate}" type="date" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>公告日期：</td>
				<td colspan="2"><fmt:formatDate value="${enterpriseWorker.aollowDate}" type="date" pattern="yyyy-MM-dd HH:mm"/></td>
			</tr>
			<tr>
				<td colspan="7" align="center">正在申请登记情况</td>
			</tr>
			<tr>
				<td colspan="7" align="center">待添加......</td>
			</tr>
			<tr>
				<td colspan="7" align="center">历年执业登记记录</td>
			</tr>
			
			<c:forEach items="${personRcordeList}" var="list" varStatus="index">
				<tr>
					<td>${index.index+1 }-公告日期：</td>
					<td colspan="6"><fmt:formatDate value="${list.publicDate}" type="date" pattern="yyyy-MM-dd HH:mm"/></td>
				</tr>
				<tr>
					<td>登记类型：</td>
					<td colspan="3">${fns:getDictLabel(list.declareType,'counselor_type','')}</td>
					<td>结论：</td>
					<td colspan="2">${fns:getDictLabel(list.fdeclareResult,'final_result','')}</td>
				</tr>
				<tr>
					<td>执业单位：</td>
					<td colspan="3">${list.companyName}</td>
					<td>地区：</td>
					<td colspan="2">${list.officeName}</td>
				</tr>
				<tr>
					<td>主专业：</td>
					<td colspan="3">${fns:getDictLabel(list.registerMainSpecialty, 'specialty_type', '')}</td>
					<td>辅专业：</td>
					<td colspan="2">${fns:getDictLabel(list.registerAuxiliarySpecialty, 'specialty_type', '')}</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		</div>
		<div class="form-actions">
			<input type="hidden" id="certificatelook" class="btn btn-primary" value="登记证书预览" onclick="printInfo()">
			<input id="jqprint" style="text-align: right;" class="btn" type="button" value="打 印" onclick="jqp()"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>