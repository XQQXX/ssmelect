package com.elect.service;

import com.elect.pojo.Book;
import com.elect.pojo.Category;
import com.elect.pojo.Product;

import java.util.List;
import java.util.Map;

public interface MainService {

    /**
     * 首页推荐
     * @return
     * @throws Exception
     */
    List<Book> recommend() throws Exception;

    /**
     * 热销推荐
     * @return
     * @throws Exception
     */
    List<Product> hotBook() throws Exception;

    /**
     * 最新上架
     * @return
     * @throws Exception
     */
    List<Product> newBook() throws Exception;

    /**
     * 图书ALL
     * @return
     * @throws Exception
     */
    List<Book> bookList() throws Exception;

    /**
     * 图书分类
     * @return
     * @throws Exception
     */
    Map<Category,List<Category>> Category() throws Exception;

    /**
     * 按图书类别查询
     * @param id
     * @return
     * @throws Exception
     */
    List<Book> CateList(int id) throws Exception;

    /**
     * 分页
     * @param page
     * @param bookCats
     * @return
     * @throws Exception
     */
    List<Book> paging(int page, List<Book> bookCats) throws Exception;

    /**
     * 显示书籍详情
     * @param id
     * @return
     * @throws Exception
     */
    Book detailBook(int id) throws Exception;
}
