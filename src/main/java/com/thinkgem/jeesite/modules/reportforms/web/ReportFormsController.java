package com.thinkgem.jeesite.modules.reportforms.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.reportforms.entity.ExportApplyPersonNumInfo;
import com.thinkgem.jeesite.modules.reportforms.entity.ExportApplyPersonTimeInfo;
import com.thinkgem.jeesite.modules.reportforms.entity.ExportInitialRegistrationByProfession;
import com.thinkgem.jeesite.modules.reportforms.entity.InitialRegistrationByProfession;
import com.thinkgem.jeesite.modules.reportforms.entity.PretrialUnitsReportInfo;
import com.thinkgem.jeesite.modules.reportforms.entity.exportEffectiveCounselorData;
import com.thinkgem.jeesite.modules.reportforms.entity.ReportEnterpriseWorkers;
import com.thinkgem.jeesite.modules.reportforms.entity.ExportUnitsReportInfo;
import com.thinkgem.jeesite.modules.reportforms.service.InitialRegistrationByProfessionService;
import com.thinkgem.jeesite.modules.reportforms.service.ReportEnterpriseWorkersService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 导出咨询师信息报表
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="${adminPath}/report")
public class ReportFormsController extends BaseController{
	
	@Autowired
	private ReportEnterpriseWorkersService reportEnterpriseWorkersService;
	
	@Autowired
	private InitialRegistrationByProfessionService initialRegistrationByProfessionService;
	
	/*
	 * 查询出有效咨询师信息，返回到前台对应页面
	 */
	@RequestMapping(value="effectiveCounselorDataStatistics")
	public String effectiveCounselorDataStatistics(ReportEnterpriseWorkers reportEnterpriseWorkers,HttpServletRequest request,HttpServletResponse response,Model model, RedirectAttributes redirectAttributes){
		User user = UserUtils.getUser();
		Page<ReportEnterpriseWorkers> page = null;
		if(user !=null && "1".equals(user.getOffice().getId())){
			reportEnterpriseWorkers.setIsValid("1");
			page= this.reportEnterpriseWorkersService.findEffectiveCounselorInfo(new Page<ReportEnterpriseWorkers>(request, response),reportEnterpriseWorkers);
		}else if(user !=null){
			reportEnterpriseWorkers.setIsValid("1");
			reportEnterpriseWorkers.setOfficeId(user.getOffice().getId());
			page= this.reportEnterpriseWorkersService.findEffectiveCounselorInfo(new Page<ReportEnterpriseWorkers>(request, response),reportEnterpriseWorkers);
		}else{
			addMessage(redirectAttributes, "当前用户为空，查询失败");
		}
		model.addAttribute("page", page);
		return "modules/reportforms/effectiveCounselorDataStatistics";
	}
	
	
	/**
	 * 根据条件导出有效咨询师信息
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	
    @RequestMapping(value = "exportEffectiveCounselorData", method=RequestMethod.POST)
    public String exportEffectiveCounselorDataFile(ReportEnterpriseWorkers reportEnterpriseWorkers,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			User user = UserUtils.getUser();
			List<exportEffectiveCounselorData> list =new ArrayList<exportEffectiveCounselorData>();
			if (user.getOffice() == null) {// 判断office是否为空
				
			} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
				
				if ("1".equals(user.getOffice().getId())) {
					reportEnterpriseWorkers.setIsValid("1");
				} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
					reportEnterpriseWorkers.setIsValid("1");
					reportEnterpriseWorkers.setOfficeId(user.getOffice().getId());
				}
				List<ReportEnterpriseWorkers> workList = reportEnterpriseWorkersService.findEffectiveCounselorInfo(reportEnterpriseWorkers);	
				
				if(workList!=null && workList.size()>0){
					int index = 1;
					for (ReportEnterpriseWorkers work : workList) {
						work.setIndex(index++);
						list.add(new exportEffectiveCounselorData(work));
					}
				}
			}
            String fileName = "有效咨询师数据统计"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		new ExportExcel("有效咨询师数据统计", exportEffectiveCounselorData.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出有效咨询师数据！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/report/effectiveCounselorDataStatistics?repage";
    }
	
//----------------------------------- 有效咨询师数据统计END--------------------------------------------------------------------
    
    /*
	 * 查询出预审单位上报信息，返回到前台对应页面
	 */
	@RequestMapping(value="pretrialUnitsReportInfo")
	public String pretrialUnitsReportInfo(ReportEnterpriseWorkers enterpriseWorkers,HttpServletRequest request,HttpServletResponse response,Model model, RedirectAttributes redirectAttributes){
		User user = UserUtils.getUser();
		Page<ReportEnterpriseWorkers> page = null;
		if(user !=null && "1".equals(user.getOffice().getId())){
			page= this.reportEnterpriseWorkersService.findPretrialUnitsReportInfo(new Page<ReportEnterpriseWorkers>(request, response),enterpriseWorkers);
		}else if(user !=null){
			enterpriseWorkers.setOfficeId(user.getOffice().getId());
			page= this.reportEnterpriseWorkersService.findPretrialUnitsReportInfo(new Page<ReportEnterpriseWorkers>(request, response),enterpriseWorkers);
		}else{
			addMessage(redirectAttributes, "当前用户为空，查询失败");
		}
		model.addAttribute("page", page);
		return "modules/reportforms/pretrialUnitsReportInfo";
	}
	
