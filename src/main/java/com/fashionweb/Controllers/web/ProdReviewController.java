package com.fashionweb.Controllers.web;

import com.fashionweb.Entity.Embeddable.ProdReviewsId;
import com.fashionweb.Entity.ProdReview;
import com.fashionweb.dto.request.proreview.ProdReviewDTO;
import com.fashionweb.service.IStorageService;
import com.fashionweb.service.Impl.ProdReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/prod-reviews")
public class ProdReviewController {
    @Autowired
    private ProdReviewService prodReviewService;

    @Autowired
    private IStorageService storageService;

    @GetMapping
    public ResponseEntity<List<ProdReviewDTO>> getAllProdReviews() {
        List<ProdReview> reviews = prodReviewService.getAllProdReviews();
        List<ProdReviewDTO> reviewDTOs = reviews.stream()
                .map(review -> new ProdReviewDTO(
                        review.getReviewId().getProdId(),
                        review.getReviewId().getAccId(),
                        review.getRating(),
                        review.getComment(),
                        review.getImages()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviewDTOs);
    }

//    @GetMapping("/add")
//    String addProdReview() {
//        return "web/testpopupreview";
//    }

    @PostMapping("/createprodreview")
    @ResponseBody
    public ResponseEntity<ProdReviewDTO> createProdReview(
            @ModelAttribute @Valid ProdReviewDTO prodReviewDTO,
            @RequestParam(required = false) MultipartFile file
    ) {
        String fileName = null;

        // Xử lý lưu file ảnh nếu có
        if (file != null && !file.isEmpty()) {
            fileName = storageService.getStorageFileName(file, String.valueOf(System.currentTimeMillis()));
            storageService.store(file, fileName);
        }

        // Tạo đối tượng ProdReview từ DTO
        ProdReview prodReview = new ProdReview();
        prodReview.setReviewId(new ProdReviewsId(prodReviewDTO.getProdId(), prodReviewDTO.getAccId()));
        prodReview.setComment(prodReviewDTO.getComment());
        prodReview.setImages(fileName); // Nếu có file, gán tên file vào hình ảnh

        // Lưu đánh giá vào cơ sở dữ liệu
        ProdReview savedReview = prodReviewService.createProdReview(prodReview);

        // Trả về DTO sau khi lưu
        ProdReviewDTO response = new ProdReviewDTO(
                savedReview.getReviewId().getProdId(),
                savedReview.getReviewId().getAccId(),
                savedReview.getRating(),
                savedReview.getComment(),
                savedReview.getImages()
        );

        return ResponseEntity.ok(response);
    }


}
