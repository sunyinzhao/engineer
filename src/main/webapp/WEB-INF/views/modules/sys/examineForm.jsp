<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>审查管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/examine/">审查列表</a></li>
		<li class="active"><a href="${ctx}/sys/examine/form?id=${examine.id}&parent.id=${examine.parent.id}">审查<shiro:hasPermission name="sys:examine:edit">${not empty examine.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:examine:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="examine" action="${ctx}/sys/examine/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">上级菜单:</label>
			<div class="controls">
                <sys:treeselect id="examine" name="parent.id" value="${examine.parent.id}" labelName="parent.name" labelValue="${examine.parent.name}"
					title="菜单" url="/sys/examine/treeData" extId="${examine.id}" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required input-xlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">链接:</label>
			<div class="controls">
				<form:input path="href" htmlEscape="false" maxlength="2000" class="input-xxlarge"/>
				<span class="help-inline">点击菜单跳转的页面</span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">目标:</label>
			<div class="controls">
				<form:input path="target" htmlEscape="false" maxlength="10" class="input-small"/>
				<span class="help-inline">链接地址打开的目标窗口，默认：mainFrame</span>
			</div>
		</div>--%>

		<%--<div class="control-group">
			<label class="control-label">图标:</label>
			<div class="controls">
				<sys:iconselect id="icon" name="icon" value="${examine.icon}"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="50" class="required digits input-small"/>
				<span class="help-inline">排列顺序，升序。</span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">可见:</label>
			<div class="controls">
				<form:radiobuttons path="isShow" items="${fns:getDictList('show_hide')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline">该菜单或操作是否显示到系统菜单中</span>
			</div>
		</div>--%>
		<%--<div class="control-group">
			<label class="control-label">权限标识:</label>
			<div class="controls">
				<form:input path="permission" htmlEscape="false" maxlength="100" class="input-xxlarge"/>
				<span class="help-inline">控制器中定义的权限标识，如：@RequiresPermissions("权限标识")</span>
			</div>
		</div>--%>

		<div class="control-group">
			<label class="control-label">预期值:</label>
			<div class="controls">
				<form:input path="predict" htmlEscape="false" maxlength="100" class="input-xlarge"/>
				<span class="help-inline"></span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">实际值:</label>
			<div class="controls">
				<form:input path="realValue" htmlEscape="false" maxlength="100" class="input-xlarge"/>
				<span class="help-inline"></span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">结果类型:</label>
			<div class="controls">
				<form:input path="resultType" htmlEscape="false" maxlength="100" class="input-xlarge"/>
				<span class="help-inline"></span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">结果:</label>
			<div class="controls">
				<form:input path="result" htmlEscape="false" maxlength="100" class="input-xlarge"/>
				<span class="help-inline"></span>
			</div>
		</div>

	<%--	<div class="control-group">
			<label class="control-label">申请单号:</label>
			<div class="controls">
				<form:input path="applyNo" htmlEscape="false" maxlength="100" class="input-xlarge"/>
				<span class="help-inline"></span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">列表类型(基本类型的项时,1.展示图片列表   当点击的是专业力量时 2.展示人员列表  当点击的是 业绩的时候,3 展示的是project列表):</label>
			<div class="controls">
				<form:input path="kindOf" htmlEscape="false" maxlength="100" class="input-xlarge"/>
				<span class="help-inline"></span>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">展示类型(1.基本,专业.2.信用.3.业绩):</label>
			<div class="controls">
				<form:input path="kind" htmlEscape="false" maxlength="100" class="input-xlarge"/>
				<span class="help-inline"></span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">文件类型:</label>
			<div class="controls">
				<form:input path="type" htmlEscape="false" maxlength="100" class="input-xlarge"/>
				<span class="help-inline"></span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">不符合规则原因:</label>
			<div class="controls">
				<form:input path="rejectType" htmlEscape="false" maxlength="100" class="input-xlarge"/>
				<span class="help-inline"></span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<%--<shiro:hasPermission name="sys:examine:edit">--%><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;<%--</shiro:hasPermission>--%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>