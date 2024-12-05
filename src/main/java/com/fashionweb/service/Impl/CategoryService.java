package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Category;
import com.fashionweb.Entity.Subcategory;
import com.fashionweb.repository.ICategoryRepository;
import com.fashionweb.repository.IProductRepository;
import com.fashionweb.repository.ISubcategoryRepository;
import com.fashionweb.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    ICategoryRepository categoryRepos;
    @Autowired
    ISubcategoryRepository subcategoryRepos;
    @Autowired
    IProductRepository productRepos;


    @Override
    public List<Category> findAll() {
        return categoryRepos.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepos.findById(id);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepos.findByCateName(name);
    }

    @Override
    public void createCategory(Category category) {
        categoryRepos.save(category);
    }

    @Override
    public void updateCategory(Category category) {
        if(categoryRepos.existsById(category.getCategoryId())) {
            throw new RuntimeException("Không tìm thấy 'Category' với id(" + category.getCategoryId() + ")");
        }

        categoryRepos.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        if(categoryRepos.existsById(id)) {
            throw new RuntimeException("Không tìm thấy 'Category' với id(" + id + ")");
        }

        categoryRepos.deleteById(id);
    }

    @Override
    public List<Subcategory> findSubcategoriesByCategoryId(Long cateId) {
        return subcategoryRepos.findAllByCategoryCategoryId(cateId);
    }

}
