package com.thinkgem.jeesite.modules.cfca;


import com.thinkgem.jeesite.common.utils.DateUtils;

import cfca.ra.common.vo.request.CertServiceRequestVO;
import cfca.ra.common.vo.response.CertServiceResponseVO;
import cfca.ra.toolkit.RAClient;
import cfca.ra.toolkit.exception.RATKException;

// 证书申请
public class Test2101 {
    public static void main(String[] args) {
    	
    	
    	System.out.println(DateUtils.parseStringToDate("20180913172748"));
    	System.out.println(DateUtils.formatDateTime(DateUtils.parseStringToDate("20200913172748")));
    	/*System.out.println(System.currentTimeMillis());
        // String locale = "zh_CN";
		//ca名称
		String caName = "OCA31";
        // 普通证书 普通：1 高级：2
       
        String certType = "1";       			// 证书类型标识（复合证书证书类型用'-'连接，其中1：签名单证，2：双证，3-加密单证） 复合证书 单单1-1 单双1-2 双单2-1 双双2-2 
        String customerType = "1";				// 个人-1  企业-2   服务器-3   设备证书-6   场景证书-7  
        
        String userName = "xqg001";     		// 用户姓名
        // String userNameInDn = "testName";
        // String userIdent = "Z1234567890";
        String identType = "0";    				// 证件类型 ‘0’： 居民身份证
        String identNo = "231085198701222922";  // 证件号
        //String keyAlg = "RSA"; 					//加密算法 默认 “RSA”
        //String keyLength = "2048"; 				//密钥长度 默认 “2048”
        String branchCode = "678";               //证书所属机构
        // String email = "tdest";
        // String phoneNo = "12345678";
        // String address = "address";
        // String duration = "24";
        // String endTime = "20160101000001"; // endTime与duration同时非空时，证书截止时间以endTime为准，duration作为证书默认有效期记入数据库
        // String addIdentNoExt = "false";
        // String addEmailExt = "false";
        // String selfExtValue = "extValue";
        // 设备标识
        String deviceIdentifier = "19+x,2.168.117.101";
        // 部门名称
        String departmentNameInCert = "部门名称";
        // 组织机构名称
        String organizationNameInCert = "组织机构名称";
        // 营业地点所在地市
        String locality = "北京";
        // 营业地点所在省
        String stateOrProvince = "北京";
        // 国家,不填默认为CN
        String country = "CN";
        // 开始时间(不能早于当前时间一定范围，该值可在sys.ini中配置)
        // String startTime = "20170903154323";
        try {
            RAClient client = TestConfig.getRAClient();

            CertServiceRequestVO certServiceRequestVO = new CertServiceRequestVO();
            certServiceRequestVO.setTxCode("2101");
			certServiceRequestVO.setCaName(caName);
            // certServiceRequestVO.setLocale(locale);
            certServiceRequestVO.setCertType(certType);
            certServiceRequestVO.setCustomerType(customerType);
            certServiceRequestVO.setUserName(userName);
            // certServiceRequestVO.setUserNameInDn(userNameInDn);
            // certServiceRequestVO.setUserIdent(userIdent);
            certServiceRequestVO.setIdentType(identType);
            certServiceRequestVO.setIdentNo(identNo);
            // certServiceRequestVO.setKeyLength(keyLength);
            // certServiceRequestVO.setKeyAlg(keyAlg);
            certServiceRequestVO.setBranchCode(branchCode);
            // certServiceRequestVO.setEmail(email);
            // certServiceRequestVO.setPhoneNo(phoneNo);
            // certServiceRequestVO.setAddress(address);
            // certServiceRequestVO.setDuration(duration);
            //certServiceRequestVO.setStartTime(startTime);
            // certServiceRequestVO.setEndTime(endTime);
            // certServiceRequestVO.setAddIdentNoExt(addIdentNoExt);
            // certServiceRequestVO.setAddEmailExt(addEmailExt);
            // certServiceRequestVO.setSelfExtValue(selfExtValue);
            certServiceRequestVO.setDeviceIdentifier(deviceIdentifier);
            certServiceRequestVO.setDepartmentNameInCert(departmentNameInCert);
            certServiceRequestVO.setOrganizationNameInCert(organizationNameInCert);
            certServiceRequestVO.setLocality(locality);
            certServiceRequestVO.setStateOrProvince(stateOrProvince);
            certServiceRequestVO.setCountry(country);

            CertServiceResponseVO certServiceResponseVO = (CertServiceResponseVO) client.process(certServiceRequestVO);

            System.out.println(certServiceResponseVO.getResultCode());
            System.out.println(certServiceResponseVO.getResultMessage());
            if (RAClient.SUCCESS.equals(certServiceResponseVO.getResultCode())) {
                System.out.println(certServiceResponseVO.getDn());
                System.out.println(certServiceResponseVO.getSequenceNo());
                System.out.println(certServiceResponseVO.getSerialNo());
                System.out.println(certServiceResponseVO.getAuthCode());
                System.out.println(certServiceResponseVO.getStartTime());
                System.out.println(certServiceResponseVO.getEndTime());
            }
        } catch (RATKException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis());*/
    }
}
