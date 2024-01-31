package com.my.mainstore.service;

import java.util.List;

import com.my.mainstore.model.Products;

public interface ProductsService {

	List<Products> getAllProductsList();

	Products deletedById(Long prodid);

	Products updateProducts(Products prod);

	Products createProducts(Products prod);

	Products getProductById(Long prodid);

	List<Products> getProductsSearchingByParam(String param);

}
