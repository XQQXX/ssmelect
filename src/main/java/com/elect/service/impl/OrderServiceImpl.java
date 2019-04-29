package com.elect.service.impl;

import com.elect.mapper.*;
import com.elect.pojo.*;
import com.elect.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private AddressDao addressDao;

    @Override
    public int addOrder(List<Cart> carts,Order order) throws Exception {
        double total_price=0;
        for(Cart c:carts){
            total_price+=c.getDang_price()*c.getProduct_num();
        }
        int user_id=carts.get(0).getUser_id();
        String order_time=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date());
        order.setUser_id(user_id);
        order.setTotal_price(total_price);
        order.setOrder_time(order_time);
        orderDao.addOrder(order);
        int order_id=order.getId();
        return order_id;
    }

    @Override
    public void addItem(List<Cart> carts, int order_id) throws Exception {
        for(Cart c:carts){
            Item item=new Item(order_id,c.getProduct_id(),c.getProduct_name(),c.getDang_price(),c.getProduct_num(),c.getDang_price()*c.getProduct_num());
            itemDao.addItem(item);
        }
    }

    @Override
    public Order findOrder(int order_id) throws Exception {
        Order order=orderDao.findByOrderId(order_id);
        List<Item> items=itemDao.findByOrderId(order_id);
        order.setItems(items);
        return order;
    }

    @Override
    public void addAddress(Receive_address receive_address) throws Exception {
        List<Receive_address> list=addressDao.findByUserId(receive_address.getUser_id());
        if(!list.contains(receive_address)) {
            addressDao.addAddress(receive_address);
        }
    }

    @Override
    public List<Receive_address> findAllAddress(int user_id) throws Exception{
        List<Receive_address> list=addressDao.findByUserId(user_id);
        return list;
    }

    @Override
    public Receive_address findAddress(int id) throws Exception {
        Receive_address receive_address=addressDao.findById(id);
        return receive_address;
    }


}
