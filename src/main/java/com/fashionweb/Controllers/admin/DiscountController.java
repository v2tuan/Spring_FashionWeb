package com.fashionweb.Controllers.admin;

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

    @GetMapping("/discountlist")
    String showAllDiscount(Model model) {
        model.addAttribute("discounts", discountDTOS(discountService.findAll()));
        return "admin/discount_list";
    }


    @GetMapping("/adddiscount")
    String showAddDiscount() {
        return "admin/create_discount";
    }

    @PostMapping("/creatediscount")
    public ResponseEntity<?> createDiscount(@ModelAttribute @Valid DiscountDTO discountDTO) {
        Discount discount = new Discount();
        discount.setVoucher(discountDTO.getVoucher());
        discount.setDescription(discountDTO.getDescription());
        discount.setDiscountPercentage(discountDTO.getDiscountPercentage());
        discount.setStartDate(discountDTO.getStartDate());
        discount.setEndDate(discountDTO.getEndDate());
        discount.setCreateDate(discountDTO.getCreateDate());
        discountService.save(discount);
        return ResponseEntity.ok("Thêm mã giảm giá thành công");
    }


    @GetMapping("/editdiscount/{id}")
    public String showEditDiscount(@PathVariable Long id, Model model) {
        Optional<Discount> optionalDiscount = discountService.findById(id);
        if (optionalDiscount.isPresent()) {
            Discount discount = optionalDiscount.get();
            DiscountDTO discountDTO = new DiscountDTO();
            discountDTO.setDiscountId(discount.getDiscountId());
            discountDTO.setVoucher(discount.getVoucher());
            discountDTO.setDescription(discount.getDescription());
            discountDTO.setDiscountPercentage(discount.getDiscountPercentage());
            discountDTO.setStartDate(discount.getStartDate());
            discountDTO.setEndDate(discount.getEndDate());
            discountDTO.setCreateDate(discount.getCreateDate());

            model.addAttribute("discount", discountDTO);
            return "admin/edit_discount";
        } else {
            return "redirect:admin/discount_list";
        }
    }

    @PostMapping("/updatediscount/{id}")
    public ResponseEntity<?> updateDiscount(@PathVariable Long id,
                                            @ModelAttribute @Valid DiscountDTO discountDTO) {
        Optional<Discount> optionalDiscount = discountService.findById(id);
        if (optionalDiscount.isPresent()) {
            Discount discount = optionalDiscount.get();
            discount.setVoucher(discountDTO.getVoucher());
            discount.setDescription(discountDTO.getDescription());
            discount.setDiscountPercentage(discountDTO.getDiscountPercentage());
            discount.setStartDate(discountDTO.getStartDate());
            discount.setEndDate(discountDTO.getEndDate());
            discount.setCreateDate(discountDTO.getCreateDate());

            Discount updatedDiscount = discountService.save(discount);
            return ResponseEntity.ok("Cập nhật mã giảm giá thành công");
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy mã giảm giá");
        }
    }


    @GetMapping("/deletediscount/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteDiscount(@PathVariable Long id) {
        Optional<Discount> optionalDiscount = discountService.findById(id);
        if (optionalDiscount.isPresent()) {
            discountService.deleteById(id);
            return ResponseEntity.ok("Xóa mã giảm giá thành công");
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy mã giảm giá");
        }
    }

}
