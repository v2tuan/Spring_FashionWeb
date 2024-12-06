package com.fashionweb.Controllers;

import com.fashionweb.Entity.Discount;
import com.fashionweb.Entity.Order;
import com.fashionweb.dto.request.discount.DiscountDTO;
import com.fashionweb.service.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class DiscountUserController {
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
        return "admin/discountforuser_list";
    }

}
