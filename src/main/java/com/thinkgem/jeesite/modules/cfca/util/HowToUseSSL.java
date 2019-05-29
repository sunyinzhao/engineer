package com.thinkgem.jeesite.modules.cfca.util;

import cfca.paperless.client.servlet.PaperlessClient;

public class HowToUseSSL {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // 1，使用单向SSL连接时，按照如下方式构造客户端对象
        PaperlessClient oneWayAuthClientBean = new PaperlessClient(PaperlessCloudConfig.url, 10000, 60000);
        try {
            oneWayAuthClientBean.initSSLContext(null, null, PaperlessCloudConfig.trustStorePath, PaperlessCloudConfig.trustStorePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // TODO process business logic

        // 如下的签名数据需要记录保存
        if (oneWayAuthClientBean.getHttpsClient() != null) {
            System.out.println("p7SignData:" + new String(oneWayAuthClientBean.getHttpsClient().getP7SignData()));
        }

        // 2，使用双向SSL连接时，按照如下方式构造客户端对象
        PaperlessClient twoWayAuthClientBean = new PaperlessClient(PaperlessCloudConfig.url, 10000, 60000);
        try {
            twoWayAuthClientBean.initSSLContext(PaperlessCloudConfig.keyStorePath, PaperlessCloudConfig.keyStorePassword, PaperlessCloudConfig.trustStorePath,
                    PaperlessCloudConfig.trustStorePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // TODO process business logic

        // 如下的签名数据需要记录保存
        if (twoWayAuthClientBean.getHttpsClient() != null) {
            System.out.println("p7SignData:" + new String(twoWayAuthClientBean.getHttpsClient().getP7SignData()));
        }

    }

}
