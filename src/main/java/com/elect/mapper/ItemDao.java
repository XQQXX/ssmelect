package com.elect.mapper;


import com.elect.pojo.Item;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDao {
    /**
     * 添加item
     * @param item
     * @throws Exception
     */
    @Insert("insert into d_item(order_id,product_id,product_name,dang_price,product_num,amount) values(#{order_id},#{product_id},#{product_name},#{dang_price},#{product_num},#{amount})")
    void addItem(Item item) throws Exception;

    /**
     * 通过订单号查询订单内商品
     * @param order_id
     * @return
     * @throws Exception
     */
    @Select("select * from d_item where order_id=#{order_id}")
    List<Item> findByOrderId(int order_id) throws Exception;
}
