package com.fashionweb.Controllers;

import com.fashionweb.Entity.Discount;
import com.fashionweb.service.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {
    @Autowired
    private IDiscountService discountService;

    // Create a new discount
    @PostMapping
    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount) {
        Discount createdDiscount = discountService.save(discount);
        return ResponseEntity.ok(createdDiscount);
    }

    // Get all discounts
    @GetMapping
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        List<Discount> discounts = discountService.findAll();
        return ResponseEntity.ok(discounts);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable Long id, @RequestBody Discount discountDetails) {
        Optional<Discount> optionalDiscount = discountService.findById(id);
        if (optionalDiscount.isPresent()) {
            Discount discount = optionalDiscount.get();
            discount.setVoucher(discountDetails.getVoucher());
            discount.setDescription(discountDetails.getDescription());
            discount.setDiscountPercentage(discountDetails.getDiscountPercentage());
            discount.setStartDate(discountDetails.getStartDate());
            discount.setEndDate(discountDetails.getEndDate());
            discount.setCreateDate(discountDetails.getCreateDate());
            Discount updatedDiscount = discountService.save(discount);
            return ResponseEntity.ok(updatedDiscount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a discount
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        Optional<Discount> optionalDiscount = discountService.findById(id);
        if (optionalDiscount.isPresent()) {
            discountService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
