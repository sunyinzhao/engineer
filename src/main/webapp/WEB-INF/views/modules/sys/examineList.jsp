<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3}).show();
		});
    	function updateSort() {
			loading('正在提交，请稍等...');
	    	$("#listForm").attr("action", "${ctx}/sys/examine/updateSort");
	    	$("#listForm").submit();
    	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/examine/">审查列表</a></li>
		<li><a href="${ctx}/sys/examine/form">审查添加</a></li>
	</ul>
	<sys:message content="${message}"/>
	<form id="listForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed hide">
			<thead><tr><th>名称</th>
			<th>${examine.kindOf }</th>
			</tr></thead>
			<tbody><c:forEach items="${list}" var="examine">
				<tr id="${examine.id}" pId="${examine.parent.id ne '1'?examine.parent.id:'0'}">
					<td nowrap><i class="icon-${not empty examine.icon?examine.icon:' hide'}"></i><a href="${ctx}/sys/examine/form?id=${examine.id}">${examine.name}</a></td>
					<td title="${examine.href}">${fns:abbr(examine.href,30)}</td>
					<td style="text-align:center;">
						<%--<shiro:hasPermission name="sys:examine:edit">--%>
							<input type="hidden" name="ids" value="${examine.id}"/>
							<input name="sorts" type="text" value="${examine.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
						<%--</shiro:hasPermission><shiro:lacksPermission name="sys:examine:edit">--%>
							<%--${examine.sort}--%>
						<%--</shiro:lacksPermission>--%>
					</td>
					<td>${examine.isShow eq '1'?'显示':'隐藏'}</td>
					<td title="${examine.permission}">${fns:abbr(examine.permission,30)}</td>
					<%--<shiro:hasPermission name="sys:examine:edit">--%><td nowrap>
						<a href="${ctx}/sys/examine/form?id=${examine.id}">修改</a>
						<a href="${ctx}/sys/examine/delete?id=${examine.id}" onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>
						<a href="${ctx}/sys/examine/form?parent.id=${examine.id}">添加下级菜单</a>
					</td><%--</shiro:hasPermission>--%>
				</tr>
			</c:forEach></tbody>
		</table>
		<shiro:hasPermission name="sys:examine:edit"><div class="form-actions pagination-left">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();"/>
		</div></shiro:hasPermission>
	 </form>
</body>
</html>