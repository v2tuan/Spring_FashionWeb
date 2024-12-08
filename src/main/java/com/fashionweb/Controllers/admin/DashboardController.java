package com.fashionweb.Controllers.admin;

import com.fashionweb.Enum.OrderStatus;
import com.fashionweb.dto.request.orderAdmin.OrderAdminDTO;
import com.fashionweb.service.Impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class DashboardController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/admin/dashboard")
    public String showOrderList(
            @RequestParam(value = "orderDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate orderDate,
            @RequestParam(value = "orderStatus", required = false) String orderStatus,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model) {

        // Chuyển giá trị orderStatus thành enum OrderStatus nếu có
        OrderStatus status = null;
        if (orderStatus != null && !orderStatus.isEmpty()) {
            try {
                status = OrderStatus.valueOf(orderStatus); // Chuyển chuỗi thành enum
            } catch (IllegalArgumentException e) {
                // Nếu giá trị không hợp lệ, không làm gì
                status = null;
            }
        }

        // Lấy các thông tin tổng quan
        Double totalRevenue = orderService.getTotalRevenue();
        long totalOrders = orderService.countTotalOrders();
        Long totalProductsSold = orderService.getTotalProductsSold();

        // Cấu hình pagination
        Pageable pageable = PageRequest.of(page, size);

        // Lấy danh sách đơn hàng đã lọc
        Page<OrderAdminDTO> orders = orderService.findAllOrderListPageable(orderDate, status, pageable);

        // Gửi dữ liệu sang view
        model.addAttribute("orders", orders.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());

        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalProductsSold", totalProductsSold);

        return "admin/dashboard";
    }
}
