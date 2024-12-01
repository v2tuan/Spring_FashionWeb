package com.fashionweb.service;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Service
public class AvatarService {
    public static BufferedImage generateAvatarFromText(String text) {
        // Cấu hình kích thước và các thông số khác của avatar
        int size = 100;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Lấy hash của tên người dùng để tạo ra một chuỗi ngẫu nhiên dùng cho việc tạo avatar
        int hash = text.hashCode();
        Random random = new Random(hash);

        // Tạo màu nền dựa trên hash của tên người dùng (màu sắc ngẫu nhiên)
        Color backgroundColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, size, size);

        // Vẽ chữ cái đầu tiên của tên người dùng ở giữa hình ảnh
        g2d.setColor(Color.WHITE);  // Màu chữ trắng
        g2d.setFont(new Font("Arial", Font.BOLD, 50));  // Font chữ lớn để dễ nhìn
        String initial = text.substring(0, 1).toUpperCase();  // Lấy chữ cái đầu tiên
        FontMetrics metrics = g2d.getFontMetrics();

        // Căn giữa chữ cái đầu tiên
        int x = (size - metrics.stringWidth(initial)) / 2;  // Căn giữa theo chiều ngang
        int y = (size + metrics.getAscent()) / 2;  // Căn giữa theo chiều dọc
        g2d.drawString(initial, x, y);  // Vẽ chữ vào vị trí đã căn chỉnh

        g2d.dispose();
        return image;
    }
}
