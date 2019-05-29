<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>高校数据bianji</title>
    <meta name="decorator" content="blank"/>
    <%--<script src="${pageContext.request.contextPath}/static/webuploader-0.1.5/image-upload/uploadImageEduAndTitle.js" type="text/javascript"></script>--%>

    <script type="text/javascript">
        // $(document).ready(function() {
        //     $("#inputForm").validate({
        //         submitHandler: function(form){
        //             form.submit();
        //         },
        //         errorContainer: "#messageBox",
        //         errorPlacement: function(error, element) {
        //             $("#messageBox").text("输入有误，请先更正。");
        //             if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
        //                 error.appendTo(element.parent().parent());
        //             } else {
        //                 error.insertAfter(element);
        //             }
        //         }
        //     });
        // });

        // function addRow(list, idx, tpl, row){
        //     //验证是否已经存在ID ,不存在 需有先保存，再进行添加附件功能
        //     // var id = $("#id").val()
        //     // if (id == "" || id == null){
        //     //     confirmx('添加附件请先保存本页面数据？', "javascript:saveBaseInfo()");
        //     // }else {
        //         $(list).append(Mustache.render(tpl, {
        //             idx: idx, delBtn: true, row: row
        //         }));
        //         $(list+idx).find("select").each(function(){
        //             $(this).val($(this).attr("data-value"));
        //         });
        //         $(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
        //             var ss = $(this).attr("data-value").split(',');
        //             for (var i=0; i<ss.length; i++){
        //                 if($(this).val() == ss[i]){
        //                     $(this).attr("checked","checked");
        //                 }
        //             }
        //         });
        //     // }
        // }
        //
        // function saveBaseInfo(){
        //     $("#saveType").val("1");
        //     $("#stage").val("1");
        //     $("#inputForm").submit();
        // }
        //
        // function delRow(obj, prefix){
        //     var id = $(prefix+"_id");
        //     var delFlag = $(prefix+"_delFlag");
        //     if (id.val() == ""){
        //         $(obj).parent().parent().remove();
        //     }else if(delFlag.val() == "0"){
        //         delFlag.val("1");
        //         $(obj).html("&divide;").attr("title", "撤销删除");
        //         $(obj).parent().parent().addClass("error");
        //     }else if(delFlag.val() == "1"){
        //         delFlag.val("0");
        //         $(obj).html("&times;").attr("title", "删除");
        //         $(obj).parent().parent().removeClass("error");
        //     }
        // }

        function checkschoolCode(){
            // ajax 异步验证学校代码不能重复
            var schoolCode 	= $("#schoolCode").val();

            var url="${ctx}/counselor/schoolData/checkschoolCode?schoolCode="+schoolCode;
            $.ajax({
                type: 'POST',
                url: url,
                dataType: 'text',
                contentType : 'application/text;charset=UTF-8',
                success: function(data) {
                    if(data == 'yes'){
                        alert("您已存在相同的学校代码，请核对后再试。");
                        $("#schoolCode").val("");
                    }else{
                        // loading('正在提交，请稍等...');
                        // $("#inputForm").submit();
                        return true;
                    }
                },
                error:function(){
                    alert("ajax请求失败");
                }
            });
        }

        function saveBaseInfo(id){
            var schoolCode = $("#schoolCode").val();
            var schoolName = $("#schoolName").val();
            // alert(schoolCode+"-←--→-"+schoolName);
            if(schoolCode == "" || schoolCode == null || schoolCode == undefined){
                alert("请输入学校代码。");
                return;
            }
            if(schoolName == null || schoolName == "" || schoolName == undefined){
                alert("请输入学校名称。");
                return;
            }
            $("#inputForm").attr("action","${ctx}/counselor/schoolData/save?id="+id);
            $("#inputForm").submit();
        }
    </script>
