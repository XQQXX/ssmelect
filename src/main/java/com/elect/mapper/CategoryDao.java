package com.elect.mapper;


import com.elect.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryDao {

    /**
     * 通过parentId查询分类
     * @param id
     * @return
     * @throws Exception
     */
    @Select("select * from d_category where parent_id=#{id}")
    List<Category> findByParentId(int id) throws Exception;

    /**
     * 通过id查询分类信息
     * @param id
     * @return
     * @throws Exception
     */
    @Select("select * from d_category where id=#{id}")
    Category findById(int id) throws Exception;

    /**
     * 修改分类信息
     * @param category
     * @throws Exception
     */
    @Update("update d_category set name=#{name} where id=#{id}")
    void updateById(Category category) throws Exception;

    /**
     * 根据id删除分类
     * @param id
     * @throws Exception
     */
    @Delete("delete from d_category where id=#{id}")
    void deleteById(int id) throws Exception;

    /**
     * 根据parent_id删除分类
     * @param parent_id
     * @throws Exception
     */
    @Delete("delete from d_category where parent_id=#{parent_id}")
    void deleteByParentId(int parent_id) throws Exception;

    /**
     * 添加分类
     * @param category
     */
    @Insert("insert into d_category(en_name,name,parent_id) values(#{en_name},#{name},#{parent_id})")
    void addCategory(Category category) throws Exception;
}
