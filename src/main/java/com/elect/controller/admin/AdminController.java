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

@RequestMapping
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private MainService mainService;

    /**
     * 管理员登录
     * @param request
     * @param name
     * @param password
     * @return
     */
    @RequestMapping("admin/login")
    public String login(HttpServletRequest request,String name, String password){
        try {
            boolean flag=adminService.login(name,password);
            if(!flag){
                String msg="账号或密码错误！";
                request.setAttribute("msg",msg);
                return "forward:login.jsp";
            }else{
//                    加载图书分类
                request.getSession().setAttribute("adminName",name);
                Map<Category, List<Category>> category = mainService.Category();
                request.getSession().setAttribute("category", category);
                return "forward:admin/main.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:login.jsp";
    }
}
