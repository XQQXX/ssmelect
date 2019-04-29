package com.elect.mapper;


import com.elect.pojo.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao {
    /**
     * 查询所有product
     * @return
     * @throws Exception
     */
    @Select("select * from d_product")
    List<Product> findAll() throws Exception;

    /**
     * 根据id查product
     * @param id
     * @return
     * @throws Exception
     */
    @Select("select * from d_product where id=#{id}")
    Product findById(int id) throws Exception;

    /**
     * 根据id删除图书
     * @param id
     * @throws Exception
     */
    @Delete(" delete from d_product where id=#{id}")
    void deleteById(int id) throws Exception;

    /**
     * 更新图书
     * @param product_id
     * @param product_name
     * @param fixed_price
     * @throws Exception
     */
    @Update("update d_product set product_name=#{product_name},fixed_price=#{fixed_price} where id=#{product_id}")
    void update(@Param("product_id") int product_id, @Param("product_name")String product_name, @Param("fixed_price")Double fixed_price) throws Exception;

    /**
     * 添加图书
     * @param product
     * @throws Exception
     */
    @Insert("insert into d_product(id,product_name,description,add_time,fixed_price,dang_price,product_pic) values(#{id},#{product_name},#{description},#{add_time},#{fixed_price},#{dang_price},#{product_pic})")
    void add(Product product) throws Exception;
}