	/**
	 * 根据条件导出预审单位上报信息
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "exportPretrialUnitsReportInfoFile", method=RequestMethod.POST)
    public String exportPretrialUnitsReportInfoFile(ReportEnterpriseWorkers reportEnterpriseWorkers,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			User user = UserUtils.getUser();
			List<PretrialUnitsReportInfo> list =new ArrayList<PretrialUnitsReportInfo>();
			if (user.getOffice() == null) {// 判断office是否为空
				
			} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
				
				if ("1".equals(user.getOffice().getId())) {
					
				} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
					reportEnterpriseWorkers.setOfficeId(user.getOffice().getId());
				}
				List<ReportEnterpriseWorkers> workList = reportEnterpriseWorkersService.findPretrialUnitsReportInfo(reportEnterpriseWorkers);	
				if(workList!=null && workList.size()>0){
					int index = 1;
					for (ReportEnterpriseWorkers work : workList) {
						work.setIndex(index++);
						list.add(new PretrialUnitsReportInfo(work));
					}
				}
			}
            String fileName = "预审单位上报信息统计"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		new ExportExcel("预审单位上报信息统计", PretrialUnitsReportInfo.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出预审单位上报信息失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/report/pretrialUnitsReportInfo?repage";
    }
    
    

    /*
	 * 查询出预审单位上报信息汇总，返回到前台对应页面
	 */
	@RequestMapping(value="unitsReportInfo")
	public String unitsReportInfo(ReportEnterpriseWorkers reportEnterpriseWorkers,HttpServletRequest request,HttpServletResponse response,Model model, RedirectAttributes redirectAttributes){
		String flag = "";//判断是地方还是中咨
		User user = UserUtils.getUser();
		Page<ReportEnterpriseWorkers> page = null;
		if(user !=null && "1".equals(user.getOffice().getId())){
			flag = "1";
			page= this.reportEnterpriseWorkersService.findUnitsReportInfo(new Page<ReportEnterpriseWorkers>(request, response),reportEnterpriseWorkers);
		}else if(user !=null){
			flag = "0";
			reportEnterpriseWorkers.setOfficeId(user.getOffice().getId());
			page= this.reportEnterpriseWorkersService.findUnitsReportInfo(new Page<ReportEnterpriseWorkers>(request, response),reportEnterpriseWorkers);
		}else{
			addMessage(redirectAttributes, "当前用户为空，查询失败");
		}
		model.addAttribute("page", page);
		model.addAttribute("flag", flag);
		return "modules/reportforms/unitsReportInfo";
	}
    
	

