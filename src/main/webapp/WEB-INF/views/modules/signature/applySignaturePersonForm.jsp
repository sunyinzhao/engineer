<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>签章咨询工程师（投资）管理</title>
	<meta name="decorator" content="default"/>
	<object id="KeyCtl" classid="CLSID:E7EE19DB-2342-4F2F-B220-F96CD2AF95F4" width="0" height="0"></object>




	<script type="text/javascript">



        $(document).ready(function() {
            var ievs= IEVersion();
            var status = "${applySignaturePerson.status}";
            if(status =="0"){
                if(ievs == -1){
                    alert("您使用的不是IE浏览器，请使用IE浏览器进行盖章操作");
                    window.history.go(-1);
                }else if(ievs == 'edge'){
                    alert("您使用的是EDGE浏览器，请使用IE浏览器进行盖章操作");
                    window.history.go(-1)
                }
            }

        });
        function IEVersion() {
            var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
            var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器
            var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器
            var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
            if(isIE) {
                var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
                reIE.test(userAgent);
                var fIEVersion = parseFloat(RegExp["$1"]);
                if(fIEVersion == 7) {
                    return 7;
                } else if(fIEVersion == 8) {
                    return 8;
                } else if(fIEVersion == 9) {
                    return 9;
                } else if(fIEVersion == 10) {
                    return 10;
                } else {
                    return 6;//IE版本<=7
                }
            } else if(isEdge) {
                return 'edge';//edge
            } else if(isIE11) {
                return 11; //IE11
            }else{
                return -1;//不是ie浏览器
            }
        }







        var CryptoAgent = "";

		$(document).ready(function() {

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

            try {
                var eDiv = document.createElement("div");
                if (navigator.appName.indexOf("Internet") >= 0 || navigator.appVersion.indexOf("Trident") >= 0) {
                    if (window.navigator.cpuClass == "x86") {
                        eDiv.innerHTML = "<object id='CryptoAgent' codebase='${pageContext.request.contextPath}/download/CryptoKit.Paperless.x86.cab' classid='clsid:B64B695B-348D-400D-8D58-9AAB1DA5851A' ></object>";
                    }
                    else {
                        eDiv.innerHTML = "<object id='CryptoAgent' codebase='${pageContext.request.contextPath}/download/CryptoKit.Paperless.x64.cab' classid='clsid:8BF7E683-630E-4B59-9E61-C996B671EBDF' ></object>";
                    }
                }
                else {
                    eDiv.innerHTML = "<embed id='CryptoAgent' type='application/npCryptoKit.Paperless.x86' style='height: 0px; width: 0px'>";
                }
                document.body.appendChild(eDiv);
            }
            catch (e) {
                alert(e);
                return;
            }
            CryptoAgent = document.getElementById("CryptoAgent");

		});


        // Select certificate 选择证书
        function SelectCertificateOnClick() {
            try {

                var subjectDNFilter = "";
                var issuerDNFilter = "";
                var serialNumFilter = "";
                var cspName = "";
                var bSelectCertResult = "";

                bSelectCertResult = CryptoAgent.SelectCertificate(subjectDNFilter, issuerDNFilter, serialNumFilter, cspName);
                // Opera浏览器，NPAPI函数执行结果为false时，不能触发异常，需要自己判断返回值。
                if (!bSelectCertResult) {
                    var errorDesc = CryptoAgent.GetLastErrorDesc();
                    alert(errorDesc);
                    return;
                }
                //document.getElementById("SelectCertResult").value = bSelectCertResult;
                GetCertInfo();

            }
            catch (e) {
                var errorDesc = CryptoAgent.GetLastErrorDesc();
                alert(errorDesc);
            }
        }


        // Get certificate information 获取证书信息
        function GetCertInfo(InfoTypeID) {

            try {

                var SubjectCN = "";

                document.getElementById("SubjectCN").value = "";
                SubjectCN = CryptoAgent.GetSignCertInfo("SubjectCN");
                // Opera浏览器，NPAPI函数执行结果为false时，不能触发异常，需要自己判断返回值。
                if (!SubjectCN) {
                    var errorDesc = CryptoAgent.GetLastErrorDesc();
                    alert(errorDesc);
                    return;
                }
                document.getElementById("SubjectCN").value = SubjectCN;

                var SubjectCN = "";

                document.getElementById("SerialNumber").value = "";
                SubjectCN = CryptoAgent.GetSignCertInfo("SerialNumber");
                // Opera浏览器，NPAPI函数执行结果为false时，不能触发异常，需要自己判断返回值。
                if (!SubjectCN) {
                    var errorDesc = CryptoAgent.GetLastErrorDesc();
                    alert(errorDesc);
                    return;
                }
                document.getElementById("SerialNumber").value = SubjectCN;

            } catch (e) {
                var errorDesc = CryptoAgent.GetLastErrorDesc();
                alert(errorDesc);
            }
        }



        function SignPKCS7(code ,algorithm) {
            try {
                var sourceHashData = "";
                var signature = "";
                document.getElementById("Signature").value = "";
                sourceHashData = document.getElementById("SourceHashData").value;
                signature = CryptoAgent.SignHashMsgPKCS7Detached(code, algorithm);
                if (!signature) {
                    var errorDesc = CryptoAgent.GetLastErrorDesc();
                    alert(errorDesc);
                    return;
                }
                return signature;
                // document.getElementById("Signature").value = signature;
            } catch (e) {
                var errorDesc = CryptoAgent.GetLastErrorDesc();
                alert(errorDesc);
            }
            return "";
        }



        function SignOnClick1(sourceHashData) {
            try {
                var signature = "";
                signature = CryptoAgent.SignHashMsgPKCS7Detached(sourceHashData, "SHA-1");
                if (!signature) {
                    var errorDesc = CryptoAgent.GetLastErrorDesc();
                    alert(errorDesc);
                    return;
                }
                return signature;
                //document.getElementById("Signature").value = signature;
            } catch (e) {
                var errorDesc = CryptoAgent.GetLastErrorDesc();
                alert(errorDesc);
            }
        }



        function sign( ){
            var serialNumber= $("#SerialNumber").val();
            if($.trim(serialNumber) == ''){
            	alert("您未插入UKey,请插入Ukey后再进行盖章。");
            	return;
            }
            //ukeyId
            var ukeyId="";
            try{
                ukeyId = KeyCtl.GetKeySn();
                if(ukeyId==""){
                    alert("您未插入UKey或UKey型号不正确");
                    return false;
                }else{
                    //获取数据处理
                }
            }catch(e){
                alert("您未安装“签章控件”，请安装！");
                return false;
            }

            var id = "${applySignaturePerson.id}";
            var signCode = "";
            var url = "${ctx}/signature/applySignaturePerson/getPdfHashCode?id="+id+"&serialNumber="+serialNumber+"&ukeyId="+ukeyId;
            $.ajax({
                type: 'POST'
                ,url: url
                ,dataType: 'text'
                ,async:false
                ,success: function(data) {
                    var parsedJson = jQuery.parseJSON(data);
                    var pdfHash=parsedJson.pdfHash;
                    var hashId =parsedJson.hashId;
                    var fileSize = parsedJson.fileSize;
                    var result = parsedJson.result;
                    if(result=="A"){
                    	alert("您未选择证书，请选择证书后再试。");
                    }else if(result=="B"){
                        alert("您未安装“签章控件”");
                    }else if(result=="C"){
                        alert("同一个Ukey中不可以存在多个电子章");
                    }else if(result=="D"){
                        alert("请解除咨询师原电子章的绑定关系");
                    }else if(result=="N"){
                        alert("其他工程师正在签章，请稍后再试");
                    }else if(result=="E"){
                    	alert(parsedJson.returnMessage);
                    }else if(result=="BLANK"){
                    	alert("此工程师电子章无效！");
                    }else if(result =="Y"){                        
                        var signCode= encodeURIComponent(SignOnClick1(pdfHash));
                        var ur2 = "${ctx}/signature/applySignaturePerson/signaturePdf?signCode="+signCode+"&hashId="+hashId+"&id="+id+"&serialNumber="+serialNumber;
                        $.ajax({
                            type: 'POST'
                            ,url: ur2
                            ,dataType: 'text'
                            ,async:false
                            ,success: function(data) {
                                if(data=='Y'){
                                    alert("签章成功");
                                }else if(data =='N'){
                                    alert("签章失败！");
                                }else{
                                    alert("其他工程师正在签章，请稍后再试...");
                                }
                            },error: function(error){
                                return false;
                            }
                        });
                    }
                },error: function(error){
                    alert("error");
                }
            });
        }

        function getUKeySn(){
            try{
                sn = KeyCtl.GetKeySn();
                if(sn==""){
                    alert("您未插入UKey或UKey型号不正确");
                }else{
                    //获取数据处理
                    alert("成功:"+sn);
                }
            }catch(e){
                alert(e);
                alert("您未安装“签章控件”，请安装！");
            }
        }

        function download(type){
            location.href='${ctx}/certificate/engineerCertificate/downloadFile?type='+type;
        }

	</script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/signature/applySignaturePerson/">已签章业绩列表</a></li>
		<li class="active"><a href="${ctx}/signature/applySignaturePerson/form?id=${applySignaturePerson.id}">签章咨询工程师（投资）<shiro:hasPermission name="signature:applySignaturePerson:edit">${not empty applySignaturePerson.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="signature:applySignaturePerson:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="applySignaturePerson" action="${ctx}/signature/applySignaturePerson/save" method="post" class="form-horizontal">
	
		<form:hidden path="id"/>
		
		<sys:message content="${message}"/>	
		<legend>签章业绩</legend>
        <c:if test="${applySignaturePerson.status =='0'}">
            <div class="control-group">
                <li class="btns">
                    <input  class="btn btn-primary" type="button" onclick="download('1');" value="浏览器插件"/>
                    <input  class="btn btn-primary" type="button" onclick="download('2');" value="证书工具"/>
                    <input  class="btn btn-primary" type="button" onclick="download('3');" value="签章控件"/>
					<input  class="btn btn-primary" type="button" onclick="download('4');" value="证书工具(指纹)"/>
                </li>
            </div>
        </c:if>


        <table class="table-form">
			<tr>
				<td class="tit">项目名称：</td>
				<td>
					<form:input path="applySignature.projectName" htmlEscape="false" maxlength="200" disabled="true" class="input-medium required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
				<td class="tit">服务范围：</td>
				<td>
				    <form:select path="applySignature.services" class="input-medium " disabled="true" >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('service_rang')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
			
				<td class="tit">服务范围小类</td>
				<td>
					<form:select path="applySignature.childServices" class="input-medium " disabled="true" >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('service_rang_'.concat(applySignaturePerson.applySignature.services))}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
			    </td>
			</tr>


			<tr>
				<td class="tit">项目性质：</td>
				<td>
					<form:select path="applySignature.projectProperty" class="input-medium  " disabled="true">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('project_property')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</td>
				<td class="tit">合同号或批复号：</td>
				<td>
					<form:input path="applySignature.contractNum" htmlEscape="false"  maxlength="30" class="input-medium " disabled="true"/>
				</td>
				<td class="tit">项目专业：</td>
				<td>
					<form:select path="applySignature.projectSpecialty" class="input-medium "  disabled="true">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false" disabled="true"/>
					</form:select>

					<form:select path="applySignature.projectSpecialtyChild"  cssStyle="width: 80px;"  disabled="true">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('specialty_type_'.concat(applySignaturePerson.applySignature.projectSpecialty))}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>

			</tr>

			

			
			<tr>
				<td class="tit">投资额：</td>
				<td>
					<form:input path="applySignature.projectInvestAmount" htmlEscape="false" class="input-medium " disabled="true" />
				</td>
				
			
				<td class="tit">地区：</td>
				<td>
					<form:select path="applySignature.area" class="input-medium " disabled="true" >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('area')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			    </td>
			    
			    <td class="tit">申请状态：</td>
				<td>
