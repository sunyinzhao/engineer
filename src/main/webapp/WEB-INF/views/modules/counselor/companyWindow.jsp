<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学历教育情况</title>
	<meta name="decorator" content="blank"/>
    <script>
       /* function searchName(obj){
            $("#inputForm").submit();
        }*/
    </script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/counselor/apply/search" method="post" class="form-horizontal">
        <li><label>查询：</label>
             <form:input path="name"/>
            <input type="submit" class="btn btn-small btn-primary" value="查询">
        </li>
        <table id="contentTable" class="table table-striped table-bordered table-condensed">
            <tr>
                <td align="center">
                    选择
                </td>
                <td>
                    公司名字
                </td>
            </tr>
            <c:forEach items="${list}" var="user">
                <tr>
                    <td>
                        <input type="radio" name="radioName" style="text-align:center;padding:10px 20px;width:100px;" value="${user.name},${user.recordNumber},${user.id},${user.officeId}" >
                    </td>
                <td>

                    <input name="userName" value="${user.name}" type="hidden">
                    ${user.name}
                </td>
                </tr>
            </c:forEach>
        </table>
	</form:form>


</body>
</html>