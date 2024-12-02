package com.fashionweb.repository;

import com.fashionweb.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCateName(String name);

}
