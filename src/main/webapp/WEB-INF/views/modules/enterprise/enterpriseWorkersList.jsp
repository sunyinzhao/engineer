<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

		$(document).ready(function() {
            $("#btnImport").click(function(){
                $.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
            });
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
<div id="importBox" class="hide">
	<form id="importForm" action="${ctx}/enterprise/enterpriseWorkers/import" method="post" enctype="multipart/form-data"
		  class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
		<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
		<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
		<a href="${ctx}/sys/user/import/template">下载模板</a>
	</form>
</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/enterprise/enterpriseWorkers/">人员列表</a></li>
		<shiro:hasPermission name="enterprise:enterpriseWorkers:edit"><li><a href="${ctx}/enterprise/enterpriseWorkers/form?remark=1">人员添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/enterpriseWorkers/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>职称：</label>
				<form:input path="title" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>

			<!-- <li class="btns">
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
			</li> -->
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>职称</th>
				<th>从事专业</th>
				<th>主专业</th>
				<th>辅专业</th>
				<th>人员类别</th>
				<shiro:hasPermission name="enterprise:enterpriseWorkers:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseWorkers">
			<tr>
				<td><a href="${ctx}/enterprise/enterpriseWorkers/form?id=${enterpriseWorkers.id}">
					${enterpriseWorkers.name}
				</a></td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.title, 'worker_title', '')}
				</td>
				
				<td>
					${fns:getDictLabel(enterpriseWorkers.engageSpecialty, 'specialty_type', '')}
				</td>
				
				<td>
					${fns:getDictLabel(enterpriseWorkers.registerMainSpecialty, 'specialty_type', '')}
				</td>
				
				<td>
					${fns:getDictLabel(enterpriseWorkers.registerAuxiliarySpecialty, 'specialty_type', '')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.type,'worker_type','')}
				</td>
				<shiro:hasPermission name="enterprise:enterpriseWorkers:edit"><td>
    				<a href="${ctx}/enterprise/enterpriseWorkers/form?id=${enterpriseWorkers.id}">修改</a>
<%--     				<c:if test="${enterpriseWorkers.type==2}"> --%>
<%-- 					<a href="${ctx}/enterprise/enterpriseWorkers/delete?id=${enterpriseWorkers.id}" onclick="return confirmx('确认要删除该人员吗？', this.href)">删除</a> --%>
<%-- 					</c:if> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>