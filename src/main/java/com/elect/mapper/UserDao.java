package com.elect.mapper;


import com.elect.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    /**
     * 通过Email查找用户
     * @param email
     * @return
     * @throws Exception
     */
    @Select("select * from d_user where email= #{email}")
    User findByEmail(String email) throws Exception;

    /**
     * 添加用户信息
     * @param user
     * @throws Exception
     */
    @Insert("insert into d_user(email,nickname,password,email_verify_code) values(#{email},#{nickname},#{password},#{email_verify_code})")
    void addUser(User user) throws Exception;

    /**
     * 更新用户详细信息
     * @param user
     * @throws Exception
     */
    @Update("update d_user set is_email_verify=#{is_email_verify},last_login_time=#{is_email_verify},last_login_ip=#{last_login_ip},email_verify_code=#{email_verify_code} where email=#{email}")
    void updateUser(User user) throws Exception;

    /**
     * 查询用户是否存在
     * @param user
     * @return
     * @throws Exception
     */
    @Select("select * from d_user where email=#{email} and password=#{password}")
    User findUser(User user) throws Exception;
}
