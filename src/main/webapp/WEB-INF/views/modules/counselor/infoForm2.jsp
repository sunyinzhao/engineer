<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>职称证书</title>
	<meta name="decorator" content="blank"/>
    <script>
       /* $(document).ready(function (){
            var value  = $("#titleLevel").val();
            var html = '';
            var result = $("#titleType").val();
            if(value!=null&&value!=''){
                $.ajax({
                    url:'${ctx}/counselor/title/ajaxTitle?value='+value,
                    type:'post',
                    dataType:'json',
                    success:function(data){

                        /!*html+='<select name="titleType" style="width: 120px">'*!/
                        //加一条空的

                        html+='<option value='+""+'></option>'
                        for(var i = 0;i<data.length;i++){
                            if(data[i].value==result){
                                html += '<option value=' + data[i].value + ' selected="selected">' + data[i].label + '</option>'
                            }else {
                                html += '<option value=' + data[i].value + '>' + data[i].label + '</option>'
                            }
                        }
                       /!* html+='</select>'*!/
                        $("#typeList").append(html)
                    }
                });
            }
        })*/
       $(document).ready(function (){
           var value  = $("#titleLevel").val();
           var html = '';
           var result = $("#titleType").val();
           if(value!=null&&value!=''){
               $.ajax({
                   url:'${ctx}/counselor/title/ajaxTitle?value='+value,
                   type:'post',
                   dataType:'json',
                   success:function(data){
                       //加一条空的
                        html+='<select class="select2-container input-medium" style="width: 120px" name="titleType" id="selectType">'
                       html+='<option value='+""+'></option>'
                       for(var i = 0;i<data.length;i++){
                           if(data[i].value==result){
                               html += '<option value=' + data[i].value + ' selected="selected">' + data[i].label + '</option>'
                           }else {
                               html += '<option value=' + data[i].value + '>' + data[i].label + '</option>'
                           }
                       }
                       html+='</select>'
                       $("#titleList").append(html)
                   }
               });
           }
       })


        function sumbitForm2(){
           //进行判定,除了工作单位以及专业,其余必填
            var form = document.getElementById("inputForm");
            var is=[2,3,5,6];
                /*for(var i = 0;i<form.length;i++){//2,3,5,6 必须有值,其余可以为空,数组方式存储
                    alert("i的值"+i+" 值:"+form.elements[i].value)
                }*/
                var selectType = $("#selectType").val();
                for(var i = 0;i<is.length;i++){
                    //alert("序号"+is[i]+"内容"+form.elements[is[i]].value)
                   if(form.elements[is[i]].value==''){
                        if(selectType!=null&&selectType!=''&&is[i]==2){
                            continue;
                        }
                       alert('存在未填写的')
                        return;
                       /*alert("序号"+is[i]+"内容"+form.elements[is[i]].value)
                       */
                   }
                }
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
                        window.close();
                    }
                }
            })
        }
        function changeTitle(obj){
            //根据不同的值,通过ajax 查找不同的
            var value =$(obj).val();
            var html = '';
            $("#titleList").empty();

            $.ajax({
                url:'${ctx}/counselor/title/ajaxTitle?value='+value,
                type:'post',
                dataType:'json',
                success:function(data){
                    html+='<select class="select2-container input-medium" style="width: 120px" name="titleType" >'
                    html+='<option value='+""+'></option>'
                    for(var i = 0;i<data.length;i++){
                        html+='<option value='+data[i].value+'>'+data[i].label+'</option>'
                    }
                    /*html+='</select>'*/
                    $("#titleList").append(html)
                }
            });

        }
    </script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
	</ul><br/>--%>
    <input type="hidden" value="${titleCertificate.titleType}" id="titleType">
	<form:form id="inputForm" modelAttribute="titleCertificate" action="${ctx}/counselor/saveForm2?tableId=${tableId}&personId=${personId}" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
        <form:input path="id" type="hidden"/>
        <legend>基本情况</legend>
        <table id="contentTable" class="table table-striped table-bordered table-condensed" align="center">
            <tr align="center">
                <td colspan="20" height="60px" width="100%" align="center"><span  style="font-size: 18px;align-content: center">职称证书情况</span></td>
            </tr>
            <tr align="center">
                <td colspan="3"  height="30px" width="15%" ><div align="center">职称级别<span style="color: red">*</span></div></td>
                <td colspan="3"  width="15%" ><div align="center">职称类型<span style="color: red">*</span></div></td>
                <td colspan="4" align="center" width="20%" ><div align="center">专业</div></td>
                <td colspan="5" align="center" width="25%" ><div align="center">批准机构<span style="color: red">*</span></div></td>
                <td colspan="5" align="center" width="25%" ><div align="center">批准时间<span style="color: red">*</span></div></td>
            </tr>
            <tr>
                <td colspan="3"  height="30px" width="15%" >
                    <form:select id="titleLevel" path="titleLevel" class="input-medium " onchange="changeTitle(this)" cssStyle="width: 120px">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('title_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </td>
                <td colspan="3"  width="15%" id="titleList" >

               <%-- <select name="titleType" id="typeList" style="width: 120px">

                    </select>--%>


                    <%--<form:input path="titleType" cssStyle="width: 90%"/>--%>
                </td>
                <td colspan="4" width="200" >
                    <form:input path="specialty"  maxlength="40" cssStyle="width: 90%"/>
                </td>
                <td colspan="5" align="center" width="25%" >
                    <form:input path="approveEmployer" maxlength="40" cssStyle="width: 90%"/>
                </td>
                <td colspan="5" align="center" width="25%" >

                    <input name="approveTime" type="text"  maxlength="20" class="input-medium Wdate "
                           value="<fmt:formatDate value="${titleCertificate.approveTime}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                </td>
            </tr>
            <tr>
                <td colspan="6" height="30px" width="30%" >获得职称证书时的工作单位</td>
                <td colspan="14"  width="70%" >
                    <form:input path="getEmployer" maxlength="80"  cssStyle="width: 97%"/>
                </td>
            </tr>
        </table>

        <br/>
		<div class="form-actions">

            <c:if test="${empty readOnly}">
            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="sumbitForm2()" value="提交"/>
            </c:if>
            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="javascript:window.close();" value="关闭"/>
			<%--<shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
            <shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" onclick="saveBaseInfo();"/>&nbsp;</shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>--%>
		</div>
	</form:form>


</body>
</html>