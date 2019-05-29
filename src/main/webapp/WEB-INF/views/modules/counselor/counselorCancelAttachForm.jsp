<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>基本情况</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function saveConfirm(){
                $("#inputForm").submit();
        }

    </script>
    <script src="${pageContext.request.contextPath}/static/webuploader-0.1.5/image-upload/uploadImage1.js" type="text/javascript"></script>
</head>
<body>
<input id="ctx" type="hidden" value="${ctx}" />
<ul class="nav nav-tabs">
    <li><a href="">注销登记</a></li>
    <li class="active"><a href="">相关附件</a></li>
</ul>
<form:form id="inputForm" modelAttribute="" action="${ctx}/counselor/counselorAttachment/changeSubmit?tableId=${tableId}&recordId=${recordId}&type=4" method="post" class="form-horizontal">
    <sys:message content="${message}"/>
    <legend>相关附件</legend>
    <table class="table-form">
        <tr >
            <td align="center" colspan="10"height="30px">相关附件</td>
        </tr>
        <tr>
            <td colspan="1" width="10%">
                序号
            </td>
            <td colspan="3" width="30%">
                填写类型
            </td>
            <td colspan="6" width="60%">
                内容
            </td>
        </tr>

        <tr>
            <td colspan="1" width="10%">
                1
            </td>
            <td colspan="3" width="30%">
                基本情况表
            </td>
            <td colspan="6" width="60%">
                <div class="control-group" align="left">
                    <div class="controls1">
                        <input class="attachmentImagePid"    type="hidden"   name="attachmentId" value="${personId}"/>
                        <input class="attachmentImageTableId"    type="hidden"   name="attachmentId" value="${tableId}"/>
                        <input class="attachmentImageTableType"    type="hidden"   name="attachmentId" value="${tableType}"/>
                        <input class="attachmentImageIds"    id="promiseAttach" type="hidden"   name="attachmentId" value=""/>
                        <input class="attachmentImageType"    type="hidden"   value="3"/>
                        <ol class="attachmentImageOl" id="promise">
                            <c:forEach items="${attachList}" var="attach">
                                <c:if test="${attach.type == '3'}">
                                    <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;
                                        <a href='javascript:' onclick='deleteImageId("${attach.id}",this);'><c:if test="${empty look}">×</c:if></a> </li>
                                </c:if>
                            </c:forEach>
                        </ol>
                        <c:if test="${empty look}">
                            <input type="button" class="btn" value="添加附件"  onclick="uploadImage(this)">
                        </c:if>
                    </div>
                </div>
            </td>
        </tr>

    </table>
    <br/>
    <div class="form-actions">


        <c:if test="${empty look}">
        <input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveConfirm()" value="确认完成"/>
        </c:if>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>


</body>
</html>