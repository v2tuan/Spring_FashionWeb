package com.fashionweb.service.Impl;

import com.fashionweb.repository.ICategoryRepository;
import com.fashionweb.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    ICategoryRepository categoryRepos;



}
