package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Subcategory;
import com.fashionweb.repository.ISubcategoryRepository;
import com.fashionweb.service.ISubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubcategoryService implements ISubcategoryService {

    @Autowired
    ISubcategoryRepository subcategoryRepos;

    @Override
    public List<Subcategory> getAll() {
        return subcategoryRepos.findAll();
    }

    @Override
    public Optional<Subcategory> getById(Long id) {
        return subcategoryRepos.findById(id);
    }

    @Override
    public Optional<Subcategory> getByName(String name) {
        return subcategoryRepos.findBySubCateName(name);
    }

    @Override
    public void createSubcategory(Subcategory subcategory) {
        subcategoryRepos.save(subcategory);
    }

    @Override
    public void updateSubcategory(Subcategory subcategory) {
        if(subcategoryRepos.existsById(subcategory.getSubCateId())) {
            throw new RuntimeException("Không tìm thấy 'Subcategory' với id(" + subcategory.getSubCateId() + ")");
        }

        subcategoryRepos.save(subcategory);
    }

    @Override
    public void deleteSubcategory(Long id) {
        if(subcategoryRepos.existsById(id)) {
            throw new RuntimeException("Không tìm thấy 'Subcategory' với id(" + id + ")");
        }

        subcategoryRepos.deleteById(id);
    }
}
