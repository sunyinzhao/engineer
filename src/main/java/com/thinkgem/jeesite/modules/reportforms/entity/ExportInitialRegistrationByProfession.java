package com.thinkgem.jeesite.modules.reportforms.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 初始登记按专业划分
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class ExportInitialRegistrationByProfession{
	

	public ExportInitialRegistrationByProfession() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 对象标识
	 */
	private static final long serialVersionUID = 1L;
	
	private String officeName;//预审单位
	private int tatol;//总结
	private int index;//序号
	
	private int farmingAndForestry               ;
	private int waterConservancyAndHydropower    ;
	private int power                            ;
	private int coal                             ;
	private int oilAndNaturalGas                 ;
	private int highway                          ;
	private int railTransit                      ;
	private int civilAviation                    ;
	private int waterTransport                   ;
	private int electroniCommunication           ;
	private int metallurgy                       ;
	private int chemicalAndMedicine              ;
	private int nuclearIndustry                  ;
	private int mechanics                        ;
	private int lightAndTextileIndustry          ;
	private int buildingMaterial                 ;
	private int architecture                     ;
	private int municipalUtilities               ;
	private int ecologicalEnvironmentEngineering ;
	private int hydrogeologySurveyGeotechnical   ;
	private int speciallty21                     ;
	private int cityPlanning                     ;
	private int earthquakeEngineering            ;
	private int engineeringTechnologyEconomy     ;
	private int ancientArchitecturalBuildings    ;
	private int oceanographicEngineering         ;
	private int povertyReductionProjects         ;
	private int energyConservation               ;
	private int mineralExploitation              ;
	private int tourismEngineering               ;
	private int meteorologicalEngineering        ;
	private int commercialGrain                  ;
	private int bioengineering                   ;
	private int cableway                         ;
	private int landUse                          ;
	private int landArrangement                  ;
	private int immigrationProject               ;
	private int postalEngineering                ;
	private int packagingIndustry                ;
	
	
	public ExportInitialRegistrationByProfession(InitialRegistrationByProfession initialRegistrationByProfession) {
		this.index = initialRegistrationByProfession.getIndex();
		this.officeName = initialRegistrationByProfession.getOfficeName();
		this.tatol = initialRegistrationByProfession.getTatol();
		this.farmingAndForestry = initialRegistrationByProfession.getFarmingAndForestry();
		this.waterConservancyAndHydropower = initialRegistrationByProfession.getWaterConservancyAndHydropower();
		this.power = initialRegistrationByProfession.getPower();
		this.coal = initialRegistrationByProfession.getCoal();
		this.oilAndNaturalGas = initialRegistrationByProfession.getOilAndNaturalGas();
		this.highway = initialRegistrationByProfession.getHighway();
		this.railTransit = initialRegistrationByProfession.getRailTransit();
		this.civilAviation = initialRegistrationByProfession.getCivilAviation();
		this.waterTransport = initialRegistrationByProfession.getWaterTransport();
		this.electroniCommunication = initialRegistrationByProfession.getElectroniCommunication();
		this.metallurgy = initialRegistrationByProfession.getMetallurgy();
		this.chemicalAndMedicine = initialRegistrationByProfession.getChemicalAndMedicine();
		this.nuclearIndustry = initialRegistrationByProfession.getNuclearIndustry();
		this.mechanics = initialRegistrationByProfession.getMechanics();
		this.lightAndTextileIndustry = initialRegistrationByProfession.getLightAndTextileIndustry();
		this.buildingMaterial = initialRegistrationByProfession.getBuildingMaterial();
		this.architecture = initialRegistrationByProfession.getArchitecture();
		this.municipalUtilities = initialRegistrationByProfession.getMunicipalUtilities();
		this.ecologicalEnvironmentEngineering = initialRegistrationByProfession.getEcologicalEnvironmentEngineering();
		this.hydrogeologySurveyGeotechnical = initialRegistrationByProfession.getHydrogeologySurveyGeotechnical();
		this.speciallty21 = initialRegistrationByProfession.getSpeciallty21();
		this.cityPlanning = initialRegistrationByProfession.getCityPlanning();
		this.earthquakeEngineering = initialRegistrationByProfession.getEarthquakeEngineering();
		this.engineeringTechnologyEconomy = initialRegistrationByProfession.getEngineeringTechnologyEconomy();
		this.ancientArchitecturalBuildings = initialRegistrationByProfession.getAncientArchitecturalBuildings();
		this.oceanographicEngineering = initialRegistrationByProfession.getOceanographicEngineering();
		this.povertyReductionProjects = initialRegistrationByProfession.getPovertyReductionProjects();
		this.energyConservation = initialRegistrationByProfession.getEnergyConservation();
		this.mineralExploitation = initialRegistrationByProfession.getMineralExploitation();
		this.tourismEngineering = initialRegistrationByProfession.getTourismEngineering();
		this.meteorologicalEngineering = initialRegistrationByProfession.getMeteorologicalEngineering();
		this.commercialGrain = initialRegistrationByProfession.getCommercialGrain();
		this.bioengineering = initialRegistrationByProfession.getBioengineering();
		this.cableway = initialRegistrationByProfession.getCableway();
		this.landUse = initialRegistrationByProfession.getLandUse();
		this.landArrangement = initialRegistrationByProfession.getLandArrangement();
		this.immigrationProject = initialRegistrationByProfession.getImmigrationProject();
		this.postalEngineering = initialRegistrationByProfession.getPostalEngineering();
		this.packagingIndustry = initialRegistrationByProfession.getPackagingIndustry();
	}
	/**
	 * @return the index
	 */
	@ExcelField(title="序号", align=2, sort=1)
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
	 * @return the officeName
	 */
	@ExcelField(title="初审机构", align=2, sort=2)
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
	@ExcelField(title="已报合计", align=2, sort=3)
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
	 * @return the farmingAndForestry
	 */
	@ExcelField(title="农业、林业", align=2, sort=4)
	public int getFarmingAndForestry() {
		return farmingAndForestry;
	}
	/**
	 * @param farmingAndForestry the farmingAndForestry to set
	 */
	public void setFarmingAndForestry(int farmingAndForestry) {
		this.farmingAndForestry = farmingAndForestry;
	}
	/**
	 * @return the waterConservancyAndHydropower
	 */
	@ExcelField(title="水利水电", align=2, sort=5)
	public int getWaterConservancyAndHydropower() {
		return waterConservancyAndHydropower;
	}
	/**
	 * @param waterConservancyAndHydropower the waterConservancyAndHydropower to set
	 */
	public void setWaterConservancyAndHydropower(int waterConservancyAndHydropower) {
		this.waterConservancyAndHydropower = waterConservancyAndHydropower;
	}
	/**
	 * @return the power
	 */
	@ExcelField(title="电力", align=2, sort=6)
	public int getPower() {
		return power;
	}
	/**
	 * @param power the power to set
	 */
	public void setPower(int power) {
		this.power = power;
	}
	/**
	 * @return the coal
	 */
	@ExcelField(title="煤炭", align=2, sort=7)
	public int getCoal() {
		return coal;
	}
	/**
	 * @param coal the coal to set
	 */
	public void setCoal(int coal) {
		this.coal = coal;
	}
	/**
	 * @return the oilAndNaturalGas
	 */
	@ExcelField(title="石油天然气", align=2, sort=8)
	public int getOilAndNaturalGas() {
		return oilAndNaturalGas;
	}
	/**
	 * @param oilAndNaturalGas the oilAndNaturalGas to set
	 */
	public void setOilAndNaturalGas(int oilAndNaturalGas) {
		this.oilAndNaturalGas = oilAndNaturalGas;
	}
	/**
	 * @return the highway
	 */
	@ExcelField(title="公路", align=2, sort=9)
	public int getHighway() {
		return highway;
	}
	/**
	 * @param highway the highway to set
	 */
	public void setHighway(int highway) {
		this.highway = highway;
	}
	/**
	 * @return the railTransit
	 */
	@ExcelField(title="铁路、城市轨道交通", align=2, sort=10)
	public int getRailTransit() {
		return railTransit;
	}
	/**
	 * @param railTransit the railTransit to set
	 */
	public void setRailTransit(int railTransit) {
		this.railTransit = railTransit;
	}
	/**
	 * @return the civilAviation
	 */
	@ExcelField(title="民航", align=2, sort=11)
	public int getCivilAviation() {
		return civilAviation;
	}
	/**
	 * @param civilAviation the civilAviation to set
	 */
	public void setCivilAviation(int civilAviation) {
		this.civilAviation = civilAviation;
	}
	/**
	 * @return the waterTransport
	 */
	@ExcelField(title="水运", align=2, sort=12)
	public int getWaterTransport() {
		return waterTransport;
	}
	/**
	 * @param waterTransport the waterTransport to set
	 */
	public void setWaterTransport(int waterTransport) {
		this.waterTransport = waterTransport;
	}
	/**
	 * @return the electroniCommunication
	 */
	@ExcelField(title="电子、信息工程", align=2, sort=13)
	public int getElectroniCommunication() {
		return electroniCommunication;
	}
	/**
	 * @param electroniCommunication the electroniCommunication to set
	 */
	public void setElectroniCommunication(int electroniCommunication) {
		this.electroniCommunication = electroniCommunication;
	}
	/**
	 * @return the metallurgy
	 */
	@ExcelField(title="冶金", align=2, sort=14)
	public int getMetallurgy() {
		return metallurgy;
	}
	/**
	 * @param metallurgy the metallurgy to set
	 */
	public void setMetallurgy(int metallurgy) {
		this.metallurgy = metallurgy;
	}
	/**
	 * @return the chemicalAndMedicine
	 */
	@ExcelField(title="石化、化工、医药", align=2, sort=15)
	public int getChemicalAndMedicine() {
		return chemicalAndMedicine;
	}
	/**
	 * @param chemicalAndMedicine the chemicalAndMedicine to set
	 */
	public void setChemicalAndMedicine(int chemicalAndMedicine) {
		this.chemicalAndMedicine = chemicalAndMedicine;
	}
	/**
	 * @return the nuclearIndustry
	 */
	@ExcelField(title="核工业", align=2, sort=16)
	public int getNuclearIndustry() {
		return nuclearIndustry;
	}
	/**
	 * @param nuclearIndustry the nuclearIndustry to set
	 */
	public void setNuclearIndustry(int nuclearIndustry) {
		this.nuclearIndustry = nuclearIndustry;
	}
	/**
	 * @return the mechanics
	 */
	@ExcelField(title="机械", align=2, sort=17)
	public int getMechanics() {
		return mechanics;
	}
	/**
	 * @param mechanics the mechanics to set
	 */
	public void setMechanics(int mechanics) {
		this.mechanics = mechanics;
	}
	/**
	 * @return the lightAndTextileIndustry
	 */
	@ExcelField(title="轻工、纺织", align=2, sort=18)
	public int getLightAndTextileIndustry() {
		return lightAndTextileIndustry;
	}
	/**
	 * @param lightAndTextileIndustry the lightAndTextileIndustry to set
	 */
	public void setLightAndTextileIndustry(int lightAndTextileIndustry) {
		this.lightAndTextileIndustry = lightAndTextileIndustry;
	}
	/**
	 * @return the buildingMaterial
	 */
	@ExcelField(title="建材", align=2, sort=19)
	public int getBuildingMaterial() {
		return buildingMaterial;
	}
	/**
	 * @param buildingMaterial the buildingMaterial to set
	 */
	public void setBuildingMaterial(int buildingMaterial) {
		this.buildingMaterial = buildingMaterial;
	}
	/**
	 * @return the architecture
	 */
	@ExcelField(title="建筑", align=2, sort=20)
	public int getArchitecture() {
		return architecture;
	}
	/**
	 * @param architecture the architecture to set
	 */
	public void setArchitecture(int architecture) {
		this.architecture = architecture;
	}
	/**
	 * @return the municipalUtilities
	 */
	@ExcelField(title="市政公用工程", align=2, sort=21)
	public int getMunicipalUtilities() {
		return municipalUtilities;
	}
	/**
	 * @param municipalUtilities the municipalUtilities to set
	 */
	public void setMunicipalUtilities(int municipalUtilities) {
		this.municipalUtilities = municipalUtilities;
	}
	/**
	 * @return the ecologicalEnvironmentEngineering
	 */
	@ExcelField(title="生态建设和环境工程", align=2, sort=22)
	public int getEcologicalEnvironmentEngineering() {
		return ecologicalEnvironmentEngineering;
	}
	/**
	 * @param ecologicalEnvironmentEngineering the ecologicalEnvironmentEngineering to set
	 */
	public void setEcologicalEnvironmentEngineering(int ecologicalEnvironmentEngineering) {
		this.ecologicalEnvironmentEngineering = ecologicalEnvironmentEngineering;
	}
	/**
	 * @return the hydrogeologySurveyGeotechnical
	 */
	@ExcelField(title="水文地质、工程测量、岩土工程", align=2, sort=23)
	public int getHydrogeologySurveyGeotechnical() {
		return hydrogeologySurveyGeotechnical;
	}
	/**
	 * @param hydrogeologySurveyGeotechnical the hydrogeologySurveyGeotechnical to set
	 */
	public void setHydrogeologySurveyGeotechnical(int hydrogeologySurveyGeotechnical) {
		this.hydrogeologySurveyGeotechnical = hydrogeologySurveyGeotechnical;
	}
	/**
	 * @return the speciallty21
	 */
	@ExcelField(title="无此专业", align=2, sort=24)
	public int getSpeciallty21() {
		return speciallty21;
	}
	/**
	 * @param speciallty21 the speciallty21 to set
	 */
	public void setSpeciallty21(int speciallty21) {
		this.speciallty21 = speciallty21;
	}
	/**
	 * @return the cityPlanning
	 */
	@ExcelField(title="城市规划", align=2, sort=25)
	public int getCityPlanning() {
		return cityPlanning;
	}
	/**
	 * @param cityPlanning the cityPlanning to set
	 */
	public void setCityPlanning(int cityPlanning) {
		this.cityPlanning = cityPlanning;
	}
	/**
	 * @return the earthquakeEngineering
	 */
	@ExcelField(title="地震工程", align=2, sort=26)
	public int getEarthquakeEngineering() {
		return earthquakeEngineering;
	}
	/**
	 * @param earthquakeEngineering the earthquakeEngineering to set
	 */
	public void setEarthquakeEngineering(int earthquakeEngineering) {
		this.earthquakeEngineering = earthquakeEngineering;
	}
	/**
	 * @return the engineeringTechnologyEconomy
	 */
	@ExcelField(title="工程技术经济", align=2, sort=27)
	public int getEngineeringTechnologyEconomy() {
		return engineeringTechnologyEconomy;
	}
	/**
	 * @param engineeringTechnologyEconomy the engineeringTechnologyEconomy to set
	 */
	public void setEngineeringTechnologyEconomy(int engineeringTechnologyEconomy) {
		this.engineeringTechnologyEconomy = engineeringTechnologyEconomy;
	}
	/**
	 * @return the ancientArchitecturalBuildings
	 */
	@ExcelField(title="古建筑", align=2, sort=28)
	public int getAncientArchitecturalBuildings() {
		return ancientArchitecturalBuildings;
	}
	/**
	 * @param ancientArchitecturalBuildings the ancientArchitecturalBuildings to set
	 */
	public void setAncientArchitecturalBuildings(int ancientArchitecturalBuildings) {
		this.ancientArchitecturalBuildings = ancientArchitecturalBuildings;
	}
	/**
	 * @return the oceanographicEngineering
	 */
	@ExcelField(title="海洋工程", align=2, sort=29)
	public int getOceanographicEngineering() {
		return oceanographicEngineering;
	}
	/**
	 * @param oceanographicEngineering the oceanographicEngineering to set
	 */
	public void setOceanographicEngineering(int oceanographicEngineering) {
		this.oceanographicEngineering = oceanographicEngineering;
	}
	/**
	 * @return the povertyReductionProjects
	 */
	@ExcelField(title="减贫工程", align=2, sort=30)
	public int getPovertyReductionProjects() {
		return povertyReductionProjects;
	}
	/**
	 * @param povertyReductionProjects the povertyReductionProjects to set
	 */
	public void setPovertyReductionProjects(int povertyReductionProjects) {
		this.povertyReductionProjects = povertyReductionProjects;
	}
	/**
	 * @return the energyConservation
	 */
	@ExcelField(title="节能", align=2, sort=31)
	public int getEnergyConservation() {
		return energyConservation;
	}
	/**
	 * @param energyConservation the energyConservation to set
	 */
	public void setEnergyConservation(int energyConservation) {
		this.energyConservation = energyConservation;
	}
	/**
	 * @return the mineralExploitation
	 */
	@ExcelField(title="矿产开发", align=2, sort=32)
	public int getMineralExploitation() {
		return mineralExploitation;
	}
	/**
	 * @param mineralExploitation the mineralExploitation to set
	 */
	public void setMineralExploitation(int mineralExploitation) {
		this.mineralExploitation = mineralExploitation;
	}
	/**
	 * @return the tourismEngineering
	 */
	@ExcelField(title="旅游工程", align=2, sort=33)
	public int getTourismEngineering() {
		return tourismEngineering;
	}
	/**
	 * @param tourismEngineering the tourismEngineering to set
	 */
	public void setTourismEngineering(int tourismEngineering) {
		this.tourismEngineering = tourismEngineering;
	}
	/**
	 * @return the meteorologicalEngineering
	 */
	@ExcelField(title="气象工程", align=2, sort=34)
	public int getMeteorologicalEngineering() {
		return meteorologicalEngineering;
	}
	/**
	 * @param meteorologicalEngineering the meteorologicalEngineering to set
	 */
	public void setMeteorologicalEngineering(int meteorologicalEngineering) {
		this.meteorologicalEngineering = meteorologicalEngineering;
	}
	/**
	 * @return the commercialGrain
	 */
	@ExcelField(title="商物粮", align=2, sort=35)
	public int getCommercialGrain() {
		return commercialGrain;
	}
	/**
	 * @param commercialGrain the commercialGrain to set
	 */
	public void setCommercialGrain(int commercialGrain) {
		this.commercialGrain = commercialGrain;
	}
	/**
	 * @return the bioengineering
	 */
	@ExcelField(title="生物工程", align=2, sort=36)
	public int getBioengineering() {
		return bioengineering;
	}
	/**
	 * @param bioengineering the bioengineering to set
	 */
	public void setBioengineering(int bioengineering) {
		this.bioengineering = bioengineering;
	}
	/**
	 * @return the cableway
	 */
	@ExcelField(title="索道", align=2, sort=37)
	public int getCableway() {
		return cableway;
	}
	/**
	 * @param cableway the cableway to set
	 */
	public void setCableway(int cableway) {
		this.cableway = cableway;
	}
	/**
	 * @return the landUse
	 */
	@ExcelField(title="土地利用", align=2, sort=38)
	public int getLandUse() {
		return landUse;
	}
	/**
	 * @param landUse the landUse to set
	 */
	public void setLandUse(int landUse) {
		this.landUse = landUse;
	}
	/**
	 * @return the landArrangement
	 */
	@ExcelField(title="土地整理", align=2, sort=39)
	public int getLandArrangement() {
		return landArrangement;
	}
	/**
	 * @param landArrangement the landArrangement to set
	 */
	public void setLandArrangement(int landArrangement) {
		this.landArrangement = landArrangement;
	}
	/**
	 * @return the immigrationProject
	 */
	@ExcelField(title="移民工程", align=2, sort=40)
	public int getImmigrationProject() {
		return immigrationProject;
	}
	/**
	 * @param immigrationProject the immigrationProject to set
	 */
	public void setImmigrationProject(int immigrationProject) {
		this.immigrationProject = immigrationProject;
	}
	/**
	 * @return the postalEngineering
	 */
	@ExcelField(title="邮政工程", align=2, sort=41)
	public int getPostalEngineering() {
		return postalEngineering;
	}
	/**
	 * @param postalEngineering the postalEngineering to set
	 */
	public void setPostalEngineering(int postalEngineering) {
		this.postalEngineering = postalEngineering;
	}
	/**
	 * @return the packagingIndustry
	 */
	@ExcelField(title="包装工业", align=2, sort=42)
	public int getPackagingIndustry() {
		return packagingIndustry;
	}
	/**
	 * @param packagingIndustry the packagingIndustry to set
	 */
	public void setPackagingIndustry(int packagingIndustry) {
		this.packagingIndustry = packagingIndustry;
	}
	
}
