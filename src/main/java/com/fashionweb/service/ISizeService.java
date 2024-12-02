package com.fashionweb.service;

import com.fashionweb.Entity.Size;

import java.util.List;
import java.util.Optional;

public interface ISizeService {
    List<Size> getAllSizes(); // Lấy tất cả các kích thước
    Optional<Size> getSize(Long sizeId); // Lấy kích thước theo id
    <S extends Size> S createSize(S size); // Tạo mới kích thước
    <S extends Size> S updateSize(S size); // Cập nhật kích thước
    void deleteSize(Long sizeId); // Xóa kích thước
    List<Size> getSizesByProduct(Long prodId); // Tìm kích thước theo sản phẩm
}
