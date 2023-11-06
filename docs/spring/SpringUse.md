# Spring use

## 1. springboot 注解

### 1.1 @DependsOn
  用于设置初始化的先后顺序，保证依赖的类先初始化


## 2. 代码分析

### 2.1 切面拦截器实现
DefaultAdvisorChainFactory

## 2. 使用记录

### 2.1 Spring 参数校验
使用MethodValidationPostProcessor对数据参数进行校验，依赖org.hibernate:hibernate-validator
使用方式：

You need add @Validated to your class like this
```java
  @RestController
  @Validated
  class Controller {
    // ... 
  }
```
add this bean to your context:

```java
@Configuration
public class Demo {
 @Bean
 public MethodValidationPostProcessor methodValidationPostProcessor() {
   return new MethodValidationPostProcessor();
 }
    
}
```

常用参数验证注解有： Min，Max， Length, Size... 等等， Validated是对Valid的一个封装

 handle exception:
```java
@ControllerAdvice
@Component
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(MethodArgumentNotValidException exception) {
        return error(exception.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList()));
    }


    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(ConstraintViolationException exception) {
s       return error(exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()));
    }

    private Map error(Object message) {
        return Collections.singletonMap("error", message);
    }
}
```

#### Swagger
```$xslt
- @Api：用在类上，说明该类的作用
- @ApiOperation：用在方法上，说明方法的作用
- @ApiImplicitParams：用在方法上包含一组参数说明
- @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面 paramType：参数放在哪个地方
    header-->请求参数的获取：@RequestHeader * query-->请求参数的获取：@RequestParam
    path（用于restful接口）-->请求参数的获取：@PathVariable
    body（不常用）
    form（不常用）
    name：参数名
    dataType：参数类型
    required：参数是否必须传
    value：参数的意思
    defaultValue：参数的默认值
- @ApiResponses：用于表示一组响应
- @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息 code：数字，例如400
- message：信息，例如"请求参数没填好"
- response：抛出异常的类
- @ApiModel：描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，
     请求参数无法使用@ApiImplicitParam注解进行描述的时候）
- @ApiModelProperty：描述一个model的属性
```

