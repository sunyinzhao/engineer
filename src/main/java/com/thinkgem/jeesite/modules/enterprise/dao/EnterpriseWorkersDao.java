/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.enterprise.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.sys.entity.User;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 咨询师注册
 * @author xqg
 * @version 2018-05-05
 */
@MyBatisDao
public interface EnterpriseWorkersDao extends CrudDao<EnterpriseWorkers> {

    /*
    * 寻找数字最大的文件路径
    * */
    public Integer findMaxNo(@Param("pid")String pid);

    /**
     * 更新用户信息
     * @return
     */
    public int updateCompany();

    //查询有图片的人员列表
    List<EnterpriseWorkers> findListByAttachment(@Param(value = "pid")String pid, @Param(value = "type") String type,@Param(value = "declareRecordId") String declareRecordId,@Param(value = "retire")String retire);
    //校检企业信息中的身份证号(添加时校检)
	List<EnterpriseWorkers> checkCardNumberAdd(EnterpriseWorkers enterprise);
	//校检企业信息中的身份证号（修改时校检）
	List<EnterpriseWorkers> checkCardNumberUpdate(EnterpriseWorkers enterprise);

    List<EnterpriseWorkers> findOrder(@Param(value = "pid")String pid, @Param(value = "declareRecordId")String declareRecordId);

    List<EnterpriseWorkers> findListBySpecialty(@Param(value = "pid") String pid,@Param(value = "specialtyType") String speciatltyType ,@Param(value = "declareRecordId") String declareRecordId);
    
    List<EnterpriseWorkers> findListByDeclareId(@Param(value = "declareRecordId") String declareRecordId);


    List<EnterpriseWorkers> findNewList3(@Param(value = "declareRecordId")String declareRecordId,@Param(value = "specialtyId") String specialtyId);

    List<EnterpriseWorkers> findNewList4(@Param(value = "declareRecordId")String declareRecordId, @Param(value = "specialtyId")String specialtyId);

    List<EnterpriseWorkers> findNewList6(@Param(value = "pid")String userId);

    List<EnterpriseWorkers> findNewList7(@Param(value = "pid")String userId);

    List<EnterpriseWorkers> findNewList8(@Param(value = "declareRecordId")String declareRecordId, @Param(value = "specialtyId")String specialtyId);

    List<EnterpriseWorkers> findNewList9(@Param(value = "declareRecordId")String declareRecordId, @Param(value = "specialtyId")String specialtyId);

    List<EnterpriseWorkers> findNewList10(@Param(value = "declareRecordId")String declareRecordId, @Param(value = "specialtyId")String specialtyId);

    List<EnterpriseWorkers> findNewList11(@Param(value = "pid")String userId);

    List<EnterpriseWorkers> findNewList12(@Param(value = "declareRecordId")String declareRecordId, @Param(value = "specialtyId")String specialtyId);

    List<EnterpriseWorkers> findNewList13(@Param(value = "declareRecordId")String declareRecordId, @Param(value = "specialtyId")String specialtyId);

    List<EnterpriseWorkers> findNewList14(@Param(value = "pid") String userId);

    List<EnterpriseWorkers> findNewList16(@Param(value = "declareRecordId")String declareRecordId, @Param(value = "specialtyId")String specialtyId);

    List<EnterpriseWorkers> findNewList17(@Param(value = "declareRecordId")String declareRecordId);

    List<EnterpriseWorkers> findNewList18(@Param(value = "declareRecordId")String declareRecordId);
    /**
     * 查询出咨询师可以当高工的
     * @param declareRecordId
     * @return
     */
    List<EnterpriseWorkers> findPersonApplyCount(@Param(value = "declareRecordId")String declareRecordId);


    List<EnterpriseWorkers> findNewList21(@Param(value = "declareRecordId")String declareRecordId, @Param(value = "specialtyId") String specialtyId);

    List<EnterpriseWorkers> findNewList22(@Param(value = "declareRecordId")String declareRecordId, @Param(value = "specialtyId") String specialtyId);

    List<EnterpriseWorkers> findNewList23(@Param(value = "pid") String userId);

    EnterpriseWorkers getByTablePerson(@Param(value = "tableId") String id, @Param(value = "personId") String personId);

    EnterpriseWorkers getByPersonId(@Param(value = "personId") String personId);
	public EnterpriseWorkers findByCertificatesNum(EnterpriseWorkers enterprise);
	public EnterpriseWorkers getTempPhoto(EnterpriseWorkers enterprise);

	public void savaCounselorToUser(User user);

	public void updateIsRegister(EnterpriseWorkers enterpriseWorks);

	public EnterpriseWorkers findPerInfo(EnterpriseWorkers enterpriseWorkers);

	public void updateZXS(EnterpriseWorkers enterpriseWorks);

	public void insertZXS(EnterpriseWorkers enterpriseWorks);

	public void updateZXSInfo(EnterpriseWorkers enterpriseWorker);

	public Integer findName(String name);

	public void updateZXSInfo01(EnterpriseWorkers enterpriseWorker);

	public void updateZXSInfo02(EnterpriseWorkers enterpriseWorker);
	public void updateForAdmin(EnterpriseWorkers enterpriseWorker);
	
	public EnterpriseWorkers getNew(String id);

	public List<EnterpriseWorkers> findPageList(EnterpriseWorkers enterpriseWorkers);

