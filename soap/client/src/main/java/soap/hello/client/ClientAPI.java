package soap.hello.client;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.hello_world_soap_http.Greeter;
import org.apache.hello_world_soap_http.HelloService;
import org.apache.hello_world_soap_http.PingMeFault;
import org.apache.hello_world_soap_http.types.GreetMe;
import org.apache.hello_world_soap_http.types.GreetMeResponse;
import org.apache.hello_world_soap_http.types.PingMe;

import org.apache.cxf.endpoint.Client;

import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

public class ClientAPI {
    private static String URL_FORMAT = "%s://%s:%d" + "/hello";

    private final Greeter greeter;

    public ClientAPI(String host, int port, KeystoreConfig config) {
        this(host, port, config, null, null);
    }

    public ClientAPI(String host, int port, KeystoreConfig config, String name, String pswd) {
        String address = String.format(URL_FORMAT, (config == null) ? "http" : "https",
                host, port);
        Service service = Service.create(HelloService.WSDL_LOCATION, HelloService.SERVICE);
        greeter = getPort(service, address, Greeter.class, name, pswd);
        if (config != null) {
            securePort(greeter, config);
        }
    }

    public void callPingMe() throws PingMeFault {
        greeter.pingMe(new PingMe());
    }

    public String callGreetMe(String text) {
        GreetMeResponse response = greeter.greetMe(new GreetMe(text));
        return response.getResponseType();
    }

    private<T> T getPort(Service service, String address, Class<T> cls) {
        T port = service.getPort(cls);
        BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
        return port;
    }

    private<T> T getPort(Service service, String address, Class<T> cls, String name, String pswd) {
        T port = service.getPort(cls);
        BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
        if (name != null) {
            bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, name);
        }
        if (pswd != null) {
            bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, pswd);
        }
        return port;
    }

    private void securePort(Greeter sh, KeystoreConfig config) {
        KeyStore keyStore;
        try (FileInputStream fis = new FileInputStream(new File(config.getPath()))) {
            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(fis, config.getPassword());

            TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustFactory.init(keyStore);

            TLSClientParameters tlsClientParameters = new TLSClientParameters();
            tlsClientParameters.setKeyManagers(new KeyManager[]{new KeyManager() {}});
            tlsClientParameters.setTrustManagers(trustFactory.getTrustManagers());
            tlsClientParameters.setDisableCNCheck(true);

            Client client = ClientProxy.getClient(sh);
            HTTPConduit conduit = (HTTPConduit) client.getConduit();
            conduit.setTlsClientParameters(tlsClientParameters);
        } catch (Exception e) {
            // TODO
        }

    }
}
