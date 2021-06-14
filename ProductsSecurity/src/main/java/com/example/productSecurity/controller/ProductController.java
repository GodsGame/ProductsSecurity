package com.example.productSecurity.controller;

import com.example.productSecurity.pojo.Product;
import com.example.productSecurity.service.ProductService;
import com.example.productSecurity.utils.DateUtils;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Resource
    private ProductService productService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addProducts(@RequestParam String name, @RequestParam BigDecimal price,
                             @RequestParam String date, @RequestParam String place,
                             @RequestParam String distributor, @RequestParam("img") MultipartFile file,
                             HttpServletRequest request) throws ParseException, IOException, WriterException {
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("id");
        Date date1 = DateUtils.date(date);
        productService.addProduct(new Product(name, price, date1, place, distributor, "", id, ""));

        //提取图片真实路径和前端访问路径
        String[] imgPaths = productService.imgPath();
        String picPath = imgPaths[0];

        //提取二维码真实路径和前端访问路径
        String[] qrPaths = productService.qrPath();

        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(picPath)));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();
        productService.setPath(qrPaths[1], imgPaths[1]);
        String imgPath2 = "target/classes/static" + imgPaths[1];
        BufferedOutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(new File(imgPath2)));
        outputStream2.write(file.getBytes());
        outputStream2.flush();
        outputStream2.close();
        return "redirect:/cards";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateProduct(@RequestParam String name, @RequestParam BigDecimal price,
                                @RequestParam String date, @RequestParam String place,
                                @RequestParam String distributor, @RequestParam("img") MultipartFile file,
                                @RequestParam String path, @RequestParam Long id,
                                HttpServletRequest request) throws ParseException, IOException, WriterException {
        String fileName = file.getOriginalFilename();
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("id");
        Date date1 = DateUtils.date(date);
        productService.updateProduct(new Product(id, name, price, date1, place, distributor, "", userId, ""));

        //修改图片
        if (!fileName.equals("")) {
            String imgPath1 = "src/main/resources/static" + path;
            BufferedOutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(new File(imgPath1)));
            outputStream1.write(file.getBytes());
            outputStream1.flush();
            outputStream1.close();
            String imgPath2 = "target/classes/static" + path;
            BufferedOutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(new File(imgPath2)));
            outputStream2.write(file.getBytes());
            outputStream2.flush();
            outputStream2.close();
        }
        return "redirect:/cards";
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

    @RequestMapping("/deleteProduct")
    public String delete(@RequestParam Long id) {
        productService.deleteProduct(id);
        return "redirect:/cards";
    }
}
