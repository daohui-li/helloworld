package soap.hello.world.logic;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.hello_world_soap_http.PingMeFault;

/**
 * Created by cdl01c on 6/15/2016.
 */
public interface PingMeLogic {
    default void preProcess(Message message) throws Fault {
        System.out.println("PingMe preProcessed");
    }

    default void process() throws PingMeFault {
        System.out.println("PingMe processed");
    }
}
