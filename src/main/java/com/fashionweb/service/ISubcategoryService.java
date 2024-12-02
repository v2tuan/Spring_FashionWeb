package com.fashionweb.service;

import com.fashionweb.Entity.Subcategory;

import java.util.List;
import java.util.Optional;

public interface ISubcategoryService {

    List<Subcategory> getAll();
    Optional<Subcategory> getById(Long id);
    Optional<Subcategory> getByName(String name);
    void createSubcategory(Subcategory subcategory);
    void updateSubcategory(Subcategory subcategory);
    void deleteSubcategory(Long id);

}
