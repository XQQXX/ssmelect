package com.elect.mapper;

import com.elect.pojo.Receive_address;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao {
    /**
     * 添加地址信息
     * @param receive_address
     * @throws Exception
     */
    @Insert("insert into d_receive_address(user_id,receive_name,full_address,postal_code,mobile,phone) values(#{user_id},#{receive_name},#{full_address},#{postal_code},#{mobile},#{phone})")
    void addAddress(Receive_address receive_address) throws Exception;

    /**
     * 根据用户id查询用户所有地址
     * @param user_id
     * @return
     * @throws Exception
     */
    @Select("select * from d_receive_address where user_id=#{user_id}")
    List<Receive_address> findByUserId(int user_id) throws Exception;

    /**
     * 根据地址id查询用户地址
     * @param id
     * @return
     * @throws Exception
     */
    @Select("select * from d_receive_address where id=#{id}")
    Receive_address findById(int id) throws Exception;
}
