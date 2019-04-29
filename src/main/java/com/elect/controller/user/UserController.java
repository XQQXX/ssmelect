package com.elect.controller.user;

import com.elect.pojo.User;
import com.elect.service.UserService;
import com.elect.util.RanCode;
import com.elect.util.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping
public class UserController {

//    注入userService
    @Autowired
    private UserService userService;

    /**
     * 用户注册1---基本信息填写
     * @param email
     * @param nickname
     * @param password
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/user/register_form")
    public String registerOne(String email,String nickname,String password,HttpServletRequest request,HttpServletResponse response){
       String uuid= RanCode.ranUUID();
       SendMail.sendMail(email,uuid);
       User user=new User(email,nickname,password,uuid);
       try {
           userService.RegistUser(user);
           request.getSession().setAttribute("user",user);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return "forward:/user/verify_form.jsp";
   }

    /**
     * 用户注册2----邮箱验证
     * @param request
     * @param emailCode
     * @return
     */
   @RequestMapping("/user/verify_form")
   public String registerTwo(HttpServletRequest request,String emailCode){
       User user=(User)request.getSession().getAttribute("user");
       try {
           if(userService.checkEmailCode(user.getEmail_verify_code(),emailCode)){
               String last_login_time=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date());
               String last_login_ip=request.getRemoteAddr();
               user.setLast_login_ip(last_login_ip);
               user.setLast_login_time(last_login_time);
               user.setIs_email_verify("Y");
               request.getSession().setAttribute("user",user);
               userService.RegistUserDetil(user);
               return "forward:register_ok.jsp";
           }else{
               request.setAttribute("errorMsg","邮箱验证失败，请检查后重新输入");
               return "forward:verify_form.jsp";
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return "forward:verify_form.jsp";
   }

    /**
     * 邮箱名验证(注册)
     * @param email
     * @return
     */
    @RequestMapping("/user/checkEmail")
    public @ResponseBody String checkEmail(String email){
        try {
            if(userService.checkEmail(email)){
                return "true";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    /**
     * 验证码验证（注册）
     * @param request
     * @param verifyCode
     * @return
     */
    @RequestMapping("/user/checkVerifyCode")
    public @ResponseBody String checkVerifyCode(HttpServletRequest request,String verifyCode){
        String code= (String) request.getSession().getAttribute("code");
        try {
            if(userService.checkCode(code,verifyCode)){
                return "true";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    @RequestMapping("/user/login_form")
    public String login(HttpServletRequest request,HttpServletResponse response,String email,String password){
        User u=new User();
        u.setEmail(email);
        u.setPassword(password);
        System.out.println(u);
        User user= null;
        try {
            user = userService.checkLogin(u);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user==null){
            request.setAttribute("login_error","邮箱或密码错误，请重新输入！");
            return "forward:/user/login_form.jsp";
        }else{
            try {
                if(user.getIs_email_verify().equals("Y")){
                    String last_login_time=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date());
                    String last_login_ip=request.getRemoteAddr();
                    user.setLast_login_ip(last_login_ip);
                    user.setLast_login_time(last_login_time);
                    try {
                        userService.RegistUserDetil(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    request.getSession().setAttribute("user",user);
                    return "redirect:../main/main";
                }else{
                    String uuid=RanCode.ranUUID();
                    SendMail.sendMail(user.getEmail(),uuid);
                    user.setEmail_verify_code(uuid);
                    request.getSession().setAttribute("user",user);
                    return "forward:/user/verify_form.jsp";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "forward:/user/login_form.jsp";
    }

    @RequestMapping("main/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().invalidate();
        return "redirect:main";
    }

}
