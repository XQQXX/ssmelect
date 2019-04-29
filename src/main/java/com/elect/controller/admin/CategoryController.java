package com.elect.controller.admin;

import com.elect.pojo.Category;
import com.elect.service.AdminService;
import com.elect.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequestMapping("admin")
@Controller
public class CategoryController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private MainService mainService;

    /**
     * 所有分类
     * @param request
     * @return
     */
    @RequestMapping("admin/category/categoryList")
    public String categoryList(HttpServletRequest request){
        try {
            Map<Category, List<Category>> category = mainService.Category();
            request.getSession().setAttribute("category", category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:list.jsp";
    }

    /**
     * 分类管理
     * @param method
     * @return
     */
    @RequestMapping("admin/category/categorys")
    public String category(String method,HttpServletRequest request){
        if (method.equals("preEdit")) {
            int id = Integer.parseInt(request.getParameter("cid"));
            try {
                Category cate = adminService.preEdit(id);
                request.setAttribute("cate", cate);
                return "forward:mod.jsp";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//            修改分类
        if (method.equals("update")) {
            int id = Integer.parseInt(request.getParameter("cid"));
            String name = request.getParameter("name");
            Category category = new Category();
            category.setName(name);
            category.setId(id);
            try {
                adminService.updateCate(category);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "forward:categoryList";
        }
//            删除界面显示
        if (method.equals("preDelete")) {
            int id = Integer.parseInt(request.getParameter("cid"));
            try {
                Category cate = adminService.preEdit(id);
                request.setAttribute("cate", cate);
                return "forward:del.jsp";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//            删除分类
        if (method.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("cid"));
            int parent_id=Integer.parseInt(request.getParameter("parent_id"));
            try {
                adminService.deleteCate(id,parent_id);
                return "forward:categoryList";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//            添加分类
        if (method.equals("add")){
            String name=request.getParameter("name");
            String en_name=request.getParameter("en_name");
            int parent_id=Integer.parseInt(request.getParameter("parent_id"));
            Category category=new Category();
            category.setName(name);
            category.setEn_name(en_name);
            category.setParent_id(parent_id);
            try {
                adminService.addCate(category);
                return "forward:categoryList";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "forward:categoryList";
    }
}
