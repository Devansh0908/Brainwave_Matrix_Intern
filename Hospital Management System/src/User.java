public class User {
    private String username;
    private String password;
    private String role; // Role can be Admin, Doctor, Ward Staff, Receptionist, Pharmacist

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}
