<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>培训院校</title>
	<meta name="decorator" content="blank"/>
    		<script src="${pageContext.request.contextPath}/static/webuploader-0.1.5/image-upload/uploadImage6.js" type="text/javascript"></script>

	<script type="text/javascript">
        function sumbitForm3(){
            //每一项都是必填,需要进行判断. 毕业时间需必须大于起始时间
            //获取表单对象
            //时间进行判断
            var targetUrl = $("#inputForm").attr("action");
            var data = $("#inputForm").serialize();
            $.ajax({
                url:targetUrl,
                type:"POST",
                cache: false,
                data:data,
                dataType:'json',
                success:function(data) {
                    if(data=='200'){
                        alert("提交成功");
                        location.href='${ctx}/counselor/self/querySpecialty'
                    }else if(data=='201'){
                        alert("请填写完整")
                    }else if(data=='202'){
                        alert("终止时间不能小于起始时间")
                    }
                }
            })
            
        }
        function addRow(list, idx, tpl, row){
            //验证是否已经存在ID ,不存在 需有先保存，再进行添加附件功能
//             var id = $("#id").val()
//             if (id == "" || id == null){
//                 confirmx('添加附件请先保存本页面数据？', "javascript:saveBaseInfo()");
//             }else {
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
//             }
        }


    </script>
</head>
<body>
	<input id="ctx" type="hidden" value="${ctx}" />
	<ul class="nav nav-tabs">
	</ul><br/>
	<form:form id="inputForm" modelAttribute="specialtyTrain" action="${ctx}/counselor/self/saveSpecialty?type=${type}" method="post" class="form-horizontal">
		<sys:message content="${message}"/>

        <table id="contentTable" class="table table-striped table-bordered table-condensed">
            <tr>
                <td>
                    <span  style="font-size: 18px;align-content: center">
                    培训院校
                </span>
                </td>
            </tr>
            <form:input path="personId" type="hidden"/>
            <form:input path="id" type="hidden"/>
            <form:input path="index" type="hidden"/>
            <form:input path="main" type="hidden"/>
            <form:input path="assist" type="hidden"/>
            <tr>
                <td>
               <div align="center">
                   
                    培训院校<span style="color: red">*</span>
                
                <form:input path="trainSchool"/>
                </td>
                <td>
						证书类型<span style="color: red">*</span>
					
					<form:input path="trainType" /></td>
            </tr>
			
			<tr>
				<td><div align="center">
						所学专业<span style="color: red">*</span>
					
					<form:input path="specialty"/></td>
					<td>
						培训起始时间<span style="color: red">*</span>
                    <input id="startTime" name="startTime" type="text" class="input-medium Wdate "
                           value="<fmt:formatDate value="${specialtyTrain.startTime}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                </td>
			</tr>
			<tr>
				<td><div align="center">
						培训终止时间<span style="color: red">*</span>
					
					
                    <input id="endTime" name="endTime" type="text"  class="input-medium Wdate "
                           value="<fmt:formatDate value="${specialtyTrain.endTime}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

                </td>
                <td>
						证书编号<span style="color: red">*</span>
					
					<form:input path="cardnum"  /></td>
			</tr>
			
			<tr>
				<td><div align="center">
						学习方式<span style="color: red">*</span>
					
					<form:input path="studyType" /></td>
					<td>校长
				<form:input path="schoolMaster" /></td>
			</tr>
			<tr>
				<td><div align="center">
						颁证时间<span style="color: red">*</span>
					</div></td>
					<td>
                    <input name="getgctime" type="text"  class="input-medium Wdate "
                           value="<fmt:formatDate value="${specialtyTrain.getgctime}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                </td>

			</tr>
			
        
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
                                    <%--<shiro:hasPermission name="declare:specialtyTrain:edit">--%>
                                        <th width="10">&nbsp;</th>
                                    <%--</shiro:hasPermission>--%>
                                </tr>
                                </thead>
                                <tbody id="counselorAttachmentList">
                                <%-- 从库中取出来显示的附件 --%>
                               
                                    <tr id="counselorAttachmentList0">
                                        <input id="counselorAttachmentList0_id" name="counselorAttachmentList[0].id" type="hidden" value=""/>
                                            <input id="counselorAttachmentList0_delFlag" name="counselorAttachmentList[0].delFlag" type="hidden" value="0"/>
                                        <%--<td>--%>
                                            <%--<input id="counselorAttachmentList${index.index}_fileName" name="counselorAttachmentList[${index.index}].fileName"  maxlength="20" type="text" value="${row.fileName}" maxlength="100" class="input-small required"/>--%>
                                        <%--</td>--%>
                                        <td>
                                           培训证书
                                        </td>

                                        <td>
                                            
                                            <input class="attachmentImagePid" type="hidden" value="${specialtyTrain.id}"/>
                                            <input class="attachmentImageType" type="hidden"   value="13"/>
                                             <input class="attachmentImageIds"  id="counselorAttachmentList0_path" name="counselorAttachmentList[0].path" type="hidden" <c:forEach items="${specialtyTrain.counselorAttachmentList}" var="attach"> value="${attach.id}" </c:forEach> />
                                              <input class="attachmentImageDeleteIds"  id="counselorAttachmentList0_deletePath" name="counselorAttachmentList[0].deletePath" type="hidden" value=""  />
                                           
                                            <ol class="attachmentImageOl" id="attachmentImage${index.index}">
                                                <c:forEach items="${specialtyTrain.counselorAttachmentList}" var="attach">
                                            
                                                <li id='${attach.id}'> <a href='${ctx}/uploadImage/showImage/id/${attach.id}' target='_blank'  >${attach.fileName}</a>&nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'>×</a> </li>
                                            
                                        </c:forEach>
                                            </ol>
                                            <input type="button" class="btn" value="添加"  onclick="uploadImage(this,'${ctx}')">
                                        </td>
                                        <td>

                                            <input id="counselorAttachmentListRemarks" name="counselorAttachmentListRemarks" type="text" <c:forEach items="${specialtyTrain.counselorAttachmentList}" var="attach" begin="0" end="0"> value="${attach.remarks}" </c:forEach>  maxlength="100" class="input-small"/>
                                        </td>
                                        <%--<shiro:hasPermission name="declare:specialtyTrain:edit">--%>
                                            <td class="text-center" width="10">
                                            <span class="close" onclick="delRow(this, '#counselorAttachmentList${index.index}')" title="删除">&times;</span>
                                        </td>
                                        <%--</shiro:hasPermission>--%>
                                    </tr>

                                

                                </tbody>
                                <%--<shiro:hasPermission name="declare:specialtyTrain:edit">--%>
