package com.elect.service.impl;


import com.elect.mapper.*;
import com.elect.pojo.*;
import com.elect.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MainServiceImpl implements MainService {

//    注入相应dao对象
    @Autowired
    private BookDao bookDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryProductDao categoryProductDao;
    @Override
    public List<Book> recommend() throws Exception {
        List<Book> list=new ArrayList<Book>();
        List<Book> books=bookDao.findBookAll();
        List<Product> products=productDao.findAll();
        while(true){
            int index=new Random().nextInt(books.size());
            books.get(index).setProduct(products.get(index));
            if(!list.contains(books.get(index))) {
                list.add(books.get(index));
            }
            if(list.size()==2){
                break;
            }
        }
        return list;
    }

    @Override
    public List<Product> hotBook() throws Exception {
        List<Product> list=new ArrayList<Product>();
        List<Product> products=productDao.findAll();
        while(true){
            int index=new Random().nextInt(products.size());
            if(!list.contains(products.get(index))) {
                list.add(products.get(index));
            }
            if(list.size()==4){
                break;
            }
        }
        return list;
    }

    @Override
    public List<Product> newBook() throws Exception {
        List<Product> list=new ArrayList<Product>();
        List<Product> products=productDao.findAll();
        while(true){
            int index=new Random().nextInt(products.size());
            Product product=products.get(index);
            if(!list.contains(product)) {
                list.add(products.get(index));
            }
            if(list.size()==4){
                break;
            }
        }
        return list;
    }

    @Override
    public List<Book> bookList() throws Exception {
        List<Book> list=new ArrayList<Book>();
        List<Book> books=bookDao.findBookAll();
        List<Product> products=productDao.findAll();
        for(int i=0;i<books.size();i++){
            books.get(i).setProduct(products.get(i));
            list.add(books.get(i));
        }
        return list;
    }

    @Override
    public Map<Category,List<Category>> Category() throws Exception {
        Map<Category,List<Category>> categoryMap=new LinkedHashMap<>();
        List<Category> list=categoryDao.findByParentId(1);
        for(int i=0;i<list.size();i++){
            List<Category> classify=categoryDao.findByParentId(list.get(i).getId());
            for (int j=0;j<classify.size();j++){
                List<Category_product> category_products=categoryProductDao.findByCatId(classify.get(j).getId());
                classify.get(j).setCategory_products(category_products);
            }
            categoryMap.put(list.get(i),classify);
        }
        return categoryMap;
    }

    @Override
    public List<Book> CateList(int id) throws Exception {
        List<Book> list=new ArrayList<Book>();
        List<Category_product> category_products =categoryProductDao.findByCatId(id);
        for(int i=0;i<category_products.size();i++) {
            Book book = bookDao.findBookById(category_products.get(i).getProduct_id());
            Product product = productDao.findById(category_products.get(i).getProduct_id());
            book.setProduct(product);
            list.add(book);
        }
        return list;
    }

    @Override
    public List<Book> paging(int page, List<Book> bookCats) throws Exception {
        List<Book> list=new ArrayList<>();
        for(int i=(page-1)*5;i<page*5&&i<bookCats.size();i++) {
            list.add(bookCats.get(i));
        }
        return list;
    }

    @Override
    public Book detailBook(int id) throws Exception {
        Book book=bookDao.findBookById(id);
        Product product=productDao.findById(id);
        book.setProduct(product);
        return book;
    }

}
