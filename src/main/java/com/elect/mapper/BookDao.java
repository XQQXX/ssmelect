package com.elect.mapper;

import com.elect.pojo.Book;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface BookDao {
    /**
     * 查询所有book表信息
     * @return
     * @throws Exception
     */
    @Select("select * from d_book")
    List<Book> findBookAll() throws Exception;

    /**
     * 根据Id查book
     * @param id
     * @return
     * @throws Exception
     */
    @Select("select * from d_book where id=#{id}")
    Book findBookById(int id) throws Exception;

    /**
     * 根据id删除图书
     * @param id
     * @throws Exception
     */
    @Delete("delete from d_book where id=#{id}")
    void deleteById(int id) throws Exception;

    /**
     * 更新图书信息
     * @param product_id
     * @param author
     */
    @Update("update d_book set author=#{author} where id=#{product_id}")
    void update(@Param("product_id") int product_id, @Param("author") String author) throws Exception;

    /**
     * 添加图书
     * @param book
     * @throws Exception
     */
    @Insert("insert into d_book(author,publishing,publish_time,word_number,which_edtion,total_page,isbn,author_summary,catalogue) values(#{author},#{publishing},#{publish_time},#{word_number},#{which_edtion},#{total_page},#{isbn},#{author_summary},#{catalogue})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void add(Book book) throws Exception;
}
