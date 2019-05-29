<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>咨询师管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.cover{
			text-align: right;
		}
		.cssNav li.finish{  
		    padding: 0px 40px;   
		    line-height: 40px;  
		    background: #50abe4;  
		    display: inline-block;   
		    color: #fff;  
		    position: relative;  
		}

		.cssNav li.finish:after{  
		    content: '';  
		    display: block;  
		    border-top: 20px solid transparent;  
		    border-bottom: 20px solid transparent;  
		    border-left: 20px solid #50abe4;  
		    position: absolute;   
		    right: -20px;   
		    top: 0;  
		    z-index: 10;  
		}
		.cssNav li.finish:before{  
		    content: '';  
		    display: block;  
		    border-top: 20px solid #50abe4;  
		    border-bottom: 20px solid #50abe4;  
		    border-left: 20px solid #fff;  
		    position: absolute;   
		    left: 0px;   
		    top: 0;  
		}
		.cssNav li.finish:first-child{    
		    border-radius: 4px 0 0 4px;    
		    padding-left: 25px;  
		}    
		.cssNav li.finish:last-child,.cssNavEnd{    
		    border-radius: 0px 4px 4px 0px;    
		    padding-right: 25px;  
		}    
		.cssNav li.finish:first-child:before{    
		    display: none;    
		}    
		.cssNav li.finish:last-child:after,.cssNavEnd:after{    
		    display: none;    
		}
		
		.cssNav li.ongoing{  
		    padding: 0px 40px;   
		    line-height: 40px;  
		    background: #E6E6E6;  
		    display: inline-block;   
		    color: black;  
		    position: relative;  
		}

		.cssNav li.ongoing:after{  
		    content: '';  
		    display: block;  
		    border-top: 20px solid transparent;  
		    border-bottom: 20px solid transparent;  
		    border-left: 20px solid #E6E6E6;  
		    position: absolute;   
		    right: -20px;   
		    top: 0;  
		    z-index: 10;  
		}
		.cssNav li.ongoing:before{  
		    content: '';  
		    display: block;  
		    border-top: 20px solid #E6E6E6;  
		    border-bottom: 20px solid #E6E6E6;  
		    border-left: 20px solid #fff;  
		    position: absolute;   
		    left: 0px;   
		    top: 0;  
		}
		.cssNav li.ongoing:first-child{    
		    border-radius: 4px 0 0 4px;    
		    padding-left: 25px;  
		}    
		.cssNav li.ongoing:last-child,.cssNavEnd{    
		    border-radius: 0px 4px 4px 0px;    
		    padding-right: 25px;  
		}    
		.cssNav li.ongoing:first-child:before{    
		    display: none;    
		}    
		.cssNav li.ongoing:last-child:after,.cssNavEnd:after{    
		    display: none;    
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
        function submitUnit(id){
            var url = '${ctx}/counselor/submitUnit?id='+id
            $.ajax({
                url:url,
                type: 'POST',
                success:function(data){
                    if(data=='201'){
                        alert("尚有信息未确认")
                    }else if(data=="200"){
                        alert("上报成功")
                        location.href='${ctx}/counselor/list'
                    }

                }
            })
        }
        function saveBaseInfo(){
            $("#status").val("1");
            $("#inputForm").submit();
        }

        function printInfo(){
            location.href='${ctx}/counselor/info/infoWindow';
        }

        function windowOpenReturn(id,type){
            var url ='${ctx}/counselor/window/queryReturn?id='+id+'&type='+type
            var name = ''
            var iHeight=400;
            var iWidth = 500;
            //window.open ("${ctx}/counselor/window/queryReturn?id="+id,"window","height=100,width=100,scrollbars=no,location=no");
            var iTop = (window.screen.height-30-iHeight)/2; //获得窗口的垂直位置;
            var iLeft = (window.screen.width-10-iWidth)/2; //获得窗口的水平位置;
            window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
        }
        
        function fileCoverPrint(personRecordId){
        	window.location.href = "${ctx}/counselor/coverPrint/fileCover?id="+personRecordId+"&declareType="+'4';
        }

        function openFail(examineId,recordId,type){//新增一个窗口,用来查找不通过与不真实的列表
            var url ='${ctx}/counselor/feedBack/openFail?examineId='+examineId+'&recordId='+recordId+'&examineType='+type
            //三个参数,1 examineId, type为初审/终审的值
            /*$.jBox.open("iframe:"+url,'不符合项',1200,540,{
                buttons:{'关闭':true}
            })*/
            location.href=url
        }

	</script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="">申请单列表</a></li>
    <li class="active"><a href="">注销登记</a></li>
</ul>
	<form:form id="inputForm" modelAttribute="enterpriseWorkers" action="" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
        <legend>注销登记</legend>
        <div class="cover">
        	<input type="button" class="btn btn-primary" value="档案袋封面打印" onclick="fileCoverPrint('${personRecordId}')">&nbsp;&nbsp;
<!--             <input type="button" class="btn btn-primary" value="登记证书预览" onclick="printInfo()"> -->
        </div>
        
        <c:if test="${empty counselor.batchStatus ||counselor.batchStatus ==1||counselor.batchStatus==2 }">
		        <div align="center">
		    <ul class="cssNav">
		        <li class="finish">
		          	  个人申报
		        </li>
		        <li class="ongoing">
		          	  单位申报
		        </li>
		        <li class="ongoing">
		          	  预审中
		        </li>
		        <li class="ongoing">
		          	  终审中
		        </li>
		        <li class="ongoing">
		          	公告
		        </li>
		    </ul>
		</div>
		</c:if>
		
		<c:if test="${counselor.batchStatus ==3||counselor.batchStatus==21 }">
		        <div align="center">
		    <ul class="cssNav">
		        <li class="finish">
		          	  个人申报
		        </li>
		        <li class="finish">
		          	  单位申报
		        </li>
		        <li class="ongoing">
		          	  预审中
		        </li>
		        <li class="ongoing">
		          	  终审中
		        </li>
		        <li class="ongoing">
		          	公告
		        </li>
		    </ul>
		</div>
		</c:if>
		
		<c:if test="${counselor.batchStatus==5||counselor.batchStatus==6||counselor.batchStatus==7||counselor.batchStatus==8||counselor.batchStatus==9||counselor.batchStatus==10}">
		        <div align="center">
		    <ul class="cssNav">
		        <li class="finish">
		          	  个人申报
		        </li>
		        <li class="finish">
		          	  单位申报
		        </li>
		        <li class="finish">
		          	  预审中
		        </li>
		        <li class="ongoing">
		          	  终审中
		        </li>
		        <li class="ongoing">
		          	公告
		        </li>
		    </ul>
		</div>
		</c:if>
		
		<c:if test="${counselor.batchStatus==11||counselor.batchStatus==12||counselor.batchStatus==13||counselor.batchStatus==14||counselor.batchStatus==15||counselor.batchStatus==16||counselor.batchStatus==17||counselor.batchStatus==18||counselor.batchStatus==19}">
		        <div align="center">
		    <ul class="cssNav">
		        <li class="finish">
		          	  个人申报
		        </li>
		        <li class="finish">
		          	  单位申报
		        </li>
		        <li class="finish">
		          	  预审中
		        </li>
		        <li class="finish">
		          	  终审中
		        </li>
		        <li class="ongoing">
		          	公告
		        </li>
		    </ul>
		</div>
		</c:if>
		
		<c:if test="${counselor.batchStatus==20}">
		        <div align="center">
		    <ul class="cssNav">
		        <li class="finish">
		          	  个人申报
		        </li>
		        <li class="finish">
		          	  单位申报
		        </li>
		        <li class="finish">
		          	  预审中
		        </li>
		        <li class="finish">
		          	  终审中
		        </li>
		        <li class="finish">
		          	公告
		        </li>
		    </ul>
		</div>
		</c:if>
        <table class="table-form">
            <tr>

                <td class="tit">姓名：</td>
                <td>
                    <form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly="true"/>
                </td>

                <td class="tit">性别:</td>
                <td>
                    <form:input path="sex" value="${fns:getDictLabel(enterpriseWorkers.sex,'sex','' )}" cssStyle="width: 80%" readonly="true"/>
                </td>

                <td class="tit">年龄:</td>
                <td>
                        <form:input path="age" htmlEscape="false" maxlength="100" class="input-xlarge  " readonly="true"/>
                </td>

            </tr>

            <tr>

                <td class="tit">证件类型：</td>
                <td>
                    <c:choose>
                        <c:when test="${enterpriseWorkers.certificatesType!=null&&enterpriseWorkers.certificatesType!=''}">
                            <form:input path="certificatesType" value="${fns:getDictLabel(enterpriseWorkers.certificatesType,'ID_type','' )}" readonly="true" cssStyle="width: 90%" />
                        </c:when>
                        <c:otherwise>
                            <form:input path="certificatesType"  cssStyle="width: 90%" readonly="true"/>
                        </c:otherwise>
                    </c:choose>
                </td>

                <td class="tit">证件号：</td>
                <td>
                    <form:input path="certificatesNum" htmlEscape="false" maxlength="100" class="input-xlarge required " readonly="true"/>
                </td>

                <td class="tit">手机号：</td>
                <td>
                        <form:input path="mobile" htmlEscape="false" maxlength="100" class="input-xlarge required " readonly="true"/>
                </td>

            </tr>

            <tr>

                <td class="tit">邮箱：</td>
                <td>
                    <form:input path="email" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly="true"/>
                </td>

                <td class="tit">执业单位：</td>
                <td>

                    <form:input path="companyName" htmlEscape="false" maxlength="100" class="input-xlarge  required" readonly="true"/>
                </td>



            </tr>

            <tr>
                <td class="tit">主专业</td>
                <td>
                    <c:choose>
                        <c:when test="${enterpriseWorkers.registerMainSpecialty!=null&&enterpriseWorkers.registerMainSpecialty!=''}">
                            <form:input path="registerMainSpecialty" value="${fns:getDictLabel(enterpriseWorkers.registerMainSpecialty,'specialty_type','' )}" readonly="true" cssStyle="width: 78%" />
                        </c:when>
                        <c:otherwise>
                            <form:input path="registerMainSpecialty"  cssStyle="width: 78%" readonly="true"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="tit">辅专业：</td>
                <td>
                        <%-- <form:input path="registerAuxiliarySpecialty" htmlEscape="false" maxlength="100" class="input-xlarge  required" readonly="true"/>--%>
                    <c:choose>
                        <c:when test="${enterpriseWorkers.registerAuxiliarySpecialty!=null&&enterpriseWorkers.registerAuxiliarySpecialty!=''}">
                            <form:input path="registerAuxiliarySpecialty" value="${fns:getDictLabel(enterpriseWorkers.registerAuxiliarySpecialty,'specialty_type','' )}" readonly="true" cssStyle="width: 78%" />
                        </c:when>
                        <c:otherwise>
                            <form:input path="registerAuxiliarySpecialty"  cssStyle="width: 78%" readonly="true"/>
                        </c:otherwise>
                    </c:choose>
                </td>

            </tr>
        </table>
        <br/>


<!--         注销原因: -->
<!--         <select> -->
<%--             <c:forEach items="${cancelList}" var="cancel"> --%>
<!--             <option></option> -->
<%--             </c:forEach> --%>
<!--         </select> -->


        <table id="contentTable" class="table table-striped table-bordered table-condensed">
            <thead>
            <tr>
                <th>序号</th>
                <th>填写内容</th>
                <th>填写状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${tableList}" var="table" varStatus="index">
                <tr>
                    <td> ${index.index+1}</td>
                    <td>
                            ${fns:getDictLabel(table.type, 'person_type', '')}
                    </td>
                    <td>

                            ${table.status=='0'?'未确认':'已确认'}
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty look}">
                                <a href="${ctx}/counselor/tableForm?id=${table.id}&personId=${enterpriseWorkers.id}&recordId=${personRecordId}&look=${look}">查看</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${ctx}/counselor/tableForm?id=${table.id}&personId=${enterpriseWorkers.id}&recordId=${personRecordId}">编辑</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%--<c:if test="${status=='17' && ((firstZdeclareResult!='1' && firstZdeclareResult!=''&&firstZdeclareResult!=null)||(secondZdeclareResult!='1' && secondZdeclareResult!=''&&secondZdeclareResult!=null))}">--%>
        <c:if test="${fyAdvice}">
            <a onclick="windowOpenReturn('${personRecordId}','44')" style="cursor: pointer">复核意见</a>
            <br/>
        </c:if>
        <c:if test="${status=='17'||status=='20'}">
            <a onclick="openFail('','${personRecordId}','4')" style="cursor: pointer">终审意见告知</a>
        </c:if>
        <br/>
        <c:if test="${hgReturn}">
            <a onclick="windowOpenReturn('${personRecordId}','22')" style="cursor: pointer">合规退回原因</a>
            <br/>
        </c:if>
        <c:if test="${ysReturn}">
            <a onclick="windowOpenReturn('${personRecordId}','33')" style="cursor: pointer">预审退回原因</a>
            <br/>
        </c:if>
        <c:if test="${reReturn}">
            <a onclick="windowOpenReturn('${personRecordId}','receive')" style="cursor: pointer">预审退回原因</a>
            <br/>
        </c:if>
		<div class="form-actions">
			<c:if test="${empty look}">
            <input id="btnSubmit" class="btn btn-primary" type="button" value="上报单位" onclick="submitUnit('${personRecordId}')"/>
            </c:if>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			<%--<shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
            <shiro:hasPermission name="enterprise:enterpriseChangeInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" onclick="saveBaseInfo();"/>&nbsp;</shiro:hasPermission>
            --%>
		</div>
	</form:form>


</body>
</html>