package soap.hello.world.endpoints;

import org.apache.hello_world_soap_http.Greeter;
import org.apache.hello_world_soap_http.PingMeFault;
import org.apache.hello_world_soap_http.types.GreetMe;
import org.apache.hello_world_soap_http.types.GreetMeOneWay;
import org.apache.hello_world_soap_http.types.GreetMeResponse;
import org.apache.hello_world_soap_http.types.ObjectFactory;
import org.apache.hello_world_soap_http.types.PingMe;
import org.apache.hello_world_soap_http.types.PingMeResponse;
import org.apache.hello_world_soap_http.types.SayHi;
import org.apache.hello_world_soap_http.types.SayHiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soap.hello.world.logic.HelloBehavior;
import soap.hello.world.logic.PingMeLogic;

import javax.jws.WebParam;

@Service
public class GreeterImpl implements Greeter {
    private static ObjectFactory objectFactory = new ObjectFactory();

    @Autowired
    private HelloBehavior behavior;

    @Autowired
    private PingMeLogic pingMeLogic;

    private static final String GREETME_FORMAT = "Hello, %s";

    @Override
    public PingMeResponse pingMe(@WebParam(partName = "in", name = "pingMe", targetNamespace = "http://apache.org/hello_world_soap_http/types") PingMe pingMe) throws PingMeFault {
        pingMeLogic.process();
        return objectFactory.createPingMeResponse();
    }

    @Override
    public GreetMeResponse greetMe(@WebParam(partName = "in", name = "greetMe", targetNamespace = "http://apache.org/hello_world_soap_http/types") GreetMe greetMe) {
        String type = greetMe.getRequestType();
        GreetMeResponse response = objectFactory.createGreetMeResponse();
        response.setResponseType(String.format(GREETME_FORMAT, type));
        return response;
    }

    @Override
    public void greetMeOneWay(@WebParam(partName = "in", name = "greetMeOneWay", targetNamespace = "http://apache.org/hello_world_soap_http/types") GreetMeOneWay in) {

    }

    @Override
    public SayHiResponse sayHi(@WebParam(partName = "in", name = "sayHi", targetNamespace = "http://apache.org/hello_world_soap_http/types") SayHi in) {
        return null;
    }
}
