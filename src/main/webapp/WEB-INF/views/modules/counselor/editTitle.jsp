<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>职称证书</title>
	<meta name="decorator" content="blank"/>
    <script src="${pageContext.request.contextPath}/static/webuploader-0.1.5/image-upload/uploadImageEduAndTitle.js" type="text/javascript"></script>

    <script>
        // var imageType = $("#selectType option:selected").val();
        // alert(imageType);
        $(document).ready(function (){
            var value  = $("#titleLevel").val();
            var html = '';
            var result = $("#titleType").val();
            if(value!=null&&value!=''){
                $.ajax({
                    url:'${ctx}/counselor/title/ajaxTitle?value='+value,
                    type:'post',
                    dataType:'json',
                    success:function(data){
                        //加一条空的
                        html+='<select class="select2-container input-medium" style="width: 180px" name="titleType" id="selectType">'
                        html+='<option value='+""+'></option>'
                        for(var i = 0;i<data.length;i++){
                            if(data[i].value==result){
                                html += '<option value=' + data[i].value + ' selected="selected">' + data[i].label + '</option>'
                            }else {
                                html += '<option value=' + data[i].value + '>' + data[i].label + '</option>'
                            }
                        }
                        html+='</select>'
                        $("#titleList").append(html)
                    }
                });
            }
        })

        function changeTitle(obj){
            //根据不同的值,通过ajax 查找不同的
            var value =$(obj).val();
            var html = '';
            $("#titleList").empty();
            $.ajax({
                url:'${ctx}/counselor/title/ajaxTitle?value='+value,
                type:'post',
                dataType:'json',
                success:function(data){
                    html+='<select class="select2-container input-medium" style="width: 180px" name="titleType" >'
                    html+='<option value='+""+'></option>'
                    for(var i = 0;i<data.length;i++){
                        html+='<option value='+data[i].value+'>'+data[i].label+'</option>'
                    }
                    /*html+='</select>'*/
                    $("#titleList").append(html)
                }
            });

        }

        function sumbitForm(){
            //每一项都是必填,需要进行判断. 毕业时间需必须大于起始时间
            //获取表单对象
            //时间进行判断
            var path = $("#counselorAttachmentList0_path").val();
            // alert(path);
            if ((path == null || path == "" )) {
                alert("需要增加附件");
                return false;
            } else {
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
                            location.href='${ctx}/counselor/self/queryTitle'
                        }else if(data=='201'){
                            alert("请填写完整")
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
    <input type="hidden" value="${titleCertificate.titleType}" id="titleType">
    <form:form id="inputForm" modelAttribute="titleCertificate" action="${ctx}/counselor/self/saveTitle?type=${type}" method="post" class="form-horizontal">
        <sys:message content="${message}"/>

        <form:input path="id" type="hidden"/>
        <form:input path="personId" type="hidden"/>
        <form:input path="index" type="hidden"/>
        <form:input path="main" type="hidden"/>
        <form:input path="assist" type="hidden"/>
    <table class="table table-striped table-bordered table-condensed">

        <legend>职称证书</legend>

        <tr>
            <td>
                <div class="control-group">
                    <label class="control-label">职称级别<span style="color: red">*</span></label>
                    <div class="controls">
                        <form:select id="titleLevel" path="titleLevel" class="input-medium " onchange="changeTitle(this)" cssStyle="width: 280px">
                            <form:option value="" label=""/>
                            <form:options items="${fns:getDictList('title_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </div>
                </div>
            </td>
            <td>
                <div class="control-group">
                    <label class="control-label">职称类型<span style="color: red">*</span></label>
                    <div class="controls" id="titleList" style="width: 280px" >

                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="control-group">
                    <label class="control-label">专业</label>
                    <div class="controls">
                        <form:input path="specialty"  maxlength="40" cssStyle="width: 280px"/>
                    </div>
                </div>
            </td>
            <td>
                <div class="control-group">
                    <label class="control-label">批准机构<span style="color: red">*</span></label>
                    <div class="controls" >
                        <form:input path="approveEmployer" maxlength="40" cssStyle="width: 280px"/>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="control-group">
                    <label class="control-label">批准时间<span style="color: red">*</span></label>
                    <div class="controls" >
                        <input name="approveTime" type="text" style="width: 280px"  maxlength="20" class="input-medium Wdate "
                               value="<fmt:formatDate value="${titleCertificate.approveTime}" pattern="yyyy-MM-dd"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                    </div>
                </div>
            </td>
            <td>
                <div class="control-group">
                    <label class="control-label">获得职称证书时的工作单位</label>
                    <div class="controls" >
                        <form:input path="getEmployer" maxlength="80"  cssStyle="width: 280px"/>
                    </div>
                </div>
            </td>
        </tr>

        <%--<table id="contentTable" class="table table-striped table-bordered table-condensed" align="center">--%>
            <%--<tr align="center">--%>
                <%--<td colspan="20" height="60px" width="100%" align="center"><span  style="font-size: 18px;align-content: center">职称证书情况</span></td>--%>
            <%--</tr>--%>
            <%--<tr align="center">--%>
                <%--<td colspan="3"  height="30px" width="15%" ><div align="center">职称级别<span style="color: red">*</span></div></td>--%>
                <%--<td colspan="3"  width="15%" ><div align="center">职称类型<span style="color: red">*</span></div></td>--%>
                <%--<td colspan="4" align="center" width="20%" ><div align="center">专业</div></td>--%>
                <%--<td colspan="5" align="center" width="25%" ><div align="center">批准机构<span style="color: red">*</span></div></td>--%>
                <%--<td colspan="5" align="center" width="25%" ><div align="center">批准时间<span style="color: red">*</span></div></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td colspan="3"  height="30px" width="15%" >--%>
                    <%--<form:select id="titleLevel" path="titleLevel" class="input-medium " onchange="changeTitle(this)" cssStyle="width: 180px">--%>
                        <%--<form:option value="" label=""/>--%>
                        <%--<form:options items="${fns:getDictList('title_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
                    <%--</form:select>--%>
                <%--</td>--%>
                <%--<td colspan="3"  width="15%" id="titleList1" >--%>

                <%--</td>--%>
                <%--<td colspan="4" width="200" >--%>
                    <%--<form:input path="specialty"  maxlength="40" cssStyle="width: 90%"/>--%>
                <%--</td>--%>
                <%--<td colspan="5" align="center" width="25%" >--%>
                    <%--<form:input path="approveEmployer" maxlength="40" cssStyle="width: 90%"/>--%>
                <%--</td>--%>
                <%--<td colspan="5" align="center" width="20%" >--%>

                    <%--<input name="approveTime" type="text"  maxlength="20" class="input-medium Wdate "--%>
                           <%--value="<fmt:formatDate value="${titleCertificate.approveTime}" pattern="yyyy-MM-dd"/>"--%>
                           <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td colspan="6" height="30px" width="30%" >获得职称证书时的工作单位</td>--%>
                <%--<td colspan="14"  width="70%" >--%>
                    <%--<form:input path="getEmployer" maxlength="80"  cssStyle="width: 97%"/>--%>
                <%--</td>--%>
            <%--</tr>--%>
        <%--</table>--%>
        <tr>
            <div class="control-group">
                <div class="controls">
                    <table id="contentTable" class="table table-striped table-bordered table-condensed required">
                        <thead>
                        <tr>
                            <th class="hide"></th>
                            <th>附件类别</th>
                            <th>文件地址</th>
                            <th>备注</th>
                        </tr>
                        </thead>
                        <tbody id="counselorAttachmentList">
                        <tr id="counselorAttachmentList0">
                            <td class="hide">
                                <input id="counselorAttachmentList0_id" name="counselorAttachmentList[0].id" type="hidden" value=""/>
                                <input id="counselorAttachmentList0_delFlag" name="counselorAttachmentList[0].delFlag" type="hidden" value="0"/>

                            </td>
                            <td>
                                职称证书
                            </td>

                            <td>
                                <input class="attachmentImagePid" type="hidden" value="${titleCertificate.id}"/>
                                <input class="attachmentImageType" type="hidden"   value="17"/>
                                <input class="attachmentImageIds"  id="counselorAttachmentList0_path" name="counselorAttachmentList[0].path" type="hidden" <c:forEach items="${titleCertificate.counselorAttachmentList}" var="attach"> value="${attach.id}" </c:forEach>  />
                                <input class="attachmentImageDeleteIds"  id="counselorAttachmentList0_deletePath" name="counselorAttachmentList[0].deletePath" type="hidden" value=""  />
                                <ol class="attachmentImageOl" id="attachmentImage0">
                                    <c:forEach items="${titleCertificate.counselorAttachmentList}" var="attach">
                                        <li id='${attach.id}'> <a href='${ctx}/uploadImage/showImage/id/${attach.id}' target='_blank' >${attach.fileName}</a>
                                            &nbsp;&nbsp;
                                            <a href='javascript:' onclick='deleteImageId("${attach.id}",this);'>×</a>
                                        </li>
                                    </c:forEach>
                                </ol>
                                <input type="button" class="btn" value="添加"  onclick="uploadImage(this,'${ctx}')">
                            </td>
                            <td>

                                <input id="counselorAttachmentListRemarks" name="counselorAttachmentListRemarks" type="text" <c:forEach items="${titleCertificate.counselorAttachmentList}" var="attach" begin="0" end="0"> value="${attach.remarks}" </c:forEach>  maxlength="100" class="input-small"/>
                            </td>
                        </tr>

                        </tbody>
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
                                <input id="attachmentImagePid" class="attachmentImagePid"    type="hidden" value="${titleCertificate.id}"/>
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
                                <%--<shiro:hasPermission name="declare:titleCertificate:edit">--%>
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
                            var data = ${fns:toJson(titleCertificate.counselorAttachmentList)};
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

            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="sumbitForm()" value="提交"/>
            <input id="bakSubmit" class="btn btn-primary" type="button" onclick="history.go(-1)" value="返回"/>
        </div>
    </form:form>


</body>
</html>