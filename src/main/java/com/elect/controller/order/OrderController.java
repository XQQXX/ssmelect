package com.elect.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.elect.pojo.Cart;
import com.elect.pojo.Order;
import com.elect.pojo.Receive_address;
import com.elect.pojo.User;
import com.elect.service.CartService;
import com.elect.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @RequestMapping("order/buildOrder")
    public String buildOrder(HttpServletRequest request,String receiveName,String fullAddress,String postalCode, String mobile,String phone){
        List<Cart> carts= (List<Cart>) request.getSession().getAttribute("cart");
        Order order=new Order();
        order.setReceive_name(receiveName);
        order.setPostal_code(postalCode);
        order.setFull_address(fullAddress);
        order.setMobile(mobile);
        order.setPhone(phone);
        try {
//                添加订单
            int order_id = orderService.addOrder(carts,order);
//                添加订单商品
            orderService.addItem(carts,order_id);
//                查询订单
            order=orderService.findOrder(order_id);
//                添加地址
            Receive_address receive_address=new Receive_address(carts.get(0).getUser_id(),receiveName,fullAddress,postalCode,mobile,phone);
            orderService.addAddress(receive_address);
//                下单成功，删除购物车内容
            cartService.delCart(carts.get(0).getUser_id(),1);
            request.getSession().setAttribute("order",order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:order_ok.jsp";
    }

    @RequestMapping("order/addressAll")
    public String addressAll(HttpServletRequest request){
//        查询所有地址
        User user=(User)request.getSession().getAttribute("user");
        try {
            List<Receive_address> addressList = orderService.findAllAddress(user.getId());
            request.getSession().setAttribute("Address",addressList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:address_form.jsp";
    }

    @RequestMapping("order/findAddress")
    public @ResponseBody String findAddress(int id){
        String address="";
        try {
            Receive_address receive_address=orderService.findAddress(id);
            address= JSONObject.toJSONString(receive_address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

}
