package com.fashionweb.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PageHTTPStatus {
    @GetMapping("/error/401")
    String error401() {
        return "401";
    }
}
