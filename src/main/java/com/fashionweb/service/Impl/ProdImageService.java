package com.fashionweb.service.Impl;

import com.fashionweb.Entity.ProdImage;
import com.fashionweb.repository.IProdImageRepository;
import com.fashionweb.service.IProdImageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdImageService implements IProdImageService {

    @Autowired
    private IProdImageRepository iProdImageRepository;

    @Override
    public List<ProdImage> getAllProdImages() {
        // Trả về tất cả hình ảnh sản phẩm từ cơ sở dữ liệu
        return iProdImageRepository.findAll();
    }

    @Override
    public Optional<ProdImage> getProdImage(Long imageId) {
        // Trả về hình ảnh sản phẩm theo ID, nếu không tìm thấy sẽ trả về Optional.empty()
        return iProdImageRepository.findById(imageId);
    }

    @Override
    public <S extends ProdImage> S createProdImage(S prodImage) {
        // Kiểm tra hình ảnh có tồn tại với sản phẩm chưa
        if (prodImage.getProduct() == null || prodImage.getProduct().getProdId() == null) {
            throw new RuntimeException("Sản phẩm không hợp lệ.");
        }
        return iProdImageRepository.save(prodImage);
    }

    @Override
    public <S extends ProdImage> S updateProdImage(S prodImage) {
        // Kiểm tra xem sản phẩm có tồn tại không trước khi cập nhật
        if (prodImage.getProduct() == null || prodImage.getProduct().getProdId() == null) {
            throw new RuntimeException("Sản phẩm không hợp lệ.");
        }
        return iProdImageRepository.save(prodImage);
    }

    @Override
    public void deleteProdImage(Long imageId) {
        // Xóa hình ảnh sản phẩm theo ID
        iProdImageRepository.deleteById(imageId);
    }

    @Override
    public List<ProdImage> getProdImagesByProduct(Long prodId) {
        // Trả về tất cả các hình ảnh sản phẩm của sản phẩm với prodId
        return iProdImageRepository.findByProductProdId(prodId);
    }

    public List<String> findImageNamesByProdId(Long prodId) {
        return iProdImageRepository.fetchImageNamesByProdId(prodId);
    }
}
