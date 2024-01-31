package com.my.mainstore.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.my.mainstore.model.Brands;

@Repository
public interface BrandsRepository extends JpaRepository<Brands, Long>{

}
