# Gradle 使用记录

## 1 基础命令

-  gradlew -q ${moduleName}:dependencies 查看依赖树

## 2. 工程结合使用

### 2.1 使用Junit测试单元类

1. 配置文件变更
```gradle
// 添加test声明
test {
    useJUnitPlatform();
}
```

2. setting-> gradle -> run tests Using:"Idea"