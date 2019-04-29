package com.elect.service.impl;

import com.elect.mapper.*;
import com.elect.pojo.*;
import com.elect.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartServiceImpl implements CartService {

    //注入dao对象
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CartDao cartDao;

    @Override
    public void addCartBook(int id,int user_id) throws Exception {
        Cart cart=cartDao.findByProductId(id,user_id);
        if(cart==null) {
            Product product = productDao.findById(id);
            cart=new Cart();
            cart.setUser_id(user_id);
            cart.setProduct_id(product.getId());
            cart.setProduct_name(product.getProduct_name());
            cart.setFixed_price(product.getFixed_price());
            cart.setDang_price(product.getDang_price());
            cartDao.addCart(cart);
        }
    }

    @Override
    public List<Cart> showCart(int i,int user_id) throws Exception {
        List<Cart> list=cartDao.findByStatus(i,user_id);
        return list;
    }

    @Override
    public void changeStatus(int id,int status) throws Exception {
        cartDao.updateStatus(id,status);
    }

    @Override
    public void changeNum(int product_id, int product_num) throws Exception {
        cartDao.updateNum(product_id,product_num);
    }

    @Override
    public void delCart(int user_id,int status) throws Exception {
        cartDao.deleteCart(user_id,status);
    }


}
