package net.chat.entity;

import net.chat.interfaces.IJSONableEntity;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sergey on 3/31/14.
 */
@Entity
@Table(name = "MESSAGES")
public class Message implements IJSONableEntity{

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @Column(name = "FROM_USER_ID")
    private Long fromUserId;

    @Column(name = "TO_USER_ID")
    private Long toUserId;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "DATE_MESSAGE")
    private Date dateMessage;

    @Column(name = "IS_READ")
    private Boolean isRead;

    @Transient
    private String fromUserName;

    @Transient
    private String toUserName;

    public Message() {

    }

    public Message(Long fromUserId, Long toUserId, String message, Date dateMessage, Boolean isRead, String fromUserName, String toUserName) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
        this.dateMessage = dateMessage;
        this.isRead = isRead;
        this.fromUserName = fromUserName;
        this.toUserName = toUserName;
    }

    public Message(Long fromUserId, Long toUserId, String message, Date dateMessage, Boolean isRead) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
        this.dateMessage = dateMessage;
        this.isRead = isRead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = dateMessage;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();

        result.put("id", getId());
        result.put("fromUserId", getFromUserId());
        result.put("fromUserName", getFromUserName());
        result.put("toUserId", getToUserId());
        result.put("toUserName", getToUserName());
        result.put("message", getMessage());
        result.put("dateMessage", getDateMessage());
        result.put("isRead", getIsRead());

        return result;
    }
}
