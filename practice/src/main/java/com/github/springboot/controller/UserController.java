package com.github.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.github.springboot.config.UserDemo;
import com.github.springboot.entity.User;
import com.github.springboot.support.exception.TestException;
import com.github.springboot.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author Xianyue
 * @Api：用在类上，说明该类的作用
 * @ApiOperation：用在方法上，说明方法的作用
 * @ApiImplicitParams：用在方法上包含一组参数说明
 * @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面 paramType：参数放在哪个地方
 * header-->请求参数的获取：@RequestHeader * query-->请求参数的获取：@RequestParam
 * path（用于restful接口）-->请求参数的获取：@PathVariable
 * body（不常用）
 * form（不常用）
 * name：参数名
 * dataType：参数类型
 * required：参数是否必须传
 * value：参数的意思
 * defaultValue：参数的默认值
 * @ApiResponses：用于表示一组响应
 * @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息 code：数字，例如400
 * message：信息，例如"请求参数没填好"
 * response：抛出异常的类
 * @ApiModel：描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，请求参数无法使用@ApiImplicitParam注解进行描述的时候）
 * @ApiModelProperty：描述一个model的属性
 */

@RestController  //Controller + ResponseBody + json转化
@RequestMapping(value = "api/v1")
@Validated
public class UserController {

    @Autowired
    UserDemo userDemo;

    @Autowired
    private UserService userService;

    private final static String prefix = "PREFIX";

    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userName", required = true, dataType = "String", value = "用户名", defaultValue = "yue"),
            @ApiImplicitParam(paramType = "query", name = "password", required = true, dataType = "String", value = "用户密码", defaultValue = "123456")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "service error")
    })

    /**
     * 通过Cacheable注解，进行redis缓存
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    @Cacheable(value = "foo", key = "#userName")
    public List<User> getUserInfo(@RequestParam(value = "userName", required = false) String userName,
        @RequestParam(value = "password", required = false) String password) {
        return userService.cacheTest();
    }

    @RequestMapping(value = "/changeUserInfo", method = RequestMethod.GET)
    public UserDemo changeUserInfo() {
        userDemo.setId(222);
        return userDemo;
    }

    @RequestMapping(value = "/getUserInfo/noCache", method = RequestMethod.GET)
    public UserDemo getUserInfoNoCache(@RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "password",
            required = false) String password) {
        return userDemo;
    }


    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public User addUser(@RequestParam(value = "username") String userName,
        @RequestParam(value = "password") @Length(min = 10, max = 30) String password) {
        return userService.addUser(userName, password);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public int updateUser(@RequestParam(value = "userId") int userId, @RequestParam(value = "username") String userName, @RequestParam(value = "password") String password) {
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setPassword(password);
        return userService.updateUser(user);
    }

    @ApiOperation(value = "事务测试", httpMethod = "POST")
    @RequestMapping(value = "/testTransaction", method = RequestMethod.POST)
    public int testTransaction(HttpServletRequest request, @RequestBody String str) throws IOException {
        User user = JSON.parseObject(str, User.class);
        return userService.testTransaction(user);
    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public List<User> getUsers(HttpServletRequest request, @RequestParam(value = "pageIndex") int pageIndex) {
        return userService.getUsersByPage(pageIndex);
    }

    @GetMapping("/exception")
    public String excpetionTest() {
        throw new RuntimeException("this is a demo exception");
    }

    @GetMapping("/ownexception")
    public String ownExcpetionTest() {
        throw new TestException("my own exception");
    }
}
