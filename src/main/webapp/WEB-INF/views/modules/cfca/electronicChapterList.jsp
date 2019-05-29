<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>执业资格证书管理</title>
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
		function uploaddata()
		{
			$("#searchForm").attr("action","${ctx}/${adminPath}/counselor/updateService/updateWorker");
// 			$("#searchForm").attr("action","${ctx}/${adminPath}/counselor/updateService/updateWorker?uuid=ferfaf4g4t4093ff329jg&pageNo=1&pageSize=10");
    		$("#searchForm").submit();
		}

        function deleteReDate(){
            $.jBox.tip('正在下发电子证书，请稍等...','loading');
            var url = "${ctx}/cfca/electronicChapter/deleteAgain";
            var enCodeUri= encodeURI(url);
            $.ajax({
                type: 'POST',
                url: enCodeUri,
                dataType: 'text',
                contentType : 'application/text;charset=UTF-8',
                success:function(data) {
                    $.jBox.closeTip()
                    if(data =="Y"){
                        alert("删除成功");
                    }else{
                        alert("删除失败");
                    }
                },
                error:function(){
                    $.jBox.closeTip();
                    alert("ajax请求失败");
                }
            });
        }


        function updateTwoCode(){
            $.jBox.tip('正在更新电子证书下载码，请稍等...','loading');
            var url = "${ctx}/cfca/electronicChapter/updateApplyAssociationElectronicChapterTwoCode";
            var enCodeUri= encodeURI(url);
            $.ajax({
                type: 'POST',
                url: enCodeUri,
                dataType: 'text',
                contentType : 'application/text;charset=UTF-8',
                success:function(data) {
                    $.jBox.closeTip()
                    if(data =="Y"){
                        alert("更新成功");
                    }else{
                        alert("更新失败");
                    }
                },
                error:function(){
                    $.jBox.closeTip();
                    alert("ajax请求失败");
                }
            });
        }
        function sysStatus(id){
            $.jBox.tip('正在同步证书状态，请稍等...','loading');
            var url = "${ctx}/cfca/electronicChapter/sysStatus?chapterId="+id;
            var enCodeUri= encodeURI(url);
            $.ajax({
                type: 'POST',
                url: enCodeUri,
                dataType: 'text',
                contentType : 'application/text;charset=UTF-8',
                success:function(data) {
                    $.jBox.closeTip()
                    if(data =="Y"){
                        alert("更新成功");
                    }else{
                        alert("更新失败");
                    }
                },
                error:function(){
                    $.jBox.closeTip();
                    alert("ajax请求失败");
                }
            });
        }

        function updateStatus(id){
            $.jBox.tip('正在更新电子证书，请稍等...','loading');
            var url = "${ctx}/cfca/electronicChapter/updateStatus?chapterId="+id;
            var enCodeUri= encodeURI(url);
            $.ajax({
                type: 'POST',
                url: enCodeUri,
                dataType: 'text',
                contentType : 'application/text;charset=UTF-8',
                success:function(data) {
                    $.jBox.closeTip()
                    if(data =="Y"){
                        alert("更新成功");
                    }else{
                        alert("更新失败");
                    }
                },
                error:function(){
                    $.jBox.closeTip();
                    alert("ajax请求失败");
                }
            });
        }




	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cfca/electronicChapter/">执业资格证书列表</a></li>
		<shiro:hasPermission name="qualificationcertificate:qualificationCertificate:edit"><li><a href="${ctx}/qualificationcertificate/qualificationCertificate/form">执业资格证书添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cfcaElectronicChapter" action="${ctx}/cfca/electronicChapter/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		 <ul class="ul-form">

			 <li><label>执业单位：</label>
				 <form:input path="company.name" htmlEscape="false" maxlength="20" class="input-small"/>
			 </li>
			 <li><label>姓名：</label>
				 <form:input path="worker.name" htmlEscape="false" maxlength="20" class="input-small"/>
			 </li>
			 <li><label>身份证号：</label>
				 <form:input path="worker.certificatesNum" htmlEscape="false" maxlength="20" class="input-small"/>
			 </li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>

			 <li class="btns"><input id="btn" class="btn btn-primary" onclick="deleteReDate()" type="button" value="删除重复数据"/></li>
			 <li class="btns"><input id="utc" class="btn btn-primary" onclick="updateTwoCode()" type="button" value="更新未下载证书下载码"/></li>


			<li class="clearfix"></li>

		</ul> 
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				
				<th>执业单位</th>
				<th>姓名</th>
				<th>身份证号</th>
				<th>电子证书编号</th>
				<th>批准日期</th>
				<th>截止日期</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cfcaElectronicChapter">
			<tr>
				<td>
						${cfcaElectronicChapter.company.name}
				</td>
				<td>
						${cfcaElectronicChapter.worker.name}
				</td>
				<td>
						${cfcaElectronicChapter.worker.certificatesNum}
				</td>

				<td>
					${cfcaElectronicChapter.serialNo}
				</td>
				<td>
				<fmt:formatDate value="${cfcaElectronicChapter.startTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
				<fmt:formatDate value="${cfcaElectronicChapter.endTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${cfcaElectronicChapter.status}
				</td>
				<td>
    				<a href="${ctx}/cfca/electronicChapter/form?id=${cfcaElectronicChapter.id}">修改</a>
					<a href="#" onclick="sysStatus('${cfcaElectronicChapter.id}')">同步状态</a>
					<a href="#" onclick="updateStatus('${cfcaElectronicChapter.id}')">证书更新</a>
					<a href="${ctx}/cfca/electronicChapter/delete?id=${cfcaElectronicChapter.id}" onclick="return confirmx('确认要删除该执业资格证书吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>