package com.my.mainstore.controller;

import com.my.mainstore.MainstoreApplication;
import com.my.mainstore.model.Products;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MainstoreApplication.class)
@Slf4j
class ProductsControllerTest {
    @Autowired
    ProductsController productsController;

    @Test
    void getAllProductsList_NotNull() {
        List<Products> prodList = productsController.getAllProductsList();
        Assertions.assertNotNull(prodList);
    }
}