package soap.hello.world.logic;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.hello_world_soap_http.PingMeFault;

/**
 * Created by cdl01c on 6/15/2016.
 */
public class PingMeLogicImpl implements PingMeLogic {
    private int count =0;

    @Override
    public void preProcess(Message message) throws Fault {
        count++;
        if ( (count % 5) == 0) {
            throw new Fault(new Exception(message.getId()));
        }
    }

    @Override
    public void process() throws PingMeFault {
        if ( (count % 3) == 0) {
            throw new PingMeFault("exception raised from PingMeLogicImpl");
        }
    }
}
