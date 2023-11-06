package io.crescent.moonstar.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

@Alias("UserEntity")
@Data
@Accessors(chain = true)
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 3748548454201468378L;
    private long userId;
    private String userName;
    private String password;

    public UserEntity() {
    }

    public UserEntity(long userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public UserEntity setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
