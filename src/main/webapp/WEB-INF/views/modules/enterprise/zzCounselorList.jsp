<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中咨协会咨询师列表</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.cssType input{
			width:150px;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function lockOver(name,certificatesNum){
        	name = encodeURI(name); 
        	window.location.href = "${ctx}/sys/user/zRegistrationForm?name="+name+"&certificatesNum="+certificatesNum;
        }
        
        function edit(id,userId){
        	window.location.href = "${ctx}/sys/user/infoShowAdmin?id="+id;
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/expert/expertDistribute/toBeAssignedApplys">待分配列表</a></li> --%>
		<li class="active"><a href="${ctx}/enterprise/auditAndReport/zzCounselorList">中咨协会咨询师列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterpriseWorkers" action="${ctx}/enterprise/auditAndReport/zzCounselorList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>归属地：</label><sys:treeselect id="officeId" name="officeId" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
				title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/>
			</li>
			<div class = "cssType">
				<li><label>单位名称：</label>
					<form:input path="companyName" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
			</div>
			<li><label>咨询师姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="certificatesNum" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
			<li><label style="width: 140px">执业登记证书编号：</label>
				<form:input path="registerCertificateNum" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
			<li><label>咨询师状态: </label>
				<form:select path="isValid" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('isValid')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>预审单位</th>
				<th>单位名称</th>
				<th>姓名</th>
				<th>性别</th>
				<th>证件号</th>
				<th>主专业</th>
				<th>辅专业</th>
				<th>咨询工程师（投资）状态</th>
				<th>冻结状态</th>
				<th>是否注册</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="enterpriseWorkers" varStatus="index">
			<tr>
				<td>
					<input type="hidden" name="userId" value="${enterpriseWorkers.userId}" >
					<input type="hidden" name="id" value="${enterpriseWorkers.id}" >
					${index.index+1}
				</td>
				<td>
					${enterpriseWorkers.officeName}
				</td>
				<td>
					${enterpriseWorkers.companyName}
				</td>
				<td>
					${enterpriseWorkers.name}
					<%-- ${fns:getDictLabel(enterpriseWorkers.title, 'worker_title', '')} --%>
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.sex, 'sex', '')}
				</td>
				<td>
					${enterpriseWorkers.certificatesNum}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.registerMainSpecialty ,"specialty_type" ,"" )}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.registerAuxiliarySpecialty  ,"specialty_type" ,"" )}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.isValid,'isValid','')}
				</td>
				<td>
					${fns:getDictLabel(enterpriseWorkers.isFreeze,'is_freeze','')}
				</td>
				<%-- <td>
					<fmt:formatDate value="${enterpriseWorkers.createDate}" type="date" pattern="yyyy-MM-dd"/>
				</td> --%>
				<td>
					${fns:getDictLabel(enterpriseWorkers.isRegister,'yes_no','')}
				</td>
				<td>
					<input type="button" value="查看" onclick="lockOver('${enterpriseWorkers.name}','${enterpriseWorkers.certificatesNum}')">
					<input type="button" value="修改" onclick="edit('${enterpriseWorkers.id}','${enterpriseWorkers.userId}')">
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>