package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.Embeddable.ProdReviewsId;
import com.fashionweb.Entity.ProdReview;
import com.fashionweb.dto.request.proreview.ProdReviewDTO;
import com.fashionweb.dto.request.proreview.ReviewSummaryDTO;
import com.fashionweb.repository.IAccountRepository;
import com.fashionweb.service.IStorageService;
import com.fashionweb.service.Impl.ProdReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fashionweb.Entity.Account;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/prod-reviews")
public class ProdReviewController {
    @Autowired
    private ProdReviewService prodReviewService;

    @Autowired
    private IStorageService storageService;

    @Autowired
    private IAccountRepository accountRepository;

    @GetMapping("/prodreviewlist")
    public String getProdReviewSummaries(Model model) {
        List<ProdReview> reviews = prodReviewService.getAllProdReviews();
        List<ReviewSummaryDTO> reviewSummaries = reviews.stream()
                .map(review -> {
                    Long accId = review.getReviewId().getAccId();
                    String fullname = accountRepository.findById(accId)
                            .map(Account::getFullname)
                            .orElse("Anonymous");
                    return new ReviewSummaryDTO(
                            accId,
                            fullname,
                            review.getComment(),
                            review.getRating()
                    );
                })
                .collect(Collectors.toList());

        model.addAttribute("reviews", reviewSummaries);

        return "web/testpopupreview"; //test
    }


    @PostMapping("/createprodreview")
    @ResponseBody
    public ResponseEntity<ProdReviewDTO> createProdReview(
            @ModelAttribute @Valid ProdReviewDTO prodReviewDTO,
            @RequestParam(required = false) MultipartFile file
    ) {
        String fileName = null;

        if (file != null && !file.isEmpty()) {
            fileName = storageService.getStorageFileName(file, String.valueOf(System.currentTimeMillis()));
            storageService.store(file, fileName);
        }

        ProdReview prodReview = new ProdReview();
        prodReview.setReviewId(new ProdReviewsId(prodReviewDTO.getProdId(), prodReviewDTO.getAccId()));
        prodReview.setComment(prodReviewDTO.getComment());
        prodReview.setImages(fileName);
        prodReview.setRating(prodReviewDTO.getRating());


        ProdReview savedReview = prodReviewService.createProdReview(prodReview);

        ProdReviewDTO response = new ProdReviewDTO(
                savedReview.getReviewId().getProdId(),
                savedReview.getReviewId().getAccId(),
                savedReview.getComment(),
                savedReview.getImages(),
                savedReview.getRating()
                );

        return ResponseEntity.ok(response);
    }


}
