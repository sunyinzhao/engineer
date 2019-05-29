<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>基本情况</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function saveForm(){
            var url = '${ctx}/counselor/isDate'
            var data = $('#inputForm').serialize();
            $.ajax({
                type: 'POST',
                url: url,
                data:data,
                dataType: 'json',
                success:function (data) {
                    if(data=='200'){
                        $("#inputForm").submit();
                    }else if(data=='201'){
                        alert("最早时间请设置为日期格式")
                    }else if(data == '202'){
                        alert("从事的工作内容为必填项")
                    }else if(data == '204'){
                        alert("带有至今项的从事的工作内容为必填项")
                    }else if(data == '203'){
                        alert("工作经历中必须包含一条结束日期是【至今】的工作经历，请确认。")
                    }
                }
            })
        }


        function saveConfirm(){
            var recordId = $("#recordId").val();
            var url = '${ctx}/counselor/isDate'
            var data = $('#inputForm').serialize();
            //加上一个flag 进行区分 是保存还是确认
            if(confirm("请认真填写，工作简历在登记事项上报终审后将无法修改！")){
                $.ajax({
                    type: 'POST',
                    url: url,
                    data:data,
                    dataType: 'json',
                    success:function (data) {
                        if(data=='200'){
                            $("#inputForm").attr("action","${ctx}/counselor/saveWorkForm?tableId=${tableId}&personId=${personId}&recordId="+recordId+"+&flag=0")
                            $("#inputForm").submit();
                        }else if(data=='201'){
                            alert("最早时间请设置为日期格式")
                        }else if(data == '202'){
                            alert("从事的工作内容为必填项")
                        }else if(data == '204'){
                            alert("带有至今项的从事的工作内容为必填项")
                        }else if(data == '203'){
                            alert("工作经历中必须包含一条结束日期是【至今】的工作经历，请确认。")
                        }
                    }
                })
            }

        }


        function FormatJsonDate1(dateObj) {
            if(dateObj!=''&&dateObj!=null&&dateObj!='undefined'){
                var date = new Date(dateObj);
                var dateStr = date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate();
                return dateStr;
            }else {
                return "";
            }

        }

        function addRow(list, idx, tpl, row,flag){
            if(flag=='1'){
                var idx = findIndex(list,idx,'_endDate')
                $(list+(idx-1)).before(Mustache.render(tpl, {
                    idx: idx, delBtn: true, row: row,index:idx+1,flag:flag
                }));
                var data = ${fns:toJson(jobKnowledge.jobKnowledgeList)};
                
                for (var i=0; i<data.length; i++){
                    if($("#jobKnowledgeList"+i+"_endDate").val()=='至今'){
                    	$("#jobKnowledgeList"+i+"_endDate").removeAttr("readonly");
                    	$("#jobKnowledgeList"+i+"_jobContent").removeAttr("readonly");
                    }
                }
                changeDate(list,idx,'_endDate','_startDate')
            }else{
                $(list).append(Mustache.render(tpl, {
                    idx: idx, delBtn: true, row: row,index:idx+1,flag:flag
                }));
            }
            $(list+idx).find("select").each(function(){

                $(this).val($(this).attr("data-value"));
            });

            $(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
                var ss = $(this).attr("data-value").split(',');
                for (var i=0; i<ss.length; i++){
                    if($(this).val() == ss[i]){
                        $(this).attr("checked","checked");
                    }
                }
            });
        }

        function findIndex(list,idx,obj){
            //设置两值. 1,是否第一次添加. 2.idx
            //如果size大于0
            var size = $("#numSize").val();
            var numFlag = $("#numFlag").val();
            //1.size大于0的时候
            if(size>0){
                //再进行判断flag 是否为空
                //给flag设置值
                var size = indexMethod(list,size,'_startDate')
                var temp = parseInt(size)+1;
                $("#numSize").val(temp);
                return size;
            }

            for(var i =idx-1;i>=0;i--){
                var temp = $(list+i+obj).val();
                if(temp!=null&&temp!=''){
                    return i+1;
                }
            }
        }

        function indexMethod(list,idx,obj){
            for(var i =idx-1;i>=0;i--){
                var temp = $(list+i+obj).val();
                if(temp!=null&&temp!=''){
                    return i+1;
                }
            }
        }

        //当添加的时候,添加的endDate 为上一个的startDate
        function changeDate(list,idx,obj,tempObj){
            //出现问题,当出现删除, 因此 list0 有值,list1 不见了, 但是list2 需要查找到list1 的值找不到,应该找list0的值>
            for(var i =idx-1;i>=0;i--){
                var temp = $(list+i+tempObj).val();
                if(temp!=null&&temp!=''){
                    break;
                }
            }
            $(list+(idx)+obj).val(temp);
        }


        function delRow(obj, prefix){
            //alert(obj);
            //alert(prefix);
            var id = $(prefix+"_id");
            var delFlag = $(prefix+"_delFlag");
            if (id.val() == ""){
                delFlag.val("1");
                $(obj).parent().parent().remove();
            }else if(delFlag.val() == "0"){
                delFlag.val("1");
                $(obj).html("&divide;").attr("title", "撤销删除");
                $(obj).parent().parent().addClass("error");
            }else if(delFlag.val() == "1"){
                delFlag.val("0");
                $(obj).html("&times;").attr("title", "删除");
                $(obj).parent().parent().removeClass("error");
            }

        }
        function onblurs(obj)
        {	
        	if(obj.value=="至今" && obj.id.indexOf("_endDate")>0) 
        	{
        		return;
        	}
        	else
        	{	
	        	if(obj.value.length!=0)
	        	{
		        	var regu =/^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
		        	if(obj.value.match(regu)==null)
		        	{
		        		alert("请输入正确的日期格式(例：2018-01-01),如果是最后一条工作经历，结束日期可以输入【至今】");
		        		obj.focus();
		        		obj.value="";
		        		return;
		        	}
		        	return;
	        	}
        	}
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="">初始登记</a></li>
    <li class="active"><a href="">工作经历</a></li>
</ul>
<input hidden id="numSize" value="${jobKnowledge.jobKnowledgeList.size()}">
<input hidden id="numFlag" value="">

<input type="hidden" id="recordId" value="${recordId}">
<form:form id="inputForm" modelAttribute="jobKnowledge" action="${ctx}/counselor/saveWorkForm?tableId=${tableId}&personId=${personId}&recordId=${recordId}" method="post" class="form-horizontal">
    <form:input path="id" type="hidden"/>
    <form:input path="title" type="hidden"/>
    <sys:message content="${message}"/>
    <input type="hidden" id="temp" value="">

    <legend>工作经历</legend>
    <table class="table-form" width="80%">
        　　<tr align="center">
        <td colspan="20" height="50px" width="100%" ><span style="font-size: 25px">工作经历</span></td>
    </tr>
        　　<tr align="center">
        <td height="50px" width="15%" colspan="3">
            起止时间
        </td>
        <td width="15%" colspan="3"  >
            单位名称
        </td>
        <td width="15%" colspan="2"  >
            从事的工作内容<span style="color: red">*</span>
        </td>
        <td width="10%" colspan="2"  >
            职务
        </td>
        <td width="10%" colspan="2"  >
            职称
        </td>
        <td width="10%" colspan="2"  >
            证明人
        </td>
        <td width="15%" colspan="3"  >
            证明人联系方式
        </td>
        <td width="5%" colspan="1"  >
            操作
        </td>
    </tr>
        <tbody id="jobKnowledgeList">
        </tbody>
        <tr>
            <td  colspan="20">
            <c:if test="${empty isAdd ||isAdd!='1'}">
                <a href="javascript:" onclick="addRow('#jobKnowledgeList', jobKnowledgeListsRowIdx, jobKnowledgeListsTpl,'','1');jobKnowledgeListsRowIdx = jobKnowledgeListsRowIdx + 1;" class="btn">新增</a>
            </c:if>
            </td>
        </tr>
    </table>
    <script type="text/template" id="jobKnowledgeListsTpl">
        <tr  id="jobKnowledgeList{{idx}}">
            <td height="50px" width="20%" colspan="3">

                <input      id="jobKnowledgeList{{idx}}_startDate" name="jobKnowledgeList[{{idx}}].startDate" type="text"  maxlength="20"  value="{{row.startDate}}" style="width:40%"
                            onchange="onblurs(this);" )
                                                                                                     ;"/>至
                <input    id="jobKnowledgeList{{idx}}_endDate" name="jobKnowledgeList[{{idx}}].endDate" type="text"  maxlength="20"  value="{{row.endDate}}" style="width:40%" onchange="onblurs(this);"/>


            </td>
            <td width="22%" colspan="3"  >
                <input id="jobKnowledgeList{{idx}}_id" name="jobKnowledgeList[{{idx}}].id" type="hidden" value="{{row.id}}" maxlength="300" style="width:95%"  />
                <input id="jobKnowledgeList{{idx}}_isChange" name="jobKnowledgeList[{{idx}}].isChange" type="hidden" value="{{row.isChange}}" maxlength="300" style="width:95%"  />
                <input id="jobKnowledgeList{{idx}}_companyName" name="jobKnowledgeList[{{idx}}].companyName" type="text" value="{{row.companyName}}" maxlength="80" style="width:90%"  />
            </td>
            <td width="15%" colspan="2"  >
                <input    id="jobKnowledgeList{{idx}}_jobContent" name="jobKnowledgeList[{{idx}}].jobContent" type="text" value="{{row.jobContent}}" maxlength="50" style="width:90%" />
            </td>
            <td width="10%" colspan="2"  >
                <input   id="jobKnowledgeList{{idx}}_duties" name="jobKnowledgeList[{{idx}}].duties" type="text" value="{{row.duties}}" maxlength="50" style="width:90%"  />
            </td>
            <td width="10%" colspan="2"  >
                <input    id="jobKnowledgeList{{idx}}_title" name="jobKnowledgeList[{{idx}}].title" type="text" value="{{row.title}}" maxlength="50" style="width:90%"  />
            </td>
            <td width="10%" colspan="2"  >
                <input id="jobKnowledgeList{{idx}}_reterence" name="jobKnowledgeList[{{idx}}].reterence"  type="text" value="{{row.reterence}}" maxlength="10" style="width:90%"  />
            </td>
            <td width="8%" colspan="3"  >
                <input   id="jobKnowledgeList{{idx}}_reterencetel" name="jobKnowledgeList[{{idx}}].reterencetel" type="text" value="{{row.reterencetel}}"  maxlength="20" style="width:90%"  />
            </td>
            <td class="text-center" width="5%" colspan="1"  >
                <input id="jobKnowledgeList{{idx}}_delFlag" name="jobKnowledgeList[{{idx}}].delFlag" type="hidden" value="0"/>
                <span id="jobKnowledgeList{{idx}}_delRow" class="close" onclick="delRow(this, '#jobKnowledgeList{{idx}}')" title="删除">&times;</span>
            </td>
        </tr>

    </script>
    <script type="text/javascript">
        var jobKnowledgeListsRowIdx = ${jobKnowledge.jobKnowledgeList.size()-1}, jobKnowledgeListsTpl = $("#jobKnowledgeListsTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
        $(document).ready(function() {
            var data = ${fns:toJson(jobKnowledge.jobKnowledgeList)};
            for (var i=0; i<data.length; i++){
                if(data[i].endDate=='至今'){
                    temp = i;
                }
                addRow('#jobKnowledgeList', jobKnowledgeListsRowIdx, jobKnowledgeListsTpl, data[i]);
                jobKnowledgeListsRowIdx = jobKnowledgeListsRowIdx - 1;

                //为0的时候,表示不可改变,需要把所有的id全部设置为readOnly
                if(data[i].isChange=='0'){
                    toOnly(i,data.length)
                }
                //新需求,当所有的
            }
            temp = (data.length-1)-temp;
            $("#jobKnowledgeList"+temp+"_endDate").attr("readonly","readonly")
            $("#jobKnowledgeList"+temp+"_endDate").removeAttr("onclick")
            $("#jobKnowledgeList"+temp+"_delRow").removeAttr("onclick")
        });
        //将所有的进行readOnly
        function toOnly(idx,length){
            idx = (length-1)-idx;
            $("#jobKnowledgeList"+idx+"_startDate").attr("readonly","readonly")
            $("#jobKnowledgeList"+idx+"_startDate").removeAttr("onclick")
            $("#jobKnowledgeList"+idx+"_endDate").attr("readonly","readonly")
            $("#jobKnowledgeList"+idx+"_endDate").removeAttr("onclick")
            $("#jobKnowledgeList"+idx+"_companyName").attr("readonly","readonly")
            $("#jobKnowledgeList"+idx+"_jobContent").attr("readonly","readonly")
            $("#jobKnowledgeList"+idx+"_duties").attr("readonly","readonly")
            $("#jobKnowledgeList"+idx+"_title").attr("readonly","readonly")
            $("#jobKnowledgeList"+idx+"_reterence").attr("readonly","readonly")
            $("#jobKnowledgeList"+idx+"_reterencetel").attr("readonly","readonly")
            $("#jobKnowledgeList"+idx+"_delRow").removeAttr("onclick")
            if($("#jobKnowledgeList"+idx+"_endDate").val()=='至今'){
            	$("#jobKnowledgeList"+idx+"_endDate").removeAttr("readonly");
            	var idx1=idx+1;
            	$("#jobKnowledgeList"+idx+"_jobContent").removeAttr("readonly");
            	$("#jobKnowledgeList"+idx1+"_endDate").removeAttr("readonly");
            	$("#jobKnowledgeList"+idx1+"_jobContent").removeAttr("readonly");
            }
        }
    </script>





    <br/>
    <div class="form-actions">
      <c:if test="${empty isAdd ||isAdd!='1'}">
        <input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveForm()" value="保存"/>
        </c:if>
        <input id="return" class="btn btn-primary" type="button" onclick="history.go(-1)" value="返回">
        <c:if test="${empty look}">
            &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveConfirm()" value="确认完成"/>
        </c:if>
    </div>
</form:form>


</body>
</html>