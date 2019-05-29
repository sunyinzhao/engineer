
package com.thinkgem.jeesite.modules.enterprise.web;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkersCount;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkersCountExport;
import com.thinkgem.jeesite.modules.enterprise.service.PersonRecordService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 咨询师登记-中咨协会模块-数据汇总报表--> 终审各批次评审情况统计汇总表
 */
@Controller
@RequestMapping(value = "${adminPath}/enterprise/enterpriseWorkersCount")
public class EnterpriseWorkersCountController extends BaseController {

	@Autowired
	private PersonRecordService personRecordService;


	/****
	 * 传入具体日期 ，返回具体日期增加一个月。
	 */
	private  String subMonth(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date dt = sdf.parse(date);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.MONTH, 1);
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}


	/**
	 * 终审各批次评审情况统计
	 * @return
	 */
	@RequestMapping("/count")
	public String count(String startBatchNo ,String endBatchNo ,String id,HttpServletRequest request,HttpServletResponse response,Model model,EnterpriseWorkersCount enterpriseWorkersCount) throws Exception {
		if (startBatchNo==null){
			return "modules/reportforms/finalSummaryOfStatistics";
		}else {
			if(!"1".equals(UserUtils.getUser().getId())){
				//单独获取登录账户的所在地区id
				String id2 = UserUtils.getUser().getOffice().getId();
				enterpriseWorkersCount.setId(id2);
			}
			else {enterpriseWorkersCount.setId(id);}//要officeId，就是省的id.
			enterpriseWorkersCount.setStartBatchNo(startBatchNo);
			enterpriseWorkersCount.setEndBatchNo(endBatchNo);
//			Page<EnterpriseWorkersCount> page = this.personRecordService.countForPersonRecord(new Page<EnterpriseWorkersCount>(request, response), enterpriseWorkersCount);
			List<EnterpriseWorkersCount> list = personRecordService.count(enterpriseWorkersCount);

			//半年六个批次
			EnterpriseWorkersCountController self = new EnterpriseWorkersCountController();
			String bno2 = self.subMonth(startBatchNo);
			String bno3 = self.subMonth(bno2);
			String bno4 = self.subMonth(bno3);
			String bno5 = self.subMonth(bno4);
			String bno6 = self.subMonth(bno5);

			//后端取到的集合遍历对象将同一个批次的封装在一个集合里
			List<EnterpriseWorkersCount> list1 = new ArrayList<EnterpriseWorkersCount>();
			List<EnterpriseWorkersCount> list2 = new ArrayList<EnterpriseWorkersCount>();
			List<EnterpriseWorkersCount> list3 = new ArrayList<EnterpriseWorkersCount>();
			List<EnterpriseWorkersCount> list4 = new ArrayList<EnterpriseWorkersCount>();
			List<EnterpriseWorkersCount> list5 = new ArrayList<EnterpriseWorkersCount>();
			List<EnterpriseWorkersCount> list6 = new ArrayList<EnterpriseWorkersCount>();
			for (int i = 0; i < list.size(); i++) {
				String bno = list.get(i).getBatchNo();
				if(startBatchNo.equals(bno)){
					list1.add(list.get(i));
				}else if(bno.equals(bno2)){
					list2.add(list.get(i));
				}else if(bno.equals(bno3)){
					list3.add(list.get(i));
				}else if(bno.equals(bno4)){
					list4.add(list.get(i));
				}else if(bno.equals(bno5)){
					list5.add(list.get(i));
				}else if(bno.equals(bno6)){
					list6.add(list.get(i));
				}
			}
//			model.addAttribute("page", list);
			model.addAttribute("list1", list1);
			model.addAttribute("list2", list2);
			model.addAttribute("list3", list3);
			model.addAttribute("list4", list4);
			model.addAttribute("list5", list5);
			model.addAttribute("list6", list6);
			return "modules/reportforms/finalSummaryOfStatistics";
		}
	}


	/**
	 * 终审各批次评审情况统计  --  导出
	 * @return
	 */
	@RequestMapping("/export")
	public String export(String startBatchNo ,String endBatchNo ,String id,HttpServletResponse response,EnterpriseWorkersCount enterpriseWorkersCount, RedirectAttributes redirectAttributes){
		try{
		if (startBatchNo==null){
			return "modules/reportforms/finalSummaryOfStatistics";
		}else {
			if(!"1".equals(UserUtils.getUser().getId())){
				//单独获取登录账户的所在地区id
				String id2 = UserUtils.getUser().getOffice().getId();
				enterpriseWorkersCount.setId(id2);
			}else {enterpriseWorkersCount.setId(id);}//要officeId，就是省的id.
			enterpriseWorkersCount.setStartBatchNo(startBatchNo);
			enterpriseWorkersCount.setEndBatchNo(endBatchNo);
			List<EnterpriseWorkersCount> list = personRecordService.count(enterpriseWorkersCount);

			//半年六个批次
			EnterpriseWorkersCountController self = new EnterpriseWorkersCountController();
			String bno2 = self.subMonth(startBatchNo);
			String bno3 = self.subMonth(bno2);
			String bno4 = self.subMonth(bno3);
			String bno5 = self.subMonth(bno4);
			String bno6 = self.subMonth(bno5);

			//后端取到的集合遍历对象将同一个批次的封装在一个集合里
			List<EnterpriseWorkersCountExport> listEwce = new ArrayList<EnterpriseWorkersCountExport>();
			List<EnterpriseWorkersCountExport> list1 = new ArrayList<EnterpriseWorkersCountExport>();
			List<EnterpriseWorkersCountExport> list2 = new ArrayList<EnterpriseWorkersCountExport>();
			List<EnterpriseWorkersCountExport> list3 = new ArrayList<EnterpriseWorkersCountExport>();
			List<EnterpriseWorkersCountExport> list4 = new ArrayList<EnterpriseWorkersCountExport>();
			List<EnterpriseWorkersCountExport> list5 = new ArrayList<EnterpriseWorkersCountExport>();
			List<EnterpriseWorkersCountExport> list6 = new ArrayList<EnterpriseWorkersCountExport>();
            List<String> totalDataList = new ArrayList<String>();
            if (list.size()!=0) {
                int index = 1;  Integer csfh= 0;  Integer zyfh= 0;  Integer dwfh= 0;Integer jxfh= 0;Integer zxfh= 0;
                Integer sqrs= 0;Integer csdj= 0;  Integer bgzy= 0;  Integer bgdw= 0;Integer jxdj= 0;Integer zxdj= 0;
                Integer yxrs= 0;Integer csbffh= 0;Integer zybffh= 0;Integer dwbf= 0;Integer jxbf= 0;Integer zxbf= 0;
                Integer sxrs= 0;Integer csbf= 0;  Integer zybf= 0;

                for (EnterpriseWorkersCount workers : list) {
    				String bno = workers.getBatchNo();
    				if(startBatchNo.equals(bno)){
    					workers.setIndex(index++);
    					list1.add(new EnterpriseWorkersCountExport(workers));
                        sqrs+=Integer.parseInt(workers.getSqrs());
                        yxrs+=Integer.parseInt(workers.getYxrs());
                        sxrs+=Integer.parseInt(workers.getSxrs());

						csfh+=Integer.parseInt(workers.getCsfh());
						csdj+=Integer.parseInt(workers.getCsdj());
						csbffh+=Integer.parseInt(workers.getCsbffh());
						csbf+=Integer.parseInt(workers.getCsbf());

						zyfh+=Integer.parseInt(workers.getZyfh());
						bgzy+=Integer.parseInt(workers.getBgzy());
						zybffh+=Integer.parseInt(workers.getZybffh());
						zybf+=Integer.parseInt(workers.getZybf());

						dwfh+=Integer.parseInt(workers.getDwfh());
						bgdw+=Integer.parseInt(workers.getBgdw());
						dwbf+=Integer.parseInt(workers.getDwbf());

						jxfh+=Integer.parseInt(workers.getJxfh());
						jxdj+=Integer.parseInt(workers.getJxdj());
						jxbf+=Integer.parseInt(workers.getJxbf());

						zxfh+=Integer.parseInt(workers.getZxfh());
						zxdj+=Integer.parseInt(workers.getZxdj());
						zxbf+=Integer.parseInt(workers.getZxbf());

    				}totalDataList.add(0,sqrs.toString());
                    totalDataList.add(1,yxrs.toString());
                    totalDataList.add(2,sxrs.toString());

                    totalDataList.add(3,csfh.toString());
                    totalDataList.add(4,csdj.toString());
                    totalDataList.add(5,csbffh.toString());
                    totalDataList.add(6,csbf.toString());
                    totalDataList.add(7,zyfh.toString());
                    totalDataList.add(8,bgzy.toString());
                    totalDataList.add(9,zybffh.toString());
                    totalDataList.add(10,zybf.toString());
                    totalDataList.add(11,dwfh.toString());
                    totalDataList.add(12,bgdw.toString());
                    totalDataList.add(13,dwbf.toString());
                    totalDataList.add(14,jxfh.toString());
                    totalDataList.add(15,jxdj.toString());
                    totalDataList.add(16,jxbf.toString());
                    totalDataList.add(17,zxfh.toString());
                    totalDataList.add(18,zxdj.toString());
                    totalDataList.add(19,zxbf.toString());
    			}
    			index = 1;sqrs= 0;yxrs= 0;sxrs= 0;
				csfh= 0;   zyfh= 0;   dwfh= 0; jxfh= 0; zxfh= 0;
				csdj= 0;   bgzy= 0;   bgdw= 0; jxdj= 0; zxdj= 0;
				csbffh= 0; zybffh= 0; dwbf= 0; jxbf= 0; zxbf= 0;
				csbf= 0;   zybf= 0;
				for (EnterpriseWorkersCount workers : list) {
                    String bno = workers.getBatchNo();
                    if(bno.equals(bno2)){
                        workers.setIndex(index++);
                        list2.add(new EnterpriseWorkersCountExport(workers));
                        sqrs+=Integer.parseInt(workers.getSqrs());
                        yxrs+=Integer.parseInt(workers.getYxrs());
                        sxrs+=Integer.parseInt(workers.getSxrs());

						csfh+=Integer.parseInt(workers.getCsfh());
						csdj+=Integer.parseInt(workers.getCsdj());
						csbffh+=Integer.parseInt(workers.getCsbffh());
						csbf+=Integer.parseInt(workers.getCsbf());

						zyfh+=Integer.parseInt(workers.getZyfh());
						bgzy+=Integer.parseInt(workers.getBgzy());
						zybffh+=Integer.parseInt(workers.getZybffh());
						zybf+=Integer.parseInt(workers.getZybf());

						dwfh+=Integer.parseInt(workers.getDwfh());
						bgdw+=Integer.parseInt(workers.getBgdw());
						dwbf+=Integer.parseInt(workers.getDwbf());

						jxfh+=Integer.parseInt(workers.getJxfh());
						jxdj+=Integer.parseInt(workers.getJxdj());
						jxbf+=Integer.parseInt(workers.getJxbf());

						zxfh+=Integer.parseInt(workers.getZxfh());
						zxdj+=Integer.parseInt(workers.getZxdj());
						zxbf+=Integer.parseInt(workers.getZxbf());
                    }totalDataList.add(20,sqrs.toString());
                    totalDataList.add(21,yxrs.toString());
                    totalDataList.add(22,sxrs.toString());

					totalDataList.add(23,csfh.toString());
					totalDataList.add(24,csdj.toString());
					totalDataList.add(25,csbffh.toString());
					totalDataList.add(26,csbf.toString());
					totalDataList.add(27,zyfh.toString());
					totalDataList.add(28,bgzy.toString());
					totalDataList.add(29,zybffh.toString());
					totalDataList.add(30,zybf.toString());
					totalDataList.add(31,dwfh.toString());
					totalDataList.add(32,bgdw.toString());
					totalDataList.add(33,dwbf.toString());
					totalDataList.add(34,jxfh.toString());
					totalDataList.add(35,jxdj.toString());
					totalDataList.add(36,jxbf.toString());
					totalDataList.add(37,zxfh.toString());
					totalDataList.add(38,zxdj.toString());
					totalDataList.add(39,zxbf.toString());
                }index = 1;sqrs= 0;yxrs= 0;sxrs= 0;
				csfh= 0;   zyfh= 0;   dwfh= 0; jxfh= 0; zxfh= 0;
				csdj= 0;   bgzy= 0;   bgdw= 0; jxdj= 0; zxdj= 0;
				csbffh= 0; zybffh= 0; dwbf= 0; jxbf= 0; zxbf= 0;
				csbf= 0;   zybf= 0;
                for (EnterpriseWorkersCount workers : list) {
                    String bno = workers.getBatchNo();
                    if(bno.equals(bno3)){
                        workers.setIndex(index++);
                        list3.add(new EnterpriseWorkersCountExport(workers));
                        sqrs+=Integer.parseInt(workers.getSqrs());
                        yxrs+=Integer.parseInt(workers.getYxrs());
                        sxrs+=Integer.parseInt(workers.getSxrs());

						csfh+=Integer.parseInt(workers.getCsfh());
						csdj+=Integer.parseInt(workers.getCsdj());
						csbffh+=Integer.parseInt(workers.getCsbffh());
						csbf+=Integer.parseInt(workers.getCsbf());

						zyfh+=Integer.parseInt(workers.getZyfh());
						bgzy+=Integer.parseInt(workers.getBgzy());
						zybffh+=Integer.parseInt(workers.getZybffh());
						zybf+=Integer.parseInt(workers.getZybf());

						dwfh+=Integer.parseInt(workers.getDwfh());
						bgdw+=Integer.parseInt(workers.getBgdw());
						dwbf+=Integer.parseInt(workers.getDwbf());

						jxfh+=Integer.parseInt(workers.getJxfh());
						jxdj+=Integer.parseInt(workers.getJxdj());
						jxbf+=Integer.parseInt(workers.getJxbf());

						zxfh+=Integer.parseInt(workers.getZxfh());
						zxdj+=Integer.parseInt(workers.getZxdj());
						zxbf+=Integer.parseInt(workers.getZxbf());
                    }totalDataList.add(40,sqrs.toString());
                    totalDataList.add(41,yxrs.toString());
                    totalDataList.add(42,sxrs.toString());

					totalDataList.add(43,csfh.toString());
					totalDataList.add(44,csdj.toString());
					totalDataList.add(45,csbffh.toString());
					totalDataList.add(46,csbf.toString());
					totalDataList.add(47,zyfh.toString());
					totalDataList.add(48,bgzy.toString());
					totalDataList.add(49,zybffh.toString());
					totalDataList.add(50,zybf.toString());
					totalDataList.add(51,dwfh.toString());
					totalDataList.add(52,bgdw.toString());
					totalDataList.add(53,dwbf.toString());
					totalDataList.add(54,jxfh.toString());
					totalDataList.add(55,jxdj.toString());
					totalDataList.add(56,jxbf.toString());
					totalDataList.add(57,zxfh.toString());
					totalDataList.add(58,zxdj.toString());
					totalDataList.add(59,zxbf.toString());
                }index = 1;sqrs= 0;yxrs= 0;sxrs= 0;
				csfh= 0;   zyfh= 0;   dwfh= 0; jxfh= 0; zxfh= 0;
				csdj= 0;   bgzy= 0;   bgdw= 0; jxdj= 0; zxdj= 0;
				csbffh= 0; zybffh= 0; dwbf= 0; jxbf= 0; zxbf= 0;
				csbf= 0;   zybf= 0;
                for (EnterpriseWorkersCount workers : list) {
                    String bno = workers.getBatchNo();
                    if(bno.equals(bno4)){
                        workers.setIndex(index++);
                        list4.add(new EnterpriseWorkersCountExport(workers));
                        sqrs+=Integer.parseInt(workers.getSqrs());
                        yxrs+=Integer.parseInt(workers.getYxrs());
                        sxrs+=Integer.parseInt(workers.getSxrs());

						csfh+=Integer.parseInt(workers.getCsfh());
						csdj+=Integer.parseInt(workers.getCsdj());
						csbffh+=Integer.parseInt(workers.getCsbffh());
						csbf+=Integer.parseInt(workers.getCsbf());

						zyfh+=Integer.parseInt(workers.getZyfh());
						bgzy+=Integer.parseInt(workers.getBgzy());
						zybffh+=Integer.parseInt(workers.getZybffh());
						zybf+=Integer.parseInt(workers.getZybf());

						dwfh+=Integer.parseInt(workers.getDwfh());
						bgdw+=Integer.parseInt(workers.getBgdw());
						dwbf+=Integer.parseInt(workers.getDwbf());

						jxfh+=Integer.parseInt(workers.getJxfh());
						jxdj+=Integer.parseInt(workers.getJxdj());
						jxbf+=Integer.parseInt(workers.getJxbf());

						zxfh+=Integer.parseInt(workers.getZxfh());
						zxdj+=Integer.parseInt(workers.getZxdj());
						zxbf+=Integer.parseInt(workers.getZxbf());
                    }totalDataList.add(60,sqrs.toString());
                    totalDataList.add(61,yxrs.toString());
                    totalDataList.add(62,sxrs.toString());

					totalDataList.add(63,csfh.toString());
					totalDataList.add(64,csdj.toString());
					totalDataList.add(65,csbffh.toString());
					totalDataList.add(66,csbf.toString());
					totalDataList.add(67,zyfh.toString());
					totalDataList.add(68,bgzy.toString());
					totalDataList.add(69,zybffh.toString());
					totalDataList.add(70,zybf.toString());
					totalDataList.add(71,dwfh.toString());
					totalDataList.add(72,bgdw.toString());
					totalDataList.add(73,dwbf.toString());
					totalDataList.add(74,jxfh.toString());
					totalDataList.add(75,jxdj.toString());
					totalDataList.add(76,jxbf.toString());
					totalDataList.add(77,zxfh.toString());
					totalDataList.add(78,zxdj.toString());
					totalDataList.add(79,zxbf.toString());
                }index = 1;sqrs= 0;yxrs= 0;sxrs= 0;
				csfh= 0;   zyfh= 0;   dwfh= 0; jxfh= 0; zxfh= 0;
				csdj= 0;   bgzy= 0;   bgdw= 0; jxdj= 0; zxdj= 0;
				csbffh= 0; zybffh= 0; dwbf= 0; jxbf= 0; zxbf= 0;
				csbf= 0;   zybf= 0;
                for (EnterpriseWorkersCount workers : list) {
                    String bno = workers.getBatchNo();
                    if(bno.equals(bno5)){
                        workers.setIndex(index++);
                        list5.add(new EnterpriseWorkersCountExport(workers));
                        sqrs+=Integer.parseInt(workers.getSqrs());
                        yxrs+=Integer.parseInt(workers.getYxrs());
                        sxrs+=Integer.parseInt(workers.getSxrs());

						csfh+=Integer.parseInt(workers.getCsfh());
						csdj+=Integer.parseInt(workers.getCsdj());
						csbffh+=Integer.parseInt(workers.getCsbffh());
						csbf+=Integer.parseInt(workers.getCsbf());

						zyfh+=Integer.parseInt(workers.getZyfh());
						bgzy+=Integer.parseInt(workers.getBgzy());
						zybffh+=Integer.parseInt(workers.getZybffh());
						zybf+=Integer.parseInt(workers.getZybf());

						dwfh+=Integer.parseInt(workers.getDwfh());
						bgdw+=Integer.parseInt(workers.getBgdw());
						dwbf+=Integer.parseInt(workers.getDwbf());

						jxfh+=Integer.parseInt(workers.getJxfh());
						jxdj+=Integer.parseInt(workers.getJxdj());
						jxbf+=Integer.parseInt(workers.getJxbf());

						zxfh+=Integer.parseInt(workers.getZxfh());
						zxdj+=Integer.parseInt(workers.getZxdj());
						zxbf+=Integer.parseInt(workers.getZxbf());
                    }totalDataList.add(80,sqrs.toString());
                    totalDataList.add(81,yxrs.toString());
                    totalDataList.add(82,sxrs.toString());

					totalDataList.add(83,csfh.toString());
					totalDataList.add(84,csdj.toString());
					totalDataList.add(85,csbffh.toString());
					totalDataList.add(86,csbf.toString());
					totalDataList.add(87,zyfh.toString());
					totalDataList.add(88,bgzy.toString());
					totalDataList.add(89,zybffh.toString());
					totalDataList.add(80,zybf.toString());
					totalDataList.add(81,dwfh.toString());
					totalDataList.add(82,bgdw.toString());
					totalDataList.add(83,dwbf.toString());
					totalDataList.add(84,jxfh.toString());
					totalDataList.add(85,jxdj.toString());
					totalDataList.add(86,jxbf.toString());
					totalDataList.add(87,zxfh.toString());
					totalDataList.add(88,zxdj.toString());
					totalDataList.add(89,zxbf.toString());
                }index = 1;sqrs= 0;yxrs= 0;sxrs= 0;
				csfh= 0;   zyfh= 0;   dwfh= 0; jxfh= 0; zxfh= 0;
				csdj= 0;   bgzy= 0;   bgdw= 0; jxdj= 0; zxdj= 0;
				csbffh= 0; zybffh= 0; dwbf= 0; jxbf= 0; zxbf= 0;
				csbf= 0;   zybf= 0;
                for (EnterpriseWorkersCount workers : list) {
                    String bno = workers.getBatchNo();
                    if(bno.equals(bno6)){
                        workers.setIndex(index++);
                        list6.add(new EnterpriseWorkersCountExport(workers));
                        sqrs+=Integer.parseInt(workers.getSqrs());
                        yxrs+=Integer.parseInt(workers.getYxrs());
                        sxrs+=Integer.parseInt(workers.getSxrs());

						csfh+=Integer.parseInt(workers.getCsfh());
						csdj+=Integer.parseInt(workers.getCsdj());
						csbffh+=Integer.parseInt(workers.getCsbffh());
						csbf+=Integer.parseInt(workers.getCsbf());

						zyfh+=Integer.parseInt(workers.getZyfh());
						bgzy+=Integer.parseInt(workers.getBgzy());
						zybffh+=Integer.parseInt(workers.getZybffh());
						zybf+=Integer.parseInt(workers.getZybf());

						dwfh+=Integer.parseInt(workers.getDwfh());
						bgdw+=Integer.parseInt(workers.getBgdw());
						dwbf+=Integer.parseInt(workers.getDwbf());

						jxfh+=Integer.parseInt(workers.getJxfh());
						jxdj+=Integer.parseInt(workers.getJxdj());
						jxbf+=Integer.parseInt(workers.getJxbf());

						zxfh+=Integer.parseInt(workers.getZxfh());
						zxdj+=Integer.parseInt(workers.getZxdj());
						zxbf+=Integer.parseInt(workers.getZxbf());
                    }totalDataList.add(90,sqrs.toString());
                    totalDataList.add(91,yxrs.toString());
                    totalDataList.add(92,sxrs.toString());

					totalDataList.add(93,csfh.toString());
					totalDataList.add(94,csdj.toString());
					totalDataList.add(95,csbffh.toString());
					totalDataList.add(96,csbf.toString());
					totalDataList.add(97,zyfh.toString());
					totalDataList.add(98,bgzy.toString());
					totalDataList.add(99,zybffh.toString());
					totalDataList.add(100,zybf.toString());
					totalDataList.add(101,dwfh.toString());
					totalDataList.add(102,bgdw.toString());
					totalDataList.add(103,dwbf.toString());
					totalDataList.add(104,jxfh.toString());
					totalDataList.add(105,jxdj.toString());
					totalDataList.add(106,jxbf.toString());
					totalDataList.add(107,zxfh.toString());
					totalDataList.add(108,zxdj.toString());
					totalDataList.add(109,zxbf.toString());
                }

				listEwce.addAll(list1);
				listEwce.addAll(list2);
				listEwce.addAll(list3);
				listEwce.addAll(list4);
				listEwce.addAll(list5);
				listEwce.addAll(list6);

				HSSFWorkbook wb = personRecordService.export(listEwce,totalDataList);
				response.setContentType("application/vnd.ms-excel");
				response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode("终审各批次评审情况统计列表"+ DateUtils.getDate("yyyyMMddHHmmss"), "UTF-8")+".xls");
				OutputStream outputStream = response.getOutputStream();
				wb.write(outputStream);
				outputStream.flush();
				outputStream.close();
//				out.clear();
//				out = pageContext.pushBody();

			}
            return "modules/reportforms/finalSummaryOfStatistics";
        }
        } catch (Exception e) {
			e.printStackTrace();
            addMessage(redirectAttributes, "导出统计列表！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/report/count?repage";
    }

}