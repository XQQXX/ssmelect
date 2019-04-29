package com.elect.service.impl;

import com.elect.mapper.*;
import com.elect.pojo.*;
import com.elect.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryProductDao categoryProductDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private ProductDao productDao;
    @Override
    public boolean login(String name, String password) throws Exception {
        Admin admin=adminDao.findByNameAndPassword(name,password);
        if(admin==null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Category preEdit(int id) throws Exception {
        Category category=categoryDao.findById(id);
        return category;
    }

    @Override
    public void updateCate(Category category) throws Exception {
        categoryDao.updateById(category);
    }

    @Override
    public void deleteCate(int id,int parent_id) throws Exception {
        if(parent_id==1){
            categoryDao.deleteById(id);
            categoryDao.deleteByParentId(id);
        }else{
            categoryDao.deleteById(id);
        }
    }

    @Override
    public void addCate(Category category) throws Exception {
        categoryDao.addCategory(category);
    }

    @Override
    public List<Category> findCate(int id) throws Exception {
        List<Category> categories=categoryDao.findByParentId(id);
        return categories;
    }

    @Override
    public void deleteBook(int id) throws Exception {
        bookDao.deleteById(id);
        productDao.deleteById(id);
        categoryProductDao.deleteByProductId(id);
    }

    @Override
    public void updateBook(Category_product category_product, String product_name, String author, Double fixed_price,int parent_id) throws Exception {
        bookDao.update(category_product.getProduct_id(),author);
        productDao.update(category_product.getProduct_id(),product_name,fixed_price);
        List<Category_product> category_products=categoryProductDao.findByProductId(category_product.getProduct_id());
        for (int i=0;i<category_products.size();i++){
            if(category_products.get(i).getCat_id()!=1){
                Category category=categoryDao.findById(category_products.get(i).getCat_id());
                if(category.getParent_id()==1){
                    Category_product category_product1=new Category_product();
                    category_product1.setProduct_id(category_product.getProduct_id());
                    category_product1.setCat_id(category.getId());
                    categoryProductDao.update(category_product1.getCat_id(),category_product1.getProduct_id(),parent_id);
                }else{
                    Category_product category_product1=new Category_product();
                    category_product1.setProduct_id(category_product.getProduct_id());
                    category_product1.setCat_id(category.getId());
                    categoryProductDao.update(category_product1.getCat_id(),category_product1.getProduct_id(),category_product.getCat_id());
                }
            }
        }
    }

    @Override
    public List<Category> findCates(int id) throws Exception {
        List<Category> list=new ArrayList<>();
        List<Category_product> category_products=categoryProductDao.findByProductId(id);
        for (int i=0;i<category_products.size();i++){
            if(category_products.get(i).getCat_id()!=1){
                Category category=categoryDao.findById(category_products.get(i).getCat_id());
                if(category.getParent_id()==1){
                   list.add(category);
                }else{
                   list.add(category);
                }
            }
        }
        return list;
    }

    @Override
    public void addBook(Book book, Product product, int parent_id, int cat_id) throws Exception {
        bookDao.add(book);
        int book_id=book.getId();
        product.setId(book_id);
        productDao.add(product);
        categoryProductDao.add(book_id,1);
        categoryProductDao.add(book_id,parent_id);
        categoryProductDao.add(book_id,cat_id);
    }
}
