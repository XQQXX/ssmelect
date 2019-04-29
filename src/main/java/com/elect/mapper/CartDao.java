package com.elect.mapper;


import com.elect.pojo.Cart;
import com.elect.pojo.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao {

    /**
     * 添加商品进购物车
     * @throws Exception
     */
    @Insert("insert into d_cart(user_id,product_id,product_name,fixed_price,dang_price) values(#{user_id},#{product_id},#{product_name},#{fixed_price},#{dang_price})")
    void addCart(Cart cart) throws Exception;

    /**
     * 根据状态查询购物车
     * @param i
     * @return
     * @throws Exception
     */
    @Select("select * from d_cart where status=#{i} and user_id=#{user_id}")
    List<Cart> findByStatus(@Param("i") int i, @Param("user_id") int user_id) throws Exception;

    /**
     * 根据product_id查询cart
     * @param product_id
     * @param user_id
     * @return
     */
    @Select("select * from d_cart where product_id= #{product_id} and user_id= #{user_id}")
    Cart findByProductId(@Param("product_id") int product_id, @Param("user_id") int user_id) throws Exception;

    /**
     * 改变status
     * @param i
     */
    @Update("update d_cart set product_num=1,status=#{status} where product_id=#{i}")
    void updateStatus(@Param("i") int i, @Param("status") int status) throws Exception;

    /**
     * 改变product_num
     * @param product_id
     * @param product_num
     */
    @Update("update d_cart set product_num=#{product_num} where product_id=#{product_id}")
    void updateNum(@Param("product_id") int product_id, @Param("product_num") int product_num) throws Exception;

    /**
     * 删除用户购物车
     * @param user_id
     * @throws Exception
     */
    @Delete("delete from d_cart where user_id=#{user_id} and status=#{status}")
    void deleteCart(@Param("user_id") int user_id, @Param("status") int status) throws Exception;
}
