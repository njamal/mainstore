package com.my.mainstore.data;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.my.mainstore.model.Products;

@Repository("productsRepository")
public interface ProductsRepository extends JpaRepository<Products, Long>{
//@Query("SELECT p FROM products p WHERE p.prodcat like'%'||:prodcat||'%'")
//public List<Products> findByProdcat(@Param("prodcat") String prodcat);
//	@Query("SELECT p FROM products p WHERE p.prodcat ilike  %?1%")
//	public List<Products> findByProdcat(String prodcat);
}
