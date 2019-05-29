<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>非咨变更管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
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
        function saveBaseInfo(){
            $("#status").val("1");
            $("#inputForm").submit();
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/enterprise/enterpriseChangeInfo/">非咨变更列表</a></li>
		<li class="active"><a href="${ctx}/enterprise/enterpriseChangeInfo/form?id=${enterpriseChangeInfo.id}">非咨变更<shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit">${not empty enterpriseChangeInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="enterprise:enterpriseChangeInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="enterpriseChangeInfo" action="${ctx}/enterprise/enterpriseChangeInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
        <form:hidden path="status"/>

		<sys:message content="${message}"/>
        <legend>企业信息变更申请单</legend>
        <table class="table-form">
            <tr>

                <td class="tit">企业名称：</td>
                <td>
                    <form:input path="nameNew" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
                </td>

                <td class="tit">原企业名称：</td>
                <td>
                    <form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge  " readonly="true"/>
                </td>

            </tr>

            <tr>

                <td class="tit">法人：</td>
                <td>
                    <form:input path="legalPersonNew" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
                </td>

                <td class="tit">原法人：</td>
                <td>
                    <form:input path="legalPerson" htmlEscape="false" maxlength="100" class="input-xlarge required " readonly="true"/>
                </td>

            </tr>

            <tr>

                <td class="tit">注册地址：</td>
                <td>
                    <form:input path="registerAddressNew" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
                </td>

                <td class="tit">原注册地址：</td>
                <td>
                    <form:input path="registerAddress" htmlEscape="false" maxlength="100" class="input-xlarge  required" readonly="true"/>
                </td>

            </tr>

            <tr>
                <td class="tit">备案号：</td>
                <td>
                    <form:input path="applicationCodeNew" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
                </td>

                <td class="tit">原备案号：</td>
                <td>
                    <form:input path="applicationCode" htmlEscape="false" maxlength="100" class="input-xlarge required " readonly="true"/>
                </td>
            </tr>
            <tr>
                <td class="tit">备注：</td>
                <td colspan="3">
                    <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
                </td>

            </tr>

            <tr>
                <td class="tit">附件表：</td>
                <td colspan="3">
                    <table id="contentTable" class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th class="hide"></th>
                            <th>文件名称</th>
                            <th>附件类别</th>
                            <th>文件地址</th>
                            <th>备注</th>
                            <shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
                        </tr>
                        </thead>
                        <tbody id="enterpriseAttachmentList">
                        </tbody>
                        <shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><tfoot>
                        <tr><td colspan="7"><a href="javascript:" onclick="addRow('#enterpriseAttachmentList', enterpriseAttachmentRowIdx, enterpriseAttachmentTpl);enterpriseAttachmentRowIdx = enterpriseAttachmentRowIdx + 1;" class="btn">新增</a></td></tr>
                        </tfoot></shiro:hasPermission>
                    </table>
                    <script type="text/template" id="enterpriseAttachmentTpl">//<!--
						<tr id="enterpriseAttachmentList{{idx}}">
							<td class="hide">
								<input id="enterpriseAttachmentList{{idx}}_id" name="enterpriseAttachmentList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="enterpriseAttachmentList{{idx}}_delFlag" name="enterpriseAttachmentList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="enterpriseAttachmentList{{idx}}_fileName" name="enterpriseAttachmentList[{{idx}}].fileName" type="text" value="{{row.fileName}}" maxlength="100" class="input-small required"/>
							</td>
							<td>
								<select id="enterpriseAttachmentList{{idx}}_type" name="enterpriseAttachmentList[{{idx}}].type" data-value="{{row.type}}" class="input-small required ">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('attachment_type')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="enterpriseAttachmentList{{idx}}_path" name="enterpriseAttachmentList[{{idx}}].path" type="hidden" value="{{row.path}}" maxlength="100" class="required"/>
								<sys:ckfinder input="enterpriseAttachmentList{{idx}}_path" type="files" uploadPath="/enterprise" selectMultiple="true" />
							</td>
							<td>

							    <input id="enterpriseAttachmentList{{idx}}_remarks" name="enterpriseAttachmentList[{{idx}}].remarks" type="text" value="{{row.remarks}}" maxlength="100" class="input-small "/></td>
							<shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#enterpriseAttachmentList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
                    </script>
                    <script type="text/javascript">
                        var enterpriseAttachmentRowIdx = 0, enterpriseAttachmentTpl = $("#enterpriseAttachmentTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
                        $(document).ready(function() {
                            var data = ${fns:toJson(enterpriseChangeInfo.enterpriseAttachmentList)};
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


			<shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
            <shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" onclick="saveBaseInfo();"/>&nbsp;</shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>