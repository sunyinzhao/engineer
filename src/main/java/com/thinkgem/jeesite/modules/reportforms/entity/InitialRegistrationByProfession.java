package com.thinkgem.jeesite.modules.reportforms.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 初始登记按专业划分
 * @author Administrator
 *
 */
public class InitialRegistrationByProfession extends DataEntity<InitialRegistrationByProfession>{
	

	public InitialRegistrationByProfession() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InitialRegistrationByProfession(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 对象标识
	 */
	private static final long serialVersionUID = 1L;
	
	private String officeName;//预审单位
	private int tatol;//总结
	private int index;//序号
	
	private int farmingAndForestry               ;  //农业、林业
	private int waterConservancyAndHydropower    ;  //水利水电
	private int power                            ;  //电力（含火电、水电、核电、新能源）
	private int coal                             ;  //煤炭
	private int oilAndNaturalGas                 ;  //石油天然气
	private int highway                          ;  //公路
	private int railTransit                      ;  //铁路、城市轨道交通
	private int civilAviation                    ;  //民航
	private int waterTransport                   ;  //水运（含港口河海工程）
	private int electroniCommunication           ;  //电子、信息工程(含通信、广电、信息化)
	private int metallurgy                       ;  //冶金（含钢铁、有色）
	private int chemicalAndMedicine              ;  //石化、化工、医药
	private int nuclearIndustry                  ;  //核工业
	private int mechanics                        ;  //机械（含智能制造）
	private int lightAndTextileIndustry          ;  //轻工、纺织
	private int buildingMaterial                 ;  //建材
	private int architecture                     ;  //建筑
	private int municipalUtilities               ;  //市政公用工程
	private int ecologicalEnvironmentEngineering ;  //生态建设和环境工程
	private int hydrogeologySurveyGeotechnical   ;  //水文地质、工程测量、岩土工程
	private int speciallty21                     ;  //speciallty21
	private int cityPlanning                     ;  //其他（城市规划）    
	private int earthquakeEngineering            ;  //其他（地震工程）    
	private int engineeringTechnologyEconomy     ;  //其他（工程技术经济）  
	private int ancientArchitecturalBuildings    ;  //其他（古建筑）     
	private int oceanographicEngineering         ;  //其他（海洋工程）    
	private int povertyReductionProjects         ;  //其他（减贫工程）    
	private int energyConservation               ;  //其他（节能）      
	private int mineralExploitation              ;  //其他（矿产开发）    
	private int tourismEngineering               ;  //其他（旅游工程）    
	private int meteorologicalEngineering        ;  //其他（气象工程）    
	private int commercialGrain                  ;  //其他（商物粮）     
	private int bioengineering                   ;  //其他（生物工程）    
	private int cableway                         ;  //其他（索道）      
	private int landUse                          ;  //其他（土地利用）    
	private int landArrangement                  ;  //其他（土地整理）    
	private int immigrationProject               ;  //其他（移民工程）    
	private int postalEngineering                ;  //其他（邮政工程）    
	private int packagingIndustry                ;  //其他（包装工业）    
	
	
	private String batchNo;
	
	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}
	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * @return the officeName
	 */
	public String getOfficeName() {
		return officeName;
	}
	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	/**
	 * @return the tatol
	 */
	public int getTatol() {
		return tatol;
	}
	/**
	 * @param tatol the tatol to set
	 */
	public void setTatol(int tatol) {
		this.tatol = tatol;
	}
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * 农业、林业
	 * @return the farmingAndForestry
	 */
	public int getFarmingAndForestry() {
		return farmingAndForestry;
	}
	/**
	 * 农业、林业
	 * @param farmingAndForestry the farmingAndForestry to set
	 */
	public void setFarmingAndForestry(int farmingAndForestry) {
		this.farmingAndForestry = farmingAndForestry;
	}
	/**
	 * 水利水电
	 * @return the waterConservancyAndHydropower
	 */
	public int getWaterConservancyAndHydropower() {
		return waterConservancyAndHydropower;
	}
	/**
	 * 水利水电
	 * @param waterConservancyAndHydropower the waterConservancyAndHydropower to set
	 */
	public void setWaterConservancyAndHydropower(int waterConservancyAndHydropower) {
		this.waterConservancyAndHydropower = waterConservancyAndHydropower;
	}
	/**
	 * 电力（含火电、水电、核电、新能源）
	 * @return the power
	 */
	public int getPower() {
		return power;
	}
	/**
	 * 电力（含火电、水电、核电、新能源）
	 * @param power the power to set
	 */
	public void setPower(int power) {
		this.power = power;
	}
	/**
	 * 煤炭
	 * @return the coal
	 */
	public int getCoal() {
		return coal;
	}
	/**
	 * 煤炭
	 * @param coal the coal to set
	 */
	public void setCoal(int coal) {
		this.coal = coal;
	}
	/**
	 * 石油天然气
	 * @return the oilAndNaturalGas
	 */
	public int getOilAndNaturalGas() {
		return oilAndNaturalGas;
	}
	/**
	 * 石油天然气
	 * @param oilAndNaturalGas the oilAndNaturalGas to set
	 */
	public void setOilAndNaturalGas(int oilAndNaturalGas) {
		this.oilAndNaturalGas = oilAndNaturalGas;
	}
	/**
	 * 公路
	 * @return the highway
	 */
	public int getHighway() {
		return highway;
	}
	/**
	 * 公路
	 * @param highway the highway to set
	 */
	public void setHighway(int highway) {
		this.highway = highway;
	}
	/**
	 * 铁路、城市轨道交通
	 * @return the railTransit
	 */
	public int getRailTransit() {
		return railTransit;
	}
	/**
	 * 铁路、城市轨道交通
	 * @param railTransit the railTransit to set
	 */
	public void setRailTransit(int railTransit) {
		this.railTransit = railTransit;
	}
	/**
	 * 民航
	 * @return the civilAviation
	 */
	public int getCivilAviation() {
		return civilAviation;
	}
	/**
	 * 民航
	 * @param civilAviation the civilAviation to set
	 */
	public void setCivilAviation(int civilAviation) {
		this.civilAviation = civilAviation;
	}
	/**
	 * 水运（含港口河海工程）
	 * @return the waterTransport
	 */
	public int getWaterTransport() {
		return waterTransport;
	}
	/**
	 * 水运（含港口河海工程）
	 * @param waterTransport the waterTransport to set
	 */
	public void setWaterTransport(int waterTransport) {
		this.waterTransport = waterTransport;
	}
	/**
	 * 电子、信息工程(含通信、广电、信息化)
	 * @return the electroniCommunication
	 */
	public int getElectroniCommunication() {
		return electroniCommunication;
	}
	/**
	 * 电子、信息工程(含通信、广电、信息化)
	 * @param electroniCommunication the electroniCommunication to set
	 */
	public void setElectroniCommunication(int electroniCommunication) {
		this.electroniCommunication = electroniCommunication;
	}
	/**冶金（含钢铁、有色）
	 * @return the metallurgy
	 */
	public int getMetallurgy() {
		return metallurgy;
	}
	/**
	 * 冶金（含钢铁、有色）
	 * @param metallurgy the metallurgy to set
	 */
	public void setMetallurgy(int metallurgy) {
		this.metallurgy = metallurgy;
	}
	/**
	 * 石化、化工、医药
	 * @return the chemicalAndMedicine
	 */
	public int getChemicalAndMedicine() {
		return chemicalAndMedicine;
	}
	/**
	 * 石化、化工、医药
	 * @param chemicalAndMedicine the chemicalAndMedicine to set
	 */
	public void setChemicalAndMedicine(int chemicalAndMedicine) {
		this.chemicalAndMedicine = chemicalAndMedicine;
	}
	/**
	 * 核工业
	 * @return the nuclearIndustry
	 */
	public int getNuclearIndustry() {
		return nuclearIndustry;
	}
	/**
	 * 核工业
	 * @param nuclearIndustry the nuclearIndustry to set
	 */
	public void setNuclearIndustry(int nuclearIndustry) {
		this.nuclearIndustry = nuclearIndustry;
	}
	/**
	 * 机械（含智能制造）
	 * @return the mechanics
	 */
	public int getMechanics() {
		return mechanics;
	}
	/**
	 * 机械（含智能制造）
	 * @param mechanics the mechanics to set
	 */
	public void setMechanics(int mechanics) {
		this.mechanics = mechanics;
	}
	/**
	 * 轻工、纺织
	 * @return the lightAndTextileIndustry
	 */
	public int getLightAndTextileIndustry() {
		return lightAndTextileIndustry;
	}
	/**
	 * 轻工、纺织
	 * @param lightAndTextileIndustry the lightAndTextileIndustry to set
	 */
	public void setLightAndTextileIndustry(int lightAndTextileIndustry) {
		this.lightAndTextileIndustry = lightAndTextileIndustry;
	}
	/**
	 * 建材
	 * @return the buildingMaterial
	 */
	public int getBuildingMaterial() {
		return buildingMaterial;
	}
	/**
	 * 建材
	 * @param buildingMaterial the buildingMaterial to set
	 */
	public void setBuildingMaterial(int buildingMaterial) {
		this.buildingMaterial = buildingMaterial;
	}
	/**
	 * 建筑
	 * @return the architecture
	 */
	public int getArchitecture() {
		return architecture;
	}
	/**
	 * 建筑
	 * @param architecture the architecture to set
	 */
	public void setArchitecture(int architecture) {
		this.architecture = architecture;
	}
	/**
	 * 市政公用工程
	 * @return the municipalUtilities
	 */
	public int getMunicipalUtilities() {
		return municipalUtilities;
	}
	/**
	 * 市政公用工程
	 * @param municipalUtilities the municipalUtilities to set
	 */
	public void setMunicipalUtilities(int municipalUtilities) {
		this.municipalUtilities = municipalUtilities;
	}
	/**
	 * 生态建设和环境工程
	 * @return the ecologicalEnvironmentEngineering
	 */
	public int getEcologicalEnvironmentEngineering() {
		return ecologicalEnvironmentEngineering;
	}
	/**
	 * 生态建设和环境工程
	 * @param ecologicalEnvironmentEngineering the ecologicalEnvironmentEngineering to set
	 */
	public void setEcologicalEnvironmentEngineering(int ecologicalEnvironmentEngineering) {
		this.ecologicalEnvironmentEngineering = ecologicalEnvironmentEngineering;
	}
	/**
	 * 水文地质、工程测量、岩土工程
	 * @return the hydrogeologySurveyGeotechnical
	 */
	public int getHydrogeologySurveyGeotechnical() {
		return hydrogeologySurveyGeotechnical;
	}
	/**
	 * 水文地质、工程测量、岩土工程
	 * @param hydrogeologySurveyGeotechnical the hydrogeologySurveyGeotechnical to set
	 */
	public void setHydrogeologySurveyGeotechnical(int hydrogeologySurveyGeotechnical) {
		this.hydrogeologySurveyGeotechnical = hydrogeologySurveyGeotechnical;
	}
	/**
	 * 专业21
	 * @return the speciallty21
	 */
	public int getSpeciallty21() {
		return speciallty21;
	}
	/**
	 * 专业21
	 * @param speciallty21 the speciallty21 to set
	 */
	public void setSpeciallty21(int speciallty21) {
		this.speciallty21 = speciallty21;
	}
	/**
	 * 其他（城市规划）
	 * @return the cityPlanning
	 */
	public int getCityPlanning() {
		return cityPlanning;
	}
	/**
	 * 其他（城市规划）
	 * @param cityPlanning the cityPlanning to set
	 */
	public void setCityPlanning(int cityPlanning) {
		this.cityPlanning = cityPlanning;
	}
	/**
	 * 其他（地震工程）
	 * @return the earthquakeEngineering
	 */
	public int getEarthquakeEngineering() {
		return earthquakeEngineering;
	}
	/**
	 * 其他（地震工程）
	 * @param earthquakeEngineering the earthquakeEngineering to set
	 */
	public void setEarthquakeEngineering(int earthquakeEngineering) {
		this.earthquakeEngineering = earthquakeEngineering;
	}
	/**
	 * 其他（工程技术经济）
	 * @return the engineeringTechnologyEconomy
	 */
	public int getEngineeringTechnologyEconomy() {
		return engineeringTechnologyEconomy;
	}
	/**
	 * 其他（工程技术经济）
	 * @param engineeringTechnologyEconomy the engineeringTechnologyEconomy to set
	 */
	public void setEngineeringTechnologyEconomy(int engineeringTechnologyEconomy) {
		this.engineeringTechnologyEconomy = engineeringTechnologyEconomy;
	}
	/**
	 * 其他（古建筑）
	 * @return the ancientArchitecturalBuildings
	 */
	public int getAncientArchitecturalBuildings() {
		return ancientArchitecturalBuildings;
	}
	/**
	 * 其他（古建筑）
	 * @param ancientArchitecturalBuildings the ancientArchitecturalBuildings to set
	 */
	public void setAncientArchitecturalBuildings(int ancientArchitecturalBuildings) {
		this.ancientArchitecturalBuildings = ancientArchitecturalBuildings;
	}
	/**
	 * 其他（海洋工程）
	 * @return the oceanographicEngineering
	 */
	public int getOceanographicEngineering() {
		return oceanographicEngineering;
	}
	/**
	 * 其他（海洋工程）
	 * @param oceanographicEngineering the oceanographicEngineering to set
	 */
	public void setOceanographicEngineering(int oceanographicEngineering) {
		this.oceanographicEngineering = oceanographicEngineering;
	}
	/**
	 * 其他（减贫工程）
	 * @return the povertyReductionProjects
	 */
	public int getPovertyReductionProjects() {
		return povertyReductionProjects;
	}
	/**
	 * 其他（减贫工程）
	 * @param povertyReductionProjects the povertyReductionProjects to set
	 */
	public void setPovertyReductionProjects(int povertyReductionProjects) {
		this.povertyReductionProjects = povertyReductionProjects;
	}
	/**
	 * 其他（节能）
	 * @return the energyConservation
	 */
	public int getEnergyConservation() {
		return energyConservation;
	}
	/**
	 * 其他（节能）
	 * @param energyConservation the energyConservation to set
	 */
	public void setEnergyConservation(int energyConservation) {
		this.energyConservation = energyConservation;
	}
	/**
	 * 其他（矿产开发）
	 * @return the mineralExploitation
	 */
	public int getMineralExploitation() {
		return mineralExploitation;
	}
	/**
	 * 其他（矿产开发）
	 * @param mineralExploitation the mineralExploitation to set
	 */
	public void setMineralExploitation(int mineralExploitation) {
		this.mineralExploitation = mineralExploitation;
	}
	/**
	 * 其他（旅游工程）
	 * @return the tourismEngineering
	 */
	public int getTourismEngineering() {
		return tourismEngineering;
	}
	/**
	 * 其他（旅游工程）
	 * @param tourismEngineering the tourismEngineering to set
	 */
	public void setTourismEngineering(int tourismEngineering) {
		this.tourismEngineering = tourismEngineering;
	}
	/**
	 * 其他（气象工程）
	 * @return the meteorologicalEngineering
	 */
	public int getMeteorologicalEngineering() {
		return meteorologicalEngineering;
	}
	/**
	 * 其他（气象工程）
	 * @param meteorologicalEngineering the meteorologicalEngineering to set
	 */
	public void setMeteorologicalEngineering(int meteorologicalEngineering) {
		this.meteorologicalEngineering = meteorologicalEngineering;
	}
	/**
	 * 其他（商物粮）
	 * @return the commercialGrain
	 */
	public int getCommercialGrain() {
		return commercialGrain;
	}
	/**
	 * 其他（商物粮）
	 * @param commercialGrain the commercialGrain to set
	 */
	public void setCommercialGrain(int commercialGrain) {
		this.commercialGrain = commercialGrain;
	}
	/**
	 * 其他（生物工程）
	 * @return the bioengineering
	 */
	public int getBioengineering() {
		return bioengineering;
	}
	/**
	 * 其他（生物工程）
	 * @param bioengineering the bioengineering to set
	 */
	public void setBioengineering(int bioengineering) {
		this.bioengineering = bioengineering;
	}
	/**
	 * 其他（索道）
	 * @return the cableway
	 */
	public int getCableway() {
		return cableway;
	}
	/**
	 * 其他（索道）
	 * @param cableway the cableway to set
	 */
	public void setCableway(int cableway) {
		this.cableway = cableway;
	}
	/**
	 * 其他（土地利用）
	 * @return the landUse
	 */
	public int getLandUse() {
		return landUse;
	}
	/**
	 * 其他（土地利用）
	 * @param landUse the landUse to set
	 */
	public void setLandUse(int landUse) {
		this.landUse = landUse;
	}
	/**
	 * 其他（土地整理）
	 * @return the landArrangement
	 */
	public int getLandArrangement() {
		return landArrangement;
	}
	/**
	 * 其他（土地整理）
	 * @param landArrangement the landArrangement to set
	 */
	public void setLandArrangement(int landArrangement) {
		this.landArrangement = landArrangement;
	}
	/**
	 * 其他（移民工程）
	 * @return the immigrationProject
	 */
	public int getImmigrationProject() {
		return immigrationProject;
	}
	/**
	 * 其他（移民工程）
	 * @param immigrationProject the immigrationProject to set
	 */
	public void setImmigrationProject(int immigrationProject) {
		this.immigrationProject = immigrationProject;
	}
	/**
	 * 其他（邮政工程）
	 * @return the postalEngineering
	 */
	public int getPostalEngineering() {
		return postalEngineering;
	}
	/**
	 * 其他（邮政工程）
	 * @param postalEngineering the postalEngineering to set
	 */
	public void setPostalEngineering(int postalEngineering) {
		this.postalEngineering = postalEngineering;
	}
	/**
	 * 其他（包装工业）
	 * @return the packagingIndustry
	 */
	public int getPackagingIndustry() {
		return packagingIndustry;
	}
	/**
	 * 其他（包装工业）
	 * @param packagingIndustry the packagingIndustry to set
	 */
	public void setPackagingIndustry(int packagingIndustry) {
		this.packagingIndustry = packagingIndustry;
	}

}
