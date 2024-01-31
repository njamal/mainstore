package com.my.mainstore.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.my.mainstore.model.Entitybody;

@Repository
public interface EntityBodyRepository extends JpaRepository<Entitybody, Long>{

}
