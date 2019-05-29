<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>基本情况</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        function saveConfirm(){
            //进行判定. 必选必填\ 需要增加isValid 进行判断,如果为0,则定义a有值
            var isValid = $("#isValid").val();
            if(isValid=="2"){
                //置为有值.
                var a = "1";
            }else{
                var a = $("#title").html();
            }

            var b = $("#idCard").html();//身份证
            var c = $("#professor").html();//职称
            var d = $("#education").html();
            var e = $("#info").html();//基本情况
            var f = $("#work").html();//劳动合同
            var g = $("#retire").html();//退休
            var h = $("#stady").html();//继续教育证明
            var l = $("#promise").html();//承诺书


            if(!$.trim(b)||!$.trim(d)||!$.trim(e)||!$.trim(f)||!$.trim(g)||!$.trim(l)){
                alert("必传附件必须填写")
            }else{
                $("#inputForm").submit();
            }
        }

        function saveAttach(){
                var personId = $("#personid").val();
                var tableId = $("#tableId").val();
                var recordId = $("#recordId").val();
                $("#inputForm").attr("action","${ctx}/counselor/counselorAttachment/saveAttach?personId=${personId}&tableId=${tableId}&recordId=${recordId}&type=1")
                $("#inputForm").submit();
        }

	</script>
    <script src="${pageContext.request.contextPath}/static/webuploader-0.1.5/image-upload/uploadImage1.js" type="text/javascript"></script>
</head>
<body>
<input id="ctx" type="hidden" value="${ctx}" />
<ul class="nav nav-tabs">
    <li><a href="">初始登记列表</a></li>
    <li class="active"><a href="">相关附件</a></li>
    <input type="hidden" id="personId" value="${personId}">
    <input type="hidden" id="recordId" value="${recordId}"/>
    <input type="hidden" id="tableId" value="${tableId}"/>
    <input type="hidden" id="isValid" value="${isValid}"/>
