package com.fashionweb.Controllers;

import com.fashionweb.Enum.OrderStatus;
import com.fashionweb.dto.request.orderAdmin.ItemDetailDTO;
import com.fashionweb.dto.request.orderAdmin.OrderDetailAdminDTO;
import com.fashionweb.service.Impl.OrderItemService;
import com.fashionweb.service.Impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class OrderDetailController {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/order-detail/{id}")
    public String showOrderDetailList(@PathVariable("id") Long orderId, Model model){
        OrderDetailAdminDTO orderDetail = orderService.fetchOrderDetail(orderId);
        List<ItemDetailDTO> items = orderItemService.fetchOrderItems(orderId);

        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("items", items);
        return "web/shop/order_detail";
    }

    @PostMapping("/updateorder")
    public String updateOrderStatus(@RequestParam("orderId") Long orderId,
                                    @RequestParam("status") OrderStatus status,
                                    RedirectAttributes redirectAttributes) {
        try {
            boolean success = orderService.updateOrderStatus(orderId, status);

            if (success) {
                redirectAttributes.addFlashAttribute("message", "Order status updated successfully");
            } else {
                redirectAttributes.addFlashAttribute("message", "Error updating order status");
            }

            return "redirect:/account/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "An error occurred: " + e.getMessage());
            return "redirect:/account/orders";
        }
    }
}
