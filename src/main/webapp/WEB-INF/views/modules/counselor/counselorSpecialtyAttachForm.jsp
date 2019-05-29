<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>基本情况</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function saveConfirm(){
            var a = $("#info").html();//基本
            var d = $("#promise").html();//承诺书
            var b = $("#title").html();//职称
            var c = $("#stady").html();//学历
            if(!$.trim(a)||!$.trim(c)||!$.trim(d)){
                alert("必传附件必须填写")
            }else{
                $("#inputForm").submit();
            }
        }

    </script>
    <script src="${pageContext.request.contextPath}/static/webuploader-0.1.5/image-upload/uploadImage1.js" type="text/javascript"></script>
</head>
<body>
<input id="ctx" type="hidden" value="${ctx}" />
<ul class="nav nav-tabs">
    <li><a href="">变更专业</a></li>
    <li class="active"><a href="">相关附件</a></li>
</ul>
<form:form id="inputForm" modelAttribute="" action="${ctx}/counselor/counselorAttachment/changeSubmit?tableId=${tableId}&recordId=${recordId}&type=3" method="post" class="form-horizontal">
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


       <%-- <tr>
            <td colspan="1" width="10%">
                1
            </td>
            <td colspan="3" width="30%">
                封面《<a href="../../static/变更登记专业封面.doc">封面打印模板.doc</a>》
            </td>
            <td colspan="6" width="60%">
                <div class="control-group" align="left">
                    <div class="controls1">
                        <input class="attachmentImagePid"    type="hidden"   name="attachmentId" value="${personId}"/>
                        <input class="attachmentImageTableId"    type="hidden"   name="attachmentId" value="${tableId}"/>
                        <input class="attachmentImageTableType"    type="hidden"   name="attachmentId" value="${tableType}"/>
                        <input class="attachmentImageIds"    id="11" type="hidden"   name="attachmentId" value=""/>
                        <input class="attachmentImageType"    type="hidden"   value="1"/>
                        <ol class="attachmentImageOl" id="aaa">
                            <c:forEach items="${attachList}" var="attach">
                                <c:if test="${attach.type == '1'}">
                                    <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;
                                        <a href='javascript:' onclick='deleteImageId("${attach.id}",this);'>
                                            <c:if test="${empty look}">×</c:if>
                                        </a>
                                    </li>
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
--%>

        <tr>
            <td colspan="1" width="10%">
                1
            </td>
            <td colspan="3" width="30%">
                承诺书《<a href="../../static/承诺书.docx">承诺书打印模板.doc</a>》(<span style="color: red">必传</span>)
            </td>
            <td colspan="6" width="60%">
                <div class="control-group" align="left">
                    <div class="controls1">
                        <input class="attachmentImagePid"    type="hidden"   name="attachmentId" value="${personId}"/>
                        <input class="attachmentImageTableId"    type="hidden"   name="attachmentId" value="${tableId}"/>
                        <input class="attachmentImageTableType"    type="hidden"   name="attachmentId" value="${tableType}"/>
                        <input class="attachmentImageIds"    id="promiseAttach" type="hidden"   name="attachmentId" value=""/>
                        <input class="attachmentImageType"    type="hidden"   value="2"/>
                        <ol class="attachmentImageOl" id="promise">
                            <c:forEach items="${attachList}" var="attach">
                                <c:if test="${attach.type == '2'}">
                                    <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'><c:if test="${empty look}">×</c:if></a> </li>
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




        <tr>
            <td colspan="1" width="10%">
                2
            </td>
            <td colspan="3" width="30%">
               基本情况表(<span style="color: red">必传</span>)
            </td>
            <td colspan="6" width="60%">
                <div class="control-group" align="left">
                    <div class="controls1">
                        <input class="attachmentImageIds"    id="titleAttach" type="hidden"   name="attachmentId" value=""/>
                        <input class="attachmentImageType"    type="hidden"   value="23"/>
                        <ol class="attachmentImageOl" id="info">
                            <c:forEach items="${attachList}" var="attach">
                                <c:if test="${attach.type == '23'}">
                                    <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank' >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'><c:if test="${empty look}">×</c:if></a> </li>
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

        <tr>
            <td colspan="1" width="10%">
                3
            </td>
            <td colspan="3" width="30%">
                专业培训、学历或学位证书(<span style="color: red">必传</span>)
            </td>
            <td colspan="6" width="60%">
                <div class="control-group" align="left">
                    <div class="controls1">
                        <input class="attachmentImageIds"    id="stadyAttach" type="hidden"   name="attachmentId" value=""/>
                        <input class="attachmentImageType"    type="hidden"   value="24"/>
                        <ol class="attachmentImageOl" id="stady">
                            <c:forEach items="${attachList}" var="attach">
                                <c:if test="${attach.type == '24'}">
                                    <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'><c:if test="${empty look}">×</c:if></a> </li>
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




       <%-- <tr>
            <td colspan="1" width="10%">
                5
            </td>
            <td colspan="3" width="30%">
                项目业绩证明
            </td>
            <td colspan="6" width="60%">
                <div class="control-group" align="left">
                    <div class="controls1">
                        <input class="attachmentImageIds"    id="workAttach" type="hidden"   name="attachmentId" value=""/>
                        <input class="attachmentImageType"    type="hidden"   value="19"/>
                        <ol class="attachmentImageOl" id="work">
                            <c:forEach items="${attachList}" var="attach">
                                <c:if test="${attach.type == '19'}">
                                    <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'><c:if test="${empty look}">×</c:if></a> </li>
                                </c:if>
                            </c:forEach>
                        </ol>
                        <c:if test="${empty look}">
                            <input type="button" class="btn" value="添加附件"  onclick="uploadImage(this)">
                        </c:if>
                    </div>
                </div>
            </td>
        </tr>--%>

        <tr>
            <td colspan="1" width="10%">
                4
            </td>
            <td colspan="3" width="30%">
                职称证明(<span style="color: red"></span>)
            </td>
            <td colspan="6" width="60%">
                <div class="control-group" align="left">
                    <div class="controls1">
                        <input class="attachmentImageIds"    id="retireAttach" type="hidden"   name="attachmentId" value=""/>
                        <input class="attachmentImageType"    type="hidden"   value="6"/>
                        <ol class="attachmentImageOl" id="title">
                            <c:forEach items="${attachList}" var="attach">
                                <c:if test="${attach.type == '6'}">
                                    <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'><c:if test="${empty look}">×</c:if></a> </li>
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


        <tr>
            <td colspan="1" width="10%">
                5
            </td>
            <td colspan="3" width="30%">
                其他附件
            </td>
            <td colspan="6" width="60%">
                <div class="control-group" align="left">
                    <div class="controls1">
                        <input class="attachmentImageIds"    id="otherAttach" type="hidden"   name="attachmentId" value=""/>
                        <input class="attachmentImageType"    type="hidden"   value="12"/>
                        <ol class="attachmentImageOl" id="other">
                            <c:forEach items="${attachList}" var="attach">
                                <c:if test="${attach.type == '12'}">
                                    <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'>
                                        <c:if test="${empty look}">×</c:if>
                                    </a> &nbsp;&nbsp;描述:<input name="remarks" value="${attach.remarks}">
                                        <input type="hidden" value="${attach.id}" name="ids">
                                    </li>
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