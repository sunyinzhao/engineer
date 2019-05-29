<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>职称证书</title>
	<meta name="decorator" content="blank"/>
    <script>


        function sumbitForm1(){
            //每一项都是必填,需要进行判断. 毕业时间需必须大于起始时间
            //获取表单对象

            //时间进行判断
            var targetUrl = $("#inputForm").attr("action");
            var data = $("#inputForm").serialize();
            $.ajax({
                url:targetUrl,
                type:"POST",
                cache: false,
                data:data,
                dataType:'json',
                success:function(data) {
                    if(data=='200'){
                        alert("提交成功")
                        self.opener.location.reload();
                        //测试父页面进行刷新
                        window.close();
                    }else if(data=='201'){
                        alert("请填写完整")
                    }
                }
            })
        }

        //编辑
        function editJsp(id){
            location.href='${ctx}/counselor/self/editTitle?id='+id+'&type=2';
        }

        //删除
        function delJsp(id,personId){
            //需要弹窗.
            if(confirm("确定要删除此项吗?")){
                location.href='${ctx}/counselor/self/delTitle?id='+id+'&personId='+personId;
            }

        }

        //跳转添加页面
        function addTitle(personId){
            location.href='${ctx}/counselor/self/editTitle?personId='+personId+'&type=1';
        }
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
        <li class="active"><a href="${ctx}/counselor/self/queryTitle">职称证书列表</a></li>
            <c:if test="${readOnly == '200'}">
                <li><a href="${ctx}/counselor/self/editTitle?personId=${personId}&type=1">职称证书新增</a></li>
            </c:if>
	</ul>
	<form:form id="inputForm" modelAttribute="titleCertificate" action="${ctx}/counselor/saveForm1?tableId=${tableId}&personId=${personId}" method="post" class="form-horizontal">
		<sys:message content="${message}"/>

        <br/>

        <table id="contentTable" class="table table-striped table-bordered table-condensed">
            <%--<tr>--%>
                <%--<td colspan="20" height="60px" width="100%" align="center">--%>
                    <%--<span  style="font-size: 18px;align-content: center">职称证书--%>
                    <%--<c:if test="${readOnly == '200'}">--%>
                        <%--&nbsp;&nbsp;&nbsp;--%>
                        <%--<input id="btnSubmit" class="btn btn-primary" type="button" onclick="addTitle('${personId}')" value="新增"/>--%>
                    <%--</c:if>--%>
                    <%--</span>--%>
                <%--</td>--%>
            <%--</tr>--%>
                <tr align="center">
                    <th ><div align="center">职称级别<span style="color: red">*</span></div></th>
                    <th ><div align="center">职称类型<span style="color: red">*</span></div></th>
                    <th ><div align="center">专业</div></th>
                    <th ><div align="center">批准机构<span style="color: red">*</span></div></th>
                    <th ><div align="center">批准时间<span style="color: red">*</span></div></th>
                    <th ><div align="center">获得职称证书时的工作单位</div></th>
                    <th ><div align="center">操作</div></th>
                </tr>
            <c:forEach items="${list}" var="title">

                <tr>
                    <td >
                            ${fns:getDictLabel(title.titleLevel,'title_level' ,'' )}
                    </td>
                    <td >
                            ${fns:getDictLabel(title.titleType,'worker_title' ,'' )}
                    </td>
                    <td  >
                        ${title.specialty}
                    </td>
                    <td >
                        ${title.approveEmployer}
                    </td>
                    <td >
                        <fmt:formatDate value="${title.approveTime}" pattern="yyyy-MM-dd"/>
                        <%--<input name="approveTime" type="text"  maxlength="20" class="input-medium Wdate "--%>
                               <%--value="<fmt:formatDate value="${title.approveTime}" pattern="yyyy-MM-dd"/>"/>--%>
                    </td>
                    <td >
                            ${title.getEmployer}
                    </td>
                    <td>
                        <c:if test="${readOnly == '200'}">
                            <input type="button" value="编辑" class="btn" onclick="editJsp('${title.id}')"/>
                            <input type="button" value="删除" class="btn" onclick="delJsp('${title.id}','${title.personId}')"/>
                        </c:if>
                    </td>
                </tr>
                <%--<tr>--%>
                    <%----%>
                <%--</tr>--%>
            </c:forEach>
        </table>

        <br/>

	</form:form>


</body>
</html>