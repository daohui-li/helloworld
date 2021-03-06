package org.apache.hello_world_soap_http;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.4
 * 2017-02-23T23:37:22.523-06:00
 * Generated source version: 3.0.4
 * 
 */
@WebServiceClient(name = "HelloService", 
                  wsdlLocation = "classpath:wsdl/Hello.wsdl",
                  targetNamespace = "http://apache.org/hello_world_soap_http") 
public class HelloService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://apache.org/hello_world_soap_http", "HelloService");
    public final static QName HelloPort = new QName("http://apache.org/hello_world_soap_http", "HelloPort");
    static {
        URL url = HelloService.class.getClassLoader().getResource("wsdl/Hello.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(HelloService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/Hello.wsdl");
        }       
        WSDL_LOCATION = url;   
    }

    public HelloService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public HelloService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HelloService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public HelloService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public HelloService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public HelloService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    

    /**
     *
     * @return
     *     returns Greeter
     */
    @WebEndpoint(name = "HelloPort")
    public Greeter getHelloPort() {
        return super.getPort(HelloPort, Greeter.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Greeter
     */
    @WebEndpoint(name = "HelloPort")
    public Greeter getHelloPort(WebServiceFeature... features) {
        return super.getPort(HelloPort, Greeter.class, features);
    }

}
