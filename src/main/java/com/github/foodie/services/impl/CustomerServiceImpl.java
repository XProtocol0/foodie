package com.github.foodie.services.impl;

import com.github.foodie.controllers.request.RegisterUserReq;
import com.github.foodie.dtos.CustomerDto;
import com.github.foodie.dtos.OrderRequestDto;
import com.github.foodie.entities.CustomerEntity;
import com.github.foodie.entities.UserAccountEntity;
import com.github.foodie.repositories.CustomerRepository;
import com.github.foodie.services.CustomerService;
import com.github.foodie.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserAccountService userAccountService;

    @Override
    public CustomerEntity registerCustomer(Long userId) {
        UserAccountEntity userAccountEntity = userAccountService.getUserById(userId);
        // check for role if user can be customer
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setUserAccountEntity(userAccountEntity);
        customerEntity.setUserId(userAccountEntity.getId());
        customerEntity.setRating(BigDecimal.ZERO);
        customerRepository.save(customerEntity);
        return customerEntity;
    }

    @Override
    public CustomerEntity getCustomer(UUID customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId).orElse(null);
        if(Objects.isNull(customerEntity)) {
            //show error
        }
        return customerEntity;
    }


    @Override
    public OrderRequestDto cancelOrder(UUID orderRequestId) {
        return null;
    }

    @Override
    public void rateShipper(UUID orderRequestId, Integer rating) {

    }

    @Override
    public CustomerDto getProfile() {
        return null;
    }

    @Override
    public List<OrderRequestDto> getAllOrders() {
        return List.of();
    }
}
