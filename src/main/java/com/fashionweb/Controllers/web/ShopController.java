package com.fashionweb.Controllers.web;

import com.fashionweb.dto.request.category.CategoryGridDTO;
import com.fashionweb.dto.request.product.ProductGridDTO;
import com.fashionweb.service.Impl.CategoryService;
import com.fashionweb.service.Impl.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/home/shop")
public class ShopController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping("/categories")
    @ResponseBody
    ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(categoryService.categoryGridDTOs(categoryService.findAll()));
    }

    @GetMapping("/subcategories")
    @ResponseBody
    ResponseEntity<?> getSubcategories() {
        return ResponseEntity.ok(categoryService.categoryGridDTOs(categoryService.findAll()));
    }

    @GetMapping("/products")
    @ResponseBody
    ResponseEntity<?> getProducts() {
        Pageable pageable = PageRequest.of(0,16);
        return ResponseEntity.ok(productService.findAllProductGridPageable(true, pageable));
    }

    @GetMapping("/productDetail")
    @ResponseBody
    ResponseEntity<?> getProductDetail(@RequestBody @Valid Long prodId) {
        return ResponseEntity.ok(productService.findProductDetailByProdId(prodId));
    }

    public List<List<ProductGridDTO>> groupedProductGrids(List<ProductGridDTO> productGridDTOs, int groupSize) {
        List<List<ProductGridDTO>> result = new ArrayList<>();
        List<ProductGridDTO> tempGroup = new ArrayList<>();

        for (ProductGridDTO product : productGridDTOs) {
            tempGroup.add(product);
            if (tempGroup.size() == groupSize) {
                result.add(new ArrayList<>(tempGroup));
                tempGroup.clear();
            }
        }

        if (!tempGroup.isEmpty()) {
            result.add(new ArrayList<>(tempGroup));
        }

        return result;
    }

    @GetMapping
    String shop(@RequestParam(value = "page", defaultValue = "0") int page,
                @RequestParam(value = "size", defaultValue = "16") int size,
                @RequestParam(value = "subCateId", required = false) Long subCateId,
                @RequestParam(value = "sort", required = false) String sort,
                Model model) {

        Set<String> validSortOptions = Set.of("latest", "rating", "rating-asc", "price", "price-desc");
        if (sort == null || !validSortOptions.contains(sort)) {
            sort = ""; // Default sort
        }

        Sort sorting = switch (sort) {
            case "latest" -> Sort.by(Sort.Direction.DESC, "createDate");
            case "rating" -> Sort.by(Sort.Direction.DESC, "rating");
            case "rating-asc" -> Sort.by(Sort.Direction.ASC, "rating");
            case "price" -> Sort.by(Sort.Direction.ASC, "promo");
            case "price-desc" -> Sort.by(Sort.Direction.DESC, "promo");
            default -> Sort.unsorted();
        };

        Pageable pageable = PageRequest.of(page, size, sorting);
        List<CategoryGridDTO> categoryGridDTOs = categoryService.categoryGridDTOs(categoryService.findAll());
        Page<ProductGridDTO> productGridDTOs = productService.findAllProductGridCriteriaPageable(subCateId, true, pageable);

        model.addAttribute("categories", categoryGridDTOs);
        model.addAttribute("products", productGridDTOs.getContent());
        model.addAttribute("prodCount", productGridDTOs.getTotalElements());
        model.addAttribute("selectedSubCateId", subCateId);
        model.addAttribute("selectedSorting", sort);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productGridDTOs.getTotalPages());

        return "web/shop/shop_content";
    }

    @GetMapping("/paginatedproducts")
    @ResponseBody
    ResponseEntity<Page<ProductGridDTO>> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int size,
            @RequestParam(defaultValue = "prodName") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return ResponseEntity.ok(productService.findAllProductGridPageable(true, pageable));
    }

    @GetMapping("/product-detail/id={prodId}")
    String productDetail(@PathVariable Long prodId, Model model) {
        model.addAttribute("products", productService.findProductDetailByProdId(prodId));
        return "web/shop/product_detail";
    }

}
