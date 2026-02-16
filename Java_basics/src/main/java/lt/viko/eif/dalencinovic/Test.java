package lt.viko.eif.dalencinovic;

public class Test extends Ultratest{
    private String name;
    private String username;
    private int password;

    public Test( String name, String username, int password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Test() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }
}
