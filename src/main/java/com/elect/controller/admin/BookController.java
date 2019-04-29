package com.elect.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.elect.pojo.Book;
import com.elect.pojo.Category;
import com.elect.pojo.Category_product;
import com.elect.pojo.Product;
import com.elect.service.AdminService;
import com.elect.service.MainService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("admin")
@Controller
public class BookController {

    @Autowired
    private MainService mainService;
    @Autowired
    private AdminService adminService;

    /**
     * 显示所有图书
     * @param request
     * @return
     */
    @RequestMapping("admin/book/bookList")
    public String bookList(HttpServletRequest request){
        try {
            List<Book> bookList=mainService.bookList();
            request.setAttribute("bookList",bookList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:list.jsp";
    }

    /**
     * 图书详情
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("admin/book/bookDetail")
    public String bookDetail(int id,HttpServletRequest request){
        try {
            Book book=mainService.detailBook(id);
            List<Category> cates=adminService.findCates(id);
            request.setAttribute("cates",cates);
            request.setAttribute("book",book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:desc.jsp";
    }

    @RequestMapping("admin/book/secondCate")
    public void secondCate(int id,HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");
        String str="";
        try {
            List<Category> categories=adminService.findCate(id);
            str= JSONObject.toJSONString(categories);
            response.getWriter().write(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *对图书进行删改
     * @param method
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("admin/book/handleBook")
    public String handleBook(String method,int id,HttpServletRequest request){
        if(method.equals("delete")){
            try {
                adminService.deleteBook(id);
                return "forward:bookList";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(method.equals("update")){
            String product_name=request.getParameter("name");
            String author=request.getParameter("author");
            Double fixed_price=Double.parseDouble(request.getParameter("price"));
            int cate_id=Integer.parseInt(request.getParameter("cate_id"));
            int parent_id=Integer.parseInt(request.getParameter("parent_id"));
            Category_product category_product=new Category_product();
            category_product.setCat_id(cate_id);
            category_product.setProduct_id(id);
            try {
                adminService.updateBook(category_product,product_name,author,fixed_price,parent_id);
                return "forward:bookList";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "forward:bookList";
    }

    /**
     * 添加图书
     * @param request
     * @return
     */
    @RequestMapping("admin/book/addBook")
    public String addBook(HttpServletRequest request){
        Map<String,String> map=new HashMap<>();
        String product_pic="";
// ========================图片保存==============================//
        //创建一个工厂类的实例，该实例为解析器提供了缺省的配置
        DiskFileItemFactory df = new DiskFileItemFactory();
        //创建一个解析器
        ServletFileUpload sf = new ServletFileUpload(df);
        /**
         * 使用解析器解析请求参数对象
         * 解析之后，会将表单中的数据转换成一个个FileItem 对象。
         *一个表单域中的数据对应于一个FileItem 对象。
         */
        try {
            List<FileItem> items=sf.parseRequest(request);
            /**
             * 遍历items集合，判断每一个FileItem对象是普通表单域还是文件表单域
             */
            for(FileItem item : items) {
                //判断item是否是文件表单域
                if(!item.isFormField()) {
                    //保存文件域上传的文件到本地(服务器)磁盘
                    //获取保存的目录的物理路径
                    ServletContext sc = request.getServletContext();
                    //获取物理路径
                    String path = sc.getRealPath("productImages");
                    System.out.println("upload的物理路径:"+path);//d:/java/upload
                    //通过item对象获取上传文件的文件名
                    product_pic = item.getName();//3.jpg
                    //创建该路径+文件名的file对象  d:/java/upload/3.jpg
                    String pathname = path + File.separator+product_pic;// File.separator 匹配不同系统的正斜杠与反斜杠
                    File file = new File(pathname);
                    //将文件写入到指定目录中
                    item.write(file);
                }else{
                    map.put(item.getFieldName(),item.getString("utf-8"));
                }
            }
//   =================图书添加==================================  //
            System.out.println(product_pic);
            String product_name=map.get("product_name");
            String description=map.get("description");
            Double fixed_price=Double.parseDouble(map.get("fixed_price"));
            Double dang_price=Double.parseDouble(map.get("dang_price"));
            String author=map.get("author");
            String publishing=map.get("publishing");
            int parent_id=Integer.parseInt(map.get("parent_id"));
            int cat_id=Integer.parseInt(map.get("cat_id"));
            Book book=new Book();
            book.setAuthor(author);
            book.setPublishing(publishing);
            book.setPublish_time(System.currentTimeMillis());
            book.setWord_number("1万");
            book.setWhich_edtion("1");
            book.setTotal_page("100");
            book.setIsbn("12345678");
            book.setAuthor_summary("作者是个好人！！");
            book.setCatalogue("这是一本好书啊");
            Product product=new Product();
            product.setProduct_name(product_name);
            product.setDescription(description);
            product.setAdd_time(System.currentTimeMillis());
            product.setFixed_price(fixed_price);
            product.setDang_price(dang_price);
            product.setProduct_pic(product_pic);
            adminService.addBook(book,product,parent_id,cat_id);
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "forward:bookList";
    }

}
