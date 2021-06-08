package com.example.productSecurity.controller;

import com.example.productSecurity.pojo.Product;
import com.example.productSecurity.service.ProductService;
import com.example.productSecurity.utils.DateUtils;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Resource
    private ProductService productService;

    @RequestMapping("/add")
    @ResponseBody
    public String addProduct(@RequestParam String name, @RequestParam BigDecimal price,
                             @RequestParam String date, @RequestParam String place,
                             @RequestParam String distributor, @RequestParam MultipartFile file,
                             HttpServletRequest request) throws ParseException, IOException, WriterException {
        String picPath = productService.imgPath(file.getOriginalFilename());
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(picPath)));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("id");
        Date date1 = DateUtils.date(date);
        productService.addProduct(new Product(name, price, date1, place, distributor, "", id, picPath));
        return "添加成功";
    }

    @RequestMapping("get")
    @ResponseBody
    public String getProduct(@RequestParam Long id) {
        Product product = productService.getProduct(id);
        return product.toString();
    }

    @RequestMapping("/qrcodeData")
    @ResponseBody
    public List<Product> qrcodeData(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("id");
        return productService.getProducts(id);
    }

}