<%-- 				    <form:input path="status" htmlEscape="false" maxlength="1" class="input-medium "/> --%>
				    <c:choose>
				    	<c:when test="${applySignaturePerson.applySignature.status =='0' }">未提交</c:when>
				    	<c:when test="${applySignaturePerson.applySignature.status =='1' }">咨询工程师（投资）签章</c:when>
				    	<c:when test="${applySignaturePerson.applySignature.status =='2' }">完成</c:when>
				    </c:choose>
				    
				    
				</td>
			</tr>
			
			<tr>
				<td class="tit">备注：</td>
				<td colspan="5">
					<form:textarea path="applySignature.remarks" htmlEscape="false" rows="4" maxlength="100" class="input-xxlarge " disabled="true" />
				</td>
				
			</tr>
			
			
			<tr>
				<td class="tit">承担工作内容职责：</td>
				<td>
					<form:select path="duty" class="input-medium " disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('signature_duty')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>				
			
				<td class="tit">完成签章：</td>
				<td colspan="3">
					<form:select path="status" class="input-medium " disabled="true">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>	
			    </td>
			</tr>
            <c:if test="${applySignaturePerson.status =='0'}">
			 <tr>
                 <td class="tit">选择证书：</td>
                    <td colspan="5">
						<input type="button" name="SelectCertificateButton" value="[选择证书]"
							   onclick="SelectCertificateOnClick()">

						<input type="text" id="SubjectCN" class="input-medium" readonly="readonly">
						<input type="hidden" id="SerialNumber" style="width: 200px">
                    </td>
                </tr>
            </c:if>
		</table>
		<div class="form-actions">
            <c:if test="${applySignaturePerson.status =='0'}">
			<shiro:hasPermission name="signature:applySignaturePerson:edit">

			<input id="btnCancel" class="btn btn-primary" type="button" value="盖章" onclick="sign()"/>
			</shiro:hasPermission>
            </c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>


</body>
</html>