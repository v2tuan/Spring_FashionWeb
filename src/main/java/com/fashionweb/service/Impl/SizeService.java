package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Embeddable.SizeId;
import com.fashionweb.Entity.Size;
import com.fashionweb.dto.request.product.SizeDTO;
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
        return iSizeRepository.save(size);
    }

    @Override
    public <S extends Size> S updateSize(S size) {

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

    public List<SizeId> simplifiedSizes(List<Size> sizes) {
        return sizes.stream().map(Size::getId).toList();
    }

    public SizeDTO sizeDTO(Size size) {
        SizeDTO sizeDTO = new SizeDTO();
        sizeDTO.setName(size.getId().getSizeName());
        sizeDTO.setQuantity(size.getQuantity());
        return sizeDTO;
    }

    public List<SizeDTO> sizeDTOs(List<Size> sizes) {
        return sizes.stream().map(this::sizeDTO).toList();
    }

    public List<SizeDTO> findSizeDTOsByProdId(Long prodId) {
        return iSizeRepository.fetchSizeDTOs(prodId);
    }
}
