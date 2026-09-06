package com.example.SpringEcom.controller;


import com.example.SpringEcom.Service.ProductService;
import com.example.SpringEcom.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product> >getProducts(){
        return new ResponseEntity<>( productService.getAllProducts(), HttpStatus.ACCEPTED);
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){

        Product p = productService.getProductByid(id);

        if(p.getId()>0){
            return new ResponseEntity<>(p, HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping("/product/{productid}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int productid){
        Product p = productService.getProductByid(productid);
        if(p.getId()>0){
            return new ResponseEntity<>(p.getImageData(), HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart("product") Product p,@RequestPart(name = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        Product savedProduct = null;

        try{
            savedProduct = productService.addProduct(p,imageFile);

            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

        }catch (IOException e)  {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart("product") Product p,@RequestPart MultipartFile imageFile) throws IOException {
        Product updateProduct = null;

        try{
            updateProduct=productService.addProduct(p,imageFile);
            return new ResponseEntity<>(updateProduct.getId()+"",HttpStatus.OK);
        }catch (IOException e)  {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product p=productService.getProductByid(id);
        if(p.getId()>0){
            productService.deleteProduct(id);
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product> products = productService.searchProducts(keyword);
        System.out.println("searching with " + keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
