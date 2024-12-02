package com.fashionweb.service;

import com.fashionweb.Entity.ProdImage;

import java.util.List;
import java.util.Optional;

public interface IProdImageService {
    List<ProdImage> getAllProdImages();
    Optional<ProdImage> getProdImage(Long imageId);
    <S extends ProdImage> S createProdImage(S prodImage);
    <S extends ProdImage> S updateProdImage(S prodImage);
    void deleteProdImage(Long imageId);

    List<ProdImage> getProdImagesByProduct(Long prodId);
}
