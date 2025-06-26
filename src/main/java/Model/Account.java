package Model;

import java.sql.Timestamp;

/**
 *
 * @author Le Tan Kim
 */
public class Account {

    private int id;
    private String fullname;
    private String email;
    private String phone;
    private String username;
    private String password;
    private int role;
    private Timestamp date;
    private int status;
    private String avatar;

    public Account() {
    }

    public Account(int id, String fullname, String email, String phone, String username, String password, int role, Timestamp date, int status) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.role = role;
        this.date = date;
        this.status = status;
    }

    public Account(int id, String fullname, String email, String phone, String username, String password, int role, Timestamp date, int status, String avatar) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.role = role;
        this.date = date;
        this.status = status;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
