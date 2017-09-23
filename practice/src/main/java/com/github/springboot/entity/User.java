package com.github.springboot.entity;

import com.github.springboot.util.PageInfo;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

@Alias("User")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User extends PageInfo implements Serializable{
    private int    userId;
    private String userName;
    private String password;

    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
}
