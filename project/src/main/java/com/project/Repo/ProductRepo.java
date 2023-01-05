package com.project.Repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Product;


public interface ProductRepo extends JpaRepository<Product, Integer> {

}
