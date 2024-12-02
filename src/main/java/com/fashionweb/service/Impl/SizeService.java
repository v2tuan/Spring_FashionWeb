package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Size;
import com.fashionweb.repository.ISizeRepository;
import com.fashionweb.service.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeService implements ISizeService {

    @Autowired
    private ISizeRepository iSizeRepository;

    @Override
    public List<Size> getAllSizes() {
        // Trả về tất cả kích thước từ cơ sở dữ liệu
        return iSizeRepository.findAll();
    }

    @Override
    public Optional<Size> getSize(Long sizeId) {
        // Trả về kích thước theo ID
        return iSizeRepository.findById(sizeId);
    }

    @Override
    public <S extends Size> S createSize(S size) {
        // Kiểm tra tính hợp lệ của thông tin sản phẩm trước khi lưu
        if (size.getProduct() == null || size.getProduct().getProdId() == null) {
            throw new RuntimeException("Thông tin sản phẩm không hợp lệ.");
        }
        return iSizeRepository.save(size);
    }

    @Override
    public <S extends Size> S updateSize(S size) {
        // Kiểm tra tính hợp lệ của thông tin trước khi cập nhật
        if (size.getProduct() == null || size.getProduct().getProdId() == null) {
            throw new RuntimeException("Thông tin sản phẩm không hợp lệ.");
        }
        return iSizeRepository.save(size);
    }

    @Override
    public void deleteSize(Long sizeId) {
        // Xóa kích thước theo ID
        iSizeRepository.deleteById(sizeId);
    }

    @Override
    public List<Size> getSizesByProduct(Long prodId) {
        // Tìm kích thước theo sản phẩm
        return iSizeRepository.findByProduct_ProdId(prodId);
    }
}
