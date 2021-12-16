package com.codeXie.mapper;

import com.codeXie.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select * from user where uname=#{uname} and pwd=#{pwd}")
    User selUserMapper(@Param("uname") String uname,@Param("pwd") String pwd);
}
