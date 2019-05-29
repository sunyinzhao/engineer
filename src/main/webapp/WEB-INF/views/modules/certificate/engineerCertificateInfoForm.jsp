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
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/certificate/engineerCertificate/engineerCertificateInfo">咨询工程师证书</a></li>
		<li class="active"><a href="${ctx}/certificate/engineerCertificate/infoForm?id=${engineerCertificate.id}">咨询工程师证书查看</a></li>
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
    </table>

		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>