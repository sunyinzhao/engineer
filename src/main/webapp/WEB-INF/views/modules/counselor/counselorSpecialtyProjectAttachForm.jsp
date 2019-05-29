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
    <li><a href="">变更专业</a></li>
    <li class="active"><a href="">业绩附件</a></li>
</ul>
<form:form id="inputForm" modelAttribute="" action="${ctx}/counselor/counselorAttachment/projectSubmit?tableId=${tableId}&recordId=${recordId}&type=3" method="post" class="form-horizontal">
    <sys:message content="${message}"/>
    <legend>业绩附件</legend>
    <table class="table-form">
        <tr >
            <td align="center" colspan="10"height="30px">相关附件</td>
        </tr>
        <tr>
            <td colspan="1" width="10%">
                序号
            </td>
            <td colspan="3" width="30%">
                业绩名称
            </td>
            <td colspan="6" width="60%">
                内容
            </td>
        </tr>

        <c:forEach items="${list}" var="map" varStatus="index">
       <tr>
            <td colspan="1" width="10%">
                ${index.index+1}
            </td>
            <td colspan="3" width="30%">
                ${map.name}
            </td>
            <td colspan="6" width="60%">
                <div class="control-group" align="left">
                    <div class="controls1">
                        <input class="attachmentImagePid"    type="hidden"   name="attachmentId" value="${map.personId}"/>
                        <input class="attachmentImageTableId"    type="hidden"   name="attachmentId" value="${map.tableId}"/>
                        <input class="attachmentImageTableType"    type="hidden"   name="attachmentId" value="${map.tableType}"/>
                        <input class="attachmentRemarks" type="hidden" name="attachmentId" value="${map.remarks}"/>
                        <input class="attachmentImageIds"    id="workAttach" type="hidden"   name="attachmentId" value=""/>
                        <input class="attachmentImageType"    type="hidden"   value="19"/>
                        <ol class="attachmentImage${index.index}" id="work${index.index}">
                            <c:forEach items="${map.list}" var="attach">
                                    <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'><c:if test="${empty look}">×</c:if></a> </li>
                            </c:forEach>
                        </ol>
                        <c:if test="${empty look}">
                            <input type="button" class="btn" value="添加附件"  onclick="uploadImage(this,'${index.index}')">
                        </c:if>
                    </div>
                </div>
            </td>
        </tr>
        </c:forEach>

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