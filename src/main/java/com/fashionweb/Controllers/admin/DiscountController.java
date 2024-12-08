package com.fashionweb.Controllers.admin;

import com.fashionweb.Entity.*;
import com.fashionweb.dto.request.discount.DiscountDTO;
import com.fashionweb.service.IDiscountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager")
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
    public String createDiscount(@ModelAttribute @Valid DiscountDTO discountDTO, RedirectAttributes redirectAttributes) {
        Discount discount = new Discount();
        discount.setVoucher(discountDTO.getVoucher());
        discount.setDescription(discountDTO.getDescription());
        discount.setDiscountPercentage(discountDTO.getDiscountPercentage());
        discount.setStartDate(discountDTO.getStartDate());
        discount.setEndDate(discountDTO.getEndDate());
        discount.setCreateDate(discountDTO.getCreateDate());

        discountService.save(discount);

        redirectAttributes.addFlashAttribute("message", "Thêm mã giảm giá thành công!");

        return "redirect:/admin/discountlist";
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
    public String updateDiscount(@PathVariable Long id,
                                 @ModelAttribute @Valid DiscountDTO discountDTO,
                                 RedirectAttributes redirectAttributes) {
        Optional<Discount> optionalDiscount = discountService.findById(id);
        if (optionalDiscount.isPresent()) {
            Discount discount = optionalDiscount.get();
            discount.setVoucher(discountDTO.getVoucher());
            discount.setDescription(discountDTO.getDescription());
            discount.setDiscountPercentage(discountDTO.getDiscountPercentage());
            discount.setStartDate(discountDTO.getStartDate());
            discount.setEndDate(discountDTO.getEndDate());
            discount.setCreateDate(discountDTO.getCreateDate());

            discountService.save(discount);

            redirectAttributes.addFlashAttribute("message", "Cập nhật mã giảm giá thành công!");

            return "redirect:/admin/discountlist";
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy mã giảm giá cần cập nhật!");

            return "redirect:/admin/discountlist";
        }
    }


    @GetMapping("/deletediscount/{id}")
    public String deleteDiscount(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Discount> optionalDiscount = discountService.findById(id);
        if (optionalDiscount.isPresent()) {
            discountService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Xóa mã giảm giá thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy mã giảm giá để xóa!");
        }
        return "redirect:/admin/discountlist";
    }


}
