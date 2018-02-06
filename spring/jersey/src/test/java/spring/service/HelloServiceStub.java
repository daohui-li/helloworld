package spring.service;

public class HelloServiceStub implements HelloService {
    @Override
    public String hello(String name) {
        return "Hello " + name + " -- from stub";
    }
}
