package com.fashionweb.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageHTTPStatus {
    @GetMapping("/403")
    String error403() {
        return "403";
    }
}