	/**
	 * 根据条件导出预审单位上报信息汇总
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "exportUnitsReportInfo", method=RequestMethod.POST)
    public String exportunitsReportInfoFile(ReportEnterpriseWorkers reportEnterpriseWorkers,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
    	int irTatol = 0;//初始登记总数
    	int cuTatol = 0;//变更单位总数
    	int crTatol = 0;//继续登记总数
    	int csTatol = 0;//变更专业总计
		try {
			User user = UserUtils.getUser();
			List<ExportUnitsReportInfo> list =new ArrayList<ExportUnitsReportInfo>();
			if (user.getOffice() == null) {// 判断office是否为空
				
			} else {// 如果不等于空，则判断是否是中咨协会管理员，如果是中咨协会管理员，则可以查询所有数据
				
				if ("1".equals(user.getOffice().getId())) {
					
				} else {// 如果不是中咨协会管理员，则只能查询本office_id下的所有数据
					reportEnterpriseWorkers.setOfficeId(user.getOffice().getId());
				}
				List<ReportEnterpriseWorkers> workList = this.reportEnterpriseWorkersService.findUnitsReportInfo(reportEnterpriseWorkers);	
				if(workList!=null && workList.size()>0){
					for (ReportEnterpriseWorkers work : workList) {
						if(!"".equals(work.getInitialRegistration())){
							irTatol += Integer.valueOf(work.getInitialRegistration());
						}
						if(!"".equals(work.getChangeSpecialty())){
							csTatol += Integer.valueOf(work.getChangeSpecialty());
						}
						if(!"".equals(work.getChangeUnit())){
							cuTatol += Integer.valueOf(work.getChangeUnit());
						}
						if(!"".equals(work.getContinueRegistration())){
							crTatol += Integer.valueOf(work.getContinueRegistration());
						}
						list.add(new ExportUnitsReportInfo(work));
					}
				}
			}
			ExportUnitsReportInfo unitsReportInfo = new ExportUnitsReportInfo();
			unitsReportInfo.setCompanyName("总计");
			unitsReportInfo.setContinueRegistration(String.valueOf(crTatol));
			unitsReportInfo.setInitialRegistration(String.valueOf(irTatol));
			unitsReportInfo.setChangeUnit(String.valueOf(cuTatol));
			unitsReportInfo.setChangeSpecialty(String.valueOf(csTatol));
			list.add(unitsReportInfo);
            String fileName = "预审单位上报信息汇总"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		new ExportExcel("预审单位上报信息汇总", ExportUnitsReportInfo.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出预审单位上报信息失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/report/unitsReportInfo?repage";
    }
    
//------------------------------------------预审单位上报信息汇总END---------------------------------------------------------
    
    
    /*
	 * 查询初始登记按照专业划分申请情况，返回到前台对应页面
	 */
	@RequestMapping(value="initialRegistrationByProfessionInfo")
	public String initialRegistrationByProfessionInfo(InitialRegistrationByProfession initialRegistrationByProfession,HttpServletRequest request,HttpServletResponse response,Model model, RedirectAttributes redirectAttributes){
		User user = UserUtils.getUser();
		Page<InitialRegistrationByProfession> page = null;
		if(user !=null && "1".equals(user.getOffice().getId())){
			page= this.initialRegistrationByProfessionService.findInitialRegistrationByProfession(new Page<InitialRegistrationByProfession>(request, response),initialRegistrationByProfession);
		}else{
			addMessage(redirectAttributes, "查询失败");
		}
		model.addAttribute("page", page);
		return "modules/reportforms/initialRegistrationByProfessionInfo";
	}
	
