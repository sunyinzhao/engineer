<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title>咨询师审查管理</title>
	<meta name="decorator" content="blank"/>
	
	<link href="${pageContext.request.contextPath}/static/viewer/viewer.min.css" type="text/css" rel="stylesheet" />
	<script src="${pageContext.request.contextPath}/static/viewer/viewer-jquery.min.js" type="text/javascript"></script>
	
	<script type="text/javascript">
        $(function() {
            viewPic();
        });

        function viewPic(){
            $('#dowebok').viewer({
                url: 'data-original',
            });
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
        .toLarge{
            font-weight: bold
        }


	</style>
</head>
<body>

                            <form:form id="inputForm" modelAttribute="ProjectInvestment" action="" method="post" class="form-horizontal">
                            <table class="table-form">
                                <tr align="center">
                                    <td width="15%" class="toLarge" >
                                        服务范围
                                    </td>
                                    <td width="10%" class="toLarge">
                                        项目名称及服务范围
                                    </td>
                                    <td width="10%" class="toLarge">
                                        项目编制或评审单位
                                    </td>
                                    <td width="10%" class="toLarge">
                                        项目完成时间
                                    </td>
                                    <td width="10%" class="toLarge">
                                        建设规模/投资额
                                    </td>
                                    <td width="10%" class="toLarge">
                                        支持申报的专业
                                    </td>
                                    <td width="15%" class="toLarge">
                                        工作内容
                                    </td>
                                    <td width="15%" class="toLarge">
                                        本人完成的具体内容
                                    </td>
                                </tr>

                                <tr>
                                    <td align="center" height="40px">
                                            ${fns:getDictLabel(project.accountant,"project_accountant" ,"" )}
                                    </td>

                                    <td align="center">
                                            ${project.projectName}
                                    </td>

                                    <td align="center">
                                            ${project.compliancerName}
                                    </td>

                                    <td align="center">
                                        <fmt:formatDate value="${project.commitTime}" pattern="yyyy-MM-dd"/>
                                    </td>

                                    <td align="center">
                                            ${project.jsgm}
                                    </td>

                                    <td align="center">
                                            ${fns:getDictLabel(project.zcSpecialty,'specialty_type' ,'' )}
                                    </td>

                                    <td align="center">
                                            ${fns:getDictLabel(project.brzy,"signature_duty" ,"" )}
                                    </td>

                                    <td align="center" >
                                            ${project.nr}
                                    </td>
                                </tr>
                            </table>
                                    </form:form>
                            <br/>
                            <br/>
                            <br/>

                            <c:if test="${empty imageList}">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <tr align="center"><span style="color: blue;font-size: 20px">此条业绩未上传图片</span></tr>
                            </c:if>
    <c:if test="${!empty imageList}">
    <div class="content2" id="imageList">
        <ul id="dowebok">
            <c:forEach items="${imageList}" var="image">
                <img  src="${ctx}/uploadImage/id/${image.id}" class="fileImage" >
            </c:forEach>
        </ul>
    </div>
    </c:if>


</div>

</div>
                            <br/>
                            <br/>
                            <br/>
                            <div align="center">
                            <input align="center" type="button" value="关 闭" class="btn" onclick="window.close()">
                            </div>
</body>
</html>