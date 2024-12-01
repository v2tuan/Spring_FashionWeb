package com.fashionweb.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GravatarUtil {
    public static String getAvatarUrlFromEmail(String email, int size) {
        // Chuẩn hóa email (chuyển thành chữ thường và bỏ khoảng trắng)
        String emailHash = DigestUtils.md5Hex(email.trim().toLowerCase());

        // Tạo màu xám dựa trên hash của email
        String avatarColor = getGrayColorFromHash(emailHash);

        // Tạo URL ảnh đại diện dựa trên màu sắc và kích thước
        return "https://www.gravatar.com/avatar/" + emailHash + "?s=" + size + "&d=retro" + "&d=blank";
    }

    // Hàm tạo màu xám từ hash của email
    private static String getGrayColorFromHash(String emailHash) {
        Random random = new Random(emailHash.hashCode());  // Dùng hash của email để tạo số ngẫu nhiên

        // Tạo giá trị ngẫu nhiên cho kênh màu xám (Red = Green = Blue)
        int gray = random.nextInt(256);  // Giá trị từ 0 đến 255

        // Trả về màu dưới dạng chuỗi HEX
        return String.format("#%02x%02x%02x", gray, gray, gray);
    }
}
