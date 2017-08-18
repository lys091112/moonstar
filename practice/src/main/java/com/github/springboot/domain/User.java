package com.github.springboot.domain;

import com.github.springboot.util.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * @author Xianyue
 */
@Alias("User")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User extends PageInfo{
    private int    userId;
    private String userName;
    private String password;
}
