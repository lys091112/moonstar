package io.crescent.moonstar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.crescent.moonstar.common.RequestHelper;
import io.crescent.moonstar.dto.ResultMessage;
import io.crescent.moonstar.entity.UserEntity;
import io.crescent.moonstar.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 */

@RestController
@RequestMapping(value = "api/v1")
@Validated
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "query/user/{userId}")
    public UserEntity getUserInfo(@PathVariable(value = "userId") String userId) {
        if (StringUtils.isEmpty(userId) || !StringUtils.isNumeric(userId)) {
            return null;
        }
        //
        return new UserEntity().setUserName("test");
    }


    @GetMapping(value = "add/user")
    public UserEntity addUser(@RequestParam(value = "username") String userName,
        @RequestParam(value = "password") @Length(min = 6, max = 18) String password) {
        return userService.addUser(userName, password);
    }

    @PostMapping(value = "/update/user")
    public ResultMessage<Boolean> updateUser(HttpServletRequest request) {
        try {
            UserEntity user = RequestHelper.getFromJson(request, UserEntity.class);
            return ResultMessage.success(userService.updateUser(user));
        } catch (Exception e) {
            LOGGER.error("update UserEntity error! e={}", e);
            return ResultMessage.failed(201, "system error!", false);
        }
    }

    @PostMapping(value = "/testTransaction")
    public int testTransaction(HttpServletRequest request, @RequestBody String str) throws IOException {
        UserEntity user = new ObjectMapper().readValue(str, UserEntity.class);
        return userService.testTransaction(user);
    }

}
