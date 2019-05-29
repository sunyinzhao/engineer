<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>退回原因</title>
	<meta name="decorator" content="blank"/>
    <script>
       /* function searchName(obj){
            $("#inputForm").submit();
        }*/
    </script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="" action="${ctx}/counselor/apply/search" method="post" class="form-horizontal">
        <li><label>退回原因：${result}</label>
        </li>
        <c:if test="${hgList!=null&&hgList.size()>0}">
            <table class="table-form">
                <tr>
                    <td>序号</td>
                    <td>类型</td>
                    <td>描述</td>
                </tr>
        <c:forEach items="${hgList}" var="compliance" varStatus="index">
            <tr>
                <td>${index.index+1}</td>
                <td>${compliance.name}</td>
                <td>${compliance.remarks}</td>
            </tr>
        </c:forEach>
            </table>
        </c:if>
	</form:form>
</body>
</html>