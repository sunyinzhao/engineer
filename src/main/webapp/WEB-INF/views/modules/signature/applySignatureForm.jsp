<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>电子签章业绩管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
			submitHandler: function(form){
				var projectName=$("#projectName").val();					
				var id=$("#id").val();
				var status=$("#status").val();
				//var url0 ="${ctx}/signature/applySignature/updatesignature?projectName="+projectName;
				var url ="${ctx}/signature/applySignature/checkProjectName?projectName="+projectName+"&id="+id;
				/*if(status=="1"){
					$.ajax({
                    type: 'POST'
                    , url: url0
                    , dataType: 'json'
                    ,success: function(data) {
                    	  //alert("未签章");
                    }
                    
                });
				}*/
				
				$.ajax({
				type: 'POST'
				, url: url
				, dataType: 'json'
				,success: function(data){
				if(data>=1){
				alert("项目名重复");
				$("#projectName").val("");
				}else{
				   loading('正在提交，请稍等...');
				   form.submit(); 
				 }
				   }
				    });
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
			
			//动态加服务范围小类
			$("#services").change(function(){
				var servicesVal = $("#services").val();

				if(servicesVal=='1'){  // 服务范围为规划时，“建设规模”与“投资额”不可用
                    $("#projectInvestAmount").val("");
                    $("#projectInvestAmount").attr("readonly","readonly");
                    $("#buildScale").val("");
                    $("#buildScale").attr("readonly","readonly");
                    $("#buildScale").removeClass("required");
                    $("#projectInvestAmount").removeClass("required");
                }else {
                    $("#buildScale").addClass("required");
                    $("#projectInvestAmount").addClass("required");
                    $("#buildScale").removeAttr("readonly");
                    $("#projectInvestAmount").removeAttr("readonly");
                }

                if(servicesVal=='3'){ // 服务范围为“评估咨询”
                    $("#veto").addClass("required");
                    $("#veto").removeAttr("readonly");
                    $("#projectAddSubAmount").addClass("required");
                    $("#projectAddSubAmount").removeAttr("readonly");
                }else {
                    $("#veto").val("");
                    $("#veto").attr("readonly","readonly");
                    $("#veto").removeClass("required");

                    $("#projectAddSubAmount").val("");
                    $("#projectAddSubAmount").attr("readonly","readonly");
                    $("#projectAddSubAmount").removeClass("required");
                }


				var url = "${ctx}/sys/dict/listDataCache?type=service_rang_"+servicesVal;
				$.ajax({
					type: 'POST'
					, url: url
				  	, dataType: 'json'
					,success: function(data) {
						var span = $("#s2id_childServices .select2-chosen ").html("");
						var htm= "<option value=''> </option>";
						$.each(data, function(idx, obj) {						    
						    htm = htm + '<option value="'+obj.value+'">'+obj.label+'</option>';						    
						});
						$("#childServices").html(htm);
					}		    
				}); 
						
				//清除之前选择内容
				$("#childServices").html("<option value=''> </option>");
				$("#s2id_childServices .select2-chosen ").html("");




			}); 
			
			
			//动态加载业绩所属
			$("#boundary").change(function(){
				var servicesVal = $("#boundary").val();
				var url = "${ctx}/sys/dict/listDataCache?type="+servicesVal;
				$.ajax({
					type: 'POST'
					, url: url
				  	, dataType: 'json'
					,success: function(data) {
						var span = $("#s2id_boundaryCase .select2-chosen ").html("");
						var htm= "<option value=''> </option>";
						$.each(data, function(idx, obj) {						    
						    htm = htm + '<option value="'+obj.value+'">'+obj.label+'</option>';						    
						});
						$("#boundaryCase").html(htm);
					}		    
				}); 						
				//清除之前选择内容
				$("#boundaryCase").html("<option value=''> </option>");
				$("#s2id_boundaryCase .select2-chosen ").html("");
			});



            //动态加载子专业
            $("#projectSpecialty").change(function(){
                var projectSpecialtyVal ="specialty_type_" +$("#projectSpecialty").val();
                var url = "${ctx}/sys/dict/listDataCache?type="+projectSpecialtyVal;
                $.ajax({
                    type: 'POST'
                    , url: url
                    , dataType: 'json'
                    ,success: function(data) {
                        var span = $("#s2id_projectSpecialtyChild .select2-chosen ").html("");
                        var htm= "<option value=''> </option>";
                        //var htm= "";//"<option value=''> </option>";
                        var i =0;
                        $.each(data, function(idx, obj) {
                            htm = htm + '<option value="'+obj.value+'">'+obj.label+'</option>';
                            i++;
                        });
                        $("#projectSpecialtyChild").html(htm);
                        if(i==0){
                            $("#projectSpecialtyChild").attr("disabled",true);
                            $("#projectSpecialtyChild").removeClass("required");
                        }else{
                            $("#projectSpecialtyChild").attr("disabled",false);
                            $("#projectSpecialtyChild").addClass("required");

                        }
                    }
                });
                //清除之前选择内容
                $("#projectSpecialtyChild").html("<option value=''> </option>");
                $("#s2id_projectSpecialtyChild .select2-chosen ").html("");
            });



            //显示弹出框
			var isShow ="${applySignature.id}";
			if(isShow == ""){
				$('.bgPop').css({"height":$(document).height()})
	            $('.bgPop,.pop').show();
			}
			 $('.pop-close').click(function () {
	         	$('.bgPop,.pop').hide();
	         });
			
		});
		
		
		
		function preAddWorkers(){
			confirmx('编辑其他数据请先保存本页面数据？', 'javascript:saveBaseInfo()');

		}




		function addWorkers(){


			// 正常打开	
			top.$.jBox.open("iframe:${ctx}/enterprise/enterpriseWorkers/listForSignature", "选择咨询工程师（投资）", 600, 550, {
				ajaxData:{selectIds: $("#${id}Id").val()},
				buttons:{"确定":"ok", "清除":"clear", "关闭":true}
				, submit:function(v, h, f){
					if (v=="ok"){
						var cusId = ""
						var table = h.find("iframe")[0];
						var table1 = $("#contentTable");
						var radio = ($(table)).contents().find("input[name=workersId]");
						var workersId=new Array();
						var i=0;
						($(table)).contents().find("input[name=workersId]").each(function () {
							 if ($(this).attr("checked") == 'checked') { 
								 workersId[i++]=$(this).val();
							 }  
						});	
						appendWorkers(workersId);
					}else if (v=="add"){
					    //window.location.href='${ctx}/enterprise/enterpriseWorkers/list';
                        //$("#searchForm").attr('action','${ctx}'+'/enterprise/enterpriseWorkers/list');
					}
					else if (v=="clear"){
						
		            }
					
				}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		
		
		//用提交表单的方式进行保存选中的数据
		function appendWorkers(workersId){
			if(workersId.length>0){
				$("#workersId").val(workersId);
				$("#inputForm").attr('action','${ctx}'+'/signature/applySignature/savePersonRelesion');  
				
				$("#inputForm").submit();
			}else{
				
			}
		};
		
		
		function saveBaseInfo(){
			$("#submitType").val("0");
		    $("#inputForm").submit();
		}
		
		function submitApply(){			
			var t = true;
			var count = 0;
			$("select[name='dutys']").each(
        		function(){
        			if($.trim($(this).val())==""){
        				t=false;
        				return;
        			}
                    count++;
        		}
        	);
        	if(count == 0){
                alert("请配备所有咨询工程师（投资）的“职责”后再提交");

        	}else if(t){
                $("#submitType").val("1");
                $("#status").val("1");
                $("#inputForm").submit();
        	}else{
                alert("请添加咨询工程师（投资）后再提交");
			}
        }
		function checkProjectName(){
			 var projectName=$("#projectName").val();
			 var id=$("#id").val();
			 var url ="${ctx}/signature/applySignature/checkProjectName?projectName="+projectName+"&id="+id;
	                $.ajax({
	                    type: 'POST'
	                    , url: url
	                    , dataType: 'json'
	                    ,success: function(data) {
	                    	 if(data>=1){
	                    		 alert("项目名重复");
	                    		 $("#projectName").val("");
	                    		 }
	                    }
	                });
			}
		function FileApply() {
			
            var e = $("#info").html();//附件1
            var f = $("#info2").html();//附件2
            var g = $("#info3").html();//附件3
            if(!$.trim(e)||!$.trim(f)||!$.trim(g)){
                alert("必传附件必须填写");
            }else{
          		 $("#submitType").val("1");
                 $("#status").val("2");
                 $("#inputForm").submit();
                 alert("归档成功！！");
 			}
		}
		
	</script>
<%-- 	${pageContext.request.contextPath} --%>
	<script src="${pageContext.request.contextPath}/static/webuploader-0.1.5/image-upload/uploadImage3.js" type="text/javascript"></script>
	
	<style>
	*{padding:0px;margin:0px;}
	.pop {  display: none;  width: 600px; min-height: 300px;  max-height: 750px;  height:450px;  position: absolute;  top: 0;  left: 0;  bottom: 0;  right: 0;  margin: auto;  padding: 25px;  z-index: 130;  border-radius: 8px;  background-color: #fff;  box-shadow: 0 3px 18px rgba(100, 0, 0, .5);  }
	.pop-top{  height:40px;  width:100%;  border-bottom: 1px #E5E5E5 solid;  }
	.pop-top h2{  float: left;  display:black}
	.pop-top span{  float: right;  cursor: pointer;  font-weight: bold; display:black}
	.pop-foot{  height:50px;  line-height:50px;  width:100%;  border-top: 1px #E5E5E5 solid;  text-align: right;  }
	.pop-cancel, .pop-ok {  padding:8px 15px;  margin:15px 5px;  border: none;  border-radius: 5px;  background-color: #337AB7;  color: #fff;  cursor:pointer;  }
	.pop-cancel {  background-color: #FFF;  border:1px #CECECE solid;  color: #000;  }
	.pop-content{  height: 370px;  }
	.pop-content-left{  float: left;  }
	.pop-content-right{  width:550px;  float: left;  padding-top:20px;  padding-left:20px;  font-size: 16px;  line-height:35px;  }
	.bgPop{  display: none;  position: absolute;  z-index: 129;  left: 0;  top: 0;  width: 100%;  height: 100%;  background: rgba(0,0,0,.2);  }
</style>
	
</head>
<body>
	<!--遮罩层-->
<div class="bgPop"></div>
<!--弹出框-->
<div class="pop">
    <div class="pop-top">
        <h2>注意事项</h2>
        <span class="pop-close">Ｘ</span>
    </div>
    <div class="pop-content">
        <div class="pop-content-left">
            <img src="" alt="" class="teathumb">
        </div>
        <div class="pop-content-right">
            <p>一、<b class="lname">工程咨询单位应对所提交业绩信息的真实性、合法性、准确性、完整性负责，接受国家发展改革委和中国工程咨询协会的必要核查，并承担相关法律责任。</b></p>
            <p>二、<b class="price">因信息填写不真实、不完整或填写错误，将影响其他有关事项的审核工作。</b></p>
            <p>三、<b class="ltime">填写的业绩信息，可用于中国工程咨询协会行业统计。</b></p>
        </div>
    </div>
    <div class="pop-foot">
        <input type="button" value="关闭" class="pop-cancel pop-close">
    </div>
</div>

	<ul class="nav nav-tabs">
		<li><a href="${ctx}/signature/applySignature/">电子签章业绩列表</a></li>
		<li class="active"><a href="${ctx}/signature/applySignature/form?id=${applySignature.id}">电子签章业绩<shiro:hasPermission name="signature:applySignature:edit">${not empty applySignature.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="signature:applySignature:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="applySignature" action="${ctx}/signature/applySignature/save" method="post" class="form-horizontal">
		<form:hidden path="id"  class='Id'/>
		<form:hidden path="enterpriseId" />



		
<!-- 		用于区分提交后是否跳转到List页面 1：不跳转，其它跳转 -->
		<input type="hidden" id="submitType" name="submitType" >
		<input type="hidden" id="workersId" name="workersId" >
		
		<form:hidden path="status" />
		
		<sys:message content="${message}"/>	
		<legend>签章业绩</legend>
		
		<table class="table-form">
			<tr>
				<td class="tit">项目名称：</td>
				<td>
<%-- 				<form:input path="projectName" htmlEscape="false" maxlength="200" class="input-medium required"/>  --%>
					<form:input onblur="checkProjectName()" path="projectName" htmlEscape="false" maxlength="200" class="input-medium required" name="projectName" id="projectName" />
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
				<td class="tit">服务范围：</td>
				<td>
				    <form:select path="services" class=" required" cssStyle="width: 177px;">
					    <form:option value="" label=""/>
					    <form:options items="${fns:getDictList('service_rang')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
			
				<td class="tit">服务范围小类</td>
				<td>
					<form:select path="childServices" cssStyle="width: 177px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('service_rang_'.concat(applySignature.services))}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
			    </td>
			</tr>
			
			<tr>
                <td class="tit">项目性质：</td>
                <td>
                    <form:select path="projectProperty" class=" required " cssStyle="width: 177px;">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('project_property')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                    <span class="help-inline"><font color="red">*</font> </span>
                </td>
				<td class="tit">合同号或批复号：</td>
				<td>
					<form:input path="contractNum" htmlEscape="false"  maxlength="30" class="input-medium required"/>
				</td>
				<td class="tit">项目专业：</td>
				<td>
				    <form:select path="projectSpecialty" cssStyle="width: 150px;">
						<form:option value="" label="无"/>
						<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>

					<form:select path="projectSpecialtyChild"  cssStyle="width: 80px;"  >
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('specialty_type_'.concat(applySignature.projectSpecialty))}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>
				
			</tr>
			<tr>
				<td class="tit">地区：</td>
                <td>
                    <form:select path="area" cssStyle="width: 177px;">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('area')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </td>
                <td class="tit">境内或境外：</td>
                <td>
                    <form:select path="boundary" cssStyle="width: 177px;">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('boundary')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </td>


                <td class="tit">投资方：</td>
                <td colspan="">
                    <form:select path="boundaryCase" cssStyle="width: 177px;">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList(applySignature.boundary)}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </td>

			</tr>
			
			<tr>
                
                <td class="tit">投资额（万元）：</td>
                <td>
                    <form:input path="projectInvestAmount" htmlEscape="false" class="input-medium number"/>
                </td>

                <td class="tit">工程咨询成果完成日期：</td>
                <td>
                    <input name="completeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
                           value="<fmt:formatDate value="${applySignature.completeDate}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </td>
                 <td class="tit">拟开工时间：</td>
                <td>
                    <input name="planBeginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate  "
                           value="<fmt:formatDate value="${applySignature.planBeginDate}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                </td>

			</tr>



            <tr>
                <td class="tit">项目境外投资额：</td>
                <td>
                    <form:input path="projectAbroadAmount" htmlEscape="false" class="input-medium number"/>
                </td>

                <td class="tit">核增减投资额：</td>
                <td colspan="">
                    <form:input path="projectAddSubAmount" htmlEscape="false" class="input-medium number"/>
                </td>
                <td class="tit">资金来源：</td>
                <td>
                    <form:select path="fundsSource"  cssStyle="width: 177px;">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('project_funds_source')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </td>

            </tr>



            <tr>
               

                <td class="tit">项目核准审核机关：</td>
                <td colspan="">
                    <form:input path="approvalOrg" htmlEscape="false" class="input-medium"/>
                </td>
                <td class="tit">是否否决：</td>
                <td>

                    <form:select path="veto" cssStyle="width: 177px;">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </td>
                <td class="tit">申请状态：</td>
                <td>
                    <c:choose>
                        <c:when test="${applySignature.status =='0' }">未提交</c:when>
                        <c:when test="${applySignature.status =='1' }">咨询工程师（投资）签章</c:when>
                        <c:when test="${applySignature.status =='2' }">完成</c:when>
                    </c:choose>
                </td>

            </tr>

			
			<tr>

                
                <td class="tit">建设规模(产能)：</td>
				<td>
					<form:textarea path="buildScale" htmlEscape="false"  rows="4" maxlength="200" class="input-xlarge "/>
				</td>
				<td class="tit">备注：</td>
				<td colspan="5">
					<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xlarge "/>
				</td>
				
			</tr>
			
			
			<tr><td class="tit" colspan="6" style="font-size: 18px; font-weight:bold;">咨询工程师（投资）     
			
				<shiro:hasPermission name="signature:applySignature:edit">	
					<c:if test="${applySignature.status =='0' }">		
						<c:choose>
						    <c:when test="${applySignature.id eq null or applySignature.id eq '' }">
						       <input onclick="preAddWorkers();" class="btn" type="button" value="添加人员" style="margin-right: 25px; float: right;"/>
						    </c:when>
						    <c:otherwise>
									<input onclick="addWorkers();" class="btn" type="button" value="添加人员" style="margin-right: 25px; float: right;"/>
						    </c:otherwise>
						</c:choose>
					</c:if>
				</shiro:hasPermission>
			
			</td></tr>
			<tr>			
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>姓名</th>
							<th>证件号</th>
							<th>主专业</th>
							<th>辅助专业</th>
							<th>职责</th>
							<shiro:hasPermission name="enterprise:enterpriseWorkers:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${applySignaturePersonList}" var="applySignaturePerson">
						<tr>
							<td>
								${applySignaturePerson.enterpriseWorkers.name}
							</td>
							<td>
			                    ${applySignaturePerson.enterpriseWorkers.certificatesNum}
							</td>
								
							
							<td>
								${fns:getDictLabel(applySignaturePerson.enterpriseWorkers.registerMainSpecialty, 'specialty_type', '')}
							</td>
							
							<td>
								${fns:getDictLabel(applySignaturePerson.enterpriseWorkers.registerAuxiliarySpecialty, 'specialty_type', '')}
							</td>
			
							
							<td>
			                    <!--把下拉框用foreach方式下拉,有值为选中状态-->
			                    
			                     <form:hidden  path="personIds" value="${applySignaturePerson.id}"/>
			                     <form:select path="dutys" class="input-medium required"   multiple="false" >
			                     <form:option value=" " label="--请选择--"/>
			                    <c:forEach items="${fns:getDictList('signature_duty')}" var="item" >
			                        <c:choose>
			                            <c:when test="${ applySignaturePerson.duty eq item.value }">
			                                <form:option value="${item.value}" selected="selected" label="${item.label}"/>
			                            </c:when>
			                             <c:otherwise>
			                                <form:option value="${item.value}" label="${item.label}"/>
			                            </c:otherwise>
			                        </c:choose>
			                    </c:forEach> 
			                    </form:select> 
							</td>
							<td>
			    			<shiro:hasPermission name="signature:applySignature:edit">
			    			
			    			<c:if test="${applySignature.status =='0' }">
								<a href="${ctx}/signature/applySignature/deletePerson?id=${applySignaturePerson.id}&signatureId=${applySignature.id}" onclick="return confirmx('确认要删除该咨询工程师（投资）吗？', this.href)">删除</a>
							</c:if>
							</shiro:hasPermission>
								</td>
					</c:forEach>
				</tbody>
				</table>
			</tr>
        <table class="table-form">
            <tr >
                <td class="tit" colspan="6" style="font-size: 18px; font-weight:bold;">相关附件（归档时必须上传）</td>
            </tr>
            <tr>
                <td colspan="1" width="10%">
                    序号
                </td>
                <td colspan="3" width="30%">
                    填写类型
                </td>
                <td colspan="6" width="60%">
                    内容
                </td>
            </tr>

            <tr>
                <td colspan="1" width="10%">
                <input id="ctx" type="hidden" value="${ctx}" />
                    1
                </td>
                <td colspan="3" width="30%">
                合同或委托函(<span style="color: red">必传</span>)
                </td>
                <td colspan="6" width="60%">
                    <div class="control-group" align="left">
                        <div class="controls1">
                            <input class="attachmentImageTableId"    type="hidden"   name="attachmentId" value="${tableId}"/>
                            <input class="attachmentImageTableType"    type="hidden"   name="attachmentId" value="${tableType}"/>
                            <input class="attachmentImageIds"    id="infoAttach" type="hidden"   name="attachmentId" value=""/>
                            <input class="attachmentImageType"    type="hidden"   value="1"/>
                            <ol class="attachmentImageOl" id="info">
                                <c:forEach items="${attachList}" var="attach">
                                    <c:if test="${attach.type == '1'}">
                                        <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'><c:if test="${applySignature.status =='0'|| applySignature.status =='1' }">×</c:if></a> </li>
                                    </c:if>
                                </c:forEach>	
                            </ol>
                            <c:if test="${applySignature.status =='1' || applySignature.status =='0' }">
                            <input type="button" class="btn" value="添加附件"  onclick="uploadImage(this);" />
                            </c:if>
                        </div>
                    </div>
                </td>
            </tr>
            
            <tr>
                <td colspan="1" width="10%">
                <input id="ctx" type="hidden" value="${ctx}" />
                    2
                </td>
                <td colspan="3" width="30%">
                   封面页及盖章页(<span style="color: red">必传</span>)
                </td>
                <td colspan="6" width="60%">
                    <div class="control-group" align="left">
                        <div class="controls1">
                            <input class="attachmentImageTableId"    type="hidden"   name="attachmentId" value="${tableId}"/>
                            <input class="attachmentImageTableType"    type="hidden"   name="attachmentId" value="${tableType}"/>
                            <input class="attachmentImageIds"    id="infoAttach" type="hidden"   name="attachmentId" value=""/>
                            <input class="attachmentImageType"    type="hidden"   value="2"/>
                            <ol class="attachmentImageOl" id="info2">
                                <c:forEach items="${attachList}" var="attach">
                                    <c:if test="${attach.type == '2'}">
                                        <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage/id/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId("${attach.id}",this);'><c:if test="${applySignature.status =='0'|| applySignature.status =='1' }">×</c:if></a> </li>
                                    </c:if>
                                </c:forEach>	
                            </ol>
                            <c:if test="${applySignature.status =='1' || applySignature.status =='0' }">
                            <input type="button" class="btn" value="添加附件"  onclick="uploadImage(this);" />
                            </c:if>
                        </div>
                    </div>
                </td>
            </tr>
            
            <tr>
                <td colspan="1" width="10%">
                <input id="ctx" type="hidden" value="${ctx}" />
                    3
                </td>
                <td colspan="3" width="30%">
                    署名页(<span style="color: red">必传</span>)
                </td>
                <td colspan="6" width="60%">
                    <div class="control-group" align="left">
                        <div class="controls1">
                            <input class="attachmentImageTableId"    type="hidden"   name="attachmentId" value="${tableId}"/>
                            <input class="attachmentImageTableType"    type="hidden"   name="attachmentId" value="${tableType}"/>
                            <input class="attachmentImageIds"    id="infoAttach" type="hidden"   name="attachmentId" value=""/>
                            <input class="attachmentImageType"    type="hidden"   value="3"/>
                            <ol class="attachmentImageOl" id="info3">
                                <c:forEach items="${attachList}" var="attach">
                                    <c:if test="${attach.type == '3'}">
                                        <li id="${attach.id}"> <a href="${ctx}/uploadImage/showImage3/id3/${attach.id}" target='_blank'  >${attach.fileName}</a> &nbsp;&nbsp;<a href="javascript:" onclick='deleteImageId("${attach.id}",this);'><c:if test="${applySignature.status =='0'|| applySignature.status =='1' }">×</c:if></a> </li>
                                    </c:if>
                                </c:forEach>	
                            </ol>
                            <c:if test="${applySignature.status =='1' || applySignature.status =='0' }">
                            <input type="button" class="btn" value="添加附件"  onclick="uploadImage(this);" />
                            </c:if>
                        </div>
                    </div>
                </td>
            </tr>
            

        </table>
			
			
			
		</table>	
		
		<div class="form-actions">
			<shiro:hasPermission name="signature:applySignature:edit">
			
			<c:if test="${applySignature.status =='0' }">
				<input id="btnTiJiao" class="btn btn-primary" type="button" value="提交"  onclick="submitApply()"/>&nbsp;
			</c:if>
			<c:if test="${applySignature.status =='1' }">
			    <input id="btnGuiDang" class="btn btn-primary" type="button" value="归档" onclick="FileApply()"/>&nbsp;
			</c:if>
			<c:if test="${applySignature.status =='1' || applySignature.status =='0' }">
			    <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
			</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>