package com.github.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.github.springboot.support.interceptor.OwnPageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Xianyue
 *         类上边添加两个
 * @Configuration注解（该注解类似于spring的配置文件）
 * @MapperScan注解，指定扫描的mapper接口所在的包 在该类中，注入了Environment实例，使用该实例可以去读取类路径下application.properties文件中的内容，读取文件内容的三种方式，见第二章 第二个spring-boot程序
 * 在该类中，使用druid数据源定义了数据源Bean，spring-boot默认使用的是tomcat-jdbc数据源，这是springboot官方推荐的数据源（性能和并发性都很好）
 * 根据数据源生成SqlSessionFactory
 * 值得注意的是，数据源是必须指定的，否则springboot启动不了
 * typeAliasesPackage和mapperLocations不是必须的，如果整个项目不需要用到*Mapper.xml来写SQL的话（即只用注解就可以搞定），那么不需要配
 * @Primary注解：指定在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@Autowire注解报错（一般用于多数据源的情况下）
 */

@Configuration
@MapperScan("com.github.springboot.mapper")
public class MybatisConfig {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource() throws Exception {
        Properties prop = new Properties();
        prop.put("driverClassName", environment.getProperty("jdbc.driverClassName"));
        prop.put("url", environment.getProperty("jdbc.url"));
        prop.put("username", environment.getProperty("jdbc.username"));
        prop.put("password", environment.getProperty("jdbc.password"));
//        prop.put("breakAfterAcquireFailure", true);
        DruidDataSource dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(prop);
        dataSource.setBreakAfterAcquireFailure(true);
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() throws Exception {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);  //指定数据源

        fb.setPlugins(new Interceptor[]{new OwnPageInterceptor()});
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        fb.setTypeAliasesPackage(environment.getProperty("mybatis.typeAliasesPackage")); //指定基包
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(environment.getProperty("mybatis.mapperLocations"))); //指定xml文件位置
        return fb.getObject();
    }
}