<!--                                     <tfoot> -->
<!--                                 <tr><td colspan="7"> -->
<!--                                     <a href="javascript:" onclick="addRow('#counselorAttachmentList', counselorAttachmentRowIdx, counselorAttachmentTpl);counselorAttachmentRowIdx = counselorAttachmentRowIdx + 1;" class="btn">新增附件</a></td></tr> -->
<!--                                 </tfoot> -->
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
                                    <td>
                                        <select id="counselorAttachmentList{{idx}}_type" name="counselorAttachmentList[{{idx}}].type" data-value="{{row.type}}" class="input-small required" >
                                             <option value="${fns:getDictLabel( 'counselor_attachment','13', '')}"></option>
                                            <c:forEach items="${fns:getDictList('attachment_person_type')}" var="dict">
                                                <option value="${dict.value}">${dict.label}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <input class="attachmentRemarks" name="counselorAttachmentList[{{idx}}].remarks" type="hidden" value="{{row.remarks}}" maxlength="100" />
                                        <input id="attachmentImagePid" class="attachmentImagePid"    type="hidden" value="${educationtbl.personId}"/>
                                        <input id="attachmentImageType" class="attachmentImageType"    type="hidden"   value="16"/>
                                        <input class="attachmentImageIds"  id="counselorAttachmentList{{idx}}_path" name="counselorAttachmentList[{{idx}}].path" type="hidden" value="{{row.path}}" />
                                        <input class="attachmentImageDeleteIds"  id="counselorAttachmentList{{idx}}_deletePath" name="counselorAttachmentList[{{idx}}].deletePath" type="hidden" value="{{row.deletePath}}" />
                                            <ol class="attachmentImageOl" id="attachmentImage{{idx}}">
                                            </ol>
                                        <input type="button" class="btn" value="添加"  onclick="uploadImage(this,'${ctx}')">
                                    </td>
                                    <td>
                                        <input id="counselorAttachmentList{{idx}}_remarks" name="counselorAttachmentList[{{idx}}].remarks" type="text" value="{{row.remarks}}" maxlength="100" class="input-small"/>
                                    </td>
                                    <%--<shiro:hasPermission name="declare:specialtyTrain:edit">--%>
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
                                    var data = ${fns:toJson(specialtyTrain.counselorAttachmentList)};
                                    var attData = ${fns:toJson(specialtyTrain.counselorAttachmentList)};
                                    for (var i=0; i<data.length; i++){
                                        //addRow('#counselorAttachmentList', counselorAttachmentRowIdx, counselorAttachmentTpl, data[i],attData);
                                        counselorAttachmentRowIdx = counselorAttachmentRowIdx + 1;
                                    }
                                });
                            </script>
                        </div>
                    </div>
                    <%--<div class="form-actions">--%>

                        <%--<shiro:hasPermission name="declare:specialtyTrain:edit">--%>
                            <%-- <c:if test="${declareRecordStatus==0}">
                                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
                            </c:if>
                            <c:if test="${declareRecordStatus==1}"> --%>
                            <%--<c:if test="${stage == '1'}">--%>
                                <%--<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存表单"/>&nbsp;--%>
                            <%--</c:if>--%>
                            <%-- </c:if> --%>
                        <%--</shiro:hasPermission>--%>

                        <%--<input id="btnCancel" class="btn" type="button" value="返 回" onclick="returnPre()"/>--%>

                    <%--</div>--%>
                </tr>
        <div class="form-actions">
                <input id="btnSubmit" class="btn btn-primary" type="button" onclick="sumbitForm3()" value="提交"/>
            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="javascript:history.back(-1);" value="关闭"/>

        </div>
	</form:form>


</body>
</html>