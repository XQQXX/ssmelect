package com.elect.mapper;


import com.elect.pojo.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao {
    /**
     * 通过用户名密码查找管理员
     * @param name
     * @param password
     * @return
     */
    @Select("select * from d_admin where name=#{name} and password=#{password}")
    Admin findByNameAndPassword(@Param("name") String name, @Param("password") String password) throws Exception;
}
