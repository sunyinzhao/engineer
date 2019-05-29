<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>初始登记按照专业划分申请情况</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.inputType input{
			width:150px;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#searchForm").validate({
				rules:{
				},
				messages:{
				},
				submitHandler: function(form){
					loading('正在为您处理数据，请稍等...');
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
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function checkBatchNo(batchNo){
        	var reg = /^[12]\d{3}(0[1-9]|1[0-2])$/;
        	var r = batchNo.match(reg);
            if(r == null){
                alert('请输入您本次接收数据的批次号，格式为yyyyMM（4位年2位月）');
                $("#batchNo").val("");
                return;
            }
        }
        
        //导出数据
        function exportData(){
        	if (window.confirm("是否确定导出当前数据。")){
	       		 $("#searchForm").attr("action","${ctx}/report/exportInitialRegistrationByProfessionInfoFile");
      			 $("#searchForm").submit();
       	 	}
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/report/unitsReportInfo">初始登记按照专业划分申请情况</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="initialRegistrationByProfession" action="${ctx}/report/initialRegistrationByProfessionInfo" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<li><label>批次号：</label>
				<form:input path="batchNo" htmlEscape="false" maxlength="20" class="input-small" onchange="checkBatchNo(this.value)"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnSubmit" class="btn btn-primary" type="button" value="导出" onclick="exportData()"/>
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>初审机构</th>
				<th>已报合计</th>
				<th>农业、林业</th>
				<th>水利水电</th>
				<th>电力</th>
				<th>煤炭</th>
				<th>石油天然气</th>
				<th>公路</th>
				<th>铁路、城市轨道交通</th>
				<th>民航</th>
				<th>水运</th>
				<th>电子、信息工程</th>
				<th>冶金</th>
				<th>石化、化工、医药</th>
				<th>核工业</th>
				<th>机械</th>
				<th>轻工、纺织</th>
				<th>建材</th>
				<th>建筑</th>
				<th>市政公用工程</th>
				<th>生态建设和环境工程</th>
				<th>水文地质、工程测量、岩土工程</th>
				<th>城市规划</th>
				<th>地震工程</th>
				<th>工程技术经济</th>
				<th>古建筑</th>
				<th>海洋工程</th>
				<th>减贫工程</th>
				<th>节能</th>
				<th>矿产开发</th>
				<th>旅游工程</th>
				<th>旅游工程</th>
				<th>气象工程</th>
				<th>商物粮</th>
				<th>生物工程</th>
				<th>索道</th>
				<th>土地利用</th>
				<th>土地整理</th>
				<th>移民工程</th>
				<th>邮政工程</th>
				<th>包装工业</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="initialRegistrationByProfession" varStatus="index">
			<tr>
				<td>${index.index+1 }</td>
				<td>${initialRegistrationByProfession.officeName}</td>
				<th>${initialRegistrationByProfession.tatol}</th>                                       
				<th>${initialRegistrationByProfession.farmingAndForestry}</th>                          
				<th>${initialRegistrationByProfession.waterConservancyAndHydropower}</th>                                                    
				<th>${initialRegistrationByProfession.power}</th>                                                         
				<th>${initialRegistrationByProfession.coal}</th>                                             
				<th>${initialRegistrationByProfession.oilAndNaturalGas}</th>                                                
				<th>${initialRegistrationByProfession.highway}</th>                                                  
				<th>${initialRegistrationByProfession.railTransit}</th>                                  
				<th>${initialRegistrationByProfession.civilAviation}</th>                                               
				<th>${initialRegistrationByProfession.waterTransport}</th>                                       
				<th>${initialRegistrationByProfession.electroniCommunication}</th>                                         
				<th>${initialRegistrationByProfession.metallurgy}</th>                             
				<th>${initialRegistrationByProfession.chemicalAndMedicine}</th>             
				<th>${initialRegistrationByProfession.nuclearIndustry }</th>                       
				<th>${initialRegistrationByProfession.mechanics}</th>                         
				<th>${initialRegistrationByProfession.lightAndTextileIndustry}</th>                   
				<th>${initialRegistrationByProfession.buildingMaterial}</th>                         
				<th>${initialRegistrationByProfession.architecture  }</th>                         
				<th>${initialRegistrationByProfession.municipalUtilities }</th>                 
				<th>${initialRegistrationByProfession.ecologicalEnvironmentEngineering}</th>           
				<th>${initialRegistrationByProfession.hydrogeologySurveyGeotechnical  }</th> 
				<th>${initialRegistrationByProfession.speciallty21 }</th>                     
				<th>${initialRegistrationByProfession.cityPlanning}</th>                     
				<th>${initialRegistrationByProfession.earthquakeEngineering}</th>                 
				<th>${initialRegistrationByProfession.engineeringTechnologyEconomy}</th>                       
				<th>${initialRegistrationByProfession.ancientArchitecturalBuildings}</th>                     
				<th>${initialRegistrationByProfession.oceanographicEngineering }</th>                     
				<th>${initialRegistrationByProfession.povertyReductionProjects }</th>                         
				<th>${initialRegistrationByProfession.energyConservation}</th>                     
				<th>${initialRegistrationByProfession.mineralExploitation}</th>                     
				<th>${initialRegistrationByProfession.tourismEngineering}</th>                     
				<th>${initialRegistrationByProfession.meteorologicalEngineering}</th>                     
				<th>${initialRegistrationByProfession.commercialGrain}</th>                       
				<th>${initialRegistrationByProfession.bioengineering}</th>                     
				<th>${initialRegistrationByProfession.cableway}</th>                         
				<th>${initialRegistrationByProfession.landUse }</th>                     
				<th>${initialRegistrationByProfession.landArrangement}</th>                     
				<th>${initialRegistrationByProfession.immigrationProject}</th>                     
				<th>${initialRegistrationByProfession.postalEngineering}</th>                     
				<th>${initialRegistrationByProfession.packagingIndustry}</th>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>