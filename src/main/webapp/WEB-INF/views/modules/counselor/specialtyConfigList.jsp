<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专业匹配列表</title>
	<meta name="decorator" content="default"/>
	<script src="${pageContext.request.contextPath}/static/jquery-easyui-1.6.10/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {

        });
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function delRecord(id){
		    if(confirm("确定删除该数据吗?")){
		        location.href='${ctx}/counselor/specialtyConfig/delete?id='+id;
			}else{
		        return;
			}
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/counselor/specialtyConfig/listAllConfig">专业匹配列表</a></li>
		<li><a href="${ctx}/counselor/specialtyConfig/save?stage=1">专业匹配添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="specialtyConfig" action="${ctx}/counselor/specialtyConfig/listAllConfig" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<div class="inputType">
			<li><label>专业关键字：</label>
				<form:input path="specialtyLabel" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			</div>
        </ul>
	</form:form>
	<sys:message content="${message}"/>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>专业</th>
				<th>学历、培训、职称专业关键字</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="specialtyConfig" varStatus="index">
			<tr>
				<td> ${index.index+1}</td>
				<td>
						${fns:getDictLabel(specialtyConfig.specialty,'specialty_type','')}
						<%--${counselor.declareType}--%>
				</td>
				<td>
						${specialtyConfig.specialtyLabel}
				</td>
				<td>
					<a href="${ctx}/counselor/specialtyConfig/edit?id=${specialtyConfig.id}" style="cursor: pointer;">编辑</a>
					<a style="cursor: pointer;" onclick="delRecord('${specialtyConfig.id}')">删除</a>

				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>