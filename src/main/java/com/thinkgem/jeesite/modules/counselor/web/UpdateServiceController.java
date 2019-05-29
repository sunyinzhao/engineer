package com.thinkgem.jeesite.modules.counselor.web;


import com.thinkgem.jeesite.common.utils.AESUtil;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.counselor.entity.UpdateService;
import com.thinkgem.jeesite.modules.counselor.service.UpdateServiceService;
import com.thinkgem.jeesite.modules.enterprise.entity.EnterpriseWorkers;
import com.thinkgem.jeesite.modules.enterprise.service.EnterpriseWorkersService;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.cdeledu.jxjywebservice.IJxjyWsService;
//import com.cdeledu.jxjywebservice.JxjyWsService;
import com.demo.client.JxjyWsService;
import com.demo.client.IJxjyWsService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

//更新数据调用接口
@Controller
@RequestMapping(value = "${adminPath}/counselor/updateService")
public class UpdateServiceController extends BaseController {
    @Autowired
    private UpdateServiceService updateServiceService;
    @Autowired
    private EnterpriseWorkersService enterpriseWorkersService;

    //一、说明：同步学员数据

    @RequestMapping("updateWorker")
    private String updateWorker() throws Exception {
        //1.准备数据
        //1.1.查找参数,找到最大的时间
        Date resultDate = updateServiceService.getDate();
        //1.2.根据时间查询enterprise表里的数据
        List<EnterpriseWorkers> list =  enterpriseWorkersService.findUpdateService(resultDate);
       
        Date date = new Date();
        for(EnterpriseWorkers e : list){
        	 StringBuffer sb = new StringBuffer();
            sb.append("<UserInfos>");
            sb.append("<UserInfo>");
            sb.append("<Username>"+e.getName()+"</Username>");
            sb.append("<Sex>"+e.getSex()+"</Sex>");
            sb.append("<Uuid>"+e.getId()+"</Uuid>");
            sb.append("<IdCard>"+e.getCertificatesNum()+"</IdCard>");
            sb.append("<QuaCardID>"+e.getProfessioncardNum()+"</QuaCardID>");
            sb.append("<ExamCardID>"+e.getRegisterCertificateNum()+"</ExamCardID>");
            //如果是空的话,需要设置为111111
            if(e.getAreaId()==null||e.getAreaId().equals("")){
                e.setAreaId("111111");
            }
            sb.append("<AreaID>"+e.getAreaId()+"</AreaID>");
            sb.append("<UnitName>"+e.getCompanyName()+"</UnitName>");
            sb.append("<MobilePhone>"+e.getCompanyTel()+"</MobilePhone>");
            sb.append("<Email>"+e.getEmail()+"</Email>");
            sb.append("</UserInfo>");
            sb.append("</UserInfos>");
            
            
            String send = sb.toString();
            //2.调用接口
            //调用接口之前先取出当前时间
            
            String key = "ae3dea013432d2fc";
            //xml进行加密
            String xml = AESUtil.AES_Encrypt(key, send);

            //获取接口,传递数据,获得数据方法
            //todo
            //1.右键项目, 点击webService .
            //2 .webServicePlatForm 选择 glass
            //3. 构建的文件放在指定目录下
            //4. 调用接口
            IJxjyWsService port = new JxjyWsService().getJxjyWsServicePort();
            String resultXml = port.qgzxgcsSyncUserInfo(key,xml);
            //3.接口返回数据进行解析
            String result = result(resultXml, "1");
            
            //result 为回调是否同步成功
            if(result!=null&&result.equals("200")){//1. 同步成功, 需要往数据库里添加一条现在的时间
             
            }else if(result.equals("201")){
                //同步失败,错误.return
            	return null;
            }
            System.out.println(resultXml);
        }
            UpdateService updateService = new UpdateService();
//            updateService.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            updateService.setId("123456");
            //把调用接口之前产生的时间,置入现在的数据库里.
            updateService.setSourceUpdate(date);
            updateServiceService.save(updateService);

        return null;
    }




