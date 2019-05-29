<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title>咨询师审查管理</title>
	<meta name="decorator" content="default"/>
	
	<link href="${pageContext.request.contextPath}/static/viewer/viewer.min.css" type="text/css" rel="stylesheet" />
	<script src="${pageContext.request.contextPath}/static/viewer/viewer-jquery.min.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	function changeStyle(obj,str){
		$(".LiSelected").each(function () { 
			
			 $(this).removeClass('LiSelected');
		});
		var jqObj = $(obj);
		if("0" == str){
			jqObj.addClass('LiSelected');
		}else{
			jqObj.parent().addClass('LiSelected');
		}
		
	}

	$(function() {
		viewPic();
		/*findImage();*!/*/

	});

	/*function findImage(){
            //判断是全屏还是侧屏
            var examineType = ${resultType}
                var kind = '${kind}'
        var recordId  = '${recordId}'
        var imageList = '';
                    if(examineType!=null&&examineType!=''){
                        if(kind!=null&&kind!=''){
                           imageList = 'imageList1'
                        }else{
                            imageList = 'imageList'
                        }
                        }
            $.ajax({
                url:"${ctx}/counselor/examine/findImage?recordId="+recordId+"&type="+examineType,
                type:"POST",
                dataType:'text',
                success:function(data){
                        imageList1(data,imageList);
                }
            })

    }*/
	
	function viewPic(){
		$('#dowebok').viewer({
			url: 'data-original',
		});
	}
	
		$(document).ready(function() {
            var radios = document.getElementsByName("result");

            //进入页面先查询是否有这个属性
            var obj = $("#inputRead").val();

            //有这个属性才能
            if(obj!=null){
                //alert("不是")
                var read = $("#inputRead").attr("checked");
                //表示选中,开放input的readOnly
                if(read == 'checked'){
                    $("#inputRemarks").removeAttr("readOnly")
                }
            }

			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});


	//点击按钮 去掉readOnly
    function changeInput(){
        $("#inputRemarks").removeAttr("readOnly");
    }
    function changeInput1(){
        $("#inputRemarks").removeAttr("value");
        $("#inputRemarks").attr("readOnly","true");
    }


    function change0(){
        $("#hiddenTr td").attr("style","");
        $("#contentTable").attr("style","");
        //需要判断
        var a = $("#select1 option:selected").val();

        if(a!="0"||a==""){
            $("#displayInput").attr("style","display:none")
            $("#displayInput1").attr("style","display:none")
        }else{

            $("#displayInput").attr("style","")
            $("#displayInput1").attr("style","")
        }
    }
    //点击是
    function change1(){
        $("#hiddenTr td").attr("style","display:none");
        $("#contentTable").attr("style","display:none");
    }
    //选择框事件
    function selectInput(){

        var a = $("#select1 option:selected").val();
        if(a=="0"){
            $("#displayInput").attr("style","")
            $("#displayInput1").attr("style","")
        }else{
            $("#displayInput").attr("style","display:none")
            $("#displayInput1").attr("style","display:none")
        }
    }

    function onChecked(obj,changeObj){//第一个参数是点击对象的值, 第二个参数是要跟变的参数id
        //var a = $("#onChecked1").val();
        var value = $(obj).val();
        if(value == '1'){
            var result = document.getElementsByTagName(changeObj);
            //alert($(result));
            $(result).removeAttr("hidden");
            //$("#trHidden").removeAttr("hidden");
        }
    }


		//保存项时需要进行控制,当result为是的时候,需要清除reject_value 与 remarks,当result为否, reject_value不为0的时候,需要清除remarks
		//remarks进行控制,
        // 1 .当第一次进入页面时,数据为否, 需要再进行判断 选项是否为其他
        // 2. 点击是,
        // 3.
		//当点击否之后,底下两个框展示.

        //点击否



        //放大图片
		function tolarge(data){
            $("#largeImage").attr("src",data)
		}



        //点击合规审查的时候进行数据校验,所有的项都有result才可以进入查看
        function getresult(id){
		    $.ajax({
                url:"${ctx}/association/associationExamine/result?id="+id,
                type:"POST",
                dataType:'text',
                success:function(data){
                    //返回的数据为 200 表示 ,所有result填写成功
                    if(data=="200"){

                            $("#resultForm").attr("action","${ctx}/association/associationExamine/table?declareRecordId="+id)
                             $("#resultForm").submit();
                    }else{
                        $("#resultForm").attr("action","");
                        alert("请审批完全")
                    }
                }
            })
        }

    function changeResult(){
        //提交出现两种情况.1种是合规的选项数据提交, 一种是预/终审 的选项. 根据type进行判断
        var type = $("#resultType").val();
        if(type=='2'){//type 为空的时候,表示为合规,合规提交
            $("#inputFormCheck").submit();
        }else{
            //type有值得时候,表示为预审, 预审提交
            $("#inputFormChecks").submit();
        }

    }

    //增加提交按钮,用于修改disBute表单
    function submitResult(){
        var declareRecordId=$("#declareRecordId").val();
        var specialtyId=$("#specialtyId").val();
        var kind=$("#kind").val();
        var expertDistributeId = $("#expertDistributeId").val();
        $.ajax({
            type: 'POST',
            url: '${ctx}/expert/submitResult?expertDistributeId='+expertDistributeId,
            dataType: 'text',
            contentType : 'application/text;charset=UTF-8',
            success: function(data) {
                if(data=='200'){
                    alert("提交成功")
                    window.parent.parent.location.assign("${ctx}/expert/pending1?flag=1")
                }else if(data=='201'){
                    alert("您还有尚未审核完成的表单")
                }else if(data=='202'){
                    window.parent.parent.location.assign("${ctx}/expert/pending1?flag=3")
                }
            },error:function(){
                alert("ajax请求失败");
            }
        });

    }


		function imageList1(data,type){
            var jsonObj = $.parseJSON(data)
            //alert(jsonObj.length);
            if (jsonObj.length>0){
                var html='';
                html=html+'<ul id="dowebok">';
                for(var i = 0;i<jsonObj.length;i++){
                    html=html +'<img src="${ctx}/uploadImage/id/'+jsonObj[i].id+'" class="fileImage" >';
                }
                html=html+'</ul>';
                $("#"+type).html(html);
            }
            else{
               /* $("#"+type).html(
                    '\t\t\t\t<div >\n' +
                    '\t\t<img src="${ctxStatic}/uploadImage/unupload.jpg" class="fileImage" >\n' +
                    '\t\t\t\t</div>\n' +'</br>'+
                    '')*/
            }
            viewPic();
        }


    function saveTest(){
        var targetUrl = $("#inputFormReject").attr("action");
        var data = $("#inputFormReject").serialize();
        $.ajax({
            url:targetUrl,
            type:"POST",
            cache: false,
            data:data,
            dataType:'json',
            success:function(map) {
                var id = map["id"]
                var index = map["index"]
                //alert(""+index)
                $("#rejectList"+index+"_id").val(id);
                var ids=$("#rejectList"+index+"_id").val();
            }
        })
    }


    //用于给添加材料进行判定的.

    function addRow(list, idx, tpl, row,flag){
        //在点击增加的时候,需要save一下数据

        //在flag!=1 的时候才进行ajax,表示不是第一次进入页面 addRow
       /* if(flag!='1'){

        var targetUrl = $("#inputForm").attr("action");
        var data = $("#inputForm").serialize();
        $.ajax({
            url:targetUrl,
            type:"POST",
            cache: false,
            data:data,
            dataType:'json',
            success:function(map) {
                var id = map["id"]
                var index = map["index"]
                //alert(""+index)
                $("#rejectList"+index+"_id").val(id);
                var ids=$("#rejectList"+index+"_id").val();
            }
                })

        }*/


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
        saveTest();

    }
    function returnapply(id){
    	if(confirm("确定要退回该申请吗？")){
			window.parent.location.assign("${ctx}/declare/declareRecord/changeStatusHg?id="+id);
    	}
    }


    //点击保存,给子节点更新颜色
        function saveSubmit(){
            var radios = document.getElementsByName("result");
            /*alert(radios[1].checked);*/
        //当点是的时候,设置为蓝色,否为红色.不填不变.

            if (radios[1].checked==true) {

                var b = parent.window.$("#changeRed").click();
            }else if (radios[0].checked==true){

                var b = parent.window.$("#changeBlue").click();
            }
           /*alert(b)*/

        }
    function returnList(a){
        if(a=='1'){
        window.parent.location.assign("${ctx}/declare/declareRecord/plicationList");
        }
        if(a=='3'){
            window.parent.location.assign("${ctx}/declare/declareRecord/declareList");
        }
        if(a=='0'){
            window.parent.location.assign("${ctx}/declare/declareRecord/returnList");
        }
    }
    
	//加载客户基本信息
	function loadPic(cusId){ 
		$("#DivCustom").load("${ctx}/cus/cusCustom/view?id="+cusId);		
	}/* alert(value);*/




    //展示后面的具体描述框有无
	function ceshi123(str,obj,changeObj){
        var ss = str.split(",");
        var value = $(obj).val();
        for(var i = 0;i<ss.length;i++){
            if(ss[i]==value){
                changeObj.removeAttr('hidden')
                return;
            }
        }
        changeObj.attr('hidden','hidden')
        //还需要把内容设置为空的
        //changeObj.removeAttr("value")
        changeObj.children('input').val("");
        //alert(a)
    }


    //type为6的 值为 1的 展示789的名字与下拉框
    function changeSelect(obj){
	    var value = $(obj).val();
        var names = document.getElementsByName("nameHidden");
        var selects = document.getElementsByName("selectHidden");
	    if(value == '0'){//不展示. 赋值hidden
	        for(var i = 0 ;i<names.length;i++){
                $(names[i]).attr('hidden','hidden');
            }
            for(var i = 0 ;i<selects.length;i++){
                $(selects[i]).attr('hidden','hidden');
            }
        }else if(value = '1'){
            for(var i = 0 ;i<names.length;i++){
                $(names[i]).removeAttr('hidden')
            }
            for(var i = 0 ;i<selects.length;i++){
                $(selects[i]).removeAttr('hidden')
            }
        }
    }

    function openFail(examineId,recordId,type){//新增一个窗口,用来查找不通过与不真实的列表
	    var url ='${ctx}/counselor/examine/openFail?examineId='+examineId+'&recordId='+recordId+'&examineType='+type
        //三个参数,1 examineId, type为初审/终审的值
        $.jBox.open("iframe:"+url,'不符合项',950,540,{
            buttons:{'关闭':true}
        })
    }

    function openReturnFail(examineId,recordId,type){//新增一个窗口,用来查找不通过与不真实的列表
        var url ='${ctx}/counselor/feedBack/openFail?examineId='+examineId+'&recordId='+recordId+'&examineType='+type+'&kind=1'
        //三个参数,1 examineId, type为初审/终审的值
        $.jBox.open("iframe:"+url,'不符合项',1000,540,{
            buttons:{'关闭':true}
        })
    }


    function changeRecord(recordId,type){
	    if(confirm('请确认提交')){
	    //合规退回需要进入
        if(type=='22'){
            var result22 = prompt("请输入退回原因","")
            if(result22){//表示点击确定

            }else{//点击取消,或者没内容
                if(result22==''){
                    alert("请填写原因")
                }
                return;
            }
        }

        if(type=='33'){
            var result33 = prompt("请输入退回原因","")
            if(result33){//表示点击确定

            }else{//点击取消,或者没内容
                if(result33==''){
                    alert("请填写原因")
                }
                return;
            }
        }

            if(type=='51'||type=='52'||type=='53'){//复议需要的文本框取值
                var fyAdvice = $("#fyAdvice").val();
                var select = document.getElementById("updatetypeF");
            }

            if(type == '41'||type == '42'||type == '43'){
                var select = document.getElementById("updatetype");//add by gaoyongjian 20181118 增加管理员可以修改专家结论
                var firOrSecsel = document.getElementById("firOrSec");//add by gaoyongjian 20181205 主辅专业是同一个专家
            }else if(type == '31'||type == '32'||type =='33'){
                var select = document.getElementById("updatetypeY");
            }else if(type == '91'||type == '92'){
            	var select = document.getElementById("updatetypeZ")
            }
            var updatetype="";
            if (select != null){
                updatetype = select.value;
            }
            var firOrSec ="";
            if(firOrSecsel != null)
            {
            	firOrSec = firOrSecsel.value;
            }
            //预审,终审需要把处罚标识代入
        //1.找到这个值, 点通过按钮和部分通过按钮 必须选择无异常
        var data = $("#improprietyForm").serialize();
	    var url = '${ctx}/counselor/examine/changeRecord?recordId='+recordId+'&type='+type+'&hgReturn='+result22+'&ysReturn='+result33+'&updatetype='+updatetype+'&fyAdvice='+fyAdvice+'&firOrSec='+firOrSec;
	    $.ajax({
                url:url,
                type:'POST',
                dataType:'json',
                data:data,
            success:function(data){
                    if(data=='201'){
                        alert("您不是该申请单的审批专家")
                    }else if(data=='12'||data=='11'){
                        alert('提交成功')
                        parent.location.href='${ctx}/enterprise/auditAndReport/waitReportList';
                    }else if(data=='21'||data=='22'){
                        alert('提交成功')
                        parent.location.href='${ctx}/enterprise/auditAndReport/localComplianceList';
                    }else if(data=='203'){//203状态为 标识不对
                        alert('处罚标识为无异常才可通过')
                        //http://localhost:8081/a/enterprise/auditAndReport/expertSuggestionCheckList
                    }else if(data=='204'){//204状态为 标识不对
                        alert('提交成功')
                        parent.location.href='${ctx}/enterprise/auditAndReport/expertSuggestionCheckList';
                     }else if(data=='206'){//206状态为 标识不对
                         alert('提交成功')
                         parent.location.href='${ctx}/enterprise/auditAndReport/waitFexpertList';
                      }else{
                        alert('提交成功')
                            //window.location.
                        parent.location.href='${ctx}/enterprise/auditAndReport/waitAuditApplyList';
                    }

            }
        })
    }
    }

    //处理标识
    function vaildateimpropriety(){
	    //1.获取表单内容
        var form = document.getElementById("improprietyForm");
    }

    function findImage(fileId){
        var url ='${ctx}/counselor/counselorAttachment/findImages?fileId='+fileId
        var name = ''
        var iHeight=600;
        var iWidth = 900;
        var iTop = (window.screen.height-30-iHeight)/2; //获得窗口的垂直位置;
        var iLeft = (window.screen.width-10-iWidth)/2; //获得窗口的水平位置;
        window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
    }

	</script>
	<style>
		.left{
			/*background-color: green;*/
			border: 1px solid #999;
			float: left;
			width: 35%;
			line-height:10px;
			min-width: 150px;
			position:relative;
			padding-top:10px;
			height: 88%;
			margin-left:-1px;
			overflow-y:auto;
			overflow-x:auto;
		}
		.leftMain{
			/*background-color: green;*/
			border: 1px solid #999;
			float: left;
			width: 30%;
			min-width: 300px;
			position:relative;
			height: 80%;
			margin-left:-1px;
			/*	margin-right: -100%;*/
		}
		.content1{
		border: 1px solid #999;
			float: left;
			position:relative;
			height: 90%;
			width: 64%;
			margin-left:-1px;
			overflow-y:auto;
		}
        .content2{
            border: 1px solid #999;
            float: left;
            position:relative;
            height: 89%;
            width: 99%;
            margin-left:-1px;
            overflow-y:auto;
        }
		.mainImage{
			max-width: 440px;
            max-height: 440px;
		}

        .fileImage{
            width: 120px;
            height: 120px;
            border: 1px solid #999;
        }

        .contentInner {
			margin-left: 220px;/*==等于左边栏宽度值==*/
			background-color: orange;
		}
		.LiSelected{border:solid 1px #666; display:table;}
		.gaoGon{color:red;font-size:12px;}
        .form-control { width:300px;
            overflow:hidden;
        }

		
	</style>
</head>
<body>

<div style="border:1px solid gainsboro; min-width: 1050px; height: auto; ">
<div style="width: 99% ; height: 450px; position:relative ; ">
    <input type="hidden" value="${examineType}" id="resultType">
    <table>
    <tr>
	    <td style="font-size: 15px;font-weight:bold; width: 80px;">
	    姓名:
        </td>
        <td style="font-size: 15px">
            ${infoMap.name}
        </td>
        <td style="font-size: 15px;font-weight:bold;">
           	 现执业单位:
        </td>
        <td style="font-size: 15px">
            ${infoMap.unit}
        </td>
        <td  style="font-size: 15px ;font-weight:bold;">
           	 预审单位:
        </td>
    	<td  style="font-size: 15px">
        ${infoMap.officeName}
		</td>
    </tr>
    <tr>
   		<td style="font-size: 15px;font-weight:bold;">
           	 申请类型:
        </td>
        <td style="font-size: 15px">
                    <%--${infoMap.type}--%>
                    ${fns:getDictLabel(infoMap.type, 'counselor_type', '')}
        </td>

   		<td style="font-size: 15px;font-weight:bold;">
           	 申请日期:
        </td>
        <td style="font-size: 15px">
            <fmt:formatDate value="${infoMap.date}" pattern="yyyy-MM-dd" />
        </td>



        <!--此处进行判断,专家不可见,user.userModel !='1'-->
        <c:if test="${user.userModel!='1'}">
            <td style="font-size: 15px;font-weight:bold;">
                联系方式:
            </td>
            <td colspan="5" style="font-size: 15px">
                ${infoMap.phone}
            </td>

        <td style="font-size: 15px;font-weight:bold;">
            联系人:
        </td>
        <td colspan="5" style="font-size: 15px">
            ${infoMap.companyContact}
        </td>
        </c:if>
        <!---->
        </tr>
    </table>

    <!--只展示图片-->
    <%--<c:if test="${workersList==null&&projectList==null}">
        <div id="content" class="content2"  &lt;%&ndash;style=" border:2px solid blueviolet; "&ndash;%&gt;>
            <ul id="dowebok">
                &lt;%&ndash;<li><img src="" class="mainImage" id="largeImage" ></li>&ndash;%&gt;
                <c:choose>
                    <c:when test="${empty imageList}">
                        <img src="${ctxStatic}/images/unupload.jpg" class="fileImage" >
                    </c:when>
                    <c:otherwise>
                        <c:forEach  items="${imageList}" var="image">
                            <img &lt;%&ndash;onclick="tolarge('${image}')"&ndash;%&gt; src="${image}" class="fileImage" >
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </ul>

        </div>
    </c:if>--%>
    <!--只展示图片-->

    <!--个人信息-->
    <c:if test="${not empty enterpriseWorkers}">

    <div class="left" id="categoryMenu">
        <form:form id="inputForm" modelAttribute="enterpriseWorkers" action="" method="post" class="form-horizontal">
        <table class="table-form">
            <tr align="center">
                <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                姓名
                </td>
                <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                ${enterpriseWorkers.name}
                </td>

            </tr>
            <tr align="center">
                <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                    性别
                </td>
                <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                        ${fns:getDictLabel(enterpriseWorkers.sex,'sex','' )}
                </td>

            </tr> <tr align="center">
            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                民族
            </td>
            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                            ${fns:getDictLabel(enterpriseWorkers.nation,'sys_nation','' )}
            </td>

        </tr> <tr align="center">
            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                证件类型
            </td>
            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                    ${fns:getDictLabel(enterpriseWorkers.certificatesType,'ID_type','' )}
            </td>

        </tr> <tr align="center">
            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                证件号
            </td>
            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                    ${enterpriseWorkers.certificatesNum}
            </td>

        </tr> <tr align="center">
            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                出生日期
            </td>
            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                    ${map.date}
            </td>
        </tr>
            </tr> <tr align="center">
            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                参加工作时间
            </td>
            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                <fmt:formatDate value="${enterpriseWorkers.entryDate}" pattern="yyyy-MM-dd"/>
            </td>
        </tr>
            </tr> <tr align="center">
            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                联系电话
            </td>
            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                    ${enterpriseWorkers.phone}
            </td>
        </tr>
            </tr> <tr align="center">
            <td class="tit" colspan="2" width="50%" height="20px" style="font-size: 18px">
                邮箱
            </td>
            <td colspan="2" width="50%" height="20px" style="font-size: 18px">
                    ${enterpriseWorkers.email}
            </td>
        </tr>
            </tr> <tr align="center">
            <td class="tit" colspan="2" width="50%" height="20px" style="font-size: 18px">
                职业证书取得年份
            </td>
            <td colspan="2" width="50%" height="20px" style="font-size: 18px">
                    ${enterpriseWorkers.getyear}
            </td>
        </tr>
            </tr> <tr align="center">
            <td class="tit" colspan="2" width="50%" height="20px" style="font-size: 18px">
                职业资格证书编号
            </td>
            <td colspan="2" width="50%" height="20px" style="font-size: 18px">
                    ${enterpriseWorkers.professioncardNum}
            </td>
        </tr>
        </table>
        </form:form>
    </div>
    </c:if>

    <!--学历教育情况-->
    <c:if test="${not empty educationtblList}">
        <div class="left" id="categoryMenu">
            <form:form id="inputForm" modelAttribute="" action="" method="post" class="form-horizontal">
                <table class="table-form">
                    <c:forEach items="${educationtblList}" var="educationtbl" varStatus="index">
                    <tr>
                        <td colspan="4"> <span style="font-weight: bold">文件:${educationtbl.index}</span></td>
                    </tr>


                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                办学类型
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                    ${fns:getDictLabel(educationtbl.schoolType,'school_type','' )}
                            </td>
                        </tr>

                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                毕业院校
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                    ${educationtbl.school}
                            </td>
                        </tr>



                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                所学专业
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                    ${educationtbl.specialty}
                            </td>
                        </tr>

                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                学习方式
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                    ${fns:getDictLabel(educationtbl.studyType,'study_type','' )}
                            </td>
                        </tr>


                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                学历
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                    ${fns:getDictLabel(educationtbl.education,'education','' )}
                            </td>
                        </tr>

                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                学制(年)
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                    ${educationtbl.studyYear}
                            </td>
                        </tr>

                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                起始时间
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                <fmt:formatDate value="${educationtbl.startTime}" pattern="yyyy-MM-dd"/>
                            </td>
                        </tr>

                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                毕业时间
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                <fmt:formatDate value="${educationtbl.endTime}" pattern="yyyy-MM-dd"/>
                            </td>
                        </tr>


                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                校(院)长
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                    ${educationtbl.schoolMaster}
                            </td>
                        </tr>

                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                证书编号
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                    ${educationtbl.zsNo}
                            </td>
                        </tr>
                        </c:forEach>
                </table>
            </form:form>
        </div>
    </c:if>

    <c:if test="${titleCertificateList !=null}">
        <div class="left" id="categoryMenu">
            <form:form id="inputForm" modelAttribute="" action="" method="post" class="form-horizontal">
                <table class="table-form">
                    <c:forEach items="${titleCertificateList}" varStatus="index" var="titleCertificate" >
                        <tr>
                            <td colspan="4"> <span style="font-weight: bold">文件:${titleCertificate.index}</span></td>
                        </tr>

                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                职称级别
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                    ${fns:getDictLabel(titleCertificate.titleLevel,'title_level','' )}
                            </td>
                        </tr>

                    <tr align="center">
                        <td class="tit" colspan="2" width="25%" height="40px" style="font-size: 18px">
                            职称
                        </td>
                        <td colspan="2" width="25%" height="50px" style="font-size: 15px">
                                ${fns:getDictLabel(titleCertificate.titleType,'worker_title','' )}
                        </td>
                    </tr>



                    <tr align="center">
                        <td class="tit" colspan="2" width="25%" height="30px" style="font-size: 18px">
                            专业
                        </td>
                        <td colspan="2" width="25%" height="30px" style="font-size: 18px">
                                ${titleCertificate.specialty}
                        </td>

                    </tr> <tr align="center">
                    <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                        评审机构
                    </td>
                    <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                            ${titleCertificate.approveEmployer}
                    </td>

                </tr>
                        <tr align="center">
                    <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                        取得时间
                    </td>
                    <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                        <fmt:formatDate value="${titleCertificate.approveTime}" pattern="yyyy-MM-dd"/>
                    </td>
                        </tr>

                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 17px">
                                获职称证书的工作单位
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                    ${titleCertificate.getEmployer}
                            </td>
                        </tr>
                        </c:forEach>
                </table>
            </form:form>
        </div>
    </c:if>

    <c:if test="${specialtyTrainList!=null}">
        <div class="left" id="categoryMenu">
            <form:form id="inputForm" modelAttribute="" action="" method="post" class="form-horizontal">
                <table class="table-form">
                    <c:forEach items="${specialtyTrainList}" var="specialtyTrain" varStatus="index">
                    <tr>
                        <td colspan="4"> <span style="font-weight: bold">文件:${specialtyTrain.index}</span></td>
                    </tr>
                    <tr align="center">
                        <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                            证书类型
                        </td>
                        <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                   ${specialtyTrain.trainType}
                        </td>
                    </tr>

                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                培训起始时间
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                        <fmt:formatDate value="${specialtyTrain.startTime}" pattern="yyyy-MM-dd"/>
                            </td>
                        </tr>

                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                培训终止时间
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                        <fmt:formatDate value="${specialtyTrain.endTime}" pattern="yyyy-MM-dd"/>
                            </td>
                        </tr>

                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                证书编号
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                    ${specialtyTrain.cardnum}
                            </td>
                        </tr>

                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                学习方式
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                    ${specialtyTrain.studyType}
                            </td>
                        </tr>

                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                校长
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                    ${specialtyTrain.schoolMaster}
                            </td>
                        </tr>

                        <tr align="center">
                            <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                                颁证时间
                            </td>
                            <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                <fmt:formatDate value="${specialtyTrain.getgctime}" pattern="yyyy-MM-dd"/>
                            </td>
                        </tr>
















                    <tr align="center">
                        <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                            学习时长
                        </td>
                        <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                ${specialtyTrain.studyTime}
                        </td>
                    </tr>





                        <tr align="center">
                    <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                        大专院校
                    </td>
                    <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                          ${specialtyTrain.trainSchool}
                    </td>

                </tr> <tr align="center">
                    <td class="tit" colspan="2" width="25%" height="30px" style="font-size: 18px">
                        专业
                    </td>
                    <td colspan="2" width="25%" height="30px" style="font-size: 18px">
                            ${specialtyTrain.specialty}
                    </td>
                </c:forEach>
                </table>
            </form:form>
        </div>
    </c:if>

    <c:if test="${not empty personRegister}">
        <div class="left" id="categoryMenu">
            <form:form id="inputForm" modelAttribute="" action="" method="post" class="form-horizontal">
                <table class="table-form">
                    <tr align="center">
                        <td class="tit" colspan="2" width="25%" height="50px" style="font-size: 18px">
                            执业单位
                        </td>
                        <td colspan="2" width="25%" height="50px" style="font-size: 18px" nowrap>
                                ${personRegister.companyName}
                        </td>

                    </tr>
                    <tr align="center">
                        <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                            预审单位
                        </td>
                        <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                <%--${personRegister.backupNum}--%>
                                        ${infoMap.officeName}
                        </td>

                    </tr> <tr align="center">
                    <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                        工作单位
                    </td>
                    <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                            ${personRegister.jobCompanyName}
                    </td>

                </tr>
                    <tr align="center">
                    <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                        备案情况
                    </td>
                    <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                            ${personRegister.backupType=='0'?'未备案':personRegister.backupType=='1'?'已备案':''}
                    </td>
                    </tr>
                    <tr align="center">
                        <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                            工作单位性质
                        </td>
                        <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                ${personRegister.companyType=='1'?'企业':personRegister.companyType=='2'?'事业':''}
                        </td>
                    </tr>
                    <tr align="center">
                        <td class="tit" colspan="2" width="25%" height="20px" style="font-size: 18px">
                            继续教育是否满足
                        </td>
                        <td colspan="2" width="25%" height="20px" style="font-size: 18px">
                                ${personRegister.iseducate=='0'?'不满足':personRegister.iseducate=='1'?'满足':''}
                        </td>
                    </tr>
                    <tr align="center">
                        <td class="tit" colspan="2" width="25%" height="30px" style="font-size: 18px">
                            申请主专业
                        </td>
                        <td colspan="2" width="25%" height="30px" style="font-size: 18px">
                                ${fns:getDictLabel(personRegister.registerMainSpecialty,'specialty_type','' )}
                        </td>
                    </tr>
                    <tr align="center">
                        <td class="tit" colspan="2" width="25%" height="30px" style="font-size: 18px">

                            申请辅助专业
                        </td>
                        <td colspan="2" width="25%" height="30px" style="font-size: 18px">
                                ${fns:getDictLabel(personRegister.registerAuxiliarySpecialty,'specialty_type','' )}
                        </td>
                    </tr>
                </table>
            </form:form>
        </div>
    </c:if>


    <c:if test="${jobKnowledgeList!=null}">
    <div class="content2" id="categoryMenu">
        <form:form class="form-horizontal">
        <table class="table-form">
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
                从事的工作内容
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
        </tr>
            <c:forEach items="${jobKnowledgeList}" var="jobKnowledge">
                <tr align="center">
                    <td height="50px" width="15%" colspan="3">
                        ${jobKnowledge.startDate}
                            至
                                ${jobKnowledge.endDate}
                        <%--<fmt:formatDate value="${jobKnowledge.startDate}" pattern="yyyy-MM-dd" /> 至
                        <fmt:formatDate value="${jobKnowledge.endDate}" pattern="yyyy-MM-dd" />--%>
                         <%--${jobKnowledge.endDate}--%>
                   </td>
                    <td width="15%" colspan="3"  >
                       ${jobKnowledge.companyName}
                   </td>
                    <td width="10%" colspan="2"  >
                       ${jobKnowledge.jobContent}
                   </td>
                    <td width="10%" colspan="2"  >
                       ${jobKnowledge.duties}
                   </td>
                    <td width="10%" colspan="2"  >
                       ${jobKnowledge.title}
                   </td>
                    <td width="10%" colspan="2"  >
                       ${jobKnowledge.reterence}
                   </td>
                    <td width="15%" colspan="3"  >
                           ${jobKnowledge.reterencetel}
                   </td>
               </tr>
            </c:forEach>
        </table>
        </form:form>
    </div>
    </c:if>


    <c:if test="${projectList!=null}">
        <div class="content2" id="categoryMenu">
            <form:form class="form-horizontal">
                <table class="table-form">
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
                    <td width="15%" colspan="3"  >
                        工作内容
                    </td>
                    <td width="15%" colspan="3"  >
                        本人完成的具体内容
                    </td>
                </tr>
                    <c:forEach items="${projectList}" var="project" varStatus="index">
                        <tr align="center">
                            <td height="50px" width="5%" colspan="1" align="center">
                                ${index.index+1}
                            </td>

                            <td width="10%" colspan="2">
                                <!--设置一个oclick 进行弹窗,弹出该类的图片集合-->
                                ${fns:getDictLabel(project.accountant,"project_accountant" ,"" )}

                            </td>
                            <td width="10%" colspan="2"  >
                                <%--<input id="projectList{{idx}}_projectName" name="projectList[{{idx}}].projectName" type="text" value="{{row.projectName}}" maxlength="100" style="width:100px"  />--%>
                                    <a onclick="findImage('${project.id}')" style="cursor: pointer;">
                                    ${project.projectName}
                                    </a>

                            </td>
                            <td width="10%" colspan="2"  >
                                <%--<input id="projectList{{idx}}_compliancerName" name="projectList[{{idx}}].compliancerName" type="text" value="{{row.compliancerName}}" maxlength="300" style="width:150px"  />--%>
                                ${project.compliancerName}
                            </td>
                            <td width="10%" colspan="2"  >
                                <fmt:formatDate value="${project.commitTime}" pattern="yyyy-MM-dd"/>
                            </td>
                            <td width="10%" colspan="2"  >
                                <%--<input id="projectList{{idx}}_jsgm" name="projectList[{{idx}}].jsgm" type="text" value="{{row.jsgm}}" maxlength="150" style="width:100px"  />--%>
                                ${project.jsgm}
                            </td>

                            <td width="10%" colspan="2"  >
                                ${fns:getDictLabel(project.zcSpecialty,'specialty_type' ,'' )}
                            </td>

                            <td width="15%" colspan="3"  >
                                ${fns:getDictLabel(project.brzy,"signature_duty" ,"" )}
                            </td>

                            <td width="15%" colspan="3"  >
                                ${project.nr}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </form:form>
        </div>
    </c:if>

    <!--执业单位 accountantStatus-->
    <c:if test="${not empty accountantStatus}">
    <c:if test="${infoMap.accountantType == '2'}">
    <div class="left" id="categoryMenu">
        <form:form id="inputForm" modelAttribute=""  class="form-horizontal">
        <table class="table-form">
            <tr>
                <td colspan="2" align="center" style="font-size: 18px" > 执业情况</td>
            </tr>
            <tr>
            <td    height="15px" style="font-size: 15px">
                调入执业单位时间
            </td>
            <td  height="20px" style="font-size: 15px">
                ${infoMap.startDate}
                <%--<fmt:formatDate value="${accountantStatus.startDate}" pattern="yyyy-MM-dd"/>--%>
            </td>
            </tr>
            <tr>
                <td height="15px" style="font-size: 15px">登记证书编号</td>
                <td height="20px" style="font-size: 15px">${accountantStatus.registerCertificateNum}</td>
            </tr>
            <tr>
                <td height="15px" style="font-size: 15px">主专业</td>
                <td height="30px" style="font-size: 15px">${fns:getDictLabel(accountantStatus.oldMainSpecialty,"specialty_type","")}</td>
            </tr>
            <tr>
                <td height="15px" style="font-size: 15px">辅专业</td>
                <td height="30px">${fns:getDictLabel(accountantStatus.oldAuxiliarySpecialty,"specialty_type","")}</td>
            </tr>
            <tr>
                <td height="15px" style="font-size: 15px">变更理由</td>
                <td height="30px" style="font-size: 15px">${fns:getDictLabel(accountantStatus.reason,"reason_type","")}
                    <c:if test="${accountantStatus.remarks!=null}">(${accountantStatus.remarks})</c:if>
                </td>
            </tr>
            <tr>
                <td height="25px" style="font-size: 15px">原执业单位</td>
                <td height="25px" style="font-size: 15px">
                       <%-- ${infoMap.companyName}--%>
                                ${infoMap.oldCompanyName}
                </td>
            </tr>
            <tr>
                <td height="15px" style="font-size: 15px">上一年度执业检查结论</td>
                <td height="20px" style="font-size: 15px">${fns:getDictLabel(accountantStatus.accountantCheck,"check_result","")}</td>
            </tr>
            <tr>
                <td height="25px" style="font-size: 15px">现申请执业单位</td>
                <td height="25px" style="font-size: 15px">
                        ${infoMap.newCompanyName}
                        <%--${accountantStatus.companyName}--%></td>
            </tr>
            <tr>
                <td height="15px" style="font-size: 15px">预审单位</td>
                <td height="20px" style="font-size: 15px">
                        ${infoMap.officeName}
                </td>
            </tr>
            <tr>
                <td height="15px" style="font-size: 15px">工作单位</td>
                <td height="20px" style="font-size: 15px">${accountantStatus.jobCompanyName}</td>
            </tr>
            <tr>
                <td height="15px" style="font-size: 15px">工作单位性质</td>
                <td height="20px" style="font-size: 15px">${fns:getDictLabel(accountantStatus.companyType,"declare_company_type","")}</td>
            </tr>
            <tr>
                <td height="15px" style="font-size: 15px">备案情况</td>
                <td height="20px" style="font-size: 15px">${fns:getDictLabel(accountantStatus.backupType,"backup_type" ,"" )}</td>
            </tr>

        </table>
        </form:form>
    </div>
    </c:if>

    <!--此表格为 变更专业表格-->
    <c:if test="${infoMap.accountantType == '3'}">
        <div class="left" id="categoryMenu">
            <form:form id="inputForm" modelAttribute=""  class="form-horizontal">
                <table class="table-form">
                    <tr>
                        <td colspan="2" align="center" style="font-size: 18px" > 执业情况</td>
                    </tr>
                    <tr>
                        <td height="15px" style="font-size: 15px">登记证书编号</td>
                        <td height="20px" style="font-size: 15px">${accountantStatus.registerCertificateNum}</td>
                    </tr>
                    <tr>
                        <td height="15px" style="font-size: 15px">原主专业</td>
                        <td height="30px" style="font-size: 15px">${fns:getDictLabel(accountantStatus.oldMainSpecialty,"specialty_type","")}</td>
                    </tr>
                    <tr>
                        <td height="15px" style="font-size: 15px">原辅专业</td>
                        <td height="30px">${fns:getDictLabel(accountantStatus.oldAuxiliarySpecialty,"specialty_type","")}</td>
                    </tr>
                    <tr>
                        <td height="15px" style="font-size: 15px">新主专业</td>
                        <td height="30px" style="font-size: 15px">${fns:getDictLabel(infoMap.mainNewValue,"specialty_type","")}</td>
                    </tr>
                    <tr>
                        <td height="15px" style="font-size: 15px">新辅专业</td>
                        <td height="30px">${fns:getDictLabel(infoMap.fNewValue,"specialty_type","")}</td>
                    </tr>

                    <tr>
                        <td height="25px" style="font-size: 15px">执业单位</td>
                        <td height="25px" style="font-size: 15px">
                                <%-- ${infoMap.companyName}--%>
                                ${enterpriseWorkers.companyName}
                        </td>
                    </tr>
                    <tr>
                        <td height="15px" style="font-size: 15px">上一年度执业检查结论</td>
                        <td height="20px" style="font-size: 15px">${fns:getDictLabel(accountantStatus.accountantCheck,"check_result","")}</td>
                    </tr>
                    <tr>
                        <td height="15px" style="font-size: 15px">预审单位</td>
                        <td height="20px" style="font-size: 15px">
                                ${infoMap.officeName}
                        </td>
                    </tr>
                    <tr>
                        <td height="15px" style="font-size: 15px">工作单位</td>
                        <td height="20px" style="font-size: 15px">${accountantStatus.jobCompanyName}</td>
                    </tr>
                    <tr>
                        <td height="15px" style="font-size: 15px">工作单位性质</td>
                        <td height="20px" style="font-size: 15px">${fns:getDictLabel(accountantStatus.companyType,"declare_company_type","")}</td>
                    </tr>
                    <tr>
                        <td height="15px" style="font-size: 15px">备案情况</td>
                        <td height="20px" style="font-size: 15px">${fns:getDictLabel(accountantStatus.backupType,"backup_type" ,"" )}</td>
                    </tr>

                </table>
            </form:form>
        </div>
    </c:if>

    </c:if>
   <%-- ${imageList1}222--%>
    <c:if test="${imageList1!=null}">
    <div class="content1" id="imageList1">
        <ul id="dowebok">
        <c:forEach items="${imageList1}" var="image">
            <img  src="${ctx}/uploadImage/id/${image.id}" class="fileImage" >
        </c:forEach>
        </ul>
    </div>
    </c:if>

    <c:if test="${imageList!=null}">
    <div class="content2" id="imageList">
        <ul id="dowebok">
            <c:forEach items="${imageList}" var="image">
                <img  src="${ctx}/uploadImage/id/${image.id}" class="fileImage" >
            </c:forEach>
        </ul>
    </div>
    </c:if>
</div>





    <!--区分选项-->
    <!--
    1. 不正确0  正确1  isRight    2, 3 ,5 ,6 ,7
    2. 未查验原件0    查验原件1  isCheck   不一致0     一致1  isRight     1,9,11,12,13
    3.  失效0          有效期内1    isRight 8
    4.  未查验 0        查验原件 1   isRight  10,14
    5.  企业法人证书  事业法人证书  不属于前两个 isCheck 0,1,2    不正确  正确 isRight 0 , 1   15
    6.
    -->
    <c:if test="${not empty checkList}">
    <!--此处放置选项form表单-->
    <div style="width: 98%; height:220px; margin-top:1px; margin-left:-1px; border: 1px solid #999;">
        <form:form id="inputFormCheck" modelAttribute="personCompliance" action="${ctx}/counselor/compliance/saveCompliance?examineId=${examineId}&recordId=${recordId}&type=${examineType}" method="post" class="form-horizontal" >
            <table>
            <c:forEach items="${checkList}" var="check" varStatus="index">
                    <input type="hidden" name="check[${index.index}].id" value="${check.id}"/>
                <input type="hidden" name="check[${index.index}].examineId" value="${check.examineId}"/>
                <input type="hidden" name="check[${index.index}].recordId" value="${check.recordId}"/>
                <input type="hidden" name="check[${index.index}].type" value="${check.type}"/>
            <tr id="checkList" >
                <td class="tit">
                    ${check.name}:
                </td>
                <td>
                    <c:if test="${check.type == 2 ||check.type == 3 ||check.type == 5 ||check.type ==6||check.type == 7}">
                        <input type="radio" name="check[${index.index}].isRight" value="0" <c:if test="${check.isRight == 0}">  checked="checked" </c:if> >不正确
                        <input type="radio" name="check[${index.index}].isRight" value="1" <c:if test="${check.isRight == 1}">  checked="checked" </c:if> >正确
                        &nbsp;&nbsp;&nbsp;描述:<input type="text" name="check[${index.index}].remarks" value="${check.remarks}">
                    </c:if>

                    <c:if test="${check.type == 4}">
                        <input type="radio" name="check[${index.index}].isRight" value="0" <c:if test="${check.isRight == 0}">  checked="checked" </c:if> >未提供
                        <input type="radio" name="check[${index.index}].isRight" value="1" <c:if test="${check.isRight == 1}">  checked="checked" </c:if> >已提供
                        &nbsp;&nbsp;&nbsp;描述:<input type="text" name="check[${index.index}].remarks" value="${check.remarks}">
                    </c:if>

                  <c:if test="${check.type == 1 ||check.type ==9||check.type ==11||check.type ==12||check.type ==13}">
                        <input type="radio" name="check[${index.index}].isCheck" value="0" <c:if test="${check.isCheck == 0}">  checked="checked" </c:if>>未查验原件
                        <input type="radio" name="check[${index.index}].isCheck" value="1" <c:if test="${check.isCheck == 1}">  checked="checked" </c:if>>查验原件
                        <input type="radio" name="check[${index.index}].isRight" value="0" <c:if test="${check.isRight == 0}">  checked="checked" </c:if>>不一致
                        <input type="radio" name="check[${index.index}].isRight" value="1" <c:if test="${check.isRight == 1}">  checked="checked" </c:if>>一致
                      &nbsp;&nbsp;&nbsp;描述:<input type="text" name="check[${index.index}].remarks" value="${check.remarks}">
                    </c:if>

                    <c:if test="${check.type == 8}">
                        <input type="radio" name="check[${index.index}].isRight" value="0" <c:if test="${check.isRight == 0}">  checked="checked" </c:if>>失效
                        <input type="radio" name="check[${index.index}].isRight" value="1" <c:if test="${check.isRight == 1}">  checked="checked" </c:if>>有效期内
                        &nbsp;&nbsp;&nbsp;描述:<input type="text" name="check[${index.index}].remarks" value="${check.remarks}">
                    </c:if>

                    <c:if test="${check.type == 10 ||check.type ==14}">
                        <input type="radio" name="check[${index.index}].isRight" value="0" <c:if test="${check.isRight == 0}">  checked="checked" </c:if>>未查验
                        <input type="radio" name="check[${index.index}].isRight" value="1" <c:if test="${check.isRight == 1}">  checked="checked" </c:if>>查验原件
                        &nbsp;&nbsp;&nbsp;描述:<input type="text" name="check[${index.index}].remarks" value="${check.remarks}">
                    </c:if>

                    <c:if test="${check.type == 15 }">
                        <input type="radio" name="check[${index.index}].isCheck" value="0" <c:if test="${check.isCheck == 0}">  checked="checked" </c:if>>企业法人证书
                        <input type="radio" name="check[${index.index}].isCheck" value="1" <c:if test="${check.isCheck == 1}">  checked="checked" </c:if>>事业法人证书
                        <input type="radio" name="check[${index.index}].isCheck" value="2" <c:if test="${check.isCheck == 2}">  checked="checked" </c:if>>不属于前两项
                        <input type="radio" name="check[${index.index}].isRight" value="1" <c:if test="${check.isRight == 1}">  checked="checked" </c:if>>不正确
                        <input type="radio" name="check[${index.index}].isRight" value="0" <c:if test="${check.isRight == 0}">  checked="checked" </c:if>>正确
                        &nbsp;&nbsp;&nbsp;描述:<input type="text" name="check[${index.index}].remarks" value="${check.remarks}">
                    </c:if>
                    <c:if test="${check.type == 16 || check.type == 17 }">
                        &nbsp;&nbsp;&nbsp;描述:<input type="text" name="check[${index.index}].remarks" value="${check.remarks}">
                    </c:if>

                   <c:if test="${check.type == 18}">
                        <input id="inputRead" type="radio" name="check[${index.index}].isRight" value="1" <c:if test="${check.isRight == 1}">  checked="checked" </c:if> onclick="changeInput();">是
                        <input type="radio" name="check[${index.index}].isRight" value="0" <c:if test="${check.isRight == 0}">  checked="checked" </c:if> onclick="changeInput1();">否
                       &nbsp;&nbsp;&nbsp;描述:<input id="inputRemarks" type="text" name="check[${index.index}].remarks" value="${check.remarks}" readonly="readonly">
                    </c:if>

                    <c:if test="${check.type == 19 || check.type ==20}">
                        <input id="inputRead" type="radio" name="check[${index.index}].isRight" value="1" <c:if test="${check.isRight == 1}">  checked="checked" </c:if> >是
                        <input type="radio" name="check[${index.index}].isRight" value="0" <c:if test="${check.isRight == 0}">  checked="checked" </c:if>">否
                        &nbsp;&nbsp;&nbsp;描述:<input type="text" name="check[${index.index}].remarks" value="${check.remarks}">
                    </c:if>

                </td>
            </tr>
            </c:forEach>
            </table>

        </form:form>

    </div>
    </c:if>

    <!--不同的type 展示不同的选项,此处展示终审与预审的选项-->
        <!--测试假数据 -->
       <%-- 不予登记的情形的审查：
        <select style="width: 200px" name="2">
            <option value=""></option>
            <option value="0">予以登记</option>
            <option value="1">不具有完全民事行为能力</option>
            <option value="2">年龄在70周岁及以上</option>
            <option value="3">同时在两个及以上工程咨询单位执业，被注销登记不满3年</option>
            <option value="4">列入发展改革部门工程咨询不良记录或“黑名单”，被注销登记</option>
        </select>--%>
    <c:if test="${not empty checkLists}">
    <div style="width: 98%; height:220px; margin-top:1px; margin-left:-1px; border: 1px solid #999;overflow-y:auto;">
        <form:form id="inputFormChecks" modelAttribute="personExpert" action="${ctx}/counselor/personExpert/savePersonExpert?examineId=${examineId}&recordId=${recordId}&type=${examineType}" method="post" class="form-horizontal" >
    <!--选中事件-->

        <%--<form:input path="examineId"/>--%>
            <table class="table-form">
                <tr>
                    <td>序号</td>
                    <td>类型</td>
                    <c:if test="${user.id eq firstZExpertId || usermodel=='2'}">
                    <td>专家1：选项</td>
                    <td>专家1：描述</td>
                    </c:if>
                    <c:if test="${secondZExpertId != null && (user.id eq secondZExpertId || usermodel=='2')}">
                    <td>专家2：选项</td>
                    <td>专家2：描述</td>
                    </c:if>
                </tr>


                <c:forEach items="${checkLists}" var="expertList" varStatus="index">
                    <tr>
                        <input name="expertList[${index.index}].id" value="${expertList.id}" type="hidden">
                        <input name="expertList[${index.index}].recordId" value="${expertList.recordId}" type="hidden">
                        <input name="expertList[${index.index}].type" value="${expertList.type}" type="hidden">
                        <input name="expertList[${index.index}].index" value="${expertList.index}" type="hidden">
                        <input name="expertList[${index.index}].examineId" value="${expertList.examineId}" type="hidden">
                        <input name="expertList[${index.index}].examineType" value="${expertList.examineType}" type="hidden">
                        <input name="expertList[${index.index}].remarks" value="${expertList.remarks}" type="hidden">
                        <td>

                            <c:choose>
                                <c:when test="${expertList.index!=''&&expertList.index!=null}">
                                    文件 ${expertList.index}
                                </c:when>
                                <c:otherwise>
                                    ${index.index+1}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                                ${expertList.name}
                        </td>
                        <c:choose>
                            <c:when test="${user.id eq firstZExpertId || usermodel=='2'}">
                                <td >
                                    <div>
                                        <c:set value="${fn:split(expertList.items,',' ) }" var="categorys" />
                                        <c:set value="person_expert${expertList.type}" var="specialty" />
                                        <form:select path="expertList[${index.index}].items" class="input-large "  multiple="true">
                                            <c:forEach items="${fns:getDictList(specialty)}" var="item" >
                                                <c:set value="0" var="selected"/>
                                                <c:forEach items="${categorys}" var="cat"  >
                                                    <c:if test="${item.value eq  cat}">
                                                        <c:set value="1" var="selected"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:choose>
                                                    <c:when test="${selected eq '1' }">
                                                        <form:option value="${item.value}" selected="selected" label="${item.label}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form:option value="${item.value}" label="${item.label}"/> ;
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </td>
                                <td>
                                    <input name="expertList[${index.index}].itemText" value="${expertList.itemText}">
                                </td>
                            </c:when>
                            <c:otherwise>
                                <input name="expertList[${index.index}].items" value="${expertList.items}" type="hidden">
                                <input name="expertList[${index.index}].itemText" value="${expertList.itemText}" type="hidden">
                            </c:otherwise>
                        </c:choose>
                       <c:choose>
                           <c:when test="${secondZExpertId != null && (user.id eq secondZExpertId || usermodel=='2')}">
                               <td>
                                   <div>
                                       <c:set value="${fn:split(expertList.secondItems,',' ) }" var="categorys" />
                                       <c:set value="person_expert${expertList.type}" var="specialty" />
                                       <form:select path="expertList[${index.index}].secondItems" class="input-large "  multiple="true">
                                           <c:forEach items="${fns:getDictList(specialty)}" var="item" >
                                               <c:set value="0" var="selected"/>
                                               <c:forEach items="${categorys}" var="cat"  >
                                                   <c:if test="${item.value eq  cat}">
                                                       <c:set value="1" var="selected"/>
                                                   </c:if>
                                               </c:forEach>
                                               <c:choose>
                                                   <c:when test="${selected eq '1' }">
                                                       <form:option value="${item.value}" selected="selected" label="${item.label}"/>
                                                   </c:when>
                                                   <c:otherwise>
                                                       <form:option value="${item.value}" label="${item.label}"/> ;
                                                   </c:otherwise>
                                               </c:choose>
                                           </c:forEach>
                                       </form:select>
                                   </div>
                               </td>
                               <td>
                                   <input name="expertList[${index.index}].secondItemText" value="${expertList.secondItemText}">
                               </td>
                           </c:when>

                        <c:otherwise>
                            <input name="expertList[${index.index}].secondItems" value="${expertList.secondItems}" type="hidden">
                            <input name="expertList[${index.index}].secondItemText" value="${expertList.secondItemText}" type="hidden">
                        </c:otherwise>
                       </c:choose>
                    </tr>
                </c:forEach>
            </table>
        </form:form>
    </div>
        <input type="button" value="未通过列表" class="btn" onclick="openFail('${examineId}','${recordId}','${personExpert.examineType}')">
    </c:if>
    </br>
    <c:if test="${usermodel=='2'&&userofficeid=='1'}">
    	<div align="center" style="font-size: 15px;font-weight:bold;">
    		终审主：${firstZExpertName}&nbsp;—&nbsp;${fns:getDictLabel(counselor.firstZdeclareResult,'decaler_result','')} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;终审辅：${secondZExpertName}&nbsp;—&nbsp;${fns:getDictLabel(counselor.secondZdeclareResult,'decaler_result','')}
    	</div>
    </c:if>

   <c:if test="${personExpert.examineType=='5'||personExpert.examineType=='4'}">
    <c:if test="${counselor.utilFeedback == '1'}">
        <input type="button" value="反馈信息" class="btn" onclick="openReturnFail('${examineId}','${recordId}','${personExpert.examineType}','1')">
   </c:if>
    </c:if>



<div style="width: 98%; height:180px; margin-top:1px; margin-left:-1px; border: 1px solid #999;">
		<form:form id="inputFormReject" modelAttribute="counselorExamineReject" action="${ctx}/counselor/examine/ajaxSaveReject" method="post" class="form-horizontal" >
            <form:input path="personRecordId" type="hidden"/>
            <form:input path="examineId" type="hidden"/>
            <sys:message content="${message}"/>
            <!--这里添加,需要添加的意见表单,补充材料展示-->

            <%--<tr id="hiddenReject" >
                <td colspan="6">
                    <div >
                        <div >
                            <table id="contentTable" class="table table-striped table-bordered table-condensed"
                                   cellpadding="0" cellspacing="0" >
                                <thead>
                                <tr>
                                    <th class="hide"></th>
                                        <th>序号</th>
                                    <th colspan="2">不符合原因</th>
                                    <th>操作</th>

                                </tr>
                                </thead>
                                <tbody id="rejectList">
                                </tbody>
                              
                                <tr><td colspan="8">
                                 <shiro:hasPermission name="expert:examine:edit">
                                	<a href="javascript:" onclick="addRow('#rejectList', rejectListsRowIdx, rejectListsTpl);rejectListsRowIdx = rejectListsRowIdx + 1;" class="btn">新增</a>
                                 </shiro:hasPermission>
                                </td></tr>
                                
                            </table>
                            <script type="text/template" id="rejectListsTpl">
                                <tr id="rejectList{{idx}}">
                                    <td class="hide">
                                        <input id="rejectList{{idx}}_id" name="rejectList[{{idx}}].id" type="text" value="{{row.id}}"/>
										<input id="rejectList{{idx}}_examineId" name="rejectList[{{idx}}].examineId" type="text" value="{{row.examineId}}"/>
                                        <input id="rejectList{{idx}}_personRecordId" name="rejectList[{{idx}}].personRecordId" type="text" value="{{row.personRecordId}}"/>
                                    </td>
                                    <td >
                                        <input  type="text" value="{{index}}" readonly="true"/>
                                        <input id="rejectList{{idx}}_delFlag" name="rejectList[{{idx}}].delFlag" type="hidden" value="0"/>
                                    </td>
                                    <td colspan="2">
                                        <input id="rejectList{{idx}}_content" name="rejectList[{{idx}}].content" type="text" onblur="saveTest()" value="{{row.content}}" maxlength="300" style="width:500px"  />
                                    </td>
                                    <td class="text-center" width="10">
                                        <span class="close" onclick="delRow(this, '#rejectList{{idx}}')" title="删除">&times;</span>
                                    </td>
                                </tr>
                            </script>
                            <script type="text/javascript">
                                var rejectListsRowIdx = 0, rejectListsTpl = $("#rejectListsTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
                                $(document).ready(function() {
                                    var data = ${fns:toJson(rejectList)};

                                    for (var i=0; i<data.length; i++){
                                        addRow('#rejectList', rejectListsRowIdx, rejectListsTpl, data[i],'1');
                                        rejectListsRowIdx = rejectListsRowIdx + 1;
                                    }
                                });
                            </script>
                        </div>
                    </div>
                </td>
            </tr>--%>
            
	            <%--<input id="save" class="btn btn-primary" type="button" value="保 存" onclick="submitForm()"/>&nbsp;--%>

	            <div class="form-actions">
                        <c:if test="${examineType!='1'&&examineType!=null&&examineType!='5'&&empty flag}">
	                    <input class="btn btn-primary" type="button" value="保存" onclick="changeResult()">
                        </c:if>
	            </div>
        </form:form>

     <c:if test="${examineType=='0'&& userofficeid =='1' && usermodel=='2'}">
        <div align="center">
        	<input type="button" value="退回预审单位" class="btn btn-primary" onclick="changeRecord('${recordId}','43')">
        </div>
    </c:if>
    <c:if test="${examineType=='1'&&empty flag}">
            <div align="center">
        <input type="button" value="同意上报" class="btn" onclick="changeRecord('${recordId}','11')">
        <input type="button" value="退回" class="btn" onclick="changeRecord('${recordId}','12')">
            </div>
    </c:if>

    <c:if test="${examineType=='2'&&empty flag}">
            <div align="center">
        <%--<input type="button" value="合规完毕" class="btn" onclick="changeRecord('${recordId}','21')">--%>
            <input type="button" value="通过" class="btn" onclick="changeRecord('${recordId}','21')">
            <input type="button" value="不通过" class="btn" onclick="changeRecord('${recordId}','23')">
                <input type="button" value="退回" class="btn" onclick="changeRecord('${recordId}','22')">
            </div>
    </c:if>

    <c:if test="${examineType=='3'&&empty flag}">
    <div align="center">
        <c:if test="${usermodel=='2'}">
            <select id="updatetypeY">
                <option value="1" selected="selected">修改专家1结论</option>
                <c:if test="${!empty counselor.secondCexpertId && counselor.secondCexpertId!=''}">
                    <option value="2">修改专家2结论</option>
                </c:if>
            </select>
        </c:if>
        <input type="button" value="通过" class="btn btn-primary" onclick="changeRecord('${recordId}','31')">
        <input type="button" value="不通过" class="btn btn-primary" onclick="changeRecord('${recordId}','32')">
         <c:if test="${user.userModel!='5'}">
        <input type="button" value="退回" class="btn btn-primary" onclick="changeRecord('${recordId}','33')">
         </c:if>
    </div>
    </c:if>

    <c:if test="${examineType=='4'&&empty flag}">
        <div align="center">
        <font color="red" size="4">
        <c:if test="${expertModel=='1'}">请审核该咨询师的：主专业</c:if>
        <c:if test="${expertModel=='2'}">请审核该咨询师的：辅专业</c:if>
        <c:if test="${expertModel=='3'}">请审核该咨询师的：主辅专业</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <c:if test="${expertModel=='3'}">
        	<select id="firOrSec">
				<option value="1" selected="selected">审核主专业</option>
				<option value="2">审核辅专业</option>
			</select>
		</c:if>
        <c:if test="${usermodel=='2'}">
			<select id="updatetype">
				<option value="1" selected="selected">修改专家1结论</option>
				<c:if test="${!empty counselor.secondZexpertId && counselor.secondZexpertId!=''}">
					<option value="2">修改专家2结论</option>
				</c:if>
			</select>
		</c:if>
        <input type="button" value="通过" class="btn btn-primary" onclick="changeRecord('${recordId}','41')">&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" value="不通过" class="btn btn-primary" onclick="changeRecord('${recordId}','42')">&nbsp;&nbsp;&nbsp;&nbsp;
        <c:if test="${usermodel=='2'}">
        	<input type="button" value="退回预审单位" class="btn btn-primary" onclick="changeRecord('${recordId}','43')">
        </c:if>
        </div>
    </c:if>
    

    <c:if test="${examineType=='5'&&empty flag}">
        &nbsp;&nbsp;&nbsp;复议建议:
        <textarea id="fyAdvice" style="width: 1000px;height: 50px">${counselor.fyAdvice}</textarea>
        <form:form id="improprietyForm" modelAttribute="counselor" action="" method="post" class="form-horizontal">
            处罚标识:
            <form:select path="impropriety" class="input-medium " cssStyle="width: 15%">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('impropriety')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </form:form>
        <div align="center">
        <c:if test="${counselor.declareType=='1' || counselor.declareType=='3'}">
        	<select id="updatetypeF">
				<option value="1" selected="selected">复核主专业</option>
				<option value="2">复核辅专业</option>
			</select>
        </c:if>
        <input type="button" value="通过" class="btn btn-primary" onclick="changeRecord('${recordId}','51')">
        <input type="button" value="不通过" class="btn btn-primary" onclick="changeRecord('${recordId}','52')">
<%--         <input type="button" value="部分通过" class="btn btn-primary" onclick="changeRecord('${recordId}','53')"> --%>
    </div>
    </c:if>

	<c:if test="${examineType=='6'&&empty flag}">
        <div align="center">
        <c:if test="${usermodel=='2'}">
			<select id="updatetypeZ">
				<option value="1" selected="selected">修改专家1结论</option>
				<c:if test="${!empty counselor.secondZexpertId && counselor.secondZexpertId!=''}">
					<option value="2">修改专家2结论</option>
				</c:if>
			</select>
		</c:if>
        <input type="button" value="通过" class="btn btn-primary" onclick="changeRecord('${recordId}','91')">
        <input type="button" value="不通过" class="btn btn-primary" onclick="changeRecord('${recordId}','92')">
    	</div>
    </c:if>


</div>

</div>

</body>
</html>