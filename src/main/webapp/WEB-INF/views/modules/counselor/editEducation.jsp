<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>学历教育情况</title>
    <meta name="decorator" content="blank"/>
    <script src="${pageContext.request.contextPath}/static/webuploader-0.1.5/image-upload/uploadImageEduAndTitle.js" type="text/javascript"></script>

    <script>
        function sumbitForm1(){
            //每一项都是必填,需要进行判断. 毕业时间需必须大于起始时间
            //获取表单对象
            //时间进行判断
            // var fileName = $("#counselorAttachmentList0_fileName").val();typeof(fileName) == "undefined" ||var id = $("#id").val(); && (id !=""&& id !=null)
            var path = $("#counselorAttachmentList0_path").val();
            // alert(path);
            if ((path == null || path == "" )) {
                alert("需要增加附件");
                return false;
            } else {
                // loading('正在提交，请稍等...');
                // form.submit();
                var targetUrl = $("#inputForm").attr("action");
                var data = $("#inputForm").serialize();
                // alert(data);
                $.ajax({
                    url:targetUrl,
                    type:"POST",
                    cache: false,
                    data:data,
                    dataType:'json',
                    success:function(data) {
                        if(data=='200'){
                            alert("提交成功")
                            location.href='${ctx}/counselor/self/queryEducation'
                        }else if(data=='201'){
                            alert("请填写完整")
                        }else if(data=='202'){
                            alert("毕业时间不可小于起始时间")
                        }
                    }
                })
            }

        }

        function addRow(list, idx, tpl, row){
            //验证是否已经存在ID ,不存在 需有先保存，再进行添加附件功能
            // var id = $("#id").val()
            // if (id == "" || id == null){
            //     confirmx('添加附件请先保存本页面数据？', "javascript:saveBaseInfo()");
            // }else {
                $(list).append(Mustache.render(tpl, {
                    idx: idx, delBtn: true, row: row
                }));
                $(list+idx).find("select").each(function(){
                    $(this).val($(this).attr("data-value"));
                });
                $(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
                    var ss = $(this).attr("data-value").split(',');
                    for (var i=0; i<ss.length; i++){
                        if($(this).val() == ss[i]){
                            $(this).attr("checked","checked");
                        }
                    }
                });
            // }
        }

        // function saveBaseInfo(){
        //     $("#saveType").val("1");
        //     $("#stage").val("1");
        //     $("#inputForm").submit();
        // }

        function delRow(obj, prefix){
            var id = $(prefix+"_id");
            var delFlag = $(prefix+"_delFlag");
            if (id.val() == ""){
                $(obj).parent().parent().remove();
            }else if(delFlag.val() == "0"){
                delFlag.val("1");
                $(obj).html("&divide;").attr("title", "撤销删除");
                $(obj).parent().parent().addClass("error");
            }else if(delFlag.val() == "1"){
                delFlag.val("0");
                $(obj).html("&times;").attr("title", "删除");
                $(obj).parent().parent().removeClass("error");
            }
        }

    </script>