	/**
	 * 导出初始登记按照专业划分申请情况
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "exportInitialRegistrationByProfessionInfoFile", method=RequestMethod.POST)
    public String exportInitialRegistrationByProfessionInfoFile(InitialRegistrationByProfession initialRegistrationByProfession,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		// 记录各个专业的总个数
		int tatol =0 ;// 总结
		int farmingAndForestry = 0;                //农业、林业                            
		int waterConservancyAndHydropower =0 ;     //水利水电                             
		int power=0;                             //电力（含火电、水电、核电、新能源）                
		int coal=0;                              //煤炭                               
		int oilAndNaturalGas =0;                  //石油天然气                            
		int highway =0;                           //公路                               
		int railTransit =0;                       //铁路、城市轨道交通                        
		int civilAviation =0;                     //民航                               
		int waterTransport =0;                    //水运（含港口河海工程）                      
		int electroniCommunication =0;            //电子、信息工程(含通信、广电、信息化)              
		int metallurgy =0;                        //冶金（含钢铁、有色）                       
		int chemicalAndMedicine =0;               //石化、化工、医药                         
		int nuclearIndustry =0;                   //核工业                              
		int mechanics =0;                         //机械（含智能制造）                        
		int lightAndTextileIndustry =0;           //轻工、纺织                            
		int buildingMaterial =0;                  //建材                               
		int architecture =0;                      //建筑                               
		int municipalUtilities =0;                //市政公用工程                           
		int ecologicalEnvironmentEngineering =0;  //生态建设和环境工程                        
		int hydrogeologySurveyGeotechnical =0;    //水文地质、工程测量、岩土工程                   
		int speciallty21 =0;                      //speciallty21                     
		int cityPlanning =0;                      //其他（城市规划）                         
		int earthquakeEngineering =0;             //其他（地震工程）                         
		int engineeringTechnologyEconomy =0;      //其他（工程技术经济）                       
		int ancientArchitecturalBuildings =0;     //其他（古建筑）                          
		int oceanographicEngineering =0;          //其他（海洋工程）                         
		int povertyReductionProjects =0;          //其他（减贫工程）                         
		int energyConservation =0;                //其他（节能）                           
		int mineralExploitation =0;               //其他（矿产开发）                         
		int tourismEngineering =0;                //其他（旅游工程）                         
		int meteorologicalEngineering =0;         //其他（气象工程）                         
		int commercialGrain =0;                   //其他（商物粮）                          
		int bioengineering =0;                    //其他（生物工程）                         
		int cableway =0;                          //其他（索道）                           
		int landUse =0;                           //其他（土地利用）                         
		int landArrangement =0;                   //其他（土地整理）                         
		int immigrationProject =0;                //其他（移民工程）                         
		int postalEngineering =0;                 //其他（邮政工程）                         
		int packagingIndustry =0;                 //其他（包装工业）                         

		try {
			User user = UserUtils.getUser();
			List<ExportInitialRegistrationByProfession> list =new ArrayList<ExportInitialRegistrationByProfession>();
			if (user.getOffice() == null) {// 判断office是否为空
				
			} else {// 如果不等于空，则是中咨协会管理员
				List<InitialRegistrationByProfession> professionList = this.initialRegistrationByProfessionService.findInitialRegistrationByProfession(initialRegistrationByProfession);	
				
				if(professionList!=null && professionList.size()>0){
					for (InitialRegistrationByProfession profession : professionList) {
						if(0 != profession.getTatol()){
							tatol += profession.getTatol();
						}
						if(0 != profession.getFarmingAndForestry()){
							farmingAndForestry +=profession.getFarmingAndForestry();
						}
						if(0 != profession.getWaterConservancyAndHydropower()){
							waterConservancyAndHydropower += profession.getWaterConservancyAndHydropower();			
						}
						if(0 != profession.getPower()){
							power +=profession.getPower();
						}
						if(0 != profession.getCoal()){
							coal +=profession.getCoal();
						}
						if(0 != profession.getOilAndNaturalGas()){
							oilAndNaturalGas +=profession.getOilAndNaturalGas();
						}
						if(0 != profession.getHighway()){
							highway +=profession.getHighway();
						}
						if(0 != profession.getRailTransit()){
							railTransit += profession.getRailTransit();
						}
						if(0 != profession.getCivilAviation()){
							civilAviation += profession.getCivilAviation();
						}
						if(0 != profession.getWaterTransport()){
							waterTransport += profession.getWaterTransport();
						}
						if(0 != profession.getElectroniCommunication()){
							electroniCommunication +=profession.getElectroniCommunication();
						}
						if(0 != profession.getMetallurgy()){
							metallurgy +=profession.getMetallurgy();
						}
						if(0 != profession.getChemicalAndMedicine()){
							chemicalAndMedicine +=profession.getChemicalAndMedicine();
						}
						if(0 != profession.getNuclearIndustry()){
							nuclearIndustry += profession.getNuclearIndustry();
						}
						if(0 != profession.getMechanics()){
							mechanics += profession.getMechanics();
						}
						if(0 != profession.getLightAndTextileIndustry()){
							lightAndTextileIndustry += profession.getLightAndTextileIndustry();
						}
						if(0 != profession.getBuildingMaterial()){
							buildingMaterial += profession.getBuildingMaterial();
						}
						if(0 != profession.getArchitecture()){
							architecture += profession.getArchitecture();
						}
						if(0 != profession.getMunicipalUtilities()){
							municipalUtilities += profession.getMunicipalUtilities();
						}
						if(0 != profession.getEcologicalEnvironmentEngineering()){
							ecologicalEnvironmentEngineering += profession.getEcologicalEnvironmentEngineering();
						}
						if(0 != profession.getHydrogeologySurveyGeotechnical()){
							hydrogeologySurveyGeotechnical += profession.getHydrogeologySurveyGeotechnical();
						}
						if(0 != profession.getSpeciallty21()){
							speciallty21 += profession.getSpeciallty21();
						}
						if(0 != profession.getCityPlanning()){
							cityPlanning += profession.getCityPlanning();
						}
						if(0 != profession.getEarthquakeEngineering()){
							earthquakeEngineering +=profession.getEarthquakeEngineering();
						}
						if(0 != profession.getEngineeringTechnologyEconomy()){
							engineeringTechnologyEconomy +=profession.getEngineeringTechnologyEconomy();
						}
						if(0 != profession.getAncientArchitecturalBuildings()){
							ancientArchitecturalBuildings +=profession.getAncientArchitecturalBuildings();
						}
						if(0 != profession.getOceanographicEngineering()){
							oceanographicEngineering += profession.getOceanographicEngineering();
						}
						if(0 != profession.getPovertyReductionProjects()){
							povertyReductionProjects += profession.getPovertyReductionProjects();
						}
						if(0 != profession.getEnergyConservation()){
							energyConservation += profession.getEnergyConservation();
						}
						if(0 != profession.getMineralExploitation()){
							mineralExploitation += profession.getMineralExploitation();
						}
						if(0 != profession.getTourismEngineering()){
							tourismEngineering +=profession.getTourismEngineering();
						}
						if(0 != profession.getMeteorologicalEngineering()){
							meteorologicalEngineering += profession.getMeteorologicalEngineering();
						}
						if(0 != profession.getCommercialGrain()){
							commercialGrain += profession.getCommercialGrain();
						}
						if(0 != profession.getBioengineering()){
							bioengineering += profession.getBioengineering();
						}
						if(0 != profession.getCableway()){
							cableway += profession.getCableway();
						}
						if(0 != profession.getLandUse()){
							landUse += profession.getLandUse();
						}
						if(0 != profession.getLandArrangement()){
							landArrangement +=profession.getLandArrangement();
						}
						if(0 != profession.getImmigrationProject()){
							immigrationProject +=profession.getImmigrationProject();
						}
						if(0 != profession.getPostalEngineering()){
							postalEngineering += profession.getPostalEngineering();			
						}
						if(0 != profession.getPackagingIndustry()){
							packagingIndustry += profession.getPackagingIndustry();
						}
					}
					ExportInitialRegistrationByProfession expertInitialRegistrationByProfession = new ExportInitialRegistrationByProfession();
					expertInitialRegistrationByProfession.setTatol(tatol);
					expertInitialRegistrationByProfession.setIndex(0);
					expertInitialRegistrationByProfession.setOfficeName("总计");
					expertInitialRegistrationByProfession.setAncientArchitecturalBuildings(ancientArchitecturalBuildings);
					expertInitialRegistrationByProfession.setArchitecture(architecture);
					expertInitialRegistrationByProfession.setBioengineering(bioengineering);
					expertInitialRegistrationByProfession.setBuildingMaterial(buildingMaterial);
					expertInitialRegistrationByProfession.setCableway(cableway);
					expertInitialRegistrationByProfession.setChemicalAndMedicine(chemicalAndMedicine);
					expertInitialRegistrationByProfession.setCityPlanning(cityPlanning);
					expertInitialRegistrationByProfession.setCivilAviation(civilAviation);
					expertInitialRegistrationByProfession.setCoal(coal);
					expertInitialRegistrationByProfession.setCommercialGrain(commercialGrain);
					expertInitialRegistrationByProfession.setEarthquakeEngineering(earthquakeEngineering);
					expertInitialRegistrationByProfession.setElectroniCommunication(electroniCommunication);
					expertInitialRegistrationByProfession.setEcologicalEnvironmentEngineering(ecologicalEnvironmentEngineering);
					expertInitialRegistrationByProfession.setEnergyConservation(energyConservation);
					expertInitialRegistrationByProfession.setEngineeringTechnologyEconomy(engineeringTechnologyEconomy);
					expertInitialRegistrationByProfession.setFarmingAndForestry(farmingAndForestry);
					expertInitialRegistrationByProfession.setHighway(highway);
					expertInitialRegistrationByProfession.setHydrogeologySurveyGeotechnical(hydrogeologySurveyGeotechnical);
					expertInitialRegistrationByProfession.setImmigrationProject(immigrationProject);
					expertInitialRegistrationByProfession.setLandArrangement(landArrangement);
					expertInitialRegistrationByProfession.setLandUse(landUse);
					expertInitialRegistrationByProfession.setLightAndTextileIndustry(lightAndTextileIndustry);
					expertInitialRegistrationByProfession.setMechanics(mechanics);
					expertInitialRegistrationByProfession.setMetallurgy(metallurgy);
					expertInitialRegistrationByProfession.setMeteorologicalEngineering(meteorologicalEngineering);
					expertInitialRegistrationByProfession.setMineralExploitation(mineralExploitation);
					expertInitialRegistrationByProfession.setMunicipalUtilities(municipalUtilities);
					expertInitialRegistrationByProfession.setNuclearIndustry(nuclearIndustry);
					expertInitialRegistrationByProfession.setOceanographicEngineering(oceanographicEngineering);
					expertInitialRegistrationByProfession.setOilAndNaturalGas(oilAndNaturalGas);
					expertInitialRegistrationByProfession.setPackagingIndustry(packagingIndustry);
					expertInitialRegistrationByProfession.setPostalEngineering(postalEngineering);
					expertInitialRegistrationByProfession.setPovertyReductionProjects(povertyReductionProjects);
					expertInitialRegistrationByProfession.setPower(power);
					expertInitialRegistrationByProfession.setRailTransit(railTransit);
					expertInitialRegistrationByProfession.setSpeciallty21(speciallty21);
					expertInitialRegistrationByProfession.setTourismEngineering(tourismEngineering);
					expertInitialRegistrationByProfession.setWaterConservancyAndHydropower(waterConservancyAndHydropower);
					expertInitialRegistrationByProfession.setWaterTransport(waterTransport);					
					list.add(expertInitialRegistrationByProfession);
					int index = 1;
					for (InitialRegistrationByProfession profession : professionList) {
						profession.setIndex(index++);
						list.add(new ExportInitialRegistrationByProfession(profession));
					}
				}
			}
            String fileName = "初始登记按专业划分申请情况"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		new ExportExcel("初始登记按专业划分申请情况", ExportInitialRegistrationByProfession.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出初始登记按专业划分申请情况失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/report/initialRegistrationByProfessionInfo?repage";
    }
    
//------------------------------------------------专家评审意见汇总END--------------------------------------------------    
    
    /*
	 * 查询登记申请汇总表-申请人数-材料，返回到前台对应页面
	 */
	@RequestMapping(value="applyPersonNum")
	public String applyPersonNum(ReportEnterpriseWorkers reportEnterpriseWorkers,HttpServletRequest request,HttpServletResponse response,Model model, RedirectAttributes redirectAttributes){
		User user = UserUtils.getUser();
		Page<ReportEnterpriseWorkers> page = null;
		if(user !=null && "1".equals(user.getOffice().getId())){
			page= this.reportEnterpriseWorkersService.findApplyPersonNum(new Page<ReportEnterpriseWorkers>(request, response),reportEnterpriseWorkers);
		}else{
			addMessage(redirectAttributes, "查询失败，获取用户信息错误");
		}
		model.addAttribute("page", page);
		return "modules/reportforms/applyPersonNum";
	}
    
