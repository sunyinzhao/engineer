<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申请单列表</title>
	<meta name="decorator" content="default"/>
	<script src="${pageContext.request.contextPath}/static/jquery-easyui-1.6.10/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		var specialtyLabel = "";
		$(document).ready(function() {
			<c:forEach items="${page.list}" var="counselor" varStatus="index">
			<c:if test="${index.last}">
            specialtyLabel = specialtyLabel+"${fns:getDictLabel(counselor.declareType, 'counselor_type', '')}";
			</c:if>
			<c:if test="${!index.last}">
            specialtyLabel = specialtyLabel+"${fns:getDictLabel(counselor.declareType, 'counselor_type', '')}"+",";
			</c:if>
			</c:forEach>
        });
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function delRecord(id,type,batchId){
		    if(type==2){
                url = '${ctx}/counselor/findBatchId?id='+id;
                var text = "";
                $.ajax({
                        type:'POST',
                        url:url,
                        dataType: 'text',
                        async: false,
                        success:function (data){
                            text=data;
                        },error:function (){
                            alert('ajax请求失败')
                        }
                    }
                )

				if(text.indexOf(",")>-1){
                    if(confirm("若删除变更单位登记事项,同批次内"+text+"将一并删除，是否继续?")){
                        location.href='${ctx}/counselor/deleteByBatchId?batchId='+batchId;
                    }else{
                        return;
                    }
                }else{
                    if(confirm("是否确认删除变更单位?")){
                        location.href='${ctx}/counselor/delete?id='+id;
                    }else{
                        return;
                    }
                }
			}else{
                if(confirm("是否确认删除?")){
                    location.href='${ctx}/counselor/delete?id='+id;
                }else{
                    return;
                }
            }


		}

		function recall(id){
		    location.href='${ctx}/counselor/recall?id='+id;
		}


		//添加新的功能, 若是三个
		function isSubmit(id,type){
		    var targetUrl= '${ctx}/counselor/isSubmit?id='+id+'&type='+type
            $.ajax({
                url:targetUrl,
                type:"POST",
                dataType:'text',
                success:function(data) {
                    if(data=='200'){
						location.href='${ctx}/counselor/firstIndex?id='+id+'&type='+type
                    }else {
                        alert(data)
                    }
                }
            })
		}


        function exportExcel() {
            location.href="${ctx}/counselor/cerateExcel/Excel4Record";
            /*confirm('确认', '确认把该搜索结果导出Excel表格 ？', function(r) {
                if (r) {
                    $.messager.progress({
                        title : '处理中',
                        msg : '请稍后',
                    });
                    $.messager.progress('close');
                    location.href="${ctx}/counselor/createExcel/Excel4Record";
                }
            });*/
        }

//         function updateDate() {
//             location.href="${ctx}/counselor/info/updateData";
//         }

//         function updateDate1() {
//             location.href="${ctx}/counselor/info/updateDataSame";
//         }

