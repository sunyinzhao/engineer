package com.thinkgem.jeesite.modules.cfca.util;

import cfca.paperless.client.bean.sealIimage.CodeStyle;
import cfca.paperless.client.bean.sealIimage.SealImageCircle;
import cfca.paperless.client.bean.sealIimage.SealImagePentagram;
import cfca.paperless.client.bean.sealIimage.SealImageText;
import cfca.paperless.client.servlet.PaperlessClient;
import cfca.paperless.client.util.PaperlessClientUtil;
import cfca.paperless.util.GUID;
import cfca.paperless.util.IoUtil;
import cfca.paperless.util.JsonUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 自助生成图片接口，使用示例。 
 * 1：可以指定印模类型【圆形（不带五角星）；圆形（带五角星）】。
 * 2：可以指定印模中的各种元素。
 * 3：可以添加业务码及指定其样式。
 * 
 * @author liliwei 
 * @Date 2018-03-06
 */
public class AutoGenerateCircleImage {

    public static void generateCircleImage(String name ,String certificateNum,String filePath) throws Exception{

        long timeStart = System.currentTimeMillis();// 开始时间

        System.out.println("AutoGenerateImageTest05 START");

        PaperlessClient clientBean = new PaperlessClient(PaperlessConfig.assistUrl, 10000, 60000);// 无纸化辅助接口的访问URL

        int width = 225;// 图片宽（单位：像素）
        int height = 225;// 图片高（单位：像素）
        /*
         * 设置圆的样式(必须要写,不能为null)
         */
        SealImageCircle circle = new SealImageCircle();
        circle.setIsMaster(1);
        circle.setCircleWidth(width);
        circle.setCircleHeight(height);
        circle.setCircleColor("ff0000");
        circle.setCircleStroke(5);  //外框宽度
        circle.setCircleAlpha(1);
        circle.setCircleIsValid(true);
        List<SealImageCircle>circleList=new ArrayList<SealImageCircle>();
        circleList.add(circle);

        SealImageCircle circle2 = new SealImageCircle();
        circle2.setIsMaster(2);
        circle2.setCircleHeight(425);
        circle2.setCircleWidth(425);
        circle2.setCircleColor("ff0000");
        circle2.setCircleStroke(2);
        circle2.setCircleAlpha(0.5f);
        circle2.setCircleIsValid(true);
        circleList.add(circle2);

        SealImageCircle circle3 = new SealImageCircle();
        circle3.setIsMaster(2);
        circle3.setCircleHeight(300);
        circle3.setCircleWidth(300);
        circle3.setCircleColor("ff0000");
        circle3.setCircleStroke(3);
        circle3.setCircleAlpha(0.5f);
        circle3.setCircleIsValid(true);
        circleList.add(circle3);
        /*
         * 设置五角星的样式
         */
       /* SealImagePentagram pentagram = new SealImagePentagram();
        pentagram.setPentagramColor("ff0000");
        pentagram.setPentagramRadius(45);
        pentagram.setPentagramAlpha(1);*/
        /*
         * 设置上悬文的内容以及样式
         */
        SealImageText topTextBean = new SealImageText();
        topTextBean.setTextIsShow(true);// 是否显示,默认是显示
        topTextBean.setText("咨询工程师(投资)执业专用章");
        topTextBean.setTextFontFamily("宋体");
        topTextBean.setTextArc(1); // 1是弧形 0是直线
        topTextBean.setTextFontSize(20);
        topTextBean.setTextFontColor("#ff0000");
        topTextBean.setTextDistance(170);   //字的长度
        topTextBean.setTextRadius(208);     //圆弧直径
        topTextBean.setTextAlpha(1);
        topTextBean.setTextPosition(0);
        topTextBean.setTextBold(true);
        /*
         * 设置中间文本1的内容以及样式
         */
        SealImageText centerTextBean1 = new SealImageText();
        centerTextBean1.setTextIsShow(true);// 是否显示,默认是显示
        centerTextBean1.setText(name);
        centerTextBean1.setTextFontFamily("宋体");
        centerTextBean1.setTextArc(0); // 1是弧形 0是直线
        centerTextBean1.setTextFontSize(getFontSize(name));
        centerTextBean1.setTextFontColor("#ff0000");
        centerTextBean1.setTextDistance(178);  //178--140
        centerTextBean1.setTextRadius(getFontLength(name));   //字的长度  2 3 ：140；4 5：150；6 :160 ; 7 :165；8：170 ，9：175；10：180
        centerTextBean1.setTextAlpha(1);
        centerTextBean1.setTextPosition(0);
        centerTextBean1.setTextBold(false);
        /*
         * 设置中间文本2的内容以及样式
         */
        /*SealImageText centerTextBean2 = new SealImageText();
        centerTextBean2.setTextIsShow(true);// 是否显示,默认是显示
        centerTextBean2.setText("www.cfca.com");
        centerTextBean2.setTextFontFamily("宋体");
        centerTextBean2.setTextArc(0); // 1是弧形 0是直线
        centerTextBean2.setTextFontSize(15);
        centerTextBean2.setTextFontColor("#ff0000");
        centerTextBean2.setTextDistance(70);
        centerTextBean2.setTextRadius(250);
        centerTextBean2.setTextAlpha(1);
        centerTextBean2.setTextPosition(1);
        centerTextBean2.setTextBold(true);*/
        /*
         * 设置下悬文的内容以及样式
         */
        SealImageText bottomTextBean = new SealImageText();
        bottomTextBean.setTextIsShow(true);// 是否显示,默认是显示
        bottomTextBean.setText(certificateNum);
        bottomTextBean.setTextFontFamily("宋体");
        bottomTextBean.setTextFontSize(16);
        bottomTextBean.setTextArc(1); // 1是弧形 0是直线
        bottomTextBean.setTextFontColor("#ff0000");
        bottomTextBean.setTextDistance(120);
        bottomTextBean.setTextRadius(205);
        bottomTextBean.setTextAlpha(1);
        bottomTextBean.setTextPosition(1);
        bottomTextBean.setTextBold(true);
        // 设置业务码的内容以及样式
        CodeStyle codeStyle = new CodeStyle();
        codeStyle.setRefContent("1234567890ABC");
        codeStyle.setFontFamily("宋体");
        codeStyle.setFontSize(12);
        codeStyle.setRatioX(0.1f);
        codeStyle.setRatioY(0.5f);
        codeStyle.setCodeColor("#0000ff");

        // 设置DPI，最终图片width=dpi/72*width,height=dpi/72*height
        int dpi=144;
        String imageStrategyXml = createImageStrategyXml(dpi, width, height, circleList, null, topTextBean, centerTextBean1, null, bottomTextBean,null);

        System.out.println(imageStrategyXml);

        // 操作员编码或者机构号
        String operatorCode = PaperlessConfig.operatorCode;
        // 渠道编码，可以为空
        String channelCode = PaperlessConfig.channelCode;

        String systemNo = GUID.generateId();// 业务流水号

        // 取得生成的图片文件数据
        byte[] result = clientBean.autoGenerateCircleImage(systemNo, imageStrategyXml, operatorCode, channelCode);

        String errorRsString = PaperlessClientUtil.isError(result);

        // 处理结果为正常，保存生成的图片文件到本地目录下
        if ("".equals(errorRsString)) {
            System.out.println("AutoGenerateImageTest05 END OK");

            System.out.println(result.length);
            IoUtil.write(filePath, result);

        } else {
            // 处理结果为异常，打印出错误码和错误信息
            System.out.println("AutoGenerateImageTest05 END NG");
            System.out.println(new String(result, "UTF-8"));
        }

        long timeEnd = System.currentTimeMillis();// 结束时间
        System.out.println("##########" + "time used:" + (timeEnd - timeStart) + "##########");
    }

