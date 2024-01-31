package com.my.mainstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.my.mainstore.dao.ProductsDao;
import com.my.mainstore.model.Products;
import com.my.mainstore.service.ProductsService;

@Service
@Qualifier("ProductsService")
public class ProducrtServiceImpl implements ProductsService{
@Autowired
ProductsDao productsDao;

@Override
public List<Products> getAllProductsList(){
	List<Products> productsList = productsDao.getAllProductsList();
return productsList;
}

@Override
public Products deletedById(Long prodid) {
	Products p = productsDao.deletedById(prodid);
	return p ;
}

@Override
public Products updateProducts(Products prod) {
	Products p = productsDao.updateProducts(prod);
	return p ;
}

@Override
public Products createProducts(Products prod){
	Products p = productsDao.createProducts(prod);
	return p ;
}

@Override
public Products getProductById(Long prodid){
	Products p = productsDao.getProductById(prodid);
	return p ;
}

@Override
public List<Products> getProductsSearchingByParam(String param) {
	List<Products> p = productsDao.getProductsSearchingByParam(param);
	return p;
}

}
