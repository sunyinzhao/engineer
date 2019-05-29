<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>执业登记情况表</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet">
	<link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet">
	<link href="${ctxStatic}/modules/cms/front/themes/basic/style.css" type="text/css" rel="stylesheet">

	<style type="text/css">
		.form-horizontal{
			width: 80%;
			margin:0 auto;
			text-align:center;
		}
	</style>
</head>
<body>
<div style="width:750pt; border: 0px solid #999;">
		<form:form id="inputForm" modelAttribute="" action="" method="post" class="form-horizontal">

		<div>
			<span style="color: red"></span>
		</div>
		<table class="table-form" border="1" width="90%" style="margin-top: 10px;">
			<tr>
				<td width="17%" height="30px">姓名：</td>
				<td width="12%">${enterpriseWorker.name }</td>
				<td width="17%">性别：</td>
				<td width="12%">${fns:getDictLabel(enterpriseWorker.sex, 'sex', '')}</td>
				<td width="17%">民族：</td>
				<td width="12%">${fns:getDictLabel(enterpriseWorker.nation, 'sys_nation', '')}</td>
				<td width="23%" rowspan="3"><img id="picture_url" width="150px" src="/engineer/certificateInfo/id/${enterpriseWorker.id}"></td>
			</tr>
			<tr>
				<td height="30px">证件类型：</td>
				<td>${fns:getDictLabel(enterpriseWorker.certificatesType, 'ID_type', '')}</td>
				<td colspan="2">身份证件号：</td>
				<td colspan="2">${fn:substring(enterpriseWorker.certificatesNum, 0, 3)}******${fn:substring(enterpriseWorker.certificatesNum, 15, 18)}</td>
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
				<td colspan="3"><fmt:formatDate value="${enterpriseWorker.validDate}" type="date" pattern="yyyy-MM-dd"/></td>
				<td>公告日期：</td>
				<td colspan="2"><fmt:formatDate value="${enterpriseWorker.aollowDate}" type="date" pattern="yyyy-MM-dd"/></td>
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
					<td colspan="6"><fmt:formatDate value="${list.publicDate}" type="date" pattern="yyyy-MM-dd"/></td>
				</tr>
				<tr>
					<td>登记类型：</td>
					<td colspan="3">${fns:getDictLabel(list.batchType,'counselor_type','')}</td>
					<td>结论：</td>
					<td colspan="2">${fns:getDictLabel(list.batchResult,'final_result','')}</td>
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
		</form:form>
	</div>
</body>
</html>