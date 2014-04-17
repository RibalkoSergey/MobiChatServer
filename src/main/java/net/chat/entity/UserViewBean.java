package net.chat.entity;

import net.chat.interfaces.IJSONableEntity;
import org.json.JSONObject;

/**
 * Created by sergey on 3/11/14.
 */
public class UserViewBean implements IJSONableEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isOnline;
    private String photo;
    private Long countNotReadMessages;

    public UserViewBean() {

    }

    public UserViewBean(Long id, String firstName, String lastName, String email, boolean isOnlain, String photo, Long countNotReadMessages) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isOnline = isOnlain;
        this.photo = photo;
        this.countNotReadMessages = countNotReadMessages;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getCountNotReadMessages() {
        return countNotReadMessages;
    }

    public void setCountNotReadMessages(Long countNotReadMessages) {
        this.countNotReadMessages = countNotReadMessages;
    }

    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();

        result.put("id", getId());
        result.put("firstName", getFirstName());
        result.put("lastName", getLastName());
        result.put("email", getEmail());
        result.put("isOnline", isOnline());
        // result.put("photo", (getPhoto() == null) ? "" :  getPhoto());
        result.put("photo", "");
        result.put("countNotReadMessages", getCountNotReadMessages());


        return result;
    }
}
