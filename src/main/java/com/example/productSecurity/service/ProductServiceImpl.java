package com.example.productSecurity.service;

import com.example.productSecurity.dao.ProductMapper;
import com.example.productSecurity.pojo.Product;
import com.example.productSecurity.utils.MybatisUtils;
import com.example.productSecurity.utils.QRCodeGenerator;
import com.google.zxing.WriterException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProductServiceImpl implements ProductService{
    /**
     * 添加商品
     * @param product
     * @throws IOException
     * @throws WriterException
     */
    @Override
    public void addProduct(Product product) throws IOException, WriterException {
//        SqlSession sqlSession = MybatisUtils.getSqlSession();
//        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
//        String path = "C:\\Users\\Administrator\\IdeaProjects\\ProductsSecurity\\src\\main\\resources\\image\\qrcode" + (productMapper.lastId() + 1) + ".png";
//        try {
//            QRCodeGenerator.code("http://localhost:8080/get?id=" + (productMapper.lastId() + 1), 350, 350, path);
//        } catch (WriterException e) {
//            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
//        } catch (IOException e) {
//            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
//        }
//        product.setQRcode(path);
//        productMapper.addProduct(product);
//        sqlSession.commit();
//        sqlSession.close();
    }

    /**
     * 获取商品
     * @param id
     * @return
     */
    @Override
    public Product getProduct(Long id) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        Product product = productMapper.getProduct(id);
        sqlSession.close();
        return product;
    }

    /**
     * 获取用户所有商品
     * @param owner
     * @return
     */
    @Override
    public List<Product> getProductName(Long owner) {
        List<Product> products = new ArrayList<>();
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        List<String> names = productMapper.getProductName(owner);
        names.forEach(name -> {
            Product product = new Product();
            product.setName(name);
            products.add(product);
        });
        sqlSession.close();
        return products;
    }

    /**
     * 获取用户所有商品
     * @param owner
     * @return
     */
    @Override
    public List<Product> getProducts(Long owner) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        List<Product> products = productMapper.getProductList(owner);
        sqlSession.close();
        return products;
    }

    /**
     * 生成新建商品图片路径
     * @param fileName
     * @return
     */
    @Override
    public String imgPath(String fileName) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        String regex = "\\.[a-zA-Z]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(fileName);
        while (m.find()) {
            fileName = m.group();
        }
        String path = "src/main/resources/static/images/image/products/product" + (productMapper.lastId() + 1) + fileName;
        sqlSession.close();
        return "";
    }

    /**
     * 生成新建商品二维码路径
     * @return
     */
    public String qrPath() throws IOException, WriterException {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        String path = "src/main/resources/static/images/images/qrcode/qrcode" + (productMapper.lastId() + 1) + ".png";
        QRCodeGenerator.code("http://localhost:8080/get?id=10000", 350, 350, "");
        sqlSession.close();
        return "";
    }
}
