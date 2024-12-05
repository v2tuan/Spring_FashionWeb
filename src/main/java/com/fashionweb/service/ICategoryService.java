package com.fashionweb.service;

import com.fashionweb.Entity.Category;
import com.fashionweb.Entity.Subcategory;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    List<Category> findAll();
    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);
    void createCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Long id);
    List<Subcategory> findSubcategoriesByCategoryId(Long cateId);

}