</head>
<body>
    <input id="ctx" type="hidden" value="${ctx}" />
    <ul class="nav nav-tabs">
        <li><a href="${ctx}/counselor/schoolData/list">高校数据列表</a></li>
        <li class="active"><a href="${ctx}/counselor/schoolData/edit?id=${educationSchoolInfo.id}">高校数据${not empty educationSchoolInfo.id?'编辑':'录入'}</a></li>
    </ul>
    <%--action="${ctx}/counselor/self/saveEducation?type=${type}"--%>
    <form:form id="inputForm" modelAttribute="educationSchoolInfo" action="${ctx}/counselor/schoolData/save?id=${educationSchoolInfo.id}" method="post" class="form-horizontal">
        <sys:message content="${message}"/>


        <table id="contentTable" class="table table-striped table-bordered table-condensed">
            <%--<div class="control-group"  align="left">--%>
                    <%--<span  style="font-size: 22px;align-content: center">学历教育情况</span>--%>
            <%--</div>--%>
            <%--<form:input path="personId" type="hidden" />--%>
            <%--<form:input path="id" type="hidden"/>--%>
            <legend>高校信息管理</legend>
                        <%--办学类型 [   ]--%>
                        <div class="control-group">
                            <label class="control-label">学校代码：</label>
                            <div class="controls">
                                <form:input path="schoolCode" maxlength="5"  cssStyle="width: 240px" id="schoolCode" onblur="checkschoolCode()" />
                                <span style="color: red">（不能为空）</span>
                            </div>
                        </div>
                        <%--毕业院校 [   ]--%>
                        <div class="control-group">
                            <label class="control-label">学校名称：</label>
                            <div class="controls">
                                <form:input path="schoolName" maxlength="50"  cssStyle="width: 240px" id="schoolName" />
                                <span style="color: red">（不能为空）</span>
                            </div>
                        </div>
                        <%--学习方式 [   ]--%>
                        <div class="control-group">
                            <label class="control-label">办学类型代码：</label>
                            <div class="controls">
                                <%--<form:select path="educationType" class="input-medium " cssStyle="width: 252px">--%>
                                    <%--<form:option value="" label=""/>--%>
                                    <%--<form:options items="${fns:getDictList('study_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
                                <%--</form:select>--%>

                                <c:set value="${fn:split(educationSchoolInfo.educationType,',' ) }" var="educationType" />  <%--得到选中的值--%>

                                <form:select path="educationType" class="input-medium required"  multiple="true" cssStyle="width: 254px">

                                    <%-- <c:forEach items="${fns:getDictList('declare_category')}" var="item" >  --%>
                                    <c:forEach items="${fns:getDictList('edu_type')}" var="item" > <%-- 遍历下拉框的所有值--%>
                                        <c:set value="0" var="selected"/>   <%--是否选中的默认值1为未选中--%>

                                        <c:forEach items="${educationType}" var="cat"  > <%--判断某一个值是否选中，选中则将变量selected设置为“0”--%>
                                            <c:if test="${item.value eq  cat}">
                                                <c:set value="1" var="selected"/>   <%--是否选中的默认值1为未选中--%>
                                            </c:if>
                                        </c:forEach>

                                        <c:choose>
                                            <c:when test="${selected eq '1' }">
                                                <form:option value="${item.value}" selected="selected" label="${item.label}"/>  <%--标签选中--%>
                                            </c:when>
                                            <c:otherwise>
                                                <form:option value="${item.value}" label="${item.label}"/>  <%--标签不选中--%>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </form:select>

                                <span style="color: red">&nbsp;&nbsp;普通高等教育&nbsp;1 ；成人高等教育&nbsp;5 ；高等教育自学考试和高等教育学历文凭考试&nbsp;6 ；网络教育为&nbsp;7 。（可多选）</span>
                            </div>
                        </div>
                        <%--学制(年) [   ]--%>
                        <div class="control-group">
                            <label class="control-label">建校年份：</label>
                            <div class="controls">
                                <form:input maxlength="4" path="schoolStartyear" cssStyle="width: 240px"/>
                                <span style="color: red">&nbsp;&nbsp;4位数字年度</span>
                            </div>
                        </div>
                        <%--学历 [   ]--%>
                        <div class="control-group">
                            <label class="control-label">可培养层次代码：</label>
                            <div class="controls">
                                <%--<form:select path="educationCode" class="input-medium " cssStyle="width: 252px">--%>
                                    <%--<form:option value="" label=""/>--%>
                                    <%--<form:options items="${fns:getDictList('education')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
                                <%--</form:select>--%>
                                <c:set value="${fn:split(educationSchoolInfo.educationCode,',' ) }" var="educationCode" />  <%--得到选中的值--%>

                                <form:select path="educationCode" class="input-medium required"  multiple="true" cssStyle="width: 254px">
                                    <c:forEach items="${fns:getDictList('edu_code')}" var="item" > <%-- 遍历下拉框的所有值--%>
                                        <c:set value="0" var="selected"/>   <%--是否选中的默认值1为未选中--%>

                                        <c:forEach items="${educationCode}" var="cat"  > <%--判断某一个值是否选中，选中则将变量selected设置为“0”--%>
                                            <c:if test="${item.value eq  cat}">
                                                <c:set value="1" var="selected"/>   <%--是否选中的默认值1为未选中--%>
                                            </c:if>
                                        </c:forEach>

                                        <c:choose>
                                            <c:when test="${selected eq '1' }">
                                                <form:option value="${item.value}" selected="selected" label="${item.label}"/>  <%--标签选中--%>
                                            </c:when>
                                            <c:otherwise>
                                                <form:option value="${item.value}" label="${item.label}"/>  <%--标签不选中--%>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </form:select>
                                <span style="color: red">&nbsp;&nbsp;博士研究生&nbsp;01；硕士研究生&nbsp;02；第二学士学位&nbsp;04；本科&nbsp;05；专科（含高职）&nbsp;06 </span>
                            </div>
                        </div>

                        <%--&lt;%&ndash;起始时间 [   ]&ndash;%&gt;--%>
                        <%--<div class="control-group">--%>
                            <%--<label class="control-label">起始时间<span style="color: red">*</span></label>--%>
                            <%--<div class="controls">--%>
                                <%--<input id="startTime" name="startTime" type="text"  maxlength="20" class="input-medium Wdate " cssStyle="width: 240px"--%>
                                       <%--value="<fmt:formatDate value="${educationSchoolInfo.startTime}" pattern="yyyy-MM-dd"/>"--%>
                                       <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--&lt;%&ndash;毕业时间 [   ]&ndash;%&gt;--%>
                        <%--<div class="control-group">--%>
                            <%--<label class="control-label">毕业时间<span style="color: red">*</span></label>--%>
                            <%--<div class="controls">--%>
                                <%--<input id="endTime" name="endTime" type="text" maxlength="20" class="input-medium Wdate " cssStyle="width: 240px"--%>
                                       <%--value="<fmt:formatDate value="${educationSchoolInfo.endTime}" pattern="yyyy-MM-dd"/>"--%>
                                       <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--&lt;%&ndash;校(院)长 [   ]&ndash;%&gt;--%>
                        <%--<div class="control-group">--%>
                            <%--<label class="control-label">校(院)长<span style="color: red">*</span></label>--%>
                            <%--<div class="controls">--%>
                                <%--<form:input  maxlength="10" path="schoolMaster" cssStyle="width: 240px" />--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--&lt;%&ndash;证书编号 [   ]&ndash;%&gt;--%>
                        <%--<div class="control-group">--%>
                            <%--<label class="control-label">证书编号<span style="color: red">*</span></label>--%>
                            <%--<div class="controls">--%>
                                <%--<form:input maxlength="20" path="zsNo" cssStyle="width: 240px"/>--%>
                            <%--</div>--%>
                        <%--</div>--%>

