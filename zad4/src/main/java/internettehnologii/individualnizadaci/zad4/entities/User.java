package internettehnologii.individualnizadaci.zad4.entities;

public class User {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role.toUpperCase();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role.toUpperCase();
    }

    public boolean match(String username,String password){
        return username.equals(this.username) && password.equals(this.password);
    }
}