</ul>
	<form:form id="inputForm" modelAttribute="" action="${ctx}/counselor/counselorAttachment/changeSubmit?tableId=${tableId}&recordId=${recordId}&type=1" method="post" class="form-horizontal">
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

            <%--<tr>
                <td colspan="1" width="10%">
                    1
                </td>
                <td colspan="3" width="30%">
                    封面《<a href="../../static/初始登记封面.docx">封面打印模板.doc</a>》
                </td>
                <td colspan="6" width="60%" align="left">
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
                                            </a> </li>
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
                            <input class="attachmentImageIds"    id="infoAttach" type="hidden"   name="attachmentId" value=""/>
                            <input class="attachmentImageType"    type="hidden"   value="3"/>
                            <ol class="attachmentImageOl" id="info">
                                <c:forEach items="${attachList}" var="attach">
                                    <c:if test="${attach.type == '3'}">
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



            <!--申请过但失效的，无需上传职业资格证书,isValid == -->
            <c:if test="${isValid!='0'}">
            <tr>
                <td colspan="1" width="10%">
                    3
                </td>
                <td colspan="3" width="30%">
                    职业资格证书
                </td>
                <td colspan="6" width="60%">
                    <div class="control-group" align="left">
                        <div class="controls1">
                            <input class="attachmentImageIds"    id="titleAttach" type="hidden"   name="attachmentId" value=""/>
                            <input class="attachmentImageType"    type="hidden"   value="4"/>
                            <ol class="attachmentImageOl" id="title">
                                <c:forEach items="${attachList}" var="attach">
                                    <c:if test="${attach.type == '4'}">
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
            </c:if>

            <tr>
                <td colspan="1" width="10%">
                    4
                </td>
                <td colspan="3" width="30%">
                    身份证(<span style="color: red">必传</span>)
                </td>
                <td colspan="6" width="60%">
                    <div class="control-group" align="left">
                        <div class="controls1">
                            <input class="attachmentImageIds"    id="idCardAttach" type="hidden"   name="attachmentId" value=""/>
                            <input class="attachmentImageType"    type="hidden"   value="5"/>
                            <ol class="attachmentImageOl" id="idCard">
                                <c:forEach items="${attachList}" var="attach">
                                    <c:if test="${attach.type == '5'}">
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
                    职称证书
                </td>
                <td colspan="6" width="60%">
                    <div class="control-group" align="left">
                        <div class="controls1">
                            <input class="attachmentImageIds"    id="professorAttach" type="hidden"   name="attachmentId" value=""/>
                            <input class="attachmentImageType"    type="hidden"   value="6"/>
                            <ol class="attachmentImageOl" id="professor">
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
                   6
                </td>
                <td colspan="3" width="30%">
                    学历学位证(<span style="color: red">必传</span>)
                </td>
                <td colspan="6" width="60%">
                    <div class="control-group" align="left">
                        <div class="controls1">
                            <input class="attachmentImageIds"    id="educationAttach" type="hidden"   name="attachmentId" value=""/>
                            <input class="attachmentImageType"    type="hidden"   value="7"/>
                            <ol class="attachmentImageOl" id="education">
                                <c:forEach items="${attachList}" var="attach">
                                    <c:if test="${attach.type == '7'}">
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
                    7
                </td>
                <td colspan="3" width="30%">
                    培训证书
                </td>
                <td colspan="6" width="60%">
                    <div class="control-group" align="left">
                        <div class="controls1">
                            <input class="attachmentImageIds"    id="trainAttach" type="hidden"   name="attachmentId" value=""/>
                            <input class="attachmentImageType"    type="hidden"   value="13"/>
                            <ol class="attachmentImageOl" id="train">
                                <c:forEach items="${attachList}" var="attach">
                                    <c:if test="${attach.type == '13'}">
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
                    8
                </td>
                <td colspan="3" width="30%">
                    劳动(聘用)合同(<span style="color: red">必传</span>)
                </td>
                <td colspan="6" width="60%">
                    <div class="control-group" align="left">
                        <div class="controls1">
                            <input class="attachmentImageIds"    id="workAttach" type="hidden"   name="attachmentId" value=""/>
                            <input class="attachmentImageType"    type="hidden"   value="8"/>
                            <ol class="attachmentImageOl" id="work">
                                <c:forEach items="${attachList}" var="attach">
                                    <c:if test="${attach.type == '8'}">
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
                    9
                </td>
                <td colspan="3" width="30%">
                    养老保险证明或退休证(退休人员使用)(<span style="color: red">必传，未采纳社会养老保险的事业单位提供人事证明时，还应同时提供事业单位法人证书</span>)
                </td>
                <td colspan="6" width="60%">
                    <div class="control-group" align="left">
                        <div class="controls1">
                            <input class="attachmentImageIds"    id="retireAttach" type="hidden"   name="attachmentId" value=""/>
                            <input class="attachmentImageType"    type="hidden"   value="9"/>
                            <ol class="attachmentImageOl" id="retire">
                                <c:forEach items="${attachList}" var="attach">
                                    <c:if test="${attach.type == '9'}">
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
                    10
                </td>
                <td colspan="3" width="30%">
                    继续教育证明
                </td>
                <td colspan="6" width="60%">
                    <div class="control-group" align="left">
                        <div class="controls1">
                            <input class="attachmentImageIds"    id="stadyAttach" type="hidden"   name="attachmentId" value=""/>
                            <input class="attachmentImageType"    type="hidden"   value="10"/>
                            <ol class="attachmentImageOl" id="stady">
                                <c:forEach items="${attachList}" var="attach">
                                    <c:if test="${attach.type == '10'}">
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
                    11
                </td>
                <td colspan="3" width="30%">
                    采用登记规程第五条提供的材料（法人证书、工作单位同意执业的证明及工作单位未备案的截图）
                </td>
                <td colspan="6" width="60%">
                    <div class="control-group" align="left">
                        <div class="controls1">
                            <input class="attachmentImageIds"    id="shiyeAttach" type="hidden"   name="attachmentId" value=""/>
                            <input class="attachmentImageType"    type="hidden"   value="11"/>
                            <ol class="attachmentImageOl" id="shiye">
                                <c:forEach items="${attachList}" var="attach">
                                    <c:if test="${attach.type == '11'}">
                                        <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'><c:if test="${empty look}">×</c:if></a>
                                            &nbsp;&nbsp;描述:<input name="remarks" value="${attach.remarks}">
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

            <tr>
                <td colspan="1" width="10%">
                    12
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
                <input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveAttach()" value="保存"/>
            </c:if>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>


</body>
</html>