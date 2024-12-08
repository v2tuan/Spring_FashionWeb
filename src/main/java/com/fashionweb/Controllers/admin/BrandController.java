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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/brands")
public class BrandController {
    @Autowired
    private IBrandService brandService;

    @GetMapping("/add")
    public String add() {
        return "admin/brands/addOrEdit";
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<BrandDTO2>> getAllBrands() {
        List<Brand> brands = brandService.getAll();
        List<BrandDTO2> brandDTOList = brands.stream().map(brand ->
                new BrandDTO2(
                        brand.getBrandName(),
                        brand.getImages()
                )
        ).toList();
        return ResponseEntity.ok(brandDTOList);
    }


    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<BrandDTO2> searchBrandByName(@RequestParam String brandName) {
        Optional<Brand> brandOptional = brandService.findByBrandName(brandName);

        if (brandOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Brand brand = brandOptional.get();
        BrandDTO2 response = new BrandDTO2(
                brand.getBrandName(),
                brand.getImages()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/createbrand")
    @ResponseBody
    public ResponseEntity<BrandDTO2> createBrand(
            @ModelAttribute BrandDTO2 brandDTO) {

        String images = brandDTO.getImages();

        Brand brand = new Brand();
        brand.setBrandName(brandDTO.getBrandName());
        brand.setImages(images);

        Brand savedBrand = brandService.createBrand(brand);

        BrandDTO2 response = new BrandDTO2(
                savedBrand.getBrandName(),
                savedBrand.getImages()
        );

        return ResponseEntity.ok(response);
    }
}
