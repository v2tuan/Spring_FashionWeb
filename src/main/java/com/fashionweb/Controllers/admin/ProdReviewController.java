package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.Embeddable.ProdReviewsId;
import com.fashionweb.Entity.ProdReview;
import com.fashionweb.Entity.Product;
import com.fashionweb.dto.request.proreview.ProdReviewDTO;
import com.fashionweb.dto.request.proreview.ReviewSummaryDTO;
import com.fashionweb.repository.IAccountRepository;
import com.fashionweb.service.IStorageService;
import com.fashionweb.service.Impl.ProdReviewService;
import com.fashionweb.service.Impl.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fashionweb.Entity.Account;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user/prod-reviews")
public class ProdReviewController {
    @Autowired
    private ProdReviewService prodReviewService;

    @Autowired
    private IStorageService storageService;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/prodreviewlist")
    public String getProdReviewSummaries(Model model) {
        List<ProdReview> reviews = prodReviewService.getAllProdReviews();
        List<ReviewSummaryDTO> reviewSummaries = reviews.stream()
                .map(review ->
                        new ReviewSummaryDTO(
                                review.getAccount().getAccId(),
                                review.getAccount().getFullname(),
                                review.getAccount().getAvatar(),
                                review.getComment(),
                                review.getRating())
                ).collect(Collectors.toList());

        model.addAttribute("reviews", reviewSummaries);

        return "web/testpopupreview"; //test
    }


    @PostMapping("/createprodreview")
    @ResponseBody
    public ResponseEntity<String> createProdReview(
            @ModelAttribute @Valid ProdReviewDTO prodReviewDTO,
            @RequestParam(required = false) MultipartFile images
    ) {
        String fileName = null;

        if (images != null && !images.isEmpty()) {
            fileName = storageService.getStorageFileName(images, String.valueOf(System.currentTimeMillis()));
            storageService.store(images, fileName);
        }

        ProdReview prodReview = new ProdReview();
        Product prot = productService.getProduct(prodReviewDTO.getProdId()).orElseThrow();
        Account acc = accountRepository.findById(prodReviewDTO.getAccId()).orElseThrow();
        prodReview.setReviewId(new ProdReviewsId(prodReviewDTO.getProdId(),prodReviewDTO.getAccId()));
        prodReview.setAccount(acc);
        prodReview.setProduct(prot);
        prodReview.setComment(prodReviewDTO.getComment());
        prodReview.setCreateDate(LocalDate.now());
        prodReview.setImages(fileName);
        prodReview.setRating(prodReviewDTO.getRating());

        prodReviewService.createProdReview(prodReview);

        return ResponseEntity.ok("Đánh giá sản phẩm đã được gửi thành công!");
    }


}
