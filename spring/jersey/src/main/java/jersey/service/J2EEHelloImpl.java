package jersey.service;

import org.jvnet.hk2.annotations.Service;

import javax.inject.Named;

@Service
@Named("jerseyBean")
public class J2EEHelloImpl implements J2EEHello {
    @Override
    public String sayHello(String name) {
        return "Hello " + name + " - from J2EE";
    }
}
