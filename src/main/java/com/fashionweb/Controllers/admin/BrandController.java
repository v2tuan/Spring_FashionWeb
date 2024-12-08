package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.Brand;
import com.fashionweb.dto.request.brand.BrandDTO2;
import com.fashionweb.service.IBrandService;
import com.fashionweb.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/brands")
public class BrandController {
    @Autowired
    private IBrandService brandService;

    @Autowired
    private IStorageService storageService;

    @GetMapping("/add")
    public String add() {
        return "admin/brands/addOrEdit";
    }

    @PostMapping("/createbrand")
    @ResponseBody
    public ResponseEntity<BrandDTO2> createBrand(
            @ModelAttribute BrandDTO2 brandDTO,
            @RequestParam(required = false) MultipartFile file) {

        String fileName = null;

        if (file != null && !file.isEmpty()) {
            fileName = storageService.getStorageFileName(file, String.valueOf(System.currentTimeMillis()));
            storageService.store(file, fileName);
        }

        Brand brand = new Brand();
        brand.setBrandName(brandDTO.getBrandName());
        brand.setImages(fileName);

        Brand savedBrand = brandService.createBrand(brand);

        BrandDTO2 response = new BrandDTO2(
                savedBrand.getBrandName(),
                savedBrand.getImages()
        );

        return ResponseEntity.ok(response);
    }

}