    public static void main(String[] args) throws Exception {
         generateCircleImage("吾拉木·哈迪尔","咨登2320081200136","d:\\AutoGenerateImageTest05.png");
    }

    private static String createImageStrategyXml(int dpi,int width, int height, List<SealImageCircle> circle, SealImagePentagram pentagram, SealImageText topTextBean, SealImageText centerTextBean1,
            SealImageText centerTextBean2, SealImageText bottomTextBean, CodeStyle codeStyle) {
        StringBuilder imageStrategyXML = new StringBuilder();

        imageStrategyXML.append("<Request>");
        imageStrategyXML.append("<DPI>").append(dpi).append("</DPI>");
        imageStrategyXML.append("<ImageWidth>").append(width).append("</ImageWidth>");// 图片宽
        imageStrategyXML.append("<ImageHeight>").append(height).append("</ImageHeight>");// 图片高
        imageStrategyXML.append("<Circle>").append(JsonUtils.writeValueAsString(circle)).append("</Circle>");// 圆
        if (pentagram != null) {
            imageStrategyXML.append("<Pentagram>").append(JsonUtils.writeValueAsString(pentagram)).append("</Pentagram>");// 五角星
        }
        if (topTextBean != null) {
            imageStrategyXML.append("<TopText>").append(JsonUtils.writeValueAsString(topTextBean)).append("</TopText>");// 上悬文
        }
        if (centerTextBean1 != null) {
            imageStrategyXML.append("<CenterTextBean1>").append(JsonUtils.writeValueAsString(centerTextBean1)).append("</CenterTextBean1>");// 中间文字1
        }
        if (centerTextBean2 != null) {
            imageStrategyXML.append("<CenterTextBean2>").append(JsonUtils.writeValueAsString(centerTextBean2)).append("</CenterTextBean2>");// 中间文字2
        }
        if (bottomTextBean != null) {
            imageStrategyXML.append("<BottomTextBean>").append(JsonUtils.writeValueAsString(bottomTextBean)).append("</BottomTextBean>"); // 下悬文
        }
        if (codeStyle != null) {
            imageStrategyXML.append("<BusinessCode>").append(JsonUtils.writeValueAsString(codeStyle)).append("</BusinessCode>");
        }
        imageStrategyXML.append("</Request>");

        return imageStrategyXML.toString();
    }


    private static  int getNameLength (String name){
        if(StringUtils.isBlank(name)){
            return 180;
        }else{
            if(name.length()==1 || name.length()==2 || name.length()==3 ){
                return 50;
            }else if(name.length()<10){
                return 16;
            }else{
                return 14;
            }
        }
    }

    /**
     * 计算印章上的字号
     * @param name
     * @return
     */
    private static  int getFontSize(String name){
        if(StringUtils.isBlank(name)){
            return 18;
        }else{
            if(name.length()<4){
                return 20;
            }else if(name.length()<8){
                return 18;
            }else if(name.length()<10){
                return 16;
            }else{
                return 14;
            }
        }
    }


    /**
     * 计算印章上人名的长度
     * @param name
     * @return
     */
    private static  int getFontLength(String name){
        if(StringUtils.isBlank(name)){
            return 140;
        }else {
            //字的长度  2 3 ：140；4 5：150；6 :160 ; 7 :165；8：170 ，9：175；10：180
            if (name.length() < 4) {
                return 140;
            } else if (name.length() < 5) {
                return 145;
            }else if (name.length() < 6) {
                return 155;
            } else if (name.length() < 7) {
                return 160;
            } else if (name.length() < 8) {
                return 165;
            } else if (name.length() < 9) {
                return 170;
            } else if (name.length() < 10) {
                return 175;
            } else  {
                return 180;
            }
        }
    }
}
