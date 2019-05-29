<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
			 	rules: {
			 		certificatesNum: {remote: "${pageContext.request.contextPath}/enterprise/cardNumber?id="+'${enterpriseWorkers.id}'}
                },
                messages: {
                	certificatesNum: {remote: "证件号已存在"},
                },
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});


            // 手机号码验证
            jQuery.validator.addMethod("mobile", function(value, element) {
                var tel = /(^1[3,4,5,7,8]\d{9}$)/g;
                return this.optional(element) || (tel.test(value));
            }, "手机为:13,14,15,17,18号段，例如：13888888888");

            // 电话号码验证
            jQuery.validator.addMethod("phone", function(value, element) {
                var tel =  /\d{2,5}-\d{7,8}$/g;
                return this.optional(element) || (tel.test(value));
            }, "格式为:固话为区号(3-4位)号码(7-8位),例如：010-88888888");
		});

        function addRow(list, idx, tpl, row){
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
        }
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
        
        function engageSpecialtyChange(engageSpecialty,selectObj){
        	if(engageSpecialty == "25"  || engageSpecialty == "22"){
                alert("该专业不做为可选专业");
                for(var i = 0; i < selectObj.length; i++) {
                    if(i==0){
                        selectObj[i].selected=true;
                    }else{
                        selectObj[i].selected=false;
                    }
                }
                var selectObjJqu = $(selectObj);
                var span = selectObjJqu.parent().find("[class='select2-chosen']");
                var sp=$(span[0]);
                sp.text("--请选择--");
	    	  }
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/enterprise/enterpriseWorkers/">人员列表</a></li>
		<li class="active"><a href="${ctx}/enterprise/enterpriseWorkers/form?id=${enterpriseWorkers.id}">人员<shiro:hasPermission name="enterprise:enterpriseWorkers:edit">${not empty enterpriseWorkers.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="enterprise:enterpriseWorkers:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/enterpriseWorkers/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="pid"/>
		<form:hidden path="fileNo"/>
		<%--<input type="hidden" id="fileNo" name="fileNo" value="${fileNo}">--%>
		<sys:message content="${message}"/>	
		
		<table class="table-form">



			<c:choose>
				<c:when test="${enterpriseWorkers.type!=1 }">
					<tr>

						<td class="tit">姓名：</td>
						<td>
							<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium required"/>
						</td>
						<td class="tit">性别：</td>
						<td>
						    <form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
						</td>
				
						<td class="tit">证件类型：<br/>(中国大陆居民选择“身份证号”)</td>
						<td>
							<form:select path="certificatesType" class="input-medium required">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('ID_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
		                </td>
		            </tr>
		            <tr>
		
		                <td class="tit">手机：</td>
		                <td>
		                    <form:input path="mobile" htmlEscape="false" maxlength="15" class="input-medium mobile "/>
		                </td>
		
		                <td class="tit">电话：</td>
		                <td colspan="">
		                    <form:input path="phone" htmlEscape="false" maxlength="15" class="input-medium  phone"/>
		                </td>
		
		
		                <td class="tit">证件号：</td>
		                <td>
		                    <form:input path="certificatesNum" htmlEscape="false" maxlength="18" class="input-medium required "/>
		                </td>
		            </tr>
		            <tr>
		            <%--学历--%>
						<td class="tit">其他执业资格证书编号：</td>
						<td>
							<form:input path="registerCertificateNum" htmlEscape="false" maxlength="255" class="input-medium "/>
						</td>
		                <td class="tit">所学专业：</td>
		                <td>
		                    <form:input path="specialty" htmlEscape="false" maxlength="50" class="input-medium "/>
		                </td>
		
		
		                <td class="tit">退休：</td>
		                <td colspan="">
		                    <form:radiobuttons path="retire" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
		                </td>
		            </tr>
		            <tr>
					<%--工作--%>
		
		                <td class="tit">从事专业：</td>
		
		                <td>
		
		                    <form:select path="engageSpecialty" class="input-medium " cssStyle="float: left ;margin-top: 5px;" onchange="engageSpecialtyChange(this.value,this)">
		                        <form:option value="" label=""/>
		                        <form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		                    </form:select>
							<div style="width: 180px;height: 40px; float: left; ">
		                        <font color="red">从事工程咨询行业的具体专业，不可修改请认真填写</font>
		                        </div>
		
		                </td>
		                <td class="tit">职称专业：</td>
		                <td>
		                    <form:input path="titleSpecialty" htmlEscape="false" maxlength="20" class="input-medium "/>
		                </td>
		                <td class="tit">职称：</td>
						<td>
		
		                    <form:select path="title" class="input-medium ">
		                        <form:option value="" label=""/>
		                        <form:options items="${fns:getDictList('worker_title')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		                    </form:select>
		                </td>
		
		            </tr>
		            <tr>
						
						<td class="tit">主专业：</td>
						<td>
							<form:select path="registerMainSpecialty" class="input-medium  " disabled="true">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</td>
						
						<td class="tit">辅助专业：</td>
						<td>
							<form:select path="registerAuxiliarySpecialty" class="input-medium " disabled="true">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</td>
		
						<td class="tit">从事工程咨询业务年限：</td>
						<td>
							<form:input path="ziXunYeWuNianXian" htmlEscape="false" maxlength="11" class="input-medium "/>
						</td>
		            </tr>
		
					<tr>
						<td class="tit">附件地址</td>
						<td colspan="5">
							<%--<input value="person${enterpriseWorkers.fileNo}" readOnly="true" />--%>
							<form:input path="" value="person${enterpriseWorkers.fileNo}"  htmlEscape="false" maxlength="200" class="input-xlarge" disabled="true"/>
						</td>
					</tr>
		            
					<tr>
						<td class="tit">备注：</td>
						<td colspan="5">
							<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-medium "/>
						</td>
					</tr>
			</c:when>
			<c:otherwise>
				<tr>

				<td class="tit">姓名：</td>
				<td>
					<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium " readonly="true"/>
				</td>
				<td class="tit">性别：</td>
				<td>
				    <form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""  readonly="true"/>
				</td>
		
				<td class="tit">证件类型：<br/>(中国大陆居民选择“身份证号”)</td>
				<td>
					<form:select path="certificatesType" class="input-medium "  disabled="true">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('ID_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
                </td>
            </tr>
            <tr>

                <td class="tit">手机：</td>
                <td>
                    <form:input path="mobile" htmlEscape="false" maxlength="15" class="input-medium mobile "  readonly="true"/>
                </td>

                <td class="tit">电话：</td>
                <td colspan="">
                    <form:input path="phone" htmlEscape="false" maxlength="15" class="input-medium  phone"  readonly="true"/>
                </td>


                <td class="tit">证件号：</td>
                <td>
                    <form:input path="certificatesNum" htmlEscape="false" maxlength="18" class="input-medium "  readonly="true"/>
                </td>
            </tr>
            <tr>
            <%--学历--%>
				<td class="tit">证书编号：</td>
				<td>
					<form:input path="registerCertificateNum" htmlEscape="false" maxlength="255" class="input-medium "  readonly="true"/>
				</td>
                <td class="tit">所学专业：</td>
                <td>

                   
                    <form:input path="specialty" htmlEscape="false" maxlength="255" class="input-medium "  readonly="true"/>
                </td>


                <td class="tit">退休：</td>
                <td colspan="">
                    <form:radiobuttons path="retire" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""  readonly="true"/>
                </td>
            </tr>
            <tr>
			<%--工作--%>

                <td class="tit">从事专业：</td>

                <td>

                    <form:select path="engageSpecialty" class="input-medium " cssStyle="float: left ;margin-top: 5px;" disabled="true">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"  readonly="true"/>
                    </form:select>
					<div style="width: 180px;height: 40px; float: left; ">
                        <font color="red">从事工程咨询行业的具体专业，不可修改请认真填写</font>
                        </div>

                </td>
                <td class="tit">职称专业：</td>
                <td>

                   
                    <form:input path="titleSpecialty" htmlEscape="false" maxlength="20" class="input-medium "  readonly="true"/>
                </td>
                <td class="tit">职称：</td>
				<td>

                    <form:select path="title" class="input-medium " >
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('worker_title')}" itemLabel="label" itemValue="value" htmlEscape="false"  readonly="true"/>
                    </form:select>
                </td>

            </tr>
            <tr>
				
				<td class="tit">主专业：</td>
				<td>
					<form:select path="registerMainSpecialty" class="input-medium  " disabled="true">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"  readonly="true"/>
					</form:select>
				</td>
				
				<td class="tit">辅助专业：</td>
				<td>
					<form:select path="registerAuxiliarySpecialty" class="input-medium " disabled="true">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"  readonly="true"/>
					</form:select>
				</td>

				<td class="tit">从事工程咨询业务年限：</td>
				<td>
					<form:input path="ziXunYeWuNianXian" htmlEscape="false" maxlength="11" class="input-medium "  />
				</td>
            </tr>

			<tr>
				<td class="tit">附件地址</td>
				<td colspan="5">
					<%--<input value="person${enterpriseWorkers.fileNo}" readOnly="true" />--%>
					<form:input path="" value="person${enterpriseWorkers.fileNo}"  htmlEscape="false" maxlength="200" class="input-xlarge"  readonly="true"/>
				</td>
			</tr>
            
			<tr>
				<td class="tit">备注：</td>
				<td colspan="5">
					<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-medium "  readonly="true"/>
				</td>
			</tr>
				</c:otherwise>
			</c:choose>
			

			<tr>
                <td class="tit">附件表：</td>
				<td colspan="5" >

                    <table id="contentTable" class="table table-striped table-bordered table-condensed"

                           cellpadding="0" cellspacing="0" style="border-collapse:collapse">
                        <thead>
                        <tr>
                            <th class="hide"></th>
                            <th>文件名称</th>
                            <th>附件类别</th>
                            <th>附件</th>

                            <shiro:hasPermission name="enterprise:enterpriseWorkers:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
                        </tr>
                        </thead>
                        <tbody id="enterpriseAttachmentList">
                        </tbody>
                        <shiro:hasPermission name="enterprise:enterpriseWorkers:edit"><tfoot>
                        <tr><td colspan="7"><a href="javascript:" onclick="addRow('#enterpriseAttachmentList', enterpriseAttachmentRowIdx, enterpriseAttachmentTpl);enterpriseAttachmentRowIdx = enterpriseAttachmentRowIdx + 1;" class="btn">新增附件</a></td></tr>
                        </tfoot></shiro:hasPermission>
                    </table>
							<script type="text/template" id="enterpriseAttachmentTpl">
                                //<!--
                                <tr id="enterpriseAttachmentList{{idx}}">
                                    <td class="hide">
                                        <input id="enterpriseAttachmentList{{idx}}_id" name="enterpriseAttachmentList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
                                        <input id="enterpriseAttachmentList{{idx}}_delFlag" name="enterpriseAttachmentList[{{idx}}].delFlag" type="hidden" value="0"/>
                                    </td>
                                    <td>
                                        <input id="enterpriseAttachmentList{{idx}}_fileName" name="enterpriseAttachmentList[{{idx}}].fileName" type="text" value="{{row.fileName}}" maxlength="100" class="input-medium "/>
                                    </td>
                                    <td>
                                        <select id="enterpriseAttachmentList{{idx}}_type" name="enterpriseAttachmentList[{{idx}}].type" data-value="{{row.type}}" class="input-medium ">
                                            <option value=""></option>
                                            <c:forEach items="${fns:getDictList('attachment_person_type')}" var="dict">
                                                <option value="${dict.value}">${dict.label}</option>
                                            </c:forEach>
                                        </select>
                                    </td>

                                    <td>
                                        <input id="enterpriseAttachmentList{{idx}}_path" name="enterpriseAttachmentList[{{idx}}].path" type="hidden" value="{{row.path}}" maxlength="100"/>
                                        <sys:ckfinder input="enterpriseAttachmentList{{idx}}_path" type="files" uploadPath="/person/person${enterpriseWorkers.fileNo}" selectMultiple="true"/>
                                    </td>
                                    <%--<td>
                                        <textarea id="enterpriseAttachmentList{{idx}}_remarks" name="enterpriseAttachmentList[{{idx}}].remarks" rows="4" maxlength="200" class="input-medium ">{{row.remarks}}</textarea>
                                    </td>--%>
                                    <shiro:hasPermission name="enterprise:enterpriseWorkers:edit"><td class="text-center" width="10">
                                        {{#delBtn}}<span class="close" onclick="delRow(this, '#enterpriseAttachmentList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
                                    </td></shiro:hasPermission>
                                </tr>//-->
							</script>
							<script type="text/javascript">
                                var enterpriseAttachmentRowIdx = 0, enterpriseAttachmentTpl = $("#enterpriseAttachmentTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
                                $(document).ready(function() {
                                    var data = ${fns:toJson(enterpriseWorkers.enterpriseAttachmentList)};
                                    for (var i=0; i<data.length; i++){
                                        addRow('#enterpriseAttachmentList', enterpriseAttachmentRowIdx, enterpriseAttachmentTpl, data[i]);
                                        enterpriseAttachmentRowIdx = enterpriseAttachmentRowIdx + 1;
                                    }
                                });
							</script>
				</td>


			</tr>
				
	</table>
		<div class="form-actions">
			<shiro:hasPermission name="enterprise:enterpriseWorkers:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>