package net.chat.entity;

import net.chat.interfaces.IJSONableEntity;
import org.json.JSONObject;

import javax.persistence.*;

/**
 * Created by sergey on 2/3/14.
 */
@Entity
@Table(name = "USERS")
public class User implements IJSONableEntity{

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "IS_ONLINE")
    private boolean isOnline;

    @Column(name = "PHOTO")
    private String photo;

    public User() {

    }

    public User(String firstName, String lastName, String login, String password, String email, boolean isOnlain, String photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.isOnline = isOnlain;
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIsOnlain() {
        return isOnline;
    }

    public void setIsOnlain(boolean isOnlain) {
        this.isOnline = isOnlain;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnlain) {
        this.isOnline = isOnlain;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();

        result.put("id", getId());
        result.put("firstName", getFirstName());
        result.put("lastName", getLastName());
        result.put("login", getLogin());
        result.put("password", getPassword());
        result.put("email", getEmail());
        result.put("isOnline", isOnline());

        return result;
    }
}
