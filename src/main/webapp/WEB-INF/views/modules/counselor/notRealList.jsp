<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>不真实列表</title>
	<meta name="decorator" content="blank"/>

    <script>
        $(document).ready(function() {
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#inputForm").submit();
            return false;
        }

        function selectall()
        {
            var mycheckbox=document.getElementById('select1');
            var checkboxs=document.getElementsByName('records');
            for(var i=0;i<checkboxs.length;i++)
            {
                checkboxs[i].checked=mycheckbox.checked;
            }
        }

        function changeSelect(n){
            if(n!=null&&n!=''){//n不等于空,表示修改全部
                var url = '${ctx}/counselor/feedBack/changeRecordAll';
            }else{
                var url = '${ctx}/counselor/feedBack/changeRecord';
            }
            message(url)
        }

        function message(url) {//ajax 提交数据
            if(confirm("确定修改数据吗")){
                var data = $("#dataForm").serialize();

                $.ajax({
                    url:url,
                    type:'POST',
                    dataType:'json',
                    data:data,
                    success:function(result){
                        if(result=='200'){
                            location.href='${ctx}/counselor/feedBack/notRealList'
                        }
                    }
                })
            }
        }


    </script>
    <style>

    </style>
</head>

<body>





	<form:form id="inputForm" modelAttribute="notReal" action="" method="post" class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>登记类型：</label>
            <form:select path="declareType" style="width:130px;" multiple="false" >
                <form:option value="" label="请选择"/>
                <form:options items="${fns:getDictList('counselor_type')}" itemLabel="label" itemValue="value"  htmlEscape="false"/>
            </form:select>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <label>名字：</label>
            <form:input path="name"/>

            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <label>单位名称：</label>
            <form:input path="companyName"/>
        </li>
        <li><label>批次号：</label>
            <form:input path="batchNo"/>

            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <label>预审单位：</label>
            <form:input path="officeName"/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </li>


        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>


        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="更改选中的" onclick="changeSelect()"/></li>

    </ul>
    </form:form>
    <form:form id="dataForm" modelAttribute="" action="" method="post" class="form-horizontal">
        <table class="table table-striped table-bordered table-condensed">
            <thead>
            <tr>
                <th style="width: 50px">
                    <input type="checkbox"  id="select1" name="select" onclick="selectall()">
                    全选
                </th>
                <%--<th>选择</th>--%>
                <th>序号</th>
                <th>预审单位</th>
                <th>单位名称</th>
                <th>姓名</th>
                <th>身份证号</th>
                <th>批次号</th>
                <th>登记类型</th>
                <th>不真实项</th>
                <th>类型</th>
                <th>描述</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.list}" var="notReal" varStatus="index">
                <tr>

                    <td>
                        <input type="checkbox"  name="records" value="${notReal.recordId}">
                    </td>

                    <td>
                       ${index.index+1}
                    </td>

                    <td>
                            ${notReal.officeName}
                    </td>

                    <td>
                            ${notReal.companyName}
                    </td>

                    <td nowrap>
                            ${notReal.name}
                    </td>

                    <td>
                            ${notReal.idCard}
                    </td>

                    <td>
                            ${notReal.batchNo}
                    </td>

                    <td nowrap>
                            ${notReal.declareType}
                    </td>

                    <td>
                            ${notReal.label}
                    </td>

                    <td nowrap>
                            不真实
                            <%--${notReal.type}--%>
                    </td>

                    <td>
                            ${notReal.text}
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="pagination">${page}</div>
    </form:form>
</body>
</html>