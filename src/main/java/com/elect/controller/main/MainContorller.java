package com.elect.controller.main;


import com.elect.pojo.Book;
import com.elect.pojo.Category;
import com.elect.pojo.Product;
import com.elect.service.AdminService;
import com.elect.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequestMapping
@Controller
public class MainContorller {

    @Autowired
    private MainService mainService;
    @Autowired
    private AdminService adminService;

    @RequestMapping("main/main")
    public String main(HttpServletRequest request){
        try {
//                首页推荐
            List<Book> recommend = mainService.recommend();
            request.getSession().setAttribute("recommend", recommend);
//                热销推荐
            List<Product> hotBook = mainService.hotBook();
            request.getSession().setAttribute("hotBook", hotBook);
//                最新上架
            List<Product> newBook = mainService.newBook();
            request.getSession().setAttribute("newBook", newBook);
//                图书分类
            Map<Category, List<Category>> category = mainService.Category();
            request.getSession().setAttribute("category", category);
//                显示所有图书
            List<Book> bookList = mainService.bookList();
            request.getSession().setAttribute("bookList",bookList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:main.jsp";
    }

//    图书分类
    @RequestMapping("main/list")
    public String list(HttpServletRequest request,int id,int parent_id){
        int page=1;
        try {
            Category category=adminService.preEdit(parent_id);
//                显示分类栏
            Map<Category, List<Category>> categoryListMap= (Map<Category, List<Category>>) request.getSession().getAttribute("category");
            List<Category> categories=categoryListMap.get(category);
            request.getSession().setAttribute("categories", categories);
            request.getSession().setAttribute("name",category);
//                按分类显示图书
            List<Book> bookCats=mainService.CateList(id);
            request.setAttribute("page",page);
            request.getSession().setAttribute("bookCats",bookCats);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:paging?page="+page;
    }

//     分页
    @RequestMapping("main/paging")
    public String paging(int page,HttpServletRequest request){
        request.setAttribute("page",page);
        List<Book> bookCats= (List<Book>) request.getSession().getAttribute("bookCats");
        //分页
        try {
            bookCats=mainService.paging(page,bookCats);
            request.setAttribute("bookCat",bookCats);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:book_list.jsp";
    }

//    book详情
    @RequestMapping("main/detail")
    public String bookDetail(int id,HttpServletRequest request){
        try {
            Book book = mainService.detailBook(id);
            request.setAttribute("book",book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:show_product.jsp";
    }
}
