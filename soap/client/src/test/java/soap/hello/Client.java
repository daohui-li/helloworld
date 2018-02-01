package soap.hello;

import org.apache.hello_world_soap_http.PingMeFault;
import soap.hello.client.ClientAPI;
import soap.hello.client.KeystoreConfig;

public class Client {
    ClientAPI api;
    private Client(boolean isSecure) {
        int port = 9000;
        KeystoreConfig keystoreConfig = null;
        if (isSecure) {
            port = 9001;
            keystoreConfig = new KeystoreConfig("truststore", "secret");
        }
        api = new ClientAPI("localhost", port, keystoreConfig, "daohui", "secret123");
    }

    public static void main(String ... args) throws PingMeFault {
        Client client = new Client(false);

        client.checkPingMe();

        client.checkGreetMe(args.length>0 ? args[0] : "world");
    }

    private void checkPingMe() {
        try {
            api.callPingMe();
            System.out.println("Server responds the pingMe request");
        } catch (PingMeFault pingMeFault) {
            System.err.println("Server generated a pingMeFault: " + pingMeFault.getMessage());
        }
    }

    private void checkGreetMe(String text) {
        System.out.println("Server response: " + api.callGreetMe(text));
    }
}
