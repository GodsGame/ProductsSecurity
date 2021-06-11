package com.example.productSecurity.service;

import com.example.productSecurity.dao.ProductMapper;
import com.example.productSecurity.pojo.Product;
import com.example.productSecurity.utils.MybatisUtils;
import com.example.productSecurity.utils.QRCodeGenerator;
import com.google.zxing.WriterException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        productMapper.addProduct(product);
        sqlSession.commit();
        sqlSession.close();
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
     * @return
     */
    @Override
    public String[] imgPath() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        String path = "src/main/resources/static/images/image/products/product" + productMapper.lastId() + ".jpg";
        String path2 = "/images/image/products/product" + productMapper.lastId() + ".jpg";
        sqlSession.close();
        String[] paths = {path, path2};
        return paths;
    }

    /**
     * 生成新建商品二维码路径
     * @return
     */
    public String[] qrPath() throws IOException, WriterException {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        String path = "src/main/resources/static/images/image/qrcode/qrcode" + productMapper.lastId() + ".png";
        String path2 = "/images/image/qrcode/qrcode" + productMapper.lastId() + ".png";
        String path3 = "target/classes/static" + path2;
        QRCodeGenerator.code("http://localhost:8080/get?id="+productMapper.lastId(), 350, 350, path);
        QRCodeGenerator.code("http://localhost:8080/get?id="+productMapper.lastId(), 350, 350, path3);
        sqlSession.close();
        String[] paths = {path, path2};
        return paths;
    }

    /**
     * 设置二维码和商品图片目录
     * @param qrPath
     * @param imgPath
     */
    @Override
    public void setPath(String qrPath, String imgPath) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        productMapper.setPath(qrPath, imgPath);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 修改商品信息
     * @param product
     */
    @Override
    public void updateProduct(Product product) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        productMapper.updateProduct(product);

        sqlSession.commit();
        sqlSession.close();
    }
}