	public List<EnterpriseWorkers> findPageList01(EnterpriseWorkers enterpriseWorkers);

	public List<EnterpriseWorkers> findPageList02(EnterpriseWorkers enterpriseWorkers);

	public List<EnterpriseWorkers> findPageList03(EnterpriseWorkers enterpriseWorkers);
	public List<EnterpriseWorkers> findPageList031(EnterpriseWorkers enterpriseWorkers);
	public List<EnterpriseWorkers> findPageList032(EnterpriseWorkers enterpriseWorkers);
	public List<EnterpriseWorkers> findPageList033(EnterpriseWorkers enterpriseWorkers);
	public List<EnterpriseWorkers> findPageList034(EnterpriseWorkers enterpriseWorkers);

	public List<EnterpriseWorkers> findPageList035(EnterpriseWorkers enterpriseWorkers);

	public List<User> checkMobile(User user2);
	
    EnterpriseWorkers getAddEmail(String personId);
    
    EnterpriseWorkers getWorkerAndOfficeCode(String personId);

    EnterpriseWorkers getByNameNum(@Param(value = "name") String name, @Param(value = "cardNumber") String cardNumber);

	public List<EnterpriseWorkers> findPageList04(EnterpriseWorkers enterpriseWorkers);

	public List<EnterpriseWorkers> findPageList05(EnterpriseWorkers enterpriseWorkers);

	public List<EnterpriseWorkers> zzDistributeExpertList(EnterpriseWorkers enterpriseWorkers);
	
	public List<EnterpriseWorkers> localdistributeExpertList1(EnterpriseWorkers enterpriseWorkers);

	public List<EnterpriseWorkers> localdistributeExpertList2(EnterpriseWorkers enterpriseWorkers);

	public void updatePictureUrl(EnterpriseWorkers enterpriseWorkers);

	public EnterpriseWorkers getPicture(String id);

	public void deletePicture(EnterpriseWorkers enterpriseWorkers);

	public void updateCompanyInfo(EnterpriseWorkers enterpriseWorkers);

	public void updateIsRegister1(EnterpriseWorkers enterpriseWorkers);

	public List<EnterpriseWorkers> findIsValidList(EnterpriseWorkers enterpriseWorkers);

	public List<EnterpriseWorkers> findPageList005(EnterpriseWorkers enterpriseWorkers);
	public List<EnterpriseWorkers> findPageList0051(EnterpriseWorkers enterpriseWorkers);

	public void updateisValid(EnterpriseWorkers enterpriseWorkers);

	public List<EnterpriseWorkers> checkCertificatesNum(EnterpriseWorkers enterpriseWorkers);

	public EnterpriseWorkers findByCertificatesNumAndName(EnterpriseWorkers enterpriseWorker);

	public List<EnterpriseWorkers> localDistributeExpertList(EnterpriseWorkers enterpriseWorkers);

	public List<EnterpriseWorkers> checkCertificatesNumisRegister(EnterpriseWorkers enterpriseWorkers);

    List<User> findSysUserName1(User user);

	public List<EnterpriseWorkers> findCounselorList(EnterpriseWorkers enterpriseWorkers);
	
	public List<EnterpriseWorkers> findCounselorInList(EnterpriseWorkers enterpriseWorkers);
	
	public List<EnterpriseWorkers> findCounselorOutList(EnterpriseWorkers enterpriseWorkers);

    String getOfficeName(@Param(value = "name") String companyName);

	public List<EnterpriseWorkers> expertSuggestionCheckList(EnterpriseWorkers enterpriseWorkers);
	public List<EnterpriseWorkers> expertSuggestionCheckList1(EnterpriseWorkers enterpriseWorkers);
	
	public List<EnterpriseWorkers> punlishList(EnterpriseWorkers enterpriseWorkers);

	public EnterpriseWorkers findByNameAndCertificatesNum(EnterpriseWorkers enterpriseWorks);

	public EnterpriseWorkers findEnterpriseWorkers(EnterpriseWorkers enterpriseWorks);
	public EnterpriseWorkers getMobileByRecordId(String id);
	
	public void updateEnterpriseWorkers(EnterpriseWorkers enterpriseWorks);

	public List<EnterpriseWorkers> findPageList06(EnterpriseWorkers enterpriseWorkers);

	public List<EnterpriseWorkers> findApplicationBills(EnterpriseWorkers enterpriseWorkers);
	public List<EnterpriseWorkers> findApplicationBillsClose(EnterpriseWorkers enterpriseWorkers);
	
	/**
	 * 有效的，并且显示出证书的状态
	 * @param enterpriseWorkers
	 * @return
	 */
	public List<EnterpriseWorkers> availableWorkersList(EnterpriseWorkers enterpriseWorkers);

	/**
	 * 意见复核，分配专家
	 * @param enterpriseWorkers
	 * @return
	 */
	public List<EnterpriseWorkers> reconsiderDistributeExpert(EnterpriseWorkers enterpriseWorkers);

	/**
	 * 更新证书状态
	 * @param enterpriseWorks
	 */
	public void updateZhengShuFlag(EnterpriseWorkers enterpriseWorks);

	
	/**
	 * 更新电子证书状态
	 * @param enterpriseWorks
	 */
	public void updateElectronicChapterFlag(EnterpriseWorkers enterpriseWorks);

    List<EnterpriseWorkers> findUpdateService(@Param(value = "resultDate") Date resultDate);
}