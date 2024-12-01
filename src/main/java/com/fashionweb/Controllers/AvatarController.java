package com.fashionweb.Controllers;

import com.fashionweb.service.AvatarService;
import com.fashionweb.service.GravatarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class AvatarController {
    @Autowired
    private AvatarService avatarService;
    @GetMapping("/avatar/{username}")
    @ResponseBody
    public byte[] generateAvatar(@PathVariable String username) throws IOException {
        // Sử dụng jdenticon để tạo hình ảnh avatar từ tên người dùng
        String hash = username != null ? username : "default";
        BufferedImage avatar = avatarService.generateAvatarFromText(hash);

        // Chuyển đổi BufferedImage thành mảng byte để trả về dưới dạng hình ảnh
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(avatar, "PNG", baos);
        return baos.toByteArray();
    }

    @GetMapping("/test")
    String index(){
        return "testavatar";
    }

    @GetMapping("/getUserGravatar")
    public String getUserGravatar(@RequestParam String email) {
        // Gọi hàm GravatarUtil để lấy URL ảnh
        String gravatarUrl = GravatarUtil.getAvatarUrlFromEmail(email, 80); // Kích thước ảnh là 80x80 pixels
        return gravatarUrl;
    }
}