</head>
<body>
    <input id="ctx" type="hidden" value="${ctx}" />
    <ul class="nav nav-tabs">
    </ul><br/>
    <form:form id="inputForm" modelAttribute="educationtbl" action="${ctx}/counselor/self/saveEducation?type=${type}" method="post" class="form-horizontal">
        <sys:message content="${message}"/>


        <table id="contentTable" class="table table-striped table-bordered table-condensed">
            <%--<div class="control-group"  align="left">--%>
                    <%--<span  style="font-size: 22px;align-content: center">学历教育情况</span>--%>
            <%--</div>--%>
            <form:input path="personId" type="hidden" />
            <form:input path="id" type="hidden"/>
            <form:input path="index" type="hidden"/>
            <form:input path="main" type="hidden"/>
            <form:input path="assist" type="hidden"/>
            <legend>学历教育情况</legend>
                <tr>
                    <td>
                        <%--办学类型 [   ]--%>
                        <div class="control-group">
                            <label class="control-label">办学类型<span style="color: red">*</span></label>
                            <div class="controls">
                                <form:select path="schoolType" class="input-medium " cssStyle="width: 252px">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('school_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>
                        </div>
                    </td>
                    <td>
                        <%--毕业院校 [   ]--%>
                        <div class="control-group">
                            <label class="control-label">毕业院校<span style="color: red">*</span></label>
                            <div class="controls">
                                <form:input  maxlength="50" path="school" cssStyle="width: 240px"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <%--所学专业 [   ]--%>
                        <div class="control-group">
                            <label class="control-label">所学专业<span style="color: red">*</span></label>
                            <div class="controls">
                                <form:input path="specialty" maxlength="100" cssStyle="width: 240px"/>
                            </div>
                        </div>
                    </td>
                    <td>
                        <%--学习方式 [   ]--%>
                        <div class="control-group">
                            <label class="control-label">学习方式<span style="color: red">*</span></label>
                            <div class="controls">
                                <form:select path="studyType" class="input-medium " cssStyle="width: 252px">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('study_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <%--学历 [   ]--%>
                        <div class="control-group">
                            <label class="control-label">学历<span style="color: red">*</span></label>
                            <div class="controls">
                                <form:select path="education" class="input-medium " cssStyle="width: 252px">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('education')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>
                        </div>
                    </td>
                    <td>
                        <%--学制(年) [   ]--%>
                        <div class="control-group">
                            <label class="control-label">学制(年)<span style="color: red">*</span></label>
                            <div class="controls">
                                <form:input maxlength="3" path="studyYear" cssStyle="width: 240px"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <%--起始时间 [   ]--%>
                        <div class="control-group">
                            <label class="control-label">起始时间<span style="color: red">*</span></label>
                            <div class="controls">
                                <input id="startTime" name="startTime" type="text"  maxlength="20" class="input-medium Wdate " cssStyle="width: 240px"
                                       value="<fmt:formatDate value="${educationtbl.startTime}" pattern="yyyy-MM-dd"/>"
                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                            </div>
                        </div>
                    </td>
                    <td>
                        <%--毕业时间 [   ]--%>
                        <div class="control-group">
                            <label class="control-label">毕业时间<span style="color: red">*</span></label>
                            <div class="controls">
                                <input id="endTime" name="endTime" type="text" maxlength="20" class="input-medium Wdate " cssStyle="width: 240px"
                                       value="<fmt:formatDate value="${educationtbl.endTime}" pattern="yyyy-MM-dd"/>"
                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <%--校(院)长 [   ]--%>
                        <div class="control-group">
                            <label class="control-label">校(院)长<span style="color: red">*</span></label>
                            <div class="controls">
                                <form:input  maxlength="10" path="schoolMaster" cssStyle="width: 240px" />
                            </div>
                        </div>
                    </td>
                    <td>
                        <%--证书编号 [   ]--%>
                        <div class="control-group">
                            <label class="control-label">证书编号<span style="color: red">*</span></label>
                            <div class="controls">
                                <form:input maxlength="20" path="zsNo" cssStyle="width: 240px"/>
                            </div>
                        </div>
                    </td>
                </tr>


            <%--<tr>--%>
                <%--<td colspan="4" height="50px" width="20%" >办学类型<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                    <%--<form:select path="schoolType" class="input-medium " cssStyle="width: 99%">--%>
                        <%--<form:option value="" label=""/>--%>
                        <%--<form:options items="${fns:getDictList('school_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
                    <%--</form:select>--%>
                <%--</td>--%>
                <%--<td colspan="4" width="20%" >毕业院校<span style="color: red">*</span></td>--%>
                <%--<td colspan="6" width="30%" ><form:input  maxlength="50" path="school"/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
               <%--<td colspan="4" height="30px" width="20%" >所学专业<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                    <%--<form:input path="specialty" maxlength="100"/>--%>
                <%--</td>--%>
                <%--<td colspan="4"  width="20%" >学习方式<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                    <%--<form:select path="studyType" class="input-medium " cssStyle="width: 99%">--%>
                        <%--<form:option value="" label=""/>--%>
                        <%--<form:options items="${fns:getDictList('study_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
                    <%--</form:select>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td colspan="4" height="30px" width="20%" >学历<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                    <%--<form:select path="education" class="input-medium " cssStyle="width: 99%">--%>
                        <%--<form:option value="" label=""/>--%>
                        <%--<form:options items="${fns:getDictList('education')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
                    <%--</form:select>--%>
                <%--</td>--%>
                <%--<td colspan="4"  width="20%" >学制(年)<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" ><form:input maxlength="3" path="studyYear" cssStyle="width: 80%"/></td>--%>

            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td colspan="4" height="30px" width="20%" >起始时间<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                    <%--<input id="startTime" name="startTime" type="text"  maxlength="20" class="input-medium Wdate " cssStyle="width: 99%"--%>
                           <%--value="<fmt:formatDate value="${educationtbl.startTime}" pattern="yyyy-MM-dd"/>"--%>
                           <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
                <%--</td>--%>
                <%--<td colspan="4"  width="20%" >毕业时间<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                    <%--<input id="endTime" name="endTime" type="text" maxlength="20" class="input-medium Wdate " cssStyle="width: 99%"--%>
                           <%--value="<fmt:formatDate value="${educationtbl.endTime}" pattern="yyyy-MM-dd"/>"--%>
                           <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
                <%--</td>--%>

            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td colspan="4" height="30px" width="20%" >校(院)长<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" ><form:input  maxlength="10" path="schoolMaster" cssStyle="width: 93%" /></td>--%>
                <%--<td colspan="4"  width="20%" >证书编号<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                    <%--<form:input maxlength="20" path="zsNo" cssStyle="width: 80%"/>--%>
                <%--</td>--%>
            <%--</tr>--%>

                <tr>
                    <div class="control-group">
                        <div class="controls">
                            <table id="contentTable" class="table table-striped table-bordered table-condensed required">
                                <thead>
                                <tr>
                                    <th class="hide"></th>
                                    <%--<th>文件名称</th>--%>
                                    <th>附件类别</th>
                                    <th>文件地址</th>
                                    <th>备注</th>
                                    <%--<th width="10">&nbsp;</th>--%>
                                </tr>
                                </thead>
                                <tbody id="counselorAttachmentList">
                                <%-- 从库中取出来显示的附件 --%>
                                <%--<c:set var="attachType" value=""  />--%>
                                <%--<c:forEach items="${educationtbl.counselorAttachmentList}" var="row" varStatus="index">--%>
                                    <%--<c:if test="${row.type ne attachType}">--%>
                                    <%--<tr id="counselorAttachmentList${index.index}">--%>
                                    <tr id="counselorAttachmentList0">
                                        <td class="hide">
                                            <%--<input id="counselorAttachmentList${index.index}_id" name="counselorAttachmentList[${index.index}].id" type="hidden" value="${row.id}"/>--%>
                                            <%--<input id="counselorAttachmentList${index.index}_delFlag" name="counselorAttachmentList[${index.index}].delFlag" type="hidden" value="0"/>--%>
                                            <input id="counselorAttachmentList0_id" name="counselorAttachmentList[0].id" type="hidden" value=""/>
                                            <input id="counselorAttachmentList0_delFlag" name="counselorAttachmentList[0].delFlag" type="hidden" value="0"/>

                                        </td>
                                        <%--<td>--%>
                                            <%--<input id="counselorAttachmentList${index.index}_fileName" name="counselorAttachmentList[${index.index}].fileName"  maxlength="20" type="text" value="${row.fileName}" maxlength="100" class="input-small required"/>--%>
                                        <%--</td>--%>
                                        <td>
                                            <%--<select id="counselorAttachmentList${index.index}_type" name="counselorAttachmentList[${index.index}].type" data-value="${row.type}" class="input-small required" >--%>
                                                <%--<option value=""></option>--%>
                                                    <%--${fns:getDictList('attachment_person_type')}  --%>
                                                <%--<c:forEach items="${fns:getDictList('attachment_person_type')}" var="dict">--%>
                                                    <%--<option <c:if test="${row.type eq dict.value}"> selected="selected" </c:if> value="${dict.value}" >--%>
                                                            <%--${dict.label}--%>
                                                    <%--</option>--%>
                                                <%--</c:forEach>--%>
                                            <%--</select>--%>
                                             学历（学位）
                                        </td>

                                        <td>
                                            <%--<input class="attachmentRemarks" name="counselorAttachmentList[${index.index}].remarks" type="hidden" value="${row.remarks}" maxlength="100" />--%>
                                            <input class="attachmentImagePid" type="hidden" value="${educationtbl.id}"/>
                                            <input class="attachmentImageType" type="hidden"   value="16"/>
                                            <input class="attachmentImageIds"  id="counselorAttachmentList0_path" name="counselorAttachmentList[0].path" type="hidden" <c:forEach items="${educationtbl.counselorAttachmentList}" var="attach"> value="${attach.id}" </c:forEach> />
                                            <input class="attachmentImageDeleteIds"  id="counselorAttachmentList0_deletePath" name="counselorAttachmentList[0].deletePath" type="hidden" value=""  />
                                            <ol class="attachmentImageOl" id="attachmentImage0">
                                                <c:forEach items="${educationtbl.counselorAttachmentList}" var="attach">
                                                    <%--<c:if test="${ attach.type eq row.type }">--%>
                                                        <li id='${attach.id}'> <a href='${ctx}/uploadImage/showImage/id/${attach.id}' target='_blank' >${attach.fileName}</a>
                                                            &nbsp;&nbsp;
                                                            <a href='javascript:' onclick='deleteImageId("${attach.id}",this);'>×</a>
                                                        </li>
                                                    <%--</c:if>--%>
                                                </c:forEach>
                                            </ol>
                                            <input type="button" class="btn" value="添加"  onclick="uploadImage(this,'${ctx}')">
                                        </td>
                                        <td>

                                            <input id="counselorAttachmentListRemarks" name="counselorAttachmentListRemarks" type="text" <c:forEach items="${educationtbl.counselorAttachmentList}" var="attach" begin="0" end="0"> value="${attach.remarks}" </c:forEach>  maxlength="100" class="input-small"/>
                                            <%--<input    id="counselorAttachmentList${index.index}_remarks" name="counselorAttachmentList[${index.index}].remarks" type="text" value="${row.remarks}" maxlength="100" class="input-small"/>--%>
                                        </td>
                                        <%--<td class="text-center" width="10">--%>
                                            <%--<span class="close" onclick="delRow(this, '#counselorAttachmentList${index.index}')" title="删除">&times;</span>--%>
                                        <%--</td>--%>
                                    </tr>
                                    <%--</c:if>--%>
                                    <%--<c:set var="attachType" value="${row.type}"  />--%>
                                <%--</c:forEach>--%>

                                </tbody>
                                <%--<shiro:hasPermission name="declare:educationtbl:edit">--%>
                                    <%--<tfoot>--%>
                                <%--<tr><td colspan="7">--%>
                                    <%--<a href="javascript:" onclick="addRow('#counselorAttachmentList', counselorAttachmentRowIdx, counselorAttachmentTpl);counselorAttachmentRowIdx = counselorAttachmentRowIdx + 1;" class="btn">新增附件</a></td></tr>--%>
                                <%--</tfoot>--%>
                                <%--</shiro:hasPermission>--%>
                            </table>
                            <script type="text/template" id="counselorAttachmentTpl">
                                        <%--//<!-- 要添加的附件-模板 --%>
                                <tr id="counselorAttachmentList{{idx}}">
                                    <td class="hide">
                                        <input id="counselorAttachmentList{{idx}}_id" name="counselorAttachmentList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
                                        <input id="counselorAttachmentList{{idx}}_delFlag" name="counselorAttachmentList[{{idx}}].delFlag" type="hidden" value="0"/>

                                    </td>
                                    <%--<td>--%>
                                        <%--<input id="counselorAttachmentList{{idx}}_fileName" name="counselorAttachmentList[{{idx}}].fileName"  maxlength="20" type="text" value="{{row.fileName}}" maxlength="100" class="input-small required"/>--%>
                                    <%--</td>--%>
                                    <c:set var="dictValue" value=""/>
                                    <td>
                                        <select id="counselorAttachmentList{{idx}}_type" name="counselorAttachmentList[{{idx}}].type" data-value="{{row.type}}" class="input-small required" >
                                                <%--${fns:getDictLabel('16', 'attachment_person_type', '')}--%>
                                            <%--<option value=""></option>--%>
                                            <c:forEach items="${fns:getDictList('attachment_person_type')}" var="dict">
                                                <%--<input  class="attachmentImageType"    type="hidden"   value="${dict.value}"/>--%>
                                                <%-- --%>
                                                <option <c:if test="${dict.value eq '16'}"> selected="selected" <c:set var="dictValue" value="${dict.value}"/> </c:if> value="${dict.value}">${dict.label}</option>

                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <input class="attachmentRemarks" name="counselorAttachmentList[{{idx}}].remarks" type="hidden" value="{{row.remarks}}" maxlength="100" />
                                        <input id="attachmentImagePid" class="attachmentImagePid"    type="hidden" value="${educationtbl.id}"/>
                                        <input id="attachmentImageType" class="attachmentImageType"    type="hidden"   value="${dictValue}"/>
                                        <input class="attachmentImageIds"  id="counselorAttachmentList{{idx}}_path" name="counselorAttachmentList[{{idx}}].path" type="hidden" value="{{row.path}}" />
                                        <input class="attachmentImageDeleteIds"  id="counselorAttachmentList{{idx}}_deletePath" name="counselorAttachmentList[{{idx}}].deletePath" type="hidden" value="{{row.deletePath}}" />
                                            <ol class="attachmentImageOl" id="attachmentImage{{idx}}">
                                            </ol>
                                        <input type="button" class="btn" value="添加"  onclick="uploadImage(this,'${ctx}')">
                                    </td>
                                    <td>
                                        <input id="counselorAttachmentList{{idx}}_remarks" name="counselorAttachmentList[{{idx}}].remarks" type="text" value="{{row.remarks}}" maxlength="100" class="input-small"/>
                                    </td>
                                    <%--<shiro:hasPermission name="declare:educationtbl:edit">--%>
                                    <td class="text-center" width="10">
                                        {{#delBtn}}<span class="close" onclick="delRow(this, '#counselorAttachmentList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
                                    </td>
                                    <%--</shiro:hasPermission>--%>
                                </tr>
                                <%--//-->--%>
                            </script>
                            <script type="text/javascript">
                                var counselorAttachmentRowIdx = 0, counselorAttachmentTpl = $("#counselorAttachmentTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");

                                $(document).ready(function() {
                                    var data = ${fns:toJson(educationtbl.counselorAttachmentList)};
                                    var attData = ${fns:toJson(educationtbl.counselorAttachmentList)};
                                    for (var i=0; i<data.length; i++){
                                        //addRow('#counselorAttachmentList', counselorAttachmentRowIdx, counselorAttachmentTpl, data[i],attData);
                                        counselorAttachmentRowIdx = counselorAttachmentRowIdx + 1;
                                    }
                                });
                            </script>
                        </div>
                    </div>

                </tr>

        </table>
        <div class="form-actions">

            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="sumbitForm1()" value="提交"/>

            <input id="bakSubmit" class="btn btn-primary" type="button" onclick="history.go(-1)" value="返回"/>
        </div>
    </form:form>


</body>
</html>