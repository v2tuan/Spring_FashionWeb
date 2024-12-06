package com.fashionweb.Controllers;

import com.fashionweb.Entity.*;
import com.fashionweb.dto.request.discount.DiscountDTO;
import com.fashionweb.service.IDiscountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class DiscountController {
    @Autowired
    private IDiscountService discountService;

//    // Create a new discount
//    @PostMapping
//    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount) {
//        Discount createdDiscount = discountService.save(discount);
//        return ResponseEntity.ok(createdDiscount);
//    }

    // Get all discounts
    @GetMapping("/discounts")
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        List<Discount> discounts = discountService.findAll();
        return ResponseEntity.ok(discounts);
    }

    public List<Long> getOrderList(List<Order> orders){
        return orders.stream().map(Order::getOrderId).toList();
    }

    public List<DiscountDTO> discountDTOS(List<Discount> discounts){
        return discounts.stream().map(discount -> {
            DiscountDTO discountDTO = new DiscountDTO();
            discountDTO.setDiscountId(discount.getDiscountId());
            discountDTO.setVoucher(discount.getVoucher());
            discountDTO.setDescription(discount.getDescription());
            discountDTO.setDiscountPercentage(discount.getDiscountPercentage());
            discountDTO.setStartDate(discount.getStartDate());
            discountDTO.setEndDate(discount.getEndDate());
            discountDTO.setCreateDate(discount.getCreateDate());
            discountDTO.setOrderIds(getOrderList(discount.getOrders()));
            return discountDTO;
        }).toList();
    }

    // Get all discounts
    @GetMapping("/discountlist")
    String showAllDiscount(Model model) {
        model.addAttribute("discounts", discountDTOS(discountService.findAll()));
        return "admin/discount_list";
    }


    // Get all discounts
    @GetMapping("/adddiscount")
    String showAddDiscount(Model model) {
        model.addAttribute("discounts", discountDTOS(discountService.findAll()));
        return "admin/create_discount";
    }

    @PostMapping("/creatediscount")
    @ResponseBody
    public ResponseEntity<?> createDiscount(@RequestBody @Valid DiscountDTO discountDTODto) {
        Discount discount = new Discount();

        discount.setVoucher(discountDTODto.getVoucher());
        discount.setDescription(discountDTODto.getDescription());
        discount.setDiscountPercentage(discountDTODto.getDiscountPercentage());
        discount.setStartDate(discountDTODto.getStartDate());
        discount.setEndDate(discountDTODto.getEndDate());
        discount.setCreateDate(discountDTODto.getCreateDate());

        discountService.save(discount);
        return ResponseEntity.ok("Thêm mã giảm giá thành công");

    }

    @PutMapping("/{id}")
    @ResponseBody
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
