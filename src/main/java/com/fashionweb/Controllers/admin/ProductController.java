package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.*;
import com.fashionweb.Entity.Embeddable.ProductImagesId;
import com.fashionweb.Entity.Embeddable.SizeId;
import com.fashionweb.dto.request.category.SubcategoryListDTO;
import com.fashionweb.dto.request.product.ProductDTO;
import com.fashionweb.dto.request.product.ProductListDTO;
import com.fashionweb.service.Impl.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    BrandService brandService;
    @Autowired
    SubcategoryService subcategoryService;
    @Autowired
    private FileSystemStorageService storageService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private ProdImageService prodImageService;

    public List<ProductListDTO> simplifiedProduct(List<Product> products) {
        return products.stream().map(product -> {
            ProductListDTO productListDTO = new ProductListDTO();
            productListDTO.setProdId(product.getProdId());
            productListDTO.setProdName(product.getProdName());
            productListDTO.setDescription(product.getDescription());
            productListDTO.setRegular(product.getRegular());
            productListDTO.setPromo(product.getPromo());
            productListDTO.setStatus(product.getStatus() != null && product.getStatus());
            productListDTO.setCreateDate(product.getCreateDate());
            productListDTO.setImgURL(productService.getImgName(product.getImages()));
            productListDTO.setBrandId(product.getBrand().getBrandId());
            productListDTO.setSubCateId(product.getSubcategory().getSubCateId());
            return productListDTO;
        }).toList();
    }

    @GetMapping("/products")
    @ResponseBody
    ResponseEntity<?> getProducts() {
        List<ProductListDTO> ProductLists = productService.findAllProductList();
        return ResponseEntity.ok(ProductLists);
    }

    @GetMapping("/productlist")
    String showProductList(Model model) {
        List<SubcategoryListDTO> Subcategories = subcategoryService.findAllSubcategoryList();
        List<ProductListDTO> ProductLists = productService.findAllProductList();
        model.addAttribute("subcategories", Subcategories);
        model.addAttribute("products", ProductLists);

        return "admin/product_list";
    }

    @GetMapping("/addproduct")
    public String AddProductForm(Model model) {
        List<Brand> Brands = brandService.getAll();
        List<SubcategoryListDTO> Subcategories = subcategoryService.findAllSubcategoryList();

        model.addAttribute("brands", Brands);
        model.addAttribute("subcategories", Subcategories);
        model.addAttribute("product", new Product());

        return "admin/addOrEditProduct";
    }

    @PostMapping("/createproduct")
    @ResponseBody
    public ResponseEntity<?> createProduct(@ModelAttribute @Valid ProductDTO productDto) {
        Product product = new Product();

        Optional<Brand> brnd = brandService.findById(productDto.getBrandId());
        Optional<Subcategory> subcat = subcategoryService.getById(productDto.getSubCateId());
        if (brnd.isPresent() && subcat.isPresent()) {
            product.setProdName(productDto.getProdName());
            product.setDescription(productDto.getDescription());
            product.setRegular(productDto.getRegular());
            product.setPromo(productDto.getPromo());
            product.setStatus(productDto.getStatus());
            product.setCreateDate(java.sql.Date.valueOf(LocalDate.now(ZoneId.systemDefault())));

            product.setBrand(brnd.get());
            product.setSubcategory(subcat.get());
            Product productadd = productService.createProduct(product);

//            for (int i = 0; i < productDto.getImages().size(); i++) {
////                throw new RuntimeException(productDto.getImages().get(i));
//                System.out.println(productDto.getImages().get(i));
//                String imageBase64 = productDto.getImages().get(i);
//
//                String base64Image = "data:image/png;base64,iVBORw0K..."; // Chuỗi Base64 của ảnh
//                String fileName = String.valueOf(System.currentTimeMillis()) + ".png"; // Tên file muốn lưu
//                storageService.storeBase64Image(base64Image, fileName);
//            }

            if(productDto.getFiles()!=null) {
                int i = 0;
                for (var image : productDto.getFiles()) {
                    // Tạo tên file duy nhất hoặc từ một ID nào đó
                    String fileName = storageService.getStorageFileName(image, String.valueOf(System.currentTimeMillis()));
                    // Lưu file vào hệ thống
                    storageService.store(image, fileName);
                    ProdImage prodImage = new ProdImage();
                    prodImage.setProduct(productadd);
                    prodImage.setImgURL(fileName);
                    ProductImagesId imagesId = new ProductImagesId();
                    imagesId.setProdId(productadd.getProdId());
                    imagesId.setStt(i);
                    prodImage.setProductImageId(imagesId);
                    prodImageService.createProdImage(prodImage);
                    i++;
                }
            }
            if(productDto.getSizes() != null) {
                for (var sizeitem : productDto.getSizes()) {
                    Size size = new Size();
                    size.setQuantity(sizeitem.getQuantity());
                    SizeId sizeId = new SizeId();
                    sizeId.setProdId(productadd.getProdId());
                    sizeId.setSizeName(sizeitem.getName());
                    size.setId(sizeId);
                    sizeService.createSize(size);
                }
            }

            return ResponseEntity.ok("Thêm sản phẩm thành công");
        } else {
            return ResponseEntity.badRequest().body("Thêm sản phẩm thất bại");
        }
    }

    @GetMapping("/editproduct/id={prodId}")
    public String EditProductForm(@PathVariable("prodId") Long prodId, Model model) {
        List<Brand> Brands = brandService.getAll();
        List<Subcategory> Subcategories = subcategoryService.getAll();
        Product product = productService.getProduct(prodId).get();

        model.addAttribute("brands", Brands);
        model.addAttribute("subcategories", Subcategories);
        model.addAttribute("product", productService.product2DTO(product));

        return "admin/editProduct";
    }

    @PostMapping("/deleteproduct/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            model.addAttribute("message", "Đã xóa sản phẩm");
        } else {
            model.addAttribute("error", "Xóa sản phẩm thất bại");
        }
        return "redirect:/admin/productlist";
    }

    @GetMapping("/orderlist")
    String order_list() {
        return "admin/order_list";
    }

    @GetMapping("/reviews")
    String reviews() {
        return "admin/reviews";
    }

    @GetMapping("/profile-settings")
    String profileSettings() {
        return "admin/profile_settings";
    }

    @GetMapping("/order-detail")
    String orderDetail() {
        return "admin/order_detail";
    }
}
