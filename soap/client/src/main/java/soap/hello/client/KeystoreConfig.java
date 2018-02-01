package soap.hello.client;

public class KeystoreConfig {
    private final String path;
    private final String password;

    public KeystoreConfig(String path, String password) {
        this.path = path;
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public char[] getPassword() {
        return password.toCharArray();
    }
}
