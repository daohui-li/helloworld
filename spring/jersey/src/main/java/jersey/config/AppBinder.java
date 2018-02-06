package jersey.config;

import jersey.service.J2EEHello;
import jersey.service.J2EEHelloImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class AppBinder extends AbstractBinder {
    @Override
    protected void configure() {
        // bind the implied class to the declared class/interface
        bind(J2EEHelloImpl.class).to(J2EEHello.class);
    }
}
