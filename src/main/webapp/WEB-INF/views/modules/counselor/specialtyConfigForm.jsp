<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学历教育情况</title>
	<meta name="decorator" content="blank"/>
    <script>
        function sumbitForm1(){
            var specialty = $("#specialty").val();
            var specialtyLabel = $("#specialtyLabel").val();
            if(specialty==null||specialty==""||typeof specialty =="undefined"||specialtyLabel==null||specialtyLabel==""||typeof specialtyLabel=="undefined"){
                alert("请填写完整");
                return;
            }
            var regEn = /[`!@#$%^&*()_+<>?:"{}.\/;'[\]]/im,
                regCn = /[·！#￥（——）：；“”‘，|《。》？【】[\]]/im;
            if(regEn.test(specialtyLabel) || regCn.test(specialtyLabel)) {
                alert('您输入了非法字符，请重新输入');
                return ;
            }
            $("#inputForm").submit();

        }

        function checkSpecialty() {
            var specialty = $("#specialty").val();
            $.ajax({
                url:"${ctx}/counselor/specialtyConfig/checkSpecialty?specialty="+specialty,
                type:'POST',
                dataType: 'text',
                success:function (date){
                    if(date != 0){
                        alert("当前专业已经被选择，请选择其他专业");
                        var selectObjJqu = $("#specialty");
                        $("#specialty option:selected").removeAttr("selected");
                        var span = selectObjJqu.parent().find("[class='select2-chosen']");
                        var sp=$(span[0]);
                        sp.text("--请选择--");

                    }
                },error:function (){
                    alert('ajax请求失败')
                }

            })
        }
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
        <li ><a href="${ctx}/counselor/specialtyConfig/listAllConfig">专业匹配列表</a></li>
        <li class="active"><a href="${ctx}/counselor/specialtyConfig/save?stage=1">专业匹配添加</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="specialtyConfig" action="${ctx}/counselor/specialtyConfig/save?stage=2" method="post" class="form-horizontal">
        <form:input path="id" type="hidden"/>
		<sys:message content="${message}"/>
        <legend>基本情况</legend>
        <table id="contentTable" class="table table-striped table-bordered table-condensed">
            <tr>
                <td colspan="4" height="50px" width="20%" >咨询专业<span style="color: red">*</span></td>
                <td colspan="8"  width="30%" >
                    <form:select path="specialty" class="input-medium " cssStyle="width: 99%" onchange="checkSpecialty()">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </td><td colspan="4" height="50px" width="20%"></td><td colspan="8"  width="30%"></td>
            </tr>
            <tr>
                <td colspan="4" height="50px" width="20%" >学历、培训、职称专业关键字<span style="color: red">*</span></td>
                <td colspan="8"  width="30%" ><form:input path="specialtyLabel" class="input-medium required" htmlEscape="false"></form:input>
                    <span style="color: red" >&nbsp;&nbsp;&nbsp;每个关键字之间用半角逗号隔开</span>
                </td>
            </tr>
        </table>

        <br/>
		<div class="form-actions">
            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="sumbitForm1()" value="提交"/>
		</div>
	</form:form>


</body>
</html>