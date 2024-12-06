package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.*;
import com.fashionweb.Entity.Embeddable.ProductImagesId;
import com.fashionweb.Entity.Embeddable.SizeId;
import com.fashionweb.dto.request.product.ProductDTO;
import com.fashionweb.service.IStorageService;
import com.fashionweb.service.Impl.*;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService CategoryService;

    @Autowired
    private BrandService BrandService;

    @Autowired
    private SubcategoryService SubcategoryService;

    @Autowired
    private BrandService brandService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private FileSystemStorageService storageService;
    @Autowired
    private SizeService SizeService;
    @Autowired
    private ProdImageService prodImageService;

    public List<Map<String, Object>> simplifiedImages(List<ProdImage> images) {
        return images.stream().map(item -> {
            Map<String, Object> items = new HashMap<>();
            items.put("imgId", item.getProductImageId());
            return items;
        }).toList();
    }

    public List<Map<String, Object>> simplifiedSizes(List<Size> sizes) {
        return sizes.stream().map(item -> {
            Map<String, Object> items = new HashMap<>();
            items.put("sizeId", item.getId());
            return items;
        }).toList();
    }

    public List<Map<String, Object>> simplifiedReviews(List<ProdReview> reviews) {
        return reviews.stream().map(item -> {
            Map<String, Object> items = new HashMap<>();
            items.put("reviewId", item.getReviewId());
            return items;
        }).toList();
    }

    public List<Map<String, Object>> simplifiedProduct(List<Product> products) {
        return products.stream().map(item -> {
            Map<String, Object> items = new HashMap<>();
            items.put("id", item.getProdId());
            items.put("name", item.getProdName());
            items.put("description", item.getDescription());
            items.put("regular", item.getRegular());
            items.put("promo", item.getPromo());
            items.put("status", item.getStatus());
            items.put("totalQuantity", item.getTotalQuantity());
            items.put("imgIds", simplifiedImages(item.getImages()));
            items.put("createDate", item.getCreateDate());
            items.put("brandId", item.getBrand().getBrandId());
            items.put("subCatId", item.getSubcategory().getSubCateId());
            items.put("sizeIds", simplifiedSizes(item.getSizes()));
            items.put("reviewIds", simplifiedReviews(item.getProductReviews()));
            return items;
        }).toList();
    }

    @GetMapping("/products")
    @ResponseBody
    ResponseEntity<?> getProducts(){
        List<Product> products = productService.getAllProducts();

        List<Map<String, Object>> validatedProducts = simplifiedProduct(products);

        return ResponseEntity.ok(validatedProducts);
    }

    @GetMapping("/productlist")
    String showProductList(Model model) {
        model.addAttribute("subcategories", SubcategoryService.getAll());

        return "admin/product_list";
    }

    @GetMapping("/addproduct")
    public String AddProductForm(Model model) {
        List<Category> categories = CategoryService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("brands", BrandService.getAll());
        model.addAttribute("subcategories", SubcategoryService.getAll());

        model.addAttribute("product", new Product());
        return "admin/addOrEditProduct";
    }

    @PostMapping("/createproduct")
    @ResponseBody
    public ResponseEntity<?> createProduct(@ModelAttribute @Valid ProductDTO productDto) {
        Product product = new Product();

        Optional<Brand> brnd = brandService.findById(productDto.getBrandId());
        Optional<Subcategory> subcat = subcategoryService.getById(productDto.getSubCateId());
        if (brnd.isPresent() && subcat.isPresent()){
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
                    SizeService.createSize(size);
                }
            }

            return ResponseEntity.ok("Thêm sản phẩm thành công");
        }
        else {
            return ResponseEntity.badRequest().body("Thêm sản phẩm thất bại");
        }
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
    String order_list(){
        return "admin/order_list";
    }

    @GetMapping("/reviews")
    String reviews() { return "admin/reviews"; }

    @GetMapping("/profile-settings")
    String profileSettings() { return "admin/profile_settings"; }

    @GetMapping("/order-detail")
    String orderDetail() {
        return "admin/order_detail";
    }
}
