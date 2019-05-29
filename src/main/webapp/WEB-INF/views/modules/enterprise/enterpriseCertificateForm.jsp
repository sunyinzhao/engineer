<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业证书管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
    // Create ActiveX object according to the platform
    var CryptoAgent = "";
    function OnLoad() {
        try {
            var eDiv = document.createElement("div");
            if (navigator.appName.indexOf("Internet") >= 0 || navigator.appVersion.indexOf("Trident") >= 0) {
                if (window.navigator.cpuClass == "x86") {
                    eDiv.innerHTML = "<object id=\"CryptoAgent\" codebase=\"CryptoKit.Paperless.x86.cab\" classid=\"clsid:B64B695B-348D-400D-8D58-9AAB1DA5851A\" ></object>";
                }
                else {
                    eDiv.innerHTML = "<object id=\"CryptoAgent\" codebase=\"CryptoKit.Paperless.x64.cab\" classid=\"clsid:8BF7E683-630E-4B59-9E61-C996B671EBDF\" ></object>";
                }
            }
            else {
                eDiv.innerHTML = "<embed id=\"CryptoAgent\" type=\"application/npCryptoKit.Paperless.x86\" style=\"height: 0px; width: 0px\">";
            }
            document.body.appendChild(eDiv);
        }
        catch (e) {
            alert(e);
            return;
        }
        CryptoAgent = document.getElementById("CryptoAgent");
    }
    // Select certificate 选择证书
    function SelectCertificateOnClick() {
        try {
            //document.getElementById("SelectCertResult").value = "";

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
       /*  <input type="radio" name="InfoTypeID" value="SubjectDN" checked="checked" />SubjectDN
        <input type="radio" name="InfoTypeID" value="SubjectCN" />SubjectCN
        <input type="radio" name="InfoTypeID" value="SerialNumber" />SerialNumber
        <input type="radio" name="InfoTypeID" value="CSPName" />CSPName
        <input type="radio" name="InfoTypeID" value="CertType" />CertType
    	<input type="radio" name="InfoTypeID" value="Issuer" />Issuer
        <input type="radio" name="InfoTypeID" value="CertContent" />CertContent */
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
    
    
    // PKCS#7分离式哈希签名
  /*   function SignPKCS7(sourceHashData) {
        try {

            signature = CryptoAgent.SignHashMsgPKCS7Detached(sourceHashData, "SHA-1"); 
            if (!signature) {
                var errorDesc = CryptoAgent.GetLastErrorDesc();
                alert(errorDesc);
                return;
            }
			alert(signature);
           // document.getElementById("Signature").value = signature;
        } catch (e) {
            var errorDesc = CryptoAgent.GetLastErrorDesc();
            alert(errorDesc);
        }
    }
     */
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
             /* var sourceHashData = "";
             

             document.getElementById("Signature").value = "";
             sourceHashData = document.getElementById("SourceHashData").value; */

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
  
    
    function sign(){
    	
		var signCode = "";
		var url = "${ctx}/enterprise/enterpriseCertificate/getPdfHashCode?signatureId="+"${enterpriseCertificate.id}";
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
				var serialNumber = $("#SerialNumber").val();				
				var ur2 = "${ctx}/enterprise/enterpriseCertificate/signaturePdf?signCode="+signCode+"&hashId="+hashId+"&enterpriseCertificateId="+"${enterpriseCertificate.id}"+"&serialNumber="+serialNumber;
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
    
/**************************************/   
    // Get certificate information 获取证书信息
    function GetCertInfoOnClick() {
        try {
            var InfoTypeID = "";
            var InfoContent = "";

            document.getElementById("CertInfoContent").value = "";

            // certificate information identifier
            InfoTypeID = GetSelectedItemValue("InfoTypeID");

            InfoContent = CryptoAgent.GetSignCertInfo(InfoTypeID);
            // Opera浏览器，NPAPI函数执行结果为false时，不能触发异常，需要自己判断返回值。
            if (!InfoContent) {
                var errorDesc = CryptoAgent.GetLastErrorDesc();
                alert(errorDesc);
                return;
            }
            document.getElementById("CertInfoContent").value = InfoContent;
        } catch (e) {
            var errorDesc = CryptoAgent.GetLastErrorDesc();
            alert(errorDesc);
        }
    }
    
    
    

    function SelectObjctById(id) {
        var obj;
        if (document.getElementById) {
            obj = document.getElementById(id);
            //alert("ID");
        }
        else if (document.all) {
            obj = document.all(id);
            //alert("ALL");
        }
        else {
            alert("The Internet Browser does't support Document all or Document getElementById");
        }
        return obj;
    }

    // Get version
    function GetVersion_OnClick() {
        var version = "";
        try {
            version = CryptoAgent.GetVersion();
            alert(version);
        }
        catch (e) {
            var strErrorDsc = CryptoAgent.GetLastErrorDesc();
            alert(strErrorDsc);
        }
    }

 


	
	// Sign P1 message
    function SignP1OnClick() {
        try {
		    var selectedAlg = GetSelectedItemValue("p1algorithm");
            var sourceHashData = "";
            var signature = "";

            document.getElementById("P1Signature").value = "";
            sourceHashData = document.getElementById("P1SourceData").value;

            signature = CryptoAgent.SignMsgPKCS1(sourceHashData,  selectedAlg);
            if (!signature) {
                var errorDesc = CryptoAgent.GetLastErrorDesc();
                alert(errorDesc);
                return;
            }

            document.getElementById("P1Signature").value = signature;
        } catch (e) {
            var errorDesc = CryptoAgent.GetLastErrorDesc();
            alert(errorDesc);
        }
    }
	
	// Sign P7 message
    function SignP7OnClick() {
        try {
		    var selectedAlg = GetSelectedItemValue("p7algorithm");
            var sourceHashData = "";
            var signature = "";

            document.getElementById("P7Signature").value = "";
            sourceHashData = document.getElementById("P7SourceData").value;

            signature = CryptoAgent.SignMsgPKCS7(sourceHashData, selectedAlg, true);
            if (!signature) {
                var errorDesc = CryptoAgent.GetLastErrorDesc();
                alert(errorDesc);
                return;
            }

            document.getElementById("P7Signature").value = signature;
        } catch (e) {
            var errorDesc = CryptoAgent.GetLastErrorDesc();
            alert(errorDesc);
        }
    }

    // Sign hash message
    function SignOnClick() {
        try {
            var sourceHashData = "";
            var signature = "";

            document.getElementById("Signature").value = "";
            sourceHashData = document.getElementById("SourceHashData").value;

            signature = CryptoAgent.SignHashMsgPKCS7Detached(sourceHashData, "SHA-1");
            if (!signature) {
                var errorDesc = CryptoAgent.GetLastErrorDesc();
                alert(errorDesc);
                return;
            }

            document.getElementById("Signature").value = signature;
        } catch (e) {
            var errorDesc = CryptoAgent.GetLastErrorDesc();
            alert(errorDesc);
        }
    }

    function GetSelectedItemValue(itemName) {
        var ele = document.getElementsByName(itemName);
        for (i = 0; i < ele.length; i++) {
            if (ele[i].checked) {
                return ele[i].value;
            }
        }
    }

    function GetSelectedItemIndex(itemName) {
        var ele = document.getElementsByName(itemName);
        for (i = 0; i < ele.length; i++) {
            if (ele[i].checked) {
                return i;
            }
        }
    }
	
	function GetSealOnClick() {
	    try {
	        var p11name = document.getElementById("P11Name").value;
		    
		    var sealImage = CryptoAgent.GetSealImage(p11name);
		    
		    document.getElementById("SealImage").value = sealImage;
		} catch (e) {
		    var errorDesc = CryptoAgent.GetLastErrorDesc();
            alert(errorDesc);
		}
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
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/enterprise/enterpriseCertificate/">企业证书列表</a></li>
		<li class="active"><a href="${ctx}/enterprise/enterpriseCertificate/form?id=${enterpriseCertificate.id}">企业证书<shiro:hasPermission name="enterprise:enterpriseCertificate:edit">${not empty enterpriseCertificate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="enterprise:enterpriseCertificate:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="enterpriseCertificate" action="${ctx}/enterprise/enterpriseCertificate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="recordId" />
		<form:hidden path="certificateOriginal" />
		<form:hidden path="certificateCounterpart" />
		
		<legend>资信证书</legend>
		<sys:message content="${message}"/>	
		<table class="table-form">
			<tr>
				<td class="tit">级别：</td>
				<td>
					<form:select path="grade" class="input-medium ">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('declare_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>
				
				<td class="tit">证书类型：</td>
				<td>
					<form:select path="certificateType" class="input-medium ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('enterprise_certificate_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>

				<td class="tit">业务：</td>
				<td>
					<form:select path="specialty" class="input-medium ">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('specialty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				 </td>
			</tr>
			
			
			<tr>
				<td class="tit">颁发机构：</td>
				<td>
					<form:input path="awardOrg" htmlEscape="false" maxlength="50" class="input-medium "/>
				</td>
				
				<td class="tit">单位名称：</td>
				<td>
					<form:input path="companyName" htmlEscape="false" maxlength="200" class="input-medium "/>
				</td>

				<td class="tit">统一社会信用代码：</td>
				<td>
					<form:input path="organizationCode" htmlEscape="false" maxlength="30" class="input-medium "/>
				 </td>
			</tr>
			
			<tr>
				<td class="tit">证书编号：</td>
				<td>
					<form:input path="certificateNum" htmlEscape="false" maxlength="30" class="input-medium "/>
				</td>
				
				<td class="tit">发证日期：</td>
				<td>
					<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${enterpriseCertificate.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</td>

				<td class="tit">有效期至：</td>
				<td>
					<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${enterpriseCertificate.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</td>
			</tr>
			
			<tr>
				<td class="tit">盖电子章：</td>
				<td colspan="5">
					<input type="button" name="SelectCertificateButton" value="[选择证书]"
                            onclick="SelectCertificateOnClick()">
                 <input type="text" id="SubjectCN" style="width: 300px" readonly="readonly">  
                 <input type="hidden" id="SerialNumber" style="width: 200px">  
				</td>
				
				
			</tr>
			
		</table>
			
		<div class="form-actions">
			<shiro:hasPermission name="enterprise:enterpriseCertificate:edit">
<!-- 			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp; -->
			
			<input id="btnSign" class="btn btn-primary" type="button" value="签章" onclick="sign()"/>
			
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
 
	
</body>
</html>