<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<c:set var="ctx" value="${pageContext.request.contextPath}${fns:getAdminPath()}"/>
<html> 
<head> 
<title>PDF_VIEW</title> 
</head> 
<body> 
<iframe src="<c:url value="/static/TrustSignPDF/web/viewer.html" />?file=${ctx}/signaturePDFView/readSignature/id/${id}"width="100%" height="800"></iframe>
</body> 
</html> 