package com.example.productSecurity.service;

import com.example.productSecurity.pojo.Product;
import com.google.zxing.WriterException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {
    /**
     * 添加商品
     * @param product
     * @throws IOException
     * @throws WriterException
     */
    void addProduct(Product product) throws IOException, WriterException;

    /**
     * 获取商品
     * @param id
     * @return
     */
    Product getProduct(Long id);

    /**
     * 获取用户的所有商品名字
     * @param owner
     * @return
     */
    List<Product> getProductName(Long owner);

    /**
     * 获取用户所有商品
     * @param owner
     * @return
     */
    List<Product> getProducts(Long owner);

    /**
     * 生成新建商品图片路径
     * @param file
     * @return
     */
    String imgPath(String file);

    /**
     * 生成新建商品二维码路径
     * @return
     */
    String qrPath() throws IOException, WriterException;
}
