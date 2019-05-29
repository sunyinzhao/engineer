<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title></title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript">
        function saveForm(){
            var data = $("#inputForm").serialize();
            ajaxLoad(data)
        }

        function saveForm1(){
            var data = $("#inputForm1").serialize();
            ajaxLoad(data)
        }

        function saveForm2(){
            var data = $("#inputForm2").serialize();
            ajaxLoad(data)
        }

        function saveForm3(){
            var data = $("#inputForm3").serialize();
            ajaxLoad(data)
        }

        function saveForm4(){
            if(confirm("提交前请确认已点击保存.提交后不可再次修改数据")){
                    //提交后,跳转index页面
                location.href='${ctx}/counselor/feedBack/saveUtil?recordId=${personExpert.recordId}';
            }
        }
        //保存后需要进行刷新页面,通过返回success 进行reload

        function ajaxLoad(data){
            var url = '${ctx}/counselor/feedBack/saveFeedBack'
            $.ajax({
                url:url,
                type:"POST",
                cache: false,
                data:data,
                dataType:'json',
                success:function(data) {
                    location.reload()
                }
            })
        }



    </script>
    <script src="${pageContext.request.contextPath}/static/webuploader-0.1.5/image-upload/uploadImage1.js" type="text/javascript"></script>
</head>
<body>
	<ul class="nav nav-tabs">
	</ul><br/>
    <input id="ctx" type="hidden" value="${ctx}" />
	<form:form id="inputForm" modelAttribute="personExpert" action="" method="post" class="form-horizontal">
        <form:hidden path="recordId"/>
        <legend>未通过列表</legend>
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
                    <td width="25%" style="font-weight: bold">类型</td>
                    <td width="23%" style="font-weight: bold">状态</td>
                    <td width="15%" style="font-weight: bold">描述</td>
                    <td width="15%" style="font-weight: bold">回复</td>
                    <td width="20%" style="font-weight: bold">添加图片</td>
                </tr>
                <c:forEach items="${personExpert.expertList}" var="personExpert" varStatus="index">
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
                        <td>
                            <c:set value="${fn:split(personExpert.items,',' ) }" var="items" />
                            <c:forEach items="${items}" var="item">
                                ${fns:getDictLabel(item,test ,"" )}<br/>
                            </c:forEach>
                            <%--${fns:getDictLabel(personExpert.items,test ,"" )}--%>
                        </td>
                        <td>
                            ${personExpert.itemText}
                        </td>

                        <td>
                            <input name="expertList[${index.index}].feedback.id" value="${personExpert.feedback.id}" type="hidden">
                            <input name="expertList[${index.index}].feedback.personRecordId" value="${personExpert.recordId}" type="hidden">
                            <input name="expertList[${index.index}].feedback.expertId" value="${personExpert.id}" type="hidden">
                            <input name="expertList[${index.index}].feedback.backTime" value="${personExpert.feedback.backTime}" type="hidden">

                            <input name="expertList[${index.index}].feedback.backMemo" value="${personExpert.feedback.backMemo}" width="10px">
                                       <%-- ${personExpert.backMemo}--%>
                        </td>
                        <td>
                            <div class="control-group" align="left">
                                <div class="controls1">
                                    <input class="attachmentImagePid"    type="hidden"   name="attachmentId" value="${personId}"/>
                                    <input class="attachmentImageTableId"    type="hidden"   name="attachmentId" value="1"/>
                                    <input class="attachmentImageTableType"    type="hidden"   name="attachmentId" value="1"/>
                                    <input class="attachmentImageIds"    id="workAttach" type="hidden"   name="attachmentId" value=""/>
                                    <input class="attachmentImageType"    type="hidden"   value="1"/>
                                    <input class="attachmentExpertId"    type="hidden"   value="${personExpert.id}"/>
                                    <ol class="attachmentImage${index.index}" id="work${index.index}">
                                        <c:forEach items="${imageList}" var="attach">
                                            <c:if test="${attach.backExpertId==personExpert.id}">
                                            <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'><c:if test="${empty flag}">×</c:if></a> </li>
                                            </c:if>
                                        </c:forEach>
                                    </ol>
                                    <c:if test="${empty flag}">
                                        <input type="button" class="btn" value="添加附件"  onclick="uploadImage(this,'${index.index}')">
                                    </c:if>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <td align="center">没有不真实或不符合的项</td>
            </c:otherwise>
        </c:choose>
    </table>
        <c:if test="${personExpert.expertList!=null&&personExpert.expertList!=''}">
            <c:if test="${empty flag}">
            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveForm()" value="保存"/>
            </c:if>
        </c:if>
	</form:form>



    <c:if test="${educationtblList!=null&&educationtblList!=''&&educationtblList.size()>0}">
        <legend>学历证明</legend>
        <form:form id="inputForm1" modelAttribute="personExpert" action="" method="post" class="form-horizontal">
            <table class="table-form">
                <tr>
                    <td width="10%" style="font-weight: bold">序号</td>
                    <td width="15%" style="font-weight: bold">项目名</td>
                    <td width="30%" style="font-weight: bold">类型</td>
                    <td width="28%" style="font-weight: bold">状态</td>
                    <td width="17%" style="font-weight: bold">描述</td>
                    <td width="17%" style="font-weight: bold">回复</td>
                    <td width="17%" style="font-weight: bold">添加图片</td>
                </tr>
                <c:forEach items="${educationtblList}" var="educationtbl" varStatus="index">
                    <c:set var="test" value="person_expert${educationtbl.type}"/>
                    <c:if test="${educationtbl.items!='1'}">
                    <tr>
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
                                <%--${fns:getDictLabel(educationtbl.items,test ,"" )}--%>
                        </td>
                        <td>
                                ${educationtbl.itemText}
                        </td>
                        <td>
                            <input name="educationtblList[${index.index}].feedback.id" value="${educationtbl.feedback.id}" type="hidden">
                            <input name="educationtblList[${index.index}].feedback.personRecordId" value="${educationtbl.recordId}" type="hidden">
                            <input name="educationtblList[${index.index}].feedback.expertId" value="${educationtbl.id}" type="hidden">
                            <input name="educationtblList[${index.index}].feedback.backTime" value="${educationtbl.feedback.backTime}" type="hidden">
                            <input name="educationtblList[${index.index}].feedback.backMemo" value="${educationtbl.feedback.backMemo}" width="10px">
                                <%-- ${personExpert.backMemo}--%>
                        </td>
                        <td>
                            <div class="control-group" align="left">
                                <div class="controls1">
                                    <input class="attachmentImagePid"    type="hidden"   name="attachmentId" value="${personId}"/>
                                    <input class="attachmentImageTableId"    type="hidden"   name="attachmentId" value="1"/>
                                    <input class="attachmentImageTableType"    type="hidden"   name="attachmentId" value="1"/>
                                    <input class="attachmentImageIds"    id="workAttach" type="hidden"   name="attachmentId" value=""/>
                                    <input class="attachmentImageType"    type="hidden"   value="1"/>
                                    <input class="educationtblExpertId"    type="hidden"   value="${educationtbl.id}"/>
                                    <ol class="educationtbl${index.index}" id="educationtbl${index.index}">
                                        <c:forEach items="${imageList}" var="attach">
                                            <c:if test="${attach.backExpertId==educationtbl.id}">
                                                <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'><c:if test="${empty flag}">×</c:if></a> </li>
                                            </c:if>
                                        </c:forEach>
                                    </ol>
                    <c:if test="${empty flag}">
                                    <input type="button" class="btn" value="添加附件"  onclick="uploadImage(this,'${index.index}','educationtbl')">
                    </c:if>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:if>
                </c:forEach>
            </table>
            <c:if test="${empty flag}">
           <input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveForm1()" value="保存"/>
            </c:if>
        </form:form>
    </c:if>

    <!--用于展示学历证明-->

    <!--用于展示职称证明-->

    <c:if test="${titleList!=null&&titleList!=''&&titleList.size()>0}">
        <legend>职称证书</legend>
        <form:form id="inputForm2" modelAttribute="" action="" method="post" class="form-horizontal">
            <table class="table-form">
                <tr>
                    <td width="10%" style="font-weight: bold">序号</td>
                    <td width="13%" style="font-weight: bold">项目名</td>
                    <td width="30%" style="font-weight: bold">类型</td>
                    <td width="30%" style="font-weight: bold">状态</td>
                    <td width="17%" style="font-weight: bold">描述</td>
                    <td width="17%" style="font-weight: bold">回复</td>
                    <td width="17%" style="font-weight: bold">添加图片</td>
                </tr>
                <c:forEach items="${titleList}" var="title" varStatus="index">
                    <c:set var="test" value="person_expert${title.type}"/>
                    <c:if test="${title.items!='1'}">
                    <tr>
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
                            <c:set value="${fn:split(title.items,',' ) }" var="items" />
                            <c:forEach items="${items}" var="item">
                                ${fns:getDictLabel(item,test ,"" )}<br/>
                            </c:forEach>
                           <%-- ${fns:getDictLabel(title.items,test ,"" )}--%>
                        </td>
                        <td>
                                ${title.itemText}
                        </td>
                        <td>
                            <input name="titleList[${index.index}].feedback.id" value="${title.feedback.id}" type="hidden">
                            <input name="titleList[${index.index}].feedback.personRecordId" value="${title.recordId}" type="hidden">
                            <input name="titleList[${index.index}].feedback.expertId" value="${title.id}" type="hidden">
                            <input name="titleList[${index.index}].feedback.backTime" value="${title.feedback.backTime}" type="hidden">
                            <input name="titleList[${index.index}].feedback.backMemo" value="${title.feedback.backMemo}" width="10px">
                        </td>
                        <td>
                            <div class="control-group" align="left">
                                <div class="controls1">
                                    <input class="attachmentImagePid"    type="hidden"   name="attachmentId" value="${personId}"/>
                                    <input class="attachmentImageTableId"    type="hidden"   name="attachmentId" value="1"/>
                                    <input class="attachmentImageTableType"    type="hidden"   name="attachmentId" value="1"/>
                                    <input class="attachmentImageIds"    id="workAttach" type="hidden"   name="attachmentId" value=""/>
                                    <input class="attachmentImageType"    type="hidden"   value="1"/>
                                    <input class="titleExpertId"    type="hidden"   value="${title.id}"/>
                                    <ol class="title${index.index}" id="title${index.index}">
                                        <c:forEach items="${imageList}" var="attach">
                                            <c:if test="${attach.backExpertId==title.id}">
                                                <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'><c:if test="${empty flag}">×</c:if></a> </li>
                                            </c:if>
                                        </c:forEach>
                                    </ol>
                    <c:if test="${empty flag}">
                                    <input type="button" class="btn" value="添加附件"  onclick="uploadImage(this,'${index.index}','title')">
                    </c:if>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </c:if>
                </c:forEach>
            </table>
            <c:if test="${empty flag}">
            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveForm2()" value="保存"/>
            </c:if>
        </form:form>
    </c:if>

    <!--用于展示培训证书-->
    <c:if test="${specialtyList!=null&&specialtyList!=''&&specialtyList.size()>0}">
        <legend>培训情况</legend>
        <form:form id="inputForm3" modelAttribute="personExpert" action="" method="post" class="form-horizontal">
            <table class="table-form">
                <tr>
                    <td width="10%" style="font-weight: bold">序号</td>
                    <td width="13%" style="font-weight: bold">项目名</td>
                    <td width="30%" style="font-weight: bold">类型</td>
                    <td width="30%" style="font-weight: bold">状态</td>
                    <td width="17%" style="font-weight: bold"> 描述</td>
                    <td width="17%" style="font-weight: bold">回复</td>
                    <td width="17%" style="font-weight: bold">添加图片</td>
                </tr>
                <c:forEach items="${specialtyList}" var="specialty" varStatus="index">
                    <c:set var="test" value="person_expert${specialty.type}"/>
                    <c:if test="${specialty.items!='1'}">
                    <tr>
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
                            <c:set value="${fn:split(specialty.items,',' ) }" var="items" />
                            <c:forEach items="${items}" var="item">
                                ${fns:getDictLabel(item,test ,"" )}<br/>
                            </c:forEach>
                                <%--${fns:getDictLabel(specialty.items,test ,"" )}--%>
                        </td>
                        <td>
                                ${specialty.itemText}
                        </td>
                        <td>
                            <input name="specialtyList[${index.index}].feedback.id" value="${specialty.feedback.id}" type="hidden">
                            <input name="specialtyList[${index.index}].feedback.personRecordId" value="${specialty.recordId}" type="hidden">
                            <input name="specialtyList[${index.index}].feedback.expertId" value="${specialty.id}" type="hidden">
                            <input name="specialtyList[${index.index}].feedback.backTime" value="${specialty.feedback.backTime}" type="hidden">
                            <input name="specialtyList[${index.index}].feedback.backMemo" value="${specialty.feedback.backMemo}" width="10px">
                        </td>

                        <td>
                            <div class="control-group" align="left">
                                <div class="controls1">
                                    <input class="attachmentImagePid"    type="hidden"   name="attachmentId" value="${personId}"/>
                                    <input class="attachmentImageTableId"    type="hidden"   name="attachmentId" value="1"/>
                                    <input class="attachmentImageTableType"    type="hidden"   name="attachmentId" value="1"/>
                                    <input class="attachmentImageIds"    id="workAttach" type="hidden"   name="attachmentId" value=""/>
                                    <input class="attachmentImageType"    type="hidden"   value="1"/>
                                    <input class="specialtyExpertId"    type="hidden"   value="${specialty.id}"/>

                                    <ol class="specialty${index.index}" id="specialty${index.index}">
                                        <c:forEach items="${imageList}" var="attach">
                                            <c:if test="${attach.backExpertId==specialty.id}">
                                                <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'><c:if test="${empty flag}">×</c:if></a> </li>
                                            </c:if>
                                        </c:forEach>
                                    </ol>
                    <c:if test="${empty flag}">
                                    <input type="button" class="btn" value="添加附件"  onclick="uploadImage(this,'${index.index}','specialty')">
                    </c:if>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </c:if>
                </c:forEach>
            </table>
            <c:if test="${empty flag}">
            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveForm3()" value="保存"/>
            </c:if>
        </form:form>
    </c:if>
    <div class="form-actions" align="center">
        <c:if test="${empty kind}">
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </c:if>
    </div>
    <c:if test="${empty flag}">
    <input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveForm4()" value="提交"/>
    </c:if>
</body>
</html>