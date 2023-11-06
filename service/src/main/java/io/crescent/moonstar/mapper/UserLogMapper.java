package io.crescent.moonstar.mapper;

import io.crescent.moonstar.entity.UserLogEntity;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserLogMapper {

    @Insert("insert into user_log (user_id, operator_type) values (#{userId},#{operatorType})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(UserLogEntity userLog);

    @Select("select * from user where userid = #{userId}")
    List<UserLogEntity> query(@Param(value = "userId") int userId);

}
