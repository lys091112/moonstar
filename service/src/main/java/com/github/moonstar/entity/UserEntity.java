package com.github.moonstar.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

@Alias("UserEntity")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 3748548454201468378L;
    private long userId;
    private String userName;
    private String password;

    public UserEntity(long userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
}
