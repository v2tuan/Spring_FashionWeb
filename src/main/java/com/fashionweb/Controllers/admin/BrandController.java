package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.Brand;
import com.fashionweb.dto.request.brand.BrandDTO2;
import com.fashionweb.service.IBrandService;
import com.fashionweb.service.Impl.BrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        return "redirect:/admin/brands/all";
    }


    @PostMapping("/createbrand")
    @ResponseBody
    public String createBrand(@ModelAttribute BrandDTO2 brandDTO, RedirectAttributes redirectAttributes) {
        String images = brandDTO.getImages();

        Brand brand = new Brand();
        brand.setBrandName(brandDTO.getBrandName());
        brand.setImages(images);

        Brand savedBrand = brandService.createBrand(brand);

        redirectAttributes.addFlashAttribute("message", "Thêm thành công!");
        return "redirect:/admin/brands/all";
    }



    @GetMapping("/editbrand/{id}")
    public String showEditBrand(@PathVariable Long id, Model model) {
        Optional<Brand> optionalBrand = brandService.findById(id);

        if (optionalBrand.isPresent()) {
            Brand brand = optionalBrand.get();

            BrandDTO2 brandDTO2 = new BrandDTO2();
            brandDTO2.setBrandId(brand.getBrandId());
            brandDTO2.setBrandName(brand.getBrandName());
            brandDTO2.setImages(brand.getImages());

            model.addAttribute("brand", brandDTO2);

            return "admin/edit_brand";
        } else {
            return "redirect:/admin/brands/all";
        }
    }


    @PostMapping("/updatebrand/{id}")
    @ResponseBody
    public String updateBrand(@PathVariable Long id,
                              @ModelAttribute @Valid BrandDTO2 brandDTO2,
                              @RequestParam("images")
                              RedirectAttributes redirectAttributes) {
        Optional<Brand> optionalBrand = brandService.findById(id);

        if (optionalBrand.isPresent()) {
            Brand brand = optionalBrand.get();

            brand.setBrandName(brandDTO2.getBrandName());
            brand.setImages(brandDTO2.getImages());

            brandService.updateBrand(brand);

            redirectAttributes.addFlashAttribute("message", "Cập nhật thương hiệu thành công!");
            return "redirect:/admin/brands/all";

        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy thương hiệu cần cập nhật!");
            return "redirect:/admin/brands/all";
        }
    }


    @PostMapping("/deletebrand/{id}")
    public String deleteBrand(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Brand> optionalBrand = brandService.findById(id);

        if (optionalBrand.isPresent()) {
            brandService.deleteBrand(id);

            redirectAttributes.addFlashAttribute("message", "Xóa thương hiệu thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy thương hiệu để xóa!");
        }

        return "redirect:/admin/brands/all";
    }


}
