package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.Brand;
import com.fashionweb.dto.request.brand.BrandDTO2;
import com.fashionweb.service.IBrandService;
import com.fashionweb.service.IStorageService;
import com.fashionweb.service.Impl.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/brands")
public class BrandController {
    @Autowired
    private IBrandService brandService;
    @Autowired
    private BrandService bService;

    @GetMapping("/all")
    public String getAllBrands(Model model) {
        List<BrandDTO2> brandDTO2s =  bService.getBrandDTO2s();

        model.addAttribute("brands", brandDTO2s);

        return "admin/brands/addOrEdit";
    }


    @GetMapping("/search")
    public String searchBrandByName(@RequestParam String brandName, Model model) {
        Optional<Brand> brandOptional = brandService.findByBrandName(brandName);

        Brand brand = brandOptional.get();
        BrandDTO2 response = new BrandDTO2(
                brand.getBrandId(),
                brand.getBrandName(),
                brand.getImages(),
                0L
        );

        model.addAttribute("brand", response);

        return "admin/brands/addOrEdit";
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
                savedBrand.getBrandId(),
                savedBrand.getBrandName(),
                savedBrand.getImages(),
                0L
        );

        return ResponseEntity.ok(response);
    }

    // Cập nhật brand
    @GetMapping("/edit/{id}")
    public String editBrand(@PathVariable("id") Long brandId, Model model) {
        Optional<Brand> brandOptional = brandService.findById(brandId);

        model.addAttribute("brand", brandOptional.get());

        return "/admin/brands";
    }

    @PostMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") Long brandId) {
        brandService.deleteBrand(brandId);
        return "/admin/brands";
    }

}
