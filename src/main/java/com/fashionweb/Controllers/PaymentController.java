package com.fashionweb.Controllers;

import com.fashionweb.Enum.OrderStatus;
import com.fashionweb.service.Impl.OrderService;
import com.fashionweb.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/user/submitOrder")
    public String submidOrder(@RequestParam("amount") Double orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request){
        int orderTotalInt = (int) Math.floor(orderTotal);

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = paymentService.createOrder(orderTotalInt, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){

        int paymentStatus =paymentService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        Long orderId = Long.parseLong(orderInfo);

        if (paymentStatus == 1) {
            redirectAttributes.addFlashAttribute("message", "Payment successful");
        } else {
            redirectAttributes.addFlashAttribute("message", "Payment failed");
        }

        return "redirect:/user/order-detail/"+orderId;
    }

}