    //2. 学时同步接口
    @RequestMapping("queryStudy")
    private String updateStudy(String idCard,String uuid,String pageNo,String pageSize) throws Exception {
        ///getQualifiedForCountyCounselor?idCard=12&Uuid=123&key=ae3dea013432d2fc&pageNo=1&pageSize=1
        //设置值
        String key = "ae3dea013432d2fc";
        pageNo = "1";
        pageSize = "1";
        //调用接口后返回的是xml
        /*String url = "http://jxjycity.cdeledu.com/cdel_jxjy_city/jxjywebservice/webservice?wsdl/getQualifiedForCountyCounselor?" +
                "idCard="+idCard+"&Uuid="+uuid+"&key="+key+"&pageNo="+pageNo+"&pageSize="+pageSize;
        String xml = serviceUtil(url);*/


        ////////////////////***/////////////////////////
        //          todo
                  IJxjyWsService port = new JxjyWsService().getJxjyWsServicePort();
                  String xml = port.getQualifiedForCountyCounselor(idCard,uuid,key,pageNo,pageSize);
        //解密.
        ////////////////////***/////////////////////////
        //String xml = null;

        String resultXml = AESUtil.AES_Decrypt(key, xml);

        String result = result(resultXml, "2");


        if(result!=null&&result.equals("200")){
            //通过
        }else if(result!=null&&result.equals("201")){
            //不通过
        }else if(result!=null&&result.equals("202")){
            //没有查找到数据
        }else if(result!=null&&result.equals("203")){
            //信息错误
        }

        return null;
    }


    //这个方法,上面两个接口调用.
    // 第一个接口调用 返回的是两种结果. "200" 同步成功.  ."错误信息"

    // 第二个接口调用 返回的是四种结果. "200" 通过 ."201" 不通过 "202" 没有查找到数据  "203" 错误信息

    @RequestMapping(value = "testResult")
    //查看回调的xml 是否为
    private String result(String xml,String type){//type 用于区分两个接口的类型
        try {
            Document doc = DocumentHelper.parseText(xml);
            //指向根节点
            Element root = doc.getRootElement();
            List<Element> elements = root.elements();
            if(type.equals("1")){
                for (Element element : elements) {
                    String name = element.getName();
                    if(name.equals("Code")){
                        String text = element.getText();
                        if(text.equals("1")){
                           return "200";
                        }else{
                            return "201";
                        }
                    }
                }
            }else if(type.equals("2")){
                for (Element element : elements) {
                    String name = element.getName();
                    if(name.equals("Code")){
                        String text = element.getText();
                        if(text.equals("0")){
                            //无合规数据
                            return "202";
                        }else{
                            //传递信息错误
                            return "203";
                        }
                    }
                    if(name.equals("UserInfo")){
                        List<Element> e1s = element.elements();
                        for(Element e1 : e1s){
                            String name1 = e1.getName();
                            if(name1.equals("isPass")){
                                String text = e1.getText();
                                if(text.equals("1")){
                                //通过
                                    return "200";
                                }else {
                                   //不通过
                                    return "201";
                                }

                            }
                        }
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private String serviceUtil(String path){
//        //传递url 返回String
//        StringBuffer sb = new StringBuffer();
//        try {
//            URL url = new URL(path);
//            //打开和url之间的连接
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            PrintWriter out = null;
//            //请求方式
////          conn.setRequestMethod("POST");
////           //设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
//            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            //获取URLConnection对象对应的输出流
//            out = new PrintWriter(conn.getOutputStream());
//            //发送请求参数即数据
//            //缓冲数据
//            out.flush();
//            //获取URLConnection对象对应的输入流
//            InputStream is = conn.getInputStream();
//            //构造一个字符流缓存
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            String str = "";
//
//            while ((str = br.readLine()) != null) {
//                sb.append(str);
//            }
//            //关闭流
//            is.close();
//            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
//            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
//            conn.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sb.toString();
//    }
}
