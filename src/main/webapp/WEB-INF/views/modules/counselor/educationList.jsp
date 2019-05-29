<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学历教育情况</title>
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
            location.href='${ctx}/counselor/self/editEducation?id='+id+'&type=2';
        }

        //删除
        function delJsp(id,personId){
            //需要弹窗.
            if(confirm("确定要删除此项吗?")){
                location.href='${ctx}/counselor/self/delEducation?id='+id+'&personId='+personId;
            }

        }

        //跳转添加页面
        function addEducation(personId){
            location.href='${ctx}/counselor/self/editEducation?personId='+personId+'&type=1';
        }
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
        <li class="active"><a href="${ctx}/counselor/self/queryEducation">学历教育列表</a></li>
        <c:if test="${readOnly == '200'}">
            <li><a href="${ctx}/counselor/self/editEducation?personId=${personId}&type=1">学历教育新增</a></li>
        </c:if>
	</ul>
	<form:form id="inputForm" modelAttribute="educationtbl" action="${ctx}/counselor/saveForm1?tableId=${tableId}&personId=${personId}" method="post" class="form-horizontal">
		<sys:message content="${message}"/>

        <br/>

        <table id="contentTable" class="table table-striped table-bordered table-condensed">
            <%--<tr>--%>
                <%--<td colspan="20" height="60px" width="100%" align="center">--%>
                    <%--<span  style="font-size: 18px;align-content: center">学历教育情况--%>
                    <%--<c:if test="${readOnly == '200'}">--%>
                <%--&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="button" onclick="addEducation('${personId}')" value="新增"/>--%>
                    <%--</c:if>--%>
                    <%--</span>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr align="center">
                <th ><div align="center">办学类型<span style="color: red">*</span></div></th>
                <th ><div align="center">毕业院校<span style="color: red">*</span></div></th>
                <th ><div align="center">所学专业<span style="color: red">*</span></div></th>
                <th ><div align="center">学习方式<span style="color: red">*</span></div></th>
                <th ><div align="center">学历<span style="color: red">*</span></div></th>
                <th ><div align="center">学制(年)<span style="color: red">*</span></div></th>
                <th ><div align="center">操作</div></th>
            </tr>
            <c:forEach items="${list}" var="educationtbl">
                <tr align="center">
                    <td >${fns:getDictLabel(educationtbl.schoolType,"school_type" ,"" )}</td>
                    <td >${educationtbl.school}</td>
                    <td >${educationtbl.specialty}</td>
                    <td >${fns:getDictLabel(educationtbl.studyType,"study_type" ,"" )}</td>
                    <td >${fns:getDictLabel(educationtbl.education,"education" ,"" )}</td>
                    <td >${educationtbl.studyYear}</td>
                    <td colspan="20" align="right">
                        <c:if test="${readOnly == '200'}">
                            <input type="button" value="编辑" class="btn" onclick="editJsp('${educationtbl.id}')"/>
                            <input type="button" value="删除" class="btn" onclick="delJsp('${educationtbl.id}','${educationtbl.personId}')"/>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>


            <%--<c:forEach items="${list}" var="educationtbl">--%>
            <%--<tr>--%>
                <%--<td colspan="4" height="50px" width="20%" >办学类型<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                            <%--${fns:getDictLabel(educationtbl.schoolType,"school_type" ,"" )}--%>
                <%--</td>--%>
                <%--<td colspan="4" width="20%" >毕业院校<span style="color: red">*</span></td>--%>
                <%--<td colspan="6" width="30%" >${educationtbl.school}</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
               <%--<td colspan="4" height="30px" width="20%" >所学专业<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                        <%--${educationtbl.specialty}--%>
                <%--</td>--%>
                <%--<td colspan="4"  width="20%" >学习方式<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                        <%--${fns:getDictLabel(educationtbl.studyType,"study_type" ,"" )}--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td colspan="4" height="30px" width="20%" >学历<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                        <%--${fns:getDictLabel(educationtbl.education,"education" ,"" )}--%>
                <%--</td>--%>
                <%--<td colspan="4"  width="20%" >学制(年)<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                        <%--${educationtbl.studyYear}--%>
                <%--</td>--%>

            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td colspan="4" height="30px" width="20%" >起始时间<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                    <%--<input id="startTime" name="startTime" type="text"  maxlength="20" class="input-medium Wdate " cssStyle="width: 99%"--%>
                           <%--value="<fmt:formatDate value="${educationtbl.startTime}" pattern="yyyy-MM-dd"/>"/>--%>
                <%--</td>--%>
                <%--<td colspan="4"  width="20%" >毕业时间<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                    <%--<input id="endTime" name="endTime" type="text" maxlength="20" class="input-medium Wdate " cssStyle="width: 99%"--%>
                           <%--value="<fmt:formatDate value="${educationtbl.endTime}" pattern="yyyy-MM-dd"/>"/>--%>
                <%--</td>--%>

            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td colspan="4" height="30px" width="20%" >校(院)长<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                        <%--${educationtbl.schoolMaster}--%>

                <%--</td>--%>
                <%--<td colspan="4"  width="20%" >证书编号<span style="color: red">*</span></td>--%>
                <%--<td colspan="6"  width="30%" >--%>
                        <%--${educationtbl.zsNo}--%>
                <%--</td>--%>
            <%--</tr>--%>


                <%--<tr>--%>

                    <%--<td colspan="20" align="right">--%>
                        <%--<c:if test="${readOnly == '200'}">--%>
                                <%--<input type="button" value="编辑" class="btn btn-primary" onclick="editJsp('${educationtbl.id}')"/>--%>
                                <%--<input type="button" value="删除" class="btn" onclick="delJsp('${educationtbl.id}','${educationtbl.personId}')"/>--%>
                        <%--</c:if>--%>
                    <%--</td>--%>

                <%--</tr>--%>

            <%--</c:forEach>--%>
        </table>

        <br/>

	</form:form>


</body>
</html>