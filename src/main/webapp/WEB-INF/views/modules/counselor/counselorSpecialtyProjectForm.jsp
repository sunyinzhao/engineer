<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>基本情况</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        function saveForm(){
            $("#inputForm").submit();
        }
        function saveConfirm(){
            var recordId = $("#recordId").val();
            //加上一个flag 进行区分 是保存还是确认
            $("#inputForm").attr("action","${ctx}/counselor/project/saveProject?tableId=${tableId}&personId=${personId}&recordId="+recordId+"&flag=0")
            $("#inputForm").submit();
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
            $(list).append(Mustache.render(tpl, {
                idx: idx, delBtn: true, row: row,index:idx+1
            }));
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
            if(flag!='1'){
            if(row.accountant!=null){
            selectOption(row.accountant,"accountant",idx)
            }
            if(row.brzy!=null){
                selectOption(row.brzy,"brzy",idx)
            }
            if(row.zcSpecialty!=null){
                selectSpecialty(row.zcSpecialty,"zcSpecialty",idx)
            }
            }
        }

        //新方法,用于选中
        function selectOption(value,obj,idx){
            for(var i = 0 ; i < 7 ; i++){
                var newObj = document.getElementById(obj+idx+i);
                if(value == i){
                    $(newObj).attr("selected","selected");
                }
            }
        }

        //用于支持专业的选中
        function selectSpecialty(value,idx){
            var main = $("#main").val();
            var assist = $("#assist").val();
            var obj1 = document.getElementById("zcSpecialty"+idx+"1");
            var obj2 = document.getElementById("zcSpecialty"+idx+"2");
            if(value == main){
                $(obj1).attr("selected","selected");
            }else if(value == assist){
                $(obj2).attr("selected","selected");
            }
        }

        function delRow(obj, prefix){
            var id = $(prefix+"_id");
            var delFlag = $(prefix+"_delFlag");
            if (id.val() == ""){
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

	</script>
    <style>
        .form-control{width:100px;}
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="">变更专业</a></li>
    <li class="active"><a href="">申请变更专业的工程咨询业绩</a></li>
</ul>
    <input type="hidden" value="${enterpriseWorkers.registerMainSpecialty}" id="main">
<input type="hidden" value="${enterpriseWorkers.registerAuxiliarySpecialty}" id="assist">
	<form:form id="inputForm" modelAttribute="projectInvestment" action="${ctx}/counselor/project/saveProject?personId=${personId}&recordId=${recordId}&tableId=${tableId}" method="post" class="form-horizontal">
        <input type="hidden" id="recordId" value="${recordId}">
        <input type="hidden" id="personId" value="${personId}">
        <sys:message content="${message}"/>
        <legend>申请变更专业的工程咨询业绩</legend>
        <table class="table-form" width="80%">
            　　<tr align="center">
                    <td colspan="20" height="50px" width="100%" ><span style="font-size: 25px">申请变更专业的工程咨询业绩</span></td>
                </tr>
            　　<tr align="center">
                    <td height="50px" width="5%" colspan="1">
                        序号
                    </td>
                    <td width="10%" colspan="2"  >
                        服务范围
                    </td>
                    <td width="10%" colspan="2"  >
                        项目名称及服务范围
                    </td>
                    <td width="10%" colspan="2"  >
                        项目编制或评审单位
                    </td>
                    <td width="10%" colspan="2"  >
                        项目完成时间
                    </td>
                    <td width="10%" colspan="2"  >
                        建设规模/投资额
                    </td>
                    <td width="10%" colspan="2"  >
                        支持申报的专业
                    </td>
                    <td width="10%" colspan="2"  >
                        工作内容
                    </td>
                    <td width="15%" colspan="3"  >
                        本人完成的具体内容
                    </td>
                    <td height="50px" width="5%" colspan="3">
                         操作
                    </td>

                 </tr>
            <tbody id="projectList">
            </tbody>
            <tr><td colspan="20">
                <c:if test="${empty look}">
                    <a href="javascript:" onclick="addRow('#projectList', projectListsRowIdx, projectListsTpl,'1');projectListsRowIdx = projectListsRowIdx + 1;" class="btn">新增</a>
                </c:if>
            </td></tr>
        </table>
        <script type="text/template" id="projectListsTpl">
            <tr  id="projectList{{idx}}">
                    <td height="50px" width="5%" colspan="1" align="center">
                            {{index}}
                    </td>

                    <td width="10%" colspan="2"  >
                        <select id = "projectList{{idx}}_accountant" name="projectList[{{idx}}].accountant" class="form-control" value="{{row.accountant}}">
                            <option id="" value=""></option>
                            <option id="accountant{{idx}}1" value="1">规划咨询</option>
                            <option id="accountant{{idx}}2" value="2" <c:if test="{{row.accountant}}=='2'">selected</c:if>>项目咨询</option>
                            <option id="accountant{{idx}}3" value="3" <c:if test="{{row.accountant}}==3">selected</c:if>>评估咨询</option>
                            <option id="accountant{{idx}}4" value="4" <c:if test="{{row.accountant}}==4">selected</c:if>>全过程工程咨询</option>
                        </select>
                        <input id="projectList{{idx}}_id" name="projectList[{{idx}}].id" type="hidden" value="{{row.id}}"  />
                        <input id="projectList{{idx}}_orderBy" name="projectList[{{idx}}].orderBy" type="hidden" value="{{row.orderBy}}"  />
                    </td>
                    <td width="10%" colspan="2"  >
                        <input id="projectList{{idx}}_projectName" name="projectList[{{idx}}].projectName" type="text" value="{{row.projectName}}" maxlength="100" style="width:100px"  />

                    </td>
                    <td width="10%" colspan="2"  >
                        <input id="projectList{{idx}}_compliancerName" name="projectList[{{idx}}].compliancerName" type="text" value="{{row.compliancerName}}" maxlength="300" style="width:150px"  />
                    </td>
                    <td width="10%" colspan="2"  >
                        <input name="projectList[{{idx}}].commitTime" type="text"  maxlength="20" class="input-medium Wdate "
                               value="{{row.commitTime}}"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                    </td>
                    <td width="10%" colspan="2"  >
                        <input id="projectList{{idx}}_jsgm" name="projectList[{{idx}}].jsgm" type="text" value="{{row.jsgm}}" maxlength="150" style="width:100px"  />
                    </td>

                    <td width="10%" colspan="2"  >
                        <select id = "projectList{{idx}}_zcSpecialty" name="projectList[{{idx}}].zcSpecialty" class="form-control" value="{{row.zcSpecialty}}">
                            <option id="" value=""></option>
                            <option id="zcSpecialty{{idx}}1" value="${enterpriseWorkers.registerMainSpecialty}">${fns:getDictLabel(enterpriseWorkers.registerMainSpecialty,'specialty_type' ,'' )}</option>
                            <option id="zcSpecialty{{idx}}2" value="${enterpriseWorkers.registerAuxiliarySpecialty}" >${fns:getDictLabel(enterpriseWorkers.registerAuxiliarySpecialty,'specialty_type' ,'' )}</option>
                        </select>
                    </td>

                    <td width="10%" colspan="2"  >
                        <select id = "projectList{{idx}}_brzy" name="projectList[{{idx}}].brzy" class="form-control" value="{{row.accountant}}">
                            <option id="" value=""></option>
                            <option id="brzy{{idx}}1" value="01">审核</option>
                            <option id="brzy{{idx}}2" value="02" >校核</option>
                            <option id="brzy{{idx}}3"value="03">参加人</option>
                            <option id="brzy{{idx}}4"value="04">主导工艺</option>
                            <option id="brzy{{idx}}5"value="05">公用工程</option>
                            <option id="brzy{{idx}}6"value="06">工程技术经济</option>
                        </select>
                    </td>

                    <td width="15%" colspan="3"  >
                        <input id="projectList{{idx}}_nr" name="projectList[{{idx}}].nr" type="text" value="{{row.nr}}" maxlength="300" style="width:150px"  />
                    </td>

                    <td class="text-center" width="5%" colspan="1"  >
                        <input id="projectList{{idx}}_delFlag" name="projectList[{{idx}}].delFlag" type="hidden" value="0"/>
                        <span class="close" onclick="delRow(this, '#projectList{{idx}}')" title="删除">&times;</span>
                    </td>
            </tr>

        </script>
        <script type="text/javascript">
            var projectListsRowIdx = 0, projectListsTpl = $("#projectListsTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
            $(document).ready(function() {
                var data = ${fns:toJson(projectInvestment.projectList)};
                var main = "${enterpriseWorkers.registerMainSpecialty}"
                var auxi = "${enterpriseWorkers.registerAuxiliarySpecialty}"
                for (var i=0; i<data.length; i++){
                   var a = FormatJsonDate1(data[i].commitTime);
                    data[i].commitTime =a;
                    //需要把account 转换成字符串
                    addRow('#projectList', projectListsRowIdx, projectListsTpl, data[i]);
                    //需要把值选中
                    var specialty = data[i].zcSpecialty;
                    //当值为 main的时候,选中第一个
                    //当值为 au 的时候,选中第二个
                    if(specialty==main){
                        $("#zcSpecialty"+i+"1").attr("selected", "selected");
                    }else if(specialty==auxi){
                        $("#zcSpecialty"+i+"2").attr("selected", "selected");
                    }

                    projectListsRowIdx = projectListsRowIdx + 1;
                }
            });
        </script>





        <br/>
		<div class="form-actions">
            <c:if test="${empty look}">

            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveForm()" value="保存"/>

            </c:if>
            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="history.go(-1)" value="返回"/>

			<%--<shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
            <shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" onclick="saveBaseInfo();"/>&nbsp;</shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>--%>
            <c:if test="${empty look}">
                &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
            <input id="btnSubmit" class="btn btn-primary" type="button" onclick="saveConfirm()" value="确认完成"/>
            </c:if>
		</div>
	</form:form>


</body>
</html>