<%--附件添加--%>
                <%--<tr>--%>
                    <%--<div class="control-group">--%>
                        <%--<div class="controls">--%>
                            <%--<table id="contentTable" class="table table-striped table-bordered table-condensed required">--%>
                                <%--<thead>--%>
                                <%--<tr>--%>
                                    <%--<th class="hide"></th>--%>
                                    <%--<th>附件类别</th>--%>
                                    <%--<th>文件地址</th>--%>
                                    <%--<th>备注</th>--%>
                                <%--</tr>--%>
                                <%--</thead>--%>
                                <%--<tbody id="counselorAttachmentList">--%>
                                    <%--<tr id="counselorAttachmentList0">--%>
                                        <%--<td class="hide">--%>
                                            <%--<input id="counselorAttachmentList0_id" name="counselorAttachmentList[0].id" type="hidden" value=""/>--%>
                                            <%--<input id="counselorAttachmentList0_delFlag" name="counselorAttachmentList[0].delFlag" type="hidden" value="0"/>--%>

                                        <%--</td>--%>
                                        <%--<td>--%>
                                             <%--学历（学位）--%>
                                        <%--</td>--%>

                                        <%--<td>--%>
                                            <%--<input class="attachmentImagePid" type="hidden" value="${educationSchoolInfo.id}"/>--%>
                                            <%--&lt;%&ndash;<input class="attachmentImageType" type="hidden"   value="16"/>&ndash;%&gt;--%>
                                            <%--<input class="attachmentImageIds"  id="counselorAttachmentList0_path" name="counselorAttachmentList[0].path" type="hidden" <c:forEach items="${educationSchoolInfo.counselorAttachmentList}" var="attach"> value="${attach.id}" </c:forEach> />--%>
                                            <%--<input class="attachmentImageDeleteIds"  id="counselorAttachmentList0_deletePath" name="counselorAttachmentList[0].deletePath" type="hidden" value=""  />--%>
                                            <%--<ol class="attachmentImageOl" id="attachmentImage0">--%>
                                                <%--<c:forEach items="${educationSchoolInfo.counselorAttachmentList}" var="attach">--%>
                                                    <%--&lt;%&ndash;<c:if test="${ attach.type eq row.type }">&ndash;%&gt;--%>
                                                        <%--<li id='${attach.id}'> <a href='${ctx}/uploadImage/showImage/id/${attach.id}' target='_blank' >${attach.fileName}</a>--%>
                                                            <%--&nbsp;&nbsp;--%>
                                                            <%--<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'>×</a>--%>
                                                        <%--</li>--%>
                                                    <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
                                                <%--</c:forEach>--%>
                                            <%--</ol>--%>
                                            <%--<input type="button" class="btn" value="添加"  onclick="uploadImage(this,'${ctx}')">--%>
                                        <%--</td>--%>
                                        <%--<td>--%>

                                            <%--<input id="counselorAttachmentListRemarks" name="counselorAttachmentListRemarks" type="text" <c:forEach items="${educationSchoolInfo.counselorAttachmentList}" var="attach" begin="0" end="0"> value="${attach.remarks}" </c:forEach>  maxlength="100" class="input-small"/>--%>
                                        <%--</td>--%>
                                    <%--</tr>--%>

                                <%--</tbody>--%>
                                <%--&lt;%&ndash;<shiro:hasPermission name="declare:educationSchoolInfo:edit">&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<tfoot>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<tr><td colspan="7">&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<a href="javascript:" onclick="addRow('#counselorAttachmentList', counselorAttachmentRowIdx, counselorAttachmentTpl);counselorAttachmentRowIdx = counselorAttachmentRowIdx + 1;" class="btn">新增附件</a></td></tr>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</tfoot>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</shiro:hasPermission>&ndash;%&gt;--%>
                            <%--</table>--%>
                            <%--<script type="text/template" id="counselorAttachmentTpl">--%>
                                        <%--&lt;%&ndash;//<!-- 要添加的附件-模板 &ndash;%&gt;--%>
                                <%--<tr id="counselorAttachmentList{{idx}}">--%>
                                    <%--<td class="hide">--%>
                                        <%--<input id="counselorAttachmentList{{idx}}_id" name="counselorAttachmentList[{{idx}}].id" type="hidden" value="{{row.id}}"/>--%>
                                        <%--<input id="counselorAttachmentList{{idx}}_delFlag" name="counselorAttachmentList[{{idx}}].delFlag" type="hidden" value="0"/>--%>

                                    <%--</td>--%>
                                    <%--&lt;%&ndash;<td>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<input id="counselorAttachmentList{{idx}}_fileName" name="counselorAttachmentList[{{idx}}].fileName"  maxlength="20" type="text" value="{{row.fileName}}" maxlength="100" class="input-small required"/>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
                                    <%--<c:set var="dictValue" value=""/>--%>
                                    <%--<td>--%>
                                        <%--<select id="counselorAttachmentList{{idx}}_type" name="counselorAttachmentList[{{idx}}].type" data-value="{{row.type}}" class="input-small required" >--%>
                                                <%--&lt;%&ndash;${fns:getDictLabel('16', 'attachment_person_type', '')}&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<option value=""></option>&ndash;%&gt;--%>
                                            <%--<c:forEach items="${fns:getDictList('attachment_person_type')}" var="dict">--%>
                                                <%--&lt;%&ndash;<input  class="attachmentImageType"    type="hidden"   value="${dict.value}"/>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash; &ndash;%&gt;--%>
                                                <%--<option <c:if test="${dict.value eq '16'}"> selected="selected" <c:set var="dictValue" value="${dict.value}"/> </c:if> value="${dict.value}">${dict.label}</option>--%>

                                            <%--</c:forEach>--%>
                                        <%--</select>--%>
                                    <%--</td>--%>
                                    <%--<td>--%>
                                        <%--<input class="attachmentRemarks" name="counselorAttachmentList[{{idx}}].remarks" type="hidden" value="{{row.remarks}}" maxlength="100" />--%>
                                        <%--<input id="attachmentImagePid" class="attachmentImagePid"    type="hidden" value="${educationSchoolInfo.id}"/>--%>
                                        <%--<input id="attachmentImageType" class="attachmentImageType"    type="hidden"   value="${dictValue}"/>--%>
                                        <%--<input class="attachmentImageIds"  id="counselorAttachmentList{{idx}}_path" name="counselorAttachmentList[{{idx}}].path" type="hidden" value="{{row.path}}" />--%>
                                        <%--<input class="attachmentImageDeleteIds"  id="counselorAttachmentList{{idx}}_deletePath" name="counselorAttachmentList[{{idx}}].deletePath" type="hidden" value="{{row.deletePath}}" />--%>
                                            <%--<ol class="attachmentImageOl" id="attachmentImage{{idx}}">--%>
                                            <%--</ol>--%>
                                        <%--<input type="button" class="btn" value="添加"  onclick="uploadImage(this,'${ctx}')">--%>
                                    <%--</td>--%>
                                    <%--<td>--%>
                                        <%--<input id="counselorAttachmentList{{idx}}_remarks" name="counselorAttachmentList[{{idx}}].remarks" type="text" value="{{row.remarks}}" maxlength="100" class="input-small"/>--%>
                                    <%--</td>--%>
                                    <%--&lt;%&ndash;<shiro:hasPermission name="declare:educationSchoolInfo:edit">&ndash;%&gt;--%>
                                    <%--<td class="text-center" width="10">--%>
                                        <%--{{#delBtn}}<span class="close" onclick="delRow(this, '#counselorAttachmentList{{idx}}')" title="删除">&times;</span>{{/delBtn}}--%>
                                    <%--</td>--%>
                                    <%--&lt;%&ndash;</shiro:hasPermission>&ndash;%&gt;--%>
                                <%--</tr>--%>
                                <%--&lt;%&ndash;//-->&ndash;%&gt;--%>
                            <%--</script>--%>
                            <%--<script type="text/javascript">--%>
                                <%--var counselorAttachmentRowIdx = 0, counselorAttachmentTpl = $("#counselorAttachmentTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");--%>

                                <%--$(document).ready(function() {--%>
                                    <%--var data = ${fns:toJson(educationSchoolInfo.counselorAttachmentList)};--%>
                                    <%--var attData = ${fns:toJson(educationSchoolInfo.counselorAttachmentList)};--%>
                                    <%--for (var i=0; i<data.length; i++){--%>
                                        <%--//addRow('#counselorAttachmentList', counselorAttachmentRowIdx, counselorAttachmentTpl, data[i],attData);--%>
                                        <%--counselorAttachmentRowIdx = counselorAttachmentRowIdx + 1;--%>
                                    <%--}--%>
                                <%--});--%>
                            <%--</script>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                <%--</tr>--%>

        </table>
        <div class="form-actions">

            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveBaseInfo('${educationSchoolInfo.id}')" value="保存"/>

            <input id="bakSubmit" class="btn btn-primary" type="button" onclick="history.go(-1)" value="返回"/>
        </div>
    </form:form>


</body>
</html>