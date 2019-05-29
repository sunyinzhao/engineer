<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>股权结构管理</title>
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
			$("#confirm").val("1");
			$("#searchForm").attr('action','${ctx}'+'/declare/declareRecord/saveConfirmStatus');    
			$("#searchForm").submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/enterprise/enterpriseStockProportion/">股权结构列表</a></li>
		<c:if test="${tableType ne '5' }"><shiro:hasPermission name="enterprise:enterpriseStockProportion:edit"><li><a href="${ctx}/enterprise/enterpriseStockProportion/form">股权结构添加</a></li></shiro:hasPermission></c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseStockProportion" action="${ctx}/enterprise/enterpriseStockProportion/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<input type="hidden" name="tableType" id="tableType" value="${tableType}">
		<input type="hidden" name="declareRecordId" id="declareRecordId" value="${declareRecordId}">
		<input type="hidden" name="confirm" id="confirm" >		
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<c:if test="${tableType eq '5' }">
				<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" onclick="complate()" type="button" value="确认完成"/>
				<a href="${ctx}/declare/declareRecord/form?id=${declareRecordId}"> <input id="btn btn-primary" class="btn" type="button" value="返 回" /></a>
				
				</li>
			</c:if>
			
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>股东名称</th>
				<th>股权比例%</th>
				<%--<th>修改时间</th>--%>
				<th>备注</th>
				<shiro:hasPermission name="enterprise:enterpriseStockProportion:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseStockProportion">
			<tr>
				<td><a href="${ctx}/enterprise/enterpriseStockProportion/form?id=${enterpriseStockProportion.id}">
					${enterpriseStockProportion.shareholder}
				</a></td>
				<td>
					${enterpriseStockProportion.stockProportion}
				</td>
				<%--<td>
					<fmt:formatDate value="${enterpriseStockProportion.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>--%>
				<td>
					${enterpriseStockProportion.remarks}
				</td>
				<shiro:hasPermission name="enterprise:enterpriseStockProportion:edit"><td>
    				<a href="${ctx}/enterprise/enterpriseStockProportion/form?id=${enterpriseStockProportion.id}">修改</a>
					<a href="${ctx}/enterprise/enterpriseStockProportion/delete?id=${enterpriseStockProportion.id}" onclick="return confirmx('确认要删除该股权结构吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>