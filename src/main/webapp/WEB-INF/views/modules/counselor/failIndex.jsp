<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学历教育情况</title>
	<meta name="decorator" content="blank"/>
    <script>

    </script>
</head>
<body>
	<ul class="nav nav-tabs">
	</ul><br/>
	<form:form id="inputForm" modelAttribute="personExpert" action="" method="post" class="form-horizontal">
        <legend>未通过内容列表</legend>
    <table class="table-form">
        终审1结论:${fns:getDictLabel(fResult,'decaler_result','')}
        &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        <c:if test="${not empty sResult}">
            终审2结论:${fns:getDictLabel(sResult,'decaler_result','')}
        </c:if>
        <c:choose>
            <c:when test="${empty message}">
                <tr>
                    <td width="5%" style="font-weight: bold">序号</td>
                    <td width="10%" style="font-weight: bold">项目名</td>
                    <td width="20%" style="font-weight: bold">类型</td>
                    <c:choose>
                        <c:when test="${secondFlag != null}">
                            <td width="15%" style="font-weight: bold">状态1</td>
                            <td width="17.5%" style="font-weight: bold">描述1</td>
                            <td width="15%" style="font-weight: bold">状态2</td>
                            <td width="17.5%" style="font-weight: bold">描述2</td>
                        </c:when>
                        <c:otherwise>
                            <td width="25%" style="font-weight: bold">状态</td>
                            <td width="40%" style="font-weight: bold">描述</td>
                        </c:otherwise>
                    </c:choose>

                </tr>
                <c:forEach items="${list}" var="personExpert" varStatus="index">
                    <c:set var="test" value="person_expert${personExpert.type}"/>
                    <tr>
                        <td >
                            ${index.index+1}
                        </td>
                        <td>
                            ${personExpert.examineId}
                        </td>
                        <td>
                            ${personExpert.name}
                        </td>
                        <c:choose>
                            <c:when test="${secondFlag != null}">
                                <td>
                                    <c:set value="${fn:split(personExpert.items,',' ) }" var="items" />
                                    <c:forEach items="${items}" var="item">
                                        ${fns:getDictLabel(item,test ,"" )}<br/>
                                    </c:forEach>
                                </td>
                                <td>
                                        ${personExpert.itemText}
                                </td>
                                <td>
                                    <c:set value="${fn:split(personExpert.secondItems,',' ) }" var="items" />
                                    <c:forEach items="${items}" var="item">
                                        ${fns:getDictLabel(item,test ,"" )}<br/>
                                    </c:forEach>
                                </td>
                                <td>
                                        ${personExpert.secondItemText}
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <c:set value="${fn:split(personExpert.items,',' ) }" var="items" />
                                    <c:forEach items="${items}" var="item">
                                        ${fns:getDictLabel(item,test ,"" )}<br/>
                                    </c:forEach>
                                </td>
                                <td>
                                        ${personExpert.itemText}
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <td align="center">没有不真实或不符合的项</td>
            </c:otherwise>
        </c:choose>
    </table>
	</form:form>



    <c:if test="${educationtblList!=null&&educationtblList.size()>0&&educationtblList!=''}">
        <legend>学历证明</legend>
        <form:form id="inputForm" modelAttribute="" action="" method="post" class="form-horizontal">
            <table class="table-form">
                <tr>
                    <td width="10%" style="font-weight: bold">序号</td>
                    <td width="15%" style="font-weight: bold">项目名</td>
                    <td width="30%" style="font-weight: bold">类型</td>
                    <td width="28%" style="font-weight: bold">状态</td>
                    <td width="17%" style="font-weight: bold">描述</td>
                </tr>
                <c:forEach items="${educationtblList}" var="educationtbl" varStatus="index">
                    <c:set var="test" value="person_expert${educationtbl.type}"/>
                    <tr <c:if test="${educationtbl.items=='1'}">hidden </c:if>>
                        <td>
                            序号:${educationtbl.index}
                        </td>
                        <td>
                                ${educationtbl.examineId}
                        </td>
                        <td>
                                ${educationtbl.name}
                        </td>
                        <td >

                            <c:set value="${fn:split(educationtbl.items,',' ) }" var="items" />
                            <c:forEach items="${items}" var="item">
                                ${fns:getDictLabel(item,test ,"" )}<br/>
                            </c:forEach>
                        </td>
                        <td>
                                ${educationtbl.itemText}
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form:form>
    </c:if>

    <!--用于展示学历证明-->

    <!--用于展示职称证明-->

    <c:if test="${titleList!=null&&titleList.size()>0&&titleList!=''}">
        <legend>职称证书</legend>
        <form:form id="inputForm" modelAttribute="" action="" method="post" class="form-horizontal">
            <table class="table-form">
                <tr>
                    <td width="10%" style="font-weight: bold">序号</td>
                    <td width="13%" style="font-weight: bold">项目名</td>
                    <td width="30%" style="font-weight: bold">类型</td>
                    <td width="30%" style="font-weight: bold">状态</td>
                    <td width="17%" style="font-weight: bold">描述</td>
                </tr>
                <c:forEach items="${titleList}" var="title" varStatus="index">
                    <c:set var="test" value="person_expert${title.type}"/>
                    <tr <c:if test="${title.items=='1'}">hidden </c:if>>
                        <td>
                            序号:${title.index}
                        </td>
                        <td>
                                ${title.examineId}
                        </td>
                        <td>
                                ${title.name}
                        </td>
                        <td >
                            <%--${fns:getDictLabel(title.items,test ,"" )}--%>

                                <c:set value="${fn:split(title.items,',' ) }" var="items" />
                                <c:forEach items="${items}" var="item">
                                    ${fns:getDictLabel(item,test ,"" )}<br/>
                                </c:forEach>
                        </td>
                        <td>
                                ${title.itemText}
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form:form>
    </c:if>

    <!--用于展示培训证书-->
    <c:if test="${specialtyList!=null&&specialtyList.size()>0&&specialtyList!=''}">
        <legend>培训情况</legend>
        <form:form id="inputForm" modelAttribute="" action="" method="post" class="form-horizontal">
            <table class="table-form">
                <tr>
                    <td width="10%" style="font-weight: bold">序号</td>
                    <td width="13%" style="font-weight: bold">项目名</td>
                    <td width="30%" style="font-weight: bold">类型</td>
                    <td width="30%" style="font-weight: bold">状态</td>
                    <td width="17%" style="font-weight: bold"> 描述</td>
                </tr>
                <c:forEach items="${specialtyList}" var="specialty" varStatus="index">
                    <c:set var="test" value="person_expert${specialty.type}"/>
                    <tr <c:if test="${specialty.items=='1'}">hidden </c:if>>
                        <td>
                            序号:${specialty.index}
                        </td>
                        <td>
                                ${specialty.examineId}
                        </td>
                        <td>
                                ${specialty.name}
                        </td>
                        <td >
                                <%--${fns:getDictLabel(specialty.items,test ,"" )}--%>
                                    <c:set value="${fn:split(specialty.items,',' ) }" var="items" />
                                    <c:forEach items="${items}" var="item">
                                        ${fns:getDictLabel(item,test ,"" )}<br/>
                                    </c:forEach>

                        </td>
                        <td>
                                ${specialty.itemText}
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form:form>
    </c:if>
</body>
</html>