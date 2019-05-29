<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>附件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

        function complate(){

            $("#searchForm").attr('action','${ctx}'+'/declare/declareRecord/saveConfirmStatus?tableType=7&confirm=1');
            $("#searchForm").submit();
        }

        function returnPre() {
            window.document.location="${ctx}/declare/declareRecord/form?id=${declareRecordId}&declareRecordStatus=${declareRecordStatus}";
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">附件列表</a></li>
		<c:if test="${declareRecordStatus==0}">

			<shiro:hasPermission name="enterprise:enterpriseAttachment:edit"><li><a href="${ctx}/enterprise/enterpriseAttachment/form?declareRecordId=${declareRecordId}&declareRecordStatus=${declareRecordStatus}&declareRecordFileNo=${declareRecordFileNo}">附件添加</a></li></shiro:hasPermission>

		</c:if>
		<c:if test="${declareRecordStatus==1}">

		<shiro:hasPermission name="enterprise:enterpriseAttachment:edit"><li><a href="${ctx}/enterprise/enterpriseAttachment/form?declareRecordId=${declareRecordId}&declareRecordStatus=${declareRecordStatus}&declareRecordFileNo=${declareRecordFileNo}">附件添加</a></li></shiro:hasPermission>

		</c:if>

	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseAttachment" action="${ctx}/enterprise/enterpriseAttachment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id = "declareRecordId" name="declareRecordId" type="hidden" value="${declareRecordId}"/>
		<ul class="ul-form">
			<%--<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>--%>
				<c:if test="${declareRecordStatus==0}">
				<input  class="btn btn-primary" onclick="complate()" type="button" value="确认完成" />
				</c:if>
				<c:if test="${declareRecordStatus==1}">
					<input  class="btn btn-primary" onclick="complate()" type="button" value="确认完成" />
				</c:if>

                <input id="btn btn-primary" class="btn" type="button" value="返 回" onclick="returnPre()" />

        </ul>
	</form:form>
	<sys:message content="${message}"/>

	<input type="hidden" value="${declareRecordId}" id="declareRecordId" name="declareRecordId">
	<%--<form:hidden path="declareRecordId"/>--%>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>文件名称</th>
				<th>文件类型</th>
				<th>备注</th>
				<shiro:hasPermission name="enterprise:enterpriseAttachment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseAttachment" varStatus="index">
			<tr>
				<td> ${index.index+1}</td>
				<td>
						${enterpriseAttachment.fileName}
				</td>

				<td>
						${fns:getDictLabel(enterpriseAttachment.type, 'attachment_type', '')}
				</td>

				<%--
					<c:if test="${enterpriseAttachment.type>10}">
						个人
					</c:if>
					<c:if test="${enterpriseAttachment.type<=10}">
						单位
					</c:if>
				</td>--%>

				<td>
					${enterpriseAttachment.remarks}
				</td>
				<shiro:hasPermission name="enterprise:enterpriseAttachment:edit"><td>
					<c:if test="${declareRecordStatus==0}">
						<a href="${ctx}/enterprise/enterpriseAttachment/form?id=${enterpriseAttachment.id}&declareRecordId=${declareRecordId}&declareRecordStatus=${declareRecordStatus}&declareRecordFileNo=${declareRecordFileNo}">修改</a>
						<a href="${ctx}/enterprise/enterpriseAttachment/delete?id=${enterpriseAttachment.id}&declareRecordStatus=${declareRecordStatus}&declareRecordFileNo=${declareRecordFileNo} " onclick="return confirmx('确认要删除该附件吗？', this.href)">删除</a>
					</c:if>
					<c:if test="${declareRecordStatus==1}">
    				<a href="${ctx}/enterprise/enterpriseAttachment/form?id=${enterpriseAttachment.id}&declareRecordId=${declareRecordId}&declareRecordStatus=${declareRecordStatus}&declareRecordFileNo=${declareRecordFileNo}">修改</a>
					<a href="${ctx}/enterprise/enterpriseAttachment/delete?id=${enterpriseAttachment.id}&declareRecordStatus=${declareRecordStatus}&declareRecordFileNo=${declareRecordFileNo} " onclick="return confirmx('确认要删除该附件吗？', this.href)">删除</a>
					</c:if>

					<c:if test="${declareRecordStatus==2}">
						<a href="${ctx}/enterprise/enterpriseAttachment/form?id=${enterpriseAttachment.id}&declareRecordId=${declareRecordId}&declareRecordStatus=${declareRecordStatus}&declareRecordFileNo=${declareRecordFileNo}">查看</a>
					</c:if>

					<c:if test="${declareRecordStatus==3}">
						<a href="${ctx}/enterprise/enterpriseAttachment/form?id=${enterpriseAttachment.id}&declareRecordId=${declareRecordId}&declareRecordStatus=${declareRecordStatus}&declareRecordFileNo=${declareRecordFileNo}">查看</a>
					</c:if>

				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>