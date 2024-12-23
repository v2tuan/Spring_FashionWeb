package com.fashionweb.Controllers.web;

import com.fashionweb.Entity.Account;
import com.fashionweb.dto.response.ProductResponeDTO;
import com.fashionweb.repository.IProductRepository;
import com.fashionweb.service.IStorageService;
import com.fashionweb.service.Impl.AccountService;
import com.fashionweb.service.Impl.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home")
public class WebController {
    @Autowired
    AccountService accountService;
    @Autowired
    AddressService addressService;
    @Autowired
    private IStorageService storageService;
    @Autowired
    IProductRepository productRepository;

    @GetMapping
    String home(Model model) {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        Optional<Account> account = accountService.getAccounts(email);

        if (account.isPresent()) {
            // Đẩy dữ liệu vào model
            model.addAttribute("avatar", account.get().getAvatar());
        }

        List<ProductResponeDTO> listProduct = productRepository.findTopSellingProductSummaries();
        model.addAttribute("products", listProduct);
        return "web/home";
    }

    @GetMapping("/user-register")
    String userRegister() { return "web/user-register"; }

    @GetMapping("/user-login")
    String userLogin() { return "web/user-login"; }


    @GetMapping("/check-out")
    String checkOut() {
        return "web/shop/check_out";
    }

    @GetMapping("/shopping-cart")
    String shoppingCart() {
        return "web/shop/shopping_cart";
    }

    @GetMapping("/profile")
    String profile(){
        return "web/my_profile";
    }


    @PostMapping("/deladdr/{id}")
    String deleteAccount(@PathVariable long id, Model model){
        if (addressService.deleteAddress(id)) {
            model.addAttribute("errorMessage", "Xóa thành công");
        }
        return "web/manager_address";
    }

    @GetMapping("/files/")
    public ResponseEntity<Resource> serveDefaultFile() {
        return serveFile("default.jpg");
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Resource file = storageService.loadAsResource(filename);
            String contentType = Files.probeContentType(Paths.get(file.getURI()));
            if (contentType == null) {
                contentType = "image/jpeg";
            }

            return ResponseEntity.ok()
                    .header("Content-Type", contentType != null ? contentType : "application/octet-stream")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null); // Trả về 404 nếu không tìm thấy file
        }
    }

}
