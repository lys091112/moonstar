package com.github.springboot.domain;

import com.github.springboot.util.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * @author Xianyue
 */
@Alias("User")
@Getter
@Setter
@NoArgsConstructor
public class User extends PageInfo{
    private int    userId;
    private String userName;
    private String password;

    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
}
