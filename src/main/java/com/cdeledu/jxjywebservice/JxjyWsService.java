
package com.cdeledu.jxjywebservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "JxjyWsService", targetNamespace = "http://jxjywebservice.cdeledu.com/", wsdlLocation = "http://jxjycityceshi.cdeledu.com/cdel_jxjy_city/jxjywebservice/webservice?wsdl")
public class JxjyWsService
    extends Service
{

    private final static URL JXJYWSSERVICE_WSDL_LOCATION;
    private final static WebServiceException JXJYWSSERVICE_EXCEPTION;
    private final static QName JXJYWSSERVICE_QNAME = new QName("http://jxjywebservice.cdeledu.com/", "JxjyWsService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://jxjycityceshi.cdeledu.com/cdel_jxjy_city/jxjywebservice/webservice?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        JXJYWSSERVICE_WSDL_LOCATION = url;
        JXJYWSSERVICE_EXCEPTION = e;
    }

    public JxjyWsService() {
        super(__getWsdlLocation(), JXJYWSSERVICE_QNAME);
    }

    public JxjyWsService(WebServiceFeature... features) {
        super(__getWsdlLocation(), JXJYWSSERVICE_QNAME, features);
    }

    public JxjyWsService(URL wsdlLocation) {
        super(wsdlLocation, JXJYWSSERVICE_QNAME);
    }

    public JxjyWsService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, JXJYWSSERVICE_QNAME, features);
    }

    public JxjyWsService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public JxjyWsService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns IJxjyWsService
     */
    @WebEndpoint(name = "JxjyWsServicePort")
    public IJxjyWsService getJxjyWsServicePort() {
        return super.getPort(new QName("http://jxjywebservice.cdeledu.com/", "JxjyWsServicePort"), IJxjyWsService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IJxjyWsService
     */
    @WebEndpoint(name = "JxjyWsServicePort")
    public IJxjyWsService getJxjyWsServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://jxjywebservice.cdeledu.com/", "JxjyWsServicePort"), IJxjyWsService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (JXJYWSSERVICE_EXCEPTION!= null) {
            throw JXJYWSSERVICE_EXCEPTION;
        }
        return JXJYWSSERVICE_WSDL_LOCATION;
    }

}
