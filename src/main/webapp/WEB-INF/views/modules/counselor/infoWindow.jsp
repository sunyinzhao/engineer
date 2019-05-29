<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>登记证书</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/jquery-print/jQuery.print.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		
        function jqp(){
            jQuery('#printDiv').print(
                {
                   //Use Global styles
                   globalStyles : true
                   ,mediaPrint : true
					//stylesheet : "http://fonts.googleapis.com/css?family=Inconsolata",
                   ,iframe : true
                   ,noPrintSelector : ".avoid-this"
                   ,prepend : "Hello World!!!<br/>"
                   ,append : "<br/>Buh Bye!"
                   ,deferred: $.Deferred().done(function() { console.log('Printing done', arguments); })
                }
            );
        }
	</script>

    <script type="text/javascript">

        var HKEY_Root, HKEY_Path, HKEY_Key;

        HKEY_Root = "HKEY_CURRENT_USER";

        HKEY_Path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";

        function jqprintDiv() {
            //打印初始化
            var isIE = /msie/.test(navigator.userAgent.toLowerCase()); //jquery1.9之后采用
           // var isIE = $.browser.msie;//jquery1.9之前采用
			alert(isIE);
            if (isIE) {
                //IE浏览器执行
              	//printitIE('PrintArea');
                PageSetup_Null();
            } else {
                //其他浏览器执行通用打印
                //$("#PrintArea").jqprint();
                jqp();
            }
        }

        function printitIE(MyDiv) {

            PageSetup_Null();

            //提示窗口
            if (confirm('确定打印吗？')) {
                var newstr = document.getElementById(MyDiv).innerHTML;
                document.body.innerHTML = "<div style='position:absolute;left:20px;top:20px;'>" + newstr + "</div>";
                window.print();
                return false;
            }
        }

        function PageSetup_Null() {
            try {
                alert(2);
                var Wsh = new ActiveXObject("WScript.Shell");
                alert(3);
                HKEY_Key = "header";
                alert(4);
                Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
                alert(5);
                HKEY_Key = "footer";
                alert(6);
                Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
                alert(7);
                jqp();
            }
            catch (e) { alert(e);}
        }
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/counselor/info/infoWindow">咨询工程师(投资)登记证书</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="declareRecord" action="" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<center>
			<%--A4纸张大小：595*842--%>
			<div style="width:595pt; height:842pt; border: 1px solid #999;">
				<div id="printDiv" style="width: 595pt;height:842pt; background-image:url(../../../static/images/background.jpg);background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;">
					<center>
						<div style="width: 595pt; height: 700pt; /*border:1px solid black;*/"  >
							<table class="print-table-form" style="width: 580pt; margin-top: 2pt;  table-layout:fixed;">
								<tr style="algin:center;height: 200pt;font-family:'FangSong'; color: black;">
									<td style="font-size: 25pt ;line-height: 40pt; text-align: center; " colspan="3"  >
											中 华 人 民 共 和 国<br/>咨询工程师（投资）登记证书
									</td>
								</tr>
								<tr>
									<td style="" colspan="2">
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:36pt; font-family:'FangSong'; color: black;">
											姓名：${enterpriseWorkers.name }
										</p>
									</td>
								</tr>
								<tr>
									<td style="" colspan="2">
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:36pt; font-family:'FangSong'; color: black;">
											性别：${fns:getDictLabel(enterpriseWorkers.sex ,"sex" ,"" )}
										</p>
									</td>

									<!--放置图片-->
									<td rowspan="4">
										<img  src="${ctx}/uploadImage/id2/${enterpriseWorkers.id}">
									</td>
								</tr>
								<tr>
									<td style="" colspan="2">
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:36pt; font-family:'FangSong'; color: black;">

											身份证号：<c:if test="${enterpriseWorkers.certificatesType=='1'}">
										${enterpriseWorkers.certificatesNum}
										</c:if>
										</p>
									</td>
								</tr>
								<tr>
									<td style="" colspan="2">
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:36pt; font-family:'FangSong'; color: black;">
											证书编号：${enterpriseWorkers.registerCertificateNum}
										</p>
									</td>
								</tr>

								<tr>
									<td style="" colspan="2">
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:36pt; font-family:'FangSong'; color: black;">
											主专业：${fns:getDictLabel(enterpriseWorkers.registerMainSpecialty,'specialty_type','') }
										</p>
									</td>

								</tr>

								<tr>
									<td style="" colspan="2">
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:36pt; font-family:'FangSong'; color: black;">
											辅专业：${fns:getDictLabel(enterpriseWorkers.registerAuxiliarySpecialty,'specialty_type','') }
										</p>
									</td>
								</tr>
								<tr>
									<td style="" colspan="2">
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:36pt; font-family:'FangSong'; color: black;">
											执业单位:${enterpriseWorkers.companyName}
										</p>
									</td>
								</tr>
								<tr>
									<td style="" colspan="2">
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:36pt; font-family:'FangSong'; color: black;">
											有效期至:
										</p>
									</td>
								</tr>
								<tr>
									<td style="font-size: 10pt ;line-height: 40pt; text-align: center; " colspan="2"  >
										本电子证书是中华人民共和国咨询工程师(投资)的执业凭证.<br/>
										准许持证人在规定的执业范围内和登记有效期内执业.<br/>
										通过扫描右上角的二维码可查询证书具体信息.
									</td>
								</tr>

								<tr align="right">
									<td style="font-size: 15pt ;line-height: 30pt; align:right;" colspan="2"  >
										<br/><br/><br/>
											登记机构(章)<br/>
											批准日期:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日
									</td>
								</tr>
							</table>
						</div>
					</center>
				</div>
			</div>
		</center>
		<div class="form-actions">
			<input id="jqprint" class="btn" type="button" value="打 印" onclick="jqp()"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>