	/**
	 * 导出登记申请汇总表-申请人次
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "exportApplyPersonNumInfo", method=RequestMethod.POST)
    public String exportApplyPersonNumInfoFile(ReportEnterpriseWorkers reportEnterpriseWorkers,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
    	int irTatol = 0;//初始登记总数
    	int cuTatol = 0;//变更单位总数
    	int crTatol = 0;//继续登记总数
    	int csTatol = 0;//变更专业总数
    	int corTatol = 0;//注销登记总数
    	long tatol = 0L;//总计
    	int subtotal = 0 ;//小计
		try {
			User user = UserUtils.getUser();
			List<ExportApplyPersonNumInfo> list =new ArrayList<ExportApplyPersonNumInfo>();
			if (user.getOffice() == null) {// 判断office是否为空
				
			} else {
				List<ReportEnterpriseWorkers> workList = this.reportEnterpriseWorkersService.findApplyPersonNum(reportEnterpriseWorkers);	
				if(workList!=null && workList.size()>0){
					
					for (ReportEnterpriseWorkers work : workList) {
						if(!"".equals(work.getTatol())){
							tatol += work.getTatol();
						}
						if(!"".equals(work.getInitialRegistration())){
							irTatol += Integer.valueOf(work.getInitialRegistration());
						}
						if(!"".equals(work.getSubtotal())){
							subtotal += work.getSubtotal();
						}
						if(!"".equals(work.getChangeSpecialty())){
							csTatol += Integer.valueOf(work.getChangeSpecialty());
						}
						if(!"".equals(work.getChangeUnit())){
							cuTatol += Integer.valueOf(work.getChangeUnit());
						}
						if(!"".equals(work.getContinueRegistration())){
							crTatol += Integer.valueOf(work.getContinueRegistration());
						}
						if(!"".equals(work.getCancellationOfRegistration())){
							corTatol += Integer.valueOf(work.getCancellationOfRegistration());
						}
					}
					
					ExportApplyPersonNumInfo applyPersonNumInfo = new ExportApplyPersonNumInfo();
					applyPersonNumInfo.setOfficeName("总计");
					applyPersonNumInfo.setTatol(tatol);
					applyPersonNumInfo.setInitialRegistration(String.valueOf(irTatol));
					applyPersonNumInfo.setSubtotal(subtotal);
					applyPersonNumInfo.setChangeSpecialty(String.valueOf(csTatol));
					applyPersonNumInfo.setChangeUnit(String.valueOf(cuTatol));
					applyPersonNumInfo.setContinueRegistration(String.valueOf(crTatol));
					applyPersonNumInfo.setCancellationOfRegistration(String.valueOf(corTatol));
					list.add(applyPersonNumInfo);
					
					int index = 1;
					for (ReportEnterpriseWorkers reportWorkers : workList) {
						reportWorkers.setIndex(index++);
						list.add(new ExportApplyPersonNumInfo(reportWorkers));
					}
				}
			}
            String fileName = "登记申请汇总-申请人数-材料"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		new ExportExcel("登记申请汇总-申请人数-材料", ExportApplyPersonNumInfo.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出登记申请汇总-申请人数-材料汇总信息失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/report/applyPersonNum?repage";
    }
    
    /*
	 * 查询登记申请汇总表-申请人次，返回到前台对应页面
	 */
	@RequestMapping(value="applyPersonTime")
	public String applyPersonTime(ReportEnterpriseWorkers reportEnterpriseWorkers,HttpServletRequest request,HttpServletResponse response,Model model, RedirectAttributes redirectAttributes){
		User user = UserUtils.getUser();
		Page<ReportEnterpriseWorkers> page = null;
		if(user !=null && "1".equals(user.getOffice().getId())){
			page= this.reportEnterpriseWorkersService.findApplyPersonTime(new Page<ReportEnterpriseWorkers>(request, response),reportEnterpriseWorkers);
		}else{
			addMessage(redirectAttributes, "查询失败，获取用户信息错误");
		}
		model.addAttribute("page", page);
		return "modules/reportforms/applyPersonTime";
	}
	
