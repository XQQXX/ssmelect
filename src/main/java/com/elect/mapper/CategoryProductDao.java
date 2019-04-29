package com.elect.mapper;

import com.elect.pojo.Category_product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryProductDao {

    @Select("select * from d_category_product where cat_id=#{id}")
    List<Category_product> findByCatId(int id) throws Exception;

    /**
     * 根据product_id删除信息
     * @param id
     * @throws Exception
     */
    @Delete("delete from d_category_product where product_id=#{id}")
    void deleteByProductId(int id) throws Exception;

    @Update("update d_category_product set cat_id=#{id} where product_id=#{product_id} and cat_id=#{cat_id}")
    void update(@Param("cat_id") int cat_id,@Param("product_id") int product_id,@Param("id") int id) throws Exception;

    @Select("select * from d_category_product where product_id=#{product_id}")
    List<Category_product> findByProductId(int product_id) throws Exception;

    @Insert("insert into d_category_product(product_id,cat_id) values(#{book_id},#{id})")
    void add(@Param("book_id") int book_id,@Param("id") int id) throws Exception;
}
