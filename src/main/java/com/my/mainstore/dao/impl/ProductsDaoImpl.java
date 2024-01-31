package com.my.mainstore.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import com.my.mainstore.dao.ProductsDao;
import com.my.mainstore.data.ProductsRepository;
import com.my.mainstore.model.Products;

@Repository
public class ProductsDaoImpl extends JdbcDaoSupport implements ProductsDao {
@Autowired
ProductsRepository productsRepository;
@Autowired
DataSource datasource;
@PostConstruct
private void initializer() {
	setDataSource(datasource);
}

@Override
public List<Products> getAllProductsList() {
	List<Products> products = productsRepository.findAll();
	return products;
}

@Override
public Products deletedById(Long prodid) {
	Products p = productsRepository.getOne(prodid);
	if(null != p ) {
		productsRepository.deleteById(prodid);
		logger.info("Data with ID : " + p.getProdid() + " was deleted !!!!!!! ");
		return p;
}else {
	logger.info("No Data found !!!!!!! ");
	return null;
}
	
}

@Override
public Products updateProducts(Products prod) {
	prod.setUpdatedby(2020);
	prod.setUpdateddate(timestamp);
	Products products = productsRepository.save(prod);
	if(null == products) {
		return null;
	}
	return products;
	
}

Timestamp timestamp = new Timestamp(System.currentTimeMillis());

@Override
public Products createProducts(Products prod) {
	logger.info("created new product data !!!!!!! " +prod.getProdid());
	prod.setCreatedby(2020);
	prod.setCreateddate(timestamp);
	prod.setUpdatedby(2020);
	prod.setUpdateddate(timestamp);

	Products p = productsRepository.save(prod);
//	if(null == p) {
//		logger.info("created new product data !!!!!!! ");
//	p = productsRepository.save(prod);
//	}else {
//		logger.info("product data existed !!!!!!! ");
//	}
	return p;
}

@Override
public Products getProductById(Long prodid) {
	Products p = productsRepository.getOne(prodid);
	if(null != p) {
		logger.info("GET product data BY ID : " + p.getProdid() + " !!!!!!! ");
		return p;
	}
	logger.info("GET product data BY ID : " + p.getProdid() + " is NULL !!!!!!! ");
	return null;
	
}

//@Override
//public List<Products> getProductsSearchingByParam(String param) {
//	List<Products> p = productsRepository.findByProdcat(param);
//	return p;
//}

@Override
public List<Products> getProductsSearchingByParam(String param) {
	String sql = "SELECT p.* FROM products p where p.prodname ilike'%"+param+"%'  order by prodname";
			//and p.qty > 1000 order by prodname";
	logger.info("sql !!!!!!!!!!! : " + sql);
	List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);			
	List<Products> result = new ArrayList<Products>();
	for(Map<String, Object> row:rows){
		Products p = new Products();
		p.setProdid((Long)row.get("prodid"));
		p.setProdcat((String)row.get("prodcat"));
		p.setProddesc((String)row.get("proddesc"));
		p.setProdname((String)row.get("prodname"));
		p.setQty((Integer)row.get("qty"));
		result.add(p);
	}			
	return result;
}

}
