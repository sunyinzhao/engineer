<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>咨询工程师证书管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

        var CryptoAgent = "";
        function OnLoad() {
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
        }

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

            OnLoad();

            SelectCertificateOnClick();
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

                    return;
                }
                return signature;
                //document.getElementById("Signature").value = signature;
            } catch (e) {
                var errorDesc = CryptoAgent.GetLastErrorDesc();
                alert(errorDesc);
            }
        }


        function sign(){
            var signCode = "";
            var serialNumber = $("#SerialNumber").val();
            if(serialNumber ==""){
                alert("请先选择电子证书");
            }
            var url = "${ctx}/certificate/engineerCertificate/getPdfHashCode?engineerCertificateId="+"${engineerCertificate.id}"+"&serialNumber="+serialNumber;
            $.ajax({
                type: 'POST'
                ,url: url
                ,dataType: 'text'
                ,async:false
                ,success: function(data) {

                    var parsedJson = jQuery.parseJSON(data);
                    var pdfHash=parsedJson.pdfHash;
                    var hashId =parsedJson.hashId;
                    //var signCode= SignPKCS7(pdfHash,"SHA-1");
                    var signCode= encodeURIComponent(SignOnClick1(pdfHash));
                    var ur2 = "${ctx}/certificate/engineerCertificate/signaturePdf?signCode="+signCode+"&hashId="+hashId+"&engineerCertificateId="+"${engineerCertificate.id}"+"&serialNumber="+serialNumber;
                    $.ajax({
                        type: 'POST'
                        ,url: ur2
                        ,dataType: 'text'
                        ,async:false
                        ,success: function(data) {
                            if(data=='Y'){
                                alert("盖章成功！");
                            }
                        },error: function(error){
                            alert("盖章失败");
                        }
                    });


                },error: function(error){
                    alert("error");
                }
            });
        }

    </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/certificate/engineerCertificate/">咨询工程师证书列表</a></li>
		<li class="active"><a href="${ctx}/certificate/engineerCertificate/form?id=${engineerCertificate.id}">咨询工程师证书<shiro:hasPermission name="certificate:engineerCertificate:edit">${not empty engineerCertificate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="certificate:engineerCertificate:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="engineerCertificate" action="${ctx}/certificate/engineerCertificate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
        <form:hidden path="batchId"/>


		<legend>咨询工程师（投资）证书</legend>
		<sys:message content="${message}"/>

    <table class="table-form">
        <tr>
            <td class="tit" style="font-weight: bold;" >姓名：</td>
            <td>
                    ${engineerCertificate.name }

            </td>

            <td class="tit" style="font-weight: bold;">身份证号：</td>
            <td>
                    ${engineerCertificate.certificateNum }

            </td>
            <td class="tit" style="font-weight: bold;">性别：</td>
            <td>
                    ${engineerCertificate.sex }

            </td>

        </tr>

        <tr>
            <td class="tit" style="font-weight: bold;" >主专业：</td>
            <td>
                    ${engineerCertificate.specialtyMain }

            </td>

            <td class="tit" style="font-weight: bold;">辅专业：</td>
            <td>
                    ${engineerCertificate.specialtyAuxiliary }

            </td>

            <td class="tit" style="font-weight: bold;">批准日期：</td>
            <td>
                    <fmt:formatDate value="${engineerCertificate.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
        </tr>


        <tr>
            <td class="tit" style="font-weight: bold;" >执业单位：</td>
            <td>
                    ${engineerCertificate.practisingCompany }

            </td>

            <td class="tit" style="font-weight: bold;">证书编号：</td>
            <td colspan="">
                    ${engineerCertificate.registerCertificateNum }

            </td>
            <td class="tit" style="font-weight: bold;">颁发机构：</td>
            <td>
                    ${engineerCertificate.awardOrg }
            </td>


        </tr>
<%--
        <shiro:hasPermission name="enterprise:enterpriseCertificate:edit">
--%>
            <tr>
                <td class="tit" style="font-weight: bold;">电子章：</td>
                <td colspan="5">
                    <input type="button" name="SelectCertificateButton" value="[选择证书]"
                           onclick="SelectCertificateOnClick()">
                    <input type="text" id="SubjectCN" style="width: 300px" readonly="readonly">
                    <input type="hidden" id="SerialNumber" style="width: 200px">
                </td>
            </tr>
        <%--</shiro:hasPermission>--%>


    </table>

		<div class="form-actions">
			<shiro:hasPermission name="certificate:engineerCertificate:edit">

                <%--<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;--%>

                    <input id="btnSign" class="btn btn-primary" type="button" value="签章" onclick="sign()"/>


            </shiro:hasPermission>


			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>