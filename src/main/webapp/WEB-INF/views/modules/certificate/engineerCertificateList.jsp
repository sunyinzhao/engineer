<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>咨询工程师证书管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        var CryptoAgent = "";

		$(document).ready(function() {
            $("#searchForm").validate({
                submitHandler: function(form){

                    var name = $("#officeIdName").val();
                    $("#officeName").val(name);
                    // loading('正在提交，请稍等...');
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
          //  SelectCertificateOnClick();

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


        function sign( engineerCertificateId ,serialNumber){
            var signCode = "";
            var url = "${ctx}/certificate/engineerCertificate/getPdfHashCode?engineerCertificateId="+engineerCertificateId+"&serialNumber="+serialNumber;
            $.ajax({
                type: 'POST'
                ,url: url
                ,dataType: 'text'
                ,async:false
                ,success: function(data) {
                    var parsedJson = jQuery.parseJSON(data);
                    var pdfHash=parsedJson.pdfHash;
                    var hashId =parsedJson.hashId;
                    var signCode= encodeURIComponent(SignOnClick1(pdfHash));
                    var ur2 = "${ctx}/certificate/engineerCertificate/signaturePdf?signCode="+signCode+"&hashId="+hashId+"&engineerCertificateId="+engineerCertificateId+"&serialNumber="+serialNumber;
                    $.ajax({
                        type: 'POST'
                        ,url: ur2
                        ,dataType: 'text'
                        ,async:false
                        ,success: function(data) {
                            if(data=='Y'){
                                return true;
                            }
                        },error: function(error){
                            return false;
                        }
                    });
                },error: function(error){
                    alert("error");
                }
            });
        }

    function batchCreateWorkerPdf(){
        var count= $("#count").val();
        if( count=="" ||  !isNaN(parseInt(count))){

            var serialNumber = $("#SerialNumber").val();
            if(serialNumber ==""){
                alert("请先选择电子证书");
                return;
            }
            //$.jBox.tip('正在生成证书，请稍等...','loading');
            var officeId = $("#officeIdId").val();
            var practisingCompany = $("#practisingCompany").val();
            var name = $("#name").val();
            var certificateNum = $("#certificateNum").val();
            var registerCertificateNum = $("#registerCertificateNum").val();

            var url = "${ctx}/certificate/engineerCertificate/getCertificateList?count="+count
                +"&serialNumber="+serialNumber+"&officeId="+officeId
                +"&practisingCompany="+practisingCompany+"&name="+name
                +"&certificateNum="+certificateNum+"&registerCertificateNum="+registerCertificateNum;
            $.ajax({
                type: 'POST'
                ,url: url
                ,dataType: 'json'
                ,async:false
                ,success: function(data) {
                    alert(data.length)
                    for(var i = 0; i < data.length; i++){
                        sign(data[i].id,serialNumber);
                        console.log("已经完成数："+(i+1));
                    }
                    alert("签章完毕！");
                   // $.jBox.closeTip();
                },error: function(error){
                   // $.jBox.closeTip();
                        alert("error");
                }
            });
        }else{
            alert("请输入正确的数值");
        }

    }



        function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

        function download(type){
            location.href='${ctx}/certificate/engineerCertificate/downloadFile?type='+type;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/certificate/engineerCertificate/">咨询工程师证书列表</a></li>

	</ul>
	<form:form id="searchForm" modelAttribute="engineerCertificate" action="${ctx}/certificate/engineerCertificate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="officeName" name="officeName" type="hidden" value="${engineerCertificate.officeName}"/>

		<ul class="ul-form">
			<li><label>归属地：</label><sys:treeselect id="officeId" name="officeId" value="${engineerCertificate.officeId}"
												   labelName="company.name" labelValue="${engineerCertificate.officeName}"
												   title="公司" url="/sys/office/treeData?type=1" cssClass="input-small"
												   allowClear="true"/>
			</li>
			<div class="cssType">
				<li><label>执业单位：</label>
					<form:input path="practisingCompany" htmlEscape="false" maxlength="20" class="input-small"/>
				</li>
			</div>
			<li><label>咨询师姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="certificateNum" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
			<li><label style="width: 140px">执业登记证书编号：</label>
				<form:input path="registerCertificateNum" htmlEscape="false" maxlength="20" class="input-small"/>
			</li>
			<li><label>是否已签章：</label>

				<form:select path="certificateOriginal" class="input-small">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>


			<li class="clearfix"></li>
		</ul>

		<ul class="ul-form">
            <li><label style="width: 140px;">批量盖章的条数：</label>
                <input type="text"id="count" name="count"  placeholder="默认20,最多500" class="input-small " />
            </li>

			<li><label style="width: 60px;">电子章：</label>
			<input type="button" name="SelectCertificateButton" value="[选择证书]"
				   onclick="SelectCertificateOnClick()">

			<input type="text" id="SubjectCN" class="input-small" readonly="readonly">
			<input type="hidden" id="SerialNumber" style="width: 200px">



            <li  class="btns">
                <input class="btn btn-primary" type="button" value="批量盖章" onclick="batchCreateWorkerPdf()">
            </li>

			<li class="btns"><input  class="btn btn-primary" type="button" onclick="download('1');" value="浏览器插件"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" onclick="download('2');" value="证书工具"/></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>性别</th>
				<th>执业单位</th>
				<th>主专业</th>
				<th>辅专业</th>
				<th>批准日期</th>
				<shiro:hasPermission name="certificate:engineerCertificate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="engineerCertificate">
			<tr>
				<td><a href="${ctx}/certificate/engineerCertificate/form?id=${engineerCertificate.id}">
					${engineerCertificate.name}
				</a></td>
				<td>
					${engineerCertificate.sex}
				</td>
				<td>
					${engineerCertificate.practisingCompany}
				</td>
				<td>
					${engineerCertificate.specialtyMain}
				</td>
				<td>
					${engineerCertificate.specialtyAuxiliary}
				</td>
				<td>
					<fmt:formatDate value="${engineerCertificate.startDate}" pattern="yyyy-MM-dd"/>
				</td>

				<shiro:hasPermission name="certificate:engineerCertificate:edit"><td>
    				<a href="${ctx}/certificate/engineerCertificate/form?id=${engineerCertificate.id}">签章</a>
					<c:if test="${engineerCertificate.certificateOriginal !=null && engineerCertificate.certificateOriginal ne '' }">
						<a target="_blank" href="${ctx}/signaturePDFView/viewEngineerSignature?id=${engineerCertificate.workerId}">签章文件</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>