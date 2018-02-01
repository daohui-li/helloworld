package soap.hello.world.interceptors;

import org.apache.cxf.binding.soap.interceptor.SoapHeaderInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import soap.hello.world.logic.HelloBehavior;
import soap.hello.world.logic.PingMeLogic;

@Component
public class RequestInterceptor extends SoapHeaderInterceptor {
    private static final String PINGME = "pingMe";

    @Autowired
    private HelloBehavior behavior;

    @Autowired
    private PingMeLogic pingMeLogic;

    @Override
    public void handleMessage(Message m) throws Fault {
        MessageInfo mi = (MessageInfo) m.get("org.apache.cxf.service.model.MessageInfo");
        String methodName = mi.getName().getLocalPart();
        if (PINGME.equals(methodName)) {
            pingMeLogic.preProcess(m);
        }
        super.handleMessage(m);

    }
}
