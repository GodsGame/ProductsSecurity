package com.example.productSecurity;

import com.example.productSecurity.dao.ProductMapper;
import com.example.productSecurity.dao.UserMapper;
import com.example.productSecurity.pojo.Product;
import com.example.productSecurity.pojo.User;
import com.example.productSecurity.service.ProductService;
import com.example.productSecurity.service.UserService;
import com.example.productSecurity.utils.MybatisUtils;
import com.example.productSecurity.utils.QRCodeGenerator;
import com.google.zxing.WriterException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ProductsSecurityApplicationTests {

    private static Logger logger = Logger.getLogger(ProductsSecurityApplicationTests.class);

    @Resource
    UserService userService;

    @Resource
    ProductService productService;

    //@Test
    void contextLoads() throws ParseException {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        String str = "2021-04-26";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(str);
        productMapper.addProduct(new Product("ipad pro 2021", new BigDecimal(6199), new java.sql.Date(date.getTime()), "中国", "苹果公司", "", 1L, ""));
        sqlSession.commit();
        sqlSession.close();
        System.out.println("添加成功");
    }

    @Test
    void test1() {
        logger.info("This is info");
        logger.debug("This is debug");
        logger.error("This is error");
    }


    @Test
    void test() throws IOException, WriterException {
        String path = "src/main/resources/static/images/image/qrcode/qrcode.png";
        try {
            QRCodeGenerator.code("http://localhost:8080/get?id=10000", 350, 350, path);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }

    @Test
    void test2() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        System.out.println(sqlSession);
        System.out.println(productMapper.getProduct(10001L));
        sqlSession.close();
    }

}
