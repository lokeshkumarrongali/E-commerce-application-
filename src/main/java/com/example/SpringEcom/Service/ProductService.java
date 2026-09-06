package com.example.SpringEcom.Service;

import com.example.SpringEcom.Repo.ProductRepo;
import com.example.SpringEcom.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class ProductService {


@Autowired
private ProductRepo productRepo;
    public List<Product> getAllProducts() {

        return productRepo.findAll();
    }

    public Product getProductByid(int id) {

        return productRepo.findById(id).orElse(null);
    }

    public Product addProduct(Product p, MultipartFile file) throws IOException {

        if (file != null) {
            p.setImageName(file.getOriginalFilename());
            p.setImageType(file.getContentType());
            p.setImageData(file.getBytes());
        }
        return productRepo.save(p);
    }

    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepo.searchProducts(keyword);
    }
}