//         function updateDate2() {
//             location.href="${ctx}/counselor/info/saveExpert3";
//         }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">登记事项列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="counselor" action="${ctx}/counselor/search" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:input path="personId" type="hidden" />
		<ul class="ul-form">
			<li><label>登记类型：</label>
				<%--<form:input path="declareType" htmlEscape="false" maxlength="20" class="input-medium"/>--%>

				<form:select path="declareType" style="width:130px;" multiple="false" >
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('counselor_type')}" itemLabel="label" itemValue="value"  htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>登记日期：</label>
				<%--${counselor.startDate}--%>
				<input name="startDate" type="text"  maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${counselor.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<%--<form:input type="date" path="startDate" />--%>
				至
				<input name="endDate" type="text"  maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${counselor.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<%--<input type="date" name="startDate">
					至
				<input type="date" name="endDate">--%>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
<!--             <a id="consumesOutExcel" class="easyui-linkbutton" style="" data-options="iconCls:'icon-redo'" onclick="exportExcel()">导出表</a> -->
<!-- 			<a id="consumesOutExcel" class="easyui-linkbutton" style="" data-options="iconCls:'icon-redo'" onclick="updateDate()">更新小于35数据</a> -->
<!-- 			<a id="consumesOutExcel" class="easyui-linkbutton" style="" data-options="iconCls:'icon-redo'" onclick="updateDate1()">更新大于35的数据</a> -->
<!-- 			<a id="consumesOutExcel" class="easyui-linkbutton" style="" data-options="iconCls:'icon-redo'" onclick="updateDate2()">复制数据到ExamineType4</a> -->
        </ul>
	</form:form>
	<sys:message content="${message}"/>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>登记类型</th>
				<th>登记日期</th>
				<th>登记事项批次状态</th>
				<th>登记事项状态</th>
<!-- 				<th>专家结论</th> -->
				<th>公告结论</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="counselor" varStatus="index">
			<tr>
				<td> ${index.index+1}</td>
				<td>
						${fns:getDictLabel(counselor.declareType, 'counselor_type', '')}
						<%--${counselor.declareType}--%>
				</td>
				<td>
						<%--${fns:getDictLabel(enterpriseAttachment.type, 'attachment_type', '')}--%>
					<%--${counselor.declareDate}--%>
							<fmt:formatDate value="${counselor.declareDate}" type="date" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
						${fns:getDictLabel(counselor.batchStatus, 'counselor_status', '')}
				</td>
				<td>
						${fns:getDictLabel(counselor.declareStatus, 'counselor_status', '')}
				</td>
<!-- 				<td> -->
<%-- 						${fns:getDictLabel(counselor.zdeclareResult, 'final_result', '')} --%>
<!-- 				</td> -->
				<td>
					<c:if test="${counselor.batchStatus=='20'}">
						${fns:getDictLabel(counselor.fdeclareResult, 'final_result', '')}
					</c:if>
				</td>
				<td>
					<c:if test="${counselor.declareStatus=='1'||counselor.declareStatus=='4'||counselor.declareStatus=='14'||counselor.declareStatus=='22'}">
								<!--初次登记-->
							<a <%--href="${ctx}/counselor/firstIndex?id=${counselor.id}&type=${counselor.declareType}"--%> style="cursor: pointer;" onclick="isSubmit('${counselor.id}','${counselor.declareType}')">编辑</a>
							<a style="cursor: pointer;" onclick="delRecord('${counselor.id}','${counselor.declareType}','${counselor.batchId}')">删除</a>
<%-- 						<a href="${ctx}/counselor/examine/tree?recordId=${counselor.id}&type=1">单位测试</a> --%>
<%-- 						<a href="${ctx}/counselor/examine/tree?recordId=${counselor.id}&type=2">合规测试</a> --%>
<%-- 						<a href="${ctx}/counselor/examine/tree?recordId=${counselor.id}&type=3">预审测试</a> --%>
<%-- 						<a href="${ctx}/counselor/examine/tree?recordId=${counselor.id}&type=4">终审测试</a> --%>
<%-- 						<a href="${ctx}/counselor/examine/tree?recordId=${counselor.id}&type=5">复议测试</a> --%>
<%-- 						<a href="${ctx}/counselor/personExpert/addCheck?recordId=${counselor.id}&type=3">预审测试增加</a> --%>
<%-- 						<a href="${ctx}/counselor/personExpert/addCheck?recordId=${counselor.id}&type=4">终审测试增加</a> --%>
<%-- 						<a href="${ctx}/counselor/view/returnList">退回登记项列表</a> --%>
					</c:if>
					<c:if test="${counselor.declareStatus!='1'&&counselor.declareStatus!='4'&&counselor.declareStatus!='14'&&counselor.declareStatus!='22'}">
					<a href="${ctx}/counselor/firstIndex?id=${counselor.id}&type=${counselor.declareType}&look='1'">查看</a>
<%-- 						<a href="${ctx}/counselor/examine/tree?recordId=${counselor.id}&type=1">单位测试</a> --%>
<%-- 						<a href="${ctx}/counselor/examine/tree?recordId=${counselor.id}&type=2">合规测试</a> --%>
<%-- 						<a href="${ctx}/counselor/examine/tree?recordId=${counselor.id}&type=3">预审测试</a> --%>
<%-- 						<a href="${ctx}/counselor/examine/tree?recordId=${counselor.id}&type=4">终审测试</a> --%>
<%-- 						<a href="${ctx}/counselor/examine/tree?recordId=${counselor.id}&type=5">复议测试</a> --%>
<%-- 						<a href="${ctx}/counselor/personExpert/addCheck?recordId=${counselor.id}&type=3">预审测试增加</a> --%>
<%--                         <a href="${ctx}/counselor/personExpert/addCheck?recordId=${counselor.id}&type=4">终审测试增加</a> --%>
					</c:if>
					<c:if test="${counselor.declareStatus=='2'}">
						<a onclick="recall('${counselor.id}')" style="cursor:pointer">撤回</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>