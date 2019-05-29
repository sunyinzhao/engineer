<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>培训院校</title>
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
            location.href='${ctx}/counselor/self/editSpecialty?id='+id+'&type=2';
        }

        //删除
        function delJsp(id,personId){
            //需要弹窗.
            if(confirm("确定要删除此项吗?")){
                location.href='${ctx}/counselor/self/delSpecialty?id='+id+'&personId='+personId;
            }

        }

        //跳转添加页面
        function addSpecialty(personId){
            location.href='${ctx}/counselor/self/editSpecialty?personId='+personId+'&type=1';
        }
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/counselor/self/querySpecialty">信息</a></li>
		<li><a href="${ctx}/counselor/self/editSpecialty?personId=${personId}&type=1">添加</a></li>
		
	</ul>
	
	<form:form id="inputForm" modelAttribute="specialtyTrain" action="${ctx}/counselor/saveForm1?tableId=${tableId}&personId=${personId}" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<%-- <input id="btnSubmit" class="btn btn-primary" type="button" onclick="addSpecialty('${personId}')" value="新增"/> --%>

        <br/>

        <table id="contentTable" class="table table-striped table-bordered table-condensed">
            <tr>
                <td colspan="20" height="60px" width="100%" align="center"><span  style="font-size: 18px;align-content: center">培训院校
                    
                </span></td>
            </tr>
            <tr>
              <td>培训院校</td>
              <td>证书类型*</td>
              <td>所学专业*</td>
              <td>培训起始时间*</td>
              <td>培训终止时间*</td>
              <td>证书编号*</td>
              <td>学习方式*</td>
              <td>校长*</td>
              <td>颁证时间*</td>
              <td>操作</td>
            </tr>

            <c:forEach items="${list}" var="specialtyTrain">
                <tr>
                <td>${specialtyTrain.trainSchool }</td>
                    <td>
                        ${specialtyTrain.trainType}
                    </td>
                    <td >
                            ${specialtyTrain.specialty}
                    </td>
                    <td >
                        <fmt:formatDate value="${specialtyTrain.startTime}" pattern="yyyy-MM-dd"/>
                        <%--<input id="startTime" name="startTime" type="text"  maxlength="20" class="input-medium Wdate "--%>
                               <%--value="<fmt:formatDate value="${specialtyTrain.startTime}" pattern="yyyy-MM-dd"/>"--%>
                               <%--/>--%>
                    </td>
                    <td>
                        <fmt:formatDate value="${specialtyTrain.endTime}" pattern="yyyy-MM-dd"/>
                        <%--<input id="endTime" name="endTime" type="text"  maxlength="20" class="input-medium Wdate "--%>
                               <%--value="<fmt:formatDate value="${specialtyTrain.endTime}" pattern="yyyy-MM-dd"/>"--%>
                               <%--/>--%>
                    </td>

                    <td>  
                            ${specialtyTrain.cardnum}
                    </td>
                    <td>
                        
                            ${specialtyTrain.studyType}
                    </td>
                    <td>
                            ${specialtyTrain.schoolMaster}
                    </td>
                    <td>
                        <fmt:formatDate value="${specialtyTrain.getgctime}" pattern="yyyy-MM-dd"/>
                        <%--<input name="getgctime" type="text"  maxlength="20" class="input-medium Wdate "--%>
                               <%--value="<fmt:formatDate value="${specialtyTrain.getgctime}" pattern="yyyy-MM-dd"/>"--%>
                               <%--/>--%>
                    </td>
                    <td>
                        <c:if test="${readOnly == '200'}">
                                <input type="button" value="编辑" class="btn btn-primary" onclick="editJsp('${specialtyTrain.id}')"/>
                                <input type="button" value="删除" class="btn" onclick="delJsp('${specialtyTrain.id}','${specialtyTrain.personId}')"/>
                        </c:if>
                    </td>
                </tr>
                
            </c:forEach>
                </table>
	</form:form>
</body>
</html>