package com.elect.controller.cart;

import com.alibaba.fastjson.JSONObject;
import com.elect.pojo.Cart;
import com.elect.pojo.User;
import com.elect.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

//    显示购物车
    @RequestMapping("cart/cart")
    public String cart(HttpServletRequest request){
        try {
            User user= (User) request.getSession().getAttribute("user");
            if(user==null){
                return "forward:cart_list.jsp";
            }else {
                List<Cart> cart = cartService.showCart(1, user.getId());
                List<Cart> delCart = cartService.showCart(0, user.getId());
                request.getSession().setAttribute("cart", cart);
                request.getSession().setAttribute("delCart", delCart);
                return "forward:cart_list.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:cart_list.jsp";
    }

//  购物车添加商品
    @RequestMapping("cart/shop")
    public void shop(int id,int userId){
        try {
            cartService.addCartBook(id,userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//     删除恢复购物车
    @RequestMapping("cart/changeStatus")
    public String changeStatus(int id,int status){
        try {
            cartService.changeStatus(id,status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:cart";
    }

//    改变商品数量
    @RequestMapping("cart/changeNum")
    public @ResponseBody String changeNum(HttpServletRequest request,int product_id,int product_num){
        String price="";
        try {
            User user= (User) request.getSession().getAttribute("user");
            cartService.changeNum(product_id,product_num);
            List<Cart> cart = cartService.showCart(1, user.getId());
            int total=0;
            int save=0;
            for(Cart c:cart){
                total+=c.getDang_price()*c.getProduct_num();
                save+=(c.getFixed_price()-c.getDang_price())*c.getProduct_num();
            }
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("total",total);
            jsonObject.put("save",save);
            price=JSONObject.toJSONString(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return price;
    }


    @RequestMapping("cart/account")
    public String account(HttpServletRequest request){
        try {
            User user= (User) request.getSession().getAttribute("user");
            List<Cart> cart = cartService.showCart(1, user.getId());
            List<Cart> delCart = cartService.showCart(0, user.getId());
            request.getSession().setAttribute("cart", cart);
            request.getSession().setAttribute("delCart", delCart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:../order/order_info.jsp";
    }

}
