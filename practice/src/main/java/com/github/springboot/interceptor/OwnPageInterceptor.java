package com.github.springboot.interceptor;

import com.github.springboot.util.PageInfo;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Xianyue
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
//@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}))
public class OwnPageInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("------------------------------> ---------------------");
        StatementHandler handler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(handler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        BoundSql boundSql = handler.getBoundSql();
        Object param = (Object) boundSql.getParameterObject();
        String sql = boundSql.getSql();
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        String sqlId = mappedStatement.getId();
        if (sqlId.contains("ByPage") && param instanceof PageInfo) {
            PageInfo pageInfo = (PageInfo) param;
            String countSql = "select count(*) from (" + sql + ") as total";
            //注解里面的参数
            Connection connection = (Connection) invocation.getArgs()[0];
            PreparedStatement countStatement = connection.prepareStatement(countSql);
            ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
            parameterHandler.setParameters(countStatement);
            ResultSet rs = countStatement.executeQuery();
            if (rs.next()) {
                pageInfo.setTotalCount(rs.getInt(1));
            }
            String pageSql = sql + "limit " + pageInfo.startIndex() + " " + pageInfo.getPageSize();
            metaObject.setValue("delegate.boundSql.sql", pageSql);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
