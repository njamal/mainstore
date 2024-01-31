package com.my.mainstore.dao;

import java.util.List;

import com.my.mainstore.model.Products;

public interface ProductsDao {
	List<Products> getAllProductsList();

	Products deletedById(Long prodid);

	Products updateProducts(Products prod);

	Products createProducts(Products prod);

	Products getProductById(Long prodid);

	List<Products> getProductsSearchingByParam(String param);
}
