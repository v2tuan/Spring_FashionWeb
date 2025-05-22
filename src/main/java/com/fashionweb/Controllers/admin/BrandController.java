package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.Brand;
import com.fashionweb.dto.request.brand.BrandDTO2;
import com.fashionweb.service.IBrandService;
import com.fashionweb.service.IStorageService;
import com.fashionweb.service.Impl.BrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    @Autowired
    private IStorageService storageService;

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
    public String createBrand(@ModelAttribute BrandDTO2 brandDTO, @RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
        String fileName = "";
            // Tạo tên file duy nhất hoặc từ một ID nào đó
            fileName = storageService.getStorageFileName(file, String.valueOf(System.currentTimeMillis()));
            // Lưu file vào hệ thống
            storageService.store(file, fileName);

        Brand brand = new Brand();
        brand.setBrandName(brandDTO.getBrandName());
        brand.setImages(fileName);

        Brand savedBrand = brandService.createBrand(brand);

        redirectAttributes.addFlashAttribute("message", "Thêm thành công!");
        return "redirect:/admin/brands/all";
    }

    @GetMapping("/editbrand/{id}")
    public String showEditBrand(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Brand> optionalBrand = brandService.findById(id);
        if (optionalBrand.isPresent()) {
            Brand brand = optionalBrand.get();
            BrandDTO2 brandDTO2 = new BrandDTO2(
                    brand.getBrandId(),
                    brand.getBrandName(),
                    brand.getImages(),
                    0L // Giả sử prodCount không được dùng lúc này
            );
            model.addAttribute("brands", bService.getBrandDTO2s()); // Để hiển thị danh sách
            model.addAttribute("selectedBrand", brandDTO2); // Để frontend biết brand đang edit
            return "redirect:/admin/brands/all"; // Trả về cùng template, modal sẽ hiển thị
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy thương hiệu!");
            return "redirect:/admin/brands/all";
        }
    }

    // Xử lý update brand
    @PostMapping("/editbrand/{id}")
    public String updateBrand(@PathVariable Long id,
                              @RequestParam("brandName") String brandName,
                              @RequestParam(value = "file", required = false) MultipartFile file,
                              RedirectAttributes redirectAttributes) {
        try {
            Optional<Brand> optionalBrand = brandService.findById(id);
            if (optionalBrand.isPresent()) {
                Brand brand = optionalBrand.get();
                brand.setBrandName(brandName);

                if (file != null && !file.isEmpty()) {
                    // Upload file mới
                    String fileName = storageService.getStorageFileName(file, String.valueOf(System.currentTimeMillis()));
                    storageService.store(file, fileName);
                    brand.setImages(fileName);
                }

                brandService.updateBrand(brand);
                redirectAttributes.addFlashAttribute("message", "Cập nhật thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy thương hiệu!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật: " + e.getMessage());
        }
        return "redirect:/admin/brands/all";
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
