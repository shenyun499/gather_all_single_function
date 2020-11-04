package entity;

/**
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-08-14
 */
public class User {

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "entity.User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
