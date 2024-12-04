package com.fashionweb.service.Impl;

import com.fashionweb.Entity.Discount;
import com.fashionweb.Entity.Order;
import com.fashionweb.repository.IDiscountRepository;
import com.fashionweb.repository.IOrderRepository;
import com.fashionweb.service.IDiscountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountService implements IDiscountService {
    @Autowired
    private IDiscountRepository discountRepository;
    @Autowired
    private IOrderRepository orderRepos;

    @Override
    public <S extends Discount> S save(S entity) {
        if(entity.getDiscountId() == null){
            return discountRepository.save(entity);
        }else {
            Optional<Discount> discountOptional = discountRepository.findById(entity.getDiscountId());
            if (discountOptional.isPresent()) {
                return discountRepository.save(entity);
            } else {
                throw new EntityNotFoundException("Discount with ID " + entity.getDiscountId() + " does not exist.");
            }
        }
    }

    @Override
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    @Override
    public Optional<Discount> findById(Long id) {
        return discountRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        discountRepository.deleteById(id);
    }

    @Override
    public Optional<Discount> findByStartDate(Date startDate) {
        return discountRepository.findByStartDate(startDate);
    }

    @Override
    public Optional<Discount> findByEndDate(Date endDate) {
        return discountRepository.findByEndDate(endDate);
    }

    @Override
    public List<Order> findOrdersByDiscountId(Long discountId) {
        return orderRepos.findAllByDiscountDiscountId(discountId);
    }
}
