package com.elect.mapper;


import com.elect.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao {
    /**
     * 添加订单信息
     * @param order
     * @return
     * @throws Exception
     */
    @Insert("insert into d_order(user_id,order_time,total_price,receive_name,full_address,postal_code,mobile,phone) values(#{user_id},#{order_time},#{total_price},#{receive_name},#{full_address},#{postal_code},#{mobile},#{phone})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void addOrder(Order order) throws Exception;

    /**
     * 通过订单号查询订单
     * @param order_id
     * @return
     * @throws Exception
     */
    @Select("select * from d_order where id=#{order_id}")
    Order findByOrderId(int order_id) throws Exception;
}
