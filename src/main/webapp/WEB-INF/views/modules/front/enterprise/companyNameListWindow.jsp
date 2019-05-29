<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位名称</title>
	<link href="${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet">
	<link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet">
	<link href="${ctxStatic}/modules/cms/front/themes/basic/style.css" type="text/css" rel="stylesheet">
	<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/modules/cms/front/themes/basic/script.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.method.min.js" type="text/javascript"></script>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		/* function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    } */
	    $(document).ready(function() {
	    	$("#searchForm").validate({
                rules: {
                    name:{required:true}
                },
                messages: {
                	name:{
						required:"必填"
					}
                },
                submitHandler: function(form){
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
	    })
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="user" action="${pageContext.request.contextPath}/enterprise/companyNameWidowSearch" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<ul class="ul-form">
				<li><label><h5>单位名称：</h5></label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			</ul>
		</ul>
		</form:form>
		<form:form modelAttribute="user" action="#" method="post" class="breadcrumb form-search">
		<table id="contentTable" border="1px" style="width: 100%">
			<thead>
				<tr>
					<th width="20%">选择/单选</th>
					<th>单位名称</th>
				</tr>
			</thead>
			<tbody>
			<%-- <c:forEach items="${page.list}" var="user"> --%>
			<c:forEach items="${page}" var="user">
				<tr>
					<td>
						<input type="radio" name="name" style="text-align:center;padding:10px 20px;width:100px;" value="${user.name }" >
						<input type="hidden" name="${user.name }" value="${user.id }" >
						<input type="hidden" name="${user.name }1" value="${user.officeId }" >
					</td>
					<td>
						${user.name}
					</td>
				</tr>
			</c:forEach>
		</tbody>
		</form:form>
		<%-- <div class="pagination">${page}</div> --%>
</body>
</html>