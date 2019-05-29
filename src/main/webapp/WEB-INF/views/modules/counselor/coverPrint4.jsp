<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>咨询工程师(投资)登记申请表-继续登记(封面)</title>
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
		<li class="active"><a href="#">咨询工程师(投资)登记申请表-继续登记(封面)</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="declareRecord" action="${ctx}/declare/declareRecord/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<legend>继续登记</legend>
		<center>
			<%--A4纸张大小：595*842--%>
			<div style="width:595pt; height:842pt; border: 1px solid #999;">
				<div id="printDiv" style="width: 595pt;">
					<center>
						<div style="width: 595pt; height: 700pt; /*border:1px solid black;*/"  >
							<table class="print-table-form" style="width: 400pt; margin-top: 20pt;  table-layout:fixed;">
								<tr style="height: 50pt; margin-top: 20pt; font-size: 15pt ; font-family:'SimHei'; color: black;">
									<td></td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;地区：${enterpriseWorkers.officeName}</td>
								</tr>
								<tr style="height: 200pt;font-family:'FangSong'; color: black;">
									<td style="font-size: 25pt ;line-height: 40pt; text-align: center; " colspan="2"  >
											中 华 人 民 共 和 国<br>咨询工程师（投资）登记申请表<br>（继续 登 记 ）
									</td>
								</tr>
								<tr>
									<td style="" colspan="2">
										<br>
										<br>
										<br>
										<br>
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:36pt; font-family:'FangSong'; color: black;">
											申请人姓名：${enterpriseWorkers.name }
										</p>
									</td>
								</tr>
								<tr>
									<td style="" colspan="2">
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:36pt; font-family:'FangSong'; color: black;">
											执业单位（盖章）：${enterpriseWorkers.companyName }
										</p>
									</td>
								</tr>
								<tr>
									<td style="" colspan="2">
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:36pt; font-family:'FangSong'; color: black;">
											通讯地址：${enterpriseWorkers.address }
										</p>
									</td>

								</tr>
								<tr>
									<td style="" colspan="2">
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:36pt; font-family:'FangSong'; color: black;">
											邮编：
										</p>
									</td>
								</tr>
								<tr>
									<td style="">
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:36pt; font-family:'FangSong'; color: black;">
											联系人：${enterpriseBase.contactsZy }
										</p>
									</td>
									<td style="">
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:0pt; font-family:'FangSong'; color: black;">
											联系电话：${enterpriseBase.contactsZyPhone }
										</p>
									</td>
								</tr>
								<tr>
									<td style="" colspan="2">
										<p style="line-height: 30pt; font-size: 15pt ;text-indent:36pt; font-family:'FangSong'; color: black;">
											申请日期:&nbsp;&nbsp;<fmt:formatDate value="${personRecord.declareDate}" type="date" pattern="yyyy年MM月dd日"/>
										</p>
									</td>
								</tr>
								<tr style="height: 250pt;font-family:'FangSong'; color: black;">
									<td style="font-size: 15pt ;line-height: 40pt; text-align: center; " colspan="2"  >
											中 国 工 程 咨 询 协 会 
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