	/**
	 * 导出登记申请汇总表-申请人次
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "exportApplyPersonTimeInfo", method=RequestMethod.POST)
    public String eexportApplyPersonTimeInfoFile(ReportEnterpriseWorkers reportEnterpriseWorkers,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
    	int irTatol = 0;//初始登记总数
    	int cuTatol = 0;//变更单位总数
    	int crTatol = 0;//继续登记总数
    	int csTatol = 0;//变更专业总数
    	int corTatol = 0;//注销登记总数
    	long tatol = 0L;//总计
    	int aleryReportTatol = 0 ;//已上报终审
		try {
			User user = UserUtils.getUser();
			List<ExportApplyPersonTimeInfo> list =new ArrayList<ExportApplyPersonTimeInfo>();
			if (user.getOffice() == null) {// 判断office是否为空
				
			} else {
				List<ReportEnterpriseWorkers> workList = this.reportEnterpriseWorkersService.findApplyPersonTime(reportEnterpriseWorkers);	
				if(workList!=null && workList.size()>0){
					
					for (ReportEnterpriseWorkers work : workList) {
						if(!"".equals(work.getTatol())){
							tatol += work.getTatol();
						}
						if(!"".equals(work.getInitialRegistration())){
							irTatol += Integer.valueOf(work.getInitialRegistration());
						}
						if(!"".equals(work.getAleryReportTatol())){
							aleryReportTatol += work.getAleryReportTatol();
						}
						if(!"".equals(work.getChangeSpecialty())){
							csTatol += Integer.valueOf(work.getChangeSpecialty());
						}
						if(!"".equals(work.getChangeUnit())){
							cuTatol += Integer.valueOf(work.getChangeUnit());
						}
						if(!"".equals(work.getContinueRegistration())){
							crTatol += Integer.valueOf(work.getContinueRegistration());
						}
						if(!"".equals(work.getCancellationOfRegistration())){
							corTatol += Integer.valueOf(work.getCancellationOfRegistration());
						}
					}
					
					ExportApplyPersonTimeInfo applyPersonTimeInfo = new ExportApplyPersonTimeInfo();
					applyPersonTimeInfo.setOfficeName("总计");
					applyPersonTimeInfo.setTatol(tatol);
					applyPersonTimeInfo.setInitialRegistration(String.valueOf(irTatol));
					applyPersonTimeInfo.setAleryReportTatol(aleryReportTatol);
					applyPersonTimeInfo.setChangeSpecialty(String.valueOf(csTatol));
					applyPersonTimeInfo.setChangeUnit(String.valueOf(cuTatol));
					applyPersonTimeInfo.setContinueRegistration(String.valueOf(crTatol));
					applyPersonTimeInfo.setCancellationOfRegistration(String.valueOf(corTatol));
					list.add(applyPersonTimeInfo);
					
					int index = 1;
					for (ReportEnterpriseWorkers reportWorkers : workList) {
						reportWorkers.setIndex(index++);
						list.add(new ExportApplyPersonTimeInfo(reportWorkers));
					}
				}
			}
            String fileName = "登记申请汇总-申请人次汇总"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		new ExportExcel("登记申请汇总-申请人次汇总", ExportApplyPersonTimeInfo.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出登记申请汇总-申请人次汇总信息失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/report/applyPersonTime?repage";
    }
    
//------------------------------------------登记申请汇总表END--------------------------------------------------------------------
    
}
